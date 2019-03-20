package hmgww2.blocks;
 
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hmgww2.blocks.tile.TileEntityFlag_JPN;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
 
public class BlockFlag_JPN extends BlockJPNFlagBase
{
    @SideOnly(Side.CLIENT)
    private IIcon TopIcon;
 
    @SideOnly(Side.CLIENT)
    private IIcon SideIcon;
    public BlockFlag_JPN() {
        this.setTickRandomly(true);
    }
    
    /**
     * Called upon block activation (right click on the block.)
     */
    public boolean onBlockActivated(World par1World, int par1, int par2, int par3, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
        /*if (par1World.isRemote)
        {
            return true;
        }
        else*/
        {
        	TileEntityFlag_JPN flag = (TileEntityFlag_JPN) par1World.getTileEntity(par1, par2, par3);
        	if(flag.spawn){
        		flag.spawn = false;
        	}else{
        		flag.spawn = true;
        	}
            return true;
        }
    }
    
    @Override
	public TileEntity createNewTileEntity(World world, int a) {
 
		return new TileEntityFlag_JPN();
	}
}