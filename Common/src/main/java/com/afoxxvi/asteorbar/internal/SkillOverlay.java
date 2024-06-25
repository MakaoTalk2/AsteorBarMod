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
    @Override
    public void renderOverlay(RenderGui gui, PoseStack poseStack, float partialTick, int screenWidth, int screenHeight) {
        if (!InternalInfo.activated) {
            return;
        }
        gui.leftHeight(-7);
        gui.rightHeight(-7);
        RenderSystem.enableBlend();
        RenderSystem.setShaderTexture(0, new ResourceLocation("minecraft", "textures/gui/widgets.png"));
        int middle = screenWidth / 2;
        for (int i = 0; i < 4; i++) {
            int x = middle - 91 - 29 - 21 * 4 + i * 21;
            int y = screenHeight - 23;
            GuiHelper.drawTexturedRect(poseStack, x, y, 24, 22, 29, 24);
        }
        for (int i = 0; i < 8; i++) {
            if ((InternalInfo.skillShow & (1 << (i + 4))) == 0) continue;
            int x = middle + 91 + i * 21;
            int y = screenHeight - 23;
            GuiHelper.drawTexturedRect(poseStack, x, y, 53, 22, 29, 24);
        }
        //RenderSystem.setShaderTexture(0, SKILL_TEXTURE);
        //GuiHelper.drawTexturedRect(poseStack, left, top, 9, 0, 182, 12);
        for (int i = 0; i < 16; i++) {
            if ((InternalInfo.skillShow & (1 << i)) == 0) continue;
            int x = i < 4 ? (middle - 91 - 26 - 21 * 4 + i * 21) : (middle + 91 + 10 + (i - 4) * 21);
            int y = screenHeight - 19;
            String texture = InternalInfo.skillName[i];
            if (!texture.isEmpty()) {
                var rl = new ResourceLocation(ResourceLocation.DEFAULT_NAMESPACE, "textures/item/gear/" + texture + ".png");
                RenderSystem.setShaderTexture(0, rl);
                GuiHelper.drawTexturedRect(poseStack, x, y, x + 16, y + 16, 0, 0, 32, 32, 32, 32);
            }
            if (Minecraft.getInstance().options.renderDebug) {
                Overlays.addStringRender(x + 8, y + 6, 0xFFFFFF, texture, Overlays.ALIGN_CENTER, true);
            }
            if (AsteorBar.tick - InternalInfo.skillGlint[i] < 20) {
                int color = Utils.modifyAlpha(InternalInfo.skillGlintColor[i], (int) (0x7F * (1.0 - (AsteorBar.tick - InternalInfo.skillGlint[i]) / 20.0)));
                GuiHelper.drawSolidColor(poseStack, x, y, x + 16, y + 16, color);
            }
            if ((InternalInfo.toggle & (1 << i)) != 0) {
                int color = Utils.modifyAlpha(0x4caf50, (int) (0x7F * (1.0 - 0.8 * Math.abs(20 - AsteorBar.tick % 40) / 20.0)));
                GuiHelper.drawSolidColor(poseStack, x, y, x + 16, y + 16, color);
            }
            int cooldown = InternalInfo.skillCooldown[i];
            if (cooldown < Byte.MAX_VALUE) {
                var len = cooldown * 16 / Byte.MAX_VALUE;
                GuiHelper.drawSolidColor(poseStack, x + 8, y, x + 16, y + 16, 0x80D0D0D0);
                GuiHelper.drawSolidColor(poseStack, x + 8, y + len, x + 16, y + 16, 0x80D0D0D0);
                //GuiHelper.drawSolidColor(poseStack, x, y + 9, x + 24, y + 10, 0xFF000000);
                //GuiHelper.drawSolidColor(poseStack, x, y + 9, x + len, y + 10, 0xFFD0D0D0);
            }
            int mana = InternalInfo.skillMana[i];
            if (mana < Byte.MAX_VALUE) {
                var len = mana * 16 / Byte.MAX_VALUE;
                GuiHelper.drawSolidColor(poseStack, x, y, x + 8, y + 16, 0x8000FFFF);
                GuiHelper.drawSolidColor(poseStack, x, y + len, x + 8, y + 16, 0x8000FFFF);
                //GuiHelper.drawSolidColor(poseStack, x, y, x + 24, y + 1, 0xFF000000);
                //GuiHelper.drawSolidColor(poseStack, x, y, x + len, y + 1, 0xFF00FFFF);
            }
        }
        RenderSystem.disableBlend();
    }
}
