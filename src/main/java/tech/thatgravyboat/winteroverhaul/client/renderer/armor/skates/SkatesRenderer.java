package tech.thatgravyboat.winteroverhaul.client.renderer.armor.skates;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraftforge.fml.ModList;
import software.bernie.geckolib3.compat.PatchouliCompat;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;
import tech.thatgravyboat.winteroverhaul.common.items.SkateItem;

import java.util.Arrays;

public class SkatesRenderer extends GeoArmorRenderer<SkateItem> {

    public SkatesRenderer() {
        super(new SkatesModel());

        this.headBone = null;
        this.bodyBone = null;
        this.rightArmBone = null;
        this.leftArmBone = null;
        this.rightLegBone = "rightBoot";
        this.leftLegBone = "leftBoot";
        this.rightBootBone = "rightBoot";
        this.leftBootBone = "leftBoot";
    }

    @Override
    public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        renderWithColor(0,  matrixStackIn, bufferIn, packedLightIn, red, green, blue, alpha);
    }

    public void renderWithColor(float partialTicks, PoseStack stack, VertexConsumer bufferIn, int packedLightIn, float red, float green, float blue, float alpha) {
        stack.translate(0.0D, 24 / 16F, 0.0D);
        stack.scale(-1.0F, -1.0F, 1.0F);
        GeoModel model = getGeoModelProvider().getModel(getGeoModelProvider().getModelResource(currentArmorItem));

        AnimationEvent<SkateItem> itemEvent = new AnimationEvent<>(this.currentArmorItem, 0, 0, 0, false,
                Arrays.asList(this.itemStack, this.entityLiving, this.armorSlot));
        getGeoModelProvider().setLivingAnimations(currentArmorItem, this.getUniqueID(this.currentArmorItem), itemEvent);
        this.fitToBiped();
        stack.pushPose();
        RenderSystem.setShaderTexture(0, getTextureLocation(currentArmorItem));
        RenderType renderType = getRenderType(currentArmorItem, partialTicks, stack, null, bufferIn, packedLightIn, getTextureLocation(currentArmorItem));
        render(model, currentArmorItem, partialTicks, renderType, stack, null, bufferIn, packedLightIn, OverlayTexture.NO_OVERLAY, red, green, blue, alpha);
        if (ModList.get().isLoaded("patchouli")) PatchouliCompat.patchouliLoaded(stack);
        stack.popPose();
        stack.scale(-1.0F, -1.0F, 1.0F);
        stack.translate(0.0D, -24 / 16F, 0.0D);
    }
}
