package handmadeguns;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
 
public class HMGMessageKeyPressedC implements IMessage {
 
    public int key;
    public int entityID;
 
    public HMGMessageKeyPressedC(){}
 
    public HMGMessageKeyPressedC(int i) {
        this.key = i;
    }
    
    public HMGMessageKeyPressedC(int i, int j) {
        this.key = i;
        this.entityID = j;
    }
 
    @Override
    public void fromBytes(ByteBuf buf) {
        this.key = buf.readByte();
        this.entityID = buf.readInt();
    }
 
    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeByte(this.key);
        buf.writeInt(this.entityID);
    }
}