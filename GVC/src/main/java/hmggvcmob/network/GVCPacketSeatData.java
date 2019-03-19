package hmggvcmob.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

public class GVCPacketSeatData implements IMessage {

    public int targetID = -1;
    public int seatnum = -1;
    public int[] seatEntityIDs = null;
    public int[] IDs = null;
    public float[] seatsizex = null;
    public float[] seatsizey = null;

    public GVCPacketSeatData(){

    }
    public GVCPacketSeatData(int tgtID){
        targetID = tgtID;
    }
    public GVCPacketSeatData(int tgtID,int num,int[] entityids,int[] ids,float[] seatsizex,float[] seatsizey){
        this(tgtID);
        seatnum = num;
        seatEntityIDs = entityids;
        IDs = ids;
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
            IDs = new int[seatnum];
            for(int i = 0; i < IDs.length; i ++ ){
                IDs[i] = buf.readInt();
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
            for (int seatID : IDs) {
                buf.writeInt(seatID);
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
