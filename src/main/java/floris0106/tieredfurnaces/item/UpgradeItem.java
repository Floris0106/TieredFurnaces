package floris0106.tieredfurnaces.item;

import floris0106.tieredfurnaces.FurnaceTier;
import floris0106.tieredfurnaces.block.ITieredFurnaceBlock;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class UpgradeItem extends Item
{
	private final FurnaceTier from;
	private final FurnaceTier to;

	public UpgradeItem(Properties properties, FurnaceTier from, FurnaceTier to)
	{
		super(properties);
		this.from = from;
		this.to = to;
	}

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        if (context.getLevel() instanceof ServerLevel serverLevel)
        {
            BlockPos pos = context.getClickedPos();
            BlockState blockState = serverLevel.getBlockState(pos);
            if (blockState.getBlock() instanceof ITieredFurnaceBlock tieredFurnace && tieredFurnace.getTier() == from)
            {
                context.getItemInHand().consume(1, context.getPlayer());
                tieredFurnace.setTier(to, serverLevel, pos, blockState);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    public FurnaceTier getFrom()
	{
		return from;
	}

	public FurnaceTier getTo()
	{
		return to;
	}
}