package hmgww2.entity;


import hmvehicle.entity.parts.turrets.TurretObj;
import net.minecraft.world.World;

import javax.vecmath.Vector3d;

public class EntityGER_FighterA extends EntityGER_FighterBase
{
	// public int type;
	
	public EntityGER_FighterA(World par1World)
	{
		super(par1World);
		this.setSize(5f, 3f);
		this.maxHealth = 500;
//		nboundingbox = new ModifiedBoundingBox(-20,-20,-20,20,20,20,0,0,-6.27,2.5,5,19);
//		nboundingbox.rot.set(this.bodyRot);
//		proxy.replaceBoundingbox(this,nboundingbox);
//		((ModifiedBoundingBox)this.boundingBox).update(this.posX,this.posY,this.posZ);
		armor = 5;
		ignoreFrustumCheck = true;
		baseLogic.throttle_gearDown = 1.7f;
		baseLogic.throttle_Max = 3.0f;
		baseLogic.rollspeed = 0.2f;
		baseLogic.pitchspeed = 0.4f;
		baseLogic.yawspeed = 0.15f;
		baseLogic.Dive_bombing = true;
		baseLogic.sholdUseMain_ToG = true;
		baseLogic.startDive = 40;
		baseLogic.maxDive = 85;
		baseLogic.cruiseALT = 80;
		baseLogic.maxClimb = -25;
		baseLogic.maxbank = 40;
		baseLogic.minALT = 30;
		baseLogic.soundname = "hmgww2:hmgww2.sound_pera";
		baseLogic.pitchsighwidthmax = 20;
		baseLogic.pitchsighwidthmin = -20;
		baseLogic.yawsightwidthmax = 15;
		baseLogic.yawsightwidthmin = -15;
		
		baseLogic.camerapos = new double[]{0,2.1,-0.5};
		baseLogic.rotcenter = new double[]{0,1.5,0};
		
		baseLogic.onground_pitch = -10;
		TurretObj cannon3 = new TurretObj(worldObj);
		{
			{
				cannon3.onMotherPos = new Vector3d(0,0,0);
				cannon3.motherRotCenter = new Vector3d(baseLogic.rotcenter);
				cannon3.cannonPos = new Vector3d(1.4,0.6,-2);
				cannon3.turretspeedY = 5;
				cannon3.turretspeedP = 8;
				cannon3.turretanglelimtPitchMax = 5;
				cannon3.turretanglelimtPitchmin = -80;
				cannon3.traverseSound = null;
				cannon3.currentEntity = this;
				cannon3.powor = 15;
				cannon3.ex = 0.5f;
				cannon3.cycle_setting = 1;
				cannon3.flashscale = 2;
				cannon3.firesound = "handmadeguns:handmadeguns.20mmfire";
				cannon3.flashoffset = 1;
				cannon3.spread = 2.5f;
				cannon3.speed = 8;
				cannon3.magazineMax = 50;
				cannon3.magazinerem = 50;
				cannon3.reloadTimer = 150;
				cannon3.canex = false;
				cannon3.guntype = 2;
			}
			{
				TurretObj cannon4 = new TurretObj(worldObj);
				cannon4.onMotherPos = new Vector3d(0,0,0);
				cannon4.motherRotCenter = new Vector3d(baseLogic.rotcenter);
				cannon4.cannonPos = new Vector3d(-1.4,0.6,-2);
				cannon4.turretspeedY = 5;
				cannon4.turretspeedP = 8;
				cannon4.turretanglelimtPitchMax = 5;
				cannon4.turretanglelimtPitchmin = -80;
				cannon4.traverseSound = null;
				cannon4.currentEntity = this;
				cannon4.powor = 15;
				cannon4.ex = 0.5f;
				cannon4.cycle_setting = 1;
				cannon4.flashscale = 2;
				cannon4.firesound = "handmadeguns:handmadeguns.20mmfire";
				cannon4.flashoffset = 1;
				cannon4.spread = 2.5f;
				cannon4.speed = 8;
				cannon4.magazineMax = 50;
				cannon4.magazinerem = 50;
				cannon4.reloadTimer = 150;
				cannon4.canex = false;
				cannon4.guntype = 2;
				cannon3.addchild_triggerLinked(cannon4);
			}
		}
		baseLogic.subTurret = cannon3;
		baseLogic.mainTurret = new TurretObj(worldObj);
		{
			baseLogic.mainTurret.onMotherPos = new Vector3d(0,-1,0);
			baseLogic.mainTurret.motherRotCenter = new Vector3d(baseLogic.rotcenter);
			baseLogic.mainTurret.turretspeedY = 5;
			baseLogic.mainTurret.turretspeedP = 8;
			baseLogic.mainTurret.turretanglelimtPitchMax = 5;
			baseLogic.mainTurret.turretanglelimtPitchmin = -80;
			baseLogic.mainTurret.traverseSound = null;
			baseLogic.mainTurret.currentEntity = this;
			baseLogic.mainTurret.powor = 1000;
			baseLogic.mainTurret.ex = 8.0F;
			baseLogic.mainTurret.cycle_setting = 0;
			baseLogic.mainTurret.firesound = null;
			baseLogic.mainTurret.bulletmodel = "byfrou01_Bomb";
			baseLogic.mainTurret.flashName = null;
			baseLogic.mainTurret.spread = 30;
			baseLogic.mainTurret.speed = 0.1f;
			baseLogic.mainTurret.canex = true;
			baseLogic.mainTurret.guntype = 2;
			baseLogic.mainTurret.magazineMax = 1;
			baseLogic.mainTurret.magazinerem = 1;
			baseLogic.mainTurret.reloadSetting = 500;
		}
	}
	protected void applyEntityAttributes()
	{
		maxHealth = 450;
		super.applyEntityAttributes();
	}
}
