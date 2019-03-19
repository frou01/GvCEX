package MetalGearDungeons;


import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureStart;

public class MapgeneratorMetalGear extends MapGenStructure {

	public MapgeneratorMetalGear(){
		super();
	}

	@Override
	public String func_143025_a() {
		return "TestGenerator";
	}

	@Override
	protected boolean canSpawnStructureAtCoords(int x, int z) {
		return x == 0 && z == 0;
	}

	@Override
	protected StructureStart getStructureStart(int x, int z) {
		return new StructuresStartDungeonMetalGear(this.worldObj, this.rand, x, z);
	}
}
