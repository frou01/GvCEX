package Sokoniyatugairuzo;

import Sokoniyatugairuzo.event.Targeting;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy {
    public void enjectevent(){
        MinecraftForge.EVENT_BUS.register(new Targeting());
    }
}
