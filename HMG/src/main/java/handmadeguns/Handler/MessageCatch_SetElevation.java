package handmadeguns.Handler;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import handmadeguns.entity.PlacedGunEntity;
import handmadeguns.items.guns.HMGItem_Unified_Guns;
import handmadeguns.network.PacketSetElevation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import static handmadeguns.HandmadeGunsCore.HMG_proxy;

public class MessageCatch_SetElevation implements IMessageHandler<PacketSetElevation, IMessage> {
	@Override
	public IMessage onMessage(PacketSetElevation message, MessageContext ctx) {
		//モード設定やアタッチメントGUI同様で行けるだろう
		World world;
//        System.out.println("debug");
		if(ctx.side.isServer()) {
			world = ctx.getServerHandler().playerEntity.worldObj;
		}else{
			world = HMG_proxy.getCilentWorld();
		}
		try {
			if(world != null){
				Entity shooter = world.getEntityByID(message.targetEntityID);
				if(shooter instanceof EntityLivingBase && ((EntityLivingBase) shooter).getHeldItem() != null && ((EntityLivingBase) shooter).getHeldItem().getItem() instanceof HMGItem_Unified_Guns) {
//					System.out.println("debug" + message.elevationID);
					((EntityLivingBase) shooter).getHeldItem().getTagCompound().setInteger("currentElevation",message.elevationID);
				}else
				if(shooter != null && shooter.ridingEntity instanceof PlacedGunEntity){
					HMGItem_Unified_Guns gunitem = ((PlacedGunEntity) shooter.ridingEntity).gunItem;
					ItemStack itemStack = ((PlacedGunEntity) shooter.ridingEntity).gunStack;
					if(gunitem != null && itemStack != null){
						itemStack.getTagCompound().setInteger("currentElevation",message.elevationID);
					}
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
