package DungeonGeneratorBase;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.common.MinecraftForge;

import java.io.*;
import java.util.*;

import static handmadeguns.HandmadeGunsCore.HMG_proxy;

@Mod(
        modid	= "DungeonGeneratorBase",
        name	= "DungeonGeneratorBase",
        version	= "1.7.x-srg-1"
)
public class mod_DungeonGeneratorBase {
    public static Item dangeonSelector;
//    public static String dangeondatapath = Loader.instance().getConfigDir().getAbsolutePath() + File.separatorChar + "GVC_Dungeon" + File.separatorChar + "assets" + File.separatorChar;
//    public static JarFile datafile;

    public static File[] filelist1;
    public static File directory1;
    
    public static HashMap<String , DungeonData_withSettings> dungeonDataList = new HashMap<String , DungeonData_withSettings>();
    
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent pEvent) {
        File packdir = new File(HMG_proxy.ProxyFile(), "GVCDungeons_Packs");
        packdir.mkdirs();
    
        File[] packlist = packdir.listFiles();
        Arrays.sort(packlist, new Comparator<File>() {
            public int compare(File file1, File file2){
                return file1.getName().compareTo(file2.getName());
            }
        });
        for (File apack : packlist){
    
            if (apack.isDirectory()) {
                File settingFile = new File(apack, "setting.txt");
                DungeonData_withSettings dungeonDataWithSettings = new DungeonData_withSettings(settingFile);
                ArrayList<DungeonData> dungeonData = new ArrayList<>();
                File direData = new File(apack, "data");
                File[] fileData = direData.listFiles();
                if (fileData != null) {
                    Arrays.sort(fileData, new Comparator<File>() {
                        public int compare(File file1, File file2) {
                            return file1.getName().compareTo(file2.getName());
                        }
                    });
                    for (int ii = 0; ii < fileData.length; ii++) {
                        try {
                            if (fileData[ii].isFile()) {
                                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileData[ii]), "Shift-JIS"));
                                dungeonData.add(DungeonData.loadDungeon(br));
                            }
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
                dungeonDataWithSettings.dungeonData = dungeonData;
                dungeonDataList.put(dungeonDataWithSettings.name, dungeonDataWithSettings);
            }
        }
        MapGenStructureIO.registerStructure(StructuresStartDungeonBase.class, "GVCDungeonStructureBase");
        MapGenStructureIO.func_143031_a(ComponentDungeonBase.class, "GVCDungeonComponentBase");
    
        GenerateEventHandler generateEventHandler = new GenerateEventHandler(new MapGenStructure_forGVC());
        MinecraftForge.EVENT_BUS.register(generateEventHandler);
        MinecraftForge.TERRAIN_GEN_BUS.register(generateEventHandler);
//        String path = "assets/";
//        Class classobj = mod_DungeonGeneratorBase.class;
//        URL dirURL = classobj.getResource(path);
//        if (dirURL == null)
//        {
//            String me = classobj.getName().replace(".", "/") + ".class";
//            dirURL = classobj.getClassLoader().getResource(me);
//        }
//        if (dirURL != null && dirURL.getProtocol().equals("jar")) {
//            try {
//                String jarPath = dirURL.getPath().substring(5, dirURL.getPath().indexOf("!"));
//
//                datafile = new JarFile(URLDecoder.decode(jarPath, "UTF-8"));
//                Enumeration<JarEntry> entries = datafile.entries();
//
//                File f = new File(dangeondatapath);
//                f.mkdirs();
//                //config下にデータ置き場生成
//
//                if (f.exists()) {
//                    while (entries.hasMoreElements()) {
//                        JarEntry entry = (JarEntry) entries.nextElement();
//                        String name = entry.getName();
//                        if (name.startsWith(path)) {
//                            //assets下のディレクトリだけ抜き出す
//
//                            //                            System.out.println("debug" + name);
//
//                            if (entry.isDirectory()) {
//                                File childdir = new File(Loader.instance().getConfigDir().getAbsolutePath() + File.separatorChar + "GVC_Dungeon" + File.separatorChar, entry.getName());
//                                childdir.mkdirs();
//                                //ディレクトリ生成
//                            } else {
//
//                                InputStream zipinputstream = jar.getInputStream(entry);
//                                File targetFile = new File(Loader.instance().getConfigDir().getAbsolutePath() + File.separatorChar + "GVC_Dungeon", entry.getName());
//                                if(!targetFile.exists()) {
//                                    OutputStream outStream = new FileOutputStream(targetFile, true);//追加モード
//                                    while (zipinputstream.available() > 0) {
//                                        outStream.write(zipinputstream.read());
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }
    public static boolean getNextDungeon(MapGenStructure_forGVC mapGenStructure_forGVC,World world,int x, int z,Random rand){
        for(DungeonData_withSettings dungeonDataWithSettings :dungeonDataList.values()){
            if(dungeonDataWithSettings.onStrongHold && mapGenStructure_forGVC.canSpawnStructureAtCoords_astrongHold(x,z)){
                mapGenStructure_forGVC.currentDungeon = dungeonDataWithSettings;
                return true;
            }
            boolean flag_biomeCheck = dungeonDataWithSettings.biomeRestrictionType;
            int biomeID = world.getBiomeGenForCoords(x * 16,z * 16).biomeID;
            for(int settedID: dungeonDataWithSettings.biomeSetting){
                if(settedID == biomeID){
                    if(dungeonDataWithSettings.biomeRestrictionType){
                        flag_biomeCheck &= false;
                    }else {
                        flag_biomeCheck |= true;
                    }
                }
            }
            
            if((flag_biomeCheck && (x % dungeonDataWithSettings.interval) == 0 && (z % dungeonDataWithSettings.interval) == 0 &&rand.nextFloat() < dungeonDataWithSettings.frequency)
                       ||
                       ((dungeonDataWithSettings.hasFixedPosition && x == dungeonDataWithSettings.x && z == dungeonDataWithSettings.z))){
                mapGenStructure_forGVC.currentDungeon = dungeonDataWithSettings;
                return true;
            }
        }
        return false;
    }
}
