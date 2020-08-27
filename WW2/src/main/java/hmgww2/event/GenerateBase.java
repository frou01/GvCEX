package hmgww2.event;

import cpw.mods.fml.common.IWorldGenerator;
import hmgww2.mod_GVCWW2;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.Random;

public class GenerateBase implements IWorldGenerator {


	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		WorldChunkManager worldchunkmanager = world.getWorldChunkManager();
		if (worldchunkmanager != null) {
			if (world.provider instanceof WorldProviderSurface) {
				this.generateOre(world, random, chunkX << 4, chunkZ << 4);
			}


			if (world.provider instanceof WorldProviderHell) {
				this.generateOreHell(world, random, chunkX << 4, chunkZ << 4);
			}


			if (world.provider instanceof WorldProviderEnd) {
				this.generateOreEnd(world, random, chunkX << 4, chunkZ << 4);
			}
		}
	}


	private void generateOre(World world, Random random, int x, int z) {
		{

			int genX = x + random.nextInt(16);
			int genZ = z + random.nextInt(16);
			int genY = world.getHeightValue(genX, genZ);
			if (genY > 60) {
				if (random.nextInt(mod_GVCWW2.cfg_basegenerated) == 0) {
					int i = random.nextInt(8);
					WorldBlockWW2 var1 = new WorldBlockWW2(i, 2);
					var1.setScale(1, 1, 1);
					var1.generate(world, random, genX, genY, genZ);
				}
			}
		}
		{

			int genX = x + random.nextInt(16);
			int genZ = z + random.nextInt(16);
			int genY = world.getHeightValue(genX, genZ);
			if (genY > 60) {
				if (random.nextInt(mod_GVCWW2.cfg_basegenerated2) == 0) {
					int i = random.nextInt(9);
					WorldBlockWW2 var1 = new WorldBlockWW2(i, 0);
					var1.setScale(1, 1, 1);
					var1.generate(world, random, genX, genY, genZ);
				}
			}
		}
		{

			int genX = x + random.nextInt(16);
			int genZ = z + random.nextInt(16);
			int genY = world.getHeightValue(genX, genZ);
			if (genY > 60) {
	 				/*if(random.nextInt(100) == 0 && (world.getBiomeGenForCoords(genX, genZ) == BiomeGenBase.beach
	 						||world.getBiomeGenForCoords(genX, genZ) == BiomeGenBase.desert
	 						||world.getBiomeGenForCoords(genX, genZ) == BiomeGenBase.ocean
	 						||world.getBiomeGenForCoords(genX, genZ) == BiomeGenBase.deepOcean
	 						||world.getBiomeGenForCoords(genX, genZ) == BiomeGenBase.mushroomIsland
	 						||world.getBiomeGenForCoords(genX, genZ) == BiomeGenBase.swampland))*/
				if (random.nextInt(mod_GVCWW2.cfg_basegenerated3) == 0) {
					int i = random.nextInt(4);
					WorldBlockWW2 var1 = new WorldBlockWW2(i, 1);
					var1.setScale(1, 1, 1);
					var1.generate(world, random, genX, genY, genZ);
				}
			}
		}

	}


	private void generateOreHell(World world, Random random, int x, int z) {
	}


	private void generateOreEnd(World world, Random random, int x, int z) {
	}
} 
