package hmgww2.blocks;

import hmgww2.Nation;
import net.minecraft.block.Block;
//import net.minecraft.world.gen.structure.StructureStrongholdPieces;;

public class BlockRUSFlagBase extends BlockFlagBase {
	public BlockRUSFlagBase() {
		super();
		setHardness(50F);
		setResistance(2000.0F);
		setStepSound(Block.soundTypeStone);
		nation = Nation.USSR;
	}

}