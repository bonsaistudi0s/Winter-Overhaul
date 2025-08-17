package tech.thatgravyboat.winteroverhaul.common.util;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class BiomeSpawns {
    private final List<SpawnData> spawns = new ArrayList<>();

    public void addSpawn(Predicate<Holder<Biome>> predicate, MobCategory category, MobSpawnSettings.SpawnerData spawnerData) {
        spawns.add(new SpawnData(predicate, category, spawnerData));
    }

    public List<SpawnData> getSpawns() {
        return spawns;
    }

    public record SpawnData(
        Predicate<Holder<Biome>> selector,
        MobCategory category,
        MobSpawnSettings.SpawnerData spawnerData
    ) {}
}
