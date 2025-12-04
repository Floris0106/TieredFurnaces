package floris0106.tieredfurnaces.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.item.crafting.RecipeType;

import floris0106.tieredfurnaces.TieredFurnaces;

public class TieredBlastFurnaceMenu extends AbstractTieredFurnaceMenu {
	public TieredBlastFurnaceMenu(int containerId, Inventory playerInventory)
	{
		super(TieredFurnaces.TIERED_BLAST_FURNACE_MENU.get(), RecipeType.BLASTING, RecipeBookType.BLAST_FURNACE, containerId, playerInventory);
	}

	public TieredBlastFurnaceMenu(int containerId, Inventory playerInventory, Container kilnContainer, ContainerData kilnData)
	{
		super(TieredFurnaces.TIERED_BLAST_FURNACE_MENU.get(), RecipeType.BLASTING, RecipeBookType.BLAST_FURNACE, containerId, playerInventory, kilnContainer, kilnData);
	}
}