package com.afoxxvi.asteorbar.overlay.parts;

import com.afoxxvi.asteorbar.AsteorBar;
import com.google.common.collect.Iterables;
import net.minecraft.Util;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.lib.ModTags;
import vazkii.botania.xplat.IXplatAbstractions;

import java.util.List;

public class BotaniaOverlay extends SimpleBarOverlay {
    @Override
    protected Parameters getParameters(Player player) {
        if (player.isSpectator()) {
            return null;
        }
        int totalMana = 0;
        int totalMaxMana = 0;
        boolean anyRequest = false;

        Container mainInv = player.getInventory();
        Container accInv = BotaniaAPI.instance().getAccessoriesInventory(player);

        int invSize = mainInv.getContainerSize();
        int size = invSize + accInv.getContainerSize();

        for (int i = 0; i < size; i++) {
            boolean useAccessories = i >= invSize;
            Container inv = useAccessories ? accInv : mainInv;
            ItemStack stack = inv.getItem(i - (useAccessories ? invSize : 0));

            if (!stack.isEmpty()) {
                anyRequest = anyRequest || stack.is(ModTags.Items.MANA_USING_ITEMS);
            }
        }

        List<ItemStack> items = ManaItemHandler.instance().getManaItems(player);
        List<ItemStack> acc = ManaItemHandler.instance().getManaAccesories(player);
        for (ItemStack stack : Iterables.concat(items, acc)) {
            var manaItem = IXplatAbstractions.INSTANCE.findManaItem(stack);
            if (!manaItem.isNoExport()) {
                totalMana += manaItem.getMana();
                totalMaxMana += manaItem.getMaxMana();
            }
        }
        if (anyRequest) {
            Parameters parameters = new Parameters();
            parameters.value = totalMana;
            parameters.capacity = totalMaxMana;
            int color = Mth.hsvToRgb(0.55F, (float) Math.min(1F, Math.sin(Util.getMillis() / 200D) * 0.5 + 1F), 1F);
            color = (0xff - (color >> 16 & 0xff)) << 24 | color & 0xffffff;
            parameters.emptyColor = Mth.hsvToRgb(0.55F, 0.3F, 1F) & 0xffffff | 0x99000000;
            parameters.fillColor = color;
            parameters.boundColor = Mth.hsvToRgb(0.55F, 0.2F, 1F) | 0xff000000;
            return parameters;
        }
        return null;
    }

    @Override
    protected boolean shouldRender(Player player) {
        return AsteorBar.compatibility.botania && AsteorBar.config.hookBotania();
    }
}
