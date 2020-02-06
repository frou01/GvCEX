package handmadevehicle.network.handles;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import handmadevehicle.HMVehicle;
import handmadevehicle.network.packets.HMVPacketOpenVehicleGui;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import static handmadeguns.HandmadeGunsCore.HMG_proxy;

public class HandleOpenVehicleGui implements IMessageHandler<HMVPacketOpenVehicleGui, IMessage> {
	@Override
	public IMessage onMessage(HMVPacketOpenVehicleGui message, MessageContext ctx) {
		World world;
//        System.out.println("debug");
		if(ctx.side.isServer()) {
			world = ctx.getServerHandler().playerEntity.worldObj;
		}else{
			world = HMG_proxy.getCilentWorld();
		}
		try {
			if(world != null){
				Entity opener = world.getEntityByID(message.playerID);
				if(opener instanceof EntityPlayer) {
					((EntityPlayer) opener).openGui(HMVehicle.INSTANCE, message.ID, opener.worldObj, (int)opener.posX, (int)opener.posY, (int)opener.posZ);
				}
			}
//        bullet = message.bullet.setdata(bullet);
//        System.out.println("bullet "+ bullet);
		}catch (ClassCastException e) {
			e.printStackTrace();
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
