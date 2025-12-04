package floris0106.tieredfurnaces;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;

import java.util.List;
import java.util.function.Supplier;

public class EnumExtensions
{
	public static class RecipeBookType
	{
		public static final EnumProxy<net.minecraft.world.inventory.RecipeBookType> FIRING = new EnumProxy<>(net.minecraft.world.inventory.RecipeBookType.class);
	}

	public static class RecipeBookCategories
	{
		public static final EnumProxy<net.minecraft.client.RecipeBookCategories> KILN_SEARCH = new EnumProxy<>(
			net.minecraft.client.RecipeBookCategories.class, (Supplier<List<ItemStack>>) () -> List.of(new ItemStack(Items.COMPASS))
		);
		public static final EnumProxy<net.minecraft.client.RecipeBookCategories> KILN_BLOCKS = new EnumProxy<>(
			net.minecraft.client.RecipeBookCategories.class, (Supplier<List<ItemStack>>) () -> List.of(new ItemStack(Items.SMOOTH_STONE))
		);
		public static final EnumProxy<net.minecraft.client.RecipeBookCategories> KILN_MISC = new EnumProxy<>(
			net.minecraft.client.RecipeBookCategories.class, (Supplier<List<ItemStack>>) () -> List.of(new ItemStack(Items.BRICK))
		);
	}
}