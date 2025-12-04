package floris0106.tieredfurnaces.block;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import floris0106.tieredfurnaces.FurnaceTier;
import floris0106.tieredfurnaces.FurnaceType;
import floris0106.tieredfurnaces.TieredFurnaces;
import floris0106.tieredfurnaces.block.entity.AbstractTieredFurnaceBlockEntity;
import floris0106.tieredfurnaces.block.entity.TieredSmokerBlockEntity;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SmokerBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TieredSmokerBlock extends SmokerBlock implements ITieredFurnaceBlock
{
	public static final MapCodec<TieredSmokerBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		propertiesCodec(),
		FurnaceTier.CODEC.fieldOf("tier").forGetter(TieredSmokerBlock::getTier)
	).apply(instance, TieredSmokerBlock::new));

	private final FurnaceTier tier;

	public TieredSmokerBlock(Properties properties, FurnaceTier tier)
	{
		super(properties);
		this.tier = tier;
	}

	@Override
	public MapCodec<SmokerBlock> codec()
	{
		return CODEC.flatXmap(DataResult::success, smoker ->
		{
			if (smoker instanceof TieredSmokerBlock tieredSmoker)
				return DataResult.success(tieredSmoker);
			else
				return DataResult.error(() -> "Block must be an instance of TieredSmokerBlock");
		});
	}

	@Override
	public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType)
	{
		return level.isClientSide ? null : createTickerHelper(blockEntityType, TieredFurnaces.TIERED_SMOKER_BLOCK_ENTITY.get(), AbstractTieredFurnaceBlockEntity::serverTick);
	}

	@Override
	protected void openContainer(Level level, BlockPos pos, Player player)
	{
		if (level.getBlockEntity(pos) instanceof TieredSmokerBlockEntity blockEntity)
		{
			player.openMenu(blockEntity);
			player.awardStat(Stats.INTERACT_WITH_SMOKER);
		}
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
	{
		return new TieredSmokerBlockEntity(pos, state);
	}

    @Override
    public FurnaceType getType()
    {
        return FurnaceType.SMOKER;
    }

	@Override
	public FurnaceTier getTier() {
		return tier;
	}
}