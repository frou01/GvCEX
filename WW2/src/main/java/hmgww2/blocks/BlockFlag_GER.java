package hmgww2.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hmgww2.blocks.tile.FlagType;
import net.minecraft.util.IIcon;
//import net.minecraft.world.gen.structure.StructureStrongholdPieces;;


public class BlockFlag_GER extends BlockGERFlagBase {
	@SideOnly(Side.CLIENT)
	private IIcon TopIcon;

	@SideOnly(Side.CLIENT)
	private IIcon SideIcon;

	public BlockFlag_GER() {
		super();
		flagType = FlagType.Barrack;
		maxs = 15;
		spawntime = 800;
	}
}