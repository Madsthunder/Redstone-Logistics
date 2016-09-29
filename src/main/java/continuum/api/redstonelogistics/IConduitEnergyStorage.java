package continuum.api.redstonelogistics;

import continuum.redstonelogistics.tileentity.TileEntityEnergyConduit;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;

public interface IConduitEnergyStorage extends IEnergyStorage
{
	public static class Impl extends EnergyStorage implements IConduitEnergyStorage
	{
		private ConduitSystem system;
		private TileEntityEnergyConduit conduit;
		private boolean ticked = false;
		
		public Impl()
		{
			super(0, 0);
		}
		
		public Impl(TileEntityEnergyConduit conduit, int maxSaturation, int maxTransferRate)
		{
			super(maxSaturation, maxTransferRate);
			this.conduit = conduit;
		}
		
		protected IConduitEnergyStorage setMaxSaturation(int maxSaturation)
		{
			this.capacity = maxSaturation;
			return this;
		}
		
		protected IConduitEnergyStorage setTransferRate(int maxTransferRate)
		{
			this.maxReceive = this.maxExtract = maxTransferRate;
			return this;
		}
		@Override
		public int receiveEnergy(int maxRecieve, boolean simulate)
		{
			if(this.canReceive())
				return this.system.distributeEnergy(maxRecieve, simulate);
			else
				return 0;
		}
		
		@Override
		public boolean canReceive()
		{
			return this.system != null && !this.system.isTicked();
		}
		
		public World getWorld()
		{
			return this.conduit.getWorld();
		}
		
		public BlockPos getPos()
		{
			return this.conduit.getPos();
		}
		
		public int getTransferRate()
		{
			return this.maxReceive;
		}
		
		public int recieveEnergySystem(int maxReceive, boolean simulate)
		{
			if(this.canReceive())
			{
		        int received = Math.min(this.capacity - this.energy, Math.min(this.maxReceive, maxReceive));
		        if (!simulate)
		            this.energy += received;
		        return received;
			}
			else
				return 0;
	    }

		@Override
		public int extractEnergySystem(int maxExtract, boolean simulate)
		{
			if(this.canExtract())
			{
		        int extracted = Math.min(this.energy, Math.min(this.maxExtract, maxExtract));
		        if (!simulate)
		            this.energy -= extracted;
		        return extracted;
			}
			else
	            return 0;
	    }
		
		@Override
		public ConduitSystem getConduitSystem()
		{
			return this.system;
		}

		@Override
		public void setConduitSystem(ConduitSystem system)
		{
			this.system = system;
		}
	}
	
	public World getWorld();
	public BlockPos getPos();
	public int getTransferRate();
	public int recieveEnergySystem(int maxTransfer, boolean simulate);
	public int extractEnergySystem(int maxTransfer, boolean simulate);
	public ConduitSystem getConduitSystem();
	public void setConduitSystem(ConduitSystem system);
}
