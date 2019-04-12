package hmggvcmob.entity;

import handmadeguns.HMGPacketHandler;
import handmadeguns.entity.bullets.*;
import handmadeguns.network.PacketPlaysound;
import handmadeguns.network.PacketSpawnParticle;
import hmggvcmob.util.Calculater;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;

import javax.vecmath.AxisAngle4d;
import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;

import java.util.ArrayList;

import static handmadeguns.HandmadeGunsCore.cfg_defgravitycof;
import static hmggvcmob.event.GVCMXEntityEvent.soundedentity;
import static java.lang.Math.*;
import static hmggvcmob.util.Calculater.*;
import static net.minecraft.util.MathHelper.wrapAngleTo180_double;
import static net.minecraft.util.MathHelper.wrapAngleTo180_float;

public class TurretObj {
    public Vector3d turretYawCenterpos = new Vector3d(0,0,0);
    public Vector3d turretPitchCenterpos = new Vector3d(0,0,0);
    public Vector3d cannonpos = new Vector3d();
    public Vector3d onmotherPos = new Vector3d();
    public Vector3d motherRotCenter = new Vector3d();
    
    public int cycle_timer;
    public int cycle_setting = 100;
    
    public double turretrotationYaw;
    public double turretrotationPitch;
    
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
    
    public String traverseSound = "gvcmob:gvcmob.traverseSound";
    public float traversesoundLV = 1;
    public float traversesoundPitch = 1;
    
    TurretObj mother;
    ArrayList<TurretObj> childs = new ArrayList<>();
    ArrayList<TurretObj> childsOnBarrel = new ArrayList<>();
    
    World worldObj;
    public Entity currentEntity;
    
    public float speed = 8;
    public float acceler = 0;
    public float spread = 0.5f;
    
    public String flushName = "CannonMuzzleFlash";
    public int flushfuse = 3;
    public float flushscale = 5;
    public float flushoffset = 6;
    
    public String firesound = "gvcmob:gvcmob.120mmFire";
    public float firesoundLV = 5;
    public float firesoundPitch = 1;
    
    public int powor;
    public int guntype;
    public float ex = 2.5F;
    public int fuse = 0;
    public float gravity = 0.04903325f;
    public boolean canex = true;
    public String bulletmodel = "default";
    public int shotgun_pellet = 1;
    
    public int magazinerem;
    public int magazineMax = -1;
    public int reloadTimer = -1;
    public int reloadSetting = 0;
    
    public boolean canHoming = false;
    public float induction_precision = 15;
    public Entity target;
    
    public boolean ready = false;
    
    public TurretObj(World worldObj){
        this.worldObj = worldObj;
    }
    public Vector3d getGlobalVector_fromLocalVector(Vector3d local){
        Quat4d turretyawrot = new Quat4d(0,0,0,1);
        Vector3d axisy = Calculater.transformVecByQuat(new Vector3d(0,1,0), turretyawrot);
        AxisAngle4d axisyangledy = new AxisAngle4d(axisy, toRadians(turretrotationYaw)/2);
        turretyawrot = Calculater.quatRotateAxis(turretyawrot,axisyangledy);
        
        
        Quat4d turretpitchrot = new Quat4d(0,0,0,1);
        Vector3d axisx = Calculater.transformVecByQuat(new Vector3d(1,0,0), turretpitchrot);
        AxisAngle4d axisyangledp = new AxisAngle4d(axisx, toRadians(turretrotationPitch)/2);
        turretpitchrot = Calculater.quatRotateAxis(turretpitchrot,axisyangledp);
        
        local = new Vector3d(local);
        local.sub(turretYawCenterpos);
        local.sub(turretPitchCenterpos);
        Vector3d transformedVecYawCenter;
        Vector3d transformedVecPitchCenter;
        {
            Vector3d temp = new Vector3d(turretYawCenterpos);
            temp.sub(motherRotCenter);
            temp = transformVecByQuat(temp, motherRot);
            temp.add(motherRotCenter);
            transformedVecYawCenter = temp;
        }
        {
            Vector3d temp = new Vector3d(turretPitchCenterpos);
            temp.sub(motherRotCenter);
            temp = transformVecByQuat(temp, motherRot);
            temp.add(motherRotCenter);
            temp.sub(turretYawCenterpos);
            temp = transformVecByQuat(temp, turretyawrot);
            temp.add(turretYawCenterpos);
            transformedVecPitchCenter = temp;
        }
        
        local.sub(motherRotCenter);
        local = transformVecByQuat(local,motherRot);
        local.add(motherRotCenter);
        local = transformVecByQuat(local,turretpitchrot);
        local = transformVecByQuat(local,turretyawrot);
        local.add(transformedVecYawCenter);
        local.add(transformedVecPitchCenter);
        
        return local;
    }
    public Vector3d getGlobalVector_fromLocalVector_onTurretPoint(Vector3d local){
        Quat4d turretyawrot = new Quat4d(0,0,0,1);
        Vector3d axisy = Calculater.transformVecByQuat(new Vector3d(0,1,0), turretyawrot);
        AxisAngle4d axisyangledy = new AxisAngle4d(axisy, toRadians(turretrotationYaw)/2);
        turretyawrot = Calculater.quatRotateAxis(turretyawrot,axisyangledy);
        
        
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
        Vector3d axisy = Calculater.transformVecByQuat(new Vector3d(0,1,0), turretyawrot);
        AxisAngle4d axisyangledy = new AxisAngle4d(axisy, toRadians(turretrotationYaw)/2);
        turretyawrot = Calculater.quatRotateAxis(turretyawrot,axisyangledy);
        
        Quat4d yaw_mother = new Quat4d();
        yaw_mother.mul(motherRot,turretyawrot);
        
        Quat4d turretpitchrot = new Quat4d(0,0,0,1);
        Vector3d axisx = Calculater.transformVecByQuat(new Vector3d(1,0,0), turretpitchrot);
        AxisAngle4d axisyangledp = new AxisAngle4d(axisy, toRadians(turretrotationPitch)/2);
        turretpitchrot = Calculater.quatRotateAxis(turretpitchrot,axisyangledp);
        
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
        return Calculater.transformVecByQuat(naturalVector,motherRot);
    }
    
    public Vector3d getCannonDir(){
        Vector3d lookVec = new Vector3d(0,0,-1);
        lookVec = transformVecByQuat(lookVec,turretRot);
        lookVec = transformVecByQuat(lookVec,motherRot);
        
        return lookVec;
    }
    public Vector3d getCannonpos(){
        Vector3d Vec_transformedbybody = getGlobalVector_fromLocalVector(cannonpos);
    
        transformVecforMinecraft(Vec_transformedbybody);
        return new Vector3d(pos.x + Vec_transformedbybody.x,
                                   pos.y + Vec_transformedbybody.y,
                                   -pos.z + Vec_transformedbybody.z);
    }
    public boolean aimtoAngle(double targetyaw,double targetpitch){
        
        if(canHoming){
            lock(targetyaw,targetpitch);
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
    
    public void lock(double rotationYaw, double rotationPitch){
        try {
            double f1 = cos(-rotationYaw * 0.017453292F - (float)Math.PI);
            double f2 = sin(-rotationYaw * 0.017453292F - (float)Math.PI);
            double f3 = -cos(-rotationPitch * 0.017453292F);
            double f4 = sin(-rotationPitch * 0.017453292F);
            Vector3d bodyvector = new Vector3d((double)(f2 * f3), (double)f4, (double)(f1 * f3));
            bodyvector.scale(-1);
            target = null;
            double predeg = -1;
            for (Object obj : worldObj.loadedEntityList) {
                Entity aEntity = (Entity) obj;
                if (!aEntity.isDead) {
                    if (aEntity.worldObj == this.worldObj && aEntity.canBeCollidedWith() && currentEntity != null && aEntity != currentEntity.ridingEntity) {
                        double distsq = currentEntity.getDistanceSqToEntity(aEntity);
                        if (distsq < 16777216) {
                            Vector3d totgtvec = new Vector3d(currentEntity.posX - aEntity.posX, currentEntity.posY - aEntity.posY, currentEntity.posZ - aEntity.posZ);
                            if (totgtvec.length() > 1) {
                                totgtvec.scale(1);
                                double deg = wrapAngleTo180_double(toDegrees(totgtvec.angle(bodyvector)));
                                if (target == null || (abs(deg) < predeg || predeg == -1)) {
                                    predeg = deg;
                                    target = aEntity;
                                }
                            }
                        }
                    }
                }
            }
            if(currentEntity instanceof EntityPlayerMP && target != null){
                HMGPacketHandler.INSTANCE.sendTo(new PacketSpawnParticle(target.posX, target.posY + target.height/2,target.posZ, 2), (EntityPlayerMP) currentEntity);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
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
            if (targetyaw > turretanglelimtYawMax) {
                targetyaw = turretanglelimtYawMax;
                inrange = false;
            } else if (targetyaw < turretanglelimtYawmin) {
                targetyaw = turretanglelimtYawmin;
                inrange = false;
            }
        }else {
            targetyaw = turretanglelimtYawmin;
        }
        if(traverseSound != null && (abs(turretrotationYaw - targetyaw)>1 || abs(turretrotationPitch - targetpitch)>1))
            currentEntity.playSound(traverseSound, traversesoundLV, traversesoundPitch);
        
        double AngulardifferenceYaw = targetyaw - this.turretrotationYaw;
        AngulardifferenceYaw = wrapAngleTo180_double(AngulardifferenceYaw);
        if(Double.isNaN(targetyaw))AngulardifferenceYaw = 0;
        boolean result1 = false;
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
        AxisAngle4d axisxangledY = new AxisAngle4d(axisY, toRadians(targetyaw)/2);
        turretRot = quatRotateAxis(turretRot,axisxangledY);
        
        Vector3d axisX = transformVecByQuat(unitX, turretRot);
        AxisAngle4d axisxangledX = new AxisAngle4d(axisX, toRadians(-targetpitch)/2);
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
        
        return ready = result1 && result2 && inrange && canfire;
    }
    public void setmotherpos(Vector3d motherPos,Quat4d motherRot){
        this.motherRot = motherRot;
        this.motherPos = motherPos;
        turretRot = new Quat4d(0,0,0,1);
        
        Vector3d temppos = getGlobalVector_fromLocalVector_onMother(onmotherPos);
        
        
        temppos.add(this.motherPos);
        
        this.pos = temppos;
    }
    public void update(Quat4d motherRot,Vector3d motherPos){
        this.motherRot = motherRot;
        this.motherPos = motherPos;
        turretRot = new Quat4d(0,0,0,1);
        
        Vector3d temppos = getGlobalVector_fromLocalVector_onMother(onmotherPos);
        
        
        temppos.add(this.motherPos);
        
        this.pos = temppos;
        
        Vector3d axisY = transformVecByQuat(unitY, turretRot);
        AxisAngle4d axisxangledY = new AxisAngle4d(axisY, toRadians(turretrotationYaw)/2);
        turretRot = quatRotateAxis(turretRot,axisxangledY);
        
        
        for(TurretObj achild :childs){
            Quat4d temp = new Quat4d(this.motherRot);
            temp.mul(turretRot);
            achild.update(temp, this.pos);
        }
        
        Vector3d axisX = transformVecByQuat(unitX, turretRot);
        AxisAngle4d axisxangledX = new AxisAngle4d(axisX, toRadians(-turretrotationPitch)/2);
        turretRot = quatRotateAxis(turretRot,axisxangledX);
        
        for(TurretObj achild :childsOnBarrel){
            Quat4d temp = new Quat4d(this.motherRot);
            temp.mul(turretRot);
            achild.update(temp, this.pos);
        }
        cycle_timer--;
        if(magazineMax != -1 && magazinerem <= 0 && reloadSetting != -1){
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
    public void fire(){
        Vector3d Vec_transformedbybody = getGlobalVector_fromLocalVector(cannonpos);
        
        transformVecforMinecraft(Vec_transformedbybody);
        if((magazineMax == -1 || magazinerem > 0) && cycle_timer <0){
            cycle_timer = cycle_setting;
            magazinerem--;
            if (!this.worldObj.isRemote) {
                Vector3d lookVec = getCannonDir();
                transformVecforMinecraft(lookVec);
                if(firesound != null)HMGPacketHandler.INSTANCE.sendToAll(new PacketPlaysound(currentEntity, firesound, firesoundPitch, firesoundLV));
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
                if(bullets != null)for(HMGEntityBulletBase abullet:bullets) {
                    abullet.gra = (float) (gravity / cfg_defgravitycof);
                    abullet.setLocationAndAngles(
                            pos.x + Vec_transformedbybody.x,
                            pos.y + Vec_transformedbybody.y,
                            -pos.z + Vec_transformedbybody.z,
                            (float) turretrotationYaw, (float) turretrotationPitch);
                    abullet.setThrowableHeading(lookVec.x, lookVec.y, lookVec.z, speed, spread, currentEntity);
                    abullet.canbounce = false;
                    abullet.fuse = fuse;
                    abullet.acceleration = acceler;
                    if(canHoming){
                        abullet.induction_precision = induction_precision;
                        abullet.homingEntity = target;
                    }
                    this.worldObj.spawnEntityInWorld(abullet);
                }
                if(flushName != null){
                    PacketSpawnParticle flash = new PacketSpawnParticle(
                                                                               pos.x + Vec_transformedbybody.x + lookVec.x * flushoffset,
                                                                               pos.y + Vec_transformedbybody.y + lookVec.y * flushoffset,
                                                                               -pos.z + Vec_transformedbybody.z + lookVec.z * flushoffset,
                                                                               toDegrees(-atan2(lookVec.x, lookVec.z)),
                                                                               toDegrees(-asin(lookVec.y)), 100, flushName, true);
                    flash.fuse = flushfuse;
                    flash.scale = flushscale;
                    flash.id = 100;
                    HMGPacketHandler.INSTANCE.sendToAll(flash);
                }
            }
        }
    }
    
    public void fireall(){
        this.fire();
        for(TurretObj achild : childs){
            achild.currentEntity = this.currentEntity;
            achild.fireall();
        }
    }
    public void  addchild(TurretObj child){
        childs.add(child);
    }
    public void  addchildonBarrel(TurretObj child){
        childsOnBarrel.add(child);
    }
    
    public ArrayList<TurretObj> getChilds(){
        return childs;
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
        Vector3d temp = getGlobalVector_fromLocalVector(new Vector3d(cannonpos));
        thisposVec.add(temp);
        
        transformVecforMinecraft(thisposVec);
        Vector3d targetVec = new Vector3d(target.posX, target.posY, target.posZ);
        targetVec.sub(thisposVec);
        targetVec.normalize();
        float targetpitch = wrapAngleTo180_float(-(float) CalculateGunElevationAngle(thisposVec.x, thisposVec.y, thisposVec.z, target, gravity, speed)[0]);
        float targetyaw = (float) -toDegrees(atan2(targetVec.x, targetVec.z));
        
        return aimtoAngle(targetyaw,targetpitch);
    }
    
    
    public boolean aimToPos(double targetX,double targetY,double targetZ) {
        Vector3d thisposVec = new Vector3d(pos);
        Vector3d temp = getGlobalVector_fromLocalVector(new Vector3d(cannonpos));
        thisposVec.add(temp);
        
        transformVecforMinecraft(thisposVec);
        Vector3d targetVec = new Vector3d(targetX, targetY, targetZ);
        targetVec.sub(thisposVec);
        targetVec.normalize();
        float targetpitch = wrapAngleTo180_float(-(float) CalculateGunElevationAngle(thisposVec.x, thisposVec.y, thisposVec.z, targetX, targetY, targetZ, gravity, speed)[0]);
        float targetyaw = (float) -toDegrees(atan2(targetVec.x, targetVec.z));
        
        return aimtoAngle(targetyaw,targetpitch);
    }
    
}
