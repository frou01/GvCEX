package hmggvcmob.item;

import hmggvcmob.entity.EntitySupportTGT;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import static hmggvcmob.GVCMobPlus.tabgvcm;

public class GVCXrequestSupport extends Item {
    public int type;
    public GVCXrequestSupport(int t){
        super();
        this.maxStackSize = 64;
        type = t;
        this.setCreativeTab(tabgvcm);
    }
    public ItemStack onItemRightClick(ItemStack p_77659_1_, World world, EntityPlayer entityplayer)
    {
        if (!world.isRemote) {
            EntitySupportTGT var8 = new EntitySupportTGT(world, (EntityLivingBase) entityplayer, 20.0F);
            var8.type = type;
            world.spawnEntityInWorld(var8);
        }
        p_77659_1_.stackSize--;
        return p_77659_1_;
    }
}
