package handmadevehicle.entity.prefab;

import handmadevehicle.entity.parts.IVehicle;
import handmadevehicle.entity.parts.turrets.TurretObj;
import net.minecraft.world.World;

import javax.vecmath.Vector3d;

public class Prefab_AttachedWeapon {
	public Prefab_AttachedWeapon motherTurret;
	
	public Prefab_Turret prefab_turret;
	public Prefab_AttachedWeapon[] prefab_Childturrets;
	public Prefab_AttachedWeapon[] prefab_ChildOnBarrel;
	public Vector3d turretsPos;//砲塔搭載位置
	public int linkedGunStackID = -1;
	
	public TurretObj getTurretOBJ(World world, IVehicle motherEntity, TurretObj[] turretObjs_all, int[] nonprimitiveID){
		TurretObj current = prefab_turret.getnewTurret(world);
		turretObjs_all[nonprimitiveID[0]] = current;
		nonprimitiveID[0]++;
		current.linkedGunStackID = linkedGunStackID;
		current.motherRotCenter = motherEntity.getBaseLogic().prefab_vehicle.rotcenterVec;
		current.onMotherPos = turretsPos;
		if(prefab_turret.useVehicleInventory){
			current.connectedInventory = motherEntity.getBaseLogic().inventoryVehicle;
		}
		for(Prefab_AttachedWeapon prefab_attachedWeapon_child:prefab_Childturrets)
			current.addchild_triggerLinked(prefab_attachedWeapon_child.getTurretOBJ(world,motherEntity,turretObjs_all,nonprimitiveID));
		if(prefab_ChildOnBarrel != null)for(Prefab_AttachedWeapon prefab_attachedWeapon_child:prefab_ChildOnBarrel)
			current.addchildonBarrel(prefab_attachedWeapon_child.getTurretOBJ(world,motherEntity,turretObjs_all,nonprimitiveID));
		return current;
	}
}
