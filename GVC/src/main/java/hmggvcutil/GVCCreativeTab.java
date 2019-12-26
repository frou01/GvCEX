package hmggvcutil;
 
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
 
public class GVCCreativeTab extends CreativeTabs
{
	public GVCCreativeTab(String label)
	{
		super(label);
	}
 
	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem()
	{
		return GVCUtils.fn_cm;
	}
 
	@Override
	@SideOnly(Side.CLIENT)
	public String getTranslatedTabLabel()
	{
		return "GVCUtil";
	}
 
}