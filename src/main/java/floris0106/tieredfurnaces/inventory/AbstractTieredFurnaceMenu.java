package floris0106.tieredfurnaces.inventory;

import floris0106.tieredfurnaces.config.Config;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractFurnaceMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeType;

import javax.annotation.ParametersAreNonnullByDefault;

import floris0106.tieredfurnaces.TieredFurnaces;

@ParametersAreNonnullByDefault
public abstract class AbstractTieredFurnaceMenu extends AbstractFurnaceMenu {
	protected AbstractTieredFurnaceMenu(MenuType<? extends AbstractTieredFurnaceMenu> menuType, RecipeType<? extends AbstractCookingRecipe> recipeType, RecipeBookType recipeBookType, int containerId, Inventory playerInventory)
	{
		super(menuType, recipeType, recipeBookType, containerId, playerInventory, new SimpleContainer(3), new SimpleContainerData(7));
	}

	protected AbstractTieredFurnaceMenu(MenuType<? extends AbstractTieredFurnaceMenu> menuType, RecipeType<? extends AbstractCookingRecipe> recipeType, RecipeBookType recipeBookType, int containerId, Inventory playerInventory, Container container, ContainerData data)
	{
		super(menuType, recipeType, recipeBookType, containerId, playerInventory, container, data);
	}

	@Override
	protected boolean canSmelt(ItemStack stack)
	{
		return super.canSmelt(stack) || (stack.is(TieredFurnaces.PELTIER_ELEMENT_ITEM) && Config.PELTIER_ENERGY_GENERATION.get() > 0.0);
	}

	@Override
	protected boolean isFuel(ItemStack stack)
	{
		return super.isFuel(stack) || (stack.is(TieredFurnaces.PELTIER_ELEMENT_ITEM) && Config.PELTIER_ENERGY_CONSUMPTION.get() > 0.0);
	}

	@Override
	public float getLitProgress()
	{
		return data.get(0) / (float) Short.MAX_VALUE;
	}

	@Override
	public float getBurnProgress()
	{
		return data.get(1) / (float) Short.MAX_VALUE;
	}

	public int getEnergyStored()
	{
		return data.get(2) | (data.get(3) << 16);
	}

	public int getMaxEnergyStored()
	{
		return data.get(4) | (data.get(5) << 16);
	}

	public boolean isUsingPeltierElement()
	{
		return data.get(6) != 0;
	}
}