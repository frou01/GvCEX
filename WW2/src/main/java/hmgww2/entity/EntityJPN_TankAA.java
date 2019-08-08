package hmgww2.entity;


import hmggvcmob.ai.AITankAttack;
import handmadevehicle.entity.parts.logics.TankBaseLogic;
import handmadevehicle.entity.parts.turrets.TurretObj;
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
			TurretObj cannon1 = new TurretObj(worldObj);
			cannon1.onMotherPos = new Vector3d(0,0,0);
			cannon1.cannonPos = new Vector3d(0,2,0.7);
			cannon1.multicannonPos = new Vector3d[2];
			cannon1.multicannonPos[0] = new Vector3d(0.2500,2.850,1);
			cannon1.multicannonPos[1] = new Vector3d(-0.2500,2.850,1);
			cannon1.turretPitchCenterpos = new Vector3d(0F, 2.550f, 0.1000);
			cannon1.turretspeedY = 5;
			cannon1.turretspeedP = 8;
			cannon1.turretanglelimtPitchMax = 5;
			cannon1.turretanglelimtPitchmin = -80;
			cannon1.traverseSound = null;
			cannon1.currentEntity = this;
			cannon1.powor = 25;
			cannon1.ex = 1.0F;
			cannon1.canex = false;
			cannon1.cycle_setting = 2;
			cannon1.firesound = "hmgww2:hmgww2.20mmfire";
			cannon1.spread = 15;
			cannon1.speed = 6;
			cannon1.magazinerem = 20;
			cannon1.magazineMax = 20;
			cannon1.reloadSetting = 50;
			cannon1.guntype = 2;
			cannon1.flashoffset = 2;
			cannon1.syncTurretAngle = true;
			cannon1.fireAll_child = false;
			mainTurret = cannon1;
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
