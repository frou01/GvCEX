package hmvehicle.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;

public class HMVPacketPickNewEntity implements IMessage {
	public int pickingEntityID;
	public int[] pickedEntityIDs = null;
	public boolean addMode = false;
	
	
	public HMVPacketPickNewEntity(){
	
	}
	public HMVPacketPickNewEntity(int pickingEntityID){
		this.pickingEntityID = pickingEntityID;
	}
	public HMVPacketPickNewEntity(int pickingEntityID,int pickedEntityID){
		this.pickingEntityID = pickingEntityID;
		this.pickedEntityIDs = new int[]{pickedEntityID};
		addMode = true;
	}
	
	public HMVPacketPickNewEntity(int pickingEntityID,int[] pickedEntityIDs){
		this.pickingEntityID = pickingEntityID;
		this.pickedEntityIDs = pickedEntityIDs;
	}
	public HMVPacketPickNewEntity(int pickingEntityID,Entity[] pickedEntities){
		this.pickingEntityID = pickingEntityID;
		int[] pickedEntityIDs = new int[pickedEntities.length];
		int cnt = 0;
		for(Entity entity:pickedEntities){
			if(entity != null)pickedEntityIDs[cnt] = entity.getEntityId();
			else pickedEntityIDs[cnt] = -1;
			cnt++;
		}
		this.pickedEntityIDs = pickedEntityIDs;
	}
	
	
	@Override
	public void fromBytes(ByteBuf buf) {
		pickingEntityID = buf.readInt();
		addMode = buf.readBoolean();
		int length = buf.readInt();
		if(length != -1) {
			pickedEntityIDs = new int[length];
			for (int id = 0; id < length; id++)
				pickedEntityIDs[id] = buf.readInt();
		}
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(pickingEntityID);
		buf.writeBoolean(addMode);
		if(pickedEntityIDs != null) {
			buf.writeInt(pickedEntityIDs.length);
			for (int id : pickedEntityIDs)
				buf.writeInt(id);
		}else
			buf.writeInt(-1);
	}
}
