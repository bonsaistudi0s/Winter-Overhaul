package tech.thatgravyboat.winteroverhaul.client.renderer.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;
import tech.thatgravyboat.winteroverhaul.WinterOverhaul;
import tech.thatgravyboat.winteroverhaul.common.entity.Robin;

public class RobinModel extends GeoModel<Robin> {

    @Override
    public ResourceLocation getModelResource(Robin object) {
        return WinterOverhaul.id("geo/robin.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Robin object) {
        return WinterOverhaul.id("textures/entity/robin.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Robin animatable) {
        return WinterOverhaul.id("animations/robin.animation.json");
    }

    @Override
    public void setCustomAnimations(Robin animatable, long instanceId, AnimationState<Robin> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animationState == null) return;

        EntityModelData extraDataOfType = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
        var head = this.getAnimationProcessor().getBone("head");
        head.setRotX((extraDataOfType.headPitch() * Mth.DEG_TO_RAD - 0.261799f));
        head.setRotY(extraDataOfType.netHeadYaw() * Mth.DEG_TO_RAD);
    }
}
