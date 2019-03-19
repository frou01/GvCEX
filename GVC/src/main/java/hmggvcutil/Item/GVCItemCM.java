package hmggvcutil.Item;

import hmggvcutil.GVCUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GVCItemCM extends ItemFood
{	
    public GVCItemCM(int par1, boolean par3)
    {
        super(par1, par3);
        this.maxStackSize = 64;
        setCreativeTab(GVCUtils.tabgvc);
        this.setAlwaysEdible();
    }
    
    public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        --par1ItemStack.stackSize;
        par3EntityPlayer.getFoodStats().func_151686_a(this, par1ItemStack);
        par2World.playSoundAtEntity(par3EntityPlayer, "random.burp", 0.5F, par2World.rand.nextFloat() * 0.1F + 0.9F);
        
        if (!par2World.isRemote)
        {
        	par3EntityPlayer.setHealth(par3EntityPlayer.getHealth() + 10.0F);
        }
        
        this.onFoodEaten(par1ItemStack, par2World, par3EntityPlayer);
        return par1ItemStack;
    }    
}