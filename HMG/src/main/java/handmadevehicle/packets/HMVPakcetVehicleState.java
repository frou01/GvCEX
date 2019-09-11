package handmadevehicle.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

import javax.vecmath.Quat4d;

public class HMVPakcetVehicleState implements IMessage {
    public int targetID;
    public Quat4d rot = new Quat4d();
    public float th;

    public HMVPakcetVehicleState(){
    }
    public HMVPakcetVehicleState(int tgtid , Quat4d tgtrot , float t, boolean tr1, boolean tr2){
        targetID = tgtid;
        rot = tgtrot;
        th = t;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        targetID = buf.readInt();
        th = buf.readFloat();
        rot.x = buf.readDouble();
        rot.y = buf.readDouble();
        rot.z = buf.readDouble();
        rot.w = buf.readDouble();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(targetID);
        buf.writeFloat(th);
        buf.writeDouble(rot.x);
        buf.writeDouble(rot.y);
        buf.writeDouble(rot.z);
        buf.writeDouble(rot.w);
    }
}
