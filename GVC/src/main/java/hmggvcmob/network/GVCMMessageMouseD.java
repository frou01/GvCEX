package hmggvcmob.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

public class GVCMMessageMouseD implements IMessage {

    public int x;
    public int y;
    public int fre;

    public GVCMMessageMouseD() {
    }

    public GVCMMessageMouseD(int x, int y, int entity) {
        this.x = x;
        this.y = y;
        this.fre = entity;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.x = buf.readByte();
        this.y = buf.readByte();
        this.fre = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeByte(this.x);
        buf.writeByte(this.y);
        buf.writeInt(this.fre);
    }
}