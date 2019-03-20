package hmgww2.network;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
 
public class WW2MessageKeyPressed implements IMessage {
 
    public int key;
    public int fre;
 
    public WW2MessageKeyPressed(){}
 
    public WW2MessageKeyPressed(int i) {
        this.key = i;
    }
    public WW2MessageKeyPressed(int i, int entity) {
        this.key = i;
        this.fre = entity;
    }
 
    @Override
    public void fromBytes(ByteBuf buf) {
        this.key = buf.readByte();
        this.fre = buf.readInt();
    }
 
    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeByte(this.key);
        buf.writeInt(this.fre);
    }
}