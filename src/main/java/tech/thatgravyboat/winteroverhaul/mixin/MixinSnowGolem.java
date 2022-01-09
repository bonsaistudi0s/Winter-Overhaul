package tech.thatgravyboat.winteroverhaul.mixin;

import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
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
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import tech.thatgravyboat.winteroverhaul.common.entity.GolemAttackableTargetGoal;
import tech.thatgravyboat.winteroverhaul.common.entity.GolemRangedAttackGoal;
import tech.thatgravyboat.winteroverhaul.common.entity.ISnowGolemSnowball;
import tech.thatgravyboat.winteroverhaul.common.entity.IUpgradeAbleSnowGolem;
import tech.thatgravyboat.winteroverhaul.common.items.GolemUpgradeItem;
import tech.thatgravyboat.winteroverhaul.common.items.GolemUpgradeSlot;
import tech.thatgravyboat.winteroverhaul.common.registry.ModItems;
import tech.thatgravyboat.winteroverhaul.common.registry.ModParticles;

import java.util.stream.IntStream;

@Mixin(SnowGolem.class)
public abstract class MixinSnowGolem extends Mob implements IUpgradeAbleSnowGolem, IEntityAdditionalSpawnData {

    @Unique
    private final NonNullList<ItemStack> upgrades = NonNullList.withSize(GolemUpgradeSlot.values().length, ItemStack.EMPTY);

    protected MixinSnowGolem(EntityType<? extends Mob> p_21368_, Level p_21369_) {
        super(p_21368_, p_21369_);
    }


    @Inject(method = "aiStep", at = @At("HEAD"))
    private void onAiStep(CallbackInfo ci) {
        SnowGolem golem = (SnowGolem) ((Object)this);
        if (!golem.level.isClientSide) {
            if (upgrades != null) {
                upgrades.forEach(stack -> {
                    if (stack.getItem() instanceof GolemUpgradeItem upgradeItem) upgradeItem.tick(stack, golem);
                });
            }
        }else if (this.tickCount % 2 == 0) {
            SimpleParticleType particleType;
            float health = golem.getHealth()/golem.getMaxHealth();
            if (health >= 0.6f) particleType = ModParticles.SNOWFAKE_1.get();
            else if (health >=  0.3) particleType = ModParticles.SNOWFAKE_2.get();
            else particleType = ModParticles.SNOWFAKE_3.get();
            this.level.addParticle(particleType, this.getRandomX(0.5D), this.getRandomY(), this.getRandomZ(0.5D), 0.0D, 0.0D, 0.0D);
        }
    }

    @Inject(method = "registerGoals", at = @At("TAIL"))
    private void changeRangeAttackGoal(CallbackInfo ci) {
        SnowGolem golem = (SnowGolem) ((Object)this);
        golem.goalSelector.getAvailableGoals().removeIf(goal -> goal.getGoal() instanceof RangedAttackGoal);
        golem.goalSelector.addGoal(1, new GolemRangedAttackGoal(golem, 1.25D, 20, 10.0F));
        golem.targetSelector.getAvailableGoals().removeIf(goal -> goal.getGoal() instanceof NearestAttackableTargetGoal);
        golem.targetSelector.addGoal(1, new GolemAttackableTargetGoal<>(this, Mob.class, 10, true, false, entity -> entity instanceof Enemy));
    }

    @Inject(method = "performRangedAttack", locals = LocalCapture.CAPTURE_FAILHARD, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getEyeY()D"))
    private void onSnowballCreation(LivingEntity pTarget, float pDistanceFactor, CallbackInfo ci, Snowball snowball) {
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
        if (upgrades != null) {
            for (ItemStack itemstack : this.upgrades) {
                CompoundTag compoundtag = new CompoundTag();
                if (!itemstack.isEmpty()) itemstack.save(compoundtag);
                listtag.add(compoundtag);
            }
        }
        pCompound.put("GolemUpgrades", listtag);
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void onLoadNbt(CompoundTag pCompound, CallbackInfo ci) {
        if (pCompound.contains("GolemUpgrades", Tag.TAG_LIST)) {
            ListTag listtag = pCompound.getList("GolemUpgrades", Tag.TAG_COMPOUND);
            if (upgrades != null) {
                for (int i = 0; i < this.upgrades.size(); ++i) {
                    CompoundTag itemTag = listtag.getCompound(i);
                    if (!itemTag.isEmpty()) this.upgrades.set(i, ItemStack.of(itemTag));
                }
            }
        }
    }

    @Override
    public ItemStack setGolemUpgradeInSlot(GolemUpgradeSlot slot, ItemStack stack) {
        if (upgrades == null) return ItemStack.EMPTY;
        return upgrades.set(slot.index, stack);
    }

    @Override
    public ItemStack getGolemUpgradeInSlot(GolemUpgradeSlot slot) {
        if (upgrades == null) return ItemStack.EMPTY;
        return upgrades.get(slot.index);
    }

    @Override
    public void writeSpawnData(FriendlyByteBuf buffer) {
        buffer.writeInt(upgrades.size());
        for (ItemStack upgrade : upgrades) buffer.writeItem(upgrade.copy());
    }

    @Override
    public void readSpawnData(FriendlyByteBuf buffer) {
        IntStream.range(0, buffer.readInt()).forEach(i -> upgrades.set(i, buffer.readItem()));
    }

    @Override
    public @NotNull Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
