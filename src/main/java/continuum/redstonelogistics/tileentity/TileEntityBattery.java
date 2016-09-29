package continuum.redstonelogistics.tileentity;

import java.util.Map;

import com.google.common.collect.Maps;

import continuum.api.redstonelogistics.StorageSide;
import continuum.api.redstonelogistics.TransferType;
import continuum.essentials.tileentity.TileEntitySyncable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import scala.reflect.internal.Trees.This;

public class TileEntityBattery extends TileEntitySyncable implements ITickable
{
	private final IEnergyStorage storage;
	private final Map<EnumFacing, StorageSide> sides;
	
	public TileEntityBattery(int transferRate, int maxStorage)
	{
		this.storage = new EnergyStorage(maxStorage, transferRate);
		this.sides = Maps.newHashMap();
		for(EnumFacing direction : EnumFacing.values())
			this.sides.put(direction, new StorageSide(storage, TransferType.BOTH));
	}

	@Override
	public NBTTagCompound writeItemsToNBT()
	{
		NBTTagCompound compound = new NBTTagCompound();
		compound.setTag("energy", CapabilityEnergy.ENERGY.writeNBT(this.storage, null));
		for(EnumFacing side : EnumFacing.values())
		{
			TransferType type = this.sides.get(side).getTransferType();
			if(type != TransferType.DISABLED)
				compound.setByte(side.getName(), (byte)type.ordinal());
		}
		return compound;
	}

	@Override
	public void readItemsFromNBT(NBTTagCompound compound)
	{
		CapabilityEnergy.ENERGY.readNBT(this.storage, null, compound.getTag("energy"));
		for(EnumFacing side : EnumFacing.values())
			this.sides.put(side, new StorageSide(this.storage, TransferType.values()[compound.getByte(side.getName())]));
	}

	@Override
	public void update()
	{
		for(EnumFacing side : EnumFacing.values())
			if(this.sides.get(side).canExtract())
			{
				TileEntity entity = this.getWorld().getTileEntity(this.getPos().offset(side));
				if(entity != null && entity.hasCapability(CapabilityEnergy.ENERGY, side.getOpposite()))
					this.storage.extractEnergy(entity.getCapability(CapabilityEnergy.ENERGY, side.getOpposite()).receiveEnergy(this.storage.extractEnergy(Integer.MAX_VALUE, true), false), false);
			}
		System.out.println(this.storage.getEnergyStored());
	}
}
