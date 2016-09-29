package continuum.api.redstonelogistics;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

public enum TransferType
{
	DISABLED(false, false),
	INPUT(true, false),
	OUTPUT(false, true),
	BOTH(true, true);
	
	private final boolean input;
	private final boolean output;
	
	private TransferType(boolean input, boolean output)
	{
		this.input = input;
		this.output = output;
	}
	
	public boolean canInput()
	{
		return this.input;
	}
	
	public boolean canOutput()
	{
		return this.output;
	}
}
