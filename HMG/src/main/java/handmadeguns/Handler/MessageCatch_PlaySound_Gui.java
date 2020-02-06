package handmadeguns.Handler;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import handmadeguns.network.PacketPlaySound_Gui;

import static handmadeguns.HandmadeGunsCore.HMG_proxy;

public class MessageCatch_PlaySound_Gui implements IMessageHandler<PacketPlaySound_Gui, IMessage> {
	@Override
	public IMessage onMessage(PacketPlaySound_Gui message, MessageContext ctx) {
		HMG_proxy.playGUISound(message.name,message.speed);
		return null;
	}
}
