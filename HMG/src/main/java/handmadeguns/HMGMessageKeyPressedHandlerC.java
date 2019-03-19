package handmadeguns;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class HMGMessageKeyPressedHandlerC implements IMessageHandler<HMGMessageKeyPressedC, IMessage> {
 
    @Override
    public IMessage onMessage(HMGMessageKeyPressedC message, MessageContext ctx) {
    	EntityPlayer player = HandmadeGunsCore.proxy.getEntityPlayerInstance();
    	if(message.key == 10){
    		//Entity en = (Entity) player.worldObj.getEntityByID(message.key2);
    		//if(player == en)
    		if(player.getEntityId() == message.entityID)
    		{
    			NBTTagCompound nbt = player.getEntityData();
    			nbt.setInteger("hitentity", 20);
    			//player.worldObj.playSoundAtEntity(player, "gvcmob:gvcmob.heli", 4.0F, 1.0F);
    			//player.addChatComponentMessage(new ChatComponentText(String.format("Received byte %d", message.key2)));
    		}
    	}
    	
    	
    	
        return null;
    }
}