package floris0106.tieredfurnaces.data;

import com.google.common.collect.Table;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.registries.DeferredItem;

import floris0106.tieredfurnaces.FurnaceTier;
import floris0106.tieredfurnaces.TieredFurnaces;
import floris0106.tieredfurnaces.item.UpgradeItem;

public class Language extends LanguageProvider
{
	public Language(PackOutput output)
	{
		super(output, TieredFurnaces.MODID, "en_us");
	}

	@Override
	protected void addTranslations()
	{
		add("itemGroup.tieredFurnaces", "Tiered Furnaces");
		add("stat.tieredfurnaces.interact_with_kiln", "Interactions with Kiln");
		add("gui.recipebook.toggleRecipes.firable", "Showing Firable");
		add("item.tieredfurnaces.peltier_element", "Peltier Element");
		add("container.abstracttieredfurnace.energy", "Energy: %s/%s FE");
		add("subtitles.block.kiln.fire_crackle", "Kiln crackles");
        add("subtitles.entity.villager.work_clayworker", "Clayworker works");
        add("entity.minecraft.villager.tieredfurnaces.clayworker", "Clayworker");
        add("tieredfurnaces.config.tiers", "Furnace Tiers");
        add("tieredfurnaces.config.energy", "Energy");
        add("tieredfurnaces.config.energy.generation_per_tick", "Base Generation per Tick");
        add("tieredfurnaces.config.energy.consumption_per_tick", "Base Consumption per Tick");
		add("tieredfurnaces.config.energy.buffer_capacity", "Buffer Capacity");
		add("tieredfurnaces.config.energy.buffer_scaling", "Buffer Scaling");

        for (FurnaceTier tier : FurnaceTier.values())
            add("tieredfurnaces.config.tiers." + tier.getSerializedName() + ".multiplier", tier.humanReadableName() + " Multiplier");

		for (FurnaceTier tier : FurnaceTier.values())
		{
			String namePrefix = tier.idPrefix();
			if (!namePrefix.isEmpty())
				namePrefix = Character.toUpperCase(namePrefix.charAt(0)) + namePrefix.substring(1).replace("_", " ");

			addBlock(TieredFurnaces.TIERED_FURNACE_BLOCKS.get(tier), namePrefix + "Furnace");
			addBlock(TieredFurnaces.TIERED_BLAST_FURNACE_BLOCKS.get(tier), namePrefix + "Blast Furnace");
			addBlock(TieredFurnaces.TIERED_SMOKER_BLOCKS.get(tier), namePrefix + "Smoker");
			addBlock(TieredFurnaces.TIERED_KILN_BLOCKS.get(tier), namePrefix + "Kiln");
		}

		for (Table.Cell<FurnaceTier, FurnaceTier, DeferredItem<UpgradeItem>> cell : TieredFurnaces.UPGRADE_ITEMS.cellSet())
			addItem(cell.getValue(), cell.getRowKey().humanReadableName() + " to " + cell.getColumnKey().humanReadableName() + " Upgrade");
	}
}