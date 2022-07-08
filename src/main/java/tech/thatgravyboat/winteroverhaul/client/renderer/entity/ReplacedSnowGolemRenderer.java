package tech.thatgravyboat.winteroverhaul.client.renderer.entity;

import com.mojang.authlib.minecraft.client.MinecraftClient;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoReplacedEntityRenderer;
import software.bernie.geckolib3.util.RenderUtils;
import tech.thatgravyboat.winteroverhaul.client.renderer.entity.layers.*;
import tech.thatgravyboat.winteroverhaul.client.renderer.entity.model.ReplacedSnowGolemModel;
import tech.thatgravyboat.winteroverhaul.common.entity.ReplacedSnowGolem;

@SuppressWarnings({"unchecked", "rawtypes"})
public class ReplacedSnowGolemRenderer extends GeoReplacedEntityRenderer<ReplacedSnowGolem> {

    private boolean hasPumpkin = false;
    private MultiBufferSource source;

    public ReplacedSnowGolemRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ReplacedSnowGolemModel(), new ReplacedSnowGolem());
        addLayer(new RandomButtonLayer(this));
        addLayer(new RandomFaceLayer(this));
        addLayer(new RandomArmLayer(this));
        addLayer(new UpgradeLayer(this));
        //addLayer(new PumpkinLayer(this));
    }

    @Override
    public void renderEarly(Object animatable, PoseStack stackIn, float partialTicks, @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.hasPumpkin = animatable instanceof SnowGolem golem && golem.hasPumpkin();
        this.source = renderTypeBuffer;
        super.renderEarly(animatable, stackIn, partialTicks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    @Override
    public void renderRecursively(GeoBone bone, PoseStack stack, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (bone.getName().equals("head") && hasPumpkin && this.source != null) {
            stack.pushPose();
            RenderUtils.translate(bone, stack);
            RenderUtils.moveToPivot(bone, stack);
            RenderUtils.rotate(bone, stack);
            RenderUtils.scale(bone, stack);
            RenderUtils.moveBackFromPivot(bone, stack);

            //stack.multiply(Vec3f.NEGATIVE_Z.getDegreesQuaternion(90));
            stack.translate(0, 1.6, 0);
            stack.scale(0.58F, 0.58F, 0.58F);

            Minecraft.getInstance().getItemRenderer()
                    .renderStatic(new ItemStack(Blocks.CARVED_PUMPKIN), ItemTransforms.TransformType.HEAD, packedLightIn, packedOverlayIn, stack, this.source, 0);
            stack.popPose();

            bufferIn = this.source.getBuffer(RenderType.entityTranslucent(getTextureLocation(null)));
        }
        super.renderRecursively(bone, stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    @Override
    public RenderType getRenderType(Object animatable, float partialTicks, PoseStack stack, @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(textureLocation);
    }
}
