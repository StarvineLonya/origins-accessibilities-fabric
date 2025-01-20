package com.starvinelonya.oriacs.registry;

import com.starvinelonya.oriacs.Oriacs;
import com.starvinelonya.oriacs.effect.SimpleMobEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class OriacsMobEffects {
    private static StatusEffect register(String id, StatusEffect entry) {
        return Registry.register(Registries.STATUS_EFFECT, new Identifier(Oriacs.MOD_ID, id), entry);
    }

    public static final SimpleMobEffect WATER_RESISTANCE = (SimpleMobEffect) register("water_resistance", new SimpleMobEffect(StatusEffectCategory.BENEFICIAL, 0xAACCFF));

    public static final SimpleMobEffect SUNLIGHT_RESISTANCE = (SimpleMobEffect) register("sunlight_resistance", new SimpleMobEffect(StatusEffectCategory.BENEFICIAL, 0x000000));

    public static final SimpleMobEffect FRESH_AIR = (SimpleMobEffect) register("fresh_air", new SimpleMobEffect(StatusEffectCategory.BENEFICIAL, 0x000000));

    public static void registerMobEffects() {
    }

}
