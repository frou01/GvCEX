package hmgww2.event;

import java.util.Random;

import hmgww2.mod_GVCWW2;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldBlockWW2 extends WorldGenerator{

	public boolean flag = false;
	public int i;
	public int be;
	public WorldBlockWW2(int ii, int b){
		//this.flag = flag;
		this.i = ii;
		this.be = b;
	}
	
	@Override
	public boolean generate(World par1World, Random random, int par1, int par2, int par3) {
		if(be == 0){
			if(par1World.getBlock(par1, par2-1, par3) != Blocks.air
					&& par1World.getBlock(par1, par2-1, par3) != Blocks.water
					&& par1World.getBlock(par1, par2-1, par3) != Blocks.lava){
				if(i == 0){
					if(mod_GVCWW2.cfg_buildbase_usa){
		    		par1World.setBlock(par1+0, par2+0, par3+0, mod_GVCWW2.b_base2_usa);
					}
				}else if(i == 1){
					if(mod_GVCWW2.cfg_buildbase_jpn){
		    		par1World.setBlock(par1+0, par2+0, par3+0, mod_GVCWW2.b_base3_jpn);
					}
				}else if(i == 2){
					if(mod_GVCWW2.cfg_buildbase_usa){
		    		par1World.setBlock(par1+0, par2+0, par3+0, mod_GVCWW2.b_base3_usa);
					}
				}else if(i == 3){
					if(mod_GVCWW2.cfg_buildbase_ger){
		    		par1World.setBlock(par1+0, par2+0, par3+0, mod_GVCWW2.b_base2_ger);
					}
				}else if(i == 4){
					if(mod_GVCWW2.cfg_buildbase_ger){
		    		par1World.setBlock(par1+0, par2+0, par3+0, mod_GVCWW2.b_base3_ger);
					}
				}else if(i == 5){
					if(mod_GVCWW2.cfg_buildbase_rus){
		    		par1World.setBlock(par1+0, par2+0, par3+0, mod_GVCWW2.b_base2_rus);
					}
				}else if(i == 6){
					if(mod_GVCWW2.cfg_buildbase_rus){
		    		par1World.setBlock(par1+0, par2+0, par3+0, mod_GVCWW2.b_base3_rus);
					}
		    	}else{
		    		if(mod_GVCWW2.cfg_buildbase_jpn){
		    		par1World.setBlock(par1+0, par2+0, par3+0, mod_GVCWW2.b_base2_jpn);
		    		}
		    	}
			}
		}else if(be == 1){
			if(par1World.getBlock(par1, par2-1, par3) != Blocks.air
					&& par1World.getBlock(par1, par2-1, par3) != Blocks.water
					&& par1World.getBlock(par1, par2-1, par3) != Blocks.lava){
				if(i == 0){
					if(mod_GVCWW2.cfg_buildbase_usa){
		    		par1World.setBlock(par1+0, par2+0, par3+0, mod_GVCWW2.b_base4_usa);
					}
				}else if(i == 1){
					if(mod_GVCWW2.cfg_buildbase_usa){
		    		par1World.setBlock(par1+0, par2+0, par3+0, mod_GVCWW2.b_base4_usa);
					}
		    	}else{
		    		if(mod_GVCWW2.cfg_buildbase_jpn){
		    		par1World.setBlock(par1+0, par2+0, par3+0, mod_GVCWW2.b_base4_jpn);
		    		}
		    	}
			}
		}else if(be == 2){
		if(par1World.getBlock(par1, par2-1, par3) != Blocks.air
				&& par1World.getBlock(par1, par2-1, par3) != Blocks.water
				&& par1World.getBlock(par1, par2-1, par3) != Blocks.lava){
			if(i == 0){
				if(mod_GVCWW2.cfg_buildbase_usa){
	    		par1World.setBlock(par1+0, par2+0, par3+0, mod_GVCWW2.b_base_usa);
				}
			}else if(i == 1){
				if(mod_GVCWW2.cfg_buildbase_usa){
	    		par1World.setBlock(par1+0, par2+0, par3+0, mod_GVCWW2.b_base_usa);
				}
			}else if(i == 2){
				if(mod_GVCWW2.cfg_buildbase_ger){
	    		par1World.setBlock(par1+0, par2+0, par3+0, mod_GVCWW2.b_base_ger);
				}
			}else if(i == 3){
				if(mod_GVCWW2.cfg_buildbase_ger){
	    		par1World.setBlock(par1+0, par2+0, par3+0, mod_GVCWW2.b_base_ger);
				}
			}else if(i == 4){
				if(mod_GVCWW2.cfg_buildbase_rus){
	    		par1World.setBlock(par1+0, par2+0, par3+0, mod_GVCWW2.b_base_rus);
				}
			}else if(i == 5){
				if(mod_GVCWW2.cfg_buildbase_rus){
	    		par1World.setBlock(par1+0, par2+0, par3+0, mod_GVCWW2.b_base_rus);
				}
	    	}else{
	    		if(mod_GVCWW2.cfg_buildbase_jpn){
	    		par1World.setBlock(par1+0, par2+0, par3+0, mod_GVCWW2.b_base_jpn);
	    		}
	    	}
		}
	}
		return true;
	}
}
