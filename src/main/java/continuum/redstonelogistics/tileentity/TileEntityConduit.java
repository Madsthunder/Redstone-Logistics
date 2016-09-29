package continuum.redstonelogistics.tileentity;

import java.util.HashSet;

import com.google.common.collect.Sets;

import continuum.api.multipart.MultipartInfoList;
import continuum.core.mod.CTCore_OH;
import continuum.essentials.mod.CTMod;
import continuum.essentials.tileentity.TileEntitySyncable;
import continuum.redstonelogistics.cuboids.ConduitCuboids;
import continuum.redstonelogistics.mod.RedLogistics_EH;
import continuum.redstonelogistics.mod.RedLogistics_OH;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public class TileEntityConduit extends TileEntitySyncable
{
	public final CTMod<RedLogistics_OH, RedLogistics_EH> mod = CTCore_OH.mods.get("redlogistics");
	private final Class<? extends TileEntityConduit> clasz;
	private HashSet<EnumFacing> blocked = Sets.newHashSet();
	
	public TileEntityConduit(Class<? extends TileEntityConduit> clasz)
	{
		this.clasz = clasz;
	}
	
	@Override
	public NBTTagCompound writeItemsToNBT()
	{
		NBTTagCompound compound = new NBTTagCompound();
		for (EnumFacing direction : EnumFacing.values())
			compound.setBoolean(direction.getName(), this.blocked.contains(direction));
		return compound;
	}
	
	@Override
	public void readItemsFromNBT(NBTTagCompound compound)
	{
		for (EnumFacing direction : EnumFacing.values())
			if(compound.getBoolean(direction.getName()))
				this.blocked.add(direction);
	}
	
	public boolean canConnect(EnumFacing to)
	{
		return !this.blocked.contains(to) && this.clasz == null ? true : this.clasz.isInstance(this.getWorld().getTileEntity(this.getPos().offset(to)));
	}
	
	public boolean canConnect(EnumFacing to, MultipartInfoList infoList)
	{
		return this.canConnect(to) && !infoList.boxIntersectsList(infoList.findMultipartForEntity(this), ConduitCuboids.values()[to.ordinal() + 1].getShowableCuboid(), true, false);
	}
	
	@Override
	public Block getBlockType()
	{
		if(this.getWorld() != null)
		{
			TileEntity entity = this.getWorld().getTileEntity(this.getPos());
			if(entity.hasCapability(MultipartInfoList.MULTIPARTINFOLIST, null))
				this.blockType = entity.getCapability(MultipartInfoList.MULTIPARTINFOLIST, null).findMultipartForEntity(this).getBlock();
		}
		return super.getBlockType();
	}
}
