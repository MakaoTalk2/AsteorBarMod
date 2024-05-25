package com.afoxxvi.asteorbar.config;

public class ConfigComment {
    public static final String enableOverlay = "Whether to enable the overlay. If disabled, all other overlay options will be ignored.";
    public static final String overlayLayoutStyle = "The layout style of the overlay. 0: none, 1: above hot bar long, 2: above hot bar short, 3: top left, 4: top right, 5: bottom left, 6: bottom right";
    public static final String overlayTextScale = "The text scale of the overlay";
    public static final String fullFoodLevelValue = "Full food level value. If you are using a mod that changes the max food level, you may need to change this value.";
    public static final String fullSaturationValue = "Full saturation value. If you are using a mod that changes the max saturation value, you may need to change this value.";
    public static final String fullExhaustionValue = "Full exhaustion value. If you are using a mod that changes the max exhaustion value, you may need to change this value.";
    public static final String fullArmorValue = "Full armor value. If you are using a mod that changes the max armor value, you may need to change this value.";
    public static final String fullArmorToughnessValue = "Full armor toughness value. If you are using a mod that changes the max armor toughness value, you may need to change this value.";
    public static final String fullHealthValue = "Full health value. Determines the amount single health bar represents. No effect while stack health bar is disabled.";
    public static final String enableStackHealthBar = "Whether to enable stack health bar. If enabled, the health bar will be displayed like multiple health bars with different colors stacked together. Note that once enabled, the health bar color in specific conditions will be rendered half transparently on the health bar, and if the absorption display mode is 0, it will be changed to 2  in game dynamically to avoid ambiguity.";
    public static final String stackHealthBarColors = "The color list of the stack health bar. Split by commas, each color must be in ARGB format, no space, no illegal characters, no tail comma. For example: '#FF00FF00,#FFFF0000,#FF0000FF', the health bars will pick colors from the list sequentially, and return to the first color when the list is exhausted.";
    public static final String hideUnchangingBarAfterSeconds = "If a bar value is not changing in some seconds, then hide them. 0 to disable.";
    public static final String healthColorNormal = "The color of the health bar. 0x00000000 to 0xFFFFFFFF. ARGB format.";
    public static final String healthColorPoison = "The color of the poison health bar. 0x00000000 to 0xFFFFFFFF. ARGB format.";
    public static final String healthColorWither = "The color of the wither health bar. 0x00000000 to 0xFFFFFFFF. ARGB format.";
    public static final String healthColorFrozen = "The color of the frozen health bar. 0x00000000 to 0xFFFFFFFF. ARGB format.";
    public static final String healthBoundColor = "The color of the health bar bound. 0x00000000 to 0xFFFFFFFF. ARGB format.";
    public static final String healthBoundColorBlink = "The color of the health bar bound when blinking. 0x00000000 to 0xFFFFFFFF. ARGB format.";
    public static final String healthBoundColorLow = "The color of the low health bar bound. 0x00000000 to 0xFFFFFFFF. ARGB format.";
    public static final String healthEmptyColor = "The color of the empty part of the health bar. 0x00000000 to 0xFFFFFFFF. ARGB format.";
    public static final String absorptionColor = "The color of the absorption bar. 0x00000000 to 0xFFFFFFFF. ARGB format.";
    public static final String absorptionBoundColor = "The color of the absorption bar bound. 0x00000000 to 0xFFFFFFFF. ARGB format.";
    public static final String foodColorNormal = "The color of the normal food level bar. 0x00000000 to 0xFFFFFFFF. ARGB format.";
    public static final String foodColorHunger = "The color of the hunger food level bar. 0x00000000 to 0xFFFFFFFF. ARGB format.";
    public static final String foodBoundColor = "The color of the food level bar bound. 0x00000000 to 0xFFFFFFFF. ARGB format.";
    public static final String foodBoundColorBlink = "The color of the food level bar bound when blinking. 0x00000000 to 0xFFFFFFFF. ARGB format.";
    public static final String foodEmptyColor = "The color of the empty part of the food level bar. 0x00000000 to 0xFFFFFFFF. ARGB format.";
    public static final String saturationColor = "The color of the saturation bar. 0x00000000 to 0xFFFFFFFF. ARGB format.";
    public static final String experienceColor = "The color of the experience bar. 0x00000000 to 0xFFFFFFFF. ARGB format.";
    public static final String experienceBoundColor = "The color of the experience bar bound. 0x00000000 to 0xFFFFFFFF. ARGB format.";
    public static final String experienceEmptyColor = "The color of the empty part of the experience bar. 0x00000000 to 0xFFFFFFFF. ARGB format.";
    public static final String airColor = "The color of the air bar. 0x00000000 to 0xFFFFFFFF. ARGB format.";
    public static final String airBoundColor = "The color of the air bar bound. 0x00000000 to 0xFFFFFFFF. ARGB format.";
    public static final String mountHealthColor = "The color of the mount health bar. 0x00000000 to 0xFFFFFFFF. ARGB format.";
    public static final String mountHealthColor2 = "The color of the second mount health bar. 0x00000000 to 0xFFFFFFFF. ARGB format.";
    public static final String mountHealthBoundColor = "The color of the mount health bar bound. 0x00000000 to 0xFFFFFFFF. ARGB format.";
    public static final String mountHealthBoundColor2 = "The color of the second mount health bar bound. 0x00000000 to 0xFFFFFFFF. ARGB format.";
    public static final String mountHealthEmptyColor = "The color of the empty part of the mount health bar. 0x00000000 to 0xFFFFFFFF. ARGB format.";
    public static final String mountHealthOnLeftSide = "Whether to display the mount health bar on the left side. Or it will be displayed on the right side.";
    public static final String armorColor = "The color of the armor bar. 0x00000000 to 0xFFFFFFFF. ARGB format.";
    public static final String armorBoundColor = "The color of the armor bar bound. 0x00000000 to 0xFFFFFFFF. ARGB format.";
    public static final String armorEmptyColor = "The color of the empty part of the armor bar. 0x00000000 to 0xFFFFFFFF. ARGB format.";
    public static final String armorToughnessColor = "The color of the armor toughness bar. 0x00000000 to 0xFFFFFFFF. ARGB format.";
    public static final String enableHealthBlink = "Whether to enable health bar blink. This feature is designed to simulate the vanilla health icon blink.";
    public static final String lowHealthRate = "The health bar will start to flash when health rate is lower than this value. From 0.0-1.0. 0.0 means never flash.";
    public static final String shakeHealthAndFoodWhileLow = "Whether to shake health and food bar while low value.";
    public static final String overwriteVanillaArmorBar = "Whether to overwrite vanilla armor bar. If you don't like the mod's armor bar, you can disable this option.";
    public static final String overwriteVanillaExperienceBar = "Whether to overwrite vanilla experience bar. If you don't like the mod's experience bar, you can disable this option, progress label won't be affected.";
    public static final String displayExperienceProgress = "Whether to display experience progress on the side of the experience bar.";
    public static final String displayExperienceLevel = "Whether to display experience level on the experience bar.";
    public static final String displayHealthText = "Whether to display health text.";
    public static final String displayAbsorptionMethod = "0: Absorption will be displayed together with health bar. 1: Absorption will be displayed half transparently on the health bar. 2: Absorption will be displayed as bounds. Note: Since the absorption value can be higher than the max health, an extra number will be displayed to indicate value of absorption/max health, you can turn it off by editing 'displayAbsorptionDivMaxHealth'.";
    public static final String displayAbsorptionDivMaxHealth = "Whether to display the value of (absorption / max health). To avoid ambiguity, turn it to true if you hide the health text and don't display absorption bar together with health bar, or you may not be able to get correct absorption value.";
    public static final String displayAbsorptionTextMethod = "0: Absorption text will be displayed together with health text. for example: 15(+10)/20. 1: Absorption text will be displayed separately. for example: 10 15/20. Note: if 'displayHealthText' is false, absorption text will be disabled.";
    public static final String enableFoodBlink = "Whether to enable food level bar blink. This feature is designed to simulate the vanilla food icon shake.";
    public static final String displaySaturation = "Whether to display saturation bar.";
    public static final String displayExhaustion = "Whether to display exhaustion bar.";
    public static final String displayFoodText = "Whether to display food text.";
    public static final String displayArmorToughness = "Whether to display armor toughness bar.";
    public static final String cornerBarLength = "The length of the bars if using corner layout. Affected bars: health, food, experience.";
    public static final String cornerHorizontalPadding = "The horizontal padding of the bars if using corner layout.";
    public static final String cornerVerticalPadding = "The vertical padding of the bars if using corner layout.";
    public static final String forceRenderAtCorner = "Force every bar to be rendered at the corner. Only works in corner layout.";
    public static final String enableHealthBar = "Whether to enable health bar for entity. If disabled, all other health bar options will be ignored.";
    public static final String maxDistance = "The maximum distance to display mob health bar.";
    public static final String showOnSelf = "Whether to display health bar on self.";
    public static final String showOnPlayers = "Whether to display health bar on players.";
    public static final String showOnBosses = "Whether to display health bar on bosses.";
    public static final String showOnArmorStands = "Whether to display health bar on armor stands.";
    public static final String showOnFullHealthWithoutAbsorption = "Whether to display health bar on mobs with full health if the mob's absorption value is 0.";
    public static final String showOnFullHealthWithAbsorption = "Whether to display health bar on mobs with full health if the mob's absorption value is not 0.";
    public static final String healthBarAlpha = "The alpha of the health bar. 0 to 255. The alpha part of the color will be replaced by this value unless this value is 0.";
    public static final String healthBarHalfWidth = "The half width of the health bar.";
    public static final String healthBarHalfHeight = "The half height of the health bar.";
    public static final String healthBarOffsetY = "The offset of the health bar on the Y axis.";
    public static final String healthBarScale = "The scale of the health bar.";
    public static final String healthBarTextScale = "The scale of the health bar text.";
    public static final String healthBarTextOffsetY = "The offset of the health bar text on the Y axis.";
    public static final String healthBarBoundWidth = "The width of the health bar bound. 0 to 10. Hint: This value is a little hard to adjust. If you want to make the bounds looks thinner, you can increase the health bar width&height and decrease the health bar scale. You may also need to change the text scale and offset. This can be complicated, I highly recommend you to use some in-game config mod like 'configured'.";
    public static final String healthBarBoundVertex = "Whether to render the vertex of the health bar bound.";
    public static final String healthBarHealthColor = "The color of the health bar. 0x00000000 to 0xFFFFFFFF. ARGB format.";
    public static final String healthBarAbsorptionColor = "The color of the absorption bar. 0x00000000 to 0xFFFFFFFF. ARGB format.";
    public static final String healthBarBoundColor = "The color of the health bar bound. 0x00000000 to 0xFFFFFFFF. ARGB format.";
    public static final String healthBarEmptyColor = "The color of the empty part of the health bar. 0x00000000 to 0xFFFFFFFF. ARGB format.";
    public static final String healthBarHealthColorDynamic = "Whether to use dynamic color for health bar. The color will be picked between healthBarHealthColorFull and healthBarHealthColorEmpty based on the health rate. If disabled, the health bar will always be healthBarHealthColor";
    public static final String healthBarHealthColorFull = "The color of the health bar when the mob is full health. 0x00000000 to 0xFFFFFFFF. ARGB format.";
    public static final String healthBarHealthColorEmpty = "The color of the health bar when the mob is low health. 0x00000000 to 0xFFFFFFFF. ARGB format.";
    public static final String hookToughAsNails = "Whether to hook Tough As Nails. If enabled, the mod will display the thirst bar.";
    public static final String hookThirstWasTaken = "Whether to hook Thirst Was Taken. If enabled, the mod will display the thirst bar.";
    public static final String hookMekanism = "Whether to hook Mekanism. If enabled, the mod will display the energy bar.";
    public static final String hookDehydration = "Whether to hook Dehydration. If enabled, the mod will display the thirst bar.";
    public static final String hookParcool = "Whether to hook Parcool. If enabled, the mod will display the energy bar.";
    public static final String hookIronsSpellbooks = "Whether to hook Iron's Spellbooks. If enabled, the mod will display the mana bar.";
    public static final String hookFeathers = "Whether to hook Feathers. If enabled, the mod will display the energy bar.";
    public static final String hookAppleSkin = "Whether to hook AppleSkin. If enabled, the mod will display health and food preview.";
    public static final String hookSuperiorShields = "Whether to hook Superior Shields. If enabled, the mod will display the shield bar.";
    public static final String hookVampirism = "Whether to hook Vampirism. If enabled, the mod will display the blood bar.";
    public static final String hookBotania = "Whether to hook Botania. If enabled, the mod will display the mana bar.";
    public static final String hookOrigins = "Whether to hook Origins.";
    public static final String hookTFC = "Whether to hook TFC.";
    public static final String hookArsNouveau = "Whether to hook Ars Nouveau.";
}
