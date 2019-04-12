package hmggvcmob.entity;

import handmadeguns.HandmadeGunsCore;
import hmggvcmob.network.GVCMMessageKeyPressed;
import hmggvcmob.network.GVCMPacketHandler;
import hmggvcmob.util.Calculater;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

import javax.vecmath.AxisAngle4d;
import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;

import java.util.ArrayList;

import static hmggvcmob.GVCMobPlus.cfgVehicleWheel_DownRange;
import static hmggvcmob.GVCMobPlus.cfgVehicleWheel_UpRange;
import static hmggvcmob.GVCMobPlus.proxy;
import static hmggvcmob.event.GVCMXEntityEvent.soundedentity;
import static hmggvcmob.util.Calculater.*;
import static java.lang.Math.*;
import static net.minecraft.util.MathHelper.wrapAngleTo180_double;
import static net.minecraft.util.MathHelper.wrapAngleTo180_float;

public class TankBaseLogic {
    public float bodyrotationYaw;
    public float bodyrotationPitch;
    public float bodyrotationRoll;
    public float turretrotationYaw;
    public float turretrotationPitch;

    public float prevturretrotationYaw;
    public float prevturretrotationPitch;
    public float prevbodyrotationYaw;
    public float prevbodyrotationPitch;
    public float prevbodyrotationRoll;

    public float turretanglelimtYawMax = 360;
    public float turretanglelimtYawmin = -360;
    public float turretanglelimtPitchMax = 15;
    public float turretanglelimtPitchmin = -15;
    public float rotationmotion;
    public float throttle;

    public boolean server1 = false;
    public boolean server2 = false;
    public boolean serverspace = false;
    public boolean serverx = false;
    public boolean serverw = false;
    public boolean servers = false;
    public boolean servera = false;
    public boolean serverd = false;
    public boolean serverf = false;

    EntityLiving tank;
    ITank idriveableVehicle;
    float yawspeed;
    float speed;
    int tracksoundticks;
    boolean ismanual;
    boolean spinturn;
    public boolean canControlonWater;
    String tracksound;
    
    public boolean gear = true;

    public Quat4d bodyRot = new Quat4d(0,0,0,1);
    public Quat4d turretRot = new Quat4d(0,0,0,1);
    public static final Vector3d unitX = new Vector3d(1,0,0);
    public static final Vector3d unitY = new Vector3d(0,1,0);
    public static final Vector3d unitZ = new Vector3d(0,0,1);
    public TankBaseLogic(EntityLiving entityLiving,float yawspeed,float speed,String sound){
        this.tank = entityLiving;
        this.idriveableVehicle = (ITank) entityLiving;
        this.yawspeed = yawspeed;
        this.speed = speed;
        tracksound = sound;
    }
    public TankBaseLogic(EntityLiving entityLiving,float yawspeed,float speed,boolean spinturn,String sound){
        this(entityLiving,yawspeed,speed,sound);
        this.spinturn = spinturn;
    }
    public void updateCommon(){
        ismanual = !((tank instanceof IRideableTank) && ((IRideableTank) tank).standalone());




        {
            bodyRot = new Quat4d(0,0,0,1);

            Vector3d axisy = transformVecByQuat(new Vector3d(unitY), bodyRot);
            AxisAngle4d axisyangled = new AxisAngle4d(axisy, toRadians(bodyrotationYaw)/2);
            bodyRot = quatRotateAxis(bodyRot,axisyangled);

            Vector3d axisx = transformVecByQuat(new Vector3d(unitX), bodyRot);
            AxisAngle4d axisxangled = new AxisAngle4d(axisx, toRadians(-bodyrotationPitch)/2);
            bodyRot = quatRotateAxis(bodyRot,axisxangled);

            Vector3d axisz = transformVecByQuat(new Vector3d(unitZ), bodyRot);
            AxisAngle4d axiszangled = new AxisAngle4d(axisz, toRadians(bodyrotationRoll)/2);
            bodyRot = quatRotateAxis(bodyRot,axiszangled);

            idriveableVehicle.setBodyrot(bodyRot);
        }


        ((ModifiedBoundingBox)tank.boundingBox).rot.set(bodyRot);
        ((ModifiedBoundingBox)tank.boundingBox).posX = tank.posX;
        ((ModifiedBoundingBox)tank.boundingBox).posY = tank.posY;
        ((ModifiedBoundingBox)tank.boundingBox).posZ = tank.posZ;


        tracksoundticks--;
    }
    public void updateClient(){
        this.prevbodyrotationYaw = this.bodyrotationYaw;


        this.prevturretrotationPitch = this.turretrotationPitch;
        this.prevturretrotationYaw = this.turretrotationYaw;



        this.turretrotationYaw = idriveableVehicle.getTurretrotationYaw();
        this.turretrotationPitch = idriveableVehicle.getTurretrotationPitch();
        this.bodyrotationYaw = idriveableVehicle.getRotationYaw();
        this.bodyrotationPitch = idriveableVehicle.getRotationPitch();
        this.bodyrotationRoll = idriveableVehicle.getRotationRoll();

        tank.rotationYaw = this.bodyrotationYaw;
        tank.prevRotationYaw = this.prevbodyrotationYaw;
        tank.prevRotationYawHead = this.prevturretrotationYaw;

        while (this.bodyrotationYaw - this.prevbodyrotationYaw < -180.0F)
        {
            this.prevbodyrotationYaw -= 360.0F;
        }

        while (this.bodyrotationYaw - this.prevbodyrotationYaw >= 180.0F)
        {
            this.prevbodyrotationYaw += 360.0F;
        }
        while (this.turretrotationYaw - this.prevturretrotationYaw < -180.0F)
        {
            this.prevturretrotationYaw -= 360.0F;
        }

        while (this.turretrotationYaw - this.prevturretrotationYaw >= 180.0F)
        {
            this.prevturretrotationYaw += 360.0F;
        }

        ismanual = (tank instanceof IRideableTank) && !((IRideableTank) tank).standalone();
        if(ismanual && tank.riddenByEntity == proxy.getEntityPlayerInstance()){
            ArrayList<Integer> keys = new ArrayList<>();
            if (proxy.leftclick()) {
                keys.add(11);
            }
            if (proxy.rightclick()) {
                keys.add(12);
            }
            if (proxy.jumped()) {
                keys.add(13);
            }
            if (proxy.xclick()) {
                keys.add(14);
            }
            if (proxy.wclick()) {
                keys.add(16);
            }
            if (proxy.aclick()) {
                keys.add(17);
            }
            if (proxy.sclick()) {
                keys.add(18);
            }
            if (proxy.dclick()) {
                keys.add(19);
            }
            if (proxy.fclick()) {
                keys.add(20);
            }
            int[] i = new int[keys.size()];
            for(int id = 0; id < i.length ; id++){
                i[id] = keys.get(id);
            }
            GVCMPacketHandler.INSTANCE.sendToServer(new GVCMMessageKeyPressed(i, tank.getEntityId()));
        }
    }
    public void updateServer(){
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
                bodyrotationPitch *= 0.99 * (idriveableVehicle.ishittingWater() ? 0.9:1);
                bodyrotationRoll *= 0.99 * (idriveableVehicle.ishittingWater() ? 0.9:1);
                if(tank.onGround || (canControlonWater && idriveableVehicle.ishittingWater())) {
                    bodyrotationPitch += ((float) -toDegrees(sin(pitchVec.yCoord)) - bodyrotationPitch) * 0.2;
//                System.out.println("debug " + bodyrotationPitch);
                    bodyrotationRoll += ((float) -toDegrees(sin(rollVec.yCoord)) - bodyrotationRoll) * 0.2;
                }
                if(idriveableVehicle.ishittingWater()){
                    bodyrotationPitch *=0.9;
                    bodyrotationRoll *=0.9;
                }

//                if(tank.worldObj.isRemote){
//                    System.out.println(vec1);
//                    System.out.println(vec2);
//                    System.out.println(normal);
//                }
            }
        }


        bodyrotationYaw = wrapAngleTo180_float(bodyrotationYaw);
        turretrotationYaw = wrapAngleTo180_float(turretrotationYaw);
        idriveableVehicle.setTurretrotationYaw(this.turretrotationYaw);
        idriveableVehicle.setTurretrotationPitch(this.turretrotationPitch);
        idriveableVehicle.setRotationYaw(bodyrotationYaw);
        idriveableVehicle.setRotationPitch(bodyrotationPitch);
        idriveableVehicle.setRotationRoll(bodyrotationRoll);

        boolean needTracksound = false;
        ismanual = (tank instanceof IRideableTank) && !((IRideableTank) tank).standalone();
        if(ismanual && tank.riddenByEntity != null){
            aimMainTurret_toAngle(tank.riddenByEntity.getRotationYawHead(),tank.riddenByEntity.rotationPitch);
        }
        needTracksound = control();
        
        rotationmotion*=idriveableVehicle.ishittingWater()?0.95:tank.onGround ? 0.8f:0.999;
        if(tank.onGround || (canControlonWater && idriveableVehicle.ishittingWater()))bodyrotationYaw += rotationmotion;
        if(abs(rotationmotion)<0.01)rotationmotion = 0;

        tracksoundticks--;
        if ((abs(throttle) > 0.0||needTracksound)&& tracksoundticks < 0) {
            tank.worldObj.playSoundEffect(tank.posX, tank.posY, tank.posZ, tracksound, 4, throttle < 0.05 ? 1 : 0.8f + throttle/10);
            tank.getEntityData().setFloat("GunshotLevel", 4);
            if (tank.getEntityData().getFloat("GunshotLevel") < 0.1)
                soundedentity.add(tank);
            tracksoundticks = 4;
        }
    }

    public boolean control(){
        boolean needTracksound = false;
        if(ismanual && tank instanceof IRideableTank && tank.riddenByEntity != null){
            if (servera) {
                rotationmotion -= (throttle + (spinturn && abs(throttle) == 0? 1 : 0)) * yawspeed * (tank.onGround? 1 : idriveableVehicle.ishittingWater() ? 0.5 : 0);
                if(abs(throttle)>0||spinturn)needTracksound = true;
            }else
            if (serverd) {
                rotationmotion += (throttle + (spinturn && abs(throttle) == 0 ? 1 : 0)) * yawspeed * (tank.onGround? 1 : idriveableVehicle.ishittingWater() ? 0.5 : 0);
                if(abs(throttle)>0||spinturn)needTracksound = true;
            }
            if (serverw) {throttle += 0.45f;
            }else
            if (servers) {throttle -= 0.45f;;
            }
            if (serverspace){throttle = 0;
            }
            if (server2){
                ((IRideableTank)tank).mainFire();
            }
            if(server1){
                ((IRideableTank)tank).subFire();
            }
            movebyPlayer(0,0.29400003f,0.29999995f);
        }
    
        if(!ismanual) {
            if(tank.getNavigator().noPath() && tank.getAttackTarget() != null){
                Entity target = tank.getAttackTarget();
                Vector3d courseVec = new Vector3d(target.posX,target.posY,target.posZ);
                courseVec.sub(new Vector3d(tank.posX, tank.posY, tank.posZ));
                courseVec.normalize();
                float targetyaw = wrapAngleTo180_float(-(float) toDegrees(atan2(courseVec.x, courseVec.z)));
                double angledef = targetyaw - this.bodyrotationYaw;
                if (angledef > 15) {
                    throttle += 0.45;
                    rotationmotion += (throttle + (spinturn && abs(throttle) == 0 ? 1 : 0)) * yawspeed * (tank.onGround ? 1 : idriveableVehicle.ishittingWater() ? 0.5 : 0);
                    if (abs(throttle) > 0 || spinturn) needTracksound = true;
                } else if (angledef < -15) {
                    throttle += 0.45;
                    rotationmotion -= (throttle + (spinturn && abs(throttle) == 0 ? 1 : 0)) * yawspeed * (tank.onGround ? 1 : idriveableVehicle.ishittingWater() ? 0.5 : 0);
                    if (abs(throttle) > 0 || spinturn) needTracksound = true;
                } else {
                    rotationmotion *= 0.25f;
                }
            }else {
                float angledef = -wrapAngleTo180_float(bodyrotationYaw - tank.rotationYaw);
                if (angledef > 15) {
                    rotationmotion += (throttle + (spinturn && abs(throttle) == 0 ? 1 : 0)) * yawspeed * (tank.onGround ? 1 : idriveableVehicle.ishittingWater() ? 0.5 : 0);
                    if (abs(throttle) > 0 || spinturn) needTracksound = true;
                } else if (angledef < -15) {
                    rotationmotion -= (throttle + (spinturn && abs(throttle) == 0 ? 1 : 0)) * yawspeed * (tank.onGround ? 1 : idriveableVehicle.ishittingWater() ? 0.5 : 0);
                    if (abs(throttle) > 0 || spinturn) needTracksound = true;
                } else {
                    rotationmotion *= 0.25f;
                }
            }
        }
        if(throttle > 5){
            throttle = 5;
        }else if(throttle < -2.5){
            throttle = -2.5f;
        }else
        if(abs(throttle)<1){
            throttle *= 0.9;
        }
        
        if(abs(throttle)<0.01)throttle = 0;
        
        return needTracksound;
    }
    public void moveFlying(float p_70060_1_, float front_speed, float p_70060_3_)
    {
        ismanual = (tank instanceof IRideableTank) && !((IRideableTank) tank).standalone();
        if (!ismanual)
        {
            float f3 = p_70060_1_ * p_70060_1_ + front_speed * front_speed;
    
            if (f3 >= 1.0E-4F)
            {
                f3 = MathHelper.sqrt_float(f3);
        
                if (f3 < 1.0F)
                {
                    f3 = 1.0F;
                }
                f3 = p_70060_3_ / f3;
                f3 = abs(f3);
                throttle += 0.45f;
                f3 *= throttle * 0.15 * speed;
                if(idriveableVehicle.ishittingWater())f3 *= 0.4;
                front_speed *= f3;
                if(tank.onGround || (canControlonWater && idriveableVehicle.ishittingWater())) {
                    Vector3d frontVec = getTransformedVector_onbody(new Vector3d(0,0,-1));
                    frontVec.scale(front_speed);
                    transformVecforMinecraft(frontVec);
                    tank.motionX += frontVec.x;
                    tank.motionY += frontVec.y;
                    tank.motionZ += frontVec.z;
                }
            }else {
                throttle *= 0.9;
            }
        }
    }
    public void movebyPlayer(float p_70060_1_, float front_speed, float p_70060_3_)
    {
        float f3 = p_70060_1_ * p_70060_1_ + front_speed * front_speed;

        if (f3 >= 1.0E-4F)
        {
            f3 = MathHelper.sqrt_float(f3);

            if (f3 < 1.0F)
            {
                f3 = 1.0F;
            }
            f3 = p_70060_3_ / f3;
            f3 = abs(f3);
            f3 *= throttle * 0.15 * speed;
            if(idriveableVehicle.ishittingWater())f3 *= 0.4;
            front_speed *= f3;
//			System.out.println("debug" + throttle);
            if(tank.onGround || (canControlonWater && idriveableVehicle.ishittingWater())) {

                Vector3d frontVec = getTransformedVector_onbody(new Vector3d(0,0,-1));
                frontVec.scale(front_speed);
                transformVecforMinecraft(frontVec);
                tank.motionX += frontVec.x;
                tank.motionY += frontVec.y;
                tank.motionZ += frontVec.z;
            }
        }
    }
    public boolean aimMainTurret_toAngle(double targetyaw, double targetpitch){
        idriveableVehicle.getMainTurret().currentEntity = tank;
        boolean result = idriveableVehicle.getMainTurret().aimtoAngle(targetyaw,targetpitch);
        turretrotationYaw = (float) idriveableVehicle.getMainTurret().turretrotationYaw;
        turretrotationPitch = (float) idriveableVehicle.getMainTurret().turretrotationPitch;
        return result;
    }
    public boolean aimMainTurret_toTarget(Entity target){
        if(idriveableVehicle.getMainTurret() != null) {
            idriveableVehicle.getMainTurret().currentEntity = tank;
            boolean result = idriveableVehicle.getMainTurret().aimToEntity(target);
            turretrotationYaw = (float) idriveableVehicle.getMainTurret().turretrotationYaw;
            turretrotationPitch = (float) idriveableVehicle.getMainTurret().turretrotationPitch;
            return result;
        }else return false;
    }
    public boolean aimMainTurret_toPos(double targetX,double targetY,double targetZ){
        if(idriveableVehicle.getMainTurret() != null) {
            idriveableVehicle.getMainTurret().currentEntity = tank;
            boolean result = idriveableVehicle.getMainTurret().aimToPos(targetX,targetY,targetZ);
            turretrotationYaw = (float) idriveableVehicle.getMainTurret().turretrotationYaw;
            turretrotationPitch = (float) idriveableVehicle.getMainTurret().turretrotationPitch;
            return result;
        }else return false;
    }

//    public Vector3d getTransformedVector_onturret(Vector3d naturalVector,Vector3d turretYawCenterpos){
//        Quat4d turretyawrot = new Quat4d(0,0,0,1);
//
//        Vector3d axisy = Calculater.transformVecByQuat(new Vector3d(0,1,0), turretyawrot);
//        AxisAngle4d axisyangled = new AxisAngle4d(axisy, toRadians(turretrotationYaw)/2);
//        turretyawrot = Calculater.quatRotateAxis(turretyawrot,axisyangled);
//        Vector3d Vec_transformedbyturret = Calculater.transformVecByQuat(naturalVector,turretyawrot);
//
//        Vector3d Vec_transformedbybody = Calculater.transformVecByQuat(Vec_transformedbyturret,bodyRot);
//
//        Vector3d TurretRotCenterVec_transformedbybody = getTransformedVector_onbody(turretYawCenterpos);
//        Vec_transformedbybody.add(TurretRotCenterVec_transformedbybody);
//
//        return Vec_transformedbybody;
//    }

//    public Vector3d getCannonDir(){
//        Vector3d lookVec = new Vector3d(0,0,-1);
//        lookVec = transformVecByQuat(lookVec,turretRot);
//        lookVec = transformVecByQuat(lookVec,bodyRot);
//
//        return lookVec;
//    }
    public Vector3d getTransformedVector_onbody(Vector3d naturalVector){
        return Calculater.transformVecByQuat(naturalVector,bodyRot);
    }

//    public boolean turretMove(double targetyaw, double targetpitch){
//
//        boolean inrange = true;
//        if(targetyaw > turretanglelimtYawMax){
//            targetyaw = turretanglelimtYawMax;
//            inrange = false;
//        }else
//        if(targetyaw < turretanglelimtYawmin){
//            targetyaw = turretanglelimtYawmin;
//            inrange = false;
//        }
//
//        if(targetpitch > turretanglelimtPitchMax){
//            targetpitch = turretanglelimtPitchMax;
//            inrange = false;
//        }else
//        if(targetpitch < turretanglelimtPitchmin){
//            targetpitch = turretanglelimtPitchmin;
//            inrange = false;
//        }
//        if(abs(turretrotationYaw - targetyaw)>1 || abs(turretrotationPitch - targetpitch)>1)
//            tank.playSound("gvcmob:gvcmob.traverseSound", 0.5F, 1f);
//
//        double AngulardifferenceYaw = targetyaw - this.turretrotationYaw;
//        AngulardifferenceYaw = wrapAngleTo180_double(AngulardifferenceYaw);
//        if(Double.isNaN(targetyaw))AngulardifferenceYaw = 0;
//        boolean result1 = false;
//        if(AngulardifferenceYaw > 5){
//            turretrotationYaw += 5;
//        }else if(AngulardifferenceYaw < -5){
//            turretrotationYaw -= 5;
//        }else {
//            this.turretrotationYaw += AngulardifferenceYaw;
//            result1 = true;
//        }
//
//
//        double AngulardifferencePitch = targetpitch - this.turretrotationPitch;
//        if(Double.isNaN(targetpitch))AngulardifferencePitch = 0;
//        AngulardifferencePitch = wrapAngleTo180_double(AngulardifferencePitch);
//        boolean result2 = false;
//
//        if(AngulardifferencePitch > 5){
//            turretrotationPitch += 5;
//        }else if(AngulardifferencePitch < -5){
//            turretrotationPitch -= 5;
//        }else {
//            this.turretrotationPitch += AngulardifferencePitch;
//            result2 = true;
//        }
//        turretRot = new Quat4d(0,0,0,1);
//
//
//        Vector3d axisY = transformVecByQuat(unitY, turretRot);
//        AxisAngle4d axisxangledY = new AxisAngle4d(axisY, toRadians(targetyaw)/2);
//        turretRot = quatRotateAxis(turretRot,axisxangledY);
//
//        Vector3d axisX = transformVecByQuat(unitX, turretRot);
//        AxisAngle4d axisxangledX = new AxisAngle4d(axisX, toRadians(-targetpitch)/2);
//        turretRot = quatRotateAxis(turretRot,axisxangledX);
//
//        return result1 && result2 && inrange;
//    }
    public void setLocationAndAngles(float p_70012_7_, float p_70012_8_){
        bodyrotationYaw = p_70012_7_;
        bodyrotationPitch = p_70012_8_;
    }


    public void setControl_RightClick(boolean value) {
        server1 = value;
    }


    public void setControl_LeftClick(boolean value) {
        server2 = value;
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


//    public boolean getControl_RightClick() {
//        return server1;
//    }
}
