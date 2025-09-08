package tech.thatgravyboat.winteroverhaul.client.renderer.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.animatable.processing.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;
import tech.thatgravyboat.winteroverhaul.WinterOverhaul;
import tech.thatgravyboat.winteroverhaul.common.entity.ReplacedSnowGolem;

public class ReplacedSnowGolemModel<E extends ReplacedSnowGolem> extends GeoModel<E> {

    private static final ResourceLocation BASE_TEXTURE = WinterOverhaul.id("textures/entity/snow_golem.png");
    private static final ResourceLocation MODEL = WinterOverhaul.id("snow_golem");
    private static final ResourceLocation ANIMATION = WinterOverhaul.id("snow_golem");

    @Override
    public ResourceLocation getModelResource(GeoRenderState renderState) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(GeoRenderState animatable) {
        return BASE_TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationResource(E animatable) {
        return ANIMATION;
    }

    @Override
    public void setCustomAnimations(AnimationState<E> animationState) {
        super.setCustomAnimations(animationState);

        if (animationState == null) return;

        float netHeadYaw = animationState.getDataOrDefault(DataTickets.ENTITY_YAW, 0f);
        float headPitch = animationState.getDataOrDefault(DataTickets.ENTITY_PITCH, 0f);

        GeoBone head = this.getAnimationProcessor().getBone("head");
        head.setRotY(netHeadYaw * Mth.DEG_TO_RAD);
        head.setRotX(headPitch * Mth.DEG_TO_RAD);
        GeoBone upperBody = this.getAnimationProcessor().getBone("body_2");
        upperBody.setRotY(netHeadYaw * Mth.DEG_TO_RAD * 0.25F);
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
}
