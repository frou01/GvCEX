package hmvehicle.handles;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import hmvehicle.entity.parts.ITank;
import hmvehicle.entity.parts.Iplane;
import hmvehicle.packets.HMVPakcetVehicleState;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import static handmadeguns.HandmadeGunsCore.proxy;

public class HMVMHandleVehicleState implements IMessageHandler<HMVPakcetVehicleState, IMessage> {

    @Override
    public IMessage onMessage(HMVPakcetVehicleState message, MessageContext ctx) {
        World world;
//        System.out.println("debug");
        if(ctx.side.isServer()) {
            world = ctx.getServerHandler().playerEntity.worldObj;
        }else{
            world = proxy.getCilentWorld();
        }
        if(world != null) {
            Entity entity = world.getEntityByID(message.targetID);
            if (entity instanceof Iplane) {
                ((Iplane) entity).setBodyRot(message.rot);
                ((Iplane) entity).setthrottle(message.th);
            }else
            if(entity instanceof ITank){
                ((ITank) entity).setthrottle(message.th);
                ((ITank) entity).setrotationYawmotion(message.yawmotion);
            }
        }
        return null;
    }
}
