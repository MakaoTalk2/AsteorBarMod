package com.afoxxvi.asteorbar;

import com.afoxxvi.asteorbar.mixin.AccessorRangedAttribute;
import com.afoxxvi.asteorbar.network.NetworkHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class AsteorBarFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        NetworkHandler.init();
        ClientLifecycleEvents.CLIENT_STARTED.register(client -> {
            Attribute maxHealthAttribute = BuiltInRegistries.ATTRIBUTE.get(BuiltInRegistries.ATTRIBUTE.getKey(Attributes.MAX_HEALTH));
            AccessorRangedAttribute accessorMaxHealthAttribute = (AccessorRangedAttribute) maxHealthAttribute;
            accessorMaxHealthAttribute.setMaxValue(1048576D);
            Attribute absorptionAttribute = BuiltInRegistries.ATTRIBUTE.get(BuiltInRegistries.ATTRIBUTE.getKey(Attributes.MAX_ABSORPTION));
            AccessorRangedAttribute accessorAbsorptionAttribute = (AccessorRangedAttribute) absorptionAttribute;
            accessorAbsorptionAttribute.setMaxValue(1048576D);
        });
    }
}
