package hmvehicle.handles;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import hmvehicle.entity.parts.HasBaseLogic;
import hmvehicle.entity.parts.IneedMouseTrack;
import hmvehicle.packets.HMVPacketMouseD;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class HMVHandleMouseD implements IMessageHandler<HMVPacketMouseD, IMessage> {
    @Override
    public IMessage onMessage(HMVPacketMouseD packetMouseD, MessageContext messageContext) {
        EntityPlayer entityPlayer = messageContext.getServerHandler().playerEntity;
        Entity en = entityPlayer.worldObj.getEntityByID(packetMouseD.fre);
        if(en instanceof HasBaseLogic && ((HasBaseLogic) en).getBaseLogic() instanceof IneedMouseTrack){
            ((IneedMouseTrack)((HasBaseLogic) en).getBaseLogic()).setMouse(packetMouseD.x,packetMouseD.y);
        }else if(en instanceof IneedMouseTrack){
            ((IneedMouseTrack) en).setMouse(packetMouseD.x,packetMouseD.y);
        }
        return null;
    }
}
