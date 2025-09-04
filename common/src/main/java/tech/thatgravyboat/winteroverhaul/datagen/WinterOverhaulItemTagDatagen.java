package tech.thatgravyboat.winteroverhaul.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import tech.thatgravyboat.winteroverhaul.common.registry.ModItems;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class WinterOverhaulItemTagDatagen extends ItemTagsProvider {
    public WinterOverhaulItemTagDatagen(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, CompletableFuture.completedFuture(blockTagKey -> Optional.empty()));
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(ItemTags.DYEABLE)
            .add(ModItems.SKATES.get());
    }
}
