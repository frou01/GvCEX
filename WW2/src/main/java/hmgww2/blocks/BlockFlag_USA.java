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


public class BlockFlag_USA extends BlockUSAFlagBase
{
    @SideOnly(Side.CLIENT)
    private IIcon TopIcon;
 
    @SideOnly(Side.CLIENT)
    private IIcon SideIcon;
    public BlockFlag_USA() {
	    super();
	    flagType = FlagType.Barrack;
	    maxs = 45;
	    spawntime = 750;
    }
    
}