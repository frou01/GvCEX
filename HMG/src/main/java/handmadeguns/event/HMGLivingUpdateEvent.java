package handmadeguns.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import handmadeguns.entity.bullets.HMGEntityBulletBase;
import handmadevehicle.entity.EntityVehicle;
import net.minecraftforge.event.entity.EntityEvent;

public class HMGLivingUpdateEvent {
    @SubscribeEvent
    public void canupdate(EntityEvent.CanUpdate event){
        if(event.entity instanceof HMGEntityBulletBase){
            event.entity.setDead();
        }
        if(event.entity instanceof EntityVehicle && ((EntityVehicle) event.entity).despawn){
            event.entity.setDead();
        }
    }

}
