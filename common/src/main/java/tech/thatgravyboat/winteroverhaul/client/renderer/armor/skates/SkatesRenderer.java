package tech.thatgravyboat.winteroverhaul.client.renderer.armor.skates;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import tech.thatgravyboat.winteroverhaul.WinterOverhaul;
import tech.thatgravyboat.winteroverhaul.common.items.SkateItem;

import java.util.Arrays;

public class SkatesRenderer extends GeoArmorRenderer<SkateItem> {

    public SkatesRenderer() {
        super(new SkatesModel());
    }

    @Override
    public GeoBone getRightLegBone() {
        return this.model.getBone("rightBoot").orElse(null);
    }

    @Override
    public GeoBone getLeftLegBone() {
        return this.model.getBone("leftBoot").orElse(null);
    }

    @Override
    public GeoBone getRightBootBone() {
        return this.model.getBone("rightBoot").orElse(null);
    }

    @Override
    public GeoBone getLeftBootBone() {
        return this.model.getBone("leftBoot").orElse(null);
    }

    @Override
    public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        renderWithColor(0,  matrixStackIn, bufferIn, packedLightIn, red, green, blue, alpha);
    }

    public void renderWithColor(float partialTicks, PoseStack stack, VertexConsumer bufferIn, int packedLightIn, float red, float green, float blue, float alpha) {
        BakedGeoModel model = this.getGeoModel().getBakedModel(this.getGeoModel().getModelResource(this.animatable));

        AnimationState<SkateItem> itemState = new AnimationState<>(this.animatable, 0f, 0f, partialTicks, false);
        this.getGeoModel().setCustomAnimations(this.animatable, this.getInstanceId(animatable), itemState);
        this.applyBaseTransformations(this.baseModel);
        stack.pushPose();
        var bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
        { // base
            int color = this.getAnimatable().getColor(this.getCurrentStack());

            red = (float) (color >> 16 & 255) / 255.0F;
            green = (float) (color >> 8 & 255) / 255.0F;
            blue = (float) (color & 255) / 255.0F;

            ResourceLocation texture = WinterOverhaul.id("textures/entity/skates/base.png");
            RenderSystem.setShaderTexture(0, texture);
            RenderType renderType = getRenderType(animatable, texture, bufferSource, partialTicks);
            this.reRender(model, stack, bufferSource, this.animatable, renderType, bufferSource.getBuffer(renderType), partialTicks, packedLightIn, OverlayTexture.NO_OVERLAY, red, green, blue, alpha);
        }
        { // overlay
            ResourceLocation texture = WinterOverhaul.id("textures/entity/skates/overlay.png");
            RenderSystem.setShaderTexture(0, texture);
            RenderType renderType = getRenderType(animatable, texture, bufferSource, partialTicks);
            this.reRender(model, stack, bufferSource, this.animatable, renderType, bufferSource.getBuffer(renderType), partialTicks, packedLightIn, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, alpha);
        }
        //if (ModList.get().isLoaded("patchouli")) PatchouliCompat.patchouliLoaded(stack);
        stack.popPose();
    }
}
