package floris0106.tieredfurnaces.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import floris0106.tieredfurnaces.FurnaceTier;
import floris0106.tieredfurnaces.FurnaceType;
import floris0106.tieredfurnaces.TieredFurnaces;
import floris0106.tieredfurnaces.block.entity.AbstractTieredFurnaceBlockEntity;
import floris0106.tieredfurnaces.block.entity.TieredKilnBlockEntity;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TieredKilnBlock extends AbstractFurnaceBlock implements ITieredFurnaceBlock
{
	public static final MapCodec<TieredKilnBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		propertiesCodec(),
		FurnaceTier.CODEC.fieldOf("tier").forGetter(TieredKilnBlock::getTier)
	).apply(instance, TieredKilnBlock::new));

	private final FurnaceTier tier;

	public TieredKilnBlock(Properties properties, FurnaceTier tier)
	{
		super(properties);
		this.tier = tier;
	}

	@Override
	protected MapCodec<TieredKilnBlock> codec()
	{
		return CODEC;
	}

	@Override
	public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType)
	{
		return level.isClientSide ? null : createTickerHelper(blockEntityType, TieredFurnaces.TIERED_KILN_BLOCK_ENTITY.get(), AbstractTieredFurnaceBlockEntity::serverTick);
	}

	@Override
	protected void openContainer(Level level, BlockPos pos, Player player)
	{
		if (level.getBlockEntity(pos) instanceof TieredKilnBlockEntity blockEntity)
		{
			player.openMenu(blockEntity);
			player.awardStat(TieredFurnaces.INTERACT_WITH_KILN_STAT.get());
		}
	}

	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state)
	{
		return new TieredKilnBlockEntity(pos, state);
	}

	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random)
	{
		if (!state.getValue(LIT))
			return;

		double x = pos.getX() + 0.5;
		double y = pos.getY();
		double z = pos.getZ() + 0.5;
		if (random.nextDouble() < 0.1)
			level.playLocalSound(x, y, z, TieredFurnaces.KILN_FIRE_CRACKLE_SOUND_EVENT.get(), SoundSource.BLOCKS, 1.0F, 1.0F, false);

		Direction facing = state.getValue(FACING);
		Direction.Axis axis = facing.getAxis();
		x += axis == Direction.Axis.X ? facing.getStepX() * 0.52 : random.nextDouble() * 0.4 - 0.2;
		y += (random.nextDouble() * 11.0 + 2.0) / 16.0;
		z += axis == Direction.Axis.Z ? facing.getStepZ() * 0.52 : random.nextDouble() * 0.4 - 0.2;
		level.addParticle(ParticleTypes.FLAME, x, y, z, 0.0, 0.0, 0.0);
	}

	@Override
    public FurnaceType getType()
    {
        return FurnaceType.KILN;
    }

	@Override
    public FurnaceTier getTier() {
        return tier;
    }
}