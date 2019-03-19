package handmadeguns.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;

import java.io.*;

public class PacketDamageHeldItemsAttach implements IMessage {
    public int entityid;
    public int shooterid;
    public int value;

    public PacketDamageHeldItemsAttach(){ }
    public PacketDamageHeldItemsAttach(Entity shootentity, int typ){
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

    public static byte[] fromObject(Object o) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = new ObjectOutputStream(bos);
        out.writeObject(o);
        byte[] bytes = bos.toByteArray();
        out.close();
        bos.close();
        return bytes;
    }
    public static Object toObject(byte[] bytes) throws OptionalDataException, StreamCorruptedException, ClassNotFoundException, IOException{
        return new ObjectInputStream(new ByteArrayInputStream(bytes)).readObject();
    }
}