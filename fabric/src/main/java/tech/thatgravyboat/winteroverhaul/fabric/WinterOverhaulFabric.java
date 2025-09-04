package tech.thatgravyboat.winteroverhaul.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import tech.thatgravyboat.winteroverhaul.WinterOverhaul;
import tech.thatgravyboat.winteroverhaul.common.registry.ModItems;
import tech.thatgravyboat.winteroverhaul.common.util.BiomeSpawns;
import tech.thatgravyboat.winteroverhaul.common.util.EntityAttributesBuilder;

public class WinterOverhaulFabric implements ModInitializer {
    public static final WinterOverhaul MOD = new WinterOverhaul();

    @Override
    public void onInitialize() {
        MOD.register();

        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) ->
            MOD.onEntityRightClick(entity, player.getItemInHand(hand), player));

        var attributes = new EntityAttributesBuilder();
        MOD.addAttributes(attributes);

        var spawns = new BiomeSpawns();
        MOD.addSpawns(spawns);

        attributes.getAttributeSupplierMap().forEach(FabricDefaultAttributeRegistry::register);

        int i = 0;
        for (BiomeSpawns.SpawnData spawn : spawns.getSpawns()) {
            BiomeModifications.create(WinterOverhaul.id("mob_drops_" + i++))
                .add(ModificationPhase.ADDITIONS, selection -> spawn.selector().test(selection.getBiomeRegistryEntry()), (selection, modification) -> {
                    modification.getSpawnSettings().addSpawn(spawn.category(), spawn.spawnerData());
                });
        }

        ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> {
            if (MOD.onEntityDamage(entity, source)) {
                return false;
            }

            return true;
        });

        MOD.onComplete();
    }
}
