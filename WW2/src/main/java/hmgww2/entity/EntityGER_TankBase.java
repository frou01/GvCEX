package hmgww2.entity;

import hmgww2.Nation;
import net.minecraft.world.World;

public class EntityGER_TankBase extends EntityBases_Tank{
	
	public EntityGER_TankBase(World par1World) {
		super(par1World);
	}
	
	@Override
	public Nation getnation() {
		return Nation.GER;
	}
}
