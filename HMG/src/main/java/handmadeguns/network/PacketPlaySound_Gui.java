package handmadeguns.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

import static cpw.mods.fml.common.network.ByteBufUtils.readUTF8String;
import static cpw.mods.fml.common.network.ByteBufUtils.writeUTF8String;

public class PacketPlaySound_Gui implements IMessage {
	public PacketPlaySound_Gui(){

	}
	public String name;
	public float speed;

	public PacketPlaySound_Gui(String name,float speed){
		this.name = name;
		this.speed = speed;
	}
	@Override
	public void fromBytes(ByteBuf buf) {
		name = readUTF8String(buf);
		speed = buf.readFloat();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		writeUTF8String(buf,name);
		buf.writeFloat(speed);
	}
}
