package com.example.data

import com.example.model.AnalysisResult
import com.example.model.ClayPot
import com.example.model.HeatSuitability
import com.example.model.PotCategory
import com.example.model.PotRecommendation
import com.example.model.RecommendationCriteria
import java.util.UUID

object MockData {

    val samplePresets = listOf(
        PresetItem(
            key = "handi",
            title = "Traditional Cooking Handi",
            subtitle = "Deep spherical pot with steam-retaining rim",
            estimatedCapacity = "2.5 L",
            category = PotCategory.COOKING
        ),
        PresetItem(
            key = "matka",
            title = "Earthen Water Matka",
            subtitle = "Round natural porous evaporative cooling pitcher",
            estimatedCapacity = "5.0 L",
            category = PotCategory.WATER_STORAGE
        ),
        PresetItem(
            key = "tawa",
            title = "Terracotta Cooking Tawa",
            subtitle = "Flattish convex griddle for artisanal flatbreads",
            estimatedCapacity = "0.8 L",
            category = PotCategory.COOKING
        ),
        PresetItem(
            key = "urn",
            title = "Glazed Decorative Urn",
            subtitle = "High-gloss finished ceramic terracotta vessel",
            estimatedCapacity = "3.2 L",
            category = PotCategory.DECORATIVE
        )
    )

    data class PresetItem(
        val key: String,
        val title: String,
        val subtitle: String,
        val estimatedCapacity: String,
        val category: PotCategory
    )

    val potsCatalog: List<ClayPot> = listOf(
        ClayPot(
            id = "pot_handi_1",
            name = "Traditional Biryani Handi",
            category = PotCategory.COOKING,
            description = "A deep, wide-mouthed earthen pot with a narrow neck and curved belly. Designed specifically for dum cooking and slow simmer stews.",
            capacity = "2.5 L",
            material = "Natural Unglazed Terracotta with Mica Inclusions",
            heatSuitability = HeatSuitability.SUITABLE,
            heatSuitabilityNote = "Suitable for low to medium direct gas flame and charcoal embers. Requires gradual heat build-up.",
            usage = listOf("Biryani", "Slow Cooking", "Curries", "Dal", "Dum Stews"),
            safetyGuidelines = listOf(
                "Never place cold pot directly onto high flame; sudden thermal shock will cause fractures.",
                "Always ensure liquid or oil is added before placing on heat source.",
                "Do not use if structural hairline cracks are visible near base.",
                "Use heat diffuser on high-BTU modern gas burners."
            ),
            careAndMaintenance = listOf(
                "Initial Seasoning: Soak in clean water for 24 hours, dry in sunlight, rub with edible mustard or sesame oil, and warm with rice starch water.",
                "Cleaning: Scrub only with warm water and soft coconut coir or nylon brush. Never use chemical dish soaps as clay absorbs surfactants.",
                "Drying: Allow to air dry completely in ventilated area before stowing.",
                "Storage: Store uncovered with paper towel inside to absorb any ambient moisture."
            ),
            seasoningInstructions = "Submerge in fresh water for 24 hrs. Coat interior with oil and simmer rice water once before first culinary use.",
            origin = "Northern & Central India Artisanal Guilds",
            presetKey = "handi"
        ),
        ClayPot(
            id = "pot_matka_2",
            name = "Royal Matka Water Cooler",
            category = PotCategory.WATER_STORAGE,
            description = "A classic spherical earthenware pitcher crafted from micro-porous alluvial riverbed clay. Uses capillary evaporation to cool drinking water naturally by 4-6°C.",
            capacity = "5.0 L",
            material = "Unfinished Alluvial Micro-Porous Red Clay",
            heatSuitability = HeatSuitability.NOT_RECOMMENDED,
            heatSuitabilityNote = "Strictly for ambient water storage. Direct heating will bake out micro-capillaries and risk explosion.",
            usage = listOf("Water Storage", "Natural Cooling", "Alkaline Water Infusion", "Household Dispenser"),
            safetyGuidelines = listOf(
                "Keep elevated on a ring stand or sand bowl to allow continuous evaporation.",
                "Do not store dairy, citrus, or fermented beverages in raw water pots.",
                "Flush with fresh boiled water once every fortnight to prevent microbial film."
            ),
            careAndMaintenance = listOf(
                "Cleaning: Rinse with hot water and baking soda or rock salt; avoid scented detergents.",
                "Outer surface: Gently wipe exterior salt rings with damp cotton cloth.",
                "Rejuvenation: Re-soak overnight every season to clear pores."
            ),
            seasoningInstructions = "Fill with fresh water and discard initial batch after 48 hours to activate evaporative capillary pathways.",
            origin = "Rajasthan & Gujarat Clay Traditions",
            presetKey = "matka"
        ),
        ClayPot(
            id = "pot_tawa_3",
            name = "Terracotta Roti Tawa",
            category = PotCategory.COOKING,
            description = "Shallow, gently curved griddle designed for even heat distribution. Imparts natural earthy aroma and superior crust texture to artisanal breads.",
            capacity = "0.8 L",
            material = "Dense Stoneware-Terracotta Composite Clay",
            heatSuitability = HeatSuitability.SUITABLE,
            heatSuitabilityNote = "Suitable for gas flame with heat diffuser plate. Maximum recommended surface temperature 260°C.",
            usage = listOf("Flatbreads", "Rotis", "Pita", "Tortillas", "Dry Roasting Spices"),
            safetyGuidelines = listOf(
                "Preheat gradually on low flame for 4 minutes before raising to cooking temperature.",
                "Never rinse with cold water while the tawa is hot.",
                "Do not deep fry or submerge in excess oil on open flame."
            ),
            careAndMaintenance = listOf(
                "Wipe clean with a damp cloth and a pinch of dry flour after each use.",
                "Lightly wipe with a drop of coconut or vegetable oil before storing.",
                "Never wash in automatic dishwasher."
            ),
            seasoningInstructions = "Rub raw garlic and mustard oil on surface, heat gently on lowest flame for 10 minutes, allow to cool.",
            origin = "South Asian Village Pottery Tradition",
            presetKey = "tawa"
        ),
        ClayPot(
            id = "pot_curd_4",
            name = "Earthen Yogurt Setting Jar",
            category = PotCategory.FERMENTATION,
            description = "Porous clay pot designed to absorb excess whey during milk curdling, yielding exceptionally thick, creamy, natural yogurt without synthetic gelatin.",
            capacity = "1.5 L",
            material = "Low-Fire Porous Terracotta",
            heatSuitability = HeatSuitability.NOT_RECOMMENDED,
            heatSuitabilityNote = "Used at ambient temperatures for setting yogurt and microbial fermentation.",
            usage = listOf("Yogurt Setting", "Kefir", "Buttermilk Storage", "Culturing"),
            safetyGuidelines = listOf(
                "Clean thoroughly between culture cycles to maintain beneficial bacterial balance.",
                "Not suitable for direct flame or oven baking."
            ),
            careAndMaintenance = listOf(
                "Wash with warm water and lemon rind or mild vinegar rinse.",
                "Sun dry thoroughly for at least 4 hours after washing."
            ),
            seasoningInstructions = "Rinse with hot water and let air dry completely before introducing starter culture.",
            origin = "Bengal & Odisha Earthen Curd Vessels",
            presetKey = "handi"
        ),
        ClayPot(
            id = "pot_kulhar_5",
            name = "Traditional Artisanal Kulhar",
            category = PotCategory.TRADITIONAL,
            description = "Single-fired terracotta cup without glaze, traditionally used for steaming spiced chai or chilled rabdi, imparting unmatched petrichor aroma.",
            capacity = "0.25 L",
            material = "Single-Fired Unglazed Earthen Clay",
            heatSuitability = HeatSuitability.LIMITED,
            heatSuitabilityNote = "Can hold boiling hot tea or coffee; not intended for placement over direct flames.",
            usage = listOf("Masala Chai", "Lassi", "Dessert Serving", "Rabdi", "Artisanal Tableware"),
            safetyGuidelines = listOf(
                "Ensure smooth rim before drinking to avoid clay micro-splinters.",
                "Disposable in traditional culture, but can be reused for cold desserts if washed gently."
            ),
            careAndMaintenance = listOf(
                "Hand rinse in warm water; avoid harsh scrubbing to preserve natural edge.",
                "Store in dry ambient cupboard."
            ),
            seasoningInstructions = "Brief warm water soak for 5 minutes prior to first serving.",
            origin = "Varanasi Pottery Clusters",
            presetKey = "handi"
        ),
        ClayPot(
            id = "pot_urn_6",
            name = "Glazed Terracotta Vessel",
            category = PotCategory.DECORATIVE,
            description = "Stately terracotta urn with decorative glazed exterior and sealed inner lining. Designed for aesthetic interior decor, flower arrangements, or dry grain storage.",
            capacity = "3.2 L",
            material = "Fired Terracotta with Lead-Free Vitrified Glaze",
            heatSuitability = HeatSuitability.NOT_RECOMMENDED,
            heatSuitabilityNote = "Glazed finishes are not rated for stovetop cooking. Glaze might craze or chip under flame.",
            usage = listOf("Home Decor", "Dry Goods Storage", "Planter", "Centerpiece"),
            safetyGuidelines = listOf(
                "Check food safety rating if storing acidic liquids.",
                "Keep away from sudden knocks on stone or metal surfaces."
            ),
            careAndMaintenance = listOf(
                "Clean with microfiber cloth and mild eco-friendly soap.",
                "Polish exterior with natural beeswax for lasting sheen."
            ),
            seasoningInstructions = "No seasoning required due to sealed glaze.",
            origin = "Mediterranean & Khurja Heritage Studios",
            presetKey = "urn"
        ),
        ClayPot(
            id = "pot_pickle_7",
            name = "Martaban Ceramic-Clay Pickle Jar",
            category = PotCategory.STORAGE,
            description = "Traditional glazed top and unglazed terracotta base jar specifically crafted for fermenting mango, lime, and chili pickles in mustard oil.",
            capacity = "4.0 L",
            material = "Stoneware-Lined Terracotta with High-Alumina Glaze",
            heatSuitability = HeatSuitability.NOT_RECOMMENDED,
            heatSuitabilityNote = "Acid-resistant storage vessel; not designed for stovetop heating.",
            usage = listOf("Pickle Fermentation", "Dry Spices", "Fermented Vegetables", "Kombucha"),
            safetyGuidelines = listOf(
                "Ensure seal cloth is tied firmly around neck to prevent airborne contaminants.",
                "Use clean, dry wooden spoons only when scooping contents."
            ),
            careAndMaintenance = listOf(
                "Wash with warm water and rock salt; dry fully under midday sun before packing pickles.",
                "Keep lid groove clean."
            ),
            seasoningInstructions = "Sun bake empty for 1 day before adding salt and oil mixture.",
            origin = "Punjab & Uttar Pradesh Heritage Pottery",
            presetKey = "urn"
        ),
        ClayPot(
            id = "pot_serving_8",
            name = "Artisanal Serving Kadhai",
            category = PotCategory.SERVING,
            description = "Rustic earthen bowl with sculpted side handles. Keeps prepared soups, gravies, and rice piping hot on the dining table.",
            capacity = "1.8 L",
            material = "Burnished Terracotta Clay",
            heatSuitability = HeatSuitability.LIMITED,
            heatSuitabilityNote = "Can be placed in warm oven (up to 180°C) for gentle reheating; not for direct high gas flame.",
            usage = listOf("Table Serving", "Salads", "Slow Warmth Retention", "Soup Presentation"),
            safetyGuidelines = listOf(
                "Always use heat-resistant mats under the pot when placing on dining tables.",
                "Avoid dropping heavy metal spoons against the clay base."
            ),
            careAndMaintenance = listOf(
                "Wash with soft sponge and warm water.",
                "Let dry thoroughly upside down on a drying rack."
            ),
            seasoningInstructions = "Rub interior with rice oil and let sit for 12 hours before first serving.",
            origin = "Kerala Clay Craft Cooperative",
            presetKey = "handi"
        )
    )

    fun analyzePotImage(presetKey: String?, customFileName: String?): AnalysisResult {
        // If matches known preset or is analyzed, generate realistic structured output
        return when (presetKey) {
            "matka" -> AnalysisResult(
                id = UUID.randomUUID().toString(),
                potType = "Artisanal Water Matka (Surahi)",
                potTypeExplanation = "Spherical wide-body shape with rounded base and narrow collar designed for evaporative thermal cooling.",
                confidenceScore = 0.96f,
                estimatedCapacity = "5.0 L",
                capacityNote = "Estimated based on geometric proportions and standard pitcher ratios. Actual volume may vary by ±10%.",
                material = "Natural Alluvial Porous Clay",
                materialExplanation = "Unfinished, matte terracotta finish with visible natural micro-pores and capillary breathability.",
                heatSuitability = HeatSuitability.NOT_RECOMMENDED,
                heatSuitabilityExplanation = "Intended strictly for ambient liquid cooling. Direct flame heating will destroy capillary cooling channels and cause thermal cracking.",
                recommendedUsage = listOf("Water Storage", "Natural Cooling", "Alkaline Water", "Ambient Dispenser"),
                safetyGuidelines = listOf(
                    "Store on elevated ring base with drainage dish to catch natural capillary condensation.",
                    "Do not use for boiling or cooking on direct stovetop.",
                    "Flush thoroughly with fresh warm water every 10–14 days.",
                    "Never freeze or expose to sub-zero temperatures with liquid inside."
                ),
                careAndMaintenance = listOf(
                    "Cleaning: Rinse with lukewarm water and a teaspoon of baking soda; do not use chemical soaps.",
                    "Drying: Air dry in breezy shade periodically.",
                    "Storage: When not in use, ensure pot is bone-dry to prevent mildew in pores."
                ),
                smartRecommendations = listOf(
                    "Pairs best with an earthen lid and brass ladle.",
                    "Keep in an airy corridor or open kitchen corner for optimal evaporative cooling rate.",
                    "Consider adding a handful of vetiver roots inside for traditional summer cooling aroma."
                ),
                presetKey = "matka",
                shapeDescription = "Symmetric spherical bulb with flared pouring collar and circular base."
            )
            "tawa" -> AnalysisResult(
                id = UUID.randomUUID().toString(),
                potType = "Terracotta Cooking Tawa (Griddle)",
                potTypeExplanation = "Flat convex circular clay disc engineered for even heat distribution across flatbreads.",
                confidenceScore = 0.94f,
                estimatedCapacity = "0.8 L",
                capacityNote = "Estimated shallow volume. Intended for contact surface cooking rather than liquid containment.",
                material = "High-Density Reinforced Stoneware Clay",
                materialExplanation = "Smooth burnished cooking face treated with fine iron-rich slip for reduced stickiness and heat retention.",
                heatSuitability = HeatSuitability.SUITABLE,
                heatSuitabilityExplanation = "Designed for gas flame or charcoal cooking when used with gentle preheating and moderate flame settings.",
                recommendedUsage = listOf("Flatbreads", "Rotis & Naan", "Pancakes", "Tortillas", "Spice Toasting"),
                safetyGuidelines = listOf(
                    "Preheat on low flame for 3–5 minutes before raising heat to avoid thermal shock.",
                    "Never shock hot tawa with cold water; allow to cool naturally to room temperature before washing.",
                    "Do not place directly onto extreme high-BTU open campfire flames without a diffuser plate."
                ),
                careAndMaintenance = listOf(
                    "Cleaning: Gently scrape crumbs with wooden spatula and wipe with damp linen cloth.",
                    "Oiling: Lightly buff with coconut or sesame oil after every 3 uses to maintain natural seasoning.",
                    "Storage: Store horizontally in padded shelf or hang using dedicated wall strap."
                ),
                smartRecommendations = listOf(
                    "Always season with oil and garlic prior to initial flatbread session.",
                    "Use a wire heat diffuser rack if your stove has concentrated single-point flame rings."
                ),
                presetKey = "tawa",
                shapeDescription = "Convex circular disk with shallow bevel edge."
            )
            "urn" -> AnalysisResult(
                id = UUID.randomUUID().toString(),
                potType = "Glazed Ornamental Terracotta Urn",
                potTypeExplanation = "High-profile decorative vessel with vitrified gloss coating, flared rim, and ornamental shoulder fluting.",
                confidenceScore = 0.92f,
                estimatedCapacity = "3.2 L",
                capacityNote = "Estimated volumetric internal displacement. Suitable for dry goods or decorative display.",
                material = "Vitrified Glazed Earthenware",
                materialExplanation = "Terracotta core kiln-fired with mineral glaze finish, creating an impermeable glass-like outer barrier.",
                heatSuitability = HeatSuitability.NOT_RECOMMENDED,
                heatSuitabilityExplanation = "Glazed coatings cannot withstand uneven thermal expansion on direct stove flames; heating may cause glaze crazing, chemical release, or explosive cracking.",
                recommendedUsage = listOf("Home Decoration", "Dry Spice Storage", "Indoor Planter", "Decorative Centerpiece"),
                safetyGuidelines = listOf(
                    "Do not expose to open stovetop or broiler flames.",
                    "Verify non-toxic certified glaze before storing pickled or acidic liquids for extended periods.",
                    "Handle with dry hands; glazed surfaces become slick when wet."
                ),
                careAndMaintenance = listOf(
                    "Cleaning: Wash with mild organic dish detergent and warm water.",
                    "Drying: Towel dry with microfiber cloth to prevent water spots on gloss glaze.",
                    "Maintenance: Avoid abrasive scourers or wire wool."
                ),
                smartRecommendations = listOf(
                    "Excellent as an ambient kitchen countertop vessel for storing dry beans or rice.",
                    "Display in natural indirect sunlight to highlight rich glaze undertones."
                ),
                presetKey = "urn",
                shapeDescription = "Elongated classical amphora silhouette with glazed finish."
            )
            else -> AnalysisResult(
                id = UUID.randomUUID().toString(),
                potType = "Traditional Clay Cooking Handi",
                potTypeExplanation = "Deep spherical earthen vessel featuring curved sides, thick walls, and a recessed rim designed for retaining steam moisture.",
                confidenceScore = 0.95f,
                estimatedCapacity = "2.5 L",
                capacityNote = "Estimated based on pot body proportions and neck flare. Actual capacity may vary by ±0.3 L.",
                material = "Natural Unglazed Terracotta",
                materialExplanation = "Artisanal porous clay body with natural earthy red-brown coloration and fine sand temper for heat resistance.",
                heatSuitability = HeatSuitability.SUITABLE,
                heatSuitabilityExplanation = "Suitable for low to medium direct flame and slow simmer cooking. Gradual heat distribution preserves food nutrients.",
                recommendedUsage = listOf("Slow Cooking", "Biryani & Rice", "Lentil Curries", "Stews", "Clay Pot Baking"),
                safetyGuidelines = listOf(
                    "Always begin on the lowest heat setting for 5 minutes before increasing flame.",
                    "Ensure bottom of pot has adequate liquid or sauce before applying heat to prevent dry scorching.",
                    "Do not transfer directly from hot flame onto cold granite countertops; always place onto a cork or wooden trivet.",
                    "Discard or repurpose if bottom surface shows deep spider-web fractures."
                ),
                careAndMaintenance = listOf(
                    "Initial Seasoning: Soak in fresh water for 24 hours, dry in sun, rub with oil, and simmer rice water once.",
                    "Washing: Use hot water and soft natural bristle brush. Avoid synthetic dishwashing soap as clay absorbs chemicals.",
                    "Thorough Drying: Dry completely in sunlight or airy spot before storing to prevent mold.",
                    "Storage: Store without lid on top to maintain natural airflow."
                ),
                smartRecommendations = listOf(
                    "Ideal for cooking traditional slow-cooked curries and biryani where moisture recycling is essential.",
                    "Add an unglazed clay lid sealed with wheat dough (dum) for authentic aroma retention.",
                    "Keep on medium-low flame for the best flavor infusion."
                ),
                presetKey = "handi",
                shapeDescription = "Wide spherical base tapering into a defined neck and lip collar."
            )
        }
    }

    fun getRecommendations(criteria: RecommendationCriteria): List<PotRecommendation> {
        return potsCatalog.map { pot ->
            var score = 70
            var reason = "Matches general clay pottery criteria."

            // Usage matching
            if (criteria.usage.isNotBlank()) {
                val matchesUsage = pot.usage.any { it.contains(criteria.usage, ignoreCase = true) } ||
                        pot.category.displayName.contains(criteria.usage, ignoreCase = true)
                if (matchesUsage) {
                    score += 15
                    reason = "Perfect match for your intended ${criteria.usage} usage."
                }
            }

            // Heat matching
            if (criteria.heatNeeded.isNotBlank()) {
                when {
                    criteria.heatNeeded.startsWith("Yes", ignoreCase = true) -> {
                        if (pot.heatSuitability == HeatSuitability.SUITABLE) {
                            score += 15
                            reason += " Fully rated for direct heat cooking."
                        } else {
                            score -= 25
                        }
                    }
                    criteria.heatNeeded.startsWith("No", ignoreCase = true) -> {
                        if (pot.heatSuitability != HeatSuitability.SUITABLE) {
                            score += 10
                            reason += " Safe and specialized for non-heating use."
                        }
                    }
                }
            }

            // Capacity matching
            if (criteria.capacity.isNotBlank()) {
                if (pot.capacity.contains(criteria.capacity.replace(" ", ""), ignoreCase = true) ||
                    (criteria.capacity.contains("2-3", ignoreCase = true) && pot.capacity.contains("2.5")) ||
                    (criteria.capacity.contains("5", ignoreCase = true) && pot.capacity.contains("5.0")) ||
                    (criteria.capacity.contains("Under 1", ignoreCase = true) && pot.capacity.contains("0."))
                ) {
                    score += 10
                    reason += " Matches your preferred ${pot.capacity} capacity."
                }
            }

            PotRecommendation(
                pot = pot,
                matchScore = score.coerceIn(55, 99),
                matchReason = reason.trim()
            )
        }.sortedByDescending { it.matchScore }.take(4)
    }
}
