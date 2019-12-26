package handmadevehicle.Items;

import handmadevehicle.entity.EntityVehicle;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemWrench extends Item {
	
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase target)
	{
//		if(target instanceof EntityVehicle){
//			target.heal(1);
//			return true;
//		}
		return false;
	}
	public boolean itemInteractionForEntity2(ItemStack stack, EntityPlayer player, EntityVehicle target)
	{
		target.heal(5);
//		if(target instanceof EntityVehicle){
//			target.heal(1);
//			return true;
//		}
		return false;
	}
	
}
