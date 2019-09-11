package handmadevehicle.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

public class HMVPacketMouseD implements IMessage {

    public float x;
    public float y;
    public float yaw;
    public int fre;
    public float cam_p;
    public float cam_y;

    public HMVPacketMouseD() {
    }
    
    public HMVPacketMouseD(float x, float y, float yaw, float cam_y, float cam_p, int entity) {
        this.x = x;
        this.y = y;
        this.yaw = yaw;
        this.fre = entity;
        this.cam_y = cam_y;
        this.cam_p = cam_p;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.x = buf.readFloat();
        this.y = buf.readFloat();
        this.yaw = buf.readFloat();
        this.cam_p = buf.readFloat();
        this.cam_y = buf.readFloat();
        this.fre = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeFloat(this.x);
        buf.writeFloat(this.y);
        buf.writeFloat(this.yaw);
        buf.writeFloat(this.cam_p);
        buf.writeFloat(this.cam_y);
        buf.writeInt(this.fre);
    }
}