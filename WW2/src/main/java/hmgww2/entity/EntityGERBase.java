package hmgww2.entity;

import handmadeguns.entity.IFF;
import hmgww2.Nation;
import hmgww2.mod_GVCWW2;
import net.minecraft.world.World;

public class
EntityGERBase extends EntityBases implements IFF {


	public EntityGERBase(World par1World) {
		super(par1World);
		this.flag = mod_GVCWW2.b_flag_ger;
		this.flag2 = mod_GVCWW2.b_flag2_ger;
		this.flag3 = mod_GVCWW2.b_flag3_ger;
		this.flag4 = mod_GVCWW2.b_flag2_ger;
	}

	@Override
	public Nation getnation() {
		return Nation.GER;
	}
}
