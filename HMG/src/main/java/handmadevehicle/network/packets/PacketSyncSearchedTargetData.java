package handmadevehicle.network.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import handmadeguns.Util.EntityLinkedPos_Motion;
import handmadevehicle.entity.parts.turrets.TurretObj;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;

import static cpw.mods.fml.common.network.ByteBufUtils.readTag;
import static cpw.mods.fml.common.network.ByteBufUtils.writeTag;

public class PacketSyncSearchedTargetData implements IMessage {
	public ArrayList<EntityLinkedPos_Motion> targets = new ArrayList<EntityLinkedPos_Motion>();
	public int syncTargetID;
	public PacketSyncSearchedTargetData(){

	}
	public PacketSyncSearchedTargetData(ArrayList<EntityLinkedPos_Motion> targets, int syncTargetID){
		this.targets = targets;
		this.syncTargetID = syncTargetID;
	}

	public void fromBytes(ByteBuf buf) {
		syncTargetID = buf.readInt();
		int size = buf.readInt();
		for(int i = 0;i < size;i++){
			EntityLinkedPos_Motion addNew = new EntityLinkedPos_Motion();
			addNew.fromBytes(buf);
			targets.add(addNew);
		}
	}

	public void toBytes(ByteBuf buf) {
		buf.writeInt(syncTargetID);
		buf.writeInt(targets.size());
		for(EntityLinkedPos_Motion aObj: targets){
			aObj.toBytes(buf);
		}
	}
}
