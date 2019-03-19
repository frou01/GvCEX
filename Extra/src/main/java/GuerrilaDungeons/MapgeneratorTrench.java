package GuerrilaDungeons;


import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureStart;

public class MapgeneratorTrench extends MapGenStructure {

	public MapgeneratorTrench(){
		super();
	}

	@Override
	public String func_143025_a() {
		return "TestGenerator";
	}

	@Override
	protected boolean canSpawnStructureAtCoords(int x, int z) {
		return worldObj.getBiomeGenForCoords(x * 16,z * 16) != BiomeGenBase.ocean && worldObj.getBiomeGenForCoords(x * 16,z * 16) != BiomeGenBase.deepOcean &&  worldObj.getBiomeGenForCoords(x * 16,z * 16) != BiomeGenBase.river && worldObj.getBiomeGenForCoords(x * 16,z * 16) != BiomeGenBase.frozenOcean && worldObj.getBiomeGenForCoords(x * 16,z * 16) != BiomeGenBase.frozenRiver && x % 3 == 0 && z % 3 == 0 && rand.nextInt(10) == 0;
	}

	@Override
	protected StructureStart getStructureStart(int x, int z) {
		return new StructuresStartDungeontrench(this.worldObj, this.rand, x, z);
	}
}
