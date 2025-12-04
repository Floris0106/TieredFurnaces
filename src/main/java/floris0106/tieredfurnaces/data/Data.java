package floris0106.tieredfurnaces.data;

import floris0106.tieredfurnaces.TieredFurnaces;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = TieredFurnaces.MODID)
public class Data
{
	@SubscribeEvent
	public static void onGatherData(GatherDataEvent event)
	{
		DataGenerator generator = event.getGenerator();
		PackOutput output = generator.getPackOutput();
		CompletableFuture<HolderLookup.Provider> lookup = event.getLookupProvider();
		ExistingFileHelper fileHelper = event.getExistingFileHelper();

		generator.addProvider(event.includeServer(), new Loot(output, lookup));
		generator.addProvider(event.includeServer(), new BlockTags(output, lookup, fileHelper));
		generator.addProvider(event.includeServer(), new Recipes(output, lookup));

		generator.addProvider(event.includeClient(), new Language(output));
        generator.addProvider(event.includeClient(), new ItemModels(output, fileHelper));
		generator.addProvider(event.includeClient(), new BlockStates(output, fileHelper));
		generator.addProvider(event.includeClient(), new SoundDefinitions(output, fileHelper));
	}
}