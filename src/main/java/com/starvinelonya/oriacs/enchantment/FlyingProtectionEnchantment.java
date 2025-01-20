package com.starvinelonya.oriacs.enchantment;

import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;

public class FlyingProtectionEnchantment extends ProtectionEnchantment {
    public FlyingProtectionEnchantment() {
        super(Rarity.RARE, Type.ALL, EquipmentSlot.HEAD);
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return Enchantments.RESPIRATION.isAcceptableItem(stack);
    }

    @Override
    public boolean isTreasure() {
        return true;
    }


    @Override
    public int getProtectionAmount(int level, DamageSource source) {
        if (!source.isSourceCreativePlayer() && source.getName().contains("fly_into_wall")) {
            return level * 3;
        }
        return 0;
    }
}
