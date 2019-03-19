package DungeonGeneratorBase;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.item.Item;

import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;

import static handmadeguns.HandmadeGunsCore.proxy;

@Mod(
        modid	= "DungeonGeneratorBase",
        name	= "DungeonGeneratorBase",
        version	= "1.7.x-srg-1"
)
public class mod_DungeonGeneratorBase {
    public static Item dangeonSelector;
    public static String dangeondatapath = Loader.instance().getConfigDir().getAbsolutePath() + File.separatorChar + "GVC_Dungeon" + File.separatorChar + "assets" + File.separatorChar;
    public static JarFile datafile;

    public static File[] filelist1;
    public static File directory1;
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent pEvent) {
        String path = "assets/";
        Class classobj = mod_DungeonGeneratorBase.class;
        URL dirURL = classobj.getResource(path);
        if (dirURL == null)
        {
            String me = classobj.getName().replace(".", "/") + ".class";
            dirURL = classobj.getClassLoader().getResource(me);
        }
        if (dirURL != null && dirURL.getProtocol().equals("jar")) {
            try {
                String jarPath = dirURL.getPath().substring(5, dirURL.getPath().indexOf("!"));

                datafile = new JarFile(URLDecoder.decode(jarPath, "UTF-8"));
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
