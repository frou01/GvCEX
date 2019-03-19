package hmggvcmob.entity.friend;


import handmadeguns.entity.bullets.HMGEntityBullet;
import handmadeguns.entity.bullets.HMGEntityBulletRocket;
import hmggvcmob.GVCMobPlus;
import hmggvcmob.entity.*;
import hmggvcmob.network.GVCMPacketHandler;
import hmggvcmob.network.GVCPakcetVehicleState;
import hmggvcmob.util.Calculater;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import javax.vecmath.AxisAngle4d;
import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;

import static handmadeguns.HandmadeGunsCore.cfg_defgravitycof;
import static hmggvcmob.GVCMobPlus.proxy;
import static hmggvcmob.event.GVCMXEntityEvent.soundedentity;
import static hmggvcmob.util.Calculater.CalculateGunElevationAngle;
import static java.lang.Math.*;
import static net.minecraft.util.MathHelper.wrapAngleTo180_float;

public class GVCEntitySoldierHeli extends Entity implements ImultiRideableVehicle
{
	// public int type;
	public final int turretyawDataID = 2;
	public final int turretpitchDataID = 3;
	public int rocket = 90;
	public boolean dir = true;
	public float health = 200;
	public float maxhealth = 200;
	public float rollladder;
	public float pitchladder;
	public float yawladder;
	public float bodyrotationYaw;
	public float bodyrotationPitch;
	public float bodyrotationRoll;
	public float prevbodyrotationYaw;
	public float prevbodyrotationPitch;
	public float prevbodyrotationRoll;
	public float prevangletime;
	public float angletime;
	public int fireCycle1;
	public int fireCycle2;
	public int cooltime;
	public int cooltime2;
	public int magazine;
	public float throttle;
	public int mode = 0;//0:attack 1:leave 2:follow player 3:go to home
	int modechangecool = 0;
	public int soundtick = 0;
	public boolean trigger1 = false;
	public boolean trigger2 = false;
	public double[][] gunpos = new double[6][3];
	public Quat4d bodyRot = new Quat4d(0,0,0,1);
	Vector3d unitX = new Vector3d(1,0,0);
	Vector3d unitY = new Vector3d(0,1,0);
	Vector3d unitZ = new Vector3d(0,0,1);
	public GVCEntityChild[] childEntities = new GVCEntityChild[6];
	public ChildInfo[] childInfo = new ChildInfo[6];
	boolean isinit;
	ModifiedBoundingBox nboundingbox;
	public TurretObj turret;



	public double turretYaw;
	public double turretPitch;
	public boolean turretTrigger;
	public Quat4d turretRot = new Quat4d(0,0,0,1);

	public GVCEntitySoldierHeli(World par1World)
	{
		super(par1World);
		this.setSize(5f, 5f);
		nboundingbox = new ModifiedBoundingBox(-20,-20,-20,20,20,20,
				                                      0,2.5,-3,2.5,5,19);
		nboundingbox.centerRotX = 0;
		nboundingbox.centerRotY = 2.5;
		nboundingbox.centerRotZ = 0;
		nboundingbox.rot.set(this.bodyRot);
		proxy.replaceBoundingbox(this,nboundingbox);
		((ModifiedBoundingBox)this.boundingBox).updateOBB(this.posX,this.posY,this.posZ);
		ignoreFrustumCheck = true;
		gunpos[0][0] = 2.17;
		gunpos[0][1] = 1.27;
		gunpos[0][2] = 0.19;
		gunpos[1][0] = -1.96;
		gunpos[1][1] = 1.27;
		gunpos[1][2] = 0.19;
		gunpos[2][0] = 2.9;
		gunpos[2][1] = 1.03;
		gunpos[2][2] = -0.41;
		gunpos[3][0] = -2.68;
		gunpos[3][1] = 1.03;
		gunpos[3][2] = -0.41;

		gunpos[4][0] = 0.1176;
		gunpos[4][1] = 0.8190;
		gunpos[4][2] = 6.4434;

		gunpos[5][0] = 0.1260 - gunpos[4][0];
		gunpos[5][1] = 0.5770 - gunpos[4][1];
		gunpos[5][2] = 7.8844 - gunpos[4][2];
		this.fireCycle1 = 1;
		this.fireCycle2 = 30;
		turret = new TurretObj(worldObj);
		{
			turret.onmotherPos = new Vector3d(gunpos[4][0],gunpos[4][1],-gunpos[4][2]);
			turret.currentEntity = this;
			turret.powor = 26;
			turret.spread = 8;
			turret.ex = 0.1f;
			turret.canex = false;
			turret.guntype = 10;
			turret.firesound = "handmadeguns:handmadeguns.fire";
			turret.bulletmodel = "byfrou01_GreenTracer";
			turret.turretanglelimtPitchmin = -10;
			turret.turretanglelimtPitchMax = 30;
			turret.turretanglelimtYawmin = -45;
			turret.turretanglelimtYawMax = 45;
			turret.cycle_setting = 0;
			turret.flushoffset = 1.2f;
		}
	}

	@Override
	protected void entityInit() {
		dataWatcher.addObject(turretyawDataID,Float.valueOf(0));
		dataWatcher.addObject(turretpitchDataID,Float.valueOf(0));
	}

	public double getMountedYOffset() {
		return 1.0D;
	}



	public boolean attackEntityFrom(DamageSource source, float par2)
	{
		if(par2 <= 10){
			this.playSound("random.anvil_land", 0.5F, 1F);
			return false;
		}else if(par2 > 10 && par2 <= 49){
			this.playSound("random.anvil_land", 0.5F, 1.5F);
			par2 = par2 /2;
		}
		if(source.getEntity() == null)return false;
		for(Entity aseat : this.childEntities) {
			if (aseat != null && aseat.riddenByEntity == source.getEntity()) {
				return false;
			}
		}
		health -=par2;
		return super.attackEntityFrom(source, par2);

	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {
		this.health = p_70037_1_.getFloat("health");
		this.rocket = p_70037_1_.getInteger("rocket");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {
		p_70014_1_.setFloat("health",health);
		p_70014_1_.setInteger("rocket",rocket);
	}
	public boolean canBePushed()
	{
		return true;
	}
	public boolean canBeCollidedWith()
	{
		return true;
	}
	public boolean shouldRiderSit(){
		return true;
	}
	public void onUpdate()
	{
		double backmotionY1 = motionY;
		super.onUpdate();
		motionY = backmotionY1;
		Vector3d tailwingvector = Calculater.transformVecByQuat(new Vector3d(unitY), bodyRot);
		Vector3d bodyvector = Calculater.transformVecByQuat(new Vector3d(unitZ), bodyRot);
		Vector3d mainwingvector = Calculater.transformVecByQuat(new Vector3d(unitX), bodyRot);
		Calculater.transformVecforMinecraft(tailwingvector);
		Calculater.transformVecforMinecraft(bodyvector);
		Calculater.transformVecforMinecraft(mainwingvector);
		throttle -=0.01;
		if(!isinit){
			initseat();
		}
		if(worldObj.isRemote){
			prevbodyrotationYaw = bodyrotationYaw;
			prevbodyrotationPitch = bodyrotationPitch;
			prevbodyrotationRoll = bodyrotationRoll;
			prevbodyrotationYaw=wrapAngleTo180_float(prevbodyrotationYaw);
			bodyrotationYaw = wrapAngleTo180_float(bodyrotationYaw);
			if(this.health <= this.maxhealth/2) {
				if (this.health <= this.maxhealth / 4) {
					this.worldObj.spawnParticle("smoke", this.posX + 2*mainwingvector.x, this.posY + 2*mainwingvector.y, this.posZ + 2*mainwingvector.z, 0.0D, 0.0D, 0.0D);
					this.worldObj.spawnParticle("smoke", this.posX - 2*mainwingvector.x, this.posY - 2*mainwingvector.y, this.posZ - 2*mainwingvector.z, 0.0D, 0.0D, 0.0D);
					int rx = this.worldObj.rand.nextInt(5);
					int rz = this.worldObj.rand.nextInt(5);
					this.worldObj.spawnParticle("flame", this.posX - 2 + rx, this.posY + 2D, this.posZ - 2 + rz, 0.0D, 0.0D, 0.0D);
					this.worldObj.spawnParticle("flame", this.posX - 2 + rx, this.posY + 2D, this.posZ - 2 + rz, 0.0D, 0.0D, 0.0D);
				} else {
					this.worldObj.spawnParticle("smoke", this.posX + 2, this.posY + 2D, this.posZ - 1, 0.0D, 0.0D, 0.0D);
				}
			}
			double[] xyz = Calculater.eulerfrommatrix(Calculater.matrixfromQuat(bodyRot));
			bodyrotationPitch = (float) toDegrees(xyz[0]);
			if(!Double.isNaN(xyz[1])){
				bodyrotationYaw = (float) toDegrees(xyz[1]);
			}
			bodyrotationRoll = (float) toDegrees(xyz[2]);
			turret(mainwingvector,tailwingvector,bodyvector);
		}else{
			GVCMPacketHandler.INSTANCE.sendToAll(new GVCPakcetVehicleState(this.getEntityId(),bodyRot, throttle,trigger1,trigger2));
			for(int x = (int)this.boundingBox.minX+3;x<=this.boundingBox.maxX-3;x++){
				for(int y = (int)this.boundingBox.minY+3;y<=this.boundingBox.maxY-3;y++){
					for(int z = (int)this.boundingBox.minZ+3;z<=this.boundingBox.maxZ-3;z++){
						Block collidingblock = worldObj.getBlock(x,y,z);
						if(collidingblock.getMaterial() == Material.leaves || collidingblock.getMaterial() == Material.wood || collidingblock.getMaterial() == Material.glass || collidingblock.getMaterial() == Material.cloth){
							worldObj.setBlockToAir(x,y,z);
						}
					}
				}
			}

			if(childEntities[5] != null && childEntities[5].riddenByEntity instanceof EntitySoBase){
				control();
				if(childEntities[5].riddenByEntity.isDead)this.setDead();
			}
			FCS(mainwingvector,tailwingvector,bodyvector);
			turret(mainwingvector,tailwingvector,bodyvector);

			double[] xyz = Calculater.eulerfrommatrix(Calculater.matrixfromQuat(bodyRot));
			bodyrotationPitch = (float) toDegrees(xyz[0]);
			if(!Double.isNaN(xyz[1])){
				bodyrotationYaw = (float) toDegrees(xyz[1]);
			}
			bodyrotationRoll = (float) toDegrees(xyz[2]);


			this.rotationYaw = bodyrotationYaw;
			this.rotationPitch = bodyrotationPitch;
			if(this.throttle >= 0.2){
				if(this.getEntityData().getFloat("GunshotLevel")<4) soundedentity.add(this);
				this.getEntityData().setFloat("GunshotLevel",4);
				this.playSound("gvcmob:gvcmob.heli", 4F, 0.8f + throttle /25);
			}
		}
		motionUpdate(mainwingvector,tailwingvector,bodyvector);
		seatUpdate(mainwingvector,tailwingvector,bodyvector);
	}

	void control(){
		EntitySoBase pilot = (EntitySoBase) childEntities[5].riddenByEntity;
		pilot.viewWide = 1.57f;
		int genY = this.worldObj.getHeightValue((int) this.posX, (int) this.posZ) + 15;
		if (this.posY-genY < 10) {
			if(throttle <4f)
				throttle += 0.025;
		}else
		if (this.posY-genY < 20) {
			if(throttle <3f)
			throttle += 0.025;
		}
		if (this.posY-genY > 20) {
			if(throttle >2.2f)
			throttle -= 0.025;
		}
		switch (mode){
			case 1: {
				this.bodyrotationPitch = wrapAngleTo180_float(this.bodyrotationPitch);
				float targetpitch = 30;
				float Angulardifference = wrapAngleTo180_float(bodyrotationPitch - targetpitch);
				if (abs(Angulardifference) > 2) {
					if (Angulardifference < 0) {
						pitchladder++;
					} else {
						pitchladder--;
					}
				}

				if (abs(bodyrotationRoll) > 10) {
					if (bodyrotationRoll < 0) {
						rollladder++;
					} else {
						rollladder--;
					}
				}
				modechangecool++;
				if (modechangecool > 60) {
					mode = 0;
					modechangecool = 0;
				}
				break;
			}
			case 0: {
				if (pilot.getAttackTarget() != null) {
					EntityLivingBase target = pilot.getAttackTarget();
					double d5 = target.posX - this.posX;
					double d7 = target.posZ - this.posZ;
					float targetrote = wrapAngleTo180_float(-(float) toDegrees(atan2(d5, d7)));
					boolean result1, result2;
					float Angulardifference = wrapAngleTo180_float(bodyrotationYaw - targetrote);
					if (abs(Angulardifference) > 2) {
						if (Angulardifference < 0) {
							yawladder++;
						} else {
							yawladder--;
						}
						result1 = false;
					} else {
						result1 = true;
					}
					this.bodyrotationPitch = wrapAngleTo180_float(this.bodyrotationPitch);
					if(bodyrotationPitch>45){
						mode = 1;
					}
					float targetpitch = wrapAngleTo180_float(-(float) CalculateGunElevationAngle(this, target, (float) (0.1 * cfg_defgravitycof), 6)[0]);
					Angulardifference = wrapAngleTo180_float(bodyrotationPitch - targetpitch);
					if (abs(Angulardifference) > 2) {
						if (Angulardifference < 0) {
							pitchladder++;
						} else {
							pitchladder--;
						}
						result2 = false;
					} else {
						result2 = true;
					}

					if (abs(bodyrotationRoll) > 10) {
						if (bodyrotationRoll < 0) {
							rollladder++;
						} else {
							rollladder--;
						}
					}

					trigger1 = trigger2 = false;
					if (result1 && result2) {
						trigger1 = trigger2 = true;
					}
				} else {
					this.bodyrotationPitch = wrapAngleTo180_float(this.bodyrotationPitch);
					float targetpitch = 0;
					float Angulardifference = wrapAngleTo180_float(bodyrotationPitch - targetpitch);
					if (Angulardifference < 0) {
						pitchladder += 0.5;
					} else {
						pitchladder -= 0.5;
					}

					if (bodyrotationRoll < 0) {
						rollladder += 0.5;
					} else {
						rollladder -= 0.5;
					}
				}
				break;
			}
		}
		if (throttle < 0) {
			throttle = 0;
		}
		if (throttle > 5) {
			throttle = 5;
		}
		throttle += 0.01;
		throttle = throttle - (throttle - 2.5f) * 0.01f;
		yawladder *= 0.8;
		rollladder *=0.8;
		pitchladder *=0.8;

		if (abs(pitchladder) > 0.001) {
			Vector3d axisx = Calculater.transformVecByQuat(new Vector3d(unitX), bodyRot);
			AxisAngle4d axisxangled = new AxisAngle4d(axisx, toRadians(-pitchladder / 4));
			bodyRot = Calculater.quatRotateAxis(bodyRot, axisxangled);
		}
		if (abs(yawladder) > 0.001) {
			Vector3d axisy = Calculater.transformVecByQuat(new Vector3d(unitY), bodyRot);
			AxisAngle4d axisyangled = new AxisAngle4d(axisy, toRadians(yawladder / 4));
			bodyRot = Calculater.quatRotateAxis(bodyRot, axisyangled);
		}
		if (abs(rollladder) > 0.001) {
			Vector3d axisz = Calculater.transformVecByQuat(new Vector3d(unitZ), bodyRot);
			AxisAngle4d axiszangled = new AxisAngle4d(axisz, toRadians(rollladder / 4));
			bodyRot = Calculater.quatRotateAxis(bodyRot, axiszangled);
		}
		double[] xyz = Calculater.eulerfrommatrix(Calculater.matrixfromQuat(bodyRot));
		bodyrotationPitch = (float) toDegrees(xyz[0]);
		if(!Double.isNaN(xyz[1])){
			bodyrotationYaw = (float) toDegrees(xyz[1]);
		}
		bodyrotationRoll = (float) toDegrees(xyz[2]);
		childEntities[5].riddenByEntity.rotationYaw = bodyrotationYaw;
		while (this.bodyrotationYaw - this.prevbodyrotationYaw < -180.0F)
		{
			this.prevbodyrotationYaw -= 360.0F;
		}

		while (this.bodyrotationYaw - this.prevbodyrotationYaw >= 180.0F)
		{
			this.prevbodyrotationYaw += 360.0F;
		}

		childEntities[5].riddenByEntity.prevRotationYaw = prevbodyrotationYaw;
		childEntities[5].riddenByEntity.rotationPitch = bodyrotationPitch;
		childEntities[5].riddenByEntity.prevRotationPitch = prevbodyrotationPitch;
	}

	void turret(Vector3d mainwingvector,Vector3d tailwingvector,Vector3d bodyvector){
		if(childEntities[4] != null && childEntities[4].riddenByEntity instanceof EntityLiving ) {
			EntityLiving gunner = (EntityLiving) childEntities[4].riddenByEntity;
			if (childEntities[5] != null && childEntities[5].riddenByEntity instanceof EntityLiving) {
				gunner.setAttackTarget(((EntityLiving) childEntities[5].riddenByEntity).getAttackTarget());
				if (((EntityLiving) childEntities[5].riddenByEntity).getAttackTarget() != null)
					gunner.getLookHelper().setLookPositionWithEntity(((EntityLiving) childEntities[5].riddenByEntity).getAttackTarget(), 180, 180);
				gunner.getLookHelper().onUpdateLook();
			}
			turret.currentEntity = gunner;
			turret.update(bodyRot, new Vector3d(this.posX, this.posY, -this.posZ));
			boolean insight = turret.aimtoAngle(gunner.getRotationYawHead(), gunner.rotationPitch);
			if(!worldObj.isRemote) {
				turretYaw = turret.turretrotationYaw;
				turretPitch = turret.turretrotationPitch;
				dataWatcher.updateObject(turretyawDataID,Float.valueOf((float) turretYaw));
				dataWatcher.updateObject(turretpitchDataID,Float.valueOf((float) turretPitch));
			}else {
				turretYaw = dataWatcher.getWatchableObjectFloat(turretyawDataID);
				turretPitch = dataWatcher.getWatchableObjectFloat(turretpitchDataID);
			}
			if (gunner.getAttackTarget() != null && gunner.getEntitySenses().canSee(gunner.getAttackTarget())) {
				turretTrigger = insight;
			}
			if (!worldObj.isRemote) {
				if (turretTrigger) {
					{
						turret.fire();
					}
					turretTrigger = false;
				}
			}
//			Quat4d gunnerRot = new Quat4d(0,0,0,1);
//			{
//				AxisAngle4d axisxangled = new AxisAngle4d(unitX, -toRadians(gunner.rotationPitch)/2);
//				gunnerRot = Calculater.quatRotateAxis(gunnerRot,axisxangled);
//			}
//			{
//				AxisAngle4d axisxangled = new AxisAngle4d(unitY, toRadians(gunner.getRotationYawHead())/2);
//				gunnerRot = Calculater.quatRotateAxis(gunnerRot,axisxangled);
//			}
//			{
//				Vector3d axisx = Calculater.transformVecByQuat(new Vector3d(unitX), bodyRot);
//				AxisAngle4d axisxangled = new AxisAngle4d(axisx, toRadians(bodyrotationPitch)/2);
//				gunnerRot = Calculater.quatRotateAxis(gunnerRot,axisxangled);
//			}
//			{
//				Vector3d axisy = Calculater.transformVecByQuat(new Vector3d(unitY), bodyRot);
//				AxisAngle4d axisyangled = new AxisAngle4d(axisy, toRadians(-bodyrotationYaw)/2);
//				gunnerRot = Calculater.quatRotateAxis(gunnerRot,axisyangled);
//			}
//			{
//				Vector3d axisz = Calculater.transformVecByQuat(new Vector3d(unitZ), bodyRot);
//				AxisAngle4d axiszangled = new AxisAngle4d(axisz, toRadians(-bodyrotationRoll)/2);
//				gunnerRot = Calculater.quatRotateAxis(gunnerRot,axiszangled);
//			}
//
//			double[] xyz = Calculater.eulerfrommatrix(Calculater.matrixfromQuat(gunnerRot));
//			turretPitch = toDegrees(xyz[0]);
//			turretYaw = toDegrees(xyz[1]);
//			boolean insight = true;
//			if(turretPitch<-10){
//				turretPitch = -10;
//				insight = false;
//			}else if(turretPitch>30){
//				turretPitch = 30;
//				insight = false;
//			}
//			if(turretYaw<-45){
//				turretYaw = -45;
//				insight = false;
//			}else if(turretYaw>45){
//				turretYaw = 45;
//				insight = false;
//			}
//
////			System.out.println("debug RotationYawHead" + gunner.getRotationYawHead());
////			System.out.println("debug turretYaw" + turretYaw);
////			System.out.println("debug rotationPitch" + gunner.rotationPitch);
////			System.out.println("debug turretPitch" + turretPitch);
//			turretRot = new Quat4d(bodyRot);
//			{
//				Vector3d axisx = Calculater.transformVecByQuat(new Vector3d(unitX), bodyRot);
//				AxisAngle4d axisxangled = new AxisAngle4d(axisx, toRadians(-turretPitch)/2);
//				turretRot = Calculater.quatRotateAxis(turretRot,axisxangled);
//			}
//			{
//				Vector3d axisy = Calculater.transformVecByQuat(new Vector3d(unitY), bodyRot);
//				AxisAngle4d axisyangled = new AxisAngle4d(axisy, toRadians(turretYaw)/2);
//				turretRot = Calculater.quatRotateAxis(turretRot,axisyangled);
//			}
//			if(gunner.getAttackTarget() != null && gunner.getEntitySenses().canSee(gunner.getAttackTarget())){
//				turretTrigger = insight;
//			}
//			if(!worldObj.isRemote){
//				if(turretTrigger){
//					{
//						HMGEntityBullet var3 = new HMGEntityBullet(this.worldObj, childEntities[4].riddenByEntity, 30, 8, 3);
//						Vector3d mainwingvector2 = Calculater.transformVecByQuat(new Vector3d(unitX), turretRot);
//						Vector3d tailwingvector2 = Calculater.transformVecByQuat(new Vector3d(unitY), turretRot);
//						Vector3d bodyvector2 = Calculater.transformVecByQuat(new Vector3d(unitZ), turretRot);
//						Calculater.transformVecforMinecraft(mainwingvector2);
//						Calculater.transformVecforMinecraft(tailwingvector2);
//						Calculater.transformVecforMinecraft(bodyvector2);
//						var3.setLocationAndAngles(
//								this.posX + mainwingvector.x * gunpos[4][0] +     tailwingvector.x * (gunpos[4][1] - 2.5)   - bodyvector.x * gunpos[4][2] 	+   mainwingvector2.x * gunpos[5][0]       + tailwingvector2.x * (gunpos[5][1] - 2.5) - bodyvector2.x * gunpos[5][2],
//								this.posY + mainwingvector.y * gunpos[4][0] + 2.5 + tailwingvector.y * (gunpos[4][1] - 2.5) - bodyvector.y * gunpos[4][2]  	+ 	mainwingvector2.y * gunpos[5][0] + 2.5 + tailwingvector2.y * (gunpos[5][1] - 2.5) - bodyvector2.y * gunpos[5][2],
//								this.posZ + mainwingvector.z * gunpos[4][0] +     tailwingvector.z * (gunpos[4][1] - 2.5)   - bodyvector.z * gunpos[4][2] 	+   mainwingvector2.z * gunpos[5][0]       + tailwingvector2.z * (gunpos[5][1] - 2.5) - bodyvector2.z * gunpos[5][2],
//								bodyrotationYaw,bodyrotationPitch);
////						var3.setHeadingFromThrower(bodyrotationPitch, this.bodyrotationYaw, 0, 8, 10F);
//						var3.motionX = bodyvector2.x * -6 + this.rand.nextGaussian() * (double)(this.rand.nextBoolean() ? -1 : 1) * 0.01 * 3;
//						var3.motionY = bodyvector2.y * -6 + this.rand.nextGaussian() * (double)(this.rand.nextBoolean() ? -1 : 1) * 0.01 * 3;
//						var3.motionZ = bodyvector2.z * -6 + this.rand.nextGaussian() * (double)(this.rand.nextBoolean() ? -1 : 1) * 0.01 * 3;
//						var3.bulletTypeName = "byfrou01_GreenTracer";
//						this.worldObj.spawnEntityInWorld(var3);
//					}
//					if(this.getEntityData().getFloat("GunshotLevel")<4)
//						soundedentity.add(this);
//					this.getEntityData().setFloat("GunshotLevel",4);
//					this.playSound("gvcguns:gvcguns.fire", 4.0F, 0.5F);
//					turretTrigger = false;
//				}
//			}
//		}else {
//			turretTrigger = false;
//		}
		}else {
			turretTrigger = false;
		}
	}

	public void initseat(){
		for (int i = 0; i< childEntities.length; i++) {
			childInfo[i] = new ChildInfo();
			if(!worldObj.isRemote) {
				childEntities[i] = new GVCEntityChild(worldObj,1,1,true);
				childEntities[i].setLocationAndAngles(this.posX,this.posY,this.posZ,0,0);
				childEntities[i].master = this;
				childEntities[i].idinmasterEntityt = i;
				if(i>5){
					childEntities[i].rideable = false;
				}
				worldObj.spawnEntityInWorld(childEntities[i]);
			}
			isinit = true;
			switch (i){
				case 0:
					childInfo[i].pos[0] = -0.73;
					childInfo[i].pos[1] = 1.1;
					childInfo[i].pos[2] = 1.75;
					break;
				case 1:
					childInfo[i].pos[0] = -0.73;
					childInfo[i].pos[1] = 1.1;
					childInfo[i].pos[2] = 1.00;
					break;
				case 2:
					childInfo[i].pos[0] = 1.0;
					childInfo[i].pos[1] = 1.1;
					childInfo[i].pos[2] = 1.75;
					break;
				case 3:
					childInfo[i].pos[0] = 1.0;
					childInfo[i].pos[1] = 1.1;
					childInfo[i].pos[2] = 1.00;
					break;
				case 4:
					childInfo[i].pos[0] = 0.15;
					childInfo[i].pos[1] = 0.7;
					childInfo[i].pos[2] = 5.8;
					break;
				case 5:
					childInfo[i].pos[0] = 0.24;
					childInfo[i].pos[1] = 1.4;
					childInfo[i].pos[2] = 4;
					break;
			}
		}
	}

	void FCS(Vector3d mainwingvector,Vector3d tailwingvector,Vector3d bodyvector){
		if (trigger1) {
			for(int i = 0;i<2;i++){
				HMGEntityBullet var3 = new HMGEntityBullet(this.worldObj, childEntities[5].riddenByEntity, 40, 8, 3);
				var3.gra = 0.1f;
				var3.setLocationAndAngles(
						this.posX + mainwingvector.x * gunpos[i][0] +     tailwingvector.x * (gunpos[i][1] - 2.5) - bodyvector.x * gunpos[i][2]
						, this.posY + mainwingvector.y * gunpos[i][0] + 2 + tailwingvector.y * (gunpos[i][1] - 2.5) - bodyvector.y * gunpos[i][2]
						, this.posZ + mainwingvector.z * gunpos[i][0] +     tailwingvector.z * (gunpos[i][1] - 2.5) - bodyvector.z * gunpos[i][2]
						,bodyrotationYaw,bodyrotationPitch);
//						var3.setHeadingFromThrower(bodyrotationPitch, this.bodyrotationYaw, 0, 8, 10F);
				var3.motionX = bodyvector.x * -6 + this.rand.nextGaussian() * (double)(this.rand.nextBoolean() ? -1 : 1) * 0.01 * 3;
				var3.motionY = bodyvector.y * -6 + this.rand.nextGaussian() * (double)(this.rand.nextBoolean() ? -1 : 1) * 0.01 * 3;
				var3.motionZ = bodyvector.z * -6 + this.rand.nextGaussian() * (double)(this.rand.nextBoolean() ? -1 : 1) * 0.01 * 3;
				var3.bulletTypeName = "byfrou01_GreenTracer";
				this.worldObj.spawnEntityInWorld(var3);
			}
			if(this.getEntityData().getFloat("GunshotLevel")<4)
				soundedentity.add(this);
			this.getEntityData().setFloat("GunshotLevel",4);
			this.playSound("gvcguns:gvcguns.fire", 4.0F, 0.5F);
			trigger1 = false;
		}
		if (trigger2) {
			if (rocket > 0) {
				rocket--;
				for(int i = 2;i<4;i++){
					HMGEntityBulletRocket var3 = new HMGEntityBulletRocket(this.worldObj, childEntities[5].riddenByEntity, 100, 8, 8, 0.5F, GVCMobPlus.cfg_blockdestory);
					var3.gra = 0.1f;
					var3.setLocationAndAngles(
							this.posX + mainwingvector.x * gunpos[i][0] +     tailwingvector.x * (gunpos[i][1] - 2.5) - bodyvector.x * gunpos[i][2]
							, this.posY + mainwingvector.y * gunpos[i][0] + 2 + tailwingvector.y * (gunpos[i][1] - 2.5) - bodyvector.y * gunpos[i][2]
							, this.posZ + mainwingvector.z * gunpos[i][0] +     tailwingvector.z * (gunpos[i][1] - 2.5) - bodyvector.z * gunpos[i][2]
							,bodyrotationYaw,bodyrotationPitch);
					var3.acceleration = 1.3f;
					var3.motionX = bodyvector.x * -3 + this.rand.nextGaussian() * (double)(this.rand.nextBoolean() ? -1 : 1) * 0.01 * 6;
					var3.motionY = bodyvector.y * -3 + this.rand.nextGaussian() * (double)(this.rand.nextBoolean() ? -1 : 1) * 0.01 * 6;
					var3.motionZ = bodyvector.z * -3 + this.rand.nextGaussian() * (double)(this.rand.nextBoolean() ? -1 : 1) * 0.01 * 6;
					this.worldObj.spawnEntityInWorld(var3);
				}
				if(this.getEntityData().getFloat("GunshotLevel")<4)
					soundedentity.add(this);
				this.getEntityData().setFloat("GunshotLevel",4);
				this.playSound("gvcmob:gvcmob.missile1", 4.0F, 1.0F);
			}
			trigger2 = false;
		}
	}

	void seatUpdate(Vector3d mainwingvector,Vector3d tailwingvector,Vector3d bodyvector){
		for(int i = 0; i< childEntities.length; i++){
			GVCEntityChild achild = childEntities[i];
			if(achild != null) {
				achild.setLocationAndAngles(
						this.posX + mainwingvector.x * this.childInfo[i].pos[0] + tailwingvector.x * (this.childInfo[i].pos[1] - 2.5) - bodyvector.x * this.childInfo[i].pos[2]
						, this.posY + mainwingvector.y * this.childInfo[i].pos[0] + 2 + tailwingvector.y * (this.childInfo[i].pos[1] - 2.5) - bodyvector.y * this.childInfo[i].pos[2]
						, this.posZ + mainwingvector.z * this.childInfo[i].pos[0] + tailwingvector.z * (this.childInfo[i].pos[1] - 2.5) - bodyvector.z * this.childInfo[i].pos[2]
						, this.bodyrotationYaw, this.bodyrotationPitch);
				achild.prevPosX = this.prevPosX + mainwingvector.x * this.childInfo[i].pos[0] + tailwingvector.x * (this.childInfo[i].pos[1] - 2.5) - bodyvector.x * this.childInfo[i].pos[2];
				achild.prevPosY = this.prevPosY + mainwingvector.y * this.childInfo[i].pos[0] + 2 + tailwingvector.y * (this.childInfo[i].pos[1] - 2.5) - bodyvector.y * this.childInfo[i].pos[2];
				achild.prevPosZ = this.prevPosZ + mainwingvector.z * this.childInfo[i].pos[0] + tailwingvector.z * (this.childInfo[i].pos[1] - 2.5) - bodyvector.z * this.childInfo[i].pos[2];
				achild.master = this;
				if(achild.riddenByEntity != null) {
					achild.riddenByEntity.posX = this.prevPosX + (this.posX - this.prevPosX) + mainwingvector.x * this.childInfo[i].pos[0] + tailwingvector.x * (this.childInfo[i].pos[1] - 2.5) - bodyvector.x * this.childInfo[i].pos[2];
					achild.riddenByEntity.posY = this.prevPosY + (this.posY - this.prevPosY) + mainwingvector.y * this.childInfo[i].pos[0] + 2 + tailwingvector.y * (this.childInfo[i].pos[1] - 2.5) - bodyvector.y * this.childInfo[i].pos[2] + achild.riddenByEntity.yOffset;
					achild.riddenByEntity.posZ = this.prevPosZ + (this.posZ - this.prevPosZ) + mainwingvector.z * this.childInfo[i].pos[0] + tailwingvector.z * (this.childInfo[i].pos[1] - 2.5) - bodyvector.z * this.childInfo[i].pos[2];
				}
				if(!worldObj.isRemote && achild.riddenByEntity==null && (i == 5 || rand.nextInt(50) == 0)){
					EntitySoBase newRider = null;
					int type = rand.nextInt(3);
					switch (type){
						case 0:
							newRider = new GVCEntitySoldierMG(worldObj);
							break;
						case 1:
							newRider = new GVCEntitySoldierRPG(worldObj);
							break;
						case 2:
							newRider = new GVCEntitySoldier(worldObj);
							break;
						default:
							newRider = new GVCEntitySoldier(worldObj);
					}
					worldObj.spawnEntityInWorld(newRider);
					if(i != 5)
						newRider.addRandomArmor();
					newRider.mountEntity(achild);
					achild.riddenByEntity = newRider;
					newRider.ridingEntity = achild;

				}
			}else {
				{
					achild = new GVCEntityChild(worldObj);
					achild.setLocationAndAngles(
							this.posX + mainwingvector.x * childInfo[i].pos[0] + tailwingvector.x * (childInfo[i].pos[1] - 2.5) - bodyvector.x * childInfo[i].pos[2]
							, this.posY + mainwingvector.y * childInfo[i].pos[0] + 2 + tailwingvector.y * (childInfo[i].pos[1] - 2.5) - bodyvector.y * childInfo[i].pos[2]
							, this.posZ + mainwingvector.z * childInfo[i].pos[0] + tailwingvector.z * (childInfo[i].pos[1] - 2.5) - bodyvector.z * childInfo[i].pos[2]
							, bodyrotationYaw, bodyrotationPitch);
					achild.master = this;
					worldObj.spawnEntityInWorld(achild);
				}
			}
		}
	}

	void motionUpdate(Vector3d mainwingvector,Vector3d tailwingvector,Vector3d bodyvector){
		if (!(Double.isNaN(tailwingvector.x) || Double.isNaN(tailwingvector.y) || Double.isNaN(tailwingvector.z))) {
			motionX += tailwingvector.x * throttle * 0.196;
			motionY += tailwingvector.y * throttle * 0.196;
			motionZ += tailwingvector.z * throttle * 0.196;
		}
		if (throttle > 5) {
			throttle = 5;
		}
		if (throttle < 0) {
			throttle = 0;
		}
		this.fallDistance = 0;
		angletime += throttle * 60;
		prevangletime = angletime;
		angletime = wrapAngleTo180_float(angletime);
		while (this.angletime - this.prevangletime < -180.0F) {
			this.prevangletime -= 360.0F;
		}
		while (this.angletime - this.prevangletime >= 180.0F) {
			this.prevangletime += 360;
		}
		if(!worldObj.isRemote&& health < 0) {
			worldObj.createExplosion(this, posX, posY + 2, posZ, 4, true);
			worldObj.createExplosion(this, posX + 2, posY + 2, posZ, 3, true);
			worldObj.createExplosion(this, posX - 2, posY + 2, posZ, 3, true);
			worldObj.createExplosion(this, posX, posY + 2, posZ + 2, 3, true);
			worldObj.createExplosion(this, posX, posY + 2, posZ - 2, 3, true);
			setDead();
		}
		this.motionY -= 0.49;
		this.motionY *= 0.8;
		this.motionX *= 0.8;
		this.motionZ *= 0.8;
		double backmotionX = motionX;
		double backmotionY = motionY;
		double backmotionZ = motionZ;
		moveEntity(motionX, motionY, motionZ);
		if (this.isCollidedHorizontally) {
			if (backmotionX * backmotionX + backmotionY * backmotionY + backmotionZ * backmotionZ > 1) {
				attackEntityFrom(DamageSource.fall, (float) (backmotionX * backmotionX + backmotionY * backmotionY + backmotionZ * backmotionZ) * 20);
			}
		}
		if (this.onGround) {
			Vector3d axisx = new Vector3d(-cos(toRadians(bodyrotationYaw)), 0, sin(toRadians(bodyrotationYaw)));
			AxisAngle4d axisxangled = new AxisAngle4d(axisx, toRadians(-bodyrotationPitch / 10));
			bodyRot = Calculater.quatRotateAxis(bodyRot, axisxangled);
			
			axisx = Calculater.transformVecByQuat(new Vector3d(unitZ), bodyRot);
			if (bodyrotationRoll < 45 && bodyrotationRoll > -45) {
				axisxangled = new AxisAngle4d(axisx, toRadians(-bodyrotationRoll / 10));
			}
			if (bodyrotationRoll < -45 && bodyrotationRoll > -135) {
				attackEntityFrom(DamageSource.fall, (float) (20));
				axisxangled = new AxisAngle4d(axisx, toRadians((-90 - bodyrotationRoll) / 10));
			}
			if (bodyrotationRoll < 135 && bodyrotationRoll > 45) {
				attackEntityFrom(DamageSource.fall, (float) (20));
				axisxangled = new AxisAngle4d(axisx, toRadians((90 - bodyrotationRoll) / 10));
			}
			if (bodyrotationRoll > 135) {
				attackEntityFrom(DamageSource.fall, (float) (30));
				axisxangled = new AxisAngle4d(axisx, toRadians((180 - bodyrotationRoll) / 10));
			}
			if (bodyrotationRoll < -135) {
				attackEntityFrom(DamageSource.fall, (float) (30));
				axisxangled = new AxisAngle4d(axisx, toRadians((-180 - bodyrotationRoll) / 10));
			}
			bodyRot = Calculater.quatRotateAxis(bodyRot, axisxangled);
		}
		this.setPosition(posX,posY,posZ);
		nboundingbox.rot.set(this.bodyRot);
		nboundingbox.posX = this.posX;
		nboundingbox.posY = this.posY;
		nboundingbox.posZ = this.posZ;
	}
	public boolean isConverting() {
		return false;
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
		return fireCycle2;
	}

	@Override
	public int getfirecycleprogress2() {
		return cooltime2;
	}

	@Override
	public float getturretrotationYaw() {
		return 0;
	}

	@Override
	public float getbodyrotationYaw() {
		return bodyrotationYaw;
	}

	@Override
	public float getthrottle() {
		return (float) throttle;
	}

	@Override
	public void setBodyRot(Quat4d quat4d) {
		this.bodyRot = quat4d;
	}

	@Override
	public void setthrottle(float th) {
		this.throttle = th;
	}

	@Override
	public void setTrigger(boolean trig1, boolean trig2) {
		trigger1 = trig1;
		trigger2 = trig2;
	}

	@Override
	public GVCEntityChild[] getChilds() {
		return childEntities;
	}

	@Override
	public void addChild(GVCEntityChild seat) {
		if(seat.idinmasterEntityt != -1 && seat.idinmasterEntityt < childEntities.length){
			this.childEntities[seat.idinmasterEntityt] = seat;
			this.childEntities[seat.idinmasterEntityt].master = this;
		}
	}

	@Override
	public boolean isRidingEntity(Entity entity) {
		for(int i = 0; i < childEntities.length; i++){
			if(childEntities[i] != null){
				if(childEntities[i].riddenByEntity == entity)return true;
			}
		}
		return entity == this;
	}

	@Override
	public boolean isChild(Entity entity){
		for(Entity achild:childEntities){
			if(entity == achild)return true;
		}
		return entity == this;
	}

	@Override
	public int getpilotseatid() {
		return -1;
	}

	public void applyEntityCollision(Entity p_70108_1_)
	{
//		for(GVCEntityChild aseat: childEntities){
//			if(aseat != null && aseat.riddenByEntity == p_70108_1_)return;
//		}
//		super.applyEntityCollision(p_70108_1_);
	}
	@Override
	public boolean shouldRenderInPass(int pass)
	{
		return pass == 1 || pass==0;
	}
}
