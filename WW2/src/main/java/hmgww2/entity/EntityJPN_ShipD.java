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

public class EntityJPN_ShipD extends EntityJPN_ShipBase
{
	// public int type;
	
	public EntityJPN_ShipD(World par1World) {
		super(par1World);
		this.setSize(7, 10);
		ignoreFrustumCheck = true;
		renderDistanceWeight = 1024*1024;
		
		baseLogic = new VesselBaseLogicLogic(this,0.02f,0.6f,false,"hmgww2:hmgww2.VesselTurbine");
		baseLogic.canControlonWater = true;
		baseLogic.always_point_to_target = false;
		
		nboundingbox = new ModifiedBoundingBox(-7,0,-7,7,13,7,
				                                      0,6.5,8.6,
				                                      10,13,82);
		nboundingbox.boxes = new OBB[7];
		
		nboundingbox.boxes[0] = new OBB
				                        (
						                        new Vector3d(0,2.6,40),
						                        new Vector3d(5.6,5.7,5)
				                        );
		nboundingbox.boxes[1] = new OBB
				                        (
						                        new Vector3d(0,2.6,28),
						                        new Vector3d(7.5,5.7,18.5)
				                        );
		nboundingbox.boxes[2] = new OBB
				                        (
						                        new Vector3d(0,2.6,13.5),
						                        new Vector3d(7.5,5.7,13.5)
				                        );
		nboundingbox.boxes[3] = new OBB
				                        (
						                        new Vector3d(0,2.6,-2.1),
						                        new Vector3d(7.5,5.7,19.5)
				                        );
		nboundingbox.boxes[4] = new OBB
				                        (
						                        new Vector3d(0,2.6,-2.1),
						                        new Vector3d(7.5,5.7,19.5)
				                        );
		nboundingbox.boxes[5] = new OBB
				                        (
						                        new Vector3d(0,3.6,-17  ),
						                        new Vector3d(7.5,7.6,19.5)
				                        );
		nboundingbox.boxes[6] = new OBB
				                        (
						                        new Vector3d(0,4,-33  ),
						                        new Vector3d(5,8.3,13.5)
				                        );
		
		nboundingbox.rot.set(baseLogic.bodyRot);
		GVCMobPlus.proxy.replaceBoundingbox(this,nboundingbox);
		nboundingbox.centerRotX = 0;
		nboundingbox.centerRotY = 0;
		nboundingbox.centerRotZ = 0;
		aiTankAttack = new AITankAttack(this,3600,900,10,10);
		aiTankAttack.setAlways_movearound(true);
		this.tasks.addTask(1,aiTankAttack);
		playerpos = new Vector3d(0,19.02,-13.32);
		zoomingplayerpos = new Vector3d(-0.8,3.2D,-0.3);
		cannonpos = new Vector3d(0,0.7324,-1.872);
		turretpos = new Vector3d(0,7.9753,-27.07);
		
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
		
		
		TurretObj mainTurret1 = new TurretObj(worldObj);
		{
			mainTurret1.onMotherPos = turretpos;
			mainTurret1.cannonPos = cannonpos;
			mainTurret1.turretPitchCenterpos = new Vector3d(0,0.7329,0.01402);
			mainTurret1.turretspeedY = 5;
			mainTurret1.turretspeedP = 8;
			mainTurret1.turretanglelimtPitchMax = 5;
			mainTurret1.turretanglelimtPitchmin = -80;
			mainTurret1.traverseSound = null;
			mainTurret1.currentEntity = this;
			mainTurret1.powor = 120;
			mainTurret1.ex = 1.0F;
			mainTurret1.cycle_setting = 120;
			mainTurret1.firesound = "hmgww2:hmgww2.12.7cmFire";
			mainTurret1.spread = 3;
			mainTurret1.speed = 4;
			mainTurret1.canex = true;
			mainTurret1.guntype = 2;
			mainTurret1.fireRists = new FireRist[]{new FireRist(160,-160,90,-90)};
		}
		turretpos = new Vector3d(0,5.8224,9.864);
		TurretObj mainTurret2 = new TurretObj(worldObj);
		{
			mainTurret2.onMotherPos = turretpos;
			mainTurret2.cannonPos = cannonpos;
			mainTurret2.turretPitchCenterpos = new Vector3d(0,0.7329,0.01402);
			mainTurret2.turretspeedY = 5;
			mainTurret2.turretspeedP = 8;
			mainTurret2.turretanglelimtPitchMax = 5;
			mainTurret2.turretanglelimtPitchmin = -80;
			mainTurret2.traverseSound = null;
			mainTurret2.currentEntity = this;
			mainTurret2.powor = 120;
			mainTurret2.ex = 1.0F;
			mainTurret2.cycle_setting = 120;
			mainTurret2.firesound = "hmgww2:hmgww2.12.7cmFire";
			mainTurret2.spread = 3;
			mainTurret2.speed = 4;
			mainTurret2.canex = true;
			mainTurret2.guntype = 2;
			mainTurret2.fireRists = new FireRist[]{new FireRist(150,15,90,-90),new FireRist(-15,-150,90,-90)};
		}
		turretpos = new Vector3d(0,5.311,3.527);
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
			torp1.powor = 2400;
			torp1.ex = 1.0F;
			torp1.cycle_setting = 20;
			torp1.firesound = "hmgww2:hmgww2.TorpLaunch";
			torp1.flashName = null;
			torp1.spread = 4;
			torp1.speed = 1f;
			torp1.acceler = 0.001f;
			torp1.magazinerem = 2;
			torp1.magazineMax = 2;
			torp1.reloadSetting = 1200;
			torp1.canex = true;
			torp1.guntype = 11;
			torp1.fuse = 400;
			torp1.torpdraft = 2;
			torp1.bulletmodel = "byfrou01_torpedo";
			torp1.fireRists = new FireRist[]{new FireRist(150,30,90,-90),new FireRist(-30,-150,90,-90)};
		}
		
		turretpos = new Vector3d(-1.7710,5.311,44.3919);
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
			depth_charge1.ex = 1;
			depth_charge1.cycle_setting = 20;
			depth_charge1.firesound = null;
			depth_charge1.flashName = null;
			depth_charge1.spread = 4;
			depth_charge1.speed = 0.4f;
			depth_charge1.magazinerem = 20;
			depth_charge1.magazineMax = 20;
			depth_charge1.reloadSetting = 1200;
			depth_charge1.canex = true;
			depth_charge1.guntype = 2;
			depth_charge1.resistanceinwater = 1;
			depth_charge1.fuse = 30;
			depth_charge1.damageRange = 30;
			depth_charge1.bulletmodel = "byfrou01_depth_charge";
		}
			turretpos = new Vector3d(1.7710,5.311,44.3919);
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
			depth_charge2.ex = 1;
			depth_charge2.cycle_setting = 20;
			depth_charge2.firesound = null;
			depth_charge2.flashName = null;
			depth_charge2.spread = 4;
			depth_charge2.speed = 0.4f;
			depth_charge2.magazinerem = 20;
			depth_charge2.magazineMax = 20;
			depth_charge2.reloadSetting = 1200;
			depth_charge2.canex = true;
			depth_charge2.guntype = 2;
			depth_charge2.resistanceinwater = 1;
			depth_charge2.fuse = 30;
			depth_charge2.damageRange = 30;
			depth_charge2.bulletmodel = "byfrou01_depth_charge";
		}
		
		turrets = new TurretObj[]{mainTurret1,mainTurret2,torp1,depth_charge1,depth_charge2};
		mainturrets = new TurretObj[]{mainTurret1,mainTurret2};
		subturrets = new TurretObj[]{torp1};
		SPturrets = new TurretObj[]{depth_charge1,depth_charge2};
		armor = 24;
		armor_Top_cof =
				armor_Front_cof =
						armor_Back_cof =
								armor_Side_cof = 1;
	}
	public void onUpdate() {
		if (this.standalone()) {
			if (!worldObj.isRemote && rand.nextInt(100) == 1) {
				boolean flag = false;
				for (int id = 1; id < ((VesselBaseLogicLogic)baseLogic).riddenByEntities.length; id++) {
					if(((VesselBaseLogicLogic)baseLogic).riddenByEntities[id] == null)flag = true;
				}
				if(flag) {
					EntityJPN_S entityJPN_s = new EntityJPN_S(worldObj);
					entityJPN_s.addGun(3);
					entityJPN_s.setLocationAndAngles(this.posX, this.posY, this.posZ, 0, 0);
					worldObj.spawnEntityInWorld(entityJPN_s);
					if (!pickupEntity(entityJPN_s, 1)) entityJPN_s.setDead();
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
	public void setassault(){
		aiTankAttack.setMinrenge(0);
		aiTankAttack.setMaxrenge(0);
		aiTankAttack.setMgmaxrange(400);
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
		maxHealth = 2000;
		super.applyEntityAttributes();
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
