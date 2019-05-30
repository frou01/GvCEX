package hmgww2.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.SideOnly;
import hmgww2.mod_GVCWW2;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.util.IIcon;
//import net.minecraft.world.gen.structure.StructureStrongholdPieces;;


public class BlockBaseBlock4_JPN extends Block {
	@SideOnly(Side.CLIENT)
	private IIcon TopIcon;

	@SideOnly(Side.CLIENT)
	private IIcon SideIcon;

	public BlockBaseBlock4_JPN() {
		super(Material.iron);
		setHardness(50F);
		setResistance(2000.0F);
		setStepSound(Block.soundTypeStone);
	}

	public void onBlockHarvested(World p_149681_1_, int p_149681_2_, int p_149681_3_, int p_149681_4_, int p_149681_5_,
			EntityPlayer p_149681_6_) {
	}

	public void onBlockAdded(World par1World, int par1, int par2, int par3) {
		int umi = 0;
		for (int i0 = 0; i0 < 64; ++i0) {
			for (int i1 = 0; i1 < 4; ++i1) {
				for (int i2 = 0; i2 < 32; ++i2) {
					if (par1World.getBlock(par1 + i0, par2 - i1, par3 + i2 + 32) == Blocks.water) {
						++umi;
					}
				}
			}
		}
		if (umi > 2048) {
			for (int i0 = 0; i0 < 64; ++i0) {
				for (int i1 = 0; i1 < 16; ++i1) {
					for (int i2 = 0; i2 < 64; ++i2) {
						{
							par1World.setBlock(par1 + i0, par2 + i1, par3 + i2, Blocks.air);
						}
					}
				}
			}
			for (int l = 0; l < 32; l++) {
				{
					for (int i0 = 0; i0 < 64; ++i0) {
						for (int i2 = 0; i2 < 32; ++i2) {
							par1World.setBlock(par1 + i0, par2 - l, par3 + i2, Blocks.stone);
						}
					}
					for (int i0 = 0; i0 < 16; ++i0) {
						for (int i2 = 0; i2 < 32; ++i2) {
							par1World.setBlock(par1 + i0, par2 - l, par3 + i2+31, Blocks.stone);
						}
					}
					for (int i0 = 0; i0 < 16; ++i0) {
						for (int i2 = 0; i2 < 32; ++i2) {
							par1World.setBlock(par1 + i0+48, par2 - l, par3 + i2+31, Blocks.stone);
						}
					}
				}
			}

			this.BuildTent(par1World, par1 + 53, par2 + 1, par3 + 7);
			this.BuildTent(par1World, par1 + 53, par2 + 1, par3 + 24);
			
			this.BuildTent2(par1World, par1 + 16, par2 + 1, par3 + 16);
			this.BuildTent2(par1World, par1 + 33, par2 + 1, par3 + 16);

			this.BuildTower(par1World, par1 + 1, par2 + 1, par3 + 1);
			this.BuildTower(par1World, par1 + 60, par2 + 1, par3 + 1);
			this.BuildTower(par1World, par1 + 60, par2 + 1, par3 + 60);

			this.BuildBox(par1World, par1 + 2, par2 + 1, par3 + 11);
			this.BuildBox(par1World, par1 + 2, par2 + 1, par3 + 15);
			this.BuildBox(par1World, par1 + 2, par2 + 1, par3 + 19);
			this.BuildBox(par1World, par1 + 2, par2 + 1, par3 + 23);
			this.BuildBox(par1World, par1 + 2, par2 + 1, par3 + 27);
			this.BuildBox(par1World, par1 + 2, par2 + 1, par3 + 31);

			par1World.setBlock(par1 + 56, par2 + 1, par3 + 42, mod_GVCWW2.b_flag4_jpn);
			par1World.setBlock(par1 + 6, par2 + 1, par3 + 60, mod_GVCWW2.b_flag4_jpn);
		}else{
			par1World.setBlock(par1 + 0, par2 + 0, par3 + 0, Blocks.air);
		}
	}

	public void BuildTent(World par1World, int par1, int par2, int par3){
    	for (int i0 = 0; i0 < 9; ++i0){
    		for (int i1 = 0; i1 < 5; ++i1){
    			for (int i2 = 0; i2 < 13; ++i2){
    				{
    					par1World.setBlock(par1+i0, par2+i1, par3+i2, Blocks.stonebrick);
    				}
    			}
    		}
    	}
    	for (int i0 = 0; i0 < 8; ++i0){
    		for (int i1 = 0; i1 < 4; ++i1){
    			for (int i2 = 0; i2 < 11; ++i2){
    				{
    					par1World.setBlock(par1+i0, par2+i1, par3+i2+1, Blocks.air);
    				}
    			}
    		}
    	}
    	for (int i0 = 0; i0 < 9; ++i0){
    			for (int i2 = 0; i2 < 13; ++i2){
    				{
    					par1World.setBlock(par1+i0, par2+5, par3+i2, Blocks.wool,0,2);
    				}
    			}
    	}
    	par1World.setBlock(par1+1, par2+4, par3+1, Blocks.glowstone);
    	par1World.setBlock(par1+1, par2+4, par3+11, Blocks.glowstone);
    	par1World.setBlock(par1+7, par2+4, par3+1, Blocks.glowstone);
    	par1World.setBlock(par1+7, par2+4, par3+11, Blocks.glowstone);
    }
	
	public void BuildTent2(World par1World, int par1, int par2, int par3){
    	for (int i0 = 0; i0 < 15; ++i0){
    		for (int i1 = 0; i1 < 6; ++i1){
    			for (int i2 = 0; i2 < 10; ++i2){
    				{
    					par1World.setBlock(par1+i0, par2+i1, par3+i2, Blocks.stonebrick);
    				}
    			}
    		}
    	}
    	for (int i0 = 0; i0 < 13; ++i0){
    		for (int i1 = 0; i1 < 5; ++i1){
    			for (int i2 = 0; i2 < 9; ++i2){
    				{
    					par1World.setBlock(par1+i0+1, par2+i1, par3+i2+1, Blocks.air);
    				}
    			}
    		}
    	}
    	for (int i0 = 0; i0 < 15; ++i0){
    			for (int i2 = 0; i2 < 10; ++i2){
    				{
    					par1World.setBlock(par1+i0, par2+6, par3+i2, Blocks.wool,0,2);
    				}
    			}
    	}
    	par1World.setBlock(par1+1, par2+5, par3+1, Blocks.glowstone);
    	par1World.setBlock(par1+1, par2+5, par3+8, Blocks.glowstone);
    	par1World.setBlock(par1+13, par2+5, par3+1, Blocks.glowstone);
    	par1World.setBlock(par1+13, par2+5, par3+8, Blocks.glowstone);
    	par1World.setBlock(par1+7, par2+5, par3+1, Blocks.glowstone);
    	par1World.setBlock(par1+8, par2+5, par3+1, Blocks.glowstone);
    	par1World.setBlock(par1+7, par2+5, par3+8, Blocks.glowstone);
    	par1World.setBlock(par1+8, par2+5, par3+8, Blocks.glowstone);
    }

	public void BuildTower(World par1World, int par1, int par2, int par3) {
		for (int i0 = 0; i0 < 8; ++i0) {
			for (int i1 = 0; i1 < 3; ++i1) {
				for (int i2 = 0; i2 < 3; ++i2) {
					{
						par1World.setBlock(par1 + i1, par2 + i0, par3 + i2, Blocks.fence);
					}
				}
			}
			par1World.setBlock(par1 + 1, par2 + i0, par3 + 1, Blocks.air);
		}
		for (int i1 = 0; i1 < 3; ++i1) {
			for (int i2 = 0; i2 < 3; ++i2) {
				{
					par1World.setBlock(par1 + i1, par2 + 8, par3 + i2, Blocks.planks, 0, 2);
					par1World.setBlock(par1 + i1, par2 + 9, par3 + i2, Blocks.fence);
				}
			}
		}
		par1World.setBlock(par1 + 3, par2 + 8, par3 + 1, Blocks.wool, 0, 2);
		par1World.setBlock(par1 + 4, par2 + 8, par3 + 1, Blocks.wool, 0, 2);
		par1World.setBlock(par1 + 1, par2 + 9, par3 + 1, Blocks.air);
	}

	public void BuildBox(World par1World, int par1, int par2, int par3) {
		for (int i0 = 0; i0 < 3; ++i0) {
			for (int i1 = 0; i1 < 2; ++i1) {
				for (int i2 = 0; i2 < 2; ++i2) {
					{
						par1World.setBlock(par1 + i0, par2 + i1, par3 + i2, Blocks.iron_block);
					}
				}
			}
		}
	}
}