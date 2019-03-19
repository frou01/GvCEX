package hmggvcmob;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenDungeons;
import net.minecraft.world.gen.feature.WorldGenMinable;
import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.WorldProviderHell;

import static hmggvcmob.GVCMobPlus.cfg_creatCamp;

public class WWorld extends WorldGenDungeons implements IWorldGenerator {


	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	{
		switch(world.provider.dimensionId)
		{
			case -1: generateNether(world, random, chunkX * 16, chunkZ * 16);
			case 0: generateSurface(world, random, chunkX * 16, chunkZ * 16);
			case 1: generateEnd(world, random, chunkX * 16, chunkZ * 16);
		}
//		if (worldchunkmanager != null)
//		{
//			if (world.provider instanceof WorldProviderSurface)
//			{
//				this.generateOre(world, random, chunkX << 4, chunkZ << 4);
//			}
//
//
//
//			if (world.provider instanceof WorldProviderHell)
//			{
//				this.generateOreHell(world, random, chunkX << 4, chunkZ << 4);
//			}
//
//
//			if (world.provider instanceof WorldProviderEnd)
//			{
//				this.generateOreEnd(world, random, chunkX << 4, chunkZ << 4);
//			}
//
//	 		/*switch (world.provider.dimensionId)
//	 	    {
//	 	    case -1:
//	 	    	generateOreHell(world, random, chunkX * 16, chunkZ * 16);
//	 	    case 0:
//	 	    	generateOre(world, random, chunkX * 16, chunkZ * 16);
//	 	    case 1:
//	 	    	generateOreEnd(world, random, chunkX * 16, chunkZ * 16);
//	 	    }*/
//		}



	}



	private void generateSurface(World world, Random random, int x, int z)
	{
		//int i = world.rand.nextInt(4);
	 		/*

	 		 */
		//for (int i = 0; i < 1; ++i)
		//for (int e = world.rand.nextInt(5); 0 < e; e--)
		{

			//int genX =  x + random.nextInt(GVCMobPlus.cfg_creatCamp);
			//int genZ =  z + random.nextInt(GVCMobPlus.cfg_creatCamp);
			int genX =  x + random.nextInt(16);
			int genZ =  z + random.nextInt(16);
			int genY =  world.getHeightValue(genX, genZ);


			int s = world.rand.nextInt(8)+1;
			//if(world.rand.nextInt(GVCMobPlus.cfg_creatCamp)==0)
			if(genY > 60)
			{
				//if(world.getBlockLightValue(genX, genY, genZ) > 11 && world.isAirBlock(genX, genY, genZ))
				{
					generate(world, random, genX, genY, genZ);
				}
				//(new WorldGenMinable(GVCMobPlus.fn_Gcamp,0, 3, Blocks.grass)).generate(world, random, genX, genY, genZ);
			}
		}
	}


	private void generateNether(World world, Random random, int x, int z)
	{
		//int i = world.rand.nextInt(4);
	 		/*

	 		 */
		//for (int i = 0; i < 1; ++i)
		//for (int e = world.rand.nextInt(5); 0 < e; e--)
		{

			//int genX =  x + random.nextInt(GVCMobPlus.cfg_creatCamp);
			//int genZ =  z + random.nextInt(GVCMobPlus.cfg_creatCamp);
			int genX =  x + random.nextInt(16);
			int genZ =  z + random.nextInt(16);
			int genY =  world.getHeightValue(genX, genZ);


			//if(world.rand.nextInt(GVCMobPlus.cfg_creatCamp)==0)
			if(genY > 10)
			{
				//if(world.getBlockLightValue(genX, genY, genZ) > 11 && world.isAirBlock(genX, genY, genZ))
				{
					generate(world, random, genX, genY, genZ);
				}
				//(new WorldGenMinable(GVCMobPlus.fn_Gcamp,0, 3, Blocks.grass)).generate(world, random, genX, genY, genZ);
			}
		}
	}


	private void generateEnd(World world, Random random, int x, int z)
	{
			/*for (int i = 0; i < 30; ++i)
	 		{
	 			int genX =  x + random.nextInt(16);
	 			int genY = 10 + random.nextInt(50);
	 			int genZ =  z + random.nextInt(16);


	 			/*
	118
	 			(new WorldGenMinable(Blocks.iron_block, 0, 4, Blocks.end_stone)).generate(world, random, genX, genY, genZ);
	 		} */
	}
	@Override
	public boolean generate(World par1World, Random random, int par1, int par2, int par3) {
		//for (int i0 = 0; i0 < 32; ++i0){
		//for (int i1 = 0; i1 < 32; ++i1){
		//for (int i2 = 0; i2 < 32; ++i2){

		if(!(par1World.getBlock(par1, par2, par3) == Blocks.wool)){
			if(random.nextInt(cfg_creatCamp) == 0) {
				if (par1World.getBlock(par1, par2 - 1, par3) == Blocks.grass
						|| par1World.getBlock(par1, par2 - 1, par3) == Blocks.sand) {
					int rnd = random.nextInt(3);
					switch (rnd) {
						case 0: {
							par1World.setBlock(par1 + 0, par2 + 0, par3 + 0, GVCMobPlus.fn_Gcamp2);
							break;
						}
						case 1: {
							par1World.setBlock(par1 + 0, par2 + 0, par3 + 0, GVCMobPlus.fn_Gcamp);
							break;
						}
						case 2: {
							par1World.setBlock(par1 + 0, par2 + 0, par3 + 0, GVCMobPlus.fn_Gcamp3);
						}
					}
				}
			}
			//}
			//}
		}
		//}
		return true;

	}
}
