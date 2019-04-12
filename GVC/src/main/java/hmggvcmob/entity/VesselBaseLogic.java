package hmggvcmob.entity;

import hmggvcmob.network.GVCMPacketHandler;
import hmggvcmob.network.GVCPakcetVehicleTurretSync;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;

import static java.lang.Math.abs;
import static net.minecraft.util.MathHelper.wrapAngleTo180_float;

public class VesselBaseLogic extends TankBaseLogic{
    IMultiTurretVehicle iMultiTurretVehicle;
    public VesselBaseLogic(EntityLiving entityLiving, float yawspeed, float speed, String sound){
        super(entityLiving,yawspeed,speed,sound);
        this.tank = entityLiving;
        this.iMultiTurretVehicle = (IMultiTurretVehicle) entityLiving;
        this.yawspeed = yawspeed;
        this.speed = speed;
        tracksound = sound;
    }
    public VesselBaseLogic(EntityLiving entityLiving,float yawspeed,float speed,boolean spinturn,String sound){
        this(entityLiving,yawspeed,speed,sound);
        this.spinturn = spinturn;
    }
    public void updateClient(){
        super.updateClient();
    }
    public void updateServer(){
        super.updateServer();
        GVCMPacketHandler.INSTANCE.sendToAll(new GVCPakcetVehicleTurretSync(tank.getEntityId(), iMultiTurretVehicle));
        if(ismanual && tank instanceof IRideableTank && tank.riddenByEntity != null){
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
}
