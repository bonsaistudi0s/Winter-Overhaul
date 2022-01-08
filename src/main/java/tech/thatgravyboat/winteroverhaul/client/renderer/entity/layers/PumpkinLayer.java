package tech.thatgravyboat.winteroverhaul.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;
import software.bernie.geckolib3.util.AnimationUtils;
import software.bernie.geckolib3.util.RenderUtils;

@SuppressWarnings({"unchecked", "rawtypes"})
public class PumpkinLayer extends GeoLayerRenderer {

    public PumpkinLayer(IGeoRenderer entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack stack, MultiBufferSource buffer, int packedLight, Entity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity instanceof SnowGolem golem) {
            if (golem.hasPumpkin()) {
                Minecraft minecraft = Minecraft.getInstance();
                boolean flag = minecraft.shouldEntityAppearGlowing(golem) && golem.isInvisible();
                if (!golem.isInvisible() || flag) {
                    stack.pushPose();
                    stack.translate(0.0D, 1.65625D, 0.0D);
                    stack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
                    stack.mulPose(Vector3f.ZP.rotationDegrees(180.0F));
                    stack.scale(0.6875F, -0.6875F, -0.6875F);
                    if (flag) {
                        BlockState blockstate = Blocks.CARVED_PUMPKIN.defaultBlockState();
                        BlockRenderDispatcher blockrenderdispatcher = minecraft.getBlockRenderer();
                        BakedModel bakedmodel = blockrenderdispatcher.getBlockModel(blockstate);
                        int i = LivingEntityRenderer.getOverlayCoords(golem, 0.0F);
                        stack.translate(-0.5D, -0.5D, -0.5D);
                        blockrenderdispatcher.getModelRenderer().renderModel(stack.last(), buffer.getBuffer(RenderType.outline(TextureAtlas.LOCATION_BLOCKS)), blockstate, bakedmodel, 0.0F, 0.0F, 0.0F, packedLight, i);
                    } else {
                        minecraft.getItemRenderer().renderStatic(golem, new ItemStack(Blocks.CARVED_PUMPKIN), ItemTransforms.TransformType.HEAD, false, stack, buffer, golem.level, packedLight, LivingEntityRenderer.getOverlayCoords(golem, 0.0F), golem.getId());
                    }

                    stack.popPose();
                }
            }
        }
    }
}
