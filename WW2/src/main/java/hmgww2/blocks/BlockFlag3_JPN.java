package hmgww2.blocks;
 
import java.util.List;
import java.util.Random;
 
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import cpw.mods.fml.relauncher.SideOnly;
import hmgww2.blocks.tile.TileEntityFlag3_JPN;
import hmgww2.blocks.tile.TileEntityFlag_JPN;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.IIcon;
//import net.minecraft.world.gen.structure.StructureStrongholdPieces;;
import net.minecraft.util.MathHelper;
 
public class BlockFlag3_JPN extends BlockJPNFlagBase
{
    @SideOnly(Side.CLIENT)
    private IIcon TopIcon;
 
    @SideOnly(Side.CLIENT)
    private IIcon SideIcon;
    public BlockFlag3_JPN() {
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
        	TileEntityFlag3_JPN flag = (TileEntityFlag3_JPN) par1World.getTileEntity(par1, par2, par3);
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
 
		return new TileEntityFlag3_JPN();
	}
}