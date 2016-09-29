package continuum.redstonelogistics.tileentity;

import continuum.api.multipart.MultipartStateList;
import continuum.api.redstonelogistics.ConduitSystem;
import continuum.api.redstonelogistics.IConduitEnergyStorage;
import continuum.redstonelogistics.cuboids.ConduitCuboids;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;

public class TileEntityEnergyConduit extends TileEntityConduitColored
{
	private IConduitEnergyStorage storage;
	
	public TileEntityEnergyConduit()
	{
		super(null);
	}
	
	public TileEntityEnergyConduit(int maxSaturation, int maxTransferRate)
	{
		this();
		this.storage = new IConduitEnergyStorage.Impl(this, maxSaturation, maxTransferRate);
	}
	
	public boolean canConnect(EnumFacing to)
	{
		TileEntity entity = this.getWorld().getTileEntity(this.getPos().offset(to));
		return super.canConnect(to) && entity != null && entity.hasCapability(CapabilityEnergy.ENERGY, to.getOpposite());
	}
	
	@Override
	public boolean hasCapability(Capability capability, EnumFacing side)
	{
		if(capability == CapabilityEnergy.ENERGY && this.storage != null)
		{
			TileEntity entity = this.getWorld().getTileEntity(this.getPos());
			if(entity.hasCapability(MultipartStateList.MULTIPARTINFOLIST, null))
			{
				MultipartStateList infoList = entity.getCapability(MultipartStateList.MULTIPARTINFOLIST, null);
				return super.canConnect(side) && !infoList.boxIntersectsList(infoList.findMultipartForEntity(this), ConduitCuboids.values()[side.ordinal() + 1].getShowableCuboid(), true, false);
			}
			else
				return super.canConnect(side);
		}
		else
			return super.hasCapability(capability, side);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing side)
	{
		if(capability == CapabilityEnergy.ENERGY)
			return (T)this.storage;
		else
			return super.getCapability(capability, side);
	}
	
	@Override
	public void updateContainingBlockInfo()
	{
		super.updateContainingBlockInfo();
		if(this.getWorld() != null)
		{
			this.storage.setConduitSystem(new ConduitSystem(this.storage));
		}
	}
	
	@Override
	public Block getBlockType()
	{
		return super.getBlockType();
	}
}
