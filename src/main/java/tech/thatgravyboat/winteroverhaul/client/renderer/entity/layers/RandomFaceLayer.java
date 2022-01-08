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
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;
import tech.thatgravyboat.winteroverhaul.WinterOverhaul;

import java.util.UUID;

@SuppressWarnings({"unchecked", "rawtypes"})
public class RandomFaceLayer extends GeoLayerRenderer {

    private static final int SIZE = 12;

    private static final ResourceLocation[] TEXTURES = Util.make(() -> {
        ResourceLocation[] textures = new ResourceLocation[SIZE];
        for (int i = 0; i < SIZE; i++) {
            textures[i] = new ResourceLocation(WinterOverhaul.MODID, "textures/entity/faces/snow_golem_face_"+(i+1)+".png");
        }
        return textures;
    });

    public RandomFaceLayer(IGeoRenderer entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack stack, MultiBufferSource buffer, int packedLightIn, Entity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity.isInvisible()) return;
        if (entity instanceof SnowGolem snowGolem && snowGolem.hasPumpkin()) return;
        GeoModel normalModel = this.getEntityModel().getModel(this.getEntityModel().getModelLocation(null));
        VertexConsumer consumer = buffer.getBuffer(RenderType.entityTranslucent(getRandomTexture(entity.getUUID())));
        getRenderer().render(normalModel, entity, partialTicks,
                null, stack, null, consumer,
                packedLightIn, OverlayTexture.NO_OVERLAY,
                1f, 1f, 1f, 1f);
    }

    private ResourceLocation getRandomTexture(UUID uuid) {
        return TEXTURES[(int) (Math.abs(~(uuid.getLeastSignificantBits() ^ (uuid.getMostSignificantBits() | 0xFF00FFFFFFFFFFL))) % SIZE)];
    }
}