package floris0106.tieredfurnaces;

import floris0106.tieredfurnaces.entity.npc.VillagerTrades;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;

@EventBusSubscriber
public class EventHandler
{
    @SubscribeEvent
    public static void registerVillagerTrades(VillagerTradesEvent event)
    {
        if (event.getType() == VillagerProfession.MASON)
            VillagerTrades.registerMasonTrades(event.getTrades());
        else if (event.getType() == TieredFurnaces.POTTER_PROFESSION.get())
            VillagerTrades.registerPotterTrades(event.getTrades());
    }
}