package floris0106.tieredfurnaces.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.state.BlockState;

import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

import floris0106.tieredfurnaces.TieredFurnaces;
import floris0106.tieredfurnaces.inventory.TieredBlastFurnaceMenu;

@ParametersAreNonnullByDefault
public class TieredBlastFurnaceBlockEntity extends AbstractTieredFurnaceBlockEntity
{
	public TieredBlastFurnaceBlockEntity(BlockPos pos, BlockState state)
	{
		super(TieredFurnaces.TIERED_BLAST_FURNACE_BLOCK_ENTITY.get(), pos, state, RecipeType.BLASTING);
	}

	@Override
	protected int getBurnDuration(ItemStack fuel)
	{
		return super.getBurnDuration(fuel) / 2;
	}

	@Override
	protected @NotNull AbstractContainerMenu createMenu(int id, Inventory inventory)
	{
		return new TieredBlastFurnaceMenu(id, inventory, this, containerData);
	}
}