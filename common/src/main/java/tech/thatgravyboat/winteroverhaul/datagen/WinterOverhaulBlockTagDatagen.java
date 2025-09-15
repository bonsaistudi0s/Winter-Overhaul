package tech.thatgravyboat.winteroverhaul.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import tech.thatgravyboat.winteroverhaul.common.registry.ModBlockTags;

import java.util.concurrent.CompletableFuture;

public class WinterOverhaulBlockTagDatagen extends IntrinsicHolderTagsProvider<Block> {
    public WinterOverhaulBlockTagDatagen(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, Registries.BLOCK, lookupProvider, block -> block.builtInRegistryHolder().key());
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(ModBlockTags.ICE)
            .add(Blocks.FROSTED_ICE)
            .addOptionalTag(BlockTags.ICE.location());
    }
}
