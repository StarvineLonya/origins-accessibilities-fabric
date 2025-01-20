package com.starvinelonya.oriacs.mixin;

import com.simibubi.create.content.kinetics.fan.AirCurrent;
import com.simibubi.create.content.kinetics.fan.IAirCurrentSource;
import com.simibubi.create.content.kinetics.fan.processing.AllFanProcessingTypes;
import com.simibubi.create.content.kinetics.fan.processing.FanProcessingType;
import com.starvinelonya.oriacs.registry.OriacsMobEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Pseudo
@Mixin(value = AirCurrent.class, remap = false)
public abstract class AirCurrentMixin {
    @Shadow
    public abstract FanProcessingType getTypeAt(float offset);

    @Shadow
    @Final
    public IAirCurrentSource source;

    @ModifyVariable(method = "tickAffectedEntities", name = "entity", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;setVelocity(Lnet/minecraft/util/math/Vec3d;)V", remap = true))
    private Entity oriacs$applyFreshAirEffect(Entity entity) {
        if (entity.age % 20 == 0 && entity instanceof LivingEntity living) {
            FanProcessingType type = getTypeAt((float) entity.getPos().distanceTo(Vec3d.ofCenter(this.source.getAirCurrentPos())) - 0.5f);
            if (type == null || type == AllFanProcessingTypes.NONE) {
                living.addStatusEffect(new StatusEffectInstance(OriacsMobEffects.FRESH_AIR, 30, 0, true, false, true));
            }
        }
        return entity;
    }
}
