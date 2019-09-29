package handmadevehicle.network.handles;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import handmadevehicle.entity.parts.IVehicle;
import handmadevehicle.network.packets.HMVPacketPickNewEntity;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import static handmadeguns.HandmadeGunsCore.HMG_proxy;

public class HMVHandlePickNewEntity implements IMessageHandler<HMVPacketPickNewEntity, IMessage> {
	@Override
	public IMessage onMessage(HMVPacketPickNewEntity message, MessageContext ctx) {
		
		World world;
//        System.out.println("debug");
		if(ctx.side.isServer()) {
			world = ctx.getServerHandler().playerEntity.worldObj;
			Entity picking;
			if((picking= world.getEntityByID(message.pickingEntityID)) != null && picking instanceof IVehicle){
				{
					int[] ids = new int[((IVehicle) picking).getRiddenEntityList().length];
					Entity[] entitylist = ((IVehicle) picking).getRiddenEntityList();
					int cnt = 0;
					for (Entity picked : entitylist) {
						if (picked != null) ids[cnt] = picked.getEntityId();
						else ids[cnt] = -1;
						cnt++;
					}
					return new HMVPacketPickNewEntity(message.pickingEntityID, ids);
				}
			}
		}else{
			world = HMG_proxy.getCilentWorld();
			Entity picking;
			Entity picked;
			if((picking= world.getEntityByID(message.pickingEntityID)) != null && picking instanceof IVehicle){
				Entity[] entitylist = ((IVehicle) picking).getRiddenEntityList();
				int cnt = 0;
				for (int a_id : message.pickedEntityIDs) {
					picked = world.getEntityByID(a_id);
					entitylist[cnt] = picked;
					cnt ++;
				}
			}
		}
		return null;
	}
}
