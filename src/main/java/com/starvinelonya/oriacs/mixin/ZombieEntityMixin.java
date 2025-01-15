package com.starvinelonya.oriacs.mixin;

import com.starvinelonya.oriacs.item.UmbrellaItem;
import com.starvinelonya.oriacs.registry.OriacsItems;
import net.fabricmc.fabric.mixin.entity.event.MobEntityMixin;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ZombieEntity.class)
public class ZombieEntityMixin extends HostileEntity {
    protected ZombieEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "initEquipment", at = @At("TAIL"))
    private void initEquipment(Random random, LocalDifficulty localDifficulty, CallbackInfo ci) {
        if (random.nextFloat() < (this.getWorld().getDifficulty() == Difficulty.HARD ? 0.05F : 0.01F)) {
            int i = random.nextInt(3);
            if (i == 0) {
                UmbrellaItem umbrellaItem = (UmbrellaItem) OriacsItems.UMBRELLA;
                ItemStack stack = umbrellaItem.getDefaultStack();
                umbrellaItem.setColor(stack, random.nextInt(0xFFFFFF));
                this.equipStack(EquipmentSlot.OFFHAND, stack);
            }
        }
    }
}
