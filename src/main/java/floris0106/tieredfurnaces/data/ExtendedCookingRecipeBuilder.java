package floris0106.tieredfurnaces.data;

import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import javax.annotation.ParametersAreNonnullByDefault;

import floris0106.tieredfurnaces.item.crafting.FiringRecipe;

@ParametersAreNonnullByDefault
public class ExtendedCookingRecipeBuilder extends SimpleCookingRecipeBuilder
{
	protected ExtendedCookingRecipeBuilder(RecipeCategory category, CookingBookCategory bookCategory, ItemLike result, Ingredient ingredient, float experience, int cookingTime, AbstractCookingRecipe.Factory<?> factory)
	{
		super(category, bookCategory, result, ingredient, experience, cookingTime, factory);
	}

	public static SimpleCookingRecipeBuilder firing(Ingredient ingredient, RecipeCategory category, ItemLike result, float experience, int cookingTime) {
		return new ExtendedCookingRecipeBuilder(category, determineFiringRecipeCategory(result), result, ingredient, experience, cookingTime, FiringRecipe::new);
	}

	private static CookingBookCategory determineFiringRecipeCategory(ItemLike result) {
		return result.asItem() instanceof BlockItem ? CookingBookCategory.BLOCKS : CookingBookCategory.MISC;
	}
}