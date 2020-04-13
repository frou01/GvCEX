package handmadevehicle.entity.parts;

import handmadeguns.HandmadeGunsCore;
import handmadevehicle.Utils;
import handmadevehicle.entity.EntityDummy_rider;
import handmadevehicle.entity.parts.logics.BaseLogic;
import handmadevehicle.entity.parts.turrets.TurretObj;
import handmadevehicle.entity.prefab.Prefab_Vehicle_Base;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.MathHelper;

import javax.vecmath.Vector3d;

import java.util.Random;

import static handmadevehicle.Utils.*;
import static handmadevehicle.entity.parts.Modes.Wait;
import static java.lang.Math.*;
import static java.lang.Math.min;
import static net.minecraft.util.MathHelper.wrapAngleTo180_double;
import static net.minecraft.util.MathHelper.wrapAngleTo180_float;

public class MoveHelperForVehicle {
	private Entity entity;
	private BaseLogic baseLogic;
	private double posX;
	private double posY;
	private double posZ;
	/** The speed at which the entity should move */
	private double speed;
	private boolean update;
	private Random rand = new Random();

	public MoveHelperForVehicle(Entity entity,BaseLogic baseLogic){
		this.entity = entity;
		this.baseLogic = baseLogic;
	}
	public void setMoveTo(double p_75642_1_, double p_75642_3_, double p_75642_5_, double p_75642_7_)
	{
		this.posX = p_75642_1_;
		this.posY = p_75642_3_;
		this.posZ = p_75642_5_;
		this.speed = p_75642_7_;
		this.update = true;
	}

	public void onUpdateMoveHelper()
	{
		if(baseLogic.riddenByEntities[0] instanceof EntityPlayer)return;
		if(baseLogic.riddenByEntities[0] instanceof EntityLiving && !baseLogic.riddenByEntities[0].isDead) {
			baseLogic.setControl_throttle_down(false);
			baseLogic.setControl_throttle_up(false);
			baseLogic.setControl_yaw_Right(false);
			baseLogic.setControl_yaw_Left(false);
			baseLogic.setControl_Space(false);
			baseLogic.setControl_brake(false);


			int i = MathHelper.floor_double(this.entity.boundingBox.minY + 0.5D);
			double d0 = this.posX - this.entity.posX;
			double d1 = this.posZ - this.entity.posZ;
			double d2 = this.posY - (double) i;
			double d3 = d0 * d0 + d2 * d2 + d1 * d1;

			if (!entity.worldObj.isRemote) {
				float f = (float) (Math.atan2(d1, d0) * 180.0D / Math.PI) - 90.0F;
				prefab_vehicle = baseLogic.prefab_vehicle;

				baseLogic.setControl_Flare(false);
				if(baseLogic.mc_Entity.getEntityData().getBoolean("behome") && rand.nextInt(100) == 0){
					baseLogic.setControl_Flare(true);
				}
				double distToFinal = 0;
				if(baseLogic.mc_Entity.getNavigator().getPath() != null){
					PathPoint pathPoint = baseLogic.mc_Entity.getNavigator().getPath().getFinalPathPoint();
					distToFinal = baseLogic.mc_Entity.getDistanceSq(pathPoint.xCoord,pathPoint.yCoord,pathPoint.zCoord);
				}
				this.setToLogic(this.baseLogic.bodyrotationYaw, f,distToFinal);//TODO 距離は必ず最終目的地までで計算するように変えよう
			}
		}
	}

	private void setToLogic(float currentYaw, float targetYaw,double dist)
	{
		if(prefab_vehicle.T_Land_F_Plane) {
			if(speed != 0) {
				baseLogic.setControl_brake(false);
				baseLogic.setControl_Space(false);
			}
			System.out.println("" + dist);
			if(dist > 36){
				update = false;
				if (speed != 0) {
					float dif = MathHelper.wrapAngleTo180_float(targetYaw - currentYaw);
					boolean reverse = abs(dif)>90;

					if(reverse){
						dif -=180;
						dif = MathHelper.wrapAngleTo180_float(dif);
					}
					float yawspeed = abs(prefab_vehicle.yawspeed_taxing * baseLogic.throttle) * 20 * prefab_vehicle.gravity;
					if (yawspeed > 30) yawspeed = 30;
					boolean insight = false;
					if (dif> yawspeed) {
						if (speed > 0) baseLogic.setControl_yaw_Right(true);
						else baseLogic.setControl_yaw_Left(true);

						if (dist < 100 && prefab_vehicle.forced_rudder_effect_OnGround > 0) {
							baseLogic.setControl_brake(true);
						}
					} else if (dif< -yawspeed ) {

						if (speed > 0) baseLogic.setControl_yaw_Left(true);
						else baseLogic.setControl_yaw_Right(true);

						if (dist < 100 && prefab_vehicle.forced_rudder_effect_OnGround > 0) {
							baseLogic.setControl_brake(true);
						}
					} else {

						double[] xyz = eulerfromQuat((baseLogic.rotationmotion));
						if(xyz[1]>dif / yawspeed / 1000){
							baseLogic.setControl_yaw_Right(false);
							baseLogic.setControl_yaw_Left(false);
							if (speed > 0) baseLogic.setControl_yaw_Left(true);
							else baseLogic.setControl_yaw_Right(true);
						}else if(xyz[1]<dif / yawspeed / 1000){
							baseLogic.setControl_yaw_Right(false);
							baseLogic.setControl_yaw_Left(false);
							if (speed > 0) baseLogic.setControl_yaw_Right(true);
							else baseLogic.setControl_yaw_Left(true);
						}
						insight = true;
					}
					if(!insight){
						double[] xyz = eulerfromQuat((baseLogic.rotationmotion));
						if(xyz[1]>dif / yawspeed / 250){
							baseLogic.setControl_yaw_Right(false);
							baseLogic.setControl_yaw_Left(false);
							if (speed > 0) baseLogic.setControl_yaw_Left(true);
							else baseLogic.setControl_yaw_Right(true);
						}else if(xyz[1]<dif / yawspeed / 250){
							baseLogic.setControl_yaw_Right(false);
							baseLogic.setControl_yaw_Left(false);
							if (speed > 0) baseLogic.setControl_yaw_Right(true);
							else baseLogic.setControl_yaw_Left(true);
						}
					}

//			System.out.println("debug speed:" + speed);
					float targetThrottle = 0;
					if (insight) {
						if (speed > 0) targetThrottle = (float) (prefab_vehicle.throttle_Max * speed);
						else targetThrottle = (float) (-prefab_vehicle.throttle_min * speed);
					} else {
						if (speed > 0) targetThrottle = prefab_vehicle.throttle_Max * 0.5f;
						else if (speed < 0) targetThrottle = -prefab_vehicle.throttle_min * 0.5f;
					}
					if(reverse){
						targetThrottle *= -1;
					}
					targetThrottle *= dist/100;
					if (targetThrottle == 0) {
						baseLogic.setControl_throttle_down(false);
						baseLogic.setControl_Space(true);
					} else if (abs(targetThrottle - baseLogic.throttle) > prefab_vehicle.throttle_speed){
						if (baseLogic.throttle > targetThrottle) {
							baseLogic.setControl_throttle_down(true);
						} else {
							baseLogic.setControl_throttle_up(true);
						}
						if(abs(baseLogic.throttle) > targetThrottle)baseLogic.setControl_brake(true);
					}else {
						baseLogic.throttle = targetThrottle;
					}
				}else {
					baseLogic.setControl_brake(true);
					baseLogic.setControl_Space(true);
				}
			}
		} else {
			baseLogic.setControl_brake(false);
			baseLogic.setControl_Space(false);
			Vector3d bodyvector = transformVecByQuat(new Vector3d(unitZ), baseLogic.bodyRot);
			bodyvector.normalize();
			transformVecforMinecraft(bodyvector);
			autocontrol(bodyvector);
		}
	}
	public boolean T_useMain_F_useSub = true;
	public boolean climbing;
	public boolean diveStart = false;
	public int outSightCnt = 0;
	public int reChaseCool = 0;
	public boolean climbYawDir=new Random().nextBoolean();
	
	TurretObj subTurret;
	TurretObj mainTurret;
	Prefab_Vehicle_Base prefab_vehicle;
	private Vector3d lastSeenPosition = null;

	void autocontrol(Vector3d bodyvector){
		if(baseLogic.health>0 && baseLogic.riddenByEntities[0] instanceof EntityLiving){
			if(rand.nextInt(10000) == 0)climbYawDir = !climbYawDir;
			EntityLiving pilot = (EntityLiving) baseLogic.riddenByEntities[0];
			EntityLivingBase target = (pilot).getAttackTarget();
			bodyvector = new Vector3d(bodyvector);//copy
			bodyvector.scale(-1);
			int genY = entity.worldObj.getHeightValue((int) baseLogic.mc_Entity.posX, (int) baseLogic.mc_Entity.posZ);//target alt
			float alt;
			alt = (float) (baseLogic.mc_Entity.posY - genY);
			baseLogic.seatInfos[0].gunTrigger1 = false;
			baseLogic.seatInfos[0].gunTrigger2 = false;
			Vector3d targetPos = getSeeingPosition(target);
			if(baseLogic.seatInfos[0].subgun != null) {
				subTurret = baseLogic.seatInfos[0].subgun.getAvailableTurret();
			}
			if(baseLogic.seatInfos[0].maingun != null) {
				mainTurret = baseLogic.seatInfos[0].maingun[baseLogic.seatInfos[0].currentWeaponMode].getAvailableTurret();
				if (mainTurret == null){
					baseLogic.seatInfos[0].currentWeaponMode++;
					if (baseLogic.seatInfos[0].currentWeaponMode >= baseLogic.seatInfos[0].maingun.length) {
						baseLogic.seatInfos[0].currentWeaponMode = 0;
					}
					mainTurret = baseLogic.seatInfos[0].maingun[baseLogic.seatInfos[0].currentWeaponMode].getAvailableTurret();
				}
			}
			boolean noWeapon = mainTurret == null && subTurret == null;
			if(targetPos != null && (pilot).getAttackTarget() != null && !(pilot).getAttackTarget().isDead){
				double targetyaw = 0;
				double targetpitch = 0;
				boolean ATG = prefab_vehicle.type_F_Plane_T_Heli || ((pilot).getAttackTarget().posY<100 && (pilot).getAttackTarget().onGround);
				if(target.ridingEntity != null){
					ATG |= target.ridingEntity.onGround;
					if(target.ridingEntity instanceof EntityDummy_rider){
						BaseLogic targetVehicle = ((EntityDummy_rider) target.ridingEntity).linkedBaseLogic;
						ATG |= targetVehicle.mc_Entity.onGround;
						ATG |= targetVehicle.prefab_vehicle.T_Land_F_Plane;
					}
				}
				Vector3d courseVec = new Vector3d(targetPos);
				Vector3d thisPos = new Vector3d(baseLogic.mc_Entity.posX, baseLogic.mc_Entity.posY+prefab_vehicle.rotcenterVec.y, baseLogic.mc_Entity.posZ);
				courseVec.sub(thisPos);
				double terminalSpeed = 0;
				if(mainTurret != null && mainTurret.gunItem != null){
					terminalSpeed = mainTurret.gunItem.getTerminalspeed();
				}
				if(!noWeapon){
					if(prefab_vehicle.type_F_Plane_T_Heli && mainTurret != null && mainTurret.gunItem != null){
						Vector3d motionVec = new Vector3d(baseLogic.mc_Entity.motionX,baseLogic.mc_Entity.motionY,baseLogic.mc_Entity.motionZ);

						if (terminalSpeed != 0) {
							Vector3d PredictedOffset = LinePrediction(new Vector3d(baseLogic.mc_Entity.posX,baseLogic.mc_Entity.posY,baseLogic.mc_Entity.posZ),
									new Vector3d(targetPos),
									new Vector3d(-baseLogic.mc_Entity.motionX, -baseLogic.mc_Entity.motionY, -baseLogic.mc_Entity.motionZ),
									terminalSpeed);
							PredictedOffset.sub(targetPos);
							courseVec.add(PredictedOffset);
						} else if (mainTurret.gunItem.gunInfo.speed != 0) {

							Vector3d PredictedOffset = LinePrediction(new Vector3d(baseLogic.mc_Entity.posX,baseLogic.mc_Entity.posY,baseLogic.mc_Entity.posZ),
									new Vector3d(targetPos),
									new Vector3d(-baseLogic.mc_Entity.motionX, -baseLogic.mc_Entity.motionY, -baseLogic.mc_Entity.motionZ),
									mainTurret.gunItem.gunInfo.speed);
							PredictedOffset.sub(targetPos);
							courseVec.add(PredictedOffset);
						}
					}
				}
//						double angletocourse = toDegrees(bodyvector.angle(courseVec));
//						System.out.println("" + angletocourse);
				targetyaw = wrapAngleTo180_float(-(float) toDegrees(atan2(courseVec.x, courseVec.z)));
				targetpitch = noWeapon?(prefab_vehicle.type_F_Plane_T_Heli?prefab_vehicle.cruiseNoseDown:0):-toDegrees(asin(courseVec.y/courseVec.length()));
				if(noWeapon){
					targetpitch = prefab_vehicle.type_F_Plane_T_Heli?prefab_vehicle.cruiseNoseDown:0;
				}else
				if(mainTurret != null && mainTurret.gunItem != null && terminalSpeed == 0){
					courseVec.add(thisPos);
					Vector3d cannonPos = mainTurret.forAim_getCannonPosGlobal();

					targetpitch = -(CalculateGunElevationAngle(
							cannonPos.x,
							cannonPos.y,
							cannonPos.z,

							courseVec.x,
							courseVec.y + 20/getDistanceSq(thisPos,courseVec),
							courseVec.z,mainTurret.gunItem.gunInfo.gravity * (float) HandmadeGunsCore.cfg_defgravitycof,mainTurret.gunItem.gunInfo.speed)[0]);
				}
				double angularDifferenceYaw = wrapAngleTo180_double(targetyaw + (noWeapon ? (climbYawDir ? 90 : -90) : 0) - baseLogic.bodyrotationYaw);
				double angularDifferencePitch = wrapAngleTo180_double(targetpitch - baseLogic.bodyrotationPitch);
				boolean insight =
						angularDifferenceYaw > prefab_vehicle.yawsightwidthmin &&
								angularDifferenceYaw < prefab_vehicle.yawsightwidthmax &&
								angularDifferencePitch > prefab_vehicle.pitchsighwidthmin &&
								angularDifferencePitch < prefab_vehicle.pitchsighwidthmax;
				if(!ATG) {
					if (alt + baseLogic.mc_Entity.motionY * 10 < prefab_vehicle.minALT||climbing) {
						climbing = true;
						if(alt > prefab_vehicle.cruiseALT)climbing = false;
						if(!prefab_vehicle.type_F_Plane_T_Heli){
							if (alt < prefab_vehicle.cruiseALT)
								baseLogic.server_easyMode_pitchTarget = prefab_vehicle.maxClimb;
							else
								baseLogic.server_easyMode_pitchTarget = 0;
						}else {
							if (alt < prefab_vehicle.cruiseALT)
								baseLogic.server_easyMode_pitchTarget = prefab_vehicle.maxClimb*baseLogic.localMotionVec.z;
							else
								baseLogic.server_easyMode_pitchTarget = prefab_vehicle.cruiseNoseDown;
						}
						baseLogic.server_easyMode_yawTarget = baseLogic.bodyrotationYaw + (climbYawDir ? 1 : -1);
						if(alt < prefab_vehicle.minALT)baseLogic.setControl_flap(true);
					} else {
						if(reChaseCool>0){
							reChaseCool--;
							if(!prefab_vehicle.type_F_Plane_T_Heli){
								baseLogic.server_easyMode_pitchTarget = prefab_vehicle.maxClimb;
							}else {
								baseLogic.server_easyMode_pitchTarget = prefab_vehicle.maxClimb*baseLogic.localMotionVec.z;
							}
						}else {
							if(targetpitch>prefab_vehicle.maxDive && alt < prefab_vehicle.minALT){
								reChaseCool=300;
							}
							baseLogic.server_easyMode_pitchTarget = !prefab_vehicle.type_F_Plane_T_Heli ? -20 : 0;

							baseLogic.server_easyMode_yawTarget = targetyaw;
							baseLogic.server_easyMode_pitchTarget = targetpitch;
							if (abs(angularDifferenceYaw) > 60) {
								baseLogic.server_easyMode_pitchTarget *= 1 - (abs(angularDifferenceYaw) - 60) / 150;
								baseLogic.server_easyMode_pitchTarget -= 5;
							}
						}
					}
					baseLogic.setControl_throttle_up(true);
				} else {
					if(alt < prefab_vehicle.minALT)climbing = true;
					reChaseCool--;
					if ((climbing || targetPos.y > baseLogic.mc_Entity.posY) || reChaseCool>0 || (!diveStart)) {
						if(alt < prefab_vehicle.minALT)baseLogic.server_easyMode_pitchTarget = !prefab_vehicle.type_F_Plane_T_Heli ? prefab_vehicle.maxClimb : 0;
						else if(alt < prefab_vehicle.cruiseALT)baseLogic.server_easyMode_pitchTarget = !prefab_vehicle.type_F_Plane_T_Heli ? prefab_vehicle.maxClimb:(prefab_vehicle.cruiseNoseDown/2);
						else if(alt > prefab_vehicle.cruiseALT + 10)baseLogic.server_easyMode_pitchTarget = !prefab_vehicle.type_F_Plane_T_Heli ? 10:(prefab_vehicle.cruiseNoseDown);
						else baseLogic.server_easyMode_pitchTarget = !prefab_vehicle.type_F_Plane_T_Heli ? 0:(prefab_vehicle.cruiseNoseDown);
						if(alt >= prefab_vehicle.cruiseALT)climbing = false;
						baseLogic.setControl_throttle_up(true);
						if(alt < prefab_vehicle.minALT)baseLogic.setControl_flap(true);
//						System.out.println("debug1 " + targetpitch);
//						System.out.println("debug2 " + climbing);
//						System.out.println("debug3 " + reChaseCool);
//						System.out.println("debug4 " + diveStart);

						diveStart = targetpitch < prefab_vehicle.startDive;
						if(diveStart && alt < prefab_vehicle.minALT){
							baseLogic.server_easyMode_yawTarget = baseLogic.bodyrotationYaw + (climbYawDir ? 25 : -25);
						}else {
							baseLogic.server_easyMode_yawTarget = baseLogic.bodyrotationYaw;
						}
					} else {
						if(targetpitch>prefab_vehicle.maxDive || alt < prefab_vehicle.minALT){
							reChaseCool=100;
							diveStart = false;
						}
						baseLogic.server_easyMode_yawTarget = targetyaw;
						baseLogic.server_easyMode_pitchTarget = targetpitch;
						if (abs(angularDifferenceYaw) > 45) {
							baseLogic.server_easyMode_pitchTarget = targetpitch*(1-(abs(angularDifferenceYaw)-45)/45);
//							baseLogic.server_easyMode_yawTarget = targetyaw * (5/abs(targetyaw));
						}
						if(!prefab_vehicle.type_F_Plane_T_Heli && baseLogic.throttle>prefab_vehicle.throttle_Max * 0.8){
							baseLogic.setControl_throttle_up(false);
							baseLogic.setControl_throttle_down(true);
							baseLogic.setControl_brake(true);
						}else
						if(prefab_vehicle.type_F_Plane_T_Heli || baseLogic.throttle<prefab_vehicle.throttle_Max * 0.6) {
							baseLogic.setControl_throttle_up(true);
							baseLogic.setControl_throttle_down(false);
							baseLogic.setControl_brake(false);
						}
					}
				}
				//FCS
				{
					if (insight) {
						if (prefab_vehicle.useMain_withSub) {
							baseLogic.seatInfos[0].gunTrigger1 = true;
							baseLogic.seatInfos[0].gunTrigger2 = true;
						} else if (T_useMain_F_useSub) {
							if (mainTurret != null) {
								if((mainTurret.gunItem.gunInfo.speed >3 || mainTurret.gunItem.gunInfo.acceleration > 0)){
									if (!mainTurret.isreloading())
										baseLogic.seatInfos[0].gunTrigger1 = true;
								}
							}
						} else {
							if (subTurret != null) {
								if (!subTurret.isreloading())
									baseLogic.seatInfos[0].gunTrigger2 = true;
							}
						}
						if(outSightCnt>0)outSightCnt--;
						else outSightCnt = 0;
					}else {
						if(reChaseCool<0)outSightCnt++;
						if(outSightCnt>prefab_vehicle.outSightCntMax){
							reChaseCool = 100;
							outSightCnt = 0;
						}
					}
				}
//				subTurret = baseLogic.seatInfos[0].subgun;
//				if(baseLogic.seatInfos[0].maingun != null) {
//					mainTurret = baseLogic.seatInfos[0].maingun[baseLogic.seatInfos[0].currentWeaponMode];
//					if (mainTurret.gunItem == null){
//						baseLogic.seatInfos[0].currentWeaponMode++;
//						if (baseLogic.seatInfos[0].currentWeaponMode >= baseLogic.seatInfos[0].maingun.length) {
//							baseLogic.seatInfos[0].currentWeaponMode = 0;
//						}
//						mainTurret = null;
//					}
//				}
//				if (subTurret != null && subTurret.gunItem == null){
//					subTurret = null;
//				}
//				if(!T_useMain_F_useSub && subTurret == null)T_useMain_F_useSub = true;
//				if(T_useMain_F_useSub && mainTurret == null)T_useMain_F_useSub = false;
//				if(target.onGround && genY < targetPos.y)
//					alt = (float) (baseLogic.mc_Entity.posY - targetPos.y);
//				Vector3d courseVec = new Vector3d(targetPos.x,targetPos.y,targetPos.z);
//				courseVec.sub(new Vector3d(baseLogic.mc_Entity.posX, baseLogic.mc_Entity.posY, baseLogic.mc_Entity.posZ));
//				courseVec.normalize();
//
//				float targetyaw = wrapAngleTo180_float(-(float) toDegrees(atan2(courseVec.x, courseVec.z)));
//				double AngulardifferenceYaw = targetyaw - baseLogic.bodyrotationYaw;
//				AngulardifferenceYaw = wrapAngleTo180_double(AngulardifferenceYaw);
//				double planespeed = baseLogic.mc_Entity.motionX * baseLogic.mc_Entity.motionX + baseLogic.mc_Entity.motionY * baseLogic.mc_Entity.motionY + baseLogic.mc_Entity.motionZ * baseLogic.mc_Entity.motionZ;
//				TurretObj currentTurret = T_useMain_F_useSub ? mainTurret : subTurret;
//				if(currentTurret != null) {
//					Vector3d currentCannonpos = currentTurret.forAim_getCannonPos();
//					double[] elevations = CalculateGunElevationAngle(currentCannonpos.x, currentCannonpos.y, currentCannonpos.z, target, (float) currentTurret.gunItem.gunInfo.gravity, currentTurret.gunItem.gunInfo.speed + (float) sqrt(planespeed));
//					float targetpitch = (float) -elevations[0];
//					if (!prefab_vehicle.useMain_withSub && (T_useMain_F_useSub ? mainTurret.isreloading() : subTurret.isreloading())) {
//						T_StartDive_F_FlyToStartDivePos = false;
//					}
//					if (!prefab_vehicle.useMain_withSub) {
//						if (T_useMain_F_useSub) {
//							if (mainTurret.isreloading()) {
//								rising_after_Attack = true;
//								T_useMain_F_useSub = !T_useMain_F_useSub;
//								changeWeaponCycle = 0;
//							}
//						} else {
//							if (subTurret.isreloading()) {
//								rising_after_Attack = true;
//								T_useMain_F_useSub = !T_useMain_F_useSub;
//								changeWeaponCycle = 0;
//							}
//						}
//					}
//					double toTargetPitch = -toDegrees(asin(courseVec.y));
//					if(currentTurret.gunItem.gunInfo.acceleration>0){
//						targetpitch = (float) toTargetPitch;
//						elevations[2] = 0;
//					}else
//					if(currentTurret.gunItem.gunInfo.speed<1 && (elevations[2] == -1 || elevations[0]<0)){
//						if(prefab_vehicle.type_F_Plane_T_Heli)
//							targetpitch = prefab_vehicle.maxDive/2;//bomb
//						else targetpitch = 0;
//						elevations[2] = 0;
//					}
//					Vector3d motionvec = new Vector3d(baseLogic.mc_Entity.motionX, baseLogic.mc_Entity.motionY, baseLogic.mc_Entity.motionZ);
//					if (!rising_after_Attack && T_StartDive_F_FlyToStartDivePos) {
////					System.out.println("" + targetpitch);
//						boolean insight = elevations[2] != -1;
//						boolean istarget_onGround = ((target instanceof ITank && ((ITank) target).getBaseLogic().ishittingWater()) || target.isInWater() || target.onGround || (target.ridingEntity != null && target.ridingEntity.onGround));
//
//						if (prefab_vehicle.type_F_Plane_T_Heli) {
//							if ((alt > prefab_vehicle.cruiseALT + 30))
//								baseLogic.setControl_throttle_down(true);
//							else
//								baseLogic.setControl_throttle_up(true);
//							if (alt < prefab_vehicle.cruiseALT) T_StartDive_F_FlyToStartDivePos = false;
//							if (abs(toTargetPitch)>prefab_vehicle.maxDive)T_StartDive_F_FlyToStartDivePos = false;
//						} else if (T_useMain_F_useSub && prefab_vehicle.Dive_bombing && istarget_onGround) {
//							if (toTargetPitch < prefab_vehicle.startDive) {
//								targetpitch = prefab_vehicle.maxClimb;
//								baseLogic.setControl_throttle_up(true);
//								insight = false;
//								outSightCnt--;
//							} else {
//								if (targetpitch < prefab_vehicle.startDive) {
//									float AngulardifferencePitch = targetpitch - baseLogic.bodyrotationPitch;
//									targetpitch = prefab_vehicle.startDive-20;
//									baseLogic.setControl_throttle_up(true);
//									insight = AngulardifferenceYaw > prefab_vehicle.yawsightwidthmin && AngulardifferenceYaw < prefab_vehicle.yawsightwidthmax && AngulardifferencePitch > prefab_vehicle.pitchsighwidthmin && AngulardifferencePitch < prefab_vehicle.pitchsighwidthmax;
//								} else {
//									baseLogic.setControl_throttle_up(true);
//								}
//							}
//						} else if (T_useMain_F_useSub && prefab_vehicle.Torpedo_bomber) {
//							baseLogic.setControl_throttle_up(true);
//							if (alt < prefab_vehicle.minALT + 5) {
//								targetpitch = -20;
//							} else if (alt > prefab_vehicle.minALT) {
//								targetpitch = 10;
//							} else {
//								targetpitch = 0;
//							}
//						} else if (prefab_vehicle.throttledown_onDive && istarget_onGround) {
//							if (baseLogic.throttle > (prefab_vehicle.throttle_Max / 3) * 2) baseLogic.setControl_throttle_down(true);
//							else baseLogic.setControl_throttle_up(true);
//						} else {
//							baseLogic.setControl_throttle_up(true);
//						}
//						float AngulardifferencePitch = targetpitch - baseLogic.bodyrotationPitch;
//						if (targetpitch < prefab_vehicle.maxClimb) targetpitch = prefab_vehicle.maxClimb;
//						if (prefab_vehicle.type_F_Plane_T_Heli && targetpitch > prefab_vehicle.maxDive) {
//							targetpitch = prefab_vehicle.maxDive;
//							T_StartDive_F_FlyToStartDivePos = false;
//						}
////					if(targetpitch < 0 && istarget_onGround)targetpitch = 0;
//						if (!istarget_onGround) {
//							pitchHandle_considerYaw(targetpitch, AngulardifferenceYaw,AngulardifferenceYaw > 0);
//							handle_Yaw(AngulardifferenceYaw, targetpitch, alt);
//							if(prefab_vehicle.type_F_Plane_T_Heli)
//								rollHandle(0);
//						} else {
////							System.out.println("debug");
//							if ((motionvec.y < -0.1 || alt < prefab_vehicle.cruiseALT)) {
//								targetpitch/=3;
//								baseLogic.setControl_throttle_up(true);
//							}else {
//								targetpitch *=1.2;
//								if(alt > prefab_vehicle.cruiseALT+10)baseLogic.setControl_throttle_down(true);
//							}
//							if (handle_Yaw(AngulardifferenceYaw, 0, alt)) {
//								if (abs(AngulardifferenceYaw) > 20 && abs(baseLogic.bodyrotationRoll) < 10) {
//									pitchHandle_considerYaw(targetpitch, AngulardifferenceYaw,AngulardifferenceYaw > 0);
//								} else {
//									pitchHandle(targetpitch);
//								}
//								if(prefab_vehicle.type_F_Plane_T_Heli){
//									rollHandle(climbYawDir?targetpitch/2:-targetpitch/2);
//								}
//							} else {
//								pitchHandle_considerYaw(0, AngulardifferenceYaw,AngulardifferenceYaw > 0);
//							}
//						}
////						System.out.println("debug insight pre " + insight);
//						insight &= AngulardifferenceYaw > prefab_vehicle.yawsightwidthmin && AngulardifferenceYaw < prefab_vehicle.yawsightwidthmax &&
//								AngulardifferencePitch > prefab_vehicle.pitchsighwidthmin && AngulardifferencePitch < prefab_vehicle.pitchsighwidthmax;
////						System.out.println("debug insight aft " + insight);
//						if (T_useMain_F_useSub && prefab_vehicle.Torpedo_bomber) {
//							insight = baseLogic.mc_Entity.getDistanceSq(targetPos.x,targetPos.y,targetPos.z) < 1600;
//							if (insight) {
//								rising_after_Attack = true;
//								T_StartDive_F_FlyToStartDivePos = false;
//							}
//						}
////						System.out.println("debug AngulardifferenceYaw " + AngulardifferenceYaw);
////						System.out.println("debug targetpitch " + (baseLogic.bodyrotationPitch - targetpitch));
//						if (!prefab_vehicle.useMain_withSub && mainTurret != null && subTurret != null) {
//							if (prefab_vehicle.sholdUseMain_ToG) {
//								if (istarget_onGround) {
//									T_useMain_F_useSub = !mainTurret.isreloading();
//								} else {
//									T_useMain_F_useSub = false;
//								}
//							} else if (prefab_vehicle.sholdUseMain_ToA) {
//								if (!istarget_onGround) {
//									T_useMain_F_useSub = !mainTurret.isreloading();
//								} else {
//									T_useMain_F_useSub = false;
//								}
//							}
//						}
//						if (insight) {
////							System.out.println("debug");
//							if (prefab_vehicle.useMain_withSub) {
//								baseLogic.seatInfos[0].gunTrigger1 = true;
//								baseLogic.seatInfos[0].gunTrigger2 = true;
//							} else if (T_useMain_F_useSub) {
//								if (mainTurret != null) {
//									if (!mainTurret.isreloading())
//										baseLogic.seatInfos[0].gunTrigger1 = true;
//									else {
//										rising_after_Attack = true;
//										T_useMain_F_useSub = !T_useMain_F_useSub;
//										changeWeaponCycle = 0;
//									}
//								}
//							} else {
//								if (subTurret != null) {
//									if (!subTurret.isreloading())
//										baseLogic.seatInfos[0].gunTrigger2 = true;
//									else {
//										rising_after_Attack = true;
//										T_useMain_F_useSub = !T_useMain_F_useSub;
//										changeWeaponCycle = 0;
//									}
//								}
//							}
//							outSightCnt--;
//						} else {
//							outSightCnt++;
//							if (outSightCnt > prefab_vehicle.outSightCntMax) {
//								outSightCnt = 0;
//								rising_after_Attack = true;
//								//離脱
//							}
//						}
//						changeWeaponCycle++;
//						if (changeWeaponCycle > prefab_vehicle.changeWeaponCycleSetting) {
//							changeWeaponCycle = 0;
//							T_useMain_F_useSub = !T_useMain_F_useSub;
//						}
//						if (target.onGround && toTargetPitch > prefab_vehicle.maxDive) {
//							T_StartDive_F_FlyToStartDivePos = false;
//							rising_after_Attack = true;
//						}
//						if (!prefab_vehicle.Torpedo_bomber && alt < prefab_vehicle.minALT) {
//							rising_after_Attack = true;
//						}
//					} else {
//						if (!target.onGround) T_StartDive_F_FlyToStartDivePos = true;
//						if(prefab_vehicle.type_F_Plane_T_Heli) {
//							if (toTargetPitch < prefab_vehicle.maxDive / 2) T_StartDive_F_FlyToStartDivePos = true;
//						}else if (toTargetPitch < (prefab_vehicle.Dive_bombing ? prefab_vehicle.startDive / 1.1 : prefab_vehicle.startDive))
//							T_StartDive_F_FlyToStartDivePos = true;
//						if (!target.onGround && outSightCnt > 0) {
//							if (toTargetPitch < 0 && alt > prefab_vehicle.minALT) rising_after_Attack = false;
//							if (alt > prefab_vehicle.cruiseALT) rising_after_Attack = false;
//							rising_after_Attack = true;
//							outSightCnt -= 10;
//						}
//						if (target.onGround) {
//							if(outSightCnt > 0) {
//								rising_after_Attack = true;
//								outSightCnt -= 4;
//							}
//							if(outSightCnt <= 0)rising_after_Attack = false;
//						}
//						if(prefab_vehicle.Dive_bombing && baseLogic.bodyrotationPitch>30){
//							baseLogic.setControl_brake(true);
//						}else {
//							baseLogic.setControl_brake(false);
//						}
//						if (rand.nextInt(500) == 0) climbYawDir = !climbYawDir;
//						if (prefab_vehicle.type_F_Plane_T_Heli) {
//							float targetHead = prefab_vehicle.maxDive/2;
//							if ((motionvec.y < -0.1 || alt < prefab_vehicle.cruiseALT)) {
//								targetHead/=3;
//								baseLogic.setControl_throttle_up(true);
//							}else {
//								targetHead *=1.2;
//								if(alt > prefab_vehicle.cruiseALT+10)baseLogic.setControl_throttle_down(true);
//							}
//							rollHandle(0);
//							handle_withPitch(0, targetHead , alt);
//							if (alt < prefab_vehicle.cruiseALT) T_StartDive_F_FlyToStartDivePos = false;
//						} else {
//							baseLogic.setControl_throttle_up(true);
//							if (alt > prefab_vehicle.cruiseALT + 40)
//								handle_withPitch(!T_StartDive_F_FlyToStartDivePos && !(toTargetPitch < (prefab_vehicle.Dive_bombing ? prefab_vehicle.startDive / 2 : prefab_vehicle.startDive)) && alt > prefab_vehicle.cruiseALT * 0.75 ? -AngulardifferenceYaw : climbYawDir ? 12 : -12, 2, prefab_vehicle.cruiseALT);
//							else {
//								handle_withPitch(!T_StartDive_F_FlyToStartDivePos && !(toTargetPitch < (prefab_vehicle.Dive_bombing ? prefab_vehicle.startDive / 2 : prefab_vehicle.startDive)) && alt > prefab_vehicle.cruiseALT * 0.75 ? -AngulardifferenceYaw : climbYawDir ? 12 : -12, alt < prefab_vehicle.cruiseALT ? prefab_vehicle.maxClimb : 0, prefab_vehicle.cruiseALT);
//							}
//							if(prefab_vehicle.type_F_Plane_T_Heli)
//								rollHandle(0);
//						}
//					}
//				}else {
////					System.out.println("debug");
//					Vector3d motionvec = new Vector3d(baseLogic.mc_Entity.motionX, baseLogic.mc_Entity.motionY, baseLogic.mc_Entity.motionZ);
//					if (prefab_vehicle.type_F_Plane_T_Heli) {
//						if(entity.getDistanceSqToEntity(target)>4096){
//							AngulardifferenceYaw = targetyaw+(climbYawDir ? 45 : -45) - baseLogic.bodyrotationYaw;
//						}else
//						if(entity.getDistanceSqToEntity(target)<1024){
//							AngulardifferenceYaw = targetyaw+(climbYawDir ? 120 : -120) - baseLogic.bodyrotationYaw;
//						}else
//							AngulardifferenceYaw = targetyaw+(climbYawDir ? 70 : -70) - baseLogic.bodyrotationYaw;
//						AngulardifferenceYaw = wrapAngleTo180_double(AngulardifferenceYaw);
//
//						float targetHead = prefab_vehicle.maxDive/2;
//						if ((motionvec.y < -0.1 || alt < prefab_vehicle.cruiseALT)) {
//							targetHead/=3;
//							baseLogic.setControl_throttle_up(true);
//						}else {
//							targetHead *=1.2;
//							if(alt > prefab_vehicle.cruiseALT+10)baseLogic.setControl_throttle_down(true);
//						}
//						rollHandle(climbYawDir?20:-20);
//						handle_withPitch(AngulardifferenceYaw, targetHead , alt);
//					} else {
//						baseLogic.setControl_throttle_up(true);
//						handle_withPitch(AngulardifferenceYaw + (climbYawDir ? 15 : -15), 0, alt);
//					}
//					rollHandle(0);
//				}
////				if (rising_after_Attack && alt < 50) {
////					pitchHandle(maxClimb);
////					baseLogic.setControl_throttle_up(true);
////				} else {
////					if(rising_after_Attack)rising_after_Attack = false;
////					if(alt < 20)T_StartDive_F_FlyToStartDivePos = rising_after_Attack = true;
////					{
////						AngulardifferenceYaw = wrapAngleTo180_double(AngulardifferenceYaw);
////						if (subTurret == null) T_useMain_F_useSub = true;
////						if(targetpitch<maxDive-20)T_StartDive_F_FlyToStartDivePos = true;
////						if(targetpitch>maxDive)T_StartDive_F_FlyToStartDivePos = false;
////						if(alt>60)T_StartDive_F_FlyToStartDivePos = true;
////						if(target.onGround)T_StartDive_F_FlyToStartDivePos = true;
////						System.out.println("" + AngulardifferenceYaw + T_StartDive_F_FlyToStartDivePos);
////						if(T_StartDive_F_FlyToStartDivePos) {
////							boolean insight = true;
////							if(AngulardifferenceYaw < -3){
////								//turn left
////								if (alt > 30) {
////									if (baseLogic.bodyrotationRoll > -maxbank * (target.onGround?0.5:1) + 3) {
////										mousex += 8;
////									} else if (baseLogic.bodyrotationRoll < -maxbank * (target.onGround?0.5:1) - 3) {
////										mousex -= 8;
////									}
////								}
////								if (alt > 10 && abs(baseLogic.bodyrotationRoll) < 90) {
////									yawladder++;
////								}
////							}else if(AngulardifferenceYaw > 3){
////								//turn right
////								if (alt > 30) {
////									if (baseLogic.bodyrotationRoll > maxbank * (target.onGround?0.5:1) + 3) {
////										mousex += 8;
////									} else if (baseLogic.bodyrotationRoll < maxbank * (target.onGround?0.5:1) - 3) {
////										mousex -= 8;
////									}
////								}
////								if (alt > 10 && abs(baseLogic.bodyrotationRoll) < 90) {
////									yawladder--;
////								}
////							}else {
////								if (baseLogic.bodyrotationRoll > 2) {
////									mousex += 4;
////								} else if (baseLogic.bodyrotationRoll < -2) {
////									mousex -= 4;
////								}
////							}
////							if (baseLogic.bodyrotationPitch < targetpitch - 2) {
////								mousey -= 16;
////							} else if (baseLogic.bodyrotationPitch > targetpitch + 2) {
////								mousey += 16;
////							}
////							if(abs(baseLogic.bodyrotationPitch - targetpitch)>5) insight = false;
////							if(abs(AngulardifferenceYaw)>5) insight = false;
////							if(insight){
////								if(throttle > throttle_Max/2)baseLogic.setControl_throttle_down(true);
////								if(useMain_withSub){
////									baseLogic.seatInfos[0].gunTrigger1 = true;
////									baseLogic.seatInfos[0].gunTrigger2 = true;
////								}else if(T_useMain_F_useSub){
////									baseLogic.seatInfos[0].gunTrigger1 = true;
////								}else if(!T_useMain_F_useSub){
////									baseLogic.seatInfos[0].gunTrigger2 = true;
////								}
////								changeWeaponCycle ++;
////								if(changeWeaponCycle > changeWeaponCycleSetting){
////									changeWeaponCycle = 0;
////									T_useMain_F_useSub = !T_useMain_F_useSub;
////								}
////							}else {
////								baseLogic.setControl_throttle_up(true);
////							}
////						} else {
////							if (baseLogic.bodyrotationRoll > 5) {
////								mousex += 4;
////							} else if (baseLogic.bodyrotationRoll < -5) {
////								mousex -= 4;
////							}
////							if (target.onGround && alt > 30){
////								if(abs(baseLogic.bodyrotationRoll) < 90){
////									if (baseLogic.bodyrotationPitch < 10) {
////										mousey -= 4;
////									} else if (baseLogic.bodyrotationPitch > 10) {
////										mousey += 4;
////									}
////								}
////							}
////						}
////					}
////				}
			}else
			{
				Modes mode = Wait;
				if(pilot instanceof Hasmode) {
					mode = ((Hasmode) pilot).getMobMode();
				}
				double[] targetpos = null;
				switch (mode) {
					case Wait://wait
					break;
					case Follow:
					case Go://follow

						if(((Hasmode) pilot).getMoveToPos() != null){
							targetpos = new double[]{((Hasmode) pilot).getMoveToPos().x,
									((Hasmode) pilot).getMoveToPos().y,
									((Hasmode) pilot).getMoveToPos().z};
						}else {
							targetpos = ((Hasmode) pilot).getTargetpos();
						}
					break;

					case Land: {
						Vector3d motionvec = new Vector3d(baseLogic.mc_Entity.motionX, baseLogic.mc_Entity.motionY, baseLogic.mc_Entity.motionZ);
						if (motionvec.length() > 0.1) motionvec.normalize();
						if (pilot instanceof Hasmode)
							targetpos = ((Hasmode) pilot).getTargetpos();
						if (targetpos == null)
							targetpos = new double[]{baseLogic.mc_Entity.posX, baseLogic.mc_Entity.posY, baseLogic.mc_Entity.posZ};
						alt = (float) (baseLogic.mc_Entity.posY - targetpos[1]);
						Vector3d courseVec = new Vector3d(targetpos);
						courseVec.sub(new Vector3d(baseLogic.mc_Entity.posX, courseVec.y, baseLogic.mc_Entity.posZ));
//						double angletocourse = toDegrees(bodyvector.angle(courseVec));
//						System.out.println("" + angletocourse);
						float targetyaw = wrapAngleTo180_float(-(float) toDegrees(atan2(courseVec.x, courseVec.z)));
						courseVec.y = 0;
						double dist = courseVec.length();
						double targetALT = courseVec.length()/2;
						if(targetALT < 1)targetALT =1;
						if(targetALT > 40)targetALT = 40;
						if (alt < prefab_vehicle.minALT) {
							baseLogic.server_easyMode_yawTarget = targetyaw;
							baseLogic.server_easyMode_pitchTarget = 1;
							baseLogic.setControl_brake(true);
						} else {
							if (prefab_vehicle.type_F_Plane_T_Heli) {
								if (courseVec.length() > 32) {
									baseLogic.server_easyMode_yawTarget = targetyaw;
									baseLogic.server_easyMode_pitchTarget = 1+baseLogic.localMotionVec.z;
								} else {
									baseLogic.server_easyMode_yawTarget = targetyaw;
									baseLogic.server_easyMode_pitchTarget = baseLogic.localMotionVec.z;
								}
							} else {
								targetALT = courseVec.length()/10;
								if(targetALT < 1)targetALT =1;
								if(targetALT > 40)targetALT = 40;
								float pitch = alt < targetALT ? prefab_vehicle.maxClimb : ((alt > targetALT + 60) ? 5 : -1);
								baseLogic.server_easyMode_yawTarget = targetyaw;
								baseLogic.server_easyMode_pitchTarget = pitch;
							}
						}
						baseLogic.setControl_brake(true);
						if(dist < 20){
							if(baseLogic.mc_Entity.onGround){
								if (baseLogic.throttle > prefab_vehicle.throttle_Max * 0.3){
									baseLogic.setControl_throttle_down(true);
								}else if (baseLogic.throttle <0){
									baseLogic.setControl_throttle_up(true);
								}
							}else
							if (baseLogic.throttle > prefab_vehicle.throttle_Max * 0.6){
								baseLogic.setControl_throttle_down(true);
							}else {
								baseLogic.setControl_throttle_up(true);
							}
						}else
						if (prefab_vehicle.type_F_Plane_T_Heli && (motionvec.y < -0.1 || alt < targetALT)) {
							baseLogic.setControl_throttle_up(true);
						} else if (!prefab_vehicle.type_F_Plane_T_Heli && (alt < targetALT)) {
							baseLogic.setControl_throttle_up(true);
						} else {
							if (baseLogic.throttle > prefab_vehicle.throttle_Max * 0.8 || (prefab_vehicle.type_F_Plane_T_Heli && alt + 3 > targetALT))
								baseLogic.setControl_throttle_down(true);
						}
						return;
					}
				}


				if(targetpos == null)targetpos = new double[]{baseLogic.mc_Entity.posX, baseLogic.mc_Entity.posY, baseLogic.mc_Entity.posZ};
				Vector3d motionvec = new Vector3d(baseLogic.mc_Entity.motionX, baseLogic.mc_Entity.motionY, baseLogic.mc_Entity.motionZ);
				if (motionvec.length() > 0.1) motionvec.normalize();
				Vector3d courseVec = new Vector3d(targetpos);
				courseVec.sub(new Vector3d(baseLogic.mc_Entity.posX, courseVec.y, baseLogic.mc_Entity.posZ));
//						double angletocourse = toDegrees(bodyvector.angle(courseVec));
//						System.out.println("" + angletocourse);
				float targetyaw = wrapAngleTo180_float(-(float) toDegrees(atan2(courseVec.x, courseVec.z)));
				if(alt < prefab_vehicle.minALT){
					baseLogic.server_easyMode_yawTarget = targetyaw;
					baseLogic.server_easyMode_pitchTarget = !prefab_vehicle.type_F_Plane_T_Heli ? prefab_vehicle.maxClimb : 0;
				}
				else {
					if (prefab_vehicle.type_F_Plane_T_Heli) {
						double dist = courseVec.length();
						if (dist > 128) {
							baseLogic.server_easyMode_yawTarget = targetyaw;
							baseLogic.server_easyMode_pitchTarget = 10;
						} else if (dist > 32) {
							baseLogic.server_easyMode_yawTarget = targetyaw;
							baseLogic.server_easyMode_pitchTarget = 3;
						} else {
							baseLogic.server_easyMode_yawTarget = targetyaw;
							baseLogic.server_easyMode_pitchTarget = 0;
						}
					} else {
						baseLogic.server_easyMode_yawTarget = targetyaw;
						baseLogic.server_easyMode_pitchTarget = alt < prefab_vehicle.cruiseALT ?
								prefab_vehicle.maxClimb
								:
								((alt > prefab_vehicle.cruiseALT + 60) ?
										2
										:
										0);
					}
				}

				if (prefab_vehicle.type_F_Plane_T_Heli && (motionvec.y < -0.1 || alt < prefab_vehicle.cruiseALT)) {
					baseLogic.setControl_throttle_up(true);
				} else if (!prefab_vehicle.type_F_Plane_T_Heli && (alt < prefab_vehicle.cruiseALT)) {
					baseLogic.setControl_throttle_up(true);
				} else {
					if (baseLogic.throttle > prefab_vehicle.throttle_Max * 0.8 || (prefab_vehicle.type_F_Plane_T_Heli && alt + 10 > prefab_vehicle.cruiseALT && baseLogic.throttle > prefab_vehicle.throttle_Max * 0.4))
						baseLogic.setControl_throttle_down(true);
				}
			}
		}
	}
	public Vector3d getSeeingPosition(Entity target){
		if(target != null){
			if(lastSeenPosition == null)lastSeenPosition = new Vector3d();
			lastSeenPosition.set(target.posX,target.posY + target.height/2,target.posZ);
		}
		return lastSeenPosition;
	}
}
