package handmadevehicle.events;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import handmadevehicle.entity.parts.IVehicle;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

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
}
