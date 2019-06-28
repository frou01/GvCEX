package hmvehicle.handles;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import hmvehicle.HMVPacketHandler;
import hmvehicle.entity.parts.HasBaseLogic;
import hmvehicle.entity.parts.ImultiRidable;
import hmvehicle.entity.parts.logics.MultiRiderLogics;
import hmvehicle.packets.HMVPacketDisMountEntity;
import hmvehicle.packets.HMVPacketPickNewEntity;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import static handmadeguns.HandmadeGunsCore.proxy;

public class HMVHandleDisMountEntity implements IMessageHandler<HMVPacketDisMountEntity, IMessage> {
	@Override
	public IMessage onMessage(HMVPacketDisMountEntity message, MessageContext ctx) {
		
		World world;
//        System.out.println("debug");
		if(ctx.side.isServer()) {
			world = ctx.getServerHandler().playerEntity.worldObj;
			Entity picking;
			if((picking= world.getEntityByID(message.pickingEntityID)) != null && picking instanceof HasBaseLogic && ((HasBaseLogic) picking).getBaseLogic() instanceof MultiRiderLogics){
				MultiRiderLogics multiRiderLogics = (MultiRiderLogics) ((HasBaseLogic) picking).getBaseLogic();
				multiRiderLogics.disMountEntity(world.getEntityByID(message.disMountEntityID));
				int[] ids = new int[multiRiderLogics.getRiddenEntityList().length];
				Entity[] entitylist = multiRiderLogics.getRiddenEntityList();
				int cnt = 0;
				for (Entity picked : entitylist) {
					if (picked != null) ids[cnt] = picked.getEntityId();
					else ids[cnt] = -1;
					cnt++;
				}
				HMVPacketHandler.INSTANCE.sendToAll(new HMVPacketPickNewEntity(message.pickingEntityID, ids));
			}
		}else{
		}
		return null;
	}
}
