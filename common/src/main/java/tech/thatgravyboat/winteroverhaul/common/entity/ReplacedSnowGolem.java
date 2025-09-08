package tech.thatgravyboat.winteroverhaul.common.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoReplacedEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.animatable.processing.AnimationController;
import software.bernie.geckolib.animatable.processing.AnimationState;
import software.bernie.geckolib.animatable.processing.AnimationTest;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.constant.dataticket.DataTicket;
import software.bernie.geckolib.util.GeckoLibUtil;

public class ReplacedSnowGolem implements GeoReplacedEntity {
    public static final DataTicket<Boolean> IS_PASSENGER = DataTicket.create("is_passenger", Boolean.class);

    protected static final RawAnimation WALK = RawAnimation.begin().thenLoop("walk");
    protected static final RawAnimation IDLE = RawAnimation.begin().thenLoop("idle");
    protected static final RawAnimation SIT = RawAnimation.begin().thenLoop("sit");

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private static <P extends GeoAnimatable> PlayState predicate(AnimationTest<P> event) {
        if (event.getDataOrDefault(DataTickets.IS_MOVING, false)) {
            event.controller().setAnimation(WALK);
        } else {
            if (event.getDataOrDefault(IS_PASSENGER, false)) {
                event.controller().setAnimation(SIT);
            } else {
                event.controller().setAnimation(IDLE);
            }
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>("controller", 20, ReplacedSnowGolem::predicate));
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
