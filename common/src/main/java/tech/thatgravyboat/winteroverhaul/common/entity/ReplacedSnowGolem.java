package tech.thatgravyboat.winteroverhaul.common.entity;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import software.bernie.geckolib.animatable.GeoReplacedEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.network.SerializableDataTicket;
import software.bernie.geckolib.util.GeckoLibUtil;
import tech.thatgravyboat.winteroverhaul.WinterOverhaul;

public class ReplacedSnowGolem implements GeoReplacedEntity {
    public static final SerializableDataTicket<Boolean> HAS_PUMPKIN = GeckoLibUtil.addDataTicket(SerializableDataTicket.ofBoolean(WinterOverhaul.id("has_pumpkin")));
    public static final SerializableDataTicket<Boolean> IS_INVISIBLE = GeckoLibUtil.addDataTicket(SerializableDataTicket.ofBoolean(WinterOverhaul.id("is_invisible")));

    protected static final RawAnimation WALK = RawAnimation.begin().thenLoop("animation.snow_golem.walk");
    protected static final RawAnimation IDLE = RawAnimation.begin().thenLoop("animation.snow_golem.idle");

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private static  <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(WALK);
        } else {
            event.getController().setAnimation(IDLE);
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 20, ReplacedSnowGolem::predicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public EntityType<?> getReplacingEntityType() {
        return EntityType.SNOW_GOLEM;
    }
}
