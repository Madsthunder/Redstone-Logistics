package continuum.redstonelogistics.loaders;

import continuum.essentials.mod.ObjectLoader;
import continuum.redstonelogistics.mod.RedLogistics_EH;
import continuum.redstonelogistics.mod.RedLogistics_OH;

public class UtilityLoader implements ObjectLoader<RedLogistics_OH, RedLogistics_EH>
{
	@Override
	public String getName()
	{
		return "Utilities";
	}
}
