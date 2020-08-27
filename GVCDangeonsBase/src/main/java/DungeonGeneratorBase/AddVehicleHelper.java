package DungeonGeneratorBase;

import cpw.mods.fml.common.registry.GameRegistry;
import handmadeguns.items.guns.HMGItem_Unified_Guns;
import handmadevehicle.entity.EntityVehicle;
import handmadevehicle.entity.prefab.Prefab_Vehicle_Base;
import hmggvcmob.GVCMobPlus;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Random;

import static handmadevehicle.entity.EntityVehicle.EntityVehicle_spawnByMob;

public class AddVehicleHelper {
	String vehicleType;
	float rotationYaw;
	public AddVehicleHelper(String s,float yaw){
		vehicleType = s;
		rotationYaw = yaw;
	}
	public void setVehicle(World world, double x, double y, double z){
		EntityVehicle bespawningEntity = EntityVehicle_spawnByMob(world,vehicleType);
		bespawningEntity.canDespawn = false;
		Random rand = new Random();
		Prefab_Vehicle_Base prefab_vehicle = bespawningEntity.getBaseLogic().prefab_vehicle;
		for(int slotID = 0 ;slotID < prefab_vehicle.weaponSlotNum;slotID++) {
			if(!prefab_vehicle.weaponSlot_linkedTurret_StackWhiteList.isEmpty()) {

				int randUsingSlot = rand.nextInt(prefab_vehicle.weaponSlot_linkedTurret_StackWhiteList.get(slotID).length);
				String whiteList = prefab_vehicle.weaponSlot_linkedTurret_StackWhiteList.get(slotID)[randUsingSlot];
				System.out.println("" + whiteList);
				Item check = GameRegistry.findItem("HandmadeGuns", whiteList);
				if (check instanceof HMGItem_Unified_Guns && ((HMGItem_Unified_Guns) check).gunInfo.guerrila_can_use) {
					bespawningEntity.getBaseLogic().inventoryVehicle.setInventorySlotContents(slotID, new ItemStack(check));
				}
			}else{
				int randUsingSlot = rand.nextInt(GVCMobPlus.Guns_CanUse.size());
				Item choosenGun = GVCMobPlus.Guns_CanUse.get(randUsingSlot);
				bespawningEntity.getBaseLogic().inventoryVehicle.setInventorySlotContents(slotID, new ItemStack(choosenGun));
			}
		}
		bespawningEntity.setLocationAndAngles(x, y, z, rotationYaw , 0.0F);
		world.spawnEntityInWorld(bespawningEntity);
	}
}
