package hmggvcmob.entity;

import cpw.mods.fml.client.FMLClientHandler;
import handmadeguns.entity.bullets.HMGEntityBullet;
import handmadeguns.entity.bullets.HMGEntityBulletBase;
import hmggvcmob.network.GVCMPacketHandler;
import hmggvcmob.network.GVCPacketSeatData;
import hmggvcmob.network.GVCPakcetVehicleState;
import hmggvcmob.util.Calculater;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import org.lwjgl.input.Mouse;

import javax.vecmath.AxisAngle4d;
import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;

import java.util.Random;

import static hmggvcmob.GVCMobPlus.proxy;
import static hmggvcmob.event.GVCMXEntityEvent.soundedentity;
import static hmggvcmob.util.Calculater.angle_cos;
import static hmggvcmob.util.Calculater.vector_interior_division;
import static java.lang.Math.*;
import static java.lang.Math.toRadians;
import static net.minecraft.util.MathHelper.wrapAngleTo180_float;

public class PlaneBaseLogic {public int rocket = 2;
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
	
	public float bodyrotationYaw;
	public float bodyrotationPitch;
	public float bodyrotationRoll;
	public float prevbodyrotationYaw;
	public float prevbodyrotationPitch;
	public float prevbodyrotationRoll;
	public float prevangletime;
	public float angletime;
	public int fireCycle1;
	public int cooltime;
	public int magazine;
	public float throttle;
	public float speedfactor = 0.03f;
	public float speedfactor_af = 0.01f;
	public float liftfactor = 0.04f;
	public float flapliftfactor = 0.0008f;
	public float dragfactor = 0.00011f;
	public float flapdragfactor = 0.000001f;
	public float geardragfactor = 0.000005f;
	public int mode = 0;//0:attack 1:leave 2:follow player 3:go to home
	public int soundtick = 0;
	public boolean trigger1 = false;
	public boolean trigger2 = false;
	public double[][] gunpos = new double[6][3];
	public Quat4d bodyRot = new Quat4d(0,0,0,1);
	public Quat4d rotationmotion = new Quat4d(0,0,0,1);
	public Quat4d prevbodyRot = new Quat4d(0,0,0,1);
	public Vector3d prevmotionVec = new Vector3d(0,0,0);
	Vector3d unitX = new Vector3d(1,0,0);
	Vector3d unitY = new Vector3d(0,1,0);
	Vector3d unitZ = new Vector3d(0,0,1);
	public EntityCameraDummy camera;
	public GVCEntityChild[] childEntities = new GVCEntityChild[1];
	public ChildInfo[] childInfo = new ChildInfo[1];
	boolean isinit;
	ModifiedBoundingBox nboundingbox;
	
	public int gearprogress;
	public int flaplevel;
	
	World worldObj;
	
	Entity planebody;
	Iplane plane;
	
	Random rand = new Random();
	
	public PlaneBaseLogic(World world,Entity entity) {
		gunpos[0][0] = 0.1;
		gunpos[0][1] = 1.27;
		gunpos[0][2] = 0.19;
		gunpos[1][0] = -0.1;
		gunpos[1][1] = 1.27;
		gunpos[1][2] = 0.19;
		gunpos[2][0] = 2.9;
		gunpos[2][1] = 1.03;
		gunpos[2][2] = -0.41;
		gunpos[3][0] = -2.68;
		gunpos[3][1] = 1.03;
		gunpos[3][2] = -0.41;
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
			GVCMPacketHandler.INSTANCE.sendToAll(new GVCPakcetVehicleState(planebody.getEntityId(),bodyRot, throttle,trigger1,trigger2));
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
				planebody.playSound("gvcmob:gvcmob.plane", 4F, 0.8f + throttle /25);
			}
		}
		rotationmotion.normalize();
		bodyRot.mul(rotationmotion);
		rotationmotion.interpolate(new Quat4d(0,0,0,1),0.2d);
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
		if (proxy.wclick()) {
			throttle += 0.05;
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
	
	public void initseat(){
		for (int i = 0; i< childEntities.length; i++) {
			childInfo[i] = new ChildInfo();
			if(!worldObj.isRemote) {
				childEntities[i] = new GVCEntityChild(worldObj,1,1,true);
				childEntities[i].setLocationAndAngles(planebody.posX,planebody.posY,planebody.posZ,0,0);
				childEntities[i].master = planebody;
				childEntities[i].idinmasterEntityt = i;
				worldObj.spawnEntityInWorld(childEntities[i]);
			}
			isinit = true;
			switch (i){
				case 0:
					childInfo[i].pos[0] = 0;
					childInfo[i].pos[1] = 1.1;
					childInfo[i].pos[2] = 3;
					break;
//				case 1:
//					childInfo[i].pos[0] = -0.73;
//					childInfo[i].pos[1] = 1.1;
//					childInfo[i].pos[2] = 1.00;
//					break;
//				case 2:
//					childInfo[i].pos[0] = 1.0;
//					childInfo[i].pos[1] = 1.1;
//					childInfo[i].pos[2] = 1.75;
//					break;
//				case 3:
//					childInfo[i].pos[0] = 1.0;
//					childInfo[i].pos[1] = 1.1;
//					childInfo[i].pos[2] = 1.00;
//					break;
//				case 4:
//					childInfo[i].pos[0] = 0.15;
//					childInfo[i].pos[1] = 0.7;
//					childInfo[i].pos[2] = 5.8;
//					break;
//				case 5:
//					childInfo[i].pos[0] = 0.24;
//					childInfo[i].pos[1] = 1.4;
//					childInfo[i].pos[2] = 4;
//					break;
			}
		}
	}
	
	void FCS(Vector3d mainwingvector,Vector3d tailwingvector,Vector3d bodyvector){
		if (trigger1) {
			for(int i = 0;i<2;i++){
				HMGEntityBullet var3 = new HMGEntityBullet(this.worldObj, childEntities[0].riddenByEntity, 40, 8, 3);
				var3.setLocationAndAngles(
						planebody.posX + mainwingvector.x * gunpos[i][0] +     tailwingvector.x * (gunpos[i][1] - 2.5) - bodyvector.x * gunpos[i][2]
						, planebody.posY + mainwingvector.y * gunpos[i][0] + 2 + tailwingvector.y * (gunpos[i][1] - 2.5) - bodyvector.y * gunpos[i][2]
						, planebody.posZ + mainwingvector.z * gunpos[i][0] +     tailwingvector.z * (gunpos[i][1] - 2.5) - bodyvector.z * gunpos[i][2]
						,bodyrotationYaw,bodyrotationPitch);
//						var3.setHeadingFromThrower(bodyrotationPitch, this.bodyrotationYaw, 0, 8, 10F);
				var3.motionX = planebody.motionX + bodyvector.x * -6 + this.rand.nextGaussian() * (double)(this.rand.nextBoolean() ? -1 : 1) * 0.01 * 3;
				var3.motionY = planebody.motionY + bodyvector.y * -6 + this.rand.nextGaussian() * (double)(this.rand.nextBoolean() ? -1 : 1) * 0.01 * 3;
				var3.motionZ = planebody.motionZ + bodyvector.z * -6 + this.rand.nextGaussian() * (double)(this.rand.nextBoolean() ? -1 : 1) * 0.01 * 3;
				var3.bulletTypeName = "byfrou01_GreenTracer";
				this.worldObj.spawnEntityInWorld(var3);
			}
			if(planebody.getEntityData().getFloat("GunshotLevel")<4)
				soundedentity.add(planebody);
			planebody.getEntityData().setFloat("GunshotLevel",4);
			planebody.playSound("gvcguns:gvcguns.fire", 4.0F, 0.5F);
			trigger1 = false;
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
		
		Vector3d motionvec = new Vector3d(planebody.motionX, planebody.motionY,planebody.motionZ);
		if(planebody.onGround && throttle>0.01 && throttle<0.2 && motionvec.length()<1){
			planebody.motionX = bodyvector.x*0.2;
			planebody.motionY = bodyvector.y*0.2;
			planebody.motionZ = bodyvector.z*0.2;
		}else {
			if (!(Double.isNaN(bodyvector.x) || Double.isNaN(bodyvector.y) || Double.isNaN(bodyvector.z))) {
				planebody.motionX -= bodyvector.x * throttle * speedfactor;
				planebody.motionY -= bodyvector.y * throttle * speedfactor;
				planebody.motionZ -= bodyvector.z * throttle * speedfactor;
				if(throttle>9.5){
					planebody.motionX -= bodyvector.x * throttle * speedfactor_af;
					planebody.motionY -= bodyvector.y * throttle * speedfactor_af;
					planebody.motionZ -= bodyvector.z * throttle * speedfactor_af;
				}
			}
		}
		motionvec = new Vector3d(planebody.motionX, planebody.motionY, planebody.motionZ);
		{
			motionvec = new Vector3d(planebody.motionX, planebody.motionY, planebody.motionZ);
			bodyvector.normalize();
			motionvec.add(bodyvector);
			double cos = angle_cos(bodyvector, motionvec);
			if (!planebody.onGround && motionvec.lengthSquared() > 0.01) {
				Vector3d axisstall = new Vector3d();
				motionvec.normalize();
				axisstall.cross(bodyvector, motionvec);
				axisstall.normalize();
				axisstall.z = -axisstall.z;
				Quat4d quat4d = new Quat4d();
				quat4d.inverse(bodyRot);
				quat4d.normalize();
				axisstall = Calculater.transformVecByQuat(axisstall,quat4d);
				if(! Double.isNaN(axisstall.x) && ! Double.isNaN(axisstall.y) && ! Double.isNaN(axisstall.z)) {
					AxisAngle4d axisxangledstall = new AxisAngle4d(axisstall, abs(cos) * acos(-cos) / 500);
					rotationmotion = Calculater.quatRotateAxis(rotationmotion, axisxangledstall);
				}
			}
		}
		
		motionvec = new Vector3d(planebody.motionX, planebody.motionY, planebody.motionZ);
		if(motionvec.length()>0.01) {
			double cos;
			cos = angle_cos(bodyvector, motionvec);
			if (motionvec.length() > 0.01 && abs(cos) > 0.01) {
				Vector3d bodyvectorForscaling = new Vector3d(bodyvector);
				bodyvectorForscaling.normalize();
				bodyvectorForscaling.scale(-motionvec.length());
				cos = angle_cos(motionvec, bodyvectorForscaling);
				motionvec = vector_interior_division(bodyvectorForscaling,motionvec,cos);
				motionvec.normalize();
				motionvec.scale(bodyvectorForscaling.length());
			}
			cos = angle_cos(bodyvector, motionvec);
			Vector3d tailwingvectorForFloating = new Vector3d(tailwingvector);
			tailwingvectorForFloating.scale(motionvec.length() * -cos * (liftfactor + flaplevel * flapliftfactor));
//			System.out.println("debug" + cos);
			motionvec.y -=0.49;
			motionvec.x += tailwingvectorForFloating.x;
			motionvec.y += tailwingvectorForFloating.y;
			motionvec.z += tailwingvectorForFloating.z;
			cos = angle_cos(bodyvector, motionvec);
			if(motionvec.length()>0.01) {
				Vector3d airDrug = new Vector3d(motionvec);
				double sin = sqrt(1-cos * cos);
				airDrug.scale(motionvec.length() * motionvec.length() * (dragfactor + gearprogress * geardragfactor + flaplevel*flapdragfactor) + (sin * sin)/20);
				motionvec.sub(airDrug);
			}else {
				motionvec.scale(0);
			}
		}else {
			motionvec.scale(0);
			motionvec.y -=0.49;
		}
		if(! Double.isNaN(motionvec.x) && ! Double.isNaN(motionvec.y) && ! Double.isNaN(motionvec.z)) {
			planebody.motionX = abs(motionvec.x) > 0.01 ? motionvec.x : 0;
			planebody.motionY = abs(motionvec.y) > 0.01 ? motionvec.y : 0;
			planebody.motionZ = abs(motionvec.z) > 0.01 ? motionvec.z : 0;
		}
		double backmotionX = planebody.motionX;
		double backmotionY = planebody.motionY;
		double backmotionZ = planebody.motionZ;
		motionvec.normalize();
		if(motionvec.y < -bodyvector.y || planebody.onGround){
			Flapextension();
		}else {
			Flapstorage();
		}
		planebody.moveEntity(planebody.motionX,planebody.motionY,planebody.motionZ);
		
		if(throttle >10){
			throttle = 10;
		}
		if(throttle <0){
			throttle = 0;
		}
		planebody.fallDistance=0;
		if(!worldObj.isRemote && health < 0){
			worldObj.createExplosion(planebody,planebody.posX  ,planebody.posY+2,planebody.posZ  ,4,true);
			worldObj.createExplosion(planebody,planebody.posX+2,planebody.posY+2,planebody.posZ  ,3,true);
			worldObj.createExplosion(planebody,planebody.posX-2,planebody.posY+2,planebody.posZ  ,3,true);
			worldObj.createExplosion(planebody,planebody.posX  ,planebody.posY+2,planebody.posZ+2,3,true);
			worldObj.createExplosion(planebody,planebody.posX  ,planebody.posY+2,planebody.posZ-2,3,true);
			planebody.setDead();
		}
		if(planebody.isCollidedHorizontally){
			if(backmotionX*backmotionX + backmotionY*backmotionY + backmotionZ*backmotionZ > 1){
				planebody.attackEntityFrom(DamageSource.fall, (float) (backmotionX*backmotionX + backmotionY*backmotionY + backmotionZ*backmotionZ)*20);
			}
		}
		if(planebody.onGround){
			Vector3d axisx = new Vector3d(-cos(toRadians(bodyrotationYaw)),0,sin(toRadians(bodyrotationYaw)));
			if(abs(bodyrotationPitch)>15){
				planebody.attackEntityFrom(DamageSource.fall, (float) (30));
			}
			AxisAngle4d axisxangled = new AxisAngle4d(axisx, toRadians(-bodyrotationPitch/10));
			bodyRot = Calculater.quatRotateAxis(bodyRot,axisxangled);
			
			axisx = Calculater.transformVecByQuat(new Vector3d(unitZ), bodyRot);
			if(bodyrotationRoll<45 && bodyrotationRoll>-45){
				axisxangled = new AxisAngle4d(axisx, toRadians(-bodyrotationRoll/10));
			}
			if(bodyrotationRoll<-45 && bodyrotationRoll>-135){
				planebody.attackEntityFrom(DamageSource.fall, (float) (20));
				axisxangled = new AxisAngle4d(axisx, toRadians((-90-bodyrotationRoll)/10));
			}
			if(bodyrotationRoll<135 && bodyrotationRoll>45){
				planebody.attackEntityFrom(DamageSource.fall, (float) (20));
				axisxangled = new AxisAngle4d(axisx, toRadians((90-bodyrotationRoll)/10));
			}
			if(bodyrotationRoll>135){
				planebody.attackEntityFrom(DamageSource.fall, (float) (30));
				axisxangled = new AxisAngle4d(axisx, toRadians((180-bodyrotationRoll)/10));
			}
			if(bodyrotationRoll<-135){
				planebody.attackEntityFrom(DamageSource.fall, (float) (30));
				axisxangled = new AxisAngle4d(axisx, toRadians((-180-bodyrotationRoll)/10));
			}
			bodyRot = Calculater.quatRotateAxis(bodyRot,axisxangled);
			
			gearprogress = 100;
		}else {
			if(throttle > 2.5){
				gearprogress--;
			}else {
				gearprogress++;
			}
			
			if(gearprogress<0){
				gearprogress=0;
			}
			if(gearprogress>100){
				gearprogress=100;
			}
			
		}
		if(flaplevel<0){
			flaplevel=0;
		}
		if(flaplevel>75){
			flaplevel=75;
		}
		nboundingbox.rot.set(this.bodyRot);
		nboundingbox.updateOBB(planebody.posX,planebody.posY,planebody.posZ);
		((ModifiedBoundingBox)planebody.boundingBox).rot.inverse(this.bodyRot);
		((ModifiedBoundingBox)planebody.boundingBox).updateOBB(planebody.posX,planebody.posY,planebody.posZ);
		
		if(childEntities[0]!=null) {
			childEntities[0].motionX = planebody.motionX;
			childEntities[0].motionY = planebody.motionY;
			childEntities[0].motionZ = planebody.motionZ;
			if(childEntities[0].riddenByEntity != null){
				childEntities[0].riddenByEntity.motionX = planebody.motionX;
				childEntities[0].riddenByEntity.motionY = planebody.motionY;
				childEntities[0].riddenByEntity.motionZ = planebody.motionZ;
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
