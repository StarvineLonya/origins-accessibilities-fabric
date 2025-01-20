package com.starvinelonya.oriacs.client;

import com.starvinelonya.oriacs.item.UmbrellaItem;
import com.starvinelonya.oriacs.registry.OriacsItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;

public class OriacsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> stack != null && stack.getItem() instanceof UmbrellaItem ? ((UmbrellaItem) stack.getItem()).getColor(stack) : 0xaaaaaa, OriacsItems.UMBRELLA);
    }
}
