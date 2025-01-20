package com.starvinelonya.oriacs.registry;

import com.starvinelonya.oriacs.Oriacs;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistry;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class OriacsPotions {

    public static final Potion WATER_RESISTANCE = register("water_resistance", "water_resistance", OriacsMobEffects.WATER_RESISTANCE, 3600);
    public static final Potion LONG_WATER_RESISTANCE = register("long_water_resistance", "water_resistance", OriacsMobEffects.WATER_RESISTANCE, 9600);
    public static final Potion SUNLIGHT_RESISTANCE = register("sunscreen", "sunscreen", OriacsMobEffects.SUNLIGHT_RESISTANCE, 3600);
    public static final Potion LONG_SUNLIGHT_RESISTANCE = register("long_sunscreen", "sunscreen", OriacsMobEffects.SUNLIGHT_RESISTANCE, 9600);

    public static final Potion FRESH_AIR = register("fresh_air", "fresh_air", OriacsMobEffects.FRESH_AIR, 600);

    public static Potion register(String id, String name, StatusEffect effect, int duration) {
        return Registry.register(Registries.POTION, new Identifier(Oriacs.MOD_ID, id), new Potion(name, new StatusEffectInstance(effect, duration)));
    }

    public static void registerPotions() {
        FabricBrewingRecipeRegistry.registerPotionRecipe(Potions.WATER_BREATHING, Ingredient.ofItems(Items.SCUTE), WATER_RESISTANCE);
        FabricBrewingRecipeRegistry.registerPotionRecipe(WATER_RESISTANCE, Ingredient.ofItems(Items.REDSTONE), LONG_WATER_RESISTANCE);
        FabricBrewingRecipeRegistry.registerPotionRecipe(Potions.NIGHT_VISION, Ingredient.ofItems(Items.GLOW_INK_SAC), SUNLIGHT_RESISTANCE);
        FabricBrewingRecipeRegistry.registerPotionRecipe(SUNLIGHT_RESISTANCE, Ingredient.ofItems(Items.REDSTONE), LONG_SUNLIGHT_RESISTANCE);
    }
}
