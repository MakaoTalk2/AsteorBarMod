package com.afoxxvi.asteorbar.overlay.parts;

import com.afoxxvi.asteorbar.AsteorBar;
import com.afoxxvi.asteorbar.overlay.RenderGui;
import com.afoxxvi.asteorbar.utils.GuiHelper;
import com.afoxxvi.asteorbar.utils.Utils;
import com.mojang.blaze3d.systems.RenderSystem;
import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.power.HudRendered;
import io.github.apace100.apoli.util.HudRender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class OriginsOverlay extends BaseOverlay {
    private SimpleBarOverlay subOverlay = null;
    private static final int BAR_WIDTH = 71;
    private static final int BAR_HEIGHT = 8;
    private static final int ICON_SIZE = 8;

    private static final int BAR_INDEX_OFFSET = BAR_HEIGHT + 2;
    private static final int ICON_INDEX_OFFSET = ICON_SIZE + 1;

    @Override
    public void renderOverlay(RenderGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {
        if (!(AsteorBar.compatibility.apoli && AsteorBar.config.hookApoli())) {
            return;
        }
        final var player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }
        List<HudRendered> hudPowers = PowerHolderComponent.KEY.get(player).getPowers().stream().filter(p -> p instanceof HudRendered).map(p -> (HudRendered) p).sorted(
                Comparator.comparing(hudRenderedA -> hudRenderedA.getRenderSettings().getSpriteLocation())
        ).toList();
        for (HudRendered hudRendered : hudPowers) {
            HudRender hudRender = hudRendered.getRenderSettings();
            if (hudRender.shouldRender(player) && hudRendered.shouldRender()) {
                if (subOverlay == null) {
                    subOverlay = new OriginSimpleBar();
                }
                final var originSimpleBar = (OriginSimpleBar) subOverlay;
                originSimpleBar.hudRender = hudRender;
                originSimpleBar.hudRendered = hudRendered;
                subOverlay.render(gui, guiGraphics, partialTick, screenWidth, screenHeight);
            }
        }
    }

    private static class OriginSimpleBar extends SimpleBarOverlay {
        protected HudRender hudRender;
        protected HudRendered hudRendered;

        private static int getFillColor(int index) {
            switch (index) {
                case 0 -> {
                    return 0xffc78c79;
                }
                case 1 -> {
                    return 0xff385fff;
                }
                case 2 -> {
                    return 0xfffaff35;
                }
                case 3 -> {
                    return 0xffff33ea;
                }
                case 4, 5 -> {
                    return 0xffffffff;
                }
                case 6 -> {
                    return 0xff608d6f;
                }
                case 7 -> {
                    return 0xffffc650;
                }
                case 8 -> {
                    return 0xff339127;
                }
                default -> {
                    return 0xffe0e0e0;
                }
            }
        }

        @Override
        protected Parameters getParameters(Player player) {
            if (hudRendered == null || hudRender == null || hudRendered.getFill() == 0.0) {
                return null;
            }
            Parameters parameters = new Parameters();
            parameters.value = hudRendered.getFill();
            parameters.capacity = 1.0F;
            parameters.fillColor = getFillColor(hudRender.getBarIndex());
            parameters.boundColor = Utils.mixColor(0xff000000, parameters.fillColor, 0.5F);
            parameters.emptyColor = 0xff202c25;
            return parameters;
        }

        @Override
        protected void drawDecorations(GuiGraphics guiGraphics, int left, int top, int right, int bottom, Parameters parameters, boolean flip) {
            int barV = BAR_HEIGHT + hudRender.getBarIndex() * BAR_INDEX_OFFSET;
            RenderSystem.setShaderTexture(0, hudRender.getSpriteLocation());
            GuiHelper.drawTexturedRect(guiGraphics, (right + left) / 2 - ICON_SIZE / 2, (top + bottom) / 2 - ICON_SIZE / 2, 73, barV, ICON_SIZE, ICON_SIZE);
            RenderSystem.setShaderTexture(0, LIGHTMAP_TEXTURE);
        }

        @Override
        protected boolean shouldRender(Player player) {
            return true;
        }
    }
}
