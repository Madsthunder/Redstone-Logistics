package continuum.redstonelogistics.tileentity;

import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import continuum.api.multipart.MultipartInfo;
import continuum.essentials.tileentity.CTTileEntity;
import continuum.multipart.tileentity.TileEntityMultiblock;
import continuum.redstonelogistics.blocks.BlockRedstoneWire;
import continuum.redstonelogistics.blocks.BlockWire;
import continuum.redstonelogistics.cuboids.WireCuboids;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;

public abstract class TileEntityWire extends CTTileEntity
{
	private final Class<? extends TileEntityWire> clasz;
	
	public TileEntityWire(Class<? extends TileEntityWire> clasz)
	{
		this.clasz = clasz;
	}
	
	public abstract Map<Pair<EnumFacing, EnumFacing>, WireCuboids> getIntersections();
	
	public Boolean canConnect(EnumFacing attached, EnumFacing to)
	{
		return attached.getAxis() != to.getAxis();
	}
	
	public Boolean canConnect(EnumFacing attached, EnumFacing to, TileEntityMultiblock multiblock)
	{
		if(attached.getAxis() != to.getAxis() && !this.cuboidIntersects(attached, to, multiblock))
		{
			IBlockState state;
			for (MultipartInfo info : multiblock.getAllDataOfTileEntityInstance(this.clasz))
				if(info.getTileEntity() != this && (state = info.getState()).getBlock() instanceof BlockWire)
					return state.getValue(BlockWire.direction) == to;
		}
		return this.canConnect(attached, to);
	}
	
	public Boolean cuboidIntersects(EnumFacing attached, EnumFacing to, TileEntityMultiblock multiblock)
	{
		WireCuboids intersection = this.getIntersections().get(Pair.of(attached, to));
		return intersection == null ? false : multiblock.boxIntersectsMultipart(null, intersection.getSelectableCuboid(), false, true);
	}
}
