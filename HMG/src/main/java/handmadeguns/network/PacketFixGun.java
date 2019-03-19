package handmadeguns.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

public class PacketFixGun implements IMessage {

    public int shooterid = -1;
    public PacketFixGun(){
    }
    public PacketFixGun(int id ){
        shooterid = id;
    }
    @Override
    public void fromBytes(ByteBuf buf) {
        shooterid = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(shooterid);
    }
}
