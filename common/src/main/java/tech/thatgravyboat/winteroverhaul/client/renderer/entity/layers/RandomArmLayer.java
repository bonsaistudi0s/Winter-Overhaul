package tech.thatgravyboat.winteroverhaul.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.SnowGolem;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import tech.thatgravyboat.winteroverhaul.WinterOverhaul;
import tech.thatgravyboat.winteroverhaul.client.renderer.entity.ReplacedSnowGolemRenderer;
import tech.thatgravyboat.winteroverhaul.common.entity.ReplacedSnowGolem;

import java.util.UUID;

public class RandomArmLayer extends GeoRenderLayer<ReplacedSnowGolem> {

    private static final int SIZE = 10;

    private static final ResourceLocation[] TEXTURES = Util.make(() -> {
        ResourceLocation[] textures = new ResourceLocation[SIZE];
        for (int i = 0; i < SIZE; i++) {
            textures[i] = WinterOverhaul.id("textures/entity/arms/snow_golem_arms_"+(i+1)+".png");
        }
        return textures;
    });

    private final ReplacedSnowGolemRenderer renderer;

    public RandomArmLayer(ReplacedSnowGolemRenderer entityRendererIn) {
        super(entityRendererIn);
        this.renderer = entityRendererIn;
    }

    @Override
    public void render(PoseStack stack, ReplacedSnowGolem entity, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTicks, int packedLightIn, int packedOverlay) {
        SnowGolem snowGolem = this.renderer.getCurrentEntity();
        if (snowGolem.isInvisible()) return;
        RenderType renderType1 = RenderType.entityTranslucent(getRandomTexture(snowGolem.getUUID()));
        getRenderer().reRender(bakedModel, stack, bufferSource, entity, renderType1,
                bufferSource.getBuffer(renderType1), partialTicks,
                packedLightIn, OverlayTexture.NO_OVERLAY,
                0xFFFFFFFF);
    }

    private ResourceLocation getRandomTexture(UUID uuid) {
        return TEXTURES[(int) (Math.abs(uuid.getLeastSignificantBits() ^ (uuid.getMostSignificantBits() | 0xFF00FFFFFFFFFFL)) % SIZE)];
    }
}
