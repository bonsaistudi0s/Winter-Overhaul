package tech.thatgravyboat.winteroverhaul.client.renderer.entity;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.SnowGolemRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.SnowGolem;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoReplacedEntityRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;
import tech.thatgravyboat.winteroverhaul.client.renderer.entity.layers.*;
import tech.thatgravyboat.winteroverhaul.client.renderer.entity.model.ReplacedSnowGolemModel;
import tech.thatgravyboat.winteroverhaul.common.entity.ReplacedSnowGolem;

public class ReplacedSnowGolemRenderer<S extends SnowGolemRenderState & GeoRenderState> extends GeoReplacedEntityRenderer<ReplacedSnowGolem, SnowGolem, S> {

    public ReplacedSnowGolemRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ReplacedSnowGolemModel<>(), new ReplacedSnowGolem());
        addRenderLayer(new RandomButtonLayer(this));
        addRenderLayer(new RandomFaceLayer(this));
        addRenderLayer(new RandomArmLayer(this));
        addRenderLayer(new UpgradeLayer(this));
        addRenderLayer(new PumpkinLayer(this));
    }

    @Override
    protected S createBaseRenderState(SnowGolem entity) {
        return (S) new SnowGolemRenderState();
    }

    @Override
    public void extractRenderState(SnowGolem entity, S renderState, float partialTick) {
        super.extractRenderState(entity, renderState, partialTick);
        renderState.hasPumpkin = entity.hasPumpkin();
    }

    @Override
    public void addRenderData(ReplacedSnowGolem animatable, SnowGolem relatedObject, S renderState) {
        renderState.addGeckolibData(ReplacedSnowGolem.IS_PASSENGER, relatedObject.isPassenger() && relatedObject.getVehicle() != null);
    }

    @Override
    public @Nullable RenderType getRenderType(S renderState, ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(textureLocation);
    }
}
