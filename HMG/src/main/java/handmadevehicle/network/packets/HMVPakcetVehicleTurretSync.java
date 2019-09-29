package handmadevehicle.network.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import handmadevehicle.entity.parts.IMultiTurretVehicle;
import handmadevehicle.entity.parts.turrets.TurretObj;
import io.netty.buffer.ByteBuf;

public class HMVPakcetVehicleTurretSync implements IMessage {
    public int targetID;
    public int turretnum;

    public TurretSyncData[] turretSyncDatas;
    public HMVPakcetVehicleTurretSync(){
    }
    public HMVPakcetVehicleTurretSync(int tgtid , IMultiTurretVehicle multiTurretVehicle){
        targetID = tgtid;
        TurretObj[] turretObjs = multiTurretVehicle.getTurrets();
        turretnum = turretObjs.length;
        turretSyncDatas = new TurretSyncData[turretnum];
        for(int i = 0;i < turretObjs.length;i++){
            turretSyncDatas[i] = new TurretSyncData(turretObjs[i]);
        }
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        targetID = buf.readInt();
        turretnum = buf.readInt();
        turretSyncDatas = new TurretSyncData[turretnum];
        for(int i = 0;i < turretnum;i++){
            turretSyncDatas[i] = new TurretSyncData(buf);
        }
        
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(targetID);
        buf.writeInt(turretnum);
        for(int i = 0;i < turretnum;i++){
            turretSyncDatas[i].toBytes(buf);
        }
    }
}
