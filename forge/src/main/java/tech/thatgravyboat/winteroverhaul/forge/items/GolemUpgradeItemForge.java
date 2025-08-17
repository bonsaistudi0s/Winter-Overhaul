package tech.thatgravyboat.winteroverhaul.forge.items;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import tech.thatgravyboat.winteroverhaul.client.renderer.armor.cosmetics.CosmeticsRenderer;
import tech.thatgravyboat.winteroverhaul.common.items.GolemUpgradeItem;
import tech.thatgravyboat.winteroverhaul.common.items.GolemUpgradeSlot;

import java.util.function.Consumer;

public class GolemUpgradeItemForge extends GolemUpgradeItem {
    public GolemUpgradeItemForge(GolemUpgradeSlot slot, Properties pProperties) {
        super(slot, pProperties);
    }

    @Override
    public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer) {
        super.initializeClient(consumer);
        consumer.accept(new IClientItemExtensions() {
            private GeoArmorRenderer<?> renderer;

            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                if (this.renderer == null)
                    this.renderer = new CosmeticsRenderer();

                this.renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);
                return this.renderer;
            }
        });
    }
}
