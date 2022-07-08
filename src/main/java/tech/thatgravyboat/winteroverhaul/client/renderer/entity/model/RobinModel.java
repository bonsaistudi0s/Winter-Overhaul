package tech.thatgravyboat.winteroverhaul.client.renderer.entity.model;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;
import tech.thatgravyboat.winteroverhaul.WinterOverhaul;
import tech.thatgravyboat.winteroverhaul.common.entity.Robin;

import java.util.List;

public class RobinModel extends AnimatedGeoModel<Robin> {

    @Override
    public ResourceLocation getModelResource(Robin object) {
        return new ResourceLocation(WinterOverhaul.MODID, "geo/robin.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Robin object) {
        return new ResourceLocation(WinterOverhaul.MODID, "textures/entity/robin.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Robin animatable) {
        return new ResourceLocation(WinterOverhaul.MODID, "animations/robin.animation.json");
    }

    @Override
    public void setLivingAnimations(Robin entity, Integer uniqueID, @Nullable AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);

        if (customPredicate == null) return;

        EntityModelData extraDataOfType = getExtraData(customPredicate);
        var head = this.getAnimationProcessor().getBone("head");
        head.setRotationX((extraDataOfType.headPitch * ((float)Math.PI / 180F))-0.261799f);
        head.setRotationY(extraDataOfType.netHeadYaw * ((float)Math.PI / 180F));
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static <T> T getExtraData(AnimationEvent customPredicate) {
        return (T) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
    }
}
