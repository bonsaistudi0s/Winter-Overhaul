package tech.thatgravyboat.winteroverhaul.forge.items;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import tech.thatgravyboat.winteroverhaul.WinterOverhaul;
import tech.thatgravyboat.winteroverhaul.client.renderer.armor.skates.SkatesRenderer;
import tech.thatgravyboat.winteroverhaul.common.items.SkateItem;

import java.util.Objects;
import java.util.function.Consumer;

public class SkateItemForge extends SkateItem {
    public SkateItemForge(Properties builder) {
        super(builder);
    }

    public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer) {
        super.initializeClient(consumer);
        consumer.accept(new IClientItemExtensions() {
            private GeoArmorRenderer<?> renderer;

            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                if (this.renderer == null)
                    this.renderer = new SkatesRenderer();

                this.renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);
                return this.renderer;
            }
        });
    }

    @Override
    public final String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        ResourceLocation normal = WinterOverhaul.id("textures/entity/skates/base.png");
        ResourceLocation overlay = WinterOverhaul.id("textures/entity/skates/overlay.png");
        return Objects.equals(type, "overlay") ? overlay.toString() : normal.toString();
    }
}
