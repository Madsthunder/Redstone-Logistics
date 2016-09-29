package continuum.redstonelogistics.loaders;

import continuum.essentials.block.ConnectableCuboids;
import continuum.essentials.mod.CTMod;
import continuum.essentials.mod.ObjectLoader;
import continuum.redstonelogistics.blocks.BlockEnergyConduit;
import continuum.redstonelogistics.blocks.BlockRedstoneWire;
import continuum.redstonelogistics.cuboids.ConduitCuboids;
import continuum.redstonelogistics.mod.RedLogistics_EH;
import continuum.redstonelogistics.mod.RedLogistics_OH;
import continuum.redstonelogistics.tileentity.TileEntityBattery;
import continuum.redstonelogistics.tileentity.TileEntityEnergyConduit;
import continuum.redstonelogistics.tileentity.TileEntityRedstoneWire;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class BlockLoader implements ObjectLoader<RedLogistics_OH, RedLogistics_EH>
{
	@Override
	public void pre(CTMod<RedLogistics_OH, RedLogistics_EH> mod)
	{
		RedLogistics_OH holder = mod.getObjectHolder();
		holder.conduit_cuboids = new ConnectableCuboids(ConduitCuboids.values());
		ForgeRegistries.BLOCKS.register(holder.bronze_conduit = new BlockEnergyConduit("bronze_energy", holder.conduit_cuboids, 20, 1000));
		ForgeRegistries.BLOCKS.register(holder.redstone_wire = new BlockRedstoneWire(Material.CIRCUITS, "alloy"));
		TileEntity.addMapping(TileEntityEnergyConduit.class, "energy_conduit");
		TileEntity.addMapping(TileEntityBattery.class, "battery");
		TileEntity.addMapping(TileEntityRedstoneWire.class, "redstone_wire");
	}
	
	@Override
	public String getName()
	{
		return "Blocks";
	}
}
