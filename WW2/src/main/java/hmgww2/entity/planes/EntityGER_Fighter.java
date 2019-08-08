package hmgww2.entity.planes;


import handmadevehicle.entity.parts.SeatInfo;
import handmadevehicle.entity.parts.turrets.TurretObj;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import javax.vecmath.Vector3d;

public class EntityGER_Fighter extends EntityGER_FighterBase
{
	// public int type;
	
	public EntityGER_Fighter(World par1World)
	{
		super(par1World);
		this.setSize(5f, 3f);
//		nboundingbox = new ModifiedBoundingBox(-20,-20,-20,20,20,20,0,0,-6.27,2.5,5,19);
//		nboundingbox.rot.set(this.bodyRot);
//		proxy.replaceBoundingbox(this,nboundingbox);
//		((ModifiedBoundingBox)this.boundingBox).update(this.posX,this.posY,this.posZ);
		ignoreFrustumCheck = true;
		armor = 4;
		baseLogic.planeInfo.throttle_Max = 5.0f;
		baseLogic.planeInfo.rollspeed = 0.4f;
		baseLogic.planeInfo.pitchspeed = 0.4f;
		baseLogic.planeInfo.yawspeed = 0.15f;
		baseLogic.planeInfo.maxDive = 60;
		baseLogic.planeInfo.startDive = 30;
		baseLogic.planeInfo.cruiseALT = 120;
		baseLogic.planeInfo.maxClimb = -32;
		baseLogic.planeInfo.maxbank = 60;
		baseLogic.planeInfo.outSightCntMax = 200;
		baseLogic.planeInfo.soundname = "hmgww2:hmgww2.sound_pera";
		
		baseLogic.planeInfo.camerapos = new double[]{-0.04216,1.799,-0.9228};
		baseLogic.planeInfo.rotcenter = new double[]{0,1.1,0};
		baseLogic.riddenByEntities = new Entity[1];
		baseLogic.seatInfos = new SeatInfo[1];
		baseLogic.seatInfos[0] = new SeatInfo();
		baseLogic.seatInfos_zoom[0] = new SeatInfo();
		baseLogic.seatInfos[0].pos = baseLogic.planeInfo.camerapos;
		baseLogic.seatInfos_zoom[0].pos = baseLogic.planeInfo.camerapos;
		
		baseLogic.onground_pitch = -15;
		baseLogic.planeInfo.useMain_withSub = true;
		baseLogic.mainTurret = new TurretObj(worldObj);
		{
			baseLogic.mainTurret.onMotherPos = new Vector3d(0,0,0);
			baseLogic.mainTurret.motherRotCenter = new Vector3d(baseLogic.planeInfo.rotcenter);
			baseLogic.mainTurret.cannonPos = new Vector3d(0,1.5,-2);
			baseLogic.mainTurret.turretspeedY = 5;
			baseLogic.mainTurret.turretspeedP = 8;
			baseLogic.mainTurret.turretanglelimtPitchMax = 5;
			baseLogic.mainTurret.turretanglelimtPitchmin = -80;
			baseLogic.mainTurret.traverseSound = null;
			baseLogic.mainTurret.currentEntity = this;
			baseLogic.mainTurret.powor = 15;
			baseLogic.mainTurret.ex = 0.5f;
			baseLogic.mainTurret.cycle_setting = 1;
			baseLogic.mainTurret.flashscale = 2;
			baseLogic.mainTurret.firesound = "handmadeguns:handmadeguns.20mmfire";
			baseLogic.mainTurret.flashoffset = 1;
			baseLogic.mainTurret.spread = 2.5f;
			baseLogic.mainTurret.speed = 8;
			baseLogic.mainTurret.magazineMax = 50;
			baseLogic.mainTurret.magazinerem = 50;
			baseLogic.mainTurret.reloadTimer = 150;
			baseLogic.mainTurret.canex = false;
			baseLogic.mainTurret.guntype = 2;
		}
		baseLogic.subTurret = new TurretObj(worldObj);
		{
			baseLogic.subTurret.onMotherPos = new Vector3d(0,0,0);
			baseLogic.subTurret.motherRotCenter = new Vector3d(baseLogic.planeInfo.rotcenter);
			baseLogic.subTurret.cannonPos = new Vector3d(-0.2,1.64,-2.2);
			baseLogic.subTurret.turretspeedY = 5;
			baseLogic.subTurret.turretspeedP = 8;
			baseLogic.subTurret.turretanglelimtPitchMax = 5;
			baseLogic.subTurret.turretanglelimtPitchmin = -80;
			baseLogic.subTurret.traverseSound = null;
			baseLogic.subTurret.currentEntity = this;
			baseLogic.subTurret.powor = 8;
			baseLogic.subTurret.cycle_setting = 0;
			baseLogic.subTurret.flashscale = 1;
			baseLogic.subTurret.firesound = "handmadeguns:handmadeguns.fire";
			baseLogic.subTurret.flashoffset = 1;
			baseLogic.subTurret.spread = 1.5f;
			baseLogic.subTurret.speed = 10;
			baseLogic.subTurret.canex = true;
			baseLogic.subTurret.magazineMax = 100;
			baseLogic.subTurret.magazinerem = 100;
			baseLogic.subTurret.reloadTimer = 70;
			baseLogic.subTurret.guntype = 0;
			{
				TurretObj turretObj = new TurretObj(worldObj);
				turretObj.onMotherPos = new Vector3d(0,0,0);
				turretObj.motherRotCenter = new Vector3d(baseLogic.planeInfo.rotcenter);
				turretObj.cannonPos = new Vector3d(0.2,1.64,-2.2);
				turretObj.turretspeedY = 5;
				turretObj.turretspeedP = 8;
				turretObj.turretanglelimtPitchMax = 5;
				turretObj.turretanglelimtPitchmin = -80;
				turretObj.traverseSound = null;
				turretObj.currentEntity = this;
				turretObj.powor = 8;
				turretObj.cycle_setting = 0;
				turretObj.flashscale = 1;
				turretObj.firesound = "handmadeguns:handmadeguns.fire";
				turretObj.flashoffset = 1;
				turretObj.spread = 1.5f;
				turretObj.speed = 10;
				turretObj.canex = true;
				turretObj.magazineMax = 100;
				turretObj.magazinerem = 100;
				turretObj.reloadTimer = 70;
				turretObj.guntype = 0;
				baseLogic.subTurret.addchild_triggerLinked(turretObj);
			}
		}
	}
	protected void applyEntityAttributes()
	{
		maxHealth = 120;
		super.applyEntityAttributes();
	}
}
