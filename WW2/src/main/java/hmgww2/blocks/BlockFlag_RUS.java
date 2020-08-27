package hmgww2.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hmgww2.blocks.tile.FlagType;
import net.minecraft.util.IIcon;
//import net.minecraft.world.gen.structure.StructureStrongholdPieces;;


public class BlockFlag_RUS extends BlockRUSFlagBase {
	@SideOnly(Side.CLIENT)
	private IIcon TopIcon;

	@SideOnly(Side.CLIENT)
	private IIcon SideIcon;

	public BlockFlag_RUS() {
		super();
		flagType = FlagType.Barrack;
		maxs = 25;
		spawntime = 600;
	}
}