package hmggvcmob.entity.guerrilla;


import hmvehicle.entity.parts.IVehicle;
import hmggvcutil.GVCUtils;
import hmggvcmob.entity.GVCEx;
import hmvehicle.entity.parts.Iplane;
import hmvehicle.entity.parts.logics.IbaseLogic;
import hmvehicle.entity.parts.logics.PlaneBaseLogic;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.*;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import javax.vecmath.Quat4d;

import static hmggvcutil.GVCUtils.fn_rpg7;
import static hmggvcmob.GVCMobPlus.cfg_guerrillasrach;

public class GVCEntityHeli extends EntityGBase implements Iplane {
	public int homeposX;
	public int homeposY;
	public int homeposZ;
	PlaneBaseLogic baseLogic;
	float maxHealth = 150;
	public GVCEntityHeli(World par1World) {
		super(par1World);
		this.setSize(5f, 5f);
//		nboundingbox = new ModifiedBoundingBox(-20,-20,-20,20,20,20,0,0,-6.27,2.5,5,19);
//		nboundingbox.rot.set(this.bodyRot);
//		proxy.replaceBoundingbox(this,nboundingbox);
//		((ModifiedBoundingBox)this.boundingBox).update(this.posX,this.posY,this.posZ);
		ignoreFrustumCheck = true;
		baseLogic = new PlaneBaseLogic(worldObj, this);
		baseLogic.speedfactor = 0.009f;
		baseLogic.liftfactor = 0.05f;
		baseLogic.flapliftfactor = 0.00005f;
		baseLogic.flapdragfactor = 0.0000000001f;
		baseLogic.geardragfactor = 0.000000001f;
		baseLogic.dragfactor = 0.07f;
		baseLogic.gravity = 0.049f;
		baseLogic.stability = 600;
		baseLogic.rotmotion_reduceSpeed = 0.1;
//		baseLogic.slipresist = 4;
	
	}
	
	public double getMountedYOffset() {
		return 0.6D;
	}
	
	
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0D);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(maxHealth);
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(80.0D);
		this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(30.0D);
	}
	
	public void onUpdate() {
		double[] pos = new double[]{this.posX, this.posY, this.posZ};
		double[] motion = new double[]{this.motionX, this.motionY, this.motionZ};
		boolean onground = this.onGround;
		super.onUpdate();
		this.onGround = onground;
		this.posX = pos[0];
		this.posY = pos[1];
		this.posZ = pos[2];
		this.motionX = motion[0];
		this.motionY = motion[1];
		this.motionZ = motion[2];
//		if (!this.standalone() && baseLogic.childEntities[0] != null && baseLogic.childEntities[0].riddenByEntity == null) {
//			baseLogic.throttle--;
//		}
		baseLogic.onUpdate();
	}
	
	public void setVelocity(double p_70016_1_, double p_70016_3_, double p_70016_5_) {
//		baseLogic.setVelocity(p_70016_1_,p_70016_3_,p_70016_5_);
	}
	
	@Override
	public int getfirecyclesettings1() {
		return 0;
	}
	
	@Override
	public int getfirecycleprogress1() {
		return 0;
	}
	
	@Override
	public int getfirecyclesettings2() {
		return 0;
	}
	
	@Override
	public int getfirecycleprogress2() {
		return 0;
	}
	
	@Override
	public float getturretrotationYaw() {
		return 0;
	}
	
	@Override
	public float getbodyrotationYaw() {
		return baseLogic.bodyrotationYaw;
	}
	
	@Override
	public float getthrottle() {
		return (float) baseLogic.throttle;
	}
	
	@Override
	public void setBodyRot(Quat4d quat4d) {
		baseLogic.bodyRot = quat4d;
	}
	
	@Override
	public void setthrottle(float th) {
		baseLogic.throttle = th;
	}
	
	@Override
	public int getpilotseatid() {
		return 0;
	}
	
	public void applyEntityCollision(Entity p_70108_1_) {
	}
	
	@Override
	public boolean shouldRenderInPass(int pass) {
		return pass == 1 || pass == 0;
	}
	
	@Override
	public IbaseLogic getBaseLogic() {
		return baseLogic;
	}
	
	protected void onDeathUpdate() {
		++this.deathTicks;
		if(this.deathTicks > 40) {
			if (worldObj.isRemote) {
				for (int i = 0; i < 5; i++) {
					worldObj.spawnParticle("smoke",
							this.posX + (float) (rand.nextInt(30) - 15) / 10,
							this.posY + (float) (rand.nextInt(30) - 15) / 10 + 1.5f,
							this.posZ + (float) (rand.nextInt(30) - 15) / 10,
							0.0D, 0.2D, 0.0D);
					worldObj.spawnParticle("cloud",
							this.posX + (float) (rand.nextInt(30) - 15) / 10,
							this.posY + (float) (rand.nextInt(30) - 15) / 10 + 1.5f,
							this.posZ + (float) (rand.nextInt(30) - 15) / 10,
							0.0D, 0.3D, 0.0D);
				}
			}
		}
		if (this.deathTicks >= 140) {
			for (int i = 0; i < 15; i++) {
				worldObj.spawnParticle("flame",
						this.posX + (float) (rand.nextInt(20) - 10) / 10,
						this.posY + (float) (rand.nextInt(20) - 10) / 10,
						this.posZ + (float) (rand.nextInt(20) - 10) / 10,
						(rand.nextInt(20) - 10) / 100,
						(rand.nextInt(20) - 10) / 100,
						(rand.nextInt(20) - 10) / 100 );
				worldObj.spawnParticle("smoke",
						this.posX + (float) (rand.nextInt(30) - 15) / 10,
						this.posY + (float) (rand.nextInt(30) - 15) / 10,
						this.posZ + (float) (rand.nextInt(30) - 15) / 10,
						(rand.nextInt(20) - 10) / 100,
						(rand.nextInt(20) - 10) / 100,
						(rand.nextInt(20) - 10) / 100 );
				worldObj.spawnParticle("cloud",
						this.posX + (float) (rand.nextInt(30) - 15) / 10,
						this.posY + (float) (rand.nextInt(30) - 15) / 10,
						this.posZ + (float) (rand.nextInt(30) - 15) / 10,
						(rand.nextInt(20) - 10) / 100,
						(rand.nextInt(20) - 10) / 100,
						(rand.nextInt(20) - 10) / 100 );
			}
			if(this.deathTicks == 150)
				this.setDead();
		}
	}
	
	public boolean isConverting() {
		return false;
	}
	
	public boolean canBePushed() {
		return false;
	}
	
	public void moveFlying(float p_70060_1_, float p_70060_2_, float p_70060_3_) {
	}
	
	public void jump() {
	
	}
	
	public void moveEntityWithHeading(float p_70612_1_, float p_70612_2_) {
	
	}
	
	@Override
	public int getMobMode() {
		return 0;
	}
	
	@Override
	public double[] getTargetpos() {
		return new double[3];
	}
	
	@Override
	public boolean standalone() {
		return true;
	}
	public void setPosition(double x, double y, double z)
	{
		if(baseLogic != null)baseLogic.setPosition(x,y,z);
	}
}
