package continuum.redstonelogistics.mod;

import continuum.essentials.mod.CTMod;
import continuum.redstonelogistics.loaders.BlockLoader;
import continuum.redstonelogistics.loaders.ClientLoader;
import continuum.redstonelogistics.loaders.ItemLoader;
import continuum.redstonelogistics.loaders.UtilityLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = "RedLogistics", name = "Redstone Logistics", version = "0.0.1", dependencies = "required-after:CTCore")
public class RedLogistics_Mod extends CTMod<RedLogistics_OH, RedLogistics_EH>
{
	
	public RedLogistics_Mod()
	{
		super(RedLogistics_OH.getHolder(), new BlockLoader(), new ItemLoader(), new UtilityLoader(), new ClientLoader());
	}
	
	@Mod.EventHandler
	public void construction(FMLConstructionEvent event)
	{
		super.construction(event);
	}
	
	@Mod.EventHandler
	public void pre(FMLPreInitializationEvent event)
	{
		super.pre(event);
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		super.init(event);
	}
	
	@Mod.EventHandler
	public void post(FMLPostInitializationEvent event)
	{
		super.post(event);
	}
}
