package hmggvcutil.entity.ai;

import handmadeguns.items.guns.HMGItem_Unified_Guns;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;

import java.util.List;
import java.util.Random;

import static java.lang.Math.abs;

public class AIAttackGun extends EntityAIBase {
    private EntityLiving shooter;//ゲリラさん
    private EntityLivingBase target;//ターゲット
    private int forget;
    private int aimtimer;
    public int burstcool = 20;//連射の間隔
    public int retriggerCool;
    private boolean isSelectorChecked = false;
    private Random rnd;
    //used by Guerrilla
    public AIAttackGun(EntityLiving guerrilla,float range,int spread,int bursttime){
        this.shooter = guerrilla;
        this.burstcool = bursttime*5;
        rnd = new Random();
    }
    public AIAttackGun(EntityLiving guerrilla, float range, int spread, int bursttime, boolean ismoveable){
        this(guerrilla,range,spread,bursttime);
    }
    public AIAttackGun(EntityLiving guerrilla, float range, int spread, int bursttime, int burstcool, boolean ismoveable){
        this(guerrilla,range,spread,bursttime,ismoveable);
        this.burstcool = burstcool;
    }
    public AIAttackGun(EntityLiving guerrilla, float range, int spread, int bursttime, boolean ismoveable,boolean sac){
        this(guerrilla,range,spread,bursttime,ismoveable);
    }
    @Override
    public boolean shouldExecute() {
        EntityLivingBase entityliving = shooter.getAttackTarget();
        if (entityliving == null || entityliving.isDead || forget>300) {
            shooter.setAttackTarget(null);
            shooter.getEntityData().setBoolean("HMGisUsingItem", false);
            if(shooter.getHeldItem() != null && shooter.getHeldItem().getTagCompound() != null)shooter.getHeldItem().getTagCompound().setBoolean("IsTriggered", false);
            forget = 0;
            aimtimer = 0;
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
        forget = 0;
        aimtimer= 0;
        shooter.getEntityData().setBoolean("HMGisUsingItem", false);
        if(shooter.getHeldItem() != null && shooter.getHeldItem().getTagCompound() != null)shooter.getHeldItem().getTagCompound().setBoolean("IsTriggered", false);
    }
    @Override
    public void updateTask() {
        try {
            shooter.getEntityData().setBoolean("HMGisUsingItem", false);
            if (target != null) {
                boolean see = shooter.getEntitySenses().canSee(target);
                if (!see) {
                    shooter.setAttackTarget(null);
                    aimtimer = 0;
                } else {
                    aimtimer++;
                    forget ++ ;
                    shooter.getLookHelper().setLookPosition(target.posX, target.posY + target.getEyeHeight(), target.posZ, 90F, 90F);

                    if(aimtimer > 10) {
                        if (shooter.getHeldItem() != null) {
                            if (shooter.getHeldItem().getItem() instanceof HMGItem_Unified_Guns) {
                                if (shooter.getHeldItem().getTagCompound() != null)
                                    shooter.getHeldItem().getTagCompound().setBoolean("IsTriggered", true);
                                shooter.getEntityData().setBoolean("HMGisUsingItem", true);
                                if (!isSelectorChecked) {
                                    List<Integer> burst = ((HMGItem_Unified_Guns) shooter.getHeldItem().getItem()).gunInfo.burstcount;
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
                                        if (shooter.getHeldItem().getTagCompound() != null)
                                            shooter.getHeldItem().getTagCompound().setInteger("HMGMode", mode);
                                    isSelectorChecked = true;
                                }
                                if (((HMGItem_Unified_Guns) shooter.getHeldItem().getItem()).getburstCount(shooter.getHeldItem().getTagCompound().getInteger("HMGMode")) != -1) {
                                    retriggerCool = rnd.nextInt((int) (abs( ((HMGItem_Unified_Guns) shooter.getHeldItem().getItem()).gunInfo.recoil) + 1) * 10);
                                }
                            }
                        }
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
