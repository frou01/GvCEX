package hmggvcmob.ai;

import handmadeguns.entity.PlacedGunEntity;
import handmadeguns.items.guns.HMGItem_Unified_Guns;
import hmggvcutil.entity.GVCEntityBox;
import handmadevehicle.SlowPathFinder.WorldForPathfind;
import hmggvcmob.entity.IGVCmob;
import hmggvcmob.entity.friend.EntitySoBases;
import hmggvcmob.entity.guerrilla.EntityGBases;
import handmadevehicle.entity.parts.Hasmode;
import handmadevehicle.entity.parts.ITank;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.init.Items;
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

    private boolean fEnable = true;
    //used by Guerrilla
    public AIAttackGun(EntityLiving guerrilla,float range,float minrange,int bursttimer,WorldForPathfind worldForPathfind){
        this.shooter = guerrilla;
        this.maxshootrange = range;
        this.minshootrange = minrange;
        this.bursttime = bursttimer;
        this.burstcool = bursttime*5;
        this.rnd = new Random();
        this.worldForPathfind = worldForPathfind;
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
            lastSeenPosition = null;
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
                if (shooter.ridingEntity == null && !(shooter.getHeldItem() != null && shooter.getHeldItem().getItem() instanceof HMGItem_Unified_Guns)) {
                    maxrange = 0;
                    minrange = 0;
                }
                Vector3d currentAttackToPosition = getSeeingPosition();
                if (currentAttackToPosition != null) {
                    double tocurrentAttackToPosition = shooter.getDistanceSq(currentAttackToPosition.x, currentAttackToPosition.y + target.getEyeHeight(), currentAttackToPosition.z);
                    boolean see = shooter.canEntityBeSeen(target);
                    boolean isnotblinded;
                    boolean canSee = isnotblinded = !shooter.isPotionActive(Potion.blindness);
                    canSee &= see;
                    if (canSee) {
                        shooter.getLookHelper().setLookPosition(currentAttackToPosition.x, currentAttackToPosition.y + target.getEyeHeight(), currentAttackToPosition.z, 1000000, 1000000);
                    }else {
                        changesearchdircool--;
                        if (changesearchdircool < 0) {
                            searchdirY = rnd.nextBoolean();
                            searchdirP = rnd.nextBoolean();
                            changesearchdircool = rnd.nextInt(10) * 50;
                        }
                        float f1;
                        float f2;
                        float f3;
                        float f4;
                        f1 = MathHelper.cos(-(shooter.rotationYawHead + (searchdirY ? 8 : -8)) * 0.017453292F - (float) Math.PI);
                        f2 = MathHelper.sin(-(shooter.rotationYawHead + (searchdirY ? 8 : -8)) * 0.017453292F - (float) Math.PI);
                        f3 = -MathHelper.cos(-(shooter.rotationPitch + (searchdirP ? 0 : -0)) * 0.017453292F);
                        f4 = MathHelper.sin(-(shooter.rotationPitch + (searchdirP ? 0 : -0)) * 0.017453292F);
                        Vec3 look = Vec3.createVectorHelper((double) (f2 * f3), (double) f4, (double) (f1 * f3));
                        shooter.getLookHelper().setLookPosition(shooter.posX + look.xCoord, shooter.posY + shooter.getEyeHeight() + look.yCoord, shooter.posZ + look.zCoord, 90F, 18000000);
                    }
                    if(refindpath < 0){
                        shooter.getNavigator().clearPathEntity();
                        if(!canSee || maxrange < tocurrentAttackToPosition)
                            shooter.getNavigator().setPath(worldForPathfind.getEntityPathToXYZ(shooter,MathHelper.floor_double(currentAttackToPosition.x),
                                    MathHelper.floor_double(currentAttackToPosition.y),
                                    MathHelper.floor_double(currentAttackToPosition.z),
                                    80,true,false,true,false),1);
                        else
                        if(minrange > tocurrentAttackToPosition)
                            shooter.getNavigator().setPath(worldForPathfind.getEntityPathToXYZ(shooter,MathHelper.floor_double(currentAttackToPosition.x),
                                    MathHelper.floor_double(currentAttackToPosition.y),
                                    MathHelper.floor_double(currentAttackToPosition.z),
                                    80,true,false,true,false),-1);
                        refindpath = 10;
                    }
//                System.out.println("debug PR" + shooter.rotationPitch);
//                shooter.getLookHelper().onUpdateLook();
//                System.out.println("debug AF" + shooter.rotationPitch);
                    refindpath -= 1;
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
    private boolean canNavigate()
    {
        return this.shooter.onGround || shooter.isInWater() || this.shooter.isRiding() && this.shooter.ridingEntity instanceof EntityChicken;
    }
    public Vector3d getSeeingPosition(){
        if(shooter.canEntityBeSeen(target)){
            if(lastSeenPosition == null)lastSeenPosition = new Vector3d();
            lastSeenPosition.set(target.posX,target.posY,target.posZ);
        }
        return lastSeenPosition;
    }
}
