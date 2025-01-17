package com.starvinelonya.oriacs.item;


import com.mojang.logging.annotations.MethodsReturnNonnullByDefault;
import com.starvinelonya.oriacs.registry.OriacsItems;
import io.github.apace100.origins.power.OriginsPowerTypes;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidDrainable;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import static net.minecraft.block.cauldron.CauldronBehavior.WATER_CAULDRON_BEHAVIOR;
import static net.minecraft.block.cauldron.CauldronBehavior.emptyCauldron;


@MethodsReturnNonnullByDefault

public class DivingHelmetItem extends OriacsArmorItem {

    public DivingHelmetItem(FabricItemSettings settings) {
        super(OriacsArmorMaterials.DIVING, Type.HELMET, settings);
        WATER_CAULDRON_BEHAVIOR.put(this, (state, world, pos, player, hand, stack) -> emptyCauldron(state, world, pos, player, hand, stack, this.transformToLandwalking(stack), (statex) -> statex.get(LeveledCauldronBlock.LEVEL) == 3, SoundEvents.ITEM_BUCKET_FILL));
    }

    public ItemStack transformToLandwalking(ItemStack original) {
        ItemStack transformed = OriacsItems.LANDWALKING_HELMET.getDefaultStack();
        transformed.setNbt(original.getOrCreateNbt());
        return transformed;
    }

    @Override
    public TypedActionResult<ItemStack> use(World level, PlayerEntity player, Hand hand) {
        if(player.shouldCancelInteraction()) {
            ItemStack original = player.getStackInHand(hand);
            BlockHitResult hit = raycast(level, player, RaycastContext.FluidHandling.SOURCE_ONLY);
            if(hit.getType() == HitResult.Type.BLOCK) {
                Direction direction = hit.getSide();
                BlockPos pos = hit.getBlockPos();
                BlockPos pos1 = pos.offset(direction);
                if(level.canPlayerModifyAt(player, pos) && player.canPlaceOn(pos1, direction, original)) {
                    BlockState state = level.getBlockState(pos);
                    if(state.getBlock() instanceof FluidDrainable pickup &&
                            pickup.tryDrainFluid(level, pos, state).getItem().equals(Items.WATER_BUCKET)) {
                        ItemStack transformed = this.transformToLandwalking(original);
                        player.incrementStat(Stats.USED.getOrCreateStat(this));
                        pickup.getBucketFillSound().ifPresent(sound -> player.playSound(sound, 1.0F, 1.0F));
                        level.emitGameEvent(player, GameEvent.FLUID_PICKUP, pos);
                        return TypedActionResult.success(transformed, level.isClient);
                    }
                }
            }
            return TypedActionResult.pass(original);
        } else {
            return super.use(level, player, hand);
        }
    }


    @Override
    public void inventoryTick(ItemStack stack, World level, Entity player, int slot, boolean selected) {
        if (slot == EquipmentSlot.HEAD.getEntitySlotId()) {
            if(!level.isClient && player.age % 20 == 0) {
                NbtCompound tag = stack.getOrCreateNbt();
                int progress = tag.getInt(TRANSFORM_PROGRESS);
                int respiration = EnchantmentHelper.getLevel(Enchantments.RESPIRATION, stack);
                if(player.isSubmergedInWater()) {
                    if(OriginsPowerTypes.WATER_BREATHING.get(player) == null && player instanceof LivingEntity) {
                        ((LivingEntity) player).addStatusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, 20, 0, false, false, true));
                    }
                    tag.putInt(TRANSFORM_PROGRESS, progress + 1);
                } else tag.putInt(TRANSFORM_PROGRESS, Math.max(0, progress -1));
                if(progress > 600
                        * (1 + respiration * 1.0)) {
                    tag.putInt(TRANSFORM_PROGRESS, 0);
                    ItemStack transformed = this.transformToLandwalking(stack);
                    player.equipStack(EquipmentSlot.HEAD, transformed);
                }
            }
        }
    }

}