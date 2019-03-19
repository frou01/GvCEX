package handmadeguns;
 
//import net.minecraftforge.fml.relauncher.Side;
//import net.minecraftforge.fml.relauncher.SideOnly;
//import net.minecraft.creativetab.CreativeTabs;
//import net.minecraft.item.Item;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class HMGCreativeTab_ForCustom extends CreativeTabs
{
	String itemName;
	String tabName;
	Item tabitem;
	public HMGCreativeTab_ForCustom(String label,String itemname)
	{
		super(label);
		tabName = label;
		itemName = itemname;
	}
 
	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem()
	{
		if(tabitem == null) {
			String[] type = itemName.split(":");
			tabitem = GameRegistry.findItem(type[0], type[1]);
			System.out.println("debug" + tabitem);
		}
		return tabitem;
	}
 
	@Override
	@SideOnly(Side.CLIENT)
	public String getTranslatedTabLabel()
	{
		return tabName;
	}
 
}