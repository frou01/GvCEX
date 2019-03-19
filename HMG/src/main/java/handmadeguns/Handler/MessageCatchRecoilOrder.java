package handmadeguns.Handler;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import handmadeguns.items.HMGItemAttachment_grip;
import handmadeguns.items.guns.HMGItem_Unified_Guns;
import handmadeguns.HandmadeGunsCore;
import handmadeguns.network.PacketRecoil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

import static handmadeguns.HandmadeGunsCore.proxy;


public class MessageCatchRecoilOrder implements IMessageHandler<PacketRecoil, IMessage> {
    @Override//IMessageHandlerのメソッド
    public IMessage onMessage(PacketRecoil message, MessageContext ctx) {
        //クライアントへ送った際に、EntityPlayerインスタンスはこのように取れる。
        //EntityPlayer player = SamplePacketMod.proxy.getEntityPlayerInstance();
        //サーバーへ送った際に、EntityPlayerインスタンス（EntityPlayerMPインスタンス）はこのように取れる。
        //EntityPlayer entityPlayer = ctx.getServerHandler().playerEntity;
        //Do something.
        World world;
//        System.out.println("debug");
        if (ctx.side.isServer()) {
            world = ctx.getServerHandler().playerEntity.worldObj;
        } else {
            world = proxy.getCilentWorld();
        }
        if(ctx.side.isClient())
        try {
            if (world != null) {
                EntityPlayer entityPlayer = proxy.getEntityPlayerInstance();
                ItemStack stack = entityPlayer.getHeldItem();
                if(stack != null){
                    Item item = stack.getItem();
                    ItemStack[] items = new ItemStack[6];
                    if(item instanceof HMGItem_Unified_Guns){
                        NBTTagList tags = (NBTTagList) stack.getTagCompound().getTag("Items");
                        if (tags != null) {
                            for (int i = 0; i < 7; i++)//133
                            {
                                NBTTagCompound tagCompound = tags.getCompoundTagAt(i);
                                int slot = tagCompound.getByte("Slot");
                                if (slot >= 0 && slot < items.length) {
                                    items[slot] = ItemStack.loadItemStackFromNBT(tagCompound);
//                    if(items[slot] != null) {
//                        System.out.println(""+ i + "" + items[slot].getItem().getUnlocalizedName());
//                    }
                                }
                            }
                        }
                        float reduceRecoilLevel = 1;
                        if(HandmadeGunsCore.Key_ADS(entityPlayer)){
                            if(items[4] != null && items[4].getItem() instanceof HMGItemAttachment_grip)
                                reduceRecoilLevel = ((HMGItemAttachment_grip) items[4].getItem()).reduceRecoilLevel_ADS;
                            entityPlayer.rotationPitch -= ((HMGItem_Unified_Guns) item).recoil_sneak * reduceRecoilLevel;
                        }else {
                            if(items[4] != null && items[4].getItem() instanceof HMGItemAttachment_grip)
                                reduceRecoilLevel = ((HMGItemAttachment_grip) items[4].getItem()).reduceRecoilLevel;
                            entityPlayer.rotationPitch -= ((HMGItem_Unified_Guns) item).recoil * reduceRecoilLevel;
                        }
                    }
                }
            }
//        bullet = message.bullet.setdata(bullet);
//        System.out.println("bullet "+ bullet);
        } catch (ClassCastException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}