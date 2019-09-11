package hmgww2.entity.planes;


import handmadevehicle.entity.parts.SeatInfo;
import handmadevehicle.entity.parts.turrets.TurretObj;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import javax.vecmath.Vector3d;

public class EntityUSSR_Fighter extends EntityUSSR_FighterBase
{
	// public int type;
	
    public EntityUSSR_Fighter(World par1World)
    {
	    super(par1World);
	    this.setSize(5f, 3f);
//		nboundingbox = new ModifiedBoundingBox(-20,-20,-20,20,20,20,0,0,-6.27,2.5,5,19);
//		nboundingbox.rot.set(this.bodyRot);
//		proxy.replaceBoundingbox(this,nboundingbox);
//		((ModifiedBoundingBox)this.boundingBox).updateOBB(this.posX,this.posY,this.posZ);
	    ignoreFrustumCheck = true;
	    armor = 5;
	    baseLogic.prefab_vehicle.throttle_Max = 4.0f;
	    baseLogic.prefab_vehicle.rollspeed = 0.7f;
	    baseLogic.prefab_vehicle.pitchspeed = 0.4f;
	    baseLogic.prefab_vehicle.yawspeed = 0.1f;
	    baseLogic.prefab_vehicle.maxDive = 60;
	    baseLogic.prefab_vehicle.startDive = 30;
	    baseLogic.prefab_vehicle.maxClimb = -25;
	    baseLogic.prefab_vehicle.maxbank = 50;
	    baseLogic.prefab_vehicle.soundname = "hmgww2:hmgww2.sound_pera";
	    
	    baseLogic.prefab_vehicle.camerapos = new double[]{0,1.711,-0.4223};
	    baseLogic.prefab_vehicle.rotcenter = new double[]{0,1.1,0};
	    baseLogic.riddenByEntities = new Entity[1];
	    baseLogic.seatInfos = new SeatInfo[1];
	    baseLogic.seatInfos[0] = new SeatInfo();
	    baseLogic.seatInfos_zoom[0] = new SeatInfo();
	    baseLogic.seatInfos[0].pos = baseLogic.prefab_vehicle.camerapos;
	    baseLogic.seatInfos_zoom[0].pos = baseLogic.prefab_vehicle.camerapos;
	
	    baseLogic.onground_pitch = -12.08f;
	    baseLogic.prefab_vehicle.useMain_withSub = true;
	    baseLogic.mainTurret = new TurretObj(worldObj);
	    {
		    baseLogic.mainTurret.onMotherPos = new Vector3d(0,0,0);
		    baseLogic.mainTurret.motherRotCenter = new Vector3d(0,1.2,0);
		    baseLogic.mainTurret.cannonPos = new Vector3d(0,1.2,-2);
		    baseLogic.mainTurret.turretspeedY = 5;
		    baseLogic.mainTurret.turretspeedP = 8;
		    baseLogic.mainTurret.turretanglelimtPitchMax = 5;
		    baseLogic.mainTurret.turretanglelimtPitchmin = -80;
		    baseLogic.mainTurret.traverseSound = null;
		    baseLogic.mainTurret.currentEntity = this;
		    baseLogic.mainTurret.powor = 20;
		    baseLogic.mainTurret.ex = 0.1f;
		    baseLogic.mainTurret.cycle_setting = 1;
		    baseLogic.mainTurret.flashscale = 2;
		    baseLogic.mainTurret.firesound = "handmadeguns:handmadeguns.20mmfire";
		    baseLogic.mainTurret.flashoffset = 1;
		    baseLogic.mainTurret.spread = 5;
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
		    baseLogic.subTurret.motherRotCenter = new Vector3d(0,1.2,0);
		    baseLogic.subTurret.multicannonPos = new Vector3d[2];
		    baseLogic.subTurret.multicannonPos[0] = new Vector3d(0.24,1.47,-2);
		    baseLogic.subTurret.multicannonPos[1] = new Vector3d(-0.24,1.47,-2);
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
		    baseLogic.subTurret.spread = 10;
		    baseLogic.subTurret.speed = 10;
		    baseLogic.subTurret.canex = true;
		    baseLogic.subTurret.magazineMax = 100;
		    baseLogic.subTurret.magazinerem = 100;
		    baseLogic.subTurret.reloadTimer = 70;
		    baseLogic.subTurret.guntype = 0;
		    baseLogic.subTurret.fireAll_child = false;
	    }
    }
	protected void applyEntityAttributes()
	{
		maxHealth = 80;
		super.applyEntityAttributes();
	}
}
