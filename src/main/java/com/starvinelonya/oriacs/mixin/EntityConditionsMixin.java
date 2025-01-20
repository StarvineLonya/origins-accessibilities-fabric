package com.starvinelonya.oriacs.mixin;

import com.starvinelonya.oriacs.item.UmbrellaItem;
import com.starvinelonya.oriacs.registry.OriacsMobEffects;
import io.github.apace100.apoli.power.factory.condition.EntityConditions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityConditions.class)
public class EntityConditionsMixin {
    @Redirect(method = "lambda$register$9", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getBrightnessAtEyes()F"))
    private static float oriacs$checkSunResistanceAndUmbrella(Entity instance) {
        if (!(instance instanceof LivingEntity living)) return 0;
        if (living.hasStatusEffect(OriacsMobEffects.SUNLIGHT_RESISTANCE) ||
                UmbrellaItem.canKeepOutSunlight(living)
        ) {
            return 0.5f;
        }
        return instance.getBrightnessAtEyes();
    }

}
