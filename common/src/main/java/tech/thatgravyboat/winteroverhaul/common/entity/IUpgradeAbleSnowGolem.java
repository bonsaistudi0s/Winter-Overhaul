package tech.thatgravyboat.winteroverhaul.common.entity;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import tech.thatgravyboat.winteroverhaul.common.items.GolemUpgradeSlot;

public interface IUpgradeAbleSnowGolem {

    /**
     * @param slot the slot in which to set the upgrade to.
     * @param stack the upgrade to put into the slot.
     * @return returns the upgrade that was in the slot already.
     */
    ItemStack setGolemUpgradeInSlot(GolemUpgradeSlot slot, ItemStack stack);

    /**
     * @param slot the slot in which to get the upgrade.
     * @return returns the upgrade that is in the slot.
     */
    ItemStack getGolemUpgradeInSlot(GolemUpgradeSlot slot);

    /**
     * @return The list of upgrades on this snow golem.
     */
    NonNullList<ItemStack> winteroverhaul_getUpgrades();

    /**
     * @param upgrades
     */
    void winteroverhaul_setUpgrades(NonNullList<ItemStack> upgrades);

    /**
     * Updates the internal upgrades to all clients.
     */
    @ApiStatus.Internal
    default void winteroverhaul_updateUpgrades() {
        winteroverhaul_setUpgrades(winteroverhaul_getUpgrades());
    }
}
