package tech.thatgravyboat.winteroverhaul.common.entity;

import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class ReplacedSnowGolem implements IAnimatable {

    private final AnimationFactory factory = new AnimationFactory(this);

    private static  <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.snow_golem.walk", true));
        } else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.snow_golem.idle", true));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 20, ReplacedSnowGolem::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
