package tech.thatgravyboat.winteroverhaul.datagen;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.client.color.item.Dye;
import net.minecraft.client.renderer.item.BlockModelWrapper;
import net.minecraft.client.renderer.item.ClientItem;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.DyedItemColor;
import tech.thatgravyboat.winteroverhaul.common.registry.ModItems;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class WinterOverhaulItemInfoDatagen implements DataProvider {
    private final PackOutput.PathProvider pathProvider;

    public WinterOverhaulItemInfoDatagen(PackOutput output) {
        this.pathProvider = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "items");
    }

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        var itemInfos = new HashMap<Item, ClientItem>();

        for (RegistrySupplier<Item> item : ModItems.ITEMS) {
            itemInfos.put(item.get(), new ClientItem(new BlockModelWrapper.Unbaked(item.getId().withPrefix("item/"), List.of()), ClientItem.Properties.DEFAULT));
        }

        // skates are dyeable
        itemInfos.put(ModItems.SKATES.get(), new ClientItem(new BlockModelWrapper.Unbaked(ModItems.SKATES.getId().withPrefix("item/"), List.of(new Dye(DyedItemColor.LEATHER_COLOR))), ClientItem.Properties.DEFAULT));

        return DataProvider.saveAll(output, ClientItem.CODEC, item -> this.pathProvider.json(item.builtInRegistryHolder().key().location()), itemInfos);
    }

    @Override
    public String getName() {
        return "WinterOverhaul Item Info Provider";
    }
}
