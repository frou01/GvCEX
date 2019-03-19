package hmggvcmob.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import hmggvcmob.entity.guerrilla.GVCEntityGK;
import hmggvcmob.util.GKdata;
import io.netty.buffer.ByteBuf;

import java.io.*;

public class GVCXMPacketSyncPMCHeliData implements IMessage {
    public int entityid;
    public float bodyrotationYaw;
    public float bodyrotationPitch;
    public float bodyrotationRoll;

    public GVCXMPacketSyncPMCHeliData(){ }
    public GVCXMPacketSyncPMCHeliData(int id, float yaw,float pitch,float roll){
        entityid = id;
        bodyrotationYaw = yaw;
        bodyrotationPitch = pitch;
        bodyrotationRoll = roll;
    }


    @Override
    public void toBytes(ByteBuf buffer)
    {
        buffer.writeInt(entityid);
        buffer.writeFloat(bodyrotationYaw);
        buffer.writeFloat(bodyrotationPitch);
        buffer.writeFloat(bodyrotationRoll);
//        System.out.println("debug");
    }
    @Override
    public void fromBytes(ByteBuf buffer)
    {
        this.entityid = buffer.readInt();
        bodyrotationYaw = buffer.readFloat();
        bodyrotationPitch = buffer.readFloat();
        bodyrotationRoll = buffer.readFloat();
    }
}
