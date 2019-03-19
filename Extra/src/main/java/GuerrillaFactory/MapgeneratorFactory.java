package GuerrillaFactory;


import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureStart;

public class MapgeneratorFactory extends MapGenStructure {

	public MapgeneratorFactory(){
		super();
	}

	@Override
	public String func_143025_a() {
		return "TestGenerator";
	}

	@Override
	protected boolean canSpawnStructureAtCoords(int x, int z) {
		return worldObj.getBiomeGenForCoords(x * 16, z * 16) != BiomeGenBase.ocean && worldObj.getBiomeGenForCoords(x * 16, z * 16) != BiomeGenBase.deepOcean && (x % 9 == 0 && z % 9 == 0 && rand.nextInt(48) == 0/* || x == 0 && z == 0*/);
	}

	@Override
	protected StructureStart getStructureStart(int x, int z) {
		return new StructuresStartDungeonFactory(this.worldObj, this.rand, x, z);
	}
}
