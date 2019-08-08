package hmggvcmob.entity.guerrilla;


import handmadeguns.items.guns.HMGItem_Unified_Guns;
import handmadevehicle.entity.parts.logics.IbaseLogic;
import handmadevehicle.entity.parts.logics.PlaneBaseLogic;
import net.minecraft.entity.*;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import javax.vecmath.Quat4d;

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
//		proxy_HMVehicle.replaceBoundingbox(this,nboundingbox);
//		((ModifiedBoundingBox)this.boundingBox).update(this.posX,this.posY,this.posZ);
		ignoreFrustumCheck = true;
		baseLogic = new PlaneBaseLogic(worldObj, this);
		baseLogic.planeInfo.soundname = "gvcmob:gvcmob.heli";
		baseLogic.planeInfo.soundpitch = 1.2f;
		baseLogic.planeInfo.speedfactor = 0.012f;
		baseLogic.planeInfo.yawspeed = 0.02f;
		baseLogic.planeInfo.liftfactor = 0;
		baseLogic.planeInfo.flapliftfactor = 0;
		baseLogic.planeInfo.flapdragfactor = 0;
		baseLogic.planeInfo.geardragfactor = 0;
		baseLogic.planeInfo.dragfactor = 0.07f;
		baseLogic.planeInfo.gravity = 0.049f;
		baseLogic.planeInfo.stability2 = 0;
		baseLogic.planeInfo.stability = 0;
		baseLogic.planeInfo.rotmotion_reduceSpeed = 0;
		baseLogic.planeInfo.forced_rudder_effect = 0.99f;
		baseLogic.planeInfo.forced_rotmotion_reduceSpeed = 0.05f;
		baseLogic.planeInfo.slipresist = 0;
		baseLogic.planeInfo.throttle_Max = 5;
		baseLogic.planeInfo.unitThrottle.set(0,-1,0);
		baseLogic.planeInfo.brakedragfactor = 0;
		baseLogic.planeInfo.maxClimb = 0;
		baseLogic.planeInfo.maxDive = 5;
		baseLogic.planeInfo.minALT = 5;
		baseLogic.planeInfo.cruiseALT = 20;
		baseLogic.planeInfo.changeWeaponCycleSetting = 50;
		baseLogic.planeInfo.type_F_Plane_T_Heli = true;
		baseLogic.planeInfo.displayModernHud = true;
		baseLogic.seatInfos = new SeatInfo[1];
		baseLogic.seatInfos_zoom = new SeatInfo[1];
		baseLogic.riddenByEntities = new Entity[1];
		baseLogic.seatInfos[0] = new SeatInfo();
		baseLogic.seatInfos[0].pos[0] = 0;
		baseLogic.seatInfos[0].pos[1] = 2.5;
		baseLogic.seatInfos[0].pos[2] = 0;
		ModifiedBoundingBox nboundingbox = new ModifiedBoundingBox(-1.5,0,-1.5,
				                                                          1.5,5,1.5,
				                                                          0,1.5,4,2.5,3,8);
		nboundingbox.rot.set(baseLogic.bodyRot);
		proxy_HMVehicle.replaceBoundingbox(this,nboundingbox);
		((ModifiedBoundingBox)this.boundingBox).update(this.posX,this.posY,this.posZ);
	
	}
	
	public double getMountedYOffset() {
		return 0.6D;
	}
	
	
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0D);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(160);
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
		if (this.standalone()) {
			if (!worldObj.isRemote && rand.nextInt(10) == 1) {
				boolean flag = false;
				for (int id = 0; id < (baseLogic).riddenByEntities.length; id++) {
					if((baseLogic).riddenByEntities[id] == null)flag = true;
				}
				if(flag) {
//					System.out.println("debug");
					EntityGBase gvcEntityGuerrilla = new GVCEntityGuerrillaMG(worldObj);
					gvcEntityGuerrilla.addRandomArmor();
					gvcEntityGuerrilla.setLocationAndAngles(this.posX, this.posY, this.posZ, 0, 0);
					worldObj.spawnEntityInWorld(gvcEntityGuerrilla);
					if (!pickupEntity(gvcEntityGuerrilla, 0)) gvcEntityGuerrilla.setDead();
					if(gvcEntityGuerrilla.getHeldItem() != null && gvcEntityGuerrilla.getHeldItem().getItem() instanceof HMGItem_Unified_Guns && ((HMGItem_Unified_Guns) gvcEntityGuerrilla.getHeldItem().getItem()).gunInfo.needfix)gvcEntityGuerrilla.setDead();
				}
				
			}
		}
//		if (!this.standalone() && baseLogic.childEntities[0] != null && baseLogic.childEntities[0].riddenByEntity == null) {
//			baseLogic.throttle--;
//		}
		baseLogic.onUpdate();
	}
	public boolean attackEntityFrom(DamageSource source, float par2) {
		if(source.getDamageType().equals(DamageSource.fall.damageType) ||
				   source.getDamageType().equals(DamageSource.outOfWorld.damageType) ||
				   source.getDamageType().equals(DamageSource.inWall.damageType))return super.attackEntityFrom(source, par2);
		par2 -= 4;
		if(par2 < 0)par2 = 0;
		if (this.riddenByEntity == source.getEntity()) {
			return false;
		} else if (this == source.getEntity()) {
			return false;
		} else if(this instanceof ImultiRidable && ((ImultiRidable)this).isRidingEntity(source.getEntity())) {
			return false;
		}else {
			return super.attackEntityFrom(source, par2);
		}
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
		super.setPosition(x,y,z);
		if(baseLogic != null)baseLogic.setPosition(x,y,z);
	}
	
	
	@Override
	public void moveEntity(double x, double y, double z){
		baseLogic.moveEntity(x,y,z);
	}
	
	@Override
	public void updateFallState_public(double stepHeight, boolean onground){
		this.updateFallState(stepHeight,onground);
	}
	
	@Override
	public void func_145775_I_public() {
		this.func_145775_I();
	}
}
