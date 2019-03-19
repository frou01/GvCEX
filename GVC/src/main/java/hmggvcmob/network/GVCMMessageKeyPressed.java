package hmggvcmob.network;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
 
public class GVCMMessageKeyPressed implements IMessage {
 
    public int[] keys;
    public int targetID;
 
    public GVCMMessageKeyPressed(){}
 
    public GVCMMessageKeyPressed(int[] i) {
        this.keys = i;
    }
    public GVCMMessageKeyPressed(int[] i, int entity) {
        this.keys = i;
        this.targetID = entity;
    }
 
    @Override
    public void fromBytes(ByteBuf buf) {
        int num = buf.readByte();
        keys = new int[num];
        for(int i = 0;i<num;i++){
            keys[i] = buf.readByte();
        }
        this.targetID = buf.readInt();
    }
 
    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeByte(this.keys.length);
        for(int i = 0;i<keys.length;i++){
            buf.writeByte(keys[i]);
        }
        buf.writeInt(this.targetID);
    }
}