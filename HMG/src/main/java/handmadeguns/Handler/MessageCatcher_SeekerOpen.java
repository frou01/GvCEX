package handmadeguns.Handler;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import handmadeguns.items.guns.HMGItem_Unified_Guns;
import handmadeguns.network.PacketSeekerOpen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import static handmadeguns.HandmadeGunsCore.HMG_proxy;

public class MessageCatcher_SeekerOpen implements IMessageHandler<PacketSeekerOpen, IMessage> {
    @Override
    public IMessage onMessage(PacketSeekerOpen message, MessageContext ctx) {
        World world;
//        System.out.println("debug");
        if(ctx.side.isServer()) {
            world = ctx.getServerHandler().playerEntity.worldObj;
        }else{
            world = HMG_proxy.getCilentWorld();
        }
        try {
            if(world != null){
                Entity shooter = world.getEntityByID(message.shooterid);
                if(shooter instanceof EntityLivingBase && ((EntityLivingBase) shooter).getHeldItem() != null && ((EntityLivingBase) shooter).getHeldItem().getItem() instanceof HMGItem_Unified_Guns) {
                    NBTTagCompound nbt = ((EntityLivingBase) shooter).getHeldItem().getTagCompound();
                    if(!nbt.getBoolean("SeekerOpened")){
                        nbt.setBoolean("islockedentity", false);
                        nbt.setBoolean("islockedblock",false);
                    }
                    nbt.setBoolean("SeekerOpened",!nbt.getBoolean("SeekerOpened"));
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
