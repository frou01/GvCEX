package hmvehicle;


import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import hmvehicle.events.HMV_Event;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

import java.io.File;


@Mod(
		modid	= "HMVehicle",
		name	= "HMVehicle",
		version	= "1.7.x-srg-1",
		dependencies="required-after:HandmadeGuns"
)
public class HMVehicle {
	@SidedProxy(clientSide = "hmvehicle.CLProxy", serverSide = "hmvehicle.CMProxy")
	public static CMProxy proxy_HMVehicle;
	@Mod.Instance("HMVehicle")
	public static HMVehicle INSTANCE;

	public static boolean isDebugMessage = true;



	public static void Debug(String pText, Object... pData) {
		if (isDebugMessage) {
			System.out.println(String.format("HMVehicle-" + pText, pData));
		}
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent pEvent) {
		File configFile = pEvent.getSuggestedConfigurationFile();
		Configuration lconf = new Configuration(configFile);
		lconf.load();
		lconf.save();
		HMVPacketHandler.init();
	}

	@EventHandler
	public void init(FMLInitializationEvent pEvent) {
	
	}
	
	@Mod.EventHandler
	public void postInit(FMLServerStartingEvent event) {
		HMV_Event hmv_event = new HMV_Event();
		FMLCommonHandler.instance().bus().register(hmv_event);
		MinecraftForge.EVENT_BUS.register(hmv_event);
	}
}
