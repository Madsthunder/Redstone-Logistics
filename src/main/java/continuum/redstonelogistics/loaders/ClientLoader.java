package continuum.redstonelogistics.loaders;

import com.google.common.base.Functions;

import continuum.core.mod.CTCore_OH;
import continuum.essentials.mod.CTMod;
import continuum.essentials.mod.ObjectLoader;
import continuum.redstonelogistics.client.model.ModelWire;
import continuum.redstonelogistics.mod.RedLogistics_EH;
import continuum.redstonelogistics.mod.RedLogistics_OH;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ClientLoader implements ObjectLoader<RedLogistics_OH, RedLogistics_EH>
{
	@SideOnly(Side.CLIENT)
	@Override
	public void pre(CTMod<RedLogistics_OH, RedLogistics_EH> mod)
	{
		RedLogistics_OH holder = mod.getObjectHolder();
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(holder.bronze_conduit), 0, new ModelResourceLocation("redlogistics:bronze_energy_conduit", "inventory"));
		CTCore_OH.models.put(new ResourceLocation(holder.getModid(), "models/block/wire"), Functions.constant(holder.wireModel = new ModelWire()));
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void init(CTMod<RedLogistics_OH, RedLogistics_EH> mod)
	{
		IResourceManager manager = Minecraft.getMinecraft().getResourceManager();
		if(manager instanceof IReloadableResourceManager)
			((IReloadableResourceManager)manager).registerReloadListener(mod.getObjectHolder().wireModel);
	}
	
	@Override
	public String getName()
	{
		return "Client";
	}
	
	@Override
	public Side getSide()
	{
		return Side.CLIENT;
	}
}
