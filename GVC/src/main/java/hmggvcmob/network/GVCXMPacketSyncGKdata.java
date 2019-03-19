package hmggvcmob.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import hmggvcmob.entity.guerrilla.GVCEntityGK;
import hmggvcmob.util.GKdata;
import io.netty.buffer.ByteBuf;

import java.io.*;

public class GVCXMPacketSyncGKdata implements IMessage {
    public int entityid;
    public GKdata data;

    public GVCXMPacketSyncGKdata(){ }
    public GVCXMPacketSyncGKdata(int id, GVCEntityGK serverentity){
        entityid = id;
        data = new GKdata(serverentity);
    }


    @Override
    public void toBytes(ByteBuf buffer)
    {
        buffer.writeInt(entityid);
        try {
            buffer.writeInt(fromObject(data).length);
            buffer.writeBytes(fromObject(data));
        } catch (NotSerializableException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
//        System.out.println("debug");
    }
    @Override
    public void fromBytes(ByteBuf buffer)
    {
        this.entityid = buffer.readInt();
        byte[] temp = new byte[buffer.readInt()];
        buffer.readBytes(temp);
        try{
            data = (GKdata) toObject(temp);
        }catch (OptionalDataException e){
            e.printStackTrace();
        }catch (StreamCorruptedException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }catch (ClassCastException e){
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
