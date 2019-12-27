package hmggvcmob.ai;

import handmadevehicle.SlowPathFinder.WorldForPathfind;
import handmadevehicle.entity.EntityDummy_rider;
import hmggvcmob.IflagBattler;
import hmggvcmob.entity.ICommandedEntity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.MathHelper;

import javax.vecmath.Vector3d;
import java.util.Random;

public class AICommandedEntity extends EntityAIBase {
	private EntityLivingBase leaderEntity;
	private EntityLiving commandedEntity;
	private WorldForPathfind worldForPathfind;
	private int state;
	private double[] targetPos;
	private int mode;
	@Override
	public boolean shouldExecute() {
		mode = ((ICommandedEntity)commandedEntity).getCommandState();
		if(mode == 1 && (leaderEntity = ((ICommandedEntity)commandedEntity).getLeaderPlayer()) != null){
			targetPos = new double[]{ leaderEntity.posX, leaderEntity.posY, leaderEntity.posZ};

			double meToTDist = commandedEntity.getDistance(targetPos[0], targetPos[1], targetPos[2]);

			if (meToTDist  < 2) {
				((ICommandedEntity)commandedEntity).setState((byte) 3);
			} else if (meToTDist > 16) {
				((ICommandedEntity)commandedEntity).setState((byte) 1);
			} else if (meToTDist > 10) {
				((ICommandedEntity)commandedEntity).setState((byte) 0);
			}

			state = ((ICommandedEntity) commandedEntity).getState();
			return true;
		}else if(mode == 0 && (leaderEntity = (EntityLivingBase) ((ICommandedEntity)commandedEntity).getPlatoonLeader()) != null){
			targetPos = ((IflagBattler)leaderEntity).getTargetpos();
			if(targetPos != null) {
				double meToTDist = commandedEntity.getDistance(targetPos[0], targetPos[1], targetPos[2]);
				double leaderToTDist = leaderEntity.getDistance(targetPos[0], targetPos[1], targetPos[2]);

				if (leaderEntity == commandedEntity) {
					((ICommandedEntity) commandedEntity).setState((byte) 2);
				} else if (leaderToTDist > 5) {
					if (meToTDist + 4 > leaderToTDist) {
						((ICommandedEntity) commandedEntity).setState((byte) 1);
					} else if (meToTDist + 10 < leaderToTDist) {
						((ICommandedEntity) commandedEntity).setState((byte) 0);
					}
				} else {
					if (meToTDist < 5) {
						((ICommandedEntity) commandedEntity).setState((byte) 3);
					} else if (meToTDist > 16) {
						((ICommandedEntity) commandedEntity).setState((byte) 1);
					} else if (meToTDist > 10) {
						((ICommandedEntity) commandedEntity).setState((byte) 0);
					}
				}
				state = ((ICommandedEntity) commandedEntity).getState();
				return true;
			}else {
				((ICommandedEntity)commandedEntity).joinPlatoon(null);
				return false;
			}
		}
		return false;
	}

	public void resetTask() {
		((ICommandedEntity)commandedEntity).setState((byte) -1);
	}
	public AICommandedEntity(EntityLiving entityLiving,WorldForPathfind worldForPathfind){
		this.commandedEntity = entityLiving;
		this.worldForPathfind = worldForPathfind;
	}
	Random random = new Random();
	int changeMoveModeCNT = 0;
	int rePathCNT = 10;
	boolean moveRound = false;
	boolean roundDir = random.nextBoolean();

	@Override
	public void updateTask(){

		{
			rePathCNT--;
			if(rePathCNT<0 || commandedEntity.getNavigator().noPath()){
				rePathCNT = 10;
				changeMoveModeCNT--;
				if(changeMoveModeCNT<0){
					moveRound = random.nextBoolean();
					roundDir = random.nextBoolean();
					if(moveRound)
						changeMoveModeCNT = 40 + random.nextInt(40);
					else
						changeMoveModeCNT = 60 + random.nextInt(40);
				}
				commandedEntity.getNavigator().clearPathEntity();

				double meToTDist = commandedEntity.getDistanceSq(targetPos[0], targetPos[1], targetPos[2]);
				double meToLeaderDist = commandedEntity.getDistanceSqToEntity(leaderEntity);
				Vector3d reCurToTargetPosition;
				if (meToLeaderDist > 256) {
					reCurToTargetPosition = new Vector3d(leaderEntity.posX,
							leaderEntity.posY,
							leaderEntity.posZ);
					moveRound = false;
					((ICommandedEntity)commandedEntity).setState((byte) 0);
				} else {
					reCurToTargetPosition = new Vector3d(targetPos[0], targetPos[1], targetPos[2]);
				}
				Vector3d moveToVec = new Vector3d(commandedEntity.posX, commandedEntity.posY, commandedEntity.posZ);
				Vector3d reCurToTargetPosition_copy = new Vector3d(reCurToTargetPosition);
				reCurToTargetPosition.sub(moveToVec);
				double speed = 0;

				if (meToTDist > 4) {
					if (state == 1) {
						speed = 1;
					} else if (state == 2) {
						speed = 0.6;
					}
				} else if (state == 3) {
					speed = -1;
				}
				if (moveRound) {
					reCurToTargetPosition.x = reCurToTargetPosition_copy.z * -1;
					reCurToTargetPosition.z = reCurToTargetPosition_copy.x;
					if (roundDir){
						reCurToTargetPosition.x *= -1;
						reCurToTargetPosition.z *= -1;
					}
					reCurToTargetPosition_copy.scale(speed);
					reCurToTargetPosition.add(reCurToTargetPosition_copy);
					speed = 1;
				} else {
					reCurToTargetPosition.scale(speed != 0 ? 1 : 0);
				}
				moveToVec.add(reCurToTargetPosition);

				commandedEntity.getNavigator().setPath(worldForPathfind.getEntityPathToXYZ(commandedEntity,
						MathHelper.floor_double(moveToVec.x),
						MathHelper.floor_double(moveToVec.y),
						MathHelper.floor_double(moveToVec.z),
						80, true, false, false, true), speed);
//				System.out.println("debug" + moveToVec);
				if(commandedEntity.ridingEntity instanceof EntityDummy_rider && reCurToTargetPosition.lengthSquared() < 64){
					commandedEntity.getNavigator().clearPathEntity();
				}
			}
		}
	}

}