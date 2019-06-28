package hmgww2.entity;


import hmvehicle.entity.parts.IMultiTurretVehicle;
import hmvehicle.entity.parts.SeatInfo;
import hmvehicle.entity.parts.turrets.TurretObj;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import javax.vecmath.Vector3d;
import java.util.ArrayList;

import static hmvehicle.Utils.addAllTurret;

public class EntityUSSR_FighterA extends EntityUSSR_FighterBase implements IMultiTurretVehicle
{
	// public int type;
	TurretObj turret1;
	public EntityUSSR_FighterA(World par1World)
    {
	    super(par1World);
	    this.setSize(5f, 3f);
	    ignoreFrustumCheck = true;
	    armor = 10;
	    baseLogic.throttle_gearDown = 1.7f;
	    baseLogic.throttle_Max = 3.0f;
	    baseLogic.rollspeed = 0.1f;
	    baseLogic.pitchspeed = 0.2f;
	    baseLogic.yawspeed = 0.15f;
	    baseLogic.maxDive = 70;
	    baseLogic.startDive =20;
	    baseLogic.maxClimb = -17;
	    baseLogic.maxbank = 30;
	    baseLogic.cruiseALT = 30;
	    baseLogic.minALT = 10;
	    baseLogic.sholdUseMain_ToG = true;
	    baseLogic.soundname = "hmgww2:hmgww2.sound_pera";
	    baseLogic.pitchsighwidthmax = 20;
	    baseLogic.pitchsighwidthmin = -20;
	    baseLogic.yawsightwidthmax = 15;
	    baseLogic.yawsightwidthmin = -15;
	
	    baseLogic.camerapos = new double[]{0,2.6,0.5};
	    baseLogic.rotcenter = new double[]{0,1.5,0};
	    baseLogic.riddenByEntities = new Entity[2];
	    baseLogic.riddenByEntitiesInfo = new SeatInfo[2];
	    baseLogic.riddenByEntitiesInfo[0] = new SeatInfo();
	    baseLogic.riddenByEntitiesInfo_zoom[0] = new SeatInfo();
	    baseLogic.riddenByEntitiesInfo[0].pos = baseLogic.camerapos;
	    baseLogic.riddenByEntitiesInfo_zoom[0].pos = new double[]{1,2.6,0};
	    
	    
	    baseLogic.riddenByEntitiesInfo[1] = new SeatInfo();
	    baseLogic.riddenByEntitiesInfo[1].pos = new double[]{0,2.6,1};
	
	    baseLogic.onground_pitch = -10;
	    TurretObj cannon1 = new TurretObj(worldObj);
	    {
		    cannon1.onMotherPos = new Vector3d(2.4,1.397,-1.933);
		    cannon1.cannonPos = new Vector3d(0,0,0);
		    cannon1.flashoffset = 0;
		    cannon1.turretspeedY = 5;
		    cannon1.turretspeedP = 8;
		    cannon1.turretanglelimtPitchMax = 5;
		    cannon1.turretanglelimtPitchmin = -80;
		    cannon1.traverseSound = null;
		    cannon1.currentEntity = this;
		    cannon1.powor = 27;
		    cannon1.ex = 0.5f;
		    cannon1.cycle_setting = 1;
		    cannon1.flashscale = 2;
		    cannon1.firesound = "handmadeguns:handmadeguns.20mmfire";
		    cannon1.magazineMax = 50;
		    cannon1.magazinerem = 50;
		    cannon1.spread = 2.5f;
		    cannon1.speed = 8;
		    cannon1.canex = false;
		    cannon1.guntype = 2;
		
		    {
			    TurretObj cannon2 = new TurretObj(worldObj);
			    cannon2.onMotherPos = new Vector3d(-2.4,1.397,-1.933);
			    cannon2.motherRotCenter = new Vector3d(0,0,0);
			    cannon2.flashoffset = 0;
			    cannon2.turretspeedY = 5;
			    cannon2.turretspeedP = 8;
			    cannon2.turretanglelimtPitchMax = 5;
			    cannon2.turretanglelimtPitchmin = -80;
			    cannon2.traverseSound = null;
			    cannon2.currentEntity = this;
			    cannon2.powor = 15;
			    cannon2.ex = 0.5F;
			    cannon2.cycle_setting = 1;
			    cannon2.flashscale = 2;
			    cannon2.firesound = "handmadeguns:handmadeguns.20mmfire";
			    cannon2.magazineMax = 50;
			    cannon2.magazinerem = 50;
			    cannon2.spread = 2.5f;
			    cannon2.speed = 8;
			    cannon2.canex = false;
			    cannon2.guntype = 2;
			    cannon1.addbrother(cannon2);
		    }
		    {
			    TurretObj cannon3 = new TurretObj(worldObj);
			    cannon3.onMotherPos = new Vector3d(2.3,1.397,-1.933);
			    cannon3.motherRotCenter = new Vector3d(0,0,0);
			    cannon3.turretspeedY = 5;
			    cannon3.turretspeedP = 8;
			    cannon3.turretanglelimtPitchMax = 5;
			    cannon3.turretanglelimtPitchmin = -80;
			    cannon3.traverseSound = null;
			    cannon3.currentEntity = this;
			    cannon3.powor = 8;
			    cannon3.cycle_setting = 0;
			    cannon3.flashscale = 1;
			    cannon3.firesound = "handmadeguns:handmadeguns.fire";
			    cannon3.spread = 2;
			    cannon3.speed = 10;
			    cannon3.magazineMax = 100;
			    cannon3.magazinerem = 100;
			    cannon3.reloadTimer = 70;
			    cannon3.guntype = 0;
			    cannon1.addbrother(cannon3);
		    }
		    {
			    TurretObj cannon4 = new TurretObj(worldObj);
			    cannon4.onMotherPos = new Vector3d(-2.3,1.397,-1.933);
			    cannon4.motherRotCenter = new Vector3d(0,0,0);
			    cannon4.turretspeedY = 5;
			    cannon4.turretspeedP = 8;
			    cannon4.turretanglelimtPitchMax = 5;
			    cannon4.turretanglelimtPitchmin = -80;
			    cannon4.traverseSound = null;
			    cannon4.currentEntity = this;
			    cannon4.powor = 8;
			    cannon4.cycle_setting = 0;
			    cannon4.flashscale = 1;
			    cannon4.firesound = "handmadeguns:handmadeguns.fire";
			    cannon4.spread = 2;
			    cannon4.speed = 10;
			    cannon4.magazineMax = 100;
			    cannon4.magazinerem = 100;
			    cannon4.reloadTimer = 70;
			    cannon4.guntype = 0;
			    cannon1.addbrother(cannon4);
		    }
		
		    {
			    turret1 = new TurretObj(worldObj);
			    turret1.onMotherPos = new Vector3d(0,2.2804,1.8456);
			    turret1.turretPitchCenterpos = new Vector3d(0,0.0943,0.1269);
			    turret1.cannonPos = new Vector3d(0,0.1463,-0.8696);
			    turret1.turretanglelimtYawmin = 60;
			    turret1.turretanglelimtYawMax = -60;
			    turret1.turretanglelimtPitchmin = -60;
			    turret1.turretanglelimtPitchMax =  10;
			    turret1.flashoffset = 0;
			    turret1.turretspeedY = 5;
			    turret1.turretspeedP = 8;
			    turret1.turretanglelimtPitchMax = 5;
			    turret1.turretanglelimtPitchmin = -80;
			    turret1.traverseSound = null;
			    turret1.currentEntity = this;
			    turret1.powor = 15;
			    turret1.ex = 0.5F;
			    turret1.cycle_setting = 1;
			    turret1.flashscale = 2;
			    turret1.firesound = "handmadeguns:handmadeguns.20mmfire";
			    turret1.magazineMax = 50;
			    turret1.magazinerem = 50;
			    turret1.spread = 2;
			    turret1.speed = 8;
			    turret1.canex = false;
			    turret1.guntype = 2;
			    baseLogic.riddenByEntitiesInfo[1].gun = turret1;
			    baseLogic.riddenByEntitiesInfo[1].hasGun = true;
		    }
	    }
	    baseLogic.subTurret = cannon1;
	    baseLogic.mainTurret = new TurretObj(worldObj);
	    {
		    baseLogic.mainTurret.onMotherPos = new Vector3d(0,0,0);
		    baseLogic.mainTurret.cannonPos = new Vector3d(-1,-2,1);
		    baseLogic.mainTurret.motherRotCenter = new Vector3d(0,1.2,0);
		    baseLogic.mainTurret.turretspeedY = 5;
		    baseLogic.mainTurret.turretspeedP = 8;
		    baseLogic.mainTurret.turretanglelimtPitchMax = 5;
		    baseLogic.mainTurret.turretanglelimtPitchmin = -80;
		    baseLogic.mainTurret.traverseSound = null;
		    baseLogic.mainTurret.currentEntity = this;
		    baseLogic.mainTurret.powor = 80;
		    baseLogic.mainTurret.ex = 0.5f;
		    baseLogic.mainTurret.canex = false;
		    baseLogic.mainTurret.burstmaxRound = 24;
		    baseLogic.mainTurret.cycle_setting = 0;
		    baseLogic.mainTurret.firesound = null;
		    baseLogic.mainTurret.bulletmodel = "byfrou01_Bomb";
		    baseLogic.mainTurret.flashName = null;
		    baseLogic.mainTurret.spread = 4;
		    baseLogic.mainTurret.speed = 0;
		    baseLogic.mainTurret.canex = false;
		    baseLogic.mainTurret.guntype = 2;
		    baseLogic.mainTurret.magazineMax = 2;
		    baseLogic.mainTurret.magazinerem = 2;
		    baseLogic.mainTurret.reloadSetting = 600;
		    TurretObj bomb2 = new TurretObj(worldObj);
		    bomb2.onMotherPos = new Vector3d(0,0,0);
		    bomb2.cannonPos = new Vector3d(1,-2,1);
		    bomb2.motherRotCenter = new Vector3d(0,1.2,0);
		    bomb2.turretspeedY = 5;
		    bomb2.turretspeedP = 8;
		    bomb2.turretanglelimtPitchMax = 5;
		    bomb2.turretanglelimtPitchmin = -80;
		    bomb2.traverseSound = null;
		    bomb2.currentEntity = this;
		    bomb2.powor = 80;
		    bomb2.ex = 0.5f;
		    bomb2.canex = false;
		    bomb2.burstmaxRound = 24;
		    bomb2.cycle_setting = 0;
		    bomb2.firesound = null;
		    bomb2.bulletmodel = "byfrou01_Bomb";
		    bomb2.flashName = null;
		    bomb2.spread = 4;
		    bomb2.speed = 0;
		    bomb2.canex = false;
		    bomb2.guntype = 2;
		    bomb2.magazineMax = 2;
		    bomb2.magazinerem = 2;
		    bomb2.reloadSetting = 600;
		    baseLogic.mainTurret.addchild_triggerLinked(bomb2);
	    }
    }
	protected void applyEntityAttributes()
	{
		maxHealth = 300;
		super.applyEntityAttributes();
	}
	
	@Override
	public TurretObj[] getmainTurrets() {
		if(baseLogic.mainTurrets == null) {
			ArrayList<TurretObj> turrets = new ArrayList<TurretObj>();
			addAllTurret(turrets, baseLogic.mainTurret);
			baseLogic.mainTurrets = turrets.toArray(new TurretObj[turrets.size()]);
		}
		return baseLogic.mainTurrets;
	}
	
	@Override
	public TurretObj[] getsubTurrets() {
		if(baseLogic.subTurrets == null) {
			ArrayList<TurretObj> turrets = new ArrayList<TurretObj>();
			addAllTurret(turrets, baseLogic.subTurret);
			baseLogic.subTurrets = turrets.toArray(new TurretObj[turrets.size()]);
		}
		return baseLogic.subTurrets;
	}
	
	@Override
	public TurretObj[] getTurrets() {
		if(baseLogic.turrets == null) {
			ArrayList<TurretObj> turrets = new ArrayList<TurretObj>();
			addAllTurret(turrets, turret1);
			addAllTurret(turrets, baseLogic.mainTurret);
			addAllTurret(turrets, baseLogic.subTurret);
			baseLogic.turrets = turrets.toArray(new TurretObj[turrets.size()]);
		}
		return baseLogic.turrets;
	}
}
