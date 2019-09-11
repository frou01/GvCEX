package handmadevehicle;


import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.*;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.discovery.ContainerType;
import cpw.mods.fml.common.discovery.ModCandidate;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import handmadeguns.command.HMG_CommandReloadparm;
import handmadevehicle.Items.ItemWrench;
import handmadevehicle.command.HMV_CommandReloadparm;
import handmadevehicle.entity.EntityVehicle;
import handmadevehicle.events.HMVRenderSomeEvent;
import handmadevehicle.events.HMV_Event;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.client.model.ModelFormatException;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

import static handmadevehicle.CMProxy.hmv_commandReloadparm;


@Mod(
		modid	= "HMVehicle",
		name	= "HMVehicle",
		version	= "1.7.x-srg-1",
		dependencies="required-after:HandmadeGuns"
)
public class HMVehicle {
	@SidedProxy(clientSide = "handmadevehicle.CLProxy", serverSide = "handmadevehicle.CMProxy")
	public static CMProxy HMV_Proxy;
	@Mod.Instance("HMVehicle")
	public static HMVehicle INSTANCE;

	public static ItemWrench itemWrench;
	public static boolean isDebugMessage = true;
	public static final CreativeTabs tabHMV = new HMVDefaultTab("HMV");
	public static double cfgVehicleWheel_UpRange = 1;
	public static double cfgVehicleWheel_DownRange = 2;
	
	
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
		File packdir = new File(HMV_Proxy.ProxyFile(), "handmadeVehicles_Packs");
		packdir.mkdirs();
		{
			
			File[] packlist = packdir.listFiles();
			Arrays.sort(packlist, new Comparator<File>() {
				public int compare(File file1, File file2) {
					return file1.getName().compareTo(file2.getName());
				}
			});
			for (File apack : packlist) {
				String assetsdirstring = apack.getName() + File.separatorChar + "assets" + File.separatorChar + "handmadevehicle" + File.separatorChar;
				
				HMVAddSounds.load(new File(packdir, assetsdirstring +
						                                    "sounds"), new File(packdir, assetsdirstring));
				if (apack.isDirectory()) {
					if (pEvent.getSide().isClient()) {
						try {
							HashMap<String, Object> map = new HashMap<String, Object>();
							map.put("modid", "HMVehicle");
							map.put("name", "HMVehicle");
							map.put("version", "1");
							FMLModContainer container = new FMLModContainer("handmadevehicle.HMVehicle", new ModCandidate(apack, apack, apack.isDirectory() ? ContainerType.DIR : ContainerType.JAR), map);
							container.bindMetadata(MetadataCollection.from(null, ""));
							FMLClientHandler.instance().addModAsResource(container);
						} catch (Exception e) {
							System.out.println("Failed to load resource " + apack.getName());
							e.printStackTrace();
						}
						// Add the directory to the content pack list
						System.out.println("Loaded content pack resource : " + apack.getName());
					}
				}
			}
			if(pEvent.getSide().isClient()) Minecraft.getMinecraft().refreshResources();
		}
	}

	@EventHandler
	public void init(FMLInitializationEvent pEvent) {
		int id = 0;
		EntityRegistry.registerModEntity(EntityVehicle.class , "HMVVehicle" , id++ , this , 250, 1, true);
		itemWrench = new ItemWrench();
		GameRegistry.registerItem(itemWrench,"Wrench");
		itemWrench.setUnlocalizedName("Wrench");
		itemWrench.setTextureName("wrench");
		if(pEvent.getSide().isClient()) {
			HMVRenderSomeEvent event = new HMVRenderSomeEvent();
			MinecraftForge.EVENT_BUS.register(event);
			FMLCommonHandler.instance().bus().register(event);
		}
		
		File packdir = new File(HMV_Proxy.ProxyFile(), "handmadeVehicles_Packs");
		packdir.mkdirs();
		{
			
			File[] packlist = packdir.listFiles();
			Arrays.sort(packlist, new Comparator<File>() {
				public int compare(File file1, File file2){
					return file1.getName().compareTo(file2.getName());
				}
			});
			
			for (File apack : packlist) {
				
				
				File weaponDir = new File(apack, "AddWeapon");
				File[] fileWeapon = weaponDir.listFiles();
				Arrays.sort(fileWeapon, new Comparator<File>() {
					public int compare(File file1, File file2) {
						return file1.getName().compareTo(file2.getName());
					}
				});
				for (int num = 0; num < fileWeapon.length; num++) {
					if (fileWeapon[num].isFile()) {
						try {
							AddWeapon.load(pEvent.getSide().isClient(), fileWeapon[num]);
						} catch (ModelFormatException e) {
							e.printStackTrace();
						}
					}
				}
				
				File vehicleDir = new File(apack, "AddVehicle");
				File[] fileVehicle = vehicleDir.listFiles();
				Arrays.sort(fileVehicle, new Comparator<File>() {
					public int compare(File file1, File file2) {
						return file1.getName().compareTo(file2.getName());
					}
				});
				for (int num = 0; num < fileVehicle.length; num++) {
					if (fileVehicle[num].isFile()) {
						try {
							new AddNewVehicle().load(pEvent.getSide().isClient(), fileVehicle[num]);
						} catch (ModelFormatException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	
	@Mod.EventHandler
	public void postInit(FMLServerStartingEvent event) {
		HMV_Event hmv_event = new HMV_Event();
		FMLCommonHandler.instance().bus().register(hmv_event);
		MinecraftForge.EVENT_BUS.register(hmv_event);
	}
	@EventHandler
	public void serverStarting(FMLServerStartingEvent event){
		event.registerServerCommand(hmv_commandReloadparm);
	}
}
