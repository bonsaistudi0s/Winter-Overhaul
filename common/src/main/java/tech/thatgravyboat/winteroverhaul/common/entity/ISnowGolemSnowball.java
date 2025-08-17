package tech.thatgravyboat.winteroverhaul.common.entity;

import net.minecraft.world.effect.MobEffectInstance;

public interface ISnowGolemSnowball {

    void winteroverhaul_setGolemMultiplier(int multiplier);

    int winteroverhaul_getGolemMultiplier();

    void winteroverhaul_setGolemSnowball(boolean statement);

    boolean winteroverhaul_isGolemSnowball();

    void winteroverhaul_addMobEffect(MobEffectInstance effect);
}
