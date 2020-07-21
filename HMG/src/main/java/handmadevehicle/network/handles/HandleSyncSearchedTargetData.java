package handmadevehicle.network.handles;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import handmadevehicle.entity.parts.IVehicle;
import handmadevehicle.network.packets.PacketSyncSearchedTargetData;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import static handmadeguns.HandmadeGunsCore.HMG_proxy;

public class HandleSyncSearchedTargetData implements IMessageHandler<PacketSyncSearchedTargetData, IMessage> {
	@Override
	public IMessage onMessage(PacketSyncSearchedTargetData message, MessageContext ctx) {
		World world;
//        System.out.println("debug");
		if(ctx.side.isServer()) {
			world = ctx.getServerHandler().playerEntity.worldObj;
		}else{
			world = HMG_proxy.getCilentWorld();
		}

		Entity target = world.getEntityByID(message.syncTargetID);
		if(target instanceof IVehicle){
			((IVehicle) target).getBaseLogic().detectedList.clear();
			((IVehicle) target).getBaseLogic().detectedList.addAll(message.targets);
		}
		return null;
	}
}
