package tech.thatgravyboat.winteroverhaul.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import tech.thatgravyboat.winteroverhaul.WinterOverhaul;
import tech.thatgravyboat.winteroverhaul.client.renderer.entity.ReplacedSnowGolemRenderer;
import tech.thatgravyboat.winteroverhaul.common.entity.IUpgradeAbleSnowGolem;
import tech.thatgravyboat.winteroverhaul.common.entity.ReplacedSnowGolem;
import tech.thatgravyboat.winteroverhaul.common.items.GolemUpgradeSlot;

import java.util.Optional;

public class UpgradeLayer extends GeoRenderLayer<ReplacedSnowGolem> {

    private final ReplacedSnowGolemRenderer renderer;

    public UpgradeLayer(ReplacedSnowGolemRenderer entityRendererIn) {
        super(entityRendererIn);
        this.renderer = entityRendererIn;
    }

    @Override
    public void render(PoseStack poseStack, ReplacedSnowGolem animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        SnowGolem entity = this.renderer.getCurrentEntity();

        if (entity.isInvisible()) return;
        if (entity instanceof IUpgradeAbleSnowGolem upgradeAbleSnowGolem) {
            ItemStack scarf = upgradeAbleSnowGolem.getGolemUpgradeInSlot(GolemUpgradeSlot.SCARF);
            getTexture(scarf).ifPresent(texture -> {
                RenderType translucentType = RenderType.entityTranslucent(texture);
                VertexConsumer consumer = bufferSource.getBuffer(translucentType);

                getRenderer().reRender(bakedModel, poseStack, bufferSource,
                    animatable, translucentType, consumer, partialTick,
                    packedLight, OverlayTexture.NO_OVERLAY,
                    0xFFFFFFFF);
            });

            ItemStack face = upgradeAbleSnowGolem.getGolemUpgradeInSlot(GolemUpgradeSlot.FACE);
            getTexture(face).ifPresent(texture -> {
                RenderType translucentType = RenderType.entityTranslucent(texture);
                VertexConsumer consumer = bufferSource.getBuffer(translucentType);

                getRenderer().reRender(bakedModel, poseStack, bufferSource,
                    animatable, translucentType, consumer, partialTick,
                    packedLight, OverlayTexture.NO_OVERLAY,
                    0xFFFFFFFF);
            });

            ItemStack hat = upgradeAbleSnowGolem.getGolemUpgradeInSlot(GolemUpgradeSlot.HAT);
            getTexture(hat).ifPresent(texture -> {
                RenderType translucentType = RenderType.entityTranslucent(texture);
                VertexConsumer consumer = bufferSource.getBuffer(translucentType);

                getRenderer().reRender(bakedModel, poseStack, bufferSource,
                    animatable, translucentType, consumer, partialTick,
                    packedLight, OverlayTexture.NO_OVERLAY,
                    0xFFFFFFFF);
            });
        }
    }

    private static Optional<ResourceLocation> getTexture(ItemStack item) {
        if (item.isEmpty()) return Optional.empty();
        ResourceLocation itemPath = BuiltInRegistries.ITEM.getKey(item.getItem());
        if (itemPath == null) return Optional.empty();
        return Optional.of(WinterOverhaul.id("textures/entity/upgrades/"+itemPath.getPath()+".png"));
    }
}
