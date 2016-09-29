package continuum.redstonelogistics.multiparts;

import java.util.List;

import continuum.api.multipart.Multipart;
import continuum.api.multipart.MultipartInfo;
import continuum.api.multipart.MultipartInfoList;
import continuum.essentials.block.ICuboid;
import continuum.redstonelogistics.blocks.BlockWire;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.registry.RegistryDelegate;

@Optional.Interface(iface = "continuum.api.multipart.Multipart", modid = "ctmultipart")
public class MultipartWire extends Multipart
{
	private final RegistryDelegate<Block> del;
	
	public MultipartWire(BlockWire block)
	{
		this.del = block.delegate;
	}
	
	@Override
	@Optional.Method(modid = "ctmultipart")
	public boolean canPlaceIn(IBlockAccess access, BlockPos pos, IBlockState state, MultipartInfoList infoList, RayTraceResult result)
	{
		return false;
	}
	
	@Override
	@Optional.Method(modid = "ctmultipart")
	public List<ICuboid> getSelectableCuboids(MultipartInfo info)
	{
		return null;
	}
	
	@Override
	@Optional.Method(modid = "ctmultipart")
	public Block getBlock()
	{
		return this.del.get();
	}
}
