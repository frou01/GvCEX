package hmggvcmob.ai;

import handmadeguns.entity.PlacedGunEntity;
import handmadeguns.items.guns.HMGItem_Unified_Guns;
import hmggvcutil.entity.GVCEntityBox;
import hmggvcmob.IflagBattler;
import hmggvcmob.SlowPathFinder.WorldForPathfind;
import hmggvcmob.entity.IGVCmob;
import hmggvcmob.entity.friend.EntitySoBases;
import hmggvcmob.entity.guerrilla.EntityGBases;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

import java.util.List;
import java.util.Random;

import static java.lang.Math.abs;

public class AIAttackGun extends EntityAIBase {
    private EntityLiving shooter;//ゲリラさん
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
    private boolean know_the_position_of_the_enemy = false;
    private double lastTargetX;
    private double lastTargetY;
    private double lastTargetZ;
    private boolean shoot_avoid_checkline = true;
    private int changesearchdircool = 0;
    private boolean searchdirY;
    private boolean searchdirP;
    public boolean assault;
    public float assaultrange;//この距離まで詰める

    private int refindpath = 0;

    private boolean fEnable = true;
    //used by Guerrilla
    public AIAttackGun(EntityLiving guerrilla,float range,float minrange,int spread,int bursttime){
        this.shooter = guerrilla;
        maxshootrange = range;
        minshootrange = minrange;
        this.bursttime = bursttime;
        this.burstcool = bursttime*5;
        rnd = new Random();
        worldForPathfind = new WorldForPathfind(guerrilla.worldObj);
    }
    public AIAttackGun(EntityLiving guerrilla, float range,float minrange, int spread, int bursttime, boolean ismoveable){
        this(guerrilla,range,minrange,spread,bursttime);
        this.ismoveable = ismoveable;
    }
    public AIAttackGun(EntityLiving guerrilla, float range,float minrange, int spread, int bursttime, int burstcool, boolean ismoveable){
        this(guerrilla,range,minrange,spread,bursttime,ismoveable);
        this.burstcool = burstcool;
    }
    public AIAttackGun(EntityLiving guerrilla, float range,float minrange, int spread, int bursttime, int burstcool, boolean ismoveable,boolean assault){
        this(guerrilla,range,minrange,spread,bursttime,ismoveable);
        this.burstcool = burstcool;
        this.assault = assault;
    }
    public AIAttackGun(EntityLiving guerrilla, float range,float minrange, int spread, int bursttime, boolean ismoveable,boolean sac){
        this(guerrilla,range,minrange,spread,bursttime,ismoveable);
        shoot_avoid_checkline = sac;
    }
    @Override
    public boolean shouldExecute() {
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
            know_the_position_of_the_enemy = false;
            return false;
        } else {
            target = entityliving;
            return true;
        }
    }
    @Override
    public boolean continueExecuting() {
        return shouldExecute();
    }

    @Override
    public void resetTask() {
        burstingtime = 0;
    }
    @Override
    public void updateTask() {
        try {
            if (target != null) {
//                System.out.println("debug");
                double maxrange = maxshootrange * maxshootrange;
                double minrange = minshootrange * minshootrange;
                if(!(shooter.ridingEntity instanceof PlacedGunEntity) && !(shooter.getHeldItem() != null && shooter.getHeldItem().getItem() instanceof HMGItem_Unified_Guns)){
                    maxrange = 0;
                    minrange = 0;
                }
                double tolastseepoint = shooter.getDistanceSq(lastTargetX, lastTargetY + target.getEyeHeight(), lastTargetZ);
                double totargetdist = shooter.getDistanceSqToEntity(target);
                boolean see = shooter.getEntitySenses().canSee(target);
                boolean isnotblinded;
                boolean cansee = isnotblinded = !shooter.isPotionActive(Potion.blindness);
                cansee &= see;
                if(cansee) {
                    if (shooter instanceof IGVCmob)
                        cansee = ((IGVCmob) shooter).canSeeTarget(target);
                }
                if (ismoveable && !(cansee) && know_the_position_of_the_enemy) {
                    //敵の位置を把握していて、敵が隠れたなら追いかけにかかる
                    if(canNavigate())shooter.getNavigator().setPath(worldForPathfind.getEntityPathToXYZ(shooter, MathHelper.floor_double(lastTargetX), MathHelper.floor_double(lastTargetY), MathHelper.floor_double(lastTargetZ), 60, true, false, false, true), 1.0d);
                    if(tolastseepoint>4)if(know_the_position_of_the_enemy)shooter.getLookHelper().setLookPosition(lastTargetX, lastTargetY, lastTargetZ, 90F, 90F);
                } else if (!(target.riddenByEntity instanceof GVCEntityBox && target.isSneaking()) && cansee ) {
                    //敵がダンボールに隠れていなくて、視界内に居るなら
                    lastTargetX = target.posX;
                    lastTargetY = target.posY + target.getEyeHeight();
                    lastTargetZ = target.posZ;
                    know_the_position_of_the_enemy = true;
                    if(maxrange < totargetdist || (assault && assaultrange * assaultrange < totargetdist)) {
                        if(canNavigate())shooter.getNavigator().setPath(worldForPathfind.getEntityPathToXYZ(shooter, MathHelper.floor_double(target.posX), MathHelper.floor_double(target.posY), MathHelper.floor_double(target.posZ), 60f, true, false, false, true), 1.0d);
                    } else if(minrange > totargetdist){
                        if(canNavigate())shooter.getNavigator().setPath(worldForPathfind.getEntityPathToXYZ(shooter, MathHelper.floor_double(target.posX), MathHelper.floor_double(target.posY), MathHelper.floor_double(target.posZ), 60f, true, false, false, true), -1.0d);
                    }else {
                        shooter.getNavigator().clearPathEntity();
                    }
                    shooter.getLookHelper().setLookPositionWithEntity(target, 90F, 90F);
                }
                if(!cansee){
                    shooter.getLookHelper().onUpdateLook();
                    changesearchdircool--;
                    if(changesearchdircool<0){
                        searchdirY = rnd.nextBoolean();
                        searchdirP = rnd.nextBoolean();
                        changesearchdircool = rnd.nextInt(10)*50;
                    }
                    float f1;
                    float f2;
                    float f3;
                    float f4;
                    f1 = MathHelper.cos(-(shooter.rotationYawHead + (searchdirY?8:-8)) * 0.017453292F - (float)Math.PI);
                    f2 = MathHelper.sin(-(shooter.rotationYawHead + (searchdirY?8:-8)) * 0.017453292F - (float)Math.PI);
                    f3 = -MathHelper.cos(-(shooter.rotationPitch + (searchdirP?8:-8)) * 0.017453292F);
                    f4 = MathHelper.sin(-(shooter.rotationPitch + (searchdirP?8:-8)) * 0.017453292F);
                    Vec3 look = Vec3.createVectorHelper((double)(f2 * f3), (double)f4, (double)(f1 * f3));
                    shooter.getLookHelper().setLookPosition(shooter.posX + look.xCoord, shooter.posY + shooter.getEyeHeight() + look.yCoord, shooter.posZ + look.zCoord, 90F, 90F);
                }
                refindpath-=10;
                if (!cansee) {
                    aiming = 0;
                } else {
                    aiming++;
                }
                if (maxrange>totargetdist && know_the_position_of_the_enemy && isnotblinded && ((cansee && aiming > 40) || (!cansee && Warningcoolcnt < 0) && shooter.getDistanceSq(lastTargetX, lastTargetY + target.getEyeHeight(), lastTargetZ)>16) && burstingtime >0 && retriggerCool <= 0) {

                    if (shooter.getHeldItem() != null) {
                        if (shooter.getHeldItem().getItem() instanceof HMGItem_Unified_Guns) {
                            shooter.getHeldItem().getTagCompound().setBoolean("IsTriggered", true);
                            shooter.getEntityData().setBoolean("HMGisUsingItem", true);
                            if (!isSelectorChecked) {
                                List<Integer> burst = ((HMGItem_Unified_Guns) shooter.getHeldItem().getItem()).burstcount;
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
                                    shooter.getHeldItem().getTagCompound().setInteger("HMGMode", mode);
                                isSelectorChecked = true;
                            }
                            if (((HMGItem_Unified_Guns) shooter.getHeldItem().getItem()).getburstCount(shooter.getHeldItem().getTagCompound().getInteger("HMGMode")) != -1) {
                                retriggerCool = rnd.nextInt((int) (abs(((HMGItem_Unified_Guns) shooter.getHeldItem().getItem()).recoil)+1) * 10);
                            }
                        }
                    }
                    if (shooter.ridingEntity instanceof PlacedGunEntity) {
                        ItemStack gunstack = ((PlacedGunEntity) shooter.ridingEntity).gunStack;
                        if (gunstack.getItem() instanceof HMGItem_Unified_Guns) {
                            gunstack.getTagCompound().setBoolean("IsTriggered", true);
                            shooter.getEntityData().setBoolean("HMGisUsingItem", true);
                            if (!isSelectorChecked) {
                                List<Integer> burst = ((HMGItem_Unified_Guns) gunstack.getItem()).burstcount;
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
                                    gunstack.getTagCompound().setInteger("HMGMode", mode);
                                isSelectorChecked = true;
                            }
                            if (((HMGItem_Unified_Guns) gunstack.getItem()).getburstCount(gunstack.getTagCompound().getInteger("HMGMode")) != -1) {
                                retriggerCool = rnd.nextInt((int) (abs(((HMGItem_Unified_Guns) gunstack.getItem()).recoil)+1) * 10);
                            }
                        }
                    }
                    forget = 0;
                }
                burstingtime--;
                if (!cansee) {
                    forget++;
                    if(shooter instanceof IflagBattler && ((IflagBattler) shooter).istargetingflag()){
                        forget+=100;
                        Vec3 flagpos = ((IflagBattler) shooter).getflagposition();
                        if(shooter.getDistanceSq((int)flagpos.xCoord, (int) flagpos.yCoord, (int) flagpos.zCoord)>10 && (shooter.onGround || shooter.isWet())) if(canNavigate())shooter.getNavigator().setPath(worldForPathfind.getEntityPathToXYZ(shooter, (int)flagpos.xCoord, (int) flagpos.yCoord, (int) flagpos.zCoord, 120, true, false, false, true), 1.0d);
                    }
                }
                if (burstingtime < 0) {
                    if(burstcoolcnt<0) {
                        burstingtime = rnd.nextInt(bursttime);
                        burstcoolcnt = burstcool;
                    }else {
                        burstcoolcnt--;
                    }
                }
                if(!cansee && Warningcoolcnt < -20){
                    Warningcoolcnt = rnd.nextInt(Warningblank) + 80;
                }
                Warningcoolcnt--;
                retriggerCool--;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    private boolean canNavigate()
    {
        return this.shooter.onGround || shooter.isInWater() || this.shooter.isRiding() && this.shooter.ridingEntity instanceof EntityChicken;
    }
}
