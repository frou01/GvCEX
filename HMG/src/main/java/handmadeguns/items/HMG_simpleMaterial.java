package handmadeguns.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class HMG_simpleMaterial extends Item {
	public boolean cosume_onCraft = true;
	@Override
	public boolean doesContainerItemLeaveCraftingGrid(ItemStack p_77630_1_)
	{
		return false;
	}
}
