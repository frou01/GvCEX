package hmggvcutil.Item;


import hmggvcutil.GVCUtils;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;


public class GVCItemForTranslate extends Item {

	public GVCItemForTranslate() {
		this.maxStackSize = 64;
		setCreativeTab(GVCUtils.tabgvc);
	}
	public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag){
		itemstack.setItemDamage(0);
	}
		/*
		@Override
	    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
	    {
	        player.openGui(GVCUtils.INSTANCE, 0, world, (int)player.posX, (int)player.posY, (int)player.posZ);
	        return itemStack;
	    }*/

}
