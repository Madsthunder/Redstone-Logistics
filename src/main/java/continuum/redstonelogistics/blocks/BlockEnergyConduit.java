package continuum.redstonelogistics.blocks;

import java.util.List;

import com.google.common.collect.Lists;

import continuum.api.redstonelogistics.ConduitSystem;
import continuum.api.redstonelogistics.IConduitEnergyStorage;
import continuum.api.redstonelogistics.IEnergyConduit;
import continuum.essentials.block.ConnectableCuboids;
import continuum.redstonelogistics.tileentity.TileEntityEnergyConduit;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class BlockEnergyConduit extends BlockConduit implements IEnergyConduit
{
	private final int maxTransferRate;
	private final int maxSaturation;
	
	public BlockEnergyConduit(String name, ConnectableCuboids cuboids, int maxTransferRate, int maxSaturation)
	{
		super(name, Material.IRON, cuboids);
		this.maxTransferRate = maxTransferRate;
		this.maxSaturation = maxSaturation;
	}
	
	@Override
	public boolean canConnectTo(IBlockAccess access, BlockPos pos, EnumFacing direction)
	{
		return super.canConnectTo(access, pos, direction);
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state)
	{
		super.breakBlock(world, pos, state);
		List<ConduitSystem> updated = Lists.newArrayList();
		for(EnumFacing side : EnumFacing.values())
		{
			TileEntity entity = world.getTileEntity(pos.offset(side));
			if(entity != null && entity.hasCapability(CapabilityEnergy.ENERGY, side.getOpposite()))
			{
				IEnergyStorage storage = entity.getCapability(CapabilityEnergy.ENERGY, side.getOpposite());
				if(storage instanceof IConduitEnergyStorage)
				{
					ConduitSystem system = ((IConduitEnergyStorage)storage).getConduitSystem();
					if(!updated.contains(system))
					{
						system.update((IConduitEnergyStorage)storage);
						updated.add(system);
					}
				}
			}
		}
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state)
	{
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state)
	{
		return new TileEntityEnergyConduit(this.maxSaturation, this.maxTransferRate);
	}
	
	@Override
	public int getMaxSaturation(IBlockAccess access, BlockPos pos)
	{
		return this.maxSaturation;
	}
	
	@Override
	public int getMaxTransferRate(IBlockAccess access, BlockPos pos)
	{
		return this.maxTransferRate;
	}
}