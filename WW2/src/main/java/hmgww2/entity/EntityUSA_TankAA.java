package hmgww2.entity;


import hmggvcmob.ai.AITankAttack;
import hmvehicle.entity.parts.logics.TankBaseLogic;
import hmvehicle.entity.parts.turrets.TurretObj;
import net.minecraft.world.World;

import javax.vecmath.Vector3d;

public class EntityUSA_TankAA extends EntityUSA_TankBase
{
	// public int type;
	
	public EntityUSA_TankAA(World par1World)
	{
		super(par1World);
		this.setSize(4F, 2.5F);
		baseLogic = new TankBaseLogic(this,0.14f,0.7f,false,"gvcmob:gvcmob.JeepWheel");
		aiTankAttack = new AITankAttack(this,6400,1600,10,10);
		this.tasks.addTask(1,aiTankAttack);
		playerpos = new Vector3d(0,3.25D,0.6);
		zoomingplayerpos = new Vector3d(0,3.25D,0.6);
		{
			TurretObj turret1 = new TurretObj(worldObj);
			turret1.onMotherPos = new Vector3d(0,0,1.500);
			turret1.cannonPos = new Vector3d(0,2.8625,1);
			turret1.multicannonPos = new Vector3d[4];
			turret1.multicannonPos[0] = new Vector3d(0.9404,3.063,1);
			turret1.multicannonPos[1] = new Vector3d(-0.9404,3.063,1);
			turret1.multicannonPos[2] = new Vector3d(0.9404,2.662,0);
			turret1.multicannonPos[3] = new Vector3d(-0.9404,2.662,0);
			turret1.turretPitchCenterpos = new Vector3d(0F, 2.9F, -0.3f);
			turret1.turretspeedY = 5;
			turret1.turretspeedP = 8;
			turret1.turretanglelimtPitchMax = 5;
			turret1.turretanglelimtPitchmin = -80;
			turret1.traverseSound = null;
			turret1.currentEntity = this;
			turret1.powor = 16;
			turret1.ex = 1.0F;
			turret1.canex = false;
			turret1.cycle_setting = 0;
			turret1.firesound = "handmadeguns:handmadeguns.HeavyMachineGun";
			turret1.spread = 7;
			turret1.speed = 4;
			turret1.magazinerem = -1;
			turret1.magazineMax = -1;
			turret1.reloadSetting = -1;
			turret1.guntype = 0;
			turret1.syncTurretAngle = true;
			turret1.fireAll = false;
			turret1.flashoffset = 2;
			turret1.flashscale = 2;
			mainTurret = turret1;
		}
		subTurret = null;
		
		turrets = new TurretObj[]{mainTurret};
		armor = 6;
	}
	protected void applyEntityAttributes()
	{
		maxHealth = 250;
		super.applyEntityAttributes();
	}
}
