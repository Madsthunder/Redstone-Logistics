package continuum.redstonelogistics.tileentity;

import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import continuum.api.redstonelogistics.ITileColorable;
import continuum.essentials.tileentity.CTTileEntity;
import continuum.redstonelogistics.cuboids.WireCuboids;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

public class TileEntityRedstoneWire extends TileEntityWire implements ITileColorable
{
	private ConduitColors color = ConduitColors.NONE;
	
	public TileEntityRedstoneWire()
	{
		super(TileEntityRedstoneWire.class);
	}
	
	@Override
	public NBTTagCompound writeItemsToNBT()
	{
		NBTTagCompound compound = new NBTTagCompound();
		compound.merge(this.color.writeToNBT());
		return compound;
	}
	
	@Override
	public void readItemsFromNBT(NBTTagCompound compound)
	{
		this.color = ConduitColors.readFromNBT(compound);
	}
	
	@Override
	public ConduitColors getColor()
	{
		return this.color;
	}
	
	@Override
	public void setColor(ConduitColors color)
	{
		this.color = color;
	}
	
	@Override
	public Map<Pair<EnumFacing, EnumFacing>, WireCuboids> getIntersections()
	{
		return WireCuboids.planesToIntersection;
	}
}
