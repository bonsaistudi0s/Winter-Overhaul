package tech.thatgravyboat.winteroverhaul.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
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
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tech.thatgravyboat.winteroverhaul.common.entity.GolemAttackableTargetGoal;
import tech.thatgravyboat.winteroverhaul.common.entity.GolemRangedAttackGoal;
import tech.thatgravyboat.winteroverhaul.common.entity.ISnowGolemSnowball;
import tech.thatgravyboat.winteroverhaul.common.entity.IUpgradeAbleSnowGolem;
import tech.thatgravyboat.winteroverhaul.common.items.GolemUpgradeItem;
import tech.thatgravyboat.winteroverhaul.common.items.GolemUpgradeSlot;
import tech.thatgravyboat.winteroverhaul.common.registry.ModItems;
import tech.thatgravyboat.winteroverhaul.common.registry.ModParticles;

@Mixin(SnowGolem.class)
public abstract class MixinSnowGolem extends Mob implements IUpgradeAbleSnowGolem {

    @Unique
    private final NonNullList<ItemStack> winteroverhaul_upgrades = NonNullList.withSize(GolemUpgradeSlot.values().length, ItemStack.EMPTY);

    @Unique private static final EntityDataAccessor<ItemStack> WINTEROVERHAUL_HAT = SynchedEntityData.defineId(SnowGolem.class, EntityDataSerializers.ITEM_STACK);
    @Unique private static final EntityDataAccessor<ItemStack> WINTEROVERHAUL_FACE = SynchedEntityData.defineId(SnowGolem.class, EntityDataSerializers.ITEM_STACK);
    @Unique private static final EntityDataAccessor<ItemStack> WINTEROVERHAUL_SCARF = SynchedEntityData.defineId(SnowGolem.class, EntityDataSerializers.ITEM_STACK);

    protected MixinSnowGolem(EntityType<? extends Mob> p_21368_, Level p_21369_) {
        super(p_21368_, p_21369_);
    }

    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    private void registerWinterOverhaulData(CallbackInfo ci) {
        this.getEntityData().define(WINTEROVERHAUL_HAT, ItemStack.EMPTY);
        this.getEntityData().define(WINTEROVERHAUL_FACE, ItemStack.EMPTY);
        this.getEntityData().define(WINTEROVERHAUL_SCARF, ItemStack.EMPTY);
    }

    @Inject(method = "aiStep", at = @At("HEAD"))
    private void onAiStep(CallbackInfo ci) {
        SnowGolem golem = (SnowGolem) ((Object)this);
        if (!golem.level().isClientSide) {
            if (winteroverhaul_upgrades != null) {
                winteroverhaul_upgrades.forEach(stack -> {
                    if (stack.getItem() instanceof GolemUpgradeItem upgradeItem) upgradeItem.tick(stack, golem);
                });
            }
        }else if (this.tickCount % 2 == 0) {
            SimpleParticleType particleType;
            float health = golem.getHealth()/golem.getMaxHealth();
            if (health >= 0.6f) particleType = ModParticles.SNOWFLAKE_1.get();
            else if (health >=  0.3) particleType = ModParticles.SNOWFLAKE_2.get();
            else particleType = ModParticles.SNOWFLAKE_3.get();
            this.level().addParticle(particleType, this.getRandomX(0.5D), this.getRandomY(), this.getRandomZ(0.5D), 0.0D, 0.0D, 0.0D);
        }
    }

    @Inject(method = "registerGoals", at = @At("TAIL"))
    private void changeRangeAttackGoal(CallbackInfo ci) {
        SnowGolem golem = (SnowGolem) ((Object)this);
        this.goalSelector.getAvailableGoals().removeIf(goal -> goal.getGoal() instanceof RangedAttackGoal);
        this.goalSelector.addGoal(1, new GolemRangedAttackGoal(golem, 1.25D, 20, 10.0F));
        this.targetSelector.getAvailableGoals().removeIf(goal -> goal.getGoal() instanceof NearestAttackableTargetGoal);
        this.targetSelector.addGoal(1, new GolemAttackableTargetGoal<>(this, Mob.class, 10, true, false, entity -> entity instanceof Enemy));
    }

    @Inject(method = "performRangedAttack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getEyeY()D"))
    private void onSnowballCreation(LivingEntity pTarget, float pDistanceFactor, CallbackInfo ci, @Local Snowball snowball) {
        if (snowball instanceof ISnowGolemSnowball snowGolemSnowball) {
            snowGolemSnowball.winteroverhaul_setGolemSnowball(true);
            Item scraf = getGolemUpgradeInSlot(GolemUpgradeSlot.SCARF).getItem();
            Item hat = getGolemUpgradeInSlot(GolemUpgradeSlot.HAT).getItem();
            int amount = hat.equals(ModItems.RED_HAT.get()) ? 2 : 0;
            amount += scraf.equals(ModItems.RED_SCARF.get()) ? 2 : 0;
            if (amount > 0) snowGolemSnowball.winteroverhaul_setGolemMultiplier(amount);
            Item face = getGolemUpgradeInSlot(GolemUpgradeSlot.FACE).getItem();
            if (face.equals(Items.CARROT) || face.equals(Items.GOLDEN_CARROT)) {
                snowGolemSnowball.winteroverhaul_addMobEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 1));
                if (face.equals(Items.GOLDEN_CARROT)) {
                    snowGolemSnowball.winteroverhaul_addMobEffect(new MobEffectInstance(MobEffects.WEAKNESS, 20, 4));
                }
            }
        }
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void onSaveNbt(CompoundTag pCompound, CallbackInfo ci) {
        ListTag listtag = new ListTag();
        if (winteroverhaul_upgrades != null) {
            for (ItemStack itemstack : this.winteroverhaul_upgrades) {
                CompoundTag compoundtag = new CompoundTag();
                if (!itemstack.isEmpty()) itemstack.save(compoundtag);
                listtag.add(compoundtag);
            }

            winteroverhaul_updateUpgrades();
        }
        pCompound.put("GolemUpgrades", listtag);
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void onLoadNbt(CompoundTag pCompound, CallbackInfo ci) {
        if (pCompound.contains("GolemUpgrades", Tag.TAG_LIST)) {
            ListTag listtag = pCompound.getList("GolemUpgrades", Tag.TAG_COMPOUND);
            if (winteroverhaul_upgrades != null) {
                for (int i = 0; i < this.winteroverhaul_upgrades.size(); ++i) {
                    CompoundTag itemTag = listtag.getCompound(i);
                    if (!itemTag.isEmpty()) this.winteroverhaul_upgrades.set(i, ItemStack.of(itemTag));
                }

                winteroverhaul_updateUpgrades();
            }
        }
    }

    @Override
    public ItemStack setGolemUpgradeInSlot(GolemUpgradeSlot slot, ItemStack stack) {
        if (winteroverhaul_upgrades == null) return ItemStack.EMPTY;
        winteroverhaul_updateUpgrades();
        return winteroverhaul_upgrades.set(slot.index, stack);
    }

    @Override
    public ItemStack getGolemUpgradeInSlot(GolemUpgradeSlot slot) {
        return switch (slot) {
            case HAT -> this.getEntityData().get(WINTEROVERHAUL_HAT);
            case FACE -> this.getEntityData().get(WINTEROVERHAUL_FACE);
            case SCARF -> this.getEntityData().get(WINTEROVERHAUL_SCARF);
        };
    }

    @Unique
    private void winteroverhaul_updateUpgrades() {
        if (winteroverhaul_upgrades == null)
            return;

        this.getEntityData().set(WINTEROVERHAUL_HAT, winteroverhaul_upgrades.get(GolemUpgradeSlot.HAT.index));
        this.getEntityData().set(WINTEROVERHAUL_FACE, winteroverhaul_upgrades.get(GolemUpgradeSlot.FACE.index));
        this.getEntityData().set(WINTEROVERHAUL_SCARF, winteroverhaul_upgrades.get(GolemUpgradeSlot.SCARF.index));
    }
}
