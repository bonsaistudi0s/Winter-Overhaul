package tech.thatgravyboat.winteroverhaul.client.renderer.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.animatable.processing.AnimationState;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;
import tech.thatgravyboat.winteroverhaul.WinterOverhaul;
import tech.thatgravyboat.winteroverhaul.common.entity.Robin;

public class RobinModel extends GeoModel<Robin> {

    @Override
    public ResourceLocation getModelResource(GeoRenderState object) {
        return WinterOverhaul.id("robin");
    }

    @Override
    public ResourceLocation getTextureResource(GeoRenderState object) {
        return WinterOverhaul.id("textures/entity/robin.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Robin animatable) {
        return WinterOverhaul.id("robin");
    }

    @Override
    public void setCustomAnimations(AnimationState<Robin> animationState) {
        super.setCustomAnimations(animationState);
        if (animationState == null) return;

        float netHeadYaw = animationState.getDataOrDefault(DataTickets.ENTITY_YAW, 0f);
        float headPitch = animationState.getDataOrDefault(DataTickets.ENTITY_PITCH, 0f);

        var head = this.getAnimationProcessor().getBone("head");
        head.setRotX((headPitch * Mth.DEG_TO_RAD - 0.261799f));
        head.setRotY(netHeadYaw * Mth.DEG_TO_RAD);
    }
}
