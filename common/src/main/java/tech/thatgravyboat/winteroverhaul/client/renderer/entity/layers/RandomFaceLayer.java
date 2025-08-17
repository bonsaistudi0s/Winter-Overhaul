package tech.thatgravyboat.winteroverhaul.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.SnowGolem;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import tech.thatgravyboat.winteroverhaul.WinterOverhaul;
import tech.thatgravyboat.winteroverhaul.client.renderer.entity.ReplacedSnowGolemRenderer;
import tech.thatgravyboat.winteroverhaul.common.entity.ReplacedSnowGolem;

import java.util.UUID;

public class RandomFaceLayer extends GeoRenderLayer<ReplacedSnowGolem> {

    private static final int SIZE = 12;

    private static final ResourceLocation[] TEXTURES = Util.make(() -> {
        ResourceLocation[] textures = new ResourceLocation[SIZE];
        for (int i = 0; i < SIZE; i++) {
            textures[i] = new ResourceLocation(WinterOverhaul.MODID, "textures/entity/faces/snow_golem_face_"+(i+1)+".png");
        }
        return textures;
    });

    private final ReplacedSnowGolemRenderer renderer;

    public RandomFaceLayer(ReplacedSnowGolemRenderer entityRendererIn) {
        super(entityRendererIn);
        this.renderer = entityRendererIn;
    }

    @Override
    public void render(PoseStack poseStack, ReplacedSnowGolem animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        SnowGolem entity = this.renderer.getCurrentEntity();
        if (entity.isInvisible()) return;
        if (entity.hasPumpkin()) return;
        RenderType renderType1 = RenderType.entityTranslucent(getRandomTexture(entity.getUUID()));
        VertexConsumer consumer = bufferSource.getBuffer(renderType1);
        getRenderer().reRender(bakedModel, poseStack, bufferSource,
                animatable, renderType1, consumer, partialTick,
                packedLight, packedOverlay,
                1f, 1f, 1f, 1f);
    }

    private ResourceLocation getRandomTexture(UUID uuid) {
        return TEXTURES[(int) (Math.abs(~(uuid.getLeastSignificantBits() ^ (uuid.getMostSignificantBits() | 0xFF00FFFFFFFFFFL))) % SIZE)];
    }
}
