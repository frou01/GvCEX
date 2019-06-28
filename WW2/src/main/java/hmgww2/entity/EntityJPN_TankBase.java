package hmgww2.entity;

import hmgww2.Nation;
import net.minecraft.world.World;

public class EntityJPN_TankBase extends EntityBases_Tank{
	
	public EntityJPN_TankBase(World par1World) {
		super(par1World);
	}
	
	@Override
	public Nation getnation() {
		return Nation.JPN;
	}
}
