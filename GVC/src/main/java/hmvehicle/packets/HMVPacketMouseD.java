package hmvehicle.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

public class HMVPacketMouseD implements IMessage {

    public float x;
    public float y;
    public float z;
    public int fre;

    public HMVPacketMouseD() {
    }

    public HMVPacketMouseD(float x, float y, int entity) {
        this.x = x;
        this.y = y;
        this.fre = entity;
    }
    public HMVPacketMouseD(float x, float y,float z, int entity) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.fre = entity;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.x = buf.readFloat();
        this.y = buf.readFloat();
        this.z = buf.readFloat();
        this.fre = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeFloat(this.x);
        buf.writeFloat(this.y);
        buf.writeFloat(this.z);
        buf.writeInt(this.fre);
    }
}