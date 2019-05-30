package hmgww2.entity;


import hmggvcmob.GVCMobPlus;
import hmggvcmob.ai.AITankAttack;
import hmvehicle.entity.parts.ModifiedBoundingBox;
import hmvehicle.entity.parts.turrets.TurretObj;
import hmvehicle.entity.parts.logics.VesselBaseLogic;
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
		
		baseLogic = new VesselBaseLogic(this,0.025f,0.8f,false,"hmgww2:hmgww2.Uboat");
		baseLogic.canControlonWater = true;
		baseLogic.always_poit_to_target = true;
		aiTankAttack = new AITankAttack(this,100,4,10,10);
		aiTankAttack.setAlways_poit_to_target(true);
		this.tasks.addTask(1,aiTankAttack);
		playerpos = new Vector3d(0,7.2D,-0.3);
		zoomingplayerpos = new Vector3d(0,15.1069,-0.6658);
		turretpos = new Vector3d(0,2.6440,-30.4535);
		TurretObj torp1 = new TurretObj(worldObj);
		{
			torp1.onmotherPos = turretpos;
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
			torp1.firesound = null;
			torp1.flushName = null;
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
		issubmarine = true;
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
		if(this.standalone()){
			if(this.getAttackTarget() != null)
				draft = 14;
			else
				draft = 4.1306f;
		}
	}
}
