package hmgww2;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class WW2CreativeTab extends CreativeTabs {
	public WW2CreativeTab(String label) {
		super(label);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem() {
		return mod_GVCWW2.bi_flag_jpn;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getTranslatedTabLabel() {
		return "GVCWW2";
	}

}