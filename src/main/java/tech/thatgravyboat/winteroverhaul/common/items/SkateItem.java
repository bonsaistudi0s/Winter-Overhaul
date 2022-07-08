package tech.thatgravyboat.winteroverhaul.common.items;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;
import tech.thatgravyboat.winteroverhaul.WinterOverhaul;

import java.util.Objects;
import java.util.function.Consumer;

public class SkateItem extends ArmorItem implements IAnimatable, DyeableLeatherItem {

    private final AnimationFactory factory = new AnimationFactory(this);

    public SkateItem(Properties builder) {
        super(ArmorMaterials.LEATHER, EquipmentSlot.FEET, builder);
    }

    @Override
    public void registerControllers(AnimationData data) {

    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }



    @Override
    public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer) {
        super.initializeClient(consumer);
        consumer.accept(new IClientItemExtensions() {

            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                return (HumanoidModel<?>) GeoArmorRenderer.getRenderer(SkateItem.this.getClass(), livingEntity)
                        .applyEntityStats(original)
                        .applySlot(equipmentSlot).setCurrentItem(livingEntity, itemStack, equipmentSlot);
            }
        });
    }

    @Override
    public final String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        ResourceLocation normal = new ResourceLocation(WinterOverhaul.MODID, "textures/entity/skates/base.png");
        ResourceLocation overlay = new ResourceLocation(WinterOverhaul.MODID, "textures/entity/skates/overlay.png");
        return Objects.equals(type, "overlay") ? overlay.toString() : normal.toString();
    }

}
