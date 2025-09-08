package tech.thatgravyboat.winteroverhaul.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.data.tags.VanillaItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import tech.thatgravyboat.winteroverhaul.common.registry.ModItems;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class WinterOverhaulItemTagDatagen extends IntrinsicHolderTagsProvider<Item> {
    public WinterOverhaulItemTagDatagen(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, Registries.ITEM, lookupProvider, item -> item.builtInRegistryHolder().key());
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(ItemTags.DYEABLE)
            .add(ModItems.SKATES.get());
    }
}
