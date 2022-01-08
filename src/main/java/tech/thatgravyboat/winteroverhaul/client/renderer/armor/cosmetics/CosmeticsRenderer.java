package tech.thatgravyboat.winteroverhaul.client.renderer.armor.cosmetics;

import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;
import tech.thatgravyboat.winteroverhaul.common.items.GolemUpgradeItem;

public class CosmeticsRenderer extends GeoArmorRenderer<GolemUpgradeItem> {

    public CosmeticsRenderer() {
        super(new CosmeticModel());

        this.headBone = "head";
        this.bodyBone = null;
        this.rightArmBone = null;
        this.leftArmBone = null;
        this.rightLegBone = null;
        this.leftLegBone = null;
        this.rightBootBone = null;
        this.leftBootBone = null;

    }
}
