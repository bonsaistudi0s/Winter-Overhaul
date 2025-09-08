package tech.thatgravyboat.winteroverhaul.client.renderer.armor.skates;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;
import tech.thatgravyboat.winteroverhaul.WinterOverhaul;
import tech.thatgravyboat.winteroverhaul.common.items.GolemUpgradeItem;
import tech.thatgravyboat.winteroverhaul.common.items.SkateItem;

public class SkatesModel extends GeoModel<SkateItem> {

    @Override
    public ResourceLocation getModelResource(GeoRenderState object) {
        return WinterOverhaul.id("skates");
    }

    @Override
    public ResourceLocation getTextureResource(GeoRenderState object) {
        return WinterOverhaul.id("textures/entity/skates/base.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SkateItem animatable) {
        return WinterOverhaul.id("empty");
    }
}
