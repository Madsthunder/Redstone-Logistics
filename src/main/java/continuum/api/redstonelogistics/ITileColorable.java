package continuum.api.redstonelogistics;

import net.minecraft.item.EnumDyeColor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IStringSerializable;

public interface ITileColorable
{
	public ConduitColors getColor();
	
	public void setColor(ConduitColors color);
	
	public static enum ConduitColors
	{
		WHITE,
		ORANGE,
		MAGENTA,
		LIGHT_BLUE,
		YELLOW,
		LIME,
		PINK,
		GRAY,
		SILVER,
		CYAN,
		PURPLE,
		BLUE,
		BROWN,
		GREEN,
		RED,
		BLACK,
		NONE;
		
		public EnumDyeColor getColor()
		{
			if(this != NONE)
				return EnumDyeColor.values()[this.ordinal()];
			return null;
		}
		
		public Boolean canConnectTo(ITileColorable tile)
		{
			return this.canConnectTo(tile.getColor());
		}
		
		public Boolean canConnectTo(ConduitColors color)
		{
			return color == NONE || this == NONE ? true : color == this;
		}
		
		public NBTTagCompound writeToNBT()
		{
			NBTTagCompound compound = new NBTTagCompound();
			if(this == NONE)
				return compound;
			compound.setByte("color", (byte)this.ordinal());
			return compound;
		}
		
		public static ConduitColors readFromNBT(NBTTagCompound compound)
		{
			if(compound.hasKey("color"))
				return values()[compound.getByte("color")];
			return NONE;
		}
	}
}
