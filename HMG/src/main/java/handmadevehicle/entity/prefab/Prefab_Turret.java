package handmadevehicle.entity.prefab;

import handmadeguns.items.GunInfo;
import handmadeguns.items.guns.HMGItem_Unified_Guns;
import handmadevehicle.entity.parts.turrets.FireRist;
import handmadevehicle.entity.parts.turrets.TurretObj;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.vecmath.Vector3d;

public class Prefab_Turret {
	public String turretName;
	
	public boolean linked_MotherTrigger = true;
	public boolean fireAll_child = false;
	public boolean fireAll_cannon = false;
	public boolean positionLinked = true;
	public boolean onlyAim = false;

	public boolean syncTurretAngle = false;
	public boolean lockToPilotAngle = false;
	public boolean lockOnByVehicle = false;

	public int elevationType = 0;
	public String traverseSound = "handmadevehicle:handmadevehicle.traverseSound";
	public float traversesoundLV = 1;
	public float traversesoundPitch = 1;
	public boolean hasReflexSight;
	public boolean useVehicleInventory;

	public boolean canReloadAirBone = true;
	
	public FireRist[] fireRists;
	
	
	public final GunInfo gunInfo;

	public final HMGItem_Unified_Guns attachedItem;
	
	public boolean userOnBarrell = false;
	public double flashoffset;
	public int childFireBlank = 10;

	public boolean needGunStack = false;
	public boolean useGunSight = false;

	public boolean useVehicleRadar = false;
	public boolean forceSyncTarget = false;
	public boolean syncMotherTarget = false;
	public String[] gunStackwhitelist;

	public Prefab_Turret(){
		gunInfo = new GunInfo();
		attachedItem = null;
	}
	public Prefab_Turret(HMGItem_Unified_Guns unified_gun){
		this.gunInfo = unified_gun.gunInfo;
		this.attachedItem = unified_gun;
		attachGunStack(unified_gun);
	}
	public void attachGunStack(HMGItem_Unified_Guns unified_gun){
	}

	public TurretObj getnewTurret(World world){
		TurretObj turretObj = new TurretObj(world);
		turretObj.prefab_turret = this;
		if(!this.needGunStack) {
			if(this.attachedItem != null){
				turretObj.gunItem = this.attachedItem;
				turretObj.gunStack = new ItemStack(turretObj.gunItem);
			}else {
				turretObj.gunItem.gunInfo = this.gunInfo;
				turretObj.gunItem.setMaxDamage(gunInfo.bulletRound);
				turretObj.gunStack.setItemDamage(turretObj.gunStack.getMaxDamage());
			}
		}else {
			turretObj.gunStack = null;
			turretObj.gunItem = null;
		}
		return turretObj;
	}
}
