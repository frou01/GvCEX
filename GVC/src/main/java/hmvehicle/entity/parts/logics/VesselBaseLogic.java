package hmvehicle.entity.parts.logics;

import hmvehicle.HMVPacketHandler;
import hmvehicle.entity.parts.IMultiTurretVehicle;
import hmvehicle.entity.parts.ITank;
import hmvehicle.entity.parts.SeatInfo;
import hmvehicle.packets.HMVPacketPickNewEntity;
import hmvehicle.packets.HMVPakcetVehicleTurretSync;
import hmvehicle.entity.parts.turrets.TurretObj;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;

import javax.vecmath.Vector3d;

import static hmggvcmob.GVCMobPlus.proxy;
import static hmggvcmob.util.Calculater.*;
import static java.lang.Math.abs;

public class VesselBaseLogic extends TankBaseLogic implements MultiRiderLogics{
    IMultiTurretVehicle iMultiTurretVehicle;
    public Entity[] riddenByEntities = new Entity[1];
    public SeatInfo[] riddenByEntitiesInfo = new SeatInfo[1];
    public SeatInfo[] riddenByEntitiesInfo_zoom = new SeatInfo[1];
    boolean firstUpdate = true;
    public VesselBaseLogic(EntityLiving entityLiving, float yawspeed, float speed, String sound){
        super(entityLiving,yawspeed,speed,sound);
        this.tank = entityLiving;
        this.iMultiTurretVehicle = (IMultiTurretVehicle) entityLiving;
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
        int cnt = 0;
        for (Entity entity : riddenByEntities) {
//			if(worldObj.isRemote)System.out.println("debug CL " + entity);
//			else System.out.println("debug SV " + entity);
            if (entity != null) {
                tank.riddenByEntity = entity;
                {
                    Vector3d tempplayerPos = new Vector3d(proxy.iszooming() ? riddenByEntitiesInfo_zoom[cnt].pos : riddenByEntitiesInfo[cnt].pos);
                    Vector3d temp = transformVecByQuat(tempplayerPos, bodyRot);
                    transformVecforMinecraft(temp);
                    temp.add(new Vector3d(tank.posX,
                                                 tank.posY,
                                                 tank.posZ));
//			temp.add(playeroffsetter);
//			System.out.println(temp);
                    tank.riddenByEntity.setPosition(temp.x,
                            temp.y - ((tank.worldObj.isRemote && tank.riddenByEntity instanceof EntityPlayer) ? 0 : tank.riddenByEntity.getEyeHeight()),
                            temp.z);
                    tank.riddenByEntity.posX = temp.x;
                    tank.riddenByEntity.posY = temp.y - ((tank.worldObj.isRemote && tank.riddenByEntity instanceof EntityPlayer) ? 0 : tank.riddenByEntity.getEyeHeight());
                    tank.riddenByEntity.posZ = temp.z;
                    tank.riddenByEntity.motionX = 0.0D;
                    tank.riddenByEntity.motionY = 0.0D;
                    tank.riddenByEntity.motionZ = 0.0D;
                }
                entity.ridingEntity = tank;
                if (!tank.worldObj.isRemote && entity instanceof EntityPlayer) entity.ridingEntity = null;
            }
            cnt++;
        }
        tank.riddenByEntity = null;
    }
    public void updateClient(){
        {
            int cnt = 0;
            for (Entity entity : riddenByEntities) {
                if (entity != null) {
                    if(!tank.worldObj.isRemote && entity instanceof EntityPlayer && entity.isSneaking() || entity.isDead){
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
        }
        tank.riddenByEntity = riddenByEntities[0];
        super.updateClient();
    }
    public void updateServer(){
        {
            int cnt = 0;
            for (Entity entity : riddenByEntities) {
                if (entity != null) {
                    if(!tank.worldObj.isRemote && entity instanceof EntityPlayer && entity.isSneaking() || entity.isDead){
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
        }
        tank.riddenByEntity = riddenByEntities[0];
        super.updateServer();
        HMVPacketHandler.INSTANCE.sendToAll(new HMVPakcetVehicleTurretSync(tank.getEntityId(), iMultiTurretVehicle));
        if(ismanual && tank instanceof ITank && tank.riddenByEntity != null){
            iMultiTurretVehicle.Excontrol1(serverf);
            iMultiTurretVehicle.Excontrol2(serverx);
        }
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
    public boolean pickupEntity(Entity p_70085_1_, int StartSeachSeatNum){
        boolean flag = false;
        if(!tank.worldObj.isRemote) {
            for (int id = StartSeachSeatNum; id < riddenByEntities.length; id++) {
                if (riddenByEntities[id] == null) {
                    riddenByEntities[id] = p_70085_1_;
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
}
