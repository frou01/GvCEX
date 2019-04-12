package hmggvcmob.network;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import hmggvcmob.entity.IMultiTurretVehicle;
import hmggvcmob.entity.ImultiRideableVehicle;
import hmggvcmob.entity.TurretObj;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import static handmadeguns.HandmadeGunsCore.proxy;

public class GVCMHandleVehicleTurretSync implements IMessageHandler<GVCPakcetVehicleTurretSync, IMessage> {

    @Override
    public IMessage onMessage(GVCPakcetVehicleTurretSync message, MessageContext ctx) {
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
                    aturretobj.reloadTimer = message.reloadstates[i];
                    aturretobj.cycle_timer = message.cyclestates[i];
                }
            }
        }
        return null;
    }
}
