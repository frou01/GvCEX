package hmggvcutil.Item;

import hmggvcutil.GVCUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GVCItemHealthPack extends Item
{
    public GVCItemHealthPack()
    {
        this.maxStackSize = 64;
        setCreativeTab(GVCUtils.tabgvc);
    }
    
    
    public ItemStack onEaten(ItemStack p_77654_1_, World par2World, EntityPlayer par3EntityPlayer)
    {
        if (!par3EntityPlayer.capabilities.isCreativeMode)
        {
            --p_77654_1_.stackSize;
        }

            if (!par2World.isRemote)
            {
         	   par3EntityPlayer.setHealth(par3EntityPlayer.getHealth() + 4.0F);
             
            }

        return p_77654_1_;
    }
    
    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
		  return EnumAction.drink;
    }
    
    /**
     * How long it takes to use or consume an item
     */
    public int getMaxItemUseDuration(ItemStack p_77626_1_)
    {
        return 20;
    }
    
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        		par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
        return par1ItemStack;
    }
}