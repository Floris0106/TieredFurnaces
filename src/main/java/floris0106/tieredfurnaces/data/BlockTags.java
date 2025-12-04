package floris0106.tieredfurnaces.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

import floris0106.tieredfurnaces.TieredFurnaces;

public class BlockTags extends BlockTagsProvider
{
	public BlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup, ExistingFileHelper fileHelper) {

		super(output, lookup, TieredFurnaces.MODID, fileHelper);
	}

	@Override
	protected void addTags(@NotNull HolderLookup.Provider provider)
	{
		tag(net.minecraft.tags.BlockTags.MINEABLE_WITH_PICKAXE).addAll(TieredFurnaces.BLOCKS.getEntries()
			.stream()
			.map(DeferredHolder::getKey)
			.toList());
		tag(net.minecraft.tags.BlockTags.FEATURES_CANNOT_REPLACE).addAll(TieredFurnaces.BLOCKS.getEntries()
			.stream()
			.map(DeferredHolder::getKey)
			.toList());
	}
}