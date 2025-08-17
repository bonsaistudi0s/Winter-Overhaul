package tech.thatgravyboat.winteroverhaul.common.registry.forge;

import net.minecraft.world.item.Item;
import tech.thatgravyboat.winteroverhaul.common.items.GolemUpgradeItem;
import tech.thatgravyboat.winteroverhaul.common.items.GolemUpgradeSlot;
import tech.thatgravyboat.winteroverhaul.common.items.SkateItem;
import tech.thatgravyboat.winteroverhaul.forge.items.GolemUpgradeItemForge;
import tech.thatgravyboat.winteroverhaul.forge.items.SkateItemForge;

public class ModItemsImpl {
    public static GolemUpgradeItem createGolemUpgradeItem(GolemUpgradeSlot slot, Item.Properties pProperties) {
        return new GolemUpgradeItemForge(slot, pProperties);
    }

    public static SkateItem createSkateItem(Item.Properties properties) {
        return new SkateItemForge(properties);
    }
}
