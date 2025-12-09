package floris0106.tieredfurnaces.entity.npc;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades.EmeraldForItems;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.entity.npc.VillagerTrades.ItemsForEmeralds;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.PotDecorations;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@ParametersAreNonnullByDefault
public class VillagerTrades
{
    public static void registerMasonTrades(Int2ObjectMap<List<ItemListing>> trades)
    {
        List<ItemListing> noviceTrades = trades.get(1);
        noviceTrades.clear();
        noviceTrades.add(new EmeraldForItems(Blocks.STONE, 20, 16, 2));
        noviceTrades.add(new ItemsForEmeralds(Blocks.CHISELED_STONE_BRICKS, 1, 4, 16, 1));

        List<ItemListing> apprenticeTrades = trades.get(2);
        apprenticeTrades.clear();
        apprenticeTrades.add(new EmeraldForItems(Blocks.COBBLED_DEEPSLATE, 16, 16, 10));
        apprenticeTrades.add(new ItemsForEmeralds(Blocks.DEEPSLATE_TILES, 1, 4, 16, 5));

        List<ItemListing> expertTrades = trades.get(4);
        expertTrades.removeIf(itemListing -> itemListing instanceof ItemsForEmeralds);
        expertTrades.add(new EmeraldForItems(Blocks.TUFF, 16, 16, 30));
        expertTrades.add(new ItemsForEmeralds(Blocks.CALCITE, 1, 4, 16, 15));
    }

    public static void registerClayworkerTrades(Int2ObjectMap<List<ItemListing>> trades)
    {
        List<ItemListing> noviceTrades = trades.get(1);
        noviceTrades.add(new EmeraldForItems(Items.CLAY_BALL, 10, 16, 2));
        noviceTrades.add(new ItemsForEmeralds(Items.BRICK, 1, 10, 16, 1));

        List<ItemListing> apprenticeTrades = trades.get(2);
        apprenticeTrades.add(new EmeraldForItems(Blocks.SANDSTONE, 16, 16, 10));
        apprenticeTrades.add(new EmeraldForItems(Blocks.RED_SANDSTONE, 16, 16, 10));
        apprenticeTrades.add(new ItemsForEmeralds(Blocks.SMOOTH_SANDSTONE, 1, 4, 16, 5));
        apprenticeTrades.add(new ItemsForEmeralds(Blocks.SMOOTH_RED_SANDSTONE, 1, 4, 16, 5));

        List<ItemListing> journeymanTrades = trades.get(3);
        for (Block block : new Block[]
            {
                Blocks.ORANGE_TERRACOTTA,
                Blocks.WHITE_TERRACOTTA,
                Blocks.BLUE_TERRACOTTA,
                Blocks.LIGHT_BLUE_TERRACOTTA,
                Blocks.GRAY_TERRACOTTA,
                Blocks.LIGHT_GRAY_TERRACOTTA,
                Blocks.BLACK_TERRACOTTA,
                Blocks.RED_TERRACOTTA,
                Blocks.PINK_TERRACOTTA,
                Blocks.MAGENTA_TERRACOTTA,
                Blocks.LIME_TERRACOTTA,
                Blocks.GREEN_TERRACOTTA,
                Blocks.CYAN_TERRACOTTA,
                Blocks.PURPLE_TERRACOTTA,
                Blocks.YELLOW_TERRACOTTA,
                Blocks.BROWN_TERRACOTTA,
                Blocks.ORANGE_GLAZED_TERRACOTTA,
                Blocks.WHITE_GLAZED_TERRACOTTA,
                Blocks.BLUE_GLAZED_TERRACOTTA,
                Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA,
                Blocks.GRAY_GLAZED_TERRACOTTA,
                Blocks.LIGHT_GRAY_GLAZED_TERRACOTTA,
                Blocks.BLACK_GLAZED_TERRACOTTA,
                Blocks.RED_GLAZED_TERRACOTTA,
                Blocks.PINK_GLAZED_TERRACOTTA,
                Blocks.MAGENTA_GLAZED_TERRACOTTA,
                Blocks.LIME_GLAZED_TERRACOTTA,
                Blocks.GREEN_GLAZED_TERRACOTTA,
                Blocks.CYAN_GLAZED_TERRACOTTA,
                Blocks.PURPLE_GLAZED_TERRACOTTA,
                Blocks.YELLOW_GLAZED_TERRACOTTA,
                Blocks.BROWN_GLAZED_TERRACOTTA
            })
            journeymanTrades.add(new ItemsForEmeralds(block, 1, 2, 16, 10));

        List<ItemListing> expertTrades = trades.get(4);
        expertTrades.add(new EmeraldForItems(Blocks.BASALT, 16, 16, 30));
        expertTrades.add(new ItemsForEmeralds(Blocks.SMOOTH_BASALT, 1, 4, 16, 15));
        expertTrades.add(new ItemsForEmeralds(Items.NETHER_BRICK, 1, 10, 12, 15));

        List<ItemListing> masterTrades = trades.get(5);
        masterTrades.add(new DecoratedPotForEmeralds(1, 30));
    }

    private record DecoratedPotForEmeralds(int maxUses, int villagerXp) implements ItemListing
    {
        private static final List<List<Item>> SHERDS = List.of(
            List.of(
                Items.ARCHER_POTTERY_SHERD,
                Items.MINER_POTTERY_SHERD,
                Items.PRIZE_POTTERY_SHERD,
                Items.SKULL_POTTERY_SHERD
            ),
            List.of(Items.ARMS_UP_POTTERY_SHERD,Items.BREWER_POTTERY_SHERD),
            List.of(
                Items.ANGLER_POTTERY_SHERD,
                Items.SHELTER_POTTERY_SHERD,
                Items.SNORT_POTTERY_SHERD,
                Items.BLADE_POTTERY_SHERD,
                Items.EXPLORER_POTTERY_SHERD,
                Items.MOURNER_POTTERY_SHERD,
                Items.PLENTY_POTTERY_SHERD
            ),
            List.of(
                Items.BURN_POTTERY_SHERD,
                Items.DANGER_POTTERY_SHERD,
                Items.FRIEND_POTTERY_SHERD,
                Items.HEART_POTTERY_SHERD,
                Items.HEARTBREAK_POTTERY_SHERD,
                Items.HOWL_POTTERY_SHERD,
                Items.SHEAF_POTTERY_SHERD
            )
        );

        @Override
        public MerchantOffer getOffer(Entity trader, RandomSource random)
        {
            AtomicInteger cost = new AtomicInteger(16);
            PotDecorations decorations = new PotDecorations(SHERDS.stream().map(sherds ->
            {
                if (random.nextInt(4) != 0)
                    return Items.BRICK;

                cost.addAndGet(12);
                return sherds.get(random.nextInt(sherds.size()));
            }).toList());
            DataComponentMap dataComponents = DataComponentMap.builder()
                .set(DataComponents.POT_DECORATIONS, decorations)
                .build();
            ItemStack pot = new ItemStack(Items.DECORATED_POT);
            pot.applyComponents(dataComponents);

            return new MerchantOffer(new ItemCost(Items.EMERALD, cost.get()), pot, maxUses, villagerXp, 0.2F);
        }
    }
}