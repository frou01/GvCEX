package hmgww2.entity;

import hmgww2.Nation;
import net.minecraft.world.World;

import static handmadevehicle.Utils.transformVecByQuat;

public class EntityJPN_ShipBase extends EntityBases_Ship
{
	public EntityJPN_ShipBase(World par1World) {
		super(par1World);
	}
	
	@Override
	public Nation getnation() {
		return Nation.JPN;
	}
}
