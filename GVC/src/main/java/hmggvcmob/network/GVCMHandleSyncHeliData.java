package hmggvcmob.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import hmggvcmob.entity.friend.GVCEntityPMCHeli;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import static handmadeguns.HandmadeGunsCore.HMG_proxy;

public class GVCMHandleSyncHeliData implements IMessageHandler<GVCXMPacketSyncPMCHeliData, IMessage> {
    @Override
    public IMessage onMessage(GVCXMPacketSyncPMCHeliData gvcmPacketSyncFlagdata, MessageContext messageContext) {
        World world;
//        System.out.println("debug");
        if(messageContext.side.isServer()) {
            world = messageContext.getServerHandler().playerEntity.worldObj;
        }else{
            world = HMG_proxy.getCilentWorld();
        }
        Entity entity = world.getEntityByID(gvcmPacketSyncFlagdata.entityid);
        if(entity instanceof GVCEntityPMCHeli) {
//            ((GVCEntityPMCHeli)entity).bodyrotationYaw = gvcmPacketSyncFlagdata.bodyrotationYaw;
//            ((GVCEntityPMCHeli)entity).bodyrotationPitch = gvcmPacketSyncFlagdata.bodyrotationPitch;
//            ((GVCEntityPMCHeli)entity).bodyrotationRoll = gvcmPacketSyncFlagdata.bodyrotationRoll;
        }
        return null;
    }
}
