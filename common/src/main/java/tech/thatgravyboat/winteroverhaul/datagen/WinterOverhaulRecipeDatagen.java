package tech.thatgravyboat.winteroverhaul.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.ShapedRecipe;
import tech.thatgravyboat.winteroverhaul.common.registry.ModItems;

import java.util.function.Consumer;

public class WinterOverhaulRecipeDatagen extends RecipeProvider {
    public WinterOverhaulRecipeDatagen(PackOutput output) {
        super(output);
    }

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> writer) {
        scarf(writer, Items.CYAN_WOOL, ModItems.CYAN_SCARF.get());
        scarf(writer, Items.GREEN_WOOL, ModItems.GREEN_SCARF.get());
        scarf(writer, Items.RED_WOOL, ModItems.RED_SCARF.get());
        scarf(writer, Items.YELLOW_WOOL, ModItems.YELLOW_SCARF.get());

        winterHat(writer, Items.CYAN_WOOL, ModItems.CYAN_HAT.get());
        winterHat(writer, Items.GREEN_WOOL, ModItems.GREEN_HAT.get());
        winterHat(writer, Items.RED_WOOL, ModItems.RED_HAT.get());
        winterHat(writer, Items.YELLOW_WOOL, ModItems.YELLOW_HAT.get());

        winterHat(writer, Items.BLACK_WOOL, ModItems.TOP_HAT.get());

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SKATES.get())
            .define('#', Items.LEATHER)
            .define('I', Items.IRON_INGOT)
            .unlockedBy("get_wool", has(Items.IRON_INGOT))
            .pattern("# #")
            .pattern("# #")
            .pattern("I I")
            .save(writer);
    }

    private void scarf(Consumer<FinishedRecipe> output, Item wool, Item result) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, result)
            .unlockedBy("get_wool", has(ItemTags.WOOL))
            .define('#', wool)
            .pattern("###")
            .save(output);
    }

    private void winterHat(Consumer<FinishedRecipe> output, Item wool, Item result) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, result)
            .define('#', wool)
            .unlockedBy("get_wool", has(ItemTags.WOOL))
            .pattern("###")
            .pattern("# #")
            .save(output);
    }
}
