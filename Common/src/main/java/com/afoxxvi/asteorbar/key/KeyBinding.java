package com.afoxxvi.asteorbar.key;

import com.afoxxvi.asteorbar.AsteorBar;
import com.afoxxvi.asteorbar.internal.InternalInfo;
import com.afoxxvi.asteorbar.overlay.Overlays;
import com.afoxxvi.asteorbar.tooltip.Tooltips;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class KeyBinding {
    public static final KeyMapping TOGGLE_OVERLAY = new KeyMapping("asteorbar.key.toggle_overlay", GLFW.GLFW_KEY_F8, "asteorbar.key.category");
    public static final KeyMapping TOGGLE_MOB_BAR = new KeyMapping("asteorbar.key.toggle_mob_bar", GLFW.GLFW_KEY_F10, "asteorbar.key.category");

    public static final KeyMapping CAST_SKILL_1 = new KeyMapping("asteorbar.key.cast_skill_1", GLFW.GLFW_KEY_1, "asteorbar.key.category");
    public static final KeyMapping CAST_SKILL_2 = new KeyMapping("asteorbar.key.cast_skill_2", GLFW.GLFW_KEY_2, "asteorbar.key.category");
    public static final KeyMapping CAST_SKILL_3 = new KeyMapping("asteorbar.key.cast_skill_3", GLFW.GLFW_KEY_3, "asteorbar.key.category");
    public static final KeyMapping CAST_SKILL_4 = new KeyMapping("asteorbar.key.cast_skill_4", GLFW.GLFW_KEY_4, "asteorbar.key.category");
    public static final KeyMapping CAST_ACTIVE_SKILL_WHEEL = new KeyMapping("asteorbar.key.cast_active_skill_wheel", GLFW.GLFW_KEY_R, "asteorbar.key.category");
    public static final KeyMapping CAST_QUICK_SKILL_WHEEL = new KeyMapping("asteorbar.key.cast_quick_skill_wheel", GLFW.GLFW_KEY_T, "asteorbar.key.category");

    public static final KeyMapping RUSH_FORWARD = new KeyMapping("asteorbar.key.rush_forward", GLFW.GLFW_KEY_W, "asteorbar.key.category");
    public static final KeyMapping RUSH_BACKWARD = new KeyMapping("asteorbar.key.rush_backward", GLFW.GLFW_KEY_S, "asteorbar.key.category");
    public static final KeyMapping RUSH_LEFT = new KeyMapping("asteorbar.key.rush_left", GLFW.GLFW_KEY_A, "asteorbar.key.category");
    public static final KeyMapping RUSH_RIGHT = new KeyMapping("asteorbar.key.rush_right", GLFW.GLFW_KEY_D, "asteorbar.key.category");
    public static final KeyMapping RUSH_INSTANT = new KeyMapping("asteorbar.key.rush_instant", GLFW.GLFW_KEY_LEFT_ALT, "asteorbar.key.category");

    public static final KeyMapping SCROLL_TOOLTIP_UP = new KeyMapping("asteorbar.key.scroll_tooltip_up", GLFW.GLFW_KEY_PAGE_UP, "asteorbar.key.category");
    public static final KeyMapping SCROLL_TOOLTIP_DOWN = new KeyMapping("asteorbar.key.scroll_tooltip_down", GLFW.GLFW_KEY_PAGE_DOWN, "asteorbar.key.category");
    public static final KeyMapping SCROLL_TOOLTIP_LEFT = new KeyMapping("asteorbar.key.scroll_tooltip_left", GLFW.GLFW_KEY_HOME, "asteorbar.key.category");
    public static final KeyMapping SCROLL_TOOLTIP_RIGHT = new KeyMapping("asteorbar.key.scroll_tooltip_right", GLFW.GLFW_KEY_END, "asteorbar.key.category");
    public static final KeyMapping SCROLL_TOOLTIP_RESET = new KeyMapping("asteorbar.key.scroll_tooltip_reset", GLFW.GLFW_KEY_ENTER, "asteorbar.key.category");

    private static final List<KeyState> KEY_STATES = new ArrayList<>();
    private static final KeyState ACTIVE_SKILL_WHEEL_STATE = register(CAST_ACTIVE_SKILL_WHEEL);
    private static final KeyState QUICK_SKILL_WHEEL_STATE = register(CAST_QUICK_SKILL_WHEEL);
    private static final KeyState RUSH_FORWARD_STATE = register(RUSH_FORWARD);
    private static final KeyState RUSH_BACKWARD_STATE = register(RUSH_BACKWARD);
    private static final KeyState RUSH_LEFT_STATE = register(RUSH_LEFT);
    private static final KeyState RUSH_RIGHT_STATE = register(RUSH_RIGHT);

    private static void update() {
        for (KeyState k : KEY_STATES) {
            k.update();
        }
    }

    private static KeyState register(KeyMapping key) {
        var k = new KeyState(key);
        KEY_STATES.add(k);
        return k;
    }

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
        /*
        while (KeyBinding.RUSH_FORWARD.consumeClick()) {
            if (AsteorBar.rush) continue;
            AsteorBar.platformAdapter.sendRushPacket(0);
            AsteorBar.rush = true;
        }
        while (KeyBinding.RUSH_BACKWARD.consumeClick()) {
            if (AsteorBar.rush) continue;
            AsteorBar.platformAdapter.sendRushPacket(1);
            AsteorBar.rush = true;
        }
        while (KeyBinding.RUSH_LEFT.consumeClick()) {
            if (AsteorBar.rush) continue;
            AsteorBar.platformAdapter.sendRushPacket(2);
            AsteorBar.rush = true;
        }
        while (KeyBinding.RUSH_RIGHT.consumeClick()) {
            if (AsteorBar.rush) continue;
            AsteorBar.platformAdapter.sendRushPacket(3);
            AsteorBar.rush = true;
        }*/
        update();
        int direction = -2;
        while (KeyBinding.RUSH_INSTANT.consumeClick()) {
            direction = -1;
        }
        if (direction == -1) {
            if (RUSH_LEFT.isDown()) {
                direction = 2;
            } else if (RUSH_RIGHT.isDown()) {
                direction = 3;
            } else if (RUSH_FORWARD.isDown()) {
                direction = 0;
            } else if (RUSH_BACKWARD.isDown()) {
                direction = 1;
            }
            if (direction != -1) {
                if (AsteorBar.rush) return;
                AsteorBar.platformAdapter.sendRushPacket(direction);
                AsteorBar.rush = true;
            }
        }
    }
}
