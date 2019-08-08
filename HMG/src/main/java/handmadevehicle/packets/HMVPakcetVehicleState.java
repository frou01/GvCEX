package handmadevehicle.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

import javax.vecmath.Quat4d;

public class HMVPakcetVehicleState implements IMessage {
    public int targetID;
    public Quat4d rot = new Quat4d();
    public float th;
    public float yawmotion;
    public Quat4d turret;
    public boolean trigger1;
    public boolean trigger2;

    public HMVPakcetVehicleState(){
    }
    public HMVPakcetVehicleState(int tgtid , Quat4d tgtrot , float t, boolean tr1, boolean tr2){
        targetID = tgtid;
        rot = tgtrot;
        th = t;
        trigger1 = tr1;
        trigger2 = tr2;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        targetID = buf.readInt();
        th = buf.readFloat();
        yawmotion = buf.readFloat();
        trigger1 = buf.readBoolean();
        trigger2 = buf.readBoolean();
        rot.x = buf.readDouble();
        rot.y = buf.readDouble();
        rot.z = buf.readDouble();
        rot.w = buf.readDouble();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(targetID);
        buf.writeFloat(th);
        buf.writeFloat(yawmotion);
        buf.writeBoolean(trigger1);
        buf.writeBoolean(trigger2);
        buf.writeDouble(rot.x);
        buf.writeDouble(rot.y);
        buf.writeDouble(rot.z);
        buf.writeDouble(rot.w);
    }
}
