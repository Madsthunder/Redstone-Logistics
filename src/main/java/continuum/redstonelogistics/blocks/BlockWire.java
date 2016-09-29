package continuum.redstonelogistics.blocks;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;

import continuum.api.multipart.MultipartState;
import continuum.essentials.block.BlockConnectable;
import continuum.essentials.block.CuboidSelector;
import continuum.essentials.block.IBlockBoundable;
import continuum.essentials.block.ICuboid;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.BlockStateContainer.Builder;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;

public abstract class BlockWire extends Block implements IBlockBoundable
{
	public static final PropertyDirection direction = PropertyDirection.create("direction");
	private final List<ICuboid> cuboids;
	private AxisAlignedBB bounds = Block.FULL_BLOCK_AABB;
	
	public BlockWire(Material material, String name, ICuboid... cuboids)
	{
		super(material);
		this.cuboids = Arrays.asList(cuboids);
		this.setRegistryName(name + "_wire");
		this.setUnlocalizedName(name + "_wire");
		IBlockState state = this.getDefaultState();
		IExtendedBlockState state1 = state instanceof IExtendedBlockState ? (IExtendedBlockState)state : null;
		if(state1 != null)
		{
			for (IUnlistedProperty<Boolean> connected : BlockConnectable.isConectedUnlisted)
				state1 = state1.withProperty(connected, false);
			state = state1;
		}
		this.setDefaultState(state.withProperty(direction, EnumFacing.DOWN));
	}
	
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(direction, EnumFacing.values()[meta]);
	}
	
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(direction).ordinal();
	}
	
	@Override
	public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase entity)
	{
		return this.getDefaultState().withProperty(direction, facing.getOpposite());
	}
	
	/**
	 * CALL THIS INSTEAD OF
	 * {@link Block#getActualState(IBlockState, IBlockAccess, BlockPos)}.
	 */
	@Override
	public IBlockState getExtendedState(IBlockState state, IBlockAccess access, BlockPos pos)
	{
		if(state instanceof IExtendedBlockState)
		{
			IExtendedBlockState state1 = (IExtendedBlockState)state;
			Integer[] ordinals;
			switch(state.getValue(direction).getAxis())
			{
				case X :
					ordinals = new Integer[] { 0, 1, 2, 3 };
					break;
				case Y :
					ordinals = new Integer[] { 2, 3, 4, 5 };
					break;
				case Z :
					ordinals = new Integer[] { 0, 1, 4, 5 };
					break;
				default:
					ordinals = new Integer[0];
			}
			for (Integer ordinal : ordinals)
				state1 = state1.withProperty(BlockConnectable.isConectedUnlisted[ordinal], this.canConnectTo(state.getValue(direction), access, pos, EnumFacing.values()[ordinal]));
			return state1;
		}
		return super.getActualState(state, access, pos);
	}
	
	@Override
	public RayTraceResult collisionRayTrace(IBlockState state, World world, BlockPos pos, Vec3d start, Vec3d end)
	{
		return CuboidSelector.getSelectionBox(this, world, pos, start, end, this.getCuboids(state.getBlock().getExtendedState(state, world, pos)));
	}
	
	@Override
	public RayTraceResult rayTrace(BlockPos pos, Vec3d start, Vec3d end, AxisAlignedBB aabb)
	{
		return super.rayTrace(pos, start, end, aabb);
	}
	
	public List<ICuboid> getCuboids(IBlockState state)
	{
		List<ICuboid> cuboids = Lists.newArrayList();
		for (ICuboid cuboid : this.cuboids)
			if(cuboid.isUsable(state))
				cuboids.add(cuboid);
		return cuboids;
	}
	
	@Override
	public void setBlockBounds(AxisAlignedBB aabb)
	{
		
		this.bounds = aabb;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess access, BlockPos pos)
	{
		return this.bounds;
	}
	
	@Override
	public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB aabb, List list, Entity entity)
	{
		List<ICuboid> cuboids = this.getCuboids(state.getBlock().getExtendedState(state, world, pos));
		AxisAlignedBB cuboid;
		if(!cuboids.isEmpty() && aabb.intersectsWith((cuboid = cuboids.get(0).getShowableCuboid())))
			list.add(cuboid);
	}
	
	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}
	
	public abstract Boolean canConnectTo(EnumFacing attached, IBlockAccess access, BlockPos pos, EnumFacing direction);
	
	public Boolean canConnectToMultipart(MultipartState info, EnumFacing direction)
	{
		return false;
	}
	
	public BlockStateContainer createBlockState()
	{
		return new Builder(this).add(direction).add(BlockConnectable.isConectedUnlisted).build();
	}
}
