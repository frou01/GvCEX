package handmadevehicle.network.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

public class HMVPacketChangeSeat implements IMessage {
	public int targetID;
	public int currentSeatID;
	public boolean dir;
	public HMVPacketChangeSeat(){
	
	}
	public HMVPacketChangeSeat(int targetID,int currentSeatID,boolean dir){
		this.targetID = targetID;
		this.currentSeatID = currentSeatID;
		this.dir = dir;
	}
	@Override
	public void fromBytes(ByteBuf buf) {
		targetID = buf.readInt();
		currentSeatID = buf.readInt();
		dir = buf.readBoolean();
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(targetID);
		buf.writeInt(currentSeatID);
		buf.writeBoolean(dir);
	}
}
