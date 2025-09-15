package tech.thatgravyboat.winteroverhaul.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tech.thatgravyboat.winteroverhaul.common.entity.IUpgradeAbleSnowGolem;
import tech.thatgravyboat.winteroverhaul.common.items.GolemUpgradeSlot;
import tech.thatgravyboat.winteroverhaul.common.items.SkateItem;
import tech.thatgravyboat.winteroverhaul.common.registry.ModBlockTags;
import tech.thatgravyboat.winteroverhaul.common.registry.ModItems;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity extends Entity {

    public MixinLivingEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Inject(method = "getBlockSpeedFactor", at = @At("HEAD"), cancellable = true)
    private void onGetBlockSpeedFactor(CallbackInfoReturnable<Float> cir) {
        BlockState state = this.level().getBlockState(this.getBlockPosBelowThatAffectsMyMovement());
        boolean isIce = state.is(ModBlockTags.ICE);
        //noinspection ConstantConditions
        boolean isWearingBoots = (Object)this instanceof LivingEntity livingEntity && livingEntity.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof SkateItem;
        if (isIce && isWearingBoots) cir.setReturnValue(1.05f);
    }

    @ModifyVariable(
        method = "travelInAir",
        at = @At(value = "STORE"),
        slice = @Slice(
            from = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getBlockPosBelowThatAffectsMyMovement()Lnet/minecraft/core/BlockPos;"),
            to = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;handleRelativeFrictionAndCalculateMovement(Lnet/minecraft/world/phys/Vec3;F)Lnet/minecraft/world/phys/Vec3;")
        ),
        ordinal = 0
    )
    private float changeFriction(float friction) {
        BlockState state = this.level().getBlockState(this.getBlockPosBelowThatAffectsMyMovement());
        boolean isIce = state.is(ModBlockTags.ICE);
        //noinspection ConstantConditions
        if (isIce && !this.isSprinting() && (Object)this instanceof LivingEntity livingEntity && livingEntity.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof SkateItem) {
            return 0.8F;
        }
        return friction;
    }

    @ModifyExpressionValue(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isInWaterOrRain()Z"))
    private boolean disableDamageFromRainIfWearingTopHat(boolean original) {
        if ((Object) this instanceof SnowGolem snowGolem && original) {
            if (((AccessorEntity) this).callIsInRain() && !this.isInWater()
                && ((IUpgradeAbleSnowGolem) snowGolem).getGolemUpgradeInSlot(GolemUpgradeSlot.HAT).is(ModItems.TOP_HAT.get())) {
                return false;
            }
        }

        return original;
    }
}
