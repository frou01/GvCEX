package minegear;


import java.io.File;

import net.minecraft.world.gen.structure.MapGenStructureIO;


import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import cpw.mods.fml.common.registry.GameRegistry;


@Mod(
		modid	= "MineGear",
		name	= "MineGear",
		version	= "1.7.x-srg-1"
		)
public class mod_MineGear {
	//public static final KeyBinding Speedreload = new KeyBinding("Key.reload", Keyboard.KEY_R, "GVCGunsPlus");
	
	public static boolean isDebugMessage = true;
	
	public static boolean cfg_setBase;
	public static int cfg_creatBase;

	public static Block mg_clear;
	public static Block mg_base1;
	
	public static Block mg_baseblock_mgt;
	public static Block mg_baseblock_hangar1;
	public static Block mg_baseblock_barracks1;
	public static Block mg_baseblock_base1;


	
	
	protected static File configFile;


	public static void Debug(String pText, Object... pData) {
		if (isDebugMessage) {
			System.out.println(String.format("MineGear-" + pText, pData));
		}
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent pEvent) {
		
		configFile = pEvent.getSuggestedConfigurationFile();
		Configuration lconf = new Configuration(configFile);
		lconf.load();
		cfg_setBase	= lconf.get("Base", "cfg_setBase", true).getBoolean(true);
		cfg_creatBase	= lconf.get("Base", "cfg_creatBase", 48).getInt(48);
		lconf.save();



		MapGenStructureIO.registerStructure(StructureBaseStart.class, "DGStructureStart_2");
		MapGenStructureIO.func_143031_a(ComponentDungeonSmallBase.class, "DGDungeon_2");
		GenerateEventHandler generateEventHandler = new GenerateEventHandler();
		MinecraftForge.EVENT_BUS.register(generateEventHandler);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent pEvent) {

		if(this.cfg_setBase==true){
//			GameRegistry.registerWorldGenerator(new MGGeneratBase(),10);

		}
		FMLCommonHandler.instance().bus().register(this);

	}
	
	@SubscribeEvent
    public void KeyHandlingEvent(KeyInputEvent event) 
	{
		
    }
	
	
	
	}