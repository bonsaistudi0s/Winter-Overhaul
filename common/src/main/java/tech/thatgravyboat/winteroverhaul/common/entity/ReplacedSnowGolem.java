package tech.thatgravyboat.winteroverhaul.common.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import software.bernie.geckolib.animatable.GeoReplacedEntity;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class ReplacedSnowGolem implements GeoReplacedEntity {
    protected static final RawAnimation WALK = RawAnimation.begin().thenLoop("walk");
    protected static final RawAnimation IDLE = RawAnimation.begin().thenLoop("idle");
    protected static final RawAnimation SIT = RawAnimation.begin().thenLoop("sit");

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private static <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(WALK);
        } else {
            Entity entity = event.getData(DataTickets.ENTITY);

            if (entity.isPassenger() && entity.getVehicle() != null) {
                event.getController().setAnimation(SIT);
            } else {
                event.getController().setAnimation(IDLE);
            }
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
