package tech.thatgravyboat.winteroverhaul.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tech.thatgravyboat.winteroverhaul.common.entity.*;
import tech.thatgravyboat.winteroverhaul.common.items.GolemUpgradeItem;
import tech.thatgravyboat.winteroverhaul.common.items.GolemUpgradeSlot;
import tech.thatgravyboat.winteroverhaul.common.registry.ModItems;
import tech.thatgravyboat.winteroverhaul.common.registry.ModParticles;

@Mixin(SnowGolem.class)
public abstract class MixinSnowGolem extends Mob implements IUpgradeAbleSnowGolem {

    protected MixinSnowGolem(EntityType<? extends Mob> p_21368_, Level p_21369_) {
        super(p_21368_, p_21369_);
    }

    @Inject(method = "aiStep", at = @At("HEAD"))
    private void onAiStep(CallbackInfo ci) {
        SnowGolem golem = (SnowGolem) ((Object) this);
        if (!golem.level().isClientSide) {
            if (winteroverhaul_getUpgrades() != null) {
                winteroverhaul_getUpgrades().forEach(stack -> {
                    if (stack.getItem() instanceof GolemUpgradeItem upgradeItem) upgradeItem.tick(stack, golem);
                });
            }
        } else if (this.tickCount % 2 == 0) {
            SimpleParticleType particleType;
            float health = golem.getHealth() / golem.getMaxHealth();
            if (health >= 0.6f) particleType = ModParticles.SNOWFLAKE_1.get();
            else if (health >= 0.3) particleType = ModParticles.SNOWFLAKE_2.get();
            else particleType = ModParticles.SNOWFLAKE_3.get();
            this.level().addParticle(particleType, this.getRandomX(0.5D), this.getRandomY(), this.getRandomZ(0.5D), 0.0D, 0.0D, 0.0D);
        }
    }

    @Inject(method = "registerGoals", at = @At("TAIL"))
    private void changeRangeAttackGoal(CallbackInfo ci) {
        SnowGolem golem = (SnowGolem) ((Object) this);
        this.goalSelector.getAvailableGoals().removeIf(goal -> goal.getGoal() instanceof RangedAttackGoal);
        this.goalSelector.addGoal(1, new GolemRangedAttackGoal(golem, 1.25D, 20, 10.0F));
        this.targetSelector.getAvailableGoals().removeIf(goal -> goal.getGoal() instanceof NearestAttackableTargetGoal);
        this.targetSelector.addGoal(1, new GolemAttackableTargetGoal<>(this, Mob.class, 10, true, false, (entity, level) -> entity instanceof Enemy));
    }

    @ModifyExpressionValue(method = "performRangedAttack", at = @At(value = "NEW", target = "(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/world/entity/projectile/Snowball;"))
    private Snowball onSnowballCreation(Snowball snowball) {
        if (snowball instanceof ISnowGolemSnowball snowGolemSnowball) {
            snowGolemSnowball.winteroverhaul_setGolemSnowball(true);
            Item scraf = getGolemUpgradeInSlot(GolemUpgradeSlot.SCARF).getItem();
            Item hat = getGolemUpgradeInSlot(GolemUpgradeSlot.HAT).getItem();
            int amount = hat.equals(ModItems.RED_HAT.get()) ? 2 : 0;
            amount += scraf.equals(ModItems.RED_SCARF.get()) ? 2 : 0;
            if (amount > 0) snowGolemSnowball.winteroverhaul_setGolemMultiplier(amount);
            Item face = getGolemUpgradeInSlot(GolemUpgradeSlot.FACE).getItem();
            if (face.equals(Items.CARROT) || face.equals(Items.GOLDEN_CARROT)) {
                snowGolemSnowball.winteroverhaul_addMobEffect(new MobEffectInstance(MobEffects.SLOWNESS, 20, 1));
                if (face.equals(Items.GOLDEN_CARROT)) {
                    snowGolemSnowball.winteroverhaul_addMobEffect(new MobEffectInstance(MobEffects.WEAKNESS, 20, 4));
                }
            }
        }
        return snowball;
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void onSaveNbt(ValueOutput output, CallbackInfo ci) {
        output.store("GolemUpgrades", GolemUpgradeAttachmentHelper.CODEC, winteroverhaul_getUpgrades());
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void onLoadNbt(ValueInput input, CallbackInfo ci) {
        winteroverhaul_setUpgrades(input.read("GolemUpgrades", GolemUpgradeAttachmentHelper.CODEC).orElse(GolemUpgradeAttachmentHelper.createDefault()));
    }

    @Override
    public ItemStack setGolemUpgradeInSlot(GolemUpgradeSlot slot, ItemStack stack) {
        ItemStack oldStack = winteroverhaul_getUpgrades().set(slot.index, stack);
        winteroverhaul_updateUpgrades();
        return oldStack;
    }

    @Override
    public ItemStack getGolemUpgradeInSlot(GolemUpgradeSlot slot) {
        return this.winteroverhaul_getUpgrades().get(slot.index);
    }
}