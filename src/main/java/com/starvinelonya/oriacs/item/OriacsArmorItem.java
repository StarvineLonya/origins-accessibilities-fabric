package com.starvinelonya.oriacs.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;

public class OriacsArmorItem extends ArmorItem {
    public static final String TRANSFORM_PROGRESS = "TransformProgress";

    public OriacsArmorItem(ArmorMaterial material, Type type, FabricItemSettings settings) {
        super(material, type, settings);
    }

}
