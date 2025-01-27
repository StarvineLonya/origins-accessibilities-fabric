package com.starvinelonya.oriacs.mixin;

import com.starvinelonya.oriacs.item.UmbrellaItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Inject(method = "isBeingRainedOn", at = @At("HEAD"), cancellable = true)
    private void oriacs$checkUmbrella(CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof LivingEntity living && UmbrellaItem.canKeepOutRain(living)) {
            cir.setReturnValue(false);
        }
    }


}