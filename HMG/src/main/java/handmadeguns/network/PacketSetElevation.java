package handmadeguns.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

public class PacketSetElevation implements IMessage {
	public int targetEntityID,targetSlot,elevationID;

	public PacketSetElevation(){

	}
	public PacketSetElevation(int targetEntityID,int targetSlot,int elevationID){
		this.targetEntityID = targetEntityID;
		this.targetSlot = targetSlot;
		this.elevationID = elevationID;
	}

	//TODO クライアント側が選択した仰角設定番号を鯖に送信
	@Override
	public void fromBytes(ByteBuf buf) {
		this.targetEntityID = buf.readInt();
		this.targetSlot = buf.readInt();
		this.elevationID = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.targetEntityID);
		buf.writeInt(this.targetSlot);
		buf.writeInt(this.elevationID);
	}
}
