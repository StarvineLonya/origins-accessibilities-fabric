package com.starvinelonya.oriacs.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.starvinelonya.oriacs.registry.OriacsItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.GlassBottleItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GlassBottleItem.class)
public class GlassBottleItemMixin extends Item {
    public GlassBottleItemMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/TypedActionResult;pass(Ljava/lang/Object;)Lnet/minecraft/util/TypedActionResult;", ordinal = 0), cancellable = true)
    private void oriacs$fillAir(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir, @Local ItemStack itemStack){
        if (user.getY() >= 86){

            world.playSound(user, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.NEUTRAL, 1.0f, 0.5f);
            user.incrementStat(Stats.USED.getOrCreateStat(itemStack.getItem()));
            cir.setReturnValue(
                    TypedActionResult.success(
                            ItemUsage.exchangeStack(itemStack, user, OriacsItems.FRESH_AIR_BOTTLE.getDefaultStack()),
                            world.isClient
                    )
            );
        }
    }
}
