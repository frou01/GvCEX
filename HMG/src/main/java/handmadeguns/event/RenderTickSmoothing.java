package handmadeguns.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import handmadeguns.entity.HMGEntityParticles;
import handmadeguns.client.render.HMGRenderItemGun_U;
import handmadeguns.client.render.HMGRenderItemGun_U_NEW;

public class RenderTickSmoothing {
    public static float smooth = 0;

    public static boolean test_ReCreate = false;
    @SubscribeEvent
    public void renderTick(TickEvent.RenderTickEvent event)
    {
        switch(event.phase)
        {
            case START :
                if(event.renderTickTime<1)
                HMGRenderItemGun_U.smoothing = event.renderTickTime;
                HMGRenderItemGun_U_NEW.smoothing = event.renderTickTime;
                HMGEntityParticles.particaltick = event.renderTickTime;
                smooth = event.renderTickTime;
                break;
            case END :
                if(test_ReCreate) {
                    test_ReCreate = false;

                }
                break;
        }
    }
}
