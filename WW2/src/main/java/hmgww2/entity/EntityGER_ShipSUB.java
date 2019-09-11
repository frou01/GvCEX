package hmgww2.entity;


import handmadevehicle.entity.parts.logics.VesselBaseLogicLogic;
import hmggvcmob.GVCMobPlus;
import hmggvcmob.ai.AITankAttack;
import handmadevehicle.entity.parts.ModifiedBoundingBox;
import handmadevehicle.entity.parts.OBB;
import handmadevehicle.entity.parts.turrets.TurretObj;
import net.minecraft.world.World;

import javax.vecmath.Vector3d;

public class EntityGER_ShipSUB extends EntityGER_ShipBase
{
	// public int type;
	
	public EntityGER_ShipSUB(World par1World) {
		super(par1World);
		this.setSize(7, 10);
		ignoreFrustumCheck = true;
		renderDistanceWeight = Double.MAX_VALUE;
		
		nboundingbox = new ModifiedBoundingBox(boundingBox.minX,boundingBox.minY,boundingBox.minZ,boundingBox.maxX,boundingBox.maxY,boundingBox.maxZ,
				                                      0,5,-1.078,
				                                      7,10,67.2181);
		nboundingbox.rot.set(baseLogic.bodyRot);
		GVCMobPlus.proxy.replaceBoundingbox(this,nboundingbox);
		nboundingbox.centerRotX = 0;
		nboundingbox.centerRotY = 0;
		nboundingbox.centerRotZ = 0;
		
		nboundingbox.boxes = new OBB[8];
		
		nboundingbox.boxes[0] = new OBB
				                        (
						                        new Vector3d(0,3.177,-26.66),
						                        new Vector3d(3.6457,5.8054,11.7439)
				                        );
		nboundingbox.boxes[1] = new OBB
				                        (
						                        new Vector3d(0,2.839,-17.39),
						                        new Vector3d(4.1833,5.7887,6.7945)
				                        );
		nboundingbox.boxes[2] = new OBB
				                        (
						                        new Vector3d(0,2.759,-12.23),
						                        new Vector3d(5.2500,5.6290,3.5417)
				                        );
		nboundingbox.boxes[3] = new OBB
				                        (
						                        new Vector3d(0,2.759,-5.229),
						                        new Vector3d(6.6502,5.6290,10.4582)
				                        );
		nboundingbox.boxes[4] = new OBB
				                        (
						                        new Vector3d(0,2.759,5.603),
						                        new Vector3d(6.6502,5.6290,11.2068)
				                        );
		nboundingbox.boxes[5] = new OBB
				                        (
						                        new Vector3d(0,2.896,13.99),
						                        new Vector3d(5.9710,5.3551,5.5797)
				                        );
		nboundingbox.boxes[6] = new OBB
				                        (
						                        new Vector3d(0,3.051,25.73),
						                        new Vector3d(4.1833,5.0440,17.9086)
				                        );
		nboundingbox.boxes[7] = new OBB
				                        (
						                        new Vector3d(0,7.019,1.521),
						                        new Vector3d(2.3525,4.1400,10.4571)
				                        );
		
		baseLogic = new VesselBaseLogicLogic(this,0.025f,0.3f,false,"hmgww2:hmgww2.Uboat");
		baseLogic.canControlonWater = true;
		baseLogic.always_point_to_target = true;
		aiTankAttack = new AITankAttack(this,100,4,10,10);
		aiTankAttack.setAlways_poit_to_target(true);
		this.tasks.addTask(1,aiTankAttack);
		((VesselBaseLogicLogic)baseLogic).seatInfos[0].pos = new double[]{0,10.1280,-2.53};
		((VesselBaseLogicLogic)baseLogic).seatInfos_zoom[0].pos = new double[]{0,15.1069,-0.6658};
		turretpos = new Vector3d(0,2.6440,-35.4535);
		TurretObj torp1 = new TurretObj(worldObj);
		{
			torp1.onMotherPos = turretpos;
			torp1.turretspeedY = 5;
			torp1.turretspeedP = 8;
			torp1.turretanglelimtPitchMax = -30;
			torp1.turretanglelimtPitchmin = -30;
			torp1.turretanglelimtYawMax = 10;
			torp1.turretanglelimtYawmin = -10;
			torp1.traverseSound = null;
			torp1.currentEntity = this;
			torp1.powor = 2500;
			torp1.ex = 1.0F;
			torp1.cycle_setting = 20;
			torp1.firesound = "hmgww2:hmgww2.sub_torpLaunch";
			torp1.flashName = null;
			torp1.spread = 4;
			torp1.speed = 1f;
			torp1.acceler = 0.001f;
			torp1.magazinerem = 2;
			torp1.magazineMax = 2;
			torp1.reloadSetting = 1200;
			torp1.canex = true;
			torp1.guntype = 11;
			torp1.fuse = 300;
			torp1.torpdraft = 2;
			torp1.bulletmodel = "byfrou01_torpedo";
		}
		
		turrets = new TurretObj[]{torp1};
		mainturrets = new TurretObj[]{torp1};
		armor = 12;
		armor_tilt = 0;
		armor_Top_cof = 1;
		armor_Front_cof = 1;
		armor_Back_cof = 1;
		armor_Side_cof = 1;
		issubmarine = true;
		modeBell_normal = "hmgww2:hmgww2.ShipAlert";
		modeBell_SP = "hmgww2:hmgww2.ShipAlert";
	}
	protected void applyEntityAttributes()
	{
		maxHealth = 1000;
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
	
	
	public void onUpdate(){
		super.onUpdate();
		if(usingSP){
			((VesselBaseLogicLogic)baseLogic).seatInfos_zoom[0].pos = new double[]{0,14.90,-0.6658};
		}else {
			((VesselBaseLogicLogic)baseLogic).seatInfos_zoom[0].pos = new double[]{0,11.16,-0.6658};
		}
		if(!worldObj.isRemote){
			usingSP = draft > 8;
		}
		if(this.standalone()){
			if(this.getAttackTarget() != null)
				draft = 14;
			else
				draft = 4.1306f;
		}
	}
}
