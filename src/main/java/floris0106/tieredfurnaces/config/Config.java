package floris0106.tieredfurnaces.config;

import com.google.common.collect.ImmutableMap;
import floris0106.tieredfurnaces.FurnaceTier;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.Map;

public class Config
{
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;

    public static final Map<FurnaceTier, ModConfigSpec.DoubleValue> TIER_SPEED_MULTIPLIERS;
    public static final ModConfigSpec.DoubleValue PELTIER_ENERGY_GENERATION;
    public static final ModConfigSpec.DoubleValue PELTIER_ENERGY_CONSUMPTION;

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
                .translation("tieredfurnaces.config.tiers." + tier.getSerializedName() + ".speed_multiplier")
                .defineInRange(tier.getSerializedName() + "_speed_multiplier", tier.getDefaultSpeedMultiplier(), 0.1, 200.0));
        TIER_SPEED_MULTIPLIERS = tierSpeedMultipliers.build();

        BUILDER.pop();

        BUILDER
            .comment("Options related to Peltier elements")
            .translation("tieredfurnaces.config.peltier")
            .push("peltier");

        PELTIER_ENERGY_GENERATION = BUILDER
            .comment("Energy generated per tick when a Peltier element is used as the input in a furnace")
            .comment("Set this to 0 to disable Peltier elements being used for energy generation")
            .translation("tieredfurnaces.config.peltier.generation_per_tick")
            .defineInRange("generationPerTick", 20.0, 0.0, 100000.0);

        PELTIER_ENERGY_CONSUMPTION = BUILDER
            .comment("Energy consumed per tick when a Peltier element is used as the fuel in a furnace")
            .comment("Set this to 0 to disable Peltier elements being used as fuel")
            .translation("tieredfurnaces.config.peltier.consumption_per_tick")
            .defineInRange("consumptionPerTick", 30.0, 0.0, 100000.0);

        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}