package hmggvcmob.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import hmggvcmob.entity.GVCEntityChild;
import hmggvcmob.entity.friend.GVCEntityPlane;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import static handmadeguns.HandmadeGunsCore.proxy;

public class GVCHandleHudEntityTracking implements IMessageHandler<GVCPacket_HudEntitytracking, IMessage> {
    @Override
    public IMessage onMessage(GVCPacket_HudEntitytracking message, MessageContext ctx) {

        World world;
//        System.out.println("debug");
        Entity clientplayer= null;
        if(ctx.side.isServer()) {
            return null;
        }else{
            clientplayer = proxy.getEntityPlayerInstance();
            world = proxy.getCilentWorld();
            if(clientplayer != world.getEntityByID(message.TGTEntityID))return null;
            if(clientplayer.ridingEntity instanceof GVCEntityChild && ((GVCEntityChild) clientplayer.ridingEntity).master instanceof GVCEntityPlane){
                ((GVCEntityPlane) ((GVCEntityChild) clientplayer.ridingEntity).master).trackedEntityPos = message.trackedEntityPos;
            }
        }
        return null;
    }
}
