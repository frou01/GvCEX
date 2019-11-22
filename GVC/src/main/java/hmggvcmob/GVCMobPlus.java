package hmggvcmob;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import handmadeguns.HMGGunMaker;
import handmadeguns.items.guns.HMGItem_Unified_Guns;
import hmggvcmob.camp.CampObj;
import hmggvcmob.item.*;
import hmggvcmob.item.GVCItemPMCEgg;
import hmggvcmob.block.GVCBlockFlag;
import hmggvcmob.block.GVCBlockGuerrillaCamp;
import hmggvcmob.block.GVCBlockGuerrillaCamp2;
import hmggvcmob.block.GVCBlockMobSpawnerExtended;
import hmggvcmob.block.GVCXBlockFriendCamp;
import hmggvcmob.entity.*;
import hmggvcmob.entity.friend.*;
import hmggvcmob.entity.guerrilla.*;
import hmggvcmob.event.GVCMRenderSomeEvent;
import hmggvcmob.event.GVCMSpawnEvent;
import hmggvcmob.event.GVCMXEntityEvent;
import hmggvcmob.network.GVCMPacketHandler;
import hmggvcmob.proxy.CommonSideProxyGVCM;
import hmggvcmob.tab.GVMTab;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hmggvcmob.util.SpotObj;
import hmggvcmob.world.WWorld;
import hmggvcutil.GVCUtils;
import handmadevehicle.HMVehicle;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

import static hmggvcmob.GVCMobPlus.modid;

@Mod(modid=modid, name=modid, version="HD11", dependencies="required-after:GVCGuns;required-after:HMVehicle")
public class GVCMobPlus
{
    public static final String modid = "GVCMob";
    @SidedProxy(clientSide="hmggvcmob.proxy.ClientProxyGVCM", serverSide="hmggvcmob.proxy.CommonSideProxyGVCM")
    public static CommonSideProxyGVCM proxy;
    public static boolean isDebugMessage = true;
    public static boolean cfg_setCamp = true;
    public static boolean cfg_guerrillacanusePlacedGun = true;
    public static boolean cfg_mob_setCamp = true;
    @Mod.Instance(modid)
    public static GVCMobPlus INSTANCE;
    public static boolean cfg_modeGorC = true;
    public static final CreativeTabs tabgvcm = new GVMTab("GVCmobs");
    public static int cfg_creatCamp;
    public static double cfg_guerrillasrach;
    public static double cfg_guerrillaspawngroup;
    public static int cfg_guerrillaspawnnomal;
    public static int cfg_guerrillaspawntank;
    public static boolean cfg_guerrillaspawndrawn;
    public static float cfg_soldierspawnnormal;
    public static float cfg_soldierspawntank;
    public static int cfg_flagspawnlevel;
    public static int cfg_flagspawninterval;
    public static boolean cfg_canspawnguerrilla;
    public static boolean cfg_canspawnsolider;
    public static boolean cfg_cansetIED;
    public static boolean cfg_blockdestory;
    public static boolean cfg_modelobj;
    public static Map<String, Integer> ignoreSoTargetEntity = new HashMap();
    public static Map<String, CampObj> campsHash = new HashMap();
    public static boolean cfg_throwngrenade;
    public static boolean cfg_canspawnhell;
    public static boolean cfg_canspawnsky;
    public static boolean cfg_canEjectCartridge;
    public static Block fn_Gcamp;
    public static Block fn_Gcamp2;
    public static Block fn_Gcamp3;
    public static Item fn_guerrillaegg;
    public static Item fn_guerrillabegg;
    public static Item fn_guerrillaspegg;
    public static Item fn_guerrillarpgegg;
    public static Item fn_guerrillasgegg;
    public static Item fn_guerrillamgegg;
    public static Item fn_guerrillaattackheliegg;
    public static Item fn_guerrillamegg;
    public static Item fn_gkegg;
    public static Item fn_tankegg;
    public static Item fn_apcegg;
    public static Item fn_heliegg;
    public static Item fn_jeepegg;
    public static Item fn_aagegg;
    public static Item fn_t90egg;
    public static Item fn_drawnegg;
    public static Item fn_flameregg;
    public static Item fn_skeletonegg;
    public static Item fn_endegg;
    public static Item arenaUnit;
    public static Item fn_mobspawner_egg;
    public static Item fn_mobspawner2_egg;
    public static Item fn_flagegg;
    public static Item fn_aaegg;
    public static Item fn_pmcegg;
    public static Item fn_pmcspegg;
    public static Item fn_pmcmgegg;
    public static Item fn_pmcrpgegg;
    public static Item fn_pmctankegg;
    public static Item fn_pmcheliegg;
    public static Item fn_pmcGKegg;
    public static Item fn_pmctankT90;
    public static Item fn_MGM;
    public static Item fn_plane;
    public static Item fn_pmcBMPEgg;
    public static Item fn_soldieregg;
    public static Item fn_soldierspegg;
    public static Item fn_soldiermgegg;
    public static Item fn_soldierrpgegg;
    public static Item fn_soldiertankegg;
    public static Item fn_soldierheliegg;
    public static Item fn_soldierbmpegg;
    public static Block fn_mobspawner;
    public static GVCBlockMobSpawnerExtended GKspawner;
    public static GVCBlockMobSpawnerExtended tankspawner;
    public static GVCBlockMobSpawnerExtended APCspawner;
    public static GVCBlockFlag fn_playerFlag;
    public static CampObj boxes;
    public static GVCBlockFlag fn_SoldierFlag;
    public static CampObj soldiers;
    public static GVCBlockFlag fn_Supplyflag;
    public static Block fn_Guerrillaflag;
    public static CampObj guerrillas;
    public static int bulletbase = 61;
    public static Item gvcmx_reqsupport_arty;
    public static Item gvcmx_reqsupport_heli;
    public static Item defsetter;
    public static List Guns_AR = new ArrayList();
    public static List Guns_SG = new ArrayList();
    public static List Guns_SR = new ArrayList();
    public static List Guns_LMG = new ArrayList();
    public static List Guns_RR = new ArrayList();
    public static List Guns_FL = new ArrayList();
    public static List Guns_HVG = new ArrayList();
    public static List Guns_BoltR = new ArrayList();
    public static List Guns_SWORD = new ArrayList();
    protected static File configFile;

    public static ArrayList<SpotObj> spotObjs = new ArrayList<SpotObj>();
    
    public static Achievement No_place_to_HIDE;
    public static Achievement killedGuerrilla;
    public static Achievement Union_Army;
    public static Achievement Old_soldiers_never_fade;
    public static Achievement power_of_number;
    public static Achievement unmanned_Craft;
    public static Achievement war_has_changed;
    public static Achievement unending_war;
    public static Achievement METAL_GEAR;
    public static Achievement spear_the_gungnir;
    public static Achievement Gun_of_the_Lost_Country;
    public static Achievement beacon_defensive;
    public static Achievement supply;
    
    public static String id_of_No_place_to_HIDE =           "achievement.No_place_to_HIDE";
    public static String id_of_killedGuerrilla =            "achievement.killedGuerrilla";
    public static String id_of_union =                      "achievement.union";
    public static String id_of_Old_soldiers_never_fade =    "achievement.Old_soldiers_never_fade";
    public static String id_of_power_of_number =            "achievement.power_of_number";
    public static String id_of_unmanned_Craft =             "achievement.unmanned_Craft";
    public static String id_of_war_has_changed =            "achievement.war_has_changed";
    public static String id_of_unending_war =               "achievement.unending_war";
    public static String id_of_METAL_GEAR =                 "achievement.METAL_GEAR";
    public static String id_of_spear_the_gungnir =          "achievement.spear_the_gungnir";
    public static String id_of_Gun_of_the_Lost_Country =    "achievement.Gun_of_the_Lost_Country";
    public static String id_of_beacon_defensive =           "achievement.beacon_defensive";

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent pEvent)
    {
    
        
        configFile = pEvent.getSuggestedConfigurationFile();
        Configuration lconf = new Configuration(configFile);
        lconf.load();
        cfg_setCamp = lconf.get("world", "cfg_setCamp", true).getBoolean(true);
        cfg_mob_setCamp = lconf.get("world", "cfg_mob_setCamp", true).getBoolean(true);
        cfg_creatCamp = lconf.get("world", "cfg_creatCamp", 160).getInt(160);
        cfg_modeGorC = lconf.get("render", "cfg_ModeGorC", true).getBoolean(true);
        cfg_guerrillasrach = lconf.get("Guerrilla", "cfg_GuerrillaMobSrach", 60).getDouble(60.0D);
        cfg_guerrillaspawnnomal = lconf.get("Guerrilla", "cfg_GuerrillaSpawnNomal", 5).getInt(5);
        cfg_guerrillaspawntank = lconf.get("Guerrilla", "cfg_GuerrillaSpawntank", 2).getInt(2);
        cfg_guerrillaspawndrawn = lconf.get("Guerrilla", "cfg_CanSpawndrawn", true).getBoolean(true);
        cfg_guerrillacanusePlacedGun = lconf.get("Guerrilla", "cfg_CanusePlacedGun", true).getBoolean(true);

        cfg_soldierspawnnormal = (float)lconf.get("Soldier", "cfg_SoldierSpawnNormal", 2).getDouble(2.0D);
        cfg_soldierspawntank = (float)lconf.get("Soldier", "cfg_SoldierSpawntank", 1).getDouble(1.0D);
        String ignoretgtmobs = lconf.get("Soldier", "ignoreSoldierTargetEntityClass", "").getString();
        String[] ignoretgtmob = ignoretgtmobs.split(",");
        for (int i = 0; i < ignoretgtmob.length; i++) {
            ignoreSoTargetEntity.put(ignoretgtmob[i], Integer.valueOf(i));
        }
        {

            String[] types = lconf.get("Vehicle", "cfg_Soldier_VehicleType",new String[]{"50","KPZ-70:10"}).getStringList();
            GVCEntitySoldierRPG.vehicleSpawnGachaOBJ = new VehicleSpawnGachaOBJ[types.length];
            int cnt = 0;
            for(String atype:types){
                GVCEntitySoldierRPG.vehicleSpawnGachaOBJ[cnt] = new VehicleSpawnGachaOBJ(atype);
                GVCEntitySoldierRPG.vehilceGacha_rate_sum+=GVCEntitySoldierRPG.vehicleSpawnGachaOBJ[cnt].rate;
                cnt++;
            }
        }
        {
            String[] types = lconf.get("Vehicle", "cfg_Guerrilla_VehicleType",new String[]{"50","T-34-85_mod:10"}).getStringList();
            GVCEntityGuerrillaRPG.vehicleSpawnGachaOBJ = new VehicleSpawnGachaOBJ[types.length];
            int cnt = 0;
            for(String atype:types){
                GVCEntityGuerrillaRPG.vehicleSpawnGachaOBJ[cnt] = new VehicleSpawnGachaOBJ(atype);
                GVCEntityGuerrillaRPG.vehilceGacha_rate_sum+=GVCEntityGuerrillaRPG.vehicleSpawnGachaOBJ[cnt].rate;
                cnt++;
            }
        }
        cfg_flagspawnlevel = lconf.get("Guerrilla", "cfg_FlagSpawnLevel", 180).getInt(180);
        cfg_flagspawninterval = lconf.get("Guerrilla", "cfg_Flagspawninterval", 2400).getInt(2400);
        HMVehicle.cfgVehicleWheel_UpRange = lconf.get("Vehicle", "cfgVehicleWheel_UpRange", 1).getDouble(1);
        HMVehicle.cfgVehicleWheel_DownRange = lconf.get("Vehicle", "cfgVehicleWheel_DownRange", 2).getDouble(2);
        cfg_cansetIED = lconf.get("world", "cfg_CansetIED", true).getBoolean(true);
        cfg_blockdestory = lconf.get("world", "cfg_BlockDestory", true).getBoolean(true);
        cfg_canEjectCartridge = lconf.get("Guerrilla", "cfg_canEjectCartridge", true).getBoolean(true);

        cfg_throwngrenade = lconf.get("Guerrilla", "cfg_throwngrenade", true).getBoolean(true);

        cfg_canspawnguerrilla = lconf.get("CanSpawn", "cfg_CanSpawnGuerrilla", true).getBoolean(true);
        cfg_canspawnsolider = lconf.get("CanSpawn", "cfg_CanSpawnSolider", true).getBoolean(true);
        cfg_canspawnhell = lconf.get("CanSpawn", "cfg_canspawnhell", true).getBoolean(true);
        cfg_canspawnsky = lconf.get("CanSpawn", "cfg_canspawnsky", true).getBoolean(true);

        lconf.save();

        fn_Gcamp = new GVCBlockGuerrillaCamp().setBlockName("GCamp").setBlockTextureName("gvcmob:camp");
        GameRegistry.registerBlock(fn_Gcamp, "Camp");
        fn_Gcamp2 = new GVCBlockGuerrillaCamp2().setBlockName("GCamp2").setBlockTextureName("gvcmob:camp");
        GameRegistry.registerBlock(fn_Gcamp2, "Camp2");
        fn_Gcamp3 = new GVCXBlockFriendCamp().setBlockName("GCamp3").setBlockTextureName("gvcmob:camp");
        GameRegistry.registerBlock(fn_Gcamp3, "Camp3");

        fn_mobspawner = new GVCBlockMobSpawnerExtended().setBlockName("MobSpawnerKai").setBlockTextureName("gvcmob:mobspawner");
        GameRegistry.registerBlock(fn_mobspawner, "MobSpawnerKai");
        GKspawner = new GVCBlockMobSpawnerExtended();
        GKspawner.setBlockName("MobSpawnerKai_IRVING").setBlockTextureName("gvcmob:mobspawner");
        GKspawner.mobname = "GVCMob.IRVING";
        GameRegistry.registerBlock(GKspawner, "MobSpawnerKai_IRVING");

        tankspawner = new GVCBlockMobSpawnerExtended();
        tankspawner.setBlockName("MobSpawnerKai_TANK").setBlockTextureName("gvcmob:mobspawner");
        tankspawner.mobname = "GVCMob.Tank";
        GameRegistry.registerBlock(tankspawner, "MobSpawnerKai_TANK");

        APCspawner = new GVCBlockMobSpawnerExtended();
        APCspawner.setBlockName("MobSpawnerKai_APC").setBlockTextureName("gvcmob:mobspawner");
        APCspawner.mobname = "GVCMob.APC";
        GameRegistry.registerBlock(APCspawner, "MobSpawnerKai_APC");
        soldiers = new CampObj();
        soldiers.campName = "UNION";
        soldiers.campBlockTextureModel= "gvcmob:textures/model/pflagtexture.png";
        campsHash.put(soldiers.campName,soldiers);
        soldiers.teamEntityClasses = new Class[]{ GVCEntitySoldier.class,GVCEntitySoldier.class,GVCEntitySoldier.class,GVCEntitySoldier.class, GVCEntitySoldierMG.class, GVCEntitySoldierRPG.class, GVCEntitySoldierSP.class };
        fn_SoldierFlag = new GVCBlockFlag(soldiers);
        fn_SoldierFlag.setBlockName("BaseFlagBlock").setBlockTextureName("gvcmob:mobspawner");
        soldiers.campsBlock = fn_SoldierFlag;
        GameRegistry.registerBlock(fn_SoldierFlag, "BaseFlagBlock");


        boxes = new CampObj();
        boxes.campName = "Supply";
        boxes.campBlockTextureModel= "gvcmob:textures/model/sflagtexture.png";
        boxes.teamEntityClasses = new Class[]{ EntitySupplyBox.class };
        boxes.flagWidth = 0;
        campsHash.put(boxes.campName, boxes);

        fn_Supplyflag = new GVCBlockFlag(boxes);
        fn_Supplyflag.setBlockName("SupplyFlagBlock").setBlockTextureName("gvcmob:mobspawner");
        boxes.campsBlock = fn_Supplyflag;
        GameRegistry.registerBlock(fn_Supplyflag, "SupplyFlagBlock");

        guerrillas = new CampObj();
        guerrillas.campBlockTextureModel= "gvcmob:textures/model/gflagtexture.png";
        guerrillas.campName = "ALLIES Radical Party";
        campsHash.put(guerrillas.campName,guerrillas);
        //TODO
        guerrillas.teamEntityClasses = new Class[]{ GVCEntityGuerrilla.class,GVCEntityGuerrilla.class,GVCEntityGuerrilla.class,GVCEntityGuerrilla.class, GVCEntityGuerrillaMG.class,GVCEntityGuerrillaMG.class, GVCEntityGuerrillaSP.class, GVCEntityGuerrillaRPG.class };
        fn_Guerrillaflag = new GVCBlockFlag(guerrillas).setBlockName("GuerrillaBaseFlagBlock").setBlockTextureName("gvcmob:mobspawner");
        guerrillas.campsBlock = fn_Guerrillaflag;
        GameRegistry.registerBlock(fn_Guerrillaflag, "GuerrillaBaseFlagBlock");

        fn_guerrillaegg = new GVCItemGuerrillaEgg(0).setUnlocalizedName("GuerrillaEgg").setTextureName("gvcmob:guerrillaegg");
        GameRegistry.registerItem(fn_guerrillaegg, "GuerrillaEgg");
        fn_guerrillabegg = new GVCItemGuerrillaEgg(1).setUnlocalizedName("GuerrillaBEgg").setTextureName("gvcmob:guerrillabegg");
        GameRegistry.registerItem(fn_guerrillabegg, "GuerrillaBEgg");
        fn_guerrillaspegg = new GVCItemGuerrillaEgg(2).setUnlocalizedName("GuerrillaspEgg").setTextureName("gvcmob:guerrillaspegg");
        GameRegistry.registerItem(fn_guerrillaspegg, "GuerrillaspEgg");
        fn_guerrillarpgegg = new GVCItemGuerrillaEgg(3).setUnlocalizedName("GuerrillarpgEgg").setTextureName("gvcmob:guerrillarpgegg");
        GameRegistry.registerItem(fn_guerrillarpgegg, "GuerrillarpgEgg");
        fn_guerrillasgegg = new GVCItemGuerrillaEgg(4).setUnlocalizedName("GuerrillaSGEgg").setTextureName("gvcmob:guerrillasgegg");
        GameRegistry.registerItem(fn_guerrillasgegg, "GuerrillaSGEgg");
        fn_guerrillamgegg = new GVCItemGuerrillaEgg(5).setUnlocalizedName("GuerrillaMGEgg").setTextureName("gvcmob:guerrillamgegg");
        GameRegistry.registerItem(fn_guerrillamgegg, "GuerrillaMGEgg");
        fn_guerrillaattackheliegg = new GVCItemGuerrillaEgg(18).setUnlocalizedName("WZ10Egg").setTextureName("gvcmob:wz10egg");
        GameRegistry.registerItem(fn_guerrillaattackheliegg, "WZ10Egg");

//        GameRegistry.registerItem(new ItemSpoter(),"test");
        
        fn_gkegg = new GVCItemGuerrillaEgg(6).setUnlocalizedName("GKEgg").setTextureName("gvcmob:gkegg");
        GameRegistry.registerItem(fn_gkegg, "GKEgg");
        fn_tankegg = new GVCItemGuerrillaEgg(7).setUnlocalizedName("TankEgg").setTextureName("gvcmob:tankegg");
        GameRegistry.registerItem(fn_tankegg, "TankEgg");
        fn_apcegg = new GVCItemGuerrillaEgg(8).setUnlocalizedName("APCEgg").setTextureName("gvcmob:apcegg");
        GameRegistry.registerItem(fn_apcegg, "APCEgg");
        fn_heliegg = new GVCItemGuerrillaEgg(9).setUnlocalizedName("HeliEgg").setTextureName("gvcmob:heliegg");
        GameRegistry.registerItem(fn_heliegg, "HeliEgg");
        fn_jeepegg = new GVCItemGuerrillaEgg(10).setUnlocalizedName("JeepEgg").setTextureName("gvcmob:jeepegg");
        GameRegistry.registerItem(fn_jeepegg, "JeepEgg");
        fn_aagegg = new GVCItemGuerrillaEgg(11).setUnlocalizedName("AAGEgg").setTextureName("gvcmob:aagegg");
        GameRegistry.registerItem(fn_aagegg, "AAGEgg");
        fn_guerrillamegg = new GVCItemGuerrillaEgg(12).setUnlocalizedName("GuerrillaMEgg").setTextureName("gvcmob:guerrillamegg");
        GameRegistry.registerItem(fn_guerrillamegg, "GuerrillaMEgg");
        fn_t90egg = new GVCItemGuerrillaEgg(14).setUnlocalizedName("T-90Egg").setTextureName("gvcmob:t-90");
        GameRegistry.registerItem(fn_t90egg, "T-90Egg");
        fn_drawnegg = new GVCItemGuerrillaEgg(15).setUnlocalizedName("DrawnEgg").setTextureName("gvcmob:drawn");
        GameRegistry.registerItem(fn_drawnegg, "DrawnEgg");
        fn_flameregg = new GVCItemGuerrillaEgg(16).setUnlocalizedName("GuerrillaFlamerEgg").setTextureName("gvcmob:flamer");
        GameRegistry.registerItem(fn_flameregg, "GuerrillaFlamerEgg");
        fn_skeletonegg = new GVCItemGuerrillaEgg(17).setUnlocalizedName("GuerrillaSkeletonEgg").setTextureName("gvcmob:gskeleton");
        GameRegistry.registerItem(fn_skeletonegg, "GuerrillaSkeletonEgg");
        fn_endegg = new GVCItemGuerrillaEgg(19).setUnlocalizedName("GuerrillaEndEgg").setTextureName("gvcmob:gEnd");
        GameRegistry.registerItem(fn_endegg, "GuerrillaEndEgg");

        fn_mobspawner_egg = new GVCItemMobSpawnerEgg(0).setUnlocalizedName("MobSpawnerEgg").setTextureName("gvcmob:mobspawneregg");
        GameRegistry.registerItem(fn_mobspawner_egg, "MobSpawnerEgg");
        fn_mobspawner2_egg = new GVCItemMobSpawnerEgg(1).setUnlocalizedName("MobSpawner2Egg").setTextureName("gvcmob:mobspawner2egg");
        GameRegistry.registerItem(fn_mobspawner2_egg, "MobSpawner2Egg");

        fn_flagegg = new GVCItemGuerrillaFlagEgg().setUnlocalizedName("FlagEgg").setTextureName("gvcmob:flagegg");
        GameRegistry.registerItem(fn_flagegg, "FlagEgg");

        fn_pmcegg = new GVCItemPMCEgg(0).setUnlocalizedName("PMCEgg").setTextureName("gvcmob:pmcegg");
        GameRegistry.registerItem(fn_pmcegg, "PMCEgg");
        fn_pmcspegg = new GVCItemPMCEgg(1).setUnlocalizedName("PMCSPEgg").setTextureName("gvcmob:pmcspegg");
        GameRegistry.registerItem(fn_pmcspegg, "PMCSPEgg");
        fn_pmcmgegg = new GVCItemPMCEgg(2).setUnlocalizedName("PMCMGEgg").setTextureName("gvcmob:pmcmgegg");
        GameRegistry.registerItem(fn_pmcmgegg, "PMCMGEgg");
        fn_pmcrpgegg = new GVCItemPMCEgg(3).setUnlocalizedName("PMCRPGEgg").setTextureName("gvcmob:pmcrpgegg");
        GameRegistry.registerItem(fn_pmcrpgegg, "PMCRPGEgg");
        fn_pmctankegg = new GVCItemPMCEgg(4).setUnlocalizedName("PMCTankEgg").setTextureName("gvcmob:pmctankegg");
        GameRegistry.registerItem(fn_pmctankegg, "PMCTankEgg");
        fn_pmcheliegg = new GVCItemPMCEgg(5).setUnlocalizedName("PMCHeliEgg").setTextureName("gvcmob:pmcheliegg");
        GameRegistry.registerItem(fn_pmcheliegg, "PMCHeliEgg");
        fn_pmcGKegg = new GVCItemPMCEgg(6).setUnlocalizedName("PMCGKEgg").setTextureName("gvcmob:pmcGKegg");
        GameRegistry.registerItem(fn_pmcGKegg, "PMCGKEgg");
        fn_pmctankT90 = new GVCItemPMCEgg(7).setUnlocalizedName("PMCT-90A").setTextureName("gvcmob:PMCT-90A");
        GameRegistry.registerItem(fn_pmctankT90, "PMCT-90A");
        fn_MGM = new GVCItemPMCEgg(8).setUnlocalizedName("Metal Gear AX-55 ").setTextureName("gvcmob:MGM");
        GameRegistry.registerItem(fn_MGM, "MGM");
        fn_plane = new GVCItemPMCEgg(9).setUnlocalizedName("MiG").setTextureName("gvcmob:MiG");
        GameRegistry.registerItem(fn_plane, "MiG");
        fn_pmcBMPEgg = new GVCItemPMCEgg(10).setUnlocalizedName("PMCBMP").setTextureName("gvcmob:PMCBMP");
        GameRegistry.registerItem(fn_pmcBMPEgg, "PMCBMP");

        fn_soldieregg = new GVCItemGuerrillaSoldierEgg(0).setUnlocalizedName("SoldierEgg").setTextureName("gvcmob:solideregg");
        GameRegistry.registerItem(fn_soldieregg, "SoldierEgg");
        fn_soldierspegg = new GVCItemGuerrillaSoldierEgg(1).setUnlocalizedName("SoldierSPEgg").setTextureName("gvcmob:soliderspegg");
        GameRegistry.registerItem(fn_soldierspegg, "SoldierSPEgg");
        fn_soldiermgegg = new GVCItemGuerrillaSoldierEgg(2).setUnlocalizedName("SoldierMGEgg").setTextureName("gvcmob:solidermgegg");
        GameRegistry.registerItem(fn_soldiermgegg, "SoldierMGEgg");
        fn_soldierrpgegg = new GVCItemGuerrillaSoldierEgg(3).setUnlocalizedName("SoldierRPGEgg").setTextureName("gvcmob:soliderrpgegg");
        GameRegistry.registerItem(fn_soldierrpgegg, "SoldierRPGEgg");
        fn_soldiertankegg = new GVCItemGuerrillaSoldierEgg(4).setUnlocalizedName("SoldierTankEgg").setTextureName("gvcmob:solidertankegg");
        GameRegistry.registerItem(fn_soldiertankegg, "SoldierTankEgg");
        fn_soldierheliegg = new GVCItemGuerrillaSoldierEgg(5).setUnlocalizedName("SoldierHeliEgg").setTextureName("gvcmob:soliderheliegg");
        GameRegistry.registerItem(fn_soldierheliegg, "SoldierHeliEgg");
        fn_soldierbmpegg = new GVCItemGuerrillaSoldierEgg(6).setUnlocalizedName("SoldierBMPEgg").setTextureName("gvcmob:soliderbmpegg");
        GameRegistry.registerItem(fn_soldierbmpegg, "SoldierBMPEgg");
        gvcmx_reqsupport_arty = new GVCXrequestSupport(0).setUnlocalizedName("requestSupport_arty").setTextureName("gvcmob:requestsupport_arty");
        GameRegistry.registerItem(gvcmx_reqsupport_arty, "requestSupport_arty");
        gvcmx_reqsupport_heli = new GVCXrequestSupport(1).setUnlocalizedName("requestSupport_heli").setTextureName("gvcmob:requestsupport_heli");
        GameRegistry.registerItem(gvcmx_reqsupport_heli, "requestSupport_Heli");
        arenaUnit = new GVCItemarenaUnit().setUnlocalizedName("arenaUnit").setTextureName("gvcmob:arenaUnit").setMaxStackSize(64);
        GameRegistry.registerItem(arenaUnit, "arenaUnit");
        defsetter = new GVCItemPMCDefSetter().setUnlocalizedName("defsetter").setTextureName("gvcmob:defsetter");
        GameRegistry.registerItem(defsetter, "defsetter");

        GVCMPacketHandler.init();
        
    }

    public static void Debug(String pText, Object... pData)
    {
        if (isDebugMessage) {
            System.out.println(String.format("GVCMob-" + pText, pData));
        }
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent pEvent)
    {
        int id = 0;
        EntityRegistry.registerModEntity(GVCEntityGuerrilla.class, "Guerrilla", ++id, this, 250, 3, true);
        EntityRegistry.registerModEntity(GVCEntityGuerrillaSP.class, "Guerrillasp", ++id, this, 250, 3, true);
        EntityRegistry.registerModEntity(GVCEntityGuerrillaRPG.class, "Guerrillarpg", ++id, this, 250, 3, true);
        EntityRegistry.registerModEntity(GVCEntityGuerrillaSG.class, "Guerrillasg", ++id, this, 250, 3, true);
        EntityRegistry.registerModEntity(GVCEntityGuerrillaMG.class, "Guerrillamg", ++id, this, 250, 3, true);
        EntityRegistry.registerModEntity(GVCEntityGuerrillaBM.class, "Bommer", ++id, this, 250, 3, true);
        EntityRegistry.registerModEntity(GVCEntityGuerrillaP.class, "GuerrillaP", ++id, this, 250, 3, true);
        EntityRegistry.registerModEntity(GVCEntityGuerrillaSkeleton.class, "GuerrillaSkeleton", ++id, this, 250, 3, true);
        EntityRegistry.registerModEntity(GVCEntityGuerrilla_ender.class, "GuerrillaEnder", ++id, this, 250, 3, true);
        EntityRegistry.registerModEntity(GVCEntityGK.class, "IRVING", ++id, this, 250, 3, true);
//        EntityRegistry.registerModEntity(GVCEntityTank.class, "Tank", ++id, this, 250, 3, true);
//        EntityRegistry.registerModEntity(GVCEntityAPC.class, "APC", ++id, this, 250, 3, true);
//        EntityRegistry.registerModEntity(GVCEntityHeli.class, "Heli", ++id, this, 250, 3, true);
//        EntityRegistry.registerModEntity(GVCEntityWZ10AttackHeli.class, "WZ10Attackheli", ++id, this, 250, 3, true);
//        EntityRegistry.registerModEntity(GVCEntityJeep.class, "Jeep", ++id, this, 250, 3, true);
        EntityRegistry.registerModEntity(GVCEntityAAG.class, "AAG", ++id, this, 250, 3, true);

        EntityRegistry.registerModEntity(GVCEntityAA.class, "AA", ++id, this, 250, 3, true);
        EntityRegistry.registerModEntity(GVCEntityDrawn.class, "Drawn", ++id, this, 250, 3, true);

        EntityRegistry.registerModEntity(GVCEntityFlag.class, "Flag", ++id, this, 50, 3, false);

        EntityRegistry.registerModEntity(GVCEntityPMC.class, "PMC", ++id, this, 250, 3, true);
        EntityRegistry.registerModEntity(GVCEntityPMCSP.class, "PMCSP", ++id, this, 250, 3, true);
        EntityRegistry.registerModEntity(GVCEntityPMCMG.class, "PMCMG", ++id, this, 250, 3, true);
        EntityRegistry.registerModEntity(GVCEntityPMCRPG.class, "PMCRPG", ++id, this, 250, 3, true);
        EntityRegistry.registerModEntity(GVCEntitySoldier.class, "Solider", ++id, this, 250, 3, true);
        EntityRegistry.registerModEntity(GVCEntitySoldierSP.class, "SoliderSP", ++id, this, 250, 3, true);
        EntityRegistry.registerModEntity(GVCEntitySoldierMG.class, "SoliderMG", ++id, this, 250, 3, true);
        EntityRegistry.registerModEntity(GVCEntitySoldierRPG.class, "SoliderRPG", ++id, this, 250, 3, true);
//        EntityRegistry.registerModEntity(GVCEntitySoldierTank.class, "SoliderTank", ++id, this, 250, 3, true);
//        EntityRegistry.registerModEntity(GVCEntitySoldierHeli.class, "SoliderHeli", ++id, this, 250, 3, true);
//        EntityRegistry.registerModEntity(GVCEntityPMCTank.class, "PMCTank", ++id, this, 250, 3, true);
        EntityRegistry.registerModEntity(GVCEntityFriendGK.class, "PMCFriendIRVING", ++id, this, 250, 3, true);
//        EntityRegistry.registerModEntity(GVCEntityPMCHeli.class, "PMCHeli", ++id, this, 250, 3, true);
//        EntityRegistry.registerModEntity(GVCEntityPlane.class, "Plane", ++id, this, 250, 3, true);
        EntityRegistry.registerModEntity(GVCXEntitySoldierSpawn.class, "SysSpawnSoldier", ++id, this, 50, 3, false);

        EntityRegistry.registerModEntity(GVCEntityGuerrillaM.class, "GuerrillaM", ++id, this, 250, 3, false);
//        EntityRegistry.registerModEntity(GVCEntityTankT90.class, "T-90A(Guerrilla)", ++id, this, 250, 3, true);
//        EntityRegistry.registerModEntity(GVCEntityPMCT90Tank.class, "T-90A(PMC)", ++id, this, 250, 3, true);
//        EntityRegistry.registerModEntity(GVCEntitySoldierBMP.class, "BMP-1", ++id, this, 250, 3, true);
//        EntityRegistry.registerModEntity(GVCEntityPMCBMP.class, "PMC_BMP-1", ++id, this, 250, 3, true);
//        EntityRegistry.registerModEntity(EntityChild.class, "GVCSeat", ++id, this, 50, 3, false);
        EntityRegistry.registerModEntity(GVCdummyhitbox.class, "GVCDummybox", ++id, this, 50, 3, true);
        EntityRegistry.registerModEntity(EntitySupportTGT.class, "SysSummonsupportEntity", ++id, this, 128, 3, false);
        EntityRegistry.registerModEntity(GVCEntityAPCSpawn.class, "GVC_Spawner_Throw", ++id, this, 250, 3, false);
        EntityRegistry.registerModEntity(EntityParachute.class, "GVC_EntityParachute", ++id, this, 250, 3, false);
        EntityRegistry.registerModEntity(EntityMGAX55.class, "GVC_EntityMGAX55", ++id, this, 250, 3, true);
        EntityRegistry.registerModEntity(GVCEntityGuerrilla_Flamer.class, "GuerrillaFlamer", ++id, this, 250, 3, true);
        EntityRegistry.registerModEntity(EntitySupplyBox.class, "SupplyBox", ++id, this, 250, 3, true);
        EntityRegistry.registerModEntity(TU95.class, "TU95", ++id, this, 250, 3, true);

        //spawn
        {
            BiomeGenBase[] biomeList = null;
            biomeList = BiomeGenBase.getBiomeGenArray();
            for(BiomeGenBase biome : biomeList)
            {
                if(biome!=null)
                {
                    if(biome == BiomeGenBase.hell){
                        if(cfg_canspawnhell){
                            this.addspawnHell(biome);
                        }
                    }else
                    if(biome == BiomeGenBase.sky){
                        if(cfg_canspawnsky){
                            if (cfg_canspawnguerrilla) {
                                EntityRegistry.addSpawn(GVCEntityGuerrilla_ender.class, cfg_guerrillaspawnnomal * 3, cfg_guerrillaspawnnomal * 3, cfg_guerrillaspawnnomal * 3, EnumCreatureType.monster, new BiomeGenBase[]{biome});
                            }
                        }
                    }else this.spawn(biome);
                }
            }
        }

        GameRegistry.addRecipe(new ItemStack(fn_pmcegg, 1),
                "e",
                "i",
                'i', Items.iron_ingot,
                'e', Items.emerald
        );
        GameRegistry.addRecipe(new ItemStack(fn_pmcspegg, 1),
                "e",
                "g",
                'g', Items.gold_ingot,
                'e', Items.emerald
        );
        GameRegistry.addRecipe(new ItemStack(fn_pmcmgegg, 1),
                "e",
                "i",
                'i', Blocks.iron_block,
                'e', Items.emerald
        );
        GameRegistry.addRecipe(new ItemStack(fn_pmcrpgegg, 1),
                "e",
                "i",
                'i', Blocks.tnt,
                'e', Items.emerald
        );
        GameRegistry.addRecipe(new ItemStack(fn_pmctankegg, 1),
                " e ",
                "bbb",
                'b', Blocks.emerald_block,
                'e', Blocks.iron_block
        );
        GameRegistry.addRecipe(new ItemStack(fn_pmcheliegg, 1),
                "e",
                "b",
                'b', Blocks.gold_block,
                'e', Items.emerald
        );
        GameRegistry.addRecipe(new ItemStack(fn_pmcGKegg, 1),
                "e",
                "r",
                "b",
                'b', Blocks.emerald_block,
                'e', Items.emerald,
                'r', Blocks.redstone_block
        );


        GameRegistry.addRecipe(new ItemStack(fn_flagegg, 1),
                "ddd",
                "ded",
                "ddd",
                'd', Items.diamond,
                'e', Items.egg
        );
        GameRegistry.addRecipe(new ItemStack(fn_pmctankT90, 1),
                "ddd",
                "ded",
                "ddd",
                'e', Blocks.emerald_block,
                'd', Blocks.diamond_block
        );
        GameRegistry.addRecipe(new ItemStack(arenaUnit, 1),
                "ddd",
                "eee",
                "ddd",
                'd', Items.gunpowder,
                'e', Blocks.iron_bars
        );
        GameRegistry.addRecipe(new ItemStack(gvcmx_reqsupport_arty, 1),
                "ddd",
                "aaa",
                "ddd",
                'd', Items.emerald,
                'a', Items.redstone
        );
        GameRegistry.addRecipe(new ItemStack(gvcmx_reqsupport_heli, 1),
                "dad",
                "dad",
                "dad",
                'd', Items.emerald,
                'a', Items.redstone
        );
        GameRegistry.addRecipe(new ItemStack(defsetter, 1),
                "I ",
                "dd",
                'd', Items.emerald,'I',Items.iron_ingot
        );
        GameRegistry.addRecipe(new ItemStack(fn_SoldierFlag, 1),
                " I ",
                " I ",
                "ddd",
                'd', Items.emerald,'I',Items.iron_ingot
        );
        GameRegistry.addRecipe(new ItemStack(fn_Supplyflag, 1),
                " I ",
                " I ",
                " d ",
                'd', Blocks.emerald_block,'I',Items.iron_ingot
        );
        GameRegistry.addRecipe(new ItemStack(fn_MGM, 1),
                "ddd",
                "ddd",
                "ddd",
                'd', Blocks.beacon,'I',Items.iron_ingot
        );
        GameRegistry.addRecipe(new ItemStack(fn_plane, 1),
                "ddd",
                "IdI",
                "III",
                'd', Blocks.beacon,'I',Items.iron_ingot
        );
        GameRegistry.addRecipe(new ItemStack(fn_pmcBMPEgg, 1),
                "IrI",
                "IdI",
                "III",
                'd', Blocks.emerald_block,'I',Items.iron_ingot,
                'r', Blocks.redstone_block
        );

        proxy.initSome();
        if (cfg_setCamp == true) {
            GameRegistry.registerWorldGenerator(new WWorld(), 10);
        }

        if(pEvent.getSide().isClient()) {
            GVCMRenderSomeEvent event = new GVCMRenderSomeEvent();
            MinecraftForge.EVENT_BUS.register(event);
            FMLCommonHandler.instance().bus().register(event);
        }
        MinecraftForge.EVENT_BUS.register(new GVCMSpawnEvent());

        proxy.registerTileEntity();

        Object guns = new ArrayList();
        ((List)guns).addAll(HMGGunMaker.Guns);
        for (int i = 0; i < ((List)guns).size(); i++)
        {
            if ((((List)guns).get(i) instanceof HMGItem_Unified_Guns)) {
                HMGItem_Unified_Guns unified_guns = (HMGItem_Unified_Guns)((List)guns).get(i);
                if (unified_guns.gunInfo.guerrila_can_use) {
                    switch (unified_guns.gunInfo.canuseclass)
                    {
                        case 0:
                            Guns_AR.add(unified_guns);
                            break;
                        case 1:
                            Guns_SG.add(unified_guns);
                            break;
                        case 2:
                            Guns_SR.add(unified_guns);
                            break;
                        case 3:
                            Guns_LMG.add(unified_guns);
                            break;
                        case 4:
                            Guns_RR.add(unified_guns);
                            break;
                        case 5:
                            Guns_FL.add(unified_guns);
                            break;
                    }
                    if(unified_guns.gunInfo.guntype == 0 && unified_guns.gunInfo.needcock){
                        Guns_BoltR.add(unified_guns);
                    }
                    if(unified_guns.gunInfo.canfix){
                        Guns_HVG.add(unified_guns);
                    }
                    Debug("Listing HMG guns." + unified_guns.getUnlocalizedName(), new Object[0]);
                }
            }
        }
    
    
        No_place_to_HIDE = new Achievement(id_of_No_place_to_HIDE, "No_place_to_HIDE", 0, 0, new ItemStack(fn_guerrillaegg, 1, 15), null).initIndependentStat().registerStat();

        killedGuerrilla = new Achievement(id_of_killedGuerrilla, "killedGuerrilla", 1, 1, new ItemStack(GVCUtils.fn_ak74, 1, 15), No_place_to_HIDE).initIndependentStat().registerStat();

        Union_Army = new Achievement(id_of_union, "Union_Army", 1, 2, new ItemStack(fn_soldierbmpegg, 1, 15), No_place_to_HIDE).initIndependentStat().registerStat();

        Old_soldiers_never_fade = new Achievement(id_of_Old_soldiers_never_fade, "Old_soldiers_never_fade", 1, 3, new ItemStack(fn_tankegg, 1, 15), No_place_to_HIDE).initIndependentStat().registerStat();

        power_of_number = new Achievement(id_of_power_of_number, "power_of_number", 2, 0, new ItemStack(GVCUtils.fn_ak74, 1, 15), No_place_to_HIDE).initIndependentStat().registerStat();

        unmanned_Craft = new Achievement(id_of_unmanned_Craft, "unmanned_Craft", 1, 4, new ItemStack(fn_drawnegg, 1, 15), No_place_to_HIDE).initIndependentStat().registerStat();

        war_has_changed = new Achievement(id_of_war_has_changed, "war_has_changed", 1, 5, new ItemStack(fn_gkegg, 1, 15), No_place_to_HIDE).initIndependentStat().registerStat();

        unending_war = new Achievement(id_of_unending_war, "unending_war", 1, 6, new ItemStack(fn_skeletonegg, 1, 15), No_place_to_HIDE).initIndependentStat().registerStat();

        METAL_GEAR = new Achievement(id_of_METAL_GEAR, "METAL_GEAR", 3, 0 , new ItemStack(fn_MGM, 1, 15), null).initIndependentStat().registerStat();

        spear_the_gungnir = new Achievement(id_of_spear_the_gungnir, "spear_the_gungnir", 4, 0, new ItemStack(GVCUtils.fn_ptrk, 1, 15), null).initIndependentStat().registerStat();

        Gun_of_the_Lost_Country = new Achievement(id_of_Gun_of_the_Lost_Country, "Gun_of_the_Lost_Country", 4, 1, new ItemStack(GVCUtils.type38, 1, 15), null).initIndependentStat().registerStat();

        beacon_defensive = new Achievement(id_of_beacon_defensive, "beacon_defensive", 5, 0, new ItemStack(GVCUtils.fn_boxegg, 1, 15), null).initIndependentStat().registerStat();
        
        Achievement[] achievements = new Achievement[] { No_place_to_HIDE, killedGuerrilla,Union_Army,Old_soldiers_never_fade,power_of_number,unmanned_Craft,war_has_changed,unending_war,METAL_GEAR,spear_the_gungnir,Gun_of_the_Lost_Country,beacon_defensive};
        AchievementPage.registerAchievementPage(new AchievementPage("Guerrilla VS Command mod : HD Edition", achievements));
    }
    
    @Mod.EventHandler
    public void postInit(FMLServerStartingEvent event) {
        GVCMXEntityEvent gvcmxEntityEvent = new GVCMXEntityEvent();
        FMLCommonHandler.instance().bus().register(gvcmxEntityEvent);
        MinecraftForge.EVENT_BUS.register(gvcmxEntityEvent);
    }

    public void spawn(BiomeGenBase biome)
    {
        if (cfg_canspawnguerrilla)
        {
            EntityRegistry.addSpawn(GVCEntityGuerrilla.class, cfg_guerrillaspawnnomal * 3, cfg_guerrillaspawnnomal * 3, cfg_guerrillaspawnnomal * 3, EnumCreatureType.monster, new BiomeGenBase[] { biome });

            EntityRegistry.addSpawn(GVCEntityGuerrillaSP.class, cfg_guerrillaspawnnomal * 2, cfg_guerrillaspawnnomal * 2, cfg_guerrillaspawnnomal * 2, EnumCreatureType.monster, new BiomeGenBase[] { biome });

            EntityRegistry.addSpawn(GVCEntityGuerrillaRPG.class, cfg_guerrillaspawnnomal * 2, cfg_guerrillaspawnnomal * 2, cfg_guerrillaspawnnomal * 2, EnumCreatureType.monster, new BiomeGenBase[] { biome });

            EntityRegistry.addSpawn(GVCEntityGuerrillaSG.class, cfg_guerrillaspawnnomal * 3, cfg_guerrillaspawnnomal * 3, cfg_guerrillaspawnnomal * 3, EnumCreatureType.monster, new BiomeGenBase[] { biome });

            EntityRegistry.addSpawn(GVCEntityGuerrillaBM.class, cfg_guerrillaspawnnomal * 3, cfg_guerrillaspawnnomal * 3, cfg_guerrillaspawnnomal * 3, EnumCreatureType.monster, new BiomeGenBase[] { biome });

            EntityRegistry.addSpawn(GVCEntityGuerrillaM.class, cfg_guerrillaspawnnomal * 1, cfg_guerrillaspawnnomal * 4, cfg_guerrillaspawnnomal * 4, EnumCreatureType.monster, new BiomeGenBase[] { biome });

            EntityRegistry.addSpawn(GVCEntityGuerrilla_Flamer.class, cfg_guerrillaspawnnomal * 1, cfg_guerrillaspawnnomal * 1, cfg_guerrillaspawnnomal * 1, EnumCreatureType.monster, new BiomeGenBase[] { biome });

            EntityRegistry.addSpawn(GVCEntityGK.class, cfg_guerrillaspawntank * 6, cfg_guerrillaspawntank * 2, cfg_guerrillaspawntank * 3, EnumCreatureType.monster, new BiomeGenBase[] { biome });

//            EntityRegistry.addSpawn(GVCEntityTank.class, cfg_guerrillaspawntank * 4, cfg_guerrillaspawntank, cfg_guerrillaspawntank, EnumCreatureType.monster, new BiomeGenBase[] { biome });

//            EntityRegistry.addSpawn(GVCEntityJeep.class, cfg_guerrillaspawntank * 6, cfg_guerrillaspawntank * 2, cfg_guerrillaspawntank * 6, EnumCreatureType.monster, new BiomeGenBase[] { biome });

//            EntityRegistry.addSpawn(GVCEntityAPC.class, cfg_guerrillaspawntank * 6, cfg_guerrillaspawntank, cfg_guerrillaspawntank * 4, EnumCreatureType.monster, new BiomeGenBase[] { biome });
            if (biome != BiomeGenBase.hell) {
//                EntityRegistry.addSpawn(GVCEntityHeli.class, cfg_guerrillaspawntank * 4, cfg_guerrillaspawntank * 2, cfg_guerrillaspawntank * 5, EnumCreatureType.monster, new BiomeGenBase[] { biome });
            }
            if ((cfg_guerrillaspawndrawn) && (biome != BiomeGenBase.hell)) {
                EntityRegistry.addSpawn(GVCEntityDrawn.class, cfg_guerrillaspawntank * 5, 1, 1, EnumCreatureType.monster, new BiomeGenBase[] { biome });
//                EntityRegistry.addSpawn(GVCEntityWZ10AttackHeli.class, cfg_guerrillaspawntank * 1, cfg_guerrillaspawntank, cfg_guerrillaspawntank, EnumCreatureType.monster, new BiomeGenBase[] { biome });
            }
//            EntityRegistry.addSpawn(GVCEntityTankT90.class, cfg_guerrillaspawntank * 1, cfg_guerrillaspawntank, cfg_guerrillaspawntank, EnumCreatureType.monster, new BiomeGenBase[] { biome });
            
        }
        if (cfg_canspawnsolider)
        {
            EntityRegistry.addSpawn(GVCEntitySoldier.class, (int)(cfg_soldierspawnnormal * 6.0F), 4, 1, EnumCreatureType.monster, new BiomeGenBase[] { biome });

            EntityRegistry.addSpawn(GVCEntitySoldierSP.class, (int)(cfg_soldierspawnnormal * 6.0F), 1, 1, EnumCreatureType.monster, new BiomeGenBase[] { biome });

            EntityRegistry.addSpawn(GVCEntitySoldierMG.class, (int)(cfg_soldierspawnnormal * 6.0F), 2, 1, EnumCreatureType.monster, new BiomeGenBase[] { biome });

            EntityRegistry.addSpawn(GVCEntitySoldierRPG.class, (int)(cfg_soldierspawnnormal * 6.0F), 1, 1, EnumCreatureType.monster, new BiomeGenBase[] { biome });

//            EntityRegistry.addSpawn(GVCEntitySoldierTank.class, (int)(cfg_soldierspawntank * 4.0F), 1, 1, EnumCreatureType.monster, new BiomeGenBase[] { biome });

//            EntityRegistry.addSpawn(GVCEntitySoldierBMP.class, (int)(cfg_soldierspawntank * 6.0F), 1, 1, EnumCreatureType.monster, new BiomeGenBase[] { biome });

//            EntityRegistry.addSpawn(GVCEntitySoldierHeli.class, (int)(cfg_soldierspawntank * 2.0F), 1, 1, EnumCreatureType.monster, new BiomeGenBase[] { biome });
        }
    }
    public void addspawnHell(BiomeGenBase biome)
    {
        if (cfg_canspawnguerrilla)
        {
            EntityRegistry.addSpawn(GVCEntityGuerrillaSkeleton.class, cfg_guerrillaspawnnomal * 3, cfg_guerrillaspawnnomal * 3, cfg_guerrillaspawnnomal * 3, EnumCreatureType.monster, new BiomeGenBase[] { biome });
        }
    }
}
