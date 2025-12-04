package floris0106.tieredfurnaces.client.gui.screens.recipebook;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.gui.screens.recipebook.AbstractFurnaceRecipeBookComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;

import java.util.Set;

@MethodsReturnNonnullByDefault
public class FiringRecipeBookComponent extends AbstractFurnaceRecipeBookComponent
{
	private static final Component FILTER_NAME = Component.translatable("gui.recipebook.toggleRecipes.firable");

	@Override
	protected Component getRecipeFilterName() {
		return FILTER_NAME;
	}

	@SuppressWarnings("deprecation")
	@Override
	protected Set<Item> getFuelItems() {
		return AbstractFurnaceBlockEntity.getFuel().keySet();
	}
}