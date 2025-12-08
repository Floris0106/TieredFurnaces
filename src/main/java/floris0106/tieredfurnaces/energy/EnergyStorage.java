package floris0106.tieredfurnaces.energy;

import net.minecraft.util.Mth;
import net.neoforged.neoforge.energy.IEnergyStorage;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class EnergyStorage implements IEnergyStorage
{
	private final int capacity;
	private int energy;
	private boolean receivingEnabled;
	private boolean extractingEnabled;

	public EnergyStorage(int capacity)
	{
		this.capacity = capacity;
		energy = 0;
		receivingEnabled = false;
		extractingEnabled = false;
	}

	public void enableReceiving()
	{
		receivingEnabled = true;
		extractingEnabled = false;
	}

	public void enableExtracting()
	{
		receivingEnabled = false;
		extractingEnabled = true;
	}

	public void disable()
	{
		receivingEnabled = false;
		extractingEnabled = false;
	}

	public void setEnergyClamped(int energy)
	{
		this.energy = Mth.clamp(energy, 0, capacity);
	}

	@Override
	public int receiveEnergy(int amount, boolean simulate)
	{
		if (!canReceive() || amount <= 0)
			return 0;

		int energyReceived = Math.min(this.capacity - this.energy, amount);
		if (!simulate)
			this.energy += energyReceived;
		return energyReceived;
	}

	@Override
	public int extractEnergy(int amount, boolean simulate)
	{
		if (!canExtract() || amount <= 0)
			return 0;

		int energyExtracted = Math.min(this.energy, amount);
		if (!simulate)
			this.energy -= energyExtracted;
		return energyExtracted;
	}

	@Override
	public int getEnergyStored()
	{
		return energy;
	}

	@Override
	public int getMaxEnergyStored()
	{
		return capacity;
	}

	@Override
	public boolean canExtract()
	{
		return extractingEnabled;
	}

	@Override
	public boolean canReceive()
	{
		return receivingEnabled;
	}
}