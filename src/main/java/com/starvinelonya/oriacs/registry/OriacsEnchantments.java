package com.starvinelonya.oriacs.registry;

import com.starvinelonya.oriacs.Oriacs;
import com.starvinelonya.oriacs.enchantment.FlyingProtectionEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class OriacsEnchantments {

    public static final FlyingProtectionEnchantment FLYING_PROTECTION = (FlyingProtectionEnchantment) register("flying_protection", new FlyingProtectionEnchantment());

    public static Enchantment register(String name, Enchantment enchantment) {
        return Registry.register(Registries.ENCHANTMENT, new Identifier(Oriacs.MOD_ID, name), enchantment);
    }

}
