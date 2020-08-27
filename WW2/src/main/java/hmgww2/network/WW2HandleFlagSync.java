package hmgww2.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import hmgww2.blocks.tile.TileEntityBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import static handmadeguns.HandmadeGunsCore.HMG_proxy;

public class WW2HandleFlagSync implements IMessageHandler<WW2PacketFlagSync, IMessage> {
	@Override
	public IMessage onMessage(WW2PacketFlagSync message, MessageContext messageContext) {
		World world;
//        System.out.println("debug");
		if (messageContext.side.isServer()) {
			world = messageContext.getServerHandler().playerEntity.worldObj;
		} else {
			world = HMG_proxy.getCilentWorld();
		}
		if (world != null) {
			TileEntity tile = world.getTileEntity(message.x, message.y, message.z);
			if (tile instanceof TileEntityBase) {
				((TileEntityBase) tile).setInvasionSet(message.inv);
				((TileEntityBase) tile).nation = message.nation;
			}
		}
		return null;
	}
}
