package floris0106.tieredfurnaces.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.item.crafting.RecipeType;

import floris0106.tieredfurnaces.TieredFurnaces;

public class TieredSmokerMenu extends AbstractTieredFurnaceMenu {
	public TieredSmokerMenu(int containerId, Inventory playerInventory)
	{
		super(TieredFurnaces.TIERED_SMOKER_MENU.get(), RecipeType.SMOKING, RecipeBookType.SMOKER, containerId, playerInventory);
	}

	public TieredSmokerMenu(int containerId, Inventory playerInventory, Container kilnContainer, ContainerData kilnData)
	{
		super(TieredFurnaces.TIERED_SMOKER_MENU.get(), RecipeType.SMOKING, RecipeBookType.SMOKER, containerId, playerInventory, kilnContainer, kilnData);
	}
}