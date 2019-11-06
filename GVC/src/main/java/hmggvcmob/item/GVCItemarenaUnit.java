package hmggvcmob.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;

import static hmggvcmob.GVCMobPlus.tabgvcm;

public class GVCItemarenaUnit extends Item {
    public GVCItemarenaUnit() {
        super();
        this.setCreativeTab(tabgvcm);
    }
    public boolean itemInteractionForEntity(ItemStack p_111207_1_, EntityPlayer p_111207_2_, EntityLivingBase p_111207_3_)
    {
//        if(p_111207_3_ instanceof GVCEntityPMCT90Tank){
//            if(((GVCEntityPMCT90Tank) p_111207_3_).remainarena < 10) {
//                ((GVCEntityPMCT90Tank) p_111207_3_).remainarena++;
//                p_111207_1_.stackSize--;
//                p_111207_2_.addChatComponentMessage(new ChatComponentTranslation(
//                        "Arena Units:  "+((GVCEntityPMCT90Tank) p_111207_3_).remainarena));
//                return true;
//            }
//        }
        return false;
    }
}
