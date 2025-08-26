package tech.thatgravyboat.winteroverhaul.common.registry;

import dev.architectury.core.item.ArchitecturySpawnEggItem;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import tech.thatgravyboat.winteroverhaul.WinterOverhaul;
import tech.thatgravyboat.winteroverhaul.common.items.GolemUpgradeItem;
import tech.thatgravyboat.winteroverhaul.common.items.GolemUpgradeSlot;
import tech.thatgravyboat.winteroverhaul.common.items.SkateItem;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(WinterOverhaul.MODID, Registries.ITEM);

    public static final RegistrySupplier<Item> YELLOW_SCARF = ITEMS.register("yellow_scarf", () -> createGolemUpgradeItem(GolemUpgradeSlot.SCARF, createProps()));
    public static final RegistrySupplier<Item> RED_SCARF = ITEMS.register("red_scarf", () -> createGolemUpgradeItem(GolemUpgradeSlot.SCARF, createProps()));
    public static final RegistrySupplier<Item> CYAN_SCARF = ITEMS.register("cyan_scarf", () -> createGolemUpgradeItem(GolemUpgradeSlot.SCARF, createProps()));
    public static final RegistrySupplier<Item> GREEN_SCARF = ITEMS.register("green_scarf", () -> createGolemUpgradeItem(GolemUpgradeSlot.SCARF, createProps()));

    public static final RegistrySupplier<Item> YELLOW_HAT = ITEMS.register("yellow_winter_hat", () -> createGolemUpgradeItem(GolemUpgradeSlot.HAT, createProps()));
    public static final RegistrySupplier<Item> RED_HAT = ITEMS.register("red_winter_hat", () -> createGolemUpgradeItem(GolemUpgradeSlot.HAT, createProps()));
    public static final RegistrySupplier<Item> CYAN_HAT = ITEMS.register("cyan_winter_hat", () -> createGolemUpgradeItem(GolemUpgradeSlot.HAT, createProps()));
    public static final RegistrySupplier<Item> GREEN_HAT = ITEMS.register("green_winter_hat", () -> createGolemUpgradeItem(GolemUpgradeSlot.HAT, createProps()));

    public static final RegistrySupplier<Item> TOP_HAT = ITEMS.register("top_hat", () -> createGolemUpgradeItem(GolemUpgradeSlot.HAT, createProps()));

    public static final RegistrySupplier<Item> ROBIN_SPAWN_EGG = ITEMS.register("robin_spawn_egg", () ->
            new ArchitecturySpawnEggItem(ModEntities.ROBIN, 0x57372F, 0xC96125, createProps()));

    public static final RegistrySupplier<Item> SKATES = ITEMS.register("skates", () -> createSkateItem(createProps()));

    // We require to do this in order to make rendering actually work correctly
    @ExpectPlatform
    private static GolemUpgradeItem createGolemUpgradeItem(GolemUpgradeSlot slot, Item.Properties pProperties) {
        throw new IllegalStateException("Unimplemented on current platform!");
    }

    @ExpectPlatform
    private static SkateItem createSkateItem(Item.Properties properties) {
        throw new IllegalStateException("Unimplemented on current platform!");
    }

    public static void registerToCreativeTab(CreativeModeTab.Output output) {
        output.accept(YELLOW_SCARF.get());
        output.accept(RED_SCARF.get());
        output.accept(CYAN_SCARF.get());
        output.accept(GREEN_SCARF.get());

        output.accept(YELLOW_HAT.get());
        output.accept(RED_HAT.get());
        output.accept(CYAN_HAT.get());
        output.accept(GREEN_HAT.get());

        output.accept(TOP_HAT.get());
        output.accept(ROBIN_SPAWN_EGG.get());
        output.accept(SKATES.get());
    }

    private static Item.Properties createProps() {
        return new Item.Properties();
    }

}
