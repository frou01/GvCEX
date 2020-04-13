package hmggvcmob.ai;

import hmggvcmob.block.GVCBlockFlag;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Blocks;

public class AIBreakBlock extends EntityAIBase
{
    private int breakingTime;
    private int field_75358_j = -1;
    private EntityLiving theEntity;
    protected Block field_151504_e;
    public int mode = 0;
    public int[] targetBlockpos = new int[3];
    private static final String __OBFID = "CL_00001577";

    public AIBreakBlock(EntityLiving p_i1618_1_)
    {
        theEntity = p_i1618_1_;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if((theEntity.getAttackTarget() != null && !theEntity.getEntitySenses().canSee(theEntity.getAttackTarget()))){
            targetBlockpos = new int[]{
                    (int)(theEntity.posX + theEntity.getLookVec().xCoord),
                    (int)(theEntity.posY + 1 + theEntity.getLookVec().yCoord),
                    (int)(theEntity.posZ + theEntity.getLookVec().zCoord)};
            this.field_151504_e = this.func_151503_a(targetBlockpos[0],targetBlockpos[1],targetBlockpos[2]);
            if(field_151504_e == null || field_151504_e == Blocks.air){
                mode = 1;
                targetBlockpos = new int[]{
                        (int)(theEntity.posX + theEntity.getLookVec().xCoord),
                        (int)(theEntity.posY + theEntity.getLookVec().yCoord),
                        (int)(theEntity.posZ + theEntity.getLookVec().zCoord)};
                this.field_151504_e = this.func_151503_a(targetBlockpos[0],targetBlockpos[1],targetBlockpos[2]);
            }
            if(field_151504_e == null || field_151504_e == Blocks.air){
                mode = 1;
                targetBlockpos = new int[]{
                        (int)(theEntity.posX + theEntity.getLookVec().xCoord),
                        (int)(theEntity.posY + theEntity.getLookVec().yCoord),
                        (int)(theEntity.posZ + theEntity.getLookVec().zCoord)};
                this.field_151504_e = this.func_151503_a(targetBlockpos[0],targetBlockpos[1] + 2,targetBlockpos[2]);
            }
            if(field_151504_e == null || field_151504_e == Blocks.air){
                mode = 1;
                targetBlockpos = new int[]{
                        (int)(theEntity.posX + theEntity.getLookVec().xCoord),
                        (int)(theEntity.posY + theEntity.getLookVec().yCoord),
                        (int)(theEntity.posZ + theEntity.getLookVec().zCoord)};
                this.field_151504_e = this.func_151503_a(targetBlockpos[0],targetBlockpos[1] - 1,targetBlockpos[2]);
            }
            return this.field_151504_e != null;
        }
        return false;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        super.startExecuting();
        this.breakingTime = 0;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return this.breakingTime <= 240 && theEntity.getDistanceSq(targetBlockpos[0] + 0.5,targetBlockpos[1] + 0.5,targetBlockpos[2] + 0.5)<9;
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        super.resetTask();
        this.theEntity.worldObj.destroyBlockInWorldPartially(this.theEntity.getEntityId(),targetBlockpos[0],targetBlockpos[1],targetBlockpos[2], -1);
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {

//        System.out.println("debug");
        if (this.theEntity.getRNG().nextInt(20) == 0)
        {
            this.theEntity.worldObj.playAuxSFX(1010, targetBlockpos[0],targetBlockpos[1],targetBlockpos[2], 0);
        }

        ++this.breakingTime;
        int i = (int)((float)this.breakingTime / 240.0F * 10.0F);

        if (i != this.field_75358_j)
        {
            this.theEntity.worldObj.destroyBlockInWorldPartially(this.theEntity.getEntityId(), targetBlockpos[0],targetBlockpos[1],targetBlockpos[2], i);
            this.field_75358_j = i;
        }

        if (this.breakingTime == field_151504_e.getBlockHardness(theEntity.worldObj,targetBlockpos[0],targetBlockpos[1],targetBlockpos[2])*80)
        {
            this.theEntity.worldObj.setBlockToAir(targetBlockpos[0],targetBlockpos[1],targetBlockpos[2]);
            this.theEntity.worldObj.playAuxSFX(1012, targetBlockpos[0],targetBlockpos[1],targetBlockpos[2], 0);
            this.theEntity.worldObj.playAuxSFX(2001, targetBlockpos[0],targetBlockpos[1],targetBlockpos[2], Block.getIdFromBlock(this.field_151504_e));
        }
    }
    private Block func_151503_a(int p_151503_1_, int p_151503_2_, int p_151503_3_)
    {
        Block block = this.theEntity.worldObj.getBlock(p_151503_1_, p_151503_2_, p_151503_3_);
        return (block != Blocks.air || block instanceof GVCBlockFlag) ? block:null;
    }
}
