package handmadeguns.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;

import java.io.*;

public class PacketShotBullet implements IMessage {
    public int entityid;
    public int shooterid;
    public int type;
    public boolean isunder;
    public boolean useundersettings;
    public boolean isADS;

    public PacketShotBullet(){ }
    public PacketShotBullet(Entity shootentity,int typ,boolean isunder,boolean ads ){
        shooterid = shootentity.getEntityId();
        type = typ;
        this.isunder = isunder;
        isADS = ads;
    }

    @Override
    public void toBytes(ByteBuf buffer)
    {
        buffer.writeInt(entityid);
        buffer.writeInt(shooterid);
        buffer.writeInt(type);
        buffer.writeBoolean(isunder);
        buffer.writeBoolean(useundersettings);
//        System.out.println("debug");
    }
    @Override
    public void fromBytes(ByteBuf buffer)
    {
        entityid = buffer.readInt();
        shooterid = buffer.readInt();
        type = buffer.readInt();
        isunder = buffer.readBoolean();
        useundersettings = buffer.readBoolean();
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
