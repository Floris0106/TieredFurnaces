package floris0106.tieredfurnaces;

import net.fabric_extras.structure_pool.api.StructurePoolAPI;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;

import floris0106.tieredfurnaces.entity.npc.VillagerTrades;

@EventBusSubscriber
public class EventHandler
{
    @SubscribeEvent
    public static void registerVillagerTrades(VillagerTradesEvent event)
    {
        if (event.getType() == VillagerProfession.MASON)
            VillagerTrades.registerMasonTrades(event.getTrades());
        else if (event.getType() == TieredFurnaces.CLAYWORKER_PROFESSION.get())
            VillagerTrades.registerClayworkerTrades(event.getTrades());
    }

    @SubscribeEvent
    public static void injectStructures(ServerStartingEvent event)
    {
        MinecraftServer server = event.getServer();

        StructurePoolAPI.injectIntoStructurePool(
            server,
            ResourceLocation.withDefaultNamespace("village/desert/houses"),
            TieredFurnaces.resourceLocation("village/desert/houses/desert_clayworker_1"),
            2
        );
        StructurePoolAPI.injectIntoStructurePool(
            server,
            ResourceLocation.withDefaultNamespace("village/plains/houses"),
            TieredFurnaces.resourceLocation("village/plains/houses/plains_clayworkers_house_1"),
            2
        );
        StructurePoolAPI.injectIntoStructurePool(
            server,
            ResourceLocation.withDefaultNamespace("village/savanna/houses"),
            TieredFurnaces.resourceLocation("village/savanna/houses/savanna_clayworker_1"),
            2
        );
        StructurePoolAPI.injectIntoStructurePool(
            server,
            ResourceLocation.withDefaultNamespace("village/snowy/houses"),
            TieredFurnaces.resourceLocation("village/snowy/houses/snowy_clayworkers_house_1"),
            2
        );
        StructurePoolAPI.injectIntoStructurePool(
            server,
            ResourceLocation.withDefaultNamespace("village/snowy/houses"),
            TieredFurnaces.resourceLocation("village/snowy/houses/snowy_clayworkers_house_2"),
            2
        );
        StructurePoolAPI.injectIntoStructurePool(
            server,
            ResourceLocation.withDefaultNamespace("village/taiga/houses"),
            TieredFurnaces.resourceLocation("village/taiga/houses/taiga_clayworkers_house_1"),
            2
        );
    }
}