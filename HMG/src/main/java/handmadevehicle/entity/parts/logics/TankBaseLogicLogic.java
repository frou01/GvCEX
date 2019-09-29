//package handmadevehicle.entity.parts.logics;
//
//import handmadevehicle.Utils;
//import handmadevehicle.network.HMVPacketHandler;
//import handmadevehicle.entity.parts.*;
//import handmadevehicle.entity.prefab.Prefab_Vehicle_Base;
//import handmadevehicle.entity.prefab.Prefab_Vehicle_LandVehicle;
//import handmadevehicle.network.packets.*;
//import net.minecraft.entity.Entity;
//import net.minecraft.entity.EntityLiving;
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.entity.SharedMonsterAttributes;
//import net.minecraft.util.MathHelper;
//import net.minecraft.util.MovingObjectPosition;
//import net.minecraft.util.Vec3;
//
//import javax.vecmath.AxisAngle4d;
//import javax.vecmath.Quat4d;
//import javax.vecmath.Vector3d;
//
//import java.util.ArrayList;
//
//import static handmadevehicle.HMVehicle.*;
//import static handmadevehicle.Utils.*;
//import static java.lang.Math.*;
//import static net.minecraft.util.MathHelper.wrapAngleTo180_float;
//
//public class TankBaseLogicLogic extends BaseLogic implements IbaseLogic ,MultiRiderLogics{
//
//    public boolean server1 = false;
//    public boolean server2 = false;
//    public boolean serverspace = false;
//    public boolean serverw = false;
//    public boolean servers = false;
//    public boolean servera = false;
//    public boolean serverd = false;
//    public boolean serverf = false;
//    public boolean serverx = false;
//
//    public float rotationmotion;
//
//    public static final Vector3d unitX = new Vector3d(1,0,0);
//    public static final Vector3d unitY = new Vector3d(0,1,0);
//    public static final Vector3d unitZ = new Vector3d(0,0,1);
//    ITank idriveableVehicle;
//    int tracksoundticks;
//    boolean ismanual = true;
//
//    public Prefab_Vehicle_LandVehicle tankinfo;
//
//    public TankBaseLogicLogic(EntityLiving entityLiving) {
//        super(entityLiving.worldObj,entityLiving);
//        this.mc_Entity = entityLiving;
//        this.worldObj = entityLiving.worldObj;
//        this.idriveableVehicle = (ITank) entityLiving;
//        this.iVehicle = (IVehicle) entityLiving;
//        if(entityLiving instanceof IMultiTurretVehicle)
//            this.iMultiTurretVehicle = (IMultiTurretVehicle) entityLiving;
//        info = tankinfo = new Prefab_Vehicle_LandVehicle();
//    }
//    public TankBaseLogicLogic(EntityLiving entityLiving, float yawspeed, float speed, String sound){
//        this(entityLiving);
//        info = tankinfo = new Prefab_Vehicle_LandVehicle();
//        tankinfo.yawspeed = yawspeed;
//        tankinfo.speed = speed;
//    }
//    public TankBaseLogicLogic(EntityLiving entityLiving, float yawspeed, float speed, boolean spinturn, String sound){
//        this(entityLiving,yawspeed,speed,sound);
//        tankinfo.spinturn = spinturn;
//    }
//    public void updateCommon(){
//        ismanual = !((mc_Entity instanceof Hasmode) && ((Hasmode) mc_Entity).standalone());
//        while (this.bodyrotationYaw - this.prevbodyrotationYaw < -180.0F)
//        {
//            this.prevbodyrotationYaw -= 360.0F;
//        }
//
//        while (this.bodyrotationYaw - this.prevbodyrotationYaw >= 180.0F)
//        {
//            this.prevbodyrotationYaw += 360.0F;
//        }
//        super.updateCommon();
//
//        ((ModifiedBoundingBox) mc_Entity.boundingBox).rot.set(bodyRot);
//        ((ModifiedBoundingBox) mc_Entity.boundingBox).update(mc_Entity.posX, mc_Entity.posY, mc_Entity.posZ);
//
//
//        tracksoundticks--;
//
//        if(mc_Entity instanceof EntityLivingBase)((EntityLivingBase) mc_Entity).getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
//        riderPosUpdate();
//    }
//    public void updateClient(){
//        double[] xyz = Utils.eulerfrommatrix(Utils.matrixfromQuat(bodyRot));
//        bodyrotationPitch = (float) toDegrees(xyz[0]);
//        if(!Double.isNaN(xyz[1])){
//            bodyrotationYaw = (float) toDegrees(xyz[1]);
//        }
//        bodyrotationRoll = (float) toDegrees(xyz[2]);
//
//        updateRider();
//
//        if(needStartSound){
//            needStartSound = false;
//            HMV_Proxy.playsoundasVehicle(64, mc_Entity);
//        }
//        needStartSound = getsound() != null;
//        this.prevbodyrotationYaw = this.bodyrotationYaw;
//
//        mc_Entity.rotationYaw = this.bodyrotationYaw;
//        mc_Entity.prevRotationYaw = this.prevbodyrotationYaw;
//
//
//        ismanual = !((mc_Entity instanceof Hasmode) && ((Hasmode) mc_Entity).standalone());
//        if(ismanual && mc_Entity.riddenByEntity == HMV_Proxy.getEntityPlayerInstance()){
//            ArrayList<Integer> keys = new ArrayList<>();
//            if (HMV_Proxy.leftclick()) {
//                keys.add(11);
//            }
//            if (HMV_Proxy.rightclick()) {
//                keys.add(12);
//            }
//            if (HMV_Proxy.throttle_BrakeKeyDown()) {
//                keys.add(13);
//            }
//            if (HMV_Proxy.air_Brake_click()) {
//                keys.add(14);
//            }
//            if (HMV_Proxy.throttle_up_click()) {
//                keys.add(16);
//            }
//            if (HMV_Proxy.yaw_Left_click()) {
//                keys.add(17);
//            }
//            if (HMV_Proxy.throttle_down_click()) {
//                keys.add(18);
//            }
//            if (HMV_Proxy.yaw_Right_click()) {
//                keys.add(19);
//            }
//            if (HMV_Proxy.flap_click()) {
//                keys.add(20);
//            }
//            int[] i = new int[keys.size()];
//            for(int id = 0; id < i.length ; id++){
//                i[id] = keys.get(id);
//            }
//            HMVPacketHandler.INSTANCE.sendToServer(new HMVMMessageKeyPressed(i, mc_Entity.getEntityId()));
//        }
//    }
//    public void updateServer(){
//        updateRider();
//        {
//            bodyRot = new Quat4d(0,0,0,1);
//
//            Vector3d axisy = transformVecByQuat(new Vector3d(unitY), bodyRot);
//            AxisAngle4d axisyangled = new AxisAngle4d(axisy, toRadians(bodyrotationYaw)/2);
//            bodyRot = quatRotateAxis(bodyRot,axisyangled);
//
//            Vector3d axisx = transformVecByQuat(new Vector3d(unitX), bodyRot);
//            AxisAngle4d axisxangled = new AxisAngle4d(axisx, toRadians(-bodyrotationPitch)/2);
//            bodyRot = quatRotateAxis(bodyRot,axisxangled);
//
//            Vector3d axisz = transformVecByQuat(new Vector3d(unitZ), bodyRot);
//            AxisAngle4d axiszangled = new AxisAngle4d(axisz, toRadians(bodyrotationRoll)/2);
//            bodyRot = quatRotateAxis(bodyRot,axiszangled);
//
//        }
//        HMVPacketHandler.INSTANCE.sendToAll(new HMVPakcetVehicleState(mc_Entity.getEntityId(),bodyRot, throttle,false,false));
//        followGround();
//
//        bodyrotationYaw = wrapAngleTo180_float(bodyrotationYaw);
//
//        ismanual = !((mc_Entity instanceof Hasmode) && ((Hasmode) mc_Entity).standalone());
//        control();
//        if(throttle > tankinfo.throttle_Max){
//            throttle = tankinfo.throttle_Max;
//        }else if(throttle < tankinfo.throttle_min){
//            throttle = tankinfo.throttle_min;
//        }else
//        if(!serverw && !servers && abs(throttle)<1){
//            throttle *= 0.99;
//        }
//
//        if(abs(throttle)<0.0001)throttle = 0;
//
//        rotationmotion*= mc_Entity.onGround?0.8f:ishittingWater() ? 0.95:0.999;
//        if(mc_Entity.onGround || (tankinfo.canControlonWater && ishittingWater()))bodyrotationYaw += rotationmotion;
//        if(abs(rotationmotion)<0.01)rotationmotion = 0;
//
////        tracksoundticks--;
////        if ((abs(throttle) > 0.0||needTracksound)&& tracksoundticks < 0) {
////            tank.worldObj.playSoundEffect(tank.posX, tank.posY, tank.posZ, tracksound, 4, throttle < 0.05 ? 1 : 0.8f + throttle/10);
////            tank.getEntityData().setFloat("GunshotLevel", 4);
////            if (tank.getEntityData().getFloat("GunshotLevel") < 0.1)
////                soundedentity.add(tank);
////            tracksoundticks = 10;
////        }
//
//        if(iMultiTurretVehicle != null) {
//            HMVPacketHandler.INSTANCE.sendToAll(new HMVPakcetVehicleTurretSync(mc_Entity.getEntityId(), iMultiTurretVehicle));
//            if (ismanual && mc_Entity instanceof ITank && mc_Entity.riddenByEntity != null) {
//                iMultiTurretVehicle.Excontrol1(serverf);
//                iMultiTurretVehicle.Excontrol2(serverx);
//            }
//        }
//    }
//
//    public void followGround(){
//
//        {
//            Vec3 tankFrontVec_level;
//            Vec3 tankRight;
//            {
//                float f1;
//                float f2;
//                f1 = -MathHelper.cos(-bodyrotationYaw * 0.017453292F - (float) Math.PI);
//                f2 = -MathHelper.sin(-bodyrotationYaw * 0.017453292F - (float) Math.PI);
//                tankFrontVec_level = Vec3.createVectorHelper((double) (f2) * 1.5, (double) 0, (double) (f1) * 1.5);
//                tankRight = Vec3.createVectorHelper((f1)* 1.5, (double) 0, -(f2)* 1.5);
//            }
//            Vector3d tankFrontVec = transformVecByQuat(new Vector3d(0,0,-1),bodyRot);
//            {
//                Vector3d temp = new Vector3d(tankFrontVec);
//                temp.y = 0;
//                tankFrontVec.scale(1.5f/temp.length());
//            }
//            Vec3 FR;
//            Vec3 FL;
//            Vec3 BR;
//            Vec3 BL;
//            {
//                Vec3 vec3 = Vec3.createVectorHelper(mc_Entity.posX, mc_Entity.posY + tankFrontVec.y + cfgVehicleWheel_UpRange, mc_Entity.posZ);
//                vec3 = vec3.addVector(tankFrontVec_level.xCoord, tankFrontVec_level.yCoord, tankFrontVec_level.zCoord);
//                vec3 = vec3.addVector(tankRight.xCoord, tankRight.yCoord, tankRight.zCoord);
////            playerlook = Vec3.createVectorHelper(playerlook.xCoord * 256, playerlook.yCoord * 256, playerlook.zCoord * 256);
//                Vec3 vec31 = vec3.addVector(0, -cfgVehicleWheel_DownRange, 0);
//                MovingObjectPosition amovingObjectPosition = mc_Entity.worldObj.func_147447_a(vec3, vec31, false, true, false);
//                if(amovingObjectPosition == null)FR = vec31;
//                else FR = amovingObjectPosition.hitVec;
//            }
//            {
//                Vec3 vec3 = Vec3.createVectorHelper(mc_Entity.posX, mc_Entity.posY + tankFrontVec.y + cfgVehicleWheel_UpRange, mc_Entity.posZ);
//                vec3 = vec3.addVector(tankFrontVec_level.xCoord, tankFrontVec_level.yCoord, tankFrontVec_level.zCoord);
//                vec3 = vec3.addVector(-tankRight.xCoord, -tankRight.yCoord, -tankRight.zCoord);
////            playerlook = Vec3.createVectorHelper(playerlook.xCoord * 256, playerlook.yCoord * 256, playerlook.zCoord * 256);
//                Vec3 vec31 = vec3.addVector(0, -cfgVehicleWheel_DownRange, 0);
//                MovingObjectPosition amovingObjectPosition = mc_Entity.worldObj.func_147447_a(vec3, vec31, false, true, false);
//                if(amovingObjectPosition == null)FL = vec31;
//                else FL = amovingObjectPosition.hitVec;
//            }
//            {
//                Vec3 vec3 = Vec3.createVectorHelper(mc_Entity.posX, mc_Entity.posY - tankFrontVec.y + cfgVehicleWheel_UpRange, mc_Entity.posZ);
//                vec3 = vec3.addVector(-tankFrontVec_level.xCoord, -tankFrontVec_level.yCoord, -tankFrontVec_level.zCoord);
//                vec3 = vec3.addVector(tankRight.xCoord, tankRight.yCoord, tankRight.zCoord);
////            playerlook = Vec3.createVectorHelper(playerlook.xCoord * 256, playerlook.yCoord * 256, playerlook.zCoord * 256);
//                Vec3 vec31 = vec3.addVector(0, -cfgVehicleWheel_DownRange, 0);
//                MovingObjectPosition amovingObjectPosition = mc_Entity.worldObj.func_147447_a(vec3, vec31, false, true, false);
//                if(amovingObjectPosition == null)BR = vec31;
//                else BR = amovingObjectPosition.hitVec;
//            }
//            {
//                Vec3 vec3 = Vec3.createVectorHelper(mc_Entity.posX, mc_Entity.posY - tankFrontVec.y + cfgVehicleWheel_UpRange, mc_Entity.posZ);
//                vec3 = vec3.addVector(-tankFrontVec_level.xCoord, -tankFrontVec_level.yCoord, -tankFrontVec_level.zCoord);
//                vec3 = vec3.addVector(-tankRight.xCoord, -tankRight.yCoord, -tankRight.zCoord);
////            playerlook = Vec3.createVectorHelper(playerlook.xCoord * 256, playerlook.yCoord * 256, playerlook.zCoord * 256);
//                Vec3 vec31 = vec3.addVector(0, -cfgVehicleWheel_DownRange, 0);
//                MovingObjectPosition amovingObjectPosition = mc_Entity.worldObj.func_147447_a(vec3, vec31, false, true, false);
//                if(amovingObjectPosition == null)BL = vec31;
//                else BL = amovingObjectPosition.hitVec;
//            }
//
//            {
//                Vec3 vec1 = BL.addVector(-FR.xCoord,-FR.yCoord,-FR.zCoord);
//                Vec3 vec2 = BR.addVector(-FL.xCoord,-FL.yCoord,-FL.zCoord);
//                Vec3 normal = vec1.crossProduct(vec2).normalize();
//                tankRight = tankRight.normalize();
//                Vec3 pitchVec = normal.crossProduct(tankRight).normalize();
//                Vec3 rollVec = normal.crossProduct(pitchVec).normalize();
//                bodyrotationPitch *= (ishittingWater() ? 0.9:1);
//                bodyrotationRoll *= (ishittingWater() ? 0.9:1);
//                if(mc_Entity.onGround || (tankinfo.canControlonWater && ishittingWater())) {
//                    bodyrotationPitch += ((float) -toDegrees(sin(pitchVec.yCoord)) - bodyrotationPitch) * 0.2;
////                System.out.println("debug " + bodyrotationPitch);
//                    bodyrotationRoll += ((float) -toDegrees(sin(rollVec.yCoord)) - bodyrotationRoll) * 0.2;
//                }
//
////                if(tank.worldObj.isRemote){
////                    System.out.println(vec1);
////                    System.out.println(vec2);
////                    System.out.println(normal);
////                }
//            }
//        }
//    }
//    public boolean control(){
//        boolean needTracksound = false;
//        if(ismanual && mc_Entity instanceof ITank && mc_Entity.riddenByEntity != null){
//            if (servera) {
//                rotationmotion -= (throttle + (tankinfo.spinturn && abs(throttle) == 0? 1 : 0)) * tankinfo.yawspeed * (mc_Entity.onGround? 1 : ishittingWater() ? 0.5 : 0);
//                if(abs(throttle)>0||tankinfo.spinturn)needTracksound = true;
//            }else
//            if (serverd) {
//                rotationmotion += (throttle + (tankinfo.spinturn && abs(throttle) == 0 ? 1 : 0)) * tankinfo.yawspeed * (mc_Entity.onGround? 1 : ishittingWater() ? 0.5 : 0);
//                if(abs(throttle)>0||tankinfo.spinturn)needTracksound = true;
//            }
//            if (serverw) {throttle += tankinfo.throttle_speed;
//            }else
//            if (servers) {throttle -= tankinfo.throttle_speed/2;;
//            }
//            if (serverspace){throttle = 0;
//            }
//            if (server2){
//                ((ITank) mc_Entity).mainFire();
//            }
//            if(server1){
//                ((ITank) mc_Entity).subFire();
//            }
//            movebyPlayer(throttle * tankinfo.speed * 0.025f);
//        }
//
//        if(!ismanual) {
//            if(tankinfo.always_point_to_target && mc_Entity instanceof EntityLiving && ((EntityLiving) mc_Entity).getNavigator().noPath() && ((EntityLiving) mc_Entity).getAttackTarget() != null){
//                Entity target = ((EntityLiving) mc_Entity).getAttackTarget();
//                Vector3d courseVec = new Vector3d(target.posX,target.posY,target.posZ);
//                courseVec.sub(new Vector3d(mc_Entity.posX, mc_Entity.posY, mc_Entity.posZ));
//                courseVec.normalize();
//                float targetyaw = wrapAngleTo180_float(-(float) toDegrees(atan2(courseVec.x, courseVec.z)));
//                double angledef = targetyaw - this.bodyrotationYaw;
//                if (angledef > 15) {
//                    throttle += tankinfo.throttle_speed;
//                    rotationmotion += (throttle + (tankinfo.spinturn && abs(throttle) == 0 ? 1 : 0)) * tankinfo.yawspeed * (mc_Entity.onGround ? 1 : ishittingWater() ? 0.5 : 0);
//                    if (abs(throttle) > 0 || tankinfo.spinturn) needTracksound = true;
//                } else if (angledef < -15) {
//                    throttle += tankinfo.throttle_speed;
//                    rotationmotion -= (throttle + (tankinfo.spinturn && abs(throttle) == 0 ? 1 : 0)) * tankinfo.yawspeed * (mc_Entity.onGround ? 1 : ishittingWater() ? 0.5 : 0);
//                    if (abs(throttle) > 0 || tankinfo.spinturn) needTracksound = true;
//                } else {
//                    rotationmotion *= 0.25f;
//                }
//            }else {
//                float angledef = -wrapAngleTo180_float(bodyrotationYaw - mc_Entity.rotationYaw);
//                if (angledef > 15) {
//                    throttle += tankinfo.throttle_speed;
//                    rotationmotion += (throttle + (tankinfo.spinturn && abs(throttle) == 0 ? 1 : 0)) * tankinfo.yawspeed * (mc_Entity.onGround ? 1 : ishittingWater() ? 0.5 : 0);
//                    if (abs(throttle) > 0 || tankinfo.spinturn) needTracksound = true;
//                } else if (angledef < -15) {
//                    throttle += tankinfo.throttle_speed;
//                    rotationmotion -= (throttle + (tankinfo.spinturn && abs(throttle) == 0 ? 1 : 0)) * tankinfo.yawspeed * (mc_Entity.onGround ? 1 : ishittingWater() ? 0.5 : 0);
//                    if (abs(throttle) > 0 || tankinfo.spinturn) needTracksound = true;
//                } else {
//                    rotationmotion *= 0.25f;
//                }
//            }
//        }
//
//        return needTracksound;
//    }
//    public void moveFlying(float p_70060_1_, float front_speed, float p_70060_3_)
//    {
//        if(!ismanual) {
//            boolean direction = front_speed > 0;
//            boolean stop = front_speed != 0;
//            front_speed = throttle * tankinfo.speed * 0.025f;
//            if (ishittingWater()) front_speed *= 0.4;
//            if (mc_Entity.onGround || (tankinfo.canControlonWater && ishittingWater())) {
//                Vector3d frontVec = getTransformedVector_onbody(new Vector3d(0, 0, -1));
//                frontVec.scale(front_speed);
//                transformVecforMinecraft(frontVec);
//                mc_Entity.motionX += frontVec.x;
//                mc_Entity.motionY += frontVec.y;
//                mc_Entity.motionZ += frontVec.z;
//            }
//            if (stop) {
//                if (direction) {
//                    throttle += tankinfo.throttle_speed;
//                } else {
//                    throttle -= tankinfo.throttle_speed;
//                }
//            }
//        }
//    }
//    public void movebyPlayer(float front_speed)
//    {
//        if(ishittingWater())front_speed *= 0.4;
//        if(mc_Entity.onGround || (tankinfo.canControlonWater && ishittingWater())) {
//            Vector3d frontVec = getTransformedVector_onbody(new Vector3d(0,0,-1));
//            frontVec.scale(front_speed);
//            transformVecforMinecraft(frontVec);
//            mc_Entity.motionX += frontVec.x;
//            mc_Entity.motionY += frontVec.y;
//            mc_Entity.motionZ += frontVec.z;
//        }
//    }
//
////    public Vector3d getTransformedVector_onturret(Vector3d naturalVector,Vector3d turretYawCenterpos){
////        Quat4d turretyawrot = new Quat4d(0,0,0,1);
////
////        Vector3d axisy = Utils.transformVecByQuat(new Vector3d(0,1,0), turretyawrot);
////        AxisAngle4d axisyangled = new AxisAngle4d(axisy, toRadians(turretrotationYaw)/2);
////        turretyawrot = Utils.quatRotateAxis(turretyawrot,axisyangled);
////        Vector3d Vec_transformedbyturret = Utils.transformVecByQuat(naturalVector,turretyawrot);
////
////        Vector3d Vec_transformedbybody = Utils.transformVecByQuat(Vec_transformedbyturret,bodyRot);
////
////        Vector3d TurretRotCenterVec_transformedbybody = getTransformedVector_onbody(turretYawCenterpos);
////        Vec_transformedbybody.add(TurretRotCenterVec_transformedbybody);
////
////        return Vec_transformedbybody;
////    }
//
////    public Vector3d getCannonDir(){
////        Vector3d lookVec = new Vector3d(0,0,-1);
////        lookVec = transformVecByQuat(lookVec,turretRot);
////        lookVec = transformVecByQuat(lookVec,bodyRot);
////
////        return lookVec;
////    }
//
//    public Vector3d getTransformedVector_onbody(Vector3d naturalVector){
//        return Utils.transformVecByQuat(naturalVector,bodyRot);
//    }
//
////    public boolean turretMove(double targetyaw, double targetpitch){
////
////        boolean inrange = true;
////        if(targetyaw > turretanglelimtYawMax){
////            targetyaw = turretanglelimtYawMax;
////            inrange = false;
////        }else
////        if(targetyaw < turretanglelimtYawmin){
////            targetyaw = turretanglelimtYawmin;
////            inrange = false;
////        }
////
////        if(targetpitch > turretanglelimtPitchMax){
////            targetpitch = turretanglelimtPitchMax;
////            inrange = false;
////        }else
////        if(targetpitch < turretanglelimtPitchmin){
////            targetpitch = turretanglelimtPitchmin;
////            inrange = false;
////        }
////        if(abs(turretrotationYaw - targetyaw)>1 || abs(turretrotationPitch - targetpitch)>1)
////            tank.playSound("gvcmob:gvcmob.traverseSound", 0.5F, 1f);
////
////        double AngulardifferenceYaw = targetyaw - this.turretrotationYaw;
////        AngulardifferenceYaw = wrapAngleTo180_double(AngulardifferenceYaw);
////        if(Double.isNaN(targetyaw))AngulardifferenceYaw = 0;
////        boolean result1 = false;
////        if(AngulardifferenceYaw > 5){
////            turretrotationYaw += 5;
////        }else if(AngulardifferenceYaw < -5){
////            turretrotationYaw -= 5;
////        }else {
////            this.turretrotationYaw += AngulardifferenceYaw;
////            result1 = true;
////        }
////
////
////        double AngulardifferencePitch = targetpitch - this.turretrotationPitch;
////        if(Double.isNaN(targetpitch))AngulardifferencePitch = 0;
////        AngulardifferencePitch = wrapAngleTo180_double(AngulardifferencePitch);
////        boolean result2 = false;
////
////        if(AngulardifferencePitch > 5){
////            turretrotationPitch += 5;
////        }else if(AngulardifferencePitch < -5){
////            turretrotationPitch -= 5;
////        }else {
////            this.turretrotationPitch += AngulardifferencePitch;
////            result2 = true;
////        }
////        turretRot = new Quat4d(0,0,0,1);
////
////
////        Vector3d axisY = transformVecByQuat(unitY, turretRot);
////        AxisAngle4d axisxangledY = new AxisAngle4d(axisY, toRadians(targetyaw)/2);
////        turretRot = quatRotateAxis(turretRot,axisxangledY);
////
////        Vector3d axisX = transformVecByQuat(unitX, turretRot);
////        AxisAngle4d axisxangledX = new AxisAngle4d(axisX, toRadians(-targetpitch)/2);
////        turretRot = quatRotateAxis(turretRot,axisxangledX);
////
////        return result1 && result2 && inrange;
////    }
//
//    public void setLocationAndAngles(float p_70012_7_, float p_70012_8_){
//        bodyrotationYaw = p_70012_7_;
//        bodyrotationPitch = p_70012_8_;
//    }
//
//
//    public void setControl_RightClick(boolean value) {
//        server1 = value;
//    }
//
//
//    public void setControl_LeftClick(boolean value) {
//        server2 = value;
//    }
//
//
//    public void setControl_Space(boolean value) {
//        serverspace = value;
//    }
//
//
//    public void setControl_brake(boolean value) {
//        serverx = value;
//    }
//
//
//    public void setControl_throttle_up(boolean value) {
//        serverw = value;
//    }
//
//
//    public void setControl_yaw_Left(boolean value) {
//        servera = value;
//    }
//
//
//    public void setControl_throttle_down(boolean value) {
//        servers = value;
//    }
//
//
//    public void setControl_yaw_Right(boolean value) {
//        serverd = value;
//    }
//
//
//    public void setControl_flap(boolean value) {
//        serverf = value;
//    }
//
//    @Override
//    public boolean getControl_RightClick() {
//        return server1;
//    }
//
//    @Override
//    public boolean getControl_LeftClick() {
//        return server2;
//    }
//
//    @Override
//    public boolean getControl_Space() {
//        return serverspace;
//    }
//
//    @Override
//    public boolean getControl_x() {
//        return serverx;
//    }
//
//    @Override
//    public boolean getControl_w() {
//        return serverw;
//    }
//
//    @Override
//    public boolean getControl_a() {
//        return servera;
//    }
//
//    @Override
//    public boolean getControl_s() {
//        return servers;
//    }
//
//    @Override
//    public boolean getControl_d() {
//        return serverd;
//    }
//
//    @Override
//    public boolean getControl_f() {
//        return serverf;
//    }
//
//
//    @Override
//    public String getsound() {
//        return tankinfo.soundname;
//    }
//
//    public float getsoundPitch(){
//        return abs(throttle/tankinfo.throttle_Max*tankinfo.soundpitch);
//    }
//
//    @Override
//    public void yourSoundIsremain(){
//        needStartSound = false;
//    }
//
//    public boolean riddenbyPlayer(){
//        return HMV_Proxy.getEntityPlayerInstance() == mc_Entity.riddenByEntity;
//    }
//
//    public void setinfo(Prefab_Vehicle_Base info) {
//        super.setinfo(info);
//        this.tankinfo = (Prefab_Vehicle_LandVehicle) info;
//    }
//}
