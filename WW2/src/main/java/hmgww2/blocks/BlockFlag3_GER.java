package hmgww2.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hmgww2.blocks.tile.FlagType;
import net.minecraft.util.IIcon;
//import net.minecraft.world.gen.structure.StructureStrongholdPieces;;


public class BlockFlag3_GER extends BlockGERFlagBase {
	@SideOnly(Side.CLIENT)
	private IIcon TopIcon;

	@SideOnly(Side.CLIENT)
	private IIcon SideIcon;

	public BlockFlag3_GER() {

		super();
		flagType = FlagType.AirBase;
		maxs = 10;
		spawntime = 3600;
		flagRange = 64;
	}
}