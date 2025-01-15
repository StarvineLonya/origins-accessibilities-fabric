package com.starvinelonya.oriacs.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.state.property.Properties;

import static com.starvinelonya.oriacs.Oriacs.CONFIG;

public class UmbrellaItem extends Item implements DyeableItem, Vanishable {

    public boolean canKeepOutRain(ItemStack stack) {
        return CONFIG.UMBRELLA_CAN_KEEP_OUT_RAIN.get() && stack.getDamageValue() < stack.getMaxDamage();
    }

    public static boolean canKeepOutRain(LivingEntity entity) {
        ItemStack stack = entity.getMainHandStack();
        if(stack.getItem() instanceof UmbrellaItem umbrella && umbrella.canKeepOutRain(stack)) return true;
        stack = entity.getOffHandStack();
        return stack.getItem() instanceof UmbrellaItem umbrella && umbrella.canKeepOutRain(stack);
    }

    public boolean canKeepOutSunlight(ItemStack stack) {
        if(!CONFIG.UMBRELLA_CAN_KEEP_OUT_SUNLIGHT.get()) return false;
        int color = this.getColor(stack);
        int max = CONFIG.UMBRELLA_MAX_KEEP_OUT_SUNLIGHT_COLOR.get();
        return (color >> 16 & 255) <= max && (color >> 8 & 255) <= max && (color & 255) <= max;
    }

    public static boolean canKeepOutSunlight(LivingEntity entity) {
        ItemStack stack = entity.getMainHandStack();
        if(stack.getItem() instanceof UmbrellaItem umbrella && umbrella.canKeepOutSunlight(stack)) return true;
        stack = entity.getOffHandStack();
        return stack.getItem() instanceof UmbrellaItem umbrella && umbrella.canKeepOutSunlight(stack);
    }

    /**
     * Umbrella is made of Phantom Membrane instead of Leather, so the color must be changed
     */
    @Override
    public int getColor(ItemStack pStack) {
        NbtCompound compoundtag = pStack.getE("display");
        return compoundtag != null && compoundtag.contains("color", 99) ? compoundtag.getInt("color") : 0xDCD9C0;
    }

    @Override
    public int getBarColor(ItemStack pStack) {
        return BAR_COLOR;
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return stack.getDamageValue() > 0;
    }

    @Override
    public boolean canBeDepleted() {
        return false;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return CONFIG.UMBRELLA_MAX_DAMAGE.get();
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return ItemStack.isSameIgnoreDurability(oldStack, newStack);
    }

    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        if(entity.tickCount % 20 != 0) return;
        Level level = entity.level;
        BlockPos pos = entity.blockPosition();
        boolean isInRain = level.isRainingAt(pos) || level.isRainingAt(new BlockPos(pos.getX(), entity.getBoundingBox().maxY, pos.getZ()));
        for(ItemStack stack : entity.getHandSlots()) {
            if(stack.getItem() instanceof UmbrellaItem) {
                if(isInRain) {
                    stack.setDamageValue(Mth.clamp(stack.getDamageValue() + 1, 0, stack.getMaxDamage()));
                } else if(level.getBiome(pos).value().shouldSnowGolemBurn(pos)) {
                    stack.setDamageValue(Mth.clamp(stack.getDamageValue() - 2, 0, stack.getMaxDamage()));
                } else {
                    stack.setDamageValue(Mth.clamp(stack.getDamageValue() - 1, 0, stack.getMaxDamage()));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingSpawn(LivingSpawnEvent.SpecialSpawn event) {
        if(event.getEntity() instanceof Zombie zombie) {
            RandomSource random = zombie.getRandom();
            if(zombie.level.getDifficulty() == Difficulty.HARD &&
                    random.nextFloat() < OriacsServerConfig.CONFIG.UMBRELLA_SPAWN_WITH_ZOMBIE_CHANCE.get() &&
                    zombie.getItemInHand(InteractionHand.OFF_HAND).isEmpty()
            ) {
                UmbrellaItem umbrella = OriacsItems.UMBRELLA.get();
                ItemStack stack = umbrella.getDefaultInstance();
                umbrella.setColor(stack, random.nextInt(0xFFFFFF));
                zombie.setItemInHand(InteractionHand.OFF_HAND, stack);
            }
        }
    }



    public UmbrellaItem(FabricItemSettings settings) {
        super(settings.maxCount(1));
    }
}
