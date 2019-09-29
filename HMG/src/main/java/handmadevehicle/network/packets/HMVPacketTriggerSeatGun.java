package handmadevehicle.network.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

public class HMVPacketTriggerSeatGun implements IMessage {
	public boolean trigger1 = false;
	public boolean trigger2 = false;
	public int targetID;
	public int targetSeatID;
	public HMVPacketTriggerSeatGun(){
	
	}
	public HMVPacketTriggerSeatGun(boolean trigger1,boolean trigger2,int entityID,int seatID){
		this.trigger1 = trigger1;
		this.trigger2 = trigger2;
		this.targetID = entityID;
		this.targetSeatID = seatID;
	}
	@Override
	public void fromBytes(ByteBuf buf) {
		trigger1 = buf.readBoolean();
		trigger2 = buf.readBoolean();
		targetID = buf.readInt();
		targetSeatID = buf.readInt();
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(trigger1);
		buf.writeBoolean(trigger2);
		buf.writeInt(targetID);
		buf.writeInt(targetSeatID);
	}
}
