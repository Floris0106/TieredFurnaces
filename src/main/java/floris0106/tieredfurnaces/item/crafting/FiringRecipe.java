package floris0106.tieredfurnaces.item.crafting;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

import floris0106.tieredfurnaces.FurnaceTier;
import floris0106.tieredfurnaces.TieredFurnaces;

@MethodsReturnNonnullByDefault
public class FiringRecipe extends AbstractCookingRecipe
{
	public FiringRecipe(String group, CookingBookCategory category, Ingredient ingredient, ItemStack result, float experience, int cookingTime)
	{
		super(TieredFurnaces.FIRING_RECIPE_TYPE.get(), group, category, ingredient, result, experience, cookingTime);
	}

	@Override
	public ItemStack getToastSymbol()
	{
		return new ItemStack(TieredFurnaces.TIERED_KILN_BLOCKS.get(FurnaceTier.BASE).get());
	}

	@Override
	public RecipeSerializer<?> getSerializer()
	{
		return TieredFurnaces.FIRING_RECIPE_SERIALIZER.get();
	}
}