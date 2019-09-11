package handmadeguns.Handler;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import handmadeguns.HandmadeGunsCore;
import handmadeguns.network.PacketOpenGui;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import static handmadeguns.HandmadeGunsCore.HMG_proxy;

public class MessageCatcher_OpenGui implements IMessageHandler<PacketOpenGui, IMessage> {
    @Override
    public IMessage onMessage(PacketOpenGui message, MessageContext ctx) {
        World world;
//        System.out.println("debug");
        if(ctx.side.isServer()) {
            world = ctx.getServerHandler().playerEntity.worldObj;
        }else{
            world = HMG_proxy.getCilentWorld();
        }
        try {
            if(world != null){
                Entity opener = world.getEntityByID(message.entityID);
                if(opener != null && opener instanceof EntityPlayer) {
                    ((EntityPlayer) opener).openGui(HandmadeGunsCore.INSTANCE, message.guiID, opener.worldObj, (int)opener.posX, (int)opener.posY, (int)opener.posZ);
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
