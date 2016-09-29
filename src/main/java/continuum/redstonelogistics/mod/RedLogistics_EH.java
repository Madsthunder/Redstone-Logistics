package continuum.redstonelogistics.mod;

import java.util.Set;

import com.google.common.collect.Sets;

import continuum.api.redstonelogistics.ConduitSystem;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@EventBusSubscriber
public class RedLogistics_EH
{
	public static final Set<ConduitSystem> systems = Sets.newHashSet();
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onServerTick(TickEvent.ServerTickEvent event)
	{
		for(ConduitSystem system : systems)
			system.resetTicked();
	}
}
