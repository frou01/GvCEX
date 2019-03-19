package hmggvcmob.network;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import hmggvcmob.entity.ImultiRideableVehicle;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import static handmadeguns.HandmadeGunsCore.proxy;

public class GVCMHandleVehicleState implements IMessageHandler<GVCPakcetVehicleState, IMessage> {

    @Override
    public IMessage onMessage(GVCPakcetVehicleState message, MessageContext ctx) {
        World world;
//        System.out.println("debug");
        if(ctx.side.isServer()) {
            world = ctx.getServerHandler().playerEntity.worldObj;
        }else{
            world = proxy.getCilentWorld();
        }
        if(world != null) {
            Entity entity = world.getEntityByID(message.targetID);
            if (entity instanceof ImultiRideableVehicle && (!world.isRemote ||(((ImultiRideableVehicle) entity).getpilotseatid() == -1 ||((ImultiRideableVehicle) entity).getChilds()[((ImultiRideableVehicle) entity).getpilotseatid()] != null && ((ImultiRideableVehicle) entity).getChilds()[((ImultiRideableVehicle) entity).getpilotseatid()].riddenByEntity != FMLClientHandler.instance().getClientPlayerEntity()))) {
                ((ImultiRideableVehicle) entity).setBodyRot(message.rot);
                ((ImultiRideableVehicle) entity).setthrottle(message.th);
                ((ImultiRideableVehicle) entity).setTrigger(message.trigger1,message.trigger2);
            }
        }
        return null;
    }
}
