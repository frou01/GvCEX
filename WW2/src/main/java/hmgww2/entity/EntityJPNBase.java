package hmgww2.entity;

import handmadeguns.entity.IFF;
import hmggvcmob.entity.IGVCmob;
import hmgww2.Nation;
import hmgww2.mod_GVCWW2;
import net.minecraft.world.World;

public class EntityJPNBase extends EntityBases implements IFF, IGVCmob {


	public EntityJPNBase(World par1World) {
		super(par1World);
		this.flag = mod_GVCWW2.b_flag_jpn;
		this.flag2 = mod_GVCWW2.b_flag2_jpn;
		this.flag3 = mod_GVCWW2.b_flag3_jpn;
		this.flag4 = mod_GVCWW2.b_flag2_jpn;
	}

	@Override
	public Nation getnation() {
		return Nation.JPN;
	}
}