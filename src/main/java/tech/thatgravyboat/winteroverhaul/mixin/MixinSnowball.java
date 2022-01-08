package tech.thatgravyboat.winteroverhaul.mixin;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Snowball;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import tech.thatgravyboat.winteroverhaul.common.entity.ISnowGolemSnowball;

import java.util.ArrayList;
import java.util.List;

@Mixin(Snowball.class)
public class MixinSnowball implements ISnowGolemSnowball {

    @Unique
    private boolean golemSnowball = false;

    @Unique
    private int golemMultiplier = 1;

    @Unique
    private final List<MobEffectInstance> hitEffects = new ArrayList<>();

    @Redirect(method = "onHitEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"))
    private boolean onHurt(Entity entity, DamageSource source, float amount) {
        if (winteroverhaul_isGolemSnowball()) {
            amount = amount == 0 ? 1 : amount;
            amount *= winteroverhaul_getGolemMultiplier();
        }
        if (entity instanceof LivingEntity livingEntity) hitEffects.forEach(livingEntity::addEffect);
        return entity.hurt(source, amount);
    }

    @Override
    public void winteroverhaul_setGolemMultiplier(int multiplier) {
        this.golemMultiplier = multiplier;
    }

    @Unique
    @Override
    public int winteroverhaul_getGolemMultiplier() {
        return golemMultiplier;
    }

    @Override
    public void winteroverhaul_setGolemSnowball(boolean statement) {
        this.golemSnowball = statement;
    }

    @Override
    public boolean winteroverhaul_isGolemSnowball() {
        return golemSnowball;
    }

    @Override
    public void winteroverhaul_addMobEffect(MobEffectInstance effect) {
        hitEffects.add(effect);
    }
}
