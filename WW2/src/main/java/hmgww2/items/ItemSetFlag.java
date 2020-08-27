package hmgww2.items;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemSetFlag extends Item {
	private static final String __OBFID = "CL_00001771";

	public Block block;

	public ItemSetFlag(Block b) {
		this.block = b;
	}

	/**
	 * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
	 * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
	 */
	public boolean onItemUse(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
		if (p_77648_3_.isRemote) {
			return true;
		} else if (p_77648_7_ != 1) {
			return false;
		} else {
			p_77648_3_.setBlock(p_77648_4_, p_77648_5_ + 1, p_77648_6_, this.block);
			--p_77648_1_.stackSize;
			return true;
		}
	}
}