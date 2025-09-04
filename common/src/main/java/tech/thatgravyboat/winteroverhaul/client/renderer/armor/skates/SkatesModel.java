package tech.thatgravyboat.winteroverhaul.client.renderer.armor.skates;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import tech.thatgravyboat.winteroverhaul.WinterOverhaul;
import tech.thatgravyboat.winteroverhaul.common.items.GolemUpgradeItem;
import tech.thatgravyboat.winteroverhaul.common.items.SkateItem;

public class SkatesModel extends GeoModel<SkateItem> {

    @Override
    public ResourceLocation getModelResource(SkateItem object) {
        return WinterOverhaul.id("geo/skates.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SkateItem object) {
        return WinterOverhaul.id("textures/entity/skates/base.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SkateItem animatable) {
        return WinterOverhaul.id("animations/empty.animation.json");
    }
}
