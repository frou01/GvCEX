package GuerrilaDungeons;

import DungeonGeneratorBase.DangeonData;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.common.MinecraftForge;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;

import static DungeonGeneratorBase.DangeonData.loadDangeon;
import static DungeonGeneratorBase.mod_DungeonGeneratorBase.dangeondatapath;
import static DungeonGeneratorBase.mod_DungeonGeneratorBase.datafile;
import static handmadeguns.HandmadeGunsCore.proxy;

@Mod(
        modid	= "TrenchDangeonGenerator",
        name	= "TrenchDangeonGenerator",
        version	= "1.7.x-srg-1",
        dependencies = "required-after:GVCMob"
)
public class mod_TrenchDangeonGenerator {
    public static File directory1;
    @Mod.EventHandler
    public void init_(FMLInitializationEvent pEvent) {
        try {
            String path = "assets/trenchdangeon/1Trench.gvcdg";
            InputStream entry = datafile.getInputStream(datafile.getEntry(path));
//            File file = new File(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(entry));
            loadDangeonData(reader);
            entry.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            String path = "assets/trenchdangeon/2UnderGroundChests.gvcdg";
            InputStream entry = datafile.getInputStream(datafile.getEntry(path));
//            File file = new File(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(entry));
            loadDangeonData(reader);
            entry.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            String path = "assets/trenchdangeon/3Tochka.gvcdg";
            InputStream entry = datafile.getInputStream(datafile.getEntry(path));
//            File file = new File(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(entry));
            loadDangeonData(reader);
            entry.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        directory1 = new File(proxy.ProxyFile(),"mods" + File.separatorChar + "Gdungeon");
//        File[] filelist = directory1.listFiles();
//        if(filelist != null) {
//            Arrays.sort(filelist, new java.util.Comparator<File>() {
//                public int compare(File file1, File file2){
//                    return file1.getName().compareTo(file2.getName());
//                }
//            });
//            for (File afile : filelist) {
//                loadDangeonData(afile);
//            }
//        }
        MapGenStructureIO.registerStructure(StructuresStartDungeontrench.class, "DGStructureStart_1");
        MapGenStructureIO.func_143031_a(ComponentDungeontrench.class, "DGDungeon_1");
        GenerateEventHandler generateEventHandler = new GenerateEventHandler();
        MinecraftForge.EVENT_BUS.register(generateEventHandler);
        MinecraftForge.TERRAIN_GEN_BUS.register(generateEventHandler);

    }

    int minx = -1;
    int miny = -1;
    int minz = -1;
    int maxx = -1;
    int maxy = -1;
    int maxz = -1;

    static ArrayList<DangeonData> dangeonData = new ArrayList<DangeonData>();

    public void loadDangeonData(BufferedReader file){
        dangeonData.add(loadDangeon(file));
    }
}
