package hmvehicle.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;

import java.util.ArrayList;

public class HMVPacket_HudEntitytracking implements IMessage {
    public float[][] trackedEntityPos;
    public int TGTEntityID;

    public HMVPacket_HudEntitytracking(){
    }
    public HMVPacket_HudEntitytracking(Entity pilot, ArrayList<Entity> entitys){
        TGTEntityID = pilot.getEntityId();

        int trackedEntitynum;
        trackedEntitynum = entitys.size();

        trackedEntityPos = new float[trackedEntitynum][3];
        int index = 0;
        for(Entity entity:entitys){
            trackedEntityPos[index][0] = (float) entity.posX;
            trackedEntityPos[index][1] = (float) entity.posY;
            trackedEntityPos[index][2] = (float) entity.posZ;
            index++;
        }
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        TGTEntityID = buf.readInt();
        int trackedEntitynum;
        trackedEntitynum = buf.readInt();
        trackedEntityPos = new float[trackedEntitynum][3];
        for(int index = 0;index<trackedEntitynum;index++){
            trackedEntityPos[index][0] = buf.readFloat();
            trackedEntityPos[index][1] = buf.readFloat();
            trackedEntityPos[index][2] = buf.readFloat();
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(TGTEntityID);
        int trackedEntitynum = trackedEntityPos.length;
        buf.writeInt(trackedEntitynum);
        for(int index = 0;index<trackedEntitynum;index++){
            buf.writeFloat(trackedEntityPos[index][0]);
            buf.writeFloat(trackedEntityPos[index][1]);
            buf.writeFloat(trackedEntityPos[index][2]);
        }
    }
}
