package handmadevehicle.entity.parts.turrets;

import handmadeguns.HMGPacketHandler;
import handmadeguns.HandmadeGunsCore;
import handmadeguns.entity.EntityHasMaster;
import handmadeguns.entity.SpHitCheckEntity;
import handmadeguns.entity.bullets.*;
import handmadeguns.items.guns.HMGItem_Unified_Guns;
import handmadeguns.network.PacketSpawnParticle;
import handmadevehicle.Utils;
import handmadevehicle.entity.EntityDummy_rider;
import handmadevehicle.entity.parts.HasBaseLogic;
import handmadevehicle.entity.parts.HasLoopSound;
import handmadevehicle.entity.parts.logics.BaseLogic;
import handmadevehicle.entity.prefab.Prefab_Turret;
import handmadevehicle.network.HMVPacketHandler;
import handmadevehicle.network.packets.HMVPacketMissileAndLockMarker;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import javax.vecmath.AxisAngle4d;
import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;

import java.util.ArrayList;

import static handmadevehicle.HMVehicle.HMV_Proxy;
import static handmadevehicle.Utils.RotateVectorAroundX;
import static java.lang.Math.*;
import static handmadevehicle.Utils.*;
import static net.minecraft.util.MathHelper.wrapAngleTo180_double;
import static net.minecraft.util.MathHelper.wrapAngleTo180_float;

public class TurretObj implements HasLoopSound{
    public HMGItem_Unified_Guns gunItem = new HMGItem_Unified_Guns();
    public ItemStack gunStack = new ItemStack(gunItem);
    
    public IInventory connectedInventory;
    public int currentCannonID = 0;
    
//    public boolean bursting = false;
    
//    public int cycle_timer;
    
    public double turretrotationYaw;
    public double turretrotationPitch;
    public double targetTurretrotationYaw;
    public double targetTurretrotationPitch;
    public double prevturretrotationYaw;
    public double prevturretrotationPitch;

    public boolean noTraverse = false;
    
    public Quat4d motherRot = new Quat4d();
    public Vector3d motherRotCenter = new Vector3d();
    public Quat4d turretRot = new Quat4d();
    public Quat4d turretRotYaw = new Quat4d();
    public Vector3d onMotherPos = new Vector3d();
    public Vector3d motherPos = new Vector3d();
    public Vector3d pos = new Vector3d();

    
    public float turretMoving = 0;
    
    public boolean traverseSoundRemain;

    public boolean isMother = false;
    TurretObj mother;
    ArrayList<TurretObj> childs = new ArrayList<>();
    ArrayList<TurretObj> brothers = new ArrayList<>();
    ArrayList<TurretObj> childsOnBarrel = new ArrayList<>();
    
    World worldObj;
    public Entity currentEntity;
    public Entity motherEntity;
    
    
    
//    public int magazinerem;
//    public int reloadTimer = -1;
    
    
    private int triggerFreeze = 10;
    private int childFireBlank = 10;

    public ArrayList<HMGEntityBulletBase> missile = new ArrayList<HMGEntityBulletBase>();
    public Entity target;
    public Vec3 lockedBlockPos;
    
    public boolean upDateAim = false;
    public boolean readyaim = false;
    public boolean aimIn = false;
    
    
    public Prefab_Turret prefab_turret;
    public int linkedGunStackID;
//    private int burstedRound;
    
    public TurretObj(World worldObj){
        this.worldObj = worldObj;
        gunItem = new HMGItem_Unified_Guns();
        gunStack = new ItemStack(gunItem);
    }


    public Vector3d getGlobalVector_fromLocalVector(Vector3d local){
        Quat4d turretyawrot = new Quat4d(0,0,0,1);
        Vector3d axisy = Utils.transformVecByQuat(new Vector3d(0,1,0), turretyawrot);
        AxisAngle4d axisyangledy = new AxisAngle4d(axisy, toRadians(180 + turretrotationYaw)/2);
        turretyawrot = Utils.quatRotateAxis(turretyawrot,axisyangledy);


        Quat4d turretpitchrot = new Quat4d(0,0,0,1);
        Vector3d axisx = Utils.transformVecByQuat(new Vector3d(1,0,0), turretpitchrot);
        AxisAngle4d axisyangledp = new AxisAngle4d(axisx, toRadians(turretrotationPitch)/2);
        turretpitchrot = Utils.quatRotateAxis(turretpitchrot,axisyangledp);

        Vector3d global = new Vector3d(local);

        if(this.gunItem != null) {
            global.sub(gunItem.gunInfo.posGetter.turretPitchCenterpos);
            global = transformVecByQuat(global, turretpitchrot);
            global.add(gunItem.gunInfo.posGetter.turretPitchCenterpos);
            global.sub(gunItem.gunInfo.posGetter.turretYawCenterpos);
            global = transformVecByQuat(global, turretyawrot);
            global.add(gunItem.gunInfo.posGetter.turretYawCenterpos);
        }
        global = transformVecByQuat(global, motherRot);

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

        return global;
    }
    public Vector3d getGlobalPos(Vector3d local){
        Quat4d turretyawrot = new Quat4d(0,0,0,1);
        Vector3d axisy = Utils.transformVecByQuat(new Vector3d(0,1,0), turretyawrot);
        AxisAngle4d axisyangledy = new AxisAngle4d(axisy, toRadians(180 + turretrotationYaw)/2);
        turretyawrot = Utils.quatRotateAxis(turretyawrot,axisyangledy);


        Quat4d turretpitchrot = new Quat4d(0,0,0,1);
        Vector3d axisx = Utils.transformVecByQuat(new Vector3d(1,0,0), turretpitchrot);
        AxisAngle4d axisyangledp = new AxisAngle4d(axisx, toRadians(turretrotationPitch)/2);
        turretpitchrot = Utils.quatRotateAxis(turretpitchrot,axisyangledp);

        Vector3d global = new Vector3d(local);

        if(this.gunItem != null) {
            global.sub(gunItem.gunInfo.posGetter.turretYawCenterpos);
            global.sub(gunItem.gunInfo.posGetter.turretPitchCenterpos);
            global = transformVecByQuat(global, turretpitchrot);
            global.add(gunItem.gunInfo.posGetter.turretPitchCenterpos);
            global = transformVecByQuat(global, turretyawrot);
            global.add(gunItem.gunInfo.posGetter.turretYawCenterpos);
        }
        global.sub(motherRotCenter);
        global = transformVecByQuat(global, motherRot);
        global.add(motherRotCenter);
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

        return global;
    }
    public Vector3d getGlobalVector_fromLocalVector_onTurretPoint(Vector3d local){
        if(prefab_turret.useGunSight){
            Vector3d global = new Vector3d(local);
            if (this.gunStack != null && this.gunItem != null) {
                global = seatVec();
            }
            global = transformVecByQuat(global, motherRot);
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
            return global;
        }else {
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

            return getGlobalVector_fromLocalVector_onTurretPointForCannon(local);
        }
    }
    public Vector3d getGlobalVector_fromLocalVector_onTurretPointForCannon(Vector3d local){
        Quat4d turretyawrot = new Quat4d(0, 0, 0, 1);
        Vector3d axisy = new Vector3d(0, 1, 0);
        AxisAngle4d axisyangledy = new AxisAngle4d(axisy, toRadians(turretrotationYaw) / 2);
        turretyawrot = Utils.quatRotateAxis(turretyawrot, axisyangledy);

        Vector3d global = new Vector3d(local);
        if (this.gunItem != null) {
            global.sub(gunItem.gunInfo.posGetter.turretYawCenterpos);
            global = transformVecByQuat(global, turretyawrot);
            global.add(gunItem.gunInfo.posGetter.turretYawCenterpos);
        }
        global = transformVecByQuat(global, motherRot);
        return global;
    }


    public Vector3d seatVec(){
        double[] sightingpos = gunItem.getSeatpos(gunStack);
        Vector3d vec =new Vector3d(-sightingpos[0],sightingpos[1],sightingpos[2]);
        if(gunItem.gunInfo.userOnBarrel) {
            vec.sub(gunItem.gunInfo.posGetter.turretPitchCenterpos);
            RotateVectorAroundX(vec,-turretrotationPitch);
            vec.add(gunItem.gunInfo.posGetter.turretPitchCenterpos);
        }
        vec.sub(gunItem.gunInfo.posGetter.turretYawCenterpos);
        RotateVectorAroundY(vec,180 + turretrotationYaw);
        vec.add(gunItem.gunInfo.posGetter.turretYawCenterpos);
        return vec;
    }
    public Vector3d getGlobalVector_fromLocalVector_onMother(Vector3d local){
        
        local = new Vector3d(local);
        if(mother != null){
            local = mother.getGlobalVector_fromLocalVector_onTurretPoint(local);
            local.add(this.motherPos);
        }else {
            local.sub(this.motherRotCenter);
            local = transformVecByQuat(local, motherRot);
            local.add(this.motherRotCenter);
            local.add(this.motherPos);
        }
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
        
        Vector3d transformedVecYawCenter = transformVecByQuat(new Vector3d(gunItem.gunInfo.posGetter.turretYawCenterpos),motherRot);
        Vector3d transformedVecPitchCenter = transformVecByQuat(new Vector3d(gunItem.gunInfo.posGetter.turretPitchCenterpos),yaw_mother);
        global.sub(transformedVecYawCenter);
        global.sub(transformedVecPitchCenter);
        pitch_yaw_mother.inverse();
        Vector3d transformedGlobalVec = transformVecByQuat(new Vector3d(global),pitch_yaw_mother);
        transformedGlobalVec.add(gunItem.gunInfo.posGetter.turretYawCenterpos);
        transformedGlobalVec.add(gunItem.gunInfo.posGetter.turretPitchCenterpos);
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
    public boolean aimtoAngle(double targetyaw,double targetpitch){
        for(TurretObj achild : childs){
            if(achild.prefab_turret.syncTurretAngle)achild.aimtoAngle(targetyaw,targetpitch);
        }
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
        
        return setTurretTarget(toDegrees(atan2(lookVec.x,lookVec.z)) , toDegrees(asin(lookVec.y)));
    }
    
    public void lock(){
        if(gunItem != null && currentEntity != null) {
            try {
                Vector3d frontVec = getCannonDir();
                transformVecforMinecraft(frontVec);
                frontVec.scale(-1);
                target = null;
                double predeg = -1;
                if(gunItem.gunInfo.canlockEntity) {
                    for (Object obj : worldObj.loadedEntityList) {
                        Entity aEntity = (Entity) obj;
                        if (!aEntity.isDead) {
                            if (aEntity.worldObj == this.worldObj && aEntity.canBeCollidedWith() &&
                                    iscandamageentity(aEntity) &&
                                    (!gunItem.gunInfo.lock_to_Vehicle || aEntity.width > 1.2)) {
                                double distsq = currentEntity.getDistanceSqToEntity(aEntity);
                                if (distsq < 16777216) {
                                    Vector3d totgtvec = new Vector3d(currentEntity.posX - aEntity.posX, currentEntity.posY - aEntity.posY, currentEntity.posZ - aEntity.posZ);
                                    if (totgtvec.length() > 1) {
                                        totgtvec.normalize();
                                        if (totgtvec.y < gunItem.gunInfo.lookDown) {
                                            double deg = wrapAngleTo180_double(toDegrees(totgtvec.angle(frontVec)));
                                            if (gunItem.gunInfo.seekerSize > abs(deg) && (abs(deg) < predeg || predeg == -1)) {
                                                predeg = deg;
                                                target = aEntity;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if(target != null) {
                        if (target.ridingEntity != null) target = target.ridingEntity;
                        if (target instanceof EntityDummy_rider) {
                            target = ((EntityDummy_rider) target).linkedBaseLogic.mc_Entity;
                        }
                        target.getEntityData().setBoolean("behome", true);
                    }
                }
                if(gunItem.gunInfo.canlockBlock){
                    Vec3 vec3 = Vec3.createVectorHelper(currentEntity.posX, currentEntity.posY + currentEntity.getEyeHeight(), currentEntity.posZ);
                    Vec3 playerlook = getMinecraftVecObj(frontVec);

                    playerlook.xCoord *= -1;
                    playerlook.yCoord *= -1;
                    playerlook.zCoord *= -1;

                    playerlook = Vec3.createVectorHelper(playerlook.xCoord * 256, playerlook.yCoord * 256, playerlook.zCoord * 256);

                    Vec3 vec31 = Vec3.createVectorHelper(currentEntity.posX + playerlook.xCoord, currentEntity.posY + currentEntity.getEyeHeight() + playerlook.yCoord, currentEntity.posZ + playerlook.zCoord);
                    MovingObjectPosition movingobjectposition = handmadeguns.Util.Utils.getmovingobjectPosition_forBlock(worldObj,vec3, vec31, false, true, false);//Õ“Ë‚·‚éƒuƒƒbƒN‚ð’²‚×‚é
                    if(movingobjectposition != null && movingobjectposition.hitVec != null){
                        lockedBlockPos = movingobjectposition.hitVec;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (missile != null) {
                ArrayList<HMGEntityBulletBase> tempRemove = new ArrayList<HMGEntityBulletBase>();
                for (HMGEntityBulletBase amissile : missile) {
                    amissile.homingEntity = target;
                    amissile.lockedBlockPos = lockedBlockPos;
                    if(!amissile.isSemiActive){
                        tempRemove.add(amissile);
                    }
                }
                if(lockedBlockPos != null && currentEntity instanceof EntityPlayerMP) {
                    HMVPacketHandler.INSTANCE.sendTo(new HMVPacketMissileAndLockMarker(missile,
                                    new double[]{lockedBlockPos.xCoord,lockedBlockPos.yCoord,lockedBlockPos.zCoord}),
                            (EntityPlayerMP) currentEntity);
                }
                if (currentEntity instanceof EntityPlayerMP && target != null) {
                    HMVPacketHandler.INSTANCE.sendTo(new HMVPacketMissileAndLockMarker(missile, target), (EntityPlayerMP) currentEntity);
                }
                missile.removeAll(tempRemove);
            }
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
    
    public boolean setTurretTarget(double targetyaw, double targetpitch){
        targetTurretrotationYaw = targetyaw;
        targetTurretrotationPitch = targetpitch;
        upDateAim = true;
        return readyaim;
    }
    public void moveTurret(){
        if(gunItem != null && (upDateAim || worldObj.isRemote)) {
            upDateAim = false;
            targetTurretrotationYaw = wrapAngleTo180_double(targetTurretrotationYaw);
            targetTurretrotationPitch = wrapAngleTo180_double(targetTurretrotationPitch);
            turretrotationYaw = wrapAngleTo180_double(turretrotationYaw);
            turretrotationPitch = wrapAngleTo180_double(turretrotationPitch);
            boolean inrange = true;
            if (gunItem.gunInfo.turretanglelimtPitchMax != gunItem.gunInfo.turretanglelimtPitchmin) {
                if (targetTurretrotationPitch > gunItem.gunInfo.turretanglelimtPitchMax) {
                    targetTurretrotationPitch = gunItem.gunInfo.turretanglelimtPitchMax;
                    inrange = false;
                } else if (targetTurretrotationPitch < gunItem.gunInfo.turretanglelimtPitchmin) {
                    targetTurretrotationPitch = gunItem.gunInfo.turretanglelimtPitchmin;
                    inrange = false;
                }
            } else {
                targetTurretrotationPitch = gunItem.gunInfo.turretanglelimtPitchmin;
            }
            if (gunItem.gunInfo.turretanglelimtYawMax != gunItem.gunInfo.turretanglelimtYawmin) {
                if (gunItem.gunInfo.turretanglelimtYawMax < gunItem.gunInfo.turretanglelimtYawmin) {
                    if (targetTurretrotationYaw < 0 && targetTurretrotationYaw > gunItem.gunInfo.turretanglelimtYawMax) {
                        targetTurretrotationYaw = gunItem.gunInfo.turretanglelimtYawMax;
                        inrange = false;
                    } else if (targetTurretrotationYaw > 0 && targetTurretrotationYaw < gunItem.gunInfo.turretanglelimtYawmin) {
                        targetTurretrotationYaw = gunItem.gunInfo.turretanglelimtYawmin;
                        inrange = false;
                    }
                } else {
                    if (targetTurretrotationYaw > gunItem.gunInfo.turretanglelimtYawMax) {
                        targetTurretrotationYaw = gunItem.gunInfo.turretanglelimtYawMax;
                        inrange = false;
                    } else if (targetTurretrotationYaw < gunItem.gunInfo.turretanglelimtYawmin) {
                        targetTurretrotationYaw = gunItem.gunInfo.turretanglelimtYawmin;
                        inrange = false;
                    }
                }
            } else {
                targetTurretrotationYaw = gunItem.gunInfo.turretanglelimtYawmin;
            }
            if (gunItem.gunInfo.turretspeedY == -1) turretrotationYaw = targetTurretrotationYaw;
            if (gunItem.gunInfo.turretspeedP == -1) turretrotationPitch = targetTurretrotationPitch;


            boolean result1 = false;
            if (gunItem.gunInfo.turretspeedY > 0) {
                double AngulardifferenceYaw = targetTurretrotationYaw - this.turretrotationYaw;
                AngulardifferenceYaw = wrapAngleTo180_double(AngulardifferenceYaw);
                if (Double.isNaN(targetTurretrotationYaw)) AngulardifferenceYaw = 0;

                float out_rangeCenter_yaw = (gunItem.gunInfo.turretanglelimtYawMax + gunItem.gunInfo.turretanglelimtYawmin) / 2;
                if (gunItem.gunInfo.turretanglelimtYawMax > gunItem.gunInfo.turretanglelimtYawmin)
                    out_rangeCenter_yaw += 180;
                double AngulardifferenceYaw_toCenter = out_rangeCenter_yaw - this.turretrotationYaw;
                AngulardifferenceYaw_toCenter = wrapAngleTo180_double(AngulardifferenceYaw_toCenter);
                boolean reverse = abs(gunItem.gunInfo.turretanglelimtYawMax) + abs(gunItem.gunInfo.turretanglelimtYawmin) < 360 && (AngulardifferenceYaw < 0) == (AngulardifferenceYaw_toCenter < 0) && (abs(AngulardifferenceYaw) >= abs(AngulardifferenceYaw_toCenter));

                if (reverse) {
                    if (AngulardifferenceYaw > 0) {
                        turretrotationYaw -= gunItem.gunInfo.turretspeedY;
                    } else if (AngulardifferenceYaw < 0) {
                        turretrotationYaw += gunItem.gunInfo.turretspeedY;
                    }
                } else if (AngulardifferenceYaw > gunItem.gunInfo.turretspeedY) {
                    turretrotationYaw += gunItem.gunInfo.turretspeedY;
                } else if (AngulardifferenceYaw < -gunItem.gunInfo.turretspeedY) {
                    turretrotationYaw -= gunItem.gunInfo.turretspeedY;
                } else {
                    this.turretrotationYaw += AngulardifferenceYaw;
                    result1 = true;
                }
            } else {
                result1 = true;
            }

            boolean result2 = false;
            if (gunItem.gunInfo.turretspeedP > 0) {
                double AngulardifferencePitch = targetTurretrotationPitch - this.turretrotationPitch;
                if (Double.isNaN(targetTurretrotationPitch)) AngulardifferencePitch = 0;
                AngulardifferencePitch = wrapAngleTo180_double(AngulardifferencePitch);

                if (AngulardifferencePitch > gunItem.gunInfo.turretspeedP) {
                    turretrotationPitch += gunItem.gunInfo.turretspeedP;
                } else if (AngulardifferencePitch < -gunItem.gunInfo.turretspeedP) {
                    turretrotationPitch -= gunItem.gunInfo.turretspeedP;
                } else {
                    this.turretrotationPitch += AngulardifferencePitch;
                    result2 = true;
                }
                turretRot = new Quat4d(0, 0, 0, 1);


                Vector3d axisY = transformVecByQuat(unitY, turretRot);
                AxisAngle4d axisxangledY = new AxisAngle4d(axisY, toRadians(turretrotationYaw) / 2);
                turretRot = quatRotateAxis(turretRot, axisxangledY);

                Vector3d axisX = transformVecByQuat(unitX, turretRot);
                AxisAngle4d axisxangledX = new AxisAngle4d(axisX, toRadians(-turretrotationPitch) / 2);
                turretRot = quatRotateAxis(turretRot, axisxangledX);
            } else {
                result2 = true;
            }
            boolean canfire = false;
            if (prefab_turret.fireRists != null) {
                for (FireRist afirRist : prefab_turret.fireRists) {
                    boolean temp = true;
                    if (targetTurretrotationYaw > afirRist.YawMax) {
                        temp = false;
                    } else if (targetTurretrotationYaw < afirRist.YawMin) {
                        temp = false;
                    }

                    if (targetTurretrotationPitch > afirRist.PitchMax) {
                        temp = false;
                    } else if (targetTurretrotationPitch < afirRist.PitchMin) {
                        temp = false;
                    }
                    canfire |= temp;
                }
            } else canfire = true;
            aimIn = inrange;

            while (this.turretrotationYaw - this.prevturretrotationYaw < -180.0F) {
                this.prevturretrotationYaw -= 360.0F;
            }

            while (this.turretrotationYaw - this.prevturretrotationYaw >= 180.0F) {
                this.prevturretrotationYaw += 360.0F;
            }

            readyaim = result1 && result2 && inrange && canfire;
        }else {
            readyaim = false;
        }
    }
    boolean isOnBarrel = false;
    public void calculatePos(){
        Vector3d tempOnMotherPos = new Vector3d(onMotherPos);
        if(!prefab_turret.positionLinked && mother != null){

            System.out.println("mother pos " + mother.onMotherPos);
            System.out.println("child  pos " + this.onMotherPos);
            tempOnMotherPos.sub(mother.onMotherPos);
        }
        if(gunItem != null)tempOnMotherPos.y += gunItem.gunInfo.yoffset;
        if(isOnBarrel) {
            tempOnMotherPos.z *= -1;
            this.pos.set(mother.getGlobalPos(tempOnMotherPos));
            this.pos.add(motherPos);
        }
        else {
            this.pos = getGlobalVector_fromLocalVector_onMother(tempOnMotherPos);
        }
    }
    public void update(Quat4d motherRot,Vector3d motherPos){
        prevturretrotationYaw = turretrotationYaw;
        prevturretrotationPitch = turretrotationPitch;
        moveTurret();
        if(motherEntity instanceof HasBaseLogic) {
            connectedInventory = ((HasBaseLogic) motherEntity).getBaseLogic().inventoryVehicle;
        }
        if(!prefab_turret.needGunStack) {
            if (gunItem == null) gunItem = new HMGItem_Unified_Guns();
            if (gunStack == null) gunStack = new ItemStack(gunItem);
            gunItem.gunInfo = prefab_turret.gunInfo;
        }else {
            gunStack = connectedInventory.getStackInSlot(linkedGunStackID);
            if(gunStack != null && gunStack.getItem() instanceof HMGItem_Unified_Guns){
                gunItem = (HMGItem_Unified_Guns) gunStack.getItem();
                if (gunStack.stackSize < 0) {
                    gunStack = null;
                    gunItem = null;
                }
            }else {
                gunStack = null;
                gunItem = null;
            }
        }
        if(currentEntity == null)
            triggerFreeze = 10;
        if(worldObj.isRemote && gunItem != null) {
            double deltaRotYaw = prevturretrotationYaw - turretrotationYaw;
            double deltaRotPitch = prevturretrotationPitch - turretrotationPitch;
            turretMoving += ((float) sqrt(deltaRotPitch * deltaRotPitch + deltaRotYaw * deltaRotYaw) - turretMoving) * 0.3f;
            if(turretMoving < min(gunItem.gunInfo.turretspeedY,gunItem.gunInfo.turretspeedP)/10)turretMoving = 0;
            if (gunItem.gunInfo.turretspeedY >0 && gunItem.gunInfo.turretspeedP >0 && !traverseSoundRemain && currentEntity != null && prefab_turret.traverseSound != null && (turretMoving > 0)) {
                HMV_Proxy.playsoundasTurret(prefab_turret.traversesoundLV*4, this);
            }
            traverseSoundRemain = false;
        }
        this.motherRot = motherRot;
        this.motherPos = motherPos;
        triggerFreeze--;
        if(readytoFire())childFireBlank=prefab_turret.childFireBlank;
        else childFireBlank--;
        if(!worldObj.isRemote){
            lock();
        }
        if(gunItem != null && gunItem.gunInfo.semiActive){
            ArrayList<HMGEntityBulletBase> forRemove = null;
            for (HMGEntityBulletBase amissile:missile) {
                if (amissile != null && amissile.isDead){
                    if(forRemove == null)forRemove = new ArrayList<HMGEntityBulletBase>();
                    forRemove.add(amissile);
                }
            }
            if(forRemove != null)missile.removeAll(forRemove);
        }
        calculatePos();
        turretRot = new Quat4d(0,0,0,1);
        Vector3d axisY = transformVecByQuat(unitY, turretRot);
        AxisAngle4d axisxangledY = new AxisAngle4d(axisY, toRadians(turretrotationYaw)/2);
        turretRot = quatRotateAxis(turretRot,axisxangledY);
        turretRotYaw.set(turretRot);

        for(TurretObj abrother :brothers){
            abrother.update(this.motherRot, this.motherPos);
        }

        Quat4d temp = new Quat4d(this.motherRot);
        temp.mul(turretRot);
        for(TurretObj achild :childs){
            achild.motherEntity = this.motherEntity;
            achild.mother = this;
            achild.update(temp, pos);
        }
        
        Vector3d axisX = transformVecByQuat(unitX, turretRot);
        AxisAngle4d axisxangledX = new AxisAngle4d(axisX, toRadians(-turretrotationPitch)/2);
        turretRot = quatRotateAxis(turretRot,axisxangledX);
    
        temp = new Quat4d(this.motherRot);
        temp.mul(turretRot);
        for(TurretObj achild :childsOnBarrel){
            achild.motherEntity = this.motherEntity;
            achild.mother = this;
            achild.isOnBarrel = true;
            achild.update(temp, this.pos);
        }
        if(gunStack != null) {
            getDummyStackTag().setBoolean("IsTurretStack", true);
            gunItem.onUpdate_fromTurret(gunStack, worldObj, currentEntity, linkedGunStackID == -1 ? -10:linkedGunStackID, true, this);
            if(getDummyStackTag() != null)getDummyStackTag().setBoolean("IsTurretStack", false);
        }
        this.currentEntity = null;
//        if(!worldObj.isRemote) {
//            cycle_timer--;
//            if (!worldObj.isRemote) readyload = ((max_Bullet() == -1 || magazinerem > 0));
//            if (magazinerem <= 0 && prefab_turret.gunInfo.reloadTimes[0] != -1) {
//                if (reloadTimer == prefab_turret.gunInfo.reloadTimes[0])
//                    if (currentEntity != null && prefab_turret.gunInfo.soundre != null)
//                        HMGPacketHandler.INSTANCE.sendToAll(new PacketPlaysound(currentEntity, prefab_turret.gunInfo.soundre[0], prefab_turret.gunInfo.soundrespeed, prefab_turret.gunInfo.soundrelevel, prefab_turret.gunInfo.reloadTimes[0]));
//                reloadTimer--;
//                if (reloadTimer < 0) {
//                    magazinerem = max_Bullet();
//                    reloadTimer = prefab_turret.gunInfo.reloadTimes[0];
//                }
//            }
//        }
    }
    public NBTTagCompound getDummyStackTag(){
        if(gunStack == null)return null;
        if(gunStack.getItem() instanceof HMGItem_Unified_Guns) {
            gunItem = (HMGItem_Unified_Guns) gunStack.getItem();
            if (gunStack.getTagCompound() == null) gunItem.checkTags(gunStack);
            return gunStack.getTagCompound();
        }
        gunStack = null;
        return null;
    }
    public boolean isreloading(){
        return getDummyStackTag() == null || getDummyStackTag().getBoolean("IsReloading");
    }
    public boolean isLoading(){
        return getDummyStackTag() == null || !getDummyStackTag().getBoolean("Recoiled");
    }
    public boolean readytoFire(){
        return  !isreloading() && !isLoading();
    }
    public int getSyncroFireNum(){
	    if(gunItem.gunInfo.posGetter.multicannonPos != null && prefab_turret.fireAll_cannon){
		    currentCannonID = 0;
		    return gunItem.gunInfo.posGetter.multicannonPos.length;
	    }
	    return 1;
    }
    public boolean fire(){
        if(triggerFreeze>0)return false;
	    if(getDummyStackTag() != null){
	        getDummyStackTag().setBoolean("IsTriggered", true);
        }
//        int loopNum = 1;
//        if(prefab_turret.multicannonPos != null && prefab_turret.fireAll_cannon){
//            currentCannonID = 0;
//            loopNum = prefab_turret.multicannonPos.length;
//        }
//        boolean shot = false;
//        if (currentEntity == null || triggerFreeze > 0 || (prefab_turret.gunInfo.semiActive && missile != null))return true;
//        if (!this.worldObj.isRemote) {
//            for(int cnt = 0;cnt < loopNum;cnt++) {
//                Vector3d Vec_transformedbybody;
//                if (prefab_turret.multicannonPos == null) {
//                    Vec_transformedbybody = getGlobalVector_fromLocalVector(prefab_turret.cannonPos);
//                } else {
//                    Vec_transformedbybody = getGlobalVector_fromLocalVector(prefab_turret.multicannonPos[currentCannonID]);
//                    currentCannonID++;
//                    if (currentCannonID >= prefab_turret.multicannonPos.length) currentCannonID = 0;
//                }
//                transformVecforMinecraft(Vec_transformedbybody);
//                Vector3d lookVec = getCannonDir();
//                transformVecforMinecraft(lookVec);
//                if (prefab_turret.gunInfo.soundbase != null)
//                    HMGPacketHandler.INSTANCE.sendToAll(new PacketPlaysound(currentEntity, prefab_turret.gunInfo.soundbase, prefab_turret.gunInfo.soundspeed, prefab_turret.gunInfo.soundbaselevel));
//
//                if (currentEntity.getEntityData().getFloat("GunshotLevel") < 0.1)
//                    HMG_proxy.playerSounded(currentEntity);
//                currentEntity.getEntityData().setFloat("GunshotLevel", prefab_turret.gunInfo.soundbaselevel);
//                HMGEntityBulletBase[] bullets = null;
//                switch (prefab_turret.gunInfo.guntype) {
//                    case 0:
//                    case 4:
//                    case 1:
//                        bullets = FireBullet(worldObj, currentEntity);
//                        break;
//                    case 2:
//                        bullets = FireBulletGL(worldObj, currentEntity);
//                        break;
//                    case 3:
//                        bullets = FireBulletRPG(worldObj, currentEntity);
//                        break;
//                    case 5:
//                        bullets = FireBulletFrame(worldObj, currentEntity);
//                        break;
//                    case 6:
//                        bullets = FireBulletAP(worldObj, currentEntity);
//                        break;
//                    case 7:
//                        bullets = FireBulletFrag(worldObj, currentEntity);
//                        break;
//                    case 8:
//                        bullets = FireBulletAT(worldObj, currentEntity);
//                        break;
//                    case 9:
//                        bullets = FireBulletTE(worldObj, currentEntity);
//                        break;
//                    case 10:
//                        bullets = FireBulletHE(worldObj, currentEntity);
//                        break;
//                    case 11:
//                        bullets = FireBulletTorp(worldObj, currentEntity);
//                        break;
//                }
//                if (bullets != null) for (HMGEntityBulletBase abullet : bullets) {
//                    abullet.gra = (float) (prefab_turret.gunInfo.gravity / cfg_defgravitycof);
//                    abullet.setLocationAndAngles(
//                            pos.x + Vec_transformedbybody.x,
//                            pos.y + Vec_transformedbybody.y,
//                            -pos.z + Vec_transformedbybody.z,
//                            (float) turretrotationYaw, (float) turretrotationPitch);
//                    abullet.setThrowableHeading(lookVec.x, lookVec.y, lookVec.z, prefab_turret.gunInfo.speed, prefab_turret.gunInfo.spread_setting, currentEntity);
//                    abullet.canbounce = false;
//                    abullet.fuse = prefab_turret.gunInfo.fuse;
//                    abullet.damageRange = prefab_turret.gunInfo.damagerange;
//                    abullet.acceleration = prefab_turret.gunInfo.acceleration;
//                    abullet.resistanceinwater = prefab_turret.gunInfo.resistanceinWater;
//                    if (prefab_turret.gunInfo.canlock) {
//                        abullet.induction_precision = prefab_turret.gunInfo.induction_precision;
//                        abullet.homingEntity = target;
//                    }
//                    if (prefab_turret.gunInfo.semiActive) {
//                        missile = abullet;
//                    }
////                        System.out.println("currentEntity  " + currentEntity);
//                    this.worldObj.spawnEntityInWorld(abullet);
//                }
//                shot =  true;
//            }
//        }
        return readytoFire();
    }
    public Vector3d forAim_getCannonPos(){
        Vector3d cannonpos;
        if (gunItem.gunInfo.posGetter.multicannonPos == null) {
            cannonpos = getGlobalVector_fromLocalVector(gunItem.gunInfo.posGetter.cannonPos);
        } else {
            cannonpos = getGlobalVector_fromLocalVector(gunItem.gunInfo.posGetter.multicannonPos[currentCannonID]);
        }
        transformVecforMinecraft(cannonpos);

        cannonpos.add(pos);
        Vector3d lookVec = getCannonDir();
        transformVecforMinecraft(lookVec);
        transformVecforMinecraft(cannonpos);
        return cannonpos;
    }
    public Vector3d forAim_getCannonPosGlobal(){
        if(gunItem != null) {
            Vector3d cannonPos;
            if (gunItem.gunInfo.posGetter.multicannonPos == null) {
                cannonPos = getGlobalVector_fromLocalVector(gunItem.gunInfo.posGetter.cannonPos);
            } else {
                cannonPos = getGlobalVector_fromLocalVector(gunItem.gunInfo.posGetter.multicannonPos[currentCannonID]);
                currentCannonID++;
                if (currentCannonID >= gunItem.gunInfo.posGetter.multicannonPos.length) currentCannonID = 0;
            }
            cannonPos.add(pos);
            transformVecforMinecraft(cannonPos);
            return cannonPos;
        }return new Vector3d();
    }
    public void setBulletsPos(HMGEntityBulletBase[] bullets) {
	    Vector3d cannonPos;
	    if (gunItem.gunInfo.posGetter.multicannonPos == null) {
		    cannonPos = getGlobalVector_fromLocalVector(gunItem.gunInfo.posGetter.cannonPos);
	    } else {
		    cannonPos = getGlobalVector_fromLocalVector(gunItem.gunInfo.posGetter.multicannonPos[currentCannonID]);
		    currentCannonID++;
		    if (currentCannonID >= gunItem.gunInfo.posGetter.multicannonPos.length) currentCannonID = 0;
	    }
	    cannonPos.add(pos);
	    Vector3d lookVec = getCannonDir();
	    transformVecforMinecraft(lookVec);
	    transformVecforMinecraft(cannonPos);
        if(motherEntity instanceof HasBaseLogic){
            BaseLogic linkedBaseLogic = ((HasBaseLogic) motherEntity).getBaseLogic();
            if(linkedBaseLogic.prefab_vehicle.recoilResist != 0) {
                Vector3d centerOfGravity = new Vector3d(linkedBaseLogic.prefab_vehicle.center_of_gravity);
                Vector3d localBulletPos = new Vector3d();
                Vector3d relativeCannonPos = new Vector3d(cannonPos);
                relativeCannonPos.sub(new Vector3d(motherEntity.posX,motherEntity.posY,motherEntity.posZ));

                Vector3d localBulletMotion = new Vector3d();
                getVector_local_inRotatedObj(relativeCannonPos, localBulletPos, linkedBaseLogic.bodyRot);
                getVector_local_inRotatedObj(lookVec, localBulletMotion, linkedBaseLogic.bodyRot);
//                System.out.println("localBulletPos" + localBulletPos);
                localBulletPos.sub(centerOfGravity);

                localBulletMotion.normalize();
                Vector3d recoilRotVector = new Vector3d();
                recoilRotVector.cross(localBulletPos, localBulletMotion);
                recoilRotVector.normalize();
                AxisAngle4d recoilRotor = new AxisAngle4d(recoilRotVector, -gunItem.gunInfo.recoil/linkedBaseLogic.prefab_vehicle.recoilResist * recoilRotVector.length());
                linkedBaseLogic.rotationmotion = Utils.quatRotateAxis(linkedBaseLogic.rotationmotion, recoilRotor);
            }
        }
	    for (HMGEntityBulletBase abullet : bullets) {
            abullet.setPosition(cannonPos.x,cannonPos.y,cannonPos.z);
		    abullet.setThrowableHeading(lookVec.x, lookVec.y, lookVec.z, gunItem.gunInfo.speed, gunItem.gunInfo.spread_setting, motherEntity);
            abullet.prevRotationYaw = abullet.rotationYaw = (float)(atan2(lookVec.x, lookVec.z) * 180.0D / Math.PI);
            abullet.prevRotationPitch = abullet.rotationPitch = (float)(atan2(lookVec.y, (double)sqrt(lookVec.x * lookVec.x + lookVec.z * lookVec.z)) * 180.0D / Math.PI);
		    if(abullet.homingEntity != null)missile.add(abullet);
	    }
	    {
		    if(gunItem.gunInfo.flashname != null){
                PacketSpawnParticle flash = new PacketSpawnParticle(
                        cannonPos.x + lookVec.x * prefab_turret.flashoffset,
                        cannonPos.y + lookVec.y * prefab_turret.flashoffset,
                        cannonPos.z + lookVec.z * prefab_turret.flashoffset,
                        toDegrees(-atan2(lookVec.x, lookVec.z)),
                        toDegrees(-asin(lookVec.y)), 100, gunItem.gunInfo.flashname, true);
                flash.fuse = gunItem.gunInfo.flashfuse;
                flash.scale = gunItem.gunInfo.flashScale;
                flash.id = 100;
                HMGPacketHandler.INSTANCE.sendToAll(flash);
		    }else {
                PacketSpawnParticle flash = new PacketSpawnParticle(
                        cannonPos.x + lookVec.x * prefab_turret.flashoffset,
                        cannonPos.y + lookVec.y * prefab_turret.flashoffset,
                        cannonPos.z + lookVec.z * prefab_turret.flashoffset,
                        toDegrees(-atan2(lookVec.x, lookVec.z)),
                        toDegrees(-asin(lookVec.y)), 0,100);
                flash.fuse = gunItem.gunInfo.flashfuse;
                flash.scale = gunItem.gunInfo.flashScale;
                flash.id = 100;
                HMGPacketHandler.INSTANCE.sendToAll(flash);
		    }
        }
    }
    public int max_Bullet(){
        return gunItem.max_Bullet(gunStack);
        
    }
//    public boolean currentMagzine_has_roundOption(){
//        Item currentmagazine = prefab_turret.gunInfo.magazine[0];
//        if(currentmagazine instanceof HMGItemCustomMagazine){
//            return ((HMGItemCustomMagazine) currentmagazine).hasRoundOption;
//        }
//        return false;
//    }
    
    public boolean fireall(){
        boolean flag = this.fire();
        breakpoint:if(prefab_turret.fireAll_child || (childFireBlank < 0 && (!flag))) {
            for (TurretObj achild : childs) {
                if(prefab_turret.fireAll_child){
                    achild.triggerFreeze = this.triggerFreeze;
                }
                achild.currentEntity = this.currentEntity;
                if(achild.prefab_turret.linked_MotherTrigger && achild.fireall()){
                    flag = true;
                    if(!prefab_turret.fireAll_child)break breakpoint;
                }
            }
            for (TurretObj achild : childsOnBarrel) {
                if(prefab_turret.fireAll_child){
                    achild.triggerFreeze = this.triggerFreeze;
                }
                achild.currentEntity = this.currentEntity;
                if(achild.prefab_turret.linked_MotherTrigger && achild.fireall()){
                    flag = true;
                    if(!prefab_turret.fireAll_child)break breakpoint;
                }
            }
            for (TurretObj abrothers : brothers) {
                if(prefab_turret.fireAll_child){
                    abrothers.triggerFreeze = this.triggerFreeze;
                }
                abrothers.currentEntity = this.currentEntity;
                if(abrothers.prefab_turret.linked_MotherTrigger && abrothers.fireall()){
                    flag = true;
                    if(!prefab_turret.fireAll_child)break breakpoint;
                }
            }
        }
        if(flag)childFireBlank = prefab_turret.childFireBlank;
        return flag;
    }

    public TurretObj getAvailableTurret(){
        if(!isreloading()){
            return this;
        }

        for (TurretObj achild : childs) {
            achild.currentEntity = this.currentEntity;
            if(!achild.isreloading()){
                return achild;
            }
        }
        for (TurretObj achild : childsOnBarrel) {
            achild.currentEntity = this.currentEntity;
            if(!achild.isreloading()){
                return achild;
            }
        }
        for (TurretObj abrothers : brothers) {
            abrothers.currentEntity = this.currentEntity;
            if(!abrothers.isreloading()){
                return abrothers;
            }
        }
        return null;
    }
    public void addchild_triggerLinked(TurretObj child){
        child.motherRotCenter = new Vector3d(this.prefab_turret.gunInfo.posGetter.turretYawCenterpos);
        childs.add(child);

    }
//    public void addchild_NOTtriggerLinked(TurretObj child){
//        child.prefab_turret.linked_MotherTrigger = false;
//        if(!child.prefab_turret.positionLinked){
//            child.onMotherPos.sub(this.onMotherPos);
//        }
//        childs.add(child);
//    }
    public void addbrother(TurretObj child){
        brothers.add(child);
    }
    public void  addchildonBarrel(TurretObj child){
        child.motherRotCenter = new Vector3d(this.prefab_turret.gunInfo.posGetter.turretYawCenterpos);
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
//
//    public HMGEntityBulletBase[] FireBullet( World par2World, Entity par3Entity){
//        HMGEntityBulletBase[] bulletinstances = new HMGEntityBulletBase[prefab_turret.gunInfo.pellet];
//        for(int i = 0;i < prefab_turret.gunInfo.pellet ; i++){
//            bulletinstances[i] = new HMGEntityBullet(par2World, par3Entity,
//                                                            prefab_turret.gunInfo.power, prefab_turret.gunInfo.speed, prefab_turret.gunInfo.spread_setting, prefab_turret.gunInfo.bulletmodelN);
//        }
//        return bulletinstances;
//    }
//    public HMGEntityBulletBase[] FireBulletAP( World par2World, Entity par3Entity){
//        HMGEntityBulletBase[] bulletinstances = new HMGEntityBulletBase[prefab_turret.gunInfo.pellet];
//        for(int i = 0;i < prefab_turret.gunInfo.pellet ; i++){
//            bulletinstances[i] = new HMGEntityBullet_AP(par2World, par3Entity,
//                                                               prefab_turret.gunInfo.power, prefab_turret.gunInfo.speed, prefab_turret.gunInfo.spread_setting, prefab_turret.gunInfo.bulletmodelN);
//        }
//        return bulletinstances;
//    }
//    public HMGEntityBulletBase[] FireBulletFrag( World par2World, Entity par3Entity){
//        if(prefab_turret.gunInfo.guntype == 1) {
//            HMGEntityBulletBase[] bulletinstances = new HMGEntityBulletBase[1];
//            for (int i = 0; i < 1; i++) {
//                bulletinstances[i] = new HMGEntityBullet_Frag(par2World, par3Entity,
//                                                                     prefab_turret.gunInfo.power, prefab_turret.gunInfo.speed, prefab_turret.gunInfo.spread_setting, prefab_turret.gunInfo.bulletmodelN);
//            }
//            return bulletinstances;
//        }else {
//            HMGEntityBulletBase[] bulletinstances = new HMGEntityBulletBase[prefab_turret.gunInfo.pellet];
//            for(int i = 0;i < prefab_turret.gunInfo.pellet ; i++){
//                bulletinstances[i] = new HMGEntityBullet_Frag(par2World, par3Entity,
//                                                                     prefab_turret.gunInfo.power, prefab_turret.gunInfo.speed, prefab_turret.gunInfo.spread_setting, prefab_turret.gunInfo.bulletmodelN);
//            }
//            return bulletinstances;
//        }
//    }
//    public HMGEntityBulletBase[] FireBulletAT( World par2World, Entity par3Entity){
//        HMGEntityBulletBase[] bulletinstances = new HMGEntityBulletBase[prefab_turret.gunInfo.pellet];
//        for(int i = 0;i < prefab_turret.gunInfo.pellet ; i++){
//            bulletinstances[i] = new HMGEntityBullet_AT(par2World, par3Entity,
//                                                               prefab_turret.gunInfo.power, prefab_turret.gunInfo.speed, prefab_turret.gunInfo.spread_setting, prefab_turret.gunInfo.bulletmodelN);
//        }
//        return bulletinstances;
//    }
//
//
//
//    public HMGEntityBulletBase[] FireBulletGL( World par2World, Entity par3Entity){
//        HMGEntityBulletBase[] bulletinstances = new HMGEntityBulletBase[prefab_turret.gunInfo.pellet];
//        for(int i = 0;i < prefab_turret.gunInfo.pellet ; i++){
//            bulletinstances[i] = new HMGEntityBulletExprode(par2World, par3Entity,
//                                                                   prefab_turret.gunInfo.power, prefab_turret.gunInfo.speed, prefab_turret.gunInfo.spread_setting, prefab_turret.gunInfo.ex, prefab_turret.gunInfo.destroyBlock, prefab_turret.gunInfo.bulletmodelN);
//        }
//        return bulletinstances;
//    }
//    public HMGEntityBulletBase[] FireBulletTE( World par2World, Entity par3Entity){
//        HMGEntityBulletBase[] bulletinstances = new HMGEntityBulletBase[prefab_turret.gunInfo.pellet];
//        for(int i = 0;i < prefab_turret.gunInfo.pellet ; i++){
//            bulletinstances[i] = new HMGEntityBullet_TE(par2World, par3Entity,
//                                                               prefab_turret.gunInfo.power, prefab_turret.gunInfo.speed, prefab_turret.gunInfo.spread_setting, 2, prefab_turret.gunInfo.destroyBlock, prefab_turret.gunInfo.bulletmodelN);
//        }
//        return bulletinstances;
//    }
//    public HMGEntityBulletBase[] FireBulletRPG( World par2World, Entity par3Entity){
//        HMGEntityBulletBase[] bulletinstances = new HMGEntityBulletBase[prefab_turret.gunInfo.pellet];
//        for(int i = 0;i < prefab_turret.gunInfo.pellet ; i++){
//            bulletinstances[i] = new HMGEntityBulletRocket(par2World, par3Entity,
//                                                                  prefab_turret.gunInfo.power, prefab_turret.gunInfo.speed, prefab_turret.gunInfo.spread_setting, prefab_turret.gunInfo.ex, prefab_turret.gunInfo.destroyBlock, prefab_turret.gunInfo.bulletmodelN);
//        }
//        return bulletinstances;
//    }
//    public HMGEntityBulletBase[] FireBulletHE( World par2World, Entity par3Entity){
//        HMGEntityBulletBase[] bulletinstances = new HMGEntityBulletBase[prefab_turret.gunInfo.pellet];
//        for(int i = 0;i < prefab_turret.gunInfo.pellet ; i++){
//            bulletinstances[i] = new HMGEntityBullet_HE(par2World, par3Entity,
//                                                               prefab_turret.gunInfo.power, prefab_turret.gunInfo.speed, prefab_turret.gunInfo.spread_setting, prefab_turret.gunInfo.bulletmodelN);
//            bulletinstances[i].canex = prefab_turret.gunInfo.destroyBlock;
//            ((HMGEntityBullet_HE)bulletinstances[i]).exlevel = prefab_turret.gunInfo.ex;
//        }
//        return bulletinstances;
//    }
//    public HMGEntityBulletBase[] FireBulletFrame( World par2World, Entity par3Entity){
//        HMGEntityBulletBase[] bulletinstances = new HMGEntityBulletBase[prefab_turret.gunInfo.pellet];
//        for(int i = 0;i < prefab_turret.gunInfo.pellet ; i++){
//            bulletinstances[i] = new HMGEntityBullet_Flame(par2World, par3Entity,
//                                                                  prefab_turret.gunInfo.power, prefab_turret.gunInfo.speed, prefab_turret.gunInfo.spread_setting, prefab_turret.gunInfo.bulletmodelN);
//        }
//        return bulletinstances;
//    }
//    public HMGEntityBulletBase[] FireBulletTorp( World par2World, Entity par3Entity){
//        HMGEntityBulletBase[] bulletinstances = new HMGEntityBulletBase[prefab_turret.gunInfo.pellet];
//        for(int i = 0;i < prefab_turret.gunInfo.pellet ; i++){
//            bulletinstances[i] = new HMGEntityBulletTorp(par2World, par3Entity,
//                                                                prefab_turret.gunInfo.power, prefab_turret.gunInfo.speed, prefab_turret.gunInfo.spread_setting, prefab_turret.gunInfo.ex, prefab_turret.gunInfo.destroyBlock, prefab_turret.gunInfo.bulletmodelN);
//            ((HMGEntityBulletTorp)bulletinstances[i]).draft = prefab_turret.gunInfo.torpdraft;
//        }
//        return bulletinstances;
//    }
    
    public boolean aimToEntity(Entity target) {
        Vector3d thisposVec = new Vector3d(pos);
        Vector3d temp = getGlobalVector_fromLocalVector(new Vector3d(gunItem.gunInfo.posGetter.cannonPos));
        thisposVec.add(temp);
        
        transformVecforMinecraft(thisposVec);
        Vector3d targetVec = new Vector3d(target.posX, target.posY, target.posZ);
        targetVec.sub(thisposVec);
        targetVec.normalize();
        double[] elevation = CalculateGunElevationAngle(thisposVec.x, thisposVec.y, thisposVec.z, target, gunItem.gunInfo.gravity * (float)HandmadeGunsCore.cfg_defgravitycof, gunItem.gunInfo.speed);
        if(elevation[2] == -1) {
            return false;
        }else {
            float targetpitch = wrapAngleTo180_float(-(float) elevation[prefab_turret.elevationType]);
            float targetyaw = (float) -toDegrees(atan2(targetVec.x, targetVec.z));
    
            return aimtoAngle(targetyaw, targetpitch);
        }
    }
    
    
    public boolean aimToPos(double targetX,double targetY,double targetZ) {
        Vector3d thisposVec = forAim_getCannonPosGlobal();
        Vector3d targetVec = new Vector3d(targetX, targetY, targetZ);

        targetVec.sub(thisposVec);
        targetVec.normalize();
        if(gunItem != null) {
            double[] elevation = CalculateGunElevationAngle(thisposVec.x, thisposVec.y, thisposVec.z, targetX, targetY, targetZ, gunItem.gunInfo.gravity * (float)HandmadeGunsCore.cfg_defgravitycof, gunItem.gunInfo.speed);
            if (elevation[2] == -1) {
                return false;
            } else {
                float targetpitch = wrapAngleTo180_float(-(float) elevation[0]);
                float targetyaw = (float) -toDegrees(atan2(targetVec.x, targetVec.z));

                return aimtoAngle(targetyaw, targetpitch);
            }
        }else return false;
    }
    public void freezTrigger(){
        this.freezTrigger(10);
    }
    public void freezTrigger(int time){
        this.triggerFreeze = time;
        for(TurretObj child : childs){
            child.freezTrigger(time);
        }
        for(TurretObj brother : brothers){
            brother.freezTrigger(time);
        }
    }
    
    @Override
    public void yourSoundIsremain(String playingSound) {
        traverseSoundRemain = true;
    }
    public String  getsound() {
        return prefab_turret.traverseSound;
    }
    public float getsoundPitch() {
        return turretMoving>0?prefab_turret.traversesoundPitch:0;
    }
    
    
    public void saveToTag(NBTTagCompound tagCompound,int id){
        NBTTagCompound temp = new NBTTagCompound();
        temp.setDouble("turretrotationYaw",turretrotationYaw);
        temp.setDouble("turretrotationPitch",turretrotationPitch);

        if(motherEntity instanceof HasBaseLogic) {
            connectedInventory = ((HasBaseLogic) motherEntity).getBaseLogic().inventoryVehicle;
        }
        if(prefab_turret.needGunStack)gunStack = connectedInventory.getStackInSlot(linkedGunStackID);

        if(gunStack != null && gunStack.getItem() instanceof HMGItem_Unified_Guns) {
            if (getDummyStackTag() != null) temp.setTag("DummysTag", getDummyStackTag());
            temp.setInteger("DummysDamage", gunStack.getItemDamage());
            temp.setInteger("DummysStackSize", gunStack.stackSize);
            gunItem = (HMGItem_Unified_Guns) gunStack.getItem();
        }
        tagCompound.setTag("turret" + id,temp);
    }
    public void readFromTag(NBTTagCompound tagCompound,int id){
        NBTTagCompound temp = tagCompound.getCompoundTag("turret" + id);
        turretrotationYaw = temp.getDouble("turretrotationYaw");
        turretrotationPitch = temp.getDouble("turretrotationPitch");

        if(motherEntity instanceof HasBaseLogic) {
            connectedInventory = ((HasBaseLogic) motherEntity).getBaseLogic().inventoryVehicle;
        }
        if(prefab_turret.needGunStack)gunStack = connectedInventory.getStackInSlot(linkedGunStackID);

        if(gunStack != null && gunStack.getItem() instanceof HMGItem_Unified_Guns) {
            gunStack.setTagCompound(temp.getCompoundTag("DummysTag"));
            gunStack.setItemDamage(temp.getInteger("DummysDamage"));
            gunStack.stackSize = temp.getInteger("DummysStackSize");
            gunItem = (HMGItem_Unified_Guns) gunStack.getItem();
        }
    }
    public static TurretObj getActiveTurret(TurretObj turretObj){
        if(turretObj.gunStack == null){
            if(!turretObj.getChilds().isEmpty())for(TurretObj child:turretObj.getChilds()){
                TurretObj temp_child = getActiveTurret(child);
                if(temp_child != null && temp_child.gunStack != null)return temp_child;
            }
            if(!turretObj.getChildsOnBarrel().isEmpty())for(TurretObj child:turretObj.getChildsOnBarrel()){
                TurretObj temp_child = getActiveTurret(child);
                if(temp_child != null && temp_child.gunStack != null)return temp_child;
            }
            return null;
        }else return turretObj;
    }
}
