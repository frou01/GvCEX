package hmggvcmob.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

import static cpw.mods.fml.common.network.ByteBufUtils.readUTF8String;
import static cpw.mods.fml.common.network.ByteBufUtils.writeUTF8String;

public class GVCMPacketSyncFlagdata implements IMessage {
    public int x;
    public int y;
    public int z;
    public int height;
    public int respawncycle;
    public boolean campChanged;
    public String campName;
    public GVCMPacketSyncFlagdata(){
    }
    @Override
    public void fromBytes(ByteBuf byteBuf) {
        x = byteBuf.readInt();
        y = byteBuf.readInt();
        z = byteBuf.readInt();
        height = byteBuf.readInt();
        respawncycle = byteBuf.readInt();
        campChanged = byteBuf.readBoolean();
        if(campChanged){
            campName = readUTF8String(byteBuf);
        }
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeInt(x);
        byteBuf.writeInt(y);
        byteBuf.writeInt(z);
        byteBuf.writeInt(height);
        byteBuf.writeInt(respawncycle);
        byteBuf.writeBoolean(campChanged);
        if(campChanged){
            writeUTF8String(byteBuf,campName);
        }
    }
}
