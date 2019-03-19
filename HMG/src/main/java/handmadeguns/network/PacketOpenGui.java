package handmadeguns.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

public class PacketOpenGui implements IMessage {
    public int guiID;
    public int entityID;
    public PacketOpenGui(){

    }

    public PacketOpenGui(int guiid,int entityid){
        this.guiID = guiid;
        this.entityID = entityid;
    }
    @Override
    public void fromBytes(ByteBuf byteBuf) {
        guiID = byteBuf.readInt();
        entityID = byteBuf.readInt();
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeInt(guiID);
        byteBuf.writeInt(entityID);
    }
}
