package com.example.data.firebase

import android.content.Context
import android.util.Log
import com.example.model.AnalysisResult
import com.example.model.HeatSuitability
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

data class UserProfile(
    val uid: String,
    val email: String,
    val displayName: String,
    val photoUrl: String? = null,
    val isAnonymous: Boolean = false,
    val isCloudSynced: Boolean = false
)

class FirebaseManager(private val context: Context) {

    private val tag = "FirebaseManager"

    val isFirebaseAvailable: Boolean
        get() = try {
            FirebaseApp.getApps(context).isNotEmpty()
        } catch (_: Exception) {
            false
        }

    private val auth: FirebaseAuth?
        get() = if (isFirebaseAvailable) {
            try { FirebaseAuth.getInstance() } catch (_: Exception) { null }
        } else null

    private val firestore: FirebaseFirestore?
        get() = if (isFirebaseAvailable) {
            try { FirebaseFirestore.getInstance() } catch (_: Exception) { null }
        } else null

    val currentUser: UserProfile?
        get() {
            val user = auth?.currentUser ?: return null
            return UserProfile(
                uid = user.uid,
                email = user.email ?: "artisan@terramech.app",
                displayName = user.displayName ?: user.email?.substringBefore("@") ?: "Artisan",
                photoUrl = user.photoUrl?.toString(),
                isAnonymous = user.isAnonymous,
                isCloudSynced = true
            )
        }

    suspend fun signInWithEmail(email: String, pass: String): Result<UserProfile> = withContext(Dispatchers.IO) {
        val a = auth
        if (a == null) {
            // Graceful demo mode fallback when google-services.json is not configured
            return@withContext Result.success(
                UserProfile(
                    uid = "demo_artisan_user",
                    email = if (email.isBlank()) "artisan@terramech.app" else email,
                    displayName = if (email.contains("@")) email.substringBefore("@") else "Artisan User",
                    isCloudSynced = false
                )
            )
        }

        try {
            val authResult = try {
                a.signInWithEmailAndPassword(email, pass).await()
            } catch (_: Exception) {
                // If account doesn't exist, create it
                a.createUserWithEmailAndPassword(email, pass).await()
            }

            val user = authResult.user
            if (user != null) {
                Result.success(
                    UserProfile(
                        uid = user.uid,
                        email = user.email ?: email,
                        displayName = user.displayName ?: email.substringBefore("@"),
                        isCloudSynced = true
                    )
                )
            } else {
                Result.failure(Exception("Failed to obtain user session"))
            }
        } catch (e: Exception) {
            Log.e(tag, "Firebase email auth error: ${e.message}", e)
            Result.failure(e)
        }
    }

    suspend fun signInWithGoogleCredential(idToken: String): Result<UserProfile> = withContext(Dispatchers.IO) {
        val a = auth ?: return@withContext Result.failure(Exception("Firebase not initialized"))
        try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val authResult = a.signInWithCredential(credential).await()
            val user = authResult.user
            if (user != null) {
                Result.success(
                    UserProfile(
                        uid = user.uid,
                        email = user.email ?: "google.artisan@terramech.app",
                        displayName = user.displayName ?: "Google Artisan",
                        photoUrl = user.photoUrl?.toString(),
                        isCloudSynced = true
                    )
                )
            } else {
                Result.failure(Exception("Google Sign-In returned null user"))
            }
        } catch (e: Exception) {
            Log.e(tag, "Google credential auth error: ${e.message}", e)
            Result.failure(e)
        }
    }

    suspend fun signOut() = withContext(Dispatchers.IO) {
        try {
            auth?.signOut()
        } catch (e: Exception) {
            Log.e(tag, "Sign out error", e)
        }
    }

    // Firestore Real-time Analysis Sync
    suspend fun saveAnalysisToCloud(userId: String, result: AnalysisResult) = withContext(Dispatchers.IO) {
        val db = firestore ?: return@withContext
        try {
            val data = hashMapOf(
                "id" to result.id,
                "potType" to result.potType,
                "potTypeExplanation" to result.potTypeExplanation,
                "confidenceScore" to result.confidenceScore,
                "estimatedCapacity" to result.estimatedCapacity,
                "capacityNote" to result.capacityNote,
                "material" to result.material,
                "materialExplanation" to result.materialExplanation,
                "heatSuitability" to result.heatSuitability.name,
                "heatSuitabilityExplanation" to result.heatSuitabilityExplanation,
                "recommendedUsage" to result.recommendedUsage,
                "safetyGuidelines" to result.safetyGuidelines,
                "careAndMaintenance" to result.careAndMaintenance,
                "smartRecommendations" to result.smartRecommendations,
                "presetKey" to (result.presetKey ?: "handi"),
                "timestamp" to result.timestamp,
                "shapeDescription" to result.shapeDescription
            )
            db.collection("users")
                .document(userId)
                .collection("analyses")
                .document(result.id)
                .set(data)
                .await()
            Log.d(tag, "Successfully saved analysis ${result.id} to Firestore")
        } catch (e: Exception) {
            Log.w(tag, "Cloud sync skipped: ${e.message}")
        }
    }

    suspend fun deleteAnalysisFromCloud(userId: String, analysisId: String) = withContext(Dispatchers.IO) {
        val db = firestore ?: return@withContext
        try {
            db.collection("users")
                .document(userId)
                .collection("analyses")
                .document(analysisId)
                .delete()
                .await()
        } catch (e: Exception) {
            Log.w(tag, "Cloud delete skipped: ${e.message}")
        }
    }

    fun observeCloudAnalyses(userId: String): Flow<List<AnalysisResult>> = callbackFlow {
        val db = firestore
        if (db == null) {
            close()
            return@callbackFlow
        }

        val registration = db.collection("users")
            .document(userId)
            .collection("analyses")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.w(tag, "Firestore listen error", error)
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val list = snapshot.documents.mapNotNull { doc ->
                        try {
                            val heatStr = doc.getString("heatSuitability") ?: "UNKNOWN"
                            val heat = try { HeatSuitability.valueOf(heatStr) } catch (_: Exception) { HeatSuitability.UNKNOWN }
                            AnalysisResult(
                                id = doc.getString("id") ?: doc.id,
                                potType = doc.getString("potType") ?: "Traditional Clay Pot",
                                potTypeExplanation = doc.getString("potTypeExplanation") ?: "",
                                confidenceScore = (doc.getDouble("confidenceScore") ?: 0.95).toFloat(),
                                estimatedCapacity = doc.getString("estimatedCapacity") ?: "2.5 L",
                                capacityNote = doc.getString("capacityNote") ?: "Estimated from image.",
                                material = doc.getString("material") ?: "Natural Terracotta",
                                materialExplanation = doc.getString("materialExplanation") ?: "",
                                heatSuitability = heat,
                                heatSuitabilityExplanation = doc.getString("heatSuitabilityExplanation") ?: "",
                                recommendedUsage = (doc.get("recommendedUsage") as? List<*>)?.mapNotNull { it?.toString() } ?: emptyList(),
                                safetyGuidelines = (doc.get("safetyGuidelines") as? List<*>)?.mapNotNull { it?.toString() } ?: emptyList(),
                                careAndMaintenance = (doc.get("careAndMaintenance") as? List<*>)?.mapNotNull { it?.toString() } ?: emptyList(),
                                smartRecommendations = (doc.get("smartRecommendations") as? List<*>)?.mapNotNull { it?.toString() } ?: emptyList(),
                                presetKey = doc.getString("presetKey"),
                                timestamp = doc.getLong("timestamp") ?: System.currentTimeMillis(),
                                shapeDescription = doc.getString("shapeDescription") ?: ""
                            )
                        } catch (e: Exception) {
                            null
                        }
                    }
                    trySend(list)
                }
            }
        awaitClose { registration.remove() }
    }
}
