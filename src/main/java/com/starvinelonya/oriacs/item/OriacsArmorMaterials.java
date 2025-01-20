package com.starvinelonya.oriacs.item;

import com.mojang.logging.annotations.MethodsReturnNonnullByDefault;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

import java.util.Locale;

@MethodsReturnNonnullByDefault
public enum OriacsArmorMaterials implements ArmorMaterial {

    DIVING(Ingredient.ofItems(Items.COPPER_INGOT),
            9,
            new int[]{1, 4, 5, 2},
            1, 0, 9,
            SoundEvents.ITEM_ARMOR_EQUIP_IRON),
    CHAINMEMBRANE(Ingredient.ofItems(Items.PHANTOM_MEMBRANE),
            25,
            new int[]{1, 4, 5, 2},
            4, 0, 15,
            SoundEvents.ITEM_ARMOR_EQUIP_CHAIN);

    private final Ingredient repairIngredient;
    private final int[] durabilities;
    private final int[] armorValues;
    private final float toughness;
    private final float knockbackResistance;
    private final int enchantmentValue;
    private final SoundEvent equipSound;

    OriacsArmorMaterials(Ingredient repairIngredient,
                         int[] durabilities,
                         int[] armorValues,
                         float toughness,
                         float knockbackResistance,
                         int enchantmentValue,
                         SoundEvent equipSound) {
        this.repairIngredient = repairIngredient;
        this.durabilities = durabilities;
        this.armorValues = armorValues;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.enchantmentValue = enchantmentValue;
        this.equipSound = equipSound;
    }

    OriacsArmorMaterials(Ingredient repairIngredient,
                         int durabilityMultiplier,
                         int[] armorValues,
                         float toughness,
                         float knockbackResistance,
                         int enchantmentValue,
                         SoundEvent equipSound) {
        this(repairIngredient,
                new int[]{
                        13 * durabilityMultiplier,
                        15 * durabilityMultiplier,
                        16 * durabilityMultiplier,
                        11 * durabilityMultiplier
                }, armorValues, toughness, knockbackResistance, enchantmentValue, equipSound);
    }


    @Override
    public int getDurability(ArmorItem.Type type) {
        return durabilities[type.ordinal()];
    }

    @Override
    public int getProtection(ArmorItem.Type type) {
        return armorValues[type.ordinal()];
    }

    @Override
    public int getEnchantability() {
        return enchantmentValue;
    }

    @Override
    public SoundEvent getEquipSound() {
        return equipSound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return repairIngredient;
    }

    @Override
    public String getName() {
        return this.name().toLowerCase(Locale.ROOT);
    }

    @Override
    public float getToughness() {
        return toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return knockbackResistance;
    }

}
