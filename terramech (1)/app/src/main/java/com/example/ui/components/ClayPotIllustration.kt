package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.ui.theme.OrganicSageLight
import com.example.ui.theme.TerracottaContainer
import com.example.ui.theme.TerracottaLight
import com.example.ui.theme.TerracottaPrimary
import com.example.ui.theme.TerracottaPrimaryVariant

@Composable
fun ClayPotIllustration(
    modifier: Modifier = Modifier,
    presetKey: String? = "handi",
    isScanning: Boolean = false,
    size: Dp = 180.dp
) {
    val infiniteTransition = rememberInfiniteTransition(label = "Scanning")
    val scanProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scanProgress"
    )

    Box(
        modifier = modifier
            .size(size)
            .clip(RoundedCornerShape(20.dp))
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        TerracottaContainer.copy(alpha = 0.5f),
                        Color(0xFFF7EFE5),
                        Color(0xFFEBE0D0)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = this.size.width
            val h = this.size.height

            // Background subtle clay texture lines
            for (i in 1..4) {
                drawCircle(
                    color = Color(0x12A0401B),
                    radius = w * 0.15f * i,
                    center = Offset(w * 0.5f, h * 0.55f),
                    style = Stroke(width = 1.5f)
                )
            }

            when (presetKey) {
                "matka" -> {
                    // Spherical Matka Pitcher
                    val bodyBrush = Brush.radialGradient(
                        colors = listOf(TerracottaLight, TerracottaPrimary, TerracottaPrimaryVariant),
                        center = Offset(w * 0.42f, h * 0.52f),
                        radius = w * 0.45f
                    )
                    // Neck
                    val neckPath = Path().apply {
                        moveTo(w * 0.38f, h * 0.28f)
                        lineTo(w * 0.35f, h * 0.22f)
                        lineTo(w * 0.65f, h * 0.22f)
                        lineTo(w * 0.62f, h * 0.28f)
                        close()
                    }
                    drawPath(neckPath, bodyBrush)
                    // Rim
                    drawOval(
                        color = Color(0xFFD67347),
                        topLeft = Offset(w * 0.32f, h * 0.19f),
                        size = Size(w * 0.36f, h * 0.08f)
                    )
                    drawOval(
                        color = Color(0xFF75280E),
                        topLeft = Offset(w * 0.36f, h * 0.21f),
                        size = Size(w * 0.28f, h * 0.045f)
                    )
                    // Main Round Belly
                    drawCircle(
                        brush = bodyBrush,
                        radius = w * 0.34f,
                        center = Offset(w * 0.5f, h * 0.58f)
                    )
                    // Stand Base
                    drawOval(
                        color = Color(0xFF6B2B15),
                        topLeft = Offset(w * 0.38f, h * 0.88f),
                        size = Size(w * 0.24f, h * 0.05f)
                    )
                    // Decorative tribal pattern around shoulder
                    val patternPath = Path().apply {
                        moveTo(w * 0.22f, h * 0.52f)
                        quadraticTo(w * 0.5f, h * 0.62f, w * 0.78f, h * 0.52f)
                    }
                    drawPath(
                        path = patternPath,
                        color = Color(0x55FFE4D6),
                        style = Stroke(width = 3f)
                    )
                }
                "tawa" -> {
                    // Terracotta Roti Tawa (Convex disc with handle)
                    val tawaBrush = Brush.linearGradient(
                        colors = listOf(TerracottaPrimary, Color(0xFF722D16), Color(0xFF4A1A0B)),
                        start = Offset(w * 0.2f, h * 0.3f),
                        end = Offset(w * 0.8f, h * 0.7f)
                    )
                    // Handle
                    val handlePath = Path().apply {
                        moveTo(w * 0.68f, h * 0.62f)
                        lineTo(w * 0.92f, h * 0.76f)
                        lineTo(w * 0.88f, h * 0.84f)
                        lineTo(w * 0.62f, h * 0.70f)
                        close()
                    }
                    drawPath(handlePath, Color(0xFF5A2514))
                    // Rim Bevel
                    drawOval(
                        brush = tawaBrush,
                        topLeft = Offset(w * 0.12f, h * 0.22f),
                        size = Size(w * 0.68f, h * 0.52f)
                    )
                    // Cooking Surface Inner Circle
                    drawOval(
                        color = Color(0xFF8C391B),
                        topLeft = Offset(w * 0.16f, h * 0.26f),
                        size = Size(w * 0.60f, h * 0.44f)
                    )
                    // Center seasoning ring
                    drawOval(
                        color = Color(0x332B1810),
                        topLeft = Offset(w * 0.26f, h * 0.34f),
                        size = Size(w * 0.40f, h * 0.28f)
                    )
                }
                "urn" -> {
                    // Glazed Decorative Urn
                    val urnBrush = Brush.linearGradient(
                        colors = listOf(Color(0xFFE56B3A), Color(0xFF9E3A18), Color(0xFF481507)),
                        start = Offset(w * 0.25f, h * 0.2f),
                        end = Offset(w * 0.75f, h * 0.8f)
                    )
                    // Base Foot
                    drawOval(
                        color = Color(0xFF5C200E),
                        topLeft = Offset(w * 0.36f, h * 0.84f),
                        size = Size(w * 0.28f, h * 0.06f)
                    )
                    // Body
                    val urnPath = Path().apply {
                        moveTo(w * 0.38f, h * 0.26f)
                        cubicTo(w * 0.20f, h * 0.38f, w * 0.22f, h * 0.62f, w * 0.40f, h * 0.84f)
                        lineTo(w * 0.60f, h * 0.84f)
                        cubicTo(w * 0.78f, h * 0.62f, w * 0.80f, h * 0.38f, w * 0.62f, h * 0.26f)
                        close()
                    }
                    drawPath(urnPath, urnBrush)
                    // Side Handles
                    val leftHandle = Path().apply {
                        moveTo(w * 0.25f, h * 0.36f)
                        cubicTo(w * 0.12f, h * 0.44f, w * 0.12f, h * 0.58f, w * 0.27f, h * 0.64f)
                    }
                    drawPath(leftHandle, Color(0xFF883113), style = Stroke(width = 6f, cap = StrokeCap.Round))

                    val rightHandle = Path().apply {
                        moveTo(w * 0.75f, h * 0.36f)
                        cubicTo(w * 0.88f, h * 0.44f, w * 0.88f, h * 0.58f, w * 0.73f, h * 0.64f)
                    }
                    drawPath(rightHandle, Color(0xFF883113), style = Stroke(width = 6f, cap = StrokeCap.Round))
                    // Rim
                    drawOval(
                        color = Color(0xFFD67347),
                        topLeft = Offset(w * 0.32f, h * 0.20f),
                        size = Size(w * 0.36f, h * 0.09f)
                    )
                    // Glaze shine reflection
                    drawOval(
                        color = Color(0x40FFFFFF),
                        topLeft = Offset(w * 0.34f, h * 0.36f),
                        size = Size(w * 0.10f, h * 0.22f)
                    )
                }
                else -> {
                    // Traditional Cooking Handi
                    val handiBrush = Brush.radialGradient(
                        colors = listOf(TerracottaLight, TerracottaPrimary, Color(0xFF722D16)),
                        center = Offset(w * 0.44f, h * 0.52f),
                        radius = w * 0.44f
                    )
                    // Rim Collar
                    drawOval(
                        color = Color(0xFF5A2514),
                        topLeft = Offset(w * 0.28f, h * 0.22f),
                        size = Size(w * 0.44f, h * 0.11f)
                    )
                    drawOval(
                        color = Color(0xFFD67347),
                        topLeft = Offset(w * 0.30f, h * 0.21f),
                        size = Size(w * 0.40f, h * 0.08f)
                    )
                    // Handi Wide Belly
                    val handiPath = Path().apply {
                        moveTo(w * 0.34f, h * 0.28f)
                        cubicTo(w * 0.16f, h * 0.42f, w * 0.16f, h * 0.72f, w * 0.38f, h * 0.84f)
                        lineTo(w * 0.62f, h * 0.84f)
                        cubicTo(w * 0.84f, h * 0.72f, w * 0.84f, h * 0.42f, w * 0.66f, h * 0.28f)
                        close()
                    }
                    drawPath(handiPath, handiBrush)
                    // Base Ring
                    drawOval(
                        color = Color(0xFF4A1A0B),
                        topLeft = Offset(w * 0.36f, h * 0.82f),
                        size = Size(w * 0.28f, h * 0.06f)
                    )
                    // Authentic Clay concentric wheel lines
                    drawArc(
                        color = Color(0x33FFE2D4),
                        startAngle = 10f,
                        sweepAngle = 160f,
                        useCenter = false,
                        topLeft = Offset(w * 0.22f, h * 0.46f),
                        size = Size(w * 0.56f, h * 0.26f),
                        style = Stroke(width = 2.5f)
                    )
                    drawArc(
                        color = Color(0x22FFE2D4),
                        startAngle = 15f,
                        sweepAngle = 150f,
                        useCenter = false,
                        topLeft = Offset(w * 0.25f, h * 0.56f),
                        size = Size(w * 0.50f, h * 0.22f),
                        style = Stroke(width = 2f)
                    )
                }
            }

            // AI Overlay HUD Elements
            // Corner Bracket Reticles
            val cornerLen = w * 0.10f
            val strokeW = 3f
            val reticleColor = if (isScanning) OrganicSageLight else TerracottaPrimary.copy(alpha = 0.6f)

            // Top-Left
            drawLine(reticleColor, Offset(w * 0.08f, h * 0.08f), Offset(w * 0.08f + cornerLen, h * 0.08f), strokeW)
            drawLine(reticleColor, Offset(w * 0.08f, h * 0.08f), Offset(w * 0.08f, h * 0.08f + cornerLen), strokeW)

            // Top-Right
            drawLine(reticleColor, Offset(w * 0.92f, h * 0.08f), Offset(w * 0.92f - cornerLen, h * 0.08f), strokeW)
            drawLine(reticleColor, Offset(w * 0.92f, h * 0.08f), Offset(w * 0.92f, h * 0.08f + cornerLen), strokeW)

            // Bottom-Left
            drawLine(reticleColor, Offset(w * 0.08f, h * 0.92f), Offset(w * 0.08f + cornerLen, h * 0.92f), strokeW)
            drawLine(reticleColor, Offset(w * 0.08f, h * 0.92f), Offset(w * 0.08f, h * 0.92f - cornerLen), strokeW)

            // Bottom-Right
            drawLine(reticleColor, Offset(w * 0.92f, h * 0.92f), Offset(w * 0.92f - cornerLen, h * 0.92f), strokeW)
            drawLine(reticleColor, Offset(w * 0.92f, h * 0.92f), Offset(w * 0.92f, h * 0.92f - cornerLen), strokeW)

            // If active scanning, render animated laser line with glow
            if (isScanning) {
                val scanY = h * 0.12f + (h * 0.76f * scanProgress)
                // Laser Glow
                drawLine(
                    color = OrganicSageLight.copy(alpha = 0.35f),
                    start = Offset(w * 0.06f, scanY),
                    end = Offset(w * 0.94f, scanY),
                    strokeWidth = 9f,
                    cap = StrokeCap.Round
                )
                // Laser Core
                drawLine(
                    color = Color.White,
                    start = Offset(w * 0.08f, scanY),
                    end = Offset(w * 0.92f, scanY),
                    strokeWidth = 2.5f,
                    cap = StrokeCap.Round
                )
                // Center tracking dot
                drawCircle(
                    color = Color(0xFF52B788),
                    radius = 5f,
                    center = Offset(w * 0.5f, scanY)
                )
            }
        }
    }
}
