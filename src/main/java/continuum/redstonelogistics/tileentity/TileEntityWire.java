package continuum.redstonelogistics.tileentity;

import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import continuum.api.multipart.MultipartState;
import continuum.api.multipart.MultipartStateList;
import continuum.essentials.tileentity.TileEntitySyncable;
import continuum.redstonelogistics.blocks.BlockWire;
import continuum.redstonelogistics.cuboids.WireCuboids;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;

public abstract class TileEntityWire extends TileEntitySyncable
{
	private final Class<? extends TileEntityWire> clasz;
	
	public TileEntityWire(Class<? extends TileEntityWire> clasz)
	{
		this.clasz = clasz;
	}
	
	public abstract Map<Pair<EnumFacing, EnumFacing>, WireCuboids> getIntersections();
	
	public boolean canConnect(EnumFacing attached, EnumFacing to)
	{
		return attached.getAxis() != to.getAxis();
	}
	
	public boolean canConnect(EnumFacing attached, EnumFacing to, MultipartStateList infoList)
	{
		if(attached.getAxis() != to.getAxis() && !this.cuboidIntersects(attached, to, infoList))
		{
			IBlockState state;
			for (MultipartState info : infoList.getAllInfoOfTileEntityInstance(this.clasz))
				if(info.getTileEntity() != this && (state = info.getState()).getBlock() instanceof BlockWire)
					return state.getValue(BlockWire.direction) == to;
		}
		return this.canConnect(attached, to);
	}
	
	public boolean cuboidIntersects(EnumFacing attached, EnumFacing to, MultipartStateList infoList)
	{
		WireCuboids intersection = this.getIntersections().get(Pair.of(attached, to));
		return intersection == null ? false : infoList.boxIntersectsList(null, intersection.getSelectableCuboid(), false, true);
	}
}
