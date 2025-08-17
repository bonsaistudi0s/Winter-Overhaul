package tech.thatgravyboat.winteroverhaul.datagen;

import dev.architectury.injectables.annotations.PlatformOnly;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import tech.thatgravyboat.winteroverhaul.common.registry.ModEntities;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class WinterOverhaulLootTableDatagen extends LootTableProvider {
    public WinterOverhaulLootTableDatagen(PackOutput output) {
        super(output, Set.of(), List.of(
            new SubProviderEntry(WinterOverhaulEntityLootDatagen::new, LootContextParamSets.ALL_PARAMS)
        ));
    }

    public static class WinterOverhaulEntityLootDatagen extends EntityLootSubProvider implements KnownEntityTypeProvider {
        public WinterOverhaulEntityLootDatagen() {
            super(FeatureFlags.DEFAULT_FLAGS);
        }

        @Override
        public void generate() {
            this.add(ModEntities.ROBIN.get(), LootTable.lootTable()
                .withPool(
                    LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1f))
                        .setBonusRolls(ConstantValue.exactly(0f))
                        .add(
                            LootItem.lootTableItem(Items.FEATHER)
                                .apply(
                                    SetItemCountFunction.setCount(UniformGenerator.between(0f, 2f))
                                )
                                .apply(
                                    LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0f, 1f))
                                )
                        )
                )
            );
        }

        @PlatformOnly("fabric")
        public Stream<Holder.Reference<EntityType<?>>> winteroverhaul$getKnownEntityTypes() {
            return Stream.of(ModEntities.ROBIN.get().builtInRegistryHolder());
        }

        @PlatformOnly("forge")
        public Stream<EntityType<?>> getKnownEntityTypes() {
            return Stream.of(ModEntities.ROBIN.get());
        }
    }
}
