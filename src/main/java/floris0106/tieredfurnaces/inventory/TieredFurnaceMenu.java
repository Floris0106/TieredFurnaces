package floris0106.tieredfurnaces.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.item.crafting.RecipeType;

import floris0106.tieredfurnaces.TieredFurnaces;

public class TieredFurnaceMenu extends AbstractTieredFurnaceMenu {
	public TieredFurnaceMenu(int containerId, Inventory playerInventory)
	{
		super(TieredFurnaces.TIERED_FURNACE_MENU.get(), RecipeType.SMELTING, RecipeBookType.FURNACE, containerId, playerInventory);
	}

	public TieredFurnaceMenu(int containerId, Inventory playerInventory, Container kilnContainer, ContainerData kilnData)
	{
		super(TieredFurnaces.TIERED_FURNACE_MENU.get(), RecipeType.SMELTING, RecipeBookType.FURNACE, containerId, playerInventory, kilnContainer, kilnData);
	}
}