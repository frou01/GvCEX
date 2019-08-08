package handmadevehicle.entity.parts.logics;

import cpw.mods.fml.client.FMLClientHandler;
import handmadevehicle.Utils;
import handmadevehicle.HMVPacketHandler;
import handmadevehicle.entity.parts.*;
import handmadevehicle.entity.prefab.Prefab_Vehicle_Base;
import handmadevehicle.entity.prefab.Prefab_Vehicle_Plane;
import handmadevehicle.packets.*;
import handmadevehicle.entity.EntityCameraDummy;
import handmadevehicle.entity.parts.turrets.TurretObj;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import org.lwjgl.input.Mouse;

import javax.vecmath.AxisAngle4d;
import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;

import java.util.ArrayList;
import java.util.Random;

import static handmadevehicle.Utils.*;
import static handmadevehicle.HMVehicle.proxy_HMVehicle;
import static java.lang.Math.*;
import static java.lang.Math.toRadians;
import static net.minecraft.util.MathHelper.wrapAngleTo180_double;
import static net.minecraft.util.MathHelper.wrapAngleTo180_float;

public class PlaneBaseLogic extends LogicsBase implements IbaseLogic,IneedMouseTrack,IControlable,MultiRiderLogics {
	public Prefab_Vehicle_Plane planeInfo;
	public float health = 150;
	public float maxhealth = 150;
	public float mousex;
	public float mousey;
	public float yawrudder;
	public float rollrudder;
	public float pitchrudder;
	
	public boolean climbYawDir=new Random().nextBoolean();
	public boolean mouseStickMode = true;
	
	public int mode = 0;//0:attack 1:leave 2:follow player 3:go to home
	public boolean trigger1 = false;
	public boolean trigger2 = false;
	//	public double[][] gunpos = new double[6][3];
	public Vector3d prevmotionVec = new Vector3d(0,0,0);
	Vector3d unitX = new Vector3d(1,0,0);
	Vector3d unitY = new Vector3d(0,1,0);
	Vector3d unitZ = new Vector3d(0,0,1);
	public EntityCameraDummy camera;
	public Quat4d camerarot = new Quat4d(0,0,0,1);
	public Quat4d camerarot_current = new Quat4d(0,0,0,1);
//	public double[] camerapos_zoom = new double[]{0,2.5-0.21,-3.6};
	public Quat4d camerarot_zoom = new Quat4d(0,0,0,1);
	
	public float cameraYaw;
	public float cameraPitch;
	
	public int gearprogress;
	public int flaplevel;
	
	
	
	
	
	IPlane plane;
	Hasmode plane_hasmode;
	
	public float perapos;
	public float prevperapos;
	
	public boolean rising_after_Attack;
	
	public boolean T_useMain_F_useSub = true;
	public boolean T_StartDive_F_FlyToStartDivePos = true;
	public int changeWeaponCycle;
	
	public int outSightCnt = 0;
	
	
	
	
	public boolean serverspace = false;
	public boolean serverw = false;
	public boolean servers = false;
	public boolean servera = false;
	public boolean serverd = false;
	public boolean serverf = false;
	public boolean serverx = false;
	
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
		seatInfos[0] = new SeatInfo();
		seatInfos[0].pos[0] = 0;
		seatInfos[0].pos[1] = 2.3;
		seatInfos[0].pos[2] = 0;
		worldObj = world;
		mc_Entity = entity;
		iVehicle = (IVehicle) entity;
		plane = (IPlane) entity;
		if(entity instanceof Hasmode)plane_hasmode = (Hasmode) entity;
		
		camera = new EntityCameraDummy(this.worldObj);
	}
	public void onUpdate(){
		((ModifiedBoundingBox) mc_Entity.boundingBox).rot.inverse(this.bodyRot);
		((ModifiedBoundingBox) mc_Entity.boundingBox).update(mc_Entity.posX, mc_Entity.posY, mc_Entity.posZ);
		int cnt = 0;
		for (Entity entity : riddenByEntities) {
			if (entity != null) {
				if((mc_Entity.worldObj.isRemote && entity instanceof EntityPlayer && entity.isSneaking())) {
//					riddenByEntities[cnt] = null;
//					entity.ridingEntity = null;
					HMVPacketHandler.INSTANCE.sendToServer(new HMVPacketDisMountEntity(mc_Entity.getEntityId(),entity.getEntityId()));
				}else
				if(!mc_Entity.worldObj.isRemote && entity.isDead){
//						System.out.println("debug");
					if(mc_Entity instanceof EntityLiving && entity instanceof EntityLiving && ((EntityLiving) mc_Entity).getAttackTarget() != null)((EntityLiving) entity).setAttackTarget(((EntityLiving) mc_Entity).getAttackTarget());
					riddenByEntities[cnt] = null;
					entity.ridingEntity = null;
					HMVPacketHandler.INSTANCE.sendToAll(new HMVPacketPickNewEntity(mc_Entity.getEntityId(),riddenByEntities));
				}else {
					entity.ridingEntity = mc_Entity;
				}
			}
			cnt ++;
		}
		mc_Entity.riddenByEntity = riddenByEntities[0];
		
		
		prevmotionVec.set(mc_Entity.motionX, mc_Entity.motionY, mc_Entity.motionZ);
		Vector3d tailwingvector = Utils.transformVecByQuat(new Vector3d(unitY), bodyRot);
		Vector3d bodyvector = Utils.transformVecByQuat(new Vector3d(unitZ), bodyRot);
		Vector3d mainwingvector = Utils.transformVecByQuat(new Vector3d(unitX), bodyRot);
		tailwingvector.normalize();
		bodyvector.normalize();
		mainwingvector.normalize();
		Utils.transformVecforMinecraft(tailwingvector);
		Utils.transformVecforMinecraft(bodyvector);
		Utils.transformVecforMinecraft(mainwingvector);
		if(needStartSound){
			needStartSound = false;
			if(mc_Entity.worldObj.isRemote)proxy_HMVehicle.playsoundasVehicle(1024, mc_Entity);
		}
		needStartSound = getsound() != null;
		if(mc_Entity instanceof EntityLivingBase)this.health = ((EntityLivingBase) mc_Entity).getHealth();
		if(worldObj.isRemote){
			prevbodyrotationYaw = bodyrotationYaw;
			prevbodyrotationPitch = bodyrotationPitch;
			prevbodyrotationRoll = bodyrotationRoll;
			
			while (this.bodyrotationYaw - this.prevbodyrotationYaw < -180.0F)
			{
				this.prevbodyrotationYaw -= 360.0F;
			}
			
			while (this.bodyrotationYaw - this.prevbodyrotationYaw >= 180.0F)
			{
				this.prevbodyrotationYaw += 360.0F;
			}
			
			
			
			while (this.bodyrotationRoll - this.prevbodyrotationRoll < -180.0F)
			{
				this.prevbodyrotationRoll -= 360.0F;
			}
			
			while (this.bodyrotationRoll - this.prevbodyrotationRoll >= 180.0F)
			{
				this.prevbodyrotationRoll += 360.0F;
			}
			prevbodyrotationYaw=wrapAngleTo180_float(prevbodyrotationYaw);
			bodyrotationYaw = wrapAngleTo180_float(bodyrotationYaw);
			if(this.health <= planeInfo.maxhealth/2) {
				if (this.health <= planeInfo.maxhealth / 4) {
					this.worldObj.spawnParticle("smoke", mc_Entity.posX + 2*mainwingvector.x, mc_Entity.posY + 2*mainwingvector.y, mc_Entity.posZ + 2*mainwingvector.z, 0.0D, 0.0D, 0.0D);
					this.worldObj.spawnParticle("smoke", mc_Entity.posX - 2*mainwingvector.x, mc_Entity.posY - 2*mainwingvector.y, mc_Entity.posZ - 2*mainwingvector.z, 0.0D, 0.0D, 0.0D);
					int rx = this.worldObj.rand.nextInt(5);
					int rz = this.worldObj.rand.nextInt(5);
					this.worldObj.spawnParticle("flame", mc_Entity.posX - 2 + rx, mc_Entity.posY + 2D, mc_Entity.posZ - 2 + rz, 0.0D, 0.0D, 0.0D);
					this.worldObj.spawnParticle("flame", mc_Entity.posX - 2 + rx, mc_Entity.posY + 2D, mc_Entity.posZ - 2 + rz, 0.0D, 0.0D, 0.0D);
				} else {
					this.worldObj.spawnParticle("smoke", mc_Entity.posX + 2, mc_Entity.posY + 2D, mc_Entity.posZ - 1, 0.0D, 0.0D, 0.0D);
				}
			}
			if(riddenByEntities[plane.getpilotseatid()] == proxy_HMVehicle.getEntityPlayerInstance()) {
				camera.setDead();
				camera = new EntityCameraDummy(this.worldObj);
				camera.setLocationAndAngles(
						mc_Entity.posX + bodyvector.x + tailwingvector.x + mainwingvector.x,
						mc_Entity.posY + bodyvector.y + tailwingvector.y + mainwingvector.y,
						mc_Entity.posZ + bodyvector.z + tailwingvector.z + mainwingvector.z,
						bodyrotationYaw,bodyrotationPitch);
				riddenByEntities[plane.getpilotseatid()].isDead = false;
				riddenByEntities[0].motionX = mc_Entity.motionX;
				riddenByEntities[0].motionY = mc_Entity.motionY;
				riddenByEntities[0].motionZ = mc_Entity.motionZ;
			}else{
				tailwingvector = Utils.transformVecByQuat(new Vector3d(unitY), bodyRot);
				bodyvector = Utils.transformVecByQuat(new Vector3d(unitZ), bodyRot);
				mainwingvector = Utils.transformVecByQuat(new Vector3d(unitX), bodyRot);
				tailwingvector.normalize();
				bodyvector.normalize();
				mainwingvector.normalize();
				Utils.transformVecforMinecraft(tailwingvector);
				Utils.transformVecforMinecraft(bodyvector);
				Utils.transformVecforMinecraft(mainwingvector);
				
				double[] xyz = Utils.eulerfrommatrix(Utils.matrixfromQuat(bodyRot));
				bodyrotationPitch = (float) toDegrees(xyz[0]);
				if(!Double.isNaN(xyz[1])){
					bodyrotationYaw = (float) toDegrees(xyz[1]);
				}
				bodyrotationRoll = (float) toDegrees(xyz[2]);
			}
//			turret(mainwingvector,tailwingvector,bodyvector);
		}else{
			for(int x = (int) mc_Entity.boundingBox.minX+3; x<= mc_Entity.boundingBox.maxX-3; x++){
				for(int y = (int) mc_Entity.boundingBox.minY+3; y<= mc_Entity.boundingBox.maxY-3; y++){
					for(int z = (int) mc_Entity.boundingBox.minZ+3; z<= mc_Entity.boundingBox.maxZ-3; z++){
						Block collidingblock = worldObj.getBlock(x,y,z);
						if(collidingblock.getMaterial() == Material.leaves || collidingblock.getMaterial() == Material.wood || collidingblock.getMaterial() == Material.glass || collidingblock.getMaterial() == Material.cloth){
							worldObj.setBlockToAir(x,y,z);
						}
					}
				}
			}
			
			FCS(mainwingvector,tailwingvector,bodyvector);
			if(mc_Entity instanceof Hasmode && ((Hasmode) mc_Entity).standalone()){
				autocontrol(bodyvector);
			}
			HMVPacketHandler.INSTANCE.sendToAll(new HMVPakcetVehicleState(mc_Entity.getEntityId(),bodyRot, throttle,trigger1,trigger2));
//			turret(mainwingvector,tailwingvector,bodyvector);
			
			double[] xyz = Utils.eulerfrommatrix(Utils.matrixfromQuat(bodyRot));
			bodyrotationPitch = (float) toDegrees(xyz[0]);
			if(!Double.isNaN(xyz[1])){
				bodyrotationYaw = (float) toDegrees(xyz[1]);
			}
			bodyrotationRoll = (float) toDegrees(xyz[2]);
			
			
			mc_Entity.rotationYaw = bodyrotationYaw;
			mc_Entity.rotationPitch = bodyrotationPitch;
//			if(this.throttle >= 0.2){
//				if(planebody.getEntityData().getFloat("GunshotLevel")<4) soundedentity.add(planebody);
//				planebody.getEntityData().setFloat("GunshotLevel",4);
//				planebody.playSound(soundname, 4F, 0.8f + throttle /throttle_Max * 0.4f);
//			}
		}
		control(bodyvector);
		if(mainTurret != null){
			mainTurret.motherEntity = mc_Entity;
			if(riddenByEntities[plane.getpilotseatid()] != null && riddenByEntities[plane.getpilotseatid()] != null){
				mainTurret.currentEntity = riddenByEntities[plane.getpilotseatid()];
			}else
				mainTurret.currentEntity = this.mc_Entity;
			if(!(mc_Entity instanceof Hasmode && ((Hasmode) mc_Entity).standalone()) && (riddenByEntities[plane.getpilotseatid()] != null &&
					                                                                             riddenByEntities[plane.getpilotseatid()] == null))mainTurret.freezTrigger();
			mainTurret.update(bodyRot,new Vector3d(mc_Entity.posX, mc_Entity.posY,-mc_Entity.posZ));
		}
		if(subTurret != null){
			subTurret.motherEntity = mc_Entity;
			if(riddenByEntities[plane.getpilotseatid()] != null && riddenByEntities[plane.getpilotseatid()] != null){
				subTurret.currentEntity = riddenByEntities[plane.getpilotseatid()];
			}else
				subTurret.currentEntity = this.mc_Entity;
			if(!(mc_Entity instanceof Hasmode && ((Hasmode) mc_Entity).standalone()) && (riddenByEntities[plane.getpilotseatid()] != null &&
					                                                                             riddenByEntities[plane.getpilotseatid()] == null))subTurret.freezTrigger();
			subTurret.update(bodyRot,new Vector3d(mc_Entity.posX, mc_Entity.posY,-mc_Entity.posZ));
		}
		prevperapos = perapos;
		perapos += throttle*100;
		while (this.perapos - this.prevperapos < -180.0F)
		{
			this.prevperapos -= 360.0F;
		}
		while (this.perapos - this.prevperapos >= 180.0F)
		{
			this.prevperapos += 360.0F;
		}
		motionUpdate(mainwingvector,tailwingvector,bodyvector);
		
		if(mc_Entity instanceof IMultiTurretVehicle){
			HMVPacketHandler.INSTANCE.sendToAll(new HMVPakcetVehicleTurretSync(mc_Entity.getEntityId(), (IMultiTurretVehicle) mc_Entity));
		}
		
		riderPosUpdate();
		prevbodyRot.x = bodyRot.x;
		prevbodyRot.y = bodyRot.y;
		prevbodyRot.z = bodyRot.z;
		prevbodyRot.w = bodyRot.w;
	}
	
	
	public void riderPosUpdate_forRender(Vector3d thispos){
		
		int cnt = 0;
//		System.out.println("thispos  " + thispos);
		for (Entity entity : riddenByEntities) {
			if (entity != null) {
				entity.posX =
						entity.prevPosX =
								entity.lastTickPosX = thispos.x + seatInfos[cnt].currentSeatOffset_fromV.x;
				entity.posY =
						entity.prevPosY =
								entity.lastTickPosY = thispos.y + seatInfos[cnt].currentSeatOffset_fromV.y;
				entity.posZ =
						entity.prevPosZ =
								entity.lastTickPosZ = thispos.z + seatInfos[cnt].currentSeatOffset_fromV.z;
			}
			cnt++;
		}
	}
	
	public void setVelocity(double p_70016_1_, double p_70016_3_, double p_70016_5_)
	{
//		if(!(riddenByEntities[plane.getpilotseatid()] != null && riddenByEntities[plane.getpilotseatid()] == FMLClientHandler.instance().getClientPlayerEntity())){
//			planebody.motionX = p_70016_1_;
//			planebody.motionY = p_70016_3_;
//			planebody.motionZ = p_70016_5_;
//		}
	}
	
	void control(Vector3d bodyvector){
		if(worldObj.isRemote && !plane_hasmode.standalone() && ispilot(proxy_HMVehicle.getEntityPlayerInstance())){
			ArrayList<Integer> keys = new ArrayList<>();
			if (proxy_HMVehicle.leftclick()) {
				keys.add(11);
			}
			if (proxy_HMVehicle.rightclick()) {
				keys.add(12);
			}
			if (proxy_HMVehicle.spaceKeyDown()) {
				keys.add(13);
			}
			if (serverx = proxy_HMVehicle.xclick()) {
				keys.add(14);
			}
			if (serverw = proxy_HMVehicle.wclick()) {
				keys.add(16);
			}
			if (servera = proxy_HMVehicle.aclick()) {
				keys.add(17);
			}
			if (servers = proxy_HMVehicle.sclick()) {
				keys.add(18);
			}
			if (serverd = proxy_HMVehicle.dclick()) {
				keys.add(19);
			}
			if (serverf = proxy_HMVehicle.fclick()) {
				keys.add(20);
			}
			int[] i = new int[keys.size()];
			for(int id = 0; id < i.length ; id++){
				i[id] = keys.get(id);
			}
			HMVPacketHandler.INSTANCE.sendToServer(new HMVMMessageKeyPressed(i, mc_Entity.getEntityId()));
			if(!FMLClientHandler.instance().getClient().isGamePaused()) {
				camerarot_current.set(camerarot);
				if(mouseStickMode){
					mousex += ((float)Mouse.getDX())/4;
					mousey += ((float)Mouse.getDY())/4;
//					cameraYaw = 0;
//					cameraPitch = 0;
				}else {
					cameraYaw += ((float)Mouse.getDX())/4;
					cameraPitch += ((float)Mouse.getDY())/4;
					if(cameraPitch>90)cameraPitch = 90;
					if(cameraPitch<-90)cameraPitch = -90;
					if(proxy_HMVehicle.hasStick()){
						mousex = proxy_HMVehicle.getXaxis() * 16;
						yawrudder = proxy_HMVehicle.getZaxis() * 5;
						mousey = -proxy_HMVehicle.getYaxis() * 16;
					}
				}
				{
					if (riddenByEntities[0] != null) {
						Quat4d currentcamRot = new Quat4d(bodyRot);
						currentcamRot.mul(proxy_HMVehicle.iszooming()?camerarot_zoom: camerarot_current);
						double[] cameraxyz = Utils.eulerfrommatrix(Utils.matrixfromQuat(currentcamRot));
						cameraxyz[0] = toDegrees(cameraxyz[0]);
						cameraxyz[1] = toDegrees(cameraxyz[1]);
						cameraxyz[2] = toDegrees(cameraxyz[2]);
						riddenByEntities[0].rotationYaw = (float) cameraxyz[1];
						riddenByEntities[0].prevRotationYaw = (float) cameraxyz[1];
						riddenByEntities[0].setRotationYawHead((float) cameraxyz[1]);
						riddenByEntities[0].rotationPitch = (float) cameraxyz[0];
						riddenByEntities[0].prevRotationPitch = (float) cameraxyz[0];
					}
				}
				Quat4d Headrot = new Quat4d(0,0,0,1);
				Headrot = quatRotateAxis(Headrot,new AxisAngle4d(unitX,toRadians(cameraPitch)/2));
				Headrot = quatRotateAxis(Headrot,new AxisAngle4d(unitY,toRadians(cameraYaw)/2));
				camerarot_current.mul(Headrot);
				if(proxy_HMVehicle.changeControlclick())mouseStickMode = !mouseStickMode;
				if(proxy_HMVehicle.resetCamrotclick()){
					cameraYaw = 0;
					cameraPitch = 0;
				}
				if(proxy_HMVehicle.pitchUp())mousey =16;
				if(proxy_HMVehicle.pitchDown())mousey =-16;
				if(proxy_HMVehicle.rollRight())mousex =16;
				if(proxy_HMVehicle.rollLeft())mousex =-16;
				HMVPacketHandler.INSTANCE.sendToServer(new HMVPacketMouseD(mousex,
						                                                          mousey, yawrudder, mc_Entity.getEntityId()));
			}
		}
//		if(!worldObj.isRemote)System.out.println("" + yawladder);
		rollrudder = (abs(mousex) > 16 ? (mousex > 0 ? 16f : -16f) : mousex)/8;
		pitchrudder = (abs(mousey) > 16 ? (mousey > 0 ? 16f : -16f) : mousey)/8;
		
		if(abs(mousex) > 16){
			mousex *= 0.7;
		}else {
			mousex = 0;
		}
		if(abs(mousey) > 16){
			mousey *= 0.7;
		}else {
			mousey = 0;
		}
		if(!worldObj.isRemote) {
//			System.out.println("yawladder " + yawladder);
//		System.out.println("" + pitchladder);
			Vector3d motionvec = new Vector3d(mc_Entity.motionX, mc_Entity.motionY, mc_Entity.motionZ);
			if (motionvec.length() > 0.001) {
				double cof = (planeInfo.forced_rudder_effect * throttle - ((1-planeInfo.forced_rudder_effect) * angle_cos(bodyvector, motionvec) * motionvec.length()));
				if (abs(pitchrudder) > 0.0001) {
//				Vector3d axisx = Utils.transformVecByQuat(new Vector3d(unitX), bodyRot);
					AxisAngle4d axisxangled = new AxisAngle4d(unitX, toRadians(-pitchrudder /4 * cof * planeInfo.pitchspeed));
					rotationmotion = Utils.quatRotateAxis(rotationmotion, axisxangled);
				}
				float tempYaw = yawrudder;
				if(abs(tempYaw) > 5){
					tempYaw *= 5/abs(tempYaw);
				}
				if (abs(yawrudder) > 0.001) {
					AxisAngle4d axisyangled;
					if (mc_Entity.onGround && motionvec.length() < 1) {
//					Vector3d axisy = Utils.transformVecByQuat(new Vector3d(unitY), bodyRot);
						axisyangled = new AxisAngle4d(unitY, toRadians(yawrudder /5 * motionvec.length() * planeInfo.yawspeed_taxing));
					} else {
//					Vector3d axisy = Utils.transformVecByQuat(new Vector3d(unitY), bodyRot);
						axisyangled = new AxisAngle4d(unitY, toRadians(yawrudder /5 * cof * planeInfo.yawspeed));
					}
					rotationmotion = Utils.quatRotateAxis(rotationmotion, axisyangled);
				}
				if (abs(rollrudder) > 0.0001) {
//				Vector3d axisz = Utils.transformVecByQuat(new Vector3d(unitZ), bodyRot);
					AxisAngle4d axiszangled = new AxisAngle4d(unitZ, toRadians(rollrudder /4 * cof * planeInfo.rollspeed));
					rotationmotion = Utils.quatRotateAxis(rotationmotion, axiszangled);
				}
			}
			
			double[] xyz = Utils.eulerfrommatrix(Utils.matrixfromQuat(bodyRot));
			bodyrotationPitch = (float) toDegrees(xyz[0]);
			if (!Double.isNaN(xyz[1])) {
				bodyrotationYaw = (float) toDegrees(xyz[1]);
			}
			bodyrotationRoll = (float) toDegrees(xyz[2]);
			camera.rotationYawHead = bodyrotationYaw;
			camera.prevRotationYawHead = prevbodyrotationYaw;
			camera.prevRotationYaw = prevbodyrotationYaw;
			camera.prevRotationPitch = prevbodyrotationPitch;
//		HMVPacketHandler.INSTANCE.sendToServer(new HMVPakcetVehicleState(planebody.getEntityId(),bodyRot, throttle,trigger1,trigger2));
//				if(th<2.5){
//					th +=0.1;
//				}
//				if (proxy.wclick()) {
//					th += 0.1;
//					GVCMPacketHandler.INSTANCE.sendToServer(new HMVMMessageKeyPressed(16, this.getEntityId()));
//				}
//				if (proxy.aclick()) {
////					GVCMPacketHandler.INSTANCE.sendToServer(new HMVMMessageKeyPressed(17, this.getEntityId()));
//					servera = true;
//				}
//				if (proxy.sclick()) {
//					th -= 0.1;
//					GVCMPacketHandler.INSTANCE.sendToServer(new HMVMMessageKeyPressed(18, this.getEntityId()));
//				}
//				if (proxy.dclick()) {
////					GVCMPacketHandler.INSTANCE.sendToServer(new HMVMMessageKeyPressed(19, this.getEntityId()));
//					serverd = true;
//				}
//				if (proxy.leftclick()) {
//					GVCMPacketHandler.INSTANCE.sendToServer(new HMVMMessageKeyPressed(11, this.getEntityId()));
//				}
//				if (proxy.spaceKeyDown()) {
//					GVCMPacketHandler.INSTANCE.sendToServer(new HMVMMessageKeyPressed(12, this.getEntityId()));
//				}
//				if (proxy.fclick()) {
//					GVCMPacketHandler.INSTANCE.sendToServer(new HMVMMessageKeyPressed(20, this.getEntityId()));
//				}
//
//				th -=0.05;
//				if(th>5){
//					th = 5;
//				}
//				if(th<0){
//					th = 0;
//				}
////				GVCMPacketHandler.INSTANCE.sendToServer(new HMVPacketMouseD(Mouse.getDX(),Mouse.getDY(),this.getEntityId()));
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
//					mainwingvector = Utils.rotationVector_byAxisVector(bodyVector,mainwingvector, abs(mousex)>4 ? (mousex>0?4f:-4f):mousex);
//					tailwingvector = Utils.rotationVector_byAxisVector(bodyVector,tailwingvector, abs(mousex)>4 ? (mousex>0?4f:-4f):mousex);
//				}
//				mousex*=0.9;
//
//				prevmousey = mousey;
//				if (abs(mousey)>0.00001) {
//					bodyVector     = Utils.rotationVector_byAxisVector(mainwingvector,bodyVector    ,abs(mousey)>4?(mousey>0?-4f:4f):-mousey);
//					tailwingvector = Utils.rotationVector_byAxisVector(mainwingvector,tailwingvector,abs(mousey)>4?(mousey>0?-4f:4f):-mousey);
//				}
//				mousey*=0.9;
//				prevyawladder = yawladder;
//				yawladder *=0.8;
//				if(abs(yawladder) < 0.001) yawladder = prevyawladder =0;else {
//					bodyVector = Utils.rotationVector_byAxisVector(tailwingvector, bodyVector, yawladder);
//					mainwingvector = Utils.rotationVector_byAxisVector(tailwingvector, mainwingvector, yawladder);
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
		}
		
		if(!plane_hasmode.standalone()) {
			if (health > 0 && serverw) {
				throttle += 0.05;
			}
			if (health <= 0) {
				throttle -= 0.05;
			}
			if (servera) {
				yawrudder--;
			}
			if (serverd) {
				yawrudder++;
			}
			if (servers) {
				throttle -= 0.05;
			}
		}
		if(abs(yawrudder) < 0.001) yawrudder = 0;
		yawrudder *= 0.8;
		
	}
	void autocontrol(Vector3d bodyvector){
		if(health>0){
			bodyvector = new Vector3d(bodyvector);//copy
			bodyvector.scale(-1);
			int genY = this.worldObj.getHeightValue((int) mc_Entity.posX, (int) mc_Entity.posZ);//target alt
			float alt;
			alt = (float) (mc_Entity.posY - genY);
			if(mc_Entity instanceof EntityLiving && ((EntityLiving) mc_Entity).getAttackTarget() != null){
				if(!T_useMain_F_useSub && subTurret == null)T_useMain_F_useSub = true;
				EntityLivingBase target = ((EntityLiving) mc_Entity).getAttackTarget();
				if(target.onGround && genY < target.posY)
					alt = (float) (mc_Entity.posY - target.posY);
//				if((target.onGround || (target.ridingEntity != null && target.ridingEntity.onGround)) && genY<target.posY){
//					alt = (float) (planebody.posY - target.posY);
//				}
				Vector3d courseVec = new Vector3d(target.posX,target.posY,target.posZ);
				courseVec.sub(new Vector3d(mc_Entity.posX, mc_Entity.posY, mc_Entity.posZ));
				courseVec.normalize();
				
				float targetyaw = wrapAngleTo180_float(-(float) toDegrees(atan2(courseVec.x, courseVec.z)));
				double AngulardifferenceYaw = targetyaw - this.bodyrotationYaw;
				AngulardifferenceYaw = wrapAngleTo180_double(AngulardifferenceYaw);
				double planespeed = mc_Entity.motionX * mc_Entity.motionX + mc_Entity.motionY * mc_Entity.motionY + mc_Entity.motionZ * mc_Entity.motionZ;
				TurretObj currentTurret = T_useMain_F_useSub ? mainTurret : subTurret;
				if(currentTurret != null) {
					Vector3d currentCannonpos = currentTurret.getCannonPos();
					double[] elevations = CalculateGunElevationAngle(currentCannonpos.x, currentCannonpos.y, currentCannonpos.z, target, (float) currentTurret.prefab_turret.gunInfo.gravity, currentTurret.prefab_turret.gunInfo.speed + (float) sqrt(planespeed));
					float targetpitch = (float) -elevations[0];
					if (!planeInfo.useMain_withSub && (T_useMain_F_useSub ? mainTurret.isreloading() : subTurret.isreloading())) {
						T_StartDive_F_FlyToStartDivePos = false;
					}
					if (!planeInfo.useMain_withSub) {
						if (T_useMain_F_useSub) {
							if (mainTurret.isreloading()) {
								rising_after_Attack = true;
								T_useMain_F_useSub = !T_useMain_F_useSub;
								changeWeaponCycle = 0;
							}
						} else {
							if (subTurret.isreloading()) {
								rising_after_Attack = true;
								T_useMain_F_useSub = !T_useMain_F_useSub;
								changeWeaponCycle = 0;
							}
						}
					}
					double toTargetPitch = -toDegrees(asin(courseVec.y));
					Vector3d motionvec = new Vector3d(mc_Entity.motionX, mc_Entity.motionY, mc_Entity.motionZ);
					if (!rising_after_Attack && T_StartDive_F_FlyToStartDivePos) {
//					System.out.println("" + targetpitch);
						boolean insight = elevations[2] != -1;
						boolean istarget_onGround = ((target instanceof ITank && ((ITank) target).ishittingWater()) || target.isInWater() || target.onGround || (target.ridingEntity != null && target.ridingEntity.onGround));
						
						if (planeInfo.type_F_Plane_T_Heli) {
							if ((motionvec.y < -0.1 || alt < planeInfo.cruiseALT))
								throttle += 0.05;
							else
								throttle -= 0.05;
							if (alt < planeInfo.cruiseALT) T_StartDive_F_FlyToStartDivePos = false;
						} else if (T_useMain_F_useSub && planeInfo.Dive_bombing && istarget_onGround) {
							if (toTargetPitch < planeInfo.startDive) {
								targetpitch = planeInfo.maxClimb;
								throttle += 0.05;
								insight = false;
								outSightCnt--;
							} else {
								if (targetpitch < planeInfo.startDive) {
									float AngulardifferencePitch = targetpitch - bodyrotationPitch;
									targetpitch = planeInfo.startDive-20;
									throttle += 0.05;
									insight = AngulardifferenceYaw > planeInfo.yawsightwidthmin && AngulardifferenceYaw < planeInfo.yawsightwidthmax && AngulardifferencePitch > planeInfo.pitchsighwidthmin && AngulardifferencePitch < planeInfo.pitchsighwidthmax;;
								} else {
									throttle += 0.05;
								}
							}
						} else if (T_useMain_F_useSub && planeInfo.Torpedo_bomber) {
							throttle += 0.05;
							if (alt < planeInfo.minALT + 5) {
								targetpitch = -20;
							} else if (alt > planeInfo.minALT) {
								targetpitch = 10;
							} else {
								targetpitch = 0;
							}
						} else if (planeInfo.throttledown_onDive && istarget_onGround) {
							if (throttle > (planeInfo.throttle_Max / 3) * 2) throttle -= 0.05;
							else throttle += 0.05;
						} else {
							throttle += 0.05;
						}
						float AngulardifferencePitch = targetpitch - bodyrotationPitch;
						if (targetpitch < planeInfo.maxClimb) targetpitch = planeInfo.maxClimb;
						if (planeInfo.type_F_Plane_T_Heli && targetpitch > planeInfo.maxDive) {
							targetpitch = planeInfo.maxDive;
							T_StartDive_F_FlyToStartDivePos = false;
						}
//					if(targetpitch < 0 && istarget_onGround)targetpitch = 0;
						if (!istarget_onGround) {
							handle_Yaw(AngulardifferenceYaw, targetpitch, alt);
							pitchHandle_considerYaw(targetpitch, AngulardifferenceYaw > 0);
						} else {
							if (handle_Yaw(AngulardifferenceYaw, 0, alt)) {
								if (abs(AngulardifferenceYaw) > 20 && abs(bodyrotationRoll) < 10) {
									pitchHandle_considerYaw(targetpitch, AngulardifferenceYaw > 0);
								} else {
									pitchHandle(targetpitch);
								}
							} else {
								pitchHandle_considerYaw(0, AngulardifferenceYaw > 0);
							}
						}
						insight &= AngulardifferenceYaw > planeInfo.yawsightwidthmin && AngulardifferenceYaw < planeInfo.yawsightwidthmax && AngulardifferencePitch > planeInfo.pitchsighwidthmin && AngulardifferencePitch < planeInfo.pitchsighwidthmax;
						if (T_useMain_F_useSub && planeInfo.Torpedo_bomber) {
							insight = mc_Entity.getDistanceSqToEntity(target) < 1600;
							if (insight) {
								rising_after_Attack = true;
								T_StartDive_F_FlyToStartDivePos = false;
							}
						}
						if (!planeInfo.useMain_withSub && mainTurret != null && subTurret != null) {
							if (planeInfo.sholdUseMain_ToG) {
								if (istarget_onGround) {
									T_useMain_F_useSub = !mainTurret.isreloading();
								} else {
									T_useMain_F_useSub = false;
								}
							} else if (planeInfo.sholdUseMain_ToA) {
								if (!istarget_onGround) {
									T_useMain_F_useSub = !mainTurret.isreloading();
								} else {
									T_useMain_F_useSub = false;
								}
							}
						}
						if (insight) {
							if (planeInfo.useMain_withSub) {
								trigger1 = true;
								trigger2 = true;
							} else if (T_useMain_F_useSub) {
								if (!mainTurret.isreloading())
									trigger1 = true;
								else {
									rising_after_Attack = true;
									T_useMain_F_useSub = !T_useMain_F_useSub;
									changeWeaponCycle = 0;
								}
							} else {
								if (!subTurret.isreloading())
									trigger2 = true;
								else {
									rising_after_Attack = true;
									T_useMain_F_useSub = !T_useMain_F_useSub;
									changeWeaponCycle = 0;
								}
							}
							outSightCnt--;
						} else {
							outSightCnt++;
							if (outSightCnt > planeInfo.outSightCntMax) {
								outSightCnt = 0;
								rising_after_Attack = true;
								//離脱
							}
						}
						changeWeaponCycle++;
						if (changeWeaponCycle > planeInfo.changeWeaponCycleSetting) {
							changeWeaponCycle = 0;
							T_useMain_F_useSub = !T_useMain_F_useSub;
						}
						if (target.onGround && toTargetPitch > planeInfo.maxDive) {
							T_StartDive_F_FlyToStartDivePos = false;
							rising_after_Attack = true;
						}
						if (!planeInfo.Torpedo_bomber && alt < planeInfo.minALT) {
							rising_after_Attack = true;
						}
					} else {
						if (planeInfo.type_F_Plane_T_Heli) {
							if ((motionvec.y < -0.1 || alt < planeInfo.cruiseALT))
								throttle += 0.05;
							else
								throttle -= 0.05;
							if (alt < planeInfo.cruiseALT) T_StartDive_F_FlyToStartDivePos = false;
						} else
							throttle += 0.05;
						if (!target.onGround) T_StartDive_F_FlyToStartDivePos = true;
						if (toTargetPitch < (planeInfo.Dive_bombing ? planeInfo.startDive / 1.1 : planeInfo.startDive))
							T_StartDive_F_FlyToStartDivePos = true;
						if (toTargetPitch < 0 && alt > planeInfo.minALT) rising_after_Attack = false;
						if (alt > planeInfo.cruiseALT) rising_after_Attack = false;
						if (!target.onGround && outSightCnt > 0) {
							rising_after_Attack = true;
							outSightCnt -= 10;
						}
						if(planeInfo.Dive_bombing && bodyrotationPitch>30){
							setControl_x(true);
						}else {
							setControl_x(false);
						}
						if (rand.nextInt(500) == 0) climbYawDir = !climbYawDir;
						if (planeInfo.type_F_Plane_T_Heli) {
							handle_withPitch(0, 15, planeInfo.cruiseALT);
						} else if (alt > planeInfo.cruiseALT + 40)
							handle_withPitch(!T_StartDive_F_FlyToStartDivePos && !(toTargetPitch < (planeInfo.Dive_bombing ? planeInfo.startDive / 2 : planeInfo.startDive)) && alt > planeInfo.cruiseALT * 0.75 ? -AngulardifferenceYaw : climbYawDir ? 12 : -12, 2, planeInfo.cruiseALT);
						else {
							handle_withPitch(!T_StartDive_F_FlyToStartDivePos && !(toTargetPitch < (planeInfo.Dive_bombing ? planeInfo.startDive / 2 : planeInfo.startDive)) && alt > planeInfo.cruiseALT * 0.75 ? -AngulardifferenceYaw : climbYawDir ? 12 : -12, alt < planeInfo.cruiseALT ? planeInfo.maxClimb : 0, planeInfo.cruiseALT);
							rollHandle(0);
						}
					}
				}else {
					Vector3d motionvec = new Vector3d(mc_Entity.motionX, mc_Entity.motionY, mc_Entity.motionZ);
					if (planeInfo.type_F_Plane_T_Heli) {
						if ((motionvec.y < -0.1 || alt < planeInfo.cruiseALT))
							throttle += 0.05;
						else if(alt < planeInfo.cruiseALT + 20)throttle -= 0.05;
						
						handle_withPitch(AngulardifferenceYaw+(climbYawDir ? 15 : -15), planeInfo.maxDive, alt);
					} else {
						throttle += 0.05;
						handle_withPitch(AngulardifferenceYaw + (climbYawDir ? 15 : -15), 0, alt);
					}
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
				switch (((Hasmode) mc_Entity).getMobMode()) {
					case 0://wait
					{
						Vector3d motionvec = new Vector3d(mc_Entity.motionX, mc_Entity.motionY, mc_Entity.motionZ);
						if(rand.nextInt(500) == 0)climbYawDir = !climbYawDir;
						if(planeInfo.type_F_Plane_T_Heli){
							handle_withPitch(climbYawDir ? 12 : -12,15,planeInfo.cruiseALT);
						}else if(alt<planeInfo.cruiseALT){
							handle_withPitch(climbYawDir ? 12 : -12,planeInfo.maxClimb,planeInfo.cruiseALT);
						}
						else if(alt>planeInfo.cruiseALT + 40) handle_withPitch(climbYawDir ? 12 : -12,2,planeInfo.cruiseALT);
						else handle_withPitch(climbYawDir ? 12 : -12,0,planeInfo.cruiseALT);
						
						if (planeInfo.type_F_Plane_T_Heli && (motionvec.y < -0.1 || alt < planeInfo.cruiseALT)) {
							throttle += 0.05;
						} else if (!planeInfo.type_F_Plane_T_Heli && (motionvec.y < bodyvector.y || alt < planeInfo.cruiseALT)) {
							throttle += 0.05;
						} else {
							if (throttle > planeInfo.throttle_Max * 0.8) throttle -= 0.05;
						}
						break;
					}
					case 1:
					case 2://follow
					{
						Vector3d motionvec = new Vector3d(mc_Entity.motionX, mc_Entity.motionY, mc_Entity.motionZ);
						if (motionvec.length() > 0.1) motionvec.normalize();
						if (planeInfo.type_F_Plane_T_Heli && (motionvec.y < -0.1 || alt < planeInfo.cruiseALT)) {
							throttle += 0.05;
						} else if (!planeInfo.type_F_Plane_T_Heli && (motionvec.y < bodyvector.y || alt < planeInfo.cruiseALT)) {
							throttle += 0.05;
						} else {
							if (throttle > planeInfo.throttle_Max * 0.8) throttle -= 0.05;
						}
						double[] targetpos = ((Hasmode) mc_Entity).getTargetpos();
						if(targetpos == null)targetpos = new double[]{mc_Entity.posX, mc_Entity.posY, mc_Entity.posZ};
						Vector3d courseVec = new Vector3d(targetpos);
						courseVec.sub(new Vector3d(mc_Entity.posX, courseVec.y, mc_Entity.posZ));
//						double angletocourse = toDegrees(bodyvector.angle(courseVec));
//						System.out.println("" + angletocourse);
						float targetyaw = wrapAngleTo180_float(-(float) toDegrees(atan2(courseVec.x, courseVec.z)));
						double AngulardifferenceYaw = targetyaw - this.bodyrotationYaw;
						AngulardifferenceYaw = wrapAngleTo180_double(AngulardifferenceYaw);
						if(alt < planeInfo.minALT){
							rollHandle(0);
							pitchHandle(!planeInfo.type_F_Plane_T_Heli ? planeInfo.maxClimb : 0);
						}
						else {
							float pitch = alt < planeInfo.cruiseALT ? planeInfo.maxClimb: ((alt > planeInfo.cruiseALT + 60) ? 5 : 0);
							if (planeInfo.type_F_Plane_T_Heli) {
								double dist = courseVec.length();
								if (dist > 16) {
									handle_withPitch(AngulardifferenceYaw, 10, alt);
								} else {
									handle_withPitch(AngulardifferenceYaw, 0, alt);
								}
							} else {
								handle_withPitch(AngulardifferenceYaw, pitch, alt);
							}
						}
//						pitchHandle(alt<60?alt<30?maxClimb:0:maxDive);
						
						break;
					}
					
				}
			}
		}else {
			throttle-=0.05;
		}
	}
	public boolean handle_withPitch(double AngulardifferenceYaw, double targetPitch, double alt){
		handle_Yaw(AngulardifferenceYaw,targetPitch,alt);
		return pitchHandle_considerYaw(targetPitch,AngulardifferenceYaw>0);
	}
	public boolean handle_Yaw(double AngulardifferenceYaw, double targetPitch, double alt){
		double maxRollCof = 1 - abs(targetPitch)/90;
		boolean canUseElevator = (abs(bodyrotationRoll) < 90 && targetPitch+10>bodyrotationPitch) || (abs(bodyrotationRoll) > 90 && targetPitch-10<bodyrotationPitch);
//		System.out.println("canUseElevator " + canUseElevator);
		boolean canUseLadder = abs(bodyrotationRoll) < 20 || (abs(bodyrotationRoll) < 90 && targetPitch-10<bodyrotationPitch) || (abs(bodyrotationRoll) > 90 && targetPitch+10>bodyrotationPitch);
//		System.out.println("canUseLadder " + canUseLadder);
//		System.out.println("turnDir " + (AngulardifferenceYaw>0));
		if(maxRollCof < 0)maxRollCof = 0;
		maxRollCof *= min(AngulardifferenceYaw * AngulardifferenceYaw, 225) / 225;
		if(AngulardifferenceYaw < 0){
			//turn Left
			if(!planeInfo.type_F_Plane_T_Heli) {
				
				if (alt > planeInfo.cruiseALT) rollHandle(-planeInfo.maxbank * maxRollCof);
				else if (alt > planeInfo.minALT) rollHandle(-planeInfo.maxbank * maxRollCof * (alt) / (planeInfo.cruiseALT));
				else {
					rollHandle(0);
				}
			}else {
				rollHandle(0);
			}
			
			if(!planeInfo.type_F_Plane_T_Heli) {
				if (abs(bodyrotationRoll) < 45) {
					yawrudder -= min(abs(AngulardifferenceYaw), 10) / 10;//a
				} else if (abs(bodyrotationRoll) > 135) {
					yawrudder += min(abs(AngulardifferenceYaw), 10) / 10;//d
				}
			}else if(canUseLadder){
				if (abs(bodyrotationRoll) < 60) {
					yawrudder -= min(abs(AngulardifferenceYaw)/5, 16)/16;//a
				} else if (abs(bodyrotationRoll) > 120) {
					yawrudder += min(abs(AngulardifferenceYaw)/5, 16)/16;//d
				}
			}
			if(!planeInfo.type_F_Plane_T_Heli) {
				if (abs(bodyrotationRoll) > 45 && abs(bodyrotationRoll) < 135 && canUseElevator) {
					if (bodyrotationRoll < 0) {
						//左に傾いている
						mousey -= abs(AngulardifferenceYaw) / planeInfo.pitchspeed / 3;//↓
					} else if (bodyrotationRoll > 0) {
						//右に傾いている
						mousey += abs(AngulardifferenceYaw) / planeInfo.pitchspeed / 3;//↑
					}
				}
			}
		}else if(AngulardifferenceYaw > 0){
			//turn Right
			
			if(!planeInfo.type_F_Plane_T_Heli) {
				if (alt > planeInfo.cruiseALT) rollHandle(planeInfo.maxbank * maxRollCof);
				else if (alt > planeInfo.minALT) rollHandle(planeInfo.maxbank * maxRollCof * (alt) / (planeInfo.cruiseALT));
				else {
					rollHandle(0);
				}
			} else {
				rollHandle(0);
			}
			
			if(!planeInfo.type_F_Plane_T_Heli) {
				if (abs(bodyrotationRoll) < 45) {
					yawrudder += min(abs(AngulardifferenceYaw), 10) / 10;//d
				} else if (abs(bodyrotationRoll) > 135) {
					yawrudder -= min(abs(AngulardifferenceYaw), 10) / 10;//a
				}
			}else if(canUseLadder){
				if (abs(bodyrotationRoll) < 60) {
					yawrudder += min(abs(AngulardifferenceYaw)/5, 16)/16;//d
				} else if (abs(bodyrotationRoll) > 120) {
					yawrudder -= min(abs(AngulardifferenceYaw)/5, 16)/16;//a
				}
			}
			if(!planeInfo.type_F_Plane_T_Heli) {
				if (abs(bodyrotationRoll) > 45 && abs(bodyrotationRoll) < 135 && canUseElevator) {
					if (bodyrotationRoll < 0) {
						//左に傾いている
						mousey += abs(AngulardifferenceYaw) / planeInfo.pitchspeed / 3;//↓
					} else if (bodyrotationRoll > 0) {
						//右に傾いている
						mousey -= abs(AngulardifferenceYaw) / planeInfo.pitchspeed / 3;//↑
					}
				}
			}
			
//			if(abs(bodyrotationRoll)<30){
//				pitchHandle(targetPitch);
//			}else
//			if(abs(bodyrotationRoll)>30 && bodyrotationPitch > targetPitch-10) {
//				if (bodyrotationRoll < 0 && bodyrotationPitch < targetPitch+5) {//機首が高い
//					//右に傾いている
//					//操縦桿押し込み
//					mousey -= abs(AngulardifferenceYaw)/2;
////					System.out.println("debug3");
//				} else if (bodyrotationRoll > 0 && bodyrotationPitch > targetPitch-5) {//機首が低い
//					//左に傾いている
//					//操縦桿引き込み
//					mousey += abs(AngulardifferenceYaw)/2;
////					System.out.println("debug4");
//				}
//			}
		}else {
			rollHandle(0);
		}
//		System.out.println("yawladder auto" + yawladder);
		return abs(AngulardifferenceYaw) < 20;
	}
	public void rollHandle(double targetRoll){
		double[] xyz = Utils.eulerfrommatrix(Utils.matrixfromQuat(rotationmotion));
		if (bodyrotationRoll > targetRoll) {
			mousex -= abs(bodyrotationRoll-targetRoll)/4;
			xyz[2] = toDegrees(xyz[2]);
			if(xyz[2]<0)mousex -= abs(xyz[2])/10;
		} else if (bodyrotationRoll < targetRoll) {
			mousex += abs(bodyrotationRoll-targetRoll)/4;
			xyz[2] = toDegrees(xyz[2]);
			if(xyz[2]>0)mousex += abs(xyz[2])/10;
		}
	}
	public boolean pitchHandle(double targetPitch){
		
		double sensiv = planeInfo.type_F_Plane_T_Heli ? 0.05:0.25;
		if(planeInfo.type_F_Plane_T_Heli) {
			if(targetPitch<planeInfo.maxClimb)targetPitch = planeInfo.maxClimb;
			if(targetPitch>planeInfo.maxDive)targetPitch = planeInfo.maxDive;
		}
		double AngulardifferencePitch = bodyrotationPitch - targetPitch;
		double[] xyz = Utils.eulerfrommatrix(Utils.matrixfromQuat(rotationmotion));
		xyz[0] = toDegrees(xyz[2]);
//		if (bodyrotationRoll > 0) {
//			mousex -= abs(AngulardifferencePitch)/15 * sensiv;
//		} else if (bodyrotationRoll < 0) {
//			mousex += abs(AngulardifferencePitch)/15 * sensiv;
//		}
		if (AngulardifferencePitch < 0){
			//機首下げ
			if(abs(bodyrotationRoll)<45) {
				mousey += abs(AngulardifferencePitch)/planeInfo.pitchspeed * sensiv;
			}else if(abs(bodyrotationRoll)>135){
				mousey -= abs(AngulardifferencePitch)/planeInfo.pitchspeed * sensiv;
			}
			if(abs(bodyrotationRoll) > 45 && abs(bodyrotationRoll) < 135) {
				if (bodyrotationRoll < 0) {
					//左に傾いている
					yawrudder +=min(abs(AngulardifferencePitch),10)/40 * sensiv;
				} else if (bodyrotationRoll > 0) {
					//右に傾いている
					yawrudder -=min(abs(AngulardifferencePitch),10)/40 * sensiv;
				}
			}
		}else if (AngulardifferencePitch > 0){
			//機首上げ
			if(abs(bodyrotationRoll)<45) {
				mousey -= abs(AngulardifferencePitch)/planeInfo.pitchspeed/2 * sensiv;;
			}else if(abs(bodyrotationRoll)>135){
				mousey += abs(AngulardifferencePitch)/planeInfo.pitchspeed/2 * sensiv;;
			}
			if(abs(bodyrotationRoll) > 45 && abs(bodyrotationRoll) < 135) {
				if (bodyrotationRoll < 0) {
					//左に傾いている
					yawrudder -=min(abs(AngulardifferencePitch),10)/40 * sensiv;;
				} else if (bodyrotationRoll > 0) {
					//右に傾いている
					yawrudder +=min(abs(AngulardifferencePitch),10)/40 * sensiv;;
				}
			}
		}else {
			return true;
		}
		return false;
	}
	
	public boolean pitchHandle_considerYaw(double targetPitch,boolean yawDir){
		//yawDir=false:turnLeft
		//yawDir=true :turnRight
		double sensiv = planeInfo.type_F_Plane_T_Heli ? 0.05:1;
		if(planeInfo.type_F_Plane_T_Heli) {
			if(targetPitch<planeInfo.maxClimb)targetPitch = planeInfo.maxClimb;
			if(targetPitch>planeInfo.maxDive)targetPitch = planeInfo.maxDive;
		}
		double AngulardifferencePitch = bodyrotationPitch - targetPitch;
		double[] xyz = Utils.eulerfrommatrix(Utils.matrixfromQuat(rotationmotion));
		xyz[0] = toDegrees(xyz[2]);
//		if (bodyrotationRoll > 0) {
//			mousex -= abs(AngulardifferencePitch)/15 * sensiv;
//		} else if (bodyrotationRoll < 0) {
//			mousex += abs(AngulardifferencePitch)/15 * sensiv;
//		}
		boolean allow_PitchUp = abs(bodyrotationRoll)<5 || (yawDir && bodyrotationRoll > 0) || (!yawDir && bodyrotationRoll < 0);
		boolean allow_PitchDown = abs(bodyrotationRoll)<5 || (yawDir && bodyrotationRoll < 0) || (!yawDir && bodyrotationRoll > 0);
		if (AngulardifferencePitch < 0){
			//機首下げ
			if(allow_PitchDown) {
				if (abs(bodyrotationRoll) < 45) {
					mousey += abs(AngulardifferencePitch) / planeInfo.pitchspeed * sensiv;
				} else if (abs(bodyrotationRoll) > 135) {
					mousey -= abs(AngulardifferencePitch) / planeInfo.pitchspeed * sensiv;
				}
			}
			if(abs(bodyrotationRoll) > 15 && abs(bodyrotationRoll) < 165) {
				if (bodyrotationRoll < 0 && !yawDir) {
					//左に傾いている
//					System.out.println("debug");
					yawrudder -=min(abs(AngulardifferencePitch),10)/10 * sensiv;
				} else if (bodyrotationRoll > 0 && yawDir) {
					//右に傾いている
//					System.out.println("debug");
					yawrudder +=min(abs(AngulardifferencePitch),10)/10 * sensiv;
				}
			}
		}else if (AngulardifferencePitch > 0){
			//機首上げ
			if(allow_PitchUp) {
				if (abs(bodyrotationRoll) < 45) {
					mousey -= abs(AngulardifferencePitch) / planeInfo.pitchspeed / 2 * sensiv;
					;
				} else if (abs(bodyrotationRoll) > 135) {
					mousey += abs(AngulardifferencePitch) / planeInfo.pitchspeed / 2 * sensiv;
					;
				}
			}
			if(abs(bodyrotationRoll) > 15 && abs(bodyrotationRoll) < 165) {
				if (bodyrotationRoll < 0 && yawDir) {
					//左に傾いている
//					System.out.println("debug");
					yawrudder +=min(abs(AngulardifferencePitch),10)/10 * sensiv;
				} else if (bodyrotationRoll > 0 && !yawDir) {
					//右に傾いている
//					System.out.println("debug");
					yawrudder -=min(abs(AngulardifferencePitch),10)/10 * sensiv;
				}
			}
		}else {
			return true;
		}
		return false;
	}
	
	void FCS(Vector3d mainwingvector,Vector3d tailwingvector,Vector3d bodyvector){
		if (trigger1) {
			if(mainTurret != null){
				if(riddenByEntities[plane.getpilotseatid()] != null)mainTurret.currentEntity = riddenByEntities[plane.getpilotseatid()];
				mainTurret.currentEntity.motionX = this.mc_Entity.motionX;
				mainTurret.currentEntity.motionY = this.mc_Entity.motionY;
				mainTurret.currentEntity.motionZ = this.mc_Entity.motionZ;
				mainTurret.fireall();
			}
//			for(int i = 0;i<2;i++){
//				HMGEntityBullet var3 = new HMGEntityBullet(this.worldObj, riddenByEntities[0].riddenByEntity, 40, 8, 3);
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
				if(riddenByEntities[plane.getpilotseatid()] != null)subTurret.currentEntity = riddenByEntities[plane.getpilotseatid()];
				subTurret.currentEntity.motionX = this.mc_Entity.motionX;
				subTurret.currentEntity.motionY = this.mc_Entity.motionY;
				subTurret.currentEntity.motionZ = this.mc_Entity.motionZ;
				subTurret.fireall();
			}
			trigger2 = false;
		}
	}
	
	void motionUpdate(Vector3d mainwingvector,Vector3d tailwingvector,Vector3d bodyvector){
		
		if(!worldObj.isRemote)
		{
			Vector3d motionvec = new Vector3d(mc_Entity.motionX, mc_Entity.motionY, mc_Entity.motionZ);
			
			Vector3d windvec = new Vector3d(motionvec);
			if (mc_Entity.onGround && windvec.y < 0) windvec.y = 0;
			if (windvec.length() > 0.000001 && planeInfo.stability != 0) {
				Vector3d axisstall = new Vector3d();
				windvec.normalize();
				windvec.add(new Vector3d(bodyvector.x * 0.3,
						                        bodyvector.y * 0.3,
						                        bodyvector.z * 0.3));
				tailwingvector.normalize();
				axisstall.cross(bodyvector, windvec);
				axisstall.normalize();
				axisstall.z = -axisstall.z;
				Quat4d quat4d = new Quat4d();
				quat4d.inverse(bodyRot);
				quat4d.normalize();
				axisstall = Utils.transformVecByQuat(axisstall, quat4d);
				if (!Double.isNaN(axisstall.x) && !Double.isNaN(axisstall.y) && !Double.isNaN(axisstall.z)) {
					windvec.set(motionvec);
					if (mc_Entity.onGround && windvec.y < 0) windvec.y = 0;
					Vector3d bodyVec_front = new Vector3d(bodyvector);
					bodyVec_front.scale(-1);
					double sin = angle_cos(bodyVec_front, motionvec);
					sin = sqrt(1 - sin * sin);
//					System.out.println(sin);
					AxisAngle4d axisxangledstall = new AxisAngle4d(axisstall, motionvec.length() * sin / planeInfo.stability);
					rotationmotion = Utils.quatRotateAxis(rotationmotion, axisxangledstall);
				}
			}
			
			windvec = new Vector3d(motionvec);
			if (mc_Entity.onGround && windvec.y < 0) windvec.y = 0;
			
			rotationmotion.normalize();
			bodyRot.mul(rotationmotion);
			if (!mc_Entity.onGround) {
				double cos = planeInfo.forced_rotmotion_reduceSpeed - ((1-planeInfo.forced_rotmotion_reduceSpeed) * angle_cos(bodyvector, motionvec)) * planeInfo.rotmotion_reduceSpeed;
				if (Double.isNaN(cos)) cos = planeInfo.rotmotion_reduceSpeed;
				if (mc_Entity.onGround || windvec.length() < 1)
					rotationmotion.interpolate(new Quat4d(0, 0, 0, 1), cos);
				else
					rotationmotion.interpolate(new Quat4d(0, 0, 0, 1), cos);
				
				double[] xyz = Utils.eulerfrommatrix(Utils.matrixfromQuat(rotationmotion));
				AxisAngle4d axiszangled = new AxisAngle4d(unitZ, -xyz[2] * planeInfo.rotmotion_reduceSpeedRoll);
				rotationmotion = Utils.quatRotateAxis(rotationmotion, axiszangled);
			} else {
				rotationmotion.interpolate(new Quat4d(0, 0, 0, 1), planeInfo.forced_rotmotion_reduceSpeed + (1-planeInfo.forced_rotmotion_reduceSpeed) * planeInfo.rotmotion_reduceSpeed * 1);
			}
			
			
			tailwingvector = Utils.transformVecByQuat(new Vector3d(unitY), bodyRot);
			bodyvector = Utils.transformVecByQuat(new Vector3d(unitZ), bodyRot);
			mainwingvector = Utils.transformVecByQuat(new Vector3d(unitX), bodyRot);
			
			Utils.transformVecforMinecraft(tailwingvector);
			Utils.transformVecforMinecraft(bodyvector);
			Utils.transformVecforMinecraft(mainwingvector);
			
			motionvec = new Vector3d(mc_Entity.motionX, mc_Entity.motionY, mc_Entity.motionZ);
			Vector3d powerVec = new Vector3d(planeInfo.unitThrottle);
			powerVec = transformVecByQuat(powerVec,bodyRot);
			transformVecforMinecraft(powerVec);
			if (!(Double.isNaN(bodyvector.x) || Double.isNaN(bodyvector.y) || Double.isNaN(bodyvector.z))) {
				powerVec.scale(-throttle * planeInfo.speedfactor);
				motionvec.add(powerVec);
				if (planeInfo.throttle_AF < throttle) {
					powerVec.normalize();
					powerVec.scale(-throttle * planeInfo.speedfactor_af);
					motionvec.add(powerVec);
				}
			}
			if (motionvec.length() > 0.01) {
				motionvec.y -= planeInfo.gravity;
				double cos;
				{
					cos = angle_cos(bodyvector, motionvec);
					Vector3d tailwingvectorForFloating = new Vector3d(tailwingvector);
					tailwingvectorForFloating.scale(motionvec.length() * motionvec.length() * -cos * (planeInfo.liftfactor + flaplevel * planeInfo.flapliftfactor));
//			System.out.println("debug" + cos);
//					motionvec.x += tailwingvectorForFloating.x / slipresist;
					motionvec.y += abs(tailwingvectorForFloating.y);
//					motionvec.z += tailwingvectorForFloating.z / slipresist;
				}
//				if (planebody.onGround && motionvec.y < 0) motionvec.y = 0;
				if (motionvec.length() > 0.000001 && planeInfo.stability2 != 0) {
					Vector3d liftVec1 = new Vector3d();
					liftVec1.cross(mainwingvector, motionvec);
					liftVec1.normalize();
					if (!(Double.isNaN(liftVec1.x) || Double.isNaN(liftVec1.y) || Double.isNaN(liftVec1.z))) {
						double sin = angle_cos(liftVec1, bodyvector);
						liftVec1.set(tailwingvector);
						liftVec1.normalize();
						liftVec1.scale(motionvec.length() * sin * planeInfo.stability2);
						if (motionvec.length() < 0.8) liftVec1.scale(motionvec.length() / 0.8);
						if (!Double.isNaN(sin)) {
//							System.out.println(sin);
							motionvec.add(liftVec1);
						}
					}
				}
				if (motionvec.length() > 0.000001) {
					Vector3d resistskidding = new Vector3d();
					resistskidding.cross(tailwingvector, motionvec);
					resistskidding.normalize();
					if (!(Double.isNaN(resistskidding.x) || Double.isNaN(resistskidding.y) || Double.isNaN(resistskidding.z))) {
						double sin = angle_cos(resistskidding, bodyvector);
						resistskidding.set(mainwingvector);
						resistskidding.normalize();
						resistskidding.scale(-motionvec.length() * sin * (mc_Entity.onGround ? planeInfo.slipresist_onground :planeInfo.slipresist));
						if (motionvec.length() < 0.8) resistskidding.scale(motionvec.length() / 0.8);
						if (!Double.isNaN(sin)) {
//							System.out.println(sin);
							motionvec.add(resistskidding);
						}
					}
				}
				if (motionvec.length() > 0.000001) {
					Vector3d airDrug = new Vector3d(motionvec);
					airDrug.scale(motionvec.length() * motionvec.length() * (planeInfo.dragfactor + gearprogress * planeInfo.geardragfactor + flaplevel * planeInfo.flapdragfactor)
							              + (mc_Entity.onGround && serverx ? motionvec.length() * planeInfo.brakedragfactor_ground : 0) + (serverx ? motionvec.length() * planeInfo.brakedragfactor : 0));
					motionvec.sub(airDrug);
				} else {
					motionvec.scale(0);
				}
			} else {
				motionvec.scale(0);
				motionvec.y -= planeInfo.gravity;
			}
			if (!Double.isNaN(motionvec.x) && !Double.isNaN(motionvec.y) && !Double.isNaN(motionvec.z)) {
				mc_Entity.motionX = abs(motionvec.x) > 0.0000001 ? motionvec.x : 0;
				mc_Entity.motionY = abs(motionvec.y) > 0.0000001 ? motionvec.y : 0;
				mc_Entity.motionZ = abs(motionvec.z) > 0.0000001 ? motionvec.z : 0;
			}
			
			motionvec = new Vector3d(mc_Entity.motionX, mc_Entity.motionY, mc_Entity.motionZ);
			HMVPacketHandler.INSTANCE.sendToAll(new HMVPakcetPlaneState(mc_Entity.getEntityId(),bodyRot,rotationmotion,motionvec,throttle));
			moveEntity(mc_Entity.motionX, mc_Entity.motionY, mc_Entity.motionZ);
			
			double backmotionX = mc_Entity.motionX;
			double backmotionY = mc_Entity.motionY;
			double backmotionZ = mc_Entity.motionZ;
			motionvec.normalize();
			if (mc_Entity.motionY > 0) {
				mc_Entity.isAirBorne = true;
			}
			
			
			if (throttle > planeInfo.throttle_Max) {
				throttle = planeInfo.throttle_Max;
			}
			if (throttle < planeInfo.throttle_min) {
				throttle = planeInfo.throttle_min;
			}
			mc_Entity.fallDistance = 0;
			if (mc_Entity.isCollidedHorizontally) {
				if (backmotionX * backmotionX + backmotionY * backmotionY + backmotionZ * backmotionZ > 1) {
					mc_Entity.attackEntityFrom(DamageSource.fall, (float) (backmotionX * backmotionX + backmotionY * backmotionY + backmotionZ * backmotionZ) * 30);
				}
			}
		}else {
			moveEntity(mc_Entity.motionX, mc_Entity.motionY, mc_Entity.motionZ);
		}
		{
			if (mc_Entity.onGround) {
				Vector3d axisx = new Vector3d(-cos(toRadians(bodyrotationYaw)), 0, sin(toRadians(bodyrotationYaw)));
				if (abs(bodyrotationPitch - planeInfo.onground_pitch) > 15) {
					mc_Entity.attackEntityFrom(DamageSource.fall, (float) (30));
				}
				AxisAngle4d axisxangled = new AxisAngle4d(axisx, toRadians((planeInfo.onground_pitch - bodyrotationPitch) / 10));
				bodyRot = Utils.quatRotateAxis(bodyRot, axisxangled);
				
				axisx = Utils.transformVecByQuat(new Vector3d(unitZ), bodyRot);
				if (bodyrotationRoll < 45 && bodyrotationRoll > -45) {
					axisxangled = new AxisAngle4d(axisx, toRadians(-bodyrotationRoll / 10));
				}
				if (bodyrotationRoll < -45 && bodyrotationRoll > -135) {
					mc_Entity.attackEntityFrom(DamageSource.fall, (float) (20));
					axisxangled = new AxisAngle4d(axisx, toRadians((-90 - bodyrotationRoll) / 10));
				}
				if (bodyrotationRoll < 135 && bodyrotationRoll > 45) {
					mc_Entity.attackEntityFrom(DamageSource.fall, (float) (20));
					axisxangled = new AxisAngle4d(axisx, toRadians((90 - bodyrotationRoll) / 10));
				}
				if (bodyrotationRoll > 135) {
					mc_Entity.attackEntityFrom(DamageSource.fall, (float) (30));
					axisxangled = new AxisAngle4d(axisx, toRadians((180 - bodyrotationRoll) / 10));
				}
				if (bodyrotationRoll < -135) {
					mc_Entity.attackEntityFrom(DamageSource.fall, (float) (30));
					axisxangled = new AxisAngle4d(axisx, toRadians((-180 - bodyrotationRoll) / 10));
				}
				bodyRot = Utils.quatRotateAxis(bodyRot, axisxangled);
				
				gearprogress = 100;
			} else {
				if (throttle > planeInfo.throttle_gearDown) {
					gearprogress--;
				} else {
					gearprogress++;
				}
				
				if (gearprogress < 0) {
					gearprogress = 0;
				}
				if (gearprogress > 100) {
					gearprogress = 100;
				}
				
			}
			Vector3d motionvec = new Vector3d(mc_Entity.motionX, mc_Entity.motionY, mc_Entity.motionZ);
			if ((motionvec.y < -bodyvector.y && planeInfo.autoflap) || mc_Entity.onGround || serverf || proxy_HMVehicle.fclick()) {
				Flapextension();
			} else {
				Flapstorage();
			}
			if (flaplevel < 0) {
				flaplevel = 0;
			}
			if (flaplevel > 75) {
				flaplevel = 75;
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
	
	public void setPosition(double p_70107_1_, double p_70107_3_, double p_70107_5_)
	{
		mc_Entity.posX = p_70107_1_;
		mc_Entity.posY = p_70107_3_;
		mc_Entity.posZ = p_70107_5_;
		float f = mc_Entity.width / 2.0F;
		float f1 = mc_Entity.height;
		mc_Entity.boundingBox.setBounds(p_70107_1_ - (double)f, p_70107_3_ - (double) mc_Entity.yOffset + (double) mc_Entity.ySize, p_70107_5_ - (double)f, p_70107_1_ + (double)f, p_70107_3_ - (double) mc_Entity.yOffset + (double) mc_Entity.ySize + (double)f1, p_70107_5_ + (double)f);
		if(mc_Entity.boundingBox instanceof ModifiedBoundingBox){
			((ModifiedBoundingBox) mc_Entity.boundingBox).update(mc_Entity.posX,
					mc_Entity.posY,
					mc_Entity.posZ);
		}
	}
	
	@Override
	public String getsound() {
		return planeInfo.soundname;
	}
	
	public float getsoundPitch(){
		return throttle /planeInfo.throttle_Max*planeInfo.soundpitch;
	}
	
	public void yourSoundIsremain(){
		needStartSound = false;
	}
	
	
	public void setControl_RightClick(boolean value) {
		trigger1 = value;
	}
	
	
	public void setControl_LeftClick(boolean value) {
		trigger2 = value;
	}
	
	
	public void setControl_Space(boolean value) {
		serverspace = value;
	}
	
	
	public void setControl_x(boolean value) {
		serverx = value;
	}
	
	
	public void setControl_w(boolean value) {
		serverw = value;
	}
	
	
	public void setControl_a(boolean value) {
		servera = value;
	}
	
	
	public void setControl_s(boolean value) {
		servers = value;
	}
	
	
	public void setControl_d(boolean value) {
		serverd = value;
	}
	
	
	public void setControl_f(boolean value) {
		serverf = value;
	}
	
	@Override
	public boolean getControl_RightClick() {
		return trigger1;
	}
	
	@Override
	public boolean getControl_LeftClick() {
		return trigger2;
	}
	
	@Override
	public boolean getControl_Space() {
		return serverspace;
	}
	
	@Override
	public boolean getControl_x() {
		return serverx;
	}
	
	@Override
	public boolean getControl_w() {
		return serverw;
	}
	
	@Override
	public boolean getControl_a() {
		return servera;
	}
	
	@Override
	public boolean getControl_s() {
		return servers;
	}
	
	@Override
	public boolean getControl_d() {
		return serverd;
	}
	
	@Override
	public boolean getControl_f() {
		return serverf;
	}
	
	@Override
	public void setMouse(float tempMouseX, float tempMouseY, float tempMouseZ) {
		mousex = tempMouseX;
		mousey = tempMouseY;
		yawrudder = tempMouseZ;
	}
	public Entity[] getRiddenEntityList(){
		return riddenByEntities;
	}
	
	@Override
	public SeatInfo[] getRiddenSeatList() {
		return seatInfos;
	}
	
	public boolean pickupEntity(Entity p_70085_1_, int startSeachSeatNum){
		if(isRidingEntity(p_70085_1_))return false;
		boolean flag = false;
		if(!mc_Entity.worldObj.isRemote) {
			for (int cnt = 0; cnt < riddenByEntities.length; cnt++) {
				int tempid = cnt + startSeachSeatNum;
				while(tempid < 0)tempid = tempid + riddenByEntities.length;
				while(tempid >= seatInfos.length)tempid = tempid - riddenByEntities.length;
				if (riddenByEntities[tempid] == null) {
					riddenByEntities[tempid] = p_70085_1_;
					flag = true;
					break;
				}
			}
			if (flag)
				HMVPacketHandler.INSTANCE.sendToAll(new HMVPacketPickNewEntity(mc_Entity.getEntityId(), riddenByEntities));
		}
		return flag;
//		p_70085_1_.mountEntity(this);
	}
	public boolean isRidingEntity(Entity entity){
		for(Entity aRiddenby: riddenByEntities){
			if(entity == aRiddenby)return true;
		}
		return false;
	}
	
	public double[] getCamerapos(){
		return proxy_HMVehicle.iszooming() && seatInfos_zoom[getpilotseatid()] != null ? seatInfos_zoom[getpilotseatid()].pos: seatInfos[getpilotseatid()].pos;
	}
	
	public int getpilotseatid(){
		return plane.getpilotseatid();
	}
	
	
	public void setinfo(Prefab_Vehicle_Base info) {
		super.setinfo(info);
		this.planeInfo = (Prefab_Vehicle_Plane) info;
	}
}
