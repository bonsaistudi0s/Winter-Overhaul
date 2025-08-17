package tech.thatgravyboat.winteroverhaul.common.registry.fabric;

import net.minecraft.world.item.Item;
import tech.thatgravyboat.winteroverhaul.common.items.GolemUpgradeItem;
import tech.thatgravyboat.winteroverhaul.common.items.GolemUpgradeSlot;
import tech.thatgravyboat.winteroverhaul.common.items.SkateItem;
import tech.thatgravyboat.winteroverhaul.fabric.common.items.GolemUpgradeItemFabric;
import tech.thatgravyboat.winteroverhaul.fabric.common.items.SkateItemFabric;

public class ModItemsImpl {
    public static GolemUpgradeItem createGolemUpgradeItem(GolemUpgradeSlot slot, Item.Properties pProperties) {
        return new GolemUpgradeItemFabric(slot, pProperties);
    }

    public static SkateItem createSkateItem(Item.Properties properties) {
        return new SkateItemFabric(properties);
    }
}
