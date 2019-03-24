package hmgww2.entity;


import hmggvcmob.entity.GVCEntityChild;
import hmggvcmob.entity.ImultiRideableVehicle;
import hmggvcmob.entity.Iplane;
import hmggvcmob.entity.PlaneBaseLogic;
import hmgww2.Nation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import javax.vecmath.Quat4d;

public class EntityUSA_FighterBase extends EntityBases_Plane implements ImultiRideableVehicle,Iplane
{
	
	public EntityUSA_FighterBase(World par1World) {
		super(par1World);
	}
	
	@Override
	public Nation getnation() {
		return Nation.USA;
	}
}
