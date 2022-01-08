package tech.thatgravyboat.winteroverhaul.common.registry;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import tech.thatgravyboat.winteroverhaul.WinterOverhaul;
import tech.thatgravyboat.winteroverhaul.common.items.GolemUpgradeItem;
import tech.thatgravyboat.winteroverhaul.common.items.GolemUpgradeSlot;
import tech.thatgravyboat.winteroverhaul.common.items.SkateItem;

public class ModItems {

    public static final CreativeModeTab TAB = new CreativeModeTab(WinterOverhaul.MODID) {
        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(TOP_HAT.get());
        }
    };


    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, WinterOverhaul.MODID);

    public static final RegistryObject<Item> YELLOW_SCARF = ITEMS.register("yellow_scarf", () -> new GolemUpgradeItem(GolemUpgradeSlot.SCARF, createProps()));
    public static final RegistryObject<Item> RED_SCARF = ITEMS.register("red_scarf", () -> new GolemUpgradeItem(GolemUpgradeSlot.SCARF, createProps()));
    public static final RegistryObject<Item> CYAN_SCARF = ITEMS.register("cyan_scarf", () -> new GolemUpgradeItem(GolemUpgradeSlot.SCARF, createProps()));
    public static final RegistryObject<Item> GREEN_SCARF = ITEMS.register("green_scarf", () -> new GolemUpgradeItem(GolemUpgradeSlot.SCARF, createProps()));


    public static final RegistryObject<Item> YELLOW_HAT = ITEMS.register("yellow_winter_hat", () -> new GolemUpgradeItem(GolemUpgradeSlot.HAT, createProps()));
    public static final RegistryObject<Item> RED_HAT = ITEMS.register("red_winter_hat", () -> new GolemUpgradeItem(GolemUpgradeSlot.HAT, createProps()));
    public static final RegistryObject<Item> CYAN_HAT = ITEMS.register("cyan_winter_hat", () -> new GolemUpgradeItem(GolemUpgradeSlot.HAT, createProps()));
    public static final RegistryObject<Item> GREEN_HAT = ITEMS.register("green_winter_hat", () -> new GolemUpgradeItem(GolemUpgradeSlot.HAT, createProps()));

    public static final RegistryObject<Item> TOP_HAT = ITEMS.register("top_hat", () -> new GolemUpgradeItem(GolemUpgradeSlot.HAT, createProps()));

    public static final RegistryObject<Item> ROBIN_SPAWN_EGG = ITEMS.register("robin_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.ROBIN, 0x57372F, 0xC96125, createProps()));

    public static final RegistryObject<Item> SKATES = ITEMS.register("skates", () -> new SkateItem(createProps()));


    private static Item.Properties createProps() {
        return new Item.Properties().tab(TAB);
    }

}
