package continuum.redstonelogistics.mod;

import continuum.essentials.block.ConnectableCuboids;
import continuum.essentials.mod.ObjectHolder;
import continuum.redstonelogistics.blocks.BlockConduit;
import continuum.redstonelogistics.blocks.BlockRedstoneWire;
import continuum.redstonelogistics.client.model.ModelWire;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RedLogistics_OH implements ObjectHolder
{
	private static RedLogistics_OH holder;
	
	static RedLogistics_OH getHolder()
	{
		return holder == null ? new RedLogistics_OH(RedLogistics_Mod.class.getAnnotation(Mod.class)) : holder;
	}
	
	public final Mod mod;
	
	private RedLogistics_OH(Mod mod)
	{
		this.mod = mod;
	}
	
	@Override
	public String getModid()
	{
		return this.mod.modid();
	}
	
	@Override
	public String getName()
	{
		return this.mod.name();
	}
	
	@Override
	public String getVersion()
	{
		return this.mod.version();
	}
	
	public BlockConduit bronze_conduit;
	
	public BlockRedstoneWire redstone_wire;
	
	public ConnectableCuboids conduit_cuboids;
	
	@SideOnly(Side.CLIENT)
	public ModelWire wireModel;
}
