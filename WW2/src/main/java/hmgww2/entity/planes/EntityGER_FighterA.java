package hmgww2.entity.planes;


import hmgww2.entity.EntityGER_S;
import handmadevehicle.entity.parts.IMultiTurretVehicle;
import handmadevehicle.entity.parts.SeatInfo;
import handmadevehicle.entity.parts.turrets.TurretObj;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import javax.vecmath.AxisAngle4d;
import javax.vecmath.Vector3d;
import java.util.ArrayList;

import static handmadevehicle.Utils.*;
import static java.lang.Math.toRadians;

public class EntityGER_FighterA extends EntityGER_FighterBase implements IMultiTurretVehicle
{
	TurretObj turret1;
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
		baseLogic.prefab_vehicle.throttle_gearDown = 1.7f;
		baseLogic.prefab_vehicle.throttle_Max = 3.0f;
		baseLogic.prefab_vehicle.rollspeed = 0.2f;
		baseLogic.prefab_vehicle.pitchspeed = 0.4f;
		baseLogic.prefab_vehicle.yawspeed = 0.15f;
		baseLogic.prefab_vehicle.Dive_bombing = true;
		baseLogic.prefab_vehicle.sholdUseMain_ToG = true;
		baseLogic.prefab_vehicle.startDive = 40;
		baseLogic.prefab_vehicle.maxDive = 80;
		baseLogic.prefab_vehicle.cruiseALT = 80;
		baseLogic.prefab_vehicle.maxClimb = -25;
		baseLogic.prefab_vehicle.maxbank = 40;
		baseLogic.prefab_vehicle.minALT = 30;
		baseLogic.prefab_vehicle.soundname = "hmgww2:hmgww2.sound_pera";
		baseLogic.prefab_vehicle.pitchsighwidthmax = 5;
		baseLogic.prefab_vehicle.pitchsighwidthmin = -10;
		baseLogic.prefab_vehicle.yawsightwidthmax = 15;
		baseLogic.prefab_vehicle.yawsightwidthmin = -15;
		
		baseLogic.prefab_vehicle.camerapos = new double[]{-0.0194,2.419,-0.5};
		baseLogic.camerarot_zoom = quatRotateAxis(baseLogic.camerarot_zoom,new AxisAngle4d(unitX,toRadians(-50)/2));
		baseLogic.prefab_vehicle.rotcenter = new double[]{0,1.5,0};
		baseLogic.riddenByEntities = new Entity[2];
		baseLogic.seatInfos = new SeatInfo[2];
		baseLogic.seatInfos[0] = new SeatInfo();
		baseLogic.seatInfos_zoom[0] = new SeatInfo();
		baseLogic.seatInfos[0].pos = baseLogic.prefab_vehicle.camerapos;
		baseLogic.seatInfos_zoom[0].pos = baseLogic.prefab_vehicle.camerapos;
		
		
		
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
		}
		baseLogic.subTurret = cannon1;
		
		TurretObj bomb1 = new TurretObj(worldObj);
		{
			bomb1.onMotherPos = new Vector3d(4,-1,0);
			bomb1.motherRotCenter = new Vector3d(baseLogic.prefab_vehicle.rotcenter);
			bomb1.turretspeedY = 5;
			bomb1.turretspeedP = 8;
			bomb1.turretanglelimtPitchMax = 5;
			bomb1.turretanglelimtPitchmin = -80;
			bomb1.traverseSound = null;
			bomb1.currentEntity = this;
			bomb1.powor = 1000;
			bomb1.ex = 3;
			bomb1.cycle_setting = 0;
			bomb1.firesound = null;
			bomb1.bulletmodel = "500kgBomb";
			bomb1.flashName = null;
			bomb1.spread = 25;
			bomb1.speed = 0.1f;
			bomb1.canex = true;
			bomb1.guntype = 2;
			bomb1.magazineMax = 1;
			bomb1.reloadSetting = 500;
		}
		
		TurretObj bomb2 = new TurretObj(worldObj);
		{
			bomb2.onMotherPos = new Vector3d(-4,-1,0);
			bomb2.motherRotCenter = new Vector3d(baseLogic.prefab_vehicle.rotcenter);
			bomb2.turretspeedY = 5;
			bomb2.turretspeedP = 8;
			bomb2.turretanglelimtPitchMax = 5;
			bomb2.turretanglelimtPitchmin = -80;
			bomb2.traverseSound = null;
			bomb2.currentEntity = this;
			bomb2.powor = 1000;
			bomb2.ex = 3;
			bomb2.cycle_setting = 0;
			bomb2.firesound = null;
			bomb2.bulletmodel = "500kgBomb";
			bomb2.flashName = null;
			bomb2.spread = 25;
			bomb2.speed = 0.1f;
			bomb2.canex = true;
			bomb2.guntype = 2;
			bomb2.magazineMax = 1;
			bomb2.reloadSetting = 500;
			bomb2.linked_MotherTrigger = false;
			bomb1.addbrother(bomb2);
		}
		baseLogic.mainTurret = bomb1;
		
		
		{
			turret1 = new TurretObj(worldObj);
			turret1.onMotherPos = new Vector3d(0,2.198,1.276);
			turret1.userOnBarrell = true;
			turret1.turretPitchCenterpos = new Vector3d(0,0,0);
			turret1.motherRotCenter = new Vector3d(0,1.5,0);
			turret1.cannonPos = new Vector3d(0,2.203,0.5968);
			turret1.cannonPos.sub(turret1.onMotherPos);
			turret1.cannonPos.z *= -1;
			turret1.turretanglelimtYawmin = 160;
			turret1.turretanglelimtYawMax = -160;
			turret1.turretanglelimtPitchMax = 5;
			turret1.turretanglelimtPitchmin = -60;
			turret1.turretrotationYaw = 180;
			turret1.flashoffset = 0;
			turret1.turretspeedY = 30;
			turret1.turretspeedP = 30;
			turret1.traverseSound = null;
			turret1.currentEntity = this;
			turret1.powor = 8;
			turret1.cycle_setting = 0;
			turret1.flashName = "arrow";
			turret1.flashscale = 1;
			turret1.flashfuse = 1;
			turret1.firesound = "handmadeguns:handmadeguns.fire";
			turret1.magazineMax = 150;
			turret1.magazinerem = 150;
			turret1.reloadSetting = 400;
			turret1.spread = 8;
			turret1.speed = 8;
			turret1.canex = false;
			turret1.guntype = 0;
			baseLogic.seatInfos[1] = new SeatInfo();
			baseLogic.seatInfos[1].pos = new double[]{0,2.2826,1.7533};
			baseLogic.seatInfos[1].maingun = turret1;
			baseLogic.seatInfos[1].hasGun = true;
		}
	}
	protected void applyEntityAttributes()
	{
		maxHealth = 450;
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
					EntityGER_S entityGER_s = new EntityGER_S(worldObj);
					entityGER_s.setLocationAndAngles(this.posX, this.posY, this.posZ, 0, 0);
					worldObj.spawnEntityInWorld(entityGER_s);
					if (!pickupEntity(entityGER_s, 1)) entityGER_s.setDead();
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
	public float getZoomlevel(){
		return 1;
	}
}
