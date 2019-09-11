//package handmadevehicle.entity.parts.logics;
//
//import handmadevehicle.entity.prefab.Prefab_Vehicle_LandVehicle;
//import net.minecraft.entity.EntityLiving;
//import net.minecraft.util.MathHelper;
//import net.minecraft.util.MovingObjectPosition;
//import net.minecraft.util.Vec3;
//
//import javax.vecmath.Vector3d;
//
//import static handmadevehicle.HMVehicle.*;
//import static handmadevehicle.Utils.*;
//import static java.lang.Math.*;
//import static net.minecraft.util.DamageSource.inWall;
//
//public class VesselBaseLogicLogic extends TankBaseLogicLogic {
//    public double pitchmotion;
//    public VesselBaseLogicLogic(EntityLiving entityLiving, float yawspeed, float speed, String sound){
//        super(entityLiving,yawspeed,speed,sound);
//        this.mc_Entity = entityLiving;
//        tankinfo = new Prefab_Vehicle_LandVehicle();
//        tankinfo.yawspeed = yawspeed;
//        tankinfo.speed = speed;
//        tankinfo.soundname = sound;
//        tankinfo.throttle_speed = 0.045f;
//    }
//    public VesselBaseLogicLogic(EntityLiving entityLiving, float yawspeed, float speed, boolean spinturn, String sound){
//        this(entityLiving,yawspeed,speed,sound);
//        tankinfo.spinturn = spinturn;
//    }
//
//    public void followGround(){
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
//                bodyrotationPitch *= (ishittingWater() ? 0.9:0.999);
//                bodyrotationRoll *= (ishittingWater() ? 0.9:0.999);
//                if(mc_Entity.onGround || (tankinfo.canControlonWater && ishittingWater())) {
//                    double deltaPitch = ((float) toDegrees(sin(pitchVec.yCoord)) - bodyrotationPitch);
//                    boolean flag = deltaPitch>0;
//                    boolean flag2 = abs(deltaPitch)<0.01;
//                    pitchmotion += flag2 ?(deltaPitch):flag?0.06:-0.03;
//                    if(mc_Entity.motionX * mc_Entity.motionX + mc_Entity.motionZ * mc_Entity.motionZ > 0){
//                        pitchmotion -=sqrt(mc_Entity.motionX * mc_Entity.motionX + mc_Entity.motionZ * mc_Entity.motionZ)*0.75/(1 +abs( bodyrotationPitch));
//                    }
//                    pitchmotion -= mc_Entity.motionY*4 / (1 + abs(bodyrotationPitch));
//                    bodyrotationRoll += ((float) -toDegrees(sin(rollVec.yCoord)) - bodyrotationRoll) * 0.2;
//                }
//                bodyrotationPitch += pitchmotion;
////                pitchmotion *= 0.9999999;
//
////                if(tank.worldObj.isRemote){
////                    System.out.println(vec1);
////                    System.out.println(vec2);
////                    System.out.println(normal);
////                }
//            }
//        }
//    }
//    public void updateServer(){
//        super.updateServer();
//        if(mc_Entity.onGround || mc_Entity.isCollided) mc_Entity.attackEntityFrom(inWall,50);
//    }
//}
