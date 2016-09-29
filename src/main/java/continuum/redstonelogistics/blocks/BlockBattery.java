package continuum.redstonelogistics.blocks;

import continuum.essentials.client.state.ITextured;
import continuum.redstonelogistics.tileentity.TileEntityBattery;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class BlockBattery extends Block implements ITextured
{
	private final ResourceLocation texture;
	private final int transferRate;
	private final int maxEnergy;
	
	public BlockBattery(String name, ResourceLocation texture, int transferRate, int maxEnergy)
	{
		super(Material.IRON);
		this.setRegistryName(name + "battery");
		this.setUnlocalizedName(name + "_battery");
		this.texture = texture;
		this.transferRate = transferRate;
		this.maxEnergy = maxEnergy;
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state)
	{
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state)
	{
		return new TileEntityBattery(this.maxEnergy, this.transferRate);
	}

	@Override
	public ResourceLocation getTexture(IBlockState state)
	{
		return this.texture;
	}
}
