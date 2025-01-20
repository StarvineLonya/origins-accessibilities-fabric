package com.starvinelonya.oriacs.mixin;

import com.starvinelonya.oriacs.item.UmbrellaItem;
import com.starvinelonya.oriacs.registry.OriacsMobEffects;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin extends LivingEntity {
    protected MobEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "isAffectedByDaylight", at = @At("HEAD"), cancellable = true)
    private void oriacs$cheSunResistanceAndUmbrella(CallbackInfoReturnable<Boolean> cir) {
        if (this.hasStatusEffect(OriacsMobEffects.SUNLIGHT_RESISTANCE) || UmbrellaItem.canKeepOutSunlight(this)) {
            cir.setReturnValue(false);
        }
    }
}
