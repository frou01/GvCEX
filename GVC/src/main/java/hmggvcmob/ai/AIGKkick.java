package hmggvcmob.ai;

import handmadeguns.entity.IFF;
import hmggvcmob.SlowPathFinder.WorldForPathfind;
import hmggvcmob.entity.IIRVING;
import hmggvcmob.entity.guerrilla.EntityGBases;
import hmggvcmob.entity.guerrilla.GVCEntityGK;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import org.lwjgl.Sys;

import java.util.Random;

public class AIGKkick extends EntityAIBase {
    private EntityLiving IRVING_body;//月光
    private handmadeguns.entity.IFF IFF;//月光
    private IIRVING IRVING_SPdata;
    public WorldForPathfind worldForPathfind;
    private EntityLivingBase target;//ターゲット
    private float range;//射程
    private int forget = 0;//忘れるまで
    private int kickprogeress;
    private Random rnd;
    private boolean fEnable = true;
    private boolean kicking = true;
    private boolean kicktype = true;
    public AIGKkick(EntityLiving guerrilla,IIRVING iirving,float range){
        this.IRVING_body = guerrilla;
        IFF = (handmadeguns.entity.IFF) guerrilla;
        this.IRVING_SPdata = iirving;
        this.range = range;
        rnd = new Random();
        worldForPathfind = new WorldForPathfind(guerrilla.worldObj);
    }
    public boolean shouldExecute() {//LMMから借りてきた
        EntityLivingBase entityliving = IRVING_body.getAttackTarget();
        if(IFF.is_this_entity_friend(entityliving)){
            entityliving = null;
        }
        if (!fEnable || entityliving == null || entityliving.isDead||forget > 1200|| IRVING_SPdata.isstaning()) {
            IRVING_SPdata.setcombattask_4(false);
            IRVING_SPdata.setkickprogeress(0);
            target = null;
            forget = 0;
            return false;
        } else if(kickprogeress>0||entityliving.getDistanceSqToEntity(IRVING_body)<range*range){
            target = entityliving;
            return true;
        }else {
            return false;
        }
    }
    @Override
    public void updateTask() {
        IRVING_body.getLookHelper().setLookPositionWithEntity(target,90,90);
        IRVING_body.getNavigator().clearPathEntity();
        if(kickprogeress == 0){
            kicktype = rnd.nextBoolean();
        }
        kickprogeress++;
        if(kickprogeress == 7){
            if(kicktype) {
                target.attackEntityFrom(DamageSource.causeMobDamage(IRVING_body), 11F);
                Vec3 looked = IRVING_body.getLookVec();
                IRVING_SPdata.setcombattask_4(true);
                target.motionX = looked.xCoord * 2;
                target.motionZ = looked.zCoord * 2;
                target.motionY += 1;
            }else {
                target.attackEntityFrom(DamageSource.causeMobDamage(IRVING_body), 11F);
                Vec3 looked = IRVING_body.getLookVec();
                IRVING_SPdata.setcombattask_4(true);
                target.motionX = looked.xCoord * 5;
                target.motionZ = looked.zCoord * 5;
                target.motionY += 0.3;
            }
        }
        if(kickprogeress > 10) {
            IRVING_SPdata.setcombattask_4(false);
            kickprogeress = 0;
        }
    }
}
