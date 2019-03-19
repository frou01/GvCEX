package hmggvcmob.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import hmggvcmob.entity.EntityMGAX55;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import static handmadeguns.HandmadeGunsCore.proxy;

public class GVCHandleMGControl implements IMessageHandler<GVCPacketMGControl, IMessage> {
    @Override
    public IMessage onMessage(GVCPacketMGControl message, MessageContext ctx) {
        World world;
//        System.out.println("debug");
        Entity clientplayer= null;
        if(ctx.side.isServer()) {
            world = ctx.getServerHandler().playerEntity.worldObj;
        }else{
            clientplayer = proxy.getEntityPlayerInstance();
            world = proxy.getCilentWorld();
        }
        if(world != null) {
            Entity entity = world.getEntityByID(message.targetID);
            if(entity instanceof EntityMGAX55 && clientplayer != ((EntityMGAX55) entity).pilot) {
                ((EntityMGAX55) entity).w = message.w;
                ((EntityMGAX55) entity).a = message.a;
                ((EntityMGAX55) entity).s = message.s;
                ((EntityMGAX55) entity).d = message.d;
                ((EntityMGAX55) entity).sp = message.sp;
                ((EntityMGAX55) entity).legSneak_CTRL = message.hold;
                ((EntityMGAX55) entity).trigger1 = message.trig1;
                ((EntityMGAX55) entity).trigger2 = message.trig2;
                ((EntityMGAX55) entity).weaponMode = message.weaponmode;
                if(!world.isRemote)GVCMPacketHandler.INSTANCE.sendToAll(message);
            }
        }
        return null;
    }
}
