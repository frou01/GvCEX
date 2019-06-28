package hmvehicle.entity.parts.turrets;

import handmadeguns.HMGPacketHandler;
import handmadeguns.entity.EntityHasMaster;
import handmadeguns.entity.SpHitCheckEntity;
import handmadeguns.entity.bullets.*;
import handmadeguns.network.PacketPlaysound;
import handmadeguns.network.PacketSpawnParticle;
import hmvehicle.Utils;
import hmvehicle.entity.parts.HasLoopSound;
import hmvehicle.entity.parts.IVehicle;
import hmvehicle.entity.parts.logics.IbaseLogic;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;

import javax.vecmath.AxisAngle4d;
import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;

import java.util.ArrayList;

import static handmadeguns.HandmadeGunsCore.cfg_defgravitycof;
import static hmggvcmob.event.GVCMXEntityEvent.soundedentity;
import static hmvehicle.HMVehicle.proxy_HMVehicle;
import static java.lang.Math.*;
import static hmvehicle.Utils.*;
import static net.minecraft.util.MathHelper.wrapAngleTo180_double;
import static net.minecraft.util.MathHelper.wrapAngleTo180_float;

public class TurretObj implements HasLoopSound{
    public Vector3d turretYawCenterpos = new Vector3d(0,0,0);
    public Vector3d turretPitchCenterpos = new Vector3d(0,0,0);
    public Vector3d cannonPos = new Vector3d();
    public Vector3d[] multicannonPos = null;
    public int currentCannonID = 0;
    public Vector3d onMotherPos = new Vector3d();
    public Vector3d motherRotCenter = new Vector3d();
    
    public boolean linked_MotherTrigger = true;
    public boolean fireAll = true;
    public boolean rock_to_Vehicle = false;
    public boolean bursting = false;
    public int burstmaxRound = -1;
    public int burstedRound = -1;
    public boolean syncTurretAngle = false;
    public double seekerSize = 60;
    
    public int elevationType = 0;
    public int cycle_timer;
    public int cycle_setting = 100;
    
    public double turretrotationYaw;
    public double turretrotationPitch;
    public double prevturretrotationYaw;
    public double prevturretrotationPitch;
    
    public Quat4d motherRot = new Quat4d();
    public Quat4d turretRot = new Quat4d();
    public Vector3d motherPos = new Vector3d();
    public Vector3d pos = new Vector3d();
    
    public float turretanglelimtYawMax = 360;
    public float turretanglelimtYawmin = -360;
    public float turretanglelimtPitchMax = 15;
    public float turretanglelimtPitchmin = -15;
    
    public FireRist[] fireRists;
    
    public double torpdraft = 0;
    
    
    public double turretspeedY = 5;
    public double turretspeedP = 5;
    
    public float turretMoving = 0;
    public float prevturretMoving = 0;
    
    public String traverseSound = "gvcmob:gvcmob.traverseSound";
    public float traversesoundLV = 1;
    public float traversesoundPitch = 1;
    public int traversesoundCool = 1;
    public boolean traverseSoundRemain;
    
    TurretObj mother;
    ArrayList<TurretObj> childs = new ArrayList<>();
    ArrayList<TurretObj> brothers = new ArrayList<>();
    ArrayList<TurretObj> childsOnBarrel = new ArrayList<>();
    
    World worldObj;
    public Entity currentEntity;
    
    public float speed = 8;
    public float acceler = 0;
    public float spread = 0.5f;
    
    public String flashName = "CannonMuzzleFlash";
    public int flashfuse = 3;
    public float flashscale = 5;
    public float flashoffset = 6;
    
    public String firesound = "gvcmob:gvcmob.120mmFire";
    public float firesoundLV = 5;
    public float firesoundPitch = 1;
    public String reloadsound = null;
    public float reloadsoundLV = 1;
    public float reloadsoundPitch = 1;
    public String magreloadfiresound = null;
    public float magreloadsoundLV = 1;
    public float magreloadsoundPitch = 1;
    
    public int powor;
    public int guntype;
    public float ex = 2.5F;
    public float damageRange = -1;
    public int fuse = 0;
    
    public float resistanceinwater = 0.4f;
    public float gravity = 0.04903325f;
    public boolean canex = true;
    public String bulletmodel = "default";
    public int shotgun_pellet = 1;
    
    public int magazinerem;
    public int magazineMax = -1;
    public int reloadTimer = -1;
    public int reloadSetting = 0;
    private int triggerFreeze = 10;
    
    public boolean canHoming = false;
    public boolean semiActive = false;
    public HMGEntityBulletBase missile;
    public float induction_precision = 15;
    public Entity target;
    
    public boolean readyaim = false;
    public boolean readyload = false;
    
    public TurretObj(World worldObj){
        this.worldObj = worldObj;
    }
    public Vector3d getGlobalVector_fromLocalVector(Vector3d local){
        Quat4d turretyawrot = new Quat4d(0,0,0,1);
        Vector3d axisy = Utils.transformVecByQuat(new Vector3d(0,1,0), turretyawrot);
        AxisAngle4d axisyangledy = new AxisAngle4d(axisy, toRadians(turretrotationYaw)/2);
        turretyawrot = Utils.quatRotateAxis(turretyawrot,axisyangledy);
        
        
        Quat4d turretpitchrot = new Quat4d(0,0,0,1);
        Vector3d axisx = Utils.transformVecByQuat(new Vector3d(1,0,0), turretpitchrot);
        AxisAngle4d axisyangledp = new AxisAngle4d(axisx, toRadians(turretrotationPitch)/2);
        turretpitchrot = Utils.quatRotateAxis(turretpitchrot,axisyangledp);
        
        local = new Vector3d(local);
        local.sub(turretYawCenterpos);
        local.sub(turretPitchCenterpos);
        local = transformVecByQuat(local, turretpitchrot);
        local.add(turretPitchCenterpos);
        local.z *= -1;
        local = transformVecByQuat(local, turretyawrot);
        local.add(turretYawCenterpos);
        local = transformVecByQuat(local, motherRot);
//        Vector3d transformedVecYawCenter;
//        Vector3d transformedVecPitchCenter;
//        {
//            Vector3d temp = new Vector3d(turretYawCenterpos);
//            temp.sub(motherRotCenter);
//            temp = transformVecByQuat(temp, motherRot);
//            temp.add(motherRotCenter);
//            transformedVecYawCenter = temp;
//        }
//        {
//            Vector3d temp = new Vector3d(turretPitchCenterpos);
//            temp.sub(turretYawCenterpos);
//            temp = transformVecByQuat(temp, turretyawrot);
//            temp.add(turretYawCenterpos);
//            temp.sub(motherRotCenter);
//            temp = transformVecByQuat(temp, motherRot);
//            temp.add(motherRotCenter);
//            transformedVecPitchCenter = temp;
//        }
//
//
//        local = transformVecByQuat(local,turretpitchrot);
//        local = transformVecByQuat(local,turretyawrot);
//        local.add(transformedVecYawCenter);
//        local.add(transformedVecPitchCenter);
        
        return local;
    }
    public Vector3d getGlobalVector_fromLocalVector_onTurretPoint(Vector3d local){
        Quat4d turretyawrot = new Quat4d(0,0,0,1);
        Vector3d axisy = Utils.transformVecByQuat(new Vector3d(0,1,0), turretyawrot);
        AxisAngle4d axisyangledy = new AxisAngle4d(axisy, toRadians(turretrotationYaw)/2);
        turretyawrot = Utils.quatRotateAxis(turretyawrot,axisyangledy);
        
        
        local = new Vector3d(local);
        local.sub(turretYawCenterpos);
        Vector3d transformedVecYawCenter = transformVecByQuat(turretYawCenterpos,motherRot);
        
        
        local = transformVecByQuat(local,turretyawrot);
        local = transformVecByQuat(local,motherRot);
        
        local.add(transformedVecYawCenter);
        return local;
    }
    public Vector3d getGlobalVector_fromLocalVector_onMother(Vector3d local){
        
        local = new Vector3d(local);
        local.sub(motherRotCenter);
        local = transformVecByQuat(new Vector3d(local),motherRot);
        local.add(motherRotCenter);
        return local;
    }
    public Vector3d getLocalVector_fromGlobalVector(Vector3d global){
        Quat4d turretyawrot = new Quat4d(0,0,0,1);
        Vector3d axisy = Utils.transformVecByQuat(new Vector3d(0,1,0), turretyawrot);
        AxisAngle4d axisyangledy = new AxisAngle4d(axisy, toRadians(turretrotationYaw)/2);
        turretyawrot = Utils.quatRotateAxis(turretyawrot,axisyangledy);
        
        Quat4d yaw_mother = new Quat4d();
        yaw_mother.mul(motherRot,turretyawrot);
        
        Quat4d turretpitchrot = new Quat4d(0,0,0,1);
        Vector3d axisx = Utils.transformVecByQuat(new Vector3d(1,0,0), turretpitchrot);
        AxisAngle4d axisyangledp = new AxisAngle4d(axisy, toRadians(turretrotationPitch)/2);
        turretpitchrot = Utils.quatRotateAxis(turretpitchrot,axisyangledp);
        
        Quat4d pitch_yaw_mother = new Quat4d();
        pitch_yaw_mother.mul(yaw_mother,turretyawrot);
        
        Vector3d transformedVecYawCenter = transformVecByQuat(new Vector3d(turretYawCenterpos),motherRot);
        Vector3d transformedVecPitchCenter = transformVecByQuat(new Vector3d(turretPitchCenterpos),yaw_mother);
        global.sub(transformedVecYawCenter);
        global.sub(transformedVecPitchCenter);
        pitch_yaw_mother.inverse();
        Vector3d transformedGlobalVec = transformVecByQuat(new Vector3d(global),pitch_yaw_mother);
        transformedGlobalVec.add(turretYawCenterpos);
        transformedGlobalVec.add(turretPitchCenterpos);
        return transformedGlobalVec;
    }
    
    public Vector3d getTransformedVector_onbody(Vector3d naturalVector){
        return Utils.transformVecByQuat(naturalVector,motherRot);
    }
    
    public Vector3d getCannonDir(){
        Vector3d lookVec = new Vector3d(0,0,-1);
        lookVec = transformVecByQuat(lookVec,turretRot);
        lookVec = transformVecByQuat(lookVec,motherRot);
        
        return lookVec;
    }
    public Vector3d getCannonPos(){
        Vector3d Vec_transformedbybody = getGlobalVector_fromLocalVector(cannonPos);
    
        transformVecforMinecraft(Vec_transformedbybody);
        return new Vector3d(pos.x + Vec_transformedbybody.x,
                                   pos.y + Vec_transformedbybody.y,
                                   -pos.z + Vec_transformedbybody.z);
    }
    public boolean aimtoAngle(double targetyaw,double targetpitch){
        
        Quat4d gunnerRot = new Quat4d(0,0,0,1);
        
        
        Vector3d axisY = transformVecByQuat(unitY, gunnerRot);
        AxisAngle4d axisxangledY = new AxisAngle4d(axisY, toRadians(targetyaw)/2);
        gunnerRot = quatRotateAxis(gunnerRot,axisxangledY);
        
        Vector3d axisX = transformVecByQuat(unitX, gunnerRot);
        AxisAngle4d axisxangledX = new AxisAngle4d(axisX, toRadians(-targetpitch)/2);
        gunnerRot = quatRotateAxis(gunnerRot,axisxangledX);
        
        
        Vector3d lookVec = new Vector3d(0,0,1);
        
        lookVec = transformVecByQuat(lookVec,gunnerRot);
        Quat4d temp = new Quat4d(motherRot);
        temp.inverse();
        lookVec = transformVecByQuat(lookVec,temp);
        
        return turretMove(toDegrees(atan2(lookVec.x,lookVec.z)) , toDegrees(asin(lookVec.y)));
    }
    
    public void lock(){
        try {
            Vector3d frontVec = getCannonDir();
            transformVecforMinecraft(frontVec);
            frontVec.scale(-1);
            target = null;
            double predeg = -1;
            for (Object obj : worldObj.loadedEntityList) {
                Entity aEntity = (Entity) obj;
                if (!aEntity.isDead) {
                    if (aEntity.worldObj == this.worldObj && aEntity.canBeCollidedWith() &&
                                iscandamageentity(aEntity) &&
                                (!rock_to_Vehicle || aEntity instanceof IVehicle)) {
                        double distsq = currentEntity.getDistanceSqToEntity(aEntity);
                        if (distsq < 16777216) {
                            Vector3d totgtvec = new Vector3d(currentEntity.posX - aEntity.posX, currentEntity.posY - aEntity.posY, currentEntity.posZ - aEntity.posZ);
                            if (totgtvec.length() > 1) {
                                totgtvec.scale(1);
                                double deg = wrapAngleTo180_double(toDegrees(totgtvec.angle(frontVec)));
                                if (seekerSize > abs(deg) && (abs(deg) < predeg || predeg == -1)) {
                                    predeg = deg;
                                    target = aEntity;
                                }
                            }
                        }
                    }
                }
            }
            if(currentEntity instanceof EntityPlayerMP && semiActive && missile != null){
                missile.homingEntity = target;
                HMGPacketHandler.INSTANCE.sendTo(new PacketSpawnParticle(missile.posX, missile.posY + missile.height/2,missile.posZ, 2), (EntityPlayerMP) currentEntity);
            }
            if(currentEntity instanceof EntityPlayerMP && target != null){
                HMGPacketHandler.INSTANCE.sendTo(new PacketSpawnParticle(target.posX, target.posY + target.height/2,target.posZ, 2), (EntityPlayerMP) currentEntity);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    
    
    private boolean iscandamageentity(Entity entity){
        if(entity != currentEntity) {
            if(entity instanceof SpHitCheckEntity){
                if (((SpHitCheckEntity)entity).isRidingEntity(currentEntity))
                    return false;
            }
            if(entity instanceof EntityHasMaster && ((EntityHasMaster) entity).getmaster() instanceof SpHitCheckEntity && (((SpHitCheckEntity) ((EntityHasMaster) entity).getmaster()).isRidingEntity(entity) || ((SpHitCheckEntity) ((EntityHasMaster) entity).getmaster()).isRidingEntity(currentEntity)))return false;
            if(entity.riddenByEntity == currentEntity
                       || entity.ridingEntity == currentEntity){
                return false;
            }
            if(entity.riddenByEntity != null && entity.riddenByEntity.riddenByEntity == currentEntity)return false;
        }else {
            return false;
        }
        return true;
    }
    
    public boolean turretMove(double targetyaw, double targetpitch){
        targetyaw = wrapAngleTo180_double(targetyaw);
        targetpitch = wrapAngleTo180_double(targetpitch);
        turretrotationYaw = wrapAngleTo180_double(turretrotationYaw);
        turretrotationPitch = wrapAngleTo180_double(turretrotationPitch);
        boolean inrange = true;
        if(turretanglelimtPitchMax != turretanglelimtPitchmin) {
            if (targetpitch > turretanglelimtPitchMax) {
                targetpitch = turretanglelimtPitchMax;
                inrange = false;
            } else if (targetpitch < turretanglelimtPitchmin) {
                targetpitch = turretanglelimtPitchmin;
                inrange = false;
            }
        }else {
            targetpitch = turretanglelimtPitchmin;
        }
        if(turretanglelimtYawMax != turretanglelimtYawmin) {
            if(turretanglelimtYawMax < turretanglelimtYawmin){
                if (targetyaw < 0 && targetyaw > turretanglelimtYawMax) {
                    targetyaw = turretanglelimtYawMax;
                    inrange = false;
                } else if (targetyaw > 0 && targetyaw < turretanglelimtYawmin) {
                    targetyaw = turretanglelimtYawmin;
                    inrange = false;
                }
            }else {
                if (targetyaw > turretanglelimtYawMax) {
                    targetyaw = turretanglelimtYawMax;
                    inrange = false;
                } else if (targetyaw < turretanglelimtYawmin) {
                    targetyaw = turretanglelimtYawmin;
                    inrange = false;
                }
            }
        }else {
            targetyaw = turretanglelimtYawmin;
        }
        
        double AngulardifferenceYaw = targetyaw - this.turretrotationYaw;
        AngulardifferenceYaw = wrapAngleTo180_double(AngulardifferenceYaw);
        if(Double.isNaN(targetyaw))AngulardifferenceYaw = 0;
        
        float out_rangeCenter_yaw = (turretanglelimtYawMax + turretanglelimtYawmin)/2;
        if(turretanglelimtYawMax > turretanglelimtYawmin)out_rangeCenter_yaw += 180;
        double AngulardifferenceYaw_toCenter = out_rangeCenter_yaw - this.turretrotationYaw;
        AngulardifferenceYaw_toCenter = wrapAngleTo180_double(AngulardifferenceYaw_toCenter);
        
        
        boolean reverse = abs(turretanglelimtYawMax) + abs(turretanglelimtYawmin) < 360 && (AngulardifferenceYaw < 0) == (AngulardifferenceYaw_toCenter < 0) && (abs(AngulardifferenceYaw) >= abs(AngulardifferenceYaw_toCenter));
        boolean result1 = false;
        if(reverse){
            if(AngulardifferenceYaw > 0){
                turretrotationYaw -= turretspeedY;
            }else if(AngulardifferenceYaw < 0){
                turretrotationYaw += turretspeedY;
            }
        }else
        if(AngulardifferenceYaw > turretspeedY){
            turretrotationYaw += turretspeedY;
        }else if(AngulardifferenceYaw < -turretspeedY){
            turretrotationYaw -= turretspeedY;
        }else {
            this.turretrotationYaw += AngulardifferenceYaw;
            result1 = true;
        }
        
        
        double AngulardifferencePitch = targetpitch - this.turretrotationPitch;
        if(Double.isNaN(targetpitch))AngulardifferencePitch = 0;
        AngulardifferencePitch = wrapAngleTo180_double(AngulardifferencePitch);
        boolean result2 = false;
        
        if(AngulardifferencePitch > turretspeedP){
            turretrotationPitch += turretspeedP;
        }else if(AngulardifferencePitch < -turretspeedP){
            turretrotationPitch -= turretspeedP;
        }else {
            this.turretrotationPitch += AngulardifferencePitch;
            result2 = true;
        }
        turretRot = new Quat4d(0,0,0,1);
        
        
        Vector3d axisY = transformVecByQuat(unitY, turretRot);
        AxisAngle4d axisxangledY = new AxisAngle4d(axisY, toRadians(turretrotationYaw)/2);
        turretRot = quatRotateAxis(turretRot,axisxangledY);
        
        Vector3d axisX = transformVecByQuat(unitX, turretRot);
        AxisAngle4d axisxangledX = new AxisAngle4d(axisX, toRadians(-turretrotationPitch)/2);
        turretRot = quatRotateAxis(turretRot,axisxangledX);
    
        boolean canfire = false;
        if(fireRists != null){
            for(FireRist afirRist : fireRists){
                boolean temp = true;
                if(targetyaw > afirRist.YawMax){
                    temp = false;
                }else
                if(targetyaw < afirRist.YawMin){
                    temp = false;
                }
    
                if(targetpitch > afirRist.PitchMax){
                    temp = false;
                }else
                if(targetpitch < afirRist.PitchMin){
                    temp = false;
                }
                canfire|=temp;
            }
        }else canfire = true;
        
        return readyaim = result1 && result2 && inrange && canfire;
    }
    public void setmotherpos(Vector3d motherPos,Quat4d motherRot){
        this.motherRot = motherRot;
        this.motherPos = motherPos;
        turretRot = new Quat4d(0,0,0,1);
        
        Vector3d temppos = getGlobalVector_fromLocalVector_onMother(onMotherPos);
        
        
        temppos.add(this.motherPos);
        
        this.pos = temppos;
    }
    public void update(Quat4d motherRot,Vector3d motherPos){
        if(worldObj.isRemote) {
            double deltaRotYaw = prevturretrotationYaw - turretrotationYaw;
            double deltaRotPitch = prevturretrotationPitch - turretrotationPitch;
            turretMoving += ((float) sqrt(deltaRotPitch * deltaRotPitch + deltaRotYaw * deltaRotYaw) - turretMoving) * 0.3f;
            if(turretMoving < min(turretspeedY,turretspeedP)/10)turretMoving = 0;
            if (!traverseSoundRemain && currentEntity != null && traverseSound != null && (turretMoving > 0)) {
                proxy_HMVehicle.playsoundasTurret(16, this);
            }
            traverseSoundRemain = false;
        }
        prevturretrotationYaw = turretrotationYaw;
        prevturretrotationPitch = turretrotationPitch;
        this.motherRot = motherRot;
        this.motherPos = motherPos;
        turretRot = new Quat4d(0,0,0,1);
        triggerFreeze--;
        traversesoundCool--;
        Vector3d temppos = getGlobalVector_fromLocalVector_onMother(onMotherPos);
        if(canHoming){
            lock();
        }
        if(semiActive){
            if(missile != null && missile.isDead)missile =null;
        }
        if(bursting)fire();
        
        temppos.add(this.motherPos);
        
        this.pos = temppos;
        
        Vector3d axisY = transformVecByQuat(unitY, turretRot);
        AxisAngle4d axisxangledY = new AxisAngle4d(axisY, toRadians(turretrotationYaw)/2);
        turretRot = quatRotateAxis(turretRot,axisxangledY);
    
    
        Quat4d temp = new Quat4d(this.motherRot);
        temp.mul(turretRot);
        for(TurretObj achild :childs){
            achild.update(temp, this.pos);
        }
        for(TurretObj abrother :brothers){
            if(syncTurretAngle){
                abrother.turretrotationYaw   = this.turretrotationYaw;
                abrother.turretrotationPitch = this.turretrotationPitch;
            }
            abrother.update(this.motherRot, this.motherPos);
        }
        
        Vector3d axisX = transformVecByQuat(unitX, turretRot);
        AxisAngle4d axisxangledX = new AxisAngle4d(axisX, toRadians(-turretrotationPitch)/2);
        turretRot = quatRotateAxis(turretRot,axisxangledX);
    
        temp = new Quat4d(this.motherRot);
        temp.mul(turretRot);
        for(TurretObj achild :childsOnBarrel){
            achild.update(temp, this.pos);
        }
        cycle_timer--;
        readyload = ((magazineMax == -1 || magazinerem > 0) && cycle_timer < 0);
        if(magazineMax != -1 && magazinerem <= 0 && reloadSetting != -1){
            if(reloadTimer == reloadSetting)
                if(currentEntity != null && magreloadfiresound != null)HMGPacketHandler.INSTANCE.sendToAll(new PacketPlaysound(currentEntity, magreloadfiresound, magreloadsoundPitch, magreloadsoundLV,reloadSetting));
            reloadTimer--;
            if(reloadTimer <0){
                magazinerem = magazineMax;
                reloadTimer = reloadSetting;
            }
        }
    }
    public boolean isreloading(){
        return  magazineMax != -1 && magazinerem <= 0;
    }
    public boolean fire(){
        int loopNum = 1;
        if(multicannonPos != null && fireAll){
            currentCannonID = 0;
            loopNum = multicannonPos.length;
        }
        boolean shot = false;
        if (currentEntity == null || triggerFreeze > 0 || (semiActive && missile != null))return true;
        for(int cnt = 0;cnt < loopNum;cnt++) {
            if (cycle_timer < 0 && loopNum == 1) {
                cycle_timer = cycle_setting;
            }else break;
            if (!this.worldObj.isRemote && (bursting || magazineMax == -1 || magazinerem > 0)) {
                Vector3d Vec_transformedbybody;
                if (multicannonPos == null) {
                    Vec_transformedbybody = getGlobalVector_fromLocalVector(cannonPos);
                } else {
                    Vec_transformedbybody = getGlobalVector_fromLocalVector(multicannonPos[currentCannonID]);
                    currentCannonID++;
                    if (currentCannonID >= multicannonPos.length) currentCannonID = 0;
                }
                transformVecforMinecraft(Vec_transformedbybody);
                Vector3d lookVec = getCannonDir();
                transformVecforMinecraft(lookVec);
                if (firesound != null)
                    HMGPacketHandler.INSTANCE.sendToAll(new PacketPlaysound(currentEntity, firesound, firesoundPitch, firesoundLV));
    
                if(!bursting&&magazinerem != 1 && reloadsound != null)HMGPacketHandler.INSTANCE.sendToAll(new PacketPlaysound(currentEntity, reloadsound, reloadsoundPitch, reloadsoundLV,cycle_setting));
                if (!bursting) magazinerem--;
                if (currentEntity.getEntityData().getFloat("GunshotLevel") < 0.1)
                    soundedentity.add(currentEntity);
                currentEntity.getEntityData().setFloat("GunshotLevel", firesoundLV);
                HMGEntityBulletBase[] bullets = null;
                switch (guntype) {
                    case 0:
                    case 4:
                    case 1:
                        bullets = FireBullet(worldObj, currentEntity);
                        break;
                    case 2:
                        bullets = FireBulletGL(worldObj, currentEntity);
                        break;
                    case 3:
                        bullets = FireBulletRPG(worldObj, currentEntity);
                        break;
                    case 5:
                        bullets = FireBulletFrame(worldObj, currentEntity);
                        break;
                    case 6:
                        bullets = FireBulletAP(worldObj, currentEntity);
                        break;
                    case 7:
                        bullets = FireBulletFrag(worldObj, currentEntity);
                        break;
                    case 8:
                        bullets = FireBulletAT(worldObj, currentEntity);
                        break;
                    case 9:
                        bullets = FireBulletTE(worldObj, currentEntity);
                        break;
                    case 10:
                        bullets = FireBulletHE(worldObj, currentEntity);
                        break;
                    case 11:
                        bullets = FireBulletTorp(worldObj, currentEntity);
                        break;
                }
                if (bullets != null) for (HMGEntityBulletBase abullet : bullets) {
                    abullet.gra = (float) (gravity / cfg_defgravitycof);
                    abullet.setLocationAndAngles(
                            pos.x + Vec_transformedbybody.x,
                            pos.y + Vec_transformedbybody.y,
                            -pos.z + Vec_transformedbybody.z,
                            (float) turretrotationYaw, (float) turretrotationPitch);
                    abullet.setThrowableHeading(lookVec.x, lookVec.y, lookVec.z, speed, spread, currentEntity);
                    abullet.canbounce = false;
                    abullet.fuse = fuse;
                    abullet.damageRange = damageRange;
                    abullet.acceleration = acceler;
                    abullet.resistanceinwater = resistanceinwater;
                    if (canHoming) {
                        abullet.induction_precision = induction_precision;
                        abullet.homingEntity = target;
                    }
                    if (semiActive) {
                        missile = abullet;
                    }
//                        System.out.println("currentEntity  " + currentEntity);
                    this.worldObj.spawnEntityInWorld(abullet);
                    if (burstmaxRound != -1) {
                        if (!bursting) bursting = true;
                        burstedRound++;
                        if (burstedRound >= burstmaxRound) {
                            bursting = false;
                            burstedRound = 0;
                        }
                    }
                }
                if (flashName != null) {
                    PacketSpawnParticle flash = new PacketSpawnParticle(
                                                                               pos.x + Vec_transformedbybody.x + lookVec.x * flashoffset,
                                                                               pos.y + Vec_transformedbybody.y + lookVec.y * flashoffset,
                                                                               -pos.z + Vec_transformedbybody.z + lookVec.z * flashoffset,
                                                                               toDegrees(-atan2(lookVec.x, lookVec.z)),
                                                                               toDegrees(-asin(lookVec.y)), 100, flashName, true);
                    flash.fuse = flashfuse;
                    flash.scale = flashscale;
                    flash.id = 100;
                    HMGPacketHandler.INSTANCE.sendToAll(flash);
                }
                shot =  true;
            }
        }
        return shot;
    }
    
    public boolean fireall(){
        boolean flag = this.fire();
        if(fireAll || !flag) {
            for (TurretObj achild : childs) {
                achild.currentEntity = this.currentEntity;
                if(achild.linked_MotherTrigger)achild.fireall();
            }
            for (TurretObj achild : childsOnBarrel) {
                achild.currentEntity = this.currentEntity;
                if(achild.linked_MotherTrigger)achild.fireall();
            }
            for (TurretObj abrothers : brothers) {
                abrothers.currentEntity = this.currentEntity;
                if(abrothers.linked_MotherTrigger)abrothers.fireall();
            }
        }
        return flag && !fireAll;
    }
    public void addchild_triggerLinked(TurretObj child){
        childs.add(child);
    }
    public void addchild_NOTtriggerLinked(TurretObj child){
        child.linked_MotherTrigger = false;
        childs.add(child);
    }
    public void addbrother(TurretObj child){
        brothers.add(child);
    }
    public void  addchildonBarrel(TurretObj child){
        childsOnBarrel.add(child);
    }
    
    public ArrayList<TurretObj> getChilds(){
        return childs;
    }
    public ArrayList<TurretObj> getBrothers(){
        return brothers;
    }
    public ArrayList<TurretObj> getChildsOnBarrel(){
        return childsOnBarrel;
    }
    
    public HMGEntityBulletBase[] FireBullet( World par2World, Entity par3Entity){
        HMGEntityBulletBase[] bulletinstances = new HMGEntityBulletBase[shotgun_pellet];
        for(int i = 0;i < shotgun_pellet ; i++){
            bulletinstances[i] = new HMGEntityBullet(par2World, par3Entity,
                                                            this.powor, speed, spread, bulletmodel);
        }
        return bulletinstances;
    }
    public HMGEntityBulletBase[] FireBulletAP( World par2World, Entity par3Entity){
        HMGEntityBulletBase[] bulletinstances = new HMGEntityBulletBase[shotgun_pellet];
        for(int i = 0;i < shotgun_pellet ; i++){
            bulletinstances[i] = new HMGEntityBullet_AP(par2World, par3Entity,
                                                               this.powor, speed, spread, bulletmodel);
        }
        return bulletinstances;
    }
    public HMGEntityBulletBase[] FireBulletFrag( World par2World, Entity par3Entity){
        if(guntype == 1) {
            HMGEntityBulletBase[] bulletinstances = new HMGEntityBulletBase[1];
            for (int i = 0; i < 1; i++) {
                bulletinstances[i] = new HMGEntityBullet_Frag(par2World, par3Entity,
                                                                     this.powor, speed, spread, bulletmodel);
            }
            return bulletinstances;
        }else {
            HMGEntityBulletBase[] bulletinstances = new HMGEntityBulletBase[shotgun_pellet];
            for(int i = 0;i < shotgun_pellet ; i++){
                bulletinstances[i] = new HMGEntityBullet_Frag(par2World, par3Entity,
                                                                     this.powor, speed, spread, bulletmodel);
            }
            return bulletinstances;
        }
    }
    public HMGEntityBulletBase[] FireBulletAT( World par2World, Entity par3Entity){
        HMGEntityBulletBase[] bulletinstances = new HMGEntityBulletBase[shotgun_pellet];
        for(int i = 0;i < shotgun_pellet ; i++){
            bulletinstances[i] = new HMGEntityBullet_AT(par2World, par3Entity,
                                                               this.powor, speed, spread, bulletmodel);
        }
        return bulletinstances;
    }
    
    
    
    public HMGEntityBulletBase[] FireBulletGL( World par2World, Entity par3Entity){
        HMGEntityBulletBase[] bulletinstances = new HMGEntityBulletBase[shotgun_pellet];
        for(int i = 0;i < shotgun_pellet ; i++){
            bulletinstances[i] = new HMGEntityBulletExprode(par2World, par3Entity,
                                                                   this.powor, speed, spread, ex, canex, bulletmodel);
        }
        return bulletinstances;
    }
    public HMGEntityBulletBase[] FireBulletTE( World par2World, Entity par3Entity){
        HMGEntityBulletBase[] bulletinstances = new HMGEntityBulletBase[shotgun_pellet];
        for(int i = 0;i < shotgun_pellet ; i++){
            bulletinstances[i] = new HMGEntityBullet_TE(par2World, par3Entity,
                                                               this.powor, speed, spread, 2, canex, bulletmodel);
        }
        return bulletinstances;
    }
    public HMGEntityBulletBase[] FireBulletRPG( World par2World, Entity par3Entity){
        HMGEntityBulletBase[] bulletinstances = new HMGEntityBulletBase[shotgun_pellet];
        for(int i = 0;i < shotgun_pellet ; i++){
            bulletinstances[i] = new HMGEntityBulletRocket(par2World, par3Entity,
                                                                  this.powor, speed, spread, ex, canex, bulletmodel);
        }
        return bulletinstances;
    }
    public HMGEntityBulletBase[] FireBulletHE( World par2World, Entity par3Entity){
        HMGEntityBulletBase[] bulletinstances = new HMGEntityBulletBase[shotgun_pellet];
        for(int i = 0;i < shotgun_pellet ; i++){
            bulletinstances[i] = new HMGEntityBullet_HE(par2World, par3Entity,
                                                               this.powor, speed, spread, bulletmodel);
            bulletinstances[i].canex = canex;
            ((HMGEntityBullet_HE)bulletinstances[i]).exlevel = ex;
        }
        return bulletinstances;
    }
    public HMGEntityBulletBase[] FireBulletFrame( World par2World, Entity par3Entity){
        HMGEntityBulletBase[] bulletinstances = new HMGEntityBulletBase[shotgun_pellet];
        for(int i = 0;i < shotgun_pellet ; i++){
            bulletinstances[i] = new HMGEntityBullet_Flame(par2World, par3Entity,
                                                                  this.powor, speed, spread, bulletmodel);
        }
        return bulletinstances;
    }
    public HMGEntityBulletBase[] FireBulletTorp( World par2World, Entity par3Entity){
        HMGEntityBulletBase[] bulletinstances = new HMGEntityBulletBase[shotgun_pellet];
        for(int i = 0;i < shotgun_pellet ; i++){
            bulletinstances[i] = new HMGEntityBulletTorp(par2World, par3Entity,
                                                                this.powor, speed, spread, ex, canex, bulletmodel);
            ((HMGEntityBulletTorp)bulletinstances[i]).draft = torpdraft;
        }
        return bulletinstances;
    }
    
    public boolean aimToEntity(Entity target) {
        Vector3d thisposVec = new Vector3d(pos);
        Vector3d temp = getGlobalVector_fromLocalVector(new Vector3d(cannonPos));
        thisposVec.add(temp);
        
        transformVecforMinecraft(thisposVec);
        Vector3d targetVec = new Vector3d(target.posX, target.posY, target.posZ);
        targetVec.sub(thisposVec);
        targetVec.normalize();
        double[] elevation = CalculateGunElevationAngle(thisposVec.x, thisposVec.y, thisposVec.z, target, gravity, speed);
        if(elevation[2] == -1) {
            return false;
        }else {
            float targetpitch = wrapAngleTo180_float(-(float) elevation[elevationType]);
            float targetyaw = (float) -toDegrees(atan2(targetVec.x, targetVec.z));
    
            return aimtoAngle(targetyaw, targetpitch);
        }
    }
    
    
    public boolean aimToPos(double targetX,double targetY,double targetZ) {
        Vector3d thisposVec = new Vector3d(pos);
        Vector3d temp = getGlobalVector_fromLocalVector(new Vector3d(cannonPos));
        thisposVec.add(temp);
        
        transformVecforMinecraft(thisposVec);
        Vector3d targetVec = new Vector3d(targetX, targetY, targetZ);
    
        targetVec.sub(thisposVec);
        targetVec.normalize();
        double[] elevation = CalculateGunElevationAngle(thisposVec.x, thisposVec.y, thisposVec.z, targetX, targetY, targetZ, gravity, speed);
        if(elevation[2] == -1) {
            return false;
        }else {
            float targetpitch = wrapAngleTo180_float(-(float) CalculateGunElevationAngle(thisposVec.x, thisposVec.y, thisposVec.z, targetX, targetY, targetZ, gravity, speed)[0]);
            float targetyaw = (float) -toDegrees(atan2(targetVec.x, targetVec.z));
        
            return aimtoAngle(targetyaw, targetpitch);
        }
    }
    public void freezTrigger(){
        this.triggerFreeze = 10;
        for(TurretObj child : childs){
            child.freezTrigger();
        }
        for(TurretObj brother : brothers){
            brother.freezTrigger();
        }
    }
    
    @Override
    public IbaseLogic getBaseLogic() {
        return null;
    }
    
    @Override
    public void yourSoundIsremain() {
        traverseSoundRemain = true;
    }
    public String  getsound() {
        return traverseSound;
    }
    public float getsoundPitch() {
        return turretMoving>0?traversesoundPitch:0;
    }
}
