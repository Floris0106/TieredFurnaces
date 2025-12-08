package floris0106.tieredfurnaces.config;

import com.google.common.collect.ImmutableMap;
import floris0106.tieredfurnaces.FurnaceTier;

import net.minecraft.util.Mth;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.Map;

public class Config
{
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;

    public static final Map<FurnaceTier, ModConfigSpec.DoubleValue> TIER_MULTIPLIERS;
    public static final ModConfigSpec.DoubleValue PELTIER_ENERGY_GENERATION;
    public static final ModConfigSpec.DoubleValue PELTIER_ENERGY_CONSUMPTION;
    public static final ModConfigSpec.IntValue ENERGY_BUFFER_CAPACITY;
    public static final ModConfigSpec.BooleanValue ENERGY_BUFFER_SCALING;

    public static int getEnergyBufferCapacity(FurnaceTier tier)
    {
        int capacity = ENERGY_BUFFER_CAPACITY.getAsInt();
        if (ENERGY_BUFFER_SCALING.getAsBoolean())
            capacity = Mth.floor(capacity * TIER_MULTIPLIERS.get(tier).getAsDouble());
        return capacity;
    }

    static
    {
        BUILDER
            .comment("Options related to furnace tiers")
            .translation("tieredfurnaces.config.tiers")
            .push("tiers");

        ImmutableMap.Builder<FurnaceTier, ModConfigSpec.DoubleValue> tierSpeedMultipliers = ImmutableMap.builder();
        for (FurnaceTier tier : FurnaceTier.values())
            tierSpeedMultipliers.put(tier, BUILDER
                .comment("Speed multiplier for " + tier.humanReadableName() + " furnaces")
                .translation("tieredfurnaces.config.tiers." + tier.getSerializedName() + ".multiplier")
                .defineInRange(tier.getSerializedName() + "Multiplier", tier.getDefaultSpeedMultiplier(), 0.1, 200.0));
        TIER_MULTIPLIERS = tierSpeedMultipliers.build();

        BUILDER.pop();

        BUILDER
            .comment("Options related to energy and Peltier elements")
            .translation("tieredfurnaces.config.energy")
            .push("energy");

        PELTIER_ENERGY_GENERATION = BUILDER
            .comment("Energy generated per tick when a Peltier element is used as the input in a furnace")
            .comment("Set this to 0 to disable Peltier elements being used for energy generation")
            .translation("tieredfurnaces.config.energy.generation_per_tick")
            .defineInRange("generationPerTick", 20.0, 0.0, 100000.0);

        PELTIER_ENERGY_CONSUMPTION = BUILDER
            .comment("Energy consumed per tick when a Peltier element is used as the fuel in a furnace")
            .comment("Set this to 0 to disable Peltier elements being used as fuel")
            .translation("tieredfurnaces.config.energy.consumption_per_tick")
            .defineInRange("consumptionPerTick", 30.0, 0.0, 100000.0);

        ENERGY_BUFFER_CAPACITY = BUILDER
            .comment("Size of furnaces' energy storage buffer")
            .translation("tieredfurnaces.config.energy.buffer_capacity")
            .defineInRange("bufferCapacity", 100000, 1, 1000000000);

        ENERGY_BUFFER_SCALING = BUILDER
            .comment("Whether the size of furnaces' energy storage buffer should scale with their tier")
            .translation("tieredfurnaces.config.energy.buffer_scaling")
            .define("bufferScaling", false);

        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}