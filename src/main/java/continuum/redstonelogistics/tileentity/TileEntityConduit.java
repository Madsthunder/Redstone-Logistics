package continuum.redstonelogistics.tileentity;

import continuum.api.multipart.BlockMultipart;
import continuum.api.multipart.MultipartInfo;
import continuum.api.multipart.TileEntityMultiblock;
import continuum.core.mod.Core_OH;
import continuum.essentials.mod.CTMod;
import continuum.essentials.tileentity.CTTileEntity;
import continuum.redstonelogistics.cuboids.ConduitCuboids;
import continuum.redstonelogistics.mod.RedLogistics_EH;
import continuum.redstonelogistics.mod.RedLogistics_OH;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

public class TileEntityConduit extends CTTileEntity
{
	public final CTMod<RedLogistics_OH, RedLogistics_EH> mod = Core_OH.mods.get("redlogistics");
	private Boolean[] blocked = new Boolean[] { false, false, false, false, false, false };
	
	public TileEntityConduit()
	{
		super(true);
	}
	
	@Override
	public NBTTagCompound writeItemsToNBT()
	{
		NBTTagCompound compound = new NBTTagCompound();
		for (Integer i = 0; i < this.blocked.length; i++)
			compound.setBoolean(EnumFacing.values()[i].getName(), this.blocked[i]);
		return compound;
	}
	
	@Override
	public void readItemsFromNBT(NBTTagCompound compound)
	{
		for (Integer i = 0; i < this.blocked.length; i++)
			this.blocked[i] = compound.getBoolean(EnumFacing.values()[i].getName());
	}
	
	public Boolean canConnect(EnumFacing to)
	{
		return !blocked[to.ordinal()];
	}
	
	public Boolean canConnect(EnumFacing to, TileEntityMultiblock multiblock)
	{
		return this.canConnect(to) && !multiblock.boxIntersectsMultipart(multiblock.findMultipartForEntity(this), ConduitCuboids.values()[to.ordinal() + 1].getShowableCuboid(), true, false);
	}
	
	@Override
	public Block getBlockType()
	{
		Block block = super.getBlockType();
		if(block instanceof BlockMultipart)
		{
			MultipartInfo info = ((TileEntityMultiblock)this.getWorld().getTileEntity(this.getPos())).findInfoForEntity(this);
			if(info != null)
				this.blockType = info.getBlock();
		}
		return this.blockType;
	}
}
