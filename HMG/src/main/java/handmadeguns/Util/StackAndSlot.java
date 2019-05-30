package handmadeguns.Util;

import net.minecraft.item.ItemStack;

public class StackAndSlot {
	public ItemStack stack;
	public int slot;
	public StackAndSlot(int slot,ItemStack stack){
		this.stack = stack;
		this.slot = slot;
	}
}
