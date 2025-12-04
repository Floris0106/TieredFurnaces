package floris0106.tieredfurnaces;

import com.mojang.serialization.Codec;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.tags.TagKey;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.Tags;

@MethodsReturnNonnullByDefault
public enum FurnaceTier implements StringRepresentable
{
	BASE,
	COPPER,
	IRON,
	GOLD,
	DIAMOND,
	NETHERITE;

	public static final Codec<FurnaceTier> CODEC = StringRepresentable.fromValues(FurnaceTier::values);

	@Override
	public String getSerializedName()
	{
		return switch (this)
		{
			case BASE -> "base";
			case COPPER -> "copper";
			case IRON -> "iron";
			case GOLD -> "gold";
			case DIAMOND -> "diamond";
			case NETHERITE -> "netherite";
		};
	}

	public String humanReadableName()
	{
		return switch (this)
		{
			case BASE -> "Base";
			case COPPER -> "Copper";
			case IRON -> "Iron";
			case GOLD -> "Gold";
			case DIAMOND -> "Diamond";
			case NETHERITE -> "Netherite";
		};
	}

	public String idPrefix()
	{
		return switch (this)
		{
			case BASE -> "";
			case COPPER -> "copper_";
			case IRON -> "iron_";
			case GOLD -> "gold_";
			case DIAMOND -> "diamond_";
			case NETHERITE -> "netherite_";
		};
	}

	public FurnaceTier previous()
	{
		return switch (this)
		{
			case BASE -> throw new IllegalStateException();
			case COPPER -> BASE;
			case IRON -> COPPER;
			case GOLD -> IRON;
			case DIAMOND -> GOLD;
			case NETHERITE -> DIAMOND;
		};
	}

	public TagKey<Item> materialTag()
	{
		return switch (this)
		{
			case BASE -> Tags.Items.COBBLESTONES;
			case COPPER -> Tags.Items.INGOTS_COPPER;
			case IRON -> Tags.Items.INGOTS_IRON;
			case GOLD -> Tags.Items.INGOTS_GOLD;
			case DIAMOND -> Tags.Items.GEMS_DIAMOND;
			case NETHERITE -> Tags.Items.INGOTS_NETHERITE;
		};
	}

	public float getSpeedMultiplier()
	{
		return switch (this)
		{
			case BASE -> 1.0f;
			case COPPER -> 2.0f;
			case IRON -> 2.5f;
			case GOLD -> 4.0f;
			case DIAMOND -> 10f;
			case NETHERITE -> 25.0f;
		};
	}
}