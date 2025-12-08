package floris0106.tieredfurnaces.block.entity;

import floris0106.tieredfurnaces.FurnaceTier;
import floris0106.tieredfurnaces.FurnaceType;
import floris0106.tieredfurnaces.TieredFurnaces;
import floris0106.tieredfurnaces.block.ITieredFurnaceBlock;
import floris0106.tieredfurnaces.config.Config;
import floris0106.tieredfurnaces.energy.EnergyStorage;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public abstract class AbstractTieredFurnaceBlockEntity extends AbstractFurnaceBlockEntity
{
	private final FurnaceTier tier;
	private final FurnaceType type;
	private final Component defaultName;
    private final EnergyStorage energyStorage;
	protected final ContainerData containerData = new ContainerData()
	{
		@Override
		public int get(int index)
		{
			return switch (index)
			{
				case 0 -> Mth.floor(litTicks / litDuration * Short.MAX_VALUE);
				case 1 -> Mth.floor(progressTicks / cookingTotalTime * Short.MAX_VALUE);
				case 2 -> energyStorage.getEnergyStored() & 0xFFFF;
				case 3 -> (energyStorage.getEnergyStored() >> 16) & 0xFFFF;
				case 4 -> energyStorage.getMaxEnergyStored() & 0xFFFF;
				case 5 -> (energyStorage.getMaxEnergyStored() >> 16) & 0xFFFF;
				case 6 -> items.get(SLOT_INPUT).is(TieredFurnaces.PELTIER_ELEMENT_ITEM) || items.get(SLOT_FUEL).is(TieredFurnaces.PELTIER_ELEMENT_ITEM) ? 1 : 0;
				default -> 0;
			};
		}

		@Override
		public void set(int index, int value)
		{
			throw new UnsupportedOperationException();
		}

		@Override
		public int getCount()
		{
			return 7;
		}
	};

    private float litTicks;
	private float progressTicks;

	protected AbstractTieredFurnaceBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState, RecipeType<? extends AbstractCookingRecipe> recipeType)
	{
		super(type, pos, blockState, recipeType);
		if (blockState.getBlock() instanceof ITieredFurnaceBlock block)
		{
			tier = block.getTier();
			this.type = block.getType();
			defaultName = block.getName();
		}
		else
			throw new IllegalStateException("Block must be an instance of ITieredFurnaceBlock");
        energyStorage = new EnergyStorage(Config.getEnergyBufferCapacity(tier));
	}

	@Override
	protected boolean isLit()
	{
		return litTicks > 0.0f;
	}

	@Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.loadAdditional(tag, registries);
        litTicks = tag.getFloat("LitTicks");
        progressTicks = tag.getFloat("ProgressTicks");
		energyStorage.setEnergyClamped(tag.getInt("Energy"));
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.saveAdditional(tag, registries);
        tag.putFloat("LitTicks", litTicks);
        tag.putFloat("ProgressTicks", progressTicks);
		tag.putInt("Energy", energyStorage.getEnergyStored());
    }

    @Override
    public void setItem(int index, ItemStack stack)
    {
        super.setItem(index, stack);
        if (index == SLOT_INPUT)
            progressTicks = 0.0f;
    }

    @Override
	protected @NotNull Component getDefaultName()
	{
		return defaultName;
	}

    public EnergyStorage getEnergyStorage()
    {
        return energyStorage;
    }

	public static void serverTick(Level level, BlockPos pos, BlockState state, AbstractTieredFurnaceBlockEntity blockEntity)
	{
		boolean wasLit = blockEntity.isLit();
		boolean changed = false;
		if (blockEntity.isLit())
			blockEntity.litTicks = Math.max(blockEntity.litTicks - blockEntity.tier.getSpeedMultiplier(), 0.0f);

		ItemStack input = blockEntity.items.get(SLOT_INPUT);
		ItemStack fuel = blockEntity.items.get(SLOT_FUEL);
		boolean hasInput = !input.isEmpty();
		boolean hasFuel = !fuel.isEmpty();
		boolean inputIsPeltierElement = input.is(TieredFurnaces.PELTIER_ELEMENT_ITEM);
		boolean fuelIsPeltierElement = fuel.is(TieredFurnaces.PELTIER_ELEMENT_ITEM);
		if (blockEntity.isLit() || hasFuel && hasInput)
		{
			RecipeHolder<? extends AbstractCookingRecipe> recipe = null;
			if (hasInput)
				recipe = blockEntity.quickCheck.getRecipeFor(new SingleRecipeInput(input), level).orElse(null);

            int maxStackSize = blockEntity.getMaxStackSize();

			int generatedEnergy = Mth.floor(Config.PELTIER_ENERGY_GENERATION.get() * blockEntity.tier.getSpeedMultiplier() * blockEntity.type.peltierEnergyMultiplier());
			boolean canGenerate = blockEntity.energyStorage.getEnergyStored() < blockEntity.energyStorage.getMaxEnergyStored() || fuelIsPeltierElement;

			if (!blockEntity.isLit() && ((inputIsPeltierElement && canGenerate) || canBurn(level.registryAccess(), recipe, blockEntity.items, maxStackSize, blockEntity)))
			{
                if (fuelIsPeltierElement)
                {
                    int consumedEnergy = Mth.floor(Config.PELTIER_ENERGY_CONSUMPTION.get() * blockEntity.tier.getSpeedMultiplier() * blockEntity.type.peltierEnergyMultiplier());
                    if (blockEntity.energyStorage.getEnergyStored() < consumedEnergy)
                        blockEntity.litDuration = 0;
                    else
                    {
						blockEntity.energyStorage.setEnergyClamped(blockEntity.energyStorage.getEnergyStored() - consumedEnergy);
                        blockEntity.litDuration = 1;
                    }
                }
                else
                    blockEntity.litDuration = blockEntity.getBurnDuration(fuel);
                blockEntity.litTicks = blockEntity.litDuration;

				if (blockEntity.isLit())
				{
					changed = true;
					if (fuel.hasCraftingRemainingItem())
						blockEntity.items.set(SLOT_FUEL, fuel.getCraftingRemainingItem());
					else if (hasFuel && !fuelIsPeltierElement)
					{
						fuel.shrink(1);
						if (fuel.isEmpty())
							blockEntity.items.set(SLOT_FUEL, fuel.getCraftingRemainingItem());
					}
				}
			}

            if (blockEntity.isLit() && inputIsPeltierElement)
            {
				if (canGenerate)
				{
					blockEntity.progressTicks = 1.0f;
					blockEntity.cookingTotalTime = 1;
					blockEntity.energyStorage.setEnergyClamped(blockEntity.energyStorage.getEnergyStored() + generatedEnergy);
				}
				else
					blockEntity.progressTicks = 0.0f;
            }
			else if (blockEntity.isLit() && canBurn(level.registryAccess(), recipe, blockEntity.items, maxStackSize, blockEntity))
			{
				blockEntity.progressTicks += blockEntity.tier.getSpeedMultiplier();
				if (blockEntity.progressTicks >= blockEntity.cookingTotalTime)
				{
					blockEntity.progressTicks = 0.0f;
					blockEntity.cookingTotalTime = getTotalCookTime(level, blockEntity);
					if (burn(level.registryAccess(), recipe, blockEntity.items, maxStackSize, blockEntity))
						blockEntity.setRecipeUsed(recipe);

					changed = true;
				}
			}
			else
				blockEntity.progressTicks = 0.0f;
		}
		else if (!blockEntity.isLit() && blockEntity.progressTicks > 0.0f)
            blockEntity.progressTicks = Math.max(blockEntity.progressTicks - 2.0f, 0.0f);

		if (fuelIsPeltierElement)
			blockEntity.energyStorage.enableReceiving();
		else if (inputIsPeltierElement)
		{
			blockEntity.energyStorage.enableExtracting();

			for (Direction direction : Direction.values())
				blockEntity.tryOutputEnergy(direction);
		}
		else
			blockEntity.energyStorage.disable();

		if (wasLit != blockEntity.isLit())
		{
			changed = true;
			state = state.setValue(AbstractFurnaceBlock.LIT, blockEntity.isLit());
			level.setBlock(pos, state, 3);
		}

		if (changed)
			setChanged(level, pos, state);
	}

	private void tryOutputEnergy(Direction direction)
	{
		BlockPos pos = worldPosition.relative(direction);
		BlockEntity toEntity = Objects.requireNonNull(level).getBlockEntity(pos);
		if (toEntity == null)
			return;

		IEnergyStorage other = level.getCapability(Capabilities.EnergyStorage.BLOCK, pos, direction.getOpposite());
		if (other == null)
			return;

		energyStorage.extractEnergy(other.receiveEnergy(energyStorage.getEnergyStored(), false), false);
	}
}