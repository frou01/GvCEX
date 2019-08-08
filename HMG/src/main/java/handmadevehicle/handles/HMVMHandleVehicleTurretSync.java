package handmadevehicle.handles;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import handmadevehicle.packets.HMVPakcetVehicleTurretSync;
import handmadevehicle.entity.parts.IMultiTurretVehicle;
import handmadevehicle.entity.parts.turrets.TurretObj;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import static handmadeguns.HandmadeGunsCore.proxy;

public class HMVMHandleVehicleTurretSync implements IMessageHandler<HMVPakcetVehicleTurretSync, IMessage> {

    @Override
    public IMessage onMessage(HMVPakcetVehicleTurretSync message, MessageContext ctx) {
        World world;
//        System.out.println("debug");
        if(ctx.side.isServer()) {
            world = ctx.getServerHandler().playerEntity.worldObj;
        }else{
            world = proxy.getCilentWorld();
        }
        if(world != null) {
            Entity entity = world.getEntityByID(message.targetID);
            if(entity instanceof IMultiTurretVehicle){
                TurretObj[] turretObjs = ((IMultiTurretVehicle) entity).getTurrets();
                for(int i = 0;i <turretObjs.length;i++){
                    TurretObj aturretobj = turretObjs[i];
                    aturretobj.turretrotationYaw = message.yaws[i];
                    aturretobj.turretrotationPitch= message.pitchs[i];
                    aturretobj.reloadTimer = message.reloadprogress[i];
                    aturretobj.readyload = message.reloadstates[i];
                    aturretobj.cycle_timer = message.cyclestates[i];
                }
            }
        }
        return null;
    }
}
