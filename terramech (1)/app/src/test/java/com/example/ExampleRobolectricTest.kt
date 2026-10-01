package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("TerraMech", appName)
  }

  @Test
  fun `verify curing guides and steps catalog`() {
    val guides = com.example.data.MockData.curingGuides
    assertEquals(true, guides.isNotEmpty())
    val handiGuide = guides.first { it.id == "curing_handi" }
    assertEquals(4, handiGuide.steps.size)
    assertEquals("Cooking Handi & Kadai", handiGuide.potType)
  }

  @Test
  fun `verify clay recipes and issues catalog`() {
    val recipes = com.example.data.MockData.clayRecipes
    assertEquals(true, recipes.isNotEmpty())
    val biryani = recipes.first { it.id == "recipe_biryani" }
    assertEquals("Earthen Dum Biryani", biryani.title)

    val issues = com.example.data.MockData.potIssues
    assertEquals(true, issues.isNotEmpty())
    val moldIssue = issues.first { it.id == "issue_mold" }
    assertEquals("White Mold / Mildew Growth", moldIssue.title)
  }
}
