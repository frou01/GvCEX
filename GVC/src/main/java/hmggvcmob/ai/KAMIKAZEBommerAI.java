package hmggvcmob.ai;

import hmggvcmob.entity.guerrilla.GVCEntityGuerrillaBM;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.monster.EntityCreeper;

public class KAMIKAZEBommerAI extends EntityAIBase
{
	/** The creeper that is swelling. */
	GVCEntityGuerrillaBM swellingCreeper;
	/** The creeper's attack target. This is used for the changing of the creeper's state. */
	EntityLivingBase creeperAttackTarget;
	private static final String __OBFID = "CL_00001614";

	public KAMIKAZEBommerAI(GVCEntityGuerrillaBM p_i1655_1_)
	{
		this.swellingCreeper = p_i1655_1_;
		this.setMutexBits(1);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	public boolean shouldExecute()
	{
		EntityLivingBase entitylivingbase = this.swellingCreeper.getAttackTarget();
		return this.swellingCreeper.getCreeperState() > 0 || entitylivingbase != null && swellingCreeper.getDistanceSqToEntity(entitylivingbase)<256 && this.swellingCreeper.getDistanceSqToEntity(entitylivingbase) < 9.0D;
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void startExecuting()
	{
		this.swellingCreeper.getNavigator().clearPathEntity();
		this.creeperAttackTarget = this.swellingCreeper.getAttackTarget();
	}

	/**
	 * Resets the task
	 */
	public void resetTask()
	{
		this.creeperAttackTarget = null;
	}

	/**
	 * Updates the task
	 */
	public void updateTask()
	{
		if (this.creeperAttackTarget == null)
		{
			this.swellingCreeper.setCreeperState(-1);
		}
		else if (this.swellingCreeper.getDistanceSqToEntity(this.creeperAttackTarget) > 49.0D)
		{
			this.swellingCreeper.setCreeperState(-1);
		}
		else if (!this.swellingCreeper.getEntitySenses().canSee(this.creeperAttackTarget))
		{
			this.swellingCreeper.setCreeperState(-1);
		}
		else
		{
			this.swellingCreeper.setCreeperState(1);
		}
	}
}
