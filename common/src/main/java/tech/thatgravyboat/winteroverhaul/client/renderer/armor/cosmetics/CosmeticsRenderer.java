package tech.thatgravyboat.winteroverhaul.client.renderer.armor.cosmetics;

import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;
import tech.thatgravyboat.winteroverhaul.common.items.GolemUpgradeItem;

public class CosmeticsRenderer<S extends HumanoidRenderState & GeoRenderState> extends GeoArmorRenderer<GolemUpgradeItem, S> {

    public CosmeticsRenderer() {
        super(new CosmeticModel());
    }

    @Override
    protected void grabRelevantBones(BakedGeoModel model) {
        super.grabRelevantBones(model);

        this.headBone = model.getBone("head").orElse(null);
    }
}
