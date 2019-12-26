package handmadevehicle.network.handles;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import handmadevehicle.entity.parts.HasBaseLogic;
import handmadevehicle.network.packets.HMVPacketSyncInventory;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import static handmadeguns.HandmadeGunsCore.HMG_proxy;

public class HMVHandleSyncInventory implements IMessageHandler<HMVPacketSyncInventory, IMessage> {
	@Override
	public IMessage onMessage(HMVPacketSyncInventory message, MessageContext ctx) {
		World world;
//        System.out.println("debug");
		if(ctx.side.isServer()) {
			world = ctx.getServerHandler().playerEntity.worldObj;
		}else{
			world = HMG_proxy.getCilentWorld();
		}
		if(world != null){
			Entity target = world.getEntityByID(message.targetID);
			if(target instanceof HasBaseLogic) {
				((HasBaseLogic) target).getBaseLogic().readFromTag(message.data);
			}
		}
		return null;
	}
}
