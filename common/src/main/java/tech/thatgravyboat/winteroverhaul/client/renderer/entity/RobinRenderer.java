package tech.thatgravyboat.winteroverhaul.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import tech.thatgravyboat.winteroverhaul.client.renderer.entity.model.RobinModel;
import tech.thatgravyboat.winteroverhaul.common.entity.Robin;

public class RobinRenderer extends GeoEntityRenderer<Robin> {

    public RobinRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new RobinModel());
    }

    @Override
    public void render(Robin entity, float entityYaw, float partialTicks, @NotNull PoseStack stack, @NotNull MultiBufferSource bufferIn, int packedLightIn) {
        if (entity.isBaby())stack.scale(0.5f, 0.5f, 0.5f);
        super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
    }

    @Override
    public RenderType getRenderType(Robin animatable, ResourceLocation textureLocation, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(textureLocation);
    }
}
