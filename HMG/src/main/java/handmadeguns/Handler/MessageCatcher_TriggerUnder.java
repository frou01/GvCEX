package handmadeguns.Handler;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import handmadeguns.items.guns.HMGItem_Unified_Guns;
import handmadeguns.network.PacketTriggerUnder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

import static handmadeguns.HandmadeGunsCore.HMG_proxy;

public class MessageCatcher_TriggerUnder implements IMessageHandler<PacketTriggerUnder, IMessage> {
    @Override
    public IMessage onMessage(PacketTriggerUnder message, MessageContext ctx) {
        World world;
        if (ctx.side.isServer()) {
            world = ctx.getServerHandler().playerEntity.worldObj;
        } else {
            world = HMG_proxy.getCilentWorld();
        }
        Entity entity = world.getEntityByID(message.playerid);
        if(entity instanceof EntityPlayer){
            ItemStack itemStack = ((EntityPlayer) entity).getHeldItem();
            if(itemStack != null && itemStack.getItem() instanceof HMGItem_Unified_Guns){
                ItemStack[] items = new ItemStack[6];
                try {
                    NBTTagList tags = (NBTTagList) itemStack.getTagCompound().getTag("Items");
                    if (tags != null) {
                        for (int i = 0; i < tags.tagCount(); i++)//133
                        {
                            NBTTagCompound tagCompound = tags.getCompoundTagAt(i);
                            int slot = tagCompound.getByte("Slot");
                            if (slot >= 0 && slot < items.length && items[slot] == null) {
                                items[slot] = ItemStack.loadItemStackFromNBT(tagCompound);
//                    if(items[slot] != null) {
//                        System.out.println(""+ i + "" + items[slot].getItem().getUnlocalizedName());
//                    }
                            }
                        }
                        if(items[4] != null && items[4].getItem() instanceof HMGItem_Unified_Guns){
                            items[4].getTagCompound().setBoolean("IsTriggered",true);
                        }
                        int compressedID = 0;
                        for (int itemid = 0; itemid < items.length; itemid++) {
                            if (items[itemid] != null && items[itemid].getItem() != null) {
                                NBTTagCompound compound = new NBTTagCompound();
                                compound.setByte("Slot", (byte) itemid);
                                items[itemid].writeToNBT(compound);
                                tags.func_150304_a(compressedID, compound);
                                compressedID++;
                            }
                        }
                    }
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
