package handmadeguns.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import handmadeguns.entity.bullets.HMGEntityBulletBase;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingEvent;

public class HMGLivingUpdateEvent {
    @SubscribeEvent
    public void canupdate(EntityEvent.CanUpdate event){
        if(event.entity instanceof HMGEntityBulletBase){
            event.entity.setDead();
        }
    }

}
