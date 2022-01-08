package tech.thatgravyboat.winteroverhaul.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;
import tech.thatgravyboat.winteroverhaul.WinterOverhaul;
import tech.thatgravyboat.winteroverhaul.common.entity.IUpgradeAbleSnowGolem;
import tech.thatgravyboat.winteroverhaul.common.items.GolemUpgradeSlot;

import java.util.Optional;

@SuppressWarnings({"unchecked", "rawtypes"})
public class UpgradeLayer extends GeoLayerRenderer {

    public UpgradeLayer(IGeoRenderer entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack stack, MultiBufferSource buffer, int packedLightIn, Entity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity.isInvisible()) return;
        if (entity instanceof IUpgradeAbleSnowGolem upgradeAbleSnowGolem) {
            GeoModel normalModel = this.getEntityModel().getModel(this.getEntityModel().getModelLocation(null));

            ItemStack scarf = upgradeAbleSnowGolem.getGolemUpgradeInSlot(GolemUpgradeSlot.SCARF);
            getTexture(scarf).ifPresent(texture ->
                    getRenderer().render(normalModel, entity, partialTicks,
                    null, stack, null, buffer.getBuffer(RenderType.entityTranslucent(texture)),
                    packedLightIn, OverlayTexture.NO_OVERLAY,
                    1f, 1f, 1f, 1f));

            ItemStack face = upgradeAbleSnowGolem.getGolemUpgradeInSlot(GolemUpgradeSlot.FACE);
            getTexture(face).ifPresent(texture ->
                    getRenderer().render(normalModel, entity, partialTicks,
                    null, stack, null, buffer.getBuffer(RenderType.entityTranslucent(texture)),
                    packedLightIn, OverlayTexture.NO_OVERLAY,
                    1f, 1f, 1f, 1f));

            ItemStack hat = upgradeAbleSnowGolem.getGolemUpgradeInSlot(GolemUpgradeSlot.HAT);
            getTexture(hat).ifPresent(texture ->
                    getRenderer().render(normalModel, entity, partialTicks,
                            null, stack, null, buffer.getBuffer(RenderType.entityTranslucent(texture)),
                            packedLightIn, OverlayTexture.NO_OVERLAY,
                            1f, 1f, 1f, 1f));


        }
    }

    private static Optional<ResourceLocation> getTexture(ItemStack item) {
        if (item.isEmpty()) return Optional.empty();
        ResourceLocation itemPath = item.getItem().getRegistryName();
        if (itemPath == null) return Optional.empty();
        return Optional.of(new ResourceLocation(WinterOverhaul.MODID, "textures/entity/upgrades/"+itemPath.getPath()+".png"));
    }
}
