package floris0106.tieredfurnaces.mixin;

import net.minecraft.world.level.block.BlastFurnaceBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FurnaceBlock;
import net.minecraft.world.level.block.SmokerBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import floris0106.tieredfurnaces.FurnaceTier;
import floris0106.tieredfurnaces.block.TieredBlastFurnaceBlock;
import floris0106.tieredfurnaces.block.TieredFurnaceBlock;
import floris0106.tieredfurnaces.block.TieredSmokerBlock;

@Mixin(Blocks.class)
public abstract class BlocksMixin
{
	@Redirect(method = "<clinit>", at = @At(value = "NEW", target = "net/minecraft/world/level/block/FurnaceBlock"))
	private static FurnaceBlock tieredfurnaces$overrideFurnaceBlock(BlockBehaviour.Properties properties)
	{
		return new TieredFurnaceBlock(properties, FurnaceTier.BASE);
	}

	@Redirect(method = "<clinit>", at = @At(value = "NEW", target = "net/minecraft/world/level/block/BlastFurnaceBlock"))
	private static BlastFurnaceBlock tieredfurnaces$overrideBlastFurnaceBlock(BlockBehaviour.Properties properties)
	{
		return new TieredBlastFurnaceBlock(properties, FurnaceTier.BASE);
	}

	@Redirect(method = "<clinit>", at = @At(value = "NEW", target = "net/minecraft/world/level/block/SmokerBlock"))
	private static SmokerBlock tieredfurnaces$overrideSmokerBlock(BlockBehaviour.Properties properties)
	{
		return new TieredSmokerBlock(properties, FurnaceTier.BASE);
	}
}