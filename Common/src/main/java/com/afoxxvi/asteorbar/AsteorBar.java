package com.afoxxvi.asteorbar;


import com.afoxxvi.asteorbar.config.ConfigAdapter;
import com.afoxxvi.asteorbar.config.DefaultConfigAdapter;
import com.afoxxvi.asteorbar.utils.PlatformAdapter;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class AsteorBar {
    public static final String MOD_ID = "asteorbar";
    public static final String MOD_NAME = "AsteorBar";
    public static ConfigAdapter config = new DefaultConfigAdapter();
    public static Compatibility compatibility = new Compatibility();

    public static PlatformAdapter platformAdapter = new PlatformAdapter() {
        @Override
        public boolean isBoss(LivingEntity livingEntity) {
            return false;
        }

        @Override
        public boolean isEyeInFluid(Player player) {
            return false;
        }

        @Override
        public RenderType getRenderType() {
            return RenderType.translucent();
        }

        @Override
        public boolean isModLoaded(String modId) {
            return false;
        }

        @Override
        public AppleSkinFoodValues getAppleSkinFoodValues(Player player) {
            return new AppleSkinFoodValues(0, 0, 0);
        }
    };

    public static class Compatibility {
        private boolean initialized = false;
        public boolean toughAsNails = false;
        public boolean thirst = false;
        public boolean mekanism = false;
        public boolean dehydration = false;
        public boolean parcool = false;
        public boolean ironsSpellbooks = false;
        public boolean feathers = false;
        public boolean appleskin = false;
        public boolean superiorshields = false;
        public boolean vampirism = false;
        public boolean lightshield = false;

        public void init() {
            if (initialized) {
                return;
            }
            toughAsNails = platformAdapter.isModLoaded("toughasnails");
            thirst = platformAdapter.isModLoaded("thirst");
            mekanism = platformAdapter.isModLoaded("mekanism");
            dehydration = platformAdapter.isModLoaded("dehydration");
            parcool = platformAdapter.isModLoaded("parcool");
            ironsSpellbooks = platformAdapter.isModLoaded("irons_spellbooks");
            feathers = platformAdapter.isModLoaded("feathers");
            appleskin = platformAdapter.isModLoaded("appleskin");
            superiorshields = platformAdapter.isModLoaded("superiorshields");
            vampirism = platformAdapter.isModLoaded("vampirism");
            lightshield = platformAdapter.isModLoaded("lightshield");
            initialized = true;
        }
    }
}
