package continuum.redstonelogistics.blocks;

import java.util.List;

import com.google.common.collect.Lists;

import continuum.api.redstonelogistics.ConduitSystem;
import continuum.api.redstonelogistics.IConduitEnergyStorage;
import continuum.essentials.block.BlockConnectableBounds;
import continuum.essentials.block.ConnectableCuboids;
import continuum.redstonelogistics.tileentity.TileEntityConduit;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class BlockConduit extends BlockConnectableBounds
{
	
	public BlockConduit(String name, Material material, ConnectableCuboids cuboids)
	{
		super(material, cuboids);
		this.setUnlocalizedName(name + "_conduit");
		this.setRegistryName(name + "_conduit");
		this.translucent = true;
		this.fullBlock = false;
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return 0;
	}
	
	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}
	
	@Override
	public boolean canConnectTo(IBlockAccess access, BlockPos pos, EnumFacing direction)
	{
		TileEntity entity = access.getTileEntity(pos);
		return entity instanceof TileEntityConduit ? ((TileEntityConduit)entity).canConnect(direction) : false;
	}
}
