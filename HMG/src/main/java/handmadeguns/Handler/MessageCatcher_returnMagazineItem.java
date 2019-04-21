package handmadeguns.Handler;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import handmadeguns.items.guns.HMGItem_Unified_Guns;
import handmadeguns.network.PacketreturnMgazineItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import static handmadeguns.HandmadeGunsCore.proxy;

public class MessageCatcher_returnMagazineItem implements IMessageHandler<PacketreturnMgazineItem, IMessage> {
    @Override
    public IMessage onMessage(PacketreturnMgazineItem message, MessageContext ctx) {
        World world;
//        System.out.println("debug");
        if(ctx.side.isServer()) {
            world = ctx.getServerHandler().playerEntity.worldObj;
        }else{
            world = proxy.getCilentWorld();
        }
        try {
            if(world != null){
                Entity shooter = world.getEntityByID(message.entityid);
                if(shooter != null && shooter instanceof EntityPlayer && ((EntityLivingBase) shooter).getHeldItem() != null) {
                    Item gunitem = ((EntityLivingBase) shooter).getHeldItem().getItem();
                    ItemStack itemStack = ((EntityLivingBase) shooter).getHeldItem();
                    if(gunitem instanceof HMGItem_Unified_Guns){
                        {
                            int returnmagazineCount = (int)((float) ((HMGItem_Unified_Guns) gunitem).gunInfo.magazineItemCount * ((((gunitem).getMaxDamage() - itemStack.getItemDamage()) / (float)(gunitem).getMaxDamage())));
//                            System.out.println("debug" + returnmagazineCount);
                            for(int i= 0;i<returnmagazineCount;i++) {
                                ((EntityPlayer) shooter).inventory.addItemStackToInventory(new ItemStack(((HMGItem_Unified_Guns) gunitem).gunInfo.magazine));
                            }
                        }
                        itemStack.setItemDamage(itemStack.getMaxDamage());
                    }
                }
            }
//        bullet = message.bullet.setdata(bullet);
//        System.out.println("bullet "+ bullet);
        }catch (ClassCastException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
