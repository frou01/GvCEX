package hmggvcmob.ai;

import handmadevehicle.SlowPathFinder.WorldForPathfind;
import hmggvcmob.entity.IIRVING;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.Vec3;

import java.util.Random;

public class AIGKHighJump extends EntityAIBase {
    private EntityLiving IRVING_body;//月光
    private handmadeguns.entity.IFF IFF;//月光
    private IIRVING IRVING_SPdata;
    public WorldForPathfind worldForPathfind;
    private EntityLivingBase target;//ターゲット
    private float range;//射程
    private int cool;
    private int forget = 0;//忘れるまで
    private Random rnd;
    private boolean fEnable = true;
    public AIGKHighJump(EntityLiving guerrilla, IIRVING iirving, float range,WorldForPathfind worldForPathfind){
        this.IRVING_body = guerrilla;
        IFF = (handmadeguns.entity.IFF) guerrilla;
        this.IRVING_SPdata = iirving;
        this.range = range;
        rnd = new Random();
        this.worldForPathfind = worldForPathfind;
    }
    public boolean shouldExecute() {
        EntityLivingBase entityliving = IRVING_body.getAttackTarget();
        if(IFF.is_this_entity_friend(entityliving)){
            entityliving = null;
        }
        if (!fEnable || entityliving == null || entityliving.isDead||forget > 1200|| IRVING_SPdata.isstaning()) {
            target = null;
            forget = 0;
            cool = 10 + rnd.nextInt(10);
            return false;
        } else if(cool<0){
            target = entityliving;
            cool = 10 + rnd.nextInt(10);
            return true;
        }else {
            cool--;
            return false;
        }
    }
    @Override
    public void updateTask() {
        IRVING_body.getLookHelper().setLookPositionWithEntity(target,90,90);
        Vec3 lookvec = IRVING_body.getLookVec();
        int dir = rnd.nextInt(4);
        boolean high = rnd.nextBoolean();
        switch (dir){
            case 0:
                IRVING_body.motionX = lookvec.xCoord;
                IRVING_body.motionZ = lookvec.zCoord;
                break;
            case 1:
                IRVING_body.motionX = -lookvec.zCoord;
                IRVING_body.motionZ = lookvec.xCoord;
                break;
            case 2:
                IRVING_body.motionX = lookvec.zCoord;
                IRVING_body.motionZ = -lookvec.xCoord;
                break;
            case 3:
                IRVING_body.motionX = -lookvec.xCoord;
                IRVING_body.motionZ = -lookvec.zCoord;
                break;
        }
        if(high)
            IRVING_body.motionY =lookvec.yCoord + 0.5;
        else
            IRVING_body.motionY =lookvec.yCoord + 1.5;
    }
}
