package hmgww2.blocks;

import hmgww2.Nation;
import net.minecraft.block.Block;
//import net.minecraft.world.gen.structure.StructureStrongholdPieces;;

public class BlockGERFlagBase extends BlockFlagBase {
	public BlockGERFlagBase() {
		super();
		setHardness(50F);
		setResistance(2000.0F);
		setStepSound(Block.soundTypeStone);
		nation = Nation.GER;
	}

}