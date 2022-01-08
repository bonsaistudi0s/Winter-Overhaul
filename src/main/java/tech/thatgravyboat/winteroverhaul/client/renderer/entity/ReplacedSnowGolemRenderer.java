package tech.thatgravyboat.winteroverhaul.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoReplacedEntityRenderer;
import tech.thatgravyboat.winteroverhaul.client.renderer.entity.layers.*;
import tech.thatgravyboat.winteroverhaul.client.renderer.entity.model.ReplacedSnowGolemModel;
import tech.thatgravyboat.winteroverhaul.common.entity.ReplacedSnowGolem;

@SuppressWarnings({"unchecked", "rawtypes"})
public class ReplacedSnowGolemRenderer extends GeoReplacedEntityRenderer<ReplacedSnowGolem> {

    public ReplacedSnowGolemRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ReplacedSnowGolemModel(), new ReplacedSnowGolem());
        addLayer(new RandomButtonLayer(this));
        addLayer(new RandomFaceLayer(this));
        addLayer(new RandomArmLayer(this));
        addLayer(new UpgradeLayer(this));
        addLayer(new PumpkinLayer(this));
    }

    @Override
    public RenderType getRenderType(Object animatable, float partialTicks, PoseStack stack, @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(textureLocation);
    }
}
