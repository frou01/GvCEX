package handmadevehicle.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import handmadevehicle.entity.parts.IMultiTurretVehicle;
import handmadevehicle.entity.parts.turrets.TurretObj;
import io.netty.buffer.ByteBuf;

public class HMVPakcetVehicleTurretSync implements IMessage {
    public int targetID;
    public int turretnum;
    public float[] yaws;
    public float[] pitchs;
    public int[] reloadprogress;
    public boolean[]   reloadstates;
    public int[]   cyclestates;

    public HMVPakcetVehicleTurretSync(){
    }
    public HMVPakcetVehicleTurretSync(int tgtid , IMultiTurretVehicle multiTurretVehicle){
        targetID = tgtid;
        TurretObj[] turretObjs = multiTurretVehicle.getTurrets();
        turretnum = turretObjs.length;
        
        yaws = new float[turretnum];
        pitchs = new float[turretnum];
        reloadprogress = new int[turretnum];
        reloadstates = new boolean[turretnum];
        cyclestates = new int[turretnum];
        for(int i = 0;i < turretObjs.length;i++){
            TurretObj aturret = turretObjs[i];
            yaws[i] = (float) aturret.turretrotationYaw;
            pitchs[i] = (float) aturret.turretrotationPitch;
            reloadprogress[i] = aturret.reloadTimer;
            reloadstates[i] = aturret.readyload;
            cyclestates[i] = aturret.cycle_timer;
        }
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        targetID = buf.readInt();
        turretnum = buf.readInt();
        
        yaws = new float[turretnum];
        pitchs = new float[turretnum];
        reloadprogress = new int[turretnum];
        reloadstates = new boolean[turretnum];
        cyclestates = new int[turretnum];
        for(int i = 0;i < turretnum;i++){
            yaws[i] = buf.readFloat();
            pitchs[i] = buf.readFloat();
            reloadprogress[i] = buf.readInt();
            reloadstates[i] = buf.readBoolean();
            cyclestates[i] = buf.readInt();
        }
        
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(targetID);
        buf.writeInt(turretnum);
        for(int i = 0;i < turretnum;i++){
            buf.writeFloat(yaws[i]);
            buf.writeFloat(pitchs[i]);
            buf.writeInt(reloadprogress[i]);
            buf.writeBoolean(reloadstates[i]);
            buf.writeInt(cyclestates[i]);
        }
    }
}
