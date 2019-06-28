package hmvehicle.entity.parts.logics;

import hmvehicle.HMVPacketHandler;
import hmvehicle.entity.parts.*;
import hmvehicle.packets.*;
import hmvehicle.entity.parts.turrets.TurretObj;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

import javax.vecmath.Vector3d;

import static hmggvcmob.GVCMobPlus.cfgVehicleWheel_DownRange;
import static hmggvcmob.GVCMobPlus.cfgVehicleWheel_UpRange;
import static hmvehicle.Utils.*;
import static hmvehicle.HMVehicle.proxy_HMVehicle;
import static java.lang.Math.*;
import static net.minecraft.util.DamageSource.inWall;

public class VesselBaseLogic extends TankBaseLogic implements MultiRiderLogics{
    IShip iMultiTurretVehicle;
    public double pitchmotion;
    public Entity[] riddenByEntities = new Entity[1];
    public SeatInfo[] riddenByEntitiesInfo = {new SeatInfo()};
    public SeatInfo[] riddenByEntitiesInfo_zoom = {new SeatInfo()};
    public VesselBaseLogic(EntityLiving entityLiving, float yawspeed, float speed, String sound){
        super(entityLiving,yawspeed,speed,sound);
        this.tank = entityLiving;
        this.iMultiTurretVehicle = (IShip) entityLiving;
        this.yawspeed = yawspeed;
        this.speed = speed;
        tracksound = sound;
        throttle_speed = 0.045f;
    }
    public VesselBaseLogic(EntityLiving entityLiving,float yawspeed,float speed,boolean spinturn,String sound){
        this(entityLiving,yawspeed,speed,sound);
        this.spinturn = spinturn;
    }
    public void updateCommon() {
        riderPosUpdate();
        super.updateCommon();
    }
    public void updateClient(){
        updateRider();
        super.updateClient();
    }
    
    public void followGround(){
        {
            Vec3 tankFrontVec_level;
            Vec3 tankRight;
            {
                float f1;
                float f2;
                f1 = -MathHelper.cos(-bodyrotationYaw * 0.017453292F - (float) Math.PI);
                f2 = -MathHelper.sin(-bodyrotationYaw * 0.017453292F - (float) Math.PI);
                tankFrontVec_level = Vec3.createVectorHelper((double) (f2) * 1.5, (double) 0, (double) (f1) * 1.5);
                tankRight = Vec3.createVectorHelper((f1)* 1.5, (double) 0, -(f2)* 1.5);
            }
            Vector3d tankFrontVec = transformVecByQuat(new Vector3d(0,0,-1),bodyRot);
            {
                Vector3d temp = new Vector3d(tankFrontVec);
                temp.y = 0;
                tankFrontVec.scale(1.5f/temp.length());
            }
            Vec3 FR;
            Vec3 FL;
            Vec3 BR;
            Vec3 BL;
            {
                Vec3 vec3 = Vec3.createVectorHelper(tank.posX, tank.posY + tankFrontVec.y + cfgVehicleWheel_UpRange, tank.posZ);
                vec3 = vec3.addVector(tankFrontVec_level.xCoord, tankFrontVec_level.yCoord, tankFrontVec_level.zCoord);
                vec3 = vec3.addVector(tankRight.xCoord, tankRight.yCoord, tankRight.zCoord);
//            playerlook = Vec3.createVectorHelper(playerlook.xCoord * 256, playerlook.yCoord * 256, playerlook.zCoord * 256);
                Vec3 vec31 = vec3.addVector(0, -cfgVehicleWheel_DownRange, 0);
                MovingObjectPosition amovingObjectPosition = tank.worldObj.func_147447_a(vec3, vec31, false, true, false);
                if(amovingObjectPosition == null)FR = vec31;
                else FR = amovingObjectPosition.hitVec;
            }
            {
                Vec3 vec3 = Vec3.createVectorHelper(tank.posX, tank.posY + tankFrontVec.y + cfgVehicleWheel_UpRange, tank.posZ);
                vec3 = vec3.addVector(tankFrontVec_level.xCoord, tankFrontVec_level.yCoord, tankFrontVec_level.zCoord);
                vec3 = vec3.addVector(-tankRight.xCoord, -tankRight.yCoord, -tankRight.zCoord);
//            playerlook = Vec3.createVectorHelper(playerlook.xCoord * 256, playerlook.yCoord * 256, playerlook.zCoord * 256);
                Vec3 vec31 = vec3.addVector(0, -cfgVehicleWheel_DownRange, 0);
                MovingObjectPosition amovingObjectPosition = tank.worldObj.func_147447_a(vec3, vec31, false, true, false);
                if(amovingObjectPosition == null)FL = vec31;
                else FL = amovingObjectPosition.hitVec;
            }
            {
                Vec3 vec3 = Vec3.createVectorHelper(tank.posX, tank.posY - tankFrontVec.y + cfgVehicleWheel_UpRange, tank.posZ);
                vec3 = vec3.addVector(-tankFrontVec_level.xCoord, -tankFrontVec_level.yCoord, -tankFrontVec_level.zCoord);
                vec3 = vec3.addVector(tankRight.xCoord, tankRight.yCoord, tankRight.zCoord);
//            playerlook = Vec3.createVectorHelper(playerlook.xCoord * 256, playerlook.yCoord * 256, playerlook.zCoord * 256);
                Vec3 vec31 = vec3.addVector(0, -cfgVehicleWheel_DownRange, 0);
                MovingObjectPosition amovingObjectPosition = tank.worldObj.func_147447_a(vec3, vec31, false, true, false);
                if(amovingObjectPosition == null)BR = vec31;
                else BR = amovingObjectPosition.hitVec;
            }
            {
                Vec3 vec3 = Vec3.createVectorHelper(tank.posX, tank.posY - tankFrontVec.y + cfgVehicleWheel_UpRange, tank.posZ);
                vec3 = vec3.addVector(-tankFrontVec_level.xCoord, -tankFrontVec_level.yCoord, -tankFrontVec_level.zCoord);
                vec3 = vec3.addVector(-tankRight.xCoord, -tankRight.yCoord, -tankRight.zCoord);
//            playerlook = Vec3.createVectorHelper(playerlook.xCoord * 256, playerlook.yCoord * 256, playerlook.zCoord * 256);
                Vec3 vec31 = vec3.addVector(0, -cfgVehicleWheel_DownRange, 0);
                MovingObjectPosition amovingObjectPosition = tank.worldObj.func_147447_a(vec3, vec31, false, true, false);
                if(amovingObjectPosition == null)BL = vec31;
                else BL = amovingObjectPosition.hitVec;
            }
        
            {
                Vec3 vec1 = BL.addVector(-FR.xCoord,-FR.yCoord,-FR.zCoord);
                Vec3 vec2 = BR.addVector(-FL.xCoord,-FL.yCoord,-FL.zCoord);
                Vec3 normal = vec1.crossProduct(vec2).normalize();
                tankRight = tankRight.normalize();
                Vec3 pitchVec = normal.crossProduct(tankRight).normalize();
                Vec3 rollVec = normal.crossProduct(pitchVec).normalize();
                bodyrotationPitch *= (idriveableVehicle.ishittingWater() ? 0.9:0.999);
                bodyrotationRoll *= (idriveableVehicle.ishittingWater() ? 0.9:0.999);
                if(tank.onGround || (canControlonWater && idriveableVehicle.ishittingWater())) {
                    double deltaPitch = ((float) toDegrees(sin(pitchVec.yCoord)) - bodyrotationPitch);
                    boolean flag = deltaPitch>0;
                    boolean flag2 = abs(deltaPitch)<0.01;
                    pitchmotion += flag2 ?(deltaPitch):flag?0.06:-0.03;
                    if(tank.motionX * tank.motionX + tank.motionZ * tank.motionZ > 0){
                        pitchmotion -=sqrt(tank.motionX * tank.motionX + tank.motionZ * tank.motionZ)*0.75/(1 +abs( bodyrotationPitch));
                    }
                    pitchmotion -= tank.motionY*4 / (1 + abs(bodyrotationPitch));
                    bodyrotationRoll += ((float) -toDegrees(sin(rollVec.yCoord)) - bodyrotationRoll) * 0.2;
                }
                bodyrotationPitch += pitchmotion;
//                pitchmotion *= 0.9999999;

//                if(tank.worldObj.isRemote){
//                    System.out.println(vec1);
//                    System.out.println(vec2);
//                    System.out.println(normal);
//                }
            }
        }
    }
    public void updateServer(){
        updateRider();
        super.updateServer();
        HMVPacketHandler.INSTANCE.sendToAll(new HMVPakcetVehicleTurretSync(tank.getEntityId(), iMultiTurretVehicle));
        if(ismanual && tank instanceof ITank && tank.riddenByEntity != null){
            iMultiTurretVehicle.Excontrol1(serverf);
            iMultiTurretVehicle.Excontrol2(serverx);
        }
        if(tank.onGround || tank.isCollided)tank.attackEntityFrom(inWall,50);
    }
    private void updateRider(){
        int cnt = 0;
        for (Entity entity : riddenByEntities) {
            if (entity != null) {
                if((tank.worldObj.isRemote && entity instanceof EntityPlayer && entity.isSneaking())) {
                    HMVPacketHandler.INSTANCE.sendToServer(new HMVPacketDisMountEntity(tank.getEntityId(),entity.getEntityId()));
                }else
                if(!tank.worldObj.isRemote && entity.isDead){
//						System.out.println("debug");
                    riddenByEntities[cnt] = null;
                    entity.ridingEntity = null;
                    HMVPacketHandler.INSTANCE.sendToAll(new HMVPacketPickNewEntity(tank.getEntityId(),riddenByEntities));
                }else {
                    entity.ridingEntity = tank;
                }
            }
            cnt ++;
        }
        tank.riddenByEntity = riddenByEntities[0];
    }
    public boolean aimMainTurret_toAngle(double targetyaw, double targetpitch){
        boolean result = false;
        if(iMultiTurretVehicle.getmainTurrets() != null) {
            for (TurretObj aturret : iMultiTurretVehicle.getmainTurrets()) {
                aturret.currentEntity = tank;
                result |= aturret.aimtoAngle(targetyaw, targetpitch);
            }
        }
    
        if(iMultiTurretVehicle.getsubTurrets() != null) {
            for (TurretObj aturret : iMultiTurretVehicle.getsubTurrets()) {
                aturret.currentEntity = tank;
                result |= aturret.aimtoAngle(targetyaw, targetpitch);
            }
        }
        return result;
    }
    public boolean aimMainTurret_toTarget(Entity target){
        boolean result = false;
        if(iMultiTurretVehicle.getmainTurrets() != null) {
            for(TurretObj aturret : iMultiTurretVehicle.getmainTurrets()){
                aturret.currentEntity = tank;
                result |= aturret.aimToEntity(target);
            }
        }
        if(iMultiTurretVehicle.getsubTurrets() != null) {
            for(TurretObj aturret : iMultiTurretVehicle.getsubTurrets()){
                aturret.currentEntity = tank;
                result |= aturret.aimToEntity(target);
            }
        }
        return result;
    }
    public boolean aimMainTurret_toPos(double targetX,double targetY,double targetZ){
        boolean result = false;
        if(iMultiTurretVehicle.getmainTurrets() != null) {
            for(TurretObj aturret : iMultiTurretVehicle.getmainTurrets()){
                aturret.currentEntity = tank;
                result |= aturret.aimToPos(targetX,targetY,targetZ);
            }
        }
        if(iMultiTurretVehicle.getsubTurrets() != null) {
            for(TurretObj aturret : iMultiTurretVehicle.getsubTurrets()){
                aturret.currentEntity = tank;
                result |= aturret.aimToPos(targetX,targetY,targetZ);
            }
        }
        return result;
    }
    public Entity[] getRiddenEntityList(){
        return riddenByEntities;
    }
    
    @Override
    public SeatInfo[] getRiddenSeatList() {
        return riddenByEntitiesInfo;
    }
    
    public boolean pickupEntity(Entity p_70085_1_, int StartSeachSeatNum){
        if(isRidingEntity(p_70085_1_))return false;
        boolean flag = false;
        if(!tank.worldObj.isRemote) {
            for (int cnt = 0; cnt < riddenByEntities.length; cnt++) {
                int tempid = cnt + StartSeachSeatNum;
                while(tempid < 0)tempid = tempid + riddenByEntities.length;
                while(tempid >= riddenByEntitiesInfo.length)tempid = tempid - riddenByEntities.length;
                if (riddenByEntities[tempid] == null) {
                    riddenByEntities[tempid] = p_70085_1_;
                    flag = true;
                    break;
                }
            }
            if (flag)
                HMVPacketHandler.INSTANCE.sendToAll(new HMVPacketPickNewEntity(tank.getEntityId(), riddenByEntities));
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
    
    public void riderPosUpdate(){
        int cnt = 0;
        Vector3d thispos = new Vector3d(tank.posX,
                                               tank.posY,
                                               tank.posZ);
//		System.out.println("thispos  " + thispos);
        for (Entity entity : riddenByEntities) {
            if (entity != null) {
//				if(worldObj.isRemote)System.out.println("debug CL Pre" + cnt + " , " + entity);
//				else System.out.println("debug SV Pre" + cnt + " , " + entity);
                Vector3d tempplayerPos = new Vector3d((proxy_HMVehicle.iszooming() && riddenByEntitiesInfo_zoom.length > cnt && riddenByEntitiesInfo_zoom[cnt] != null)?riddenByEntitiesInfo_zoom[cnt].pos:riddenByEntitiesInfo[cnt].pos);
                tempplayerPos.sub(new Vector3d(0,0,0));
                Vector3d temp = transformVecByQuat(tempplayerPos, bodyRot);
                transformVecforMinecraft(temp);
                temp.add(new Vector3d(0,0,0));
//				System.out.println("" + temp);
                temp.add(thispos);
//			temp.add(playeroffsetter);
//			System.out.println(temp);
                entity.setPosition(temp.x,
                        temp.y - ((tank.worldObj.isRemote && entity instanceof EntityPlayer) ? 0 : entity.getEyeHeight()),
                        temp.z);
                entity.posX = temp.x;
                entity.posY = temp.y - ((tank.worldObj.isRemote && entity instanceof EntityPlayer) ? 0 : entity.getEyeHeight());
                entity.posZ = temp.z;
                entity.motionX = tank.motionX;
                entity.motionY = tank.motionY;
                entity.motionZ = tank.motionZ;
                TurretObj seatGun = riddenByEntitiesInfo[cnt].gun;
                if(seatGun != null) {
                    seatGun.currentEntity = entity;
                    seatGun.update(bodyRot, new Vector3d(tank.posX, tank.posY, -tank.posZ));
                    seatGun.aimtoAngle(entity.getRotationYawHead(), entity.rotationPitch);
                }
                if(tank.worldObj.isRemote){
                    if(entity == proxy_HMVehicle.getEntityPlayerInstance()){
                        
                        if(seatGun != null)HMVPacketHandler.INSTANCE.sendToServer(new HMVPacketTriggerSeatGun(proxy_HMVehicle.leftclick(), proxy_HMVehicle.rightclick(), tank.getEntityId(), cnt));
                        if(proxy_HMVehicle.yclick())HMVPacketHandler.INSTANCE.sendToServer(new HMVPacketChangeSeat(tank.getEntityId(),cnt,true));
                        else
                        if(proxy_HMVehicle.hclick())HMVPacketHandler.INSTANCE.sendToServer(new HMVPacketChangeSeat(tank.getEntityId(),cnt,false));
                    }
                }else {
                    if(entity instanceof EntityLiving)riddenByEntitiesInfo[cnt].gunTrigger1 = entity.getEntityData().getBoolean("HMGisUsingItem");
                    if(seatGun != null && seatGun.readyaim)if(riddenByEntitiesInfo[cnt].gunTrigger1)seatGun.fireall();
//					System.out.println("" + riddenByEntitiesInfo[cnt].gunTrigger1);
                    riddenByEntitiesInfo[cnt].gunTrigger1 = false;
                }
                entity.ridingEntity = tank;
                if(entity instanceof IhasprevRidingEntity)((IhasprevRidingEntity) entity).setprevRidingEntity(tank);
//                if (!tank.worldObj.isRemote && entity instanceof EntityPlayer) entity.ridingEntity = null;
//				if(worldObj.isRemote)System.out.println("debug CL Post" + cnt + " , " + entity);
//				else System.out.println("debug SV Post" + cnt + " , " + entity);
            }
            cnt++;
        }
        tank.riddenByEntity = null;
    }
}
