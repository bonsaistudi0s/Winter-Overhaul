package tech.thatgravyboat.winteroverhaul.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.constant.dataticket.DataTicket;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;
import tech.thatgravyboat.winteroverhaul.client.renderer.entity.model.RobinModel;
import tech.thatgravyboat.winteroverhaul.common.entity.Robin;

public class RobinRenderer<S extends LivingEntityRenderState & GeoRenderState> extends GeoEntityRenderer<Robin, S> {
    public RobinRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new RobinModel());
    }

    @Override
    public void preRender(S renderState, PoseStack poseStack, BakedGeoModel model, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, boolean isReRender, int packedLight, int packedOverlay, int renderColor) {
        if (renderState.isBaby)
            poseStack.scale(0.5f, 0.5f, 0.5f);

        super.preRender(renderState, poseStack, model, bufferSource, buffer, isReRender, packedLight, packedOverlay, renderColor);
    }

    @Override
    public @Nullable RenderType getRenderType(S renderState, ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(textureLocation);
    }
}
