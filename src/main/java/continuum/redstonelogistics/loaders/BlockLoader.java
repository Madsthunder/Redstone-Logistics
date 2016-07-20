package continuum.redstonelogistics.loaders;

import continuum.essentials.block.ConnectableCuboids;
import continuum.essentials.mod.CTMod;
import continuum.essentials.mod.ObjectLoader;
import continuum.redstonelogistics.blocks.BlockConduit;
import continuum.redstonelogistics.blocks.BlockDummy;
import continuum.redstonelogistics.blocks.BlockRedstoneWire;
import continuum.redstonelogistics.cuboids.ConduitCuboids;
import continuum.redstonelogistics.mod.RedLogistics_EH;
import continuum.redstonelogistics.mod.RedLogistics_OH;
import continuum.redstonelogistics.tileentity.TileEntityRedstoneWire;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class BlockLoader implements ObjectLoader<RedLogistics_OH, RedLogistics_EH>
{
	@Override
	public void pre(CTMod<RedLogistics_OH, RedLogistics_EH> mod)
	{
		RedLogistics_OH holder = mod.getObjectHolder();
		holder.conduit_cuboids = new ConnectableCuboids(ConduitCuboids.values());
		ForgeRegistries.BLOCKS.register(holder.bronze_conduit = new BlockConduit("bronze", Material.IRON, holder.conduit_cuboids));
		ForgeRegistries.BLOCKS.register(holder.redstone_wire = new BlockRedstoneWire(Material.CIRCUITS, "alloy"));
		TileEntity.addMapping(TileEntityRedstoneWire.class, "redstone_wire");
	}
	
	@Override
	public String getName()
	{
		return "Blocks";
	}
}
