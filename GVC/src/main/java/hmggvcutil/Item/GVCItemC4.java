package hmggvcutil.Item;

import hmggvcutil.GVCUtils;
import hmggvcutil.entity.GVCEntityC4;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GVCItemC4 extends Item
{
    private static final String __OBFID = "CL_00000069";

    public GVCItemC4()
    {
        this.maxStackSize = 16;
        setCreativeTab(GVCUtils.tabgvc);
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_)
    {
        if (!p_77659_3_.capabilities.isCreativeMode)
        {
            --p_77659_1_.stackSize;
        }

        p_77659_2_.playSoundAtEntity(p_77659_3_, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        if (!p_77659_2_.isRemote)
        {
            p_77659_2_.spawnEntityInWorld(new GVCEntityC4(p_77659_2_, p_77659_3_, 0.5F));
        }

        return p_77659_1_;
    }
}