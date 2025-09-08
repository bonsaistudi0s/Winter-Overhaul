package tech.thatgravyboat.winteroverhaul.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.block.model.BlockStateModel;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.state.SnowGolemRenderState;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.constant.dataticket.DataTicket;
import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.renderer.base.PerBoneRender;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import tech.thatgravyboat.winteroverhaul.client.renderer.entity.ReplacedSnowGolemRenderer;
import tech.thatgravyboat.winteroverhaul.common.entity.ReplacedSnowGolem;

import java.util.function.BiConsumer;

public class PumpkinLayer<S extends SnowGolemRenderState & GeoRenderState> extends GeoRenderLayer<ReplacedSnowGolem, SnowGolem, S> {
    public PumpkinLayer(ReplacedSnowGolemRenderer entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void addPerBoneRender(S renderState, BakedGeoModel model, BiConsumer<GeoBone, PerBoneRender<S>> consumer) {
        super.addPerBoneRender(renderState, model, consumer);

        consumer.accept(model.getBone("head").orElseThrow(), (renderState1, stack, bone, renderType, bufferSource, packedLight, packedOverlay, renderColor) -> {
            if (renderState1.hasPumpkin) {
                Minecraft minecraft = Minecraft.getInstance();
                if (!renderState1.isInvisible || renderState1.appearsGlowing) {
                    stack.pushPose();
                    stack.translate(0.0D, 1.6D, 0.0D);
                    stack.mulPose(Axis.YP.rotationDegrees(180.0F));
                    stack.mulPose(Axis.ZP.rotationDegrees(180.0F));
                    stack.scale(0.6F, -0.6F, -0.6F);
                    BlockState blockstate = Blocks.CARVED_PUMPKIN.defaultBlockState();
                    BlockRenderDispatcher blockrenderdispatcher = minecraft.getBlockRenderer();
                    BlockStateModel bakedmodel = blockrenderdispatcher.getBlockModel(blockstate);
                    int i = LivingEntityRenderer.getOverlayCoords(renderState1, 0.0F);
                    stack.translate(-0.5D, -0.5D, -0.5D);
                    VertexConsumer vertexConsumer = renderState1.appearsGlowing && renderState1.isInvisible ? bufferSource.getBuffer(RenderType.outline(TextureAtlas.LOCATION_BLOCKS)) : bufferSource.getBuffer(ItemBlockRenderTypes.getRenderType(blockstate));
                    ModelBlockRenderer.renderModel(stack.last(), vertexConsumer, bakedmodel, 0.0F, 0.0F, 0.0F, packedLight, i);
                    stack.popPose();
                }
            }
        });
    }
}