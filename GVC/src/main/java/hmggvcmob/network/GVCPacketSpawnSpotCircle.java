package hmggvcmob.network;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import hmggvcmob.util.SpotObj;
import hmggvcmob.util.SpotType;
import io.netty.buffer.ByteBuf;

import java.io.IOException;
import java.util.ArrayList;

import static hmggvcmob.GVCMobPlus.proxy;
import static hmggvcmob.util.Calculater.*;

public class GVCPacketSpawnSpotCircle implements IMessage {
	ArrayList<SpotObj> recievedSpotobj = new ArrayList<>();
	int dimid;
	
	public GVCPacketSpawnSpotCircle(){}
	
	
	public GVCPacketSpawnSpotCircle(int[] poses,int[] targetEntityIDs,int[] types,int dimid){}
	public GVCPacketSpawnSpotCircle(ArrayList<SpotObj> spotObjs, int dimid){
		this.dimid = dimid;
		recievedSpotobj = spotObjs;
	}
	@Override
	public void fromBytes(ByteBuf buf) {
		dimid = buf.readInt();
		int size = buf.readInt();
		for(int i = 0;i < size;i++){
			{
				SpotObj addobj = new SpotObj();
				addobj.type = SpotType.values()[buf.readInt()];
				addobj.targetID = buf.readInt();
				addobj.pos[0] = buf.readFloat();
				addobj.pos[1] = buf.readFloat();
				addobj.pos[2] = buf.readFloat();
				addobj.remaintime = buf.readInt();
				if(proxy.getEntityPlayerInstance() != null && (proxy.getEntityPlayerInstance().getTeam() == null || proxy.getEntityPlayerInstance().getTeam().getRegisteredName().equals(addobj.teamname)))recievedSpotobj.add(addobj);
			}
		}
		
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(dimid);
		buf.writeInt(recievedSpotobj.size());
		for(SpotObj aspotobj : recievedSpotobj){
			{
				buf.writeInt(aspotobj.type.ordinal());
				buf.writeInt(aspotobj.targetID);
				buf.writeFloat(aspotobj.pos[0]);
				buf.writeFloat(aspotobj.pos[1]);
				buf.writeFloat(aspotobj.pos[2]);
				buf.writeInt(aspotobj.remaintime);
			}
		}
	}
}
