package hmvehicle.handles;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import hmvehicle.entity.parts.HasBaseLogic;
import hmvehicle.entity.parts.ImultiRidable;
import hmvehicle.entity.parts.SeatInfo;
import hmvehicle.entity.parts.logics.IbaseLogic;
import hmvehicle.entity.parts.logics.MultiRiderLogics;
import hmvehicle.packets.HMVPacketTriggerSeatGun;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import static handmadeguns.HandmadeGunsCore.proxy;

public class HMVHandleTriggerSeatGun implements IMessageHandler<HMVPacketTriggerSeatGun, IMessage> {
	@Override
	public IMessage onMessage(HMVPacketTriggerSeatGun message, MessageContext ctx) {
		World world;
//        System.out.println("debug");
		if(ctx.side.isServer()) {
			world = ctx.getServerHandler().playerEntity.worldObj;
		}else{
			world = proxy.getCilentWorld();
		}
		try {
			Entity targetEntity = world.getEntityByID(message.targetID);
			if (targetEntity instanceof HasBaseLogic) {
				IbaseLogic ibaseLogic = ((HasBaseLogic) targetEntity).getBaseLogic();
				if (ibaseLogic instanceof MultiRiderLogics) {
					SeatInfo[] seats = ((MultiRiderLogics) ibaseLogic).getRiddenSeatList();
					SeatInfo targetSeat = seats[message.targetSeatID];
					if (targetSeat.hasGun) {
						targetSeat.gunTrigger1 = message.trigger1;
						targetSeat.gunTrigger2 = message.trigger2;
					}
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
