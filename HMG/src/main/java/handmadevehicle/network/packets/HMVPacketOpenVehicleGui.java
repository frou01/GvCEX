package handmadevehicle.network.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

public class HMVPacketOpenVehicleGui implements IMessage {
	public int ID, playerID, vehicleID;
	public HMVPacketOpenVehicleGui(){
	
	}
	public HMVPacketOpenVehicleGui(int ID,int playerID,int vehicleID){
		this.ID = ID;
		this.playerID = playerID;
		this.vehicleID = vehicleID;
	}
	@Override
	public void fromBytes(ByteBuf buf) {
		ID = buf.readInt();
		playerID = buf.readInt();
		vehicleID = buf.readInt();
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(ID);
		buf.writeInt(playerID);
		buf.writeInt(vehicleID);
	}
}
