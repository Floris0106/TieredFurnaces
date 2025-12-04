package floris0106.tieredfurnaces.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;

import floris0106.tieredfurnaces.EnumExtensions;
import floris0106.tieredfurnaces.TieredFurnaces;

public class TieredKilnMenu extends AbstractTieredFurnaceMenu {
	public TieredKilnMenu(int containerId, Inventory playerInventory)
	{
		super(TieredFurnaces.TIERED_KILN_MENU.get(), TieredFurnaces.FIRING_RECIPE_TYPE.get(), EnumExtensions.RecipeBookType.FIRING.getValue(), containerId, playerInventory);
	}

	public TieredKilnMenu(int containerId, Inventory playerInventory, Container kilnContainer, ContainerData kilnData)
	{
		super(TieredFurnaces.TIERED_KILN_MENU.get(), TieredFurnaces.FIRING_RECIPE_TYPE.get(), EnumExtensions.RecipeBookType.FIRING.getValue(), containerId, playerInventory, kilnContainer, kilnData);
	}
}