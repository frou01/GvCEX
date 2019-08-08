package hmgww2.entity.planes;


import hmgww2.entity.EntityUSSR_S;
import handmadevehicle.entity.parts.IMultiTurretVehicle;
import handmadevehicle.entity.parts.SeatInfo;
import handmadevehicle.entity.parts.turrets.TurretObj;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import javax.vecmath.Vector3d;
import java.util.ArrayList;

import static handmadevehicle.Utils.addAllTurret;

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
	    baseLogic.planeInfo.throttle_gearDown = 1.7f;
	    baseLogic.planeInfo.throttle_Max = 3.0f;
	    baseLogic.planeInfo.rollspeed = 0.1f;
	    baseLogic.planeInfo.pitchspeed = 0.2f;
	    baseLogic.planeInfo.yawspeed = 0.1f;
	    baseLogic.planeInfo.maxDive = 65;
	    baseLogic.planeInfo.startDive = 30;
	    baseLogic.planeInfo.maxClimb = -17;
	    baseLogic.planeInfo.maxbank = 30;
	    baseLogic.planeInfo.cruiseALT = 75;
	    baseLogic.planeInfo.minALT = 10;
	    baseLogic.planeInfo.sholdUseMain_ToG = true;
	    baseLogic.planeInfo.soundname = "hmgww2:hmgww2.sound_pera";
	    baseLogic.planeInfo.pitchsighwidthmax = 20;
	    baseLogic.planeInfo.pitchsighwidthmin = -20;
	    baseLogic.planeInfo.yawsightwidthmax = 15;
	    baseLogic.planeInfo.yawsightwidthmin = -15;
	
	    baseLogic.planeInfo.camerapos = new double[]{0,2.528,-0.2805};
	    baseLogic.planeInfo.rotcenter = new double[]{0,1.5,0};
	    baseLogic.riddenByEntities = new Entity[2];
	    baseLogic.seatInfos = new SeatInfo[2];
	    baseLogic.seatInfos[0] = new SeatInfo();
	    baseLogic.seatInfos_zoom[0] = new SeatInfo();
	    baseLogic.seatInfos[0].pos = baseLogic.planeInfo.camerapos;
	    baseLogic.seatInfos_zoom[0].pos = baseLogic.planeInfo.camerapos;
	    
	    
	
	    baseLogic.onground_pitch = -10;
	    TurretObj cannon1 = new TurretObj(worldObj);
	    {
		    cannon1.onMotherPos = new Vector3d(0,0,0);
		    cannon1.motherRotCenter = new Vector3d(0,1.5,0);
		    cannon1.multicannonPos = new Vector3d[2];
		    cannon1.multicannonPos[0] = new Vector3d( 2.116,1.463,2.034);
		    cannon1.multicannonPos[1] = new Vector3d(-2.116,1.463,2.034);
		    cannon1.turretanglelimtPitchMax = 5;
		    cannon1.turretanglelimtPitchmin = -80;
		    cannon1.traverseSound = null;
		    cannon1.currentEntity = this;
		    cannon1.powor = 27;
		    cannon1.ex = 0.5f;
		    cannon1.cycle_setting = 1;
		    cannon1.flashName = "arrow";
		    cannon1.flashscale = 2;
		    cannon1.flashfuse = 1;
		    cannon1.flashoffset = 0;
		    cannon1.firesound = "handmadeguns:handmadeguns.20mmfire";
		    cannon1.magazineMax = 50;
		    cannon1.magazinerem = 50;
		    cannon1.spread = 2.5f;
		    cannon1.speed = 8;
		    cannon1.canex = false;
		    cannon1.fireAll_cannon = true;
		    cannon1.guntype = 2;
		    {
			    TurretObj cannon3 = new TurretObj(worldObj);
			    cannon3.onMotherPos = new Vector3d(0,0,0);
			    cannon3.motherRotCenter = new Vector3d(0,0,0);
			    cannon3.multicannonPos = new Vector3d[2];
			    cannon3.multicannonPos[0] = new Vector3d(2.726,1.330,1.800);
			    cannon3.multicannonPos[1] = new Vector3d(-2.726,1.330,1.800);
			    cannon3.turretspeedY = 5;
			    cannon3.turretspeedP = 8;
			    cannon3.turretanglelimtPitchMax = 5;
			    cannon3.turretanglelimtPitchmin = -80;
			    cannon3.traverseSound = null;
			    cannon3.currentEntity = this;
			    cannon3.powor = 8;
			    cannon3.cycle_setting = 0;
			    cannon3.flashName = "arrow";
			    cannon3.flashscale = 1;
			    cannon3.flashfuse = 1;
			    cannon3.flashoffset = 0;
			    cannon3.firesound = "handmadeguns:handmadeguns.fire";
			    cannon3.spread = 2;
			    cannon3.speed = 10;
			    cannon3.magazineMax = 100;
			    cannon3.magazinerem = 100;
			    cannon3.reloadTimer = 70;
			    cannon3.guntype = 0;
			    cannon3.fireAll_cannon = true;
			    cannon1.addchild_triggerLinked(cannon3);
		    }
		
		    {
			    turret1 = new TurretObj(worldObj);
			    turret1.onMotherPos = new Vector3d(0,0,1.149);
			    turret1.userOnBarrell = true;
			    turret1.turretPitchCenterpos = new Vector3d(0,2.4261,(1.149-0.9386));
			    turret1.motherRotCenter = new Vector3d(0,1.5,0);
			    turret1.cannonPos = new Vector3d(0,2.4304,0.8);
			    turret1.turretanglelimtYawmin = 95;
			    turret1.turretanglelimtYawMax = -95;
			    turret1.turretanglelimtPitchMax = 5;
			    turret1.turretanglelimtPitchmin = -80;
			    turret1.turretrotationYaw = 180;
			    turret1.flashoffset = 0;
			    turret1.turretspeedY = 10;
			    turret1.turretspeedP = 10;
			    turret1.traverseSound = null;
			    turret1.currentEntity = this;
			    turret1.powor = 15;
			    turret1.ex = 0.5F;
			    turret1.cycle_setting = 1;
			    turret1.flashName = "arrow";
			    turret1.flashscale = 2;
			    turret1.flashfuse = 1;
			    turret1.firesound = "handmadeguns:handmadeguns.dshk38Fire";
			    turret1.magazineMax = 150;
			    turret1.magazinerem = 150;
			    turret1.reloadSetting = 1000;
			    turret1.hasReflexSight = true;
			    turret1.spread = 6;
			    turret1.speed = 8;
			    turret1.canex = false;
			    turret1.guntype = 0;
			    baseLogic.seatInfos[1] = new SeatInfo();
			    baseLogic.seatInfos[1].pos = new double[]{-0.04768,2.552,1.3220};
			    baseLogic.seatInfos[1].maingun = turret1;
			    baseLogic.seatInfos[1].hasGun = true;
		    }
	    }
	    baseLogic.subTurret = cannon1;
	    baseLogic.mainTurret = new TurretObj(worldObj);
	    {
		    baseLogic.mainTurret.onMotherPos = new Vector3d(0,0,0);
		    baseLogic.mainTurret.cannonPos = new Vector3d(-1,-2,1);
		    baseLogic.mainTurret.motherRotCenter = new Vector3d(0,1.5,0);
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
		    bomb2.motherRotCenter = new Vector3d(0,0,0);
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
	public void onUpdate() {
		if (this.standalone()) {
			if (!worldObj.isRemote && rand.nextInt(100) == 1) {
				boolean flag = false;
				for (int id = 1; id < (baseLogic).riddenByEntities.length; id++) {
					if((baseLogic).riddenByEntities[id] == null)flag = true;
				}
				if(flag) {
					EntityUSSR_S entityUSSR_s = new EntityUSSR_S(worldObj);
					entityUSSR_s.setLocationAndAngles(this.posX, this.posY, this.posZ, 0, 0);
					worldObj.spawnEntityInWorld(entityUSSR_s);
					if (!pickupEntity(entityUSSR_s, 1)) entityUSSR_s.setDead();
				}
				
			}
		}
		super.onUpdate();
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
