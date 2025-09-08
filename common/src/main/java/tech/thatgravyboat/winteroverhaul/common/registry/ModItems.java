package tech.thatgravyboat.winteroverhaul.common.registry;

import dev.architectury.core.item.ArchitecturySpawnEggItem;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import tech.thatgravyboat.winteroverhaul.WinterOverhaul;
import tech.thatgravyboat.winteroverhaul.common.items.GolemUpgradeItem;
import tech.thatgravyboat.winteroverhaul.common.items.GolemUpgradeSlot;
import tech.thatgravyboat.winteroverhaul.common.items.SkateItem;

import java.util.function.Function;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(WinterOverhaul.MODID, Registries.ITEM);

    public static final RegistrySupplier<Item> YELLOW_SCARF = register("yellow_scarf", key -> new GolemUpgradeItem(GolemUpgradeSlot.SCARF, createProps(key)));
    public static final RegistrySupplier<Item> RED_SCARF = register("red_scarf", key -> new GolemUpgradeItem(GolemUpgradeSlot.SCARF, createProps(key)));
    public static final RegistrySupplier<Item> CYAN_SCARF = register("cyan_scarf", key -> new GolemUpgradeItem(GolemUpgradeSlot.SCARF, createProps(key)));
    public static final RegistrySupplier<Item> GREEN_SCARF = register("green_scarf", key -> new GolemUpgradeItem(GolemUpgradeSlot.SCARF, createProps(key)));

    public static final RegistrySupplier<Item> YELLOW_HAT = register("yellow_winter_hat", key -> new GolemUpgradeItem(GolemUpgradeSlot.HAT, createProps(key)));
    public static final RegistrySupplier<Item> RED_HAT = register("red_winter_hat", key -> new GolemUpgradeItem(GolemUpgradeSlot.HAT, createProps(key)));
    public static final RegistrySupplier<Item> CYAN_HAT = register("cyan_winter_hat", key -> new GolemUpgradeItem(GolemUpgradeSlot.HAT, createProps(key)));
    public static final RegistrySupplier<Item> GREEN_HAT = register("green_winter_hat", key -> new GolemUpgradeItem(GolemUpgradeSlot.HAT, createProps(key)));

    public static final RegistrySupplier<Item> TOP_HAT = register("top_hat", key -> new GolemUpgradeItem(GolemUpgradeSlot.HAT, createProps(key)));

    public static final RegistrySupplier<Item> ROBIN_SPAWN_EGG = register("robin_spawn_egg", key ->
            new ArchitecturySpawnEggItem(ModEntities.ROBIN, createProps(key)));

    public static final RegistrySupplier<Item> SKATES = register("skates", key -> new SkateItem(createProps(key)));

    public static final DeferredRegister<CreativeModeTab> TAB_REGISTER = DeferredRegister.create(WinterOverhaul.MODID, Registries.CREATIVE_MODE_TAB);
    public static final RegistrySupplier<CreativeModeTab> TAB = TAB_REGISTER.register("tab", () -> CreativeTabRegistry.create(builder -> builder
        .displayItems((params, output) -> ModItems.registerToCreativeTab(output))
        .title(Component.literal("Winter Overhaul"))
        .icon(() -> new ItemStack(ModItems.TOP_HAT.get()))
        .build()));

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

    private static Item.Properties createProps(ResourceKey<Item> key) {
        return new Item.Properties().setId(key);
    }

    private static <T extends Item> RegistrySupplier<T> register(String id, Function<ResourceKey<Item>, T> builderConsumer) {
        return ITEMS.register(id, () -> builderConsumer.apply(ResourceKey.create(Registries.ITEM, WinterOverhaul.id(id))));
    }
}
