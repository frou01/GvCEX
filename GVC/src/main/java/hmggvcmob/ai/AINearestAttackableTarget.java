package hmggvcmob.ai;

import hmggvcmob.entity.IRideableTank;
import hmggvcutil.entity.GVCEntityBox;
import hmggvcmob.entity.IGVCmob;
import hmggvcmob.entity.friend.EntitySoBases;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.util.Vec3;

import java.util.Collections;
import java.util.List;

import static hmggvcmob.GVCMobPlus.ignoreSoTargetEntity;
import static java.lang.Math.abs;

public class AINearestAttackableTarget extends EntityAITarget {
    private final Class targetClass;
    private final int targetChance;
    /** Instance of EntityAINearestAttackableTargetSorter. */
    private final EntityAINearestAttackableTarget.Sorter theNearestAttackableTargetSorter;
    /**
     * This filter is applied to the Entity search.  Only matching entities will be targetted.  (null -> no
     * restrictions)
     */
    private final IEntitySelector targetEntitySelector;
    private EntityLivingBase targetEntity;
    private static final String __OBFID = "CL_00001620";

    public AINearestAttackableTarget(EntityCreature p_i1663_1_, Class p_i1663_2_, int p_i1663_3_, boolean p_i1663_4_)
    {
        this(p_i1663_1_, p_i1663_2_, p_i1663_3_, p_i1663_4_, false);
    }

    public AINearestAttackableTarget(EntityCreature p_i1664_1_, Class p_i1664_2_, int p_i1664_3_, boolean p_i1664_4_, boolean p_i1664_5_)
    {
        this(p_i1664_1_, p_i1664_2_, p_i1664_3_, p_i1664_4_, p_i1664_5_, (IEntitySelector)null);
    }

    public AINearestAttackableTarget(EntityCreature p_i1665_1_, Class p_i1665_2_, int p_i1665_3_, boolean p_i1665_4_, boolean p_i1665_5_, final IEntitySelector p_i1665_6_)
    {
        super(p_i1665_1_, p_i1665_4_, p_i1665_5_);
        this.targetClass = p_i1665_2_;
        this.targetChance = p_i1665_3_;
        this.theNearestAttackableTargetSorter = new EntityAINearestAttackableTarget.Sorter(p_i1665_1_);
        this.setMutexBits(1);
        this.targetEntitySelector = new IEntitySelector()
        {
            private static final String __OBFID = "CL_00001621";
            /**
             * Return whether the specified entity is applicable to this filter.
             */
            public boolean isEntityApplicable(Entity p_82704_1_)
            {
                return !(p_82704_1_ instanceof EntityLivingBase) ? false : (p_i1665_6_ != null && !p_i1665_6_.isEntityApplicable(p_82704_1_) ? false : AINearestAttackableTarget.this.isSuitableTarget((EntityLivingBase)p_82704_1_, false));
            }
        };
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (((taskOwner instanceof IRideableTank) && !((IRideableTank) taskOwner).standalone()) || this.targetChance > 0 && taskOwner.getRNG().nextInt(this.targetChance) != 0 && this.taskOwner.getAttackTarget() != null)
        {
            return false;
        }
        else
        {
            double d0 = this.getTargetDistance();
            List list = this.taskOwner.worldObj.selectEntitiesWithinAABB(this.targetClass, this.taskOwner.boundingBox.expand(d0, d0, d0), this.targetEntitySelector);
            Collections.sort(list, this.theNearestAttackableTargetSorter);

            if (list.isEmpty())
            {
                return false;
            }
            else
            {
                Vec3 lookVec = taskOwner.getLookVec();
                if(taskOwner instanceof IGVCmob)
                    for(int i = 0;i < list.size();i++) {
                        this.targetEntity = (EntityLivingBase) list.get(i);
                        double dist = taskOwner.getDistanceToEntity(targetEntity);
                        boolean flag;

                        flag = ((IGVCmob) taskOwner).canSeeTarget(targetEntity);
                        if(taskOwner instanceof EntitySoBases){
                            flag &= !ignoreSoTargetEntity.containsKey(EntityList.getEntityString(targetEntity));
                        }
                        if(flag) {
                            if ((taskOwner.getEntitySenses().canSee(targetEntity) || ((IGVCmob) taskOwner).canhearsound(targetEntity)) && (targetEntity.riddenByEntity == null || !(targetEntity.riddenByEntity instanceof GVCEntityBox && targetEntity.distanceWalkedModified - targetEntity.prevDistanceWalkedModified < dist / 10)))
                                return true;
                        }
                    }
                return false;
            }
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        if(taskOwner instanceof IGVCmob){
            double dist = taskOwner.getDistanceToEntity(targetEntity);
            boolean flag;
            flag = ((IGVCmob) taskOwner).canSeeTarget(targetEntity) || ((IGVCmob) taskOwner).canhearsound(targetEntity);
            if(taskOwner instanceof EntitySoBases){
                flag &= !ignoreSoTargetEntity.containsKey(EntityList.getEntityString(targetEntity));
            }
            if(flag) {
                if (targetEntity != null && (targetEntity.riddenByEntity == null || !(targetEntity.riddenByEntity instanceof GVCEntityBox && targetEntity.distanceWalkedModified - targetEntity.prevDistanceWalkedModified < dist / 10))) {
                    this.taskOwner.setAttackTarget(this.targetEntity);
                    this.taskOwner.setTarget(this.targetEntity);
                }
            }
        }
        super.startExecuting();
    }
    @Override
    public void resetTask()
    {
    }
}
