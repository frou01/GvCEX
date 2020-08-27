package hmgww2.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hmgww2.blocks.tile.FlagType;
import net.minecraft.util.IIcon;

public class BlockFlag_JPN extends BlockJPNFlagBase {
	@SideOnly(Side.CLIENT)
	private IIcon TopIcon;

	@SideOnly(Side.CLIENT)
	private IIcon SideIcon;

	public BlockFlag_JPN() {
		super();
		flagType = FlagType.Barrack;
		maxs = 10;
		spawntime = 800;
	}
}