package tech.thatgravyboat.winteroverhaul.client.renderer.armor.skates;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import tech.thatgravyboat.winteroverhaul.WinterOverhaul;
import tech.thatgravyboat.winteroverhaul.common.items.SkateItem;

public class SkatesModel extends AnimatedGeoModel<SkateItem> {

    @Override
    public ResourceLocation getModelResource(SkateItem object) {
        return new ResourceLocation(WinterOverhaul.MODID, "geo/skates.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SkateItem object) {
        return new ResourceLocation(WinterOverhaul.MODID, "textures/entity/skates/base.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SkateItem animatable) {
        return new ResourceLocation(WinterOverhaul.MODID, "animations/empty.animation.json");
    }
}
