package hmggvcmob.entity.friend;


import handmadevehicle.entity.parts.logics.IbaseLogic;
import handmadevehicle.entity.parts.logics.PlaneBaseLogicLogic;
import handmadevehicle.entity.parts.turrets.TurretObj;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import javax.vecmath.*;

import java.util.ArrayList;

import static handmadevehicle.Utils.CalculateGunElevationAngle;
import static handmadevehicle.Utils.addAllTurret;

public class GVCEntityPMCHeli extends EntityPMCBase implements Iplane,IMultiTurretVehicle
{
	PlaneBaseLogicLogic baseLogic;
	TurretObj gunnerturret;
	float maxHealth = 150;
	//	ModifiedBoundingBox nboundingbox;
	public void updateRiderPosition() {
//		baseLogic.riderPosUpdate();
	}
	public GVCEntityPMCHeli(World par1World) {
		super(par1World);
		this.setSize(5f, 5f);
//		nboundingbox = new ModifiedBoundingBox(-20,-20,-20,20,20,20,0,0,-6.27,2.5,5,19);
//		nboundingbox.rot.set(this.bodyRot);
//		proxy_HMVehicle.replaceBoundingbox(this,nboundingbox);
//		((ModifiedBoundingBox)this.boundingBox).update(this.posX,this.posY,this.posZ);
		ignoreFrustumCheck = true;
		baseLogic = new PlaneBaseLogicLogic(worldObj, this);
		baseLogic.prefab_vehicle.soundname = "gvcmob:gvcmob.heli";
		baseLogic.prefab_vehicle.soundpitch = 1.2f;
		baseLogic.prefab_vehicle.speedfactor = 0.012f;
		baseLogic.prefab_vehicle.liftfactor = 0.01f;
		baseLogic.prefab_vehicle.flapliftfactor = 0;
		baseLogic.prefab_vehicle.flapdragfactor = 0;
		baseLogic.prefab_vehicle.geardragfactor = 0;
		baseLogic.prefab_vehicle.dragfactor = 0.07f;
		baseLogic.prefab_vehicle.gravity = 0.049f;
		baseLogic.prefab_vehicle.stability_motion = 0;
		baseLogic.prefab_vehicle.stability_roll = 0;
		baseLogic.prefab_vehicle.rotmotion_reduceSpeed = 0;
		baseLogic.prefab_vehicle.forced_rudder_effect = 0.99f;
		baseLogic.prefab_vehicle.forced_rotmotion_reduceSpeed = 0.05f;
		baseLogic.prefab_vehicle.slipresist = 0;
		baseLogic.prefab_vehicle.throttle_Max = 5;
		baseLogic.prefab_vehicle.unitThrottle.set(0,-1,0);
		baseLogic.prefab_vehicle.brakedragfactor = 0;
		baseLogic.prefab_vehicle.maxClimb = 0;
		baseLogic.prefab_vehicle.maxDive = 30;
		baseLogic.prefab_vehicle.minALT = 10;
		baseLogic.prefab_vehicle.cruiseALT = 25;
		baseLogic.prefab_vehicle.changeWeaponCycleSetting = 50;
		baseLogic.prefab_vehicle.type_F_Plane_T_Heli = true;
		baseLogic.prefab_vehicle.displayModernHud = true;
		
		{
			TurretObj turret = new TurretObj(worldObj);
			turret.onMotherPos = new Vector3d(0.1260, 0.9463, -6.444);
			turret.turretPitchCenterpos = new Vector3d(0, 0.8189, -6.449);
			turret.turretPitchCenterpos.sub(turret.onMotherPos);
			turret.cannonPos = new Vector3d(0.1260, 0.5770, -7.278);
			turret.cannonPos.sub(turret.onMotherPos);
			turret.turretanglelimtYawMax = 90;
			turret.turretanglelimtYawmin = -90;
			turret.turretanglelimtPitchMax = 60;
			turret.turretanglelimtPitchmin = -15;
			turret.turretspeedY = 20;
			turret.turretspeedP = 20;
			turret.motherRotCenter = new Vector3d(baseLogic.prefab_vehicle.rotcenter);
//			turret.traverseSound = null;
			turret.currentEntity = this;
			turret.powor = 26;
			turret.ex = 0.5f;
			turret.cycle_setting = 0;
			turret.flashscale = 1;
			turret.flashoffset = 0;
			turret.firesound = "handmadeguns:handmadeguns.HeavyMachineGun";
			turret.spread = 3;
			turret.speed = 8;
			turret.magazineMax = 500;
			turret.magazinerem = 500;
			turret.reloadTimer = 1200;
			turret.canex = false;
			turret.guntype = 0;
			gunnerturret = turret;
		}
		{
			TurretObj gun1 = new TurretObj(worldObj);
			gun1.onMotherPos = new Vector3d(2.17, 1.27, -0.3262);
			gun1.motherRotCenter = new Vector3d(baseLogic.prefab_vehicle.rotcenter);
			gun1.traverseSound = null;
			gun1.currentEntity = this;
			gun1.powor = 60;
			gun1.ex = 0.5f;
			gun1.cycle_setting = 0;
			gun1.flashscale = 1;
			gun1.flashoffset = 0;
			gun1.firesound = "handmadeguns:handmadeguns.HeavyMachineGun";
			gun1.spread = 2;
			gun1.speed = 8;
			gun1.magazineMax = 500;
			gun1.magazinerem = 500;
			gun1.reloadTimer = 1200;
			gun1.canex = false;
			gun1.guntype = 0;
			TurretObj gun2 = new TurretObj(worldObj);
			gun2.onMotherPos = new Vector3d(-1.96, 1.275, -0.3262);
			gun2.traverseSound = null;
			gun2.currentEntity = this;
			gun2.powor = 60;
			gun2.ex = 0.5f;
			gun2.cycle_setting = 0;
			gun2.flashscale = 1;
			gun2.flashoffset = 0;
			gun2.firesound = "handmadeguns:handmadeguns.HeavyMachineGun";
			gun2.spread = 2;
			gun2.speed = 8;
			gun2.magazineMax = 500;
			gun2.magazinerem = 500;
			gun2.reloadTimer = 1200;
			gun2.canex = false;
			gun2.guntype = 0;
			gun1.addbrother(gun2);
			
			baseLogic.mainTurret = gun1;
		}
		{
			TurretObj rocket1 = new TurretObj(worldObj);
			rocket1.onMotherPos = new Vector3d(-2.685, 1.035, -0.4187);
			rocket1.motherRotCenter = new Vector3d(baseLogic.prefab_vehicle.rotcenter);
			rocket1.traverseSound = null;
			rocket1.currentEntity = this;
			rocket1.powor = 75;
			rocket1.acceler = 0.1f;
			rocket1.ex = 0.5f;
			rocket1.cycle_setting = 1;
			rocket1.cycle_timer = -1;
			rocket1.flashscale = 1;
			rocket1.flashoffset = 0;
			rocket1.firesound = "handmadeguns:handmadeguns.missileLaunch";
			rocket1.spread = 0.5f;
			rocket1.speed = 2;
			rocket1.magazineMax = 32;
			rocket1.magazinerem = 32;
			rocket1.reloadTimer = 600;
			rocket1.canex = true;
			rocket1.guntype = 3;
			
			TurretObj rocket2 = new TurretObj(worldObj);
			rocket2.onMotherPos = new Vector3d(2.915, 1.035, -0.4187);
			rocket2.traverseSound = null;
			rocket2.currentEntity = this;
			rocket2.powor = 75;
			rocket2.acceler = 0.1f;
			rocket2.ex = 0.5f;
			rocket2.cycle_setting = 1;
			rocket2.cycle_timer = -1;
			rocket2.flashscale = 1;
			rocket2.flashoffset = 0;
			rocket2.firesound = "handmadeguns:handmadeguns.missileLaunch";
			rocket2.spread = 0.5f;
			rocket2.speed = 2;
			rocket2.magazineMax = 32;
			rocket2.magazinerem = 32;
			rocket2.reloadTimer = 600;
			rocket2.canex = true;
			rocket2.guntype = 3;
			
			rocket1.addbrother(rocket2);
			baseLogic.subTurret = rocket1;
		}
		
		baseLogic.seatInfos = new SeatInfo[6];
		baseLogic.seatInfos_zoom = new SeatInfo[6];
		baseLogic.riddenByEntities = new Entity[6];
		baseLogic.seatInfos[0] = new SeatInfo();
		baseLogic.seatInfos[0].pos[0] = 0.1074;
		baseLogic.seatInfos[0].pos[1] = 2.584;
		baseLogic.seatInfos[0].pos[2] = -4.245;
		
		baseLogic.seatInfos[1] = new SeatInfo();
		baseLogic.seatInfos[1].pos[0] = 0.1278;
		baseLogic.seatInfos[1].pos[1] = 1.565;
		baseLogic.seatInfos[1].pos[2] = -6.237;
		
		baseLogic.seatInfos_zoom[1] = new SeatInfo();
		baseLogic.seatInfos_zoom[1].pos[0] = 0.3474;
		baseLogic.seatInfos_zoom[1].pos[1] = 0.7012;
		baseLogic.seatInfos_zoom[1].pos[2] = -6.749;
		
		baseLogic.seatInfos[1].hasGun = true;
		baseLogic.seatInfos[1].maingun = gunnerturret;
		
		baseLogic.seatInfos[2] = new SeatInfo();
		baseLogic.seatInfos[2].pos[0] = 0.0332 + 1;
		baseLogic.seatInfos[2].pos[1] = 2.117;
		baseLogic.seatInfos[2].pos[2] = -1.35 + 1;
		
		baseLogic.seatInfos[3] = new SeatInfo();
		baseLogic.seatInfos[3].pos[0] = 0.0332 - 1;
		baseLogic.seatInfos[3].pos[1] = 2.117;
		baseLogic.seatInfos[3].pos[2] = -1.35 + 1;
		
		baseLogic.seatInfos[4] = new SeatInfo();
		baseLogic.seatInfos[4].pos[0] = 0.0332 + 1;
		baseLogic.seatInfos[4].pos[1] = 2.117;
		baseLogic.seatInfos[4].pos[2] = -1.35 - 1;
		
		baseLogic.seatInfos[5] = new SeatInfo();
		baseLogic.seatInfos[5].pos[0] = 0.0332 - 1;
		baseLogic.seatInfos[5].pos[1] = 2.117;
		baseLogic.seatInfos[5].pos[2] = -1.35 - 1;
		baseLogic.prefab_vehicle.camerapos = new double[]{0.1074,2.584,-4.245};
		
		
//		baseLogic.slipresist = 4;
		
		ModifiedBoundingBox nboundingbox = new ModifiedBoundingBox(-1.5,0,-1.5,1.5,5,1.5,0,0,-6.27,2.5,5,19);
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
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(150);
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(80.0D);
		this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(30.0D);
	}
	
	public void onUpdate() {
		
		if (this.standalone()) {
			if (!worldObj.isRemote && rand.nextInt(100) == 1) {
				boolean flag = false;
				for (int id = 1; id < (baseLogic).riddenByEntities.length; id++) {
					if((baseLogic).riddenByEntities[id] == null)flag = true;
				}
				if(flag) {
					GVCEntityPMCMG entityPMCMG = new GVCEntityPMCMG(worldObj);
					entityPMCMG.addRandomArmor();
					entityPMCMG.setLocationAndAngles(this.posX, this.posY, this.posZ, 0, 0);
					worldObj.spawnEntityInWorld(entityPMCMG);
					if (!pickupEntity(entityPMCMG, 0)) entityPMCMG.setDead();
				}
				
			}
		}
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
		mode= getMobMode();
	}
	
	public boolean attackEntityFrom(DamageSource source, float par2) {
		if(source.getDamageType().equals(DamageSource.fall.damageType) ||
				   source.getDamageType().equals(DamageSource.outOfWorld.damageType) ||
				   source.getDamageType().equals(DamageSource.inWall.damageType))return super.attackEntityFrom(source, par2);
		par2 -= 7;
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
	
	public boolean interact(EntityPlayer p_70085_1_) {
		if (!this.worldObj.isRemote && (this.riddenByEntity == null || this.riddenByEntity == p_70085_1_)) {
			if (p_70085_1_.isSneaking()) {
				if (p_70085_1_.getEquipmentInSlot(0) != null) {
					ItemStack itemstack = p_70085_1_.getEquipmentInSlot(0);
					if (itemstack.getItem() == Items.iron_ingot) {
						if (!p_70085_1_.capabilities.isCreativeMode) itemstack.stackSize--;
						if (itemstack.stackSize <= 0 && !p_70085_1_.capabilities.isCreativeMode) {
							p_70085_1_.destroyCurrentEquippedItem();
						}
						this.setHealth(this.getHealth() + 30);
					}
					if (itemstack.getItem() == Item.getItemFromBlock(Blocks.iron_block)) {
						if (!p_70085_1_.capabilities.isCreativeMode) itemstack.stackSize--;
						if (itemstack.stackSize <= 0 && !p_70085_1_.capabilities.isCreativeMode) {
							p_70085_1_.destroyCurrentEquippedItem();
						}
						this.setHealth(this.getHealth() + 300);
					}
				} else if (this.getMobMode() == 0) {
					mode = 1;
					this.setMobMode(1);
					
					p_70085_1_.addChatComponentMessage(new ChatComponentTranslation(
							                                                               "Defense  " + (int) posX + "," + (int) posZ));
					homeposX = (int) posX;
					homeposY = (int) posY;
					homeposZ = (int) posZ;
				} else if (this.getMobMode() == 2) {
					mode = 2;
					this.setMobMode(2);
					homeposX = (int) p_70085_1_.posX;
					homeposY = (int) p_70085_1_.posY;
					homeposZ = (int) p_70085_1_.posZ;
					master = p_70085_1_;
					p_70085_1_.addChatComponentMessage(new ChatComponentTranslation(
							                                                               "Cover mode"));
				} else if (this.getMobMode() == 2) {
					p_70085_1_.addChatComponentMessage(new ChatComponentTranslation("wait "));
					mode = 3;
					this.setMobMode(3);
				} else if (this.getMobMode() == 3) {
					mode = 0;
					this.setMobMode(0);
				}
			} else if (!p_70085_1_.isRiding()) {
				
				if(baseLogic.riddenByEntities[0] == null) {
					mode = 0;
					this.setMobMode(0);
				}
				pickupEntity(p_70085_1_,0);
			}
			return true;
		} else {
			return false;
		}
	}
	
	
	@Override
	public int getpilotseatid() {
		return 0;
	}
	@Override
	public void applyEntityCollision(Entity p_70108_1_) {
		boolean flag = p_70108_1_.riddenByEntity != this && p_70108_1_.ridingEntity != this;
		flag &= !isRidingEntity(this);
		if (flag)
		{
			double d0 = p_70108_1_.posX - this.posX;
			double d1 = p_70108_1_.posZ - this.posZ;
			double d2 = MathHelper.abs_max(d0, d1);
			
			if (d2 >= 0.009999999776482582D)
			{
				d2 = (double)MathHelper.sqrt_double(d2);
				d0 /= d2;
				d1 /= d2;
				double d3 = 1.0D / d2;
				
				if (d3 > 1.0D)
				{
					d3 = 1.0D;
				}
				
				d0 *= d3;
				d1 *= d3;
				d0 *= 0.05000000074505806D;
				d1 *= 0.05000000074505806D;
				d0 *= (double)(1.0F - this.entityCollisionReduction);
				d1 *= (double)(1.0F - this.entityCollisionReduction);
				this.addVelocity(-d0, 0.0D, -d1);
				p_70108_1_.addVelocity(d0, 0.0D, d1);
			}
		}
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
	public boolean standalone() {
		return mode != 0;
	}
	public void setPosition(double x, double y, double z)
	{
		super.setPosition(x,y,z);
		if(baseLogic != null)baseLogic.setPosition(x,y,z);
	}
	
	@Override
	public TurretObj[] getmainTurrets() {
		if(baseLogic.mainTurrets == null) {
			ArrayList<TurretObj> turrets = new ArrayList<TurretObj>();
			addAllTurret(turrets, baseLogic.mainTurret);
			baseLogic.mainTurrets = turrets.toArray(new TurretObj[turrets.size()]);
		}
		return baseLogic.mainTurrets;
	}
	
	@Override
	public TurretObj[] getsubTurrets() {
		if(baseLogic.subTurrets == null) {
			ArrayList<TurretObj> turrets = new ArrayList<TurretObj>();
			addAllTurret(turrets, baseLogic.subTurret);
			baseLogic.subTurrets = turrets.toArray(new TurretObj[turrets.size()]);
		}
		return baseLogic.subTurrets;
	}
	
	@Override
	public TurretObj[] getTurrets() {
		if(baseLogic.turrets == null) {
			ArrayList<TurretObj> turrets = new ArrayList<TurretObj>();
			addAllTurret(turrets, baseLogic.mainTurret);
			addAllTurret(turrets, gunnerturret);
			addAllTurret(turrets, baseLogic.subTurret);
			baseLogic.turrets = turrets.toArray(new TurretObj[turrets.size()]);
		}
		return baseLogic.turrets;
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
