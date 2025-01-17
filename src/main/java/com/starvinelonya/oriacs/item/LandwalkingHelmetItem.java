 package com.starvinelonya.oriacs.item;


 import com.mojang.logging.annotations.MethodsReturnNonnullByDefault;
 import com.starvinelonya.oriacs.registry.OriacsItems;
 import io.github.apace100.origins.power.OriginsPowerTypes;
 import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
 import net.minecraft.block.BlockState;
 import net.minecraft.block.FluidFillable;
 import net.minecraft.block.LeveledCauldronBlock;
 import net.minecraft.block.cauldron.CauldronBehavior;
 import net.minecraft.enchantment.EnchantmentHelper;
 import net.minecraft.enchantment.Enchantments;
 import net.minecraft.entity.Entity;
 import net.minecraft.entity.EquipmentSlot;
 import net.minecraft.entity.LivingEntity;
 import net.minecraft.entity.effect.StatusEffectInstance;
 import net.minecraft.entity.effect.StatusEffects;
 import net.minecraft.entity.player.PlayerEntity;
 import net.minecraft.fluid.Fluids;
 import net.minecraft.item.BucketItem;
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

 import static net.minecraft.block.cauldron.CauldronBehavior.emptyCauldron;


 @MethodsReturnNonnullByDefault

public class LandwalkingHelmetItem extends OriacsArmorItem {

    public LandwalkingHelmetItem(FabricItemSettings settings) {
        super(OriacsArmorMaterials.DIVING, Type.HELMET, settings);
        CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.put(this, (state, world, pos, player, hand, stack) -> emptyCauldron(state, world, pos, player, hand, stack, this.transformToDiving(stack), (statex) -> statex.get(LeveledCauldronBlock.LEVEL) == 3, SoundEvents.ITEM_BUCKET_FILL));
    }

    public ItemStack transformToDiving(ItemStack original) {
        ItemStack transformed = OriacsItems.DIVING_HELMET.getDefaultStack();
        transformed.setNbt(original.getOrCreateNbt());
        return transformed;
    }

     @Override
     public TypedActionResult<ItemStack> use(World level, PlayerEntity player, Hand hand) {
         if(player.shouldCancelInteraction()) {
             ItemStack original = player.getStackInHand(hand);
             BlockHitResult hit = raycast(level, player, RaycastContext.FluidHandling.NONE);
             if(hit.getType() == HitResult.Type.BLOCK) {
                 Direction direction = hit.getSide();
                 BlockPos pos = hit.getBlockPos();
                 BlockPos pos1 = pos.offset(direction);
                 if(level.canPlayerModifyAt(player, pos) && player.canPlaceOn(pos1, direction, original)) {
                     BlockState blockstate = level.getBlockState(pos);
                     pos = blockstate.getBlock() instanceof FluidFillable container &&
                             container.canFillWithFluid(level, pos, blockstate, Fluids.WATER) ? pos : pos1;
                     if(Items.WATER_BUCKET instanceof BucketItem bucket &&
                             bucket.placeFluid(player, level, pos, hit)
                     ) {
                         ItemStack transformed = this.transformToDiving(original);
                         player.incrementStat(Stats.USED.getOrCreateStat(this));
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
                 if (player.isSubmergedInWater()) {
                     if (OriginsPowerTypes.WATER_BREATHING.get(player) != null && player instanceof LivingEntity) {
                         ((LivingEntity) player).addStatusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, 20, 0, false, false, true));
                     }
                     tag.putInt(TRANSFORM_PROGRESS, progress + 1);
                 } else tag.putInt(TRANSFORM_PROGRESS, Math.max(0, progress - 1));
                 if (progress > 600
                         * (1 + respiration * 1.0)) {
                     tag.putInt(TRANSFORM_PROGRESS, 0);
                     ItemStack transformed = this.transformToDiving(stack);
                     player.equipStack(EquipmentSlot.HEAD, transformed);
                 }
             }
         }
     }
 }