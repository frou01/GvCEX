package hmvehicle.handles;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import hmvehicle.HMVPacketHandler;
import hmvehicle.entity.EntityChild;
import hmvehicle.packets.HMVPacketSeatData;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import static handmadeguns.HandmadeGunsCore.proxy;

public class HMVHandleSeatData implements IMessageHandler<HMVPacketSeatData, IMessage> {
    @Override
    public IMessage onMessage(HMVPacketSeatData message, MessageContext ctx) {
        World world;
//        System.out.println("debug");
        if(ctx.side.isServer()) {
            world = ctx.getServerHandler().playerEntity.worldObj;
            if(world != null) {
                Entity entity = world.getEntityByID(message.targetID);
//                if(entity instanceof ImultiSeatVehicle){
//                    EntityChild[] children = ((ImultiSeatVehicle) entity).getChildren();
//                    int[] ids = new int[children.length];
//                    float[] sizex = new float[children.length];
//                    float[] sizey = new float[children.length];
//                    int cnt = 0;
//                    for(EntityChild child:children){
//                        if(child != null) {
//                            ids[cnt] = child.getEntityId();
//                            sizex[cnt] = child.width;
//                            sizey[cnt] = child.height;
//                        }
//                        cnt++;
//                    }
//                    HMVPacketHandler.INSTANCE.sendTo(new HMVPacketSeatData(message.targetID,children.length,ids,sizex,sizey),ctx.getServerHandler().playerEntity);
//                }
            }
        }else{
            world = proxy.getCilentWorld();
            if(world != null) {
                Entity entity = world.getEntityByID(message.targetID);
//                if(entity instanceof ImultiSeatVehicle){
//                    for(int cnt = 0;cnt < message.seatnum;cnt++){
//                        if(world.getEntityByID(message.seatEntityIDs[cnt]) == null || world.getEntityByID(message.seatEntityIDs[cnt]).isDead){
//                            EntityChild newChild = new EntityChild(world,message.seatsizex[cnt],message.seatsizey[cnt],true);
//                            newChild.setEntityId(message.seatEntityIDs[cnt]);
//                            world.spawnEntityInWorld(newChild);
//                        }
//                    }
//                }
            }
        }
        return null;
    }
}
