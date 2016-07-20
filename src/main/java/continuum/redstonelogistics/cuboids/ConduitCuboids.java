package continuum.redstonelogistics.cuboids;

import continuum.essentials.block.BlockConnectable;
import continuum.essentials.block.ICuboid;
import continuum.essentials.block.StaticCuboid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;

public enum ConduitCuboids implements ICuboid
{
	CORE(new AxisAlignedBB(6, 6, 6, 10, 10, 10), new AxisAlignedBB(5.25, 5.25, 5.25, 10.75, 10.75, 10.75), null),
	DOWN(new AxisAlignedBB(6, 0, 6, 10, 5.25, 10), new AxisAlignedBB(6, 0, 6, 10, 6, 10), EnumFacing.DOWN),
	UP(new AxisAlignedBB(6, 10.75, 6, 10, 16, 10), new AxisAlignedBB(6, 10, 6, 10, 16, 10), EnumFacing.UP),
	NORTH(new AxisAlignedBB(6, 6, 0, 10, 10, 5.25), new AxisAlignedBB(6, 6, 0, 10, 10, 6), EnumFacing.NORTH),
	SOUTH(new AxisAlignedBB(6, 6, 10.75, 10, 10, 16), new AxisAlignedBB(6, 6, 10, 10, 10, 16), EnumFacing.SOUTH),
	WEST(new AxisAlignedBB(0, 6, 6, 5.25, 10, 10), new AxisAlignedBB(0, 6, 6, 6, 10, 10), EnumFacing.WEST),
	EAST(new AxisAlignedBB(10.75, 6, 6, 16, 10, 10), new AxisAlignedBB(10, 6, 6, 16, 10, 10), EnumFacing.EAST),
	CORE_D(new AxisAlignedBB(6, 5.25, 6, 10, 6, 10), new AxisAlignedBB(5.25, 5.25, 5.25, 10.75, 10.75, 10.75), EnumFacing.DOWN, true),
	CORE_U(new AxisAlignedBB(6, 10, 6, 10, 10.75, 10), new AxisAlignedBB(5.25, 5.25, 5.25, 10.75, 10.75, 10.75), EnumFacing.UP, true),
	CORE_N(new AxisAlignedBB(6, 6, 5.25, 10, 10, 6), new AxisAlignedBB(5.25, 5.25, 5.25, 10.75, 10.75, 10.75), EnumFacing.NORTH, true),
	CORE_S(new AxisAlignedBB(6, 6, 10, 10, 10, 10.75), new AxisAlignedBB(5.25, 5.25, 5.25, 10.75, 10.75, 10.75), EnumFacing.SOUTH, true),
	CORE_W(new AxisAlignedBB(5.25, 6, 6, 6, 10, 10), new AxisAlignedBB(5.25, 5.25, 5.25, 10.75, 10.75, 10.75), EnumFacing.WEST, true),
	CORE_E(new AxisAlignedBB(10, 6, 6, 10.75, 10, 10), new AxisAlignedBB(5.25, 5.25, 5.25, 10.75, 10.75, 10.75), EnumFacing.EAST, true);
	
	private final AxisAlignedBB selectable, showable;
	private final Integer direction;
	private final Boolean conditional;
	
	private ConduitCuboids(AxisAlignedBB selectable, AxisAlignedBB showable, EnumFacing direction)
	{
		this(selectable, showable, direction, false);
	}
	
	private ConduitCuboids(AxisAlignedBB selectable, AxisAlignedBB showable, EnumFacing direction, Boolean conditional)
	{
		selectable = new AxisAlignedBB(selectable.minX / 16, selectable.minY / 16, selectable.minZ / 16, selectable.maxX / 16, selectable.maxY / 16, selectable.maxZ / 16);
		this.selectable = selectable;
		showable = new AxisAlignedBB(showable.minX / 16, showable.minY / 16, showable.minZ / 16, showable.maxX / 16, showable.maxY / 16, showable.maxZ / 16);
		this.showable = showable;
		this.direction = direction == null ? null : direction.ordinal();
		this.conditional = conditional;
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
	public ICuboid addExtraData(Object data)
	{
		return this;
	}
	
	@Override
	public Object getExtraData()
	{
		return null;
	}
	
	@Override
	public Boolean isUsable(Object obj)
	{
		if(this.conditional && this.direction != null && obj instanceof IBlockState)
		{
			IBlockState state = (IBlockState)obj;
			if(state.getBlock() instanceof BlockConnectable)
				return state.getValue(BlockConnectable.isConnected[this.direction]);
		}
		return true;
	}
	
	@Override
	public ICuboid copy()
	{
		return new StaticCuboid(this);
	}
}
