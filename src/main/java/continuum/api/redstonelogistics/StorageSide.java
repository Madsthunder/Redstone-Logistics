package continuum.api.redstonelogistics;

import net.minecraftforge.energy.IEnergyStorage;

public class StorageSide implements IEnergyStorage
{
	private final IEnergyStorage storage;
	private final TransferType type;
	
	public StorageSide(IEnergyStorage storage, TransferType type)
	{
		this.storage = storage;
		this.type = type;
	}
	
	public TransferType getTransferType()
	{
		return this.type;
	}

	@Override
	public int receiveEnergy(int maxReceive, boolean simulate)
	{
		if(this.canReceive())
			return this.storage.receiveEnergy(maxReceive, simulate);
		else
			return 0;
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate)
	{
		if(this.canExtract())
			return this.storage.receiveEnergy(maxExtract, simulate);
		else
			return 0;
	}

	@Override
	public int getEnergyStored()
	{
		return this.storage.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored()
	{
		return this.storage.getMaxEnergyStored();
	}

	@Override
	public boolean canExtract()
	{
		return this.type.canOutput();
	}

	@Override
	public boolean canReceive()
	{
		return this.type.canInput();
	}

}
