package hmggvcmob.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import hmggvcmob.util.SpotObj;
import net.minecraft.world.World;

import java.util.ArrayList;

import static handmadeguns.HandmadeGunsCore.HMG_proxy;
import static hmggvcmob.event.GVCMXEntityEvent.spots_needSpawn_Client;

public class GVCHandleSpawnSpotCircle implements IMessageHandler<GVCPacketSpawnSpotCircle, IMessage> {
	
	@Override
	public IMessage onMessage(GVCPacketSpawnSpotCircle message, MessageContext ctx) {
		World world;
		if(ctx.side.isServer()) {
			world = ctx.getServerHandler().playerEntity.worldObj;
		}else{
			world = HMG_proxy.getCilentWorld();
		}
		if(message != null && world != null && world.provider != null && message.dimid == world.provider.dimensionId) {
			ArrayList<SpotObj> spotObjArrayList = spots_needSpawn_Client.get(world.provider.dimensionId);
			spotObjArrayList.addAll(message.recievedSpotobj);
		}
		return null;
	}
}
