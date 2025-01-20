package com.starvinelonya.oriacs.mixin;

import com.starvinelonya.oriacs.registry.OriacsMobEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow
    public abstract void baseTick();

    @Redirect(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;hurtByWater()Z"))
    private boolean oriacs$checkWaterResistance(LivingEntity entity) {
        return entity.hurtByWater() && !entity.hasStatusEffect(OriacsMobEffects.WATER_RESISTANCE);
    }

}