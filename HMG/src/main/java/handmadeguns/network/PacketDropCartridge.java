package handmadeguns.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;

import java.io.*;

public class PacketDropCartridge implements IMessage {
    public int shooterid;
    public String modelname = "default";
    public int type;

    public PacketDropCartridge(){ }
    public PacketDropCartridge(Entity shootentity, int typ){
        shooterid = shootentity.getEntityId();
        type = typ;
    }
    public PacketDropCartridge(Entity shootentity, int typ,String model){
        shooterid = shootentity.getEntityId();
        type = -1;
        modelname = model;
    }


    @Override
    public void toBytes(ByteBuf buffer)
    {
        buffer.writeInt(shooterid);
        buffer.writeInt(type);
        try {
            byte[] soundname = fromObject(modelname);
            buffer.writeInt(soundname.length);
            buffer.writeBytes(soundname);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println("debug");
    }
    @Override
    public void fromBytes(ByteBuf buffer)
    {
        shooterid = buffer.readInt();
        type = buffer.readInt();
        byte[] temp = new byte[buffer.readInt()];
        buffer.readBytes(temp);
        try {
            modelname = (String)toObject(temp);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
