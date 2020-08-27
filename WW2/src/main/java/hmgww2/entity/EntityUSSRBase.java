package hmgww2.entity;

import hmgww2.Nation;
import hmgww2.mod_GVCWW2;
import net.minecraft.world.World;

public class EntityUSSRBase extends EntityBases {


	public EntityUSSRBase(World par1World) {
		super(par1World);
		this.flag = mod_GVCWW2.b_flag_rus;
		this.flag2 = mod_GVCWW2.b_flag2_rus;
		this.flag3 = mod_GVCWW2.b_flag3_rus;
		this.flag4 = mod_GVCWW2.b_flag2_rus;
	}

	@Override
	public Nation getnation() {
		return Nation.USSR;
	}

}
