package handmadevehicle.entity.parts.turrets;

import handmadeguns.HMGPacketHandler;
import handmadeguns.HandmadeGunsCore;
import handmadeguns.Util.GunsUtils;
import handmadeguns.entity.bullets.*;
import handmadeguns.items.GunInfo;
import handmadeguns.items.guns.HMGItem_Unified_Guns;
import handmadeguns.network.PacketPlaySound_Gui;
import handmadeguns.network.PacketSpawnParticle;
import handmadevehicle.Utils;
import handmadevehicle.entity.EntityDummy_rider;
import handmadevehicle.entity.EntityVehicle;
import handmadevehicle.entity.parts.HasBaseLogic;
import handmadevehicle.entity.parts.HasLoopSound;
import handmadevehicle.entity.parts.logics.BaseLogic;
import handmadevehicle.entity.prefab.Prefab_Turret;
import handmadevehicle.network.HMVPacketHandler;
import handmadevehicle.network.packets.HMVPacketMissileAndLockMarker;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
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
    public HMGItem_Unified_Guns gunItem;
    public ItemStack gunStack;

    public IInventory connectedInventory;
    public int currentCannonID = 0;

    public int turretID_OnVehicle;

//    public boolean bursting = false;

//    public int cycle_timer;

    public double turretrotationYaw;
    public double turretrotationPitch;
    public double targetTurretrotationYaw;
    public double targetTurretrotationPitch;
    public double prevturretrotationYaw;
    public double prevturretrotationPitch;

    public boolean noTraverse = false;

    public Quat4d motherRot = new Quat4d(0,0,0,1);
    public Vector3d motherRotCenter = new Vector3d();
    public Quat4d turretRot = new Quat4d(0,0,0,1);
    public Quat4d turretRotYaw = new Quat4d(0,0,0,1);
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
    public boolean playerControl;
//    private int burstedRound;

    public TurretObj(World worldObj){
        this.worldObj = worldObj;
        gunItem = new HMGItem_Unified_Guns();
        gunStack = new ItemStack(gunItem);
        gunStack.setItemDamage(gunStack.getMaxDamage());
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
        Quat4d turretyawrot = new Quat4d(0,0,0,1);
        Vector3d axisy = new Vector3d(0, 1, 0);
        AxisAngle4d axisyangledy = new AxisAngle4d(axisy, toRadians(turretrotationYaw) / 2);
        turretyawrot = Utils.quatRotateAxis(turretyawrot, axisyangledy);

        Vector3d global = new Vector3d(local);
        GunInfo currentGunInfo = this.getCurrentGuninfo();
        if (currentGunInfo != null) {
            global.sub(currentGunInfo.posGetter.turretYawCenterpos);
            global = transformVecByQuat(global, turretyawrot);
            global.add(currentGunInfo.posGetter.turretYawCenterpos);
        }
        global = transformVecByQuat(global, motherRot);
        return global;
    }
    public Vector3d getGlobalVector_fromLocalVector_onBarrelPointForCannon(Vector3d local){
        Quat4d turretyawrot = new Quat4d(0,0,0,1);
        Vector3d axisy = new Vector3d(0, 1, 0);
        AxisAngle4d axisyangledy = new AxisAngle4d(axisy, toRadians(turretrotationYaw) / 2);
        turretyawrot = Utils.quatRotateAxis(turretyawrot, axisyangledy);

        Quat4d turretpitchrot = new Quat4d(0,0,0,1);
        Vector3d axisx = Utils.transformVecByQuat(new Vector3d(1,0,0), turretpitchrot);
        AxisAngle4d axisyangledp = new AxisAngle4d(axisx, toRadians(-turretrotationPitch)/2);
        turretpitchrot = Utils.quatRotateAxis(turretpitchrot,axisyangledp);

        Vector3d global = new Vector3d(local);
        GunInfo currentGunInfo = this.getCurrentGuninfo();
        if (currentGunInfo != null) {
            global.sub(currentGunInfo.posGetter.turretPitchCenterpos);
            global = transformVecByQuat(global, turretpitchrot);
            global.add(currentGunInfo.posGetter.turretPitchCenterpos);
            global.sub(currentGunInfo.posGetter.turretYawCenterpos);
            global = transformVecByQuat(global, turretyawrot);
            global.add(currentGunInfo.posGetter.turretYawCenterpos);
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
//    public Vector3d getLocalVector_fromGlobalVector(Vector3d global){
//        Quat4d turretyawrot = new Quat4d(0,0,0,1);
//        Vector3d axisy = Utils.transformVecByQuat(new Vector3d(0,1,0), turretyawrot);
//        AxisAngle4d axisyangledy = new AxisAngle4d(axisy, toRadians(turretrotationYaw)/2);
//        turretyawrot = Utils.quatRotateAxis(turretyawrot,axisyangledy);
//
//        Quat4d yaw_mother = new Quat4d(0,0,0,1);
//        yaw_mother.mul(motherRot,turretyawrot);
//
//        Quat4d pitch_yaw_mother = new Quat4d(0,0,0,1);
//        pitch_yaw_mother.mul(yaw_mother,turretyawrot);
//
//        Vector3d transformedVecYawCenter = transformVecByQuat(new Vector3d(gunItem.gunInfo.posGetter.turretYawCenterpos),motherRot);
//        Vector3d transformedVecPitchCenter = transformVecByQuat(new Vector3d(gunItem.gunInfo.posGetter.turretPitchCenterpos),yaw_mother);
//        global.sub(transformedVecYawCenter);
//        global.sub(transformedVecPitchCenter);
//        inverse_safe(pitch_yaw_mother);
//        Vector3d transformedGlobalVec = transformVecByQuat(new Vector3d(global),pitch_yaw_mother);
//        transformedGlobalVec.add(gunItem.gunInfo.posGetter.turretYawCenterpos);
//        transformedGlobalVec.add(gunItem.gunInfo.posGetter.turretPitchCenterpos);
//        return transformedGlobalVec;
//    }

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
                Vector3d frontVec;
                if(prefab_turret.lockToPilotAngle){
                    frontVec = getjavaxVecObj(currentEntity.getLookVec());
                    frontVec.scale(-1);
                }else {
                    frontVec = getCannonDir();
                    transformVecforMinecraft(frontVec);
                    frontVec.scale(-1);
                }
                Entity prevTarget = target;
                target = null;
                if(gunItem.gunInfo.canlockEntity) {
                    target = gunItem.lockOn(gunStack,worldObj,currentEntity,frontVec);
                    if(target != null) {
                        if(currentEntity instanceof EntityPlayerMP && playerControl){
                            if (gunItem.gunInfo.lockSound_NoStop) {
                                HMGPacketHandler.INSTANCE.sendTo(new PacketPlaySound_Gui(gunItem.gunInfo.lockSound_entity, 1), (EntityPlayerMP) currentEntity);
                            } else if (prevTarget != target) {
                                HMGPacketHandler.INSTANCE.sendTo(new PacketPlaySound_Gui(gunItem.gunInfo.lockSound_entity, 1), (EntityPlayerMP) currentEntity);
                            }
                        }
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
                    MovingObjectPosition movingobjectposition = GunsUtils.getmovingobjectPosition_forBlock(worldObj,vec3, vec31);//衝突するブロックを調べる
                    if(movingobjectposition != null && movingobjectposition.hitVec != null){
                        lockedBlockPos = movingobjectposition.hitVec;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(lockedBlockPos != null && currentEntity instanceof EntityPlayerMP && playerControl) {
                HMVPacketHandler.INSTANCE.sendTo(new HMVPacketMissileAndLockMarker(missile,
                                new double[]{lockedBlockPos.xCoord,lockedBlockPos.yCoord,lockedBlockPos.zCoord}),
                        (EntityPlayerMP) currentEntity);
            }
            if(currentEntity instanceof EntityPlayerMP && playerControl && target != null) {
                HMVPacketMissileAndLockMarker hmvPacketMissileAndLockMarker = new HMVPacketMissileAndLockMarker(missile, target);
                hmvPacketMissileAndLockMarker.target_Pos_Motion.markerID = turretID_OnVehicle;
                hmvPacketMissileAndLockMarker.target_Pos_Motion.displayPredict = gunItem.gunInfo.displayPredict;
                HMVPacketHandler.INSTANCE.sendTo(hmvPacketMissileAndLockMarker, (EntityPlayerMP) currentEntity);
            }
        }
    }


    public void upDateLockOn() {
        GunInfo currentGunInfo = this.getCurrentGuninfo();
        if (currentGunInfo != null && currentEntity != null) {
            HMGItem_Unified_Guns dummyGun = new HMGItem_Unified_Guns();
            dummyGun.gunInfo = currentGunInfo;
            try {
                Vector3d frontVec;
                if(prefab_turret.lockToPilotAngle){
                    frontVec = getjavaxVecObj(currentEntity.getLookVec());
                    frontVec.scale(-1);
                }else {
                    frontVec = getCannonDir();
                    transformVecforMinecraft(frontVec);
                    frontVec.scale(-1);
                }
                if (currentGunInfo.canlockEntity) {
                    dummyGun.guntemp.TGT = target;
                    target = dummyGun.canContinueLock(gunStack, worldObj, currentEntity, frontVec);
                    if (target != null) {
                        if (currentEntity instanceof EntityPlayerMP && playerControl) {
                            if (currentGunInfo.lockSound_NoStop) {
                                HMGPacketHandler.INSTANCE.sendTo(new PacketPlaySound_Gui(dummyGun.gunInfo.lockSound_entity, 1), (EntityPlayerMP) currentEntity);
                            }
                        }
                        if (target.ridingEntity != null) target = target.ridingEntity;
                        if (target instanceof EntityDummy_rider) {
                            target = ((EntityDummy_rider) target).linkedBaseLogic.mc_Entity;
                        }
                        target.getEntityData().setBoolean("behome", true);
                    }
                }

//                if(dummyGun.gunInfo.canlockBlock){
//                    Vec3 vec3 = Vec3.createVectorHelper(currentEntity.posX, currentEntity.posY + currentEntity.getEyeHeight(), currentEntity.posZ);
//                    Vec3 playerlook = getMinecraftVecObj(frontVec);
//
//                    playerlook.xCoord *= -1;
//                    playerlook.yCoord *= -1;
//                    playerlook.zCoord *= -1;
//
//                    playerlook = Vec3.createVectorHelper(playerlook.xCoord * 256, playerlook.yCoord * 256, playerlook.zCoord * 256);
//
//                    Vec3 vec31 = Vec3.createVectorHelper(currentEntity.posX + playerlook.xCoord, currentEntity.posY + currentEntity.getEyeHeight() + playerlook.yCoord, currentEntity.posZ + playerlook.zCoord);
//                    MovingObjectPosition movingobjectposition = GunsUtils.getmovingobjectPosition_forBlock(worldObj,vec3, vec31, false, true, false);//衝突するブロックを調べる
//                    if(movingobjectposition != null && movingobjectposition.hitVec != null){
//                        lockedBlockPos = movingobjectposition.hitVec;
//                    }
//                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (lockedBlockPos != null && currentEntity instanceof EntityPlayerMP && playerControl) {
                HMVPacketHandler.INSTANCE.sendTo(new HMVPacketMissileAndLockMarker(missile,
                                new double[]{lockedBlockPos.xCoord, lockedBlockPos.yCoord, lockedBlockPos.zCoord}),
                        (EntityPlayerMP) currentEntity);
            }
            if (currentEntity instanceof EntityPlayerMP && target != null) {
                HMVPacketMissileAndLockMarker hmvPacketMissileAndLockMarker = new HMVPacketMissileAndLockMarker(missile, target);
                hmvPacketMissileAndLockMarker.target_Pos_Motion.markerID = turretID_OnVehicle;
                hmvPacketMissileAndLockMarker.target_Pos_Motion.displayPredict = dummyGun.gunInfo.displayPredict;
                HMVPacketHandler.INSTANCE.sendTo(hmvPacketMissileAndLockMarker, (EntityPlayerMP) currentEntity);
            }
        }
    }

    public boolean setTurretTarget(double targetyaw, double targetpitch){
        targetTurretrotationYaw = targetyaw;
        targetTurretrotationPitch = targetpitch;
        upDateAim = true;
        return readyaim;
    }
    public void moveTurret(){
        GunInfo currentGunInfo = this.getCurrentGuninfo();
        if(currentGunInfo != null && (upDateAim || worldObj.isRemote)) {
            upDateAim = false;
            targetTurretrotationYaw = wrapAngleTo180_double(targetTurretrotationYaw);
            targetTurretrotationPitch = wrapAngleTo180_double(targetTurretrotationPitch);
            turretrotationYaw = wrapAngleTo180_double(turretrotationYaw);
            turretrotationPitch = wrapAngleTo180_double(turretrotationPitch);
            boolean inrange = true;
            if (currentGunInfo.turretanglelimtPitchMax != currentGunInfo.turretanglelimtPitchmin) {
                if (targetTurretrotationPitch > currentGunInfo.turretanglelimtPitchMax) {
                    targetTurretrotationPitch = currentGunInfo.turretanglelimtPitchMax;
                    inrange = false;
                } else if (targetTurretrotationPitch < currentGunInfo.turretanglelimtPitchmin) {
                    targetTurretrotationPitch = currentGunInfo.turretanglelimtPitchmin;
                    inrange = false;
                }
            } else {
                targetTurretrotationPitch = currentGunInfo.turretanglelimtPitchmin;
            }
            if (currentGunInfo.turretanglelimtYawMax != currentGunInfo.turretanglelimtYawmin) {
                if (currentGunInfo.turretanglelimtYawMax < currentGunInfo.turretanglelimtYawmin) {
                    if (targetTurretrotationYaw < 0 && targetTurretrotationYaw > currentGunInfo.turretanglelimtYawMax) {
                        targetTurretrotationYaw = currentGunInfo.turretanglelimtYawMax;
                        inrange = false;
                    } else if (targetTurretrotationYaw > 0 && targetTurretrotationYaw < currentGunInfo.turretanglelimtYawmin) {
                        targetTurretrotationYaw = currentGunInfo.turretanglelimtYawmin;
                        inrange = false;
                    }
                } else {
                    if (targetTurretrotationYaw > currentGunInfo.turretanglelimtYawMax) {
                        targetTurretrotationYaw = currentGunInfo.turretanglelimtYawMax;
                        inrange = false;
                    } else if (targetTurretrotationYaw < currentGunInfo.turretanglelimtYawmin) {
                        targetTurretrotationYaw = currentGunInfo.turretanglelimtYawmin;
                        inrange = false;
                    }
                }
            } else {
                targetTurretrotationYaw = currentGunInfo.turretanglelimtYawmin;
            }
            if (currentGunInfo.turretspeedY == -1) turretrotationYaw = targetTurretrotationYaw;
            if (currentGunInfo.turretspeedP == -1) turretrotationPitch = targetTurretrotationPitch;


            boolean result1 = false;
            if (currentGunInfo.turretspeedY > 0) {
                double AngulardifferenceYaw = targetTurretrotationYaw - this.turretrotationYaw;
                AngulardifferenceYaw = wrapAngleTo180_double(AngulardifferenceYaw);
                if (Double.isNaN(targetTurretrotationYaw)) AngulardifferenceYaw = 0;

                float out_rangeCenter_yaw = (currentGunInfo.turretanglelimtYawMax + currentGunInfo.turretanglelimtYawmin) / 2;
                if (currentGunInfo.turretanglelimtYawMax > currentGunInfo.turretanglelimtYawmin)
                    out_rangeCenter_yaw += 180;
                double AngulardifferenceYaw_toCenter = out_rangeCenter_yaw - this.turretrotationYaw;
                AngulardifferenceYaw_toCenter = wrapAngleTo180_double(AngulardifferenceYaw_toCenter);
                boolean reverse = abs(currentGunInfo.turretanglelimtYawMax) + abs(currentGunInfo.turretanglelimtYawmin) < 360 && (AngulardifferenceYaw < 0) == (AngulardifferenceYaw_toCenter < 0) && (abs(AngulardifferenceYaw) >= abs(AngulardifferenceYaw_toCenter));

                if (reverse) {
                    if (AngulardifferenceYaw > 0) {
                        turretrotationYaw -= currentGunInfo.turretspeedY;
                    } else if (AngulardifferenceYaw < 0) {
                        turretrotationYaw += currentGunInfo.turretspeedY;
                    }
                } else if (AngulardifferenceYaw > currentGunInfo.turretspeedY) {
                    turretrotationYaw += currentGunInfo.turretspeedY;
                } else if (AngulardifferenceYaw < -currentGunInfo.turretspeedY) {
                    turretrotationYaw -= currentGunInfo.turretspeedY;
                } else {
                    this.turretrotationYaw += AngulardifferenceYaw;
                    result1 = true;
                }
            } else {
                result1 = true;
            }

            boolean result2 = false;
            if (currentGunInfo.turretspeedP > 0) {
                double AngulardifferencePitch = targetTurretrotationPitch - this.turretrotationPitch;
                if (Double.isNaN(targetTurretrotationPitch)) AngulardifferencePitch = 0;
                AngulardifferencePitch = wrapAngleTo180_double(AngulardifferencePitch);

                if (AngulardifferencePitch > currentGunInfo.turretspeedP) {
                    turretrotationPitch += currentGunInfo.turretspeedP;
                } else if (AngulardifferencePitch < -currentGunInfo.turretspeedP) {
                    turretrotationPitch -= currentGunInfo.turretspeedP;
                } else {
                    this.turretrotationPitch += AngulardifferencePitch;
                    result2 = true;
                }
                turretRot = new Quat4d(0,0,0,1);


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
            tempOnMotherPos.sub(mother.onMotherPos);
        }
        if(gunItem != null)tempOnMotherPos.y += gunItem.gunInfo.yoffset;
        if(isOnBarrel) {
            if(mother != null){
                this.pos = mother.getGlobalVector_fromLocalVector_onBarrelPointForCannon(tempOnMotherPos);
            }else {
                this.pos = new Vector3d(tempOnMotherPos);
                this.pos.sub(this.motherRotCenter);
                this.pos = transformVecByQuat(this.pos, motherRot);
                this.pos.add(this.motherRotCenter);
            }
        }
        else {
//            this.pos = getGlobalVector_fromLocalVector_onMother(tempOnMotherPos);
            if(mother != null){
                this.pos = mother.getGlobalVector_fromLocalVector_onTurretPointForCannon(tempOnMotherPos);
            }else {
                this.pos = new Vector3d(tempOnMotherPos);
                this.pos.sub(this.motherRotCenter);
                this.pos = transformVecByQuat(this.pos, motherRot);
                this.pos.add(this.motherRotCenter);
            }
        }
        pos.add(this.motherPos);
//        if(!worldObj.isRemote) {
//            System.out.println("ID" + turretID_OnVehicle);
//            System.out.println("POS" + pos);
//        }
    }

    public boolean seekerUpdateSwitch = false;

    public void update(Quat4d motherRot,Vector3d motherPos){
        prevturretrotationYaw = turretrotationYaw;
        prevturretrotationPitch = turretrotationPitch;
        moveTurret();
        if(motherEntity instanceof HasBaseLogic) {
            connectedInventory = ((HasBaseLogic) motherEntity).getBaseLogic().inventoryVehicle;
        }
        if(prefab_turret.onlyAim){
            gunStack = null;
            gunItem = null;
        }else
        if(!prefab_turret.needGunStack) {
            if (gunItem == null) gunItem = new HMGItem_Unified_Guns();
            if (gunStack == null) {
                gunStack = new ItemStack(gunItem);
                gunStack.setItemDamage(gunStack.getMaxDamage());
            }
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
        if(worldObj.isRemote && getCurrentGuninfo() != null) {
            double deltaRotYaw = prevturretrotationYaw - turretrotationYaw;
            double deltaRotPitch = prevturretrotationPitch - turretrotationPitch;
            turretMoving = ((float) sqrt(deltaRotPitch * deltaRotPitch + deltaRotYaw * deltaRotYaw)) * 0.3f;
            if(turretMoving < min(getCurrentGuninfo().turretspeedY,getCurrentGuninfo().turretspeedP)/10)turretMoving = 0;
            if (getCurrentGuninfo().turretspeedY > 0 && getCurrentGuninfo().turretspeedP > 0 && !traverseSoundRemain && motherEntity != null && prefab_turret.traverseSound != null && (turretMoving > 0)) {
                HMV_Proxy.playsoundasTurret(prefab_turret.traversesoundLV*4, this);
            }
            traverseSoundRemain = false;
        }
        this.motherRot = motherRot;
        this.motherPos = motherPos;
        triggerFreeze--;
        if(readytoFire())childFireBlank=prefab_turret.childFireBlank;
        else childFireBlank--;
        if(!worldObj.isRemote) {
            if (seekerUpdateSwitch) {
                lock();
                seekerUpdateSwitch = false;
            } else if (target != null) {
                upDateLockOn();
            }

            GunInfo currentGunInfo = this.getCurrentGuninfo();
            if(!missile.isEmpty()){
                ArrayList<HMGEntityBulletBase> forRemove = null;
                for (HMGEntityBulletBase amissile:missile) {
                    if(amissile != null){
//                        System.out.println("" + this.target);
                        if(this.target != null && amissile.isSemiActive)amissile.homingEntity = this.target;
                    }
                    if(amissile != null && amissile.isDead){
                        if(forRemove == null)forRemove = new ArrayList<HMGEntityBulletBase>();
                        forRemove.add(amissile);
                    }
                }
                if(forRemove != null)missile.removeAll(forRemove);
                if(missile.isEmpty()){
                    if(gunStack == null){
                        target = null;
                    }
                }
            }else
            if(gunStack == null){
                target = null;
            }
        }
        calculatePos();
        turretRot = new Quat4d(0,0,0,1);
        Vector3d axisY = transformVecByQuat(unitY, turretRot);
        AxisAngle4d axisxangledY = new AxisAngle4d(axisY, toRadians(turretrotationYaw)/2);
        turretRot = quatRotateAxis(turretRot,axisxangledY);
        turretRotYaw.set(turretRot);

        for(TurretObj abrother :brothers){
            if(this.playerControl) abrother.playerControl = true;
            abrother.update(this.motherRot, this.motherPos);
        }

        Quat4d temp = new Quat4d(this.motherRot);
        temp.mul(turretRot);
        for(TurretObj achild :childs){
            achild.motherEntity = this.motherEntity;
            achild.mother = this;
            if(this.playerControl) achild.playerControl = true;
            if(prefab_turret.salvo_fire_child && seekerUpdateSwitch)achild.seekerUpdateSwitch = true;
            if(achild.currentEntity == null)achild.currentEntity = this.currentEntity;
            if(prefab_turret.forceSyncTarget)achild.target = this.target;
            if(achild.prefab_turret.syncMotherTarget)achild.target = this.target;
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
            if(this.playerControl) achild.playerControl = true;
            if(prefab_turret.salvo_fire_child && seekerUpdateSwitch)achild.seekerUpdateSwitch = true;
            if(achild.currentEntity == null)achild.currentEntity = this.currentEntity;
            if(prefab_turret.forceSyncTarget)achild.target = this.target;
            if(achild.prefab_turret.syncMotherTarget)achild.target = this.target;
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

        this.playerControl = false;
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
        return getDummyStackTag() == null || (getDummyStackTag().getBoolean("IsReloading") ||
                getDummyStackTag().getBoolean("WaitReloading"));
    }
    public boolean isLoading(){
        return getDummyStackTag() == null || !getDummyStackTag().getBoolean("Recoiled");
    }
    public boolean readytoFire(){
        return  !isreloading() && !isLoading();
    }
    public int getSyncroFireNum(){
	    if(gunItem.gunInfo.posGetter.multiCannonPos != null){
	        if(prefab_turret.fireAll_cannon) {
                currentCannonID = 0;
                return gunItem.gunInfo.posGetter.multiCannonPos.length;
            }else {
	            return prefab_turret.fire_cannon_perOneShot;
            }
	    }
	    return 1;
    }
    public boolean fire(){
        if(triggerFreeze>0)return false;
	    if(getDummyStackTag() != null){
	        getDummyStackTag().setBoolean("IsTriggered", true);
        }
        return readytoFire();
    }
    public Vector3d forAim_getCannonPos(){
        Vector3d cannonpos;
        if (gunItem.gunInfo.posGetter.multiCannonPos == null) {
            cannonpos = getGlobalVector_fromLocalVector(gunItem.gunInfo.posGetter.cannonPos);
        } else {
            cannonpos = getGlobalVector_fromLocalVector(gunItem.gunInfo.posGetter.multiCannonPos[currentCannonID]);
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
            if (gunItem.gunInfo.posGetter.multiCannonPos == null) {
                cannonPos = getGlobalVector_fromLocalVector(gunItem.gunInfo.posGetter.cannonPos);
            } else {
                cannonPos = getGlobalVector_fromLocalVector(gunItem.gunInfo.posGetter.multiCannonPos[currentCannonID]);
                currentCannonID++;
                if (currentCannonID >= gunItem.gunInfo.posGetter.multiCannonPos.length) currentCannonID = 0;
            }
            cannonPos.add(pos);
            transformVecforMinecraft(cannonPos);
            return cannonPos;
        }
        Vector3d dummy = new Vector3d(pos);
        transformVecforMinecraft(dummy);
        return dummy;
    }
    public void setBulletsPos(HMGEntityBulletBase[] bullets) {
	    Vector3d cannonPos;
	    if (gunItem.gunInfo.posGetter.multiCannonPos == null) {
		    cannonPos = getGlobalVector_fromLocalVector(gunItem.gunInfo.posGetter.cannonPos);
	    } else {
		    cannonPos = getGlobalVector_fromLocalVector(gunItem.gunInfo.posGetter.multiCannonPos[currentCannonID]);
		    currentCannonID++;
		    if (currentCannonID >= gunItem.gunInfo.posGetter.multiCannonPos.length) currentCannonID = 0;
	    }
	    cannonPos.add(pos);
	    Vector3d lookVec = getCannonDir();
	    transformVecforMinecraft(lookVec);
	    transformVecforMinecraft(cannonPos);
        if(motherEntity instanceof HasBaseLogic){
            BaseLogic linkedBaseLogic = ((HasBaseLogic) motherEntity).getBaseLogic();
            {
                Vector3d localBulletPos = new Vector3d();
                Vector3d relativeCannonPos = new Vector3d(cannonPos);
                relativeCannonPos.sub(new Vector3d(motherEntity.posX,motherEntity.posY,motherEntity.posZ));

                Vector3d localBulletMotion = new Vector3d();
                relativeCannonPos.sub(linkedBaseLogic.prefab_vehicle.rotcenterVec);
                getVector_local_inRotatedObj(relativeCannonPos, localBulletPos, linkedBaseLogic.bodyRot);
                localBulletPos.add(linkedBaseLogic.prefab_vehicle.rotcenterVec);
                getVector_local_inRotatedObj(lookVec, localBulletMotion, linkedBaseLogic.bodyRot);

//                System.out.println("localBulletPos      " + localBulletPos);
//                System.out.println("localBulletMotion   " + localBulletMotion);
                localBulletMotion.normalize();
                localBulletMotion.scale(gunItem.gunInfo.recoil);

                linkedBaseLogic.rotationByRecoil(localBulletMotion,localBulletPos);
            }
        }
        dropCartridge(worldObj,currentEntity,gunStack);
	    for (HMGEntityBulletBase abullet : bullets) {
            abullet.setPosition(cannonPos.x,cannonPos.y,cannonPos.z);
		    abullet.setThrowableHeading(lookVec.x, lookVec.y, lookVec.z, gunItem.gunInfo.speed, gunItem.gunInfo.spread_setting, motherEntity);
            abullet.prevRotationYaw = abullet.rotationYaw = (float)(-atan2(lookVec.x, lookVec.z) * 180.0D / Math.PI);
            abullet.prevRotationPitch = abullet.rotationPitch = (float)(-atan2(lookVec.y, (double)sqrt(lookVec.x * lookVec.x + lookVec.z * lookVec.z)) * 180.0D / Math.PI);
		    if(gunItem.gunInfo.semiActive){
//		        System.out.println("debug");
                abullet.isSemiActive = true;
                abullet.isActive = gunItem.gunInfo.isActive;
		        missile.add(abullet);
            }
            abullet.homingEntity = this.target;
            abullet.induction_precision = gunItem.gunInfo.induction_precision;
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

    public void dropCartridge(World world,Entity entity,ItemStack itemStack){
        Vector3d cartPos;
        if (gunItem.gunInfo.posGetter.multiCartPos == null) {
            cartPos = getGlobalVector_fromLocalVector(gunItem.gunInfo.posGetter.cartPos);
        } else {
            cartPos = getGlobalVector_fromLocalVector(gunItem.gunInfo.posGetter.multiCartPos[currentCannonID]);
        }
        cartPos.add(pos);
        transformVecforMinecraft(cartPos);
        HMGEntityBulletCartridge var8;
        for(int i=0;i < gunItem.gunInfo.cartentityCnt;i++) {
            String currentMagCart = gunItem.currentMagazine_cartridgeModelName(itemStack);
            if(currentMagCart == null){
                if (!gunItem.gunInfo.hascustomcartridgemodel)
                    var8 = new HMGEntityBulletCartridge(world, currentEntity, gunItem.gunInfo.cartType);
                else {
                    var8 = new HMGEntityBulletCartridge(world, currentEntity, -1, gunItem.gunInfo.bulletmodelCart);
                }
            }else
                var8 = new HMGEntityBulletCartridge(world, currentEntity, -1, currentMagCart);
            var8.rotationYaw += 90;
            Item cartItem = gunItem.currentMagazine_cartridgeItem(itemStack);
            if(cartItem != null)var8.itemStack = new ItemStack(cartItem);
            var8.setPosition(cartPos.x,cartPos.y,cartPos.z);
            var8.motionX = motherEntity.motionX;
            var8.motionY = motherEntity.motionY;
            var8.motionZ = motherEntity.motionZ;
            world.spawnEntityInWorld(var8);
        }
    }
    public int max_Bullet(){
        return gunItem.max_Bullet(gunStack);

    }

    public boolean fireall(){
        boolean flag = this.fire();
        breakpoint:if(prefab_turret.salvo_fire_child || (childFireBlank < 0 && (!flag))) {
            for (TurretObj achild : childs) {
                if(prefab_turret.salvo_fire_child){
                    achild.triggerFreeze = this.triggerFreeze;
                }
                achild.currentEntity = this.currentEntity;
                if(achild.prefab_turret.linked_MotherTrigger && achild.fireall()){
                    flag = true;
                    if(!prefab_turret.salvo_fire_child)break breakpoint;
                }
            }
            for (TurretObj achild : childsOnBarrel) {
                if(prefab_turret.salvo_fire_child){
                    achild.triggerFreeze = this.triggerFreeze;
                }
                achild.currentEntity = this.currentEntity;
                if(achild.prefab_turret.linked_MotherTrigger && achild.fireall()){
                    flag = true;
                    if(!prefab_turret.salvo_fire_child)break breakpoint;
                }
            }
            for (TurretObj abrothers : brothers) {
                if(prefab_turret.salvo_fire_child){
                    abrothers.triggerFreeze = this.triggerFreeze;
                }
                abrothers.currentEntity = this.currentEntity;
                if(abrothers.prefab_turret.linked_MotherTrigger && abrothers.fireall()){
                    flag = true;
                    if(!prefab_turret.salvo_fire_child)break breakpoint;
                }
            }
        }
        if(flag)childFireBlank = prefab_turret.childFireBlank;
        return flag;
    }

    public TurretObj getAvailableTurret(){


        if(!isreloading() && gunItem != null){
            return this;
        }

        TurretObj found = null;
        breakpoint:if(prefab_turret.salvo_fire_child || (childFireBlank < 0)) {
            for (TurretObj achild : childs) {
                if(achild.prefab_turret.linked_MotherTrigger && (found = achild.getAvailableTurret()) != null){
                    break breakpoint;
                }
            }
            for (TurretObj achild : childsOnBarrel) {
                if(achild.prefab_turret.linked_MotherTrigger && (found = achild.getAvailableTurret()) != null){
                    break breakpoint;
                }
            }
            for (TurretObj abrothers : brothers) {
                if(abrothers.prefab_turret.linked_MotherTrigger && (found = abrothers.getAvailableTurret()) != null){
                    break breakpoint;
                }
            }
        }

        return found;
    }
    public void addchild_triggerLinked(TurretObj child){
        child.motherRotCenter = new Vector3d(this.prefab_turret.gunInfo.posGetter.turretYawCenterpos);
        childs.add(child);

    }
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
        }else if(prefab_turret.onlyAim){
            float targetpitch = wrapAngleTo180_float((float) -toDegrees(atan2(targetVec.y, sqrt(targetVec.x * targetVec.x + targetVec.z * targetVec.z))));
            float targetyaw = (float) -toDegrees(atan2(targetVec.x, targetVec.z));

            return aimtoAngle(targetyaw, targetpitch);
        }
        return false;
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
            if(temp.hasKey("DummysTag")) {
                gunStack.setTagCompound(temp.getCompoundTag("DummysTag"));
                gunStack.setItemDamage(temp.getInteger("DummysDamage"));
                gunStack.stackSize = temp.getInteger("DummysStackSize");
            }else {
                gunStack.setItemDamage(gunStack.getMaxDamage());
            }
            gunItem = (HMGItem_Unified_Guns) gunStack.getItem();
        }
    }
    public static TurretObj getActiveTurret(TurretObj turretObj){
        if(turretObj.gunStack == null){
            if(!turretObj.getChilds().isEmpty())for(TurretObj child:turretObj.getChilds()){
                TurretObj temp_child = getActiveTurret(child);
                if(temp_child != null)return temp_child;
            }
            if(!turretObj.getChildsOnBarrel().isEmpty())for(TurretObj child:turretObj.getChildsOnBarrel()){
                TurretObj temp_child = getActiveTurret(child);
                if(temp_child != null)return temp_child;
            }
            return null;
        }else {
            return turretObj;
        }
    }

    public double getTerminalspeed() {
        if(gunItem != null)return gunItem.gunInfo.acceleration >0 ? gunItem.gunInfo.acceleration/(1-gunItem.gunInfo.resistance):gunItem.gunInfo.speed;
        return 1;
    }

    public GunInfo getCurrentGuninfo(){
        GunInfo currentGunInfo = this.prefab_turret.gunInfo;
        if(gunItem != null && gunItem.gunInfo != null){
            currentGunInfo = gunItem.gunInfo;
        }
        return currentGunInfo;
    }

    public String getName(){
        if(this.gunStack != null && this.gunStack.getDisplayName() != null && !this.gunStack.getDisplayName().equals("item.null.name")){
            return this.gunStack.getDisplayName();
        }else {
            return this.prefab_turret.turretName;
        }
    }
}
