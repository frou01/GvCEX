package hmvehicle.entity.parts.logics;

import cpw.mods.fml.client.FMLClientHandler;
import hmvehicle.Utils;
import hmvehicle.HMVPacketHandler;
import hmvehicle.entity.parts.*;
import hmvehicle.packets.*;
import hmvehicle.entity.EntityCameraDummy;
import hmvehicle.entity.parts.turrets.TurretObj;
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

import static hmvehicle.Utils.*;
import static hmvehicle.HMVehicle.proxy_HMVehicle;
import static java.lang.Math.*;
import static java.lang.Math.toRadians;
import static net.minecraft.util.MathHelper.wrapAngleTo180_double;
import static net.minecraft.util.MathHelper.wrapAngleTo180_float;

public class PlaneBaseLogic implements IbaseLogic,IneedMouseTrack,IControlable,MultiRiderLogics {
	public int rocket = 2;
	public float health = 150;
	public float maxhealth = 150;
	public float mousex;
	public float mousey;
	public float yawladder;
	public float yawspeed = 0.05f;
	public float yawspeed_taxing = 0.1f;
	public float rollladder;
	public float rollspeed = 0.12f;
	public float pitchladder;
	public float pitchspeed = 0.15f;
	public float forced_rudder_effect = 0;
	
	public float pitchsighwidthmax = 5;
	public float yawsightwidthmax = 10;
	public float pitchsighwidthmin = -5;
	public float yawsightwidthmin = -10;
	
	public float maxDive = 75;
	public float startDive = 40;
	public float maxClimb = -60;
	public float minALT=20;
	public float cruiseALT=40;
	public boolean climbYawDir=new Random().nextBoolean();
	public boolean throttledown_onDive = false;
	public boolean Dive_bombing = false;
	public boolean Torpedo_bomber = false;
	public boolean sholdUseMain_ToG = true;
	public boolean sholdUseMain_ToA = false;
	public boolean type_F_Plane_T_Heli = false;
	
	public float slipresist = 0.1f;
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
	public float stability2 = 0.7f;
	public double rotmotion_reduceSpeed = 0.2d;
	public double rotmotion_reduceSpeedRoll = 0.01d;
	public float forced_rotmotion_reduceSpeed = 0;
//	public float angletime;
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
	public float brakedragfactor = 0.05f;
	public int mode = 0;//0:attack 1:leave 2:follow player 3:go to home
	public int soundtick = 0;
	public String  soundname = "gvcmob:gvcmob.plane";
	public float  soundpitch = 2;
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
	public Vector3d unitThrottle = new Vector3d(0,0,1);
	public EntityCameraDummy camera;
	public double[] camerapos = new double[]{0,2.5-0.21,-3.6};
	public double[] camerapos_zoom = new double[]{0,2.5-0.21,-3.6};
	public double[] rotcenter = new double[]{0,2.5,0};
	boolean isinit;
	
	public int gearprogress;
	public int flaplevel;
	
	
	public TurretObj mainTurret;
	public TurretObj subTurret;
	
	public TurretObj[] turrets;
	public TurretObj[] mainTurrets;
	public TurretObj[] subTurrets;
	
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
	public int outSightCntMax = 600;
	
	public boolean autoflap = false;
	public boolean needStartSound = true;
	
	
	public boolean displayModernHud = false;
	
	
	
	
	public boolean serverspace = false;
	public boolean serverw = false;
	public boolean servers = false;
	public boolean servera = false;
	public boolean serverd = false;
	public boolean serverf = false;
	public boolean serverx = false;
	
	Random rand = new Random();
	public Entity[] riddenByEntities = new Entity[1];
	public SeatInfo[] riddenByEntitiesInfo = {new SeatInfo()};
	public SeatInfo[] riddenByEntitiesInfo_zoom = {new SeatInfo()};
	
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
		riddenByEntitiesInfo[0] = new SeatInfo();
		riddenByEntitiesInfo[0].pos[0] = 0;
		riddenByEntitiesInfo[0].pos[1] = 2.3;
		riddenByEntitiesInfo[0].pos[2] = 0;
		worldObj = world;
		planebody = entity;
		plane = (Iplane) entity;
		
		camera = new EntityCameraDummy(this.worldObj);
	}
	public void onUpdate(){
		((ModifiedBoundingBox) planebody.boundingBox).rot.inverse(this.bodyRot);
		((ModifiedBoundingBox) planebody.boundingBox).update(planebody.posX, planebody.posY, planebody.posZ);
		int cnt = 0;
		for (Entity entity : riddenByEntities) {
			if (entity != null) {
				if((planebody.worldObj.isRemote && entity instanceof EntityPlayer && entity.isSneaking())) {
//					riddenByEntities[cnt] = null;
//					entity.ridingEntity = null;
					HMVPacketHandler.INSTANCE.sendToServer(new HMVPacketDisMountEntity(planebody.getEntityId(),entity.getEntityId()));
				}else
				if(!planebody.worldObj.isRemote && entity.isDead){
//						System.out.println("debug");
					riddenByEntities[cnt] = null;
					entity.ridingEntity = null;
					HMVPacketHandler.INSTANCE.sendToAll(new HMVPacketPickNewEntity(planebody.getEntityId(),riddenByEntities));
				}else {
					entity.ridingEntity = planebody;
				}
			}
			cnt ++;
		}
		planebody.riddenByEntity = riddenByEntities[0];
		
		
		prevmotionVec.set(planebody.motionX, planebody.motionY, planebody.motionZ);
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
			if(planebody.worldObj.isRemote)proxy_HMVehicle.playsoundasVehicle(1024,planebody);
		}
		needStartSound = true;
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
			if(riddenByEntities[plane.getpilotseatid()] == proxy_HMVehicle.getEntityPlayerInstance()) {
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
				riddenByEntities[plane.getpilotseatid()].isDead = false;
				riddenByEntities[0].motionX = planebody.motionX;
				riddenByEntities[0].motionY = planebody.motionY;
				riddenByEntities[0].motionZ = planebody.motionZ;
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
			}
			HMVPacketHandler.INSTANCE.sendToAll(new HMVPakcetVehicleState(planebody.getEntityId(),bodyRot, throttle,trigger1,trigger2));
//			turret(mainwingvector,tailwingvector,bodyvector);
			
			double[] xyz = Utils.eulerfrommatrix(Utils.matrixfromQuat(bodyRot));
			bodyrotationPitch = (float) toDegrees(xyz[0]);
			if(!Double.isNaN(xyz[1])){
				bodyrotationYaw = (float) toDegrees(xyz[1]);
			}
			bodyrotationRoll = (float) toDegrees(xyz[2]);
			
			
			planebody.rotationYaw = bodyrotationYaw;
			planebody.rotationPitch = bodyrotationPitch;
//			if(this.throttle >= 0.2){
//				if(planebody.getEntityData().getFloat("GunshotLevel")<4) soundedentity.add(planebody);
//				planebody.getEntityData().setFloat("GunshotLevel",4);
//				planebody.playSound(soundname, 4F, 0.8f + throttle /throttle_Max * 0.4f);
//			}
		}
		control(bodyvector);
		if(mainTurret != null){
			if(riddenByEntities[plane.getpilotseatid()] != null && riddenByEntities[plane.getpilotseatid()] != null){
				mainTurret.currentEntity = riddenByEntities[plane.getpilotseatid()];
			}else
				mainTurret.currentEntity = this.planebody;
			if(!(planebody instanceof Hasmode && ((Hasmode) planebody).standalone()) && (riddenByEntities[plane.getpilotseatid()] != null &&
					                                                                             riddenByEntities[plane.getpilotseatid()] == null))mainTurret.freezTrigger();
			mainTurret.update(bodyRot,new Vector3d(planebody.posX,planebody.posY,-planebody.posZ));
		}
		if(subTurret != null){
			if(riddenByEntities[plane.getpilotseatid()] != null && riddenByEntities[plane.getpilotseatid()] != null){
				subTurret.currentEntity = riddenByEntities[plane.getpilotseatid()];
			}else
				subTurret.currentEntity = this.planebody;
			if(!(planebody instanceof Hasmode && ((Hasmode) planebody).standalone()) && (riddenByEntities[plane.getpilotseatid()] != null &&
					                                                                             riddenByEntities[plane.getpilotseatid()] == null))subTurret.freezTrigger();
			subTurret.update(bodyRot,new Vector3d(planebody.posX,planebody.posY,-planebody.posZ));
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
		
		if(planebody instanceof IMultiTurretVehicle){
			HMVPacketHandler.INSTANCE.sendToAll(new HMVPakcetVehicleTurretSync(planebody.getEntityId(), (IMultiTurretVehicle) planebody));
		}
		
		riderPosUpdate();
	}
	public void riderPosUpdate(){
		int cnt = 0;
		Vector3d thispos = new Vector3d(planebody.posX,
				                               planebody.posY,
				                               planebody.posZ);
//		System.out.println("thispos  " + thispos);
		for (Entity entity : riddenByEntities) {
			if (entity != null) {
//				if(worldObj.isRemote)System.out.println("debug CL Pre" + cnt + " , " + entity);
//				else System.out.println("debug SV Pre" + cnt + " , " + entity);
				Vector3d tempplayerPos = new Vector3d(proxy_HMVehicle.iszooming() && riddenByEntitiesInfo_zoom.length > cnt && riddenByEntitiesInfo_zoom[cnt] != null?riddenByEntitiesInfo_zoom[cnt].pos:riddenByEntitiesInfo[cnt].pos);
				tempplayerPos.sub(new Vector3d(rotcenter));
				Vector3d temp = transformVecByQuat(tempplayerPos, bodyRot);
				transformVecforMinecraft(temp);
				temp.add(new Vector3d(rotcenter));
//				System.out.println("" + temp);
				temp.add(thispos);
//			temp.add(playeroffsetter);
//			System.out.println(temp);
				entity.setPosition(temp.x,
						temp.y - ((planebody.worldObj.isRemote && entity instanceof EntityPlayer) ? 0 : entity.getEyeHeight()),
						temp.z);
				entity.posX = temp.x;
				entity.posY = temp.y - ((planebody.worldObj.isRemote && entity instanceof EntityPlayer) ? 0 : entity.getEyeHeight());
				entity.posZ = temp.z;
				entity.motionX = planebody.motionX;
				entity.motionY = planebody.motionY;
				entity.motionZ = planebody.motionZ;
				TurretObj seatGun = riddenByEntitiesInfo[cnt].gun;
				if(seatGun != null) {
					seatGun.currentEntity = entity;
					seatGun.update(bodyRot, new Vector3d(planebody.posX, planebody.posY, -planebody.posZ));
					if(entity instanceof EntityLiving && ((EntityLiving) entity).getAttackTarget() != null){
						seatGun.aimToEntity(((EntityLiving) entity).getAttackTarget());
					}else {
						seatGun.aimtoAngle(entity.getRotationYawHead(), entity.rotationPitch);
					}
				}
				if(worldObj.isRemote){
					if(entity == proxy_HMVehicle.getEntityPlayerInstance()){
						
						if(seatGun != null)HMVPacketHandler.INSTANCE.sendToServer(new HMVPacketTriggerSeatGun(proxy_HMVehicle.leftclick(), proxy_HMVehicle.rightclick(), planebody.getEntityId(), cnt));
						if(proxy_HMVehicle.yclick())HMVPacketHandler.INSTANCE.sendToServer(new HMVPacketChangeSeat(planebody.getEntityId(),cnt,true));
						else
						if(proxy_HMVehicle.hclick())HMVPacketHandler.INSTANCE.sendToServer(new HMVPacketChangeSeat(planebody.getEntityId(),cnt,false));
					}
				}else {
					if(seatGun != null) {
						if (entity instanceof EntityLiving) {
							if (planebody instanceof EntityLiving && ((EntityLiving) planebody).getAttackTarget() != null) {
								((EntityLiving) entity).getLookHelper().setLookPositionWithEntity(((EntityLiving) planebody).getAttackTarget(),180,180);
								((EntityLiving) entity).setAttackTarget(((EntityLiving) planebody).getAttackTarget());
								riddenByEntitiesInfo[cnt].gunTrigger1 = entity.getEntityData().getBoolean("HMGisUsingItem") || ((EntityLiving) entity).canEntityBeSeen(((EntityLiving) planebody).getAttackTarget());
							}
						}
						if (seatGun.readyaim) {
							if (riddenByEntitiesInfo[cnt].gunTrigger1) {
								seatGun.fireall();
							}
						}
//					System.out.println("" + riddenByEntitiesInfo[cnt].gunTrigger1);
						riddenByEntitiesInfo[cnt].gunTrigger1 = false;
					}
				}
				entity.ridingEntity = planebody;
				if(entity instanceof IhasprevRidingEntity)((IhasprevRidingEntity) entity).setprevRidingEntity(planebody);
//				if (!planebody.worldObj.isRemote && entity instanceof EntityPlayer) entity.ridingEntity = null;
//				if(worldObj.isRemote)System.out.println("debug CL Post" + cnt + " , " + entity);
//				else System.out.println("debug SV Post" + cnt + " , " + entity);
			}
			cnt++;
		}
		planebody.riddenByEntity = null;
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
		if(worldObj.isRemote && !plane.standalone() && ispilot(proxy_HMVehicle.getEntityPlayerInstance())){
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
			HMVPacketHandler.INSTANCE.sendToServer(new HMVMMessageKeyPressed(i, planebody.getEntityId()));
			if(!FMLClientHandler.instance().getClient().isGamePaused()) {
//				if(proxy_HMVehicle.hasStick()){
//					mousex = proxy_HMVehicle.getXaxis() * 16;
//					yawladder = proxy_HMVehicle.getZaxis() * 5;
//					mousey = -proxy_HMVehicle.getYaxis() * 16;
//				}else
					{
					mousex += ((float)Mouse.getDX())/4;
					mousey += ((float)Mouse.getDY())/4;
				}
				if(proxy_HMVehicle.pitchUp())mousey =16;
				if(proxy_HMVehicle.pitchDown())mousey =-16;
				if(proxy_HMVehicle.rollRight())mousex =16;
				if(proxy_HMVehicle.rollLeft())mousex =-16;
				HMVPacketHandler.INSTANCE.sendToServer(new HMVPacketMouseD(mousex,
						                                                          mousey,yawladder,planebody.getEntityId()));
			}
		}
//		if(!worldObj.isRemote)System.out.println("" + yawladder);
		rollladder = (abs(mousex) > 16 ? (mousex > 0 ? 16f : -16f) : mousex)/8;
		pitchladder = (abs(mousey) > 16 ? (mousey > 0 ? 16f : -16f) : mousey)/8;
		
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
			Vector3d motionvec = new Vector3d(planebody.motionX, planebody.motionY, planebody.motionZ);
			if (motionvec.length() > 0.001) {
				double cof = (forced_rudder_effect * throttle - ((1-forced_rudder_effect) * angle_cos(bodyvector, motionvec) * motionvec.length()));
				if (abs(pitchladder) > 0.0001) {
//				Vector3d axisx = Utils.transformVecByQuat(new Vector3d(unitX), bodyRot);
					AxisAngle4d axisxangled = new AxisAngle4d(unitX, toRadians(-pitchladder/4 * cof * pitchspeed));
					rotationmotion = Utils.quatRotateAxis(rotationmotion, axisxangled);
				}
				float tempYaw = yawladder;
				if(abs(tempYaw) > 5){
					tempYaw *= 5/abs(tempYaw);
				}
				if (abs(yawladder) > 0.001) {
					AxisAngle4d axisyangled;
					if (planebody.onGround && motionvec.length() < 0.2) {
//					Vector3d axisy = Utils.transformVecByQuat(new Vector3d(unitY), bodyRot);
						axisyangled = new AxisAngle4d(unitY, toRadians(yawladder/5 * cof * yawspeed_taxing));
					} else {
//					Vector3d axisy = Utils.transformVecByQuat(new Vector3d(unitY), bodyRot);
						axisyangled = new AxisAngle4d(unitY, toRadians(yawladder/5 * cof * yawspeed));
					}
					rotationmotion = Utils.quatRotateAxis(rotationmotion, axisyangled);
				}
				if (abs(rollladder) > 0.0001) {
//				Vector3d axisz = Utils.transformVecByQuat(new Vector3d(unitZ), bodyRot);
					AxisAngle4d axiszangled = new AxisAngle4d(unitZ, toRadians(rollladder/4 * cof * rollspeed));
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
			if (riddenByEntities[0] != null) {
				riddenByEntities[0].rotationYaw = bodyrotationYaw;
				riddenByEntities[0].prevRotationYaw = prevbodyrotationYaw;
			}
		}
		
		if(!plane.standalone()) {
			if (health > 0 && serverw) {
				throttle += 0.05;
			}
			if (health <= 0) {
				throttle -= 0.05;
			}
			if (servera) {
				yawladder--;
			}
			if (serverd) {
				yawladder++;
			}
			if (servers) {
				throttle -= 0.05;
			}
		}
		if(abs(yawladder) < 0.001)yawladder = 0;
		yawladder *= 0.8;
		
	}
	void autocontrol(Vector3d bodyvector){
		if(health>0){
			bodyvector = new Vector3d(bodyvector);//copy
			bodyvector.scale(-1);
			int genY = this.worldObj.getHeightValue((int) planebody.posX, (int) planebody.posZ);//target alt
			
			float alt = (float) (planebody.posY - genY);
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
				Vector3d currentCannonpos = currentTurret.getCannonPos();
				double[] elevations = CalculateGunElevationAngle(currentCannonpos.x,currentCannonpos.y,currentCannonpos.z, target, (float) currentTurret.gravity, currentTurret.speed + (float) sqrt(planespeed));
				float targetpitch = (float) -elevations[0];
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
				Vector3d motionvec = new Vector3d(planebody.motionX, planebody.motionY, planebody.motionZ);
				if(!rising_after_Attack && T_StartDive_F_FlyToStartDivePos){
//					System.out.println("" + targetpitch);
					boolean insight = elevations[2] != -1;
					boolean istarget_onGround = ((target instanceof ITank && ((ITank) target).ishittingWater())||target.isInWater() || target.onGround || (target.ridingEntity != null && target.ridingEntity.onGround));
					
					if(type_F_Plane_T_Heli){
						if((motionvec.y<-0.1 || alt<cruiseALT))
							throttle += 0.05;
						else
							throttle -= 0.05;
						if(alt<cruiseALT)T_StartDive_F_FlyToStartDivePos = false;
					}else
					if(T_useMain_F_useSub && Dive_bombing && istarget_onGround){
						if(toTargetPitch<startDive){
							targetpitch = maxClimb;
							throttle += 0.05;
							insight = false;
						}else {
							if(targetpitch < startDive){
								targetpitch = startDive/2;
								throttle += 0.05;
								insight = false;
							}else {
								throttle += 0.05;
							}
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
					float AngulardifferencePitch = targetpitch - bodyrotationPitch;
					if(targetpitch < maxClimb)targetpitch = maxClimb;
					if(type_F_Plane_T_Heli && targetpitch > maxDive){
						targetpitch = maxDive;
						T_StartDive_F_FlyToStartDivePos = false;
					}
//					if(targetpitch < 0 && istarget_onGround)targetpitch = 0;
					if(!istarget_onGround){
						handle_Yaw(AngulardifferenceYaw,targetpitch,alt);
						pitchHandle_considerYaw(targetpitch,AngulardifferenceYaw>0);
					}else {
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
					insight &= AngulardifferenceYaw > yawsightwidthmin && AngulardifferenceYaw < yawsightwidthmax && AngulardifferencePitch > pitchsighwidthmin &&AngulardifferencePitch< pitchsighwidthmax;
					if(T_useMain_F_useSub && Torpedo_bomber){
						insight = planebody.getDistanceSqToEntity(target) < 1600;
						if(insight){
							rising_after_Attack = true;
							T_StartDive_F_FlyToStartDivePos = false;
						}
					}
					if(!useMain_withSub && mainTurret != null && subTurret != null){
						if(sholdUseMain_ToG ) {
							if (istarget_onGround) {
								T_useMain_F_useSub = !mainTurret.isreloading();
							}else {
								T_useMain_F_useSub = false;
							}
						}else if(sholdUseMain_ToA){
							if(!istarget_onGround) {
								T_useMain_F_useSub = !mainTurret.isreloading();
							}else {
								T_useMain_F_useSub = false;
							}
						}
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
					if(type_F_Plane_T_Heli){
						if((motionvec.y<-0.1 || alt<cruiseALT))
							throttle += 0.05;
						else
							throttle -= 0.05;
						if(alt<cruiseALT)T_StartDive_F_FlyToStartDivePos = false;
					}else
					throttle+= 0.05;
					if(!target.onGround)T_StartDive_F_FlyToStartDivePos = true;
					if(toTargetPitch<(Dive_bombing ? startDive/2:startDive))T_StartDive_F_FlyToStartDivePos = true;
					if(toTargetPitch<0 && alt > minALT)rising_after_Attack = false;
					if(alt > cruiseALT)rising_after_Attack = false;
					if(!target.onGround && outSightCnt>0){
						rising_after_Attack = true;
						outSightCnt-=10;
					}
					
					if(rand.nextInt(500) == 0)climbYawDir = !climbYawDir;
					if(type_F_Plane_T_Heli){
						handle_withPitch(0,15,cruiseALT);
					}else
					if(alt>cruiseALT + 40)handle_withPitch(!T_StartDive_F_FlyToStartDivePos ? 0 :climbYawDir ? 12 : -12,2,cruiseALT);
					else{
						handle_withPitch(!T_StartDive_F_FlyToStartDivePos ? 0 :climbYawDir ? 12 : -12,maxClimb,cruiseALT);
						rollHandle(0);
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
				switch (((Hasmode) planebody).getMobMode()) {
					case 0://wait
					{
						Vector3d motionvec = new Vector3d(planebody.motionX, planebody.motionY, planebody.motionZ);
						if(rand.nextInt(500) == 0)climbYawDir = !climbYawDir;
						if(type_F_Plane_T_Heli){
							handle_withPitch(0,15,cruiseALT);
						}else if(alt<cruiseALT){
							if(rand.nextInt(500) == 0)climbYawDir = !climbYawDir;
							handle_withPitch(climbYawDir ? 12 : -12,maxClimb,cruiseALT);
						}
						else if(alt>cruiseALT + 40) handle_withPitch(climbYawDir ? 12 : -12,2,cruiseALT);
						else handle_withPitch(climbYawDir ? 12 : -12,0,cruiseALT);
						
						if (type_F_Plane_T_Heli && (motionvec.y < -0.1 || alt < cruiseALT)) {
							throttle += 0.05;
						} else if (!type_F_Plane_T_Heli && (motionvec.y < bodyvector.y || alt < cruiseALT)) {
							throttle += 0.05;
						} else {
							if (throttle > throttle_Max * 0.8) throttle -= 0.05;
						}
						break;
					}
					case 1:
					case 2://follow
					{
						Vector3d motionvec = new Vector3d(planebody.motionX, planebody.motionY, planebody.motionZ);
						if (motionvec.length() > 0.1) motionvec.normalize();
						if (type_F_Plane_T_Heli && (motionvec.y < -0.1 || alt < cruiseALT)) {
							throttle += 0.05;
						} else if (!type_F_Plane_T_Heli && (motionvec.y < bodyvector.y || alt < cruiseALT)) {
							throttle += 0.05;
						} else {
							if (throttle > throttle_Max * 0.8) throttle -= 0.05;
						}
						double[] targetpos = ((Hasmode) planebody).getTargetpos();
						if(targetpos == null)targetpos = new double[]{planebody.posX,planebody.posY,planebody.posZ};
						Vector3d courseVec = new Vector3d(targetpos);
						courseVec.sub(new Vector3d(planebody.posX, courseVec.y, planebody.posZ));
//						double angletocourse = toDegrees(bodyvector.angle(courseVec));
//						System.out.println("" + angletocourse);
						float targetyaw = wrapAngleTo180_float(-(float) toDegrees(atan2(courseVec.x, courseVec.z)));
						double AngulardifferenceYaw = targetyaw - this.bodyrotationYaw;
						AngulardifferenceYaw = wrapAngleTo180_double(AngulardifferenceYaw);
						if(alt < minALT){
							rollHandle(0);
							pitchHandle(!type_F_Plane_T_Heli ? maxClimb : 0);
						}
						else {
							float pitch = alt < cruiseALT ? maxClimb: ((alt > cruiseALT + 60) ? 5 : 0);
							if (type_F_Plane_T_Heli) {
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
			if(!type_F_Plane_T_Heli) {
				if (abs(AngulardifferenceYaw) > 10) {
					if (alt > cruiseALT) rollHandle(-maxbank * maxRollCof);
					else if (alt > minALT) rollHandle(-maxbank * maxRollCof * (alt) / (cruiseALT));
					else {
						rollHandle(0);
					}
				} else {
					rollHandle(0);
				}
			}else {
				rollHandle(0);
			}
			
			if(!type_F_Plane_T_Heli) {
				if (abs(bodyrotationRoll) < 45) {
					yawladder -= min(abs(AngulardifferenceYaw), 10) / 50;//a
				} else if (abs(bodyrotationRoll) > 135) {
					yawladder += min(abs(AngulardifferenceYaw), 10) / 50;//d
				}
			}else if(canUseLadder){
				if (abs(bodyrotationRoll) < 60) {
					yawladder -= min(abs(AngulardifferenceYaw)/5, 16);//a
				} else if (abs(bodyrotationRoll) > 120) {
					yawladder += min(abs(AngulardifferenceYaw)/5, 16);//d
				}
			}
			if(!type_F_Plane_T_Heli) {
				if (abs(bodyrotationRoll) > 45 && abs(bodyrotationRoll) < 135 && canUseElevator) {
					if (bodyrotationRoll < 0) {
						//左に傾いている
						mousey -= abs(AngulardifferenceYaw) / pitchspeed / 3;//↓
					} else if (bodyrotationRoll > 0) {
						//右に傾いている
						mousey += abs(AngulardifferenceYaw) / pitchspeed / 3;//↑
					}
				}
			}
		}else if(AngulardifferenceYaw > 0){
			//turn Right
			
			if(!type_F_Plane_T_Heli) {
				if (abs(AngulardifferenceYaw) > 10) {
					if (alt > cruiseALT) rollHandle(maxbank * maxRollCof);
					else if (alt > minALT) rollHandle(maxbank * maxRollCof * (alt) / (cruiseALT));
					else {
						rollHandle(0);
					}
				} else {
					rollHandle(0);
				}
			} else {
				rollHandle(0);
			}
			
			if(!type_F_Plane_T_Heli) {
				if (abs(bodyrotationRoll) < 45) {
					yawladder += min(abs(AngulardifferenceYaw), 10) / 50;//d
				} else if (abs(bodyrotationRoll) > 135) {
					yawladder -= min(abs(AngulardifferenceYaw), 10) / 50;//a
				}
			}else if(canUseLadder){
				if (abs(bodyrotationRoll) < 60) {
					yawladder += min(abs(AngulardifferenceYaw)/5, 16);//d
				} else if (abs(bodyrotationRoll) > 120) {
					yawladder -= min(abs(AngulardifferenceYaw)/5, 16);//a
				}
			}
			if(!type_F_Plane_T_Heli) {
				if (abs(bodyrotationRoll) > 45 && abs(bodyrotationRoll) < 135 && canUseElevator) {
					if (bodyrotationRoll < 0) {
						//左に傾いている
						mousey += abs(AngulardifferenceYaw) / pitchspeed / 3;//↓
					} else if (bodyrotationRoll > 0) {
						//右に傾いている
						mousey -= abs(AngulardifferenceYaw) / pitchspeed / 3;//↑
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
		
		double sensiv = type_F_Plane_T_Heli ? 0.05:0.25;
		if(type_F_Plane_T_Heli) {
			if(targetPitch<maxClimb)targetPitch = maxClimb;
			if(targetPitch>maxDive)targetPitch = maxDive;
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
				mousey += abs(AngulardifferencePitch)/pitchspeed * sensiv;
			}else if(abs(bodyrotationRoll)>135){
				mousey -= abs(AngulardifferencePitch)/pitchspeed * sensiv;
			}
			if(abs(bodyrotationRoll) > 45 && abs(bodyrotationRoll) < 135) {
				if (bodyrotationRoll < 0) {
					//左に傾いている
					yawladder+=min(abs(AngulardifferencePitch),10)/40 * sensiv;
				} else if (bodyrotationRoll > 0) {
					//右に傾いている
					yawladder-=min(abs(AngulardifferencePitch),10)/40 * sensiv;
				}
			}
		}else if (AngulardifferencePitch > 0){
			//機首上げ
			if(abs(bodyrotationRoll)<45) {
				mousey -= abs(AngulardifferencePitch)/pitchspeed/2 * sensiv;;
			}else if(abs(bodyrotationRoll)>135){
				mousey += abs(AngulardifferencePitch)/pitchspeed/2 * sensiv;;
			}
			if(abs(bodyrotationRoll) > 45 && abs(bodyrotationRoll) < 135) {
				if (bodyrotationRoll < 0) {
					//左に傾いている
					yawladder-=min(abs(AngulardifferencePitch),10)/40 * sensiv;;
				} else if (bodyrotationRoll > 0) {
					//右に傾いている
					yawladder+=min(abs(AngulardifferencePitch),10)/40 * sensiv;;
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
		double sensiv = type_F_Plane_T_Heli ? 0.05:1;
		if(type_F_Plane_T_Heli) {
			if(targetPitch<maxClimb)targetPitch = maxClimb;
			if(targetPitch>maxDive)targetPitch = maxDive;
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
					mousey += abs(AngulardifferencePitch) / pitchspeed * sensiv;
				} else if (abs(bodyrotationRoll) > 135) {
					mousey -= abs(AngulardifferencePitch) / pitchspeed * sensiv;
				}
			}
			if(abs(bodyrotationRoll) > 15 && abs(bodyrotationRoll) < 165) {
				if (bodyrotationRoll < 0 && !yawDir) {
					//左に傾いている
//					System.out.println("debug");
					yawladder-=min(abs(AngulardifferencePitch),10)/10 * sensiv;
				} else if (bodyrotationRoll > 0 && yawDir) {
					//右に傾いている
//					System.out.println("debug");
					yawladder+=min(abs(AngulardifferencePitch),10)/10 * sensiv;
				}
			}
		}else if (AngulardifferencePitch > 0){
			//機首上げ
			if(allow_PitchUp) {
				if (abs(bodyrotationRoll) < 45) {
					mousey -= abs(AngulardifferencePitch) / pitchspeed / 2 * sensiv;
					;
				} else if (abs(bodyrotationRoll) > 135) {
					mousey += abs(AngulardifferencePitch) / pitchspeed / 2 * sensiv;
					;
				}
			}
			if(abs(bodyrotationRoll) > 15 && abs(bodyrotationRoll) < 165) {
				if (bodyrotationRoll < 0 && yawDir) {
					//左に傾いている
//					System.out.println("debug");
					yawladder+=min(abs(AngulardifferencePitch),10)/10 * sensiv;
				} else if (bodyrotationRoll > 0 && !yawDir) {
					//右に傾いている
//					System.out.println("debug");
					yawladder-=min(abs(AngulardifferencePitch),10)/10 * sensiv;
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
				mainTurret.currentEntity.motionX = this.planebody.motionX;
				mainTurret.currentEntity.motionY = this.planebody.motionY;
				mainTurret.currentEntity.motionZ = this.planebody.motionZ;
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
				subTurret.currentEntity.motionX = this.planebody.motionX;
				subTurret.currentEntity.motionY = this.planebody.motionY;
				subTurret.currentEntity.motionZ = this.planebody.motionZ;
				subTurret.fireall();
			}
			trigger2 = false;
		}
	}
	
	void motionUpdate(Vector3d mainwingvector,Vector3d tailwingvector,Vector3d bodyvector){
		
		if(!worldObj.isRemote)
		{
			Vector3d motionvec = new Vector3d(planebody.motionX, planebody.motionY, planebody.motionZ);
			
			Vector3d windvec = new Vector3d(motionvec);
			if (planebody.onGround && windvec.y < 0) windvec.y = 0;
			if (windvec.length() > 0.000001 && stability != 0) {
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
					if (planebody.onGround && windvec.y < 0) windvec.y = 0;
					Vector3d bodyVec_front = new Vector3d(bodyvector);
					bodyVec_front.scale(-1);
					double sin = angle_cos(bodyVec_front, motionvec);
					sin = sqrt(1 - sin * sin);
//					System.out.println(sin);
					AxisAngle4d axisxangledstall = new AxisAngle4d(axisstall, motionvec.length() * sin / stability);
					rotationmotion = Utils.quatRotateAxis(rotationmotion, axisxangledstall);
				}
			}
			
			windvec = new Vector3d(motionvec);
			if (planebody.onGround && windvec.y < 0) windvec.y = 0;
			
			rotationmotion.normalize();
			bodyRot.mul(rotationmotion);
			if (!planebody.onGround) {
				double cos = forced_rotmotion_reduceSpeed - ((1-forced_rotmotion_reduceSpeed) * angle_cos(bodyvector, motionvec)) * rotmotion_reduceSpeed;
				if (Double.isNaN(cos)) cos = rotmotion_reduceSpeed;
				if (planebody.onGround || windvec.length() < 1)
					rotationmotion.interpolate(new Quat4d(0, 0, 0, 1), cos);
				else
					rotationmotion.interpolate(new Quat4d(0, 0, 0, 1), cos);
				
				double[] xyz = Utils.eulerfrommatrix(Utils.matrixfromQuat(rotationmotion));
				AxisAngle4d axiszangled = new AxisAngle4d(unitZ, -xyz[2] * rotmotion_reduceSpeedRoll);
				rotationmotion = Utils.quatRotateAxis(rotationmotion, axiszangled);
			} else {
				rotationmotion.interpolate(new Quat4d(0, 0, 0, 1), forced_rotmotion_reduceSpeed + (1-forced_rotmotion_reduceSpeed) * rotmotion_reduceSpeed * 1);
			}
			
			
			tailwingvector = Utils.transformVecByQuat(new Vector3d(unitY), bodyRot);
			bodyvector = Utils.transformVecByQuat(new Vector3d(unitZ), bodyRot);
			mainwingvector = Utils.transformVecByQuat(new Vector3d(unitX), bodyRot);
			
			Utils.transformVecforMinecraft(tailwingvector);
			Utils.transformVecforMinecraft(bodyvector);
			Utils.transformVecforMinecraft(mainwingvector);
			
			motionvec = new Vector3d(planebody.motionX, planebody.motionY, planebody.motionZ);
			Vector3d powerVec = new Vector3d(unitThrottle);
			powerVec = transformVecByQuat(powerVec,bodyRot);
			transformVecforMinecraft(powerVec);
			if (!(Double.isNaN(bodyvector.x) || Double.isNaN(bodyvector.y) || Double.isNaN(bodyvector.z))) {
				powerVec.scale(-throttle * speedfactor);
				motionvec.add(powerVec);
				if (throttle > 9.5) {
					powerVec.normalize();
					powerVec.scale(-throttle * speedfactor_af);
					motionvec.add(powerVec);
				}
			}
			if (motionvec.length() > 0.01) {
				motionvec.y -= gravity;
				double cos;
				{
					cos = angle_cos(bodyvector, motionvec);
					Vector3d tailwingvectorForFloating = new Vector3d(tailwingvector);
					tailwingvectorForFloating.scale(motionvec.length() * motionvec.length() * -cos * (liftfactor + flaplevel * flapliftfactor));
//			System.out.println("debug" + cos);
//					motionvec.x += tailwingvectorForFloating.x / slipresist;
					motionvec.y += abs(tailwingvectorForFloating.y);
//					motionvec.z += tailwingvectorForFloating.z / slipresist;
				}
//				if (planebody.onGround && motionvec.y < 0) motionvec.y = 0;
				if (motionvec.length() > 0.000001 && stability2 != 0) {
					Vector3d liftVec1 = new Vector3d();
					liftVec1.cross(mainwingvector, motionvec);
					liftVec1.normalize();
					if (!(Double.isNaN(liftVec1.x) || Double.isNaN(liftVec1.y) || Double.isNaN(liftVec1.z))) {
						double sin = angle_cos(liftVec1, bodyvector);
						liftVec1.set(tailwingvector);
						liftVec1.normalize();
						liftVec1.scale(motionvec.length() * sin * stability2);
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
						resistskidding.scale(-motionvec.length() * sin * slipresist);
						if (motionvec.length() < 0.8) resistskidding.scale(motionvec.length() / 0.8);
						if (!Double.isNaN(sin)) {
//							System.out.println(sin);
							motionvec.add(resistskidding);
						}
					}
				}
				if (motionvec.length() > 0.000001) {
					Vector3d airDrug = new Vector3d(motionvec);
					airDrug.scale(motionvec.length() * motionvec.length() * (dragfactor + gearprogress * geardragfactor + flaplevel * flapdragfactor) + (planebody.onGround && proxy_HMVehicle.xclick() ? motionvec.length() * 0.6 : 0) + (proxy_HMVehicle.xclick() ? motionvec.length() * brakedragfactor : 0));
					motionvec.sub(airDrug);
				} else {
					motionvec.scale(0);
				}
			} else {
				motionvec.scale(0);
				motionvec.y -= gravity;
			}
			if (!Double.isNaN(motionvec.x) && !Double.isNaN(motionvec.y) && !Double.isNaN(motionvec.z)) {
				planebody.motionX = abs(motionvec.x) > 0.0000001 ? motionvec.x : 0;
				planebody.motionY = abs(motionvec.y) > 0.0000001 ? motionvec.y : 0;
				planebody.motionZ = abs(motionvec.z) > 0.0000001 ? motionvec.z : 0;
			}
			
			motionvec = new Vector3d(planebody.motionX, planebody.motionY, planebody.motionZ);
			HMVPacketHandler.INSTANCE.sendToAll(new HMVPakcetPlaneState(planebody.getEntityId(),bodyRot,rotationmotion,motionvec,throttle));
			planebody.moveEntity(planebody.motionX, planebody.motionY, planebody.motionZ);
			
			double backmotionX = planebody.motionX;
			double backmotionY = planebody.motionY;
			double backmotionZ = planebody.motionZ;
			motionvec.normalize();
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
		}else {
			planebody.moveEntity(planebody.motionX, planebody.motionY, planebody.motionZ);
		}
		{
			if (planebody.onGround) {
				Vector3d axisx = new Vector3d(-cos(toRadians(bodyrotationYaw)), 0, sin(toRadians(bodyrotationYaw)));
				if (abs(bodyrotationPitch - onground_pitch) > 15) {
					planebody.attackEntityFrom(DamageSource.fall, (float) (30));
				}
				AxisAngle4d axisxangled = new AxisAngle4d(axisx, toRadians((onground_pitch - bodyrotationPitch) / 10));
				bodyRot = Utils.quatRotateAxis(bodyRot, axisxangled);
				
				axisx = Utils.transformVecByQuat(new Vector3d(unitZ), bodyRot);
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
				bodyRot = Utils.quatRotateAxis(bodyRot, axisxangled);
				
				gearprogress = 100;
			} else {
				if (throttle > throttle_gearDown) {
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
			Vector3d motionvec = new Vector3d(planebody.motionX, planebody.motionY, planebody.motionZ);
			if ((motionvec.y < -bodyvector.y && autoflap) || planebody.onGround || serverf || proxy_HMVehicle.fclick()) {
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
		planebody.posX = p_70107_1_;
		planebody.posY = p_70107_3_;
		planebody.posZ = p_70107_5_;
		float f = planebody.width / 2.0F;
		float f1 = planebody.height;
		planebody.boundingBox.setBounds(p_70107_1_ - (double)f, p_70107_3_ - (double)planebody.yOffset + (double)planebody.ySize, p_70107_5_ - (double)f, p_70107_1_ + (double)f, p_70107_3_ - (double)planebody.yOffset + (double)planebody.ySize + (double)f1, p_70107_5_ + (double)f);
		if(planebody.boundingBox instanceof ModifiedBoundingBox){
			((ModifiedBoundingBox) planebody.boundingBox).update(planebody.posX,
					planebody.posY,
					planebody.posZ);
		}
	}
	
	@Override
	public String getsound() {
		return soundname;
	}
	
	public float getsoundPitch(){
		return throttle /throttle_Max*soundpitch;
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
		yawladder = tempMouseZ;
	}
	public Entity[] getRiddenEntityList(){
		return riddenByEntities;
	}
	
	@Override
	public SeatInfo[] getRiddenSeatList() {
		return riddenByEntitiesInfo;
	}
	
	public boolean pickupEntity(Entity p_70085_1_, int startSeachSeatNum){
		if(isRidingEntity(p_70085_1_))return false;
		boolean flag = false;
		if(!planebody.worldObj.isRemote) {
			for (int cnt = 0; cnt < riddenByEntities.length; cnt++) {
				int tempid = cnt + startSeachSeatNum;
				while(tempid < 0)tempid = tempid + riddenByEntities.length;
				while(tempid >= riddenByEntitiesInfo.length)tempid = tempid - riddenByEntities.length;
				if (riddenByEntities[tempid] == null) {
					riddenByEntities[tempid] = p_70085_1_;
					flag = true;
					break;
				}
			}
			if (flag)
				HMVPacketHandler.INSTANCE.sendToAll(new HMVPacketPickNewEntity(planebody.getEntityId(), riddenByEntities));
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
		return proxy_HMVehicle.iszooming() ?camerapos_zoom:camerapos;
	}
}
