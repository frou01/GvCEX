package hmgww2.entity;


import hmggvcmob.ai.AITankAttack;
import hmvehicle.entity.parts.logics.TankBaseLogic;
import hmvehicle.entity.parts.turrets.TurretObj;
import net.minecraft.world.World;

import javax.vecmath.Vector3d;

public class EntityJPN_TankAA extends EntityJPN_TankBase
{
	// public int type;
	
	public EntityJPN_TankAA(World par1World)
	{
		super(par1World);
		this.setSize(4F, 2.5F);
		baseLogic = new TankBaseLogic(this,0.17f,0.55f,false,"hmgww2:hmgww2.Chi-haTrack");
		aiTankAttack = new AITankAttack(this,6400,400,10,10);
		this.tasks.addTask(1,aiTankAttack);
		playerpos = new Vector3d(-0.8,3.2D,0.3);
		zoomingplayerpos = new Vector3d(-0.8,3.2D,0.3);
		cannonpos = new Vector3d(0,2.850,1);
		turretpos = new Vector3d(0,0,0);
		{
			TurretObj turret1 = new TurretObj(worldObj);
			turret1.onMotherPos = turretpos;
			turret1.cannonPos = new Vector3d(0.2500,2.850,1);
			turret1.multicannonPos = new Vector3d[2];
			turret1.multicannonPos[0] = new Vector3d(0.2500,2.850,1);
			turret1.multicannonPos[1] = new Vector3d(-0.2500,2.850,1);
			turret1.turretPitchCenterpos = new Vector3d(0F, 2.550f, 0.1000);
			turret1.turretspeedY = 5;
			turret1.turretspeedP = 8;
			turret1.turretanglelimtPitchMax = 5;
			turret1.turretanglelimtPitchmin = -80;
			turret1.traverseSound = null;
			turret1.currentEntity = this;
			turret1.powor = 27;
			turret1.ex = 1.0F;
			turret1.canex = false;
			turret1.cycle_setting = 2;
			turret1.firesound = "hmgww2:hmgww2.20mmfire";
			turret1.spread = 7;
			turret1.speed = 4;
			turret1.magazinerem = -1;
			turret1.magazineMax = -1;
			turret1.reloadSetting = -1;
			turret1.guntype = 2;
			turret1.syncTurretAngle = true;
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
