package handmadevehicle.events;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import handmadeguns.network.PacketOpenGui;
import handmadevehicle.entity.parts.IVehicle;
import handmadevehicle.network.HMVPacketHandler;
import handmadevehicle.network.packets.HMVPacketOpenVehicleGui;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import static handmadevehicle.HMVehicle.HMV_Proxy;
import static handmadevehicle.HMVehicle.loadConfig;

public class HMV_Event {
	
	@SubscribeEvent
	public void entitydamaged(LivingHurtEvent event)
	{
		EntityLivingBase entity = event.entityLiving;
		if(entity != null){
			if ((entity.ridingEntity instanceof IVehicle)) {
				if(entity instanceof EntityPlayer) {
					event.setCanceled(true);
				}
				if(event.source.getDamageType().equals("explosion") || event.source.getDamageType().equals("explosion.player")){
					event.setCanceled(true);
				}
				entity.ridingEntity.attackEntityFrom(event.source,event.ammount);
			}
		}
	}
	
	@SubscribeEvent
	public void KeyHandlingEvent(InputEvent.KeyInputEvent event) {
		if (HMV_Proxy.reloadConfigclick()) {
			loadConfig();
		}
		if (HMV_Proxy.openGUIKeyDown() && HMV_Proxy.getEntityPlayerInstance() != null && HMV_Proxy.getEntityPlayerInstance().ridingEntity !=null) {
			HMVPacketHandler.INSTANCE.sendToServer(new HMVPacketOpenVehicleGui(0,HMV_Proxy.getEntityPlayerInstance().getEntityId(),HMV_Proxy.getEntityPlayerInstance().ridingEntity.getEntityId()));
		}
	}
}
