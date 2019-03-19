package handmadeguns.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

public class PacketTriggerUnder implements IMessage {
    public int playerid;
    public PacketTriggerUnder(){

    }
    public PacketTriggerUnder(int id){
        playerid = id;
    }
    @Override
    public void fromBytes(ByteBuf buf) {
        playerid = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(playerid);
    }
}
