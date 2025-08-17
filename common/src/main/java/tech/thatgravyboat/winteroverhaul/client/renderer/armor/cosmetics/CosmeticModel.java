package tech.thatgravyboat.winteroverhaul.client.renderer.armor.cosmetics;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.model.GeoModel;
import tech.thatgravyboat.winteroverhaul.WinterOverhaul;
import tech.thatgravyboat.winteroverhaul.common.items.GolemUpgradeItem;

public class CosmeticModel extends GeoModel<GolemUpgradeItem> {
    @Override
    public ResourceLocation getModelResource(GolemUpgradeItem object) {
        return new ResourceLocation(WinterOverhaul.MODID, "geo/cosmetics.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GolemUpgradeItem object) {
        ResourceLocation location = BuiltInRegistries.ITEM.getKey(object);
        String path = location == null ? null : location.getPath();
        return new ResourceLocation(WinterOverhaul.MODID, "textures/entity/upgrades/"+path+".png");
    }

    @Override
    public ResourceLocation getAnimationResource(GolemUpgradeItem animatable) {
        return new ResourceLocation(WinterOverhaul.MODID, "animations/empty.animation.json");
    }
}
