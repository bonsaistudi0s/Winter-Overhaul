package tech.thatgravyboat.winteroverhaul.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import tech.thatgravyboat.winteroverhaul.WinterOverhaul;
import tech.thatgravyboat.winteroverhaul.client.ModClient;
import tech.thatgravyboat.winteroverhaul.common.registry.ModItems;
import tech.thatgravyboat.winteroverhaul.common.util.BiomeSpawns;
import tech.thatgravyboat.winteroverhaul.common.util.EntityAttributesBuilder;
import tech.thatgravyboat.winteroverhaul.datagen.WinterOverhaulBlockTagDatagen;
import tech.thatgravyboat.winteroverhaul.datagen.WinterOverhaulLootTableDatagen;
import tech.thatgravyboat.winteroverhaul.datagen.WinterOverhaulRecipeDatagen;

@Mod(WinterOverhaul.MODID)
public class WinterOverhaulForge {
    public static final WinterOverhaul MOD = new WinterOverhaul();
    private static final DeferredRegister<CreativeModeTab> TAB_REGISTER = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, WinterOverhaul.MODID);
    public static final RegistryObject<CreativeModeTab> TAB = TAB_REGISTER.register("tab", () -> CreativeModeTab.builder()
        .displayItems((params, output) -> ModItems.registerToCreativeTab(output))
        .title(Component.literal("Winter Overhaul"))
        .icon(() -> new ItemStack(ModItems.TOP_HAT.get()))
        .build());

    private static final BiomeSpawns biomeSpawns = new BiomeSpawns();

    public WinterOverhaulForge(FMLJavaModLoadingContext context) {
        MinecraftForge.EVENT_BUS.register(this);
        var modBus = context.getModEventBus();
        modBus.register(WinterOverhaulForge.class);
        EventBuses.registerModEventBus(WinterOverhaul.MODID, modBus);
        MOD.register();
        TAB_REGISTER.register(modBus);
    }

    @SubscribeEvent
    public void onEntityRightClick(PlayerInteractEvent.EntityInteract event) {
        InteractionResult result = MOD.onEntityRightClick(event.getTarget(), event.getItemStack(), event.getEntity());
        if (result != InteractionResult.PASS) {
            event.setCancellationResult(result);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onEntitySpawn(MobSpawnEvent.PositionCheck event) {
        MOD.onEntitySpawn(event.getEntity(), event.getLevel());
    }

    @SubscribeEvent
    public void onAddSpawns(LevelEvent.PotentialSpawns potentialSpawns) {
        if (biomeSpawns.getSpawns().isEmpty())
            MOD.addSpawns(biomeSpawns);

        for (BiomeSpawns.SpawnData spawn : biomeSpawns.getSpawns()) {
            if (potentialSpawns.getMobCategory() == spawn.category() && spawn.selector().test(potentialSpawns.getLevel().getBiome(potentialSpawns.getPos()))) {
                potentialSpawns.addSpawnerData(spawn.spawnerData());
            }
        }
    }

    @SubscribeEvent
    public static void addAttributes(EntityAttributeCreationEvent event) {
        var builder = new EntityAttributesBuilder();
        MOD.addAttributes(builder);

        builder.getAttributeSupplierMap().forEach(event::put);
    }

    @SubscribeEvent
    public static void onComplete(FMLLoadCompleteEvent event) {
        MOD.onComplete();
    }

    @SubscribeEvent
    public void onMobDrops(LivingDropsEvent event) {
        MOD.onMobDrops(event.getEntity(), event.getDrops());
    }

    @SubscribeEvent
    public void onEntityDamage(LivingHurtEvent event) {
        if (MOD.onEntityDamage(event.getEntity(), event.getSource())) {
            event.setCanceled(true);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onClientSetup(EntityRenderersEvent.RegisterRenderers event) {
        ModClient.setupEntityRenderers();
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onRegisterParticles(RegisterParticleProvidersEvent event) {
        ModClient.setupParticles();
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onItemColors(RegisterColorHandlersEvent.Item event) {
        ModClient.onItemColors();
    }

    @SubscribeEvent
    public static void onDataGeneration(GatherDataEvent event) {
        event.getGenerator().addProvider(event.includeServer(), (DataProvider.Factory<WinterOverhaulRecipeDatagen>) WinterOverhaulRecipeDatagen::new);
        event.getGenerator().addProvider(event.includeServer(), (DataProvider.Factory<WinterOverhaulLootTableDatagen>) WinterOverhaulLootTableDatagen::new);
        event.getGenerator().addProvider(event.includeServer(), (DataProvider.Factory<WinterOverhaulBlockTagDatagen>) output -> new WinterOverhaulBlockTagDatagen(output, event.getLookupProvider()));
    }
}
