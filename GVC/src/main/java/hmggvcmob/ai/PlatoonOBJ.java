package hmggvcmob.ai;

import handmadevehicle.entity.EntityDummy_rider;
import handmadevehicle.entity.EntityVehicle;
import handmadevehicle.entity.parts.IVehicle;
import handmadevehicle.entity.parts.Modes;
import handmadevehicle.entity.parts.logics.BaseLogic;
import hmggvcmob.entity.IGVCmob;
import hmggvcmob.entity.IPlatoonable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;

import javax.vecmath.Vector3d;
import java.util.ArrayList;
import java.util.Random;

import static handmadevehicle.Utils.canMoveEntity;

public class PlatoonOBJ {
	public ArrayList<EntityAndPos> platoonMembers = new ArrayList<>();

	public EntityAndPos leader;
	public Modes platoonMode = Modes.Wait;
	ArrayList<EntityAndPos> vehicleDriver = new ArrayList<EntityAndPos>();
	ArrayList<EntityAndPos> planeDriver = new ArrayList<EntityAndPos>();
	ArrayList<EntityAndPos> normalSoldier = new ArrayList<EntityAndPos>();

	public boolean randMiser = false;
	public Random random;
	public PlatoonOBJ(){
		random = new Random();
		randMiser = random.nextBoolean();
	}

	public Vector3d PlatoonTargetPos = new Vector3d();

	public void setPlatoonTargetPos(int[] targetPos){
		PlatoonTargetPos.set(targetPos[0],targetPos[1],targetPos[2]);
	}
	public void setPlatoonTargetPos(double[] targetPos){
		PlatoonTargetPos.set(targetPos[0],targetPos[1],targetPos[2]);
	}
	public void setPlatoonTargetPos(Entity target){
		if(target.ridingEntity == null)PlatoonTargetPos.set(target.posX,target.posY,target.posZ);
		else if(target.ridingEntity instanceof EntityDummy_rider){
			EntityVehicle targetVehicle = ((EntityDummy_rider) target.ridingEntity).linkedBaseLogic.mc_Entity;
			PlatoonTargetPos.set(targetVehicle.posX,targetVehicle.posY,targetVehicle.posZ);
		}else {
			Entity targetEntity = target.ridingEntity;
			PlatoonTargetPos.set(targetEntity.posX,targetEntity.posY,targetEntity.posZ);
		}
	}

	public void SetPlatoonFormationUpdate(){
		vehicleDriver.clear();
		planeDriver.clear();
		normalSoldier.clear();
		for(EntityAndPos entityAndPos : platoonMembers)sortMember(entityAndPos);
		int cnt_plane = 0;
		Vector3d plane_leaderPos = null;
		Vector3d plane_leaderPos_to_TargetPos = null;
		Vector3d plane_leaderHeadingVector = null;
		Vector3d plane_OrthogonalVector = null;
		for(EntityAndPos entityAndPos : planeDriver){
			if(cnt_plane == 0){
				plane_leaderPos = new Vector3d();
				plane_leaderHeadingVector = new Vector3d();
				plane_leaderHeadingVector.set(entityAndPos.entity.motionX,entityAndPos.entity.motionY,entityAndPos.entity.motionZ);
				plane_leaderPos_to_TargetPos = new Vector3d();
				plane_leaderPos.set(entityAndPos.entity.posX,entityAndPos.entity.posY,entityAndPos.entity.posZ);
				plane_leaderPos_to_TargetPos.sub(PlatoonTargetPos,plane_leaderPos);
				Vector3d cross = new Vector3d(plane_leaderPos_to_TargetPos);
				cross.y = 0;
				cross.normalize();
				cross.scale(64);
				double x = cross.x;
				if(randMiser) {
					cross.x = cross.z;
					cross.z = -x;
				}else {
					cross.x = -cross.z;
					cross.z = x;
				}
				plane_OrthogonalVector = new Vector3d(cross);
				cross.add(PlatoonTargetPos);

				entityAndPos.set(cross,1);
				//todo 目標地点周囲を周回飛行
			}else
			{
				Vector3d cross = new Vector3d(plane_leaderHeadingVector);
				cross.y = 0;
				cross.normalize();
				cross.scale(32);
				double x = cross.x;
				if(randMiser) {
					cross.x = -cross.z;
					cross.z = x;
				}else {
					cross.x = cross.z;
					cross.z = -x;
				}
				Vector3d headingVector_Temp = new Vector3d(plane_leaderHeadingVector);
				headingVector_Temp.normalize();
				headingVector_Temp.scale(-16);
				Vector3d targetPosPlane = new Vector3d(plane_OrthogonalVector);
				targetPosPlane.add(headingVector_Temp);
				targetPosPlane.add(cross);
				targetPosPlane.scale(cnt_plane);
				targetPosPlane.add(plane_leaderPos);
				entityAndPos.set(targetPosPlane,1);
			}
			//todo 僚機は斜行陣を敷く。
			cnt_plane++;
		}


		int cnt_soldier = 0;
		Vector3d soldier_leaderPos = null;
		Vector3d platoonHeading = null;//PMC等はオーバーライドできるようにしておこう
		for(EntityAndPos entityAndPos : normalSoldier){
			if(cnt_soldier == 0){
				soldier_leaderPos = new Vector3d();
				platoonHeading = new Vector3d();
				soldier_leaderPos.set(entityAndPos.entity.posX,entityAndPos.entity.posY,entityAndPos.entity.posZ);
				platoonHeading.sub(PlatoonTargetPos,soldier_leaderPos);
				Vector3d leaderTargetPos = new Vector3d(platoonHeading);
				leaderTargetPos.normalize();
				leaderTargetPos.scale(-6);
				leaderTargetPos.add(PlatoonTargetPos);
				entityAndPos.set(leaderTargetPos,1);
			}else
			{
				if(entityAndPos == leader)continue;
				Vector3d targetPossoldier = new Vector3d(platoonHeading);
				targetPossoldier.normalize();
				targetPossoldier.scale(3);
				double x = targetPossoldier.x;
				if(cnt_soldier%2 == 0) {
					targetPossoldier.x = targetPossoldier.z;
					targetPossoldier.z = -x;
				}else if(cnt_soldier%2 == 1){
					targetPossoldier.x = -targetPossoldier.z;
					targetPossoldier.z = x;
				}
				targetPossoldier.scale(cnt_soldier);
				targetPossoldier.add(soldier_leaderPos);
				entityAndPos.set_withRand(targetPossoldier,1);
			}
			cnt_soldier++;
		}

		int cnt_vehicle = 0;
		Vector3d vehicle_leaderPos = null;
		Vector3d vehicle_leaderPos_to_TargetPos = null;
		for(EntityAndPos entityAndPos : vehicleDriver){
			if(cnt_vehicle == 0){
				vehicle_leaderPos = new Vector3d();
				vehicle_leaderPos_to_TargetPos = new Vector3d();
				vehicle_leaderPos.set(entityAndPos.entity.posX,entityAndPos.entity.posY,entityAndPos.entity.posZ);
				if(soldier_leaderPos != null) {
					Vector3d vehicleLeaderToSoldierLeader = new Vector3d();
					vehicleLeaderToSoldierLeader.sub(vehicle_leaderPos, soldier_leaderPos);
					if (vehicleLeaderToSoldierLeader.lengthSquared() > 256) {
						vehicleLeaderToSoldierLeader.normalize();
						vehicleLeaderToSoldierLeader.scale(16);
						vehicle_leaderPos.add(soldier_leaderPos, vehicle_leaderPos);
					}
				}
				vehicle_leaderPos_to_TargetPos.sub(PlatoonTargetPos,vehicle_leaderPos);
				Vector3d leaderTargetPos = new Vector3d(vehicle_leaderPos_to_TargetPos);
				leaderTargetPos.normalize();
				leaderTargetPos.scale(-16);
				leaderTargetPos.add(PlatoonTargetPos);
				entityAndPos.set(leaderTargetPos,1);
			}else
			{
				Vector3d targetPosvehicle = new Vector3d(vehicle_leaderPos_to_TargetPos);
				targetPosvehicle.normalize();
				targetPosvehicle.scale(12);
				double x = targetPosvehicle.x;
				if(randMiser) {
					targetPosvehicle.x = targetPosvehicle.z;
					targetPosvehicle.z = -x;
				}else {
					targetPosvehicle.x = -targetPosvehicle.z;
					targetPosvehicle.z = x;
				}
				targetPosvehicle.scale(cnt_vehicle);
				targetPosvehicle.add(vehicle_leaderPos);
				entityAndPos.set(targetPosvehicle,1);
			}
			cnt_vehicle++;
		}
	}
	//TODO 追加関数で分類分け
	public void addMember(EntityLiving entity){
		EntityAndPos entityAndPos = new EntityAndPos(entity);
		((IPlatoonable)entity).setPosObj(entityAndPos);
		sortMember(entityAndPos);
		if(leader == null)leader = entityAndPos;
		platoonMembers.add(entityAndPos);
	}
	public void sortMember(EntityAndPos entityAndPos){
		EntityLiving entity = entityAndPos.entity;
		if(canMoveEntity(entity) &&entityAndPos.entity.ridingEntity instanceof EntityDummy_rider){
			BaseLogic baseLogic = ((EntityDummy_rider) entityAndPos.entity.ridingEntity).linkedBaseLogic;
			if(baseLogic.prefab_vehicle.T_Land_F_Plane){
				vehicleDriver.add(entityAndPos);
			}else {
				planeDriver.add(entityAndPos);
			}
		}else {
			normalSoldier.add(entityAndPos);
		}

	}
}
