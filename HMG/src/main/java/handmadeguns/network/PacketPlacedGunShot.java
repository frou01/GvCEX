package handmadeguns.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

public class PacketPlacedGunShot implements IMessage {

    public int shooterid = -1;
    public boolean firing;
    public PacketPlacedGunShot(){
    }
    public PacketPlacedGunShot(int id , boolean flag){
        shooterid = id;
        firing = flag;
    }
    @Override
    public void fromBytes(ByteBuf buf) {
        shooterid = buf.readInt();
        firing = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(shooterid);
        buf.writeBoolean(firing);
    }
}
