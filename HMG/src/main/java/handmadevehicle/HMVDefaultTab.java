package handmadevehicle;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import static handmadevehicle.HMVehicle.itemWrench;

public class HMVDefaultTab extends CreativeTabs {
	public HMVDefaultTab(String lable) {
		super(lable);
	}
	
	@Override
	public Item getTabIconItem() {
		return itemWrench;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public String getTranslatedTabLabel()
	{
		return "HandMadeVehicles";
	}
}
