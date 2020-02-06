package hmggvcmob.ai;

import handmadeguns.entity.PlacedGunEntity;
import handmadeguns.items.guns.HMGItem_Unified_Guns;
import handmadevehicle.entity.EntityDummy_rider;
import hmggvcmob.IflagBattler;
import handmadevehicle.SlowPathFinder.WorldForPathfind;
import hmggvcmob.entity.friend.EntitySoBases;
import hmggvcmob.entity.guerrilla.EntityGBases;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

import javax.vecmath.Vector3d;
import java.util.List;
import java.util.Random;

import static java.lang.Math.abs;

public class AIAttackGun extends EntityAIBase {
    private EntityLiving shooter;//射撃手
    private EntityLivingBase target;//ターゲット
    public float maxshootrange;//射程
    public float minshootrange;//射程
    public int bursttime;//一連射の時間
    public int burstingtime;//撃ってる時間
    public int burstcool = 20;//連射の間隔
    public int burstcoolcnt;
    private int Warningblank = 50;
    private int Warningcoolcnt;
    public int retriggerCool;
    private boolean isSelectorChecked = false;
    int lastentitysize;
    private int forget = 0;//忘れるまで
    private int aiming = 0;//狙ってる時間
    private boolean ismoveable = true;
    private Random rnd;
    private WorldForPathfind worldForPathfind;
    private Vector3d lastSeenPosition = null;
    private boolean shoot_avoid_checkline = true;

    private int changesearchdircool = 0;
    private boolean searchdirY;
    private boolean searchdirP;
    public boolean assault;
    public float assaultrange;//この距離まで詰める

    private int refindpath = 0;
    private int dir_modeChangeCool = 600;
    private boolean roundDir;
    private boolean moveRound = false;

    private boolean fEnable = true;

    private int standingTime;

    public AIAttackGun(EntityLiving guerrilla,float range,float minrange,int bursttimer,WorldForPathfind worldForPathfind){
        this.shooter = guerrilla;
        this.maxshootrange = range;
        this.minshootrange = minrange;
        this.bursttime = bursttimer;
        this.burstcool = bursttime*5;
        this.rnd = new Random();
        this.worldForPathfind = worldForPathfind;
        this.moveRound = rnd.nextBoolean();
    }
    public AIAttackGun(EntityLiving guerrilla, float range,float minrange, int bursttime, boolean ismoveable,WorldForPathfind worldForPathfind){
        this(guerrilla,range,minrange,bursttime,worldForPathfind);
        this.ismoveable = ismoveable;
    }
    public AIAttackGun(EntityLiving guerrilla, float range,float minrange, int bursttime, int burstcool, boolean ismoveable,WorldForPathfind worldForPathfind){
        this(guerrilla,range,minrange,bursttime,ismoveable,worldForPathfind);
        this.burstcool = burstcool;
    }
    public AIAttackGun(EntityLiving guerrilla, float range,float minrange, int bursttime, int burstcool, boolean ismoveable,boolean assault,WorldForPathfind worldForPathfind){
        this(guerrilla,range,minrange,bursttime,ismoveable,worldForPathfind);
        this.burstcool = burstcool;
        this.assault = assault;
    }
    public AIAttackGun(EntityLiving guerrilla, float range,float minrange, int bursttime, boolean ismoveable,boolean sac,WorldForPathfind worldForPathfind){
        this(guerrilla,range,minrange,bursttime,ismoveable,worldForPathfind);
        shoot_avoid_checkline = sac;
    }
    public boolean forceStop = false;
    @Override
    public boolean shouldExecute() {
        if(forceStop){
            forceStop = false;
            return false;
        }
        return setUp();
    }
    public boolean setUp(){
        EntityLivingBase entityliving = shooter.getAttackTarget();
        if(shooter instanceof EntityGBases && entityliving instanceof EntityGBases){
            entityliving = null;
        }
        if(shooter instanceof EntitySoBases && entityliving instanceof EntitySoBases){
            entityliving = null;
        }
        if (entityliving == null || entityliving.isDead || forget > 500000) {
            aiming = 0;
            shooter.setAttackTarget(null);
            forget = 0;
            lastSeenPosition = null;
            return false;
        } else {
            target = entityliving;
            return true;
        }
    }
    @Override
    public boolean continueExecuting() {
        if(shouldExecute()){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void resetTask() {
        shooter.getNavigator().clearPathEntity();
        burstingtime = 0;
    }
    @Override
    public void updateTask() {
        try {
            if (target != null) {
//                System.out.println("debug");
                double maxrange = maxshootrange * maxshootrange;
                double minrange = minshootrange * minshootrange;
                if (shooter.ridingEntity == null && !(shooter.getHeldItem() != null && shooter.getHeldItem().getItem() instanceof HMGItem_Unified_Guns)) {
                    maxrange = 0;
                    minrange = 0;
                }
                Vector3d currentAttackToPosition = getSeeingPosition();
                if (currentAttackToPosition != null) {
                    double tocurrentAttackToPosition = shooter.getDistanceSq(currentAttackToPosition.x, currentAttackToPosition.y, currentAttackToPosition.z);
                    boolean see = shooter.canEntityBeSeen(target);
                    boolean isnotblinded;
                    boolean canSee = isnotblinded = !shooter.isPotionActive(Potion.blindness);
                    canSee &= see;
                    if (canSee) {
                        shooter.getLookHelper().setLookPosition(currentAttackToPosition.x, currentAttackToPosition.y, currentAttackToPosition.z, 1000000, 1000000);
                    }else {
                        lookAround();
                    }
                    if(refindpath < 0 || (shooter.getHeldItem() == null || !(shooter.getHeldItem().getItem() instanceof HMGItem_Unified_Guns))){
                        setPath(currentAttackToPosition,tocurrentAttackToPosition,canSee);
                        refindpath = 10;
                    }
//                System.out.println("debug PR" + shooter.rotationPitch);
//                shooter.getLookHelper().onUpdateLook();
//                System.out.println("debug AF" + shooter.rotationPitch);
                    refindpath -= 1;
                    dir_modeChangeCool--;
                    if(shooter.getNavigator().getPath() == null)standingTime++;
                    else standingTime--;
                    if(dir_modeChangeCool < 0 || standingTime>100){
                        dir_modeChangeCool = 20 + rnd.nextInt(60);
                        roundDir = rnd.nextBoolean();
                        moveRound = rnd.nextBoolean();
                    }
                    if (!canSee) {
                        aiming = 0;
                    } else {
                        aiming++;
                    }
                    if (maxrange > tocurrentAttackToPosition && isnotblinded &&
                            ((canSee && aiming > 40) || (!canSee && Warningcoolcnt < 0 && tocurrentAttackToPosition > 16))
                            && burstingtime > 0 && retriggerCool <= 0) {
                        ItemStack gunStack = null;
                        if (shooter.ridingEntity instanceof PlacedGunEntity) {
                            gunStack = ((PlacedGunEntity) shooter.ridingEntity).gunStack;
                        }else
                        if (shooter.getHeldItem() != null) {
                            gunStack = shooter.getHeldItem();
                        }

                        if (gunStack != null && gunStack.getItem() instanceof HMGItem_Unified_Guns) {
                            gunStack.getTagCompound().setBoolean("IsTriggered", true);
                            shooter.getEntityData().setBoolean("HMGisUsingItem", true);
                            checkSelector(gunStack);
                            if (((HMGItem_Unified_Guns) gunStack.getItem()).getburstCount(gunStack.getTagCompound().getInteger("HMGMode")) != -1) {
                                retriggerCool = rnd.nextInt((int) (abs(((HMGItem_Unified_Guns) gunStack.getItem()).gunInfo.recoil) + 1) * 3);
                            }
                        }
                        forget = 0;
                    }
                    burstingtime--;
                    if (!canSee) {
                        forget++;
                    }
                    if (burstingtime < 0) {
                        if (burstcoolcnt < 0) {
                            burstingtime = rnd.nextInt(bursttime);
                            burstcoolcnt = burstcool;
                        } else {
                            burstcoolcnt--;
                        }
                    }
                    if (!canSee && Warningcoolcnt < -20) {
                        Warningcoolcnt = rnd.nextInt(Warningblank) + 80;
                    }
                    Warningcoolcnt--;
                    retriggerCool--;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    private void lookAround(){

    }
    private void setPath(Vector3d currentAttackToPosition,double tocurrentAttackToPosition,boolean canSee){
        double maxrange = maxshootrange * maxshootrange;
        double minrange = minshootrange * minshootrange;
        if (shooter.ridingEntity == null && (shooter.getHeldItem() == null || !(shooter.getHeldItem().getItem() instanceof HMGItem_Unified_Guns))) {
            maxrange = 0;
            minrange = 0;
            moveRound = false;
        }
//        Vector3d reCurToTargetPosition = new Vector3d(currentAttackToPosition);
//        Vector3d moveTo = new Vector3d(shooter.posX, shooter.posY, shooter.posZ);
//        reCurToTargetPosition.sub(moveTo);
//        if(moveRound) {
//            Vector3d relativeCurrentAttackToPosition_Copy = new Vector3d(reCurToTargetPosition);
//            reCurToTargetPosition.x = relativeCurrentAttackToPosition_Copy.z;
//            reCurToTargetPosition.z = -relativeCurrentAttackToPosition_Copy.x;
//
//            if (roundDir) {
//                reCurToTargetPosition.x *= -1;
//                reCurToTargetPosition.z *= -1;
//            }
//            boolean flag = false;
//            if(shooter instanceof IflagBattler){
//                byte state = ((IflagBattler) shooter).getState();
//                int[] targetPos = ((IflagBattler) shooter).getTargetCampPosition();
//                Vector3d relativeCurrentFlagToPosition = new Vector3d(targetPos[0],targetPos[1],targetPos[2]);
//                Vector3d moveToflag = new Vector3d(shooter.posX, shooter.posY, shooter.posZ);
//                relativeCurrentFlagToPosition.sub(moveToflag);
//                if (state == 1) {
//                    reCurToTargetPosition.add(relativeCurrentFlagToPosition);
//                    flag = true;
//                }else
//                if (state == 2) {
//                    reCurToTargetPosition.add(relativeCurrentFlagToPosition);
//                    flag = true;
//                }
//                if (state == 4) {
//                    reCurToTargetPosition.sub(relativeCurrentFlagToPosition);
//                    flag = true;
//                }else
//                if (state == 5) {
//                    reCurToTargetPosition.sub(relativeCurrentFlagToPosition);
//                    flag = true;
//                }
//            }
//            if(!flag) {
//                if (tocurrentAttackToPosition < minrange) {
//                    reCurToTargetPosition.sub(relativeCurrentAttackToPosition_Copy);
//                } else if (tocurrentAttackToPosition > maxrange) {
//                    reCurToTargetPosition.add(relativeCurrentAttackToPosition_Copy);
//                }
//            }
//        }
//        moveTo.add(reCurToTargetPosition);
//
//        shooter.getNavigator().clearPathEntity();
//
//        if(moveRound) {
//            shooter.getNavigator().setPath(worldForPathfind.getEntityPathToXYZ(shooter, MathHelper.floor_double(moveTo.x),
//                    MathHelper.floor_double(moveTo.y),
//                    MathHelper.floor_double(moveTo.z),
//                    80, true, false, true, false), 0.4);
//        }
//        else if(!(shooter instanceof IflagBattler)||((IflagBattler) shooter).getState() == -1)//旗AI積んでる時は突撃しない
//        {
//            if (!canSee || maxrange < tocurrentAttackToPosition)
//                shooter.getNavigator().setPath(worldForPathfind.getEntityPathToXYZ(shooter, MathHelper.floor_double(moveTo.x),
//                        MathHelper.floor_double(moveTo.y),
//                        MathHelper.floor_double(moveTo.z),
//                        80, true, false, true, false), 1);
//            else if (minrange > tocurrentAttackToPosition)
//                shooter.getNavigator().setPath(worldForPathfind.getEntityPathToXYZ(shooter, MathHelper.floor_double(moveTo.x),
//                        MathHelper.floor_double(moveTo.y),
//                        MathHelper.floor_double(moveTo.z),
//                        80, true, false, true, false), -1);
//        }else {
//            byte state = ((IflagBattler) shooter).getState();
//            int[] targetPos = ((IflagBattler) shooter).getTargetCampPosition();
//            double speed = 0;
//            if (state == 1) {
//                speed = 1;
//            }else
//            if (state == 2) {
//                speed = 0.6;
//            }
//            shooter.getNavigator().setPath(worldForPathfind.getEntityPathToXYZ(shooter,
//                    targetPos[0],
//                    targetPos[1],
//                    targetPos[2],
//                    80, true, false, true, false), speed);
//        }
        Vector3d reCurToTargetPosition = new Vector3d(currentAttackToPosition);
        Vector3d moveToVec = new Vector3d(shooter.posX, shooter.posY, shooter.posZ);
        Vector3d reCurToTargetPosition_copy = new Vector3d(reCurToTargetPosition);
        reCurToTargetPosition.sub(moveToVec);
        byte state = -1;//stop


        if(shooter instanceof IflagBattler) {
            state = ((IflagBattler) shooter).getState();
        }
        if(state == -1) {
            if (!canSee || maxrange < tocurrentAttackToPosition)
                state = 1;//go
            else if (minrange > tocurrentAttackToPosition)
                state = 3;//back
            else if (moveRound)
                state = 2;//slow
        }

        double speed = 0;
        if (state == 1) {
            speed = 1;
        } else if (state == 2) {
            speed = 0.6;
        }else
        if (state == 3) {
            speed = -1;
        }
        if(moveRound) {
            reCurToTargetPosition.x = reCurToTargetPosition_copy.z * -1;
            reCurToTargetPosition.z = reCurToTargetPosition_copy.x;
            if (roundDir){
                reCurToTargetPosition.x *= -1;
                reCurToTargetPosition.z *= -1;
            }
            reCurToTargetPosition_copy.scale(speed);
            reCurToTargetPosition.add(reCurToTargetPosition_copy);
            speed = 0.6;
        }else {
            reCurToTargetPosition.scale(speed != 0?1:0);
        }
        moveToVec.add(reCurToTargetPosition);

        shooter.getNavigator().setPath(worldForPathfind.getEntityPathToXYZ(shooter,
                MathHelper.floor_double(moveToVec.x),
                MathHelper.floor_double(moveToVec.y),
                MathHelper.floor_double(moveToVec.z),
                80, true, false, false, false), speed);
    }
    private void checkSelector(ItemStack gunStack){

        if (!isSelectorChecked) {
            List<Integer> burst = ((HMGItem_Unified_Guns) gunStack.getItem()).gunInfo.burstcount;
            int temp = 0;
            int mode = 0;
            for (int i = 0; i < burst.size(); i++) {
                if (burst.get(i) == -1) {
                    mode = i;
                    break;
                }
                if (burst.get(i) != 0 && temp < burst.get(i)) {
                    temp = burst.get(i);
                    mode = i;
                }
            }
            if (burst.size() > mode)
                gunStack.getTagCompound().setInteger("HMGMode", mode);
            isSelectorChecked = true;
        }
    }
//    private boolean canNavigate()
//    {
//        return this.shooter.onGround || shooter.isInWater() || this.shooter.isRiding() && this.shooter.ridingEntity instanceof EntityChicken;
//    }
    public Vector3d getSeeingPosition(){
        if(target != null && shooter.canEntityBeSeen(target)){
            if(lastSeenPosition == null)lastSeenPosition = new Vector3d();
            Entity aimTo = target;
            if(target.ridingEntity != null){
                if(target.ridingEntity instanceof EntityDummy_rider){
                    aimTo = ((EntityDummy_rider) target.ridingEntity).linkedBaseLogic.mc_Entity;
                }else {
                    aimTo = target.ridingEntity;
                }
            }
            lastSeenPosition.set(aimTo.posX,aimTo.posY + aimTo.height/2,aimTo.posZ);
        }
        return lastSeenPosition;
    }
}
