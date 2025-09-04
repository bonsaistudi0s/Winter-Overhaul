package tech.thatgravyboat.winteroverhaul.client.renderer.armor.cosmetics;

import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import tech.thatgravyboat.winteroverhaul.common.items.GolemUpgradeItem;

public class CosmeticsRenderer extends GeoArmorRenderer<GolemUpgradeItem> {

    public CosmeticsRenderer() {
        super(new CosmeticModel());
    }

    @Override
    public @Nullable GeoBone getHeadBone(GeoModel<GolemUpgradeItem> model) {
        return model.getBone("head").orElse(null);
    }
}
