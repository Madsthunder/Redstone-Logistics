package continuum.redstonelogistics.blocks;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.base.Predicate;

import continuum.essentials.block.BlockConnectable;
import continuum.essentials.block.BlockConnectableBounds;
import continuum.essentials.block.ConnectableCuboids;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockConduit extends BlockConnectableBounds
{
	public BlockConduit(String name, Material material, ConnectableCuboids cuboids)
	{
		this(name, material, new CanConnect(), cuboids);
	}
	
	public BlockConduit(String name, Material material, Predicate<Pair<IBlockAccess, BlockPos>> predicate, ConnectableCuboids cuboids)
	{
		super(material, predicate, cuboids);
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
	
	public static class CanConnect implements Predicate<Pair<IBlockAccess, BlockPos>>
	{
		@Override
		public boolean apply(Pair<IBlockAccess, BlockPos> input)
		{
			IBlockAccess access = input.getLeft();
			BlockPos pos = input.getRight();
			IBlockState state = access.getBlockState(pos);
			if(state.getBlock() instanceof BlockConnectable)
				return true;
			return false;
		}
	}
}
