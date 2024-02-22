package com.afoxxvi.asteorbar.key;

import com.afoxxvi.asteorbar.AsteorBar;
import com.afoxxvi.asteorbar.internal.InternalInfo;
import com.afoxxvi.asteorbar.overlay.Overlays;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {
    public static final KeyMapping TOGGLE_OVERLAY = new KeyMapping("asteorbar.key.toggle_overlay", GLFW.GLFW_KEY_F8, "asteorbar.key.category");
    public static final KeyMapping TOGGLE_MOB_BAR = new KeyMapping("asteorbar.key.toggle_mob_bar", GLFW.GLFW_KEY_F10, "asteorbar.key.category");

    public static final KeyMapping CAST_SKILL_1 = new KeyMapping("asteorbar.key.cast_skill_1", GLFW.GLFW_KEY_1, "asteorbar.key.category");
    public static final KeyMapping CAST_SKILL_2 = new KeyMapping("asteorbar.key.cast_skill_2", GLFW.GLFW_KEY_2, "asteorbar.key.category");
    public static final KeyMapping CAST_SKILL_3 = new KeyMapping("asteorbar.key.cast_skill_3", GLFW.GLFW_KEY_3, "asteorbar.key.category");
    public static final KeyMapping CAST_SKILL_4 = new KeyMapping("asteorbar.key.cast_skill_4", GLFW.GLFW_KEY_4, "asteorbar.key.category");
    public static final KeyMapping CAST_SKILL_5 = new KeyMapping("asteorbar.key.cast_skill_5", GLFW.GLFW_KEY_5, "asteorbar.key.category");

    public static void handleKeyInput() {
        while (KeyBinding.TOGGLE_OVERLAY.consumeClick()) {
            if (InternalInfo.activated) {
                Overlays.style = (Overlays.style + 1) % Overlays.NUM_INTERNAL_STYLES;
                AsteorBar.config.enableOverlay(Overlays.style != 0);
                AsteorBar.config.internalOverlayStyle(Overlays.style);
                return;
            }
            Overlays.style = (Overlays.style + 1) % Overlays.NUM_STYLES;
            AsteorBar.config.enableOverlay(Overlays.style != 0);
            AsteorBar.config.overlayLayoutStyle(Overlays.style);
        }
        while (KeyBinding.TOGGLE_MOB_BAR.consumeClick()) {
            AsteorBar.config.enableHealthBar(!AsteorBar.config.enableHealthBar());
        }

        while (KeyBinding.CAST_SKILL_1.consumeClick()) {
            if (AsteorBar.castSkill) continue;
            AsteorBar.platformAdapter.sendUseSkillPacket(0);
            AsteorBar.castSkill = true;
        }
        while (KeyBinding.CAST_SKILL_2.consumeClick()) {
            if (AsteorBar.castSkill) continue;
            AsteorBar.platformAdapter.sendUseSkillPacket(1);
            AsteorBar.castSkill = true;
        }
        while (KeyBinding.CAST_SKILL_3.consumeClick()) {
            if (AsteorBar.castSkill) continue;
            AsteorBar.platformAdapter.sendUseSkillPacket(2);
            AsteorBar.castSkill = true;
        }
        while (KeyBinding.CAST_SKILL_4.consumeClick()) {
            if (AsteorBar.castSkill) continue;
            AsteorBar.platformAdapter.sendUseSkillPacket(3);
            AsteorBar.castSkill = true;
        }
        while (KeyBinding.CAST_SKILL_5.consumeClick()) {
            if (AsteorBar.castSkill) continue;
            AsteorBar.platformAdapter.sendUseSkillPacket(4);
            AsteorBar.castSkill = true;
        }
    }
}
