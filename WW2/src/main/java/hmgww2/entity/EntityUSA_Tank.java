package hmgww2.entity;


import hmggvcmob.ai.AITankAttack;
import handmadevehicle.entity.parts.logics.TankBaseLogicLogic;
import handmadevehicle.entity.parts.turrets.TurretObj;
import net.minecraft.world.World;

import javax.vecmath.Vector3d;

public class EntityUSA_Tank extends EntityUSA_TankBase
{
	// public int type;
	
	public EntityUSA_Tank(World par1World)
	{
		super(par1World);
		this.setSize(4F, 2.5F);
		this.setSize(4F, 2.5F);
		baseLogic = new TankBaseLogicLogic(this,0.21f,0.4f,false,"hmgww2:hmgww2.M4Engine");
		aiTankAttack = new AITankAttack(this,2500,400,10,10);
		this.tasks.addTask(1,aiTankAttack);
		playerpos = new Vector3d(-0.5,3.800,0.0);
		zoomingplayerpos = new Vector3d(0.32,2.7,-1.1);
		subturretpos = new Vector3d(-0.4747,1.260,-2.235);
		cannonpos = new Vector3d(0,2.7,-0.00F);
		turretpos = new Vector3d(0,0,0);
		mainTurret = new TurretObj(worldObj);
		{
			mainTurret.onMotherPos = turretpos;
			mainTurret.cannonPos = cannonpos;
			mainTurret.turretPitchCenterpos = new Vector3d(0,2.7,0.91);
			mainTurret.turretspeedY = 2.0;
			mainTurret.turretspeedP = 3;
			mainTurret.currentEntity = this;
			mainTurret.powor = 40;
			mainTurret.ex = 5.0F;
			mainTurret.firesound = "hmgww2:hmgww2.75mm-M3Fire";
			mainTurret.spread = 1.5f;
			mainTurret.speed = 5;
			mainTurret.canex = true;
			mainTurret.guntype = 2;
		}
		subTurret = new TurretObj(worldObj);
		{
			subTurret.currentEntity = this;
			subTurret.turretanglelimtPitchmin = -20;
			subTurret.turretanglelimtPitchMax = 20;
			subTurret.turretanglelimtYawmin = -20;
			subTurret.turretanglelimtYawMax = 20;
			subTurret.turretspeedY = 8;
			subTurret.turretspeedP = 10;
			subTurret.traverseSound = null;
			
			subTurret.onMotherPos = subturretpos;
			subTurret.cycle_setting = 1;
			subTurret.spread = 5;
			subTurret.speed = 8;
			subTurret.firesound = "handmadeguns:handmadeguns.fire";
			subTurret.flashName = "arrow";
			subTurret.flashfuse = 1;
			subTurret.flashscale = 1.5f;
			
			subTurret.powor = 8;
			subTurret.ex = 0;
			subTurret.canex = false;
			subTurret.guntype = 0;
			
			subTurret.magazineMax = 47;
			subTurret.reloadSetting = 100;
			subTurret.flashoffset = 0.5f;
		}
		
		turrets = new TurretObj[]{mainTurret,subTurret};
		armor = 30;
		armor_tilt = 30;
		armor_Side_cof = 0.1f;
		armor_Back_cof = 0.1f;
	}
	protected void applyEntityAttributes()
	{
		maxHealth = 400;
		super.applyEntityAttributes();
	}
}
