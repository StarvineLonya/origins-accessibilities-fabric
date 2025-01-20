package com.starvinelonya.oriacs.mixin;

import com.starvinelonya.oriacs.registry.OriacsMobEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PotionEntity.class)
public class PotionEntityMixin {

    @Redirect(method = "applyWater", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;hurtByWater()Z"))
    private boolean oriacs$checkWaterResistance(LivingEntity entity) {
        return entity.hurtByWater() && !entity.hasStatusEffect(OriacsMobEffects.WATER_RESISTANCE);
    }

}
