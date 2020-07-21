package hmggvcmob.block;

import java.util.Random;

import hmggvcmob.tile.TileEntityFlag;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import cpw.mods.fml.relauncher.SideOnly;
import hmggvcutil.GVCUtils;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.IIcon;

import static hmggvcmob.GVCMobPlus.fn_Guerrillaflag;
import static hmggvcmob.GVCMobPlus.guerrillas;
//import net.minecraft.world.gen.structure.StructureStrongholdPieces;;

public class GVCBlockGuerrillaCamp extends Block
{
	@SideOnly(Side.CLIENT)
	private IIcon TopIcon;

	@SideOnly(Side.CLIENT)
	private IIcon SideIcon;

	public GVCBlockGuerrillaCamp() {
		super(Material.rock);
		setCreativeTab(CreativeTabs.tabMisc);
		setHardness(1.5F);
		setResistance(1.0F);
		setStepSound(Block.soundTypeStone);
	/*setBlockUnbreakable();
	/*setTickRandomly(true);
	/*disableStats();
        setLightOpacity(1);
        setLightLevel(1.0F);1.0F = 15*/
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float posX, float posY, float posZ){
		//world.setBlock(x+3, y+0, z-3, Blocks.fence);
		return true;
	}


	@Override
	public int quantityDropped(Random random){
		return 1;
	}


	@Override
	public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player){
	}

	public boolean renderAsNormalBlock()
	{
		return false;
	}

	public boolean isOpaqueCube()
	{
		return false;
	}

	/**
	 * Updates the blocks bounds based on its current state. Args: world, x, y, z
	 */
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
	}

    /*public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
    	super.updateTick(par1World, par2, par3, par4, par5Random);
    	if (!par1World.isRemote)
        {
    	EntitySnowball entitysilverfish = new EntitySnowball(par1World);
        entitysilverfish.setLocationAndAngles((double)par2, (double)par3, (double)par4, 0.0F, 0.0F);
        par1World.spawnEntityInWorld(entitysilverfish);
        }
    }*/

	public void onBlockAdded(World par1World, int par1, int par2, int par3)
	{
		if(par1World.getBiomeGenForCoords(par1, par3) == BiomeGenBase.taiga
				||par1World.getBiomeGenForCoords(par1, par3) == BiomeGenBase.taigaHills
				||par1World.getBiomeGenForCoords(par1, par3) == BiomeGenBase.jungle
				||par1World.getBiomeGenForCoords(par1, par3) == BiomeGenBase.jungleHills){
			par1World.setBlock(par1, par2, par3, Blocks.air);

		}else{


			for (int i0 = 0; i0 < 16; ++i0){
				for (int i1 = 0; i1 < 16; ++i1){
					for (int i2 = 0; i2 < 16; ++i2){
						par1World.setBlock(par1+i0, par2+i1, par3+i2, Blocks.air);
						if(par1World.getBiomeGenForCoords(par1, par3) == BiomeGenBase.desert
								|| par1World.getBiomeGenForCoords(par1, par3) == BiomeGenBase.desertHills ){
							par1World.setBlock(par1+i0, par2-i1, par3+i2, Blocks.sand);
						}else{
							par1World.setBlock(par1+i0, par2-i1, par3+i2, Blocks.stone);
						}
					}
				}
			}








			//1~4
			for (int i = 0; i < 15; ++i){
				par1World.setBlock(par1+0, par2+1, par3+i, Blocks.iron_bars);
				par1World.setBlock(par1+15, par2+1, par3+i, Blocks.iron_bars);
				par1World.setBlock(par1+i, par2+1, par3+0, Blocks.iron_bars);
				par1World.setBlock(par1+i, par2+1, par3+15, Blocks.iron_bars);
				par1World.setBlock(par1+0, par2+1, par3+0, Blocks.cobblestone);
				par1World.setBlock(par1+0, par2+1, par3+15, Blocks.cobblestone);
				par1World.setBlock(par1+15, par2+1, par3+0, Blocks.cobblestone);
				par1World.setBlock(par1+15, par2+1, par3+15, Blocks.cobblestone);

				par1World.setBlock(par1+0, par2+2, par3+i, Blocks.iron_bars);
				par1World.setBlock(par1+15, par2+2, par3+i, Blocks.iron_bars);
				par1World.setBlock(par1+i, par2+2, par3+0, Blocks.iron_bars);
				par1World.setBlock(par1+i, par2+2, par3+15, Blocks.iron_bars);
				par1World.setBlock(par1+0, par2+2, par3+0, Blocks.cobblestone);
				par1World.setBlock(par1+0, par2+2, par3+15, Blocks.cobblestone);
				par1World.setBlock(par1+15, par2+2, par3+0, Blocks.cobblestone);
				par1World.setBlock(par1+15, par2+2, par3+15, Blocks.cobblestone);

				par1World.setBlock(par1+0, par2+3, par3+i, Blocks.iron_bars);
				par1World.setBlock(par1+15, par2+3, par3+i, Blocks.iron_bars);
				par1World.setBlock(par1+i, par2+3, par3+0, Blocks.iron_bars);
				par1World.setBlock(par1+i, par2+3, par3+15, Blocks.iron_bars);
				par1World.setBlock(par1+0, par2+3, par3+0, Blocks.cobblestone);
				par1World.setBlock(par1+0, par2+3, par3+15, Blocks.cobblestone);
				par1World.setBlock(par1+15, par2+3, par3+0, Blocks.cobblestone);
				par1World.setBlock(par1+15, par2+3, par3+15, Blocks.cobblestone);

    		/*par1World.setBlock(par1+0, par2+4, par3+0, Block.torchWood.blockID);
    		par1World.setBlock(par1+0, par2+4, par3+15, Block.torchWood.blockID);
    		par1World.setBlock(par1+15, par2+4, par3+0, Block.torchWood.blockID);
    		par1World.setBlock(par1+15, par2+4, par3+15, Block.torchWood.blockID);*/

				par1World.setBlock(par1+6, par2+1, par3+0, Blocks.cobblestone);
				par1World.setBlock(par1+9, par2+1, par3+0, Blocks.cobblestone);
				par1World.setBlock(par1+7, par2+1, par3+0, Blocks.air);
				par1World.setBlock(par1+8, par2+1, par3+0, Blocks.air);

				par1World.setBlock(par1+6, par2+2, par3+0, Blocks.cobblestone);
				par1World.setBlock(par1+9, par2+2, par3+0, Blocks.cobblestone);
				par1World.setBlock(par1+7, par2+2, par3+0, Blocks.air);
				par1World.setBlock(par1+8, par2+2, par3+0, Blocks.air);

				par1World.setBlock(par1+6, par2+3, par3+0, Blocks.cobblestone);
				//par1World.setBlock(par1+7, par2+3, par3+0, Block.sandStone.blockID);
				//par1World.setBlock(par1+8, par2+3, par3+0, Block.sandStone.blockID);
				par1World.setBlock(par1+9, par2+3, par3+0, Blocks.cobblestone);

				par1World.setBlock(par1+0, par2+1, par3+6, Blocks.cobblestone);
				par1World.setBlock(par1+0, par2+1, par3+9, Blocks.cobblestone);
				par1World.setBlock(par1+0, par2+1, par3+7, Blocks.air);
				par1World.setBlock(par1+0, par2+1, par3+8, Blocks.air);
				par1World.setBlock(par1+0, par2+2, par3+6, Blocks.cobblestone);
				par1World.setBlock(par1+0, par2+2, par3+9, Blocks.cobblestone);
				par1World.setBlock(par1+0, par2+2, par3+7, Blocks.air);
				par1World.setBlock(par1+0, par2+2, par3+8, Blocks.air);
				par1World.setBlock(par1+0, par2+3, par3+6, Blocks.cobblestone);
				//par1World.setBlock(par1+7, par2+3, par3+0, Block.sandStone.blockID);
				//par1World.setBlock(par1+8, par2+3, par3+0, Block.sandStone.blockID);
				par1World.setBlock(par1+0, par2+3, par3+9, Blocks.cobblestone);

				par1World.setBlock(par1+15, par2+1, par3+6, Blocks.cobblestone);
				par1World.setBlock(par1+15, par2+1, par3+9, Blocks.cobblestone);
				par1World.setBlock(par1+15, par2+1, par3+7, Blocks.air);
				par1World.setBlock(par1+15, par2+1, par3+8, Blocks.air);
				par1World.setBlock(par1+15, par2+2, par3+6, Blocks.cobblestone);
				par1World.setBlock(par1+15, par2+2, par3+9, Blocks.cobblestone);
				par1World.setBlock(par1+15, par2+2, par3+7, Blocks.air);
				par1World.setBlock(par1+15, par2+2, par3+8, Blocks.air);
				par1World.setBlock(par1+15, par2+3, par3+6, Blocks.cobblestone);
				//par1World.setBlock(par1+7, par2+3, par3+0, Block.sandStone.blockID);
				//par1World.setBlock(par1+8, par2+3, par3+0, Block.sandStone.blockID);
				par1World.setBlock(par1+15, par2+3, par3+9, Blocks.cobblestone);

				par1World.setBlock(par1+6, par2+1, par3+15, Blocks.cobblestone);
				par1World.setBlock(par1+9, par2+1, par3+15, Blocks.cobblestone);
				par1World.setBlock(par1+7, par2+1, par3+15, Blocks.air);
				par1World.setBlock(par1+8, par2+1, par3+15, Blocks.air);
				par1World.setBlock(par1+6, par2+2, par3+15, Blocks.cobblestone);
				par1World.setBlock(par1+9, par2+2, par3+15, Blocks.cobblestone);
				par1World.setBlock(par1+7, par2+2, par3+15, Blocks.air);
				par1World.setBlock(par1+8, par2+2, par3+15, Blocks.air);
				par1World.setBlock(par1+6, par2+3, par3+15, Blocks.cobblestone);
				//par1World.setBlock(par1+7, par2+3, par3+0, Block.sandStone.blockID);
				//par1World.setBlock(par1+8, par2+3, par3+0, Block.sandStone.blockID);
				par1World.setBlock(par1+9, par2+3, par3+15, Blocks.cobblestone);
			}

			for (int i = 1; i < 7; ++i){
				par1World.setBlock(par1+11, par2+i, par3+11, Blocks.fence);
				par1World.setBlock(par1+11, par2+i, par3+12, Blocks.fence);
				par1World.setBlock(par1+11, par2+i, par3+13, Blocks.fence);
				par1World.setBlock(par1+12, par2+i, par3+11, Blocks.fence);
				par1World.setBlock(par1+12, par2+i, par3+12, Blocks.fence);
				par1World.setBlock(par1+13, par2+i, par3+11, Blocks.fence);
				par1World.setBlock(par1+13, par2+i, par3+12, Blocks.fence);
				par1World.setBlock(par1+13, par2+i, par3+13, Blocks.fence);
				par1World.setBlock(par1+11, par2+7, par3+11, Blocks.planks,0,2);
				par1World.setBlock(par1+11, par2+7, par3+12, Blocks.planks,0,2);
				par1World.setBlock(par1+11, par2+7, par3+13, Blocks.planks,0,2);
				par1World.setBlock(par1+12, par2+7, par3+11, Blocks.planks,0,2);
				par1World.setBlock(par1+12, par2+7, par3+12, Blocks.planks,0,2);
				par1World.setBlock(par1+12, par2+7, par3+13, Blocks.planks,0,2);
				par1World.setBlock(par1+13, par2+7, par3+11, Blocks.planks,0,2);
				par1World.setBlock(par1+13, par2+7, par3+12, Blocks.planks,0,2);
				par1World.setBlock(par1+13, par2+7, par3+13, Blocks.planks,0,2);
				par1World.setBlock(par1+11, par2+8, par3+11, Blocks.fence);
				par1World.setBlock(par1+11, par2+8, par3+12, Blocks.fence);
				par1World.setBlock(par1+11, par2+8, par3+13, Blocks.fence);
				par1World.setBlock(par1+12, par2+8, par3+11, Blocks.fence);
				//par1World.setBlock(par1+3, par2+8, par3+3, Block.fence.blockID);
				par1World.setBlock(par1+12, par2+8, par3+13, Blocks.fence);
				par1World.setBlock(par1+13, par2+8, par3+11, Blocks.fence);
				par1World.setBlock(par1+13, par2+8, par3+12, Blocks.fence);
				par1World.setBlock(par1+13, par2+8, par3+13, Blocks.fence);
			}

			//camp
			//1
			par1World.setBlock(par1+2, par2+1, par3+7, Blocks.fence);
			par1World.setBlock(par1+2, par2+1, par3+8, Blocks.wool,13,2);
			par1World.setBlock(par1+2, par2+1, par3+9, Blocks.wool,13,2);
			//this.worldObj.setBlock2par1+3, par2+0, par3-0, Block.cloth.blockID,13,2);
			par1World.setBlock(par1+2, par2+1, par3+11, Blocks.wool,13,2);
			par1World.setBlock(par1+2, par2+1, par3+12, Blocks.wool,13,2);
			par1World.setBlock(par1+2, par2+1, par3+13, Blocks.fence);

			par1World.setBlock(par1+8, par2+1, par3+7, Blocks.fence);
			par1World.setBlock(par1+8, par2+1, par3+8, Blocks.wool,13,2);
			par1World.setBlock(par1+8, par2+1, par3+9, Blocks.wool,13,2);
			//this.worldObj.setBlock(par1-3, par2+0, par3-0, Block.cloth.blockID,13,2);
			par1World.setBlock(par1+8, par2+1, par3+11, Blocks.wool,13,2);
			par1World.setBlock(par1+8, par2+1, par3+12, Blocks.wool,13,2);
			par1World.setBlock(par1+8, par2+1, par3+13, Blocks.fence);

			par1World.setBlock(par1+3, par2+1, par3+7, Blocks.wool,13,2);
			par1World.setBlock(par1+4, par2+1, par3+7, Blocks.wool,13,2);
			//this.worldObj.setBlock(par1-0, par2+0, par3-3, Block.cloth.blockID,13,2);
			par1World.setBlock(par1+6, par2+1, par3+7, Blocks.wool,13,2);
			par1World.setBlock(par1+7, par2+1, par3+7, Blocks.wool,13,2);

			par1World.setBlock(par1+3, par2+1, par3+13, Blocks.wool,13,2);
			par1World.setBlock(par1+4, par2+1, par3+13, Blocks.wool,13,2);
			par1World.setBlock(par1+6, par2+1, par3+13, Blocks.wool,13,2);
			par1World.setBlock(par1+7, par2+1, par3+13, Blocks.wool,13,2);

			//2
			par1World.setBlock(par1+2, par2+2, par3+7, Blocks.fence);
			par1World.setBlock(par1+2, par2+2, par3+8, Blocks.wool,13,2);
			par1World.setBlock(par1+2, par2+2, par3+9, Blocks.wool,13,2);
			//this.worldObj.setBlock2par1+3, par2+0, par3-0, Block.cloth.blockID,13,2);
			par1World.setBlock(par1+2, par2+2, par3+11, Blocks.wool,13,2);
			par1World.setBlock(par1+2, par2+2, par3+12, Blocks.wool,13,2);
			par1World.setBlock(par1+2, par2+2, par3+13, Blocks.fence);

			par1World.setBlock(par1+8, par2+2, par3+7, Blocks.fence);
			par1World.setBlock(par1+8, par2+2, par3+8, Blocks.wool,13,2);
			par1World.setBlock(par1+8, par2+2, par3+9, Blocks.wool,13,2);
			//this.worldObj.setBlock(par1-3, par2+0, par3-0, Block.cloth.blockID,13,2);
			par1World.setBlock(par1+8, par2+2, par3+11, Blocks.wool,13,2);
			par1World.setBlock(par1+8, par2+2, par3+12, Blocks.wool,13,2);
			par1World.setBlock(par1+8, par2+2, par3+13, Blocks.fence);

			par1World.setBlock(par1+3, par2+2, par3+7, Blocks.wool,13,2);
			par1World.setBlock(par1+4, par2+2, par3+7, Blocks.wool,13,2);
			//this.worldObj.setBlock(par1-0, par2+0, par3-3, Block.cloth.blockID,13,2);
			par1World.setBlock(par1+6, par2+2, par3+7, Blocks.wool,13,2);
			par1World.setBlock(par1+7, par2+2, par3+7, Blocks.wool,13,2);

			par1World.setBlock(par1+3, par2+2, par3+13, Blocks.wool,13,2);
			par1World.setBlock(par1+4, par2+2, par3+13, Blocks.wool,13,2);
			par1World.setBlock(par1+6, par2+2, par3+13, Blocks.wool,13,2);
			par1World.setBlock(par1+7, par2+2, par3+13, Blocks.wool,13,2);

			//3
			par1World.setBlock(par1+2, par2+3, par3+7, Blocks.fence);
			par1World.setBlock(par1+2, par2+3, par3+8, Blocks.fence);
			par1World.setBlock(par1+2, par2+3, par3+9, Blocks.fence);
			par1World.setBlock(par1+2, par2+3, par3+10, Blocks.fence);
			par1World.setBlock(par1+2, par2+3, par3+11, Blocks.fence);
			par1World.setBlock(par1+2, par2+3, par3+12, Blocks.fence);
			par1World.setBlock(par1+2, par2+3, par3+13, Blocks.fence);

			par1World.setBlock(par1+3, par2+3, par3+7, Blocks.fence);
			par1World.setBlock(par1+3, par2+3, par3+8, Blocks.wool,13,2);
			par1World.setBlock(par1+3, par2+3, par3+9, Blocks.wool,13,2);
			par1World.setBlock(par1+3, par2+3, par3+10, Blocks.wool,13,2);
			par1World.setBlock(par1+3, par2+3, par3+11, Blocks.wool,13,2);
			par1World.setBlock(par1+3, par2+3, par3+12, Blocks.wool,13,2);
			par1World.setBlock(par1+3, par2+3, par3+13, Blocks.fence);

			par1World.setBlock(par1+4, par2+3, par3+7, Blocks.fence);
			par1World.setBlock(par1+4, par2+3, par3+8, Blocks.wool,13,2);
			par1World.setBlock(par1+4, par2+3, par3+9, Blocks.wool,13,2);
			par1World.setBlock(par1+4, par2+3, par3+10, Blocks.wool,13,2);
			par1World.setBlock(par1+4, par2+3, par3+11, Blocks.wool,13,2);
			par1World.setBlock(par1+4, par2+3, par3+12, Blocks.wool,13,2);
			par1World.setBlock(par1+4, par2+3, par3+13, Blocks.fence);

			par1World.setBlock(par1+5, par2+3, par3+7, Blocks.fence);
			par1World.setBlock(par1+5, par2+3, par3+8, Blocks.wool,13,2);
			par1World.setBlock(par1+5, par2+3, par3+9, Blocks.wool,13,2);
			par1World.setBlock(par1+5, par2+3, par3+10, Blocks.wool,13,2);
			par1World.setBlock(par1+5, par2+3, par3+11, Blocks.wool,13,2);
			par1World.setBlock(par1+5, par2+3, par3+12, Blocks.wool,13,2);
			par1World.setBlock(par1+5, par2+3, par3+13, Blocks.fence);

			par1World.setBlock(par1+6, par2+3, par3+7, Blocks.fence);
			par1World.setBlock(par1+6, par2+3, par3+8, Blocks.wool,13,2);
			par1World.setBlock(par1+6, par2+3, par3+9, Blocks.wool,13,2);
			par1World.setBlock(par1+6, par2+3, par3+10, Blocks.wool,13,2);
			par1World.setBlock(par1+6, par2+3, par3+11, Blocks.wool,13,2);
			par1World.setBlock(par1+6, par2+3, par3+12, Blocks.wool,13,2);
			par1World.setBlock(par1+6, par2+3, par3+13, Blocks.fence);

			par1World.setBlock(par1+7, par2+3, par3+7, Blocks.fence);
			par1World.setBlock(par1+7, par2+3, par3+8, Blocks.wool,13,2);
			par1World.setBlock(par1+7, par2+3, par3+9, Blocks.wool,13,2);
			par1World.setBlock(par1+7, par2+3, par3+10, Blocks.wool,13,2);
			par1World.setBlock(par1+7, par2+3, par3+11, Blocks.wool,13,2);
			par1World.setBlock(par1+7, par2+3, par3+12, Blocks.wool,13,2);
			par1World.setBlock(par1+7, par2+3, par3+13, Blocks.fence);

			par1World.setBlock(par1+8, par2+3, par3+7, Blocks.fence);
			par1World.setBlock(par1+8, par2+3, par3+8, Blocks.fence);
			par1World.setBlock(par1+8, par2+3, par3+9, Blocks.fence);
			par1World.setBlock(par1+8, par2+3, par3+10, Blocks.fence);
			par1World.setBlock(par1+8, par2+3, par3+11, Blocks.fence);
			par1World.setBlock(par1+8, par2+3, par3+12, Blocks.fence);
			par1World.setBlock(par1+8, par2+3, par3+13, Blocks.fence);

			//���
			par1World.setBlock(par1+3, par2+1, par3+8, Blocks.furnace);
			par1World.setBlock(par1+4, par2+1, par3+8, Blocks.furnace);
			par1World.setBlock(par1+6, par2+1, par3+8, Blocks.chest);
			par1World.setBlock(par1+7, par2+1, par3+8, Blocks.chest);
			//ChestGenHooks info = ChestGenHooks.getInfo(PYRAMID_DESERT_CHEST);

			par1World.setBlock(par1+3, par2+1, par3+12, Blocks.crafting_table);
			par1World.setBlock(par1+4, par2+1, par3+12, Blocks.cauldron);
			par1World.setBlock(par1+6, par2+1, par3+12, Blocks.tnt);
			par1World.setBlock(par1+7, par2+1, par3+12, Blocks.tnt);

			par1World.setBlock(par1+5, par2+0, par3+10, Blocks.mob_spawner, 2, 2);
			TileEntityMobSpawner tileentitymobspawner1 = (TileEntityMobSpawner)par1World.getTileEntity(par1+5, par2+0, par3+10);

			if (tileentitymobspawner1 != null)
			{
				tileentitymobspawner1.func_145881_a().setEntityName("GVCMob.Guerrilla");
			}
			par1World.setBlock(par1+8, par2+1, par3+4, fn_Guerrillaflag, 2, 2);
			TileEntityFlag tileEntityFlag = new TileEntityFlag(guerrillas);
			tileEntityFlag.respawncycle = 20;
			par1World.setTileEntity(par1+8, par2+1, par3+4,tileEntityFlag);

			TileEntityChest Chest3;
			Chest3 = (TileEntityChest) par1World.getTileEntity(par1+6, par2+1, par3+8);
			for (int i = 1; i < 5; ++i){
				int suro3 = par1World.rand.nextInt(27);
				Chest3.setInventorySlotContents(suro3, new ItemStack(Items.apple,5,0));
			}
			TileEntityChest Chest4;
			Chest4 = (TileEntityChest) par1World.getTileEntity(par1+7, par2+1, par3+8);
			for (int i = 1; i < 5; ++i){
				int suro2 = par1World.rand.nextInt(27);
				Chest4.setInventorySlotContents(suro2, new ItemStack(Items.bread,5,0));
			}

			TileEntityChest Chest;
			par1World.setBlock(par1+13, par2+1, par3+2, Blocks.chest);
			par1World.setBlock(par1+13, par2+1, par3+3, Blocks.chest);
			Chest = (TileEntityChest) par1World.getTileEntity(par1+13, par2+1, par3+2);
			for (int i = 0; i < 5; ++i){
				int suro = par1World.rand.nextInt(27);
				Chest.setInventorySlotContents(suro, new ItemStack(GVCUtils.fn_ak74));
			}

			TileEntityChest Chest2;
			par1World.setBlock(par1+13, par2+1, par3+5, Blocks.chest);
			par1World.setBlock(par1+13, par2+1, par3+6, Blocks.chest);
			Chest2 = (TileEntityChest) par1World.getTileEntity(par1+13, par2+1, par3+5);
			for (int i = 0; i < 20; ++i){
				int suro2 = par1World.rand.nextInt(27);
				Chest2.setInventorySlotContents(suro2, new ItemStack(GVCUtils.fn_magazine_545,2,0));
			}

			par1World.setBlock(par1+13, par2+1, par3+9, Blocks.chest);
			par1World.setBlock(par1+13, par2+1, par3+10, Blocks.chest);

			//par1World.setBlock(par1+13, par2+1, par3+12, Block.chest.blockID);
			//par1World.setBlock(par1+13, par2+1, par3+13, Block.chest.blockID);

			par1World.setBlock(par1+3, par2+1, par3+2, Blocks.tnt);
			par1World.setBlock(par1+4, par2+1, par3+2, Blocks.tnt);
			par1World.setBlock(par1+2, par2+1, par3+3, Blocks.tnt);
			par1World.setBlock(par1+3, par2+1, par3+3, Blocks.tnt);
			par1World.setBlock(par1+4, par2+1, par3+3, Blocks.tnt);
			par1World.setBlock(par1+3, par2+1, par3+4, Blocks.tnt);
			par1World.setBlock(par1+3, par2+1, par3+4, Blocks.tnt);
			par1World.setBlock(par1+3, par2+2, par3+3, Blocks.tnt);





			par1World.setBlock(par1+0, par2+0, par3+0, Blocks.grass);
		}
	}

	@Override
	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, Block neighborBlock){

	}

    /*public void onBlockAdded(World par1World, int par2, int par3, int par4)
    {
        if (!par1World.isRemote)
        {
        	EntitySnowball entitysilverfish = new EntitySnowball(par1World);
            entitysilverfish.setLocationAndAngles((double)par2, (double)par3, (double)par4, 0.0F, 0.0F);
            par1World.spawnEntityInWorld(entitysilverfish);
        }
    }*/

	//par2=x par3=y par4=z
	public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{

            /*EntitySnowball entitysilverfish = new EntitySnowball(par1World);
            entitysilverfish.setLocationAndAngles((double)par2, (double)par3, (double)par4, 0.0F, 0.0F);
            par1World.spawnEntityInWorld(entitysilverfish);

    	/*double d0 = (double)((float)par2 + par5Random.nextFloat());
        double d1 = (double)((float)par3 + 0.8F);
        double d2 = (double)((float)par4 + par5Random.nextFloat());
        double d3 = 0.0D;
        double d4 = 0.0D;
        double d5 = 0.0D;
        par1World.spawnParticle("smoke", d0, d1, d2, d3, d4, d5);*/

	}

}