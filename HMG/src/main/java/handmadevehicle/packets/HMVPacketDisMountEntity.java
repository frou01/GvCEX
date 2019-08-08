package handmadevehicle.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

public class HMVPacketDisMountEntity implements IMessage {
	public int pickingEntityID;
	public int disMountEntityID;
	
	
	public HMVPacketDisMountEntity(){
	
	}
	public HMVPacketDisMountEntity(int pickingEntityID){
		this.pickingEntityID = pickingEntityID;
	}
	public HMVPacketDisMountEntity(int pickingEntityID, int disMountEntityID){
		this.pickingEntityID = pickingEntityID;
		this.disMountEntityID = disMountEntityID;
	}
	
	
	@Override
	public void fromBytes(ByteBuf buf) {
		pickingEntityID = buf.readInt();
		disMountEntityID = buf.readInt();
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(pickingEntityID);
		buf.writeInt(disMountEntityID);
	}
}
