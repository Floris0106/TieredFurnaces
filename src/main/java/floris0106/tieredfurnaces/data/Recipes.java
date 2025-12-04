package floris0106.tieredfurnaces.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SmithingTransformRecipeBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.Tags;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import floris0106.tieredfurnaces.FurnaceTier;
import floris0106.tieredfurnaces.FurnaceType;
import floris0106.tieredfurnaces.TieredFurnaces;
import floris0106.tieredfurnaces.item.UpgradeItem;

@SuppressWarnings("SameParameterValue")
public class Recipes extends RecipeProvider
{
	public Recipes(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup)
	{
		super(output, lookup);
	}

	@Override
	protected void buildRecipes(@NotNull RecipeOutput recipeOutput)
	{
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TieredFurnaces.TIERED_KILN_ITEMS.get(FurnaceTier.BASE).get())
			.define('T', Items.TERRACOTTA)
			.define('F', Items.FURNACE)
			.define('C', Tags.Items.INGOTS_COPPER)
			.pattern("TTT")
			.pattern("TFT")
			.pattern("CCC")
			.unlockedBy("has_copper", has(Tags.Items.INGOTS_COPPER))
			.save(recipeOutput, TieredFurnaces.resourceLocation("kiln"));

		for (FurnaceType type : FurnaceType.values())
		{
			upgrade(recipeOutput, type, FurnaceTier.BASE, FurnaceTier.COPPER);
			upgrade(recipeOutput, type, FurnaceTier.BASE, FurnaceTier.IRON);
			cheapUpgrade(recipeOutput, type, FurnaceTier.COPPER, FurnaceTier.IRON);
			upgrade(recipeOutput, type, FurnaceTier.IRON, FurnaceTier.GOLD);
			cheapUpgrade(recipeOutput, type, FurnaceTier.GOLD, FurnaceTier.DIAMOND);
			smithingUpgrade(recipeOutput, type, FurnaceTier.DIAMOND, FurnaceTier.NETHERITE, Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE));
		}

		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Objects.requireNonNull(TieredFurnaces.UPGRADE_ITEMS.get(FurnaceTier.BASE, FurnaceTier.COPPER)))
			.define('M', Tags.Items.INGOTS_COPPER)
			.define('C', Tags.Items.COBBLESTONES)
			.pattern("MMM")
			.pattern("MCM")
			.pattern("MMM")
			.unlockedBy("has_copper", has(Tags.Items.INGOTS_COPPER))
			.save(recipeOutput, TieredFurnaces.resourceLocation("base_to_copper_upgrade"));
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Objects.requireNonNull(TieredFurnaces.UPGRADE_ITEMS.get(FurnaceTier.COPPER, FurnaceTier.IRON)))
			.define('M', Tags.Items.INGOTS_IRON)
			.define('C', Tags.Items.COBBLESTONES)
			.pattern(" M ")
			.pattern("MCM")
			.pattern(" M ")
			.unlockedBy("has_iron", has(Tags.Items.INGOTS_IRON))
			.save(recipeOutput, TieredFurnaces.resourceLocation("copper_to_iron_upgrade"));
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Objects.requireNonNull(TieredFurnaces.UPGRADE_ITEMS.get(FurnaceTier.BASE, FurnaceTier.IRON)))
			.define('M', Tags.Items.INGOTS_IRON)
			.define('C', Tags.Items.COBBLESTONES)
			.pattern("MMM")
			.pattern("MCM")
			.pattern("MMM")
			.unlockedBy("has_iron", has(Tags.Items.INGOTS_IRON))
			.save(recipeOutput, TieredFurnaces.resourceLocation("base_to_iron_upgrade"));
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Objects.requireNonNull(TieredFurnaces.UPGRADE_ITEMS.get(FurnaceTier.IRON, FurnaceTier.GOLD)))
			.define('M', Tags.Items.INGOTS_GOLD)
			.define('C', Tags.Items.COBBLESTONES)
			.pattern("MMM")
			.pattern("MCM")
			.pattern("MMM")
			.unlockedBy("has_gold", has(Tags.Items.INGOTS_GOLD))
			.save(recipeOutput, TieredFurnaces.resourceLocation("iron_to_gold_upgrade"));
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Objects.requireNonNull(TieredFurnaces.UPGRADE_ITEMS.get(FurnaceTier.GOLD, FurnaceTier.DIAMOND)))
			.define('M', Tags.Items.GEMS_DIAMOND)
			.define('C', Tags.Items.COBBLESTONES)
			.pattern(" M ")
			.pattern("MCM")
			.pattern(" M ")
			.unlockedBy("has_iron", has(Tags.Items.GEMS_DIAMOND))
			.save(recipeOutput, TieredFurnaces.resourceLocation("gold_to_diamond_upgrade"));
		SmithingTransformRecipeBuilder.smithing(Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE), Ingredient.of(Tags.Items.COBBLESTONES), Ingredient.of(Tags.Items.INGOTS_NETHERITE), RecipeCategory.MISC, Objects.requireNonNull(TieredFurnaces.UPGRADE_ITEMS.get(FurnaceTier.DIAMOND, FurnaceTier.NETHERITE)).get())
			.unlocks("has_netherite", has(Tags.Items.INGOTS_NETHERITE))
			.save(recipeOutput, TieredFurnaces.resourceLocation("diamond_to_netherite_upgrade"));

		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Objects.requireNonNull(TieredFurnaces.UPGRADE_ITEMS.get(FurnaceTier.BASE, FurnaceTier.IRON)))
			.define('B', Objects.requireNonNull(TieredFurnaces.UPGRADE_ITEMS.get(FurnaceTier.BASE, FurnaceTier.COPPER)))
			.define('M', Tags.Items.INGOTS_IRON)
			.pattern(" M ")
			.pattern("MBM")
			.pattern(" M ")
			.unlockedBy("has_iron", has(Tags.Items.INGOTS_IRON))
			.save(recipeOutput, TieredFurnaces.resourceLocation("base_to_iron_upgrade_via_copper"));

		for (FurnaceTier to : FurnaceTier.values())
			if (to.ordinal() > FurnaceTier.IRON.ordinal())
				for (FurnaceTier from : FurnaceTier.values())
				{
					if (to.previous().ordinal() <= from.ordinal())
						continue;

					UpgradeItem baseUpgrade = Objects.requireNonNull(TieredFurnaces.UPGRADE_ITEMS.get(from, to.previous())).get();
					switch (to)
					{
						case NETHERITE:
							smithingUpgradeItem(recipeOutput, baseUpgrade, to, Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE));
							break;
						case DIAMOND:
							cheapUpgradeItem(recipeOutput, baseUpgrade, to);
							break;
						default:
							upgradeItem(recipeOutput, baseUpgrade, to);
							break;
					}
				}

		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TieredFurnaces.PELTIER_ELEMENT_ITEM)
			.define('I', Tags.Items.INGOTS_IRON)
			.define('C', Tags.Items.INGOTS_COPPER)
			.define('Q', Tags.Items.GEMS_QUARTZ)
			.pattern("ICI")
			.pattern("QQQ")
			.pattern("ICI")
			.unlockedBy("has_quartz", has(Tags.Items.GEMS_QUARTZ))
			.save(recipeOutput, TieredFurnaces.resourceLocation("peltier_element"));

		ExtendedCookingRecipeBuilder.smoking(Ingredient.of(Items.CHORUS_FRUIT), RecipeCategory.MISC, Items.POPPED_CHORUS_FRUIT, 0.1f, 100)
			.unlockedBy("has_chorus_fruit", has(Items.CHORUS_FRUIT))
			.save(recipeOutput, TieredFurnaces.resourceLocation("popped_chorus_fruit_from_smoking"));
		ExtendedCookingRecipeBuilder.campfireCooking(Ingredient.of(Items.CHORUS_FRUIT), RecipeCategory.MISC, Items.POPPED_CHORUS_FRUIT, 0.1f, 600)
			.unlockedBy("has_chorus_fruit", has(Items.CHORUS_FRUIT))
			.save(recipeOutput, TieredFurnaces.resourceLocation("popped_chorus_fruit_from_campfire_cooking"));

		commonFiringRecipe(recipeOutput, Items.COBBLESTONE, Items.STONE);
		commonFiringRecipe(recipeOutput, Items.STONE, Items.SMOOTH_STONE);
		commonFiringRecipe(recipeOutput, Items.STONE_BRICKS, Items.CRACKED_STONE_BRICKS);
		commonFiringRecipe(recipeOutput, Items.COBBLED_DEEPSLATE, Items.DEEPSLATE);
		commonFiringRecipe(recipeOutput, Items.DEEPSLATE_BRICKS, Items.CRACKED_DEEPSLATE_BRICKS);
		commonFiringRecipe(recipeOutput, Items.DEEPSLATE_TILES, Items.CRACKED_DEEPSLATE_TILES);
		commonFiringRecipe(recipeOutput, Items.SANDSTONE, Items.SMOOTH_SANDSTONE);
		commonFiringRecipe(recipeOutput, Items.RED_SANDSTONE, Items.SMOOTH_RED_SANDSTONE);
		commonFiringRecipe(recipeOutput, Items.NETHER_BRICKS, Items.CRACKED_NETHER_BRICKS);
		commonFiringRecipe(recipeOutput, Items.BASALT, Items.SMOOTH_BASALT);
		commonFiringRecipe(recipeOutput, Items.POLISHED_BLACKSTONE_BRICKS, Items.CRACKED_POLISHED_BLACKSTONE_BRICKS);
		commonFiringRecipe(recipeOutput, Items.QUARTZ_BLOCK, Items.SMOOTH_QUARTZ);

		ExtendedCookingRecipeBuilder.firing(Ingredient.of(Items.CLAY), RecipeCategory.BUILDING_BLOCKS, Items.TERRACOTTA, 0.35f, 100)
			.unlockedBy("has_clay", has(Items.CLAY))
			.save(recipeOutput, TieredFurnaces.resourceLocation("terracotta_from_firing"));

		commonFiringRecipe(recipeOutput, Items.WHITE_TERRACOTTA, Items.WHITE_GLAZED_TERRACOTTA);
		commonFiringRecipe(recipeOutput, Items.ORANGE_TERRACOTTA, Items.ORANGE_GLAZED_TERRACOTTA);
		commonFiringRecipe(recipeOutput, Items.MAGENTA_TERRACOTTA, Items.MAGENTA_GLAZED_TERRACOTTA);
		commonFiringRecipe(recipeOutput, Items.LIGHT_BLUE_TERRACOTTA, Items.LIGHT_BLUE_GLAZED_TERRACOTTA);
		commonFiringRecipe(recipeOutput, Items.YELLOW_TERRACOTTA, Items.YELLOW_GLAZED_TERRACOTTA);
		commonFiringRecipe(recipeOutput, Items.LIME_TERRACOTTA, Items.LIME_GLAZED_TERRACOTTA);
		commonFiringRecipe(recipeOutput, Items.PINK_TERRACOTTA, Items.PINK_GLAZED_TERRACOTTA);
		commonFiringRecipe(recipeOutput, Items.GRAY_TERRACOTTA, Items.GRAY_GLAZED_TERRACOTTA);
		commonFiringRecipe(recipeOutput, Items.LIGHT_GRAY_TERRACOTTA, Items.LIGHT_GRAY_GLAZED_TERRACOTTA);
		commonFiringRecipe(recipeOutput, Items.CYAN_TERRACOTTA, Items.CYAN_GLAZED_TERRACOTTA);
		commonFiringRecipe(recipeOutput, Items.PURPLE_TERRACOTTA, Items.PURPLE_GLAZED_TERRACOTTA);
		commonFiringRecipe(recipeOutput, Items.BLUE_TERRACOTTA, Items.BLUE_GLAZED_TERRACOTTA);
		commonFiringRecipe(recipeOutput, Items.BROWN_TERRACOTTA, Items.BROWN_GLAZED_TERRACOTTA);
		commonFiringRecipe(recipeOutput, Items.GREEN_TERRACOTTA, Items.GREEN_GLAZED_TERRACOTTA);
		commonFiringRecipe(recipeOutput, Items.RED_TERRACOTTA, Items.RED_GLAZED_TERRACOTTA);
		commonFiringRecipe(recipeOutput, Items.BLACK_TERRACOTTA, Items.BLACK_GLAZED_TERRACOTTA);

		ExtendedCookingRecipeBuilder.firing(Ingredient.of(Tags.Items.SANDS), RecipeCategory.BUILDING_BLOCKS, Items.GLASS, 0.1f, 100)
			.unlockedBy("has_sand", has(Tags.Items.SANDS))
			.save(recipeOutput, TieredFurnaces.resourceLocation("glass_from_firing"));

		ExtendedCookingRecipeBuilder.firing(Ingredient.of(Items.CLAY_BALL), RecipeCategory.MISC, Items.BRICK, 0.3f, 100)
			.unlockedBy("has_clay_ball", has(Items.CLAY_BALL))
			.save(recipeOutput, TieredFurnaces.resourceLocation("brick_from_firing"));

		ExtendedCookingRecipeBuilder.firing(Ingredient.of(Items.NETHERRACK), RecipeCategory.MISC, Items.NETHER_BRICK, 0.1f, 100)
			.unlockedBy("has_netherrack", has(Items.NETHERRACK))
			.save(recipeOutput, TieredFurnaces.resourceLocation("nether_brick_from_firing"));
	}

	private static void upgrade(RecipeOutput recipeOutput, FurnaceType type, FurnaceTier from, FurnaceTier to)
	{
		Map<FurnaceTier, ? extends Supplier<BlockItem>> tiers = type.getRegisteredItems();
		TagKey<Item> material = to.materialTag();
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, tiers.get(to).get())
			.define('F', tiers.get(from).get())
			.define('M', material)
			.pattern("MMM")
			.pattern("MFM")
			.pattern("MMM")
			.unlockedBy("has_" + to.getSerializedName(), has(material))
			.save(recipeOutput, TieredFurnaces.resourceLocation(from.getSerializedName() + "_to_" + to.getSerializedName() + "_" + type.getSerializedName()));
	}
	private static void cheapUpgrade(RecipeOutput recipeOutput, FurnaceType type, FurnaceTier from, FurnaceTier to)
	{
		Map<FurnaceTier, ? extends Supplier<BlockItem>> tiers = type.getRegisteredItems();
		TagKey<Item> material = to.materialTag();
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, tiers.get(to).get())
			.define('F', tiers.get(from).get())
			.define('M', material)
			.pattern(" M ")
			.pattern("MFM")
			.pattern(" M ")
			.unlockedBy("has_" + to, has(material))
			.save(recipeOutput, TieredFurnaces.resourceLocation(from.getSerializedName() + "_to_" + to.getSerializedName() + "_" + type.getSerializedName()));
	}
	private static void smithingUpgrade(RecipeOutput recipeOutput, FurnaceType type, FurnaceTier from, FurnaceTier to, Ingredient template)
	{
		Map<FurnaceTier, ? extends Supplier<BlockItem>> tiers = type.getRegisteredItems();
		TagKey<Item> material = to.materialTag();
		SmithingTransformRecipeBuilder.smithing(template, Ingredient.of(tiers.get(from).get()), Ingredient.of(material), RecipeCategory.MISC, tiers.get(to).get())
			.unlocks("has_" + to, has(material))
			.save(recipeOutput, TieredFurnaces.resourceLocation(from.getSerializedName() + "_to_" + to.getSerializedName() + "_" + type.getSerializedName()));
	}

	private static void upgradeItem(RecipeOutput recipeOutput, UpgradeItem baseUpgrade, FurnaceTier to)
	{
		FurnaceTier from = baseUpgrade.getFrom();
		TagKey<Item> material = to.materialTag();
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Objects.requireNonNull(TieredFurnaces.UPGRADE_ITEMS.get(from, to)))
			.define('B', baseUpgrade)
			.define('M', material)
			.pattern("MMM")
			.pattern("MBM")
			.pattern("MMM")
			.unlockedBy("has_" + to.getSerializedName(), has(material))
			.save(recipeOutput, TieredFurnaces.resourceLocation(from.getSerializedName() + "_to_" + to.getSerializedName() + "_upgrade"));
	}
	private static void cheapUpgradeItem(RecipeOutput recipeOutput, UpgradeItem baseUpgrade, FurnaceTier to)
	{
		FurnaceTier from = baseUpgrade.getFrom();
		TagKey<Item> material = to.materialTag();
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Objects.requireNonNull(TieredFurnaces.UPGRADE_ITEMS.get(from, to)))
			.define('B', baseUpgrade)
			.define('M', material)
			.pattern(" M ")
			.pattern("MBM")
			.pattern(" M ")
			.unlockedBy("has_" + to.getSerializedName(), has(material))
			.save(recipeOutput, TieredFurnaces.resourceLocation(from.getSerializedName() + "_to_" + to.getSerializedName() + "_upgrade"));
	}
	private static void smithingUpgradeItem(RecipeOutput recipeOutput, UpgradeItem baseUpgrade, FurnaceTier to, Ingredient template)
	{
		FurnaceTier from = baseUpgrade.getFrom();
		TagKey<Item> material = to.materialTag();
		SmithingTransformRecipeBuilder.smithing(template, Ingredient.of(baseUpgrade), Ingredient.of(material), RecipeCategory.MISC, Objects.requireNonNull(TieredFurnaces.UPGRADE_ITEMS.get(from, to)).get())
			.unlocks("has_" + to, has(material))
			.save(recipeOutput, TieredFurnaces.resourceLocation(from.getSerializedName() + "_to_" + to.getSerializedName() + "_upgrade"));
	}

	private static void commonFiringRecipe(RecipeOutput recipeOutput, Item input, Item output)
	{
		ExtendedCookingRecipeBuilder.firing(Ingredient.of(input), RecipeCategory.BUILDING_BLOCKS, output, 0.1f, 100)
			.unlockedBy("has_" + BuiltInRegistries.ITEM.getKey(input).getPath(), has(input))
			.save(recipeOutput, TieredFurnaces.resourceLocation(BuiltInRegistries.ITEM.getKey(output).getPath() + "_from_firing"));
	}
}