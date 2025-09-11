package tech.thatgravyboat.winteroverhaul.neoforge;

import net.minecraft.data.DataProvider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import tech.thatgravyboat.winteroverhaul.client.ModClient;
import tech.thatgravyboat.winteroverhaul.common.util.EntityAttributesBuilder;
import tech.thatgravyboat.winteroverhaul.datagen.WinterOverhaulItemInfoDatagen;
import tech.thatgravyboat.winteroverhaul.datagen.WinterOverhaulItemTagDatagen;
import tech.thatgravyboat.winteroverhaul.datagen.WinterOverhaulLootTableDatagen;
import tech.thatgravyboat.winteroverhaul.datagen.WinterOverhaulRecipeDatagen;

// This shouldn't even need to be a separate file, but oh well, NeoForge shenanigans.
public class WinterOverhaulModBusEvents {
    @SubscribeEvent
    public static void addAttributes(EntityAttributeCreationEvent event) {
        var builder = new EntityAttributesBuilder();
        WinterOverhaulNeoForge.MOD.addAttributes(builder);

        builder.getAttributeSupplierMap().forEach(event::put);
    }

    @SubscribeEvent
    public static void onComplete(FMLLoadCompleteEvent event) {
        WinterOverhaulNeoForge.MOD.onComplete();
    }

    @SubscribeEvent
    public static void onClientSetup(EntityRenderersEvent.RegisterRenderers event) {
        ModClient.setupEntityRenderers();
    }

    @SubscribeEvent
    public static void onRegisterParticles(RegisterParticleProvidersEvent event) {
        ModClient.setupParticles();
    }

//    @SubscribeEvent
//    public static void onItemColors(RegisterColorHandlersEvent.Item event) {
//        ModClient.onItemColors();
//    }

    @SubscribeEvent
    public static void onDataGeneration(GatherDataEvent.Server event) {
        event.getGenerator().addProvider(true, (DataProvider.Factory<WinterOverhaulRecipeDatagen.Runner>) output -> new WinterOverhaulRecipeDatagen.Runner(output, event.getLookupProvider()));
        event.getGenerator().addProvider(true, (DataProvider.Factory<WinterOverhaulLootTableDatagen>) output -> new WinterOverhaulLootTableDatagen(output, event.getLookupProvider()));
        event.getGenerator().addProvider(true, (DataProvider.Factory<WinterOverhaulItemTagDatagen>) output -> new WinterOverhaulItemTagDatagen(output, event.getLookupProvider()));
        event.getGenerator().addProvider(true, (DataProvider.Factory<WinterOverhaulItemInfoDatagen>) WinterOverhaulItemInfoDatagen::new);
    }
}
