package handmadevehicle.entity.parts;

import handmadevehicle.Utils;
import handmadevehicle.entity.parts.logics.BaseLogic;
import handmadevehicle.entity.parts.turrets.TurretObj;
import handmadevehicle.entity.prefab.Prefab_Vehicle_Base;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;

import javax.vecmath.Vector3d;

import java.util.Random;

import static handmadevehicle.Utils.CalculateGunElevationAngle;
import static handmadevehicle.Utils.unitZ;
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
			baseLogic.setControl_Space(true);
			baseLogic.setControl_brake(true);


			int i = MathHelper.floor_double(this.entity.boundingBox.minY + 0.5D);
			double d0 = this.posX - this.entity.posX;
			double d1 = this.posZ - this.entity.posZ;
			double d2 = this.posY - (double) i;
			double d3 = d0 * d0 + d2 * d2 + d1 * d1;

			if (!entity.worldObj.isRemote) {
				float f = (float) (Math.atan2(d1, d0) * 180.0D / Math.PI) - 90.0F;
				this.setToLogic(this.baseLogic.bodyrotationYaw, f);
			}
		}
	}

	private void setToLogic(float currentYaw, float targetYaw)
	{
		if(baseLogic.prefab_vehicle.T_Land_F_Plane) {
			if(speed != 0) {
				baseLogic.setControl_brake(false);
				baseLogic.setControl_Space(false);
			}
			if(update) {
				update = false;
				float dif = MathHelper.wrapAngleTo180_float(targetYaw - currentYaw);
				float yawspeed = abs(baseLogic.prefab_vehicle.yawspeed_taxing * baseLogic.throttle) * 10;
				if(yawspeed>20)yawspeed = 20;
//			System.out.println("debug" + yawspeed);
				boolean insight = false;
				if (dif > yawspeed) {
					if (speed > 0) baseLogic.setControl_yaw_Right(true);
					else baseLogic.setControl_yaw_Left(true);
				} else if (dif < -yawspeed) {
					if (speed > 0) baseLogic.setControl_yaw_Left(true);
					else baseLogic.setControl_yaw_Right(true);
				} else {
					insight = true;
				}

//			System.out.println("debug speed:" + speed);
				float targetThrottle = 0;
				if (insight) {
					if (speed > 0) targetThrottle = (float) (baseLogic.prefab_vehicle.throttle_Max * speed);
					else targetThrottle = (float) (-baseLogic.prefab_vehicle.throttle_min * speed);
				} else {
					if (speed > 0) targetThrottle = baseLogic.prefab_vehicle.throttle_Max * 0.3f;
					else if (speed < 0) targetThrottle = -baseLogic.prefab_vehicle.throttle_min * 0.3f;
				}
//			System.out.println("debug targetThrottle:" + targetThrottle);
				if (baseLogic.throttle > targetThrottle) {
					baseLogic.setControl_throttle_down(true);
				} else {
					baseLogic.setControl_throttle_up(true);
				}
			}
		} else {
			baseLogic.setControl_brake(false);
			baseLogic.setControl_Space(false);
			Vector3d bodyvector = Utils.transformVecByQuat(new Vector3d(unitZ), baseLogic.bodyRot);
			bodyvector.normalize();
			Utils.transformVecforMinecraft(bodyvector);
			autocontrol(bodyvector);
		}
	}
	public boolean rising_after_Attack;
	public boolean T_useMain_F_useSub = true;
	public boolean T_StartDive_F_FlyToStartDivePos = true;
	public int changeWeaponCycle;
	public int outSightCnt = 0;
	public boolean climbYawDir=new Random().nextBoolean();
	
	TurretObj subTurret;
	TurretObj mainTurret;
	Prefab_Vehicle_Base prefab_vehicle;
	private Vector3d lastSeenPosition = null;
	public float target_rollrudder;
	public float target_pitchrudder;
	public float target_yawrudder;

	void autocontrol(Vector3d bodyvector){
		if(baseLogic.health>0 && baseLogic.riddenByEntities[0] instanceof EntityLiving){
			prefab_vehicle = baseLogic.prefab_vehicle;
			bodyvector = new Vector3d(bodyvector);//copy
			bodyvector.scale(-1);
			int genY = entity.worldObj.getHeightValue((int) baseLogic.mc_Entity.posX, (int) baseLogic.mc_Entity.posZ);//target alt
			float alt;
			alt = (float) (baseLogic.mc_Entity.posY - genY);
			baseLogic.seatInfos[0].gunTrigger1 = false;
			baseLogic.seatInfos[0].gunTrigger2 = false;
			EntityLivingBase target = ((EntityLiving)baseLogic.riddenByEntities[0]).getAttackTarget();
			Vector3d targetPos = getSeeingPosition(target);
			if(targetPos != null && ((EntityLiving)baseLogic.riddenByEntities[0]).getAttackTarget() != null && !((EntityLiving)baseLogic.riddenByEntities[0]).getAttackTarget().isDead){
				subTurret = baseLogic.seatInfos[0].subgun;
				if(baseLogic.seatInfos[0].maingun != null) {
					mainTurret = baseLogic.seatInfos[0].maingun[baseLogic.seatInfos[0].currentWeaponMode];
					if (mainTurret.gunItem == null){
						baseLogic.seatInfos[0].currentWeaponMode++;
						if (baseLogic.seatInfos[0].currentWeaponMode >= baseLogic.seatInfos[0].maingun.length) {
							baseLogic.seatInfos[0].currentWeaponMode = 0;
						}
						mainTurret = null;
					}
				}
				if (subTurret != null && subTurret.gunItem == null){
					subTurret = null;
				}
				if(!T_useMain_F_useSub && subTurret == null)T_useMain_F_useSub = true;
				if(T_useMain_F_useSub && mainTurret == null)T_useMain_F_useSub = false;
				if(target.onGround && genY < targetPos.y)
					alt = (float) (baseLogic.mc_Entity.posY - targetPos.y);
				Vector3d courseVec = new Vector3d(targetPos.x,targetPos.y,targetPos.z);
				courseVec.sub(new Vector3d(baseLogic.mc_Entity.posX, baseLogic.mc_Entity.posY, baseLogic.mc_Entity.posZ));
				courseVec.normalize();

				float targetyaw = wrapAngleTo180_float(-(float) toDegrees(atan2(courseVec.x, courseVec.z)));
				double AngulardifferenceYaw = targetyaw - baseLogic.bodyrotationYaw;
				AngulardifferenceYaw = wrapAngleTo180_double(AngulardifferenceYaw);
				double planespeed = baseLogic.mc_Entity.motionX * baseLogic.mc_Entity.motionX + baseLogic.mc_Entity.motionY * baseLogic.mc_Entity.motionY + baseLogic.mc_Entity.motionZ * baseLogic.mc_Entity.motionZ;
				TurretObj currentTurret = T_useMain_F_useSub ? mainTurret : subTurret;
				if(currentTurret != null) {
					Vector3d currentCannonpos = currentTurret.forAim_getCannonPos();
					double[] elevations = CalculateGunElevationAngle(currentCannonpos.x, currentCannonpos.y, currentCannonpos.z, target, (float) currentTurret.gunItem.gunInfo.gravity, currentTurret.gunItem.gunInfo.speed + (float) sqrt(planespeed));
					float targetpitch = (float) -elevations[0];
					if (!prefab_vehicle.useMain_withSub && (T_useMain_F_useSub ? mainTurret.isreloading() : subTurret.isreloading())) {
						T_StartDive_F_FlyToStartDivePos = false;
					}
					if (!prefab_vehicle.useMain_withSub) {
						if (T_useMain_F_useSub) {
							if (mainTurret.isreloading()) {
								rising_after_Attack = true;
								T_useMain_F_useSub = !T_useMain_F_useSub;
								changeWeaponCycle = 0;
							}
						} else {
							if (subTurret.isreloading()) {
								rising_after_Attack = true;
								T_useMain_F_useSub = !T_useMain_F_useSub;
								changeWeaponCycle = 0;
							}
						}
					}
					double toTargetPitch = -toDegrees(asin(courseVec.y));
					if(currentTurret.gunItem.gunInfo.acceleration>0){
						targetpitch = (float) toTargetPitch;
						elevations[2] = 0;
					}else
					if(currentTurret.gunItem.gunInfo.speed<1 && (elevations[2] == -1 || elevations[0]<0)){
						if(prefab_vehicle.type_F_Plane_T_Heli)
							targetpitch = prefab_vehicle.maxDive/2;//bomb
						else targetpitch = 0;
						elevations[2] = 0;
					}
					Vector3d motionvec = new Vector3d(baseLogic.mc_Entity.motionX, baseLogic.mc_Entity.motionY, baseLogic.mc_Entity.motionZ);
					if (!rising_after_Attack && T_StartDive_F_FlyToStartDivePos) {
//					System.out.println("" + targetpitch);
						boolean insight = elevations[2] != -1;
						boolean istarget_onGround = ((target instanceof ITank && ((ITank) target).getBaseLogic().ishittingWater()) || target.isInWater() || target.onGround || (target.ridingEntity != null && target.ridingEntity.onGround));

						if (prefab_vehicle.type_F_Plane_T_Heli) {
							if ((alt > prefab_vehicle.cruiseALT + 30))
								baseLogic.setControl_throttle_down(true);
							else
								baseLogic.setControl_throttle_up(true);
							if (alt < prefab_vehicle.cruiseALT) T_StartDive_F_FlyToStartDivePos = false;
							if (abs(toTargetPitch)>prefab_vehicle.maxDive)T_StartDive_F_FlyToStartDivePos = false;
						} else if (T_useMain_F_useSub && prefab_vehicle.Dive_bombing && istarget_onGround) {
							if (toTargetPitch < prefab_vehicle.startDive) {
								targetpitch = prefab_vehicle.maxClimb;
								baseLogic.setControl_throttle_up(true);
								insight = false;
								outSightCnt--;
							} else {
								if (targetpitch < prefab_vehicle.startDive) {
									float AngulardifferencePitch = targetpitch - baseLogic.bodyrotationPitch;
									targetpitch = prefab_vehicle.startDive-20;
									baseLogic.setControl_throttle_up(true);
									insight = AngulardifferenceYaw > prefab_vehicle.yawsightwidthmin && AngulardifferenceYaw < prefab_vehicle.yawsightwidthmax && AngulardifferencePitch > prefab_vehicle.pitchsighwidthmin && AngulardifferencePitch < prefab_vehicle.pitchsighwidthmax;
								} else {
									baseLogic.setControl_throttle_up(true);
								}
							}
						} else if (T_useMain_F_useSub && prefab_vehicle.Torpedo_bomber) {
							baseLogic.setControl_throttle_up(true);
							if (alt < prefab_vehicle.minALT + 5) {
								targetpitch = -20;
							} else if (alt > prefab_vehicle.minALT) {
								targetpitch = 10;
							} else {
								targetpitch = 0;
							}
						} else if (prefab_vehicle.throttledown_onDive && istarget_onGround) {
							if (baseLogic.throttle > (prefab_vehicle.throttle_Max / 3) * 2) baseLogic.setControl_throttle_down(true);
							else baseLogic.setControl_throttle_up(true);
						} else {
							baseLogic.setControl_throttle_up(true);
						}
						float AngulardifferencePitch = targetpitch - baseLogic.bodyrotationPitch;
						if (targetpitch < prefab_vehicle.maxClimb) targetpitch = prefab_vehicle.maxClimb;
						if (prefab_vehicle.type_F_Plane_T_Heli && targetpitch > prefab_vehicle.maxDive) {
							targetpitch = prefab_vehicle.maxDive;
							T_StartDive_F_FlyToStartDivePos = false;
						}
//					if(targetpitch < 0 && istarget_onGround)targetpitch = 0;
						if (!istarget_onGround) {
							handle_Yaw(AngulardifferenceYaw, targetpitch, alt);
							pitchHandle_considerYaw(targetpitch, AngulardifferenceYaw,AngulardifferenceYaw > 0);
							if(prefab_vehicle.type_F_Plane_T_Heli)
								rollHandle(0);
						} else {
//							System.out.println("debug");
							if ((motionvec.y < -0.1 || alt < prefab_vehicle.cruiseALT)) {
								targetpitch/=3;
								baseLogic.setControl_throttle_up(true);
							}else {
								targetpitch *=1.2;
								if(alt > prefab_vehicle.cruiseALT+10)baseLogic.setControl_throttle_down(true);
							}
							if (handle_Yaw(AngulardifferenceYaw, 0, alt)) {
								if (abs(AngulardifferenceYaw) > 20 && abs(baseLogic.bodyrotationRoll) < 10) {
									pitchHandle_considerYaw(targetpitch, AngulardifferenceYaw,AngulardifferenceYaw > 0);
								} else {
									pitchHandle(targetpitch);
								}
								if(prefab_vehicle.type_F_Plane_T_Heli){
									rollHandle(climbYawDir?targetpitch/2:-targetpitch/2);
								}
							} else {
								pitchHandle_considerYaw(0, AngulardifferenceYaw,AngulardifferenceYaw > 0);
							}
						}
//						System.out.println("debug insight pre " + insight);
						insight &= AngulardifferenceYaw > prefab_vehicle.yawsightwidthmin && AngulardifferenceYaw < prefab_vehicle.yawsightwidthmax &&
								AngulardifferencePitch > prefab_vehicle.pitchsighwidthmin && AngulardifferencePitch < prefab_vehicle.pitchsighwidthmax;
//						System.out.println("debug insight aft " + insight);
						if (T_useMain_F_useSub && prefab_vehicle.Torpedo_bomber) {
							insight = baseLogic.mc_Entity.getDistanceSq(targetPos.x,targetPos.y,targetPos.z) < 1600;
							if (insight) {
								rising_after_Attack = true;
								T_StartDive_F_FlyToStartDivePos = false;
							}
						}
//						System.out.println("debug AngulardifferenceYaw " + AngulardifferenceYaw);
//						System.out.println("debug targetpitch " + (baseLogic.bodyrotationPitch - targetpitch));
						if (!prefab_vehicle.useMain_withSub && mainTurret != null && subTurret != null) {
							if (prefab_vehicle.sholdUseMain_ToG) {
								if (istarget_onGround) {
									T_useMain_F_useSub = !mainTurret.isreloading();
								} else {
									T_useMain_F_useSub = false;
								}
							} else if (prefab_vehicle.sholdUseMain_ToA) {
								if (!istarget_onGround) {
									T_useMain_F_useSub = !mainTurret.isreloading();
								} else {
									T_useMain_F_useSub = false;
								}
							}
						}
						if (insight) {
//							System.out.println("debug");
							if (prefab_vehicle.useMain_withSub) {
								baseLogic.seatInfos[0].gunTrigger1 = true;
								baseLogic.seatInfos[0].gunTrigger2 = true;
							} else if (T_useMain_F_useSub) {
								if (mainTurret != null) {
									if (!mainTurret.isreloading())
										baseLogic.seatInfos[0].gunTrigger1 = true;
									else {
										rising_after_Attack = true;
										T_useMain_F_useSub = !T_useMain_F_useSub;
										changeWeaponCycle = 0;
									}
								}
							} else {
								if (subTurret != null) {
									if (!subTurret.isreloading())
										baseLogic.seatInfos[0].gunTrigger2 = true;
									else {
										rising_after_Attack = true;
										T_useMain_F_useSub = !T_useMain_F_useSub;
										changeWeaponCycle = 0;
									}
								}
							}
							outSightCnt--;
						} else {
							outSightCnt++;
							if (outSightCnt > prefab_vehicle.outSightCntMax) {
								outSightCnt = 0;
								rising_after_Attack = true;
								//ó£íE
							}
						}
						changeWeaponCycle++;
						if (changeWeaponCycle > prefab_vehicle.changeWeaponCycleSetting) {
							changeWeaponCycle = 0;
							T_useMain_F_useSub = !T_useMain_F_useSub;
						}
						if (target.onGround && toTargetPitch > prefab_vehicle.maxDive) {
							T_StartDive_F_FlyToStartDivePos = false;
							rising_after_Attack = true;
						}
						if (!prefab_vehicle.Torpedo_bomber && alt < prefab_vehicle.minALT) {
							rising_after_Attack = true;
						}
					} else {
						if (!target.onGround) T_StartDive_F_FlyToStartDivePos = true;
						if(prefab_vehicle.type_F_Plane_T_Heli) {
							if (toTargetPitch < prefab_vehicle.maxDive / 2) T_StartDive_F_FlyToStartDivePos = true;
						}else if (toTargetPitch < (prefab_vehicle.Dive_bombing ? prefab_vehicle.startDive / 1.1 : prefab_vehicle.startDive))
							T_StartDive_F_FlyToStartDivePos = true;
						if (!target.onGround && outSightCnt > 0) {
							if (toTargetPitch < 0 && alt > prefab_vehicle.minALT) rising_after_Attack = false;
							if (alt > prefab_vehicle.cruiseALT) rising_after_Attack = false;
							rising_after_Attack = true;
							outSightCnt -= 10;
						}
						if (target.onGround) {
							if(outSightCnt > 0) {
								rising_after_Attack = true;
								outSightCnt -= 4;
							}
							if(outSightCnt <= 0)rising_after_Attack = false;
						}
						if(prefab_vehicle.Dive_bombing && baseLogic.bodyrotationPitch>30){
							baseLogic.setControl_brake(true);
						}else {
							baseLogic.setControl_brake(false);
						}
						if (rand.nextInt(500) == 0) climbYawDir = !climbYawDir;
						if (prefab_vehicle.type_F_Plane_T_Heli) {
							float targetHead = prefab_vehicle.maxDive/2;
							if ((motionvec.y < -0.1 || alt < prefab_vehicle.cruiseALT)) {
								targetHead/=3;
								baseLogic.setControl_throttle_up(true);
							}else {
								targetHead *=1.2;
								if(alt > prefab_vehicle.cruiseALT+10)baseLogic.setControl_throttle_down(true);
							}
							rollHandle(0);
							handle_withPitch(0, targetHead , alt);
							if (alt < prefab_vehicle.cruiseALT) T_StartDive_F_FlyToStartDivePos = false;
						} else {
							baseLogic.setControl_throttle_up(true);
							if (alt > prefab_vehicle.cruiseALT + 40)
								handle_withPitch(!T_StartDive_F_FlyToStartDivePos && !(toTargetPitch < (prefab_vehicle.Dive_bombing ? prefab_vehicle.startDive / 2 : prefab_vehicle.startDive)) && alt > prefab_vehicle.cruiseALT * 0.75 ? -AngulardifferenceYaw : climbYawDir ? 12 : -12, 2, prefab_vehicle.cruiseALT);
							else {
								handle_withPitch(!T_StartDive_F_FlyToStartDivePos && !(toTargetPitch < (prefab_vehicle.Dive_bombing ? prefab_vehicle.startDive / 2 : prefab_vehicle.startDive)) && alt > prefab_vehicle.cruiseALT * 0.75 ? -AngulardifferenceYaw : climbYawDir ? 12 : -12, alt < prefab_vehicle.cruiseALT ? prefab_vehicle.maxClimb : 0, prefab_vehicle.cruiseALT);
							}
							if(prefab_vehicle.type_F_Plane_T_Heli)
								rollHandle(0);
						}
					}
				}else {
//					System.out.println("debug");
					Vector3d motionvec = new Vector3d(baseLogic.mc_Entity.motionX, baseLogic.mc_Entity.motionY, baseLogic.mc_Entity.motionZ);
					if (prefab_vehicle.type_F_Plane_T_Heli) {
						if(entity.getDistanceSqToEntity(target)>4096){
							AngulardifferenceYaw = targetyaw+(climbYawDir ? 45 : -45) - baseLogic.bodyrotationYaw;
						}else
						if(entity.getDistanceSqToEntity(target)<1024){
							AngulardifferenceYaw = targetyaw+(climbYawDir ? 120 : -120) - baseLogic.bodyrotationYaw;
						}else
							AngulardifferenceYaw = targetyaw+(climbYawDir ? 70 : -70) - baseLogic.bodyrotationYaw;
						AngulardifferenceYaw = wrapAngleTo180_double(AngulardifferenceYaw);

						float targetHead = prefab_vehicle.maxDive/2;
						if ((motionvec.y < -0.1 || alt < prefab_vehicle.cruiseALT)) {
							targetHead/=3;
							baseLogic.setControl_throttle_up(true);
						}else {
							targetHead *=1.2;
							if(alt > prefab_vehicle.cruiseALT+10)baseLogic.setControl_throttle_down(true);
						}
						rollHandle(climbYawDir?20:-20);
						handle_withPitch(AngulardifferenceYaw, targetHead , alt);
					} else {
						baseLogic.setControl_throttle_up(true);
						handle_withPitch(AngulardifferenceYaw + (climbYawDir ? 15 : -15), 0, alt);
					}
					rollHandle(0);
				}
//				if (rising_after_Attack && alt < 50) {
//					pitchHandle(maxClimb);
//					baseLogic.setControl_throttle_up(true);
//				} else {
//					if(rising_after_Attack)rising_after_Attack = false;
//					if(alt < 20)T_StartDive_F_FlyToStartDivePos = rising_after_Attack = true;
//					{
//						AngulardifferenceYaw = wrapAngleTo180_double(AngulardifferenceYaw);
//						if (subTurret == null) T_useMain_F_useSub = true;
//						if(targetpitch<maxDive-20)T_StartDive_F_FlyToStartDivePos = true;
//						if(targetpitch>maxDive)T_StartDive_F_FlyToStartDivePos = false;
//						if(alt>60)T_StartDive_F_FlyToStartDivePos = true;
//						if(target.onGround)T_StartDive_F_FlyToStartDivePos = true;
//						System.out.println("" + AngulardifferenceYaw + T_StartDive_F_FlyToStartDivePos);
//						if(T_StartDive_F_FlyToStartDivePos) {
//							boolean insight = true;
//							if(AngulardifferenceYaw < -3){
//								//turn left
//								if (alt > 30) {
//									if (baseLogic.bodyrotationRoll > -maxbank * (target.onGround?0.5:1) + 3) {
//										mousex += 8;
//									} else if (baseLogic.bodyrotationRoll < -maxbank * (target.onGround?0.5:1) - 3) {
//										mousex -= 8;
//									}
//								}
//								if (alt > 10 && abs(baseLogic.bodyrotationRoll) < 90) {
//									yawladder++;
//								}
//							}else if(AngulardifferenceYaw > 3){
//								//turn right
//								if (alt > 30) {
//									if (baseLogic.bodyrotationRoll > maxbank * (target.onGround?0.5:1) + 3) {
//										mousex += 8;
//									} else if (baseLogic.bodyrotationRoll < maxbank * (target.onGround?0.5:1) - 3) {
//										mousex -= 8;
//									}
//								}
//								if (alt > 10 && abs(baseLogic.bodyrotationRoll) < 90) {
//									yawladder--;
//								}
//							}else {
//								if (baseLogic.bodyrotationRoll > 2) {
//									mousex += 4;
//								} else if (baseLogic.bodyrotationRoll < -2) {
//									mousex -= 4;
//								}
//							}
//							if (baseLogic.bodyrotationPitch < targetpitch - 2) {
//								mousey -= 16;
//							} else if (baseLogic.bodyrotationPitch > targetpitch + 2) {
//								mousey += 16;
//							}
//							if(abs(baseLogic.bodyrotationPitch - targetpitch)>5) insight = false;
//							if(abs(AngulardifferenceYaw)>5) insight = false;
//							if(insight){
//								if(throttle > throttle_Max/2)baseLogic.setControl_throttle_down(true);
//								if(useMain_withSub){
//									baseLogic.seatInfos[0].gunTrigger1 = true;
//									baseLogic.seatInfos[0].gunTrigger2 = true;
//								}else if(T_useMain_F_useSub){
//									baseLogic.seatInfos[0].gunTrigger1 = true;
//								}else if(!T_useMain_F_useSub){
//									baseLogic.seatInfos[0].gunTrigger2 = true;
//								}
//								changeWeaponCycle ++;
//								if(changeWeaponCycle > changeWeaponCycleSetting){
//									changeWeaponCycle = 0;
//									T_useMain_F_useSub = !T_useMain_F_useSub;
//								}
//							}else {
//								baseLogic.setControl_throttle_up(true);
//							}
//						} else {
//							if (baseLogic.bodyrotationRoll > 5) {
//								mousex += 4;
//							} else if (baseLogic.bodyrotationRoll < -5) {
//								mousex -= 4;
//							}
//							if (target.onGround && alt > 30){
//								if(abs(baseLogic.bodyrotationRoll) < 90){
//									if (baseLogic.bodyrotationPitch < 10) {
//										mousey -= 4;
//									} else if (baseLogic.bodyrotationPitch > 10) {
//										mousey += 4;
//									}
//								}
//							}
//						}
//					}
//				}
			}else
			{
				int mode = 0;
				if(baseLogic.riddenByEntities[0] instanceof Hasmode) {
					mode = ((Hasmode) baseLogic.riddenByEntities[0]).getMobMode();
				}
				switch (mode) {
					case 0://wait
					{
						Vector3d motionvec = new Vector3d(baseLogic.mc_Entity.motionX, baseLogic.mc_Entity.motionY, baseLogic.mc_Entity.motionZ);
						if(rand.nextInt(500) == 0)climbYawDir = !climbYawDir;
						if(prefab_vehicle.type_F_Plane_T_Heli){
							handle_withPitch(climbYawDir ? 12 : -12,3, prefab_vehicle.cruiseALT);
						}else if(alt< prefab_vehicle.cruiseALT){
							handle_withPitch(climbYawDir ? 12 : -12, prefab_vehicle.maxClimb, prefab_vehicle.cruiseALT);
						} else if(alt> prefab_vehicle.cruiseALT + 40)
							handle_withPitch(climbYawDir ? 12 : -12,2, prefab_vehicle.cruiseALT);
						else handle_withPitch(climbYawDir ? 12 : -12,0, prefab_vehicle.cruiseALT);

						if (prefab_vehicle.type_F_Plane_T_Heli && (motionvec.y < -0.1 || alt < prefab_vehicle.cruiseALT)) {
							baseLogic.setControl_throttle_up(true);
						} else if (!prefab_vehicle.type_F_Plane_T_Heli && (motionvec.y < bodyvector.y || alt < prefab_vehicle.cruiseALT)) {
							baseLogic.setControl_throttle_up(true);
						} else {
							if (baseLogic.throttle > prefab_vehicle.throttle_Max * 0.8 || (prefab_vehicle.type_F_Plane_T_Heli && alt + 10 > prefab_vehicle.cruiseALT))
								baseLogic.setControl_throttle_down(true);
						}
						rollHandle(0);
					}
					break;
					case 1:
					case 2://follow
					{
						Vector3d motionvec = new Vector3d(baseLogic.mc_Entity.motionX, baseLogic.mc_Entity.motionY, baseLogic.mc_Entity.motionZ);
						if (motionvec.length() > 0.1) motionvec.normalize();
						double[] targetpos = null;
						if(baseLogic.riddenByEntities[0] instanceof Hasmode)targetpos = ((Hasmode)baseLogic.riddenByEntities[0]).getTargetpos();
						if(targetpos == null)targetpos = new double[]{baseLogic.mc_Entity.posX, baseLogic.mc_Entity.posY, baseLogic.mc_Entity.posZ};
						Vector3d courseVec = new Vector3d(targetpos);
						courseVec.sub(new Vector3d(baseLogic.mc_Entity.posX, courseVec.y, baseLogic.mc_Entity.posZ));
//						double angletocourse = toDegrees(bodyvector.angle(courseVec));
//						System.out.println("" + angletocourse);
						float targetyaw = wrapAngleTo180_float(-(float) toDegrees(atan2(courseVec.x, courseVec.z)));
						double AngulardifferenceYaw = targetyaw - baseLogic.bodyrotationYaw;
						AngulardifferenceYaw = wrapAngleTo180_double(AngulardifferenceYaw);
						if(alt < prefab_vehicle.minALT){
							pitchHandle(!prefab_vehicle.type_F_Plane_T_Heli ? prefab_vehicle.maxClimb : 0);
						}
						else {
							if (prefab_vehicle.type_F_Plane_T_Heli) {
								double dist = courseVec.length();
								if (dist > 128) {
									handle_withPitch(AngulardifferenceYaw, prefab_vehicle.maxDive, alt);
								} else if (dist > 32) {
									handle_withPitch(AngulardifferenceYaw, 3, alt);
								} else {
									handle_withPitch(AngulardifferenceYaw, 0, alt);
								}
							} else {
								float pitch = alt < prefab_vehicle.cruiseALT ?
										prefab_vehicle.maxClimb
										:
										((alt > prefab_vehicle.cruiseALT + 60) ?
												2
												:
												0);
								handle_withPitch(AngulardifferenceYaw, pitch, alt);
							}
						}

						if (prefab_vehicle.type_F_Plane_T_Heli && (motionvec.y < -0.1 || alt < prefab_vehicle.cruiseALT)) {
							baseLogic.setControl_throttle_up(true);
						} else if (!prefab_vehicle.type_F_Plane_T_Heli && (motionvec.y < bodyvector.y || alt < prefab_vehicle.cruiseALT)) {
							baseLogic.setControl_throttle_up(true);
						} else {
							if (baseLogic.throttle > prefab_vehicle.throttle_Max * 0.8 || (prefab_vehicle.type_F_Plane_T_Heli && alt + 10 > prefab_vehicle.cruiseALT)) baseLogic.setControl_throttle_down(true);
						}
//						pitchHandle(alt<60?alt<30?maxClimb:0:maxDive);

						rollHandle(0);
					}
					break;
					case 3: {//Land
						Vector3d motionvec = new Vector3d(baseLogic.mc_Entity.motionX, baseLogic.mc_Entity.motionY, baseLogic.mc_Entity.motionZ);
						if (motionvec.length() > 0.1) motionvec.normalize();

						double[] targetpos = null;
						if (baseLogic.riddenByEntities[0] instanceof Hasmode)
							targetpos = ((Hasmode) baseLogic.riddenByEntities[0]).getTargetpos();
						if (targetpos == null)
							targetpos = new double[]{baseLogic.mc_Entity.posX, baseLogic.mc_Entity.posY, baseLogic.mc_Entity.posZ};
						alt = (float) (baseLogic.mc_Entity.posY - targetpos[1]);
						Vector3d courseVec = new Vector3d(targetpos);
						courseVec.sub(new Vector3d(baseLogic.mc_Entity.posX, courseVec.y, baseLogic.mc_Entity.posZ));
//						double angletocourse = toDegrees(bodyvector.angle(courseVec));
//						System.out.println("" + angletocourse);
						float targetyaw = wrapAngleTo180_float(-(float) toDegrees(atan2(courseVec.x, courseVec.z)));
						double AngulardifferenceYaw = targetyaw - baseLogic.bodyrotationYaw;
						AngulardifferenceYaw = wrapAngleTo180_double(AngulardifferenceYaw);
						courseVec.y = 0;
						double dist = courseVec.length();
						double targetALT = courseVec.length()/2;
						if(targetALT < 1)targetALT =1;
						if(targetALT > 40)targetALT = 40;
						if (alt < prefab_vehicle.minALT) {
							handle_withPitch(AngulardifferenceYaw, 1, alt);
						} else {
							if (prefab_vehicle.type_F_Plane_T_Heli) {
								if (courseVec.length() > 32) {
									handle_withPitch(AngulardifferenceYaw, 1, alt);
								} else {
									handle_withPitch(AngulardifferenceYaw, baseLogic.localMotionVec.z, alt);
								}
							} else {
								float pitch = alt < targetALT ? prefab_vehicle.maxClimb : ((alt > targetALT + 60) ? 5 : 0);
								handle_withPitch(AngulardifferenceYaw, pitch, alt);
							}
						}
						rollHandle(0);
						baseLogic.setControl_brake(true);
						if(dist < 20){
							if(baseLogic.mc_Entity.onGround){
								if (baseLogic.throttle > prefab_vehicle.throttle_Max * 0.1){
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
					}
					break;

				}
			}
		}

		if(target_yawrudder > 16)target_yawrudder = 16;
		if(target_yawrudder <-16)target_yawrudder =-16;
		if(target_pitchrudder > 16)target_pitchrudder = 16;
		if(target_pitchrudder <-16)target_pitchrudder =-16;
		if(target_rollrudder > 16)target_rollrudder = 16;
		if(target_rollrudder <-16)target_rollrudder =-16;

		if(abs(target_pitchrudder - baseLogic.pitchrudder) < prefab_vehicle.rudderSpeed)baseLogic.pitchrudder = (float) (target_pitchrudder);
		else
		if(target_pitchrudder > baseLogic.pitchrudder){
			baseLogic.pitchrudder +=prefab_vehicle.rudderSpeed;
		}else if(target_pitchrudder < baseLogic.pitchrudder){
			baseLogic.pitchrudder -=prefab_vehicle.rudderSpeed;
		}

		if(abs(target_yawrudder - baseLogic.yawrudder) < prefab_vehicle.rudderSpeed)baseLogic.yawrudder = (float) (target_yawrudder);
		else
		if(target_yawrudder > baseLogic.yawrudder){
			baseLogic.setControl_yaw_Right(true);
		}else if(target_yawrudder < baseLogic.yawrudder){
			baseLogic.setControl_yaw_Left(true);
		}

		if(abs(target_rollrudder - baseLogic.rollrudder) < prefab_vehicle.rudderSpeed)baseLogic.rollrudder = (float) (target_rollrudder);
		else
		if(target_rollrudder > baseLogic.rollrudder){
			baseLogic.rollrudder +=prefab_vehicle.rudderSpeed;
		}else if(target_rollrudder < baseLogic.rollrudder){
			baseLogic.rollrudder -=prefab_vehicle.rudderSpeed;
		}
		target_pitchrudder = 0;
		target_yawrudder = 0;
		target_rollrudder = 0;
	}
	public boolean handle_withPitch(double AngulardifferenceYaw, double targetPitch, double alt){
		handle_Yaw(AngulardifferenceYaw,targetPitch,alt);
		return pitchHandle_considerYaw(targetPitch,AngulardifferenceYaw,AngulardifferenceYaw>0);
	}
	public float yawSens = 1;
	public float pitchSens_onYaw = 5;
	public boolean handle_Yaw(double AngulardifferenceYaw, double targetPitch, double alt){
		double maxRollCof = 1 - abs(targetPitch)/90;
		boolean canUseElevator = (abs(baseLogic.bodyrotationRoll) < 90 && targetPitch+2>baseLogic.bodyrotationPitch) || (abs(baseLogic.bodyrotationRoll) > 90 && targetPitch-2<baseLogic.bodyrotationPitch);
//		System.out.println("canUseElevator " + canUseElevator);
		boolean canUseLadder = abs(baseLogic.bodyrotationRoll) < 20 || (abs(baseLogic.bodyrotationRoll) < 90 && targetPitch-2<baseLogic.bodyrotationPitch) || (abs(baseLogic.bodyrotationRoll) > 90 && targetPitch+2>baseLogic.bodyrotationPitch);
//		System.out.println("canUseLadder " + canUseLadder);
//		System.out.println("turnDir " + (AngulardifferenceYaw>0));
		if(maxRollCof < 0)maxRollCof = 0;
		maxRollCof *= min(AngulardifferenceYaw * AngulardifferenceYaw, 225) / 225;
		double AngulardifferencePitch = baseLogic.bodyrotationPitch - targetPitch;
		double amountPitch = abs(AngulardifferenceYaw)/pitchSens_onYaw * abs(sin(baseLogic.bodyrotationRoll)) /(1 + abs(AngulardifferencePitch));
		if(amountPitch>16)amountPitch = 16;
		double amountYaw = abs(AngulardifferenceYaw)/yawSens * abs(cos(baseLogic.bodyrotationRoll));
		if(amountYaw>16)amountYaw = 16;
		if(AngulardifferenceYaw < 0){
			//turn Left
			if(!prefab_vehicle.type_F_Plane_T_Heli) {

				if (alt > prefab_vehicle.cruiseALT) rollHandle(-prefab_vehicle.maxbank * maxRollCof);
				else if (alt > prefab_vehicle.minALT) rollHandle(-prefab_vehicle.maxbank * maxRollCof * (alt) / (prefab_vehicle.cruiseALT));
				else {
					rollHandle(0);
				}
			}

			if(!prefab_vehicle.type_F_Plane_T_Heli) {
				if (abs(baseLogic.bodyrotationRoll) < 45) {
					target_yawrudder-=amountYaw;
				} else if (abs(baseLogic.bodyrotationRoll) > 135) {
					target_yawrudder+=amountYaw;
//					yawrudder += min(abs(AngulardifferenceYaw), 10) / 10;//d
				}
			}else if(canUseLadder){
				if (abs(baseLogic.bodyrotationRoll) < 60) {
					target_yawrudder-=amountYaw;
//					yawrudder -= min(abs(AngulardifferenceYaw)/5, 16)/16;//a
				} else if (abs(baseLogic.bodyrotationRoll) > 120) {
					target_yawrudder+=amountYaw;
//					yawrudder += min(abs(AngulardifferenceYaw)/5, 16)/16;//d
				}
			}
			if(!prefab_vehicle.type_F_Plane_T_Heli) {
				if (abs(baseLogic.bodyrotationRoll) > 45 && abs(baseLogic.bodyrotationRoll) < 135 && canUseElevator) {
					if (baseLogic.bodyrotationRoll < 0) {
						//ç∂Ç…åXÇ¢ÇƒÇ¢ÇÈ
//						mousey -= abs(AngulardifferenceYaw) / prefab_vehicle.pitchspeed / 3;//Å´
						target_pitchrudder -=amountPitch;
					} else if (baseLogic.bodyrotationRoll > 0) {
						//âEÇ…åXÇ¢ÇƒÇ¢ÇÈ
//						mousey += abs(AngulardifferenceYaw) / prefab_vehicle.pitchspeed / 3;//Å™
						target_pitchrudder +=amountPitch;
					}
				}
			}
		}else if(AngulardifferenceYaw > 0){
			//turn Right

			if(!prefab_vehicle.type_F_Plane_T_Heli) {
				if (alt > prefab_vehicle.cruiseALT) rollHandle(prefab_vehicle.maxbank * maxRollCof);
				else if (alt > prefab_vehicle.minALT) rollHandle(prefab_vehicle.maxbank * maxRollCof * (alt) / (prefab_vehicle.cruiseALT));
				else {
					rollHandle(0);
				}
			}

			if(!prefab_vehicle.type_F_Plane_T_Heli) {
				if (abs(baseLogic.bodyrotationRoll) < 45) {
//					yawrudder += min(abs(AngulardifferenceYaw), 10) / 10;//d
					target_yawrudder+=amountYaw;
				} else if (abs(baseLogic.bodyrotationRoll) > 135) {
//					yawrudder -= min(abs(AngulardifferenceYaw), 10) / 10;//a
					target_yawrudder-=amountYaw;
				}
			}else if(canUseLadder){
				if (abs(baseLogic.bodyrotationRoll) < 60) {
//					yawrudder += min(abs(AngulardifferenceYaw)/5, 16)/16;//d
					target_yawrudder+=amountYaw;
				} else if (abs(baseLogic.bodyrotationRoll) > 120) {
//					yawrudder -= min(abs(AngulardifferenceYaw)/5, 16)/16;//a
					target_yawrudder-=amountYaw;
				}
			}
			if(!prefab_vehicle.type_F_Plane_T_Heli) {
				if (abs(baseLogic.bodyrotationRoll) > 45 && abs(baseLogic.bodyrotationRoll) < 135 && canUseElevator) {
					if (baseLogic.bodyrotationRoll < 0) {
						//ç∂Ç…åXÇ¢ÇƒÇ¢ÇÈ
//						mousey += abs(AngulardifferenceYaw) / prefab_vehicle.pitchspeed / 3;//Å´
						target_pitchrudder +=amountPitch;
					} else if (baseLogic.bodyrotationRoll > 0) {
						//âEÇ…åXÇ¢ÇƒÇ¢ÇÈ
//						mousey -= abs(AngulardifferenceYaw) / prefab_vehicle.pitchspeed / 3;//Å™
						target_pitchrudder -=amountPitch;
					}
				}
			}

//			if(abs(bodyrotationRoll)<30){
//				pitchHandle(targetPitch);
//			}else
//			if(abs(bodyrotationRoll)>30 && bodyrotationPitch > targetPitch-10) {
//				if (bodyrotationRoll < 0 && bodyrotationPitch < targetPitch+5) {//ã@éÒÇ™çÇÇ¢
//					//âEÇ…åXÇ¢ÇƒÇ¢ÇÈ
//					//ëÄècûÖâüÇµçûÇ›
//					mousey -= abs(AngulardifferenceYaw)/2;
////					System.out.println("debug3");
//				} else if (bodyrotationRoll > 0 && bodyrotationPitch > targetPitch-5) {//ã@éÒÇ™í·Ç¢
//					//ç∂Ç…åXÇ¢ÇƒÇ¢ÇÈ
//					//ëÄècûÖà¯Ç´çûÇ›
//					mousey += abs(AngulardifferenceYaw)/2;
////					System.out.println("debug4");
//				}
//			}
		}else {
			if(prefab_vehicle.type_F_Plane_T_Heli)rollHandle(0);
		}
//		System.out.println("yawladder auto" + yawladder);
		return abs(AngulardifferenceYaw) < 20;
	}
	public float rollSens = 2;
	public void rollHandle(double targetRoll){
		targetRoll += baseLogic.localMotionVec.x*10;
		double AngulardifferenceRoll = baseLogic.bodyrotationRoll - targetRoll;
		double amount = abs(AngulardifferenceRoll)/rollSens;
		if(amount>16)amount = 16;
		if (baseLogic.bodyrotationRoll > targetRoll) {
			target_rollrudder -=amount;
		} else if (baseLogic.bodyrotationRoll < targetRoll) {
			target_rollrudder +=amount;
		}
	}
	public float pitchSens = 0.3f;
	public boolean pitchHandle(double targetPitch){
		if(prefab_vehicle.type_F_Plane_T_Heli) {
			if(targetPitch< prefab_vehicle.maxClimb)targetPitch = prefab_vehicle.maxClimb;
			if(targetPitch> prefab_vehicle.maxDive)targetPitch = prefab_vehicle.maxDive;
		}
		double AngulardifferencePitch = baseLogic.bodyrotationPitch - targetPitch;
//		if (baseLogic.bodyrotationRoll > 0) {
//			mousex -= abs(AngulardifferencePitch)/15 * sensiv;
//		} else if (baseLogic.bodyrotationRoll < 0) {
//			mousex += abs(AngulardifferencePitch)/15 * sensiv;
//		}
		double amount = abs(AngulardifferencePitch)/pitchSens;
		if (AngulardifferencePitch < 0){
			//ã@éÒâ∫Ç∞

			if(abs(baseLogic.bodyrotationRoll)<45) {
//				mousey += abs(AngulardifferencePitch)/ prefab_vehicle.pitchspeed * sensiv;
				target_pitchrudder += amount;
			}else if(abs(baseLogic.bodyrotationRoll)>135){
//				mousey -= abs(AngulardifferencePitch)/ prefab_vehicle.pitchspeed * sensiv;
				target_pitchrudder -= amount;
			}
			if(abs(baseLogic.bodyrotationRoll) > 45 && abs(baseLogic.bodyrotationRoll) < 135) {
				if (baseLogic.bodyrotationRoll < 0) {
					//ç∂Ç…åXÇ¢ÇƒÇ¢ÇÈ
//					yawrudder +=min(abs(AngulardifferencePitch),10)/40 * sensiv;
					target_yawrudder+=amount;
				} else if (baseLogic.bodyrotationRoll > 0) {
					//âEÇ…åXÇ¢ÇƒÇ¢ÇÈ
//					yawrudder -=min(abs(AngulardifferencePitch),10)/40 * sensiv;
					target_yawrudder-=amount;
				}
			}
		}else if (AngulardifferencePitch > 0){
			//ã@éÒè„Ç∞
			if(abs(baseLogic.bodyrotationRoll)<45) {
//				mousey -= abs(AngulardifferencePitch)/ prefab_vehicle.pitchspeed/2 * sensiv;
				target_pitchrudder -=amount;
			}else if(abs(baseLogic.bodyrotationRoll)>135){
//				mousey += abs(AngulardifferencePitch)/ prefab_vehicle.pitchspeed/2 * sensiv;
				target_pitchrudder +=amount;
			}
			if(abs(baseLogic.bodyrotationRoll) > 45 && abs(baseLogic.bodyrotationRoll) < 135) {
				if (baseLogic.bodyrotationRoll < 0) {
					//ç∂Ç…åXÇ¢ÇƒÇ¢ÇÈ
//					yawrudder -=min(abs(AngulardifferencePitch),10)/40 * sensiv;
					target_yawrudder-=amount;
				} else if (baseLogic.bodyrotationRoll > 0) {
					//âEÇ…åXÇ¢ÇƒÇ¢ÇÈ
//					yawrudder +=min(abs(AngulardifferencePitch),10)/40 * sensiv;
					target_yawrudder+=amount;
				}
			}
		}else {
			return true;
		}
		return false;
	}
	public boolean pitchHandle_considerYaw(double targetPitch,double AngulardifferenceYaw,boolean yawDir){
		//yawDir=false:turnLeft
		//yawDir=true :turnRight
		if(prefab_vehicle.type_F_Plane_T_Heli) {
			if(targetPitch< prefab_vehicle.maxClimb)targetPitch = prefab_vehicle.maxClimb;
			if(targetPitch> prefab_vehicle.maxDive)targetPitch = prefab_vehicle.maxDive;
		}
		double AngulardifferencePitch = baseLogic.bodyrotationPitch - targetPitch;
//		if (bodyrotationRoll > 0) {
//			mousex -= abs(AngulardifferencePitch)/15 * sensiv;
//		} else if (bodyrotationRoll < 0) {
//			mousex += abs(AngulardifferencePitch)/15 * sensiv;
//		}
		boolean allow_PitchUp = abs(baseLogic.bodyrotationRoll)<5 || (yawDir && baseLogic.bodyrotationRoll > 0) || (!yawDir && baseLogic.bodyrotationRoll < 0);
		boolean allow_PitchDown = abs(baseLogic.bodyrotationRoll)<5 || (yawDir && baseLogic.bodyrotationRoll < 0) || (!yawDir && baseLogic.bodyrotationRoll > 0);
		double amountPitch = abs(AngulardifferencePitch)/pitchSens * abs(cos(baseLogic.bodyrotationRoll));
		if(amountPitch>16)amountPitch = 16;
		double amountYaw = abs(AngulardifferenceYaw)/yawSens * abs(AngulardifferenceYaw)/pitchSens * abs(sin(baseLogic.bodyrotationRoll));
		if(amountYaw>16)amountYaw = 16;
		if (AngulardifferencePitch < 0){
			//ã@éÒâ∫Ç∞
			if(allow_PitchDown) {
				if (abs(baseLogic.bodyrotationRoll) < 45) {
//					mousey += abs(AngulardifferencePitch) / prefab_vehicle.pitchspeed * sensiv;
					target_pitchrudder +=amountPitch;
				} else if (abs(baseLogic.bodyrotationRoll) > 135) {
//					mousey -= abs(AngulardifferencePitch) / prefab_vehicle.pitchspeed * sensiv;
					target_pitchrudder -=amountPitch;
				}
			}
			if(abs(baseLogic.bodyrotationRoll) > 15 && abs(baseLogic.bodyrotationRoll) < 165) {
				if (baseLogic.bodyrotationRoll < 0 && !yawDir) {
					//ç∂Ç…åXÇ¢ÇƒÇ¢ÇÈ
//					System.out.println("debug");
//					yawrudder -=min(abs(AngulardifferencePitch),10)/10 * sensiv;
					target_yawrudder-=amountYaw;
				} else if (baseLogic.bodyrotationRoll > 0 && yawDir) {
					//âEÇ…åXÇ¢ÇƒÇ¢ÇÈ
//					System.out.println("debug");
//					yawrudder +=min(abs(AngulardifferencePitch),10)/10 * sensiv;
					target_yawrudder+=amountYaw;
				}
			}
		}else if (AngulardifferencePitch > 0){
			//ã@éÒè„Ç∞
			if(allow_PitchUp) {
				if (abs(baseLogic.bodyrotationRoll) < 45) {
//					mousey -= abs(AngulardifferencePitch) / prefab_vehicle.pitchspeed / 2 * sensiv;
					target_pitchrudder -=amountPitch;
					;
				} else if (abs(baseLogic.bodyrotationRoll) > 135) {
//					mousey += abs(AngulardifferencePitch) / prefab_vehicle.pitchspeed / 2 * sensiv;
					target_pitchrudder +=amountPitch;
					;
				}
			}
			if(abs(baseLogic.bodyrotationRoll) > 15 && abs(baseLogic.bodyrotationRoll) < 165) {
				if (baseLogic.bodyrotationRoll < 0 && yawDir) {
					//ç∂Ç…åXÇ¢ÇƒÇ¢ÇÈ
//					System.out.println("debug");
//					yawrudder +=min(abs(AngulardifferencePitch),10)/10 * sensiv;
					target_yawrudder+=amountYaw;
				} else if (baseLogic.bodyrotationRoll > 0 && !yawDir) {
					//âEÇ…åXÇ¢ÇƒÇ¢ÇÈ
//					System.out.println("debug");
//					yawrudder -=min(abs(AngulardifferencePitch),10)/10 * sensiv;
					target_yawrudder-=amountYaw;
				}
			}
		}else {
			return true;
		}
		return false;
	}
	public Vector3d getSeeingPosition(Entity target){
		if(target != null &&((EntityLiving)baseLogic.riddenByEntities[0]).canEntityBeSeen(target)){
			if(lastSeenPosition == null)lastSeenPosition = new Vector3d();
			lastSeenPosition.set(target.posX,target.posY,target.posZ);
		}
		return lastSeenPosition;
	}
}
