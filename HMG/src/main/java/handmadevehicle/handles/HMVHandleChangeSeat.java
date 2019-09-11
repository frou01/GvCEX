package handmadevehicle.handles;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import handmadevehicle.entity.parts.HasBaseLogic;
import handmadevehicle.entity.parts.logics.BaseLogic;
import handmadevehicle.entity.parts.logics.MultiRiderLogics;
import handmadevehicle.packets.HMVPacketChangeSeat;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import static handmadeguns.HandmadeGunsCore.HMG_proxy;

public class HMVHandleChangeSeat implements IMessageHandler<HMVPacketChangeSeat, IMessage> {
	@Override
	public IMessage onMessage(HMVPacketChangeSeat message, MessageContext ctx) {
		World world;
//        System.out.println("debug");
		if(ctx.side.isServer()) {
			world = ctx.getServerHandler().playerEntity.worldObj;
		}else{
			world = HMG_proxy.getCilentWorld();
		}
		try{
			Entity targetEntity = world.getEntityByID(message.targetID);
			if (targetEntity instanceof HasBaseLogic) {
				BaseLogic ibaseLogic = ((HasBaseLogic) targetEntity).getBaseLogic();
				if (ibaseLogic instanceof MultiRiderLogics) {
					Entity[] entities = ((MultiRiderLogics) ibaseLogic).getRiddenEntityList();
					Entity targetRider = entities[message.currentSeatID];
					entities[message.currentSeatID] = null;
					((MultiRiderLogics) ibaseLogic).pickupEntity(targetRider,message.dir ? message.currentSeatID-1:message.currentSeatID+1);
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
