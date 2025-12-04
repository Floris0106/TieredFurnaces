package floris0106.tieredfurnaces.block;

import floris0106.tieredfurnaces.FurnaceTier;
import floris0106.tieredfurnaces.FurnaceType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.Map;
import java.util.Objects;

public interface ITieredFurnaceBlock
{
    Component getName();

    FurnaceType getType();

	FurnaceTier getTier();
    default void setTier(FurnaceTier tier, ServerLevel level, BlockPos pos, BlockState state)
    {
        RegistryAccess access = level.registryAccess();

        CompoundTag tag = Objects.requireNonNull(level.getBlockEntity(pos)).saveWithoutMetadata(access);
        level.removeBlockEntity(pos);

        BlockState newState = getType().getRegisteredBlocks().get(tier).get().defaultBlockState();
        for (Map.Entry<Property<?>, Comparable<?>> entry : state.getValues().entrySet())
            newState = newState.setValue(entry.getKey(), uncheck(entry.getValue()));
        level.setBlockAndUpdate(pos, newState);

        Objects.requireNonNull(level.getBlockEntity(pos)).loadWithComponents(tag, access);
    }

    @SuppressWarnings("unchecked")
    static <T> T uncheck(Object object)
    {
        return (T) object;
    }
}