package floris0106.tieredfurnaces.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.neoforge.registries.DeferredHolder;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import javax.annotation.ParametersAreNonnullByDefault;

import floris0106.tieredfurnaces.TieredFurnaces;

public class Loot extends LootTableProvider
{
	public Loot(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup)
	{
		super(output, Set.of(), List.of(new SubProviderEntry(BlockLoot::new, LootContextParamSets.BLOCK)), lookup);
	}

	@ParametersAreNonnullByDefault
	public static class BlockLoot extends BlockLootSubProvider
	{
		protected BlockLoot(HolderLookup.Provider lookupProvider)
		{
			super(Set.of(), FeatureFlags.REGISTRY.allFlags(), lookupProvider);
		}

		@Override
		protected void generate()
		{
			for (DeferredHolder<Block, ? extends Block> entry : TieredFurnaces.BLOCKS.getEntries())
				dropSelf(entry.get());
		}

		@Override
		public @NotNull Iterable<Block> getKnownBlocks()
		{
			return TieredFurnaces.BLOCKS.getEntries()
				.stream()
				.map(holder -> (Block)holder.get())
				.toList();
		}
	}
}