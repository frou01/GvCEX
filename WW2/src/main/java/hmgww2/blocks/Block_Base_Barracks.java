package hmgww2.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class Block_Base_Barracks extends Block {
	public Block_Base_Barracks() {
		super(Material.iron);
		setHardness(50F);
		setResistance(2000.0F);
		setStepSound(Block.soundTypeStone);
	}
}
