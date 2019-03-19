package hmggvcmob.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

public class GVCPacketMGControl implements IMessage {
    public boolean w,a,s,d,sp,hold;
    public int weaponmode;
    public boolean trig1;
    public boolean trig2;
    public int targetID;

    public GVCPacketMGControl(){ }
    @Override
    public void fromBytes(ByteBuf buf) {
        targetID = buf.readInt();
        weaponmode = buf.readInt();
        w = buf.readBoolean();
        a = buf.readBoolean();
        s = buf.readBoolean();
        d = buf.readBoolean();
        sp = buf.readBoolean();
        hold = buf.readBoolean();
        trig1 = buf.readBoolean();
        trig2 = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(targetID);
        buf.writeInt(weaponmode);
        buf.writeBoolean(w);
        buf.writeBoolean(a);
        buf.writeBoolean(s);
        buf.writeBoolean(d);
        buf.writeBoolean(sp);
        buf.writeBoolean(hold);
        buf.writeBoolean(trig1);
        buf.writeBoolean(trig2);
    }
}
