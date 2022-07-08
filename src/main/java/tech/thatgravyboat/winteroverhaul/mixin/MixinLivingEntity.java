package tech.thatgravyboat.winteroverhaul.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tech.thatgravyboat.winteroverhaul.common.items.SkateItem;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity extends Entity {

    public MixinLivingEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Inject(method = "getBlockSpeedFactor", at = @At("HEAD"), cancellable = true)
    private void onGetBlockSpeedFactor(CallbackInfoReturnable<Float> cir) {
        BlockState state = this.level.getBlockState(this.getBlockPosBelowThatAffectsMyMovement());
        boolean isIce = state.is(Blocks.ICE) || state.is(Blocks.BLUE_ICE) || state.is(Blocks.PACKED_ICE);
        //noinspection ConstantConditions
        boolean isWearingBoots = ((Object)this) instanceof LivingEntity livingEntity && livingEntity.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof SkateItem;
        if (isIce && isWearingBoots) cir.setReturnValue(1.05f);
    }

    @Redirect(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;getFriction(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/Entity;)F"))
    private float onGetFriction(BlockState state, LevelReader level, BlockPos pos, Entity entity) {
        if (state.is(BlockTags.ICE) && !entity.isSprinting() && entity instanceof LivingEntity livingEntity && livingEntity.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof SkateItem) {
            return 0.8F;
        }
        return state.getFriction(level, pos, entity);
    }
}
