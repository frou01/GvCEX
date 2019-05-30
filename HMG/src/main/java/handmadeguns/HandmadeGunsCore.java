package handmadeguns;







import java.io.*;
import java.lang.reflect.Field;
import java.nio.channels.FileChannel;
import java.util.*;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.*;
import cpw.mods.fml.common.discovery.ContainerType;
import cpw.mods.fml.common.discovery.ModCandidate;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import handmadeguns.blocks.HMGBlockMounter;
import handmadeguns.command.CommandReloadparm;
import handmadeguns.entity.*;
import handmadeguns.entity.bullets.*;
import handmadeguns.event.HMGEventZoom;
import handmadeguns.event.HMGLivingUpdateEvent;
import handmadeguns.event.RenderTickSmoothing;
import handmadeguns.items.GunInfo;
import handmadeguns.items.HMGItemBullet;
import handmadeguns.items.guns.HMGItem_Unified_Guns;
import net.minecraft.block.Block;
import net.minecraftforge.client.model.ModelFormatException;
import org.apache.commons.io.FileUtils;


import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import handmadeguns.gui.HMGGuiHandler;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;



@Mod(
		modid	= "HandmadeGuns",
		name	= "HandmadeGuns",
		version	= "1.7.x-srg-1"
)
public class HandmadeGunsCore {
	static Field mcResourcePackRepository;
	static Field repositoryEntries;
	@SidedProxy(clientSide = "handmadeguns.ClientProxyHMG", serverSide = "handmadeguns.CommonSideProxyHMG")
	public static CommonSideProxyHMG proxy;
	public static final String MOD_ID = "HandmadeGuns";
	@Mod.Instance("HandmadeGuns")
	public static HandmadeGunsCore INSTANCE;
	//public static final KeyBinding Speedreload = new KeyBinding("Key.proceedreload", Keyboard.KEY_R, "GVCGunsPlus");

	public static boolean isDebugMessage = true;
	public static boolean islmmloaded;
	public static boolean isgvcloaded;

	public static boolean cfg_exprotion = true;
	public static boolean cfg_FriendFireLMM;
	public static boolean cfg_RenderGunSizeLMM;
	public static boolean cfg_RenderGunAttachmentLMM;

	public static boolean cfg_ZoomRender;
	public static int cfg_FOV;

	public static boolean cfg_RenderPlayer;

	public static boolean cfg_canEjectCartridge;
	public static int cfg_Cartridgetime;

	public static boolean cfg_muzzleflash;
	public static boolean cfg_forceunifiedguns = true;

	public static int cfg_ADS_Sneaking;
	public static String cfg_Avoid_Hit_Entitys;
	public static double cfg_defgravitycof = 0.1;
	public static boolean cfg_blockdestroy;
	public static boolean cfg_Flash;
	public static double cfg_defaultknockback;
	public static double cfg_defaultknockbacky;


	public static Item hmg_bullet;
	public static Item hmg_bullet_hg;
	public static Item hmg_bullet_shell;
	public static Item hmg_bullet_rr;
	public static Item hmg_bullet_lmg;

	public static Item hmg_battlepack;


	public static Item hmg_reddot;
	public static Item hmg_scope;
	public static Item hmg_bayonet;
	public static Item hmg_Suppressor;
	public static Item hmg_laser;
	public static Item hmg_right;
	public static Item hmg_grip;

	public static Item hmg_handing;
	public static Item hmg_handing2;

	//public static Item[] guns;
	public static List guns = new ArrayList();

	//protected static final File optionsDir = new File(Minecraft.getMinecraft().mcDataDir,"config" + File.separatorChar + "handmadeguns");



	protected static File configFile;

	public static final CreativeTabs tabhmg = new HMGCreativeTab("HMGTab");
	public static Map<String, CreativeTabs> tabshmg = new HashMap<String, CreativeTabs>();
	//TODO:FIELDS
	public static final String assetsfilepath = "mods" + File.separatorChar + "handmadeguns" + File.separatorChar + "assets" + File.separatorChar + "handmadeguns" + File.separatorChar;
	public static ArrayList<Invocable> scripts = new ArrayList<Invocable>();



	public static void Debug(String pText, Object... pData) {
		if (isDebugMessage) {
			System.out.println(String.format("HandmadeGuns-" + pText, pData));
		}
	}

	//@net.minecraftforge.fml.common.Mod.EventHandler
	@EventHandler
	public void preInit(FMLPreInitializationEvent pEvent) {
		configFile = pEvent.getSuggestedConfigurationFile();
		Configuration lconf = new Configuration(configFile);
		lconf.load();
		cfg_FriendFireLMM	= lconf.get("LMM", "cfg_FriendFireLMM", true).getBoolean(true);
		cfg_RenderGunSizeLMM	= lconf.get("LMM", "cfg_RenderGunSizeLMM", false).getBoolean(false);
		cfg_RenderGunAttachmentLMM	= lconf.get("LMM", "cfg_RenderGunAttachmentLMM", false).getBoolean(false);
		cfg_ZoomRender	= lconf.get("Render", "cfg_ZoomRender", true).getBoolean(true);
		cfg_FOV	= lconf.get("Render", "cfg_FOV", 70).getInt(70);
		cfg_RenderPlayer	= lconf.get("Render", "cfg_RenderPlayer", false).getBoolean(false);
		cfg_canEjectCartridge	= lconf.get("Cartridge", "cfg_canEjectCartridge", true).getBoolean(true);
		cfg_Cartridgetime	= lconf.get("Cartridge", "cfg_Cartridgetime", 200).getInt(200);
		cfg_muzzleflash	= lconf.get("Gun", "cfg_MuzzleFlash", true).getBoolean(true);
		cfg_ADS_Sneaking	= lconf.get("Gun", "cfg_ADS_Sneaking",  0).getInt(0);
		cfg_blockdestroy = lconf.get("Gun", "cfg_blockdestroy",  true).getBoolean(true);
		cfg_Avoid_Hit_Entitys = lconf.getString("Gun", "cfg_AvoidHit",  "","");
		cfg_Flash	= lconf.get("Render", "cfg_Flash", true).getBoolean(true);
		cfg_defaultknockback = lconf.get("Gun", "cfg_KnockBack", 0.05).getDouble(0.05);
		cfg_defaultknockbacky = lconf.get("Gun", "cfg_KnockBackY", 0.01).getDouble(0.01);

		lconf.save();

		hmg_bullet	= new HMGItemBullet().setUnlocalizedName("bulletbase_hmg").setTextureName("handmadeguns:base")
		;
		GameRegistry.registerItem(hmg_bullet, "bulletbase_hmg");


		hmg_handing	= new ItemHangingEntityHMG(0).setUnlocalizedName("hmg_handing")
				//.setTextureName("item_frame")
				.setTextureName("handmadeguns:GunRack")
				.setCreativeTab(tabhmg);
		GameRegistry.registerItem(hmg_handing, "hmg_handing");
		hmg_handing2	= new ItemHangingEntityHMG(1).setUnlocalizedName("hmg_handing2")
				.setTextureName("handmadeguns:GunRack2")
				.setCreativeTab(tabhmg);
		GameRegistry.registerItem(hmg_handing2, "hmg_handing2");


		HMGPacketHandler.init();

	    /*
		 * int power
		 * float speed
		 * float bure
		 * double recoil
		 * int proceedreload
		 * float bayonet
		 * float zoom
		 */
	    /*if(pEvent.getSide().isClient()){
	    	//File optionsDir = new File(Minecraft.getMinecraft().mcDataDir,"config" + File.separatorChar + "handmadeguns");
	    	File optionsDir = new File(Minecraft.getMinecraft().mcDataDir,"mods" + File.separatorChar + "handmadeguns");
	    if (!optionsDir.exists()) {
	        optionsDir.mkdirs();
	      }
	    }*/
		// ResourceLocation aa = new ResourceLocation("handmadeguns").getResourceDomain();
		FMLCommonHandler.instance().bus().register(this);
		proxy.setuprender();
		File packdir = new File(proxy.ProxyFile(), "handmadeguns_Packs");
		packdir.mkdirs();
		{
			File[] packlist = packdir.listFiles();
			Arrays.sort(packlist, new Comparator<File>() {
				public int compare(File file1, File file2){
					return file1.getName().compareTo(file2.getName());
				}
			});
			for (File apack : packlist) {
				if (apack.isDirectory()) {
					String assetsdirstring = apack.getName() + File.separatorChar + "assets" + File.separatorChar + "handmadeguns" + File.separatorChar;
					File diremodel = new File(apack, "addmodel");
					File[] filemodel = diremodel.listFiles();
					if(filemodel != null) {
						for (int ii = 0; ii < filemodel.length; ii++) {
							if (filemodel[ii].isFile()) {
								File directory111 = new File(packdir, assetsdirstring +
										"textures" + File.separatorChar + "model" + File.separatorChar + filemodel[ii].getName());
								try {
									FileUtils.copyFile(filemodel[ii], directory111);
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}
					}
					File diretexture = new File(apack, "addtexture");
					File[] filetexture = diretexture.listFiles();
					if(filetexture != null) {
						for (int ii = 0; ii < filetexture.length; ii++) {
							if (filetexture[ii].isFile()) {
								File directory111 = new File(packdir, assetsdirstring +
										"textures" + File.separatorChar + "items" + File.separatorChar + filetexture[ii].getName());
								try {
									FileUtils.copyFile(filetexture[ii], directory111);
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}
					}
					File diresighttexture = new File(apack, "addsighttex");
					File[] filesighttexture = diresighttexture.listFiles();
					if (filesighttexture != null) {
						for (int ii = 0; ii < filesighttexture.length; ii++) {
							if (filesighttexture[ii].isFile()) {
								File directory111 = new File(packdir,assetsdirstring +
										"textures" + File.separatorChar + "misc" + File.separatorChar + filesighttexture[ii].getName());
								try {
									FileUtils.copyFile(filesighttexture[ii], directory111);
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}
					}
					File diresound = new File(apack, "addsounds");
					File[] filesound = diresound.listFiles();
					if (filesound != null) {
						for (int ii = 0; ii < filesound.length; ii++) {
							if (filesound[ii].isFile()) {
								File directory111 = new File(packdir,assetsdirstring +
										"sounds" + File.separatorChar + filesound[ii].getName());
								try {
									FileUtils.copyFile(filesound[ii], directory111);
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}/**/
					}
					HMGAddSounds.load(new File(packdir,assetsdirstring +
							"sounds"),new File(packdir,assetsdirstring));
				}
			}

			for (File file : packdir.listFiles())
			{
				if (file.isDirectory())
				{
					try
					{
						if(pEvent.getSide().isClient()) {
							HashMap<String, Object> map = new HashMap<String, Object>();
							map.put("modid", "HandmadeGuns");
							map.put("name", "HandmadeGuns");
							map.put("version", "1");
							FMLModContainer container = new FMLModContainer("handmadeguns.HandmadeGunsCore", new ModCandidate(file, file, file.isDirectory() ? ContainerType.DIR : ContainerType.JAR), map);
							container.bindMetadata(MetadataCollection.from(null, ""));
							FMLClientHandler.instance().addModAsResource(container);
						}
					} catch (Exception e)
					{
						System.out.println("Failed to load resource " + file.getName());
						e.printStackTrace();
					}
					// Add the directory to the content pack list
					System.out.println("Loaded content pack resource : " + file.getName());
				}
			}
			if(pEvent.getSide().isClient())Minecraft.getMinecraft().refreshResources();
			Arrays.sort(packlist);
			for (File apack : packlist) {
				if (apack.isDirectory()) {
					File direTab = new File(apack, "addTab");
					File[] filetab = direTab.listFiles();
					if (filetab != null) {
						Arrays.sort(filetab, new Comparator<File>() {
							public int compare(File file1, File file2) {
								return file1.getName().compareTo(file2.getName());
							}
						});
						for (int ii = 0; ii < filetab.length; ii++) {
							if (filetab[ii].isFile()) {
								HMGAddTabs.load(pEvent.getSide().isClient(), filetab[ii]);
							}
						}
					}
					File direattach = new File(apack, "attachment");
					File[] fileattach = direattach.listFiles();
					if (fileattach != null) {
						Arrays.sort(fileattach, new Comparator<File>() {
							public int compare(File file1, File file2) {
								return file1.getName().compareTo(file2.getName());
							}
						});
						for (int ii = 0; ii < fileattach.length; ii++) {
							if (fileattach[ii].isFile()) {
								HMGAddAttachment.load(pEvent.getModConfigurationDirectory(), pEvent.getSide().isClient(), fileattach[ii]);
							}
						}
					}
					File diremag = new File(apack, "magazines");
					File[] filelistmag = diremag.listFiles();
					if (filelistmag != null) {
						Arrays.sort(filelistmag, new Comparator<File>() {
							public int compare(File file1, File file2) {
								return file1.getName().compareTo(file2.getName());
							}
						});
						for (int ii = 0; ii < filelistmag.length; ii++) {
							if (filelistmag[ii].isFile()) {
								try {
									HMGAddmagazine.load(pEvent.getSide().isClient(), filelistmag[ii]);
								} catch (ModelFormatException e) {
									e.printStackTrace();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}
					}

					File direbullet = new File(apack, "bullets");
					File[] filebullet = direbullet.listFiles();
					if (filebullet != null) {
						for (int ii = 0; ii < filebullet.length; ii++) {
							if (filebullet[ii].isFile()) {
								try {
									HMGAddBullets.load(pEvent.getSide().isClient(), filebullet[ii]);
								} catch (ModelFormatException e) {
									e.printStackTrace();
								}
							}
						}
					}
					File direjs = new File(apack, "addscripts");
					File[] filejs = direjs.listFiles();
					if (filejs != null) {
						for (int ii = 0; ii < filejs.length; ii++) {
							if (filejs[ii].isFile()) {
								File directory111 = new File(proxy.ProxyFile(), "mods" + File.separatorChar + "handmadeguns"
										+ File.separatorChar + "assets" + File.separatorChar + "handmadeguns" + File.separatorChar +
										"scripts" + File.separatorChar + filejs[ii].getName());
//							File in = new File("C:\\temp\\in.txt");
//							File out = new File("C:\\temp\\out.txt");
								try {
									FileUtils.copyFile(filejs[ii], directory111);
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}
					}
					File diregun = new File(apack, "guns");
					File[] filegun = diregun.listFiles();
					Arrays.sort(filegun, new Comparator<File>() {
						public int compare(File file1, File file2) {
							return file1.getName().compareTo(file2.getName());
						}
					});
					for (int ii = 0; ii < filegun.length; ii++) {
						if (filegun[ii].isFile()) {
							try {
								HMGAddGunsNew.load(direjs, pEvent.getSide().isClient(), filegun[ii]);
							} catch (ModelFormatException e) {
								e.printStackTrace();
							}
						}
					}
				}

			}
		}
		//TODO:INJECT_FUNCTION
		File[] packlist = packdir.listFiles();
		Arrays.sort(packlist, new Comparator<File>() {
			public int compare(File file1, File file2){
				return file1.getName().compareTo(file2.getName());
			}
		});
		for (File aPacklist : packlist) {
			if (aPacklist.isDirectory()) {
				File[] recipelist = getFileList(aPacklist, "addscripts");
				if (recipelist != null && recipelist.length > 0) {
					for (File aRecipelist : recipelist) {
						System.out.println("debug" + aRecipelist);
						try {
							ScriptEngine script = (new ScriptEngineManager(null)).getEngineByName("js");
							try {
								if (script.toString().contains("Nashorn")) {
									script.eval("load(\"nashorn:mozilla_compat.js\");");
								}
								script.eval(new FileReader(aRecipelist));
								try {
									((Invocable) script).invokeFunction("preInit", pEvent);
								} catch (ScriptException e) {
									e.printStackTrace();
								} catch (NoSuchMethodException e) {
									e.printStackTrace();
								}
								scripts.add((Invocable) script);
							} catch (ScriptException e) {
								throw new RuntimeException("Script exec error", e);
							}
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
					}
				}
				//fileSetup(filelist1[pack], "addbattlepack", "battlepacks");
			}
		}
		//END
	}

	public static void copyFile(File in, File out) throws IOException {
		@SuppressWarnings("resource")
		FileChannel inChannel = new FileInputStream(in).getChannel();
		@SuppressWarnings("resource")
		FileChannel outChannel = new FileOutputStream(out).getChannel();
		try {
			inChannel.transferTo(0, inChannel.size(),outChannel);
		}
		catch (IOException e) {
			throw e;
		}
		finally {
			if (inChannel != null) inChannel.close();
			if (outChannel != null) outChannel.close();
		}
	}


	@EventHandler
	public void init(FMLInitializationEvent pEvent) {
		int D = Short.MAX_VALUE;
		/*
		GameRegistry.addRecipe(new ItemStack(hmg_bullet_hg, 2),
				"i ",
				" g",
				'i', Items.iron_ingot,
				'g', Items.gunpowder
			);
		GameRegistry.addRecipe(new ItemStack(hmg_bullet, 2),
				"ii",
				"ig",
				'i', Items.iron_ingot,
				'g', Items.gunpowder
			);
		GameRegistry.addRecipe(new ItemStack(hmg_bullet_shell, 2),
				"ip",
				"pg",
				'i', Items.iron_ingot,
				'p', Items.paper,
				'g', Items.gunpowder
			);
		GameRegistry.addRecipe(new ItemStack(hmg_bullet_lmg, 2),
				"iig",
				"iig",
				'i', Items.iron_ingot,
				'g', Items.gunpowder
			);
		GameRegistry.addRecipe(new ItemStack(hmg_bullet_rr, 2),
				"gig",
				"gig",
				'i', Items.iron_ingot,
				'g', Items.gunpowder
			);
		*/
		GameRegistry.addRecipe(new ItemStack(hmg_handing, 1),
				"  s",
				" ss",
				"bbb",
				's', Items.stick,
				'b', new ItemStack(Blocks.wooden_slab, 1, D)
		);
		GameRegistry.addRecipe(new ItemStack(hmg_handing2, 1),
				" b",
				"sb",
				" b",
				's', Items.stick,
				'b', new ItemStack(Blocks.wooden_slab, 1, D)
		);

		EntityRegistry.instance().registerModEntity(EntityItemFrameHMG.class, "ItemFrameHMG", 200, this, 128, 5, true);
		EntityRegistry.instance().registerModEntity(HMGEntityItemMount.class, "HMGEntityItemMount", 201, this, 128, 5, true);
		EntityRegistry.instance().registerModEntity(HMGEntityItemMount2.class, "HMGEntityItemMount2", 202, this, 128, 5, true);

		//EntityRegistry.instance()ry.registerModEntity(HGEntityBullet.class, "BulletHG", 150, this, 128, 5, true);
		EntityRegistry.instance().registerModEntity(HMGEntityBullet.class, "Bullet_HMG", 260, this, 60, 5, false);
		EntityRegistry.instance().registerModEntity(HMGEntityBulletRocket.class, "BulletRPG_HMG", 261, this, 60, 5, false);
		EntityRegistry.instance().registerModEntity(HMGEntityBulletExprode.class, "BulletGrenade_HMG", 262, this, 60, 5, false);
		EntityRegistry.instance().registerModEntity(HMGEntityBulletTorp.class, "BulletTorp_HMG", 262, this, 60, 5, false);
		EntityRegistry.instance().registerModEntity(HMGEntityLight.class, "Right_HMG", 263, this, 128, 5, true);
		EntityRegistry.instance().registerModEntity(HMGEntityLight2.class, "Right2_HMG", 264, this, 128, 5, false);
		EntityRegistry.instance().registerModEntity(HMGEntityLaser.class, "Laser_HMG", 265, this, 128, 5, false);

		EntityRegistry.instance().registerModEntity(HMGEntityBullet_AP.class, "Bullet_AP_HMG", 270, this, 60, 5, false);
		EntityRegistry.instance().registerModEntity(HMGEntityBullet_Frag.class, "Bullet_Frag_HMG", 271, this, 60, 5, false);
		EntityRegistry.instance().registerModEntity(HMGEntityBullet_TE.class, "Bullet_TE_HMG", 272, this, 60, 5, false);
		EntityRegistry.instance().registerModEntity(HMGEntityBullet_AT.class, "Bullet_AT_HMG", 273, this, 60, 5, false);
		EntityRegistry.instance().registerModEntity(HMGEntityBullet_HE.class, "Bullet_HE_HMG", 274, this, 60, 5, false);
		EntityRegistry.instance().registerModEntity(HMGEntityBullet_Flame.class, "Bullet_Flame_HMG", 275, this, 60, 5, false);

		EntityRegistry.instance().registerModEntity(HMGEntityBulletCartridge.class, "BulletCartridge_HMG", 255, this, 128, 5, true);
		EntityRegistry.instance().registerModEntity(PlacedGunEntity.class, "PlacedGun", 253, this, 128, 1, true);
		Block mounter = new HMGBlockMounter(1).setBlockName("ItemHolder").setBlockTextureName("handmadeguns:camp");
		GameRegistry.registerBlock(mounter, "ItemHolder");
		GameRegistry.addRecipe(new ItemStack(mounter, 1),
				"bbb",
				"bbb",
				"   ",
				'b', new ItemStack(Blocks.wooden_slab, 1, D)
		);
//		EntityRegistry.instance().registerModEntity(HMGEntityParticles.class, "HMGEntityParticles", 267, this, 128, 5, true);

		//	EntityRegistry.registerModEntity(HMGEntityTurret.class, "HMGEntityTurret", 268, this, 128, 5, true);
		//EntityRegistry.registerModEntity(HMGEntityHand.class, "HMGEntityHand", 268, this, 128, 5, true);

		//MinecraftForge.EVENT_BUS.register(new GGEventZoom());
		MinecraftForge.EVENT_BUS.register(new HMGEventZoom());

		HMGLivingUpdateEvent hmgLivingUpdateEvent = new HMGLivingUpdateEvent();
		FMLCommonHandler.instance().bus().register(hmgLivingUpdateEvent);
		MinecraftForge.EVENT_BUS.register(hmgLivingUpdateEvent);

		RenderTickSmoothing renderTickSmoothing = new RenderTickSmoothing();
		FMLCommonHandler.instance().bus().register(renderTickSmoothing);
		MinecraftForge.EVENT_BUS.register(renderTickSmoothing);

		NetworkRegistry.INSTANCE.registerGuiHandler(this, new HMGGuiHandler());

		FMLCommonHandler.instance().bus().register(this);
		//if(pEvent.getSide().isClient())
		{
			MinecraftForge.EVENT_BUS.register(new LivingEventHooks());
		}
		//TODO:INJECT_FUNCTION
		//AddRecipe
		String filepath = "mods/handmadeguns/addgun";
		File packdir = new File(proxy.ProxyFile(), "handmadeguns_Packs");
		File[] packlist = packdir.listFiles();
		Arrays.sort(packlist, new Comparator<File>() {
			public int compare(File file1, File file2){
				return file1.getName().compareTo(file2.getName());
			}
		});
		for (File aPacklist : packlist) {
			if (aPacklist.isDirectory()) {
				File[] recipelist = getFileList(aPacklist, "addpackrecipe");
				if(recipelist != null && recipelist.length>0){
					Arrays.sort(recipelist, new Comparator<File>(){
						public int compare(File file1, File file2){
							return file1.getName().compareTo(file2.getName());
						}
					});
					for(int count = 0 ; count < recipelist.length ; count++){
						HMGAddGunsNew.addRecipe(recipelist[count]);
					}
				}

			}
		}

		for(int count = 0 ; count < scripts.size() ; count++){
			System.out.println("Output : "+(Invocable)scripts.get(count));
			try{
				((Invocable)scripts.get(count)).invokeFunction("init", pEvent);
			}catch(ScriptException e){
				e.printStackTrace();
			}catch(NoSuchMethodException e){
				e.printStackTrace();
			}
		}
		//TODO:END_INJECT_FUNCTION--------------------------------------------------------------------------------------------------------------------------------


		proxy.reisterRenderers();
		proxy.registerTileEntity();
		proxy.InitRendering();
		proxy.leftclick();
		proxy.jumped();
		proxy.Fclick();
		proxy.ADSclick();
		proxy.getEntityPlayerInstance();
	}


	@SubscribeEvent
	public void KeyHandlingEvent(KeyInputEvent event)
	{
		Minecraft minecraft = Minecraft.getMinecraft();
		Entity entity = Minecraft.getMinecraft().renderViewEntity;
		//	EntityPlayer entityplayer = (EntityPlayer)entity;
		//EntityPlayer entityplayer = Minecraft.getMinecraft().thePlayer;
		if(entity != null && entity instanceof EntityPlayer){
			EntityPlayer entityplayer = (EntityPlayer)entity;
			ItemStack itemstack = ((EntityPlayer)(entityplayer)).getCurrentEquippedItem();

//			if (proxy.Reloadkeyispressed()) {
//				if(itemstack != null && itemstack.getItem() instanceof HMGXItemGunBase){
//					HMGPacketHandler.INSTANCE.sendToServer(new HMGMessageKeyPressed(1));
//				}
//			}
//			if (proxy.Rightkeyispressed()) {
//				if(itemstack != null && itemstack.getItem() instanceof HMGXItemGunBase){
//					HMGPacketHandler.INSTANCE.sendToServer(new HMGMessageKeyPressed(2));
//				}
//			}
//			if (proxy.Attachmentkeyispressed()) {
//				if(itemstack != null && itemstack.getItem() instanceof HMGXItemGunBase)
//				{
//					HMGPacketHandler.INSTANCE.sendToServer(new HMGMessageKeyPressed(5));
//				}
//			}
		}
		//if (ClientProxyHMG.Jump.isPressed()) {
		//	if(itemstack != null && itemstack.getItem() instanceof HMGItemGunBase){
		//		HMGPacketHandler.INSTANCE.sendToServer(new HMGMessageKeyPressed(3));
		//	}
		//}


	}

	public static boolean Key_ADS(Entity entityplayer){
		if(entityplayer instanceof EntityPlayer){
			if(((EntityPlayer) entityplayer).getHeldItem() != null
					&& ((EntityPlayer) entityplayer).getHeldItem().getItem() instanceof HMGItem_Unified_Guns
					&& ((HMGItem_Unified_Guns) ((EntityPlayer) entityplayer).getHeldItem().getItem()).gunInfo.needcock
					&& ((EntityPlayer) entityplayer).getHeldItem().getTagCompound() != null
					&& !((EntityPlayer) entityplayer).getHeldItem().getTagCompound().getBoolean("Cocking")){
				return false;
			}
			if(cfg_ADS_Sneaking == 1){
				return proxy.ADSclick();
			}else if(cfg_ADS_Sneaking == 2) {
				return entityplayer.isSneaking();
			}else{
				return proxy.ADSclick() || entityplayer.isSneaking();
			}
		}else if(entityplayer instanceof PlacedGunEntity){
			return true;
		}else{
			return entityplayer.isSneaking();
		}
	}


	public class LivingEventHooks
	{
		public LivingEventHooks() {}

		@SideOnly(Side.CLIENT)
		@SubscribeEvent
		public void renderLiving(RenderPlayerEvent.Pre event)
		{
			ItemStack itemstack = event.entityPlayer.getCurrentEquippedItem();
			RenderPlayer renderplayer = event.renderer;
			if(itemstack != null && (itemstack.getItem() instanceof HMGItem_Unified_Guns) && itemstack.hasTagCompound()){
				if(itemstack.getTagCompound().getBoolean("set_up")) {
					renderplayer.modelArmor.aimedBow = renderplayer.modelArmorChestplate.aimedBow = renderplayer.modelBipedMain.aimedBow = true;
				}else {
					renderplayer.modelBipedMain.heldItemRight = renderplayer.modelArmor.heldItemRight = 4;
				}
			}
			if(event.entityPlayer.ridingEntity instanceof PlacedGunEntity){
				renderplayer.modelArmor.aimedBow = renderplayer.modelArmorChestplate.aimedBow = renderplayer.modelBipedMain.aimedBow = true;
			}
		}
		int knife = 0;
		@SubscribeEvent
		public void entitylving(TickEvent e){
			EntityPlayer entityplayer = proxy.getEntityPlayerInstance();

			knife = 0;
		}

	}
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		System.out.println("debug");
		islmmloaded = Loader.isModLoaded("lmmx");
		isgvcloaded = Loader.isModLoaded("GVCMob");

		//TODO:INJCT
		//AddScript
		for(int count = 0 ; count < scripts.size() ; count++){
			System.out.println("Output : "+(Invocable)scripts.get(count));
			try{
				((Invocable)scripts.get(count)).invokeFunction("postInit", event);
			}catch(ScriptException e){
				e.printStackTrace();
			}catch(NoSuchMethodException e){
				e.printStackTrace();
			}
		}
		//END
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event){
		event.registerServerCommand(new CommandReloadparm());
	}

	//TODO:INJ
	public static String pathConverter(String path){
		return path.replaceAll("( // | \\ )", String.valueOf(File.separatorChar));
	}

	public static void fileSetup(File file, String path0, String path1){
		File folder0 = new File(file, path0);
		File[] file0 = folder0.listFiles();
		if(file0 != null){
			for (int var1 = 0 ; var1 < file0.length ; var1++){
				if (file0[var1].isFile()){
					File copypath = new File(proxy.ProxyFile(), pathConverter(assetsfilepath + path1 + File.separatorChar + file0[var1].getName()));
					try {
						FileUtils.copyFile(file0[var1], copypath);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public static File[] getFileList(File file, String path0){
		File folder0 = new File(file, path0);
		File[] file0 = folder0.listFiles();
		return file0;
	}
	//END

}



