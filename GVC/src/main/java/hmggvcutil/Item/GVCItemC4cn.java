package hmggvcutil.Item;

import java.util.List;

import hmggvcutil.GVCUtils;
import hmggvcutil.entity.GVCEntityC4;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GVCItemC4cn extends Item
{
    private static final String __OBFID = "CL_00000069";

    public GVCItemC4cn()
    {
        this.maxStackSize = 1;
        setCreativeTab(GVCUtils.tabgvc);
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_)
    {
    	GVCEntityC4 c4;
    	List llist = p_77659_2_.getEntitiesWithinAABB(GVCEntityC4.class, p_77659_3_.boundingBox.expand(400.0D, 400.0D, 400.0D));
    	
    	if(llist!=null){
            for (int lj = 0; lj < llist.size(); lj++) {
            	
            	GVCEntityC4 entity1 = (GVCEntityC4)llist.get(lj);
            	if (entity1!=null)
                {
            		if (!p_77659_2_.isRemote)
                    {
            			entity1.setDead();
            			entity1.explode();
                    }
            		
                }
            }
        }
    	p_77659_2_.playSoundAtEntity(p_77659_3_, "random.click", 1.0F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        return p_77659_1_;
    }
}