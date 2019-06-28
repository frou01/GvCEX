package hmgww2.entity;

import hmgww2.Nation;
import net.minecraft.world.World;

public class EntityUSSR_TankBase extends EntityBases_Tank{
	
	public EntityUSSR_TankBase(World par1World) {
		super(par1World);
	}
	
	@Override
	public Nation getnation() {
		return Nation.USSR;
	}
	
}