package Sokoniyatugairuzo;

import Sokoniyatugairuzo.event.Targeting;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.common.MinecraftForge;

@Mod(
        modid	= "Sokoniyatugairuzo",
        name	= "Sokoniyatugairuzo",
        version	= "1.7.x"
)
public class Core {
    @SidedProxy(clientSide = "Sokoniyatugairuzo.ClientProxy", serverSide = "Sokoniyatugairuzo.CommonProxy")
    public static CommonProxy proxy;
    public static final String MOD_ID = "Sokoniyatugairuzo";
    @Mod.Instance("Sokoniyatugairuzo")

    public static Core INSTANCE;
//    public static final SimpleNetworkWrapper NETWORK_WRAPPER = NetworkRegistry.INSTANCE.newSimpleChannel("HandmadeGuns");
    @Mod.EventHandler
    public void init(FMLInitializationEvent pEvent){
        MinecraftForge.EVENT_BUS.register(new Targeting());
    }

}
