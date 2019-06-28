package hmgww2.entity;


import hmvehicle.entity.parts.turrets.TurretObj;
import net.minecraft.world.World;

import javax.vecmath.Vector3d;

public class EntityJPN_FighterA extends EntityJPN_FighterBase
{
	public EntityJPN_FighterA(World par1World)
	{
		super(par1World);
		this.setSize(5f, 3f);
		this.maxHealth = 500;
//		nboundingbox = new ModifiedBoundingBox(-20,-20,-20,20,20,20,0,0,-6.27,2.5,5,19);
//		nboundingbox.rot.set(this.bodyRot);
//		proxy.replaceBoundingbox(this,nboundingbox);
//		((ModifiedBoundingBox)this.boundingBox).update(this.posX,this.posY,this.posZ);
		ignoreFrustumCheck = true;
		baseLogic.throttle_gearDown = 1.7f;
		baseLogic.throttle_Max = 2.7f;
		baseLogic.Torpedo_bomber = true;
		baseLogic.rollspeed = 0.6f;
		baseLogic.pitchspeed = 0.2f;
		baseLogic.yawspeed = 0.3f;
		baseLogic.sholdUseMain_ToG = true;
		
		baseLogic.startDive = 45;
		baseLogic.maxDive = 30;
		baseLogic.cruiseALT = 40;
		baseLogic.maxClimb = -12;
		baseLogic.maxbank = 60;
		baseLogic.minALT = 5;
		baseLogic.soundname = "hmgww2:hmgww2.sound_pera";
		
		baseLogic.camerapos = new double[]{0,2.3,0};
		baseLogic.rotcenter = new double[]{0,1.5,0};
		
		baseLogic.onground_pitch = -10;
		{
			TurretObj cannon1 = new TurretObj(worldObj);
			{
				cannon1.motherRotCenter = new Vector3d(baseLogic.rotcenter);
				cannon1.cannonPos = new Vector3d(-1.45, 0.72, -0.65);
				cannon1.flashoffset = 0;
				cannon1.turretspeedY = 5;
				cannon1.turretspeedP = 8;
				cannon1.turretanglelimtPitchMax = 5;
				cannon1.turretanglelimtPitchmin = -80;
				cannon1.traverseSound = null;
				cannon1.currentEntity = this;
				cannon1.powor = 15;
				cannon1.ex = 0.5f;
				cannon1.cycle_setting = 3;
				cannon1.firesound = "handmadeguns:handmadeguns.20mmfire";
				cannon1.magazineMax = 50;
				cannon1.magazinerem = 50;
				cannon1.spread = 3;
				cannon1.speed = 8;
				cannon1.canex = false;
				cannon1.guntype = 2;
			}
			
			{
				TurretObj cannon2 = new TurretObj(worldObj);
				cannon2.motherRotCenter = new Vector3d(baseLogic.rotcenter);
				cannon2.cannonPos = new Vector3d(1.45, 0.72, -0.65);
				cannon2.flashoffset = 0;
				cannon2.turretspeedY = 5;
				cannon2.turretspeedP = 8;
				cannon2.turretanglelimtPitchMax = 5;
				cannon2.turretanglelimtPitchmin = -80;
				cannon2.traverseSound = null;
				cannon2.currentEntity = this;
				cannon2.powor = 15;
				cannon2.ex = 0.5F;
				cannon2.cycle_setting = 3;
				cannon2.firesound = "handmadeguns:handmadeguns.20mmfire";
				cannon2.magazineMax = 50;
				cannon2.magazinerem = 50;
				cannon2.spread = 3;
				cannon2.speed = 8;
				cannon2.canex = false;
				cannon2.guntype = 2;
				cannon1.addchild_triggerLinked(cannon2);
			}
			baseLogic.subTurret = cannon1;
		}
		baseLogic.mainTurret = new TurretObj(worldObj);
		{
			baseLogic.mainTurret.onMotherPos = new Vector3d(0,-1,-2);
			baseLogic.mainTurret.motherRotCenter = new Vector3d(0,1.2,0);
			baseLogic.mainTurret.cannonPos = new Vector3d(0,-0.1,-2);
			baseLogic.mainTurret.turretspeedY = 5;
			baseLogic.mainTurret.turretspeedP = 8;
			baseLogic.mainTurret.turretanglelimtPitchMax = 5;
			baseLogic.mainTurret.turretanglelimtPitchmin = -80;
			baseLogic.mainTurret.traverseSound = null;
			baseLogic.mainTurret.currentEntity = this;
			baseLogic.mainTurret.powor = 500;
			baseLogic.mainTurret.ex = 4.0F;
			baseLogic.mainTurret.cycle_setting = 2;
			baseLogic.mainTurret.firesound = null;
			baseLogic.mainTurret.flashName = null;
			baseLogic.mainTurret.spread = 0;
			baseLogic.mainTurret.speed = 0.1f;
			baseLogic.mainTurret.acceler = 0.1f;
			baseLogic.mainTurret.canex = true;
			baseLogic.mainTurret.guntype = 11;
			baseLogic.mainTurret.magazineMax = 1;
			baseLogic.mainTurret.magazinerem = 1;
			baseLogic.mainTurret.reloadSetting = 500;
			baseLogic.mainTurret.bulletmodel = "byfrou01_torpedo";
		}
	}
	protected void applyEntityAttributes()
	{
		maxHealth = 300;
		super.applyEntityAttributes();
	}
}
