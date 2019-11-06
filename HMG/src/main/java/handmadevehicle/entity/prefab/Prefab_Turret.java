package handmadevehicle.entity.prefab;

import handmadeguns.items.GunInfo;
import handmadeguns.items.guns.HMGItem_Unified_Guns;
import handmadevehicle.entity.parts.turrets.FireRist;
import handmadevehicle.entity.parts.turrets.TurretObj;
import net.minecraft.world.World;

import javax.vecmath.Vector3d;
import java.util.ArrayList;

public class Prefab_Turret {
	public Vector3d turretYawCenterpos = new Vector3d(0,0,0);
	public Vector3d turretPitchCenterpos = new Vector3d(0,0,0);
	public Vector3d cannonPos = new Vector3d();
	public Vector3d[] multicannonPos = null;
	
	public boolean linked_MotherTrigger = true;
	public boolean fireAll_child = false;
	public boolean fireAll_cannon = false;
	public boolean positionLinked = true;

	public boolean syncTurretAngle = false;
	public double seekerSize = 60;
	
	public int elevationType = 0;
	
	public float turretanglelimtYawMax = 360;
	public float turretanglelimtYawmin = -360;
	public float turretanglelimtPitchMax = 15;
	public float turretanglelimtPitchmin = -15;
	public double turretspeedY = 5;
	public double turretspeedP = 5;
	public String traverseSound = "handmadevehicle:handmadevehicle.traverseSound";
	public float traversesoundLV = 1;
	public float traversesoundPitch = 1;
	public boolean hasReflexSight;
	public boolean useVehicleInventory;
	
	public FireRist[] fireRists;
	
	
	public GunInfo gunInfo = new GunInfo();
	
	public boolean userOnBarrell = false;
	public double flashoffset;
	public int childFireBlank = 10;

	public boolean needGunStack = false;
	public String[] gunStackwhitelist;

	public Prefab_Turret(){

	}
	public Prefab_Turret(HMGItem_Unified_Guns unified_gun){
		attachGunStack(unified_gun);
	}
	public void attachGunStack(HMGItem_Unified_Guns unified_gun){

		this.gunInfo = unified_gun.gunInfo;
		this.turretYawCenterpos = new Vector3d(this.gunInfo.posGetter.turretRotationYawPoint);
		this.turretPitchCenterpos = new Vector3d(this.gunInfo.posGetter.turretRotationPitchPoint);
		if(this.gunInfo.restrictTurretMoveSpeed){
			this.turretspeedP = -1;
			this.turretspeedY = -1;
		}else {
			this.turretspeedP = this.gunInfo.turretMoveSpeedP;
			this.turretspeedY = this.gunInfo.turretMoveSpeedY;
		}
		if(this.gunInfo.restrictTurretAngle){
			this.turretanglelimtYawMax = this.gunInfo.turretanglelimtMxY;
			this.turretanglelimtYawmin = this.gunInfo.turretanglelimtmnY;
			this.turretanglelimtPitchMax = this.gunInfo.turretanglelimtMxP;
			this.turretanglelimtPitchmin = this.gunInfo.turretanglelimtmnP;
		}
		this.cannonPos = new Vector3d(this.gunInfo.posGetter.barrelpos);
		if(this.gunInfo.posGetter.multi_barrelpos != null) {
			this.multicannonPos = new Vector3d[this.gunInfo.posGetter.multi_barrelpos.length];
			int cnt = 0;
			for (double[] oneCannonPos : this.gunInfo.posGetter.multi_barrelpos)
				this.multicannonPos[cnt++] = new Vector3d(oneCannonPos);
		}
	}

	public TurretObj getnewTurret(World world){
		TurretObj turretObj = new TurretObj(world);
		turretObj.prefab_turret = this;
		if(!this.needGunStack) {
			turretObj.dummyGunItem.gunInfo = this.gunInfo;
			turretObj.dummyGunItem.setMaxDamage(gunInfo.bulletRound);
		}
		return turretObj;
	}
}
