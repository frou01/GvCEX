package handmadevehicle.network.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;

import static cpw.mods.fml.common.network.ByteBufUtils.readTag;
import static cpw.mods.fml.common.network.ByteBufUtils.writeTag;

public class HMVPacketSyncInventory implements IMessage {
	public HMVPacketSyncInventory(){
	}
	public HMVPacketSyncInventory(Entity entity){
		this.targetID = entity.getEntityId();
		this.data = entity.getEntityData();
	}

	public int targetID;
	public NBTTagCompound data;
	@Override
	public void fromBytes(ByteBuf buf) {
		targetID = buf.readInt();
		data = readTag(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(targetID);
		writeTag(buf, data);
	}
}
