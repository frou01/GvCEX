package hmgww2.entity;


import hmvehicle.entity.parts.Iplane;
import hmgww2.Nation;
import net.minecraft.world.World;

public class EntityUSSR_FighterBase extends EntityBases_Plane
{
	
	public EntityUSSR_FighterBase(World par1World) {
		super(par1World);
	}
	
	@Override
	public Nation getnation() {
		return Nation.USSR;
	}
}
