package hmggvcmob.item;

import hmggvcmob.entity.friend.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

import java.util.List;

import static hmggvcmob.GVCMobPlus.tabgvcm;

public class GVCItemPMCDefSetter extends Item {
    int mode = 0;
    public GVCItemPMCDefSetter(){
        this.setCreativeTab(tabgvcm);
        setMaxStackSize(64);
    }
    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_)
    {
        if(!p_77659_2_.isRemote && p_77659_3_.isSneaking()){
            if(mode==0){
                mode = 1;
                p_77659_3_.addChatComponentMessage(new ChatComponentTranslation(
                        "Tracking command mode"));
            }else {
                mode = 0;
                p_77659_3_.addChatComponentMessage(new ChatComponentTranslation(
                        "Defence position setter mode"));
            }
        }
        return p_77659_1_;
    }
    public boolean onItemUse(ItemStack itemStack, EntityPlayer entityPlayer, World world, int x, int y, int z, int p_77648_7_, float tox, float toy, float toz)
    {
        if(!world.isRemote) {
            List list = world.getEntitiesWithinAABBExcludingEntity(entityPlayer, entityPlayer.boundingBox.expand(10, 10, 10));
            if (list != null && !list.isEmpty()) {
                for (int i = 0; i < list.size(); ++i) {
                    if(mode == 0) {
                        Entity PMC = (Entity) list.get(i);
                        if (PMC instanceof EntityPMCBase) {
                            entityPlayer.addChatComponentMessage(new ChatComponentTranslation(
                                    "Defense  " + x + "," + z + "!"));
                            ((EntityPMCBase) PMC).mode = 1;
                            ((EntityPMCBase) PMC).homeposX = x;
                            ((EntityPMCBase) PMC).homeposY = y;
                            ((EntityPMCBase) PMC).homeposZ = z;
                            ;
                        }
                    }else {
                        Entity PMC = (Entity) list.get(i);
                        if (PMC instanceof EntityPMCBase) {
                            entityPlayer.addChatComponentMessage(new ChatComponentTranslation(
                                    "I'll Follow Leader!"));
                            ((EntityPMCBase) PMC).mode = 2;
                            ((EntityPMCBase) PMC).homeposX = (int) entityPlayer.posX;
                            ((EntityPMCBase) PMC).homeposY = (int) entityPlayer.posY;
                            ((EntityPMCBase) PMC).homeposZ = (int) entityPlayer.posZ;
                            ((EntityPMCBase) PMC).master = entityPlayer;
                            ;
                        }
                    }
                }
            }
        }
        return true;
    }

}
