package handmadevehicle.entity.prefab;

import handmadeguns.items.GunInfo;
import handmadevehicle.entity.parts.turrets.FireRist;
import handmadevehicle.entity.parts.turrets.TurretObj;
import net.minecraft.world.World;

import javax.vecmath.Vector3d;

public class Prefab_Turret {
	public Vector3d turretYawCenterpos = new Vector3d(0,0,0);
	public Vector3d turretPitchCenterpos = new Vector3d(0,0,0);
	public Vector3d cannonPos = new Vector3d();
	public Vector3d[] multicannonPos = null;
	
	public boolean linked_MotherTrigger = true;
	public boolean fireAll_child = true;
	public boolean fireAll_cannon = false;
	
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
	
	public TurretObj getnewTurret(World world){
		TurretObj turretObj = new TurretObj(world);
		turretObj.prefab_turret = this;
		turretObj.dummyGunItem.gunInfo = this.gunInfo;
		turretObj.dummyGunItem.setMaxDamage(gunInfo.bulletRound);
		return turretObj;
	}
}
