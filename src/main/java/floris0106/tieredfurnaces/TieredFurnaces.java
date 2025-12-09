package floris0106.tieredfurnaces;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;
import floris0106.tieredfurnaces.block.TieredBlastFurnaceBlock;
import floris0106.tieredfurnaces.block.TieredFurnaceBlock;
import floris0106.tieredfurnaces.block.TieredKilnBlock;
import floris0106.tieredfurnaces.block.TieredSmokerBlock;
import floris0106.tieredfurnaces.block.entity.TieredBlastFurnaceBlockEntity;
import floris0106.tieredfurnaces.block.entity.TieredFurnaceBlockEntity;
import floris0106.tieredfurnaces.block.entity.TieredKilnBlockEntity;
import floris0106.tieredfurnaces.block.entity.TieredSmokerBlockEntity;
import floris0106.tieredfurnaces.client.gui.screens.inventory.TieredFurnaceScreen;
import floris0106.tieredfurnaces.config.Config;
import floris0106.tieredfurnaces.inventory.TieredBlastFurnaceMenu;
import floris0106.tieredfurnaces.inventory.TieredFurnaceMenu;
import floris0106.tieredfurnaces.inventory.TieredKilnMenu;
import floris0106.tieredfurnaces.inventory.TieredSmokerMenu;
import floris0106.tieredfurnaces.item.UpgradeItem;
import floris0106.tieredfurnaces.item.crafting.FiringRecipe;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SimpleCookingSerializer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterRecipeBookCategoriesEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@SuppressWarnings("DataFlowIssue")
@Mod(TieredFurnaces.MODID)
public class TieredFurnaces
{
    public static final String MODID = "tieredfurnaces";

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MODID);
	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
	public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, MODID);
	public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, MODID);
    public static final DeferredRegister<ResourceLocation> STATS = DeferredRegister.create(Registries.CUSTOM_STAT, MODID);
	public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(Registries.MENU, MODID);
	public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, MODID);
    public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister.create(Registries.POINT_OF_INTEREST_TYPE, MODID);
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS = DeferredRegister.create(Registries.VILLAGER_PROFESSION, MODID);

	public static final Map<FurnaceTier, Supplier<TieredFurnaceBlock>> TIERED_FURNACE_BLOCKS;
	public static final Map<FurnaceTier, Supplier<TieredBlastFurnaceBlock>> TIERED_BLAST_FURNACE_BLOCKS;
	public static final Map<FurnaceTier, Supplier<TieredSmokerBlock>> TIERED_SMOKER_BLOCKS;
	public static final Map<FurnaceTier, Supplier<TieredKilnBlock>> TIERED_KILN_BLOCKS;

	public static final Map<FurnaceTier, Supplier<BlockItem>> TIERED_FURNACE_ITEMS;
	public static final Map<FurnaceTier, Supplier<BlockItem>> TIERED_BLAST_FURNACE_ITEMS;
	public static final Map<FurnaceTier, Supplier<BlockItem>> TIERED_SMOKER_ITEMS;
	public static final Map<FurnaceTier, Supplier<BlockItem>> TIERED_KILN_ITEMS;

	public static final Table<FurnaceTier, FurnaceTier, DeferredItem<UpgradeItem>> UPGRADE_ITEMS;

	static
    {
		ImmutableMap.Builder<FurnaceTier, Supplier<TieredFurnaceBlock>> furnaces = ImmutableMap.builder();
		furnaces.put(FurnaceTier.BASE, () -> (TieredFurnaceBlock) Blocks.FURNACE);
		for (FurnaceTier tier : FurnaceTier.values())
			if (tier != FurnaceTier.BASE)
				furnaces.put(tier, BLOCKS.register(
            	    tier.idPrefix() + "furnace",
            	    () -> new TieredFurnaceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.FURNACE), tier))
            	);
		TIERED_FURNACE_BLOCKS = furnaces.build();

		ImmutableMap.Builder<FurnaceTier, Supplier<TieredBlastFurnaceBlock>> blastFurnaces = ImmutableMap.builder();
		blastFurnaces.put(FurnaceTier.BASE, () -> (TieredBlastFurnaceBlock) Blocks.BLAST_FURNACE);
		for (FurnaceTier tier : FurnaceTier.values())
			if (tier != FurnaceTier.BASE)
				blastFurnaces.put(tier, BLOCKS.register(
            	    tier.idPrefix() + "blast_furnace",
            	    () -> new TieredBlastFurnaceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BLAST_FURNACE), tier))
            	);
		TIERED_BLAST_FURNACE_BLOCKS = blastFurnaces.build();

		ImmutableMap.Builder<FurnaceTier, Supplier<TieredSmokerBlock>> smokers = ImmutableMap.builder();
		smokers.put(FurnaceTier.BASE, () -> (TieredSmokerBlock) Blocks.SMOKER);
		for (FurnaceTier tier : FurnaceTier.values())
			if (tier != FurnaceTier.BASE)
				smokers.put(tier, BLOCKS.register(
            	    tier.idPrefix() + "smoker",
            	    () -> new TieredSmokerBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SMOKER), tier))
            	);
		TIERED_SMOKER_BLOCKS = smokers.build();

		ImmutableMap.Builder<FurnaceTier, Supplier<TieredKilnBlock>> kilns = ImmutableMap.builder();
		for (FurnaceTier tier : FurnaceTier.values())
			kilns.put(tier, BLOCKS.register(
                tier.idPrefix() + "kiln",
                () -> new TieredKilnBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_ORANGE)
				    .instrument(NoteBlockInstrument.BASEDRUM)
				    .requiresCorrectToolForDrops()
				    .strength(3.5F), tier))
            );
		TIERED_KILN_BLOCKS = kilns.build();

		ImmutableMap.Builder<FurnaceTier, Supplier<BlockItem>> furnaceItems = ImmutableMap.builder();
		furnaceItems.put(FurnaceTier.BASE, () -> (BlockItem) Items.FURNACE);
		for (FurnaceTier tier : FurnaceTier.values())
			if (tier != FurnaceTier.BASE)
				furnaceItems.put(tier, ITEMS.register(
					tier.idPrefix() + "furnace",
					() -> new BlockItem(TIERED_FURNACE_BLOCKS.get(tier).get(), new Item.Properties())
				));
		TIERED_FURNACE_ITEMS = furnaceItems.build();

		ImmutableMap.Builder<FurnaceTier, Supplier<BlockItem>> blastFurnaceItems = ImmutableMap.builder();
		blastFurnaceItems.put(FurnaceTier.BASE, () -> (BlockItem) Items.BLAST_FURNACE);
		for (FurnaceTier tier : FurnaceTier.values())
			if (tier != FurnaceTier.BASE)
				blastFurnaceItems.put(tier, ITEMS.register(
					tier.idPrefix() + "blast_furnace",
					() -> new BlockItem(TIERED_BLAST_FURNACE_BLOCKS.get(tier).get(), new Item.Properties())
				));

		TIERED_BLAST_FURNACE_ITEMS = blastFurnaceItems.build();

		ImmutableMap.Builder<FurnaceTier, Supplier<BlockItem>> smokerItems = ImmutableMap.builder();
		smokerItems.put(FurnaceTier.BASE, () -> (BlockItem) Items.SMOKER);
		for (FurnaceTier tier : FurnaceTier.values())
			if (tier != FurnaceTier.BASE)
				smokerItems.put(tier, ITEMS.register(
					tier.idPrefix() + "smoker",
					() -> new BlockItem(TIERED_SMOKER_BLOCKS.get(tier).get(), new Item.Properties())
				));
		TIERED_SMOKER_ITEMS = smokerItems.build();

		ImmutableMap.Builder<FurnaceTier, Supplier<BlockItem>> kilnItems = ImmutableMap.builder();
		for (FurnaceTier tier : FurnaceTier.values())
			kilnItems.put(tier, ITEMS.register(
				tier.idPrefix() + "kiln",
				() -> new BlockItem(TIERED_KILN_BLOCKS.get(tier).get(), new Item.Properties())
			));
		TIERED_KILN_ITEMS = kilnItems.build();

		ImmutableTable.Builder<FurnaceTier, FurnaceTier, DeferredItem<UpgradeItem>> upgradeItems = ImmutableTable.builder();
		for (FurnaceTier to : FurnaceTier.values())
			if (to != FurnaceTier.BASE)
				for (FurnaceTier from : FurnaceTier.values())
					if (to.ordinal() > from.ordinal())
						upgradeItems.put(from, to, ITEMS.register(
							from.getSerializedName() + "_to_" + to.getSerializedName() + "_upgrade",
							() -> new UpgradeItem(new Item.Properties(), from, to)
						));
		UPGRADE_ITEMS = upgradeItems.build();
	}

	public static final DeferredItem<Item> PELTIER_ELEMENT_ITEM = ITEMS.register(
		"peltier_element", () -> new Item(new Item.Properties().stacksTo(1))
	);

	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TIERED_FURNACES_TAB = CREATIVE_MODE_TABS.register(
		MODID, () -> CreativeModeTab.builder()
			.icon(() -> TIERED_KILN_ITEMS.get(FurnaceTier.DIAMOND).get().getDefaultInstance())
			.title(Component.translatable("itemGroup.tieredFurnaces"))
			.displayItems((params, output) -> {
				for (FurnaceType type : FurnaceType.values())
				{
					Map<FurnaceTier, ? extends Supplier<BlockItem>> tiers = type.getRegisteredItems();
					for (FurnaceTier tier : FurnaceTier.values())
						output.accept(tiers.get(tier).get());
				}
				for (DeferredItem<UpgradeItem> upgradeItem : UPGRADE_ITEMS.values())
					output.accept(upgradeItem);
				output.accept(PELTIER_ELEMENT_ITEM);
			})
			.build()
	);

	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TieredFurnaceBlockEntity>> TIERED_FURNACE_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
		"tiered_furnace", () -> BlockEntityType.Builder.of(TieredFurnaceBlockEntity::new,
				TIERED_FURNACE_BLOCKS.values().stream().map(Supplier::get).toArray(TieredFurnaceBlock[]::new))
			.build(null)
	);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TieredBlastFurnaceBlockEntity>> TIERED_BLAST_FURNACE_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
		"tiered_blast_furnace", () -> BlockEntityType.Builder.of(TieredBlastFurnaceBlockEntity::new,
				TIERED_BLAST_FURNACE_BLOCKS.values().stream().map(Supplier::get).toArray(TieredBlastFurnaceBlock[]::new))
			.build(null)
	);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TieredSmokerBlockEntity>> TIERED_SMOKER_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
		"tiered_smoker", () -> BlockEntityType.Builder.of(TieredSmokerBlockEntity::new,
				TIERED_SMOKER_BLOCKS.values().stream().map(Supplier::get).toArray(TieredSmokerBlock[]::new))
			.build(null)
	);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TieredKilnBlockEntity>> TIERED_KILN_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
        "tiered_kiln", () -> BlockEntityType.Builder.of(TieredKilnBlockEntity::new,
                TIERED_KILN_BLOCKS.values().stream().map(Supplier::get).toArray(TieredKilnBlock[]::new))
            .build(null)
	);

	public static final DeferredHolder<RecipeType<?>, RecipeType<FiringRecipe>> FIRING_RECIPE_TYPE = RECIPE_TYPES.register(
        "firing", () -> RecipeType.simple(resourceLocation("firing"))
	);

	public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<FiringRecipe>> FIRING_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register(
        "firing", () -> new SimpleCookingSerializer<>(FiringRecipe::new, 100)
	);

    public static final DeferredHolder<ResourceLocation, ResourceLocation> INTERACT_WITH_KILN_STAT = STATS.register(
        "interact_with_kiln", () -> resourceLocation("interact_with_kiln")
    );

	public static final DeferredHolder<MenuType<?>, MenuType<TieredFurnaceMenu>> TIERED_FURNACE_MENU = MENU_TYPES.register(
		"furnace", () -> new MenuType<>(TieredFurnaceMenu::new, FeatureFlags.VANILLA_SET)
	);
	public static final DeferredHolder<MenuType<?>, MenuType<TieredBlastFurnaceMenu>> TIERED_BLAST_FURNACE_MENU = MENU_TYPES.register(
		"blast_furnace", () -> new MenuType<>(TieredBlastFurnaceMenu::new, FeatureFlags.VANILLA_SET)
	);
	public static final DeferredHolder<MenuType<?>, MenuType<TieredSmokerMenu>> TIERED_SMOKER_MENU = MENU_TYPES.register(
		"smoker", () -> new MenuType<>(TieredSmokerMenu::new, FeatureFlags.VANILLA_SET)
	);
	public static final DeferredHolder<MenuType<?>, MenuType<TieredKilnMenu>> TIERED_KILN_MENU = MENU_TYPES.register(
		"kiln", () -> new MenuType<>(TieredKilnMenu::new, FeatureFlags.VANILLA_SET)
	);

	public static final DeferredHolder<SoundEvent, SoundEvent> KILN_FIRE_CRACKLE_SOUND_EVENT = SOUND_EVENTS.register(
		"block.kiln.fire_crackle", SoundEvent::createVariableRangeEvent
	);
    public static final DeferredHolder<SoundEvent, SoundEvent> CLAYWORKER_WORK_SOUND_EVENT = SOUND_EVENTS.register(
        "entity.villager.work_clayworker", SoundEvent::createVariableRangeEvent
    );

    public static final DeferredHolder<PoiType, PoiType> KILN_POI_TYPE = POI_TYPES.register(
        "kiln", () -> new PoiType(ImmutableSet.copyOf(TIERED_KILN_BLOCKS.get(FurnaceTier.BASE).get().getStateDefinition().getPossibleStates()), 1, 1)
    );

    public static final DeferredHolder<VillagerProfession, VillagerProfession> CLAYWORKER_PROFESSION = VILLAGER_PROFESSIONS.register(
        "clayworker", () -> new VillagerProfession(
            "clayworker",
            poiType -> poiType.is(KILN_POI_TYPE.getKey()),
            poiType -> poiType.is(KILN_POI_TYPE.getKey()),
            ImmutableSet.of(),
            ImmutableSet.of(),
            CLAYWORKER_WORK_SOUND_EVENT.get()
        )
    );

	public TieredFurnaces(IEventBus modEventBus, ModContainer modContainer)
    {
		BLOCKS.register(modEventBus);
        BLOCK_ENTITY_TYPES.register(modEventBus);
		ITEMS.register(modEventBus);
		CREATIVE_MODE_TABS.register(modEventBus);
		RECIPE_TYPES.register(modEventBus);
		RECIPE_SERIALIZERS.register(modEventBus);
		STATS.register(modEventBus);
		MENU_TYPES.register(modEventBus);
		SOUND_EVENTS.register(modEventBus);
        POI_TYPES.register(modEventBus);
        VILLAGER_PROFESSIONS.register(modEventBus);

		modEventBus.register(this);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

	@SuppressWarnings("RedundantTypeArguments")
	@SubscribeEvent
	public void registerScreens(RegisterMenuScreensEvent event)
	{
		event.<TieredFurnaceMenu, TieredFurnaceScreen<TieredFurnaceMenu>>register(TIERED_FURNACE_MENU.get(), TieredFurnaceScreen::forFurnace);
		event.<TieredBlastFurnaceMenu, TieredFurnaceScreen<TieredBlastFurnaceMenu>>register(TIERED_BLAST_FURNACE_MENU.get(), TieredFurnaceScreen::forBlastFurnace);
		event.<TieredSmokerMenu, TieredFurnaceScreen<TieredSmokerMenu>>register(TIERED_SMOKER_MENU.get(), TieredFurnaceScreen::forSmoker);
		event.<TieredKilnMenu, TieredFurnaceScreen<TieredKilnMenu>>register(TIERED_KILN_MENU.get(), TieredFurnaceScreen::forKiln);
	}

	@SubscribeEvent
	public void registerRecipeBookCategories(RegisterRecipeBookCategoriesEvent event)
	{
		List<RecipeBookCategories> categories = List.of(
			EnumExtensions.RecipeBookCategories.KILN_SEARCH.getValue(),
			EnumExtensions.RecipeBookCategories.KILN_BLOCKS.getValue(),
			EnumExtensions.RecipeBookCategories.KILN_MISC.getValue()
		);
		event.registerAggregateCategory(EnumExtensions.RecipeBookCategories.KILN_SEARCH.getValue(), categories);
		event.registerBookCategories(EnumExtensions.RecipeBookType.FIRING.getValue(), categories);
		event.registerRecipeCategoryFinder(FIRING_RECIPE_TYPE.get(), recipe ->
		{
			if (recipe.value() instanceof FiringRecipe firingRecipe && firingRecipe.category() == CookingBookCategory.BLOCKS)
				return EnumExtensions.RecipeBookCategories.KILN_BLOCKS.getValue();
			return EnumExtensions.RecipeBookCategories.KILN_MISC.getValue();
		});
	}

    @SubscribeEvent
    public void registerCapabilities(RegisterCapabilitiesEvent event)
    {
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, TIERED_FURNACE_BLOCK_ENTITY.get(),
            (blockEntity, direction) -> blockEntity.getEnergyStorage());
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, TIERED_BLAST_FURNACE_BLOCK_ENTITY.get(),
            (blockEntity, direction) -> blockEntity.getEnergyStorage());
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, TIERED_SMOKER_BLOCK_ENTITY.get(),
            (blockEntity, direction) -> blockEntity.getEnergyStorage());
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, TIERED_KILN_BLOCK_ENTITY.get(),
            (blockEntity, direction) -> blockEntity.getEnergyStorage());
    }

	public static ResourceLocation resourceLocation(String name)
	{
		return ResourceLocation.fromNamespaceAndPath(MODID, name);
	}
}