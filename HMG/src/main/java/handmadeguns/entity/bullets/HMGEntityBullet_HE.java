package handmadeguns.entity.bullets;

//import littleMaidMobX.LMM_EntityLittleMaid;
//import littleMaidMobX.LMM_EntityLittleMaidAvatar;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import static handmadeguns.HandmadeGunsCore.cfg_blockdestroy;

public class HMGEntityBullet_HE extends HMGEntityBulletExprode implements IEntityAdditionalSpawnData
{
	public HMGEntityBullet_HE(World worldIn) {
		super(worldIn);
	}
	public HMGEntityBullet_HE(World worldIn, Entity throwerIn, int damege, float bspeed, float bure,String modelname)  {
		super(worldIn, throwerIn, damege, bspeed, bure,1.9f,cfg_blockdestroy,modelname);
	}
}
