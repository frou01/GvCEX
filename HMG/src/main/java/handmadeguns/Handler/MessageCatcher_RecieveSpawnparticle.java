package handmadeguns.Handler;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import handmadeguns.HMGPacketHandler;
import handmadeguns.network.PacketRequestSpawnParticle;
import handmadeguns.network.PacketSpawnParticle;
import net.minecraft.world.World;

public class MessageCatcher_RecieveSpawnparticle implements IMessageHandler<PacketRequestSpawnParticle, IMessage> {
    @Override//IMessageHandler�̃��\�b�h
    public IMessage onMessage(PacketRequestSpawnParticle message, MessageContext ctx) {
        World world;
//        System.out.println("debug");
        if(message.name.equals("null"))
            HMGPacketHandler.INSTANCE.sendToAll(new PacketSpawnParticle(message.posx,message.posy,message.posz,message.motionX,message.motionY,message.motionZ,message.id));
        else
            HMGPacketHandler.INSTANCE.sendToAll(new PacketSpawnParticle(message.posx,message.posy,message.posz,message.motionX,message.motionY,message.motionZ,message.name));
        return null;
    }
}
