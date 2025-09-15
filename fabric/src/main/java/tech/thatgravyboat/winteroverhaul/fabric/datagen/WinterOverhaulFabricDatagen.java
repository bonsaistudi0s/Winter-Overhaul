package tech.thatgravyboat.winteroverhaul.fabric.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.packs.VanillaEntityLoot;
import net.minecraft.world.entity.EntityType;
import tech.thatgravyboat.winteroverhaul.datagen.WinterOverhaulBlockTagDatagen;
import tech.thatgravyboat.winteroverhaul.datagen.WinterOverhaulLootTableDatagen;
import tech.thatgravyboat.winteroverhaul.datagen.WinterOverhaulRecipeDatagen;

public class WinterOverhaulFabricDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        var pack = generator.createPack();

        pack.addProvider((output, registries) -> new WinterOverhaulRecipeDatagen(output));
        pack.addProvider((output, registries) -> new WinterOverhaulLootTableDatagen(output));
        pack.addProvider((output, registries) -> new WinterOverhaulBlockTagDatagen(output, registries));
    }
}
