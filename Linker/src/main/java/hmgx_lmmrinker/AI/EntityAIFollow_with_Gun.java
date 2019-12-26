package hmgx_lmmrinker.AI;

import handmadevehicle.SlowPathFinder.WorldForPathfind;
import littleMaidMobX.LMM_EntityLittleMaid;
import littleMaidMobX.LMM_IEntityAI;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.world.World;

public class EntityAIFollow_with_Gun extends EntityAIBase implements LMM_IEntityAI {
    private boolean enable;
    private LMM_EntityLittleMaid theMaid;
    private Entity theOwner;
    private World theWorld;
    private float moveSpeed;
    private PathNavigate petPathfinder;
    private WorldForPathfind worldForPathfind;
    private int field_48310_h;
    protected double maxDist;
    protected double minDist;
    protected double sprintDist;
    protected double toDistance;

    public EntityAIFollow_with_Gun(LMM_EntityLittleMaid par1EntityLittleMaid,float pSpeed,double pMin,double pMax,double pSprintDistSQ){
        theMaid = par1EntityLittleMaid;
        theWorld = par1EntityLittleMaid.worldObj;
        moveSpeed = pSpeed;
        petPathfinder = this.theMaid.getNavigator();
        worldForPathfind = new WorldForPathfind(theWorld);
        minDist = pMin;
        maxDist = pMax;
        sprintDist = pSprintDistSQ;
        enable = true;
        setMutexBits(3);
    }
    @Override
    public void setEnable(boolean pFlag) {
        enable = pFlag;
    }

    @Override
    public boolean getEnable() {
        return enable;
    }

    @Override
    public boolean shouldExecute() {
        if(!enable)
            return false;
        if(theMaid.isFreedom())
            return false;
        if(theMaid.getOwner() == null)
            return false;
        if(theMaid.getDistanceSqToEntity(theMaid.getOwner())>minDist && theMaid.getDistanceSqToEntity(theMaid.getOwner())<maxDist) {
            theOwner = theMaid.getOwner();
            this.toDistance = theMaid.getDistanceSqToEntity(theMaid.getOwner());
            return true;
        }
        return false;
    }
    public void resetTask() {
        if(worldForPathfind.slowPathfinder!=null)worldForPathfind.slowPathfinder.isserchingpath = false;
        theMaid.setSprinting(false);
        theOwner = null;
        petPathfinder.clearPathEntity();
    }
    public void updateTask(){
        theMaid.getLookHelper().setLookPositionWithEntity(theOwner, 10F,
                theMaid.getVerticalFaceSpeed());

        if (theMaid.isSitting()) {
            return;
        }
        // 指定距離以上ならダッシュ
        theMaid.setSprinting(toDistance > sprintDist);
        if (--field_48310_h > 0) {
            return;
        }
        theMaid.getSwingStatusDominant().clearItemInUse(theMaid);
        field_48310_h = 10;
        PathEntity setval = worldForPathfind.getEntityPathToXYZ(theMaid,(int)theOwner.posX,(int)theOwner.posY,(int)theOwner.posZ,120.0f,true,true,true,true);
        if(setval!=null)
            petPathfinder.setPath(setval,1.0);
//        petPathfinder.onUpdateNavigation();
    }
}
