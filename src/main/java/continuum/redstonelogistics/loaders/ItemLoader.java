package continuum.redstonelogistics.loaders;

import continuum.essentials.mod.CTMod;
import continuum.essentials.mod.ObjectLoader;
import continuum.redstonelogistics.mod.RedLogistics_EH;
import continuum.redstonelogistics.mod.RedLogistics_OH;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ItemLoader implements ObjectLoader<RedLogistics_OH, RedLogistics_EH>
{
	@Override
	public void pre(CTMod<RedLogistics_OH, RedLogistics_EH> mod)
	{
		RedLogistics_OH holder = mod.getObjectHolder();
		ForgeRegistries.ITEMS.register(new ItemBlock(holder.bronze_conduit).setUnlocalizedName(holder.bronze_conduit.getUnlocalizedName()).setRegistryName(holder.bronze_conduit.getRegistryName()));
		ForgeRegistries.ITEMS.register(new ItemBlock(holder.redstone_wire).setUnlocalizedName(holder.redstone_wire.getUnlocalizedName()).setRegistryName(holder.redstone_wire.getRegistryName()));
	}
	
	@Override
	public String getName()
	{
		return "Items";
	}
}
