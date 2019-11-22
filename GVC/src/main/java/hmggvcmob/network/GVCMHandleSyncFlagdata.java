package hmggvcmob.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import hmggvcmob.tile.TileEntityFlag;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import static handmadeguns.HandmadeGunsCore.HMG_proxy;
import static hmggvcmob.GVCMobPlus.campsHash;

public class GVCMHandleSyncFlagdata implements IMessageHandler<GVCMPacketSyncFlagdata, IMessage> {
    @Override
    public IMessage onMessage(GVCMPacketSyncFlagdata gvcmPacketSyncFlagdata, MessageContext messageContext) {
        World world;
//        System.out.println("debug");
        if(messageContext.side.isServer()) {
            world = messageContext.getServerHandler().playerEntity.worldObj;
        }else{
            world = HMG_proxy.getCilentWorld();
        }
        if(world!=null){
            TileEntity tile = world.getTileEntity(gvcmPacketSyncFlagdata.x,gvcmPacketSyncFlagdata.y,gvcmPacketSyncFlagdata.z);
            if(tile instanceof TileEntityFlag){
                ((TileEntityFlag) tile).flagHeight = gvcmPacketSyncFlagdata.height;
                ((TileEntityFlag) tile).respawncycle = gvcmPacketSyncFlagdata.respawncycle;
                if(gvcmPacketSyncFlagdata.campChanged){
                    ((TileEntityFlag) tile).campObj = campsHash.get(gvcmPacketSyncFlagdata.campName);
                }
            }
        }
        return null;
    }
}
