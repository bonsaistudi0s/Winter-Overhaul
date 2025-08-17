package tech.thatgravyboat.winteroverhaul.client.renderer.entity;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.SnowGolem;
import software.bernie.geckolib.renderer.GeoReplacedEntityRenderer;
import tech.thatgravyboat.winteroverhaul.client.renderer.entity.layers.*;
import tech.thatgravyboat.winteroverhaul.client.renderer.entity.model.ReplacedSnowGolemModel;
import tech.thatgravyboat.winteroverhaul.common.entity.ReplacedSnowGolem;

public class ReplacedSnowGolemRenderer extends GeoReplacedEntityRenderer<SnowGolem, ReplacedSnowGolem> {

    public ReplacedSnowGolemRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ReplacedSnowGolemModel<>(), new ReplacedSnowGolem());
        addRenderLayer(new RandomButtonLayer(this));
        addRenderLayer(new RandomFaceLayer(this));
        addRenderLayer(new RandomArmLayer(this));
        addRenderLayer(new UpgradeLayer(this));
        addRenderLayer(new PumpkinLayer(this));
    }

    @Override
    public RenderType getRenderType(ReplacedSnowGolem animatable, ResourceLocation textureLocation, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(textureLocation);
    }
}
