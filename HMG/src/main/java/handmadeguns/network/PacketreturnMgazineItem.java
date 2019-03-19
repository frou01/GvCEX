package handmadeguns.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

public class PacketreturnMgazineItem implements IMessage {
    public int entityid;
    public PacketreturnMgazineItem(){
    }

    public PacketreturnMgazineItem(int entityid){
        this.entityid = entityid;
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        entityid = byteBuf.readInt();
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeInt(entityid);
    }
}
