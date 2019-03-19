package hmggvcmob.ai;

import handmadeguns.entity.IFF;
import hmggvcmob.entity.friend.EntitySoBases;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;

import java.util.Iterator;
import java.util.List;

import static hmggvcmob.GVCMobPlus.ignoreSoTargetEntity;

public class AIHurtByTarget extends EntityAITarget
{
    boolean entityCallsForHelp;
    private int field_142052_b;
    private static final String __OBFID = "CL_00001619";

    public AIHurtByTarget(EntityCreature p_i1660_1_, boolean p_i1660_2_)
    {
        super(p_i1660_1_, false);
        this.entityCallsForHelp = p_i1660_2_;
        this.setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        int i = this.taskOwner.func_142015_aE();
        return i != this.field_142052_b && this.isSuitableTarget(this.taskOwner.getAITarget(), false);
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.field_142052_b = this.taskOwner.func_142015_aE();


        if(!this.taskOwner.isPotionActive(Potion.blindness) && (!(this.taskOwner instanceof IFF)|| !((IFF) this.taskOwner).is_this_entity_friend(this.taskOwner.getAITarget())) && (!(this.taskOwner instanceof EntitySoBases)|| !ignoreSoTargetEntity.containsKey(EntityList.getEntityString(this.taskOwner.getAITarget())))) {
            if (this.entityCallsForHelp)
            {
                double d0 = 16;
                List list = this.taskOwner.worldObj.getEntitiesWithinAABB(this.taskOwner.getClass(), AxisAlignedBB.getBoundingBox(this.taskOwner.posX, this.taskOwner.posY, this.taskOwner.posZ, this.taskOwner.posX + 1.0D, this.taskOwner.posY + 1.0D, this.taskOwner.posZ + 1.0D).expand(d0, 10.0D, d0));
                Iterator iterator = list.iterator();

                while (iterator.hasNext()) {
                    EntityCreature entitycreature = (EntityCreature) iterator.next();

                    if (this.taskOwner != entitycreature && entitycreature.getAttackTarget() == null && !entitycreature.isOnSameTeam(this.taskOwner.getAITarget())) {
                        entitycreature.setAttackTarget(this.taskOwner.getAITarget());
                    }
                }
                this.taskOwner.setAttackTarget(this.taskOwner.getAITarget());
            }
        }

        super.startExecuting();
    }
}