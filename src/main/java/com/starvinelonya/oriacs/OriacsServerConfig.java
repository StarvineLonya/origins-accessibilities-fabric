package com.starvinelonya.oriacs;

import com.sun.jdi.BooleanValue;
import io.github.apace100.origins.Origins;
import io.github.fabricators_of_create.porting_lib.config.ConfigEvents;
import io.github.fabricators_of_create.porting_lib.config.ModConfigSpec;
import net.minecraft.server.SaveLoading;
import org.apache.commons.lang3.tuple.Pair;

import java.util.StringJoiner;
import java.util.function.Supplier;


public class OriacsServerConfig {

    public final ModConfigSpec.BooleanValue STRICT_DIET;
    public final ModConfigSpec.IntValue DIVING_HELMET_TRANSFORM_TIME;
    public final ModConfigSpec.DoubleValue DIVING_HELMET_RESPIRATION_MULTIPLIER;
    public final ModConfigSpec.IntValue LANDWALKING_HELMET_TRANSFORM_TIME;
    public final ModConfigSpec.DoubleValue LANDWALKING_HELMET_RESPIRATION_MULTIPLIER;
    public final ModConfigSpec.IntValue UMBRELLA_MAX_DAMAGE;
    public final ModConfigSpec.BooleanValue UMBRELLA_CAN_KEEP_OUT_RAIN;
    public final ModConfigSpec.BooleanValue UMBRELLA_CAN_KEEP_OUT_SUNLIGHT;
    public final ModConfigSpec.IntValue UMBRELLA_MAX_KEEP_OUT_SUNLIGHT_COLOR;
    public final ModConfigSpec.DoubleValue UMBRELLA_SPAWN_WITH_ZOMBIE_CHANCE;
    public OriacsServerConfig(ModConfigSpec.Builder builder) {
        builder.push("diet");
        STRICT_DIET = builder
                .translation(translationKey("diet.strict_diet"))
                .comment(
                        "If enabled: Carnivore may only eat meat, Vegetarian may only eat vegetables",
                        "If disabled: Carnivore may eat anything besides vegetables, Vegetarian may eat anything besides meat",
                        "Only vanilla foods are pre-defined, you could made mod foods compatible through tags",
                        "It's recommended to leave this with the value of false if no compat has been made"
                )
                .define("strictDiet", false);
        builder.pop();

        builder.push("helmet");

        builder.push("diving");
        DIVING_HELMET_TRANSFORM_TIME = builder
                .translation(translationKey("helmet.diving.transform_time"))
                .comment("How long in seconds for the Diving Helmet to transform into Landwalking Helmet in Water")
                .defineInRange("transformTime", 600, 0, Integer.MAX_VALUE);
        DIVING_HELMET_RESPIRATION_MULTIPLIER = builder
                .translation(translationKey("helmet.diving.respiration_multiplier"))
                .comment("How much will Respiration enchantment increase the transform time for Diving Helmet")
                .defineInRange("respirationMultiplier", 1.0, 0.0, Double.MAX_VALUE);
        builder.pop();

        builder.push("landwalking");
        LANDWALKING_HELMET_TRANSFORM_TIME = builder
                .translation(translationKey("helmet.landwalking.transform_time"))
                .comment("How long in seconds for the Landwalking Helmet to transform into Diving Helmet in Water")
                .defineInRange("transformTime", 600, 0, Integer.MAX_VALUE);
        LANDWALKING_HELMET_RESPIRATION_MULTIPLIER = builder
                .translation(translationKey("helmet.landwalking.respiration_multiplier"))
                .comment("How much will Respiration enchantment increase the transform time for Landwalking Helmet")
                .defineInRange("respirationMultiplier", 1.0, 0.0, Double.MAX_VALUE);
        builder.pop();

        builder.pop();

        builder.push("umbrella");
        UMBRELLA_MAX_DAMAGE = builder
                .translation(translationKey("umbrella.max_damage"))
                .comment("Max damage of the Umbrella, aka. seconds available in rain")
                .defineInRange("maxDamage", 600, 1, Integer.MAX_VALUE);
        UMBRELLA_CAN_KEEP_OUT_RAIN = builder
                .translation(translationKey("umbrella.can_keep_out_rain"))
                .comment("If the Umbrella can be used to keep its holder from rain")
                .define("canKeepOutRain", true);
        UMBRELLA_CAN_KEEP_OUT_SUNLIGHT = builder
                .translation(translationKey("umbrella.can_keep_out_sunlight"))
                .comment("If the Umbrealla can be used to keep its holder from sunlight")
                .define("canKeepOutSunlight", true);
        UMBRELLA_MAX_KEEP_OUT_SUNLIGHT_COLOR = builder
                .translation(translationKey("umbrella.max_keep_out_sunlight_color"))
                .comment(
                        "The maximum R/G/B value of the Umbrella's color to keep out sunlight",
                        "If set to 255, umbrella of all colors can keep out sunlight"
                )
                .defineInRange("maxKeepOutSunlightColor", 127, 0, 255);
        UMBRELLA_SPAWN_WITH_ZOMBIE_CHANCE = builder
                .translation(translationKey("umbrella.spawn_with_zombie_chance"))
                .comment("The chance for a zombie is spawned with an umbrella in hard difficulty")
                .defineInRange("spawnWithZombieChance", 0.05, 0, 1);
        builder.pop();
    }

    private static String translationKey(String field) {
        return new StringJoiner(".").add("config").add(Oriacs.MOD_ID).add("server").add(field).toString();
    }


}
