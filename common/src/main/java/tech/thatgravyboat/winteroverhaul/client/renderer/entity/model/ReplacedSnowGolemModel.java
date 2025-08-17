package tech.thatgravyboat.winteroverhaul.client.renderer.entity.model;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;
import tech.thatgravyboat.winteroverhaul.WinterOverhaul;
import tech.thatgravyboat.winteroverhaul.common.entity.ReplacedSnowGolem;

public class ReplacedSnowGolemModel<E extends ReplacedSnowGolem> extends GeoModel<E> {

    private static final ResourceLocation BASE_TEXTURE = new ResourceLocation(WinterOverhaul.MODID, "textures/entity/snow_golem.png");
    private static final ResourceLocation MODEL = new ResourceLocation(WinterOverhaul.MODID, "geo/snow_golem.geo.json");
    private static final ResourceLocation ANIMATION = new ResourceLocation(WinterOverhaul.MODID, "animations/snow_golem.animation.json");

    @Override
    public ResourceLocation getModelResource(E animatable) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(E animatable) {
        return BASE_TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationResource(E animatable) {
        return ANIMATION;
    }

    @Override
    public void setCustomAnimations(E animatable, long instanceId, AnimationState<E> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);

        if (animationState == null) return;

        EntityModelData extraData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

        CoreGeoBone head = this.getAnimationProcessor().getBone("head");
        head.setRotY(extraData.netHeadYaw() * ((float)Math.PI / 180F));
        head.setRotX(extraData.headPitch() * ((float)Math.PI / 180F));
        CoreGeoBone upperBody = this.getAnimationProcessor().getBone("body_2");
        upperBody.setRotY(extraData.netHeadYaw() * ((float)Math.PI / 180F) * 0.25F);
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
