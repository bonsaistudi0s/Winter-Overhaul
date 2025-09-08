package tech.thatgravyboat.winteroverhaul.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.SnowGolem;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.constant.dataticket.DataTicket;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import tech.thatgravyboat.winteroverhaul.WinterOverhaul;
import tech.thatgravyboat.winteroverhaul.client.renderer.entity.ReplacedSnowGolemRenderer;
import tech.thatgravyboat.winteroverhaul.common.entity.ReplacedSnowGolem;

import java.util.UUID;

public class RandomArmLayer<S extends EntityRenderState & GeoRenderState> extends GeoRenderLayer<ReplacedSnowGolem, SnowGolem, S> {
    private static final DataTicket<UUID> ENTITY_UUID = DataTicket.create("entity_uuid", UUID.class);
    private static final int SIZE = 10;

    private static final ResourceLocation[] TEXTURES = Util.make(() -> {
        ResourceLocation[] textures = new ResourceLocation[SIZE];
        for (int i = 0; i < SIZE; i++) {
            textures[i] = WinterOverhaul.id("textures/entity/arms/snow_golem_arms_"+(i+1)+".png");
        }
        return textures;
    });

    public RandomArmLayer(ReplacedSnowGolemRenderer entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void addRenderData(ReplacedSnowGolem animatable, SnowGolem relatedObject, S renderState) {
        renderState.addGeckolibData(ENTITY_UUID, relatedObject.getUUID());
    }

    @Override
    public void render(S renderState, PoseStack poseStack, BakedGeoModel bakedModel, @Nullable RenderType renderType, MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, int packedLightIn, int packedOverlay, int renderColor) {
        if (renderState.isInvisible)
            return;

        RenderType renderType1 = RenderType.entityTranslucent(getRandomTexture(renderState.getOrDefaultGeckolibData(ENTITY_UUID, Util.NIL_UUID)));
        getRenderer().reRender(renderState, poseStack, bakedModel, bufferSource, renderType1,
                bufferSource.getBuffer(renderType1),
                packedLightIn, OverlayTexture.NO_OVERLAY,
                0xFFFFFFFF);
    }

    private ResourceLocation getRandomTexture(UUID uuid) {
        return TEXTURES[(int) (Math.abs(uuid.getLeastSignificantBits() ^ (uuid.getMostSignificantBits() | 0xFF00FFFFFFFFFFL)) % SIZE)];
    }
}
