package GuerrillaBigTrench;


import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureStart;

public class MapgeneratorBigTrench extends MapGenStructure {

	public MapgeneratorBigTrench(){
		super();
	}

	@Override
	public String func_143025_a() {
		return "BigTrenchGenerator";
	}

	@Override
	protected boolean canSpawnStructureAtCoords(int x, int z) {
		return (worldObj.getBiomeGenForCoords(x * 16, z * 16) != BiomeGenBase.ocean && worldObj.getBiomeGenForCoords(x * 16, z * 16) != BiomeGenBase.deepOcean && (x % 16 == 0 && z % 16 == 0 && rand.nextInt(48) == 0 ))/*|| x ==-10 && z == -10*/;
	}

	@Override
	protected StructureStart getStructureStart(int x, int z) {
		return new StructuresStartDungeonBigTrench(this.worldObj, this.rand, x, z);
	}
}
