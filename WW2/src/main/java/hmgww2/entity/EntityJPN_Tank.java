package hmgww2.entity;


import hmggvcmob.ai.AITankAttack;
import hmvehicle.entity.parts.logics.TankBaseLogic;
import hmvehicle.entity.parts.turrets.TurretObj;
import net.minecraft.world.World;

import javax.vecmath.Vector3d;

public class EntityJPN_Tank extends EntityJPN_TankBase
{
	// public int type;
	
	public EntityJPN_Tank(World par1World)
	{
		super(par1World);
		this.setSize(4F, 2.5F);
		baseLogic = new TankBaseLogic(this,0.17f,0.55f,false,"hmgww2:hmgww2.Chi-haTrack");
		aiTankAttack = new AITankAttack(this,1600,100,10,10);
		this.tasks.addTask(1,aiTankAttack);
		playerpos = new Vector3d(-0.3,3.4D,0.2);
		zoomingplayerpos = new Vector3d(0.55,2.35D,-0.80);
		cannonpos = new Vector3d(0,2.2,-2.0F);
		turretpos = new Vector3d(0,0,0);
		
		mainTurret = new TurretObj(worldObj);
		{
			mainTurret.onMotherPos = turretpos;
			mainTurret.cannonPos = cannonpos;
			mainTurret.turretPitchCenterpos = new Vector3d(0F, 2.00F, 1.00F);
			mainTurret.turretspeedY = 3;
			mainTurret.turretspeedP = 3;
			mainTurret.currentEntity = this;
			mainTurret.powor = 45;
			mainTurret.ex = 2.0F;
			mainTurret.firesound = "hmgww2:hmgww2.75mmfire";
			mainTurret.spread = 0.2f;
			mainTurret.speed = 10;
			mainTurret.canex = true;
			mainTurret.cycle_setting = 60;
			mainTurret.guntype = 2;
		}
		subTurret = new TurretObj(worldObj);
		{
			subTurret.currentEntity = this;
			subTurret.turretanglelimtPitchmin = -90;
			subTurret.turretanglelimtPitchMax = 45;
			subTurret.turretanglelimtYawmin = -360;
			subTurret.turretanglelimtYawMax = 360;
			subTurret.turretspeedY = 8;
			subTurret.turretspeedP = 10;
			subTurret.traverseSound = null;
			
			subturretpos = new Vector3d(0.4,2.8,0.2);
			subTurret.onMotherPos = subturretpos;
			subTurret.cannonPos = new Vector3d(0,0.285,1.2);
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
			
			subTurret.magazineMax = 20;
			subTurret.reloadSetting = 100;
			subTurret.flashoffset = 0.5f;
		}
		subturret_is_mainTurret_child = true;
		mainTurret.addchild_NOTtriggerLinked(subTurret);
		turrets = new TurretObj[]{mainTurret,subTurret};
		armor = 15;
		armor_Side_cof = 1;
		armor_Back_cof = 1;
	}
	protected void applyEntityAttributes()
	{
		maxHealth = 250;
		super.applyEntityAttributes();
	}
	
	public void onUpdate(){
		super.onUpdate();
		if(!worldObj.isRemote){
			subturretrotationYaw = (float) subTurret.turretrotationYaw;
			subturretrotationPitch = (float) subTurret.turretrotationPitch;
		}else {
			subTurret.turretrotationYaw = subturretrotationYaw;
			subTurret.turretrotationPitch = subturretrotationPitch;
		}
	}
}
