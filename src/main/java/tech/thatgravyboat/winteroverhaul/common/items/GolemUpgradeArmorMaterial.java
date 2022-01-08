package tech.thatgravyboat.winteroverhaul.common.items;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

public class GolemUpgradeArmorMaterial implements ArmorMaterial {

    public static final GolemUpgradeArmorMaterial INSTANCE = new GolemUpgradeArmorMaterial();

    private GolemUpgradeArmorMaterial() {

    }

    @Override
    public int getDurabilityForSlot(@NotNull EquipmentSlot slot) {
        return 0;
    }

    @Override
    public int getDefenseForSlot(@NotNull EquipmentSlot slot) {
        return 0;
    }

    @Override
    public int getEnchantmentValue() {
        return 0;
    }

    @Override
    public @NotNull SoundEvent getEquipSound() {
        return SoundEvents.ARMOR_EQUIP_LEATHER;
    }

    @Override
    public @NotNull Ingredient getRepairIngredient() {
        return Ingredient.EMPTY;
    }

    @Override
    public @NotNull String getName() {
        return "golemupgrade";
    }

    @Override
    public float getToughness() {
        return 0;
    }

    @Override
    public float getKnockbackResistance() {
        return 0;
    }
}
