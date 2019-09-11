package hmgww2.entity;


import handmadevehicle.entity.parts.logics.VesselBaseLogicLogic;
import hmggvcmob.GVCMobPlus;
import hmggvcmob.ai.AITankAttack;
import handmadevehicle.entity.parts.OBB;
import handmadevehicle.entity.parts.SeatInfo;
import handmadevehicle.entity.parts.turrets.FireRist;
import handmadevehicle.entity.parts.ModifiedBoundingBox;
import handmadevehicle.entity.parts.turrets.TurretObj;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import javax.vecmath.Vector3d;

public class EntityUSA_ShipD  extends EntityUSA_ShipBase
{
	// public int type;
	
	public EntityUSA_ShipD(World par1World)
	{
		super(par1World);
		this.setSize(7, 10);
		ignoreFrustumCheck = true;
		renderDistanceWeight = Double.MAX_VALUE;
		draft = 2.2f;
		nboundingbox = new ModifiedBoundingBox(boundingBox.minX,boundingBox.minY,boundingBox.minZ,boundingBox.maxX,boundingBox.maxY,boundingBox.maxZ,
				                                      0,5,0,
				                                      10,10,90);
		nboundingbox.rot.set(baseLogic.bodyRot);
		GVCMobPlus.proxy.replaceBoundingbox(this,nboundingbox);
		nboundingbox.centerRotX = 0;
		nboundingbox.centerRotY = 0;
		nboundingbox.centerRotZ = 0;
		
		nboundingbox.boxes = new OBB[9];
		
		nboundingbox.boxes[0] = new OBB
				                        (
						                        new Vector3d(0,3.5,40),
						                        new Vector3d(6.3,7.0,14)
				                        );
		nboundingbox.boxes[1] = new OBB
				                        (
						                        new Vector3d(0,3.7,28),
						                        new Vector3d(7.5,7.5,18.5)
				                        );
		nboundingbox.boxes[2] = new OBB
				                        (
						                        new Vector3d(0,3.7,18),
						                        new Vector3d(8.4,7.5,10)
				                        );
		nboundingbox.boxes[3] = new OBB
				                        (
						                        new Vector3d(0,3.7,11.5),
						                        new Vector3d(8.4,7.5,8)
				                        );
		nboundingbox.boxes[4] = new OBB
				                        (
						                        new Vector3d(0,3.7,1.2),
						                        new Vector3d(8.4,7.5,12)
				                        );
		nboundingbox.boxes[5] = new OBB
				                        (
						                        new Vector3d(0,5.9,-9.8),
						                        new Vector3d(8.4,12,10)
				                        );
		nboundingbox.boxes[6] = new OBB
				                        (
						                        new Vector3d(0,5.9,-21),
						                        new Vector3d(8.4,12,15)
				                        );
		nboundingbox.boxes[7] = new OBB
				                        (
						                        new Vector3d(0,4.6,-35),
						                        new Vector3d(7.8,9.2,14)
				                        );
		nboundingbox.boxes[8] = new OBB
				                        (
						                        new Vector3d(0,4.85,-44.7),
						                        new Vector3d(4.05,9.63,8.6)
				                        );
		
		baseLogic = new VesselBaseLogicLogic(this,0.01f,0.4f,false,"hmgww2:hmgww2.VesselTurbine");
		baseLogic.canControlonWater = true;
		baseLogic.always_point_to_target = false;
		
		((VesselBaseLogicLogic)baseLogic).riddenByEntities = new Entity[4];
		((VesselBaseLogicLogic)baseLogic).seatInfos = new SeatInfo[4];
		((VesselBaseLogicLogic)baseLogic).seatInfos_zoom = new SeatInfo[4];
		
		((VesselBaseLogicLogic)baseLogic).seatInfos[0] = new SeatInfo();
		((VesselBaseLogicLogic)baseLogic).seatInfos_zoom[0] = new SeatInfo();
		((VesselBaseLogicLogic)baseLogic).seatInfos[0].pos = new double[]{playerpos.x,playerpos.y,playerpos.z};
		((VesselBaseLogicLogic)baseLogic).seatInfos_zoom[0].pos = new double[]{zoomingplayerpos.x,zoomingplayerpos.y,zoomingplayerpos.z};
		
		((VesselBaseLogicLogic)baseLogic).seatInfos[1] = ((VesselBaseLogicLogic)baseLogic).seatInfos_zoom[1] = new SeatInfo();
		((VesselBaseLogicLogic)baseLogic).seatInfos[1].pos = ((VesselBaseLogicLogic)baseLogic).seatInfos_zoom[1].pos = new double[]{0,12.98,-15};
		
		((VesselBaseLogicLogic)baseLogic).seatInfos[2] = ((VesselBaseLogicLogic)baseLogic).seatInfos_zoom[2] = new SeatInfo();
		((VesselBaseLogicLogic)baseLogic).seatInfos[2].pos = ((VesselBaseLogicLogic)baseLogic).seatInfos_zoom[2].pos = new double[]{0,8.6,-0.8};
		
		((VesselBaseLogicLogic)baseLogic).seatInfos[3] = ((VesselBaseLogicLogic)baseLogic).seatInfos_zoom[3] = new SeatInfo();
		((VesselBaseLogicLogic)baseLogic).seatInfos[3].pos = ((VesselBaseLogicLogic)baseLogic).seatInfos_zoom[3].pos = new double[]{0,8.1,17.3};
		
		aiTankAttack = new AITankAttack(this,3600,900,10,10);
		aiTankAttack.setAlways_movearound(true);
		this.tasks.addTask(1,aiTankAttack);
		playerpos = new Vector3d(0,14.7269,-24.8614);
		zoomingplayerpos = new Vector3d(0,12.2064,-6.6070);
		cannonpos = new Vector3d(0,2.175,-3.566);
		Vector3d turretPitchCenterpos = new Vector3d(0,2.1706,0);
		turretpos = new Vector3d(0,8.5442,-33.5458);
		TurretObj mainTurret1 = new TurretObj(worldObj);
		{
			mainTurret1.onMotherPos = turretpos;
			mainTurret1.cannonPos = cannonpos;
			mainTurret1.turretPitchCenterpos = turretPitchCenterpos;
			mainTurret1.turretspeedY = 5;
			mainTurret1.turretspeedP = 8;
			mainTurret1.turretanglelimtPitchMax = 1;
			mainTurret1.turretanglelimtPitchmin = -80;
			mainTurret1.traverseSound = null;
			mainTurret1.currentEntity = this;
			mainTurret1.powor = 75;
			mainTurret1.ex = 1.0F;
			mainTurret1.cycle_setting = 60;
			mainTurret1.firesound = "hmgww2:hmgww2.75mmfire";
			mainTurret1.spread = 3;
			mainTurret1.speed = 4;
			mainTurret1.canex = true;
			mainTurret1.guntype = 2;
			mainTurret1.fireRists = new FireRist[]{new FireRist(160,-160,90,-90)};
		}
		turretpos = new Vector3d(2.8444,10.0666,-7.5907);
		TurretObj mainTurret2 = new TurretObj(worldObj);
		{
			mainTurret2.onMotherPos = turretpos;
			mainTurret2.cannonPos = cannonpos;
			mainTurret2.turretPitchCenterpos = turretPitchCenterpos;
			mainTurret2.turretspeedY = 5;
			mainTurret2.turretspeedP = 8;
			mainTurret2.turretanglelimtPitchMax = 1;
			mainTurret2.turretanglelimtPitchmin = -80;
			mainTurret2.traverseSound = null;
			mainTurret2.currentEntity = this;
			mainTurret2.powor = 75;
			mainTurret2.ex = 1.0F;
			mainTurret2.cycle_setting = 60;
			mainTurret2.firesound = "hmgww2:hmgww2.75mmfire";
			mainTurret2.spread = 3;
			mainTurret2.speed = 4;
			mainTurret2.canex = true;
			mainTurret2.guntype = 2;
			mainTurret2.fireRists = new FireRist[]{new FireRist(-10,-180,90,-90)};
		}
		turretpos = new Vector3d(-2.8444,10.0666,-7.5907);
		TurretObj mainTurret3 = new TurretObj(worldObj);
		{
			mainTurret3.onMotherPos = turretpos;
			mainTurret3.cannonPos = cannonpos;
			mainTurret3.turretPitchCenterpos = turretPitchCenterpos;
			mainTurret3.turretspeedY = 5;
			mainTurret3.turretspeedP = 8;
			mainTurret3.turretanglelimtPitchMax = 1;
			mainTurret3.turretanglelimtPitchmin = -80;
			mainTurret3.traverseSound = null;
			mainTurret3.currentEntity = this;
			mainTurret3.powor = 75;
			mainTurret3.ex = 1.0F;
			mainTurret3.cycle_setting = 60;
			mainTurret3.firesound = "hmgww2:hmgww2.75mmfire";
			mainTurret3.spread = 3;
			mainTurret3.speed = 4;
			mainTurret3.canex = true;
			mainTurret3.guntype = 2;
			mainTurret3.fireRists = new FireRist[]{new FireRist(180,10,90,-90)};
		}
		turretpos = new Vector3d(2.9801,6.5867,20.3231);
		TurretObj mainTurret4 = new TurretObj(worldObj);
		{
			mainTurret4.onMotherPos = turretpos;
			mainTurret4.cannonPos = cannonpos;
			mainTurret4.turretPitchCenterpos = turretPitchCenterpos;
			mainTurret4.turretspeedY = 5;
			mainTurret4.turretspeedP = 8;
			mainTurret4.turretanglelimtPitchMax = 1;
			mainTurret4.turretanglelimtPitchmin = -80;
			mainTurret4.traverseSound = null;
			mainTurret4.currentEntity = this;
			mainTurret4.powor = 75;
			mainTurret4.ex = 1.0F;
			mainTurret4.cycle_setting = 60;
			mainTurret4.firesound = "hmgww2:hmgww2.75mmfire";
			mainTurret4.spread = 3;
			mainTurret4.speed = 4;
			mainTurret4.canex = true;
			mainTurret4.guntype = 2;
			mainTurret4.fireRists = new FireRist[]{new FireRist(-20,-180,90,-90)};
		}
		turretpos = new Vector3d(-2.7055,6.5867,24.6199);
		TurretObj mainTurret5 = new TurretObj(worldObj);
		{
			mainTurret5.onMotherPos = turretpos;
			mainTurret5.cannonPos = cannonpos;
			mainTurret5.turretPitchCenterpos = turretPitchCenterpos;
			mainTurret5.turretspeedY = 5;
			mainTurret5.turretspeedP = 8;
			mainTurret5.turretanglelimtPitchMax = 1;
			mainTurret5.turretanglelimtPitchmin = -80;
			mainTurret5.traverseSound = null;
			mainTurret5.currentEntity = this;
			mainTurret5.powor = 75;
			mainTurret5.ex = 1.0F;
			mainTurret5.cycle_setting = 60;
			mainTurret5.firesound = "hmgww2:hmgww2.75mmfire";
			mainTurret5.spread = 3;
			mainTurret5.speed = 4;
			mainTurret5.canex = true;
			mainTurret5.guntype = 2;
			mainTurret5.fireRists = new FireRist[]{new FireRist(180,20,90,-90)};
		}
		turretpos = new Vector3d(0,8.5442,32.9060);
		TurretObj mainTurret6 = new TurretObj(worldObj);
		{
			mainTurret6.onMotherPos = turretpos;
			mainTurret6.cannonPos = cannonpos;
			mainTurret6.turretPitchCenterpos = turretPitchCenterpos;
			mainTurret6.turretspeedY = 5;
			mainTurret6.turretspeedP = 8;
			mainTurret6.turretanglelimtPitchMax = 1;
			mainTurret6.turretanglelimtPitchmin = -80;
			mainTurret6.traverseSound = null;
			mainTurret6.currentEntity = this;
			mainTurret6.powor = 75;
			mainTurret6.ex = 1.0F;
			mainTurret6.cycle_setting = 60;
			mainTurret6.firesound = "hmgww2:hmgww2.75mmfire";
			mainTurret6.spread = 3;
			mainTurret6.speed = 4;
			mainTurret6.canex = true;
			mainTurret6.guntype = 2;
			mainTurret6.fireRists = new FireRist[]{new FireRist(-20,-180,90,-90),new FireRist(180,20,90,-90)};
		}
		turretpos = new Vector3d(3.5077,7.1202,4.2273);
		TurretObj torp1 = new TurretObj(worldObj);
		{
			torp1.onMotherPos = turretpos;
			torp1.cannonPos = cannonpos;
			torp1.turretPitchCenterpos = new Vector3d(0,0,0);
			torp1.turretspeedY = 5;
			torp1.turretspeedP = 8;
			torp1.turretanglelimtPitchMax = 0;
			torp1.turretanglelimtPitchmin = 0;
			torp1.traverseSound = null;
			torp1.currentEntity = this;
			torp1.powor = 800;
			torp1.ex = 1.0F;
			torp1.cycle_setting = 20;
			torp1.firesound = null;
			torp1.flashName = null;
			torp1.spread = 4;
			torp1.speed = 1f;
			torp1.acceler = 0.001f;
			torp1.magazinerem = 3;
			torp1.magazineMax = 3;
			torp1.reloadSetting = -1;
			torp1.canex = true;
			torp1.guntype = 11;
			torp1.fuse = 400;
			torp1.torpdraft = 2;
			torp1.bulletmodel = "byfrou01_torpedo";
			torp1.fireRists = new FireRist[]{new FireRist(-30,-150,90,-90)};
		}
		turretpos = new Vector3d(-3.2205,7.1202,8.5345);
		TurretObj torp2 = new TurretObj(worldObj);
		{
			torp2.onMotherPos = turretpos;
			torp2.cannonPos = cannonpos;
			torp2.turretPitchCenterpos = new Vector3d(0,0,0);
			torp2.turretspeedY = 5;
			torp2.turretspeedP = 8;
			torp2.turretanglelimtPitchMax = 0;
			torp2.turretanglelimtPitchmin = 0;
			torp2.traverseSound = null;
			torp2.currentEntity = this;
			torp2.powor = 800;
			torp2.ex = 1.0F;
			torp2.cycle_setting = 20;
			torp2.firesound = null;
			torp2.flashName = null;
			torp2.spread = 4;
			torp2.speed = 1f;
			torp2.acceler = 0.001f;
			torp2.magazinerem = 3;
			torp2.magazineMax = 3;
			torp2.reloadSetting = -1;
			torp2.canex = true;
			torp2.guntype = 11;
			torp2.fuse = 400;
			torp2.torpdraft = 2;
			torp2.bulletmodel = "byfrou01_torpedo";
			torp2.fireRists = new FireRist[]{new FireRist(150,30,90,-90)};
		}
		turretpos = new Vector3d(-0.9984,7.0179,45.6628);
		TurretObj depth_charge1 = new TurretObj(worldObj);
		{
			depth_charge1.onMotherPos = turretpos;
			depth_charge1.cannonPos = cannonpos;
			depth_charge1.turretPitchCenterpos = new Vector3d(0,0,0);
			depth_charge1.turretspeedY = 5;
			depth_charge1.turretspeedP = 8;
			depth_charge1.turretanglelimtPitchMax = 20;
			depth_charge1.turretanglelimtPitchmin = 20;
			depth_charge1.turretanglelimtYawMax = 180;
			depth_charge1.turretanglelimtYawmin = 180;
			depth_charge1.traverseSound = null;
			depth_charge1.currentEntity = this;
			depth_charge1.powor = 100;
			depth_charge1.ex = 10.0F;
			depth_charge1.cycle_setting = 7;
			depth_charge1.firesound = null;
			depth_charge1.flashName = null;
			depth_charge1.spread = 4;
			depth_charge1.speed = 0.4f;
			depth_charge1.magazinerem = 20;
			depth_charge1.magazineMax = 20;
			depth_charge1.reloadSetting = 1200;
			depth_charge1.canex = true;
			depth_charge1.guntype = 2;
			depth_charge1.fuse = 40;
			depth_charge1.bulletmodel = "byfrou01_depth_charge";
		}
		turretpos = new Vector3d(0.9984,7.0179,45.6628);
		TurretObj depth_charge2 = new TurretObj(worldObj);
		{
			depth_charge2.onMotherPos = turretpos;
			depth_charge2.cannonPos = cannonpos;
			depth_charge2.turretPitchCenterpos = new Vector3d(0,0,0);
			depth_charge2.turretspeedY = 5;
			depth_charge2.turretspeedP = 8;
			depth_charge2.turretanglelimtPitchMax = 20;
			depth_charge2.turretanglelimtPitchmin = 20;
			depth_charge2.turretanglelimtYawMax = 180;
			depth_charge2.turretanglelimtYawmin = 180;
			depth_charge2.traverseSound = null;
			depth_charge2.currentEntity = this;
			depth_charge2.powor = 100;
			depth_charge2.ex = 10.0F;
			depth_charge2.cycle_setting = 7;
			depth_charge2.firesound = null;
			depth_charge2.flashName = null;
			depth_charge2.spread = 4;
			depth_charge2.speed = 0.4f;
			depth_charge2.magazinerem = 20;
			depth_charge2.magazineMax = 20;
			depth_charge2.reloadSetting = 1200;
			depth_charge2.canex = true;
			depth_charge2.guntype = 2;
			depth_charge2.fuse = 40;
			depth_charge2.bulletmodel = "byfrou01_depth_charge";
		}
		
		turrets = new TurretObj[]{mainTurret1,mainTurret2,mainTurret3,mainTurret4,mainTurret5,mainTurret6,torp1,torp2,depth_charge1,depth_charge2};
		mainturrets = new TurretObj[]{mainTurret1,mainTurret2,mainTurret3,mainTurret4,mainTurret5,mainTurret6};
		subturrets = new TurretObj[]{torp1,torp2};
		SPturrets = new TurretObj[]{depth_charge1,depth_charge2};
		armor = 12;
	}
	public void setassault(){
		aiTankAttack.setMinrenge(0);
		aiTankAttack.setMaxrenge(0);
		aiTankAttack.setMgmaxrange(1600);
		aiTankAttack.setAlways_movearound(false);
	}
	public void resetassault(){
		aiTankAttack.setMinrenge(900);
		aiTankAttack.setMaxrenge(3600);
		aiTankAttack.setMgmaxrange(-1);
		aiTankAttack.setAlways_movearound(true);
	}
	protected void applyEntityAttributes()
	{
		maxHealth = 3000;
		super.applyEntityAttributes();
	}
	
	public void onUpdate() {
		if (this.standalone()) {
			if (!worldObj.isRemote && rand.nextInt(100) == 1) {
				boolean flag = false;
				for (int id = 1; id < ((VesselBaseLogicLogic)baseLogic).riddenByEntities.length; id++) {
					if(((VesselBaseLogicLogic)baseLogic).riddenByEntities[id] == null)flag = true;
				}
				if(flag) {
					EntityUSA_S entityUSA_s = new EntityUSA_S(worldObj);
					entityUSA_s.addGun(3);
					entityUSA_s.setLocationAndAngles(this.posX, this.posY, this.posZ, 0, 0);
					worldObj.spawnEntityInWorld(entityUSA_s);
					if (!pickupEntity(entityUSA_s, 1)) entityUSA_s.setDead();
				}
				
			}
			if(usingSP && getAttackTarget() != null){
				if(getsubTurrets() != null)for(TurretObj aturret :getsubTurrets()){
					if(getAttackTarget().getDistanceSq(aturret.pos.x,getAttackTarget().posY,-aturret.pos.z) < 900) {
						aturret.currentEntity = this;
						aturret.fire();
					}
				}
			}
		}
		super.onUpdate();
	}
	@Override
	public TurretObj[] getmotherTurrets() {
		return turrets;
	}
	
	@Override
	public float getthirdDist() {
		return 50;
	}
}

