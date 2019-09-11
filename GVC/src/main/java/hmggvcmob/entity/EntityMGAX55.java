package hmggvcmob.entity;

import cpw.mods.fml.client.FMLClientHandler;
import handmadeguns.HMGPacketHandler;
import handmadeguns.HandmadeGunsCore;
import handmadeguns.entity.I_SPdamageHandle;
import handmadeguns.entity.bullets.*;
import handmadeguns.network.PacketSpawnParticle;
import handmadeguns.client.render.HMGGunParts_Motion;
import handmadeguns.client.render.HMGGunParts_Motions;
import hmggvcmob.SlowPathFinder.WorldForPathfind;
import hmggvcmob.network.GVCMPacketHandler;
import hmggvcmob.network.GVCPacketMGControl;
import handmadevehicle.Utils;
import handmadevehicle.entity.parts.ModifiedBoundingBox;
import handmadevehicle.entity.parts.OBB;
import handmadevehicle.entity.parts.SeatInfo;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;

import javax.vecmath.AxisAngle4d;
import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;

import java.util.List;
import java.util.Random;

import static hmggvcmob.GVCMobPlus.METAL_GEAR;
import static handmadevehicle.HMVehicle.HMV_Proxy;
import static handmadevehicle.Utils.*;
import static java.lang.Math.*;
import static net.minecraft.util.MathHelper.wrapAngleTo180_float;

public class EntityMGAX55 extends Entity implements I_SPdamageHandle {
    public float health = 1000;
    public float armor = 30;
    public float maxhealth = 1000;
    public float bodyrotationYaw;
    public float bodyrotationRoll;
    public float headrotationYaw;
    public float RailGUNrotationYaw;
    public float headrotationPitch;
    public float RailGUNrotationPitch;
    public float headrotationRoll;
    public float prevbodyrotationYaw;
    public float prevbodyrotationPitch;
    public float prevbodyrotationRoll;
    private GearPathNavigate navigator;

    private boolean isinit = false;



    public float legStateFoward;
    public float legStateBack;
    public float legStateRight;
    public float legStateLeft;
    public float legStateTurnRight;
    public float legStateTurnLeft;
    public float legStatejumped;


    public boolean w,a,s,d,sp;


    public boolean legmodeForward;
    public boolean legmodeForwardstop;
    public boolean legmodeBack;
    public boolean legmodeBackstop;
    public boolean legmodeRight;
    public boolean legmodeRightstop;
    public boolean legmodeLeft;
    public boolean legmodeLeftstop;
    public boolean legmodeJump;
    public boolean legmodeJumpstop;
    public boolean legmodeturnLeft;
    public boolean legmodeturnLeftstop;
    public boolean legmodeturnRight;
    public boolean legmodeturnRightstop;
    public boolean legIdle;
    public boolean legSneak_CTRL;
    public boolean legSneak_State;
    public int legSneak_State_Progress;

    public int weaponMode;

    public static int railGunCharge = 400;
    public static int railGunCool = 50;
    public int railGunChargecnt;
    public int railGunCoolcnt;
    public int railGunMagazine;


    public static int normalGunCool = 2;
    public int normalGunCoolcnt;
    public int normalGunHeat;
    public static int normalGunHeat_Max = 500;


    public int rocketCool;
    public int rocketreloadCnt;
    public int rocketreload;
    public int rocketMagazine = 90;
    public Quat4d bodyRot = new Quat4d(0,0,0,1);



    public int soundtick = 0;
    public boolean trigger1 = false;
    public boolean trigger2 = false;
    public static boolean gunposinit = false;
    public static double[][] gunpos = new double[6][3];
    public Vector3d xVector = new Vector3d();
    public Vector3d yVector = new Vector3d();
    public Vector3d zVector = new Vector3d();
    public SeatInfo[] seatInfo = new SeatInfo[6];
    private HMGGunParts_Motions onforwardmotions = new HMGGunParts_Motions();;
    public Entity pilot;
    Entity TGT;
    Vec3 lockedBlockPos;
    WorldForPathfind worldForPathfind;

    int movingCnt;

    int weaponchangeCnt;

    int moveMode = 0;
    
    public ModifiedBoundingBox nboundingbox;
    public static final Vector3d unitX = new Vector3d(1,0,0);
    public static final Vector3d unitY = new Vector3d(0,1,0);
    public static final Vector3d unitZ = new Vector3d(0,0,1);
    public float pilotseatoffsety;
    @Override
    protected void entityInit() {
        dataWatcher.addObject(23,Float.valueOf(0));
        dataWatcher.addObject(3,Float.valueOf(0));
        dataWatcher.addObject(4,Float.valueOf(0));
        dataWatcher.addObject(5,Float.valueOf(0));
        dataWatcher.addObject(6,Float.valueOf(0));
        dataWatcher.addObject(7,Integer.valueOf(0));
        dataWatcher.addObject(8,Integer.valueOf(0));
        dataWatcher.addObject(9,Integer.valueOf(0));
        dataWatcher.addObject(10,Integer.valueOf(0));
    }
    public EntityMGAX55(World p_i1582_1_) {
        super(p_i1582_1_);
        setSize(10,10);
    
//        World.MAX_ENTITY_RADIUS = 32;
        nboundingbox = new ModifiedBoundingBox(-2.5,0,-2.5,2.5,10,2.5,
                                                                          0,5,0,5,10,5);
        nboundingbox.rot.set(this.bodyRot);
        nboundingbox.boxes = new OBB[7];
        initseat(nboundingbox);
        nboundingbox.calculateMax_And_Min();
        HMV_Proxy.replaceBoundingbox(this,nboundingbox);
        ((ModifiedBoundingBox)this.boundingBox).update(this.posX,this.posY,this.posZ);
        
        stepHeight = 5;

        this.navigator = new GearPathNavigate(this,worldObj);

        ignoreFrustumCheck = true;
        worldForPathfind = new WorldForPathfind(worldObj);
        if(!gunposinit) {
            gunpos[0][0] = -5.2570;//RailGun
//            gunpos[0][1] = 8.2;
            gunpos[0][1] = 6.0716;
            gunpos[0][2] = -4.2700;


            gunpos[1][0] = 1.5;//Rocket
            gunpos[1][1] = 8;
            gunpos[1][2] = -5;

            gunpos[2][0] = 1.5;
            gunpos[2][1] = 8;
            gunpos[2][2] = -6.4;

            gunpos[3][0] = -1.5;
            gunpos[3][1] = 8;
            gunpos[3][2] = -5;

            gunpos[4][0] = -1.5;
            gunpos[4][1] = 7.2;
            gunpos[4][2] = -6.4;


            gunpos[5][0] = 0;//Head Pos
            gunpos[5][1] = 4.55;
            gunpos[5][2] = 2.0;
            gunposinit =true;
        }
        AddMotionKeyonForward(
                0,0,0,0,0,0,0,
                8,0,0,0,0,0,0);
        AddMotionKeyonForward(
                8,0,0,0,0,0,0,
                12,0,1.8f,0,0,0,0);
        AddMotionKeyonForward(
                12,0,1.8f,0,0,0,0,
                20,0,0,0,0,0,0);
        AddMotionKeyonForward(
                20,0,0,0,0,0,0,
                24,0,1.8f,0,0,0,0);

        AddMotionKeyonForward(
                24,0,1.8f,0,0,0,0,
                28,0,0,0,0,0,0);
        AddMotionKeyonForward(
                28,0,0,0,0,0,0,
                36,0,1.8f,0,0,0,0);
        AddMotionKeyonForward(
                36,0,1.8f,0,0,0,0,
                40,0,0,0,0,0,0);
        AddMotionKeyonForward(
                40,0,0,0,0,0,0,
                48,0,1.8f,0,0,0,0);
    }
    public void AddMotionKeyonForward(int   startflame,
                                      float startcenterX,
                                      float startcenterY,
                                      float startcenterZ,
                                      float startrotationX,
                                      float startrotationY,
                                      float startrotationZ,
                                      int   endflame,
                                      float endcenterX,
                                      float endcenterY,
                                      float endcenterZ,
                                      float endrotationX,
                                      float endrotationY,
                                      float endrotationZ){
        HMGGunParts_Motion motion = new HMGGunParts_Motion();
        motion.startflame =         startflame;
        motion.startposX =          startcenterX;
        motion.startposY =          startcenterY;
        motion.startposZ =          startcenterZ;
        motion.startrotationX =     startrotationX;
        motion.startrotationY =     startrotationY;
        motion.startrotationZ =     startrotationZ;
        motion.endflame =         endflame;
        motion.endposX =          endcenterX;
        motion.endposY =          endcenterY;
        motion.endposZ =          endcenterZ;
        motion.endrotationX =     endrotationX;
        motion.endrotationY =     endrotationY;
        motion.endrotationZ =     endrotationZ;
        motion.setup();
        onforwardmotions.addmotion(motion);
    }
    public void initseat(ModifiedBoundingBox motherBox){
        motherBox.boxes[0] = new OBB(new Vector3d(0,7.325,-2.65),new Vector3d(3,3,3));
        motherBox.boxes[1] = new OBB(new Vector3d(-1.23,1.5,0),new Vector3d(3,6,6));
        motherBox.boxes[2] = new OBB(new Vector3d(1.23,1.5,0),new Vector3d(3,6,6));
        motherBox.boxes[3] = new OBB(new Vector3d(0,6.5,0),new Vector3d(2.5,1.5,4));
        motherBox.boxes[4] = new OBB(new Vector3d(0, 8.2, 4.6),new Vector3d(3,3,3));
        motherBox.boxes[5] = new OBB(new Vector3d(-5.5 ,10.2, 3.8),new Vector3d(1.5,1.5,1.5));
        motherBox.boxes[6] = new OBB(new Vector3d(5.5 ,10.2, 3.8),new Vector3d(1.5,1.5,4));
//        for (int i = 0; i< childEntities.length; i++) {
//            SeatInfo[i] = new SeatInfo();
//            if(!worldObj.isRemote) {
//                childEntities[i] = new EntityChild(worldObj,1,1,true);
//                childEntities[i].setLocationAndAngles(this.posX,this.posY,this.posZ,0,0);
//                childEntities[i].master = this;
//                childEntities[i].idinmasterEntityt = i;
//                childEntities[i].rideable = i == 0;
//                worldObj.spawnEntityInWorld(childEntities[i]);
//            }
//            switch (i){
//                case 0:
//                    SeatInfo[i].pos[0] = 0;
//                    SeatInfo[i].pos[1] = 7.35;
//                    SeatInfo[i].pos[2] = 2.65;
//                    if(childEntities[i] != null){
//                        childEntities[i].setsize(3,3);
//                        childEntities[i].rideable = true;
//                    }
//                    break;
//                case 1:
//                    SeatInfo[i].pos[0] = -1.23;
//                    SeatInfo[i].pos[1] = 0;
//                    SeatInfo[i].pos[2] = 0;
//                    if(childEntities[i] != null) childEntities[i].setsize(3,6);
//                    break;
//                case 2:
//                    SeatInfo[i].pos[0] = 1.23;
//                    SeatInfo[i].pos[1] = 0;
//                    SeatInfo[i].pos[2] = 0;
//                    if(childEntities[i] != null) childEntities[i].setsize(3,6);
//                    break;
//                case 3:
//                    SeatInfo[i].pos[0] = 0;
//                    SeatInfo[i].pos[1] = 6.5;
//                    SeatInfo[i].pos[2] = 0;
//                    if(childEntities[i] != null) childEntities[i].setsize(5,3);
//                    break;
//                case 4:
//                    SeatInfo[i].pos[0] = 0;
//                    SeatInfo[i].pos[1] = 8.2;
//                    SeatInfo[i].pos[2] = -4.6;
//                    if(childEntities[i] != null) childEntities[i].setsize(5,3);
//                    break;
//                case 5:
//                    SeatInfo[i].pos[0] = 5.5;
//                    SeatInfo[i].pos[1] = 10.2;
//                    SeatInfo[i].pos[2] = -3.8;
//                    if(childEntities[i] != null) childEntities[i].setsize(3,3);
//                    break;
//            }
//        }
    }
    
    
    public boolean attackEntityFrom_with_Info(MovingObjectPosition movingObjectPosition, DamageSource source, float level){
        float temparomor = armor;
        
        return this.attackEntityFrom_exceptArmor(source,level);
    }
    
    public boolean attackEntityFrom(DamageSource source, float par2) {
        if(source.getDamageType().equals(DamageSource.fall.damageType) ||
                   source.getDamageType().equals(DamageSource.outOfWorld.damageType) ||
                   source.getDamageType().equals(DamageSource.inWall.damageType))return attackEntityFrom_exceptArmor(source, par2);
        par2 -= armor;
        if(par2 < 0)par2 = 0;
        if (this.riddenByEntity == source.getEntity()) {
            return false;
        } else if (this == source.getEntity()) {
            return false;
        } else if(this instanceof ImultiRidable && ((ImultiRidable)this).isRidingEntity(source.getEntity())) {
            return false;
        }else {
            return super.attackEntityFrom(source, par2);
        }
    }
    public boolean attackEntityFrom_exceptArmor (DamageSource source, float par2){
        return super.attackEntityFrom(source, par2);
    }
    public void onUpdate() {
        nboundingbox.update(this.posX,this.posY,this.posZ);
        bodyRot = new Quat4d(0,0,0,1);
        Vector3d axisy = transformVecByQuat(new Vector3d(unitY), bodyRot);
        AxisAngle4d axisyangled = new AxisAngle4d(axisy, toRadians(bodyrotationYaw)/2);
        bodyRot = quatRotateAxis(bodyRot,axisyangled);
        nboundingbox.rot = bodyRot;
        xVector.set(-1, 0, 0);
        yVector.set(0, 1, 0);
        zVector.set(0, 0, 1);
        Utils.RotateVectorAroundY(xVector, -bodyrotationYaw);
        Utils.RotateVectorAroundY(zVector, -bodyrotationYaw);
        if (worldObj.isRemote) {
            prevbodyrotationYaw = bodyrotationYaw;
            prevbodyrotationRoll = bodyrotationRoll;
            prevbodyrotationYaw = wrapAngleTo180_float(prevbodyrotationYaw);
            bodyrotationYaw = wrapAngleTo180_float(bodyrotationYaw);
            if (this.health <= this.maxhealth / 2) {
                if (this.health <= this.maxhealth / 4) {
                    this.worldObj.spawnParticle("smoke", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
                    this.worldObj.spawnParticle("smoke", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
                }
            }
            if (pilot == FMLClientHandler.instance().getClientPlayerEntity()) {
                control_Pilot();
            } else if (pilot != null) {
                updateLeg();
            }
            bodyrotationYaw = this.dataWatcher.getWatchableObjectFloat(23);
            headrotationYaw = this.dataWatcher.getWatchableObjectFloat(3);
            headrotationPitch = this.dataWatcher.getWatchableObjectFloat(4);
            RailGUNrotationYaw = this.dataWatcher.getWatchableObjectFloat(5);
            RailGUNrotationPitch = this.dataWatcher.getWatchableObjectFloat(6);
            switch (weaponMode) {
                case 0:
                    if (this.dataWatcher.getWatchableObjectInt(9) == 0) {
                        railGunChargecnt = this.dataWatcher.getWatchableObjectInt(7);
                        railGunCoolcnt = 0;
                    } else {
                        railGunCoolcnt = this.dataWatcher.getWatchableObjectInt(7);
                        railGunChargecnt = 0;
                    }
                    railGunMagazine = this.dataWatcher.getWatchableObjectInt(8);
                    break;
                case 1:
                    rocketMagazine = this.dataWatcher.getWatchableObjectInt(8);
                    break;
                case 2:
                    normalGunHeat = this.dataWatcher.getWatchableObjectInt(7);
                    break;
            }
            health = this.dataWatcher.getWatchableObjectInt(10);
        } else {
            if (pilot instanceof EntityPlayer) {
                ((EntityPlayer) pilot).addStat(METAL_GEAR, 1);
            }
            if (legSneak_CTRL || legSneak_State) {
                pilotseatoffsety = -2.6f;
                legmodeForward = false;
                legmodeForwardstop = false;
                legmodeBack = false;
                legmodeBackstop = false;
                legmodeRight = false;
                legmodeRightstop = false;
                legmodeLeft = false;
                legmodeLeftstop = false;
                legmodeJump = false;
                legmodeJumpstop = false;
                legmodeturnLeft = false;
                legmodeturnLeftstop = false;
                legmodeturnRight = false;
                legmodeturnRightstop = false;
                legStateFoward = 0;
                legStateBack = 0;
                legStateRight = 0;
                legStateLeft = 0;
                legStateTurnRight = 0;
                legStateTurnLeft = 0;
            } else {
                pilotseatoffsety = 0;
            }
            if (pilot instanceof EntityLivingBase) {
                if (pilot instanceof EntityLiving) {
                    pilot.setLocationAndAngles(this.posX, this.posY + 10, this.posZ, ((EntityLiving) pilot).rotationYawHead, pilot.rotationPitch);
    
                    if (((EntityLiving) pilot).getAttackTarget() != null)
                        ((EntityLiving) pilot).getLookHelper().setLookPositionWithEntity(((EntityLiving) pilot).getAttackTarget(), 360, 360);
                    ((EntityLiving) pilot).getLookHelper().onUpdateLook();
                }
                float Angulardifference;
                if (!legSneak_CTRL && !legSneak_State) {
                    bodyrotationYaw = wrapAngleTo180_float(bodyrotationYaw);
                    ((EntityLivingBase) pilot).rotationYawHead = wrapAngleTo180_float(((EntityLivingBase) pilot).rotationYawHead);
                    Angulardifference = wrapAngleTo180_float(bodyrotationYaw - ((EntityLivingBase) pilot).rotationYawHead);
                    if (Angulardifference > 3) {
                        bodyrotationYaw -= 3;
                    } else if (Angulardifference < -3) {
                        bodyrotationYaw += 3;
                    } else {
                        bodyrotationYaw = ((EntityLivingBase) pilot).rotationYawHead;
                    }
                }
    
                float temp;
                this.headrotationYaw = wrapAngleTo180_float(this.headrotationYaw);
                ((EntityLivingBase) pilot).rotationYawHead = wrapAngleTo180_float(((EntityLivingBase) pilot).rotationYawHead);
    
                temp = wrapAngleTo180_float(this.headrotationYaw + bodyrotationYaw);
                Angulardifference = wrapAngleTo180_float(temp - ((EntityLivingBase) pilot).rotationYawHead);
                if (Angulardifference > 6) {
                    temp -= 6;
                } else if (Angulardifference < -6) {
                    temp += 6;
                } else {
                    temp = ((EntityLivingBase) pilot).rotationYawHead;
                }
    
                this.headrotationYaw = temp - bodyrotationYaw;
                this.headrotationYaw = wrapAngleTo180_float(this.headrotationYaw);
    
    
                if (headrotationYaw > 30) {
                    headrotationYaw = 30;
                } else if (headrotationYaw < -30) {
                    headrotationYaw = -30;
                }
    
                this.headrotationPitch = wrapAngleTo180_float(this.headrotationPitch);
                ((EntityLivingBase) pilot).rotationPitch = wrapAngleTo180_float(((EntityLivingBase) pilot).rotationPitch);
                Angulardifference = wrapAngleTo180_float(this.headrotationPitch - ((EntityLivingBase) pilot).rotationPitch);
                if (Angulardifference > 6) {
                    this.headrotationPitch -= 6;
                } else if (Angulardifference < -6) {
                    this.headrotationPitch += 6;
                } else {
                    this.headrotationPitch = ((EntityLivingBase) pilot).rotationPitch;
                }
                if (headrotationPitch > 70) {
                    headrotationPitch = 70;
                } else if (headrotationPitch < -70) {
                    headrotationPitch = -70;
                }
                if (weaponMode == 0) {
    
                    if (pilot instanceof EntityLiving) {
                        Entity target = ((EntityLiving) pilot).getAttackTarget();
                        if (target != null) {
                            pilot.setLocationAndAngles(this.posX + xVector.x * (gunpos[0][0]) + yVector.x * (gunpos[0][1]) + zVector.x * gunpos[0][2]
                                    , this.posY + xVector.y * (gunpos[0][0]) + yVector.y * (gunpos[0][1]) + 4.12 + pilotseatoffsety + zVector.y * gunpos[0][2]
                                    , this.posZ + xVector.z * (gunpos[0][0]) + yVector.z * (gunpos[0][1]) + zVector.z * gunpos[0][2], ((EntityLiving) pilot).rotationYawHead, pilot.rotationPitch);
                            if (((EntityLiving) pilot).getAttackTarget() != null)
                                ((EntityLiving) pilot).getLookHelper().setLookPositionWithEntity(((EntityLiving) pilot).getAttackTarget(), 360, 360);
                            ((EntityLiving) pilot).getLookHelper().onUpdateLook();
                        }
                    }
    
                    temp = wrapAngleTo180_float(this.RailGUNrotationYaw + bodyrotationYaw);
                    Angulardifference = wrapAngleTo180_float(temp - ((EntityLivingBase) pilot).rotationYawHead);
                    if (Angulardifference > 2) {
                        temp -= 2;
                    } else if (Angulardifference < -2) {
                        temp += 2;
                    } else {
                        temp = ((EntityLivingBase) pilot).rotationYawHead;
                    }
    
                    this.RailGUNrotationYaw = temp - bodyrotationYaw;
                    this.RailGUNrotationYaw = wrapAngleTo180_float(this.RailGUNrotationYaw);
                    if (RailGUNrotationYaw > 10) {
                        RailGUNrotationYaw = 10;
                    } else if (RailGUNrotationYaw < -10) {
                        RailGUNrotationYaw = -10;
                    }
    
    
                    this.RailGUNrotationPitch = wrapAngleTo180_float(this.RailGUNrotationPitch);
                    ((EntityLivingBase) pilot).rotationPitch = wrapAngleTo180_float(((EntityLivingBase) pilot).rotationPitch);
    
    
                    Angulardifference = wrapAngleTo180_float(this.RailGUNrotationPitch - ((EntityLivingBase) pilot).rotationPitch);
                    if (Angulardifference > 2) {
                        this.RailGUNrotationPitch -= 2;
                    } else if (Angulardifference < -2) {
                        this.RailGUNrotationPitch += 2;
                    } else {
                        this.RailGUNrotationPitch = ((EntityLivingBase) pilot).rotationPitch;
                    }
                    if (RailGUNrotationPitch > 20) {
                        RailGUNrotationPitch = 20;
                    } else if (RailGUNrotationPitch < -20) {
                        RailGUNrotationPitch = -20;
                    }
                }
            }
            if (pilot != null && pilot instanceof EntityLivingBase && !(pilot instanceof EntityPlayer)) {
                control_ByMob();
            }
            this.rotationYaw = bodyrotationYaw;
            this.dataWatcher.updateObject(23, bodyrotationYaw);
            this.dataWatcher.updateObject(3, headrotationYaw);
            this.dataWatcher.updateObject(4, headrotationPitch);
            this.dataWatcher.updateObject(5, RailGUNrotationYaw);
            this.dataWatcher.updateObject(6, RailGUNrotationPitch);
            switch (weaponMode) {
                case 0:
                    if (railGunChargecnt > 0)
                        this.dataWatcher.updateObject(7, railGunChargecnt);
                    else
                        this.dataWatcher.updateObject(7, railGunCoolcnt);
                    this.dataWatcher.updateObject(9, railGunChargecnt > 0 ? 0 : 1);
                    this.dataWatcher.updateObject(8, railGunMagazine);
                    break;
                case 1:
                    this.dataWatcher.updateObject(8, rocketMagazine);
                    break;
                case 2:
                    this.dataWatcher.updateObject(7, normalGunHeat);
                    break;
            }
            this.dataWatcher.updateObject(10, (int) health);
        }
        FCS(xVector, zVector);
        motionUpdate(xVector, zVector);
    
        if (health < 0) setDead();
        pilot = riddenByEntity;
    
        double movespeed = sqrt(motionX * motionX + motionY * motionY + motionZ * motionZ);
    
        if (!worldObj.isRemote) {
//            destroyNearBlocks(this.boundingBox,movespeed);
        }


//        for(int i = 0; i< childEntities.length; i++) {
//            EntityChild achild = childEntities[i];
//            if(achild != null && !achild.isDead) {
//                List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(achild, achild.boundingBox.expand(0.20000000298023224D, 0.0D, 0.20000000298023224D));
//
//                try {
//                    if (list != null && !list.isEmpty()) {
//                        for (int i1 = 0; i1 < list.size(); ++i1) {
//                            Entity colliedentity = (Entity) list.get(i1);
//
//                            if (!(pilot instanceof EntityPlayer) && colliedentity != null && colliedentity != this && colliedentity != pilot && colliedentity.canBePushed() && !isChild(colliedentity)) {
//                                colliedentity.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase) pilot), (float) (64 * movespeed));
//                                ;
//                            }
//                        }
//                    }
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//
//                if(!worldObj.isRemote) {
//
////                    destroyNearBlocks(achild.boundingBox,movespeed);
//                }
//
//                if(i == 0){
//                    achild.rideable = true;
//                    achild.thirddist = 25;
//                }
//                achild.setLocationAndAngles(
//                          this.posX + xVector.x * SeatInfo[i].pos[0] + yVector.x * (SeatInfo[i].pos[1]) + zVector.x * SeatInfo[i].pos[2]
//                        , this.posY + xVector.y * SeatInfo[i].pos[0] + yVector.y * (SeatInfo[i].pos[1]) + pilotseatoffsety + zVector.y * SeatInfo[i].pos[2]
//                        , this.posZ + xVector.z * SeatInfo[i].pos[0] + yVector.z * (SeatInfo[i].pos[1]) + zVector.z * SeatInfo[i].pos[2]
//                        , bodyrotationYaw, 0);
//                achild.master = this;
//            }else {
//                if(!worldObj.isRemote) {
//                    childEntities[i] = new EntityChild(worldObj,1,1,true);
//                    childEntities[i].setLocationAndAngles(this.posX,this.posY,this.posZ,0,0);
//                    childEntities[i].master = this;
//                    childEntities[i].idinmasterEntityt = i;
//                    worldObj.spawnEntityInWorld(childEntities[i]);
//                }
//            }
//            switch (i){
//                case 0:
//                    if(childEntities[i] != null){
//                        childEntities[i].setsize(3,3);
//                        childEntities[i].rideable = true;
//                    }
//                    break;
//                case 1:
//                    if(childEntities[i] != null) childEntities[i].setsize(3,5);
//                    break;
//                case 2:
//                    if(childEntities[i] != null) childEntities[i].setsize(3,5);
//                    break;
//                case 3:
//                    if(childEntities[i] != null) childEntities[i].setsize(5,3);
//                    break;
//                case 4:
//                    if(childEntities[i] != null) childEntities[i].setsize(5,3);
//                    break;
//                case 5:
//                    if(childEntities[i] != null) childEntities[i].setsize(3,3);
//                    break;
//            }
//        }
//    }
    }

    void control_Pilot(){
        GVCPacketMGControl packetMGControl = new GVCPacketMGControl();
        packetMGControl.w = w =  HMV_Proxy.throttle_up_click();
        packetMGControl.a = a =  HMV_Proxy.yaw_Left_click();
        packetMGControl.s = s =  HMV_Proxy.throttle_down_click();
        packetMGControl.d = d =  HMV_Proxy.yaw_Right_click();
        if(HMV_Proxy.throttle_up_click()){
            legmodeForward = true;
            legmodeBack = false;
            legStateBack = 0;
            legmodeForwardstop = false;
        }else {
            legmodeForwardstop = true;
        }
        if(legStateFoward>=48){
            legStateFoward = legmodeForwardstop ? 0:25;
        }else if(legmodeForwardstop){
            //20 32 44
            if(legStateFoward == 20 || legStateFoward == 30 || legStateFoward == 42){
                legStateFoward = 0;
                legmodeForward = false;
            }
        }


        if(HMV_Proxy.throttle_down_click()){
            legmodeBack = true;
            legmodeForward = false;
            legStateFoward = 0;
            legmodeBackstop = false;
        }else {
            legmodeBackstop = true;
        }
        if(legStateBack>=48){
            legStateBack = legmodeBackstop ? 0:25;
        }else if(legmodeBackstop){
            //20 32 44
            if(legStateBack == 20 || legStateBack == 32 || legStateBack == 44){
                legStateBack = 0;
                legmodeBack = false;
            }
        }



        if(HMV_Proxy.yaw_Right_click()){
            legmodeRight = true;
            legmodeLeft = false;
            legmodeRightstop = false;
        }else {
            legmodeRightstop = true;
        }
        if(legStateRight>48){
            legStateRight = 0;
            legmodeRight = false;
        }
        if(HMV_Proxy.yaw_Left_click()){
            legmodeLeft = true;
            legmodeRight = false;
            legmodeLeftstop = false;
        }else {
            legmodeLeftstop = true;
        }
        if(legStateLeft>48){
            legStateLeft = 0;
            legmodeLeft = false;
        }
        if(HMV_Proxy.zoomclick()){
            legSneak_CTRL = !legSneak_CTRL;
        }
        if(!onGround){
            legSneak_CTRL = false;
        }
        if(legSneak_CTRL || legSneak_State || legSneak_State_Progress != 0){
            w = false;
            a = false;
            s = false;
            d = false;
            pilotseatoffsety = -2;
            legmodeForward = false;
            legmodeForwardstop = false;
            legmodeBack = false;
            legmodeBackstop = false;
            legmodeRight = false;
            legmodeRightstop = false;
            legmodeLeft = false;
            legmodeLeftstop = false;
            legmodeJump = false;
            legmodeJumpstop = false;
            legmodeturnLeft = false;
            legmodeturnLeftstop = false;
            legmodeturnRight = false;
            legmodeturnRightstop = false;
            legStateFoward = 0;
            legStateBack = 0;
            legStateRight = 0;
            legStateLeft = 0;
            legStateTurnRight = 0;
            legStateTurnLeft = 0;
        }else {
            pilotseatoffsety = 0;
            bodyrotationYaw = wrapAngleTo180_float(bodyrotationYaw);
            ((EntityLivingBase) pilot).rotationYawHead = wrapAngleTo180_float(((EntityLivingBase) pilot).rotationYawHead);
            float Angulardifference = wrapAngleTo180_float(bodyrotationYaw - ((EntityLivingBase) pilot).rotationYawHead);
            if(Angulardifference > 3){
                legmodeturnRight = true;
                legmodeturnLeft = false;
                legStateTurnLeft = 0;
                bodyrotationYaw -=3;
            }else if(Angulardifference < -3){
                legmodeturnLeft = true;
                legmodeturnRight = false;
                legStateTurnRight = 0;
                bodyrotationYaw +=3;
            }else{
                bodyrotationYaw = ((EntityLivingBase) pilot).rotationYawHead;
            }
        }

        if(legStateTurnRight>48){
            legStateTurnRight = 0;
            legmodeturnRight = false;
        }
        if(legStateTurnLeft>48){
            legStateTurnLeft = 0;
            legmodeturnLeft = false;
        }

        packetMGControl.targetID = this.getEntityId();
        if (HMV_Proxy.leftclick()) {
            packetMGControl.trig1 = true;
        }
        if (HMV_Proxy.rightclick()) {
            packetMGControl.trig2 = true;
        }
        packetMGControl.sp = sp = HMV_Proxy.throttle_BrakeKeyDown();
        packetMGControl.hold = legSneak_CTRL;
        if(legmodeForward){
            if(legStateFoward == 25||legStateFoward == 37) HandmadeGunsCore.HMG_proxy.playsoundat("gvcmob:gvcmob.Gear_Moving",5,1,1,(float)this.posX,(float)this.posY,(float)this.posZ);
            legStateFoward++;
        }
        if(legmodeBack){
            if(legStateBack == 25||legStateBack == 37) HandmadeGunsCore.HMG_proxy.playsoundat("gvcmob:gvcmob.Gear_Moving",5,1,1,(float)this.posX,(float)this.posY,(float)this.posZ);
            legStateBack++;
        }
        if(legmodeRight){
            if(legStateRight == 0) HandmadeGunsCore.HMG_proxy.playsoundat("gvcmob:gvcmob.Gear_Moving",5,1,1,(float)this.posX,(float)this.posY,(float)this.posZ);
            legStateRight+=2;
        }
        if(legmodeLeft){
            if(legStateLeft == 0) HandmadeGunsCore.HMG_proxy.playsoundat("gvcmob:gvcmob.Gear_Moving",5,1,1,(float)this.posX,(float)this.posY,(float)this.posZ);
            legStateLeft+=2;
        }
        if(legmodeturnRight){
            if(legStateTurnRight == 0) HandmadeGunsCore.HMG_proxy.playsoundat("gvcmob:gvcmob.Gear_Moving",5,1,1,(float)this.posX,(float)this.posY,(float)this.posZ);
            legStateTurnRight+=2;
        }
        if(legmodeturnLeft){
            if(legStateTurnLeft == 0) HandmadeGunsCore.HMG_proxy.playsoundat("gvcmob:gvcmob.Gear_Moving",5,1,1,(float)this.posX,(float)this.posY,(float)this.posZ);
            legStateTurnLeft+=2;
        }

        if(HMV_Proxy.reload_Semi())weaponMode++;
        if(weaponMode > 2)weaponMode = 0;
        packetMGControl.weaponmode = weaponMode;
        GVCMPacketHandler.INSTANCE.sendToServer(packetMGControl);
    }
    void updateLeg(){
        if(w){
            legmodeForward = true;
            legmodeBack = false;
            legStateBack = 0;
            legmodeForwardstop = false;
        }else {
            legmodeForwardstop = true;
        }
        if(legStateFoward>=48){
            legStateFoward = legmodeForwardstop ? 0:25;
        }else if(legmodeForwardstop){
            //20 32 44
            if(legStateFoward == 20 || legStateFoward == 30 || legStateFoward == 42){
                legStateFoward = 0;
                legmodeForward = false;
            }
        }


        if(s){
            legmodeBack = true;
            legmodeForward = false;
            legStateFoward = 0;
            legmodeBackstop = false;
        }else {
            legmodeBackstop = true;
        }
        if(legStateBack>=48){
            legStateBack = legmodeBackstop ? 0:25;
        }else if(legmodeBackstop){
            //20 32 44
            if(legStateBack == 20 || legStateBack == 32 || legStateBack == 44){
                legStateBack = 0;
                legmodeBack = false;
            }
        }



        if(d){
            legmodeRight = true;
            legmodeLeft = false;
            legmodeRightstop = false;
        }else {
            legmodeRightstop = true;
        }
        if(legStateRight>48){
            legStateRight = 0;
            legmodeRight = false;
        }
        if(a){
            legmodeLeft = true;
            legmodeRight = false;
            legmodeLeftstop = false;
        }else {
            legmodeLeftstop = true;
        }
        if(legStateLeft>48){
            legStateLeft = 0;
            legmodeLeft = false;
        }
        if(!onGround){
            legSneak_CTRL = legSneak_State = false;
        }
        if(legSneak_CTRL || legSneak_State || legSneak_State_Progress != 0){
            w = false;
            a = false;
            s = false;
            d = false;
            pilotseatoffsety = -2;
            legmodeForward = false;
            legmodeForwardstop = false;
            legmodeBack = false;
            legmodeBackstop = false;
            legmodeRight = false;
            legmodeRightstop = false;
            legmodeLeft = false;
            legmodeLeftstop = false;
            legmodeJump = false;
            legmodeJumpstop = false;
            legmodeturnLeft = false;
            legmodeturnLeftstop = false;
            legmodeturnRight = false;
            legmodeturnRightstop = false;
            legStateFoward = 0;
            legStateBack = 0;
            legStateRight = 0;
            legStateLeft = 0;
            legStateTurnRight = 0;
            legStateTurnLeft = 0;
        }else {
            pilotseatoffsety = 0;
            bodyrotationYaw = wrapAngleTo180_float(bodyrotationYaw);

            ((EntityLivingBase) pilot).rotationYawHead = wrapAngleTo180_float(((EntityLivingBase) pilot).rotationYawHead);
            float Angulardifference = wrapAngleTo180_float(bodyrotationYaw - ((EntityLivingBase) pilot).rotationYawHead);
            if(Angulardifference > 12){
                legmodeturnRight = true;
                legmodeturnLeft = false;
                legStateTurnLeft = 0;
                bodyrotationYaw -=3;
            }else if(Angulardifference < -12){
                legmodeturnLeft = true;
                legmodeturnRight = false;
                legStateTurnRight = 0;
                bodyrotationYaw +=3;
            }else{
                bodyrotationYaw = ((EntityLivingBase) pilot).rotationYawHead;
            }
        }

        if(legStateTurnRight>48){
            legStateTurnRight = 0;
            legmodeturnRight = false;
        }
        if(legStateTurnLeft>48){
            legStateTurnLeft = 0;
            legmodeturnLeft = false;
        }

        if(legmodeForward){
            if(legStateFoward == 25||legStateFoward == 37) HandmadeGunsCore.HMG_proxy.playsoundat("gvcmob:gvcmob.Gear_Moving",5,1,1,(float)this.posX,(float)this.posY,(float)this.posZ);
            legStateFoward++;
        }
        if(legmodeBack){
            if(legStateBack == 25||legStateBack == 37) HandmadeGunsCore.HMG_proxy.playsoundat("gvcmob:gvcmob.Gear_Moving",5,1,1,(float)this.posX,(float)this.posY,(float)this.posZ);
            legStateBack++;
        }
        if(legmodeRight){
            if(legStateRight == 0) HandmadeGunsCore.HMG_proxy.playsoundat("gvcmob:gvcmob.Gear_Moving",5,1,1,(float)this.posX,(float)this.posY,(float)this.posZ);
            legStateRight+=2;
        }
        if(legmodeLeft){
            if(legStateLeft == 0) HandmadeGunsCore.HMG_proxy.playsoundat("gvcmob:gvcmob.Gear_Moving",5,1,1,(float)this.posX,(float)this.posY,(float)this.posZ);
            legStateLeft+=2;
        }
        if(legmodeturnRight){
            if(legStateTurnRight == 0) HandmadeGunsCore.HMG_proxy.playsoundat("gvcmob:gvcmob.Gear_Moving",5,1,1,(float)this.posX,(float)this.posY,(float)this.posZ);
            legStateTurnRight+=2;
        }
        if(legmodeturnLeft){
            if(legStateTurnLeft == 0) HandmadeGunsCore.HMG_proxy.playsoundat("gvcmob:gvcmob.Gear_Moving",5,1,1,(float)this.posX,(float)this.posY,(float)this.posZ);
            legStateTurnLeft+=2;
        }
    }

    void control_ByMob(){
        w = a = s = d = sp = false;
        EntityLivingBase target = ((EntityLiving)pilot).getAttackTarget();
        GVCPacketMGControl packetMGControl = new GVCPacketMGControl();
        trigger1 = false;
        if(target != null) {

            movingCnt++;
            if(movingCnt >= 100){
                moveMode = rand.nextInt(4);
                movingCnt = 0;
            }
            if(moveMode == 0) {
                navigator.setPath(worldForPathfind.getEntityPathToXYZ(this, (int) target.posX, (int) target.posY, (int) target.posZ, 60, true, false, false, true),1);
            }else if(moveMode == 1){
                navigator.setPath(worldForPathfind.getEntityPathToXYZ(this, (int) (this.posX - (target.posX - this.posX)), (int) target.posY, (int) (this.posZ - (target.posZ - this.posZ)), 60, true, false, false, true),1);
            }else if(moveMode == 2){
                navigator.setPath(worldForPathfind.getEntityPathToXYZ(this, (int) (this.posX - (target.posX - this.posX)), (int) target.posY, (int) (this.posZ + (target.posZ - this.posZ)), 60, true, false, false, true),1);
            }else if(moveMode == 3){
                navigator.setPath(worldForPathfind.getEntityPathToXYZ(this, (int) (this.posX + (target.posX - this.posX)), (int) target.posY, (int) (this.posZ - (target.posZ - this.posZ)), 60, true, false, false, true),1);
            }
            navigator.onUpdateNavigation();
            if (!navigator.noPath()) {
                Vec3 vec3 = navigator.getPath().getPosition(this);

                if (vec3 != null) {
                    //�ڕW�n�_�Ɉړ�������
                    this.bodyrotationYaw = wrapAngleTo180_float(this.bodyrotationYaw);
                    float targetrote = wrapAngleTo180_float((float) -toDegrees(atan2(vec3.xCoord - this.posX, vec3.zCoord - this.posZ)));
//					System.out.println(" " + vec3.xCoord + " , " +  vec3.zCoord);
                    float Angulardifference = wrapAngleTo180_float(bodyrotationYaw - targetrote);
                    if (135 > Angulardifference && Angulardifference > 45) {
                        a = true;
                    } else if (-135 < Angulardifference && Angulardifference < -45) {
                        d = true;
                    } else if (Angulardifference > -45 && Angulardifference < 45){
                        w = true;
                    } else {
                        s = true;
                    }
                }
            }

            if (rand.nextInt(100) == 0) {
                legSneak_CTRL = !legSneak_CTRL;
            }
            if (!onGround) {
                legSneak_CTRL = legSneak_State = false;
            }

            legSneak_CTRL = false;
            packetMGControl.targetID = this.getEntityId();
            if(((EntityLiving) pilot).canEntityBeSeen(target)) {
                if (weaponMode == 2) {
                    float anglDify = wrapAngleTo180_float(headrotationYaw + this.bodyrotationYaw - ((EntityLiving) pilot).rotationYawHead);
                    float anglDifp = wrapAngleTo180_float(headrotationPitch - pilot.rotationPitch);
                    if(anglDify<10 && anglDifp>-10 && anglDifp<10 && anglDifp>-10)
                    if(weaponchangeCnt>100){
                        weaponMode = rand.nextInt(3);
                        weaponchangeCnt = 0;
                    }
                    trigger1 = true;
                }
                if (weaponMode == 0) {
                    if(RailGUNrotationYaw + this.bodyrotationYaw == ((EntityLiving) pilot).rotationYawHead && RailGUNrotationPitch == pilot.rotationPitch)trigger1 = true;
                    if(weaponchangeCnt>250){
                        weaponMode = rand.nextInt(3);
                        weaponchangeCnt = 0;
                    }
                    if(RailGUNrotationYaw<10 && RailGUNrotationYaw>-10)
                        legSneak_CTRL = true;
                }
                if (weaponMode == 1) {

                    if(weaponchangeCnt>100){
                        weaponMode = rand.nextInt(3);
                        weaponchangeCnt = 0;
                    }
                    rocketMagazine = 10;
                    trigger1 = true;
                }
            }
            weaponchangeCnt ++;
            packetMGControl.trig1 = trigger1;
//            if (proxy.rightclick()) {
//                packetMGControl.trig2 = true;
//            }
            packetMGControl.sp = sp = rand.nextInt(100) == 0;
            packetMGControl.hold = legSneak_CTRL;

            packetMGControl.weaponmode = weaponMode;
        }else {
        }
        packetMGControl.w = w;
        packetMGControl.a = a;
        packetMGControl.s = s;
        packetMGControl.d = d;
        GVCMPacketHandler.INSTANCE.sendToServer(packetMGControl);
    }


    void motionUpdate(Vector3d xVector,Vector3d zVector){
        double tempx = motionX;
        double tempy = motionY;
        double tempz = motionZ;
        if(!legSneak_CTRL && !legSneak_State && !legmodeJumpstop) {
            if(onGround) {
                if (a) {
                    motionX -= xVector.x * 0.12;
                    motionZ -= xVector.z * 0.12;
                    a = false;
                }
                if (d) {
                    motionX += xVector.x * 0.12;
                    motionZ += xVector.z * 0.12;
                    d = false;
                }
                if (w) {
                    motionX += zVector.x * 0.12;
                    motionZ += zVector.z * 0.12;
                    w = false;
                }
                if (s) {
                    motionX -= zVector.x * 0.12;
                    motionZ -= zVector.z * 0.12;
                    s = false;
                }
                if (sp) {
                    legmodeJump = true;

                    motionX *= 2;
                    motionZ *= 2;
                    motionY += 1.2 / (1 + 1.5 * sqrt((motionX * motionX) + (motionZ * motionZ)));

                    a = false;
                    d = false;
                    w = false;
                    s = false;
                    sp = false;
                }
            }else {
                if (a) {
                    motionX -= xVector.x * 0.02;
                    motionZ -= xVector.z * 0.02;
                    a = false;
                }
                if (d) {
                    motionX += xVector.x * 0.02;
                    motionZ += xVector.z * 0.02;
                    d = false;
                }
                if (w) {
                    motionX += zVector.x * 0.02;
                    motionZ += zVector.z * 0.02;
                    w = false;
                }
                if (s) {
                    motionX -= zVector.x * 0.02;
                    motionZ -= zVector.z * 0.02;
                    s = false;
                }
            }
        }

        if(!legSneak_CTRL){
            legSneak_State_Progress--;
            if(legSneak_State_Progress < 0){
                legSneak_State_Progress = 0;
            }
            legSneak_State = false;
        }else {
            legSneak_State_Progress++;
            if(legSneak_State_Progress >= 30){
                legSneak_State_Progress = 30;
                legSneak_State = true;
            }
        }
        if(worldObj.isRemote){
            motionX = tempx;
            motionY = tempy;
            motionZ = tempz;
        }
        moveEntity(motionX,motionY,motionZ);

        if(!onGround){
            legmodeJump = true;
            if(legStatejumped>0)legStatejumped --;
        }
        if(onGround && legmodeJump){
            legmodeJump = false;
            legmodeJumpstop = true;
        }
        if(legStatejumped > 12){
            legStatejumped = 12;
            legmodeJumpstop = false;
        }
        if(onGround)legStatejumped ++ ;
        motionY -= 0.049;

        if(onGround){
            motionX *= 0.75;
            motionZ *= 0.75;
        }
        else{
            motionX *= 0.99;
            motionZ *= 0.99;
        }
        motionY *= 0.99;
    }

    void FCS(Vector3d xVector,Vector3d zVector){

        switch (weaponMode){
            case 0:{
                if(!worldObj.isRemote) {
                    if (pilot != null) {
                        if (trigger1 && railGunChargecnt < 0 && railGunCoolcnt < 0 && legSneak_CTRL && legSneak_State && onGround) FireRail();
                        if (railGunChargecnt == 60) {
                            this.playSound("gvcguns:gvcguns.railcharge", 5.0F, 1.0F);
                        }
                    }
                }else {
                    if(pilot == HMV_Proxy.getEntityPlayerInstance()) {
                        Vec3 vec3 = Vec3.createVectorHelper(
                                this.posX + xVector.x * (gunpos[0][0]) + yVector.x * (gunpos[0][1]) + zVector.x * gunpos[0][2],
                                this.posY + xVector.y * (gunpos[0][0]) + yVector.y * (gunpos[0][1]) + 4.12 + pilotseatoffsety + zVector.y * gunpos[0][2],
                                this.posZ + xVector.z * (gunpos[0][0]) + yVector.z * (gunpos[0][1]) + zVector.z * gunpos[0][2]);
                        Vec3 playerlook = ((EntityLivingBase) pilot).getLook(1.0f);
                        playerlook = Vec3.createVectorHelper(playerlook.xCoord * 256, playerlook.yCoord * 256, playerlook.zCoord * 256);
                        Vec3 vec31 = Vec3.createVectorHelper(
                                this.posX + xVector.x * (gunpos[0][0]) + yVector.x * (gunpos[0][1]) + zVector.x * gunpos[0][2] + playerlook.xCoord,
                                this.posY + xVector.y * (gunpos[0][0]) + yVector.y * (gunpos[0][1]) + 4.12 + pilotseatoffsety + zVector.y * gunpos[0][2] + pilot.getEyeHeight() * 0.99 + playerlook.yCoord,
                                this.posZ + xVector.z * (gunpos[0][0]) + yVector.z * (gunpos[0][1]) + zVector.z * gunpos[0][2] + playerlook.zCoord);
                        MovingObjectPosition movingobjectposition = pilot.worldObj.func_147447_a(vec3, vec31, false, true, false);
                        Block hitblock;
                        Random rand = new Random();
                        vec3 = Vec3.createVectorHelper(
                                this.posX + xVector.x * (gunpos[0][0]) + yVector.x * (gunpos[0][1]) + zVector.x * gunpos[0][2],
                                this.posY + xVector.y * (gunpos[0][0]) + yVector.y * (gunpos[0][1]) + 4.12 + pilotseatoffsety + zVector.y * gunpos[0][2],
                                this.posZ + xVector.z * (gunpos[0][0]) + yVector.z * (gunpos[0][1]) + zVector.z * gunpos[0][2]);
                        vec31 = Vec3.createVectorHelper(
                                this.posX + xVector.x * (gunpos[0][0]) + yVector.x * (gunpos[0][1]) + zVector.x * gunpos[0][2] + playerlook.xCoord,
                                this.posY + xVector.y * (gunpos[0][0]) + yVector.y * (gunpos[0][1]) + 4.12 + pilotseatoffsety + zVector.y * gunpos[0][2] + pilot.getEyeHeight() * 0.99 + playerlook.yCoord,
                                this.posZ + xVector.z * (gunpos[0][0]) + yVector.z * (gunpos[0][1]) + zVector.z * gunpos[0][2] + playerlook.zCoord);
                        if (movingobjectposition != null) {
                            vec31 = Vec3.createVectorHelper(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
                        }
                        Entity rentity = null;
                        List list = pilot.worldObj.getEntitiesWithinAABBExcludingEntity(pilot, pilot.boundingBox.addCoord(playerlook.xCoord, playerlook.yCoord, playerlook.zCoord).expand(3.0D, 3.0D, 3.0D));
                        double d0 = 0.0D;
                        Entity entitylivingbase = pilot;
                        double d1 = 0;
                        for (int i1 = 0; i1 < list.size(); ++i1) {
                            Entity entity1 = (Entity) list.get(i1);
                            if (entity1 != this && entity1.canBeCollidedWith() && (entity1 != entitylivingbase)) {
                                float f = 0.3F;
                                AxisAlignedBB axisalignedbb = entity1.boundingBox.expand((double) f, (double) f, (double) f);
                                MovingObjectPosition movingobjectposition1 = axisalignedbb.calculateIntercept(vec3, vec31);

                                if (movingobjectposition1 != null) {
                                    d1 = vec3.distanceTo(movingobjectposition1.hitVec);

                                    if (d1 < d0 || d0 == 0.0D) {
                                        rentity = entity1;
                                        d0 = d1;
                                    }
                                }
                            }
                        }

                        if (rentity != null) {
                            d1 = vec3.distanceTo(vec31);
                            vec3.xCoord = vec3.xCoord + (vec31.xCoord - vec3.xCoord) * d0 / d1;
                            vec3.yCoord = vec3.yCoord + (vec31.yCoord - vec3.yCoord) * d0 / d1;
                            vec3.zCoord = vec3.zCoord + (vec31.zCoord - vec3.zCoord) * d0 / d1;

                            movingobjectposition = new MovingObjectPosition(rentity);
                            movingobjectposition.hitVec = vec3;
                        }
                        if (movingobjectposition != null && movingobjectposition.hitVec != null) {
                            HandmadeGunsCore.HMG_proxy.spawnParticles(new PacketSpawnParticle(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord, 2));
                        }
                    }
                }
            }
            break;
            case 1:{

                if(!worldObj.isRemote) {
                    TGT = null;
                    lockedBlockPos = null;
                    if (pilot instanceof EntityLivingBase) {
                        Vec3 vec3 = Vec3.createVectorHelper(this.posX, this.posY + 8.5, this.posZ);
                        Vec3 playerlook = ((EntityLivingBase) pilot).getLook(1.0f);
                        playerlook = Vec3.createVectorHelper(playerlook.xCoord * 256, playerlook.yCoord * 256, playerlook.zCoord * 256);
                        Vec3 vec31 = Vec3.createVectorHelper(pilot.posX + playerlook.xCoord, pilot.posY + pilot.getEyeHeight() * 0.99 + playerlook.yCoord, pilot.posZ + playerlook.zCoord);
                        MovingObjectPosition movingobjectposition = pilot.worldObj.func_147447_a(vec3, vec31, false, true, false);
                        Block hitblock;
                        Random rand = new Random();
                        vec3 = Vec3.createVectorHelper(pilot.posX, pilot.posY + pilot.getEyeHeight() * 0.99, pilot.posZ);
                        vec31 = Vec3.createVectorHelper(pilot.posX + playerlook.xCoord, pilot.posY + pilot.getEyeHeight() * 0.99 + playerlook.yCoord, pilot.posZ + playerlook.zCoord);
                        if (movingobjectposition != null) {
                            vec31 = Vec3.createVectorHelper(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
                        }
                        Entity rentity = null;
                        List list = pilot.worldObj.getEntitiesWithinAABBExcludingEntity(pilot, pilot.boundingBox.addCoord(playerlook.xCoord, playerlook.yCoord, playerlook.zCoord).expand(3.0D, 3.0D, 3.0D));
                        double d0 = 0.0D;
                        Entity entitylivingbase = pilot;
                        double d1 = 0;
                        for (int i1 = 0; i1 < list.size(); ++i1) {
                            Entity entity1 = (Entity) list.get(i1);
                            if (entity1 != this && entity1.canBeCollidedWith() && (entity1 != entitylivingbase)) {
                                float f = 0.3F;
                                AxisAlignedBB axisalignedbb = entity1.boundingBox.expand((double) f, (double) f, (double) f);
                                MovingObjectPosition movingobjectposition1 = axisalignedbb.calculateIntercept(vec3, vec31);

                                if (movingobjectposition1 != null) {
                                    d1 = vec3.distanceTo(movingobjectposition1.hitVec);

                                    if (d1 < d0 || d0 == 0.0D) {
                                        rentity = entity1;
                                        d0 = d1;
                                    }
                                }
                            }
                        }

                        if (rentity != null) {
                            d1 = vec3.distanceTo(vec31);
                            vec3.xCoord = vec3.xCoord + (vec31.xCoord - vec3.xCoord) * d0 / d1;
                            vec3.yCoord = vec3.yCoord + (vec31.yCoord - vec3.yCoord) * d0 / d1;
                            vec3.zCoord = vec3.zCoord + (vec31.zCoord - vec3.zCoord) * d0 / d1;

                            movingobjectposition = new MovingObjectPosition(rentity);
                            movingobjectposition.hitVec = vec3;
                        }
                        if (movingobjectposition != null) {
                            if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY && movingobjectposition.entityHit != null) {
                                if (pilot.ridingEntity == null || pilot.ridingEntity != movingobjectposition.entityHit) {
                                    TGT = movingobjectposition.entityHit;
                                }
                            } else {
                                if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                                    lockedBlockPos = Vec3.createVectorHelper(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
                                }
                            }
                        }
                        if (trigger1 && (TGT != null || lockedBlockPos != null)) {
                            FireMissile();
                        }
                    }
                }else if(pilot == HMV_Proxy.getEntityPlayerInstance()){
                    Vec3 vec3 = Vec3.createVectorHelper(this.posX, this.posY + 8.5, this.posZ);
                    Vec3 playerlook = ((EntityLivingBase) pilot).getLook(1.0f);
                    playerlook = Vec3.createVectorHelper(playerlook.xCoord * 256, playerlook.yCoord * 256, playerlook.zCoord * 256);
                    Vec3 vec31 = Vec3.createVectorHelper(pilot.posX + playerlook.xCoord, pilot.posY + pilot.getEyeHeight() * 0.99 + playerlook.yCoord, pilot.posZ + playerlook.zCoord);
                    MovingObjectPosition movingobjectposition = pilot.worldObj.func_147447_a(vec3, vec31, false, true, false);
                    Block hitblock;
                    Random rand = new Random();
                    vec3 = Vec3.createVectorHelper(pilot.posX, pilot.posY + pilot.getEyeHeight() * 0.99, pilot.posZ);
                    vec31 = Vec3.createVectorHelper(pilot.posX + playerlook.xCoord, pilot.posY + pilot.getEyeHeight() * 0.99 + playerlook.yCoord, pilot.posZ + playerlook.zCoord);
                    if (movingobjectposition != null) {
                        vec31 = Vec3.createVectorHelper(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
                    }
                    Entity rentity = null;
                    List list = pilot.worldObj.getEntitiesWithinAABBExcludingEntity(pilot, pilot.boundingBox.addCoord(playerlook.xCoord, playerlook.yCoord, playerlook.zCoord).expand(3.0D, 3.0D, 3.0D));
                    double d0 = 0.0D;
                    Entity entitylivingbase = pilot;
                    double d1 = 0;
                    for (int i1 = 0; i1 < list.size(); ++i1) {
                        Entity entity1 = (Entity) list.get(i1);
                        if (entity1 != this && entity1.canBeCollidedWith() && (entity1 != entitylivingbase)) {
                            float f = 0.3F;
                            AxisAlignedBB axisalignedbb = entity1.boundingBox.expand((double) f, (double) f, (double) f);
                            MovingObjectPosition movingobjectposition1 = axisalignedbb.calculateIntercept(vec3, vec31);

                            if (movingobjectposition1 != null) {
                                d1 = vec3.distanceTo(movingobjectposition1.hitVec);

                                if (d1 < d0 || d0 == 0.0D) {
                                    rentity = entity1;
                                    d0 = d1;
                                }
                            }
                        }
                    }

                    if (rentity != null) {
                        d1 = vec3.distanceTo(vec31);
                        vec3.xCoord = vec3.xCoord + (vec31.xCoord - vec3.xCoord) * d0 / d1;
                        vec3.yCoord = vec3.yCoord + (vec31.yCoord - vec3.yCoord) * d0 / d1;
                        vec3.zCoord = vec3.zCoord + (vec31.zCoord - vec3.zCoord) * d0 / d1;

                        movingobjectposition = new MovingObjectPosition(rentity);
                        movingobjectposition.hitVec = vec3;
                    }
                    if (movingobjectposition != null && movingobjectposition.hitVec != null) {

                        HandmadeGunsCore.HMG_proxy.spawnParticles(new PacketSpawnParticle(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord, 2));
                    }
                }
            }
            break;
            case 2:{
                if(!worldObj.isRemote && pilot != null && normalGunHeat < normalGunHeat_Max && trigger1 && normalGunCoolcnt<0){
                    FireMG1();
                    FireMG2();
                    normalGunHeat+=8;
                    if(normalGunHeat>normalGunHeat_Max)normalGunHeat = normalGunHeat_Max + 20;
                    normalGunCoolcnt = normalGunCool;
                }
            }
            break;
        }
        railGunChargecnt--;
        railGunCoolcnt--;
        rocketCool--;
        normalGunCoolcnt-=2;
        if(normalGunHeat>0)normalGunHeat--;
        if(rocketreloadCnt<0){
            rocketreloadCnt = 90;
            if(rocketMagazine<90) {
                rocketMagazine++;
            }
        }
        rocketreloadCnt--;
    }
    void FireRail(){

        if(railGunMagazine <= 0){
            railGunChargecnt = railGunCharge;
            railGunMagazine = 3;
        }
        HMGEntityBulletExprode bullet = new HMGEntityBulletExprode(worldObj,pilot,1000,5,0);
        bullet.exlevel = 10;
        bullet.canex = true;
        bullet.setLocationAndAngles(
                this.posX + xVector.x * (gunpos[0][0]) + yVector.x * (gunpos[0][1]) + zVector.x * gunpos[0][2]
                , this.posY + xVector.y * (gunpos[0][0]) + yVector.y * (gunpos[0][1]) + 4.12 + pilotseatoffsety + zVector.y * gunpos[0][2]
                , this.posZ + xVector.z * (gunpos[0][0]) + yVector.z * (gunpos[0][1]) + zVector.z * gunpos[0][2]
                , bodyrotationYaw + RailGUNrotationYaw, RailGUNrotationPitch);
        bullet.setHeadingFromThrower(RailGUNrotationPitch,bodyrotationYaw + RailGUNrotationYaw,100,0);
        bullet.playSound("gvcguns:gvcguns.railCannon", 10.0F, 0.8F);
        bullet.bulletTypeName = "byfrou01_RailBullet";

        PacketSpawnParticle flash = new PacketSpawnParticle(
                bullet.posX + bullet.motionX*0.8,
                bullet.posY + bullet.motionY*0.8,
                bullet.posZ + bullet.motionZ*0.8,bodyrotationYaw + RailGUNrotationYaw, RailGUNrotationPitch,0,"RailMuzzleFlash",true);
        flash.fuse = 3;
        flash.scale = 100;
        flash.id = 100;
        HMGPacketHandler.INSTANCE.sendToAll(flash);

//        for(int i= 0;i<5;i++) {
//            PacketSpawnParticle packet = new PacketSpawnParticle(
//                    bullet.posX + bullet.motionX*3,
//                    bullet.posY + bullet.motionY*3,
//                    bullet.posZ + bullet.motionZ*3,
//                    bullet.motionX/(15+i*3),
//                    bullet.motionY/(15+i*3),
//                    bullet.motionZ/(15+i*3), 3);
//            packet.fuse = 10;
//            HMGPacketHandler.INSTANCE.sendToAll(packet);
//        }

        worldObj.spawnEntityInWorld(bullet);
        railGunCoolcnt = railGunCool;
        railGunMagazine --;
    }
    void FireMissile(){
        if(rocketMagazine > 0 && rocketCool<0){
            int pos = 1 + rand.nextInt(4);
            HMGEntityBulletRocket missile = new HMGEntityBulletRocket(worldObj,pilot,200,5,3);
            missile.setLocationAndAngles(
                    this.posX + xVector.x * gunpos[pos][0] + yVector.x * (gunpos[pos][1]) + zVector.x * gunpos[pos][2]
                    , this.posY + xVector.y * gunpos[pos][0] + yVector.y * (gunpos[pos][1]) + pilotseatoffsety + zVector.y * gunpos[pos][2]
                    , this.posZ + xVector.z * gunpos[pos][0] + yVector.z * (gunpos[pos][1]) + zVector.z * gunpos[pos][2]
                    , bodyrotationYaw, 90);
            missile.motionY = 1 + this.motionY;
            missile.motionX = 0 + this.motionX;
            missile.motionZ = 0 + this.motionZ;
            missile.acceleration = 0.1f;
            missile.induction_precision = 10f;
            missile.seekerwidth = 360;
            missile.bulletTypeName = "byfrou01_Rocket";
            missile.canex = true;

            if(TGT != null){
                missile.homingEntity = TGT;
            }else if(lockedBlockPos != null){
                missile.lockedBlockPos = lockedBlockPos;
            }
            rocketCool = 5;
            worldObj.playSoundAtEntity(this,"handmadeguns:handmadeguns.missileLaunch",1,4);
            worldObj.spawnEntityInWorld(missile);
            rocketMagazine--;
        }
    }
    void FireMG1(){
        HMGEntityBullet bullet = new HMGEntityBullet(worldObj,pilot,20,5,3,null);
        bullet.setLocationAndAngles(
                this.posX + xVector.x * (gunpos[5][0] + 1) + yVector.x * (gunpos[5][1]) + zVector.x * gunpos[5][2]
                , this.posY + xVector.y * (gunpos[5][0] + 1) + yVector.y * (gunpos[5][1]) + pilotseatoffsety + 2 + zVector.y * gunpos[5][2]
                , this.posZ + xVector.z * (gunpos[5][0] + 1) + yVector.z * (gunpos[5][1]) + zVector.z * gunpos[5][2]
                , bodyrotationYaw + headrotationYaw, headrotationPitch);
        bullet.setHeadingFromThrower(headrotationPitch,bodyrotationYaw + headrotationYaw,10,3);
        bullet.playSound("handmadeguns:handmadeguns.HeavyMachineGun", 5.0F, 1.0F);
        bullet.bulletTypeName = "byfrou01_GreenTracer";
        worldObj.spawnEntityInWorld(bullet);
    }
    void FireMG2(){
        HMGEntityBullet bullet = new HMGEntityBullet(worldObj,pilot,20,5,3,null);
        bullet.setLocationAndAngles(
                this.posX + xVector.x * (gunpos[5][0] - 1) + yVector.x * (gunpos[5][1]) + zVector.x * gunpos[5][2]
                , this.posY + xVector.y * (gunpos[5][0] - 1) + yVector.y * (gunpos[5][1]) + pilotseatoffsety + 2 + zVector.y * gunpos[5][2]
                , this.posZ + xVector.z * (gunpos[5][0] - 1) + yVector.z * (gunpos[5][1]) + zVector.z * gunpos[5][2]
                , bodyrotationYaw + headrotationYaw, headrotationPitch);
        bullet.setHeadingFromThrower(headrotationPitch,bodyrotationYaw + headrotationYaw,10,3);
        bullet.playSound("handmadeguns:handmadeguns.HeavyMachineGun", 5.0F, 1.0F);
        bullet.bulletTypeName = "byfrou01_GreenTracer";
        worldObj.spawnEntityInWorld(bullet);
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {
        this.health = p_70037_1_.getFloat("health");
        this.rocketMagazine = p_70037_1_.getInteger("rocketMagazine");
        this.railGunMagazine = p_70037_1_.getInteger("railGunMagazine");

    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {
        p_70014_1_.setFloat("health",health);
        p_70014_1_.setInteger("rocketMagazine",rocketMagazine);
        p_70014_1_.setInteger("railGunMagazine",railGunMagazine);

    }

    public void setTrigger(boolean trig1, boolean trig2) {

    }

//    public EntityChild[] getChildren() {
//        return childEntities;
//    }

//    public void addChild(EntityChild seat) {
//        if(seat.idinmasterEntityt != -1 && seat.idinmasterEntityt < childEntities.length){
//            this.childEntities[seat.idinmasterEntityt] = seat;
//        }
//    }

    public boolean isRidingEntity(Entity entity) {
        
        return entity == riddenByEntity;
    }


    public void applyEntityCollision(Entity p_70108_1_)
    {
//		for(EntityChild aseat: childEntities){
//			if(aseat != null && aseat.riddenByEntity == p_70108_1_)return;
//		}
//		super.applyEntityCollision(p_70108_1_);
    }
    @Override
    public boolean interactFirst(EntityPlayer entityplayer) {
        if(!worldObj.isRemote) {
            if (entityplayer.isSneaking()) {
                if (entityplayer.getEquipmentInSlot(0) != null) {
                    ItemStack itemstack = entityplayer.getEquipmentInSlot(0);
                    if (itemstack.getItem() == Items.iron_ingot) {
                        itemstack.stackSize--;
                        this.health += 20;
                        if (this.health > maxhealth) health = maxhealth;
                        if (itemstack.stackSize <= 0 && !entityplayer.capabilities.isCreativeMode) {
                            entityplayer.destroyCurrentEquippedItem();
                        }
                    }
                    return true;
                }
            }else
            if (riddenByEntity == null && entityplayer.ridingEntity == null) {
                entityplayer.mountEntity(this);
                return true;
            }
        }
        return false;
    }
    public boolean canBePushed()
    {
        return true;
    }
    public boolean canBeCollidedWith()
    {
        return true;
    }

//    public boolean isChild(Entity entity){
//        for(Entity achild:childEntities){
//            if(entity == achild)return true;
//        }
//        return entity == this;
//    }

    public int getpilotseatid() {
        return -1;
    }

    void destroyNearBlocks(AxisAlignedBB boundingBox,double movespeed){

        int destroy_counterx = 0;
        int destroy_countery = 0;
        int destroy_counterz = 0;

        for (int x = (int) boundingBox.minX; x <= boundingBox.maxX; x++) {
            for (int y = (int) boundingBox.minY; y <= boundingBox.maxY; y++) {
                for (int z = (int) boundingBox.minZ; z <= boundingBox.maxZ; z++) {
                    Block collidingblock = worldObj.getBlock(x, y, z);
                    if (movespeed > collidingblock.getBlockHardness(null, 0, 0, 0)/2 || collidingblock.getMaterial() == Material.leaves || collidingblock.getMaterial() == Material.wood || collidingblock.getMaterial() == Material.glass || collidingblock.getMaterial() == Material.cloth) {
                        if(!worldObj.isAirBlock(x, y, z)){
                            worldObj.setBlockToAir(x,y,z);
                            this.worldObj.playAuxSFX(2001, x,y,z, Block.getIdFromBlock(collidingblock));
                            destroy_counterx++;
                            destroy_countery++;
                            destroy_counterz++;
                        }
                    }
                }
            }
        }
        for (int x = (int) boundingBox.maxX; x <= boundingBox.maxX + 2; x++) {
            for (int y = (int) boundingBox.minY+1; y <= boundingBox.maxY; y++) {
                for (int z = (int) boundingBox.minZ; z <= boundingBox.maxZ; z++) {
                    Block collidingblock = worldObj.getBlock(x, y, z);
                    if (abs(motionX) > collidingblock.getBlockHardness(null, 0, 0, 0)/2 || collidingblock.getMaterial() == Material.leaves || collidingblock.getMaterial() == Material.wood || collidingblock.getMaterial() == Material.glass || collidingblock.getMaterial() == Material.cloth) {
                        if(!worldObj.isAirBlock(x, y, z)) {
                            worldObj.setBlockToAir(x,y,z);
                            this.worldObj.playAuxSFX(2001, x,y,z, Block.getIdFromBlock(collidingblock));
                            destroy_counterx++;
                        }
                    }
                }
            }
        }
        for (int x = (int) boundingBox.minX - 2; x <= boundingBox.minX; x++) {
            for (int y = (int) boundingBox.minY+1; y <= boundingBox.maxY; y++) {
                for (int z = (int) boundingBox.minZ; z <= boundingBox.maxZ; z++) {
                    Block collidingblock = worldObj.getBlock(x, y, z);
                    if (abs(motionX) > collidingblock.getBlockHardness(null, 0, 0, 0)/2 || collidingblock.getMaterial() == Material.leaves || collidingblock.getMaterial() == Material.wood || collidingblock.getMaterial() == Material.glass || collidingblock.getMaterial() == Material.cloth) {
                        if(!worldObj.isAirBlock(x, y, z)){
                            worldObj.setBlockToAir(x,y,z);
                            this.worldObj.playAuxSFX(2001, x,y,z, Block.getIdFromBlock(collidingblock));
                            destroy_counterx++;
                        }
                    }
                }
            }
        }
        for (int x = (int) boundingBox.minX; x <= boundingBox.maxX; x++) {
            for (int y = (int) boundingBox.minY+1; y <= boundingBox.maxY; y++) {
                for (int z = (int) boundingBox.maxZ; z <= boundingBox.maxZ + 2; z++) {
                    Block collidingblock = worldObj.getBlock(x, y, z);
                    if (abs(motionZ) > collidingblock.getBlockHardness(null, 0, 0, 0)/2 || collidingblock.getMaterial() == Material.leaves || collidingblock.getMaterial() == Material.wood || collidingblock.getMaterial() == Material.glass || collidingblock.getMaterial() == Material.cloth) {
                        if(!worldObj.isAirBlock(x, y, z)){
                            worldObj.setBlockToAir(x,y,z);
                            this.worldObj.playAuxSFX(2001, x,y,z, Block.getIdFromBlock(collidingblock));
                            destroy_counterz++;
                        }
                    }
                }
            }
        }
        for (int x = (int) boundingBox.minX; x <= boundingBox.maxX; x++) {
            for (int y = (int) boundingBox.minY+1; y <= boundingBox.maxY; y++) {
                for (int z = (int) boundingBox.minZ - 2; z <= boundingBox.minZ; z++) {
                    Block collidingblock = worldObj.getBlock(x, y, z);
                    if (abs(motionZ) > collidingblock.getBlockHardness(null, 0, 0, 0)/2 || collidingblock.getMaterial() == Material.leaves || collidingblock.getMaterial() == Material.wood || collidingblock.getMaterial() == Material.glass || collidingblock.getMaterial() == Material.cloth) {
                        if(!worldObj.isAirBlock(x, y, z)){
                            worldObj.setBlockToAir(x,y,z);
                            this.worldObj.playAuxSFX(2001, x,y,z, Block.getIdFromBlock(collidingblock));
                            destroy_counterz++;
                        }
                    }
                }
            }
        }


        for (int x = (int) boundingBox.maxX; x <= boundingBox.maxX + 2; x++) {
            for (int y = (int) boundingBox.minY+1; y <= boundingBox.maxY; y++) {
                for (int z = (int) boundingBox.maxZ; z <= boundingBox.maxZ + 2; z++) {
                    Block collidingblock = worldObj.getBlock(x, y, z);
                    if (sqrt(motionX * motionX + motionZ * motionZ) > collidingblock.getBlockHardness(null, 0, 0, 0)/2 || collidingblock.getMaterial() == Material.leaves || collidingblock.getMaterial() == Material.wood || collidingblock.getMaterial() == Material.glass || collidingblock.getMaterial() == Material.cloth) {
                        if(!worldObj.isAirBlock(x, y, z)){
                            worldObj.setBlockToAir(x,y,z);
                            this.worldObj.playAuxSFX(2001, x,y,z, Block.getIdFromBlock(collidingblock));
                            destroy_counterx++;
                            destroy_counterz++;
                        }
                    }
                }
            }
        }
        for (int x = (int) boundingBox.minX - 2; x <= boundingBox.minX; x++) {
            for (int y = (int) boundingBox.minY+1; y <= boundingBox.maxY; y++) {
                for (int z = (int) boundingBox.minZ - 2; z <= boundingBox.minZ; z++) {
                    Block collidingblock = worldObj.getBlock(x, y, z);
                    if (sqrt(motionX * motionX + motionZ * motionZ) > collidingblock.getBlockHardness(null, 0, 0, 0)/2 || collidingblock.getMaterial() == Material.leaves || collidingblock.getMaterial() == Material.wood || collidingblock.getMaterial() == Material.glass || collidingblock.getMaterial() == Material.cloth) {
                        if(!worldObj.isAirBlock(x, y, z)){
                            worldObj.setBlockToAir(x,y,z);
                            this.worldObj.playAuxSFX(2001, x,y,z, Block.getIdFromBlock(collidingblock));
                            destroy_counterx++;
                            destroy_counterz++;
                        }
                    }
                }
            }
        }

        for (int x = (int) boundingBox.maxX; x <= boundingBox.maxX + 2; x++) {
            for (int y = (int) boundingBox.minY+1; y <= boundingBox.maxY; y++) {
                for (int z = (int) boundingBox.maxZ; z <= boundingBox.maxZ + 2; z++) {
                    Block collidingblock = worldObj.getBlock(x, y, z);
                    if (sqrt(motionX * motionX + motionZ * motionZ) > collidingblock.getBlockHardness(null, 0, 0, 0)/2 || collidingblock.getMaterial() == Material.leaves || collidingblock.getMaterial() == Material.wood || collidingblock.getMaterial() == Material.glass || collidingblock.getMaterial() == Material.cloth) {
                        if(!worldObj.isAirBlock(x, y, z)){
                            worldObj.setBlockToAir(x,y,z);
                            this.worldObj.playAuxSFX(2001, x,y,z, Block.getIdFromBlock(collidingblock));
                            destroy_counterx++;
                            destroy_counterz++;
                        }
                    }
                }
            }
        }
        for (int x = (int) boundingBox.minX - 2; x <= boundingBox.minX; x++) {
            for (int y = (int) boundingBox.minY+1; y <= boundingBox.maxY; y++) {
                for (int z = (int) boundingBox.minZ - 2; z <= boundingBox.minZ; z++) {
                    Block collidingblock = worldObj.getBlock(x, y, z);
                    if (sqrt(motionX * motionX + motionZ * motionZ) > collidingblock.getBlockHardness(null, 0, 0, 0)/2 || collidingblock.getMaterial() == Material.leaves || collidingblock.getMaterial() == Material.wood || collidingblock.getMaterial() == Material.glass || collidingblock.getMaterial() == Material.cloth) {
                        if(!worldObj.isAirBlock(x, y, z)){
                            worldObj.setBlockToAir(x,y,z);
                            this.worldObj.playAuxSFX(2001, x,y,z, Block.getIdFromBlock(collidingblock));
                            destroy_counterx++;
                            destroy_counterz++;
                        }
                    }
                }
            }
        }


        for(int temp= 0;temp < destroy_counterx;temp ++ ){
            motionX *= 0.95;
        }
        for(int temp= 0;temp < destroy_countery;temp ++ ){
            motionY *= 0.95;
        }
        for(int temp= 0;temp < destroy_counterz;temp ++ ){
            motionZ *= 0.95;
        }

    }
}
