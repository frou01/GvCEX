package hmgww2.entity.ai;

import handmadevehicle.entity.parts.ITank;
import handmadevehicle.entity.parts.logics.TankBaseLogicLogic;
import hmgww2.Nation;
import hmgww2.blocks.tile.TileEntityBase;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import static java.lang.Math.atan2;
import static java.lang.Math.toDegrees;

public class AITankBombEnemyFlag extends EntityAIBase {
	private EntityLiving tank_body;//戦車
	private handmadeguns.entity.IFF IFF;//戦車
	private ITank tank_SPdata;
	private int attackTime = 0;
	private World worldObj;
	private Nation nation;

	public AITankBombEnemyFlag(EntityLiving tank) {
		this.tank_body = tank;
		IFF = (handmadeguns.entity.IFF) tank;
		if (tank instanceof ITank)
			this.tank_SPdata = (ITank) tank;
		worldObj = tank_body.worldObj;
	}

	@Override
	public boolean shouldExecute() {
		if (tank_body.getAttackTarget() == null && tank_SPdata.standalone()) {
			TileEntity tileEntity = worldObj.getTileEntity((int) tank_SPdata.getTargetpos()[0], (int) tank_SPdata.getTargetpos()[1], (int) tank_SPdata.getTargetpos()[2]);
			if (tileEntity instanceof TileEntityBase && ((TileEntityBase) tileEntity).nation != this.nation) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void updateTask() {
		tank_body.getNavigator().clearPathEntity();
		tank_body.rotationYaw = (float) -toDegrees(atan2(tank_SPdata.getTargetpos()[0] - tank_body.posX, tank_SPdata.getTargetpos()[2] - tank_body.posZ));
		if (tank_SPdata.getMainTurret() != null && ((TankBaseLogicLogic) tank_SPdata.getBaseLogic()).aimMainTurret_toPos(tank_SPdata.getTargetpos()[0], tank_SPdata.getTargetpos()[1], tank_SPdata.getTargetpos()[2])) {
			tank_SPdata.getMainTurret().fireall();
		}
	}
}
