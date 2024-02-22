package com.afoxxvi.asteorbar.internal;

import com.afoxxvi.asteorbar.AsteorBar;
import com.afoxxvi.asteorbar.overlay.Overlays;
import com.afoxxvi.asteorbar.overlay.RenderGui;
import com.afoxxvi.asteorbar.overlay.parts.BaseOverlay;
import com.afoxxvi.asteorbar.utils.GuiHelper;
import com.afoxxvi.asteorbar.utils.Utils;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

public class SkillOverlay extends BaseOverlay {
    private static final ResourceLocation SKILL_TEXTURE = new ResourceLocation(AsteorBar.MOD_ID, "textures/gui/skill.png");

    @Override
    public void renderOverlay(RenderGui gui, PoseStack poseStack, float partialTick, int screenWidth, int screenHeight) {
        if (!InternalInfo.activated) {
            return;
        }
        int left = screenWidth / 2 - 91;
        int top = screenHeight - 35;
        gui.leftHeight(6);
        gui.rightHeight(6);
        RenderSystem.setShaderTexture(0, SKILL_TEXTURE);
        RenderSystem.enableBlend();
        GuiHelper.drawTexturedRect(poseStack, left, top, 9, 0, 182, 12);
        for (int i = 0; i < 7; i++) {
            if ((InternalInfo.skillShow & (1 << i)) == 0) continue;
            int x = left + 1 + i * 26;
            int y = top + 1;
            String texture = InternalInfo.skillName[i];
            if (!texture.isEmpty()) {
                var rl = new ResourceLocation(ResourceLocation.DEFAULT_NAMESPACE, "textures/item/" + texture + ".png");
                RenderSystem.setShaderTexture(0, rl);
                GuiHelper.drawTexturedRect(poseStack, x, y, x + 24, y + 10, 1, 12, 31, 24.5f, 32, 32);
            }
            if (Minecraft.getInstance().options.renderDebug) {
                Overlays.addStringRender(x + 12, y + 1, 0xFFFFFF, texture, Overlays.ALIGN_CENTER, true);
            }
            if (AsteorBar.tick - InternalInfo.skillGlint[i] < 20) {
                int color = Utils.modifyAlpha(InternalInfo.skillGlintColor[i], (int) (0x7F * (1.0 - (AsteorBar.tick - InternalInfo.skillGlint[i]) / 20.0)));
                GuiHelper.drawSolidColor(poseStack, x, y, x + 24, y + 10, color);
            }
            if ((InternalInfo.toggle & (1 << i)) != 0) {
                int color = Utils.modifyAlpha(0x4caf50, (int) (0x7F * (1.0 - 0.8 * Math.abs(20 - AsteorBar.tick % 40) / 20.0)));
                GuiHelper.drawSolidColor(poseStack, x, y, x + 24, y + 10, color);
            }
            int cooldown = InternalInfo.skillCooldown[i];
            if (cooldown < Byte.MAX_VALUE) {
                var len = cooldown * 24 / Byte.MAX_VALUE;
                GuiHelper.drawSolidColor(poseStack, x, y, x + 24, y + 10, 0x40D0D0D0);
                GuiHelper.drawSolidColor(poseStack, x + len, y, x + 24, y + 10, 0x40D0D0D0);
                //GuiHelper.drawSolidColor(poseStack, x, y + 9, x + 24, y + 10, 0xFF000000);
                //GuiHelper.drawSolidColor(poseStack, x, y + 9, x + len, y + 10, 0xFFD0D0D0);
            }
            int mana = InternalInfo.skillMana[i];
            if (mana < Byte.MAX_VALUE) {
                var len = mana * 24 / Byte.MAX_VALUE;
                GuiHelper.drawSolidColor(poseStack, x, y, x + 24, y + 10, 0x4000FFFF);
                GuiHelper.drawSolidColor(poseStack, x + len, y, x + 24, y + 10, 0x4000FFFF);
                //GuiHelper.drawSolidColor(poseStack, x, y, x + 24, y + 1, 0xFF000000);
                //GuiHelper.drawSolidColor(poseStack, x, y, x + len, y + 1, 0xFF00FFFF);
            }
        }
        RenderSystem.disableBlend();
    }
}
