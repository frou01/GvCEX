package hmgww2.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hmgww2.blocks.tile.FlagType;
import net.minecraft.util.IIcon;
//import net.minecraft.world.gen.structure.StructureStrongholdPieces;;


public class BlockFlag3_JPN extends BlockJPNFlagBase {
	@SideOnly(Side.CLIENT)
	private IIcon TopIcon;

	@SideOnly(Side.CLIENT)
	private IIcon SideIcon;

	public BlockFlag3_JPN() {

		super();
		flagType = FlagType.AirBase;
		maxs = 10;
		spawntime = 2400;
		flagRange = 64;
	}

}