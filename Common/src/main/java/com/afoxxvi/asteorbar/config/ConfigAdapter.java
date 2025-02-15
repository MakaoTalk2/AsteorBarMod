package com.afoxxvi.asteorbar.config;

public interface ConfigAdapter {
    boolean enableOverlay();

    void enableOverlay(boolean enable);

    int overlayLayoutStyle();

    void overlayLayoutStyle(int style);

    double overlayTextScale();

    int fullFoodLevelValue();

    double fullSaturationValue();

    double fullExhaustionValue();

    int fullArmorValue();

    int fullArmorToughnessValue();

    int fullHealthValue();

    boolean enableStackHealthBar();

    String stackHealthBarColors();

    int hideUnchangingBarAfterSeconds();

    int healthColorNormal();

    int healthColorPoison();

    int healthColorWither();

    int healthColorFrozen();

    int healthBoundColor();

    int healthBoundColorBlink();

    int healthBoundColorLow();

    int healthEmptyColor();

    int absorptionColor();

    int absorptionBoundColor();

    int foodColorNormal();

    int foodColorHunger();

    int foodBoundColor();

    int foodBoundColorBlink();

    int foodEmptyColor();

    int saturationColor();

    int experienceColor();

    int experienceBoundColor();

    int experienceEmptyColor();

    int airColor();

    int airBoundColor();

    int mountHealthColor();

    int mountHealthColor2();

    int mountHealthBoundColor();

    int mountHealthBoundColor2();

    int mountHealthEmptyColor();

    boolean mountHealthOnLeftSide();

    int armorColor();

    int armorBoundColor();

    int armorEmptyColor();

    int armorToughnessColor();

    boolean enableHealthBlink();

    double lowHealthRate();

    boolean shakeHealthAndFoodWhileLow();

    boolean overwriteVanillaArmorBar();

    boolean overwriteVanillaExperienceBar();

    boolean displayExperienceProgress();

    boolean displayExperienceLevel();

    boolean displayHealthText();

    int displayAbsorptionMethod();

    boolean displayAbsorptionDivMaxHealth();

    int displayAbsorptionTextMethod();

    boolean enableFoodBlink();

    boolean displaySaturation();

    boolean displayExhaustion();

    boolean displayFoodText();

    boolean displayArmorToughness();

    int cornerBarLength();

    int cornerHorizontalPadding();

    int cornerVerticalPadding();

    boolean forceRenderAtCorner();

    //mob config
    boolean enableHealthBar();

    void enableHealthBar(boolean enable);

    double maxDistance();

    boolean showOnSelf();

    boolean showOnPlayers();

    boolean showOnBosses();

    boolean showOnArmorStands();

    boolean showOnFullHealthWithoutAbsorption();

    boolean showOnFullHealthWithAbsorption();

    int healthBarAlpha();

    int healthBarHalfWidth();

    int healthBarHalfHeight();

    double healthBarOffsetY();

    double healthBarScale();

    double healthBarTextScale();

    double healthBarTextOffsetY();

    int healthBarBoundWidth();

    boolean healthBarBoundVertex();

    int healthBarHealthColor();

    int healthBarAbsorptionColor();

    int healthBarBoundColor();

    int healthBarEmptyColor();

    boolean healthBarHealthColorDynamic();

    int healthBarHealthColorFull();

    int healthBarHealthColorEmpty();

    boolean hookToughAsNails();

    boolean hookThirstWasTaken();

    boolean hookMekanism();

    boolean hookDehydration();

    boolean hookParcool();

    boolean hookIronsSpellbooks();

    boolean hookFeathers();

    boolean hookAppleSkin();

    boolean hookSuperiorShields();

    boolean hookVampirism();

    boolean hookBotania();

    boolean hookOrigins();

    boolean hookTFC();

    boolean hookArsNouveau();

    boolean hookApoli();
}
