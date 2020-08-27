package hmgww2.entity;

import hmgww2.Nation;
import hmgww2.mod_GVCWW2;
import net.minecraft.world.World;

public class EntityUSABase extends EntityBases {


	public EntityUSABase(World par1World) {
		super(par1World);
		this.flag = mod_GVCWW2.b_flag_usa;
		this.flag2 = mod_GVCWW2.b_flag2_usa;
		this.flag3 = mod_GVCWW2.b_flag3_usa;
		this.flag4 = mod_GVCWW2.b_flag2_usa;
	}

	@Override
	public Nation getnation() {
		return Nation.USA;
	}

}
