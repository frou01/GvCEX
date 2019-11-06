package handmadevehicle.network.packets;

import handmadevehicle.entity.parts.turrets.TurretObj;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;

import static cpw.mods.fml.common.network.ByteBufUtils.*;

public class TurretSyncData {
	public float yaw;
	public float pitch;
	public NBTTagCompound gunState;
	public int gunDamaged;
	public int childAndBrotherNum = 0;
	public TurretSyncData[] childData;
	public TurretSyncData(TurretObj turretObj){
		yaw = (float) turretObj.turretrotationYaw;
		pitch = (float) turretObj.turretrotationPitch;
		if(turretObj.dummyGunStack != null) {
			gunState = turretObj.getDummyStackTag();
			gunDamaged = turretObj.dummyGunStack.getItemDamage();
		}
		childData = new TurretSyncData[turretObj.getChilds().size() + turretObj.getChildsOnBarrel().size() + turretObj.getBrothers().size()];
		int id = 0;
		for(TurretObj aturretObj : turretObj.getChilds()){
			childData[id] = new TurretSyncData(aturretObj);
			id++;
		}
		for(TurretObj aturretObj : turretObj.getChildsOnBarrel()){
			childData[id] = new TurretSyncData(aturretObj);
			id++;
		}
		for(TurretObj aturretObj : turretObj.getBrothers()){
			childData[id] = new TurretSyncData(aturretObj);
			id++;
		}
		childAndBrotherNum = id;
	}
	public void setTurretData(TurretObj target){
		target.prevturretrotationYaw = target.turretrotationYaw;
		target.prevturretrotationPitch = target.turretrotationPitch;
		target.turretrotationYaw = yaw;
		target.turretrotationPitch = pitch;
		if(target.dummyGunStack != null) {
			target.dummyGunStack.setTagCompound(gunState);
			target.dummyGunStack.setItemDamage(gunDamaged);
		}
		int id = 0;
		for(TurretObj aturretObj : target.getChilds()){
			childData[id].setTurretData(aturretObj);
			id++;
		}
		for(TurretObj aturretObj : target.getChildsOnBarrel()){
			childData[id].setTurretData(aturretObj);
			id++;
		}
		for(TurretObj aturretObj : target.getBrothers()){
			childData[id].setTurretData(aturretObj);
			id++;
		}
	}
	
	public TurretSyncData(ByteBuf buf) {
		yaw = buf.readFloat();
		pitch = buf.readFloat();
		gunState = readTag(buf);
		gunDamaged = buf.readInt();
		childAndBrotherNum = buf.readInt();
		childData = new TurretSyncData[childAndBrotherNum];
		for(int id = 0;id < childAndBrotherNum ; id ++){
			childData[id] = new TurretSyncData(buf);
		}
	}
	
	public void toBytes(ByteBuf buf) {
		buf.writeFloat(yaw);
		buf.writeFloat(pitch);
		writeTag(buf,gunState);
		buf.writeInt(gunDamaged);
		buf.writeInt(childAndBrotherNum);
		for(TurretSyncData syncData:childData){
			syncData.toBytes(buf);
		}
	}
}
