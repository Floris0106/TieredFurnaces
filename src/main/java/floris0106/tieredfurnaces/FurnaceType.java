package floris0106.tieredfurnaces;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.AbstractFurnaceBlock;

import java.util.Map;
import java.util.function.Supplier;

@MethodsReturnNonnullByDefault
public enum FurnaceType implements StringRepresentable
{
    FURNACE,
    BLAST_FURNACE,
    SMOKER,
    KILN;

	@Override
	public String getSerializedName()
	{
		return switch (this)
		{
			case FURNACE -> "furnace";
			case BLAST_FURNACE -> "blast_furnace";
			case SMOKER -> "smoker";
			case KILN -> "kiln";
		};
	}

	public int peltierEnergyMultiplier()
	{
		return this == FURNACE ? 1 : 2;
	}

	public Map<FurnaceTier, ? extends Supplier<? extends AbstractFurnaceBlock>> getRegisteredBlocks() {
		return switch (this)
		{
			case FURNACE -> TieredFurnaces.TIERED_FURNACE_BLOCKS;
			case BLAST_FURNACE -> TieredFurnaces.TIERED_BLAST_FURNACE_BLOCKS;
			case SMOKER -> TieredFurnaces.TIERED_SMOKER_BLOCKS;
			case KILN -> TieredFurnaces.TIERED_KILN_BLOCKS;
		};
	}

	public Map<FurnaceTier, ? extends Supplier<BlockItem>> getRegisteredItems() {
		return switch (this)
		{
			case FURNACE -> TieredFurnaces.TIERED_FURNACE_ITEMS;
			case BLAST_FURNACE -> TieredFurnaces.TIERED_BLAST_FURNACE_ITEMS;
			case SMOKER -> TieredFurnaces.TIERED_SMOKER_ITEMS;
			case KILN -> TieredFurnaces.TIERED_KILN_ITEMS;
		};
	}
}