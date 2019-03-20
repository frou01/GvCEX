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
import gvclib.item.ItemGrenadeBase;
import gvclib.item.ItemGrenade_N;
import gvclib.item.ItemGrenade_T;
import gvclib.item.ItemGunBase;
import gvclib.item.ItemGun_AR;
import gvclib.item.ItemGun_RL;
import gvclib.item.ItemGun_SR;
import gvclib.render.RenderItem_Grenade;
import gvclib.render.RenderItem_Gun;
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
import hmgww2.entity.EntityGER_Fighter;
import hmgww2.entity.EntityGER_FighterA;
import hmgww2.entity.EntityGER_S;
import hmgww2.entity.EntityGER_Tank;
import hmgww2.entity.EntityGER_TankAA;
import hmgww2.entity.EntityGER_TankH;
import hmgww2.entity.EntityGER_TankSPG;
import hmgww2.entity.EntityJPN_Fighter;
import hmgww2.entity.EntityJPN_FighterA;
import hmgww2.entity.EntityJPN_S;
import hmgww2.entity.EntityJPN_ShipB;
import hmgww2.entity.EntityJPN_ShipD;
import hmgww2.entity.EntityJPN_Tank;
import hmgww2.entity.EntityJPN_TankAA;
import hmgww2.entity.EntityJPN_TankSPG;
import hmgww2.entity.EntityRUS_Fighter;
import hmgww2.entity.EntityRUS_FighterA;
import hmgww2.entity.EntityRUS_S;
import hmgww2.entity.EntityRUS_Tank;
import hmgww2.entity.EntityRUS_TankAA;
import hmgww2.entity.EntityRUS_TankH;
import hmgww2.entity.EntityRUS_TankSPG;
import hmgww2.entity.EntityUSA_Fighter;
import hmgww2.entity.EntityUSA_FighterA;
import hmgww2.entity.EntityUSA_S;
import hmgww2.entity.EntityUSA_ShipB;
import hmgww2.entity.EntityUSA_ShipD;
import hmgww2.entity.EntityUSA_Tank;
import hmgww2.entity.EntityUSA_TankAA;
import hmgww2.entity.EntityUSA_TankSPG;
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
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.model.AdvancedModelLoader;
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
	public static Item gun_type99;
	public static Item gun_type100;
	public static Item gun_type89;
	public static Item gun_type14;
	
	public static Item gun_m1g;
	public static Item gun_m1t;
	public static Item gun_bar;
	public static Item gun_m1904;
	public static Item gun_m1b;
	public static Item gun_m1911;
	
	public static Item gun_grenade;
	public static Item gun_grenadet;
	
	public static Item gun_kar98;
	public static Item gun_mp40;
	public static Item gun_fg42;
	public static Item gun_kar98sr;
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
		armor_jpn	= new ItemIFFArmor(armor, 0, "hmgww2:textures/armor/armor_jpn.png").setUnlocalizedName("armor_jpn")
				.setTextureName("hmgww2:jpn/armor_jpn")
				.setCreativeTab(tabgvc);
	    GameRegistry.registerItem(armor_jpn, "armor_jpn");
	    armor_usa	= new ItemIFFArmor(armor, 0, "hmgww2:textures/armor/armor_usa.png").setUnlocalizedName("armor_usa")
				.setTextureName("hmgww2:usa/armor_usa")
				.setCreativeTab(tabgvc);
	    GameRegistry.registerItem(armor_usa, "armor_usa");
	    armor_ger	= new ItemIFFArmor(armor, 0, "hmgww2:textures/armor/armor_ger.png").setUnlocalizedName("armor_ger")
				.setTextureName("hmgww2:ger/armor_ger")
				.setCreativeTab(tabgvc);
	    GameRegistry.registerItem(armor_ger, "armor_ger");
	    armor_rus	= new ItemIFFArmor(armor, 0, "hmgww2:textures/armor/armor_rus.png").setUnlocalizedName("armor_rus")
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
		
		gun_type38 = new ItemGun_SR().setUnlocalizedName("gun_type38").setTextureName("hmgww2:jpn/gun_type38")
				.setCreativeTab(tabgvc);
		{
			ItemGunBase gun = (ItemGunBase)gun_type38;
			gun.setMaxDamage(7);
			gun.powor = 12;
			gun.speed = 4;
			gun.bure = 1.0F;
			gun.recoil = 1.0D;
			gun.reloadtime = 50;
			gun.cycle = 2;
			gun.attackDamage = 7;
			gun.bayonet = true;
			gun.sound = "hmgww2:hmgww2.fire_rifle";
			gun.soundre = "hmgww2:hmgww2.reload_mag";
			gun.soundspeed = 1.0F;
			gun.cocktime = 15;
			gun.soundcock = "hmgww2:hmgww2.reload_cocking";
			gun.magazine = this.b_magazine;
			gun.scopezoom = 1.5F;
			gun.texture = new ResourceLocation("hmgww2:textures/model/jpn/type38.png");
			gun.modelhigh = 1.25F;
			gun.armoffsetzl = -1.0F;
			if(pEvent.getSide().isClient())
			{
				MinecraftForgeClient.registerItemRenderer(gun_type38, 
						new RenderItem_Gun(AdvancedModelLoader.loadModel(new ResourceLocation("hmgww2:textures/model/jpn/type38.obj"))));
			}
		}
		GameRegistry.registerItem(gun_type38, "gun_type38");
		gun_type99lmg = new ItemGun_AR().setUnlocalizedName("gun_type99lmg").setTextureName("hmgww2:jpn/gun_type99lmg")
				.setCreativeTab(tabgvc);
		{
			ItemGunBase gun = (ItemGunBase)gun_type99lmg;
			gun.setMaxDamage(30);
			gun.powor = 7;
			gun.speed = 4;
			gun.bure = 2.0F;
			gun.recoil = 2.0D;
			gun.reloadtime = 70;
			gun.cycle = 4;
			gun.sound = "hmgww2:hmgww2.fire_rifle";
			gun.soundre = "hmgww2:hmgww2.reload_mag";
			gun.soundspeed = 1.0F;
			gun.magazine = this.b_magazinemg;
			gun.scopezoom = 1.5F;
			gun.texture = new ResourceLocation("hmgww2:textures/model/jpn/type99lmg.png");
			gun.modelwidthx = 0.502F;
			gun.modelhigh = 0.75F;
			
			gun.armoffsetxr = 0.4F;
			gun.armoffsetyr = 0.3F;
			gun.armoffsetzr = 0.5F;
			gun.armoffsetxl = 0.1F;
			gun.armoffsetyl = 0.2F;
			gun.armoffsetzl = -1.5F;
			if(pEvent.getSide().isClient())
			{
				MinecraftForgeClient.registerItemRenderer(gun_type99lmg, 
						new RenderItem_Gun(AdvancedModelLoader.loadModel(new ResourceLocation("hmgww2:textures/model/jpn/type99lmg.obj"))));
			}
		}
		GameRegistry.registerItem(gun_type99lmg, "gun_type99lmg");
		gun_type99 = new ItemGun_SR().setUnlocalizedName("gun_type99").setTextureName("hmgww2:jpn/gun_type99")
				.setCreativeTab(tabgvc);
		{
			ItemGunBase gun = (ItemGunBase)gun_type99;
			gun.setMaxDamage(5);
			gun.powor = 14;
			gun.speed = 4;
			gun.bure = 1.0F;
			gun.recoil = 1.0D;
			gun.reloadtime = 60;
			gun.cycle = 2;
			gun.attackDamage = 7;
			gun.bayonet = true;
			gun.sound = "hmgww2:hmgww2.fire_rifle";
			gun.soundre = "hmgww2:hmgww2.reload_mag";
			gun.soundspeed = 1.0F;
			gun.cocktime = 20;
			gun.soundcock = "hmgww2:hmgww2.reload_cocking";
			gun.magazine = this.b_magazine;
			gun.scopezoom = 4F;
			gun.zoomre = false;
			gun.texture = new ResourceLocation("hmgww2:textures/model/jpn/type99.png");
			gun.modelhigh = 1.25F;
			gun.armoffsetzl = -1.0F;
			if(pEvent.getSide().isClient())
			{
				MinecraftForgeClient.registerItemRenderer(gun_type99, 
						new RenderItem_Gun(AdvancedModelLoader.loadModel(new ResourceLocation("hmgww2:textures/model/jpn/type99.obj"))));
			}
		}
		GameRegistry.registerItem(gun_type99, "gun_type99");
		gun_type100 = new ItemGun_AR().setUnlocalizedName("gun_type100").setTextureName("hmgww2:jpn/gun_type100")
				.setCreativeTab(tabgvc);
		{
			ItemGunBase gun = (ItemGunBase)gun_type100;
			gun.setMaxDamage(32);
			gun.powor = 4;
			gun.speed = 4;
			gun.bure = 6.0F;
			gun.recoil = 1.5D;
			gun.reloadtime = 50;
			gun.cycle = 2;
			gun.attackDamage = 7;
			gun.bayonet = true;
			gun.sound = "hmgww2:hmgww2.fire_hg";
			gun.soundre = "hmgww2:hmgww2.reload_mag";
			gun.soundspeed = 1.0F;
			gun.magazine = this.b_magazinehg;
			gun.scopezoom = 1.5F;
			gun.texture = new ResourceLocation("hmgww2:textures/model/jpn/type100.png");
			gun.modelwidthz = 0.5F;
			gun.modelhigh = 1.25F;
			
			gun.armoffsetxr = 0.375F;
			gun.armoffsetyr = 0.4F;
			gun.armoffsetzr = 0.5F;
			if(pEvent.getSide().isClient())
			{
				MinecraftForgeClient.registerItemRenderer(gun_type100, 
						new RenderItem_Gun(AdvancedModelLoader.loadModel(new ResourceLocation("hmgww2:textures/model/jpn/type100.obj"))));
			}
		}
		GameRegistry.registerItem(gun_type100, "gun_type100");
		gun_type89 = new ItemGun_RL().setUnlocalizedName("gun_type89").setTextureName("hmgww2:jpn/gun_type89")
				.setCreativeTab(tabgvc);
		{
			ItemGunBase gun = (ItemGunBase)gun_type89;
			gun.setMaxDamage(1);
			gun.powor = 25;//50
			gun.speed = 1.2F;
			gun.bure = 1.0F;
			gun.recoil = 1.0D;
			gun.reloadtime = 40;
			gun.cycle = 2;
			gun.ex = 2F;
			gun.canex = mod_GVCWW2.cfg_blockdestory;
			gun.sound = "hmgww2:hmgww2.fire_grenade";
			gun.soundre = "hmgww2:hmgww2.reload_mag";
			gun.soundspeed = 1.0F;
			gun.cocktime = 40;
			gun.soundcock = "hmgww2:hmgww2.cocking";
			gun.magazine = this.b_magazinerpg;
			gun.scopezoom = 1.5F;
			gun.gra = 0F;
			gun.vecy = -30F;
			gun.texture = new ResourceLocation("hmgww2:textures/model/jpn/type89.png");
			gun.modelwidthx = 0.3F;
			gun.modelhigh = 0.5F;
			gun.armoffsetzl = -0.5F;
			if(pEvent.getSide().isClient())
			{
				MinecraftForgeClient.registerItemRenderer(gun_type89, 
						new RenderItem_Gun(AdvancedModelLoader.loadModel(new ResourceLocation("hmgww2:textures/model/jpn/type89.obj"))));
			}
		}
		GameRegistry.registerItem(gun_type89, "gun_type89");
		gun_type14 = new ItemGun_SR().setUnlocalizedName("gun_type14").setTextureName("hmgww2:jpn/gun_type14")
				.setCreativeTab(tabgvc);
		{
			ItemGunBase gun = (ItemGunBase)gun_type14;
			gun.setMaxDamage(7);
			gun.powor = 4;
			gun.speed = 2;
			gun.bure = 1.0F;
			gun.recoil = 1.0D;
			gun.reloadtime = 20;
			gun.cycle = 2;
			gun.sound = "hmgww2:hmgww2.fire_hg";
			gun.soundre = "hmgww2:hmgww2.reload_mag";
			gun.soundspeed = 1.0F;
			gun.cocktime = 4;
			gun.soundcock = "hmgww2:hmgww2.null";
			gun.magazine = this.b_magazinehg;
			gun.scopezoom = 1.25F;
			gun.texture = new ResourceLocation("hmgww2:textures/model/jpn/type14.png");
			gun.modelhigh = 1.27F;
			gun.modelwidthz = 1.0F;
			
			gun.armoffsetxr = 0.38F;
			gun.armoffsetyr = 0.3F;
			gun.armoffsetzr = 0.5F;
			gun.armoffsetzl = 10.2F;
			
			gun.jump = -10F;
			if(pEvent.getSide().isClient())
			{
				MinecraftForgeClient.registerItemRenderer(gun_type14, 
						new RenderItem_Gun(AdvancedModelLoader.loadModel(new ResourceLocation("hmgww2:textures/model/jpn/type14.obj"))));
			}
		}
		GameRegistry.registerItem(gun_type14, "gun_type14");
		
		
		
		gun_m1g = new ItemGun_SR().setUnlocalizedName("gun_m1g").setTextureName("hmgww2:usa/gun_m1g")
				.setCreativeTab(tabgvc);
		{
			ItemGunBase gun = (ItemGunBase)gun_m1g;
			gun.setMaxDamage(7);
			gun.powor = 14;
			gun.speed = 3;
			gun.bure = 2.0F;
			gun.recoil = 2.0D;
			gun.reloadtime = 50;
			gun.cycle = 2;
			gun.sound = "hmgww2:hmgww2.fire_rifle";
			gun.soundre = "hmgww2:hmgww2.reload_mag";
			gun.soundspeed = 1.0F;
			gun.cocktime = 4;
			gun.soundcock = "hmgww2:hmgww2.null";
			gun.magazine = this.b_magazine;
			gun.scopezoom = 1.5F;
			gun.texture = new ResourceLocation("hmgww2:textures/model/usa/M1G.png");
			gun.modelhigh = 1.20F;
			gun.armoffsetzl = -1.0F;
			if(pEvent.getSide().isClient())
			{
				MinecraftForgeClient.registerItemRenderer(gun_m1g, 
						new RenderItem_Gun(AdvancedModelLoader.loadModel(new ResourceLocation("hmgww2:textures/model/usa/M1G.obj"))));
			}
		}
		GameRegistry.registerItem(gun_m1g, "gun_m1g");
		
		gun_m1t = new ItemGun_AR().setUnlocalizedName("gun_m1t").setTextureName("hmgww2:usa/gun_m1t")
				.setCreativeTab(tabgvc);
		{
			ItemGunBase gun = (ItemGunBase)gun_m1t;
			gun.setMaxDamage(20);
			gun.powor = 5;
			gun.speed = 4;
			gun.bure = 7.0F;
			gun.recoil = 2.0D;
			gun.reloadtime = 50;
			gun.cycle = 2;
			gun.sound = "hmgww2:hmgww2.fire_hg";
			gun.soundre = "hmgww2:hmgww2.reload_mag";
			gun.soundspeed = 1.0F;
			gun.magazine = this.b_magazinehg;
			gun.scopezoom = 1.5F;
			gun.texture = new ResourceLocation("hmgww2:textures/model/usa/M1T.png");
			gun.modelwidthz = 1.0F;
			gun.modelhigh = 0.95F;
			
			gun.armoffsetxr = 0.4F;
			gun.armoffsetyr = 0.3F;
			gun.armoffsetzr = 0.5F;
			gun.armoffsetxl = 0.1F;
			gun.armoffsetyl = 0.10F;
			gun.armoffsetzl = -1.0F;
			if(pEvent.getSide().isClient())
			{
				MinecraftForgeClient.registerItemRenderer(gun_m1t, 
						new RenderItem_Gun(AdvancedModelLoader.loadModel(new ResourceLocation("hmgww2:textures/model/usa/M1T.obj"))));
			}
		}
		GameRegistry.registerItem(gun_m1t, "gun_m1t");
		gun_bar = new ItemGun_AR().setUnlocalizedName("gun_bar").setTextureName("hmgww2:usa/gun_bar")
				.setCreativeTab(tabgvc);
		{
			ItemGunBase gun = (ItemGunBase)gun_bar;
			gun.setMaxDamage(20);
			gun.powor = 7;
			gun.speed = 4;
			gun.bure = 3.0F;
			gun.recoil = 2.0D;
			gun.reloadtime = 60;
			gun.cycle = 2;
			gun.sound = "hmgww2:hmgww2.fire_rifle";
			gun.soundre = "hmgww2:hmgww2.reload_mag";
			gun.soundspeed = 1.0F;
			gun.magazine = this.b_magazinemg;
			gun.scopezoom = 1.5F;
			gun.texture = new ResourceLocation("hmgww2:textures/model/usa/BAR.png");
			//gun.modelwidthz = 1.0F;
			gun.modelhigh = 1.05F;
			
			gun.armoffsetxr = 0.5F;
			gun.armoffsetyr = 0.5F;
			gun.armoffsetzr = 0.5F;
			gun.armoffsetxl = 0.1F;
			gun.armoffsetyl = 0.3F;
			gun.armoffsetzl = -1.7F;
			if(pEvent.getSide().isClient())
			{
				MinecraftForgeClient.registerItemRenderer(gun_bar, 
						new RenderItem_Gun(AdvancedModelLoader.loadModel(new ResourceLocation("hmgww2:textures/model/usa/BAR.obj"))));
			}
		}
		GameRegistry.registerItem(gun_bar, "gun_bar");
		gun_m1904 = new ItemGun_SR().setUnlocalizedName("gun_m1904").setTextureName("hmgww2:usa/gun_m1904")
				.setCreativeTab(tabgvc);
		{
			ItemGunBase gun = (ItemGunBase)gun_m1904;
			gun.setMaxDamage(5);
			gun.powor = 14;
			gun.speed = 4;
			gun.bure = 1.0F;
			gun.recoil = 1.0D;
			gun.reloadtime = 60;
			gun.cycle = 2;
			gun.sound = "hmgww2:hmgww2.fire_rifle";
			gun.soundre = "hmgww2:hmgww2.reload_mag";
			gun.soundspeed = 1.0F;
			gun.cocktime = 20;
			gun.soundcock = "hmgww2:hmgww2.reload_cocking";
			gun.magazine = this.b_magazine;
			gun.scopezoom = 4F;
			gun.zoomre = false;
			gun.texture = new ResourceLocation("hmgww2:textures/model/usa/M1904.png");
			gun.modelhigh = 1.25F;
			gun.armoffsetzl = -1.0F;
			if(pEvent.getSide().isClient())
			{
				MinecraftForgeClient.registerItemRenderer(gun_m1904, 
						new RenderItem_Gun(AdvancedModelLoader.loadModel(new ResourceLocation("hmgww2:textures/model/usa/M1904.obj"))));
			}
		}
		GameRegistry.registerItem(gun_m1904, "gun_m1904");
		gun_m1b = new ItemGun_RL().setUnlocalizedName("gun_m1b").setTextureName("hmgww2:usa/gun_m1b")
				.setCreativeTab(tabgvc);
		{
			ItemGunBase gun = (ItemGunBase)gun_m1b;
			gun.setMaxDamage(1);
			gun.powor = 40;//50
			gun.speed = 1.5F;
			gun.bure = 1.0F;
			gun.recoil = 1.0D;
			gun.reloadtime = 80;
			gun.cycle = 2;
			gun.ex = 3F;
			gun.canex = mod_GVCWW2.cfg_blockdestory;
			gun.sound = "hmgww2:hmgww2.fire_roket";
			gun.soundre = "hmgww2:hmgww2.reload_mag";
			gun.soundspeed = 1.0F;
			gun.cocktime = 80;
			gun.soundcock = "hmgww2:hmgww2.reload_cocking";
			gun.magazine = this.b_magazinerpg;
			gun.scopezoom = 1.5F;
			gun.gra = 0.025F;
			gun.texture = new ResourceLocation("hmgww2:textures/model/usa/M1B.png");
			gun.modelwidthx = 0.42F;
			gun.modelhigh = 1.2F;
			gun.armoffsetyl = 0.5F;
			gun.armoffsetzl = -0.8F;
			if(pEvent.getSide().isClient())
			{
				MinecraftForgeClient.registerItemRenderer(gun_m1b, 
						new RenderItem_Gun(AdvancedModelLoader.loadModel(new ResourceLocation("hmgww2:textures/model/usa/M1B.obj"))));
			}
		}
		GameRegistry.registerItem(gun_m1b, "gun_m1b");
		gun_m1911 = new ItemGun_SR().setUnlocalizedName("gun_m1911").setTextureName("hmgww2:usa/gun_m1911")
				.setCreativeTab(tabgvc);
		{
			ItemGunBase gun = (ItemGunBase)gun_m1911;
			gun.setMaxDamage(7);
			gun.powor = 5;
			gun.speed = 2;
			gun.bure = 1.0F;
			gun.recoil = 2.0D;
			gun.reloadtime = 20;
			gun.cycle = 2;
			gun.sound = "hmgww2:hmgww2.fire_hg";
			gun.soundre = "hmgww2:hmgww2.reload_mag";
			gun.soundspeed = 0.9F;
			gun.cocktime = 4;
			gun.soundcock = "hmgww2:hmgww2.null";
			gun.magazine = this.b_magazinehg;
			gun.scopezoom = 1.25F;
			gun.texture = new ResourceLocation("hmgww2:textures/model/usa/M1911.png");
			gun.modelhigh = 1.27F;
			gun.modelwidthz = 1.0F;
			
			gun.armoffsetxr = 0.38F;
			gun.armoffsetyr = 0.3F;
			gun.armoffsetzr = 0.5F;
			gun.armoffsetzl = 10.2F;
			
			gun.jump = -20F;
			if(pEvent.getSide().isClient())
			{
				MinecraftForgeClient.registerItemRenderer(gun_m1911, 
						new RenderItem_Gun(AdvancedModelLoader.loadModel(new ResourceLocation("hmgww2:textures/model/usa/M1911.obj"))));
			}
		}
		GameRegistry.registerItem(gun_m1911, "gun_m1911");
		
		
		
		gun_grenade = new ItemGrenade_N().setUnlocalizedName("gun_grenade").setTextureName("hmgww2:gun_grenade")
				.setCreativeTab(tabgvc);
		{
			ItemGrenadeBase gun = (ItemGrenadeBase)gun_grenade;
			gun.damege = 20;
			gun.extime = 60;
			gun.exlevel = 3F;
			gun.speed = 0.8F;
			gun.sound = "hmgww2:hmgww2.throw_grenade";
			gun.texture = new ResourceLocation("hmgww2:textures/model/set/grenade.png");
			if(pEvent.getSide().isClient())
			{
				MinecraftForgeClient.registerItemRenderer(gun_grenade, 
						new RenderItem_Grenade(AdvancedModelLoader.loadModel(new ResourceLocation("hmgww2:textures/model/set/grenade.obj"))));
			}
		}
		GameRegistry.registerItem(gun_grenade, "gun_grenade");
		gun_grenadet = new ItemGrenade_T().setUnlocalizedName("gun_grenadet").setTextureName("hmgww2:gun_grenadet")
				.setCreativeTab(tabgvc);
		{
			ItemGrenadeBase gun = (ItemGrenadeBase)gun_grenadet;
			gun.damege = 50;
			gun.extime = 80;
			gun.exlevel = 4F;
			gun.speed = 1.2F;
			gun.extrue = mod_GVCWW2.cfg_blockdestory;
			gun.exinground = true;
			gun.sound = "hmgww2:hmgww2.throw_grenade";
			gun.texture = new ResourceLocation("hmgww2:textures/model/set/grenadet.png");
			if(pEvent.getSide().isClient())
			{
				MinecraftForgeClient.registerItemRenderer(gun_grenadet, 
						new RenderItem_Grenade(AdvancedModelLoader.loadModel(new ResourceLocation("hmgww2:textures/model/set/grenadet.obj"))));
			}
		}
		GameRegistry.registerItem(gun_grenadet, "gun_grenadet");
		
		
		gun_kar98 = new ItemGun_SR().setUnlocalizedName("gun_kar98").setTextureName("hmgww2:ger/gun_kar98")
				.setCreativeTab(tabgvc);
		{
			ItemGunBase gun = (ItemGunBase)gun_kar98;
			gun.setMaxDamage(5);
			gun.powor = 16;
			gun.speed = 4;
			gun.bure = 1.0F;
			gun.recoil = 1.0D;
			gun.reloadtime = 50;
			gun.cycle = 2;
			gun.sound = "hmgww2:hmgww2.fire_rifle";
			gun.soundre = "hmgww2:hmgww2.reload_mag";
			gun.soundspeed = 1.0F;
			gun.cocktime = 10;
			gun.soundcock = "hmgww2:hmgww2.reload_cocking";
			gun.magazine = this.b_magazine;
			gun.scopezoom = 1.5F;
			gun.texture = new ResourceLocation("hmgww2:textures/model/ger/Kar98.png");
			gun.modelhigh = 1.25F;
			gun.armoffsetzl = -1.0F;
			if(pEvent.getSide().isClient())
			{
				MinecraftForgeClient.registerItemRenderer(gun_kar98, 
						new RenderItem_Gun(AdvancedModelLoader.loadModel(new ResourceLocation("hmgww2:textures/model/ger/Kar98.obj"))));
			}
		}
		GameRegistry.registerItem(gun_kar98, "gun_kar98");
		gun_mp40 = new ItemGun_AR().setUnlocalizedName("gun_mp40").setTextureName("hmgww2:ger/gun_mp40")
				.setCreativeTab(tabgvc);
		{
			ItemGunBase gun = (ItemGunBase)gun_mp40;
			gun.setMaxDamage(32);
			gun.powor = 4;
			gun.speed = 4;
			gun.bure = 10.0F;
			gun.recoil = 2.0D;
			gun.reloadtime = 50;
			gun.cycle = 2;
			gun.sound = "hmgww2:hmgww2.fire_hg";
			gun.soundre = "hmgww2:hmgww2.reload_mag";
			gun.soundspeed = 1.0F;
			gun.magazine = this.b_magazinehg;
			gun.scopezoom = 1.5F;
			gun.texture = new ResourceLocation("hmgww2:textures/model/ger/MP40.png");
			gun.modelwidthz = 1.0F;
			gun.modelhigh = 0.95F;
			
			gun.armoffsetxr = 0.4F;
			gun.armoffsetyr = 0.3F;
			gun.armoffsetzr = 0.5F;
			gun.armoffsetxl = 0.1F;
			gun.armoffsetyl = 0.30F;
			gun.armoffsetzl = -0.7F;
			if(pEvent.getSide().isClient())
			{
				MinecraftForgeClient.registerItemRenderer(gun_mp40, 
						new RenderItem_Gun(AdvancedModelLoader.loadModel(new ResourceLocation("hmgww2:textures/model/ger/MP40.obj"))));
			}
		}
		GameRegistry.registerItem(gun_mp40, "gun_mp40");
		gun_fg42 = new ItemGun_AR().setUnlocalizedName("gun_fg42").setTextureName("hmgww2:ger/gun_fg42")
				.setCreativeTab(tabgvc);
		{
			ItemGunBase gun = (ItemGunBase)gun_fg42;
			gun.setMaxDamage(20);
			gun.powor = 7;
			gun.speed = 4;
			gun.bure = 3.0F;
			gun.recoil = 2.0D;
			gun.reloadtime = 60;
			gun.cycle = 4;
			gun.sound = "hmgww2:hmgww2.fire_rifle";
			gun.soundre = "hmgww2:hmgww2.reload_mag";
			gun.soundspeed = 1.0F;
			gun.magazine = this.b_magazinemg;
			gun.scopezoom = 1.5F;
			gun.texture = new ResourceLocation("hmgww2:textures/model/ger/FG42.png");
			//gun.modelwidthz = 1.0F;
			gun.modelhigh = 0.8F;
			
			gun.armoffsetxr = 0.5F;
			gun.armoffsetyr = 0.5F;
			gun.armoffsetzr = 0.5F;
			gun.armoffsetxl = 0.1F;
			gun.armoffsetyl = 0.0F;
			gun.armoffsetzl = -1.0F;
			if(pEvent.getSide().isClient())
			{
				MinecraftForgeClient.registerItemRenderer(gun_fg42, 
						new RenderItem_Gun(AdvancedModelLoader.loadModel(new ResourceLocation("hmgww2:textures/model/ger/FG42.obj"))));
			}
		}
		GameRegistry.registerItem(gun_fg42, "gun_fg42");
		gun_kar98sr = new ItemGun_SR().setUnlocalizedName("gun_kar98sr").setTextureName("hmgww2:ger/gun_kar98sr")
				.setCreativeTab(tabgvc);
		{
			ItemGunBase gun = (ItemGunBase)gun_kar98sr;
			gun.setMaxDamage(5);
			gun.powor = 16;
			gun.speed = 4;
			gun.bure = 1.0F;
			gun.recoil = 1.0D;
			gun.reloadtime = 60;
			gun.cycle = 2;
			gun.sound = "hmgww2:hmgww2.fire_rifle";
			gun.soundre = "hmgww2:hmgww2.reload_mag";
			gun.soundspeed = 1.0F;
			gun.cocktime = 20;
			gun.soundcock = "hmgww2:hmgww2.reload_cocking";
			gun.magazine = this.b_magazine;
			gun.scopezoom = 4F;
			gun.zoomre = false;
			gun.texture = new ResourceLocation("hmgww2:textures/model/ger/Kar98SR.png");
			gun.modelhigh = 1.25F;
			gun.armoffsetzl = -1.0F;
			if(pEvent.getSide().isClient())
			{
				MinecraftForgeClient.registerItemRenderer(gun_kar98sr, 
						new RenderItem_Gun(AdvancedModelLoader.loadModel(new ResourceLocation("hmgww2:textures/model/ger/Kar98SR.obj"))));
			}
		}
		GameRegistry.registerItem(gun_kar98sr, "gun_kar98sr");
		gun_rpzb54 = new ItemGun_RL().setUnlocalizedName("gun_rpzb54").setTextureName("hmgww2:ger/gun_rpzb54")
				.setCreativeTab(tabgvc);
		{
			ItemGunBase gun = (ItemGunBase)gun_rpzb54;
			gun.setMaxDamage(1);
			gun.powor = 60;//50
			gun.speed = 1.5F;
			gun.bure = 1.0F;
			gun.recoil = 1.0D;
			gun.reloadtime = 100;
			gun.cycle = 2;
			gun.ex = 3F;
			gun.canex = mod_GVCWW2.cfg_blockdestory;
			gun.sound = "hmgww2:hmgww2.fire_roket";
			gun.soundre = "hmgww2:hmgww2.reload_mag";
			gun.soundspeed = 1.0F;
			gun.cocktime = 80;
			gun.soundcock = "hmgww2:hmgww2.reload_cocking";
			gun.magazine = this.b_magazinerpg;
			gun.scopezoom = 1.5F;
			gun.gra = 0.01F;
			gun.texture = new ResourceLocation("hmgww2:textures/model/ger/RPzB54.png");
			gun.modelwidthx = 0.42F;
			gun.modelhigh = 0.9F;
			gun.armoffsetyl = 0.5F;
			gun.armoffsetzl = -0.8F;
			if(pEvent.getSide().isClient())
			{
				MinecraftForgeClient.registerItemRenderer(gun_rpzb54, 
						new RenderItem_Gun(AdvancedModelLoader.loadModel(new ResourceLocation("hmgww2:textures/model/ger/RPzB54.obj"))));
			}
		}
		GameRegistry.registerItem(gun_rpzb54, "gun_rpzb54");
		gun_p38 = new ItemGun_SR().setUnlocalizedName("gun_p38").setTextureName("hmgww2:ger/gun_p38")
				.setCreativeTab(tabgvc);
		{
			ItemGunBase gun = (ItemGunBase)gun_p38;
			gun.setMaxDamage(7);
			gun.powor = 4;
			gun.speed = 2;
			gun.bure = 1.0F;
			gun.recoil = 2.0D;
			gun.reloadtime = 20;
			gun.cycle = 2;
			gun.sound = "hmgww2:hmgww2.fire_hg";
			gun.soundre = "hmgww2:hmgww2.reload_mag";
			gun.soundspeed = 1.0F;
			gun.cocktime = 4;
			gun.soundcock = "hmgww2:hmgww2.null";
			gun.magazine = this.b_magazinehg;
			gun.scopezoom = 1.25F;
			gun.texture = new ResourceLocation("hmgww2:textures/model/ger/P38.png");
			gun.modelhigh = 1.27F;
			gun.modelwidthz = 1.0F;
			
			gun.armoffsetxr = 0.38F;
			gun.armoffsetyr = 0.3F;
			gun.armoffsetzr = 0.5F;
			gun.armoffsetzl = 10.2F;
			
			gun.jump = -10F;
			if(pEvent.getSide().isClient())
			{
				MinecraftForgeClient.registerItemRenderer(gun_p38, 
						new RenderItem_Gun(AdvancedModelLoader.loadModel(new ResourceLocation("hmgww2:textures/model/ger/P38.obj"))));
			}
		}
		GameRegistry.registerItem(gun_p38, "gun_p38");
		
		
		gun_m1891 = new ItemGun_SR().setUnlocalizedName("gun_m1891").setTextureName("hmgww2:rus/gun_m1891")
				.setCreativeTab(tabgvc);
		{
			ItemGunBase gun = (ItemGunBase)gun_m1891;
			gun.setMaxDamage(5);
			gun.powor = 16;
			gun.speed = 4;
			gun.bure = 1.0F;
			gun.recoil = 1.0D;
			gun.reloadtime = 50;
			gun.cycle = 2;
			gun.sound = "hmgww2:hmgww2.fire_rifle";
			gun.soundre = "hmgww2:hmgww2.reload_mag";
			gun.soundspeed = 1.0F;
			gun.cocktime = 10;
			gun.soundcock = "hmgww2:hmgww2.reload_cocking";
			gun.magazine = this.b_magazine;
			gun.scopezoom = 1.5F;
			gun.texture = new ResourceLocation("hmgww2:textures/model/rus/M1891.png");
			gun.modelhigh = 1.25F;
			gun.armoffsetzl = -1.0F;
			if(pEvent.getSide().isClient())
			{
				MinecraftForgeClient.registerItemRenderer(gun_m1891, 
						new RenderItem_Gun(AdvancedModelLoader.loadModel(new ResourceLocation("hmgww2:textures/model/rus/M1891.obj"))));
			}
		}
		GameRegistry.registerItem(gun_m1891, "gun_m1891");
		gun_ppsh41 = new ItemGun_AR().setUnlocalizedName("gun_ppsh41").setTextureName("hmgww2:rus/gun_ppsh41")
				.setCreativeTab(tabgvc);
		{
			ItemGunBase gun = (ItemGunBase)gun_ppsh41;
			gun.setMaxDamage(71);
			gun.powor = 4;
			gun.speed = 4;
			gun.bure = 6.0F;
			gun.recoil = 3.5D;
			gun.reloadtime = 60;
			gun.cycle = 2;
			gun.sound = "hmgww2:hmgww2.fire_hg";
			gun.soundre = "hmgww2:hmgww2.reload_mag";
			gun.soundspeed = 1.0F;
			gun.magazine = this.b_magazinehg;
			gun.scopezoom = 1.5F;
			gun.texture = new ResourceLocation("hmgww2:textures/model/rus/PPSh41.png");
			//gun.modelwidthz = 0.5F;
			gun.modelhigh = 1.25F;
			
			gun.armoffsetxr = 0.375F;
			gun.armoffsetyr = 0.4F;
			gun.armoffsetzr = 0.5F;
			gun.armoffsetxl = 0.1F;
			gun.armoffsetyl = 1.0F;
			gun.armoffsetzl = -1.0F;
			if(pEvent.getSide().isClient())
			{
				MinecraftForgeClient.registerItemRenderer(gun_ppsh41, 
						new RenderItem_Gun(AdvancedModelLoader.loadModel(new ResourceLocation("hmgww2:textures/model/rus/PPSh41.obj"))));
			}
		}
		GameRegistry.registerItem(gun_ppsh41, "gun_ppsh41");
		gun_dp28 = new ItemGun_AR().setUnlocalizedName("gun_dp28").setTextureName("hmgww2:rus/gun_dp28")
				.setCreativeTab(tabgvc);
		{
			ItemGunBase gun = (ItemGunBase)gun_dp28;
			gun.setMaxDamage(47);
			gun.powor = 6;
			gun.speed = 4;
			gun.bure = 3.0F;
			gun.recoil = 2.0D;
			gun.reloadtime = 60;
			gun.cycle = 4;
			gun.sound = "hmgww2:hmgww2.fire_rifle";
			gun.soundre = "hmgww2:hmgww2.reload_mag";
			gun.soundspeed = 1.0F;
			gun.magazine = this.b_magazinemg;
			gun.scopezoom = 1.5F;
			gun.texture = new ResourceLocation("hmgww2:textures/model/rus/DP28.png");
			//gun.modelwidthz = 1.0F;
			gun.modelhigh = 1.1F;
			
			gun.armoffsetxr = 0.375F;
			gun.armoffsetyr = 0.4F;
			gun.armoffsetzr = 0.5F;
			gun.armoffsetxl = 0.1F;
			gun.armoffsetyl = 0.5F;
			gun.armoffsetzl = -1.5F;
			if(pEvent.getSide().isClient())
			{
				MinecraftForgeClient.registerItemRenderer(gun_dp28, 
						new RenderItem_Gun(AdvancedModelLoader.loadModel(new ResourceLocation("hmgww2:textures/model/rus/DP28.obj"))));
			}
		}
		GameRegistry.registerItem(gun_dp28, "gun_dp28");
		gun_m1891sr = new ItemGun_SR().setUnlocalizedName("gun_m1891sr").setTextureName("hmgww2:rus/gun_m1891sr")
				.setCreativeTab(tabgvc);
		{
			ItemGunBase gun = (ItemGunBase)gun_m1891sr;
			gun.setMaxDamage(5);
			gun.powor = 16;
			gun.speed = 4;
			gun.bure = 1.0F;
			gun.recoil = 1.0D;
			gun.reloadtime = 60;
			gun.cycle = 2;
			gun.sound = "hmgww2:hmgww2.fire_rifle";
			gun.soundre = "hmgww2:hmgww2.reload_mag";
			gun.soundspeed = 1.0F;
			gun.cocktime = 20;
			gun.soundcock = "hmgww2:hmgww2.reload_cocking";
			gun.magazine = this.b_magazine;
			gun.scopezoom = 4F;
			gun.zoomre = false;
			gun.texture = new ResourceLocation("hmgww2:textures/model/rus/M1891SR.png");
			gun.modelhigh = 1.25F;
			gun.armoffsetzl = -1.0F;
			if(pEvent.getSide().isClient())
			{
				MinecraftForgeClient.registerItemRenderer(gun_m1891sr, 
						new RenderItem_Gun(AdvancedModelLoader.loadModel(new ResourceLocation("hmgww2:textures/model/rus/M1891SR.obj"))));
			}
		}
		GameRegistry.registerItem(gun_m1891sr, "gun_m1891sr");
		gun_tt33 = new ItemGun_SR().setUnlocalizedName("gun_tt33").setTextureName("hmgww2:rus/gun_tt33")
				.setCreativeTab(tabgvc);
		{
			ItemGunBase gun = (ItemGunBase)gun_tt33;
			gun.setMaxDamage(7);
			gun.powor = 4;
			gun.speed = 2;
			gun.bure = 1.0F;
			gun.recoil = 2.0D;
			gun.reloadtime = 20;
			gun.cycle = 2;
			gun.sound = "hmgww2:hmgww2.fire_hg";
			gun.soundre = "hmgww2:hmgww2.reload_mag";
			gun.soundspeed = 1.0F;
			gun.cocktime = 4;
			gun.soundcock = "hmgww2:hmgww2.null";
			gun.magazine = this.b_magazinehg;
			gun.scopezoom = 1.25F;
			gun.texture = new ResourceLocation("hmgww2:textures/model/rus/TT33.png");
			gun.modelhigh = 1.27F;
			gun.modelwidthz = 1.0F;
			
			gun.armoffsetxr = 0.38F;
			gun.armoffsetyr = 0.3F;
			gun.armoffsetzr = 0.5F;
			gun.armoffsetzl = 10.2F;
			
			gun.jump = -10F;
			if(pEvent.getSide().isClient())
			{
				MinecraftForgeClient.registerItemRenderer(gun_tt33, 
						new RenderItem_Gun(AdvancedModelLoader.loadModel(new ResourceLocation("hmgww2:textures/model/rus/TT33.obj"))));
			}
		}
		GameRegistry.registerItem(gun_tt33, "gun_tt33");
		
		
		
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
		
		EntityRegistry.registerModEntity(EntityRUS_S.class, "EntityRUS_S", 61, this, 128, 5, true);
		EntityRegistry.registerModEntity(EntityRUS_Tank.class, "EntityRUS_Tank", 62, this, 128, 5, true);
		EntityRegistry.registerModEntity(EntityRUS_Fighter.class, "EntityRUS_Fighter", 63, this, 128, 5, true);
		EntityRegistry.registerModEntity(EntityRUS_TankAA.class, "EntityRUS_TankAA", 64, this, 128, 5, true);
		EntityRegistry.registerModEntity(EntityRUS_TankSPG.class, "EntityRUS_TankSPG", 65, this, 128, 5, true);
		EntityRegistry.registerModEntity(EntityRUS_FighterA.class, "EntityRUS_FighterA", 66, this, 128, 5, true);
		EntityRegistry.registerModEntity(EntityRUS_TankH.class, "EntityRUS_TankH", 67, this, 128, 5, true);
		
		
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
						EntityRegistry.addSpawn(EntityRUS_S.class, 20, 10, 20, EnumCreatureType.monster,new BiomeGenBase[] { biome });
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
							EntityRegistry.addSpawn(EntityRUS_Tank.class, 3, 2, 3, EnumCreatureType.monster, new BiomeGenBase[]{biome});
							EntityRegistry.addSpawn(EntityRUS_TankAA.class, 1, 1, 1, EnumCreatureType.monster, new BiomeGenBase[]{biome});
							EntityRegistry.addSpawn(EntityRUS_TankH.class, 1, 1, 1, EnumCreatureType.monster, new BiomeGenBase[]{biome});
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
							EntityRegistry.addSpawn(EntityRUS_Fighter.class, 2, 1, 2, EnumCreatureType.monster, new BiomeGenBase[]{biome});
							EntityRegistry.addSpawn(EntityRUS_FighterA.class, 1, 1, 1, EnumCreatureType.monster, new BiomeGenBase[]{biome});
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

	
	
