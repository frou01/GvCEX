package hmggvcmob.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import handmadevehicle.entity.parts.Modes;
import hmggvcmob.entity.IPlatoonable;
import hmggvcmob.entity.PlatoonInfoData;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import static handmadeguns.HandmadeGunsCore.HMG_proxy;

public class GVCPacket_PlatoonInfoSync implements IMessage , IMessageHandler<GVCPacket_PlatoonInfoSync, IMessage> {
	boolean isLeader;
	boolean isOnPlatoon;
	double[] target;
	int targetEntity;
	Modes mode;
	String platoonName;
	public GVCPacket_PlatoonInfoSync(){

	}
	public GVCPacket_PlatoonInfoSync(PlatoonInfoData platoonInfoData, Entity targetEntity){
		isLeader = platoonInfoData.isLeader;
		isOnPlatoon = platoonInfoData.isOnPlatoon;
		target = platoonInfoData.target;
		mode = platoonInfoData.mode;
		platoonName = platoonInfoData.platoonName;
		this.targetEntity = targetEntity.getEntityId();
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		isLeader = buf.readBoolean();
		isOnPlatoon = buf.readBoolean();
		target = new double[]{buf.readDouble(),buf.readDouble(),buf.readDouble()};
		mode = Modes.values()[buf.readInt()];
		targetEntity = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(isLeader);
		buf.writeBoolean(isOnPlatoon);
		buf.writeDouble(target[0]);
		buf.writeDouble(target[1]);
		buf.writeDouble(target[2]);
		buf.writeInt(mode.ordinal());
		buf.writeInt(targetEntity);

	}

	@Override
	public IMessage onMessage(GVCPacket_PlatoonInfoSync message, MessageContext ctx) {
		World world;
		if(ctx.side.isServer()) {
			world = ctx.getServerHandler().playerEntity.worldObj;
		}else{
			world = HMG_proxy.getCilentWorld();
		}
		Entity targetEntity = world.getEntityByID(message.targetEntity);
		if(targetEntity != null) {
			((IPlatoonable) targetEntity).getPlatoonMemberInfo().isLeader = message.isLeader;
			((IPlatoonable) targetEntity).getPlatoonMemberInfo().isOnPlatoon = message.isOnPlatoon;
			((IPlatoonable) targetEntity).getPlatoonMemberInfo().target = message.target;
			((IPlatoonable) targetEntity).getPlatoonMemberInfo().mode = message.mode;
		}

		return null;
	}
}
