package handmadevehicle.events;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import handmadeguns.network.PacketOpenGui;
import handmadevehicle.entity.EntityDummy_rider;
import handmadevehicle.entity.parts.IVehicle;
import handmadevehicle.network.HMVPacketHandler;
import handmadevehicle.network.packets.HMVPacketOpenVehicleGui;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import static handmadevehicle.HMVehicle.HMV_Proxy;
import static handmadevehicle.HMVehicle.loadConfig;
import static net.minecraft.util.DamageSource.inWall;

public class HMV_Event {
	
	@SubscribeEvent
	public void entitydamaged(LivingHurtEvent event)
	{
		EntityLivingBase entity = event.entityLiving;

		if ((entity != null && entity.ridingEntity instanceof EntityDummy_rider)) {
			float userProtect = ((EntityDummy_rider) entity.ridingEntity).
					linkedBaseLogic.prefab_vehicle.
					prefab_seats[((EntityDummy_rider) entity.ridingEntity).linkedSeatID]
					.userProtect_maxDamageLevel;
			System.out.println("userProtect" + userProtect);
			if (userProtect < 0) {
				if (entity instanceof EntityPlayer) {
					event.ammount = 0;
					event.setCanceled(true);
				} else {
					event.ammount -= entity.getMaxHealth() / 2;
				}
			} else {
				event.ammount -= userProtect;
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
