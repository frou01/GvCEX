package hmggvcmob.ai;

import handmadevehicle.SlowPathFinder.ModifiedPathNavigater;
import handmadevehicle.SlowPathFinder.WorldForPathfind;
import handmadevehicle.entity.EntityVehicle;
import handmadevehicle.entity.parts.logics.BaseLogic;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;

import static handmadevehicle.Utils.transformVecByQuat;

public class AIDriveTank extends EntityAIBase {
	public EntityVehicle vehicle;
	public boolean Limited_turning_turret = false;//車両設定と同期させる　どうやって検出しよう。手動でいいかな
	public boolean combatSituation = false;
	public EntityLiving driverBody;

	public WorldForPathfind worldForPathfind;


	public AIDriveTank(EntityLiving driverBody,EntityVehicle entityVehicle,WorldForPathfind worldForPathfind){
		this.driverBody = driverBody;
		this.vehicle = entityVehicle;
		this.worldForPathfind = worldForPathfind;
	}

	@Override
	public boolean shouldExecute() {
		if(vehicle != null && vehicle.getBaseLogic().isRidingEntity(driverBody)){
			return true;
		}else {
			vehicle = null;
		}
		return false;
	}
	
	
	@Override
	public void resetTask() {
		if(vehicle != null){
			vehicle.getNavigator().clearPathEntity();
			BaseLogic baseLogic = vehicle.getBaseLogic();
			baseLogic.setControl_throttle_down(false);
			baseLogic.setControl_throttle_up(false);
			baseLogic.setControl_yaw_Right(false);
			baseLogic.setControl_yaw_Left(false);
			baseLogic.setControl_Space(false);
			baseLogic.setControl_brake(false);
		}
		combatSituation = false;
	}
	@Override
	public void updateTask() {

		//todo こっちで戦車を動かす。
		//射撃とかはアタックで
		//パスを設定するときは必ず車両側に
		if(vehicle.getNavigator().getPath() == null && driverBody.getNavigator().getPath() != null){
			vehicle.getNavigator().setPath(worldForPathfind.getEntityPathToXYZ(vehicle,
					MathHelper.floor_double(driverBody.getNavigator().getPath().getFinalPathPoint().xCoord),
					MathHelper.floor_double(driverBody.getNavigator().getPath().getFinalPathPoint().yCoord),
					MathHelper.floor_double(driverBody.getNavigator().getPath().getFinalPathPoint().zCoord),
					60, false, false, false, false),
					driverBody.getNavigator() instanceof ModifiedPathNavigater?((ModifiedPathNavigater) driverBody.getNavigator()).getSpeed():1);
		}
		if(vehicle.getBaseLogic().ispilot(driverBody) && !driverBody.worldObj.isRemote) {
			BaseLogic baseLogic = vehicle.getBaseLogic();
			double[] nextPos = nextPosition();
			baseLogic.setControl_throttle_down(false);
			baseLogic.setControl_throttle_up(false);
			baseLogic.setControl_yaw_Right(false);
			baseLogic.setControl_yaw_Left(false);
			baseLogic.setControl_Space(false);
			baseLogic.setControl_brake(false);
			if (nextPos != null) {
				Vector3d vecToNextPos = new Vector3d(nextPos);
				vecToNextPos.sub(new Vector3d(vehicle.posX, vehicle.posY, vehicle.posZ));//相対位置
				vecToNextPos.z *= -1;
				double dist = vecToNextPos.lengthSquared();
				Quat4d invertBodyRot = new Quat4d();
				invertBodyRot.inverse(baseLogic.bodyRot);
				vecToNextPos = transformVecByQuat(vecToNextPos, invertBodyRot);
				vecToNextPos.normalize();
				vecToNextPos.scale(vehicle.getNavigator().getSpeed());
				if (vecToNextPos.z < 0 || Limited_turning_turret || combatSituation) {
					if (vecToNextPos.x < -0.1) {
						baseLogic.setControl_yaw_Right(true);
					}
					else if (vecToNextPos.x > 0.1) {
						baseLogic.setControl_yaw_Left(true);
					}
					if(dist > 16) {
						baseLogic.setControl_throttle_up(true);
					}else if(baseLogic.throttle > baseLogic.prefab_vehicle.throttle_Max/10){
						baseLogic.setControl_throttle_down(true);
					}
				} else {
					if (vecToNextPos.x < -0.1) {
						baseLogic.setControl_yaw_Left(true);
					}
					else if (vecToNextPos.x > 0.1) {
						baseLogic.setControl_yaw_Right(true);
					}
					if(dist > 16) {
						baseLogic.setControl_throttle_down(true);
					}else if(-baseLogic.throttle > baseLogic.prefab_vehicle.throttle_min/10){
						baseLogic.setControl_throttle_up(true);
					}
//					if (vecToNextPos.x < -1) {
//						baseLogic.setControl_yaw_Right(true);
//						baseLogic.setControl_brake(true);
//					}
//					else if (vecToNextPos.x > 1) {
//						baseLogic.setControl_yaw_Left(true);
//						baseLogic.setControl_brake(true);
//					}
//					else
//						baseLogic.setControl_throttle_down(true);
				}
			} else {
				baseLogic.setControl_Space(true);
				baseLogic.setControl_brake(true);
			}
		}
	}
	public double[] nextPosition(){//次に移動すべき場所を返す
		PathEntity currentPath = vehicle.getNavigator().getPath();
		if(currentPath != null && !currentPath.isFinished()){
			Vec3 nextPos = currentPath.getPosition(vehicle);
			return new double[]{nextPos.xCoord,nextPos.yCoord,nextPos.zCoord};
		}
		return null;
	}
}
