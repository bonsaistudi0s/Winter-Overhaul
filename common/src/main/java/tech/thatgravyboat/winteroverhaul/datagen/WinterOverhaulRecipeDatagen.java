package tech.thatgravyboat.winteroverhaul.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import tech.thatgravyboat.winteroverhaul.common.registry.ModItems;

import java.util.concurrent.CompletableFuture;

public class WinterOverhaulRecipeDatagen extends RecipeProvider {
    public WinterOverhaulRecipeDatagen(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    public void buildRecipes() {
        scarf(output, Items.CYAN_WOOL, ModItems.CYAN_SCARF.get());
        scarf(output, Items.GREEN_WOOL, ModItems.GREEN_SCARF.get());
        scarf(output, Items.RED_WOOL, ModItems.RED_SCARF.get());
        scarf(output, Items.YELLOW_WOOL, ModItems.YELLOW_SCARF.get());

        winterHat(output, Items.CYAN_WOOL, ModItems.CYAN_HAT.get());
        winterHat(output, Items.GREEN_WOOL, ModItems.GREEN_HAT.get());
        winterHat(output, Items.RED_WOOL, ModItems.RED_HAT.get());
        winterHat(output, Items.YELLOW_WOOL, ModItems.YELLOW_HAT.get());

        winterHat(output, Items.BLACK_WOOL, ModItems.TOP_HAT.get());

        ShapedRecipeBuilder.shaped(BuiltInRegistries.ITEM, RecipeCategory.MISC, ModItems.SKATES.get())
            .define('#', Items.LEATHER)
            .define('I', Items.IRON_INGOT)
            .unlockedBy("get_wool", has(Items.IRON_INGOT))
            .pattern("# #")
            .pattern("# #")
            .pattern("I I")
            .save(output);
    }

    private void scarf(RecipeOutput output, Item wool, Item result) {
        ShapedRecipeBuilder.shaped(BuiltInRegistries.ITEM, RecipeCategory.MISC, result)
            .unlockedBy("get_wool", has(ItemTags.WOOL))
            .define('#', wool)
            .pattern("###")
            .save(output);
    }

    private void winterHat(RecipeOutput output, Item wool, Item result) {
        ShapedRecipeBuilder.shaped(BuiltInRegistries.ITEM, RecipeCategory.MISC, result)
            .define('#', wool)
            .unlockedBy("get_wool", has(ItemTags.WOOL))
            .pattern("###")
            .pattern("# #")
            .save(output);
    }

    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
            super(packOutput, registries);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
            return new WinterOverhaulRecipeDatagen(registries, output);
        }

        @Override
        public String getName() {
            return "WinterOverhaul Recipes";
        }
    }
}
