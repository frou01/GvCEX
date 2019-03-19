package hmggvcutil;


import cpw.mods.fml.common.registry.VillagerRegistry;
import hmggvcutil.Item.*;
import hmggvcutil.entity.*;

import java.io.File;


import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;


@Mod(
		modid	= "GVCGuns",
		name	= "GVCGuns",
		version	= "1.7.x-srg-1",
		dependencies="required-after:HandmadeGuns"
)
public class GVCUtils {
	public static boolean cfg_SpwanMob = true;


	public static Item fn_boxegg;
	public static Item fn_targetegg;
	public static Item fn_sentryegg;
	public static Item fn_sentryaagegg;
	public static int GVCVillagerProfession = 5;

	public static GVCVillagerTrade villager;

	//private static final ToolMaterial IRON = null;

	//@SidedProxy(clientSide = "mmm.FN5728Guns.ProxyClient", serverSide = "mmm.lib.ProxyCommon")
	//public static ProxyCommon proxy;

	//public static boolean isArmorPiercing = true;
	//public static boolean UnlimitedInfinity = false;
	@SidedProxy(clientSide = "hmggvcutil.client.ClientProxyGVC", serverSide = "hmggvcutil.CommonSideProxyGVC")
	public static CommonSideProxyGVC proxy;
	@Mod.Instance("GVCGuns")
	public static GVCUtils INSTANCE;

	//GVCGunsReversion
	//public static final KeyBinding Speedreload = new KeyBinding("Key.reload", Keyboard.KEY_R, "GVCUtils");
	static float modelscala = 0.5F;

	public static boolean isDebugMessage = true;

	public static boolean cfg_EasyRecipe;


	public static Item fn_magazine;
	public static Item fn_magazine_545;
	public static Item fn_magazine_545_long;
	public static Item fn_magazine_556;
	public static Item fn_magazine_762;
	public static Item fn_magazine_762_clip;
	public static Item fn_magazine_1270;
	public static Item fn_magazinehg;
	public static Item fn_magazinemg;
	public static Item fn_shell;
	public static Item fn_rpg;
	public static Item fn_missile;


	public static Item fn_box;
	public static Item fn_pra;

	public static Item fn_cm;
	public static Item fn_health;

	public static ArmorMaterial praarmor;
	public static Item fn_prahelmet;
	public static Item fn_prachestp;
	public static Item fn_praleggings;
	public static Item fn_praboots;


	public static Item fn_grenade;
	public static Item fn_hundframe;


	public static Item fn_m320;
	public static Item fn_c4;
	public static Item fn_c4cn;

	//atta
	public static Item fn_reddot;
	public static Item fn_scope;
	public static Item fn_bayonet;
	public static Item fn_Suppressor;
	public static Item fn_laser;
	public static Item fn_grip;



	public static Item fn_ak74;
	public static Item fn_ak74sp;
	public static Item fn_rpk74;
	public static Item fn_aks74u;

	public static Item fn_m10;

	public static Item fn_m1911;
	public static Item fn_m870;
	public static Item fn_svd;
	public static Item fn_pkm;



	public static Item fn_m16a4;
	public static Item fn_m249;
	public static Item fn_m4a1;
	public static Item fn_m9;
	public static Item fn_g17;
	public static Item fn_g18;
	public static Item fn_m110;
	public static Item fn_m240b;
	public static Item fn_r700;
	public static Item fn_m82a3;

	public static Item fn_g36;
	public static Item fn_g36c;
	public static Item fn_mg36;


	public static Item fn_rpg7;


	public static Item fn_m134;
	public static Item fn_m2f;
	public static Item fn_smaw;


	public static Block fn_camp;

	public static Block fn_gunbox;
	public static Block fn_ied;
	public static Block fn_ied_AT;
	public static Block fn_cont;


	public static Item fn_target;

	public static int bullet = 170;
	public static Item fn_bullet;

	public static int entitygrenade = 171;
	public static Item fn_entitygrenade;

	public static int bulletg = 172;

	public static int grenadebullet = 173;

	public static int rpg = 174;

	public static int laser = 175;

	public static int gustorch = 176;

	public static int paras = 177;

	public static final int GUI_ID = 1;
	public static final int GUI_ID2 = 2;

	public static int en_reddotID;
	public static int en_laserID;
	public static int en_scopeID;


	protected static File configFile;


	public static final CreativeTabs tabgvc = new GVCCreativeTab("GVCTab");



	public static void Debug(String pText, Object... pData) {
		if (isDebugMessage) {
			System.out.println(String.format("GVCGuns-" + pText, pData));
		}
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent pEvent) {
		configFile = pEvent.getSuggestedConfigurationFile();
		Configuration lconf = new Configuration(configFile);
		lconf.load();
		cfg_SpwanMob	= lconf.get("Entity", "cfg_exprotion", true).getBoolean(true);
		lconf.save();




		fn_boxegg	= new GVCItemBoxEgg().setUnlocalizedName("boxEgg").setTextureName("gvcguns:boxegg");
		GameRegistry.registerItem(fn_boxegg, "boxEgg");
		fn_targetegg	= new GVCItemTargetEgg().setUnlocalizedName("TargetEgg").setTextureName("gvcguns:targetegg");
		GameRegistry.registerItem(fn_targetegg, "TargetEgg");
		fn_sentryegg	= new GVCItemEggSentry(0).setUnlocalizedName("SentryEgg").setTextureName("gvcguns:sentryegg");
		GameRegistry.registerItem(fn_sentryegg, "SentryEgg");
		fn_sentryaagegg	= new GVCItemEggSentry(1).setUnlocalizedName("SentryAAGEgg").setTextureName("gvcguns:sentryaagegg");
		GameRegistry.registerItem(fn_sentryaagegg, "SentryAAGEgg");
		lconf.load();
		isDebugMessage	= lconf.get("RefinedMilitaryShovelReplica", "isDebugMessage", true).getBoolean(true);
//		cfg_exprotion	= lconf.get("RefinedMilitaryShovelReplica", "cfg_exprotion", true).getBoolean(true);
//		cfg_GunModel3D	= lconf.get("RefinedMilitaryShovelReplica", "cfg_GunModel3D", true).getBoolean(true);
//		cfg_GunModelObj	= lconf.get("RefinedMilitaryShovelReplica", "cfg_GunModelObj", true).getBoolean(true);
//		cfg_ADSKeytype	= lconf.get("RefinedMilitaryShovelReplica", "cfg_ADSKeytype", false).getBoolean(false);
//		cfg_FriendFireLMM	= lconf.get("LMM", "cfg_FriendFireLMM", false).getBoolean(false);
//		cfg_RenderGunSizeLMM	= lconf.get("LMM", "cfg_RenderGunSizeLMM", false).getBoolean(false);
//		cfg_rendercrosshairs	= lconf.get("RefinedMilitaryShovelReplica", "cfg_rendercrosshairs", false).getBoolean(false);
//		en_reddotID	= lconf.get("RefinedMilitaryShovelReplica", "cfg_EnchantID_RedDot", 185).getInt(185);
//		en_laserID	= lconf.get("RefinedMilitaryShovelReplica", "cfg_EnchantID_Laser", 186).getInt(186);
//		en_scopeID	= lconf.get("RefinedMilitaryShovelReplica", "cfg_EnchantID_Scope", 187).getInt(187);
//		cfg_canEjectCartridge	= lconf.get("RefinedMilitaryShovelReplica", "cfg_canEjectCartridge", true).getBoolean(true);
//		cfg_Cartridgetime	= lconf.get("RefinedMilitaryShovelReplica", "cfg_Cartridgetime", 200).getInt(200);
//		cfg_RenderPlayer	= lconf.get("Render", "cfg_RenderPlayer", true).getBoolean(true);
		cfg_EasyRecipe	= lconf.get("GVCUtil", "cfg_EasyRecipe", true).getBoolean(true);
		lconf.save();





		fn_magazine	= GameRegistry.findItem("HandmadeGuns", "bullet_hmg");
		fn_magazine_545	= GameRegistry.findItem("HandmadeGuns", "5.45mm magazine");
		fn_magazine_545_long	= GameRegistry.findItem("HandmadeGuns", "5.45mm long magazine");
		fn_magazine_556	= GameRegistry.findItem("HandmadeGuns", "5.56mm magazine");
		fn_magazine_762	= GameRegistry.findItem("HandmadeGuns", "7.62mm magazine");
		fn_magazine_762_clip	= GameRegistry.findItem("HandmadeGuns", "7.62mm clip");
		fn_magazine_1270	= GameRegistry.findItem("HandmadeGuns", "12.7mm Magazine");
		fn_magazinehg	= GameRegistry.findItem("HandmadeGuns", "bullet_hg_hmg");
		fn_magazinemg	= GameRegistry.findItem("HandmadeGuns", "bullet_lmg_hmg");
		fn_shell	= GameRegistry.findItem("HandmadeGuns", "bullet_shell_hmg");
		fn_rpg	= GameRegistry.findItem("HandmadeGuns", "bullet_rr_hmg");

//		fn_missile	= new Item().setUnlocalizedName("missile").setTextureName("gvcguns:missile").setCreativeTab(tabgvc);
//		GameRegistry.registerItem(fn_missile, "missile");


		fn_box	= new GVCItemBox().setUnlocalizedName("box").setTextureName("gvcguns:box").setMaxStackSize(64);
		GameRegistry.registerItem(fn_box, "box");
		fn_pra	= GameRegistry.findItem("HandmadeGuns", "Prastic");
		OreDictionary.registerOre("ingotPlastic", new ItemStack(fn_pra, 1, 0));

//		fn_torch	= new GVCItemtorch().setUnlocalizedName("torch").setTextureName("gvcguns:gustorch");
//		GameRegistry.registerItem(fn_torch, "torch");
		//fn_paras	= new GVCItemparas().setUnlocalizedName("paras").setTextureName("gvcguns:paras");
		//GameRegistry.registerItem(fn_paras, "paras");
		fn_cm	= new GVCItemCM(10, false).setUnlocalizedName("cm").setTextureName("gvcguns:CM");
		GameRegistry.registerItem(fn_cm, "cm");
		fn_health	= new GVCItemHealthPack().setUnlocalizedName("HealthPack").setTextureName("gvcguns:HealthPack");
		GameRegistry.registerItem(fn_health, "HealthPack");

		praarmor = EnumHelper.addArmorMaterial("PraArmor", 430, new int[] {4, 8, 6, 2}, 10);
		fn_prahelmet	= new GVCItemArmorPra(praarmor, 0).setUnlocalizedName("prahelmet").setTextureName("gvcguns:prahelmet").setCreativeTab(tabgvc);
		GameRegistry.registerItem(fn_prahelmet, "prahelmet");
		fn_prachestp	= new GVCItemArmorPra(praarmor, 1).setUnlocalizedName("prachestp").setTextureName("gvcguns:prachestp").setCreativeTab(tabgvc);
		GameRegistry.registerItem(fn_prachestp, "prachestp");
		fn_praleggings	= new GVCItemArmorPra(praarmor, 2).setUnlocalizedName("praleggings").setTextureName("gvcguns:praleggings").setCreativeTab(tabgvc);
		GameRegistry.registerItem(fn_praleggings, "praleggings");
		fn_praboots	= new GVCItemArmorPra(praarmor, 3).setUnlocalizedName("praboots").setTextureName("gvcguns:praboots").setCreativeTab(tabgvc);
		GameRegistry.registerItem(fn_praboots, "praboots");


		fn_reddot	= GameRegistry.findItem("HandmadeGuns", "reddot");
		fn_scope	= GameRegistry.findItem("HandmadeGuns", "scope");
		fn_bayonet	= GameRegistry.findItem("HandmadeGuns", "SWORD_Sample");
		fn_Suppressor	= GameRegistry.findItem("HandmadeGuns", "Suppressor");
		fn_laser	= GameRegistry.findItem("HandmadeGuns", "laser");
		fn_grip	= GameRegistry.findItem("HandmadeGuns", "grip");

		{
			proxy.registerGuns(pEvent);
		}


			fn_camp	= new GVCblockCamp().setBlockName("Camp").setBlockTextureName("gvcguns:camp");
		GameRegistry.registerBlock(fn_camp, "Camp");

		fn_gunbox	= new GVCBlockGunBox().setBlockName("GunBox").setBlockTextureName("gvcguns:gunbox");
		GameRegistry.registerBlock(fn_gunbox, "GunBox");

		fn_ied	= new GVCBlockGunIED().setBlockName("IED")
				.setBlockTextureName("gvcguns:IED");
		fn_ied_AT	= new GVCBlockGunIED_AntiTank().setBlockName("IED_AT")
				.setBlockTextureName("gvcguns:IED_AT");


		GameRegistry.registerBlock(fn_ied, "IED");
		GameRegistry.registerBlock(fn_ied_AT, "IED_AT");

		GameRegistry.addRecipe(new ItemStack(fn_ied, 6),
				"II",
				"dd",
				'd', Items.gunpowder,'I',Items.iron_ingot
		);
		GameRegistry.addRecipe(new ItemStack(fn_ied_AT, 2),
				"III",
				"dId",
				"ddd",
				'd', Items.gunpowder,'I',Items.iron_ingot
		);

		fn_cont	= new GVCBlockCont().setBlockName("Contna").setBlockTextureName("gvcguns:cont");
		GameRegistry.registerBlock(fn_cont, "Contna");


		fn_target	= new Item().setUnlocalizedName("target").setTextureName("gvcguns:target");
		GameRegistry.registerItem(fn_target, "target");


	}

	@EventHandler
	public void init(FMLInitializationEvent pEvent) {
		villager = new GVCVillagerTrade();
		//VillagerRegistry.instance().registerVillageTradeHandler(1, new GVCVillagerTrade());
		while(VillagerRegistry.instance().getRegisteredVillagers().contains(GVCVillagerProfession)){
			GVCVillagerProfession++;
		}
		VillagerRegistry.instance().registerVillagerId(GVCVillagerProfession);
		VillagerRegistry.instance().registerVillageTradeHandler(GVCVillagerProfession, villager);

		GameRegistry.addRecipe(new ItemStack(fn_targetegg, 1),
				"r",
				"e",
				'e', Items.egg,
				'r', Items.redstone
		);

		GameRegistry.addRecipe(new ItemStack(fn_sentryegg, 1),
				" i ",
				"ib ",
				"i i",
				'i', Items.iron_ingot,
				'b', Blocks.iron_block
		);
		GameRegistry.addRecipe(new ItemStack(fn_sentryaagegg, 1),
				" i ",
				"ib ",
				"i i",
				'i', Items.gold_ingot,
				'b', Blocks.iron_block
		);

		BiomeGenBase[] biomeList = null;
		biomeList = new BiomeGenBase[]{
				BiomeGenBase.desert,
				BiomeGenBase.plains,
				BiomeGenBase.savanna,
				//BiomeGenBase.mushroomIsland,
				BiomeGenBase.forest,
				BiomeGenBase.birchForest,
				BiomeGenBase.swampland,
				BiomeGenBase.taiga,
				BiomeGenBase.extremeHills,
				BiomeGenBase.hell,
				BiomeGenBase.sky,
				BiomeGenBase.jungle,
				BiomeGenBase.stoneBeach,
				BiomeGenBase.mesa,
				BiomeGenBase.megaTaiga,
		};





		EntityRegistry.registerModEntity(GVCEntityBox.class, "Box", 0, this, 250, 1, false);
		EntityRegistry.registerModEntity(GVCEntityTarget.class, "Target", 1, this, 250, 1, false);
		EntityRegistry.registerModEntity(GVCEntitySentry.class, "Sentry", 2, this, 250, 1, false);
		EntityRegistry.registerModEntity(GVCEntityBoxSpawner.class, "Box_spawned", 3, this, 250, 1, false);

		if(cfg_SpwanMob){
			for(BiomeGenBase biome : biomeList)
			{
				if(biome!=null)
				{
					EntityRegistry.addSpawn(GVCEntityBoxSpawner.class, 20, 2, 6, EnumCreatureType.monster, biome);
		/*EntityRegistry.addSpawn(GVCEntityGuerrilla.class, 40, 5, 10, EnumCreatureType.monster, new BiomeGenBase[]{BiomeGenBase.desert});
		EntityRegistry.addSpawn(GVCEntityGuerrilla.class, 40, 5, 10, EnumCreatureType.monster, new BiomeGenBase[]{BiomeGenBase.extremeHills});
		EntityRegistry.addSpawn(GVCEntityGuerrilla.class, 40, 5, 10, EnumCreatureType.monster, new BiomeGenBase[]{BiomeGenBase.forest});
		EntityRegistry.addSpawn(GVCEntityGuerrilla.class, 40, 5, 10, EnumCreatureType.monster, new BiomeGenBase[]{BiomeGenBase.taiga});
		EntityRegistry.addSpawn(GVCEntityGuerrilla.class, 40, 5, 10, EnumCreatureType.monster, new BiomeGenBase[]{BiomeGenBase.swampland});
		EntityRegistry.addSpawn(GVCEntityGuerrilla.class, 40, 5, 10, EnumCreatureType.monster, new BiomeGenBase[]{BiomeGenBase.hell});
		EntityRegistry.addSpawn(GVCEntityGuerrilla.class, 40, 5, 10, EnumCreatureType.monster, new BiomeGenBase[]{BiomeGenBase.sky});
		EntityRegistry.addSpawn(GVCEntityGuerrilla.class, 40, 5, 10, EnumCreatureType.monster, new BiomeGenBase[]{BiomeGenBase.jungle});
		EntityRegistry.addSpawn(GVCEntityGuerrilla.class, 40, 5, 10, EnumCreatureType.monster, new BiomeGenBase[]{BiomeGenBase.savanna});
		EntityRegistry.addSpawn(GVCEntityGuerrilla.class, 40, 5, 10, EnumCreatureType.monster, new BiomeGenBase[]{BiomeGenBase.coldTaiga});
		EntityRegistry.addSpawn(GVCEntityGuerrilla.class, 40, 5, 10, EnumCreatureType.monster, new BiomeGenBase[]{BiomeGenBase.mesa});
		EntityRegistry.addSpawn(GVCEntityGuerrilla.class, 40, 5, 10, EnumCreatureType.monster, new BiomeGenBase[]{BiomeGenBase.megaTaiga});
		EntityRegistry.addSpawn(GVCEntityGuerrilla.class, 40, 5, 10, EnumCreatureType.monster, new BiomeGenBase[]{BiomeGenBase.stoneBeach});
		EntityRegistry.addSpawn(GVCEntityGuerrilla.class, 40, 5, 10, EnumCreatureType.monster, new BiomeGenBase[]{BiomeGenBase.coldBeach});
		EntityRegistry.addSpawn(GVCEntityGuerrilla.class, 40, 5, 10, EnumCreatureType.monster, new BiomeGenBase[]{BiomeGenBase.birchForest});
		EntityRegistry.addSpawn(GVCEntityGuerrilla.class, 40, 5, 10, EnumCreatureType.monster, new BiomeGenBase[]{BiomeGenBase.coldTaiga});*/
					//EntityRegistry.addSpawn(GVCEntityGuerrilla.class, 40, 5, 10, EnumCreatureType.monster);
				}
			}
		}
		/*if(FMLCommonHandler.instance().getSide() == Side.CLIENT)
		{
			RenderingRegistry.registerEntityRenderingHandler(GVCEntityBox.class, new GVCRenderBox());
			RenderingRegistry.registerEntityRenderingHandler(GVCEntityTarget.class, new GVCRenderTarget());
			VillagerRegistry.instance().registerVillagerSkin(GVCVillagerProfession, new ResourceLocation("gvcguns:textures/entity/mob/gvcVillager.png"));
		}*/

		BlockDispenser.dispenseBehaviorRegistry.putObject(this.fn_targetegg,new  IBehaviorDispenseItem(){
			@Override
			public ItemStack dispense(IBlockSource var1, ItemStack var2){
				World world = var1.getWorld();//World
		              /*Item item = var2.getItem();*/ //Item
				IPosition iposition = BlockDispenser.func_149939_a(var1);//IPosition
				double x = iposition.getX();//
				double y = iposition.getY();
				double z = iposition.getZ();//
				//world.spawnEntityInWorld(new GVCEntityTarget(world));
				//int var12 = MathHelper.floor_double((double)(par2EntityPlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
				GVCEntityTarget entityskeleton = new GVCEntityTarget(world);
				entityskeleton.setLocationAndAngles(x, y, z,  0, 0);
				world.spawnEntityInWorld(entityskeleton);
				return var2.splitStack(var2.stackSize-1);
			}
		});

		//BlockDispenser.dispenseBehaviorRegistry.putObject(fn_targetegg, new GVCBehaviorDispenseTarget());

		int D = Short.MAX_VALUE;

		if(cfg_EasyRecipe){
			GameRegistry.addRecipe(new ItemStack(Items.gunpowder, 1),
					"dd",
					"dd",
					'd', Blocks.dirt
			);
		}


//		GameRegistry.addRecipe(new ItemStack(fn_magazinehg, 1),
//				"aa",
//				"aa",
//
//				'a', Items.arrow
//		);

//		GameRegistry.addRecipe(new ItemStack(fn_magazinemg, 1),
//				"iii",
//				"ggg",
//				'i', Items.iron_ingot,
//				'g', Items.gunpowder
//		);

//		GameRegistry.addRecipe(new ItemStack(fn_rpg, 4),
//				"t  ",
//				" i ",
//				"  g",
//				't', Blocks.tnt,
//				'i', Items.iron_ingot,
//				'g', Items.gunpowder
//		);
//		GameRegistry.addRecipe(new ItemStack(fn_missile, 1),
//				"b  ",
//				" r ",
//				"  r",
//				'b', Items.repeater,
//				'r', Blocks.tnt
//		);

//		GameRegistry.addRecipe(new ItemStack(fn_shell, 16),
//				"c",
//				"p",
//				"g",
//				'c', Blocks.cobblestone,
//				'p', Items.paper,
//				'g', Items.gunpowder
//		);

//		GameRegistry.addRecipe(new ItemStack(fn_torch, 1),
//				"d ",
//				"ii",
//				"ii",
//				'i', Items.iron_ingot,
//				'd', Blocks.diamond_block
//		);




//		GameRegistry.addShapelessRecipe(new ItemStack(fn_torch, 1), new ItemStack(fn_torch, 1,D), Blocks.coal_block);

		GameRegistry.addShapelessRecipe(new ItemStack(fn_cm, 1),
				Items.apple, Items.sugar, Items.wheat, this.fn_box);

		GameRegistry.addRecipe(new ItemStack(fn_health, 2),
				"gw",
				"wg",
				'g', Blocks.glass,
				'w', new ItemStack(Blocks.wool, 1, D)
		);
		GameRegistry.addRecipe(new ItemStack(fn_box, 1),
				"ppp",
				"ppp",
				'p', Items.paper
		);

		GameRegistry.addRecipe(new ItemStack(fn_prahelmet, 1),
				"ppp",
				"pip",
				'p', this.fn_pra,
				'i', Items.iron_helmet
		);
		GameRegistry.addRecipe(new ItemStack(fn_prachestp, 1),
				"p p",
				"pip",
				"ppp",
				'p', this.fn_pra,
				'i', Items.iron_chestplate
		);
		GameRegistry.addRecipe(new ItemStack(fn_praleggings, 1),
				"ppp",
				"pip",
				"p p",
				'p', this.fn_pra,
				'i', Items.iron_leggings
		);
		GameRegistry.addRecipe(new ItemStack(fn_praboots, 1),
				"pip",
				"p p",
				'p', this.fn_pra,
				'i', Items.iron_boots
		);












		GameRegistry.addShapelessRecipe(new ItemStack(GameRegistry.findItem("HandmadeGuns","AK74")),new ItemStack(fn_ak74, 1));
		fn_ak74 = GameRegistry.findItem("HandmadeGuns","AK74");

		GameRegistry.addShapelessRecipe(new ItemStack(GameRegistry.findItem("HandmadeGuns","RPK74")),new ItemStack(fn_rpk74, 1));
		fn_rpk74 = GameRegistry.findItem("HandmadeGuns","RPK74");

		GameRegistry.addShapelessRecipe(new ItemStack(GameRegistry.findItem("HandmadeGuns","AKS74U")),new ItemStack(fn_aks74u, 1));
		fn_aks74u = GameRegistry.findItem("HandmadeGuns","AKS74U");

		GameRegistry.addShapelessRecipe(new ItemStack(GameRegistry.findItem("HandmadeGuns","M10")),new ItemStack(fn_m10, 1));
		fn_m10 = GameRegistry.findItem("HandmadeGuns","M10");

		GameRegistry.addShapelessRecipe(new ItemStack(GameRegistry.findItem("HandmadeGuns","M870")),new ItemStack(fn_m870, 1));
		fn_m870 = GameRegistry.findItem("HandmadeGuns","M870");

		GameRegistry.addShapelessRecipe(new ItemStack(GameRegistry.findItem("HandmadeGuns","SVD")),new ItemStack(fn_svd, 1));
		fn_svd = GameRegistry.findItem("HandmadeGuns","SVD");

		GameRegistry.addShapelessRecipe(new ItemStack(GameRegistry.findItem("HandmadeGuns","PKM")),new ItemStack(fn_pkm, 1));
		fn_pkm = GameRegistry.findItem("HandmadeGuns","PKM");

		GameRegistry.addShapelessRecipe(new ItemStack(GameRegistry.findItem("HandmadeGuns","M16A4")),new ItemStack(fn_m16a4, 1));
		fn_m16a4 = GameRegistry.findItem("HandmadeGuns","M16A4");

		GameRegistry.addShapelessRecipe(new ItemStack(GameRegistry.findItem("HandmadeGuns","M4A1")),new ItemStack(fn_m4a1, 1));
		fn_m4a1 = GameRegistry.findItem("HandmadeGuns","M4A1");

		GameRegistry.addShapelessRecipe(new ItemStack(GameRegistry.findItem("HandmadeGuns","M9")),new ItemStack(fn_m9, 1));
		fn_m9 = GameRegistry.findItem("HandmadeGuns","M9");
		GameRegistry.addShapelessRecipe(new ItemStack(GameRegistry.findItem("HandmadeGuns","M1911")),new ItemStack(fn_m1911, 1));
		fn_m1911 = GameRegistry.findItem("HandmadeGuns","M9");

		GameRegistry.addShapelessRecipe(new ItemStack(GameRegistry.findItem("HandmadeGuns","G17")),new ItemStack(fn_g17, 1));
		fn_g17 = GameRegistry.findItem("HandmadeGuns","G17");

		GameRegistry.addShapelessRecipe(new ItemStack(GameRegistry.findItem("HandmadeGuns","G18")),new ItemStack(fn_g18, 1));
		fn_g18 = GameRegistry.findItem("HandmadeGuns","G18");

		GameRegistry.addShapelessRecipe(new ItemStack(GameRegistry.findItem("HandmadeGuns","M110")),new ItemStack(fn_m110, 1));
		fn_m110 = GameRegistry.findItem("HandmadeGuns","M110");

		GameRegistry.addShapelessRecipe(new ItemStack(GameRegistry.findItem("HandmadeGuns","M240B")),new ItemStack(fn_m240b, 1));
		fn_m240b = GameRegistry.findItem("HandmadeGuns","M240B");

		GameRegistry.addShapelessRecipe(new ItemStack(GameRegistry.findItem("HandmadeGuns","R700")),new ItemStack(fn_r700, 1));
		fn_r700 = GameRegistry.findItem("HandmadeGuns","R700");

		GameRegistry.addShapelessRecipe(new ItemStack(GameRegistry.findItem("HandmadeGuns","M82A3")),new ItemStack(fn_m82a3, 1));
		fn_m82a3 = GameRegistry.findItem("HandmadeGuns","M82A3");

		GameRegistry.addShapelessRecipe(new ItemStack(GameRegistry.findItem("HandmadeGuns","G36")),new ItemStack(fn_g36, 1));
		fn_g36 = GameRegistry.findItem("HandmadeGuns","G36");

		GameRegistry.addShapelessRecipe(new ItemStack(GameRegistry.findItem("HandmadeGuns","G36K")),new ItemStack(fn_g36c, 1));
		fn_g36c = GameRegistry.findItem("HandmadeGuns","G36K");

		GameRegistry.addShapelessRecipe(new ItemStack(GameRegistry.findItem("HandmadeGuns","MG36")),new ItemStack(fn_mg36, 1));
		fn_mg36 = GameRegistry.findItem("HandmadeGuns","MG36");

		GameRegistry.addShapelessRecipe(new ItemStack(GameRegistry.findItem("HandmadeGuns","RPG7")),new ItemStack(fn_rpg7, 1));
		fn_rpg7 = GameRegistry.findItem("HandmadeGuns","RPG7");

		GameRegistry.addShapelessRecipe(new ItemStack(GameRegistry.findItem("HandmadeGuns","SMAW")),new ItemStack(fn_smaw, 1));
		fn_smaw = GameRegistry.findItem("HandmadeGuns","SMAW");

		GameRegistry.addShapelessRecipe(new ItemStack(GameRegistry.findItem("HandmadeGuns","M134")),new ItemStack(fn_m134, 1));
		fn_m134 = GameRegistry.findItem("HandmadeGuns","M134");
		fn_missile = GameRegistry.findItem("HandmadeGuns","missile");



		EntityRegistry.registerModEntity(GVCEntityC4.class, "C4", 191, this, 128, 1, false);


		fn_c4	= new GVCItemC4().setUnlocalizedName("C4").setTextureName("gvcguns:C4");
		GameRegistry.registerItem(fn_c4, "C4");
		fn_c4cn	= new GVCItemC4cn().setUnlocalizedName("C4cn").setTextureName("gvcguns:C4cn");
		GameRegistry.registerItem(fn_c4cn, "C4cn");

		GameRegistry.addRecipe(new ItemStack(fn_c4, 2),
				"gs",
				'g', Items.gunpowder,
				's', Items.slime_ball
		);

		GameRegistry.addRecipe(new ItemStack(fn_c4cn, 1),
				"pr",
				"pp",
				'r', Items.redstone,
				'p',  fn_pra
		);

		FMLCommonHandler.instance().bus().register(this);
		//if(pEvent.getSide().isClient())
		//MinecraftForge.EVENT_BUS.register(new GVCZonUpdate());

		//NetworkRegistry.INSTANCE.registerGuiHandler(this, new GVCGuiHandler());
		proxy.reisterRenderers();
		proxy.registerTileEntity();
		proxy.getCilentMinecraft();

		GameRegistry.registerFuelHandler(new IFuelHandler(){
			@Override
			public int getBurnTime(ItemStack fuel){
				if(fuel.getItem().equals(GVCUtils.fn_box)){
					return 3200;
				}
				return 0;
			}
		});





	}

}
