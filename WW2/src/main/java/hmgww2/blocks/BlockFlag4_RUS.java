package hmgww2.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hmgww2.Nation;
import hmgww2.blocks.tile.FlagType;
import hmgww2.blocks.tile.TileEntityBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
//import net.minecraft.world.gen.structure.StructureStrongholdPieces;;


public class BlockFlag4_RUS extends BlockJPNFlagBase
{
    @SideOnly(Side.CLIENT)
    private IIcon TopIcon;
 
    @SideOnly(Side.CLIENT)
    private IIcon SideIcon;
    public BlockFlag4_RUS() {
	    super();
	    flagType = FlagType.Port;
	    maxs = 3;
	    flagRange = 32;
	    spawntime = 1200;
    }
	
}