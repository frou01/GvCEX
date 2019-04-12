package hmggvcmob.entity;

import cpw.mods.fml.client.FMLClientHandler;
import hmggvcmob.network.GVCMPacketHandler;
import hmggvcmob.network.GVCPacketSeatData;
import hmggvcmob.network.GVCPakcetVehicleState;
import hmggvcmob.util.Calculater;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import org.lwjgl.input.Mouse;

import javax.vecmath.AxisAngle4d;
import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;

import java.util.Random;

import static handmadeguns.HandmadeGunsCore.cfg_defgravitycof;
import static hmggvcmob.GVCMobPlus.proxy;
import static hmggvcmob.event.GVCMXEntityEvent.soundedentity;
import static hmggvcmob.util.Calculater.*;
import static java.lang.Math.*;
import static java.lang.Math.toRadians;
import static net.minecraft.util.MathHelper.wrapAngleTo180_double;
import static net.minecraft.util.MathHelper.wrapAngleTo180_float;

public class PlaneBaseLogic {
	public int rocket = 2;
	public float health = 150;
	public float maxhealth = 150;
	public float mousex;
	public float mousey;
	public float yawladder;
	public float yawspeed = 0.06f;
	public float yawspeed_taxing = 1f;
	public float rollladder;
	public float rollspeed = 0.06f;
	public float pitchladder;
	public float pitchspeed = 0.06f;
	
	public float pitchsighwidthmax = 5;
	public float yawsightwidthmax = 10;
	public float pitchsighwidthmin = -5;
	public float yawsightwidthmin = -10;
	
	public float maxDive = 75;
	public float startDive = 40;
	public float maxClimb = -60;
	public float minALT=20;
	public float cruiseALT=40;
	public boolean throttledown_onDive = false;
	public boolean Dive_bombing = false;
	public boolean Torpedo_bomber = false;
	public boolean sholdUseMain_ToG = true;
	public boolean sholdUseMain_ToA = false;
	
	public float slipresist = 1;
	public float gravity = 0.49f;
	
	public float maxbank = 45;
	
	public float bodyrotationYaw;
	public float bodyrotationPitch;
	public float onground_pitch;
	public float bodyrotationRoll;
	public float prevbodyrotationYaw;
	public float prevbodyrotationPitch;
	public float prevbodyrotationRoll;
	public float stability = 500;
	public double rotmotion_reduceSpeed = 0.2d;
	public float angletime;
	public int fireCycle1;
	public int cooltime;
	public int magazine;
	public float throttle;
	public float throttle_gearDown;
	public float throttle_Max = 10;
	public float throttle_min = 0f;
	public float throttle_AF;
	public float speedfactor = 0.03f;
	public float speedfactor_af = 0.01f;
	public float liftfactor = 0.04f;
	public float flapliftfactor = 0.0008f;
	public float dragfactor = 0.00011f;
	public float flapdragfactor = 0.000001f;
	public float geardragfactor = 0.000005f;
	public int mode = 0;//0:attack 1:leave 2:follow player 3:go to home
	public int soundtick = 0;
	public String  soundname = "gvcmob:gvcmob.plane";
	public boolean trigger1 = false;
	public boolean trigger2 = false;
	//	public double[][] gunpos = new double[6][3];
	public Quat4d bodyRot = new Quat4d(0,0,0,1);
	public Quat4d rotationmotion = new Quat4d(0,0,0,1);
	public Quat4d prevbodyRot = new Quat4d(0,0,0,1);
	public Vector3d prevmotionVec = new Vector3d(0,0,0);
	Vector3d unitX = new Vector3d(1,0,0);
	Vector3d unitY = new Vector3d(0,1,0);
	Vector3d unitZ = new Vector3d(0,0,1);
	public EntityCameraDummy camera;
	public double[] camerapos = new double[]{0,2.5-0.21,-3.1};
	public double[] rotcenter = new double[]{0,2.5,0};
	public GVCEntityChild[] childEntities = new GVCEntityChild[1];
	public ChildInfo[] childInfo = new ChildInfo[1];
	boolean isinit;
	ModifiedBoundingBox nboundingbox;
	
	public int gearprogress;
	public int flaplevel;
	
	
	public TurretObj mainTurret;
	public TurretObj subTurret;
	
	World worldObj;
	
	Entity planebody;
	Iplane plane;
	
	public float perapos;
	public float prevperapos;
	
	public boolean rising_after_Attack;
	
	public boolean T_useMain_F_useSub = true;
	public boolean useMain_withSub = false;
	public int changeWeaponCycleSetting = 3000;
	public int changeWeaponCycle = 100;
	public boolean T_StartDive_F_FlyToStartDivePos = true;
	
	public int outSightCnt = 0;
	public int outSightCntMax = 100;
	
	public boolean autoflap = false;
	
	Random rand = new Random();
	
	public PlaneBaseLogic(World world,Entity entity) {
//		gunpos[0][0] = 0.1;
//		gunpos[0][1] = 1.27;
//		gunpos[0][2] = 0.19;
//		gunpos[1][0] = -0.1;
//		gunpos[1][1] = 1.27;
//		gunpos[1][2] = 0.19;
//		gunpos[2][0] = 2.9;
//		gunpos[2][1] = 1.03;
//		gunpos[2][2] = -0.41;
//		gunpos[3][0] = -2.68;
//		gunpos[3][1] = 1.03;
//		gunpos[3][2] = -0.41;
		childInfo[0] = new ChildInfo();
		childInfo[0].pos[0] = 0;
		childInfo[0].pos[1] = 1.3;
		childInfo[0].pos[2] = 0;
		worldObj = world;
		planebody = entity;
		plane = (Iplane) entity;
		nboundingbox = new ModifiedBoundingBox(-20,-20,-20,20,20,20,0,0,-6.27,2.5,5,19);
		nboundingbox.rot.set(this.bodyRot);
		proxy.replaceBoundingbox(planebody,nboundingbox);
		((ModifiedBoundingBox)planebody.boundingBox).updateOBB(planebody.posX,planebody.posY,planebody.posZ);
		camera = new EntityCameraDummy(this.worldObj);
	}
	public void onUpdate(){
		prevmotionVec.set(planebody.motionX, planebody.motionY, planebody.motionZ);
		Vector3d tailwingvector = Calculater.transformVecByQuat(new Vector3d(unitY), bodyRot);
		Vector3d bodyvector = Calculater.transformVecByQuat(new Vector3d(unitZ), bodyRot);
		Vector3d mainwingvector = Calculater.transformVecByQuat(new Vector3d(unitX), bodyRot);
		tailwingvector.normalize();
		bodyvector.normalize();
		mainwingvector.normalize();
		Calculater.transformVecforMinecraft(tailwingvector);
		Calculater.transformVecforMinecraft(bodyvector);
		Calculater.transformVecforMinecraft(mainwingvector);
		if(!isinit){
			initseat();
		}
		if(planebody instanceof EntityLivingBase)this.health = ((EntityLivingBase) planebody).getHealth();
		if(worldObj.isRemote){
			prevbodyrotationYaw = bodyrotationYaw;
			prevbodyrotationPitch = bodyrotationPitch;
			prevbodyrotationRoll = bodyrotationRoll;
			prevbodyrotationYaw=wrapAngleTo180_float(prevbodyrotationYaw);
			bodyrotationYaw = wrapAngleTo180_float(bodyrotationYaw);
			if(this.health <= this.maxhealth/2) {
				if (this.health <= this.maxhealth / 4) {
					this.worldObj.spawnParticle("smoke", planebody.posX + 2*mainwingvector.x, planebody.posY + 2*mainwingvector.y, planebody.posZ + 2*mainwingvector.z, 0.0D, 0.0D, 0.0D);
					this.worldObj.spawnParticle("smoke", planebody.posX - 2*mainwingvector.x, planebody.posY - 2*mainwingvector.y, planebody.posZ - 2*mainwingvector.z, 0.0D, 0.0D, 0.0D);
					int rx = this.worldObj.rand.nextInt(5);
					int rz = this.worldObj.rand.nextInt(5);
					this.worldObj.spawnParticle("flame", planebody.posX - 2 + rx, planebody.posY + 2D, planebody.posZ - 2 + rz, 0.0D, 0.0D, 0.0D);
					this.worldObj.spawnParticle("flame", planebody.posX - 2 + rx, planebody.posY + 2D, planebody.posZ - 2 + rz, 0.0D, 0.0D, 0.0D);
				} else {
					this.worldObj.spawnParticle("smoke", planebody.posX + 2, planebody.posY + 2D, planebody.posZ - 1, 0.0D, 0.0D, 0.0D);
				}
			}
			if(childEntities[plane.getpilotseatid()] != null && childEntities[plane.getpilotseatid()].riddenByEntity == proxy.getEntityPlayerInstance()) {
				camera.setDead();
				camera = new EntityCameraDummy(this.worldObj);
				camera.setLocationAndAngles(
						planebody.posX + bodyvector.x + tailwingvector.x + mainwingvector.x,
						planebody.posY + bodyvector.y + tailwingvector.y + mainwingvector.y,
						planebody.posZ + bodyvector.z + tailwingvector.z + mainwingvector.z,
						bodyrotationYaw,bodyrotationPitch);
				prevbodyRot.x = bodyRot.x;
				prevbodyRot.y = bodyRot.y;
				prevbodyRot.z = bodyRot.z;
				prevbodyRot.w = bodyRot.w;
				childEntities[plane.getpilotseatid()].isDead = false;
				control(bodyvector);
				childEntities[0].motionX = planebody.motionX;
				childEntities[0].motionY = planebody.motionY;
				childEntities[0].motionZ = planebody.motionZ;
			}else{
				tailwingvector = Calculater.transformVecByQuat(new Vector3d(unitY), bodyRot);
				bodyvector = Calculater.transformVecByQuat(new Vector3d(unitZ), bodyRot);
				mainwingvector = Calculater.transformVecByQuat(new Vector3d(unitX), bodyRot);
				tailwingvector.normalize();
				bodyvector.normalize();
				mainwingvector.normalize();
				Calculater.transformVecforMinecraft(tailwingvector);
				Calculater.transformVecforMinecraft(bodyvector);
				Calculater.transformVecforMinecraft(mainwingvector);
				
				double[] xyz = Calculater.eulerfrommatrix(Calculater.matrixfromQuat(bodyRot));
				bodyrotationPitch = (float) toDegrees(xyz[0]);
				if(!Double.isNaN(xyz[1])){
					bodyrotationYaw = (float) toDegrees(xyz[1]);
				}
				bodyrotationRoll = (float) toDegrees(xyz[2]);
				throttle *= 0.9;
			}
//			turret(mainwingvector,tailwingvector,bodyvector);
		}else{
			for(int x = (int)planebody.boundingBox.minX+3;x<=planebody.boundingBox.maxX-3;x++){
				for(int y = (int)planebody.boundingBox.minY+3;y<=planebody.boundingBox.maxY-3;y++){
					for(int z = (int)planebody.boundingBox.minZ+3;z<=planebody.boundingBox.maxZ-3;z++){
						Block collidingblock = worldObj.getBlock(x,y,z);
						if(collidingblock.getMaterial() == Material.leaves || collidingblock.getMaterial() == Material.wood || collidingblock.getMaterial() == Material.glass || collidingblock.getMaterial() == Material.cloth){
							worldObj.setBlockToAir(x,y,z);
						}
					}
				}
			}
			
			FCS(mainwingvector,tailwingvector,bodyvector);
			if(planebody instanceof Hasmode && ((Hasmode) planebody).standalone()){
				autocontrol(bodyvector);
			}else if(planebody instanceof ImultiRideableVehicle && childEntities[((ImultiRideableVehicle) planebody).getpilotseatid()] != null && childEntities[((ImultiRideableVehicle) planebody).getpilotseatid()].riddenByEntity == null){
				GVCMPacketHandler.INSTANCE.sendToAll(new GVCPakcetVehicleState(planebody.getEntityId(),bodyRot, throttle,trigger1,trigger2));
				throttle *= 0.9;
			}
//			turret(mainwingvector,tailwingvector,bodyvector);
			
			double[] xyz = Calculater.eulerfrommatrix(Calculater.matrixfromQuat(bodyRot));
			bodyrotationPitch = (float) toDegrees(xyz[0]);
			if(!Double.isNaN(xyz[1])){
				bodyrotationYaw = (float) toDegrees(xyz[1]);
			}
			bodyrotationRoll = (float) toDegrees(xyz[2]);
			
			
			planebody.rotationYaw = bodyrotationYaw;
			planebody.rotationPitch = bodyrotationPitch;
			if(this.throttle >= 0.2){
				if(planebody.getEntityData().getFloat("GunshotLevel")<4) soundedentity.add(planebody);
				planebody.getEntityData().setFloat("GunshotLevel",4);
				planebody.playSound(soundname, 4F, 0.8f + throttle /throttle_Max * 0.4f);
			}
		}
		rotationmotion.normalize();
		bodyRot.mul(rotationmotion);
		rotationmotion.interpolate(new Quat4d(0,0,0,1),rotmotion_reduceSpeed);
		prevperapos = perapos;
		perapos += throttle*10;
		while (this.perapos - this.prevperapos < -180.0F)
		{
			this.prevperapos -= 360.0F;
		}
		while (this.perapos - this.prevperapos >= 180.0F)
		{
			this.prevperapos += 360.0F;
		}
		motionUpdate(mainwingvector,tailwingvector,bodyvector);
		
		seatUpdate(mainwingvector,tailwingvector,bodyvector);
	}
	
	public void setVelocity(double p_70016_1_, double p_70016_3_, double p_70016_5_)
	{
		if(!(childEntities[plane.getpilotseatid()] != null && childEntities[plane.getpilotseatid()].riddenByEntity == FMLClientHandler.instance().getClientPlayerEntity())){
			planebody.motionX = p_70016_1_;
			planebody.motionY = p_70016_3_;
			planebody.motionZ = p_70016_5_;
		}
	}
	
	void control(Vector3d bodyvector){
		if (health>0 && proxy.wclick()) {
			throttle += 0.05;
		}
		if(health<=0){
			throttle -= 0.05;
		}
		if (proxy.aclick()) {
			yawladder--;
		}
		if (proxy.sclick()) {
			throttle -= 0.05;
		}
		if (proxy.dclick()) {
			yawladder++;
		}
		trigger1 = trigger2 = false;
		if (proxy.leftclick()) {
			trigger1 = true;
		}
		if (proxy.rightclick()) {
			trigger2 = true;
		}
		if(!FMLClientHandler.instance().getClient().isGamePaused()) {
			mousex += Mouse.getDX() * 0.1;
			mousey += Mouse.getDY() * 0.1;
		}
		
		
		yawladder*=0.8;
		mousex *= 0.8f;
		mousey *= 0.8f;
		mousex = abs(mousex)>4 ? (mousex>0?mousex - 4f:mousex +4f):mousex * 0.9f;
		mousey = abs(mousey)>4 ? (mousey>0?mousey - 4f:mousey +4f):mousey * 0.9f;
		rollladder = abs(mousex)>4 ? (mousex>0?4f:-4f):mousex * 0.8f;
		pitchladder = abs(mousey)>4 ? (mousey>0?4f:-4f):mousey * 0.8f;
		
		Vector3d motionvec = new Vector3d(planebody.motionX, planebody.motionY, planebody.motionZ);
		if(motionvec.length() > 0.1) {
			double cos = -angle_cos(bodyvector, motionvec) * motionvec.length();
			if (abs(pitchladder) > 0.001) {
				Vector3d axisx = Calculater.transformVecByQuat(new Vector3d(unitX), bodyRot);
				AxisAngle4d axisxangled = new AxisAngle4d(unitX, toRadians(-pitchladder / 4 * cos * pitchspeed));
				rotationmotion = Calculater.quatRotateAxis(rotationmotion, axisxangled);
			}
			if (abs(yawladder) > 0.001) {
				AxisAngle4d axisyangled;
				if(planebody.onGround && motionvec.length()<0.2){
					Vector3d axisy = Calculater.transformVecByQuat(new Vector3d(unitY), bodyRot);
					axisyangled = new AxisAngle4d(unitY, toRadians(yawladder / 4 * cos * yawspeed_taxing));
				}else {
					Vector3d axisy = Calculater.transformVecByQuat(new Vector3d(unitY), bodyRot);
					axisyangled = new AxisAngle4d(unitY, toRadians(yawladder / 4 * cos * yawspeed));
				}
				rotationmotion = Calculater.quatRotateAxis(rotationmotion, axisyangled);
			}
			if (abs(rollladder) > 0.001) {
				Vector3d axisz = Calculater.transformVecByQuat(new Vector3d(unitZ), bodyRot);
				AxisAngle4d axiszangled = new AxisAngle4d(unitZ, toRadians(rollladder / 4 * cos * rollspeed));
				rotationmotion = Calculater.quatRotateAxis(rotationmotion, axiszangled);
			}
		}
		
		double[] xyz = Calculater.eulerfrommatrix(Calculater.matrixfromQuat(bodyRot));
		bodyrotationPitch = (float) toDegrees(xyz[0]);
		if(!Double.isNaN(xyz[1])){
			bodyrotationYaw = (float) toDegrees(xyz[1]);
		}
		bodyrotationRoll = (float) toDegrees(xyz[2]);
		camera.rotationYawHead = bodyrotationYaw;
		camera.prevRotationYawHead = prevbodyrotationYaw;
		camera.prevRotationYaw = prevbodyrotationYaw;
		camera.prevRotationPitch = prevbodyrotationPitch;
		GVCMPacketHandler.INSTANCE.sendToServer(new GVCPakcetVehicleState(planebody.getEntityId(),bodyRot, throttle,trigger1,trigger2));
//				if(th<2.5){
//					th +=0.1;
//				}
//				if (proxy.wclick()) {
//					th += 0.1;
//					GVCMPacketHandler.INSTANCE.sendToServer(new GVCMMessageKeyPressed(16, this.getEntityId()));
//				}
//				if (proxy.aclick()) {
////					GVCMPacketHandler.INSTANCE.sendToServer(new GVCMMessageKeyPressed(17, this.getEntityId()));
//					servera = true;
//				}
//				if (proxy.sclick()) {
//					th -= 0.1;
//					GVCMPacketHandler.INSTANCE.sendToServer(new GVCMMessageKeyPressed(18, this.getEntityId()));
//				}
//				if (proxy.dclick()) {
////					GVCMPacketHandler.INSTANCE.sendToServer(new GVCMMessageKeyPressed(19, this.getEntityId()));
//					serverd = true;
//				}
//				if (proxy.leftclick()) {
//					GVCMPacketHandler.INSTANCE.sendToServer(new GVCMMessageKeyPressed(11, this.getEntityId()));
//				}
//				if (proxy.jumped()) {
//					GVCMPacketHandler.INSTANCE.sendToServer(new GVCMMessageKeyPressed(12, this.getEntityId()));
//				}
//				if (proxy.fclick()) {
//					GVCMPacketHandler.INSTANCE.sendToServer(new GVCMMessageKeyPressed(20, this.getEntityId()));
//				}
//
//				th -=0.05;
//				if(th>5){
//					th = 5;
//				}
//				if(th<0){
//					th = 0;
//				}
////				GVCMPacketHandler.INSTANCE.sendToServer(new GVCMMessageMouseD(Mouse.getDX(),Mouse.getDY(),this.getEntityId()));
//				mousex += Mouse.getDX()*0.01;
//				mousey += Mouse.getDY()*0.01;
//				if (servera) {
//					yawladder +=-1;
//					servera = false;
//				}
//				if (serverd) {
//					yawladder +=1;
//					serverd = false;
//				}
//
//				prevmousex = mousex;
//				if (abs(mousex)>0.00001) {
//					mainwingvector = Calculater.rotationVector_byAxisVector(bodyVector,mainwingvector, abs(mousex)>4 ? (mousex>0?4f:-4f):mousex);
//					tailwingvector = Calculater.rotationVector_byAxisVector(bodyVector,tailwingvector, abs(mousex)>4 ? (mousex>0?4f:-4f):mousex);
//				}
//				mousex*=0.9;
//
//				prevmousey = mousey;
//				if (abs(mousey)>0.00001) {
//					bodyVector     = Calculater.rotationVector_byAxisVector(mainwingvector,bodyVector    ,abs(mousey)>4?(mousey>0?-4f:4f):-mousey);
//					tailwingvector = Calculater.rotationVector_byAxisVector(mainwingvector,tailwingvector,abs(mousey)>4?(mousey>0?-4f:4f):-mousey);
//				}
//				mousey*=0.9;
//				prevyawladder = yawladder;
//				yawladder *=0.8;
//				if(abs(yawladder) < 0.001) yawladder = prevyawladder =0;else {
//					bodyVector = Calculater.rotationVector_byAxisVector(tailwingvector, bodyVector, yawladder);
//					mainwingvector = Calculater.rotationVector_byAxisVector(tailwingvector, mainwingvector, yawladder);
//				}
//				bodyrotationPitch = wrapAngleTo180_float((float) toDegrees(asin(bodyVector.yCoord)));
//
//				Vec3 temp1 = Vec3.createVectorHelper(bodyVector.xCoord,bodyVector.yCoord,bodyVector.zCoord);
//				bodyrotationYaw = wrapAngleTo180_float((float) toDegrees(atan2(temp1.xCoord, temp1.zCoord)));
//
//				if(abs(bodyrotationPitch)<45) {
//					Vec3 temp = Vec3.createVectorHelper(mainwingvector.xCoord,mainwingvector.yCoord,mainwingvector.zCoord);
//					temp.rotateAroundY(-(float) toRadians(bodyrotationYaw));
//					temp.rotateAroundX(-(float) toRadians(bodyrotationPitch));
//					bodyrotationRoll = (float) toDegrees(atan2(temp.yCoord, temp.xCoord));
//				}else {
//					Vec3 temp = Vec3.createVectorHelper(tailwingvector.xCoord,tailwingvector.yCoord,tailwingvector.zCoord);
//					temp.rotateAroundY(-(float) toRadians(bodyrotationYaw));
//					temp.rotateAroundX(-(float) toRadians(bodyrotationPitch));
//					bodyrotationRoll = (float) toDegrees(atan2(temp.yCoord, temp.xCoord))-90;
//				}
//				GVCXMPacketSyncPMCHeliData packet = new GVCXMPacketSyncPMCHeliData(this.getEntityId(),bodyrotationYaw,bodyrotationPitch,bodyrotationRoll);
//				GVCMPacketHandler.INSTANCE.sendToServer(packet);
//				FMLClientHandler.instance().getClient().mouseHelper.deltaX = 0;
//				FMLClientHandler.instance().getClient().mouseHelper.deltaY = 0;
//
//				setRotationYaw(bodyrotationYaw);
//				setRotationPitch(bodyrotationPitch);
//				setRotationRoll(bodyrotationRoll);
		childEntities[0].riddenByEntity.rotationYaw = bodyrotationYaw;
		childEntities[0].riddenByEntity.prevRotationYaw = prevbodyrotationYaw;
		childEntities[0].riddenByEntity.rotationPitch = bodyrotationPitch;
		childEntities[0].riddenByEntity.prevRotationPitch = prevbodyrotationPitch;
		
	}
	void autocontrol(Vector3d bodyvector){
		bodyvector = new Vector3d(bodyvector);//copy
		bodyvector.scale(-1);
		int genY = this.worldObj.getHeightValue((int) planebody.posX, (int) planebody.posZ);//target alt
		
		float alt = (float) (planebody.posY - genY);
		if(health>0){
		
			if(planebody instanceof EntityLiving && ((EntityLiving) planebody).getAttackTarget() != null){
				if(!T_useMain_F_useSub && subTurret == null)T_useMain_F_useSub = true;
				EntityLivingBase target = ((EntityLiving) planebody).getAttackTarget();
//				if((target.onGround || (target.ridingEntity != null && target.ridingEntity.onGround)) && genY<target.posY){
//					alt = (float) (planebody.posY - target.posY);
//				}
				Vector3d courseVec = new Vector3d(target.posX,target.posY,target.posZ);
				courseVec.sub(new Vector3d(planebody.posX, planebody.posY, planebody.posZ));
				courseVec.normalize();
				
				float targetyaw = wrapAngleTo180_float(-(float) toDegrees(atan2(courseVec.x, courseVec.z)));
				double AngulardifferenceYaw = targetyaw - this.bodyrotationYaw;
				AngulardifferenceYaw = wrapAngleTo180_double(AngulardifferenceYaw);
				double planespeed = planebody.motionX * planebody.motionX + planebody.motionY * planebody.motionY + planebody.motionZ * planebody.motionZ;
				TurretObj currentTurret = T_useMain_F_useSub ? mainTurret : subTurret;
				Vector3d currentCannonpos = currentTurret.getCannonpos();
				
				float targetpitch = wrapAngleTo180_float(-(float) CalculateGunElevationAngle(currentCannonpos.x,currentCannonpos.y,currentCannonpos.z, target, (float) currentTurret.gravity, currentTurret.speed + (float) sqrt(planespeed))[0]);
				
				if(!useMain_withSub && (T_useMain_F_useSub ? mainTurret.isreloading() : subTurret.isreloading())){
					T_StartDive_F_FlyToStartDivePos = false;
				}
				if(!useMain_withSub){
					if(T_useMain_F_useSub){
						if(mainTurret.isreloading()) {
							rising_after_Attack = true;
							T_useMain_F_useSub = !T_useMain_F_useSub;
							changeWeaponCycle = 0;
						}
					}else{
						if(subTurret.isreloading()){
							rising_after_Attack = true;
							T_useMain_F_useSub = !T_useMain_F_useSub;
							changeWeaponCycle = 0;
						}
					}
				}
				double toTargetPitch = -toDegrees(asin(courseVec.y));
				if(!rising_after_Attack && T_StartDive_F_FlyToStartDivePos){
					boolean insight = true;
					boolean istarget_onGround = (target.onGround || (target.ridingEntity != null && target.ridingEntity.onGround));
					if(T_useMain_F_useSub && Dive_bombing && istarget_onGround){
						if(toTargetPitch<startDive){
							targetpitch = -5;
							insight = false;
						}else {
							if(targetpitch < startDive){
								targetpitch = (float) toTargetPitch;
								insight = false;
							}
							if (throttle > (throttle_Max / 3) * 2) throttle -= 0.05;
							else throttle += 0.05;
						}
					}else if(T_useMain_F_useSub && Torpedo_bomber){
						throttle += 0.05;
						if(alt <minALT+5){
							targetpitch = -20;
						}else if(alt >minALT){
							targetpitch = 10;
						}
						else {
							targetpitch = 0;
						}
					}else
					if(throttledown_onDive && istarget_onGround){
						if(throttle>(throttle_Max/3)*2)throttle -= 0.05;
						else throttle += 0.05;
					}else {
						throttle += 0.05;
					}
					handle(AngulardifferenceYaw,targetpitch,alt);
					float AngulardifferencePitch = targetpitch - bodyrotationPitch;
					insight &= AngulardifferenceYaw > yawsightwidthmin && AngulardifferenceYaw < yawsightwidthmax && AngulardifferencePitch > pitchsighwidthmin &&AngulardifferencePitch< pitchsighwidthmax;
					if(T_useMain_F_useSub && Torpedo_bomber){
						insight = planebody.getDistanceSqToEntity(target) < 1024;
						if(insight){
							rising_after_Attack = true;
							T_StartDive_F_FlyToStartDivePos = false;
						}
					}
					if(!useMain_withSub && mainTurret != null && subTurret != null){
						if(sholdUseMain_ToG && istarget_onGround)
							T_useMain_F_useSub = !mainTurret.isreloading();
						else if(sholdUseMain_ToA && !istarget_onGround)
							T_useMain_F_useSub = !mainTurret.isreloading();
					}
					if(insight){
						if(useMain_withSub){
							trigger1 = true;
							trigger2 = true;
						}else if(T_useMain_F_useSub){
							if(!mainTurret.isreloading())
								trigger1 = true;
							else {
								rising_after_Attack = true;
								T_useMain_F_useSub = !T_useMain_F_useSub;
								changeWeaponCycle = 0;
							}
						}else{
							if(!subTurret.isreloading())
								trigger2 = true;
							else {
								rising_after_Attack = true;
								T_useMain_F_useSub = !T_useMain_F_useSub;
								changeWeaponCycle = 0;
							}
						}
						outSightCnt--;
					}else {
						if(!istarget_onGround)outSightCnt++;
						if(outSightCnt>outSightCntMax){
							rising_after_Attack = true;
							//離脱
						}
					}
					changeWeaponCycle ++;
					if(changeWeaponCycle > changeWeaponCycleSetting){
						changeWeaponCycle = 0;
						T_useMain_F_useSub = !T_useMain_F_useSub;
					}
					if(target.onGround && toTargetPitch>maxDive){
						T_StartDive_F_FlyToStartDivePos = false;
						rising_after_Attack = true;
					}
					if(!Torpedo_bomber && alt<minALT){
						rising_after_Attack = true;
					}
				} else {
					throttle+= 0.05;
					if(!target.onGround)T_StartDive_F_FlyToStartDivePos = true;
					if(toTargetPitch<(Dive_bombing ? startDive/2:startDive))T_StartDive_F_FlyToStartDivePos = true;
					if(targetpitch<0)rising_after_Attack = false;
					if(alt > cruiseALT)rising_after_Attack = false;
					if(!target.onGround && outSightCnt>0){
						rising_after_Attack = true;
						outSightCnt--;
					}
					rollHandle(0);
					if(alt<cruiseALT)pitchHandle(maxClimb);
					else if(alt>cruiseALT + 5)pitchHandle(5);
				}
//				if (rising_after_Attack && alt < 50) {
//					pitchHandle(maxClimb);
//					throttle += 0.05;
//				} else {
//					if(rising_after_Attack)rising_after_Attack = false;
//					if(alt < 20)T_StartDive_F_FlyToStartDivePos = rising_after_Attack = true;
//					{
//						AngulardifferenceYaw = wrapAngleTo180_double(AngulardifferenceYaw);
//						if (subTurret == null) T_useMain_F_useSub = true;
//						if(targetpitch<maxDive-20)T_StartDive_F_FlyToStartDivePos = true;
//						if(targetpitch>maxDive)T_StartDive_F_FlyToStartDivePos = false;
//						if(alt>60)T_StartDive_F_FlyToStartDivePos = true;
//						if(target.onGround)T_StartDive_F_FlyToStartDivePos = true;
//						System.out.println("" + AngulardifferenceYaw + T_StartDive_F_FlyToStartDivePos);
//						if(T_StartDive_F_FlyToStartDivePos) {
//							boolean insight = true;
//							if(AngulardifferenceYaw < -3){
//								//turn left
//								if (alt > 30) {
//									if (bodyrotationRoll > -maxbank * (target.onGround?0.5:1) + 3) {
//										mousex += 8;
//									} else if (bodyrotationRoll < -maxbank * (target.onGround?0.5:1) - 3) {
//										mousex -= 8;
//									}
//								}
//								if (alt > 10 && abs(bodyrotationRoll) < 90) {
//									yawladder++;
//								}
//							}else if(AngulardifferenceYaw > 3){
//								//turn right
//								if (alt > 30) {
//									if (bodyrotationRoll > maxbank * (target.onGround?0.5:1) + 3) {
//										mousex += 8;
//									} else if (bodyrotationRoll < maxbank * (target.onGround?0.5:1) - 3) {
//										mousex -= 8;
//									}
//								}
//								if (alt > 10 && abs(bodyrotationRoll) < 90) {
//									yawladder--;
//								}
//							}else {
//								if (bodyrotationRoll > 2) {
//									mousex += 4;
//								} else if (bodyrotationRoll < -2) {
//									mousex -= 4;
//								}
//							}
//							if (bodyrotationPitch < targetpitch - 2) {
//								mousey -= 16;
//							} else if (bodyrotationPitch > targetpitch + 2) {
//								mousey += 16;
//							}
//							if(abs(bodyrotationPitch - targetpitch)>5) insight = false;
//							if(abs(AngulardifferenceYaw)>5) insight = false;
//							if(insight){
//								if(throttle > throttle_Max/2)throttle -= 0.05;
//								if(useMain_withSub){
//									trigger1 = true;
//									trigger2 = true;
//								}else if(T_useMain_F_useSub){
//									trigger1 = true;
//								}else if(!T_useMain_F_useSub){
//									trigger2 = true;
//								}
//								changeWeaponCycle ++;
//								if(changeWeaponCycle > changeWeaponCycleSetting){
//									changeWeaponCycle = 0;
//									T_useMain_F_useSub = !T_useMain_F_useSub;
//								}
//							}else {
//								throttle += 0.05;
//							}
//						} else {
//							if (bodyrotationRoll > 5) {
//								mousex += 4;
//							} else if (bodyrotationRoll < -5) {
//								mousex -= 4;
//							}
//							if (target.onGround && alt > 30){
//								if(abs(bodyrotationRoll) < 90){
//									if (bodyrotationPitch < 10) {
//										mousey -= 4;
//									} else if (bodyrotationPitch > 10) {
//										mousey += 4;
//									}
//								}
//							}
//						}
//					}
//				}
			}else
			{
				switch (((Hasmode) planebody).getMobMode()) {
					case 1://wait
					case 2://follow
						if (alt > cruiseALT) {
							if(throttle>(throttle_Max/4) *3)throttle -= 0.05;
							else throttle += 0.05;
						} else {
							throttle += 0.05;
						}
						
						double[] targetpos = ((Hasmode) planebody).getwaitingpos();
						
						Vector3d courseVec = new Vector3d(targetpos);
						courseVec.sub(new Vector3d(planebody.posX, courseVec.y, planebody.posZ));
//						double angletocourse = toDegrees(bodyvector.angle(courseVec));
//						System.out.println("" + angletocourse);
						float targetyaw = wrapAngleTo180_float(-(float) toDegrees(atan2(courseVec.x, courseVec.z)));
						double AngulardifferenceYaw = targetyaw - this.bodyrotationYaw;
						AngulardifferenceYaw = wrapAngleTo180_double(AngulardifferenceYaw);
						if(alt > 10) handle(AngulardifferenceYaw,alt<cruiseALT+10?(alt<cruiseALT?maxClimb:0):-maxClimb,alt);
						else pitchHandle(maxClimb);
//						pitchHandle(alt<60?alt<30?maxClimb:0:maxDive);
					
						break;
					
				}
			}
		}else {
			throttle-=0.05;
		}
		yawladder *= 0.8;
		mousex *= 0.8f;
		mousey *= 0.8f;
		mousex = abs(mousex) > 4 ? (mousex > 0 ? mousex - 4f : mousex + 4f) : mousex * 0.9f;
		mousey = abs(mousey) > 4 ? (mousey > 0 ? mousey - 4f : mousey + 4f) : mousey * 0.9f;
		rollladder = abs(mousex) > 4 ? (mousex > 0 ? 4f : -4f) : mousex * 0.8f;
		pitchladder = abs(mousey) > 4 ? (mousey > 0 ? 4f : -4f) : mousey * 0.8f;
		
		Vector3d motionvec = new Vector3d(planebody.motionX, planebody.motionY, planebody.motionZ);
		if (motionvec.length() > 0.1) {
			double cos = -angle_cos(bodyvector, motionvec) * motionvec.length();
			if (abs(pitchladder) > 0.001) {
				AxisAngle4d axisxangled = new AxisAngle4d(unitX, toRadians(-pitchladder / 4 * cos * pitchspeed));
				rotationmotion = Calculater.quatRotateAxis(rotationmotion, axisxangled);
			}
			if (abs(yawladder) > 0.001) {
				AxisAngle4d axisyangled;
				if (planebody.onGround && motionvec.length() < 0.2) {
					axisyangled = new AxisAngle4d(unitY, toRadians(yawladder / 4 * cos * yawspeed_taxing));
				} else {
					axisyangled = new AxisAngle4d(unitY, toRadians(yawladder / 4 * cos * yawspeed));
				}
				rotationmotion = Calculater.quatRotateAxis(rotationmotion, axisyangled);
			}
			if (abs(rollladder) > 0.001) {
				AxisAngle4d axiszangled = new AxisAngle4d(unitZ, toRadians(rollladder / 4 * cos * rollspeed));
				rotationmotion = Calculater.quatRotateAxis(rotationmotion, axiszangled);
			}
		}
		
		double[] xyz = Calculater.eulerfrommatrix(Calculater.matrixfromQuat(bodyRot));
		bodyrotationPitch = (float) toDegrees(xyz[0]);
		if (!Double.isNaN(xyz[1])) {
			bodyrotationYaw = (float) toDegrees(xyz[1]);
		}
		bodyrotationRoll = (float) toDegrees(xyz[2]);
		
		GVCMPacketHandler.INSTANCE.sendToAll(new GVCPakcetVehicleState(planebody.getEntityId(),bodyRot, throttle,trigger1,trigger2));
	}
	public boolean handle(double AngulardifferenceYaw, double targetPitch, double alt){
		if(AngulardifferenceYaw < 0){
			//turn Right
			if(abs(AngulardifferenceYaw) >10) {
				if (alt > 30) rollHandle(-maxbank * min(abs(AngulardifferenceYaw), 10) / 10);
				else if (alt > 10) rollHandle(-maxbank * (alt) / 30 * min(abs(AngulardifferenceYaw), 10) / 10);
				else {
					rollHandle(0);
				}
			}else {
				rollHandle(0);
			}
			
			if(abs(bodyrotationRoll)<20){
				pitchHandle(targetPitch);
			}
			if(abs(bodyrotationRoll)>20) {
				if (bodyrotationRoll < 0 && bodyrotationPitch > targetPitch-10) {//機首が低い
					//右に傾いている
					//操縦桿引き込み
					mousey += abs(AngulardifferenceYaw)*4;
//					System.out.println("debug1");
				} else if (bodyrotationRoll > 0 && bodyrotationPitch < targetPitch+10) {//機種が高い
					//左に傾いている
					//操縦桿押し込み
					mousey -= abs(AngulardifferenceYaw)*4;
//					System.out.println("debug2");
				}
			}
			if(bodyrotationPitch < targetPitch+10) {//機種が高い
				if (abs(bodyrotationRoll) < 80) {
					yawladder+=min(abs(AngulardifferenceYaw),10)/10;
				} else if (abs(bodyrotationRoll) > 100) {
					//ひっくり返っている
					yawladder-=min(abs(AngulardifferenceYaw),10)/10;
				}
			}
			
		}else if(AngulardifferenceYaw > 0){
			//turn Left
			if(abs(AngulardifferenceYaw) >10) {
				if (alt > 30) rollHandle(maxbank * min(abs(AngulardifferenceYaw), 10) / 10);
				else if (alt > 10) rollHandle(maxbank * (alt) / 30 * min(abs(AngulardifferenceYaw), 10) / 10);
				else {
					rollHandle(0);
				}
			}else {
				rollHandle(0);
			}
			if(abs(bodyrotationRoll)<20){
				pitchHandle(targetPitch);
			}
			if(abs(bodyrotationRoll)>20 && bodyrotationPitch > targetPitch-10) {
				if (bodyrotationRoll < 0 && bodyrotationPitch < targetPitch+10) {//機首が高い
					//右に傾いている
					//操縦桿押し込み
					mousey -= abs(AngulardifferenceYaw);
//					System.out.println("debug3");
				} else if (bodyrotationRoll > 0 && bodyrotationPitch > targetPitch-10) {//機首が低い
					//左に傾いている
					//操縦桿引き込み
					mousey += abs(AngulardifferenceYaw);
//					System.out.println("debug4");
				}
			}
			if(bodyrotationPitch < targetPitch+10) {//機首が高い
				if (abs(bodyrotationRoll) < 80) {
					yawladder-=min(abs(AngulardifferenceYaw),10)/10;
				} else if (abs(bodyrotationRoll) > 100) {
					//ひっくり返っている
					yawladder+=min(abs(AngulardifferenceYaw),10)/10;
				}
			}
		}else {
			rollHandle(0);
			return pitchHandle(targetPitch);
		}
		return false;
	}
	public void rollHandle(double targetRoll){
		if (bodyrotationRoll > targetRoll) {
			mousex += abs(bodyrotationRoll-targetRoll)/10;
		} else if (bodyrotationRoll < targetRoll) {
			mousex -= abs(bodyrotationRoll-targetRoll)/10;
		}
	}
	public boolean pitchHandle(double targetPitch){
		double AngulardifferencePitch = bodyrotationPitch - targetPitch;
		if (AngulardifferencePitch < 0){
			//機首下げ
			if(abs(bodyrotationRoll)<80) {
				mousey -= abs(AngulardifferencePitch)*4;
			}else if(abs(bodyrotationRoll)>100){
				mousey += abs(AngulardifferencePitch)*4;
			}
			if(abs(bodyrotationRoll) > 60 && abs(bodyrotationRoll) < 120) {
				if (bodyrotationRoll < 0) {
					//左に傾いている
					yawladder+=min(abs(AngulardifferencePitch),10)/10;
				} else if (bodyrotationRoll > 0) {
					//右に傾いている
					yawladder-=min(abs(AngulardifferencePitch),10)/10;
				}
			}
		}else if (AngulardifferencePitch > 0){
			//機首上げ
			if(abs(bodyrotationRoll)<80) {
				mousey += abs(AngulardifferencePitch)*4;
			}else if(abs(bodyrotationRoll)>100){
				mousey -= abs(AngulardifferencePitch)*4;
			}
			if(abs(bodyrotationRoll) > 60 && abs(bodyrotationRoll) < 120) {
				if (bodyrotationRoll < 0) {
					//左に傾いている
					yawladder-=min(abs(AngulardifferencePitch),10)/10;
				} else if (bodyrotationRoll > 0) {
					//右に傾いている
					yawladder+=min(abs(AngulardifferencePitch),10)/10;
				}
			}
		}else {
			return true;
		}
		return false;
	}
	
	public void initseat(){
		for (int i = 0; i< childEntities.length; i++) {
			if(!worldObj.isRemote) {
				childEntities[i] = new GVCEntityChild(worldObj,1,1,true);
				childEntities[i].setLocationAndAngles(planebody.posX,planebody.posY,planebody.posZ,0,0);
				childEntities[i].master = planebody;
				childEntities[i].idinmasterEntityt = i;
				worldObj.spawnEntityInWorld(childEntities[i]);
			}
			isinit = true;
		}
	}
	
	void FCS(Vector3d mainwingvector,Vector3d tailwingvector,Vector3d bodyvector){
		if (trigger1) {
			if(mainTurret != null){
				if(childEntities[plane.getpilotseatid()] != null && childEntities[plane.getpilotseatid()].riddenByEntity != null){
					mainTurret.currentEntity = childEntities[plane.getpilotseatid()].riddenByEntity;
				}else
					mainTurret.currentEntity = this.planebody;
				mainTurret.currentEntity.motionX = this.planebody.motionX;
				mainTurret.currentEntity.motionY = this.planebody.motionY;
				mainTurret.currentEntity.motionZ = this.planebody.motionZ;
				mainTurret.fireall();
			}
//			for(int i = 0;i<2;i++){
//				HMGEntityBullet var3 = new HMGEntityBullet(this.worldObj, childEntities[0].riddenByEntity, 40, 8, 3);
//				var3.setLocationAndAngles(
//						planebody.posX + mainwingvector.x * gunpos[i][0] +     tailwingvector.x * (gunpos[i][1] - 2.5) - bodyvector.x * gunpos[i][2]
//						, planebody.posY + mainwingvector.y * gunpos[i][0] + 2 + tailwingvector.y * (gunpos[i][1] - 2.5) - bodyvector.y * gunpos[i][2]
//						, planebody.posZ + mainwingvector.z * gunpos[i][0] +     tailwingvector.z * (gunpos[i][1] - 2.5) - bodyvector.z * gunpos[i][2]
//						,bodyrotationYaw,bodyrotationPitch);
////						var3.setHeadingFromThrower(bodyrotationPitch, this.bodyrotationYaw, 0, 8, 10F);
//				var3.motionX = planebody.motionX + bodyvector.x * -6 + this.rand.nextGaussian() * (double)(this.rand.nextBoolean() ? -1 : 1) * 0.01 * 3;
//				var3.motionY = planebody.motionY + bodyvector.y * -6 + this.rand.nextGaussian() * (double)(this.rand.nextBoolean() ? -1 : 1) * 0.01 * 3;
//				var3.motionZ = planebody.motionZ + bodyvector.z * -6 + this.rand.nextGaussian() * (double)(this.rand.nextBoolean() ? -1 : 1) * 0.01 * 3;
//				var3.bulletTypeName = "byfrou01_GreenTracer";
//				this.worldObj.spawnEntityInWorld(var3);
//			}
//			if(planebody.getEntityData().getFloat("GunshotLevel")<4)
//				soundedentity.add(planebody);
//			planebody.getEntityData().setFloat("GunshotLevel",4);
//			planebody.playSound("gvcguns:gvcguns.fire", 4.0F, 0.5F);
			trigger1 = false;
		}
		if(trigger2){
			if(subTurret != null){
				if(childEntities[plane.getpilotseatid()] != null && childEntities[plane.getpilotseatid()].riddenByEntity != null){
					subTurret.currentEntity = childEntities[plane.getpilotseatid()].riddenByEntity;
				}else
					subTurret.currentEntity = this.planebody;
				subTurret.currentEntity.motionX = this.planebody.motionX;
				subTurret.currentEntity.motionY = this.planebody.motionY;
				subTurret.currentEntity.motionZ = this.planebody.motionZ;
				subTurret.fireall();
			}
			trigger2 = false;
		}
		if(mainTurret != null){
			mainTurret.update(bodyRot,new Vector3d(planebody.posX,planebody.posY,-planebody.posZ));
		}
		if(subTurret != null){
			subTurret.update(bodyRot,new Vector3d(planebody.posX,planebody.posY,-planebody.posZ));
		}
	}
	void seatUpdate(Vector3d mainwingvector,Vector3d tailwingvector,Vector3d bodyvector){
		for(int i = 0; i< childEntities.length; i++){
			GVCEntityChild achild = childEntities[i];
			if(achild != null && !achild.isDead) {
				achild.setLocationAndAngles(
						planebody.posX + mainwingvector.x * this.childInfo[i].pos[0] + tailwingvector.x * (this.childInfo[i].pos[1] - 2.5) - bodyvector.x * this.childInfo[i].pos[2]
						, planebody.posY + mainwingvector.y * this.childInfo[i].pos[0] + 2 + tailwingvector.y * (this.childInfo[i].pos[1] - 2.5) - bodyvector.y * this.childInfo[i].pos[2]
						, planebody.posZ + mainwingvector.z * this.childInfo[i].pos[0] + tailwingvector.z * (this.childInfo[i].pos[1] - 2.5) - bodyvector.z * this.childInfo[i].pos[2]
						, this.bodyrotationYaw, this.bodyrotationPitch);
				achild.prevPosX = planebody.prevPosX + mainwingvector.x * this.childInfo[i].pos[0] + tailwingvector.x * (this.childInfo[i].pos[1] - 2.5) - bodyvector.x * this.childInfo[i].pos[2];
				achild.prevPosY = planebody.prevPosY + mainwingvector.y * this.childInfo[i].pos[0] + 2 + tailwingvector.y * (this.childInfo[i].pos[1] - 2.5) - bodyvector.y * this.childInfo[i].pos[2];
				achild.prevPosZ = planebody.prevPosZ + mainwingvector.z * this.childInfo[i].pos[0] + tailwingvector.z * (this.childInfo[i].pos[1] - 2.5) - bodyvector.z * this.childInfo[i].pos[2];
				achild.master = planebody;
				if(achild.riddenByEntity != null) {
					achild.riddenByEntity.posX = planebody.prevPosX + (planebody.posX - planebody.prevPosX) + mainwingvector.x * this.childInfo[i].pos[0] + tailwingvector.x * (this.childInfo[i].pos[1] - 2.5) - bodyvector.x * this.childInfo[i].pos[2];
					achild.riddenByEntity.posY = planebody.prevPosY + (planebody.posY - planebody.prevPosY) + mainwingvector.y * this.childInfo[i].pos[0] + 2 + tailwingvector.y * (this.childInfo[i].pos[1] - 2.5) - bodyvector.y * this.childInfo[i].pos[2] + achild.riddenByEntity.yOffset;
					achild.riddenByEntity.posZ = planebody.prevPosZ + (planebody.posZ - planebody.prevPosZ) + mainwingvector.z * this.childInfo[i].pos[0] + tailwingvector.z * (this.childInfo[i].pos[1] - 2.5) - bodyvector.z * this.childInfo[i].pos[2];
					achild.riddenByEntity.motionX = this.planebody.motionX;
					achild.riddenByEntity.motionY = this.planebody.motionY;
					achild.riddenByEntity.motionZ = this.planebody.motionZ;
				}
				achild.motionX = planebody.motionX;
				achild.motionY = planebody.motionY;
				achild.motionZ = planebody.motionZ;
			}else {
				if(worldObj.isRemote){
					GVCMPacketHandler.INSTANCE.sendToServer(new GVCPacketSeatData(planebody.getEntityId()));
				}else {
					achild = new GVCEntityChild(worldObj);
					achild.setLocationAndAngles(
							planebody.posX + mainwingvector.x * childInfo[i].pos[0] + tailwingvector.x * (childInfo[i].pos[1] - 2.5) - bodyvector.x * childInfo[i].pos[2]
							, planebody.posY + mainwingvector.y * childInfo[i].pos[0] + 2 + tailwingvector.y * (childInfo[i].pos[1] - 2.5) - bodyvector.y * childInfo[i].pos[2]
							, planebody.posZ + mainwingvector.z * childInfo[i].pos[0] + tailwingvector.z * (childInfo[i].pos[1] - 2.5) - bodyvector.z * childInfo[i].pos[2]
							, bodyrotationYaw, bodyrotationPitch);
					achild.master = planebody;
					worldObj.spawnEntityInWorld(achild);
				}
			}
		}
	}
	
	void motionUpdate(Vector3d mainwingvector,Vector3d tailwingvector,Vector3d bodyvector){
//		if(!worldObj.isRemote) {
//		}else {
//			this.motionY -= 0.49;
//			motionX *= 0.95;
//			motionY *= 0.95;
//			motionZ *= 0.95;
//		}
		{
			Vector3d motionvec = new Vector3d(planebody.motionX, planebody.motionY, planebody.motionZ);
			if (planebody.onGround && throttle > 0.01 && throttle < 0.2 && motionvec.length() < 1) {
				planebody.motionX -= bodyvector.x * 0.2;
				planebody.motionY -= bodyvector.y * 0.2;
				planebody.motionZ -= bodyvector.z * 0.2;
			} else {
				if (!(Double.isNaN(bodyvector.x) || Double.isNaN(bodyvector.y) || Double.isNaN(bodyvector.z))) {
					planebody.motionX -= bodyvector.x * throttle * speedfactor;
					planebody.motionY -= bodyvector.y * throttle * speedfactor;
					planebody.motionZ -= bodyvector.z * throttle * speedfactor;
					if (throttle > 9.5) {
						planebody.motionX -= bodyvector.x * throttle * speedfactor_af;
						planebody.motionY -= bodyvector.y * throttle * speedfactor_af;
						planebody.motionZ -= bodyvector.z * throttle * speedfactor_af;
					}
				}
			}
			{
				motionvec = new Vector3d(planebody.motionX, planebody.motionY, planebody.motionZ);
				bodyvector.normalize();
				motionvec.add(bodyvector);
				double cos = angle_cos(bodyvector, motionvec);
				if (!planebody.onGround && motionvec.lengthSquared() > 0.000001) {
					Vector3d axisstall = new Vector3d();
					motionvec.normalize();
					axisstall.cross(bodyvector, motionvec);
					axisstall.normalize();
					axisstall.z = -axisstall.z;
					Quat4d quat4d = new Quat4d();
					quat4d.inverse(bodyRot);
					quat4d.normalize();
					axisstall = Calculater.transformVecByQuat(axisstall, quat4d);
					if (!Double.isNaN(axisstall.x) && !Double.isNaN(axisstall.y) && !Double.isNaN(axisstall.z)) {
						AxisAngle4d axisxangledstall = new AxisAngle4d(axisstall, abs(cos) * acos(-cos) / stability);
						rotationmotion = Calculater.quatRotateAxis(rotationmotion, axisxangledstall);
					}
				}
			}
			
			motionvec = new Vector3d(planebody.motionX, planebody.motionY, planebody.motionZ);
			if (motionvec.length() > 0.01) {
				double cos;
				cos = angle_cos(bodyvector, motionvec);
				if (motionvec.length() > 0.000001 && abs(cos) > 0.000001) {
					Vector3d bodyvectorForscaling = new Vector3d(bodyvector);
					bodyvectorForscaling.normalize();
					bodyvectorForscaling.scale(-motionvec.length());
					cos = angle_cos(motionvec, bodyvectorForscaling);
					motionvec = vector_interior_division(bodyvectorForscaling, motionvec, cos);
					motionvec.normalize();
					motionvec.scale(bodyvectorForscaling.length());
				}
				cos = angle_cos(bodyvector, motionvec);
				Vector3d tailwingvectorForFloating = new Vector3d(tailwingvector);
				tailwingvectorForFloating.scale(motionvec.length() * -cos * (liftfactor + flaplevel * flapliftfactor));
//			System.out.println("debug" + cos);
				motionvec.y -= gravity;
				motionvec.x += tailwingvectorForFloating.x / slipresist;
				motionvec.y += tailwingvectorForFloating.y;
				motionvec.z += tailwingvectorForFloating.z / slipresist;
				cos = angle_cos(bodyvector, motionvec);
				if (motionvec.length() > 0.000001) {
					Vector3d airDrug = new Vector3d(motionvec);
					double sin = sqrt(1 - cos * cos);
					airDrug.scale(motionvec.length() * motionvec.length() * (dragfactor + gearprogress * geardragfactor + flaplevel * flapdragfactor) + (sin * sin) / 20);
					motionvec.sub(airDrug);
				} else {
					motionvec.scale(0);
				}
			} else {
				motionvec.scale(0);
				motionvec.y -= 0.49;
			}
			if (!Double.isNaN(motionvec.x) && !Double.isNaN(motionvec.y) && !Double.isNaN(motionvec.z)) {
				planebody.motionX = abs(motionvec.x) > 0.0000001 ? motionvec.x : 0;
				planebody.motionY = abs(motionvec.y) > 0.0000001 ? motionvec.y : 0;
				planebody.motionZ = abs(motionvec.z) > 0.0000001 ? motionvec.z : 0;
			}
			double backmotionX = planebody.motionX;
			double backmotionY = planebody.motionY;
			double backmotionZ = planebody.motionZ;
			motionvec.normalize();
			if ((motionvec.y < -bodyvector.y && autoflap) || planebody.onGround) {
				Flapextension();
			} else {
				Flapstorage();
			}
			planebody.moveEntity(planebody.motionX, planebody.motionY, planebody.motionZ);
			if (planebody.motionY > 0) {
				planebody.isAirBorne = true;
			}
			
			
			if (throttle > throttle_Max) {
				throttle = throttle_Max;
			}
			if (throttle < throttle_min) {
				throttle = throttle_min;
			}
			planebody.fallDistance = 0;
			if (planebody.isCollidedHorizontally) {
				if (backmotionX * backmotionX + backmotionY * backmotionY + backmotionZ * backmotionZ > 1) {
					planebody.attackEntityFrom(DamageSource.fall, (float) (backmotionX * backmotionX + backmotionY * backmotionY + backmotionZ * backmotionZ) * 30);
				}
			}
			if (planebody.onGround) {
				Vector3d axisx = new Vector3d(-cos(toRadians(bodyrotationYaw)), 0, sin(toRadians(bodyrotationYaw)));
				if (abs(bodyrotationPitch - onground_pitch) > 15) {
					planebody.attackEntityFrom(DamageSource.fall, (float) (30));
				}
				AxisAngle4d axisxangled = new AxisAngle4d(axisx, toRadians((onground_pitch - bodyrotationPitch) / 10));
				bodyRot = Calculater.quatRotateAxis(bodyRot, axisxangled);
				
				axisx = Calculater.transformVecByQuat(new Vector3d(unitZ), bodyRot);
				if (bodyrotationRoll < 45 && bodyrotationRoll > -45) {
					axisxangled = new AxisAngle4d(axisx, toRadians(-bodyrotationRoll / 10));
				}
				if (bodyrotationRoll < -45 && bodyrotationRoll > -135) {
					planebody.attackEntityFrom(DamageSource.fall, (float) (20));
					axisxangled = new AxisAngle4d(axisx, toRadians((-90 - bodyrotationRoll) / 10));
				}
				if (bodyrotationRoll < 135 && bodyrotationRoll > 45) {
					planebody.attackEntityFrom(DamageSource.fall, (float) (20));
					axisxangled = new AxisAngle4d(axisx, toRadians((90 - bodyrotationRoll) / 10));
				}
				if (bodyrotationRoll > 135) {
					planebody.attackEntityFrom(DamageSource.fall, (float) (30));
					axisxangled = new AxisAngle4d(axisx, toRadians((180 - bodyrotationRoll) / 10));
				}
				if (bodyrotationRoll < -135) {
					planebody.attackEntityFrom(DamageSource.fall, (float) (30));
					axisxangled = new AxisAngle4d(axisx, toRadians((-180 - bodyrotationRoll) / 10));
				}
				bodyRot = Calculater.quatRotateAxis(bodyRot, axisxangled);
				
				gearprogress = 100;
			} else {
				if (throttle > throttle_gearDown) {
					gearprogress--;
				} else {
					gearprogress++;
					Flapextension();
				}
				
				if (gearprogress < 0) {
					gearprogress = 0;
				}
				if (gearprogress > 100) {
					gearprogress = 100;
				}
				
			}
			if (flaplevel < 0) {
				flaplevel = 0;
			}
			if (flaplevel > 75) {
				flaplevel = 75;
			}
			nboundingbox.rot.set(this.bodyRot);
			nboundingbox.updateOBB(planebody.posX, planebody.posY, planebody.posZ);
			((ModifiedBoundingBox) planebody.boundingBox).rot.inverse(this.bodyRot);
			((ModifiedBoundingBox) planebody.boundingBox).updateOBB(planebody.posX, planebody.posY, planebody.posZ);
			
			if (childEntities[0] != null) {
				childEntities[0].motionX = planebody.motionX;
				childEntities[0].motionY = planebody.motionY;
				childEntities[0].motionZ = planebody.motionZ;
				if (childEntities[0].riddenByEntity != null) {
					childEntities[0].riddenByEntity.motionX = planebody.motionX;
					childEntities[0].riddenByEntity.motionY = planebody.motionY;
					childEntities[0].riddenByEntity.motionZ = planebody.motionZ;
				}
			}
		}
		
	}
	
	public void Flapextension(){
		flaplevel++;
	}
	public void Flapstorage(){
		flaplevel--;
	}
	public boolean isConverting() {
		return false;
	}
}
