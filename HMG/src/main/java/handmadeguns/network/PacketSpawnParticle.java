package handmadeguns.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;

public class PacketSpawnParticle implements IMessage {
    public double posx;
    public double posy;
    public double posz;
    public double motionX = 0;
    public double motionY = 0;
    public double motionZ = 0;
    public String name = "";
    public int fuse = 1;
    public float scale = 1;
    public int id;//
    public boolean is3d;
    public float trailwidth;
    public PacketSpawnParticle(){ }
    public PacketSpawnParticle(double x,double y,double z,int id){
        posx = x;
        posy = y;
        posz = z;
        this.id = id;
    }
    public PacketSpawnParticle(double x,double y,double z,String name){
        posx = x;
        posy = y;
        posz = z;
        this.name = name;
    }
    public PacketSpawnParticle(double x,double y,double z,double mx,double my,double mz,int id){
        posx = x;
        posy = y;
        posz = z;
        motionX = mx;
        motionY = my;
        motionZ = mz;
        this.id = id;
    }
    public PacketSpawnParticle(double x,double y,double z,double mx,double my,double mz,String name){
        posx = x;
        posy = y;
        posz = z;
        motionX = mx;
        motionY = my;
        motionZ = mz;
        this.name = name;
    }
    public PacketSpawnParticle(double x,double y,double z,double mx,double my,double mz,String name,boolean is3d){
        posx = x;
        posy = y;
        posz = z;
        motionX = mx;
        motionY = my;
        motionZ = mz;
        this.name = name;
        this.is3d = is3d;
    }
    @Override
    public void fromBytes(ByteBuf buf) {
        this.posx = buf.readDouble();
        this.posy = buf.readDouble();
        this.posz = buf.readDouble();
        this.motionX = buf.readDouble();
        this.motionY = buf.readDouble();
        this.motionZ = buf.readDouble();
        this.id = buf.readInt();
        this.fuse = buf.readInt();
        this.scale = buf.readFloat();
        this.is3d = buf.readBoolean();
        name = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeDouble(posx);
        buf.writeDouble(posy);
        buf.writeDouble(posz);
        buf.writeDouble(motionX);
        buf.writeDouble(motionY);
        buf.writeDouble(motionZ);
        buf.writeInt(id);
        buf.writeInt(fuse);
        buf.writeFloat(scale);
        buf.writeBoolean(is3d);
        ByteBufUtils.writeUTF8String(buf,name);
    }
}
