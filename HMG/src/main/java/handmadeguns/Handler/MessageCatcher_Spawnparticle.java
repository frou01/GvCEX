package handmadeguns.Handler;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import handmadeguns.network.PacketSpawnParticle;

import static handmadeguns.HandmadeGunsCore.HMG_proxy;

public class MessageCatcher_Spawnparticle implements IMessageHandler<PacketSpawnParticle, IMessage> {
    @Override//IMessageHandler�̃��\�b�h
    public IMessage onMessage(PacketSpawnParticle message, MessageContext ctx) {
        HMG_proxy.spawnParticles(message);
        return null;
    }
}
