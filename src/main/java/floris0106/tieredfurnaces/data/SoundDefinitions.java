package floris0106.tieredfurnaces.data;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

import floris0106.tieredfurnaces.TieredFurnaces;

public class SoundDefinitions extends SoundDefinitionsProvider
{
	protected SoundDefinitions(PackOutput output, ExistingFileHelper helper)
	{
		super(output, TieredFurnaces.MODID, helper);
	}

	@Override
	public void registerSounds()
	{
		add(TieredFurnaces.KILN_FIRE_CRACKLE_SOUND_EVENT,
			SoundDefinition.definition()
				.with(sound("block/blastfurnace/blastfurnace1"))
				.with(sound("block/blastfurnace/blastfurnace2"))
				.with(sound("block/blastfurnace/blastfurnace3"))
				.with(sound("block/blastfurnace/blastfurnace4"))
				.with(sound("block/blastfurnace/blastfurnace5"))
				.subtitle("subtitles.block.kiln.fire_crackle")
		);
	}
}