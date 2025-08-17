package tech.thatgravyboat.winteroverhaul.common.entity;

import net.minecraft.world.item.ItemStack;
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
}
