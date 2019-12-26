package handmadevehicle.entity.prefab;

import handmadeguns.items.GunInfo;
import handmadeguns.items.guns.HMGItem_Unified_Guns;
import handmadevehicle.entity.parts.turrets.FireRist;
import handmadevehicle.entity.parts.turrets.TurretObj;
import net.minecraft.world.World;

import javax.vecmath.Vector3d;

public class Prefab_Turret {
	public String turretName;
	
	public boolean linked_MotherTrigger = true;
	public boolean fireAll_child = false;
	public boolean fireAll_cannon = false;
	public boolean positionLinked = true;

	public boolean syncTurretAngle = false;
	
	public int elevationType = 0;
	public String traverseSound = "handmadevehicle:handmadevehicle.traverseSound";
	public float traversesoundLV = 1;
	public float traversesoundPitch = 1;
	public boolean hasReflexSight;
	public boolean useVehicleInventory;
	
	public FireRist[] fireRists;
	
	
	public final GunInfo gunInfo;
	
	public boolean userOnBarrell = false;
	public double flashoffset;
	public int childFireBlank = 10;

	public boolean needGunStack = false;
	public boolean useGunSight = false;
	public String[] gunStackwhitelist;

	public Prefab_Turret(){
		gunInfo = new GunInfo();
	}
	public Prefab_Turret(HMGItem_Unified_Guns unified_gun){
		this.gunInfo = unified_gun.gunInfo;
		attachGunStack(unified_gun);
	}
	public void attachGunStack(HMGItem_Unified_Guns unified_gun){
	}

	public TurretObj getnewTurret(World world){
		TurretObj turretObj = new TurretObj(world);
		turretObj.prefab_turret = this;
		if(!this.needGunStack) {
			turretObj.gunItem.gunInfo = this.gunInfo;
			turretObj.gunItem.setMaxDamage(gunInfo.bulletRound);
		}else {
			turretObj.gunStack = null;
			turretObj.gunItem = null;
		}
		return turretObj;
	}
}
