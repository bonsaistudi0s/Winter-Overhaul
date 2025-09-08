package tech.thatgravyboat.winteroverhaul.common.entity;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import tech.thatgravyboat.winteroverhaul.common.items.GolemUpgradeSlot;
import tech.thatgravyboat.winteroverhaul.common.registry.ModItems;

public class GolemAttackableTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {

    public GolemAttackableTargetGoal(Mob pMob, Class<T> pTargetType, int pRandomInterval, boolean pMustSee, boolean pMustReach, TargetingConditions.Selector pSelector) {
        super(pMob, pTargetType, pRandomInterval, pMustSee, pMustReach, pSelector);
    }

    @Override
    protected double getFollowDistance() {
        float amount = 1f;
        if(this.mob instanceof IUpgradeAbleSnowGolem upgradeAbleSnowGolem) {
            Item scarf = upgradeAbleSnowGolem.getGolemUpgradeInSlot(GolemUpgradeSlot.SCARF).getItem();
            Item hat = upgradeAbleSnowGolem.getGolemUpgradeInSlot(GolemUpgradeSlot.HAT).getItem();
            amount+= scarf.equals(ModItems.GREEN_SCARF.get()) ? 0.5f : 0f;
            amount+= hat.equals(ModItems.GREEN_HAT.get()) ? 0.5f : 0f;
        }
        return super.getFollowDistance()*amount;
    }

    @Override
    protected void findTarget() {
        ServerLevel level = getServerLevel(this.mob);
        this.targetConditions.range(this.getFollowDistance());
        if (this.targetType != Player.class && this.targetType != ServerPlayer.class) {
            this.target = level.getNearestEntity(this.mob.level().getEntitiesOfClass(this.targetType, this.getTargetSearchArea(this.getFollowDistance()), (p_148152_) -> true), this.targetConditions, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
        } else {
            this.target = level.getNearestPlayer(this.targetConditions, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
        }
    }
}
