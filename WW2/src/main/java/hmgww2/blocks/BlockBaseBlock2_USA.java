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


public class BlockBaseBlock2_USA extends Block
{
    @SideOnly(Side.CLIENT)
    private IIcon TopIcon;
 
    @SideOnly(Side.CLIENT)
    private IIcon SideIcon;
    public BlockBaseBlock2_USA() {
    	super(Material.iron);
        setHardness(50F);
        setResistance(2000.0F);
        setStepSound(Block.soundTypeStone);
    }
    
    public void onBlockHarvested(World p_149681_1_, int p_149681_2_, int p_149681_3_, int p_149681_4_, int p_149681_5_, EntityPlayer p_149681_6_)
    {
    }
    
    
    public void onBlockAdded(World par1World, int par1, int par2, int par3)
    {
    	for (int l = 1; l < 64; l++) {
    		if((par1World.getBlock(par1 + 0, par2 - l, par3 + 0) == Blocks.air
    				||par1World.getBlock(par1 + 0, par2 - l, par3 + 0) == Blocks.water
    				||par1World.getBlock(par1 + 0, par2 - l, par3 + 0) == Blocks.flowing_water
    				||par1World.getBlock(par1 + 0, par2 - l, par3 + 0) == Blocks.lava
    				||par1World.getBlock(par1 + 0, par2 - l, par3 + 0) == Blocks.flowing_lava
    				)
    				||(par1World.getBlock(par1 + 31, par2 - l, par3 + 31) == Blocks.air
    				||par1World.getBlock(par1 + 31, par2 - l, par3 + 31) == Blocks.water
    				||par1World.getBlock(par1 + 31, par2 - l, par3 + 31) == Blocks.flowing_water
    				||par1World.getBlock(par1 + 31, par2 - l, par3 + 31) == Blocks.flowing_water
    				||par1World.getBlock(par1 + 31, par2 - l, par3 + 31) == Blocks.flowing_lava
    				)||(par1World.getBlock(par1 + 31, par2 - l, par3 + 0) == Blocks.air
    				||par1World.getBlock(par1 + 31, par2 - l, par3 + 0) == Blocks.water
    				||par1World.getBlock(par1 + 31, par2 - l, par3 + 0) == Blocks.flowing_water
    				||par1World.getBlock(par1 + 31, par2 - l, par3 + 0) == Blocks.flowing_water
    				||par1World.getBlock(par1 + 31, par2 - l, par3 + 0) == Blocks.flowing_lava
    				)||(par1World.getBlock(par1 + 0, par2 - l, par3 + 31) == Blocks.air
    				||par1World.getBlock(par1 + 0, par2 - l, par3 + 31) == Blocks.water
    				||par1World.getBlock(par1 + 0, par2 - l, par3 + 31) == Blocks.flowing_water
    				||par1World.getBlock(par1 + 0, par2 - l, par3 + 31) == Blocks.flowing_water
    				||par1World.getBlock(par1 + 0, par2 - l, par3 + 31) == Blocks.flowing_lava)){
    			for (int i0 = 0; i0 < 32; ++i0){
            			for (int i2 = 0; i2 < 32; ++i2){
            				par1World.setBlock(par1+i0, par2-l, par3+i2, Blocks.stone);
            			}
            	}
    		}else{
    			break;
    		}
    	}
    	
        	for (int i0 = 0; i0 < 32; ++i0){
        		for (int i1 = 0; i1 < 16; ++i1){
        			for (int i2 = 0; i2 < 32; ++i2){
        				if(!(par1World.getBlock(par1+i0, par2+i1, par3+i2) instanceof BlockFlagBase))
        				{
        			par1World.setBlock(par1+i0, par2+i1, par3+i2, Blocks.air);
        				}
        			}
        		}
        	}
        	for (int i0 = 0; i0 < 32; ++i0){
        			for (int i2 = 0; i2 < 32; ++i2){
        				{
        			par1World.setBlock(par1+i0, par2+0, par3+i2, Blocks.dirt);
        			par1World.setBlock(par1+i0, par2-1, par3+i2, Blocks.dirt);
        			par1World.setBlock(par1+i0, par2-2, par3+i2, Blocks.dirt);
        				}
        			}
        	}
        	
        	
        	this.BuildTent(par1World, par1+22, par2+1, par3+1);
        	
        	this.BuildTower(par1World, par1+1, par2+1, par3+1);
        	this.BuildTower(par1World, par1+28, par2+1, par3+28);
        	
        	this.BuildBox(par1World, par1+5, par2+1, par3+1);
        	this.BuildBox(par1World, par1+10, par2+1, par3+1);
        	this.BuildBox(par1World, par1+15, par2+1, par3+1);
        	this.BuildBox(par1World, par1+14, par2+1, par3+29);
        	this.BuildBox(par1World, par1+19, par2+1, par3+29);
        	this.BuildBox(par1World, par1+24, par2+1, par3+29);
        	
        	
        	
        	par1World.setBlock(par1+15, par2+1, par3+15, mod_GVCWW2.b_flag2_usa);
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
    					par1World.setBlock(par1+i0, par2+5, par3+i2, Blocks.wool,13,2);
    				}
    			}
    	}
    	par1World.setBlock(par1+1, par2+4, par3+1, Blocks.glowstone);
    	par1World.setBlock(par1+1, par2+4, par3+11, Blocks.glowstone);
    	par1World.setBlock(par1+7, par2+4, par3+1, Blocks.glowstone);
    	par1World.setBlock(par1+7, par2+4, par3+11, Blocks.glowstone);
    }
    
    public void BuildTower(World par1World, int par1, int par2, int par3){
    	for (int i0 = 0; i0 < 6; ++i0){
    		for (int i1 = 0; i1 < 3; ++i1){
    			for (int i2 = 0; i2 < 3; ++i2){
    				{
    					par1World.setBlock(par1+i1, par2+i0, par3+i2, Blocks.fence);
    				}
    			}
    		}
    		par1World.setBlock(par1+1, par2+i0, par3+1, Blocks.air);
    	}
    	for (int i1 = 0; i1 < 3; ++i1){
			for (int i2 = 0; i2 < 3; ++i2){
				{
					par1World.setBlock(par1+i1, par2+6, par3+i2, Blocks.planks,0,2);
					par1World.setBlock(par1+i1, par2+7, par3+i2, Blocks.fence);
				}
			}
		}
    	par1World.setBlock(par1+3, par2+6, par3+1, Blocks.wool,11,2);
    	par1World.setBlock(par1+4, par2+6, par3+1, Blocks.wool,14,2);
    	par1World.setBlock(par1+1, par2+9, par3+1, Blocks.air);
    }
    
    public void BuildBox(World par1World, int par1, int par2, int par3){
    	for (int i0 = 0; i0 < 3; ++i0){
    		for (int i1 = 0; i1 < 2; ++i1){
    			for (int i2 = 0; i2 < 2; ++i2){
    				{
    					par1World.setBlock(par1+i0, par2+i1, par3+i2, Blocks.iron_block);
    				}
    			}
    		}
    	}
    }
}