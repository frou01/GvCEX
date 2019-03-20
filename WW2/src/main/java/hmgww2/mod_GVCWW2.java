package hmgww2;



import java.io.File;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import hmgww2.blocks.BlockBaseBlock2_GER;
import hmgww2.blocks.BlockBaseBlock2_JPN;
import hmgww2.blocks.BlockBaseBlock2_RUS;
import hmgww2.blocks.BlockBaseBlock2_USA;
import hmgww2.blocks.BlockBaseBlock3_GER;
import hmgww2.blocks.BlockBaseBlock3_JPN;
import hmgww2.blocks.BlockBaseBlock3_RUS;
import hmgww2.blocks.BlockBaseBlock3_USA;
import hmgww2.blocks.BlockBaseBlock4_JPN;
import hmgww2.blocks.BlockBaseBlock4_USA;
import hmgww2.blocks.BlockBaseBlock_GER;
import hmgww2.blocks.BlockBaseBlock_JPN;
import hmgww2.blocks.BlockBaseBlock_RUS;
import hmgww2.blocks.BlockBaseBlock_USA;
import hmgww2.blocks.BlockFlag2_GER;
import hmgww2.blocks.BlockFlag2_JPN;
import hmgww2.blocks.BlockFlag2_RUS;
import hmgww2.blocks.BlockFlag2_USA;
import hmgww2.blocks.BlockFlag3_GER;
import hmgww2.blocks.BlockFlag3_JPN;
import hmgww2.blocks.BlockFlag3_RUS;
import hmgww2.blocks.BlockFlag3_USA;
import hmgww2.blocks.BlockFlag4_JPN;
import hmgww2.blocks.BlockFlag4_USA;
import hmgww2.blocks.BlockFlag_GER;
import hmgww2.blocks.BlockFlag_JPN;
import hmgww2.blocks.BlockFlag_RUS;
import hmgww2.blocks.BlockFlag_USA;
import hmgww2.entity.*;
import hmgww2.entity.EntityUSSR_TankSPG;
import hmgww2.event.EventEntityBases;
import hmgww2.event.EventEntityPlayer;
import hmgww2.event.EventOverlay;
import hmgww2.event.GenerateBase;
import hmgww2.event.RecipeRegistrys;
import hmgww2.items.ItemIFFArmor;
import hmgww2.items.ItemSetFlag;
import hmgww2.items.ItemSpwanEntity;
import hmgww2.network.WW2MessageKeyPressed;
import hmgww2.network.WW2PacketHandler;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.EnumHelper;


@Mod(
		modid	= mod_GVCWW2.MOD_ID,
		name	= mod_GVCWW2.MOD_ID,
		version	= "1.7.x-srg-1"
		)
public class mod_GVCWW2 {
	@SidedProxy(clientSide = "hmgww2.ClientProxyGVCWW2", serverSide = "hmgww2.CommonSideProxyGVCWW2")
	public static CommonSideProxyGVCWW2 proxy;
	public static final String MOD_ID = "HMGGVCWW2";
	@Mod.Instance("HMGGVCWW2")
	 
    public static mod_GVCWW2 INSTANCE;
	public static Item flash;

	public static ArmorMaterial armor;
	public static Item armor_jpn;
	public static Item armor_usa;
	public static Item armor_ger;
	public static Item armor_rus;
	
	
	public static Item spawn_jpn_s;//1
	public static Item spawn_jpn_tank;//2
	public static Item spawn_jpn_fighter;//3
	public static Item spawn_jpn_tankaa;//4
	public static Item spawn_jpn_tankspg;//5
	public static Item spawn_jpn_attcker;//6
	public static Item spawn_jpn_bship;//7
	public static Item spawn_jpn_dship;//8
	
	public static Item spawn_usa_s;
	public static Item spawn_usa_tank;
	public static Item spawn_usa_fighter;
	public static Item spawn_usa_tankaa;
	public static Item spawn_usa_tankspg;
	public static Item spawn_usa_attcker;//6
	public static Item spawn_usa_bship;//7
	public static Item spawn_usa_dship;//8
	
	public static Item spawn_ger_s;//1
	public static Item spawn_ger_tank;//2
	public static Item spawn_ger_fighter;//3
	public static Item spawn_ger_tankaa;//4
	public static Item spawn_ger_tankspg;//5
	public static Item spawn_ger_attcker;//6
	public static Item spawn_ger_tankh;//7
	public static Item spawn_ger_bship;//7
	public static Item spawn_ger_dship;//8
	
	public static Item spawn_rus_s;//1
	public static Item spawn_rus_tank;//2
	public static Item spawn_rus_fighter;//3
	public static Item spawn_rus_tankaa;//4
	public static Item spawn_rus_tankspg;//5
	public static Item spawn_rus_attcker;//6
	public static Item spawn_rus_tankh;//7
	
	public static Item spawn_rus_bship;//7
	public static Item spawn_rus_dship;//8
	
	public static Item bi_flag_jpn;
	public static int bi_flag_jpn_id;
	public static Block b_flag_jpn;
	public static Item bi_flag2_jpn;
	public static Block b_flag2_jpn;
	public static Item bi_flag3_jpn;
	public static Block b_flag3_jpn;
	public static Item bi_flag4_jpn;
	public static Block b_flag4_jpn;
	
	public static Item bi_flag_usa;
	public static Block b_flag_usa;
	public static Item bi_flag2_usa;
	public static Block b_flag2_usa;
	public static Item bi_flag3_usa;
	public static Block b_flag3_usa;
	public static Item bi_flag4_usa;
	public static Block b_flag4_usa;
	
	public static Item bi_flag_ger;
	public static Block b_flag_ger;
	public static Item bi_flag2_ger;
	public static Block b_flag2_ger;
	public static Item bi_flag3_ger;
	public static Block b_flag3_ger;
	public static Item bi_flag4_ger;
	public static Block b_flag4_ger;
	
	public static Item bi_flag_rus;
	public static Block b_flag_rus;
	public static Item bi_flag2_rus;
	public static Block b_flag2_rus;
	public static Item bi_flag3_rus;
	public static Block b_flag3_rus;
	public static Item bi_flag4_rus;
	public static Block b_flag4_rus;
	
	
	public static Block b_base_jpn;
	public static Block b_base2_jpn;
	public static Block b_base3_jpn;
	public static Block b_base4_jpn;
	
	public static Block b_base_usa;
	public static Block b_base2_usa;
	public static Block b_base3_usa;
	public static Block b_base4_usa;
	
	public static Block b_base_ger;
	public static Block b_base2_ger;
	public static Block b_base3_ger;
	public static Block b_base4_ger;
	
	public static Block b_base_rus;
	public static Block b_base2_rus;
	public static Block b_base3_rus;
	public static Block b_base4_rus;
	
	
	public static Item b_magazine;
	public static Item b_magazinehg;
	public static Item b_magazinemg;
	public static Item b_magazinerpg;
	
	public static Item gun_type38;
	public static Item gun_type99lmg;
	public static Item gun_type4Auto;
	public static Item gun_type100;
	public static Item gun_rota_cannon;
	public static Item gun_type14;
	
	public static Item gun_m1g;
	public static Item gun_m1t;
	public static Item gun_bar;
	public static Item gun_m1917;
	public static Item gun_m1b;
	public static Item gun_m1911;
	
	public static Item gun_grenade;
	
	public static Item gun_gew98;
	public static Item gun_gew43;
	public static Item gun_mp40;
	public static Item gun_mg34;
	public static Item gun_rpzb54;
	public static Item gun_p38;
	
	public static Item gun_m1891;
	public static Item gun_ppsh41;
	public static Item gun_dp28;
	public static Item gun_m1891sr;
	public static Item gun_tt33;
	
	public static boolean cfg_canspawn;
	public static boolean cfg_canspawntank;
	public static boolean cfg_canspawnfighter;
	public static boolean cfg_blockdestory;
	public static int cfg_spawnlimit;
	public static int cfg_spawnhight;
	public static int cfg_spawnlightlevel;
	
	public static boolean cfg_canbuild;
	public static int cfg_basegenerated;
	public static int cfg_basegenerated2;
	public static int cfg_basegenerated3;
	
	public static boolean cfg_spawn_jpn;
	public static boolean cfg_spawn_usa;
	public static boolean cfg_spawn_ger;
	public static boolean cfg_spawn_rus;
	
	public static int cfg_spawnblock_limit;
	public static int cfg_spawnblock_limit_s;
	public static int cfg_spawnblock_limit_tank;
	public static int cfg_spawnblock_limit_air;
	public static int cfg_spawnblock_limit_ship;
	
	public static boolean cfg_buildbase_jpn;
	public static boolean cfg_buildbase_ger;
	public static boolean cfg_buildbase_rus;
	public static boolean cfg_buildbase_usa;
	
	public static boolean cfg_candespawn;
	
	public static final CreativeTabs tabgvc = new WW2CreativeTab("GVCWW2");
	
	protected static File configFile;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent pEvent) {
		//GVCSoundEvent.registerSounds();
		//GVCLPacketHandler.init();
		configFile = pEvent.getSuggestedConfigurationFile();
		Configuration lconf = new Configuration(configFile);
		lconf.load();
		cfg_canspawn	= lconf.get("Entity", "cfg_canspawn", true).getBoolean(true);
		cfg_candespawn	= lconf.get("Entity", "cfg_candespawn", true).getBoolean(true);
		cfg_spawn_jpn	= lconf.get("Entity", "cfg_spawn_jpn", true).getBoolean(true);
		cfg_spawn_usa	= lconf.get("Entity", "cfg_spawn_usa", true).getBoolean(true);
		cfg_spawn_ger	= lconf.get("Entity", "cfg_spawn_ger", true).getBoolean(true);
		cfg_spawn_rus	= lconf.get("Entity", "cfg_spawn_rus", true).getBoolean(true);
		cfg_canspawntank	= lconf.get("Entity", "cfg_canspawntank", true).getBoolean(true);
		cfg_canspawnfighter	= lconf.get("Entity", "cfg_canspawnfighter", true).getBoolean(true);
		cfg_blockdestory	= lconf.get("Entity", "cfg_BlockDestory", true).getBoolean(true);
		cfg_spawnlimit	= lconf.get("Entity", "cfg_spawnlimit", 20).getInt(20);
		cfg_spawnhight	= lconf.get("Entity", "cfg_spawnhight", 20).getInt(20);
		cfg_spawnlightlevel	= lconf.get("Entity", "cfg_spawnlightlevel", 8).getInt(8);
		
		cfg_spawnblock_limit	= lconf.get("Entity", "cfg_spawnblock_limit", 50).getInt(50);
		cfg_spawnblock_limit_s	= lconf.get("Entity", "cfg_spawnblock_limit_s", 8).getInt(8);
		cfg_spawnblock_limit_tank	= lconf.get("Entity", "cfg_spawnblock_limit_tank", 2).getInt(2);
		cfg_spawnblock_limit_air	= lconf.get("Entity", "cfg_spawnblock_limit_air", 2).getInt(2);
		cfg_spawnblock_limit_ship	= lconf.get("Entity", "cfg_spawnblock_limit_ship", 2).getInt(2);
		
		
		cfg_canbuild	= lconf.get("World", "cfg_cancreatbase", true).getBoolean(true);
		cfg_basegenerated	= lconf.get("World", "cfg_basegenerated", 300).getInt(300);
		cfg_basegenerated2	= lconf.get("World", "cfg_basegenerated2", 360).getInt(360);
		cfg_basegenerated3	= lconf.get("World", "cfg_basegenerated3", 160).getInt(160);
		
		cfg_buildbase_jpn	= lconf.get("World", "cfg_buildbase_jpn", true).getBoolean(true);
		cfg_buildbase_ger	= lconf.get("World", "cfg_buildbase_ger", true).getBoolean(true);
		cfg_buildbase_rus	= lconf.get("World", "cfg_buildbase_rus", true).getBoolean(true);
		cfg_buildbase_usa	= lconf.get("World", "cfg_buildbase_usa", true).getBoolean(true);
		
		lconf.save();
		
		
		armor = EnumHelper.addArmorMaterial("IFFArmor", 430, new int[] {4, 8, 6, 2}, 10);
		armor_jpn	= new ItemIFFArmor(armor, 0, "hmgww2:textures/armor/armor_jpn.png",Nation.JPN).setUnlocalizedName("armor_jpn")
				.setTextureName("hmgww2:jpn/armor_jpn")
				.setCreativeTab(tabgvc);
	    GameRegistry.registerItem(armor_jpn, "armor_jpn");
	    armor_usa	= new ItemIFFArmor(armor, 0, "hmgww2:textures/armor/armor_usa.png",Nation.USA).setUnlocalizedName("armor_usa")
				.setTextureName("hmgww2:usa/armor_usa")
				.setCreativeTab(tabgvc);
	    GameRegistry.registerItem(armor_usa, "armor_usa");
	    armor_ger	= new ItemIFFArmor(armor, 0, "hmgww2:textures/armor/armor_ger.png",Nation.GER).setUnlocalizedName("armor_ger")
				.setTextureName("hmgww2:ger/armor_ger")
				.setCreativeTab(tabgvc);
	    GameRegistry.registerItem(armor_ger, "armor_ger");
	    armor_rus	= new ItemIFFArmor(armor, 0, "hmgww2:textures/armor/armor_rus.png",Nation.USSR).setUnlocalizedName("armor_rus")
				.setTextureName("hmgww2:rus/armor_rus")
				.setCreativeTab(tabgvc);
	    GameRegistry.registerItem(armor_rus, "armor_rus");
		
	    b_flag_jpn	= new BlockFlag_JPN().setBlockName("b_flag_jpn").setBlockTextureName("hmgww2:nullblock");
		GameRegistry.registerBlock(b_flag_jpn, "b_flag_jpn");
		bi_flag_jpn = new ItemSetFlag(b_flag_jpn).setUnlocalizedName("bi_flag_jpn").setTextureName("hmgww2:jpn/bi_flag_jpn")
				.setCreativeTab(tabgvc);
		GameRegistry.registerItem(bi_flag_jpn, "bi_flag_jpn");
		b_flag2_jpn	= new BlockFlag2_JPN().setBlockName("b_flag2_jpn").setBlockTextureName("hmgww2:nullblock");
		GameRegistry.registerBlock(b_flag2_jpn, "b_flag2_jpn");
		bi_flag2_jpn = new ItemSetFlag(b_flag2_jpn).setUnlocalizedName("bi_flag2_jpn").setTextureName("hmgww2:jpn/bi_flag2_jpn")
				.setCreativeTab(tabgvc);
		GameRegistry.registerItem(bi_flag2_jpn, "bi_flag2_jpn");
		b_flag3_jpn	= new BlockFlag3_JPN().setBlockName("b_flag3_jpn").setBlockTextureName("hmgww2:nullblock");
		GameRegistry.registerBlock(b_flag3_jpn, "b_flag3_jpn");
		bi_flag3_jpn = new ItemSetFlag(b_flag3_jpn).setUnlocalizedName("bi_flag3_jpn").setTextureName("hmgww2:jpn/bi_flag3_jpn")
				.setCreativeTab(tabgvc);
		GameRegistry.registerItem(bi_flag3_jpn, "bi_flag3_jpn");
		b_flag4_jpn	= new BlockFlag4_JPN().setBlockName("b_flag4_jpn").setBlockTextureName("hmgww2:nullblock");
		GameRegistry.registerBlock(b_flag4_jpn, "b_flagb_flag4_jpn2_jpn");
		bi_flag4_jpn = new ItemSetFlag(b_flag4_jpn).setUnlocalizedName("bi_flag4_jpn").setTextureName("hmgww2:jpn/bi_flag4_jpn")
				.setCreativeTab(tabgvc);
		GameRegistry.registerItem(bi_flag4_jpn, "bi_flag4_jpn");
		
		
		
		b_flag_usa	= new BlockFlag_USA().setBlockName("b_flag_usa").setBlockTextureName("hmgww2:nullblock");
		GameRegistry.registerBlock(b_flag_usa, "b_flag_usa");
		bi_flag_usa = new ItemSetFlag(b_flag_usa).setUnlocalizedName("bi_flag_usa").setTextureName("hmgww2:usa/bi_flag_usa")
				.setCreativeTab(tabgvc);
		GameRegistry.registerItem(bi_flag_usa, "bi_flag_usa");
		b_flag2_usa	= new BlockFlag2_USA().setBlockName("b_flag2_usa").setBlockTextureName("hmgww2:nullblock");
		GameRegistry.registerBlock(b_flag2_usa, "b_flag2_usa");
		bi_flag2_usa = new ItemSetFlag(b_flag2_usa).setUnlocalizedName("bi_flag2_usa").setTextureName("hmgww2:usa/bi_flag2_usa")
				.setCreativeTab(tabgvc);
		GameRegistry.registerItem(bi_flag2_usa, "bi_flag2_usa");
		b_flag3_usa	= new BlockFlag3_USA().setBlockName("b_flag3_usa").setBlockTextureName("hmgww2:nullblock");
		GameRegistry.registerBlock(b_flag3_usa, "b_flag3_usa");
		bi_flag3_usa = new ItemSetFlag(b_flag3_usa).setUnlocalizedName("bi_flag3_usa").setTextureName("hmgww2:usa/bi_flag3_usa")
				.setCreativeTab(tabgvc);
		GameRegistry.registerItem(bi_flag3_usa, "bi_flag3_usa");
		b_flag4_usa	= new BlockFlag4_USA().setBlockName("b_flag4_usa").setBlockTextureName("hmgww2:nullblock");
		GameRegistry.registerBlock(b_flag4_usa, "b_flag4_usa");
		bi_flag4_usa = new ItemSetFlag(b_flag4_usa).setUnlocalizedName("bi_flag4_usa").setTextureName("hmgww2:usa/bi_flag4_usa")
				.setCreativeTab(tabgvc);
		GameRegistry.registerItem(bi_flag4_usa, "bi_flag4_usa");
		
		
		b_flag_ger	= new BlockFlag_GER().setBlockName("b_flag_ger").setBlockTextureName("hmgww2:nullblock");
		GameRegistry.registerBlock(b_flag_ger, "b_flag_ger");
		bi_flag_ger = new ItemSetFlag(b_flag_ger).setUnlocalizedName("bi_flag_ger").setTextureName("hmgww2:ger/bi_flag_ger")
				.setCreativeTab(tabgvc);
		GameRegistry.registerItem(bi_flag_ger, "bi_flag_ger");
		b_flag2_ger	= new BlockFlag2_GER().setBlockName("b_flag2_ger").setBlockTextureName("hmgww2:nullblock");
		GameRegistry.registerBlock(b_flag2_ger, "b_flag2_ger");
		bi_flag2_ger = new ItemSetFlag(b_flag2_ger).setUnlocalizedName("bi_flag2_ger").setTextureName("hmgww2:ger/bi_flag2_ger")
				.setCreativeTab(tabgvc);
		GameRegistry.registerItem(bi_flag2_ger, "bi_flag2_ger");
		b_flag3_ger	= new BlockFlag3_GER().setBlockName("b_flag3_ger").setBlockTextureName("hmgww2:nullblock");
		GameRegistry.registerBlock(b_flag3_ger, "b_flag3_ger");
		bi_flag3_ger = new ItemSetFlag(b_flag3_ger).setUnlocalizedName("bi_flag3_ger").setTextureName("hmgww2:ger/bi_flag3_ger")
				.setCreativeTab(tabgvc);
		GameRegistry.registerItem(bi_flag3_ger, "bi_flag3_ger");
		/*b_flag4_jpn	= new BlockFlag4_JPN().setBlockName("b_flag4_jpn").setBlockTextureName("hmgww2:nullblock");
		GameRegistry.registerBlock(b_flag4_jpn, "b_flagb_flag4_jpn2_jpn");
		bi_flag4_jpn = new ItemSetFlag(b_flag4_jpn).setUnlocalizedName("bi_flag4_jpn").setTextureName("hmgww2:jpn/bi_flag4_jpn")
				.setCreativeTab(tabgvc);
		GameRegistry.registerItem(bi_flag4_jpn, "bi_flag4_jpn");
		*/
		b_flag_rus	= new BlockFlag_RUS().setBlockName("b_flag_rus").setBlockTextureName("hmgww2:nullblock");
		GameRegistry.registerBlock(b_flag_rus, "b_flag_rus");
		bi_flag_rus = new ItemSetFlag(b_flag_rus).setUnlocalizedName("bi_flag_rus").setTextureName("hmgww2:rus/bi_flag_rus")
				.setCreativeTab(tabgvc);
		GameRegistry.registerItem(bi_flag_rus, "bi_flag_rus");
		b_flag2_rus	= new BlockFlag2_RUS().setBlockName("b_flag2_rus").setBlockTextureName("hmgww2:nullblock");
		GameRegistry.registerBlock(b_flag2_rus, "b_flag2_rus");
		bi_flag2_rus = new ItemSetFlag(b_flag2_ger).setUnlocalizedName("bi_flag2_rus").setTextureName("hmgww2:rus/bi_flag2_rus")
				.setCreativeTab(tabgvc);
		GameRegistry.registerItem(bi_flag2_rus, "bi_flag2_rus");
		b_flag3_rus	= new BlockFlag3_RUS().setBlockName("b_flag3_rus").setBlockTextureName("hmgww2:nullblock");
		GameRegistry.registerBlock(b_flag3_rus, "b_flag3_rus");
		bi_flag3_rus = new ItemSetFlag(b_flag3_ger).setUnlocalizedName("bi_flag3_rus").setTextureName("hmgww2:rus/bi_flag3_rus")
				.setCreativeTab(tabgvc);
		GameRegistry.registerItem(bi_flag3_rus, "bi_flag3_rus");
		/*b_flag4_jpn	= new BlockFlag4_JPN().setBlockName("b_flag4_jpn").setBlockTextureName("hmgww2:nullblock");
		GameRegistry.registerBlock(b_flag4_jpn, "b_flagb_flag4_jpn2_jpn");
		bi_flag4_jpn = new ItemSetFlag(b_flag4_jpn).setUnlocalizedName("bi_flag4_jpn").setTextureName("hmgww2:jpn/bi_flag4_jpn")
				.setCreativeTab(tabgvc);
		GameRegistry.registerItem(bi_flag4_jpn, "bi_flag4_jpn");
		*/
		
		
		
		b_base_jpn	= new BlockBaseBlock_JPN().setBlockName("b_base_jpn").setBlockTextureName("hmgww2:b_base_jpn")
				.setCreativeTab(tabgvc);
		GameRegistry.registerBlock(b_base_jpn, "b_base_jpn");
		b_base2_jpn	= new BlockBaseBlock2_JPN().setBlockName("b_base2_jpn").setBlockTextureName("hmgww2:b_base2_jpn")
				.setCreativeTab(tabgvc);
		GameRegistry.registerBlock(b_base2_jpn, "b_base2_jpn");
		b_base3_jpn	= new BlockBaseBlock3_JPN().setBlockName("b_base3_jpn").setBlockTextureName("hmgww2:b_base3_jpn")
				.setCreativeTab(tabgvc);
		GameRegistry.registerBlock(b_base3_jpn, "b_base3_jpn");
		b_base4_jpn	= new BlockBaseBlock4_JPN().setBlockName("b_base4_jpn").setBlockTextureName("hmgww2:b_base4_jpn")
				.setCreativeTab(tabgvc);
		GameRegistry.registerBlock(b_base4_jpn, "b_base4_jpn");
		
		b_base_usa	= new BlockBaseBlock_USA().setBlockName("b_base_usa").setBlockTextureName("hmgww2:b_base_usa")
				.setCreativeTab(tabgvc);
		GameRegistry.registerBlock(b_base_usa, "b_base_usa");
		b_base2_usa	= new BlockBaseBlock2_USA().setBlockName("b_base2_usa").setBlockTextureName("hmgww2:b_base2_usa")
				.setCreativeTab(tabgvc);
		GameRegistry.registerBlock(b_base2_usa, "b_base2_usa");
		b_base3_usa	= new BlockBaseBlock3_USA().setBlockName("b_base3_usa").setBlockTextureName("hmgww2:b_base3_usa")
				.setCreativeTab(tabgvc);
		GameRegistry.registerBlock(b_base3_usa, "b_base3_usa");
		b_base4_usa	= new BlockBaseBlock4_USA().setBlockName("b_base4_usa").setBlockTextureName("hmgww2:b_base4_usa")
				.setCreativeTab(tabgvc);
		GameRegistry.registerBlock(b_base4_usa, "b_base4_usa");
		
		
		b_base_ger	= new BlockBaseBlock_GER().setBlockName("b_base_ger").setBlockTextureName("hmgww2:b_base_ger")
				.setCreativeTab(tabgvc);
		GameRegistry.registerBlock(b_base_ger, "b_base_ger");
		b_base2_ger	= new BlockBaseBlock2_GER().setBlockName("b_base2_ger").setBlockTextureName("hmgww2:b_base2_ger")
				.setCreativeTab(tabgvc);
		GameRegistry.registerBlock(b_base2_ger, "b_base2_ger");
		b_base3_ger	= new BlockBaseBlock3_GER().setBlockName("b_base3_ger").setBlockTextureName("hmgww2:b_base3_ger")
				.setCreativeTab(tabgvc);
		GameRegistry.registerBlock(b_base3_ger, "b_base3_ger");
		
		b_base_rus	= new BlockBaseBlock_RUS().setBlockName("b_base_rus").setBlockTextureName("hmgww2:b_base_rus")
				.setCreativeTab(tabgvc);
		GameRegistry.registerBlock(b_base_rus, "b_base_rus");
		b_base2_rus	= new BlockBaseBlock2_RUS().setBlockName("b_base2_rus").setBlockTextureName("hmgww2:b_base2_rus")
				.setCreativeTab(tabgvc);
		GameRegistry.registerBlock(b_base2_rus, "b_base2_rus");
		b_base3_rus	= new BlockBaseBlock3_RUS().setBlockName("b_base3_rus").setBlockTextureName("hmgww2:b_base3_rus")
				.setCreativeTab(tabgvc);
		GameRegistry.registerBlock(b_base3_rus, "b_base3_rus");
		
		
		
		
		
		
		b_magazine = new Item().setUnlocalizedName("b_magazine").setTextureName("hmgww2:b_magazine")
				.setCreativeTab(tabgvc);
		GameRegistry.registerItem(b_magazine, "b_magazine");
		b_magazinehg = new Item().setUnlocalizedName("b_magazinehg").setTextureName("hmgww2:b_magazinehg")
				.setCreativeTab(tabgvc);
		GameRegistry.registerItem(b_magazinehg, "b_magazinehg");
		b_magazinemg = new Item().setUnlocalizedName("b_magazinemg").setTextureName("hmgww2:b_magazinemg")
				.setCreativeTab(tabgvc);
		GameRegistry.registerItem(b_magazinemg, "b_magazinemg");
		b_magazinerpg = new Item().setUnlocalizedName("b_magazinerpg").setTextureName("hmgww2:b_magazinerpg")
				.setCreativeTab(tabgvc);
		GameRegistry.registerItem(b_magazinerpg, "b_magazinerpg");
		
		gun_type38 = GameRegistry.findItem("HandmadeGuns","Type38");
		
		gun_type99lmg = GameRegistry.findItem("HandmadeGuns","九九式軽機関銃");
		
		gun_type4Auto = GameRegistry.findItem("HandmadeGuns","四式自動小銃");
		
		gun_rota_cannon = GameRegistry.findItem("HandmadeGuns","試製四式七糎噴進砲");
		
		gun_type14 = GameRegistry.findItem("HandmadeGuns","十四年式拳銃");
		
		
		
		gun_m1g = GameRegistry.findItem("HandmadeGuns","M1Garand");
		
		gun_m1t = GameRegistry.findItem("HandmadeGuns","M1A1");
		
		gun_bar = GameRegistry.findItem("HandmadeGuns","Browning_Automatic_Rifle_M1918");
		
		gun_m1917 = GameRegistry.findItem("HandmadeGuns","M1917");
		
		gun_m1917 = GameRegistry.findItem("HandmadeGuns","Bazooka");
		
		gun_m1911 = GameRegistry.findItem("HandmadeGuns","WW2_M1911");
		
		
		
		
		
		gun_grenade = GameRegistry.findItem("HandmadeGuns","M26");
		
		
		gun_gew98 = GameRegistry.findItem("HandmadeGuns","Gew98");
		
		gun_gew43 = GameRegistry.findItem("HandmadeGuns","Gewehr43");
		
		gun_mp40 = GameRegistry.findItem("HandmadeGuns","MP38");
		
		gun_mg34 = GameRegistry.findItem("HandmadeGuns","MG34");
		
		gun_rpzb54 = GameRegistry.findItem("HandmadeGuns","Panzerschreck");
		
		gun_p38 = GameRegistry.findItem("HandmadeGuns","WaltherP-38");
		
		gun_m1891 = GameRegistry.findItem("HandmadeGuns","Mosin");
		
		gun_ppsh41 = GameRegistry.findItem("HandmadeGuns","ppsh41");
		
		gun_dp28 = GameRegistry.findItem("HandmadeGuns","DP28LMG");
		
		gun_dp28 = GameRegistry.findItem("HandmadeGuns","DP28LMG");
		
		gun_tt33 = GameRegistry.findItem("HandmadeGuns","TT-33");
		
		
		
		spawn_jpn_s = new ItemSpwanEntity(1).setUnlocalizedName("spawn_jpn_s").setTextureName("hmgww2:jpn/spawn_jpn_s")
				.setCreativeTab(tabgvc);
		GameRegistry.registerItem(spawn_jpn_s, "spawn_jpn_s");
		spawn_jpn_tank = new ItemSpwanEntity(2).setUnlocalizedName("spawn_jpn_tank").setTextureName("hmgww2:jpn/spawn_jpn_tank")
				.setCreativeTab(tabgvc);
		GameRegistry.registerItem(spawn_jpn_tank, "spawn_jpn_tank");
		spawn_jpn_fighter = new ItemSpwanEntity(3).setUnlocalizedName("spawn_jpn_fighter").setTextureName("hmgww2:jpn/spawn_jpn_fighter")
				.setCreativeTab(tabgvc);
		GameRegistry.registerItem(spawn_jpn_fighter, "spawn_jpn_fighter");
		spawn_jpn_tankaa = new ItemSpwanEntity(4).setUnlocalizedName("spawn_jpn_tankaa").setTextureName("hmgww2:jpn/spawn_jpn_tankaa")
				.setCreativeTab(tabgvc);
		GameRegistry.registerItem(spawn_jpn_tankaa, "spawn_jpn_tankaa");
		spawn_jpn_tankspg = new ItemSpwanEntity(5).setUnlocalizedName("spawn_jpn_tankspg").setTextureName("hmgww2:jpn/spawn_jpn_tankspg")
				.setCreativeTab(tabgvc);
		GameRegistry.registerItem(spawn_jpn_tankspg, "spawn_jpn_tankspg");
		spawn_jpn_attcker = new ItemSpwanEntity(6).setUnlocalizedName("spawn_jpn_attcker").setTextureName("hmgww2:jpn/spawn_jpn_attcker")
				.setCreativeTab(tabgvc);
		GameRegistry.registerItem(spawn_jpn_attcker, "spawn_jpn_attcker");
		spawn_jpn_bship = new ItemSpwanEntity(7).setUnlocalizedName("spawn_jpn_bship").setTextureName("hmgww2:jpn/spawn_jpn_bship")
				.setCreativeTab(tabgvc);
		GameRegistry.registerItem(spawn_jpn_bship, "spawn_jpn_bship");
		spawn_jpn_dship = new ItemSpwanEntity(8).setUnlocalizedName("spawn_jpn_dship").setTextureName("hmgww2:jpn/spawn_jpn_dship")
				.setCreativeTab(tabgvc);
		GameRegistry.registerItem(spawn_jpn_dship, "spawn_jpn_dship");
		
		
		spawn_usa_s = new ItemSpwanEntity(21).setUnlocalizedName("spawn_usa_s").setTextureName("hmgww2:usa/spawn_usa_s")
				.setCreativeTab(tabgvc);
		GameRegistry.registerItem(spawn_usa_s, "spawn_usa_s");
		spawn_usa_tank = new ItemSpwanEntity(22).setUnlocalizedName("spawn_usa_tank").setTextureName("hmgww2:usa/spawn_usa_tank")
				.setCreativeTab(tabgvc);
		GameRegistry.registerItem(spawn_usa_tank, "spawn_usa_tank");
		spawn_usa_fighter = new ItemSpwanEntity(23).setUnlocalizedName("spawn_usa_fighter").setTextureName("hmgww2:usa/spawn_usa_fighter")
				.setCreativeTab(tabgvc);
		GameRegistry.registerItem(spawn_usa_fighter, "spawn_usa_fighter");
		spawn_usa_tankaa = new ItemSpwanEntity(24).setUnlocalizedName("spawn_usa_tankaa").setTextureName("hmgww2:usa/spawn_usa_tankaa")
				.setCreativeTab(tabgvc);
		GameRegistry.registerItem(spawn_usa_tankaa, "spawn_usa_tankaa");
		spawn_usa_tankspg = new ItemSpwanEntity(25).setUnlocalizedName("spawn_usa_tankspg").setTextureName("hmgww2:usa/spawn_usa_tankspg")
				.setCreativeTab(tabgvc);
		GameRegistry.registerItem(spawn_usa_tankspg, "spawn_usa_tankspg");
		spawn_usa_attcker = new ItemSpwanEntity(26).setUnlocalizedName("spawn_usa_attcker").setTextureName("hmgww2:usa/spawn_usa_attcker")
				.setCreativeTab(tabgvc);
		GameRegistry.registerItem(spawn_usa_attcker, "spawn_usa_attcker");
		spawn_usa_bship = new ItemSpwanEntity(27).setUnlocalizedName("spawn_usa_bship").setTextureName("hmgww2:usa/spawn_usa_bship")
				.setCreativeTab(tabgvc);
		GameRegistry.registerItem(spawn_usa_bship, "spawn_usa_bship");
		spawn_usa_dship = new ItemSpwanEntity(28).setUnlocalizedName("spawn_usa_dship").setTextureName("hmgww2:usa/spawn_usa_dship")
				.setCreativeTab(tabgvc);
		GameRegistry.registerItem(spawn_usa_dship, "spawn_usa_dship");
		
		
		spawn_ger_s = new ItemSpwanEntity(41).setUnlocalizedName("spawn_ger_s").setTextureName("hmgww2:ger/spawn_ger_s")
				.setCreativeTab(tabgvc);
		GameRegistry.registerItem(spawn_ger_s, "spawn_ger_s");
		spawn_ger_tank = new ItemSpwanEntity(42).setUnlocalizedName("spawn_ger_tank").setTextureName("hmgww2:ger/spawn_ger_tank")
				.setCreativeTab(tabgvc);
		GameRegistry.registerItem(spawn_ger_tank, "spawn_ger_tank");
		spawn_ger_fighter = new ItemSpwanEntity(43).setUnlocalizedName("spawn_ger_fighter").setTextureName("hmgww2:ger/spawn_ger_fighter")
				.setCreativeTab(tabgvc);
		GameRegistry.registerItem(spawn_ger_fighter, "spawn_ger_fighter");
		spawn_ger_tankaa = new ItemSpwanEntity(44).setUnlocalizedName("spawn_ger_tankaa").setTextureName("hmgww2:ger/spawn_ger_tankaa")
				.setCreativeTab(tabgvc);
		GameRegistry.registerItem(spawn_ger_tankaa, "spawn_ger_tankaa");
		spawn_ger_tankspg = new ItemSpwanEntity(45).setUnlocalizedName("spawn_ger_tankspg").setTextureName("hmgww2:ger/spawn_ger_tankspg")
				.setCreativeTab(tabgvc);
		GameRegistry.registerItem(spawn_ger_tankspg, "spawn_ger_tankspg");
		spawn_ger_attcker = new ItemSpwanEntity(46).setUnlocalizedName("spawn_ger_attcker").setTextureName("hmgww2:ger/spawn_ger_attcker")
				.setCreativeTab(tabgvc);
		GameRegistry.registerItem(spawn_ger_attcker, "spawn_ger_attcker");
		spawn_ger_tankh = new ItemSpwanEntity(47).setUnlocalizedName("spawn_ger_tankh").setTextureName("hmgww2:ger/spawn_ger_tankh")
				.setCreativeTab(tabgvc);
		GameRegistry.registerItem(spawn_ger_tankh, "spawn_ger_tankh");
		/*spawn_usa_bship = new ItemSpwanEntity(27).setUnlocalizedName("spawn_usa_bship").setTextureName("hmgww2:usa/spawn_usa_bship")
				.setCreativeTab(tabgvc);
		GameRegistry.registerItem(spawn_usa_bship, "spawn_usa_bship");
		spawn_usa_dship = new ItemSpwanEntity(28).setUnlocalizedName("spawn_usa_dship").setTextureName("hmgww2:usa/spawn_usa_dship")
				.setCreativeTab(tabgvc);
		GameRegistry.registerItem(spawn_usa_dship, "spawn_usa_dship");
		*/
		
		
		spawn_rus_s = new ItemSpwanEntity(61).setUnlocalizedName("spawn_rus_s").setTextureName("hmgww2:rus/spawn_rus_s")
				.setCreativeTab(tabgvc);
		GameRegistry.registerItem(spawn_rus_s, "spawn_rus_s");
		spawn_rus_tank = new ItemSpwanEntity(62).setUnlocalizedName("spawn_rus_tank").setTextureName("hmgww2:rus/spawn_rus_tank")
				.setCreativeTab(tabgvc);
		GameRegistry.registerItem(spawn_rus_tank, "spawn_rus_tank");
		spawn_rus_fighter = new ItemSpwanEntity(63).setUnlocalizedName("spawn_rus_fighter").setTextureName("hmgww2:rus/spawn_rus_fighter")
				.setCreativeTab(tabgvc);
		GameRegistry.registerItem(spawn_rus_fighter, "spawn_rus_fighter");
		spawn_rus_tankaa = new ItemSpwanEntity(64).setUnlocalizedName("spawn_rus_tankaa").setTextureName("hmgww2:rus/spawn_rus_tankaa")
				.setCreativeTab(tabgvc);
		GameRegistry.registerItem(spawn_rus_tankaa, "spawn_rus_tankaa");
		spawn_rus_tankspg = new ItemSpwanEntity(65).setUnlocalizedName("spawn_rus_tankspg").setTextureName("hmgww2:rus/spawn_rus_tankspg")
				.setCreativeTab(tabgvc);
		GameRegistry.registerItem(spawn_rus_tankspg, "spawn_rus_tankspg");
		spawn_rus_attcker = new ItemSpwanEntity(66).setUnlocalizedName("spawn_rus_attcker").setTextureName("hmgww2:rus/spawn_rus_attcker")
				.setCreativeTab(tabgvc);
		GameRegistry.registerItem(spawn_rus_attcker, "spawn_rus_attcker");
		spawn_rus_tankh = new ItemSpwanEntity(67).setUnlocalizedName("spawn_rus_tankh").setTextureName("hmgww2:rus/spawn_rus_tankh")
				.setCreativeTab(tabgvc);
		GameRegistry.registerItem(spawn_rus_tankh, "spawn_rus_tankh");
		
		WW2PacketHandler.init();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent pEvent) {
		int D = Short.MAX_VALUE;
		
		EntityRegistry.registerModEntity(EntityJPN_S.class, "EntityJPN_S", 1, this, 128, 5, true);
		EntityRegistry.registerModEntity(EntityJPN_Tank.class, "EntityJPN_Tank", 2, this, 128, 5, true);
		EntityRegistry.registerModEntity(EntityJPN_Fighter.class, "EntityJPN_Fighter", 3, this, 128, 5, true);
		EntityRegistry.registerModEntity(EntityJPN_TankAA.class, "EntityJPN_TankAA", 4, this, 128, 5, true);
		EntityRegistry.registerModEntity(EntityJPN_TankSPG.class, "EntityJPN_TankSPG", 5, this, 128, 5, true);
		EntityRegistry.registerModEntity(EntityJPN_FighterA.class, "EntityJPN_FighterA", 6, this, 128, 5, true);
		EntityRegistry.registerModEntity(EntityJPN_ShipB.class, "EntityJPN_ShipB", 7, this, 128, 5, true);
		EntityRegistry.registerModEntity(EntityJPN_ShipD.class, "EntityJPN_ShipD", 8, this, 128, 5, true);
		
		
		EntityRegistry.registerModEntity(EntityUSA_S.class, "EntityUSA_S", 21, this, 128, 5, true);
		EntityRegistry.registerModEntity(EntityUSA_Tank.class, "EntityUSA_Tank", 22, this, 128, 5, true);
		EntityRegistry.registerModEntity(EntityUSA_Fighter.class, "EntityUSA_Fighter", 23, this, 128, 5, true);
		EntityRegistry.registerModEntity(EntityUSA_TankAA.class, "EntityUSA_TankAA", 24, this, 128, 5, true);
		EntityRegistry.registerModEntity(EntityUSA_TankSPG.class, "EntityUSA_TankSPG", 25, this, 128, 5, true);
		EntityRegistry.registerModEntity(EntityUSA_FighterA.class, "EntityUSA_FighterA", 26, this, 128, 5, true);
		EntityRegistry.registerModEntity(EntityUSA_ShipB.class, "EntityUSA_ShipB", 27, this, 128, 5, true);
		EntityRegistry.registerModEntity(EntityUSA_ShipD.class, "EntityUSA_ShipD", 28, this, 128, 5, true);
		
		EntityRegistry.registerModEntity(EntityGER_S.class, "EntityGER_S", 41, this, 128, 5, true);
		EntityRegistry.registerModEntity(EntityGER_Tank.class, "EntityGER_Tank", 42, this, 128, 5, true);
		EntityRegistry.registerModEntity(EntityGER_Fighter.class, "EntityGER_Fighter", 43, this, 128, 5, true);
		EntityRegistry.registerModEntity(EntityGER_TankAA.class, "EntityGER_TankAA", 44, this, 128, 5, true);
		EntityRegistry.registerModEntity(EntityGER_TankSPG.class, "EntityGER_TankSPG", 45, this, 128, 5, true);
		EntityRegistry.registerModEntity(EntityGER_FighterA.class, "EntityGER_FighterA", 46, this, 128, 5, true);
		EntityRegistry.registerModEntity(EntityGER_TankH.class, "EntityGER_TankH", 47, this, 128, 5, true);
		//EntityRegistry.registerModEntity(EntityUSA_ShipB.class, "EntityUSA_ShipB", 27, this, 128, 5, true);
		//EntityRegistry.registerModEntity(EntityUSA_ShipD.class, "EntityUSA_ShipD", 28, this, 128, 5, true);
		
		EntityRegistry.registerModEntity(EntityUSSR_S.class, "EntityUSSR_S", 61, this, 128, 5, true);
		EntityRegistry.registerModEntity(EntityUSSR_Tank.class, "EntityUSSR_Tank", 62, this, 128, 5, true);
		EntityRegistry.registerModEntity(EntityUSSR_Fighter.class, "EntityUSSR_Fighter", 63, this, 128, 5, true);
		EntityRegistry.registerModEntity(EntityUSSR_TankAA.class, "EntityUSSR_TankAA", 64, this, 128, 5, true);
		EntityRegistry.registerModEntity(EntityUSSR_TankSPG.class, "EntityUSSR_TankSPG", 65, this, 128, 5, true);
		EntityRegistry.registerModEntity(EntityUSSR_FighterA.class, "EntityUSSR_FighterA", 66, this, 128, 5, true);
		EntityRegistry.registerModEntity(EntityUSSR_TankH.class, "EntityUSSR_TankH", 67, this, 128, 5, true);
		
		
		if(pEvent.getSide().isClient())
		{
		MinecraftForge.EVENT_BUS.register(new EventOverlay());
		MinecraftForge.EVENT_BUS.register(new EventEntityBases());
		}
		MinecraftForge.EVENT_BUS.register(new EventEntityPlayer());
		
		RecipeRegistrys.recipe();
		FMLCommonHandler.instance().bus().register(this);
		proxy.reisterRenderers();
		proxy.registerTileEntity();
		proxy.InitRendering();
		proxy.jumped();
		proxy.leftclick();
		proxy.rightclick();
		proxy.xclick();
		proxy.getEntityPlayerInstance();
		
		if(cfg_canbuild){
			GameRegistry.registerWorldGenerator(new GenerateBase(), 10);
		}
		
		
		//MinecraftForge.EVENT_BUS.register(new GVCReloadEvents());
		//MinecraftForge.EVENT_BUS.register(new GVCEventsGunZoom());
		//MinecraftForge.EVENT_BUS.register(new GVCEventsGunRender());
		BiomeGenBase[] biomeList = null;
		biomeList = BiomeGenBase.getBiomeGenArray();
		for (BiomeGenBase biome : biomeList) {
			if(biome != null){
				if (this.cfg_canspawn) {
					if(this.cfg_spawn_jpn){
						EntityRegistry.addSpawn(EntityJPN_S.class, 20, 10, 20, EnumCreatureType.monster,new BiomeGenBase[] { biome });
					}
					if(this.cfg_spawn_usa){
						EntityRegistry.addSpawn(EntityUSA_S.class, 20, 10, 20, EnumCreatureType.monster,new BiomeGenBase[] { biome });
					}
					if(this.cfg_spawn_ger){
						EntityRegistry.addSpawn(EntityGER_S.class, 20, 10, 20, EnumCreatureType.monster,new BiomeGenBase[] { biome });
					}
					if(this.cfg_spawn_rus){
						EntityRegistry.addSpawn(EntityUSSR_S.class, 20, 10, 20, EnumCreatureType.monster,new BiomeGenBase[] { biome });
					}
					if (this.cfg_canspawntank) {
						if(this.cfg_spawn_jpn){
							EntityRegistry.addSpawn(EntityJPN_Tank.class, 3, 2, 3, EnumCreatureType.monster, new BiomeGenBase[]{biome});
							EntityRegistry.addSpawn(EntityJPN_TankAA.class, 1, 1, 1, EnumCreatureType.monster, new BiomeGenBase[]{biome});
						}
						if(this.cfg_spawn_usa){
							EntityRegistry.addSpawn(EntityUSA_Tank.class, 3, 2, 3, EnumCreatureType.monster, new BiomeGenBase[]{biome});
							EntityRegistry.addSpawn(EntityUSA_TankAA.class, 1, 1, 1, EnumCreatureType.monster, new BiomeGenBase[]{biome});
						}
						if(this.cfg_spawn_ger){
							EntityRegistry.addSpawn(EntityGER_Tank.class, 3, 2, 3, EnumCreatureType.monster, new BiomeGenBase[]{biome});
							EntityRegistry.addSpawn(EntityGER_TankAA.class, 1, 1, 1, EnumCreatureType.monster, new BiomeGenBase[]{biome});
							EntityRegistry.addSpawn(EntityGER_TankH.class, 1, 1, 1, EnumCreatureType.monster, new BiomeGenBase[]{biome});
						}
						if(this.cfg_spawn_rus){
							EntityRegistry.addSpawn(EntityUSSR_Tank.class, 3, 2, 3, EnumCreatureType.monster, new BiomeGenBase[]{biome});
							EntityRegistry.addSpawn(EntityUSSR_TankAA.class, 1, 1, 1, EnumCreatureType.monster, new BiomeGenBase[]{biome});
							EntityRegistry.addSpawn(EntityUSSR_TankH.class, 1, 1, 1, EnumCreatureType.monster, new BiomeGenBase[]{biome});
						}
					}
					if (this.cfg_canspawnfighter) {
						if(this.cfg_spawn_jpn){
							EntityRegistry.addSpawn(EntityJPN_Fighter.class, 2, 1, 2, EnumCreatureType.monster, new BiomeGenBase[]{biome});
							EntityRegistry.addSpawn(EntityJPN_FighterA.class, 1, 1, 1, EnumCreatureType.monster, new BiomeGenBase[]{biome});
						}
						if(this.cfg_spawn_usa){
							EntityRegistry.addSpawn(EntityUSA_Fighter.class, 2, 1, 2, EnumCreatureType.monster, new BiomeGenBase[]{biome});
							EntityRegistry.addSpawn(EntityUSA_FighterA.class, 1, 1, 1, EnumCreatureType.monster, new BiomeGenBase[]{biome});
						}
						if(this.cfg_spawn_ger){
							EntityRegistry.addSpawn(EntityGER_Fighter.class, 2, 1, 2, EnumCreatureType.monster, new BiomeGenBase[]{biome});
							EntityRegistry.addSpawn(EntityGER_FighterA.class, 1, 1, 1, EnumCreatureType.monster, new BiomeGenBase[]{biome});
						}
						if(this.cfg_spawn_rus){
							EntityRegistry.addSpawn(EntityUSSR_Fighter.class, 2, 1, 2, EnumCreatureType.monster, new BiomeGenBase[]{biome});
							EntityRegistry.addSpawn(EntityUSSR_FighterA.class, 1, 1, 1, EnumCreatureType.monster, new BiomeGenBase[]{biome});
						}
					}
				}
			}
		}
		
		/*if(FMLCommonHandler.instance().getSide() == Side.CLIENT)
        {
            this.bi_flag_jpn_id = RenderingRegistry.getNextAvailableRenderId();
 
            RenderingRegistry.registerBlockHandler(new RenderTileFlag());
 
        }*/
		
	}
	
	@SubscribeEvent
    public void KeyHandlingEvent(KeyInputEvent event) 
	{
		Minecraft minecraft = Minecraft.getMinecraft();
		Entity entity = Minecraft.getMinecraft().renderViewEntity;
		if(entity != null && entity instanceof EntityPlayer){
			EntityPlayer entityplayer = (EntityPlayer)entity;
			ItemStack itemstack = ((EntityPlayer)(entityplayer)).getCurrentEquippedItem();
			if (ClientProxyGVCWW2.Follow.getIsKeyPressed()) {
	        	{
	        		WW2PacketHandler.INSTANCE.sendToServer(new WW2MessageKeyPressed(3));
	        	}
	        }
			if (ClientProxyGVCWW2.Wait.getIsKeyPressed()) {
	        	{
	        		WW2PacketHandler.INSTANCE.sendToServer(new WW2MessageKeyPressed(4));
	        	}
	        }
			if (ClientProxyGVCWW2.Free.getIsKeyPressed()) {
	        	{
	        		WW2PacketHandler.INSTANCE.sendToServer(new WW2MessageKeyPressed(0));
	        	}
	        }
			if (ClientProxyGVCWW2.Flag.getIsKeyPressed()) {
	        	{
	        		WW2PacketHandler.INSTANCE.sendToServer(new WW2MessageKeyPressed(1));
	        	}
	        }
			
			if (ClientProxyGVCWW2.ShipFree.getIsKeyPressed()) {
	        	{
	        		WW2PacketHandler.INSTANCE.sendToServer(new WW2MessageKeyPressed(5));
	        	}
	        }
			if (ClientProxyGVCWW2.ShipWait.getIsKeyPressed()) {
	        	{
	        		WW2PacketHandler.INSTANCE.sendToServer(new WW2MessageKeyPressed(6));
	        	}
	        }
			if (ClientProxyGVCWW2.RidingShip.getIsKeyPressed()) {
	        	{
	        		WW2PacketHandler.INSTANCE.sendToServer(new WW2MessageKeyPressed(7));
	        	}
	        }
			
		}
	}
	
	
}

	
	
