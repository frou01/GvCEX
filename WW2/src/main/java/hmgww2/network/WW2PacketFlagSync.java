package hmgww2.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import hmgww2.Nation;
import io.netty.buffer.ByteBuf;

public class WW2PacketFlagSync implements IMessage {
	public int x;
	public int y;
	public int z;
	public int inv;
	public Nation nation;
	
	public WW2PacketFlagSync(){
	
	}
	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		inv = buf.readInt();
		int nationID = buf.readInt();
		if(nationID != -1)nation = Nation.values()[nationID];
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(inv);
		buf.writeInt(nation != null ? nation.ordinal():-1);
	}
}
