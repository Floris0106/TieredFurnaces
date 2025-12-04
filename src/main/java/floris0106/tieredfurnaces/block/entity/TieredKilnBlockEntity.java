package floris0106.tieredfurnaces.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

import floris0106.tieredfurnaces.TieredFurnaces;
import floris0106.tieredfurnaces.inventory.TieredKilnMenu;

@ParametersAreNonnullByDefault
public class TieredKilnBlockEntity extends AbstractTieredFurnaceBlockEntity
{
	public TieredKilnBlockEntity(BlockPos pos, BlockState state)
	{
		super(TieredFurnaces.TIERED_KILN_BLOCK_ENTITY.get(), pos, state, TieredFurnaces.FIRING_RECIPE_TYPE.get());
	}

	@Override
	protected int getBurnDuration(ItemStack fuel)
	{
		return super.getBurnDuration(fuel) / 2;
	}

	@Override
	protected @NotNull AbstractContainerMenu createMenu(int id, Inventory inventory)
	{
		return new TieredKilnMenu(id, inventory, this, containerData);
	}
}