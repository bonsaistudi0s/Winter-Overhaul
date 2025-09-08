package tech.thatgravyboat.winteroverhaul.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.constant.dataticket.DataTicket;
import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import tech.thatgravyboat.winteroverhaul.WinterOverhaul;
import tech.thatgravyboat.winteroverhaul.client.renderer.entity.ReplacedSnowGolemRenderer;
import tech.thatgravyboat.winteroverhaul.common.entity.IUpgradeAbleSnowGolem;
import tech.thatgravyboat.winteroverhaul.common.entity.ReplacedSnowGolem;
import tech.thatgravyboat.winteroverhaul.common.items.GolemUpgradeSlot;

import java.util.Optional;

public class UpgradeLayer<S extends EntityRenderState & GeoRenderState> extends GeoRenderLayer<ReplacedSnowGolem, SnowGolem, S> {
    private static final DataTicket<ItemStack> SCARF_ITEM = DataTicket.create("scarf_item", ItemStack.class);
    private static final DataTicket<ItemStack> FACE_ITEM = DataTicket.create("face_item", ItemStack.class);
    private static final DataTicket<ItemStack> HAT_ITEM = DataTicket.create("hat_item", ItemStack.class);

    private final ReplacedSnowGolemRenderer renderer;

    public UpgradeLayer(ReplacedSnowGolemRenderer entityRendererIn) {
        super(entityRendererIn);
        this.renderer = entityRendererIn;
    }

    @Override
    public void addRenderData(ReplacedSnowGolem animatable, SnowGolem relatedObject, S renderState) {
        if (relatedObject instanceof IUpgradeAbleSnowGolem upgradeAbleSnowGolem) {
            renderState.addGeckolibData(SCARF_ITEM, upgradeAbleSnowGolem.getGolemUpgradeInSlot(GolemUpgradeSlot.SCARF));
            renderState.addGeckolibData(FACE_ITEM, upgradeAbleSnowGolem.getGolemUpgradeInSlot(GolemUpgradeSlot.FACE));
            renderState.addGeckolibData(HAT_ITEM, upgradeAbleSnowGolem.getGolemUpgradeInSlot(GolemUpgradeSlot.HAT));
        }
    }

    @Override
    public void render(S renderState, PoseStack poseStack, BakedGeoModel bakedModel, @Nullable RenderType renderType, MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, int packedLight, int packedOverlay, int renderColor) {
        if (renderState.isInvisible) return;

        ItemStack scarf = renderState.getOrDefaultGeckolibData(SCARF_ITEM, ItemStack.EMPTY);
        getTexture(scarf).ifPresent(texture -> {
            RenderType translucentType = RenderType.entityTranslucent(texture);
            VertexConsumer consumer = bufferSource.getBuffer(translucentType);

            getRenderer().reRender(renderState, poseStack, bakedModel, bufferSource,
                translucentType, consumer,
                packedLight, OverlayTexture.NO_OVERLAY,
                0xFFFFFFFF);
        });

        ItemStack face = renderState.getOrDefaultGeckolibData(FACE_ITEM, ItemStack.EMPTY);
        getTexture(face).ifPresent(texture -> {
            RenderType translucentType = RenderType.entityTranslucent(texture);
            VertexConsumer consumer = bufferSource.getBuffer(translucentType);

            getRenderer().reRender(renderState, poseStack, bakedModel, bufferSource,
                translucentType, consumer,
                packedLight, OverlayTexture.NO_OVERLAY,
                0xFFFFFFFF);
        });

        ItemStack hat = renderState.getOrDefaultGeckolibData(HAT_ITEM, ItemStack.EMPTY);
        getTexture(hat).ifPresent(texture -> {
            RenderType translucentType = RenderType.entityTranslucent(texture);
            VertexConsumer consumer = bufferSource.getBuffer(translucentType);

            getRenderer().reRender(renderState, poseStack, bakedModel, bufferSource,
                translucentType, consumer,
                packedLight, OverlayTexture.NO_OVERLAY,
                0xFFFFFFFF);
        });
    }

    private static Optional<ResourceLocation> getTexture(ItemStack item) {
        if (item.isEmpty()) return Optional.empty();
        ResourceLocation itemPath = BuiltInRegistries.ITEM.getKey(item.getItem());
        if (itemPath == null) return Optional.empty();
        return Optional.of(WinterOverhaul.id("textures/entity/upgrades/"+itemPath.getPath()+".png"));
    }
}
