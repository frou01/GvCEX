package handmadevehicle.entity.prefab;

import handmadevehicle.entity.parts.IVehicle;
import handmadevehicle.entity.parts.turrets.TurretObj;
import net.minecraft.world.World;

import javax.vecmath.Vector3d;

public class Prefab_AttachedWeapon {
	public Prefab_AttachedWeapon motherTurret;
	
	public Prefab_Turret prefab_turret;
	public Prefab_AttachedWeapon[] prefab_Childturrets;
	public Vector3d turretsPos;//砲塔搭載位置
	
	public TurretObj getTurretOBJ(World world, IVehicle motherEntity, TurretObj[] turretObjs_all, int[] nonprimitiveID){
		TurretObj current = prefab_turret.getnewTurret(world);
		turretObjs_all[nonprimitiveID[0]] = current;
		nonprimitiveID[0]++;
		current.motherRotCenter = new Vector3d(motherEntity.getBaseLogic().info.rotcenter);
		current.onMotherPos = turretsPos;
		if(prefab_turret.useVehicleInventory){
			current.connectedInventory = motherEntity.getBaseLogic().inventoryVehicle;
		}
		for(Prefab_AttachedWeapon prefab_attachedWeapon_child:prefab_Childturrets)
			current.addchild_triggerLinked(prefab_attachedWeapon_child.getTurretOBJ(world,motherEntity,turretObjs_all,nonprimitiveID));
		return current;
	}
}
