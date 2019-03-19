package handmadeguns.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

public class PacketRequestSpawnParticle implements IMessage {
    public double posx;
    public double posy;
    public double posz;
    public double motionX = 0;
    public double motionY = 0;
    public double motionZ = 0;
    public String name = "null";
    public int id;
    public PacketRequestSpawnParticle(){ }
    public PacketRequestSpawnParticle(double x,double y,double z,int id){
        posx = x;
        posy = y;
        posz = z;
        this.id = id;
    }
    public PacketRequestSpawnParticle(double x,double y,double z,double mx,double my,double mz,int id){
        posx = x;
        posy = y;
        posz = z;
        motionX = mx;
        motionY = my;
        motionZ = mz;
        this.id = id;
    }
    public PacketRequestSpawnParticle(double x,double y,double z,double mx,double my,double mz,String name){
        posx = x;
        posy = y;
        posz = z;
        motionX = mx;
        motionY = my;
        motionZ = mz;
        this.name = name;
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
        ByteBufUtils.writeUTF8String(buf,name);
    }
}
