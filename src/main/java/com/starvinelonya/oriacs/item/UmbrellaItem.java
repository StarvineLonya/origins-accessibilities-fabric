package com.starvinelonya.oriacs.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Vanishable;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;


public class UmbrellaItem extends Item implements DyeableItem, Vanishable {
    public static final int BAR_COLOR = MathHelper.packRgb(0.4F, 0.4F, 1.0F);

    public UmbrellaItem(FabricItemSettings settings) {
        super(settings);
        CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(this, CauldronBehavior.CLEAN_DYEABLE_ITEM);
    }

    public boolean canKeepOutRain(ItemStack stack) {
        return stack.getDamage() < stack.getMaxDamage();
    }

    public static boolean canKeepOutRain(LivingEntity entity) {
        ItemStack stack = entity.getMainHandStack();
        if(stack.getItem() instanceof UmbrellaItem umbrella && umbrella.canKeepOutRain(stack)) return true;
        stack = entity.getOffHandStack();
        return stack.getItem() instanceof UmbrellaItem umbrella && umbrella.canKeepOutRain(stack);
    }

    public boolean canKeepOutSunlight(ItemStack stack) {
        int color = this.getColor(stack);
        int max = 127;
        return (color >> 16 & 255) <= max && (color >> 8 & 255) <= max && (color & 255) <= max;
    }

    public static boolean canKeepOutSunlight(LivingEntity entity) {
        ItemStack stack = entity.getMainHandStack();
        if(stack.getItem() instanceof UmbrellaItem umbrella && umbrella.canKeepOutSunlight(stack)) return true;
        stack = entity.getOffHandStack();
        return stack.getItem() instanceof UmbrellaItem umbrella && umbrella.canKeepOutSunlight(stack);
    }

    @Override
    public int getColor(ItemStack pStack) {
        NbtCompound compoundtag = null;
        if (pStack.getNbt() != null) {
            compoundtag = pStack.getNbt().getCompound("display");
        }
        return compoundtag != null && compoundtag.contains("color", 99) ? compoundtag.getInt("color") : 0xDCD9C0;
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        return BAR_COLOR;
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        return stack.getDamage() > 0;
    }

    @Override
    public boolean isDamageable() {
        return false;
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return false;
    }

    @Override
    public boolean allowNbtUpdateAnimation(PlayerEntity player, Hand hand, ItemStack oldStack, ItemStack newStack) {
        return ItemStack.areItemsEqual(oldStack, newStack);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        BlockPos blockPos = entity.getBlockPos();
        boolean isInRain = world.hasRain(blockPos) || entity.getWorld().hasRain(BlockPos.ofFloored(blockPos.getX(), entity.getBoundingBox().maxY, blockPos.getZ()));
        ;
        if (slot == EquipmentSlot.MAINHAND.getEntitySlotId()) {
            if (isInRain) {
                stack.setDamage(MathHelper.clamp(stack.getDamage() + 1, 0, stack.getMaxDamage()));
            }else{
                stack.setDamage(MathHelper.clamp(stack.getDamage() - 1, 0, stack.getMaxDamage()));
            }
        }
    }



}
