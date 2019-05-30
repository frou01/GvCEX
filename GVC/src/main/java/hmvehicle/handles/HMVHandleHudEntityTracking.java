package hmvehicle.handles;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import hmvehicle.packets.HMVPacket_HudEntitytracking;
import hmvehicle.entity.EntityChild;
import hmggvcmob.entity.friend.GVCEntityPlane;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import static handmadeguns.HandmadeGunsCore.proxy;

public class HMVHandleHudEntityTracking implements IMessageHandler<HMVPacket_HudEntitytracking, IMessage> {
    @Override
    public IMessage onMessage(HMVPacket_HudEntitytracking message, MessageContext ctx) {

        World world;
//        System.out.println("debug");
        Entity clientplayer= null;
        if(ctx.side.isServer()) {
            return null;
        }else{
            clientplayer = proxy.getEntityPlayerInstance();
            world = proxy.getCilentWorld();
            if(clientplayer != world.getEntityByID(message.TGTEntityID))return null;
            if(clientplayer.ridingEntity instanceof EntityChild && ((EntityChild) clientplayer.ridingEntity).master instanceof GVCEntityPlane){
                ((GVCEntityPlane) ((EntityChild) clientplayer.ridingEntity).master).trackedEntityPos = message.trackedEntityPos;
            }
        }
        return null;
    }
}
