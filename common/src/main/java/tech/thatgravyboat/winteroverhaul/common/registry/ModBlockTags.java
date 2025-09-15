package tech.thatgravyboat.winteroverhaul.common.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import tech.thatgravyboat.winteroverhaul.WinterOverhaul;

public class ModBlockTags {
    public static final TagKey<Block> ICE = TagKey.create(Registries.BLOCK, WinterOverhaul.id("ice"));
}
