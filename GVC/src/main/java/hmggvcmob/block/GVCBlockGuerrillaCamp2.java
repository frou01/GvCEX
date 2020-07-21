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

public class GVCBlockGuerrillaCamp2 extends Block
{
	@SideOnly(Side.CLIENT)
	private IIcon TopIcon;

	@SideOnly(Side.CLIENT)
	private IIcon SideIcon;

	public GVCBlockGuerrillaCamp2() {
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
							par1World.setBlock(par1+i0, par2-i1, par3+i2, Blocks.dirt);
						}
					}
				}
			}

			for (int i = 0; i < 3; ++i){
				//
				par1World.setBlock(par1+6, par2+i, par3+0, Blocks.grass);
				par1World.setBlock(par1+7, par2+i, par3+0, Blocks.grass);
				par1World.setBlock(par1+8, par2+i, par3+0, Blocks.grass);
				par1World.setBlock(par1+9, par2+i, par3+0, Blocks.grass);

				par1World.setBlock(par1+4, par2+i, par3+1, Blocks.grass);
				par1World.setBlock(par1+5, par2+i, par3+1, Blocks.grass);
				par1World.setBlock(par1+10, par2+i, par3+1, Blocks.grass);
				par1World.setBlock(par1+11, par2+i, par3+1, Blocks.grass);

				par1World.setBlock(par1+3, par2+i, par3+2, Blocks.grass);
				par1World.setBlock(par1+12, par2+i, par3+2, Blocks.grass);

				par1World.setBlock(par1+2, par2+i, par3+3, Blocks.grass);
				par1World.setBlock(par1+13, par2+i, par3+3, Blocks.grass);

				par1World.setBlock(par1+1, par2+i, par3+4, Blocks.grass);
				par1World.setBlock(par1+1, par2+i, par3+5, Blocks.grass);
				par1World.setBlock(par1+14, par2+i, par3+4, Blocks.grass);
				par1World.setBlock(par1+14, par2+i, par3+5, Blocks.grass);

				par1World.setBlock(par1+0, par2+i, par3+6, Blocks.grass);
				par1World.setBlock(par1+0, par2+i, par3+7, Blocks.grass);
				par1World.setBlock(par1+0, par2+i, par3+8, Blocks.grass);
				par1World.setBlock(par1+0, par2+i, par3+9, Blocks.grass);
				par1World.setBlock(par1+15, par2+i, par3+6, Blocks.grass);
				par1World.setBlock(par1+15, par2+i, par3+7, Blocks.grass);
				par1World.setBlock(par1+15, par2+i, par3+8, Blocks.grass);
				par1World.setBlock(par1+15, par2+i, par3+9, Blocks.grass);


				par1World.setBlock(par1+1, par2+i, par3+10, Blocks.grass);
				par1World.setBlock(par1+1, par2+i, par3+11, Blocks.grass);
				par1World.setBlock(par1+14, par2+i, par3+10, Blocks.grass);
				par1World.setBlock(par1+14, par2+i, par3+11, Blocks.grass);

				par1World.setBlock(par1+2, par2+i, par3+12, Blocks.grass);
				par1World.setBlock(par1+13, par2+i, par3+12, Blocks.grass);

				par1World.setBlock(par1+3, par2+i, par3+13, Blocks.grass);
				par1World.setBlock(par1+12, par2+i, par3+13, Blocks.grass);

				par1World.setBlock(par1+4, par2+i, par3+14, Blocks.grass);
				par1World.setBlock(par1+5, par2+i, par3+14, Blocks.grass);
				par1World.setBlock(par1+10, par2+i, par3+14, Blocks.grass);
				par1World.setBlock(par1+11, par2+i, par3+14, Blocks.grass);

				par1World.setBlock(par1+6, par2+i, par3+15, Blocks.grass);
				par1World.setBlock(par1+7, par2+i, par3+15, Blocks.grass);
				par1World.setBlock(par1+8, par2+i, par3+15, Blocks.grass);
				par1World.setBlock(par1+9, par2+i, par3+15, Blocks.grass);


				//
				par1World.setBlock(par1+6, par2+i, par3+1, Blocks.log,0,2);
				par1World.setBlock(par1+7, par2+i, par3+1, Blocks.planks,0,2);
				par1World.setBlock(par1+8, par2+i, par3+1, Blocks.planks,0,2);
				par1World.setBlock(par1+9, par2+i, par3+1, Blocks.log,0,2);

				par1World.setBlock(par1+4, par2+i, par3+2, Blocks.log,0,2);
				par1World.setBlock(par1+5, par2+i, par3+2, Blocks.planks,0,2);
				par1World.setBlock(par1+10, par2+i, par3+2, Blocks.planks,0,2);
				par1World.setBlock(par1+11, par2+i, par3+2, Blocks.log,0,2);

				par1World.setBlock(par1+3, par2+i, par3+3, Blocks.planks,0,2);
				par1World.setBlock(par1+12, par2+i, par3+3, Blocks.planks,0,2);

				par1World.setBlock(par1+2, par2+i, par3+4, Blocks.log,0,2);
				par1World.setBlock(par1+2, par2+i, par3+5, Blocks.planks,0,2);
				par1World.setBlock(par1+13, par2+i, par3+4, Blocks.log,0,2);
				par1World.setBlock(par1+13, par2+i, par3+5, Blocks.planks,0,2);

				par1World.setBlock(par1+1, par2+i, par3+6, Blocks.log,0,2);
				par1World.setBlock(par1+1, par2+i, par3+7, Blocks.planks,0,2);
				par1World.setBlock(par1+1, par2+i, par3+8, Blocks.planks,0,2);
				par1World.setBlock(par1+1, par2+i, par3+9, Blocks.log,0,2);
				par1World.setBlock(par1+14, par2+i, par3+6, Blocks.log,0,2);
				par1World.setBlock(par1+14, par2+i, par3+7, Blocks.planks,0,2);
				par1World.setBlock(par1+14, par2+i, par3+8, Blocks.planks,0,2);
				par1World.setBlock(par1+14, par2+i, par3+9, Blocks.log,0,2);


				par1World.setBlock(par1+2, par2+i, par3+10, Blocks.planks,0,2);
				par1World.setBlock(par1+2, par2+i, par3+11, Blocks.log,0,2);
				par1World.setBlock(par1+13, par2+i, par3+10, Blocks.planks,0,2);
				par1World.setBlock(par1+13, par2+i, par3+11, Blocks.log,0,2);

				par1World.setBlock(par1+3, par2+i, par3+12, Blocks.planks,0,2);
				par1World.setBlock(par1+12, par2+i, par3+12, Blocks.planks,0,2);

				par1World.setBlock(par1+4, par2+i, par3+13, Blocks.log,0,2);
				par1World.setBlock(par1+5, par2+i, par3+13, Blocks.planks,0,2);
				par1World.setBlock(par1+10, par2+i, par3+13, Blocks.planks,0,2);
				par1World.setBlock(par1+11, par2+i, par3+13, Blocks.log,0,2);

				par1World.setBlock(par1+6, par2+i, par3+14, Blocks.log,0,2);
				par1World.setBlock(par1+7, par2+i, par3+14, Blocks.planks,0,2);
				par1World.setBlock(par1+8, par2+i, par3+14, Blocks.planks,0,2);
				par1World.setBlock(par1+9, par2+i, par3+14, Blocks.log,0,2);


				//
				par1World.setBlock(par1+6, par2+3, par3+1, Blocks.fence);
				par1World.setBlock(par1+9, par2+3, par3+1, Blocks.fence);
				par1World.setBlock(par1+4, par2+3, par3+2, Blocks.fence);
				par1World.setBlock(par1+11, par2+3, par3+2, Blocks.fence);
				par1World.setBlock(par1+2, par2+3, par3+4, Blocks.fence);
				par1World.setBlock(par1+13, par2+3, par3+4, Blocks.fence);
				par1World.setBlock(par1+1, par2+3, par3+6, Blocks.log,0,2);
				par1World.setBlock(par1+1, par2+3, par3+9, Blocks.log,0,2);
				par1World.setBlock(par1+14, par2+3, par3+6, Blocks.log,0,2);
				par1World.setBlock(par1+14, par2+3, par3+9, Blocks.log,0,2);
				par1World.setBlock(par1+1, par2+4, par3+6, Blocks.log,0,2);
				par1World.setBlock(par1+1, par2+4, par3+9, Blocks.log,0,2);
				par1World.setBlock(par1+14, par2+4, par3+6, Blocks.log,0,2);
				par1World.setBlock(par1+14, par2+4, par3+9, Blocks.log,0,2);
				par1World.setBlock(par1+2, par2+3, par3+11, Blocks.fence);
				par1World.setBlock(par1+13, par2+3, par3+11, Blocks.fence);
				par1World.setBlock(par1+4, par2+3, par3+13, Blocks.fence);
				par1World.setBlock(par1+11, par2+3, par3+13, Blocks.fence);
				par1World.setBlock(par1+6, par2+3, par3+14, Blocks.fence);
				par1World.setBlock(par1+9, par2+3, par3+14, Blocks.fence);



				//
				for (int x = 0; x < 4; ++x){
					//par1World.setBlock(par1+6+x, par2+4, par3+0, Blocks.leaves,0,2);
					//par1World.setBlock(par1+6+x, par2+4, par3+15, Blocks.leaves,0,2);
					par1World.setBlock(par1+6+x, par2+4, par3+0, Blocks.leaves,12,3);
					par1World.setBlock(par1+6+x, par2+4, par3+15, Blocks.leaves,12,3);
					par1World.setBlock(par1+6+x, par2+0, par3+0, Blocks.cobblestone);
					par1World.setBlock(par1+6+x, par2+0, par3+15, Blocks.cobblestone);
				}
				for (int x = 0; x < 8; ++x){
					//par1World.setBlock(par1+4+x, par2+4, par3+1, Blocks.leaves,0,2);
					//par1World.setBlock(par1+4+x, par2+4, par3+14, Blocks.leaves,0,2);
					par1World.setBlock(par1+4+x, par2+4, par3+1, Blocks.leaves,12,3);
					par1World.setBlock(par1+4+x, par2+4, par3+14, Blocks.leaves,12,3);
					par1World.setBlock(par1+4+x, par2+0, par3+1, Blocks.cobblestone);
					par1World.setBlock(par1+4+x, par2+0, par3+14, Blocks.cobblestone);
				}
				for (int x = 0; x < 10; ++x){
					//par1World.setBlock(par1+3+x, par2+4, par3+2, Blocks.leaves,0,2);
					//par1World.setBlock(par1+3+x, par2+4, par3+13, Blocks.leaves,0,2);
					par1World.setBlock(par1+3+x, par2+4, par3+2, Blocks.leaves,12,3);
					par1World.setBlock(par1+3+x, par2+4, par3+13, Blocks.leaves,12,3);
					par1World.setBlock(par1+3+x, par2+0, par3+2, Blocks.cobblestone);
					par1World.setBlock(par1+3+x, par2+0, par3+13, Blocks.cobblestone);
				}
				for (int x = 0; x < 12; ++x){
					//par1World.setBlock(par1+2+x, par2+4, par3+3, Blocks.leaves,0,2);
					//par1World.setBlock(par1+2+x, par2+4, par3+12, Blocks.leaves,0,2);
					par1World.setBlock(par1+2+x, par2+4, par3+3, Blocks.leaves,12,3);
					par1World.setBlock(par1+2+x, par2+4, par3+12, Blocks.leaves,12,3);
					par1World.setBlock(par1+2+x, par2+0, par3+3, Blocks.cobblestone);
					par1World.setBlock(par1+2+x, par2+0, par3+12, Blocks.cobblestone);
				}
				for (int x = 0; x < 14; ++x){
					//par1World.setBlock(par1+1+x, par2+4, par3+4, Blocks.leaves,0,2);
					//par1World.setBlock(par1+1+x, par2+4, par3+11, Blocks.leaves,0,2);
					//par1World.setBlock(par1+1+x, par2+4, par3+5, Blocks.leaves,0,2);
					//par1World.setBlock(par1+1+x, par2+4, par3+10, Blocks.leaves,0,2);
					par1World.setBlock(par1+1+x, par2+4, par3+4, Blocks.leaves,12,3);
					par1World.setBlock(par1+1+x, par2+4, par3+11, Blocks.leaves,12,3);
					par1World.setBlock(par1+1+x, par2+4, par3+5, Blocks.leaves,12,3);
					par1World.setBlock(par1+1+x, par2+4, par3+10, Blocks.leaves,12,3);
					par1World.setBlock(par1+1+x, par2+0, par3+4, Blocks.cobblestone);
					par1World.setBlock(par1+1+x, par2+0, par3+11, Blocks.cobblestone);
					par1World.setBlock(par1+1+x, par2+0, par3+5, Blocks.cobblestone);
					par1World.setBlock(par1+1+x, par2+0, par3+10, Blocks.cobblestone);
				}
				for (int x = 0; x < 16; ++x){
					//par1World.setBlock(par1+0+x, par2+4, par3+6, Blocks.leaves,0,2);
					//par1World.setBlock(par1+0+x, par2+4, par3+7, Blocks.leaves,0,2);
					//par1World.setBlock(par1+0+x, par2+4, par3+8, Blocks.leaves,0,2);
					//par1World.setBlock(par1+0+x, par2+4, par3+9, Blocks.leaves,0,2);
					par1World.setBlock(par1+0+x, par2+4, par3+6, Blocks.leaves,12,3);
					par1World.setBlock(par1+0+x, par2+4, par3+7, Blocks.leaves,12,3);
					par1World.setBlock(par1+0+x, par2+4, par3+8, Blocks.leaves,12,3);
					par1World.setBlock(par1+0+x, par2+4, par3+9, Blocks.leaves,12,3);
					par1World.setBlock(par1+0+x, par2+0, par3+6, Blocks.cobblestone);
					par1World.setBlock(par1+0+x, par2+0, par3+7, Blocks.cobblestone);
					par1World.setBlock(par1+0+x, par2+0, par3+8, Blocks.cobblestone);
					par1World.setBlock(par1+0+x, par2+0, par3+9, Blocks.cobblestone);
				}

				//
				par1World.setBlock(par1+6, par2+i+1, par3+4, Blocks.log,0,2);
				par1World.setBlock(par1+9, par2+i+1, par3+4, Blocks.log,0,2);
				par1World.setBlock(par1+4, par2+i+1, par3+6, Blocks.log,0,2);
				par1World.setBlock(par1+11, par2+i+1, par3+6, Blocks.log,0,2);
				par1World.setBlock(par1+4, par2+i+1, par3+9, Blocks.log,0,2);
				par1World.setBlock(par1+11, par2+i+1, par3+9, Blocks.log,0,2);
				par1World.setBlock(par1+6, par2+i+1, par3+11, Blocks.log,0,2);
				par1World.setBlock(par1+9, par2+i+1, par3+11, Blocks.log,0,2);
				par1World.setBlock(par1+5, par2+i+1, par3+5, Blocks.planks,0,2);
				par1World.setBlock(par1+10, par2+i+1, par3+5, Blocks.planks,0,2);
				par1World.setBlock(par1+5, par2+i+1, par3+10, Blocks.planks,0,2);
				par1World.setBlock(par1+10, par2+i+1, par3+10, Blocks.planks,0,2);
				par1World.setBlock(par1+7, par2+i+1, par3+4, Blocks.fence);
				par1World.setBlock(par1+8, par2+i+1, par3+4, Blocks.fence);
				par1World.setBlock(par1+4, par2+3, par3+7, Blocks.fence);
				par1World.setBlock(par1+4, par2+3, par3+8, Blocks.fence);
				par1World.setBlock(par1+11, par2+3, par3+7, Blocks.fence);
				par1World.setBlock(par1+11, par2+3, par3+8, Blocks.fence);
				par1World.setBlock(par1+7, par2+3, par3+11, Blocks.fence);
				par1World.setBlock(par1+8, par2+3, par3+11, Blocks.fence);





			}
			par1World.setBlock(par1+1, par2+4, par3+6, Blocks.log,0,2);
			par1World.setBlock(par1+1, par2+4, par3+9, Blocks.log,0,2);
			par1World.setBlock(par1+14, par2+4, par3+6, Blocks.log,0,2);
			par1World.setBlock(par1+14, par2+4, par3+9, Blocks.log,0,2);
			par1World.setBlock(par1+7, par2+2, par3+0, Blocks.air);
			par1World.setBlock(par1+7, par2+2, par3+1, Blocks.air);
			par1World.setBlock(par1+8, par2+2, par3+0, Blocks.air);
			par1World.setBlock(par1+8, par2+2, par3+1, Blocks.air);

//    	
//    	par1World.setBlock(par1+7, par2+0, par3+7, Blocks.mob_spawner, 2, 2);
//        TileEntityMobSpawner tileentitymobspawner1 = (TileEntityMobSpawner)par1World.getTileEntity(par1+7, par2+0, par3+7);
//        if (tileentitymobspawner1 != null)
//        {
//            tileentitymobspawner1.func_145881_a().setEntityName("GVCMob.Guerrilla");
//        }
//        par1World.setBlock(par1+8, par2+0, par3+2, Blocks.mob_spawner, 2, 2);
//        TileEntityMobSpawner tileentitymobspawner2 = (TileEntityMobSpawner)par1World.getTileEntity(par1+8, par2+0, par3+2);
//        if (tileentitymobspawner2 != null)
//        {
//            tileentitymobspawner2.func_145881_a().setEntityName("GVCMob.Guerrilla");
//        }
//        par1World.setBlock(par1+4, par2+0, par3+12, Blocks.mob_spawner, 2, 2);
//        TileEntityMobSpawner tileentitymobspawner3 = (TileEntityMobSpawner)par1World.getTileEntity(par1+4, par2+0, par3+12);
//        if (tileentitymobspawner3 != null)
//        {
//            tileentitymobspawner3.func_145881_a().setEntityName("GVCMob.Guerrilla");
//        }
//        par1World.setBlock(par1+14, par2+1, par3+9, Blocks.mob_spawner, 2, 2);
//        TileEntityMobSpawner tileentitymobspawner4 = (TileEntityMobSpawner)par1World.getTileEntity(par1+14, par2+1, par3+9);
//        if (tileentitymobspawner4 != null)
//        {
//            tileentitymobspawner4.func_145881_a().setEntityName("GVCMob.Guerrilla");
//        }


			//kazari


			par1World.setBlock(par1+7, par2+1, par3+8, fn_Guerrillaflag, 2, 2);
			TileEntityFlag tileEntityFlag = new TileEntityFlag(guerrillas);
			tileEntityFlag.respawncycle = 20;
			par1World.setTileEntity(par1+7, par2+1, par3+8,tileEntityFlag);
			par1World.setBlock(par1+9, par2+1, par3+10, Blocks.furnace);
			par1World.setBlock(par1+10, par2+1, par3+9, Blocks.furnace);
			par1World.setBlock(par1+6, par2+1, par3+5, Blocks.chest);
			par1World.setBlock(par1+6, par2+1, par3+6, Blocks.chest);
			par1World.setBlock(par1+9, par2+1, par3+5, Blocks.chest);
			par1World.setBlock(par1+9, par2+1, par3+6, Blocks.chest);
			par1World.setBlock(par1+5, par2+1, par3+9, Blocks.crafting_table);
			par1World.setBlock(par1+6, par2+1, par3+10, Blocks.crafting_table);
			par1World.setBlock(par1+6, par2+2, par3+10, Blocks.crafting_table);

			TileEntityChest Chest;
			TileEntityChest Chest2;
			Chest = (TileEntityChest) par1World.getTileEntity(par1+6, par2+1, par3+5);
			Chest2 = (TileEntityChest) par1World.getTileEntity(par1+6, par2+1, par3+6);
			for (int i = 0; i < 5; ++i){
				int suro = par1World.rand.nextInt(27);
				Chest.setInventorySlotContents(suro, new ItemStack(GVCUtils.fn_ak74));
			}
			for (int i = 0; i < 20; ++i){
				int suro2 = par1World.rand.nextInt(27);
				Chest2.setInventorySlotContents(suro2, new ItemStack(GVCUtils.fn_magazine_545,2,0));
			}
			TileEntityChest Chest3;
			Chest3 = (TileEntityChest) par1World.getTileEntity(par1+9, par2+1, par3+5);
			for (int i = 1; i < 5; ++i){
				int suro3 = par1World.rand.nextInt(27);
				Chest3.setInventorySlotContents(suro3, new ItemStack(Items.apple,5,0));
			}
			TileEntityChest Chest4;
			Chest4 = (TileEntityChest) par1World.getTileEntity(par1+9, par2+1, par3+6);
			for (int i = 1; i < 5; ++i){
				int suro2 = par1World.rand.nextInt(27);
				Chest4.setInventorySlotContents(suro2, new ItemStack(Items.bread,5,0));
			}






			par1World.setBlock(par1+0, par2+0, par3+0, Blocks.grass);
		}//if end
		//end
	}

}