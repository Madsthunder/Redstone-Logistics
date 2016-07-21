package continuum.redstonelogistics.multiparts;

import java.util.List;

import continuum.api.multipart.MultipartInfo;
import continuum.api.multipart.TileEntityMultiblock;
import continuum.api.multipart.implementations.Multipart;
import continuum.essentials.block.ICuboid;
import continuum.redstonelogistics.blocks.BlockWire;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.common.registry.RegistryDelegate;

public class MultipartWire extends Multipart
{
	private final RegistryDelegate<Block> del;
	
	public MultipartWire(BlockWire block)
	{
		this.del = block.delegate;
	}
	
	@Override
	public boolean canPlaceIn(IBlockAccess access, BlockPos pos, IBlockState state, TileEntityMultiblock source, RayTraceResult result)
	{
		return false;
	}
	
	@Override
	public List<ICuboid> getCuboids(MultipartInfo info)
	{
		return null;
	}
	
	@Override
	public Block getBlock()
	{
		return this.del.get();
	}
}
