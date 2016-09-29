package continuum.api.redstonelogistics;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import continuum.redstonelogistics.mod.RedLogistics_EH;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public final class ConduitSystem
{
	private final Map<IConduitEnergyStorage, Integer> conduits = Maps.newHashMap();
	private int maxPower;
	private boolean ticked = false;
	
	public ConduitSystem(IConduitEnergyStorage base)
	{
		RedLogistics_EH.systems.add(this);
		this.update(base);
		System.out.println(this.conduits);
	}
	
	public void update(IConduitEnergyStorage from)
	{
		for(IConduitEnergyStorage storage : this.conduits.keySet())
		{
			storage.setConduitSystem(null);
			this.conduits.remove(storage);
		}
		this.maxPower = 0;
		this.conduits.putAll(getConduits(from));
		if(this.conduits.isEmpty())
		{
			RedLogistics_EH.systems.remove(this);
			return;
		}
		for(IConduitEnergyStorage storage : this.conduits.keySet())
			storage.setConduitSystem(this);
		for(int p : this.conduits.values())
			this.maxPower += p;
	}
	
	public int distributeEnergy(int maxDistribute, boolean simulate)
	{
		int power = Math.min(this.maxPower, maxDistribute);
		int remaining = power;
		List<IConduitEnergyStorage> list = Lists.newArrayList(this.conduits.keySet());
		while(!list.isEmpty() && list.size() <= remaining)
		{
			int average = remaining / list.size();
			for(IConduitEnergyStorage storage : Sets.newHashSet(list))
			{
				remaining -= storage.recieveEnergySystem(average, simulate);
				if(storage.getEnergyStored() >= storage.getMaxEnergyStored())
					list.remove(storage);
			}
		}
		Random r = new Random();
		while(remaining > 0 && !list.isEmpty())
		{
			IConduitEnergyStorage storage = list.get(r.nextInt(list.size()));
			remaining -= storage.recieveEnergySystem(1, simulate);
			list.remove(storage);
		}
		this.ticked = !simulate;
		return power - remaining;
	}
	
	public boolean isTicked()
	{
		return this.ticked;
	}
	
	public void resetTicked()
	{
		this.ticked = false;
	}
	
	public void removeStorage(IConduitEnergyStorage storage)
	{
		if(this.conduits.remove(storage) != null)
		{
			storage.setConduitSystem(null);
			RedLogistics_EH.systems.remove(this);
			List<ConduitSystem> systems = Lists.newArrayList();
			for(IConduitEnergyStorage storage1 : this.conduits.keySet())
				storage1.setConduitSystem(null);
			for(IConduitEnergyStorage storage1 : this.conduits.keySet())
				if(!systems.contains(storage1.getConduitSystem()))
					systems.add(new ConduitSystem(storage1));			
		}
	}
	
	public static Map<IConduitEnergyStorage, Integer> getConduits(IConduitEnergyStorage base)
	{
		Map<IConduitEnergyStorage, Integer> storages = Maps.newHashMap();
		storages.put(base, base.getTransferRate());
		IBlockAccess access = base.getWorld();
		if(access != null && base.getPos() != null)
		{
			int prevSize; 
			boolean updateAnyway;
			do
			{
				updateAnyway = false;
				prevSize = storages.size();
				for(Entry<IConduitEnergyStorage, Integer> entry : Sets.newHashSet(storages.entrySet()))
					for(EnumFacing direction : EnumFacing.values())
							updateAnyway = addStorageToMap(storages, getStorage(access, entry.getKey().getPos(), direction), entry.getValue()) || updateAnyway;
			}
			while(updateAnyway || prevSize != storages.size());
		}
		return storages;
	}
	
	public static IConduitEnergyStorage getStorage(IBlockAccess access, BlockPos pos, EnumFacing direction)
	{
		TileEntity entity = access.getTileEntity(pos.offset(direction));
		if(entity != null && entity.hasCapability(CapabilityEnergy.ENERGY, direction.getOpposite()))
		{
			IEnergyStorage storage = entity.getCapability(CapabilityEnergy.ENERGY, direction.getOpposite());
			return storage instanceof IConduitEnergyStorage ? (IConduitEnergyStorage)storage : null;
		}
		return null;
	}
	
	public static IConduitEnergyStorage getStorageFromPos(BlockPos pos, Set<IConduitEnergyStorage> storages)
	{
		for(IConduitEnergyStorage storage : storages)
			if(storage.getPos().equals(pos))
				return storage;
		return null;
	}
	
	public static boolean addStorageToMap(Map<IConduitEnergyStorage, Integer> map, IConduitEnergyStorage storage, int maxTransferRate)
	{
		if(storage == null)
			return false;
		else
		{
			int transferRate = Math.min(maxTransferRate, storage.getTransferRate());
			if(map.containsKey(storage))
			{
				map.replace(storage, Math.max(transferRate, map.get(storage)));
				return true;
			}
			else
			{
				map.put(storage, transferRate);
				return false;
			}
		}
	}
}
