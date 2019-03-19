package hmggvcmob.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

public class GVCPakcetHeliGunnerTrigger implements IMessage {
    public int targetID;
    public boolean trigger2;

    public GVCPakcetHeliGunnerTrigger(){
    }
    public GVCPakcetHeliGunnerTrigger(int tgtid , boolean tr2){
        targetID = tgtid;
        trigger2 = tr2;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        targetID = buf.readInt();
        trigger2 = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(targetID);
        buf.writeBoolean(trigger2);
    }
}
