package hmgww2.blocks;
 
import hmgww2.Nation;
import hmgww2.blocks.tile.FlagType;
import hmgww2.blocks.tile.TileEntityBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.SideOnly;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
//import net.minecraft.world.gen.structure.StructureStrongholdPieces;;


public class BlockFlag3_USA extends BlockUSAFlagBase
{
    @SideOnly(Side.CLIENT)
    private IIcon TopIcon;
 
    @SideOnly(Side.CLIENT)
    private IIcon SideIcon;
    public BlockFlag3_USA() {
	    super();
	    flagType = FlagType.AirBase;
	    maxs = 10;
	    spawntime = 2400;
	    flagRange = 64;
    }
	
}