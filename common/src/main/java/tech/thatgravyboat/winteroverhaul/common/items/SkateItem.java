package tech.thatgravyboat.winteroverhaul.common.items;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.ArmorMaterials;
import net.minecraft.world.item.equipment.ArmorType;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;
import tech.thatgravyboat.winteroverhaul.client.renderer.armor.skates.SkatesRenderer;

import java.util.function.Consumer;

public class SkateItem extends Item implements GeoItem {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public SkateItem(Properties builder) {
        super(builder.humanoidArmor(ArmorMaterials.LEATHER, ArmorType.BOOTS));
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            private GeoArmorRenderer<?, ?> renderer;

            @Override
            public @Nullable <S extends HumanoidRenderState> GeoArmorRenderer<?, ?> getGeoArmorRenderer(@Nullable S renderState, ItemStack itemStack, EquipmentSlot equipmentSlot, EquipmentClientInfo.LayerType type, @Nullable HumanoidModel<S> original) {
                if (this.renderer == null)
                    this.renderer = new SkatesRenderer();

                //this.renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);
                return this.renderer;
            }
        });
    }
}
