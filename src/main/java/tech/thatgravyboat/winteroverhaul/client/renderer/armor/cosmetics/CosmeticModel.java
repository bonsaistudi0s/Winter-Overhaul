package tech.thatgravyboat.winteroverhaul.client.renderer.armor.cosmetics;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import tech.thatgravyboat.winteroverhaul.WinterOverhaul;
import tech.thatgravyboat.winteroverhaul.common.items.GolemUpgradeItem;

public class CosmeticModel extends AnimatedGeoModel<GolemUpgradeItem> {

    @Override
    public ResourceLocation getModelLocation(GolemUpgradeItem object) {
        return new ResourceLocation(WinterOverhaul.MODID, "geo/cosmetics.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(GolemUpgradeItem object) {
        ResourceLocation location = object.getRegistryName();
        String path = location == null ? null : location.getPath();
        return new ResourceLocation(WinterOverhaul.MODID, "textures/entity/upgrades/"+path+".png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(GolemUpgradeItem animatable) {
        return new ResourceLocation(WinterOverhaul.MODID, "animations/empty.animation.json");
    }
}
