package continuum.redstonelogistics.blocks;

import continuum.api.redstonelogistics.IRedstoneWire;
import continuum.essentials.block.BlockConnectable;
import continuum.redstonelogistics.cuboids.WireCuboids;
import continuum.redstonelogistics.tileentity.TileEntityRedstoneWire;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.BlockStateContainer.Builder;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRedstoneWire extends BlockWire implements IRedstoneWire
{
	public static final PropertyBool powered = PropertyBool.create("powered");
	private Boolean powerDisabled;
	
	public BlockRedstoneWire(Material material, String name)
	{
		super(material, name, WireCuboids.values());
		this.setDefaultState(this.getDefaultState().withProperty(powered, false));
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return super.getMetaFromState(state) + (state.getValue(powered) ? 6 : 0);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		Boolean powered = meta >= 6;
		return super.getStateFromMeta(powered ? meta - 6 : meta).withProperty(BlockRedstoneWire.powered, powered);
	}
	
	@Override
	public Boolean canConnectTo(EnumFacing attached, IBlockAccess access, BlockPos pos, EnumFacing direction)
	{
		IBlockState state = access.getBlockState(pos.offset(direction));
		TileEntity entity = access.getTileEntity(pos);
		if(state.getBlock() instanceof BlockRedstoneWire && entity instanceof TileEntityRedstoneWire)
			return state.getValue(BlockWire.direction) == attached && ((TileEntityRedstoneWire)entity).canConnect(attached, direction);
		return false;
	}
	
	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block)
	{
		world.setBlockState(pos, state.withProperty(powered, world.getStrongPower(pos.offset(state.getValue(direction))) >= 15));
	}
	
	protected void setPowerDisabled(Boolean disabled)
	{
		this.powerDisabled = disabled;
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state)
	{
		return true;
	}
	
	public TileEntity createTileEntity(World world, IBlockState state)
	{
		return new TileEntityRedstoneWire();
	}
	
	@Override
	public ResourceLocation getTexture(IBlockState state)
	{
		return new ResourceLocation("redlogistics", "blocks/redstone_wire_" + (state.getValue(powered) ? "on" : "off"));
	}
	
	public BlockStateContainer createBlockState()
	{
		return new Builder(this).add(direction, powered).add(BlockConnectable.isConectedUnlisted).build();
	}
}
