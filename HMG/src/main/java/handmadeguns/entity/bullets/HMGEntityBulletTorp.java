package handmadeguns.entity.bullets;


import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import handmadeguns.network.PacketSpawnParticle;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import static handmadeguns.HandmadeGunsCore.cfg_defgravitycof;
import static handmadeguns.HandmadeGunsCore.proxy;

public class HMGEntityBulletTorp extends HMGEntityBulletExprode implements IEntityAdditionalSpawnData
{
	public HMGEntityBulletTorp(World worldIn) {
		super(worldIn);
	}
	public HMGEntityBulletTorp(World worldIn, Entity throwerIn, int damege, float bspeed, float bure) {
		super(worldIn, throwerIn, damege, bspeed, bure);
		this.canbounce = false;
		this.bouncerate = 0.1f;
	}
	public HMGEntityBulletTorp(World worldIn, Entity throwerIn, int damege, float bspeed, float bure, float exl, boolean canex) {
		this(worldIn, throwerIn, damege, bspeed, bure);
		exlevel = exl;
		this.canex = canex;
		this.canbounce = false;
		this.bouncerate = 0.1f;
	}
	public HMGEntityBulletTorp(World worldIn, Entity throwerIn, int damege, float bspeed, float bure, float exl, boolean canex, String modelname) {
		super(worldIn, throwerIn, damege, bspeed, bure,exl,canex, modelname);
		exlevel = exl;
		this.canex = canex;
		this.canbounce = false;
		this.bouncerate = 0.1f;
	}
	public HMGEntityBulletTorp(World worldIn, double x, double y, double z) {
		super(worldIn, x, y, z);
	}

	public void onUpdate(){
		super.onUpdate();
		if(isInWater()) {
			if (worldObj.isRemote && smoketexture == null) {
				PacketSpawnParticle packet = new PacketSpawnParticle(posX, posY, posZ, -this.motionX / 8,
						                                                    -this.motionY / 8,
						                                                    -this.motionZ / 8, 1);
				packet.scale = smokeWidth;
				packet.fuse = smoketime;
				if (smokeglow) packet.id += 100;
				proxy.spawnParticles(packet);
			}
		}
	}
	public void applyacceleration(){
		if(isInWater()) {
			double f2 = getspeed();
			if (f2 > 0) {
				this.motionX += motionX / f2 * acceleration;
				this.motionY += motionY / f2 * acceleration;
				this.motionZ += motionZ / f2 * acceleration;
//                worldObj.playSoundAtEntity(this, "handmadeguns:handmadeguns." + flyingSound,flyingSoundLV, flyingSoundSP);
			}
		}
	}
	
	public void changeVector(){
		super.changeVector();
		if(isInWater()){
			//下降を停止させる
			this.motionY += (double) gra * cfg_defgravitycof;
			if(motionY<0){
				this.motionY += (double) gra * cfg_defgravitycof/2;
			}
		}
	}
}
