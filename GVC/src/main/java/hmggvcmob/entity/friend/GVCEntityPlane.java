package hmggvcmob.entity.friend;


import hmvehicle.entity.parts.IMultiTurretVehicle;
import hmvehicle.entity.parts.Iplane;
import hmvehicle.entity.parts.ModifiedBoundingBox;
import hmvehicle.entity.parts.logics.IbaseLogic;
import hmvehicle.entity.parts.logics.PlaneBaseLogic;
import hmvehicle.entity.parts.turrets.TurretObj;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;
import java.util.ArrayList;

import static hmggvcmob.GVCMobPlus.proxy;
import static hmvehicle.Utils.addAllTurret;

public class GVCEntityPlane extends Entity implements Iplane,IMultiTurretVehicle
{
	PlaneBaseLogic baseLogic;
	
	
	public int rocket = 2;
	public float health = 150;
	public float maxhealth = 150;
	public float angletime;
	public int fireCycle1;
	public int cooltime;
	public int magazine;
	public float throttle;
	public int mode = 0;//0:attack 1:leave 2:follow player 3:go to home
//	public int soundtick = 0;
	public float[][] trackedEntityPos;
	public GVCEntityPlane(World par1World)
	{
		super(par1World);
		this.setSize(5f, 5f);
//		nboundingbox = new ModifiedBoundingBox(-20,-20,-20,20,20,20,0,0,-6.27,2.5,5,19);
//		nboundingbox.rot.set(this.bodyRot);
//		proxy.replaceBoundingbox(this,nboundingbox);
//		((ModifiedBoundingBox)this.boundingBox).update(this.posX,this.posY,this.posZ);
		ignoreFrustumCheck = true;
		this.fireCycle1 = 1;
		baseLogic = new PlaneBaseLogic(worldObj,this);
		
		baseLogic.speedfactor =    0.0043f;
		baseLogic.speedfactor_af = 0.002f;
		baseLogic.liftfactor = 0.04f;
		baseLogic.flapliftfactor = 0.00005f;
		baseLogic.flapdragfactor = 0.0000000001f;
		baseLogic.geardragfactor = 0.000000001f;
		baseLogic.dragfactor = 0.01f;
		baseLogic.gravity = 0.049f;
		baseLogic.stability = 600;
		baseLogic.stability2 = 0.5f;
		baseLogic.rotmotion_reduceSpeed = 0.05;
		
		
		baseLogic.rollspeed = 0.12f;
		baseLogic.pitchspeed = 0.15f;
		baseLogic.yawspeed = 0.05f;
		baseLogic.maxDive = 60;
		baseLogic.startDive = 30;
		baseLogic.maxClimb = -22;
		baseLogic.maxbank = 60;
		baseLogic.slipresist = 0.01f;
		baseLogic.throttle_gearDown = 0.25f;
		{
			TurretObj gun1 = new TurretObj(worldObj);
			gun1.onMotherPos = new Vector3d(0.04, 0.8659, 0);
			gun1.traverseSound = null;
			gun1.currentEntity = this;
			gun1.powor = 23;
			gun1.ex = 0.5f;
			gun1.cycle_setting = 0;
			gun1.flashscale = 1;
			gun1.firesound = "handmadeguns:handmadeguns.HeavyMachineGun";
			gun1.spread = 2;
			gun1.speed = 8;
			gun1.magazineMax = 500;
			gun1.magazinerem = 500;
			gun1.reloadTimer = 1200;
			gun1.canex = false;
			gun1.fireAll = false;
			gun1.guntype = 0;
			TurretObj gun2 = new TurretObj(worldObj);
			gun2.onMotherPos = new Vector3d(-0.04, 0.8659, 0);
			gun2.onMotherPos.sub(gun1.onMotherPos);
			gun2.traverseSound = null;
			gun2.currentEntity = this;
			gun2.powor = 23;
			gun2.ex = 0.5f;
			gun2.cycle_setting = 0;
			gun2.flashscale = 1;
			gun2.firesound = "handmadeguns:handmadeguns.HeavyMachineGun";
			gun2.spread = 2;
			gun2.speed = 8;
			gun2.magazineMax = 500;
			gun2.magazinerem = 500;
			gun2.reloadTimer = 1200;
			gun2.canex = false;
			gun2.fireAll = false;
			gun2.guntype = 0;
			gun1.addchild_triggerLinked(gun2);
			
			baseLogic.mainTurret = gun1;
		}
		{
			TurretObj missile1 = new TurretObj(worldObj);
			missile1.onMotherPos = new Vector3d(2.0399, 1.0591, -0.6568);
			missile1.traverseSound = null;
			missile1.currentEntity = this;
			missile1.powor = 600;
			missile1.acceler = 0.1f;
			missile1.induction_precision = 10;
			missile1.canHoming = true;
			missile1.semiActive = true;
			missile1.rock_to_Vehicle = true;
			missile1.ex = 3;
			missile1.cycle_setting = 1200;
			missile1.cycle_timer = -1;
			missile1.flashscale = 1;
			missile1.firesound = "handmadeguns:handmadeguns.firecannon";
			missile1.spread = 0;
			missile1.speed = 1;
			missile1.magazineMax = 4;
			missile1.magazinerem = 4;
			missile1.reloadTimer = 1000000;
			missile1.canex = true;
			missile1.fireAll = false;
			missile1.guntype = 3;
			missile1.seekerSize = 30;
			
			TurretObj missile2 = new TurretObj(worldObj);
			missile2.onMotherPos = new Vector3d(-2.0399, 1.0591, -0.6568);
			missile2.traverseSound = null;
			missile2.currentEntity = this;
			missile2.powor = 600;
			missile2.acceler = 0.1f;
			missile2.induction_precision = 10;
			missile2.canHoming = true;
			missile2.semiActive = true;
			missile2.rock_to_Vehicle = true;
			missile2.ex = 3;
			missile2.cycle_setting = 1200;
			missile2.cycle_timer = -1;
			missile2.flashscale = 1;
			missile2.firesound = "handmadeguns:handmadeguns.firecannon";
			missile2.spread = 0;
			missile2.speed = 1;
			missile2.magazineMax = 4;
			missile2.magazinerem = 4;
			missile2.reloadTimer = 1000000;
			missile2.canex = true;
			missile2.fireAll = false;
			missile2.guntype = 3;
			missile2.seekerSize = 30;
			
			missile1.addbrother(missile2);
			baseLogic.subTurret = missile1;
		}
		
		
		baseLogic.mainTurret.motherRotCenter = new Vector3d(baseLogic.rotcenter);
		baseLogic.subTurret.motherRotCenter = new Vector3d(baseLogic.rotcenter);
		
		baseLogic.riddenByEntitiesInfo[0].pos[0] = 0;
		baseLogic.riddenByEntitiesInfo[0].pos[1] = 1.1;
		baseLogic.riddenByEntitiesInfo[0].pos[2] = 3;
		baseLogic.displayModernHud = true;
		ModifiedBoundingBox nboundingbox = new ModifiedBoundingBox(-1.5,0,-1.5,1.5,5,1.5,0,0,-6.27,2.5,5,19);
		nboundingbox.rot.set(baseLogic.bodyRot);
		proxy.replaceBoundingbox(this,nboundingbox);
		((ModifiedBoundingBox)this.boundingBox).update(this.posX,this.posY,this.posZ);
	}

	@Override
	protected void entityInit() {
	}



	public boolean attackEntityFrom(DamageSource source, float par2)
	{
		if(source.getEntity() == this){
			return false;
		}
		if(isRidingEntity(source.getEntity())){
			return false;
		}
		if(par2 <= 10){
			this.playSound("random.anvil_land", 0.5F, 1F);
			return false;
		}else if(par2 > 10 && par2 <= 49){
			this.playSound("random.anvil_land", 0.5F, 1.5F);
			par2 = par2 /2;
		}
		health -= par2;
		return super.attackEntityFrom(source, par2);

	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {
		this.health = p_70037_1_.getFloat("health");
		baseLogic.bodyrotationYaw = p_70037_1_.getFloat("bodyrotationYaw");
		this.rocket = p_70037_1_.getInteger("rocket");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {
		p_70014_1_.setFloat("health",health);
		p_70014_1_.setFloat("bodyrotationYaw",baseLogic.bodyrotationYaw);
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
	@Override
	public boolean interactFirst(EntityPlayer player)
	{
//		if(!worldObj.isRemote && player.getCurrentEquippedItem()!= null && player.getCurrentEquippedItem().getItem() == GVCUtils.fn_missile && rocket<2){
//			player.inventory.consumeInventoryItem(GVCUtils.fn_rpg);
//			rocket+=1;
//			if(rocket>2){
//				rocket = 2;
//			}
//		}
		if(!isRidingEntity(player) && player.ridingEntity == null)pickupEntity(player,0);
		return false;
	}
	public void onUpdate()
	{
		super.onUpdate();
		baseLogic.onUpdate();
		if(health<0)setDead();
//		prevmotionVec.set(motionX,motionY,motionZ);
//		double backmotionY1 = motionY;
//		super.onUpdate();
//		motionY = backmotionY1;
//		Vector3d tailwingvector = Utils.transformVecByQuat(new Vector3d(unitY), bodyRot);
//		Vector3d bodyvector = Utils.transformVecByQuat(new Vector3d(unitZ), bodyRot);
//		Vector3d mainwingvector = Utils.transformVecByQuat(new Vector3d(unitX), bodyRot);
//		tailwingvector.normalize();
//		bodyvector.normalize();
//		mainwingvector.normalize();
//		Utils.transformVecforMinecraft(tailwingvector);
//		Utils.transformVecforMinecraft(bodyvector);
//		Utils.transformVecforMinecraft(mainwingvector);
//		if(!isinit){
//			initseat();
//		}
//		if(worldObj.isRemote){
//			prevbodyrotationYaw = bodyrotationYaw;
//			prevbodyrotationPitch = bodyrotationPitch;
//			prevbodyrotationRoll = bodyrotationRoll;
//			prevbodyrotationYaw=wrapAngleTo180_float(prevbodyrotationYaw);
//			bodyrotationYaw = wrapAngleTo180_float(bodyrotationYaw);
//			if(this.health <= this.maxhealth/2) {
//				if (this.health <= this.maxhealth / 4) {
//					this.worldObj.spawnParticle("smoke", this.posX + 2*mainwingvector.x, this.posY + 2*mainwingvector.y, this.posZ + 2*mainwingvector.z, 0.0D, 0.0D, 0.0D);
//					this.worldObj.spawnParticle("smoke", this.posX - 2*mainwingvector.x, this.posY - 2*mainwingvector.y, this.posZ - 2*mainwingvector.z, 0.0D, 0.0D, 0.0D);
//					int rx = this.worldObj.rand.nextInt(5);
//					int rz = this.worldObj.rand.nextInt(5);
//					this.worldObj.spawnParticle("flame", this.posX - 2 + rx, this.posY + 2D, this.posZ - 2 + rz, 0.0D, 0.0D, 0.0D);
//					this.worldObj.spawnParticle("flame", this.posX - 2 + rx, this.posY + 2D, this.posZ - 2 + rz, 0.0D, 0.0D, 0.0D);
//				} else {
//					this.worldObj.spawnParticle("smoke", this.posX + 2, this.posY + 2D, this.posZ - 1, 0.0D, 0.0D, 0.0D);
//				}
//			}
//			if(childEntities[0] != null && childEntities[0].riddenByEntity == FMLClientHandler.instance().getClientPlayerEntity()) {
//				camera.setDead();
//				camera = new EntityCameraDummy(this.worldObj);
//				camera.setLocationAndAngles(
//						this.posX + bodyvector.x + tailwingvector.x + mainwingvector.x,
//						this.posY + bodyvector.y + tailwingvector.y + mainwingvector.y,
//						this.posZ + bodyvector.z + tailwingvector.z + mainwingvector.z,
//						bodyrotationYaw,bodyrotationPitch);
//				prevbodyRot.x = bodyRot.x;
//				prevbodyRot.y = bodyRot.y;
//				prevbodyRot.z = bodyRot.z;
//				prevbodyRot.w = bodyRot.w;
//				childEntities[getpilotseatid()].isDead = false;
//				control(bodyvector);
//				hudUpdate();
//				childEntities[0].motionX = this.motionX;
//				childEntities[0].motionY = this.motionY;
//				childEntities[0].motionZ = this.motionZ;
//			}else{
//				tailwingvector = Utils.transformVecByQuat(new Vector3d(unitY), bodyRot);
//				bodyvector = Utils.transformVecByQuat(new Vector3d(unitZ), bodyRot);
//				mainwingvector = Utils.transformVecByQuat(new Vector3d(unitX), bodyRot);
//				tailwingvector.normalize();
//				bodyvector.normalize();
//				mainwingvector.normalize();
//				Utils.transformVecforMinecraft(tailwingvector);
//				Utils.transformVecforMinecraft(bodyvector);
//				Utils.transformVecforMinecraft(mainwingvector);
//
//				double[] xyz = Utils.eulerfrommatrix(Utils.matrixfromQuat(bodyRot));
//				bodyrotationPitch = (float) toDegrees(xyz[0]);
//				if(!Double.isNaN(xyz[1])){
//					bodyrotationYaw = (float) toDegrees(xyz[1]);
//				}
//				bodyrotationRoll = (float) toDegrees(xyz[2]);
//				throttle *= 0.9;
//			}
////			turret(mainwingvector,tailwingvector,bodyvector);
//		}else{
//			GVCMPacketHandler.INSTANCE.sendToAll(new HMVPakcetVehicleState(this.getEntityId(),bodyRot, throttle,trigger1,trigger2));
//			for(int x = (int)this.boundingBox.minX+3;x<=this.boundingBox.maxX-3;x++){
//				for(int y = (int)this.boundingBox.minY+3;y<=this.boundingBox.maxY-3;y++){
//					for(int z = (int)this.boundingBox.minZ+3;z<=this.boundingBox.maxZ-3;z++){
//						Block collidingblock = worldObj.getBlock(x,y,z);
//						if(collidingblock.getMaterial() == Material.leaves || collidingblock.getMaterial() == Material.wood || collidingblock.getMaterial() == Material.glass || collidingblock.getMaterial() == Material.cloth){
//							worldObj.setBlockToAir(x,y,z);
//						}
//					}
//				}
//			}
//
//			FCS(mainwingvector,tailwingvector,bodyvector);
////			turret(mainwingvector,tailwingvector,bodyvector);
//
//			double[] xyz = Utils.eulerfrommatrix(Utils.matrixfromQuat(bodyRot));
//			bodyrotationPitch = (float) toDegrees(xyz[0]);
//			if(!Double.isNaN(xyz[1])){
//				bodyrotationYaw = (float) toDegrees(xyz[1]);
//			}
//			bodyrotationRoll = (float) toDegrees(xyz[2]);
//
//
//			this.rotationYaw = bodyrotationYaw;
//			this.rotationPitch = bodyrotationPitch;
//			if(this.throttle >= 0.2){
//				if(this.getEntityData().getFloat("GunshotLevel")<4) soundedentity.add(this);
//				this.getEntityData().setFloat("GunshotLevel",4);
//				this.playSound("gvcmob:gvcmob.plane", 4F, 0.8f + throttle /25);
//			}
//		}
//		rotationmotion.normalize();
//		bodyRot.mul(rotationmotion);
//		rotationmotion.interpolate(new Quat4d(0,0,0,1),0.2d);
//		motionUpdate(mainwingvector,tailwingvector,bodyvector);
//
//		seatUpdate(mainwingvector,tailwingvector,bodyvector);
	}

	public void setVelocity(double p_70016_1_, double p_70016_3_, double p_70016_5_)
	{
		baseLogic.setVelocity(p_70016_1_,p_70016_3_,p_70016_5_);
	}

//	void control(Vector3d bodyvector){
//		illuminated = worldObj.getEntityByID(this.dataWatcher.getWatchableObjectInt(24));
//		missile = worldObj.getEntityByID(this.dataWatcher.getWatchableObjectInt(25)) instanceof HMGEntityBulletBase ? (HMGEntityBulletBase) worldObj.getEntityByID(this.dataWatcher.getWatchableObjectInt(25)) :null;
//		rocket = this.dataWatcher.getWatchableObjectInt(26);
//		health = this.dataWatcher.getWatchableObjectInt(27);
//		if (proxy.wclick()) {
//			throttle += 0.05;
//		}
//		if (proxy.aclick()) {
//			yawladder--;
//		}
//		if (proxy.sclick()) {
//			throttle -= 0.05;
//		}
//		if (proxy.dclick()) {
//			yawladder++;
//		}
//		trigger1 = trigger2 = false;
//		if (proxy.leftclick()) {
//			trigger1 = true;
//		}
//		if (proxy.rightclick()) {
//			trigger2 = true;
//		}
//		if(!FMLClientHandler.instance().getClient().isGamePaused()) {
//			mousex += Mouse.getDX() * 0.1;
//			mousey += Mouse.getDY() * 0.1;
//		}
//
//
//		yawladder*=0.8;
//		mousex *= 0.8f;
//		mousey *= 0.8f;
//		mousex = abs(mousex)>4 ? (mousex>0?mousex - 4f:mousex +4f):mousex * 0.9f;
//		mousey = abs(mousey)>4 ? (mousey>0?mousey - 4f:mousey +4f):mousey * 0.9f;
//		rollladder = abs(mousex)>4 ? (mousex>0?4f:-4f):mousex * 0.8f;
//		pitchladder = abs(mousey)>4 ? (mousey>0?4f:-4f):mousey * 0.8f;
//
//		Vector3d motionvec = new Vector3d(motionX, motionY, motionZ);
//		if(motionvec.length() > 0.1) {
//			double cos = -angle_cos(bodyvector, motionvec) * motionvec.length();
//			if (abs(pitchladder) > 0.001) {
//				Vector3d axisx = Utils.transformVecByQuat(new Vector3d(unitX), bodyRot);
//				AxisAngle4d axisxangled = new AxisAngle4d(unitX, toRadians(-pitchladder / 4 * cos*0.06));
//				rotationmotion = Utils.quatRotateAxis(rotationmotion, axisxangled);
//			}
//			if (abs(yawladder) > 0.001) {
//				AxisAngle4d axisyangled;
//				if(onGround && motionvec.length()<0.2){
//					Vector3d axisy = Utils.transformVecByQuat(new Vector3d(unitY), bodyRot);
//					axisyangled = new AxisAngle4d(unitY, toRadians(yawladder / 4 * cos * 0.2));
//				}else {
//					Vector3d axisy = Utils.transformVecByQuat(new Vector3d(unitY), bodyRot);
//					axisyangled = new AxisAngle4d(unitY, toRadians(yawladder / 4 * cos * 0.06));
//				}
//				rotationmotion = Utils.quatRotateAxis(rotationmotion, axisyangled);
//			}
//			if (abs(rollladder) > 0.001) {
//				Vector3d axisz = Utils.transformVecByQuat(new Vector3d(unitZ), bodyRot);
//				AxisAngle4d axiszangled = new AxisAngle4d(unitZ, toRadians(rollladder / 4 * cos*0.06));
//				rotationmotion = Utils.quatRotateAxis(rotationmotion, axiszangled);
//			}
//		}
//
//		double[] xyz = Utils.eulerfrommatrix(Utils.matrixfromQuat(bodyRot));
//		bodyrotationPitch = (float) toDegrees(xyz[0]);
//		if(!Double.isNaN(xyz[1])){
//			bodyrotationYaw = (float) toDegrees(xyz[1]);
//		}
//		bodyrotationRoll = (float) toDegrees(xyz[2]);
//		camera.rotationYawHead = bodyrotationYaw;
//		camera.prevRotationYawHead = prevbodyrotationYaw;
//		camera.prevRotationYaw = prevbodyrotationYaw;
//		camera.prevRotationPitch = prevbodyrotationPitch;
//		GVCMPacketHandler.INSTANCE.sendToServer(new HMVPakcetVehicleState(this.getEntityId(),bodyRot, throttle,trigger1,trigger2));
////				if(th<2.5){
////					th +=0.1;
////				}
////				if (proxy.wclick()) {
////					th += 0.1;
////					GVCMPacketHandler.INSTANCE.sendToServer(new HMVMMessageKeyPressed(16, this.getEntityId()));
////				}
////				if (proxy.aclick()) {
//////					GVCMPacketHandler.INSTANCE.sendToServer(new HMVMMessageKeyPressed(17, this.getEntityId()));
////					servera = true;
////				}
////				if (proxy.sclick()) {
////					th -= 0.1;
////					GVCMPacketHandler.INSTANCE.sendToServer(new HMVMMessageKeyPressed(18, this.getEntityId()));
////				}
////				if (proxy.dclick()) {
//////					GVCMPacketHandler.INSTANCE.sendToServer(new HMVMMessageKeyPressed(19, this.getEntityId()));
////					serverd = true;
////				}
////				if (proxy.leftclick()) {
////					GVCMPacketHandler.INSTANCE.sendToServer(new HMVMMessageKeyPressed(11, this.getEntityId()));
////				}
////				if (proxy.spaceKeyDown()) {
////					GVCMPacketHandler.INSTANCE.sendToServer(new HMVMMessageKeyPressed(12, this.getEntityId()));
////				}
////				if (proxy.fclick()) {
////					GVCMPacketHandler.INSTANCE.sendToServer(new HMVMMessageKeyPressed(20, this.getEntityId()));
////				}
////
////				th -=0.05;
////				if(th>5){
////					th = 5;
////				}
////				if(th<0){
////					th = 0;
////				}
//////				GVCMPacketHandler.INSTANCE.sendToServer(new HMVPacketMouseD(Mouse.getDX(),Mouse.getDY(),this.getEntityId()));
////				mousex += Mouse.getDX()*0.01;
////				mousey += Mouse.getDY()*0.01;
////				if (servera) {
////					yawladder +=-1;
////					servera = false;
////				}
////				if (serverd) {
////					yawladder +=1;
////					serverd = false;
////				}
////
////				prevmousex = mousex;
////				if (abs(mousex)>0.00001) {
////					mainwingvector = Utils.rotationVector_byAxisVector(bodyVector,mainwingvector, abs(mousex)>4 ? (mousex>0?4f:-4f):mousex);
////					tailwingvector = Utils.rotationVector_byAxisVector(bodyVector,tailwingvector, abs(mousex)>4 ? (mousex>0?4f:-4f):mousex);
////				}
////				mousex*=0.9;
////
////				prevmousey = mousey;
////				if (abs(mousey)>0.00001) {
////					bodyVector     = Utils.rotationVector_byAxisVector(mainwingvector,bodyVector    ,abs(mousey)>4?(mousey>0?-4f:4f):-mousey);
////					tailwingvector = Utils.rotationVector_byAxisVector(mainwingvector,tailwingvector,abs(mousey)>4?(mousey>0?-4f:4f):-mousey);
////				}
////				mousey*=0.9;
////				prevyawladder = yawladder;
////				yawladder *=0.8;
////				if(abs(yawladder) < 0.001) yawladder = prevyawladder =0;else {
////					bodyVector = Utils.rotationVector_byAxisVector(tailwingvector, bodyVector, yawladder);
////					mainwingvector = Utils.rotationVector_byAxisVector(tailwingvector, mainwingvector, yawladder);
////				}
////				bodyrotationPitch = wrapAngleTo180_float((float) toDegrees(asin(bodyVector.yCoord)));
////
////				Vec3 temp1 = Vec3.createVectorHelper(bodyVector.xCoord,bodyVector.yCoord,bodyVector.zCoord);
////				bodyrotationYaw = wrapAngleTo180_float((float) toDegrees(atan2(temp1.xCoord, temp1.zCoord)));
////
////				if(abs(bodyrotationPitch)<45) {
////					Vec3 temp = Vec3.createVectorHelper(mainwingvector.xCoord,mainwingvector.yCoord,mainwingvector.zCoord);
////					temp.rotateAroundY(-(float) toRadians(bodyrotationYaw));
////					temp.rotateAroundX(-(float) toRadians(bodyrotationPitch));
////					bodyrotationRoll = (float) toDegrees(atan2(temp.yCoord, temp.xCoord));
////				}else {
////					Vec3 temp = Vec3.createVectorHelper(tailwingvector.xCoord,tailwingvector.yCoord,tailwingvector.zCoord);
////					temp.rotateAroundY(-(float) toRadians(bodyrotationYaw));
////					temp.rotateAroundX(-(float) toRadians(bodyrotationPitch));
////					bodyrotationRoll = (float) toDegrees(atan2(temp.yCoord, temp.xCoord))-90;
////				}
////				GVCXMPacketSyncPMCHeliData packet = new GVCXMPacketSyncPMCHeliData(this.getEntityId(),bodyrotationYaw,bodyrotationPitch,bodyrotationRoll);
////				GVCMPacketHandler.INSTANCE.sendToServer(packet);
////				FMLClientHandler.instance().getClient().mouseHelper.deltaX = 0;
////				FMLClientHandler.instance().getClient().mouseHelper.deltaY = 0;
////
////				setRotationYaw(bodyrotationYaw);
////				setRotationPitch(bodyrotationPitch);
////				setRotationRoll(bodyrotationRoll);
//		childEntities[0].riddenByEntity.rotationYaw = bodyrotationYaw;
//		childEntities[0].riddenByEntity.prevRotationYaw = prevbodyrotationYaw;
//		childEntities[0].riddenByEntity.rotationPitch = bodyrotationPitch;
//		childEntities[0].riddenByEntity.prevRotationPitch = prevbodyrotationPitch;
//
//	}

//	public void initseat(){
//		for (int i = 0; i< childEntities.length; i++) {
//			SeatInfo[i] = new SeatInfo();
//			if(!worldObj.isRemote) {
//				childEntities[i] = new EntityChild(worldObj,1,1,true);
//				childEntities[i].setLocationAndAngles(this.posX,this.posY,this.posZ,0,0);
//				childEntities[i].master = this;
//				childEntities[i].idinmasterEntityt = i;
//				worldObj.spawnEntityInWorld(childEntities[i]);
//			}
//			isinit = true;
//			switch (i){
//				case 0:
//					SeatInfo[i].pos[0] = 0;
//					SeatInfo[i].pos[1] = 1.1;
//					SeatInfo[i].pos[2] = 3;
//					break;
////				case 1:
////					SeatInfo[i].pos[0] = -0.73;
////					SeatInfo[i].pos[1] = 1.1;
////					SeatInfo[i].pos[2] = 1.00;
////					break;
////				case 2:
////					SeatInfo[i].pos[0] = 1.0;
////					SeatInfo[i].pos[1] = 1.1;
////					SeatInfo[i].pos[2] = 1.75;
////					break;
////				case 3:
////					SeatInfo[i].pos[0] = 1.0;
////					SeatInfo[i].pos[1] = 1.1;
////					SeatInfo[i].pos[2] = 1.00;
////					break;
////				case 4:
////					SeatInfo[i].pos[0] = 0.15;
////					SeatInfo[i].pos[1] = 0.7;
////					SeatInfo[i].pos[2] = 5.8;
////					break;
////				case 5:
////					SeatInfo[i].pos[0] = 0.24;
////					SeatInfo[i].pos[1] = 1.4;
////					SeatInfo[i].pos[2] = 4;
////					break;
//			}
//		}
//	}

//	void FCS(Vector3d mainwingvector,Vector3d tailwingvector,Vector3d bodyvector){
//		illuminated = null;
//		double disttoilluminated = -1;
//		double dirtoilluminated = -1;
//		if(childEntities[0].riddenByEntity != null && childEntities[0].riddenByEntity instanceof EntityPlayerMP&& worldObj.getWorldInfo().getVanillaDimension() == 0){
//			ArrayList<Entity> trackingEntity = new ArrayList<>();
//			ArrayList<Entity> remove = new ArrayList<>();
//			try {
//				for (Object obj : worldObj.loadedEntityList) {
//					Entity A_flyingEntiy = (Entity) obj;
//					if (!A_flyingEntiy.isDead) {
//						if(A_flyingEntiy.worldObj == this.worldObj && A_flyingEntiy != this && (A_flyingEntiy instanceof IVehicle) && childEntities[0]!= null && A_flyingEntiy != childEntities[0].riddenByEntity) {
//							double distsq = getDistanceSqToEntity(A_flyingEntiy);
//								if (distsq < 16777216) {
//								trackingEntity.add(A_flyingEntiy);
//
//								Vector3d totgtvec = new Vector3d(posX - A_flyingEntiy.posX, posY - A_flyingEntiy.posY, posZ - A_flyingEntiy.posZ);
//								if(totgtvec.length()>1) {
//									totgtvec.scale(1);
//									double deg = wrapAngleTo180_double(toDegrees(totgtvec.angle(bodyvector)));
//									if (abs(deg) < 45) {
//										if (illuminated == null || distsq < getDistanceSqToEntity(illuminated)) {
//											illuminated = A_flyingEntiy;
//											disttoilluminated = distsq;
//											dirtoilluminated = deg;
//										}
//									}
//								}
//							}
//						}
//					} else {
//						remove.add(A_flyingEntiy);
//					}
//				}
//			}catch (Exception e){
//				e.printStackTrace();
//			}
//			flyingEntity.removeAll(remove);
//			GVCMPacketHandler.INSTANCE.sendTo(new HMVPacket_HudEntitytracking(childEntities[0].riddenByEntity,trackingEntity), (EntityPlayerMP) childEntities[0].riddenByEntity);
//		}else {
//			throttle -=0.01;
//		}
//
//		if (trigger1) {
//			for(int i = 0;i<2;i++){
//				HMGEntityBullet var3 = new HMGEntityBullet(this.worldObj, childEntities[0].riddenByEntity, 40, 8, 3);
//				var3.setLocationAndAngles(
//						this.posX + mainwingvector.x * gunpos[i][0] +     tailwingvector.x * (gunpos[i][1] - 2.5) - bodyvector.x * gunpos[i][2]
//						, this.posY + mainwingvector.y * gunpos[i][0] + 2 + tailwingvector.y * (gunpos[i][1] - 2.5) - bodyvector.y * gunpos[i][2]
//						, this.posZ + mainwingvector.z * gunpos[i][0] +     tailwingvector.z * (gunpos[i][1] - 2.5) - bodyvector.z * gunpos[i][2]
//						,bodyrotationYaw,bodyrotationPitch);
////						var3.setHeadingFromThrower(bodyrotationPitch, this.bodyrotationYaw, 0, 8, 10F);
//				var3.motionX = motionX + bodyvector.x * -6 + this.rand.nextGaussian() * (double)(this.rand.nextBoolean() ? -1 : 1) * 0.01 * 3;
//				var3.motionY = motionY + bodyvector.y * -6 + this.rand.nextGaussian() * (double)(this.rand.nextBoolean() ? -1 : 1) * 0.01 * 3;
//				var3.motionZ = motionZ + bodyvector.z * -6 + this.rand.nextGaussian() * (double)(this.rand.nextBoolean() ? -1 : 1) * 0.01 * 3;
//				var3.bulletTypeName = "byfrou01_GreenTracer";
//				this.worldObj.spawnEntityInWorld(var3);
//			}
//			if(this.getEntityData().getFloat("GunshotLevel")<4)
//				soundedentity.add(this);
//			this.getEntityData().setFloat("GunshotLevel",4);
//			this.playSound("gvcguns:gvcguns.fire", 4.0F, 0.5F);
//			trigger1 = false;
//		}
//		if(missile == null && illuminated != null) {
//			if (trigger2) {
//				if (rocket > 0) {
//					rocket--;
//					if(rocket<=gunpos.length) {
//						HMGEntityBulletRocket var3 = new HMGEntityBulletRocket(this.worldObj, childEntities[0].riddenByEntity, 450, 8, 3, 0.5F, GVCMobPlus.cfg_blockdestory);
//						var3.gra = 1f;
//						var3.setLocationAndAngles(
//								this.posX + mainwingvector.x * gunpos[rocket][0] + tailwingvector.x * (gunpos[rocket][1] - 2.5) - bodyvector.x * gunpos[rocket][2],
//								this.posY + mainwingvector.y * gunpos[rocket][0] + 2 + tailwingvector.y * (gunpos[rocket][1] - 2.5) - bodyvector.y * gunpos[rocket][2],
//								this.posZ + mainwingvector.z * gunpos[rocket][0] + tailwingvector.z * (gunpos[rocket][1] - 2.5) - bodyvector.z * gunpos[rocket][2]
//								, bodyrotationYaw, bodyrotationPitch);
//						var3.acceleration = 0.5f;
//						var3.motionX = motionX + bodyvector.x * -1;
//						var3.motionY = motionY + bodyvector.y * -1;
//						var3.motionZ = motionZ + bodyvector.z * -1;
//						var3.homingEntity = illuminated;
//						var3.induction_precision = 15;
//						this.worldObj.spawnEntityInWorld(var3);
//						missile = var3;
//					}
//					if (this.getEntityData().getFloat("GunshotLevel") < 4)
//						soundedentity.add(this);
//					this.getEntityData().setFloat("GunshotLevel", 4);
//					this.playSound("gvcmob:gvcmob.missile1", 4.0F, 1.0F);
//				}
//				trigger2 = false;
//			}
//			if(disttoilluminated != -1)
//				this.playSound("gvcguns:gvcguns.lock", (float) 5,  2 * (float)(45 - abs(dirtoilluminated))/45);
//		}
//		if(missile != null){
//			if(missile.isDead)missile = null;
//			else {
//				missile.homingEntity = illuminated;
//			}
//		}
//		this.dataWatcher.updateObject(24,illuminated != null ? illuminated.getEntityId():-1);
//		this.dataWatcher.updateObject(25,missile != null ? missile.getEntityId():-1);
//		this.dataWatcher.updateObject(26,rocket);
//		this.dataWatcher.updateObject(27,(int)health);
//	}

//	void hudUpdate(){
//		if(trackedEntityPos != null)
//		for(float[] pos :trackedEntityPos){
//			if(pos.length == 3){
//				if(FMLClientHandler.instance().getClientPlayerEntity().getDistanceSq(pos[0],
//						pos[1],
//						pos[2])>4096) {
//					Vector3d toentityVec = new Vector3d(
//							pos[0] - FMLClientHandler.instance().getClientPlayerEntity().posX,
//							pos[1] - FMLClientHandler.instance().getClientPlayerEntity().posY + FMLClientHandler.instance().getClientPlayerEntity().getEyeHeight(),
//							pos[2] - FMLClientHandler.instance().getClientPlayerEntity().posZ
//					);
//					toentityVec.normalize();
//					toentityVec.scale(64);
//					pos[0] = (float) (toentityVec.x + FMLClientHandler.instance().getClientPlayerEntity().posX);
//					pos[1] = (float) (toentityVec.y + FMLClientHandler.instance().getClientPlayerEntity().posY + FMLClientHandler.instance().getClientPlayerEntity().getEyeHeight());
//					pos[2] = (float) (toentityVec.z + FMLClientHandler.instance().getClientPlayerEntity().posZ);
//				}
//				HandmadeGunsCore.proxy.spawnParticles(new PacketSpawnParticle(pos[0],pos[1],pos[2], 2));
//			}
//		}
//	}
//	void seatUpdate(Vector3d mainwingvector,Vector3d tailwingvector,Vector3d bodyvector){
//		for(int i = 0; i< childEntities.length; i++){
//			EntityChild achild = childEntities[i];
//			if(achild != null && !achild.isDead) {
//				achild.setLocationAndAngles(
//						this.posX + mainwingvector.x * this.SeatInfo[i].pos[0] + tailwingvector.x * (this.SeatInfo[i].pos[1] - 2.5) - bodyvector.x * this.SeatInfo[i].pos[2]
//						, this.posY + mainwingvector.y * this.SeatInfo[i].pos[0] + 2 + tailwingvector.y * (this.SeatInfo[i].pos[1] - 2.5) - bodyvector.y * this.SeatInfo[i].pos[2]
//						, this.posZ + mainwingvector.z * this.SeatInfo[i].pos[0] + tailwingvector.z * (this.SeatInfo[i].pos[1] - 2.5) - bodyvector.z * this.SeatInfo[i].pos[2]
//						, this.bodyrotationYaw, this.bodyrotationPitch);
//				achild.prevPosX = this.prevPosX + mainwingvector.x * this.SeatInfo[i].pos[0] + tailwingvector.x * (this.SeatInfo[i].pos[1] - 2.5) - bodyvector.x * this.SeatInfo[i].pos[2];
//				achild.prevPosY = this.prevPosY + mainwingvector.y * this.SeatInfo[i].pos[0] + 2 + tailwingvector.y * (this.SeatInfo[i].pos[1] - 2.5) - bodyvector.y * this.SeatInfo[i].pos[2];
//				achild.prevPosZ = this.prevPosZ + mainwingvector.z * this.SeatInfo[i].pos[0] + tailwingvector.z * (this.SeatInfo[i].pos[1] - 2.5) - bodyvector.z * this.SeatInfo[i].pos[2];
//				achild.master = this;
//				if(achild.riddenByEntity != null) {
//					achild.riddenByEntity.posX = this.prevPosX + (this.posX - this.prevPosX) + mainwingvector.x * this.SeatInfo[i].pos[0] + tailwingvector.x * (this.SeatInfo[i].pos[1] - 2.5) - bodyvector.x * this.SeatInfo[i].pos[2];
//					achild.riddenByEntity.posY = this.prevPosY + (this.posY - this.prevPosY) + mainwingvector.y * this.SeatInfo[i].pos[0] + 2 + tailwingvector.y * (this.SeatInfo[i].pos[1] - 2.5) - bodyvector.y * this.SeatInfo[i].pos[2] + achild.riddenByEntity.yOffset;
//					achild.riddenByEntity.posZ = this.prevPosZ + (this.posZ - this.prevPosZ) + mainwingvector.z * this.SeatInfo[i].pos[0] + tailwingvector.z * (this.SeatInfo[i].pos[1] - 2.5) - bodyvector.z * this.SeatInfo[i].pos[2];
//				}
//				achild.motionX = motionX;
//				achild.motionY = motionY;
//				achild.motionZ = motionZ;
//			}else {
//				if(worldObj.isRemote){
//					GVCMPacketHandler.INSTANCE.sendToServer(new HMVPacketSeatData(this.getEntityId()));
//				}else {
//					achild = new EntityChild(worldObj);
//					achild.setLocationAndAngles(
//							this.posX + mainwingvector.x * SeatInfo[i].pos[0] + tailwingvector.x * (SeatInfo[i].pos[1] - 2.5) - bodyvector.x * SeatInfo[i].pos[2]
//							, this.posY + mainwingvector.y * SeatInfo[i].pos[0] + 2 + tailwingvector.y * (SeatInfo[i].pos[1] - 2.5) - bodyvector.y * SeatInfo[i].pos[2]
//							, this.posZ + mainwingvector.z * SeatInfo[i].pos[0] + tailwingvector.z * (SeatInfo[i].pos[1] - 2.5) - bodyvector.z * SeatInfo[i].pos[2]
//							, bodyrotationYaw, bodyrotationPitch);
//					achild.master = this;
//					worldObj.spawnEntityInWorld(achild);
//				}
//			}
//		}
//	}

//	void motionUpdate(Vector3d mainwingvector,Vector3d tailwingvector,Vector3d bodyvector){
////		if(!worldObj.isRemote) {
////		}else {
////			this.motionY -= 0.49;
////			motionX *= 0.95;
////			motionY *= 0.95;
////			motionZ *= 0.95;
////		}
//
//		Vector3d motionvec = new Vector3d(motionX, motionY, motionZ);
//		if(onGround && throttle>0.01 && throttle<0.2 && motionvec.length()<1){
//			motionX = bodyvector.x*0.2;
//			motionY = bodyvector.y*0.2;
//			motionZ = bodyvector.z*0.2;
//		}else {
//			if (!(Double.isNaN(bodyvector.x) || Double.isNaN(bodyvector.y) || Double.isNaN(bodyvector.z))) {
//				motionX -= bodyvector.x * throttle * 0.03;
//				motionY -= bodyvector.y * throttle * 0.03;
//				motionZ -= bodyvector.z * throttle * 0.03;
//				if(throttle>9.5){
//					motionX -= bodyvector.x * throttle * 0.01;
//					motionY -= bodyvector.y * throttle * 0.01;
//					motionZ -= bodyvector.z * throttle * 0.01;
//				}
//			}
//		}
//		motionvec = new Vector3d(motionX, motionY, motionZ);
//		{
//			motionvec = new Vector3d(motionX, motionY, motionZ);
//			bodyvector.normalize();
//			motionvec.add(bodyvector);
//			double cos = angle_cos(bodyvector, motionvec);
//			if (!this.onGround && motionvec.lengthSquared() > 0.01) {
//				Vector3d axisstall = new Vector3d();
//				motionvec.normalize();
//				axisstall.cross(bodyvector, motionvec);
//				axisstall.normalize();
//				axisstall.z = -axisstall.z;
//				Quat4d quat4d = new Quat4d();
//				quat4d.inverse(bodyRot);
//				quat4d.normalize();
//				axisstall = Utils.transformVecByQuat(axisstall,quat4d);
//				if(! Double.isNaN(axisstall.x) && ! Double.isNaN(axisstall.y) && ! Double.isNaN(axisstall.z)) {
//					AxisAngle4d axisxangledstall = new AxisAngle4d(axisstall, abs(cos) * acos(-cos) / 500);
//					rotationmotion = Utils.quatRotateAxis(rotationmotion, axisxangledstall);
//				}
//			}
//		}
//
//		motionvec = new Vector3d(motionX, motionY, motionZ);
//		if(motionvec.length()>0.01) {
//			double cos;
//			cos = angle_cos(bodyvector, motionvec);
//			if (motionvec.length() > 0.01 && abs(cos) > 0.01) {
//				Vector3d bodyvectorForscaling = new Vector3d(bodyvector);
//				bodyvectorForscaling.normalize();
//				bodyvectorForscaling.scale(-motionvec.length());
//				cos = angle_cos(motionvec, bodyvectorForscaling);
//				motionvec = vector_interior_division(bodyvectorForscaling,motionvec,cos);
//				motionvec.normalize();
//				motionvec.scale(bodyvectorForscaling.length());
//			}
//			cos = angle_cos(bodyvector, motionvec);
//			Vector3d tailwingvectorForFloating = new Vector3d(tailwingvector);
//			tailwingvectorForFloating.scale(motionvec.length() * -cos * (0.04 + flaplevel*0.0008));
////			System.out.println("debug" + cos);
//			motionvec.y -=0.49;
//			motionvec.x += tailwingvectorForFloating.x;
//			motionvec.y += tailwingvectorForFloating.y;
//			motionvec.z += tailwingvectorForFloating.z;
//			cos = angle_cos(bodyvector, motionvec);
//			if(motionvec.length()>0.01) {
//				Vector3d airDrug = new Vector3d(motionvec);
//				double sin = sqrt(1-cos * cos);
//				airDrug.scale(motionvec.length() * motionvec.length() * (0.00011 + gearprogress*0.00001 + flaplevel*0.000001) + (sin * sin)/20);
//				motionvec.sub(airDrug);
//			}else {
//				motionvec.scale(0);
//			}
//		}else {
//			motionvec.scale(0);
//			motionvec.y -=0.49;
//		}
//		if(! Double.isNaN(motionvec.x) && ! Double.isNaN(motionvec.y) && ! Double.isNaN(motionvec.z)) {
//			motionX = abs(motionvec.x) > 0.01 ? motionvec.x : 0;
//			motionY = abs(motionvec.y) > 0.01 ? motionvec.y : 0;
//			motionZ = abs(motionvec.z) > 0.01 ? motionvec.z : 0;
//		}
//		double backmotionX = motionX;
//		double backmotionY = motionY;
//		double backmotionZ = motionZ;
//		motionvec.normalize();
//		if(motionvec.y < -bodyvector.y || this.onGround){
//			Flapextension();
//		}else {
//			Flapstorage();
//		}
//		moveEntity(motionX,motionY,motionZ);
//
//		if(throttle >10){
//			throttle = 10;
//		}
//		if(throttle <0){
//			throttle = 0;
//		}
//		this.fallDistance=0;
//		if(!worldObj.isRemote && health < 0){
//			worldObj.createExplosion(this,posX,posY+2,posZ,4,true);
//			worldObj.createExplosion(this,posX+2,posY+2,posZ,3,true);
//			worldObj.createExplosion(this,posX-2,posY+2,posZ,3,true);
//			worldObj.createExplosion(this,posX,posY+2,posZ+2,3,true);
//			worldObj.createExplosion(this,posX,posY+2,posZ-2,3,true);
//			setDead();
//		}
//		if(this.isCollidedHorizontally){
//			if(backmotionX*backmotionX + backmotionY*backmotionY + backmotionZ*backmotionZ > 1){
//				attackEntityFrom(DamageSource.fall, (float) (backmotionX*backmotionX + backmotionY*backmotionY + backmotionZ*backmotionZ)*20);
//			}
//		}
//		if(this.onGround){
//			Vector3d axisx = new Vector3d(-cos(toRadians(bodyrotationYaw)),0,sin(toRadians(bodyrotationYaw)));
//			if(abs(bodyrotationPitch)>15){
//				attackEntityFrom(DamageSource.fall, (float) (30));
//			}
//			AxisAngle4d axisxangled = new AxisAngle4d(axisx, toRadians(-bodyrotationPitch/10));
//			bodyRot = Utils.quatRotateAxis(bodyRot,axisxangled);
//
//			axisx = Utils.transformVecByQuat(new Vector3d(unitZ), bodyRot);
//			if(bodyrotationRoll<45 && bodyrotationRoll>-45){
//				axisxangled = new AxisAngle4d(axisx, toRadians(-bodyrotationRoll/10));
//			}
//			if(bodyrotationRoll<-45 && bodyrotationRoll>-135){
//				attackEntityFrom(DamageSource.fall, (float) (20));
//				axisxangled = new AxisAngle4d(axisx, toRadians((-90-bodyrotationRoll)/10));
//			}
//			if(bodyrotationRoll<135 && bodyrotationRoll>45){
//				attackEntityFrom(DamageSource.fall, (float) (20));
//				axisxangled = new AxisAngle4d(axisx, toRadians((90-bodyrotationRoll)/10));
//			}
//			if(bodyrotationRoll>135){
//				attackEntityFrom(DamageSource.fall, (float) (30));
//				axisxangled = new AxisAngle4d(axisx, toRadians((180-bodyrotationRoll)/10));
//			}
//			if(bodyrotationRoll<-135){
//				attackEntityFrom(DamageSource.fall, (float) (30));
//				axisxangled = new AxisAngle4d(axisx, toRadians((-180-bodyrotationRoll)/10));
//			}
//			bodyRot = Utils.quatRotateAxis(bodyRot,axisxangled);
//
//			gearprogress = 100;
//		}else {
//			if(throttle > 2.5){
//				gearprogress--;
//			}else {
//				gearprogress++;
//			}
//
//			if(gearprogress<0){
//				gearprogress=0;
//			}
//			if(gearprogress>100){
//				gearprogress=100;
//			}
//
//		}
//		if(flaplevel<0){
//			flaplevel=0;
//		}
//		if(flaplevel>75){
//			flaplevel=75;
//		}
//		nboundingbox.rot.set(this.bodyRot);
//		nboundingbox.update(posX,posY,posZ);
//		((ModifiedBoundingBox)this.boundingBox).rot.inverse(this.bodyRot);
//		((ModifiedBoundingBox)this.boundingBox).update(this.posX,this.posY,this.posZ);
//
//		if(childEntities[0]!=null) {
//			childEntities[0].motionX = this.motionX;
//			childEntities[0].motionY = this.motionY;
//			childEntities[0].motionZ = this.motionZ;
//			if(childEntities[0].riddenByEntity != null){
//				childEntities[0].riddenByEntity.motionX = this.motionX;
//				childEntities[0].riddenByEntity.motionY = this.motionY;
//				childEntities[0].riddenByEntity.motionZ = this.motionZ;
//			}
//		}
//
//
//	}

//	public void Flapextension(){
//		flaplevel++;
//	}
//	public void Flapstorage(){
//		flaplevel--;
//	}
	
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

	public void applyEntityCollision(Entity p_70108_1_)
	{
//		for(EntityChild aseat: childEntities){
//			if(aseat != null && aseat.riddenByEntity == p_70108_1_)return;
//		}
//		super.applyEntityCollision(p_70108_1_);
	}
	@Override
	public boolean shouldRenderInPass(int pass)
	{
		return pass == 1 || pass==0;
	}
	
	@Override
	public IbaseLogic getBaseLogic() {
		return baseLogic;
	}
	
	public void setPosition(double x, double y, double z)
	{
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
			addAllTurret(turrets, baseLogic.subTurret);
			baseLogic.turrets = turrets.toArray(new TurretObj[turrets.size()]);
		}
		return baseLogic.turrets;
	}
	
	@Override
	public int getMobMode() {
		return 0;
	}
	
	@Override
	public double[] getTargetpos() {
		return new double[0];
	}
	
	@Override
	public boolean standalone() {
		return false;
	}
}
