package handmadevehicle.Items;

import handmadevehicle.VehicleType;
import handmadevehicle.entity.EntityVehicle;
import handmadevehicle.entity.EntityVehicle_Plane;
import handmadevehicle.entity.EntityVehicle_Tank;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import static handmadevehicle.HMVehicle.tabHMV;

public class ItemVehicle extends Item {
	VehicleType vehicleType;
	
	public String dataName ;
	
	public ItemVehicle(String name,VehicleType type)
	{
		super();
		this.setTextureName(name);
		this.setCreativeTab(tabHMV);
		this.dataName = name;
		this.maxStackSize = 64;
		this.vehicleType = type;
	}
	
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
		if (par3World.isRemote) {
			return true;
		} else if (par7 != 1) {
			return false;
		} else {
//			par2EntityPlayer.addStat(power_of_number, 1);
			
			int var12 = MathHelper.floor_double((double) (par2EntityPlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
			EntityVehicle bespawningEntity = null;
			switch (vehicleType){
				case Air:
					bespawningEntity = new EntityVehicle_Plane(par3World,dataName);
					break;
				case Land:
					bespawningEntity = new EntityVehicle_Tank(par3World,dataName);
					break;
			}
			bespawningEntity.setLocationAndAngles(par4 + 0.5, par5+1, par6 + 0.5, var12 , 0.0F);
			par3World.spawnEntityInWorld(bespawningEntity);
			--par1ItemStack.stackSize;
			return true;
		}
	}
}
