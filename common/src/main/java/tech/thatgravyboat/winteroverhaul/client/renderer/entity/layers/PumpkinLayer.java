package tech.thatgravyboat.winteroverhaul.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import tech.thatgravyboat.winteroverhaul.client.renderer.entity.ReplacedSnowGolemRenderer;
import tech.thatgravyboat.winteroverhaul.common.entity.ReplacedSnowGolem;

public class PumpkinLayer extends GeoRenderLayer<ReplacedSnowGolem> {
    private final ReplacedSnowGolemRenderer renderer;

    public PumpkinLayer(ReplacedSnowGolemRenderer entityRendererIn) {
        super(entityRendererIn);
        this.renderer = entityRendererIn;
    }


    @Override
    public void renderForBone(PoseStack stack, ReplacedSnowGolem animatable, GeoBone bone, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        SnowGolem golem = this.renderer.getCurrentEntity();
        if (golem.hasPumpkin() && bone.getName().equals("head")) {
            Minecraft minecraft = Minecraft.getInstance();
            boolean flag = minecraft.shouldEntityAppearGlowing(golem) && golem.isInvisible();
            if (!golem.isInvisible() || flag) {
                stack.pushPose();
                stack.translate(0.0D, 1.6D, 0.0D);
                stack.mulPose(Axis.YP.rotationDegrees(180.0F));
                stack.mulPose(Axis.ZP.rotationDegrees(180.0F));
                stack.scale(0.6F, -0.6F, -0.6F);
                if (flag) {
                    BlockState blockstate = Blocks.CARVED_PUMPKIN.defaultBlockState();
                    BlockRenderDispatcher blockrenderdispatcher = minecraft.getBlockRenderer();
                    BakedModel bakedmodel = blockrenderdispatcher.getBlockModel(blockstate);
                    int i = LivingEntityRenderer.getOverlayCoords(golem, 0.0F);
                    stack.translate(-0.5D, -0.5D, -0.5D);
                    blockrenderdispatcher.getModelRenderer().renderModel(stack.last(), bufferSource.getBuffer(RenderType.outline(TextureAtlas.LOCATION_BLOCKS)), blockstate, bakedmodel, 0.0F, 0.0F, 0.0F, packedLight, i);
                } else {
                    minecraft.getItemRenderer().renderStatic(golem, new ItemStack(Blocks.CARVED_PUMPKIN), ItemDisplayContext.HEAD, false, stack, bufferSource, golem.level(), packedLight, LivingEntityRenderer.getOverlayCoords(golem, 0.0F), golem.getId());
                }

                stack.popPose();
            }
        }
    }
}