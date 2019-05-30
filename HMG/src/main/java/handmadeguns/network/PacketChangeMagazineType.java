package handmadeguns.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;

import java.io.*;

public class PacketChangeMagazineType implements IMessage {
	public int entityid;
	public int shooterid;
	public int value;
	
	public PacketChangeMagazineType(){ }
	public PacketChangeMagazineType(Entity shootentity, int typ){
		shooterid = shootentity.getEntityId();
		value = typ;
	}
	
	
	@Override
	public void toBytes(ByteBuf buffer)
	{
		buffer.writeInt(shooterid);
		buffer.writeInt(value);
//        System.out.println("debug");
	}
	@Override
	public void fromBytes(ByteBuf buffer)
	{
		shooterid = buffer.readInt();
		value = buffer.readInt();
	}
}