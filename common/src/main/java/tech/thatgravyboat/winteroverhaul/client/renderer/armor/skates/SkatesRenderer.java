package tech.thatgravyboat.winteroverhaul.client.renderer.armor.skates;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;
import net.minecraft.world.item.component.DyedItemColor;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.processing.AnimationState;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.constant.dataticket.DataTicket;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;
import tech.thatgravyboat.winteroverhaul.WinterOverhaul;
import tech.thatgravyboat.winteroverhaul.common.items.SkateItem;

public class SkatesRenderer<S extends HumanoidRenderState & GeoRenderState> extends GeoArmorRenderer<SkateItem, S> {
    private static final DataTicket<Integer> DYED_COLOR = DataTicket.create("dyed_color", Integer.class);

    public SkatesRenderer() {
        super(new SkatesModel());
    }

    @Override
    protected void grabRelevantBones(BakedGeoModel bakedModel) {
        super.grabRelevantBones(bakedModel);

        this.rightLegBone = model.getBone("rightBoot").orElse(null);
        this.leftLegBone = model.getBone("leftBoot").orElse(null);
        this.rightBootBone = model.getBone("rightBoot").orElse(null);
        this.leftBootBone = model.getBone("leftBoot").orElse(null);
    }

    @Override
    public void renderFinal(S renderState, PoseStack poseStack, BakedGeoModel model, MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, int packedLight, int packedOverlay, int renderColor) {
        renderWithColor(0,  poseStack, buffer, packedLight, renderColor, renderState, model);
    }

    @Override
    public void addRenderData(SkateItem animatable, RenderData relatedObject, S renderState) {
        renderState.addGeckolibData(DYED_COLOR, DyedItemColor.getOrDefault(relatedObject.itemStack(), DyedItemColor.LEATHER_COLOR));
    }

    public void renderWithColor(float partialTicks, PoseStack stack, VertexConsumer bufferIn, int packedLightIn, int color, S renderState, BakedGeoModel model) {
        stack.pushPose();
        var bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
        { // base
            color = renderState.getGeckolibData(DYED_COLOR);

            ResourceLocation texture = WinterOverhaul.id("textures/entity/skates/base.png");
            RenderType renderType = getRenderType(renderState, texture);
            this.reRender(renderState, stack, model, bufferSource, renderType, bufferSource.getBuffer(renderType), packedLightIn, OverlayTexture.NO_OVERLAY, color);
        }
        { // overlay
            ResourceLocation texture = WinterOverhaul.id("textures/entity/skates/overlay.png");
            RenderType renderType = getRenderType(renderState, texture);
            this.reRender(renderState, stack, model, bufferSource, renderType, bufferSource.getBuffer(renderType), packedLightIn, OverlayTexture.NO_OVERLAY, ARGB.color(ARGB.alpha(color), 255, 255, 255));
        }
        //if (ModList.get().isLoaded("patchouli")) PatchouliCompat.patchouliLoaded(stack);
        stack.popPose();
    }
}
