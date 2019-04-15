package hmggvcmob.ai;

import hmggvcmob.SlowPathFinder.WorldForPathfind;
import hmggvcmob.entity.IRideableTank;
import hmggvcmob.entity.ITank;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;

import javax.vecmath.Vector3d;
import java.util.Random;

public class AITankAttack extends EntityAIBase {
    private EntityLiving Tank_body;//戦車
    private handmadeguns.entity.IFF IFF;//戦車
    private ITank Tank_SPdata;
    private int attackTime = 0;
    public WorldForPathfind worldForPathfind;
    private EntityLivingBase target;//ターゲット
    private Random rnd;
    private int forget = 0;//忘れるまで
    private boolean fEnable = true;
    private int aimcnt;
    private float maxrenge;
    private float minrenge;
    private boolean dir;
    private boolean movearound = false;
    private boolean always_poit_to_target = false;
    private boolean always_movearound = false;

    public boolean noLineCheck_subfire = true;

    private int mgBurstRoundMax = 40;
    private int mgBurstRound;
    private int mgBurstRoundCnt;
    private int mgBurstCoolMax = 40;
    private int mgBurstCool;
    private int mgBurstCoolCnt;
    private float mgmaxrange;

    private double lastTargetX;
    private double lastTargetY;
    private double lastTargetZ;


    public AITankAttack(EntityLiving guerrilla,float maxrenge,float minrenge){
        this.Tank_body = guerrilla;
        IFF = (handmadeguns.entity.IFF) guerrilla;
        if(guerrilla instanceof ITank)
            this.Tank_SPdata = (ITank) guerrilla;
        rnd = new Random();
        this.maxrenge = maxrenge;
        this.minrenge = minrenge;
        worldForPathfind = new WorldForPathfind(guerrilla.worldObj);
    }

    public AITankAttack(EntityLiving guerrilla,float maxrenge,float minrenge,int mgBurstRoundMax,int mgBurstCoolMax){
        this(guerrilla,maxrenge,minrenge);
        this.mgBurstRoundMax = mgBurstRoundMax;
        this.mgBurstCoolMax = mgBurstCoolMax;
    }
    @Override
    public boolean shouldExecute() {
        attackTime--;
        EntityLivingBase entityliving = Tank_body.getAttackTarget();
        boolean ismanual = (Tank_body instanceof IRideableTank) && !((IRideableTank) Tank_body).standalone();
        if (ismanual || !fEnable || entityliving == null || entityliving.isDead||forget > 1200) {
            target = null;
            Tank_body.setAttackTarget(null);
            forget = 0;
//            System.out.println("debug");
            return false;
        } else {
            target = entityliving;
            return true;
        }
    }
    public void resetTask() {
        Tank_body.setAttackTarget(null);
    }

    @Override
    public void updateTask() {

        Tank_body.getNavigator().clearPathEntity();
        double dist = Tank_body.getDistanceSqToEntity(target);
        if(always_poit_to_target || !movearound && !always_movearound) {
            if (dist > maxrenge || !Tank_body.getEntitySenses().canSee(target)) {
                Tank_body.getNavigator().setPath(worldForPathfind.getEntityPathToXYZ(Tank_body, (int) target.posX, (int) target.posY, (int) target.posZ, 80f, true, false, false, true), 1.2);
            } else if (dist < minrenge) {
                Tank_body.getNavigator().setPath(worldForPathfind.getEntityPathToXYZ(Tank_body, (int) target.posX, (int) target.posY, (int) target.posZ, 80f, true, false, false, true), -1);
            }
        }else {
            {
                Vector3d courseVec = new Vector3d(target.posX,target.posY,target.posZ);
                courseVec.sub(new Vector3d(Tank_body.posX, Tank_body.posY, Tank_body.posZ));
                courseVec.normalize();
                courseVec.scale(10);
                if(dist > maxrenge){
                
                }else if (dist < minrenge){
                    Vector3d backup = new Vector3d(courseVec);
                    if(dir){
                        double tempx = courseVec.x;
                        courseVec.x = courseVec.z;
                        courseVec.z = -tempx;
                    }else {
                        double tempx = courseVec.x;
                        courseVec.x = -courseVec.z;
                        courseVec.z = tempx;
                    }
                    courseVec.add(backup);
                }
                else if(dir){
                    double tempx = courseVec.x;
                    courseVec.x = courseVec.z;
                    courseVec.z = -tempx;
                }else {
                    double tempx = courseVec.x;
                    courseVec.x = -courseVec.z;
                    courseVec.z = tempx;
                }
                Tank_body.getNavigator().setPath(worldForPathfind.getEntityPathToXYZ(Tank_body, (int) (Tank_body.posX + courseVec.x), (int) target.posY, (int) (Tank_body.posZ + courseVec.z), 80f, true, false, false, true), 1.2);
            }
        }
        if(rnd.nextInt(50) == 1){
            dir = rnd.nextBoolean();
            movearound = !movearound;
        }
        if(Tank_body.getEntitySenses().canSee(target)){
            lastTargetX = target.posX;
            lastTargetY = target.posY + target.getEyeHeight();
            lastTargetZ = target.posZ;
        }

//        List list = Tank_body.worldObj.getEntitiesWithinAABBExcludingEntity(Tank_body, Tank_body.boundingBox.expand(0.20000000298023224D, 0.0D, 0.20000000298023224D));
//        if (list != null && !list.isEmpty()) {
//            for (int i = 0; i < list.size(); ++i) {
//                Entity colliedentity = (Entity) list.get(i);
//                colliedentity.attackEntityFrom(DamageSource.causeMobDamage(Tank_body),2);
//            }
//        }
//        System.out.println("debug");
        boolean aimed = Tank_body.getEntitySenses().canSee(target) ? Tank_SPdata.getBaseLogic().aimMainTurret_toTarget(target):Tank_SPdata.getBaseLogic().aimMainTurret_toPos(lastTargetX,lastTargetY,lastTargetZ);
        if(mgBurstRoundCnt < mgBurstRound && (mgmaxrange == -1 || dist < mgmaxrange)){
            if(Tank_body.getEntitySenses().canSee(target) || noLineCheck_subfire)Tank_SPdata.subFire(target);
            mgBurstRoundCnt++;
        }else {
            if(mgBurstCoolCnt > mgBurstCool){
                mgBurstCool = rnd.nextInt(mgBurstCoolMax);
                mgBurstRound = rnd.nextInt(mgBurstRoundMax);
                mgBurstCoolCnt= 0;
                mgBurstRoundCnt = 0;
            }
            mgBurstCoolCnt++;
        }
        if(aimed) {
            aimcnt++;
            if (aimcnt > 30) {
                Tank_SPdata.mainFire(target);
            }
        }else {
            aimcnt = 0;
        }
    }
    
    public void setMinrenge(float minrenge) {
        this.minrenge = minrenge;
    }
    
    public void setMaxrenge(float maxrenge) {
        this.maxrenge = maxrenge;
    }
    
    public void setMgmaxrange(float mgmaxrange) {
        this.mgmaxrange = mgmaxrange;
    }
    
    public void setEnable(boolean Value){
        fEnable = Value;
    }
    public void setAlways_movearound(boolean value){
        always_movearound = value;
    }
    public void setAlways_poit_to_target(boolean value){
        always_poit_to_target = value;
    }
}
