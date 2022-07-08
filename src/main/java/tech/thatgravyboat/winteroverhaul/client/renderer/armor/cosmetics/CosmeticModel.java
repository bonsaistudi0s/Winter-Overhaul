package tech.thatgravyboat.winteroverhaul.client.renderer.armor.cosmetics;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import tech.thatgravyboat.winteroverhaul.WinterOverhaul;
import tech.thatgravyboat.winteroverhaul.common.items.GolemUpgradeItem;

public class CosmeticModel extends AnimatedGeoModel<GolemUpgradeItem> {

    @Override
    public ResourceLocation getModelResource(GolemUpgradeItem object) {
        return new ResourceLocation(WinterOverhaul.MODID, "geo/cosmetics.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GolemUpgradeItem object) {
        ResourceLocation location = ForgeRegistries.ITEMS.getKey(object);
        String path = location == null ? null : location.getPath();
        return new ResourceLocation(WinterOverhaul.MODID, "textures/entity/upgrades/"+path+".png");
    }

    @Override
    public ResourceLocation getAnimationResource(GolemUpgradeItem animatable) {
        return new ResourceLocation(WinterOverhaul.MODID, "animations/empty.animation.json");
    }
}
