package com.starvinelonya.oriacs.registry;

import com.starvinelonya.oriacs.Oriacs;
import com.starvinelonya.oriacs.item.UmbrellaItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class OriacsItems {

    public static final UmbrellaItem UMBRELLA = (UmbrellaItem) registerModItem("umbrella", new UmbrellaItem(new FabricItemSettings()));


    public static Item registerModItem(String name, Item item){
        return Registry.register(Registries.ITEM, new Identifier(Oriacs.MOD_ID, name), item);
    }
    public static void registerModItems() {
        Oriacs.LOGGER.info("Registering Mod Items for " + Oriacs.MOD_ID);
    }
}
