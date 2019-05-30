package hmvehicle.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

public class HMVPacketSeatData implements IMessage {

    public int targetID = -1;
    public int seatnum = -1;
    public int[] seatEntityIDs = null;
    public float[] seatsizex = null;
    public float[] seatsizey = null;

    public HMVPacketSeatData(){

    }
    public HMVPacketSeatData(int tgtID){
        targetID = tgtID;
    }
    public HMVPacketSeatData(int tgtID, int num, int[] entityids, float[] seatsizex, float[] seatsizey){
        this(tgtID);
        seatnum = num;
        seatEntityIDs = entityids;
        this.seatsizex = seatsizex;
        this.seatsizey = seatsizey;
    }
    @Override
    public void fromBytes(ByteBuf buf) {
        targetID = buf.readInt();
        seatnum = buf.readInt();
        if(seatnum!= -1){
            seatEntityIDs = new int[seatnum];
            for(int i = 0; i < seatEntityIDs.length; i ++ ){
                seatEntityIDs[i] = buf.readInt();
            }
            seatsizex = new float[seatnum];
            for(int i = 0; i < seatsizex.length; i ++ ){
                seatsizex[i] = buf.readFloat();
            }
            seatsizey = new float[seatnum];
            for(int i = 0; i < seatsizey.length; i ++ ){
                seatsizey[i] = buf.readFloat();
            }
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(targetID);
        buf.writeInt(seatnum);
        if(seatnum != -1){
            for (int seatentityID : seatEntityIDs) {
                buf.writeInt(seatentityID);
            }
            for (float seatsizex : seatsizex) {
                buf.writeFloat(seatsizex);
            }
            for (float seatsizey : seatsizey) {
                buf.writeFloat(seatsizey);
            }
        }
    }
}
