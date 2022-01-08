package tech.thatgravyboat.winteroverhaul.client.renderer.entity.model;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;
import tech.thatgravyboat.winteroverhaul.WinterOverhaul;
import tech.thatgravyboat.winteroverhaul.common.entity.ReplacedSnowGolem;

public class ReplacedSnowGolemModel<E extends ReplacedSnowGolem> extends AnimatedGeoModel<E> {

    private static final ResourceLocation BASE_TEXTURE = new ResourceLocation(WinterOverhaul.MODID, "textures/entity/snow_golem.png");
    private static final ResourceLocation MODEL = new ResourceLocation(WinterOverhaul.MODID, "geo/snow_golem.geo.json");
    private static final ResourceLocation ANIMATION = new ResourceLocation(WinterOverhaul.MODID, "animations/snow_golem.animation.json");

    @Override
    public ResourceLocation getModelLocation(E object) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureLocation(E object) {
        return BASE_TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(E animatable) {
        return ANIMATION;
    }

    @Override
    public void setLivingAnimations(E entity, Integer uniqueID, @Nullable AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);

        if (customPredicate == null) return;

        EntityModelData extraData = getExtraData(customPredicate, EntityModelData.class);

        IBone head = this.getAnimationProcessor().getBone("head");
        head.setRotationY(extraData.netHeadYaw * ((float)Math.PI / 180F));
        head.setRotationX(extraData.headPitch * ((float)Math.PI / 180F));
        IBone upperBody = this.getAnimationProcessor().getBone("body_2");
        upperBody.setRotationY(extraData.netHeadYaw * ((float)Math.PI / 180F) * 0.25F);
//        float sinRotY = Mth.sin(upperBody.getRotationY());
//        float cosRotY = Mth.cos(upperBody.getRotationY());
//        IBone leftArm = this.getAnimationProcessor().getBone("left_arm");
//        IBone rightArm = this.getAnimationProcessor().getBone("right_arm");
//        leftArm.setRotationY(upperBody.getRotationY());
//        rightArm.setRotationY(upperBody.getRotationY() + (float)Math.PI);
//        leftArm.setPositionX(cosRotY * 5.0F);
//        leftArm.setPositionZ(-sinRotY * 5.0F);
//        rightArm.setPositionX(-cosRotY * 5.0F);
//        rightArm.setPositionZ(sinRotY * 5.0F);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static <T> T getExtraData(AnimationEvent customPredicate, Class<T> clazz) {
        return (T) customPredicate.getExtraDataOfType(clazz).get(0);
    }
}
