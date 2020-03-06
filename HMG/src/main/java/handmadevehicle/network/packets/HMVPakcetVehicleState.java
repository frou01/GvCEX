package handmadevehicle.network.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;

public class HMVPakcetVehicleState implements IMessage {
    public int targetID;
    public Quat4d rot = new Quat4d(0,0,0,1);
    public Quat4d rotmotion = new Quat4d(0,0,0,1);
    public Vector3d motionVec = new Vector3d();
    public Vector3d posVec = new Vector3d();
    public float th;
    public float health;
    public boolean onGround;

    public HMVPakcetVehicleState(){
    }
    public HMVPakcetVehicleState(int tgtid , Quat4d tgtrot , Quat4d tgtrotmotion , Vector3d tgtmotion ,Vector3d posVec , float t , float health,boolean onGround,boolean mouseMode){
        this.targetID = tgtid;
        this.rot = tgtrot;
        this.rotmotion = tgtrotmotion;
        this.motionVec = tgtmotion;
        this.posVec = posVec;
        this.th = t;
        this.health = health;
        this.onGround = onGround;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        targetID = buf.readInt();
        th = buf.readFloat();
        rot.x = buf.readDouble();
        rot.y = buf.readDouble();
        rot.z = buf.readDouble();
        rot.w = buf.readDouble();
        rotmotion.x = buf.readDouble();
        rotmotion.y = buf.readDouble();
        rotmotion.z = buf.readDouble();
        rotmotion.w = buf.readDouble();
        motionVec.x = buf.readDouble();
        motionVec.y = buf.readDouble();
        motionVec.z = buf.readDouble();
        posVec.x = buf.readDouble();
        posVec.y = buf.readDouble();
        posVec.z = buf.readDouble();
        health = buf.readFloat();
        onGround = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(targetID);
        buf.writeFloat(th);
        buf.writeDouble(rot.x);
        buf.writeDouble(rot.y);
        buf.writeDouble(rot.z);
        buf.writeDouble(rot.w);
        buf.writeDouble(rotmotion.x);
        buf.writeDouble(rotmotion.y);
        buf.writeDouble(rotmotion.z);
        buf.writeDouble(rotmotion.w);
        buf.writeDouble(motionVec.x);
        buf.writeDouble(motionVec.y);
        buf.writeDouble(motionVec.z);
        buf.writeDouble(posVec.x);
        buf.writeDouble(posVec.y);
        buf.writeDouble(posVec.z);
        buf.writeFloat(health);
        buf.writeBoolean(onGround);
    }
}
