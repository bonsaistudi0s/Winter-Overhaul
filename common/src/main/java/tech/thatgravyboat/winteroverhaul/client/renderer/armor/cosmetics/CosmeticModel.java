package tech.thatgravyboat.winteroverhaul.client.renderer.armor.cosmetics;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;
import tech.thatgravyboat.winteroverhaul.WinterOverhaul;
import tech.thatgravyboat.winteroverhaul.common.items.GolemUpgradeItem;

public class CosmeticModel extends GeoModel<GolemUpgradeItem> {
    @Override
    public ResourceLocation getModelResource(GeoRenderState renderState) {
        return WinterOverhaul.id("cosmetics");
    }

    @Override
    public ResourceLocation getTextureResource(GeoRenderState state) {
        ResourceLocation location = BuiltInRegistries.ITEM.getKey(state.getGeckolibData(DataTickets.ITEM));
        String path = location == null ? null : location.getPath();
        return WinterOverhaul.id("textures/entity/upgrades/"+path+".png");
    }

    @Override
    public ResourceLocation getAnimationResource(GolemUpgradeItem animatable) {
        return WinterOverhaul.id("empty");
    }

    @Override
    public void addAdditionalStateData(GolemUpgradeItem animatable, GeoRenderState renderState) {
        super.addAdditionalStateData(animatable, renderState);
        renderState.addGeckolibData(DataTickets.ITEM, animatable);
    }
}
