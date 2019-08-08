package hmgww2.entity.planes;


import handmadevehicle.entity.parts.IPlane;
import hmgww2.Nation;
import net.minecraft.world.World;

public class EntityGER_FighterBase extends EntityBases_Plane implements IPlane
{
	
	public EntityGER_FighterBase(World par1World) {
		super(par1World);
	}
	
	@Override
	public Nation getnation() {
		return Nation.GER;
	}
}
