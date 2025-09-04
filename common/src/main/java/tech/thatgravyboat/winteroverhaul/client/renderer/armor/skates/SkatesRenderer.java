package tech.thatgravyboat.winteroverhaul.client.renderer.armor.skates;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.item.component.DyedItemColor;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import tech.thatgravyboat.winteroverhaul.WinterOverhaul;
import tech.thatgravyboat.winteroverhaul.common.items.SkateItem;

import java.util.Arrays;

public class SkatesRenderer extends GeoArmorRenderer<SkateItem> {

    public SkatesRenderer() {
        super(new SkatesModel());
    }

    @Override
    public GeoBone getRightLegBone(GeoModel<SkateItem> model) {
        return model.getBone("rightBoot").orElse(null);
    }

    @Override
    public GeoBone getLeftLegBone(GeoModel<SkateItem> model) {
        return model.getBone("leftBoot").orElse(null);
    }

    @Override
    public GeoBone getRightBootBone(GeoModel<SkateItem> model) {
        return model.getBone("rightBoot").orElse(null);
    }

    @Override
    public GeoBone getLeftBootBone(GeoModel<SkateItem> model) {
        return model.getBone("leftBoot").orElse(null);
    }

    @Override
    public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, int color) {
        renderWithColor(0,  matrixStackIn, bufferIn, packedLightIn, color);
    }

    public void renderWithColor(float partialTicks, PoseStack stack, VertexConsumer bufferIn, int packedLightIn, int color) {
        BakedGeoModel model = this.getGeoModel().getBakedModel(this.getGeoModel().getModelResource(this.animatable));

        AnimationState<SkateItem> itemState = new AnimationState<>(this.animatable, 0f, 0f, partialTicks, false);
        this.getGeoModel().setCustomAnimations(this.animatable, this.getInstanceId(animatable), itemState);
        this.applyBaseTransformations(this.baseModel);
        stack.pushPose();
        var bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
        { // base
            color = DyedItemColor.getOrDefault(this.getCurrentStack(), DyedItemColor.LEATHER_COLOR);

            ResourceLocation texture = WinterOverhaul.id("textures/entity/skates/base.png");
            RenderSystem.setShaderTexture(0, texture);
            RenderType renderType = getRenderType(animatable, texture, bufferSource, partialTicks);
            this.reRender(model, stack, bufferSource, this.animatable, renderType, bufferSource.getBuffer(renderType), partialTicks, packedLightIn, OverlayTexture.NO_OVERLAY, color);
        }
        { // overlay
            ResourceLocation texture = WinterOverhaul.id("textures/entity/skates/overlay.png");
            RenderSystem.setShaderTexture(0, texture);
            RenderType renderType = getRenderType(animatable, texture, bufferSource, partialTicks);
            this.reRender(model, stack, bufferSource, this.animatable, renderType, bufferSource.getBuffer(renderType), partialTicks, packedLightIn, OverlayTexture.NO_OVERLAY, FastColor.ARGB32.color(FastColor.ARGB32.alpha(color), 255, 255, 255));
        }
        //if (ModList.get().isLoaded("patchouli")) PatchouliCompat.patchouliLoaded(stack);
        stack.popPose();
    }
}
