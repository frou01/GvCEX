package hmvehicle.handles;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import hmvehicle.entity.parts.ITank;
import hmvehicle.entity.parts.Iplane;
import hmvehicle.entity.parts.logics.PlaneBaseLogic;
import hmvehicle.packets.HMVPakcetPlaneState;
import hmvehicle.packets.HMVPakcetVehicleState;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import static handmadeguns.HandmadeGunsCore.proxy;

public class HMVMHandlePlaneState implements IMessageHandler<HMVPakcetPlaneState, IMessage> {

    @Override
    public IMessage onMessage(HMVPakcetPlaneState message, MessageContext ctx) {
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
                ((PlaneBaseLogic)((Iplane) entity).getBaseLogic()).rotationmotion.set(message.rotmotion);
                ((Iplane) entity).setthrottle(message.th);
                entity.motionX = message.motionVec.x;
                entity.motionY = message.motionVec.y;
                entity.motionZ = message.motionVec.z;
            }else
            if (entity instanceof ITank) {
                ((ITank) entity).setBodyRot(message.rot);
                ((PlaneBaseLogic)((ITank) entity).getBaseLogic()).rotationmotion.set(message.rotmotion);
                ((ITank) entity).setthrottle(message.th);
                entity.motionX = message.motionVec.x;
                entity.motionY = message.motionVec.y;
                entity.motionZ = message.motionVec.z;
            }
        }
        return null;
    }
}
