package continuum.api.redstonelogistics;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public interface IEnergyConduit
{
	public int getMaxSaturation(IBlockAccess access, BlockPos pos);
	public int getMaxTransferRate(IBlockAccess access, BlockPos pos);
}
