package com.starvinelonya.oriacs.registry;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroups;

public class OriacsItemGroups {
    public static void addItemsToItemGroup() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> content.add(OriacsItems.UMBRELLA));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(content -> {
            content.add(OriacsItems.DIVING_HELMET);
            content.add(OriacsItems.LANDWALKING_HELMET);
            content.add(OriacsItems.CHAINMEMBRANE_HELMET);
            content.add(OriacsItems.CHAINMEMBRANE_CHESTPLATE);
            content.add(OriacsItems.CHAINMEMBRANE_LEGGINGS);
            content.add(OriacsItems.CHAINMEMBRANE_BOOTS);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(content -> content.add(OriacsItems.FRESH_AIR_BOTTLE));
    }
}
