package hmgww2.entity;

import hmgww2.Nation;
import net.minecraft.world.World;

public class EntityGER_ShipBase extends EntityBases_Ship
{
	public EntityGER_ShipBase(World par1World) {
		super(par1World);
		draft = 4.1306f;
	}
	
	@Override
	public Nation getnation() {
		return Nation.GER;
	}
	
	public void Excontrol1(boolean keystate){
		if(keystate) {
			draft++;
			if (draft > 40) draft = 40;
		}
	}
	public void Excontrol2(boolean keystate){
		if(keystate) {
			draft--;
			if (draft < 4.1306) draft = 4.1306f;
		}
	}
}
