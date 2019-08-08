package handmadevehicle.packets;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
 
public class HMVMMessageKeyPressed implements IMessage {
 
    public int[] keys;
    public int targetID;
 
    public HMVMMessageKeyPressed(){}
 
    public HMVMMessageKeyPressed(int[] i) {
        this.keys = i;
    }
    public HMVMMessageKeyPressed(int[] i, int entity) {
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