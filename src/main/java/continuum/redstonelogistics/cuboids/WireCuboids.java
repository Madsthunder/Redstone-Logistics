package continuum.redstonelogistics.cuboids;

import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Maps;

import continuum.essentials.block.BlockConnectable;
import continuum.essentials.block.ICuboid;
import continuum.essentials.block.StaticCuboid;
import continuum.redstonelogistics.blocks.BlockWire;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.property.IExtendedBlockState;

public enum WireCuboids implements ICuboid
{
	CORE_DOWN(EnumFacing.DOWN, 5.25, 0.0, 5.25, 10.75, 2.0, 10.75, 0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
	CORE_UP(EnumFacing.UP, 5.25, 14.0, 5.25, 10.75, 16.0, 10.75, 0.0, 14.0, 0.0, 16.0, 16.0, 16.0),
	CORE_NORTH(EnumFacing.NORTH, 5.25, 5.25, 0.0, 10.75, 10.75, 2.0, 0.0, 0.0, 0.0, 16.0, 16.0, 2.0),
	CORE_SOUTH(EnumFacing.SOUTH, 5.25, 5.25, 14.0, 10.75, 10.75, 16.0, 0.0, 0.0, 14.0, 16.0, 16.0, 16.0),
	CORE_WEST(EnumFacing.WEST, 0.0, 5.25, 5.25, 2.0, 10.75, 10.75, 0.0, 0.0, 0.0, 2.0, 16.0, 16.0),
	CORE_EAST(EnumFacing.EAST, 14.0, 5.25, 5.25, 16.0, 10.75, 10.75, 14.0, 0.0, 0.0, 16.0, 16.0, 16.0),
	DOWN_NORTH(EnumFacing.DOWN, EnumFacing.NORTH, CORE_DOWN, 5.25, 0.0, 0.0, 10.75, 2.0, 5.25),
	DOWN_SOUTH(EnumFacing.DOWN, EnumFacing.SOUTH, CORE_DOWN, 5.25, 0.0, 10.75, 10.75, 2.0, 16.0),
	DOWN_WEST(EnumFacing.DOWN, EnumFacing.WEST, CORE_DOWN, 0.0, 0.0, 5.25, 5.25, 2.0, 10.75),
	DOWN_EAST(EnumFacing.DOWN, EnumFacing.EAST, CORE_DOWN, 10.75, 0.0, 5.25, 16.0, 2.0, 10.75),
	UP_NORTH(EnumFacing.UP, EnumFacing.NORTH, CORE_UP, 5.25, 14.0, 0.0, 10.75, 16.0, 5.25),
	UP_SOUTH(EnumFacing.UP, EnumFacing.SOUTH, CORE_UP, 5.25, 14.0, 10.75, 10.75, 16.0, 16.0),
	UP_WEST(EnumFacing.UP, EnumFacing.WEST, CORE_UP, 0.0, 14.0, 5.25, 5.25, 16.0, 10.75),
	UP_EAST(EnumFacing.UP, EnumFacing.EAST, CORE_UP, 10.75, 14.0, 5.25, 16.0, 16.0, 10.75),
	NORTH_DOWN(EnumFacing.NORTH, EnumFacing.DOWN, CORE_NORTH, 5.25, 0.0, 0.0, 10.75, 5.25, 2.0),
	NORTH_UP(EnumFacing.NORTH, EnumFacing.UP, CORE_NORTH, 5.25, 10.75, 0.0, 10.75, 16.0, 2.0),
	NORTH_WEST(EnumFacing.NORTH, EnumFacing.WEST, CORE_NORTH, 0.0, 5.25, 0.0, 5.25, 10.75, 2.0),
	NORTH_EAST(EnumFacing.NORTH, EnumFacing.EAST, CORE_NORTH, 10.75, 5.25, 0.0, 16.0, 10.75, 2.0),
	SOUTH_DOWN(EnumFacing.SOUTH, EnumFacing.DOWN, CORE_SOUTH, 5.25, 0.0, 14.0, 10.75, 5.25, 16.0),
	SOUTH_UP(EnumFacing.SOUTH, EnumFacing.UP, CORE_SOUTH, 5.25, 10.75, 14.0, 10.75, 16.0, 16.0),
	SOUTH_WEST(EnumFacing.SOUTH, EnumFacing.WEST, CORE_SOUTH, 0.0, 5.25, 14.0, 5.25, 10.75, 16.0),
	SOUTH_EAST(EnumFacing.SOUTH, EnumFacing.EAST, CORE_SOUTH, 10.75, 5.25, 14.0, 16.0, 10.75, 16.0),
	WEST_DOWN(EnumFacing.WEST, EnumFacing.DOWN, CORE_WEST, 0.0, 0.0, 5.25, 2.0, 5.25, 10.75),
	WEST_UP(EnumFacing.WEST, EnumFacing.UP, CORE_WEST, 0.0, 10.75, 5.25, 2.0, 16.0, 10.75),
	WEST_NORTH(EnumFacing.WEST, EnumFacing.NORTH, CORE_WEST, 0.0, 5.25, 0.0, 2.0, 10.75, 5.25),
	WEST_SOUTH(EnumFacing.WEST, EnumFacing.SOUTH, CORE_WEST, 0.0, 5.25, 10.75, 2.0, 10.75, 16.0),
	EAST_DOWN(EnumFacing.EAST, EnumFacing.DOWN, CORE_EAST, 14.0, 0.0, 5.25, 16.0, 5.25, 10.75),
	EAST_UP(EnumFacing.EAST, EnumFacing.UP, CORE_EAST, 14.0, 10.75, 5.25, 16.0, 16.0, 10.75),
	EAST_NORTH(EnumFacing.EAST, EnumFacing.NORTH, CORE_EAST, 14.0, 5.25, 0.0, 16.0, 10.75, 5.25),
	EAST_SOUTH(EnumFacing.EAST, EnumFacing.SOUTH, CORE_EAST, 14.0, 5.25, 10.75, 16.0, 10.75, 16.0);
	
	public static final Map<Pair<EnumFacing, EnumFacing>, WireCuboids> planesToIntersection = Maps.newHashMap();
	
	private final EnumFacing direction, subDirection;
	private final AxisAlignedBB selectable, showable;
	
	private WireCuboids(EnumFacing direction, Double... coords)
	{
		this(direction, null, null, coords);
	}
	
	private WireCuboids(EnumFacing direction, EnumFacing subDirection, WireCuboids base, Double... coords)
	{
		this.direction = direction;
		this.subDirection = subDirection;
		this.selectable = new AxisAlignedBB(coords[0] / 16, coords[1] / 16, coords[2] / 16, coords[3] / 16, coords[4] / 16, coords[5] / 16);
		this.showable = base == null ? new AxisAlignedBB(coords[6] / 16, coords[7] / 16, coords[8] / 16, coords[9] / 16, coords[10] / 16, coords[11] / 16) : base.getShowableCuboid();
	}
	
	@Override
	public AxisAlignedBB getSelectableCuboid()
	{
		return this.selectable;
	}
	
	@Override
	public AxisAlignedBB getShowableCuboid()
	{
		return this.showable;
	}
	
	@Override
	public ICuboid addExtraData(Object obj)
	{
		return this;
	}
	
	@Override
	public Object getExtraData()
	{
		return this.direction;
	}
	
	@Override
	public ICuboid copy()
	{
		return new StaticCuboid(this);
	}
	
	@Override
	public Boolean isUsable(Object obj)
	{
		return obj instanceof IBlockState ? ((IBlockState)obj).getValue(BlockWire.direction) == this.direction && (this.canBeIntercepted() ? obj instanceof IExtendedBlockState ? ((IExtendedBlockState)obj).getValue(BlockConnectable.isConectedUnlisted[this.subDirection.ordinal()]) : false : true) : false;
	}
	
	private Boolean canBeIntercepted()
	{
		return this.subDirection != null;
	}
	
	static
	{
		for (WireCuboids cuboid : values())
			if(cuboid.canBeIntercepted())
				planesToIntersection.put(Pair.of(cuboid.direction, cuboid.subDirection), cuboid);
	}
}
