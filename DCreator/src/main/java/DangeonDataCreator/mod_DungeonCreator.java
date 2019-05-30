package DangeonDataCreator;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import java.io.File;

import static handmadeguns.HandmadeGunsCore.proxy;

@Mod(
        modid	= "DungeonCreator",
        name	= "DungeonCreator",
        version	= "1.7.x-srg-1"
)
public class mod_DungeonCreator {
    public static Item dangeonSelector;
    public static Block blankBlock;

    public static File[] filelist1;
    public static File directory1;
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent pEvent) {
        directory1 = new File(proxy.ProxyFile(),"mods" + File.separatorChar + "dungeonCreator");
        directory1.mkdirs();

        dangeonSelector	    = new DangeonSetter().setUnlocalizedName("Setter").setTextureName("dungeonCreator:setter");
        dangeonSelector.setCreativeTab(CreativeTabs.tabTools);
        GameRegistry.registerItem(dangeonSelector, "Setter");
        GameRegistry.registerBlock(blankBlock = new BlockBlank(), "BlankBlock");
    }

}
