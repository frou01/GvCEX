package handmadevehicle.entity.parts.turrets;

import handmadeguns.HMGPacketHandler;
import handmadeguns.entity.EntityHasMaster;
import handmadeguns.entity.SpHitCheckEntity;
import handmadeguns.entity.bullets.*;
import handmadeguns.items.GunTemp;
import handmadeguns.items.HMGItemCustomMagazine;
import handmadeguns.items.guns.HMGItem_Unified_Guns;
import handmadeguns.network.PacketPlaysound;
import handmadeguns.network.PacketSpawnParticle;
import handmadevehicle.Utils;
import handmadevehicle.entity.parts.HasBaseLogic;
import handmadevehicle.entity.parts.HasLoopSound;
import handmadevehicle.entity.parts.IVehicle;
import handmadevehicle.entity.parts.logics.BaseLogic;
import handmadevehicle.entity.prefab.Prefab_Turret;
import handmadevehicle.inventory.InventoryVehicle;
import handmadevehicle.network.HMVPacketHandler;
import handmadevehicle.network.packets.HMVPacketMissileAndLockMarker;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import javax.vecmath.AxisAngle4d;
import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;

import java.util.ArrayList;

import static handmadeguns.HandmadeGunsCore.cfg_defgravitycof;
import static handmadeguns.HandmadeGunsCore.HMG_proxy;
import static handmadevehicle.HMVehicle.HMV_Proxy;
import static java.lang.Math.*;
import static handmadevehicle.Utils.*;
import static net.minecraft.util.MathHelper.wrapAngleTo180_double;
import static net.minecraft.util.MathHelper.wrapAngleTo180_float;

public class TurretObj implements HasLoopSound{
    public HMGItem_Unified_Guns dummyGunItem = new HMGItem_Unified_Guns();
    public ItemStack dummyGunStack = new ItemStack(dummyGunItem);
    
    public IInventory connectedInventory;
    public int currentCannonID = 0;
    
//    public boolean bursting = false;
    
//    public int cycle_timer;
    
    public double turretrotationYaw;
    public double turretrotationPitch;
    public double prevturretrotationYaw;
    public double prevturretrotationPitch;
    
    public Quat4d motherRot = new Quat4d();
    public Vector3d motherRotCenter = new Vector3d();
    public Quat4d turretRot = new Quat4d();
    public Quat4d turretRotYaw = new Quat4d();
    public Vector3d onMotherPos = new Vector3d();
    public Vector3d motherPos = new Vector3d();
    public Vector3d pos = new Vector3d();

    
    public float turretMoving = 0;
    
    public boolean traverseSoundRemain;
    
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
    
    public boolean readyaim = false;
    public boolean aimIn = false;
    
    
    public Prefab_Turret prefab_turret;
    public int linkedGunStackID;
//    private int burstedRound;
    
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
    
        Vector3d global = new Vector3d(local);
        global.sub(prefab_turret.turretYawCenterpos);
        global.sub(prefab_turret.turretPitchCenterpos);
        global = transformVecByQuat(global, turretpitchrot);
        global.add(prefab_turret.turretPitchCenterpos);
        global = transformVecByQuat(global, turretyawrot);
        global.add(prefab_turret.turretYawCenterpos);
        
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
    public Vector3d getGlobalVector_fromLocalVector_onTurretPoint(Vector3d local){
        if(prefab_turret.userOnBarrell)return getGlobalVector_fromLocalVector(local);
        Quat4d turretyawrot = new Quat4d(0,0,0,1);
        Vector3d axisy = new Vector3d(0,1,0);
        AxisAngle4d axisyangledy = new AxisAngle4d(axisy, toRadians(turretrotationYaw)/2);
        turretyawrot = Utils.quatRotateAxis(turretyawrot,axisyangledy);
    
        Vector3d global = new Vector3d(local);
        global.sub(prefab_turret.turretYawCenterpos);
        global = transformVecByQuat(global, turretyawrot);
        global.add(prefab_turret.turretYawCenterpos);
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
    public Vector3d getGlobalVector_fromLocalVector_onMother(Vector3d local){
        
        local = new Vector3d(local);
        if(mother != null){
            local = mother.getGlobalVector_fromLocalVector_onTurretPoint(local);
            local.add(this.motherPos);
        }else {
            local.sub(this.motherRotCenter);
            local = transformVecByQuat(new Vector3d(local), motherRot);
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
        
        Vector3d transformedVecYawCenter = transformVecByQuat(new Vector3d(prefab_turret.turretYawCenterpos),motherRot);
        Vector3d transformedVecPitchCenter = transformVecByQuat(new Vector3d(prefab_turret.turretPitchCenterpos),yaw_mother);
        global.sub(transformedVecYawCenter);
        global.sub(transformedVecPitchCenter);
        pitch_yaw_mother.inverse();
        Vector3d transformedGlobalVec = transformVecByQuat(new Vector3d(global),pitch_yaw_mother);
        transformedGlobalVec.add(prefab_turret.turretYawCenterpos);
        transformedGlobalVec.add(prefab_turret.turretPitchCenterpos);
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
                                (!prefab_turret.gunInfo.lock_to_Vehicle || aEntity instanceof IVehicle)) {
                        double distsq = currentEntity.getDistanceSqToEntity(aEntity);
                        if (distsq < 16777216) {
                            Vector3d totgtvec = new Vector3d(currentEntity.posX - aEntity.posX, currentEntity.posY - aEntity.posY, currentEntity.posZ - aEntity.posZ);
                            if (totgtvec.length() > 1) {
                                totgtvec.scale(1);
                                double deg = wrapAngleTo180_double(toDegrees(totgtvec.angle(frontVec)));
                                if (prefab_turret.seekerSize > abs(deg) && (abs(deg) < predeg || predeg == -1)) {
                                    predeg = deg;
                                    target = aEntity;
                                }
                            }
                        }
                    }
                }
            }
            if(currentEntity instanceof EntityPlayerMP && prefab_turret.gunInfo.semiActive && missile != null) {
                for (HMGEntityBulletBase amissile:missile) {
                    amissile.homingEntity = target;
                }
            }
            if(currentEntity instanceof EntityPlayerMP && target != null && missile != null){
                HMVPacketHandler.INSTANCE.sendTo(new HMVPacketMissileAndLockMarker(missile, target), (EntityPlayerMP) currentEntity);
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
        if(prefab_turret.turretanglelimtPitchMax != prefab_turret.turretanglelimtPitchmin) {
            if (targetpitch > prefab_turret.turretanglelimtPitchMax) {
                targetpitch = prefab_turret.turretanglelimtPitchMax;
                inrange = false;
            } else if (targetpitch < prefab_turret.turretanglelimtPitchmin) {
                targetpitch = prefab_turret.turretanglelimtPitchmin;
                inrange = false;
            }
        }else {
            targetpitch = prefab_turret.turretanglelimtPitchmin;
        }
        if(prefab_turret.turretanglelimtYawMax != prefab_turret.turretanglelimtYawmin) {
            if(prefab_turret.turretanglelimtYawMax < prefab_turret.turretanglelimtYawmin){
                if (targetyaw < 0 && targetyaw > prefab_turret.turretanglelimtYawMax) {
                    targetyaw = prefab_turret.turretanglelimtYawMax;
                    inrange = false;
                } else if (targetyaw > 0 && targetyaw < prefab_turret.turretanglelimtYawmin) {
                    targetyaw = prefab_turret.turretanglelimtYawmin;
                    inrange = false;
                }
            }else {
                if (targetyaw > prefab_turret.turretanglelimtYawMax) {
                    targetyaw = prefab_turret.turretanglelimtYawMax;
                    inrange = false;
                } else if (targetyaw < prefab_turret.turretanglelimtYawmin) {
                    targetyaw = prefab_turret.turretanglelimtYawmin;
                    inrange = false;
                }
            }
        }else {
            targetyaw = prefab_turret.turretanglelimtYawmin;
        }
        if(prefab_turret.turretspeedY == -1)turretrotationYaw = targetyaw;
        if(prefab_turret.turretspeedP == -1)turretrotationPitch = targetpitch;
        
    
        boolean result1 = false;
        if(prefab_turret.turretspeedY != -1) {
            double AngulardifferenceYaw = targetyaw - this.turretrotationYaw;
            AngulardifferenceYaw = wrapAngleTo180_double(AngulardifferenceYaw);
            if(Double.isNaN(targetyaw))AngulardifferenceYaw = 0;
    
            float out_rangeCenter_yaw = (prefab_turret.turretanglelimtYawMax + prefab_turret.turretanglelimtYawmin)/2;
            if(prefab_turret.turretanglelimtYawMax > prefab_turret.turretanglelimtYawmin)out_rangeCenter_yaw += 180;
            double AngulardifferenceYaw_toCenter = out_rangeCenter_yaw - this.turretrotationYaw;
            AngulardifferenceYaw_toCenter = wrapAngleTo180_double(AngulardifferenceYaw_toCenter);
            boolean reverse = abs(prefab_turret.turretanglelimtYawMax) + abs(prefab_turret.turretanglelimtYawmin) < 360 && (AngulardifferenceYaw < 0) == (AngulardifferenceYaw_toCenter < 0) && (abs(AngulardifferenceYaw) >= abs(AngulardifferenceYaw_toCenter));
    
            if (reverse) {
                if (AngulardifferenceYaw > 0) {
                    turretrotationYaw -= prefab_turret.turretspeedY;
                } else if (AngulardifferenceYaw < 0) {
                    turretrotationYaw += prefab_turret.turretspeedY;
                }
            } else if (AngulardifferenceYaw > prefab_turret.turretspeedY) {
                turretrotationYaw += prefab_turret.turretspeedY;
            } else if (AngulardifferenceYaw < -prefab_turret.turretspeedY) {
                turretrotationYaw -= prefab_turret.turretspeedY;
            } else {
                this.turretrotationYaw += AngulardifferenceYaw;
                result1 = true;
            }
        }else {
            result1 = true;
        }
    
        boolean result2 = false;
        if(prefab_turret.turretspeedP != -1) {
            double AngulardifferencePitch = targetpitch - this.turretrotationPitch;
            if (Double.isNaN(targetpitch)) AngulardifferencePitch = 0;
            AngulardifferencePitch = wrapAngleTo180_double(AngulardifferencePitch);
    
            if (AngulardifferencePitch > prefab_turret.turretspeedP) {
                turretrotationPitch += prefab_turret.turretspeedP;
            } else if (AngulardifferencePitch < -prefab_turret.turretspeedP) {
                turretrotationPitch -= prefab_turret.turretspeedP;
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
        }else {
            result2 = true;
        }
        boolean canfire = false;
        if(prefab_turret.fireRists != null){
            for(FireRist afirRist : prefab_turret.fireRists){
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
        aimIn = inrange;
        
        while (this.turretrotationYaw - this.prevturretrotationYaw < -180.0F)
        {
            this.prevturretrotationYaw -= 360.0F;
        }
    
        while (this.turretrotationYaw - this.prevturretrotationYaw >= 180.0F)
        {
            this.prevturretrotationYaw += 360.0F;
        }
        
        return readyaim = result1 && result2 && inrange && canfire;
    }
    public void calculatePos(){
        Vector3d temppos = getGlobalVector_fromLocalVector_onMother(onMotherPos);
        
        
        
        this.pos = temppos;
    }
    public void update(Quat4d motherRot,Vector3d motherPos){
        if(!prefab_turret.needGunStack) {
            if (dummyGunItem == null) dummyGunItem = new HMGItem_Unified_Guns();
            if (dummyGunStack == null) dummyGunStack = new ItemStack(dummyGunItem);
        }else if(motherEntity instanceof HasBaseLogic){
            InventoryVehicle inventoryVehicle = ((HasBaseLogic) motherEntity).getBaseLogic().inventoryVehicle;
            dummyGunStack = inventoryVehicle.getStackInSlot(linkedGunStackID);
            if(dummyGunStack != null){
                dummyGunItem = (HMGItem_Unified_Guns) dummyGunStack.getItem();
                prefab_turret.gunInfo = dummyGunItem.gunInfo;
            }
        }
        if(currentEntity == null)
            triggerFreeze = 10;
        if(worldObj.isRemote) {
            double deltaRotYaw = prevturretrotationYaw - turretrotationYaw;
            double deltaRotPitch = prevturretrotationPitch - turretrotationPitch;
            turretMoving += ((float) sqrt(deltaRotPitch * deltaRotPitch + deltaRotYaw * deltaRotYaw) - turretMoving) * 0.3f;
            if(turretMoving < min(prefab_turret.turretspeedY,prefab_turret.turretspeedP)/10)turretMoving = 0;
            if (!traverseSoundRemain && currentEntity != null && prefab_turret.traverseSound != null && (turretMoving > 0)) {
                HMV_Proxy.playsoundasTurret(prefab_turret.traversesoundLV*4, this);
            }
            traverseSoundRemain = false;
        }
        prevturretrotationYaw = turretrotationYaw;
        prevturretrotationPitch = turretrotationPitch;
        this.motherRot = motherRot;
        this.motherPos = motherPos;
        triggerFreeze--;
        if(readytoFire())childFireBlank=prefab_turret.childFireBlank;
        else childFireBlank--;
        if(!worldObj.isRemote && prefab_turret.gunInfo.canlock){
            lock();
        }
        if(prefab_turret.gunInfo.semiActive){
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
    
        Quat4d temp = new Quat4d(this.motherRot);
        temp.mul(turretRot);
        Vector3d pos_selected = this.motherPos;
        Quat4d rot_selected = this.motherRot;
        if(prefab_turret.positionLinked){
            pos_selected = this.pos;
            rot_selected = temp;
        }
        for(TurretObj achild :childs){
            achild.currentEntity = this.currentEntity;
            achild.motherEntity = this.motherEntity;
            achild.mother = this;
            achild.update(rot_selected, pos_selected);
        }
        for(TurretObj abrother :brothers){
            if(prefab_turret.syncTurretAngle){
                abrother.turretrotationYaw   = this.turretrotationYaw;
                abrother.turretrotationPitch = this.turretrotationPitch;
                abrother.mother = this;
            }
            abrother.update(this.motherRot, this.motherPos);
        }
        
        Vector3d axisX = transformVecByQuat(unitX, turretRot);
        AxisAngle4d axisxangledX = new AxisAngle4d(axisX, toRadians(-turretrotationPitch)/2);
        turretRot = quatRotateAxis(turretRot,axisxangledX);
    
        temp = new Quat4d(this.motherRot);
        temp.mul(turretRot);
        for(TurretObj achild :childsOnBarrel){
            achild.currentEntity = this.currentEntity;
            achild.motherEntity = this.motherEntity;
            achild.mother = this;
            achild.update(temp, this.pos);
        }
    
        GunTemp.currentConnectedTurret = this;
        if(dummyGunStack != null) {
            getDummyStackTag().setBoolean("IsTurretStack", true);
            dummyGunItem.onUpdate_fromTurret(dummyGunStack, worldObj, currentEntity, -10, true, this);
            getDummyStackTag().setBoolean("IsTurretStack", false);
        }
        GunTemp.currentConnectedTurret = null;
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
        if(dummyGunStack == null)return null;
    	if(dummyGunStack.getTagCompound() == null)dummyGunItem.checkTags(dummyGunStack);
	    return dummyGunStack.getTagCompound();
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
	    if(prefab_turret.multicannonPos != null && prefab_turret.fireAll_cannon){
		    currentCannonID = 0;
		    return prefab_turret.multicannonPos.length;
	    }
	    return 1;
    }
    public boolean fire(){
        if(triggerFreeze>0)return false;
	    if(getDummyStackTag() != null)getDummyStackTag().setBoolean("IsTriggered", true);
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
    public void setBulletsPos(HMGEntityBulletBase[] bullets) {
	    Vector3d cannonPos;
	    if (prefab_turret.multicannonPos == null) {
		    cannonPos = getGlobalVector_fromLocalVector(prefab_turret.cannonPos);
	    } else {
		    cannonPos = getGlobalVector_fromLocalVector(prefab_turret.multicannonPos[currentCannonID]);
		    currentCannonID++;
		    if (currentCannonID >= prefab_turret.multicannonPos.length) currentCannonID = 0;
	    }
	    cannonPos.add(pos);
	    Vector3d lookVec = getCannonDir();
	    transformVecforMinecraft(lookVec);
	    transformVecforMinecraft(cannonPos);
        if(motherEntity instanceof HasBaseLogic){
            BaseLogic linkedBaseLogic = ((HasBaseLogic) motherEntity).getBaseLogic();
            if(linkedBaseLogic.info.recoilResist != 0) {
                Vector3d centerOfGravity = new Vector3d(linkedBaseLogic.prefab_vehicle.center_of_gravity);
                Vector3d localBulletPos = new Vector3d();
                Vector3d relativeCannonPos = new Vector3d(cannonPos);
                relativeCannonPos.sub(new Vector3d(motherEntity.posX,motherEntity.posY,motherEntity.posZ));

                Vector3d localBulletMotion = new Vector3d();
                getVector_local_inRotatedObj(relativeCannonPos, localBulletPos, linkedBaseLogic.bodyRot);
                getVector_local_inRotatedObj(lookVec, localBulletMotion, linkedBaseLogic.bodyRot);
                System.out.println("localBulletPos" + localBulletPos);
                localBulletPos.sub(centerOfGravity);

                Vector3d recoilRotVector = new Vector3d();
                recoilRotVector.cross(localBulletPos, localBulletMotion);
                AxisAngle4d recoilRotor = new AxisAngle4d(recoilRotVector, -dummyGunItem.gunInfo.recoil/linkedBaseLogic.info.recoilResist);
                linkedBaseLogic.rotationmotion = Utils.quatRotateAxis(linkedBaseLogic.rotationmotion, recoilRotor);
            }
        }
	    for (HMGEntityBulletBase abullet : bullets) {
            abullet.setPosition(cannonPos.x,cannonPos.y,cannonPos.z);
		    abullet.setThrowableHeading(lookVec.x, lookVec.y, lookVec.z, prefab_turret.gunInfo.speed, prefab_turret.gunInfo.spread_setting, currentEntity);
		    if(abullet.homingEntity != null)missile.add(abullet);
	    }
	    {
		    if(prefab_turret.gunInfo.flashname != null){
                PacketSpawnParticle flash = new PacketSpawnParticle(
                        cannonPos.x + lookVec.x * prefab_turret.flashoffset,
                        cannonPos.y + lookVec.y * prefab_turret.flashoffset,
                        cannonPos.z + lookVec.z * prefab_turret.flashoffset,
                        toDegrees(-atan2(lookVec.x, lookVec.z)),
                        toDegrees(-asin(lookVec.y)), 100, prefab_turret.gunInfo.flashname, true);
                flash.fuse = prefab_turret.gunInfo.flashfuse;
                flash.scale = prefab_turret.gunInfo.flashScale;
                flash.id = 100;
                HMGPacketHandler.INSTANCE.sendToAll(flash);
		    }else {
                PacketSpawnParticle flash = new PacketSpawnParticle(
                        cannonPos.x + lookVec.x * prefab_turret.flashoffset,
                        cannonPos.y + lookVec.y * prefab_turret.flashoffset,
                        cannonPos.z + lookVec.z * prefab_turret.flashoffset,
                        toDegrees(-atan2(lookVec.x, lookVec.z)),
                        toDegrees(-asin(lookVec.y)), 0,100);
                flash.fuse = prefab_turret.gunInfo.flashfuse;
                flash.scale = prefab_turret.gunInfo.flashScale;
                flash.id = 100;
                HMGPacketHandler.INSTANCE.sendToAll(flash);
		    }
        }
    }
    public int max_Bullet(){
        return dummyGunItem.max_Bullet(dummyGunStack);
        
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
        breakpoint:if(childFireBlank < 0 && (prefab_turret.fireAll_child || !flag)) {
            for (TurretObj achild : childs) {
                achild.currentEntity = this.currentEntity;
                if(achild.prefab_turret.linked_MotherTrigger && achild.fireall()){
                    flag = true;
                    if(!prefab_turret.fireAll_child)break breakpoint;
                }
            }
            for (TurretObj achild : childsOnBarrel) {
                achild.currentEntity = this.currentEntity;
                if(achild.prefab_turret.linked_MotherTrigger && achild.fireall()){
                    flag = true;
                    if(!prefab_turret.fireAll_child)break breakpoint;
                }
            }
            for (TurretObj abrothers : brothers) {
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
    public void addchild_triggerLinked(TurretObj child){
        child.motherRotCenter = new Vector3d(this.prefab_turret.turretYawCenterpos);
        childs.add(child);
    }
    public void addchild_NOTtriggerLinked(TurretObj child){
        child.prefab_turret.linked_MotherTrigger = false;
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
        Vector3d temp = getGlobalVector_fromLocalVector(new Vector3d(prefab_turret.cannonPos));
        thisposVec.add(temp);
        
        transformVecforMinecraft(thisposVec);
        Vector3d targetVec = new Vector3d(target.posX, target.posY, target.posZ);
        targetVec.sub(thisposVec);
        targetVec.normalize();
        double[] elevation = CalculateGunElevationAngle(thisposVec.x, thisposVec.y, thisposVec.z, target, prefab_turret.gunInfo.gravity, prefab_turret.gunInfo.speed);
        if(elevation[2] == -1) {
            return false;
        }else {
            float targetpitch = wrapAngleTo180_float(-(float) elevation[prefab_turret.elevationType]);
            float targetyaw = (float) -toDegrees(atan2(targetVec.x, targetVec.z));
    
            return aimtoAngle(targetyaw, targetpitch);
        }
    }
    
    
    public boolean aimToPos(double targetX,double targetY,double targetZ) {
        Vector3d thisposVec = new Vector3d(pos);
        Vector3d temp = getGlobalVector_fromLocalVector(new Vector3d(prefab_turret.cannonPos));
        thisposVec.add(temp);
        
        transformVecforMinecraft(thisposVec);
        Vector3d targetVec = new Vector3d(targetX, targetY, targetZ);
    
        targetVec.sub(thisposVec);
        targetVec.normalize();
        double[] elevation = CalculateGunElevationAngle(thisposVec.x, thisposVec.y, thisposVec.z, targetX, targetY, targetZ, prefab_turret.gunInfo.gravity, prefab_turret.gunInfo.speed);
        if(elevation[2] == -1) {
            return false;
        }else {
            float targetpitch = wrapAngleTo180_float(-(float) CalculateGunElevationAngle(thisposVec.x, thisposVec.y, thisposVec.z, targetX, targetY, targetZ, prefab_turret.gunInfo.gravity, prefab_turret.gunInfo.speed)[0]);
            float targetyaw = (float) -toDegrees(atan2(targetVec.x, targetVec.z));
        
            return aimtoAngle(targetyaw, targetpitch);
        }
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
    public void yourSoundIsremain() {
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
        if(dummyGunStack != null) {
            if (getDummyStackTag() != null) temp.setTag("DummysTag", getDummyStackTag());
            temp.setInteger("DummysDamage", dummyGunStack.getItemDamage());
        }
        tagCompound.setTag("turret" + id,temp);
    }
    public void readFromTag(NBTTagCompound tagCompound,int id){
        NBTTagCompound temp = tagCompound.getCompoundTag("turret" + id);
        turretrotationYaw = temp.getDouble("turretrotationYaw");
        turretrotationPitch = temp.getDouble("turretrotationPitch");
        if(dummyGunStack != null) {
            dummyGunStack.setTagCompound(temp.getCompoundTag("DummysTag"));
            dummyGunStack.setItemDamage(temp.getInteger("DummysDamage"));
        }
    }
}
