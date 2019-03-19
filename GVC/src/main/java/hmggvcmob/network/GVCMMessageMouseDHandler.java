package hmggvcmob.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import hmggvcmob.entity.friend.EntityPMCBase;
import hmggvcmob.entity.friend.GVCEntityPMCHeli;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class GVCMMessageMouseDHandler implements IMessageHandler<GVCMMessageMouseD, IMessage> {
    @Override
    public IMessage onMessage(GVCMMessageMouseD gvcmMessageMouseD, MessageContext messageContext) {
        EntityPlayer entityPlayer = messageContext.getServerHandler().playerEntity;
        EntityLivingBase en = (EntityLivingBase) entityPlayer.worldObj.getEntityByID(gvcmMessageMouseD.fre);
        return null;
    }
}
