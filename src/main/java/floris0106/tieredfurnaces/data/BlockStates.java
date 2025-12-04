package floris0106.tieredfurnaces.data;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import floris0106.tieredfurnaces.FurnaceTier;
import floris0106.tieredfurnaces.FurnaceType;
import floris0106.tieredfurnaces.TieredFurnaces;

public class BlockStates extends BlockStateProvider
{
	public BlockStates(PackOutput output, ExistingFileHelper fileHelper)
	{
		super(output, TieredFurnaces.MODID, fileHelper);
	}

	@Override
	protected void registerStatesAndModels()
	{
		for (FurnaceTier tier : FurnaceTier.values())
		{
			String idPrefix = tier.idPrefix();

			for (FurnaceType type : FurnaceType.values())
			{
				String id = idPrefix + type.getSerializedName();

				ResourceLocation side = textureId(type, tier, id, "side");
				ResourceLocation front = textureId(type, tier, id, "front");
				ResourceLocation frontOn = textureId(type, tier, id, "front_on");
				ResourceLocation bottom = textureId(type, tier, id, "bottom");
				ResourceLocation top = textureId(type, tier, id, "top");
				BlockModelBuilder model = models().orientableWithBottom(id, side, front, bottom, top);
				BlockModelBuilder modelOn = models().orientableWithBottom(id + "_on", side, frontOn, bottom, top);

				AbstractFurnaceBlock block = type.getRegisteredBlocks().get(tier).get();

				horizontalBlock(block, blockState ->
				{
					if (blockState.getValue(AbstractFurnaceBlock.LIT))
						return modelOn;
					else
						return model;
				});
				itemModels().simpleBlockItem(block);
			}
		}
	}

	private static ResourceLocation textureId(FurnaceType type, FurnaceTier tier, String id, String side)
	{
		if (side.equals("top"))
		{
			if (type == FurnaceType.BLAST_FURNACE)
				return ResourceLocation.withDefaultNamespace("block/blast_furnace_top");
			if (type != FurnaceType.SMOKER && tier != FurnaceTier.BASE)
				return ResourceLocation.withDefaultNamespace("block/" + tier.idPrefix() + "block");
			if (type == FurnaceType.FURNACE)
				return ResourceLocation.withDefaultNamespace("block/furnace_top");
			if (type == FurnaceType.KILN)
				return TieredFurnaces.resourceLocation("block/kiln_top");
		}
		else if (side.equals("bottom"))
		{
			if (type == FurnaceType.BLAST_FURNACE)
			{
				if (tier == FurnaceTier.BASE)
					return ResourceLocation.withDefaultNamespace("block/blast_furnace_top");
				else
					return ResourceLocation.withDefaultNamespace("block/" + tier.idPrefix() + "block");
			}
			if (type == FurnaceType.FURNACE)
				return ResourceLocation.withDefaultNamespace("block/furnace_top");
		}

		String namespace = tier == FurnaceTier.BASE && type != FurnaceType.KILN ? "minecraft" : TieredFurnaces.MODID;
		return ResourceLocation.fromNamespaceAndPath(namespace, "block/" + id + "_" + side);
	}
}