package handmadevehicle.packets;

import handmadevehicle.entity.parts.turrets.TurretObj;
import io.netty.buffer.ByteBuf;

public class TurretSyncData {
	public float yaw;
	public float pitch;
	public int reloadProgress;
	public int magazineState;
	public int   cycleState;
	public boolean   reloadState;
	public int childAndBrotherNum = 0;
	public TurretSyncData[] childData;
	public TurretSyncData(TurretObj turretObj){
		yaw = (float) turretObj.turretrotationYaw;
		pitch = (float) turretObj.turretrotationPitch;
		reloadProgress = turretObj.reloadTimer;
		magazineState = turretObj.magazinerem;
		reloadState = turretObj.readyload;
		cycleState = turretObj.cycle_timer;
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
		target.reloadTimer = reloadProgress;
		target.magazinerem = magazineState;
		target.readyload = reloadState;
		target.cycle_timer = cycleState;
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
		reloadProgress = buf.readInt();
		magazineState = buf.readInt();
		cycleState = buf.readInt();
		reloadState = buf.readBoolean();
		childAndBrotherNum = buf.readInt();
		childData = new TurretSyncData[childAndBrotherNum];
		for(int id = 0;id < childAndBrotherNum ; id ++){
			childData[id] = new TurretSyncData(buf);
		}
	}
	
	public void toBytes(ByteBuf buf) {
		buf.writeFloat(yaw);
		buf.writeFloat(pitch);
		buf.writeInt(reloadProgress);
		buf.writeInt(magazineState);
		buf.writeInt(cycleState);
		buf.writeBoolean(reloadState);
		buf.writeInt(childAndBrotherNum);
		for(TurretSyncData syncData:childData){
			syncData.toBytes(buf);
		}
	}
}
