package tech.thatgravyboat.winteroverhaul.client.renderer.armor.cosmetics;

import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import tech.thatgravyboat.winteroverhaul.common.items.GolemUpgradeItem;

public class CosmeticsRenderer extends GeoArmorRenderer<GolemUpgradeItem> {

    public CosmeticsRenderer() {
        super(new CosmeticModel());
    }

    @Override
    public GeoBone getHeadBone() {
        return this.model.getBone("head").orElse(null);
    }
}
