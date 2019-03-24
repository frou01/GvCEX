package hmgww2.entity;


import hmggvcmob.entity.TurretObj;
import net.minecraft.world.World;

import javax.vecmath.Vector3d;

public class EntityUSSR_FighterA extends EntityUSSR_FighterBase
{
	// public int type;
	
    public EntityUSSR_FighterA(World par1World)
    {
	    super(par1World);
	    this.setSize(5f, 3f);
//		nboundingbox = new ModifiedBoundingBox(-20,-20,-20,20,20,20,0,0,-6.27,2.5,5,19);
//		nboundingbox.rot.set(this.bodyRot);
//		proxy.replaceBoundingbox(this,nboundingbox);
//		((ModifiedBoundingBox)this.boundingBox).updateOBB(this.posX,this.posY,this.posZ);
	    ignoreFrustumCheck = true;
	    this.fireCycle1 = 1;
	    baseLogic.speedfactor = 0.0035f;
	    baseLogic.throttle_gearDown = 1.7f;
	    baseLogic.throttle_Max = 4.0f;
	    baseLogic.rollspeed = 0.2f;
	    baseLogic.pitchspeed = 0.15f;
	    baseLogic.yawspeed = 0.15f;
	    baseLogic.maxDive = 60;
	    baseLogic.maxClimb = -17;
	    baseLogic.maxbank = 30;
	    baseLogic.soundname = "hmgww2:hmgww2.sound_pera";
	
	    baseLogic.camerapos = new double[]{0,2.3,0};
	    baseLogic.rotcenter = new double[]{0,1.5,0};
	
	    baseLogic.onground_pitch = -10;
	    TurretObj cannon1 = new TurretObj(worldObj);
	    {
		    cannon1.onmotherPos = new Vector3d(1.35,1.2,-2);
		    cannon1.motherRotCenter = new Vector3d(0,1.5,0);
		    cannon1.cannonpos = new Vector3d(0,1.5,-2);
		    cannon1.flushoffset = 0;
		    cannon1.turretspeedY = 5;
		    cannon1.turretspeedP = 8;
		    cannon1.turretanglelimtPitchMax = 5;
		    cannon1.turretanglelimtPitchmin = -80;
		    cannon1.traverseSound = null;
		    cannon1.currentEntity = this;
		    cannon1.powor = 37;
		    cannon1.ex = 0.5f;
		    cannon1.cycle_setting = 4;
		    cannon1.firesound = "hmgww2:hmgww2.fire_30mm";
		    cannon1.magazineMax = 50;
		    cannon1.magazinerem = 50;
		    cannon1.spread = 1;
		    cannon1.speed = 4;
		    cannon1.canex = true;
		    cannon1.guntype = 2;
		
		    {
			    TurretObj cannon2 = new TurretObj(worldObj);
			    cannon2.onmotherPos = new Vector3d(-2.7,0,0);
			    cannon2.motherRotCenter = new Vector3d(0,0,0);
			    cannon2.cannonpos = new Vector3d(0,1.5,-2);
			    cannon2.flushoffset = 0;
			    cannon2.turretspeedY = 5;
			    cannon2.turretspeedP = 8;
			    cannon2.turretanglelimtPitchMax = 5;
			    cannon2.turretanglelimtPitchmin = -80;
			    cannon2.traverseSound = null;
			    cannon2.currentEntity = this;
			    cannon2.powor = 37;
			    cannon2.ex = 0.5F;
			    cannon2.cycle_setting = 4;
			    cannon2.firesound = "hmgww2:hmgww2.fire_30mm";
			    cannon2.magazineMax = 50;
			    cannon2.magazinerem = 50;
			    cannon2.spread = 5;
			    cannon2.speed = 4;
			    cannon2.canex = true;
			    cannon2.guntype = 2;
			    cannon1.addchild(cannon2);
		    }
		    {
			    TurretObj cannon3 = new TurretObj(worldObj);
			    cannon3.onmotherPos = new Vector3d(-1.25,0.95,0);
			    cannon3.motherRotCenter = new Vector3d(0,0,0);
			    cannon3.cannonpos = new Vector3d(0,2.15,-2);
			    cannon3.turretspeedY = 5;
			    cannon3.turretspeedP = 8;
			    cannon3.turretanglelimtPitchMax = 5;
			    cannon3.turretanglelimtPitchmin = -80;
			    cannon3.traverseSound = null;
			    cannon3.currentEntity = this;
			    cannon3.powor = 8;
			    cannon3.ex = 0.5f;
			    cannon3.cycle_setting = 0;
			    cannon3.firesound = "handmadeguns:handmadeguns.fire";
			    cannon3.spread = 5;
			    cannon3.speed = 5;
			    cannon3.magazineMax = 100;
			    cannon3.magazinerem = 100;
			    cannon3.reloadTimer = 70;
			    cannon3.canex = true;
			    cannon3.guntype = 0;
			    cannon1.addchild(cannon3);
		    }
		    {
			    TurretObj cannon4 = new TurretObj(worldObj);
			    cannon4.onmotherPos = new Vector3d(-1.45,0.95,0);
			    cannon4.motherRotCenter = new Vector3d(0,0,0);
			    cannon4.cannonpos = new Vector3d(0,2.15,-2);
			    cannon4.turretspeedY = 5;
			    cannon4.turretspeedP = 8;
			    cannon4.turretanglelimtPitchMax = 5;
			    cannon4.turretanglelimtPitchmin = -80;
			    cannon4.traverseSound = null;
			    cannon4.currentEntity = this;
			    cannon4.powor = 8;
			    cannon4.ex = 0.5f;
			    cannon4.cycle_setting = 0;
			    cannon4.firesound = "handmadeguns:handmadeguns.fire";
			    cannon4.spread = 5;
			    cannon4.speed = 5;
			    cannon4.magazineMax = 100;
			    cannon4.magazinerem = 100;
			    cannon4.reloadTimer = 70;
			    cannon4.canex = true;
			    cannon4.guntype = 0;
			    cannon1.addchild(cannon4);
		    }
	    }
	    baseLogic.mainTurret = cannon1;
	    baseLogic.subTurret = new TurretObj(worldObj);
	    {
		    baseLogic.subTurret.onmotherPos = new Vector3d(0,-0.1,-2);
		    baseLogic.subTurret.motherRotCenter = new Vector3d(0,1.2,0);
		    baseLogic.subTurret.cannonpos = new Vector3d(0,-0.1,-2);
		    baseLogic.subTurret.turretspeedY = 5;
		    baseLogic.subTurret.turretspeedP = 8;
		    baseLogic.subTurret.turretanglelimtPitchMax = 5;
		    baseLogic.subTurret.turretanglelimtPitchmin = -80;
		    baseLogic.subTurret.traverseSound = null;
		    baseLogic.subTurret.currentEntity = this;
		    baseLogic.subTurret.powor = 15;
		    baseLogic.subTurret.ex = 4.0F;
		    baseLogic.subTurret.cycle_setting = 2;
		    baseLogic.subTurret.firesound = null;
		    baseLogic.subTurret.bulletmodel = "byfrou01_Bomb";
		    baseLogic.subTurret.flushName = null;
		    baseLogic.subTurret.spread = 0;
		    baseLogic.subTurret.speed = 0;
		    baseLogic.subTurret.canex = true;
		    baseLogic.subTurret.guntype = 2;
		    baseLogic.subTurret.magazineMax = 3;
		    baseLogic.subTurret.magazinerem = 3;
		    baseLogic.subTurret.reloadSetting = 500;
		    TurretObj bomb2 = new TurretObj(worldObj);
		    bomb2.onmotherPos = new Vector3d(0,-0.1,-2);
		    bomb2.motherRotCenter = new Vector3d(0,1.2,0);
		    bomb2.cannonpos = new Vector3d(0,-0.1,-2);
		    bomb2.turretspeedY = 5;
		    bomb2.turretspeedP = 8;
		    bomb2.turretanglelimtPitchMax = 5;
		    bomb2.turretanglelimtPitchmin = -80;
		    bomb2.traverseSound = null;
		    bomb2.currentEntity = this;
		    bomb2.powor = 15;
		    bomb2.ex = 4.0F;
		    bomb2.cycle_setting = 2;
		    bomb2.firesound = null;
		    bomb2.bulletmodel = "byfrou01_Bomb";
		    bomb2.flushName = null;
		    bomb2.spread = 0;
		    bomb2.speed = 0;
		    bomb2.canex = true;
		    bomb2.guntype = 2;
		    bomb2.magazineMax = 3;
		    bomb2.magazinerem = 3;
		    bomb2.reloadSetting = 500;
		    baseLogic.subTurret.addchild(bomb2);
	    }
    }
    
}
