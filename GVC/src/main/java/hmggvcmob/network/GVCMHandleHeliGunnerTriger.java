package hmggvcmob.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import hmggvcmob.entity.friend.GVCEntityPMCHeli;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import static handmadeguns.HandmadeGunsCore.proxy;

public class GVCMHandleHeliGunnerTriger implements IMessageHandler<GVCPakcetHeliGunnerTrigger, IMessage> {

    @Override
    public IMessage onMessage(GVCPakcetHeliGunnerTrigger message, MessageContext ctx) {
        World world;
//        System.out.println("debug");
        if(ctx.side.isServer()) {
            world = ctx.getServerHandler().playerEntity.worldObj;
        }else{
            world = proxy.getCilentWorld();
        }
        if(world != null) {
            Entity entity = world.getEntityByID(message.targetID);
            if (entity instanceof GVCEntityPMCHeli) {
//                ((GVCEntityPMCHeli) entity).turretTrigger = message.trigger2;
            }
        }
        return null;
    }
}
