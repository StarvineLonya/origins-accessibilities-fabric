package com.starvinelonya.oriacs.registry;

import com.starvinelonya.oriacs.Oriacs;
import com.starvinelonya.oriacs.item.*;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class OriacsItems {

    public static final UmbrellaItem UMBRELLA = (UmbrellaItem) registerModItem("umbrella", new UmbrellaItem(new FabricItemSettings().maxDamage(600)));
    public static final DivingHelmetItem DIVING_HELMET = (DivingHelmetItem) registerModItem("diving_helmet", new DivingHelmetItem(new FabricItemSettings()));

    public static final LandwalkingHelmetItem LANDWALKING_HELMET = (LandwalkingHelmetItem) registerModItem("landwalking_helmet", new LandwalkingHelmetItem(new FabricItemSettings()));

    public static final OriacsArmorItem CHAINMEMBRANE_HELMET = (OriacsArmorItem) registerModItem("chainmembrane_helmet", new OriacsArmorItem(OriacsArmorMaterials.CHAINMEMBRANE, ArmorItem.Type.HELMET, new FabricItemSettings()));
    public static final OriacsArmorItem CHAINMEMBRANE_CHESTPLATE = (OriacsArmorItem) registerModItem("chainmembrane_chestplate", new OriacsArmorItem(OriacsArmorMaterials.CHAINMEMBRANE, ArmorItem.Type.CHESTPLATE, new FabricItemSettings()));
    public static final OriacsArmorItem CHAINMEMBRANE_LEGGINGS = (OriacsArmorItem) registerModItem("chainmembrane_leggings", new OriacsArmorItem(OriacsArmorMaterials.CHAINMEMBRANE, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final OriacsArmorItem CHAINMEMBRANE_BOOTS = (OriacsArmorItem) registerModItem("chainmembrane_boots", new OriacsArmorItem(OriacsArmorMaterials.CHAINMEMBRANE, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    public static final FreshAirBottleItem FRESH_AIR_BOTTLE = (FreshAirBottleItem) registerModItem("fresh_air_bottle", new FreshAirBottleItem(new FabricItemSettings().maxCount(1)));
    public static Item registerModItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(Oriacs.MOD_ID, name), item);
    }

    public static void registerModItems() {
        Oriacs.LOGGER.info("Registering Mod Items for " + Oriacs.MOD_ID);
    }
}
