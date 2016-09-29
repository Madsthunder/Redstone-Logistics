package continuum.redstonelogistics.tileentity;

import continuum.api.redstonelogistics.ITileColorable;
import continuum.core.mod.CTCore_OH;
import continuum.essentials.mod.CTMod;
import continuum.redstonelogistics.mod.RedLogistics_EH;
import continuum.redstonelogistics.mod.RedLogistics_OH;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public class TileEntityConduitColored extends TileEntityConduit implements ITileColorable
{
	public final CTMod<RedLogistics_OH, RedLogistics_EH> mod = CTCore_OH.mods.get("redlogistics");
	private ConduitColors color = ConduitColors.NONE;
	
	public TileEntityConduitColored(Class<? extends TileEntityConduit> clasz)
	{
		super(clasz);
	}
	
	@Override
	public NBTTagCompound writeItemsToNBT()
	{
		NBTTagCompound compound = new NBTTagCompound();
		compound.merge(super.writeItemsToNBT());
		compound.merge(this.color.writeToNBT());
		return compound;
	}
	
	@Override
	public void readItemsFromNBT(NBTTagCompound compound)
	{
		super.readItemsFromNBT(compound);
		this.color = ConduitColors.readFromNBT(compound);
	}
	
	@Override
	public boolean canConnect(EnumFacing to)
	{
		TileEntity entity = this.getWorld().getTileEntity(this.getPos().offset(to));
		return super.canConnect(to) && entity instanceof ITileColorable ? ((ITileColorable)entity).getColor().canConnectTo(this) : false;
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
}
