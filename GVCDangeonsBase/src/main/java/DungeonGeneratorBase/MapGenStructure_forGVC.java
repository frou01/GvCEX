package DungeonGeneratorBase;

import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureStart;
import net.minecraftforge.common.BiomeManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static DungeonGeneratorBase.mod_DungeonGeneratorBase.getNextDungeon;

public class MapGenStructure_forGVC extends MapGenStructure {
	
	public final List field_151546_e;
	private boolean ranBiomeCheck;
	private int field_82672_i;
	private double field_82671_h;
	private ChunkCoordIntPair[] structureCoords;
	
	public DungeonData_withSettings currentDungeon = null;
	
	public MapGenStructure_forGVC(){
		super();
		this.structureCoords = new ChunkCoordIntPair[3];
		this.field_82671_h = 32.0D;
		this.field_82672_i = 3;
		this.field_151546_e = new ArrayList();
		BiomeGenBase[] abiomegenbase = BiomeGenBase.getBiomeGenArray();
		int i = abiomegenbase.length;
		
		for (int j = 0; j < i; ++j)
		{
			BiomeGenBase biomegenbase = abiomegenbase[j];
			
			if (biomegenbase != null && biomegenbase.rootHeight > 0.0F && !BiomeManager.strongHoldBiomesBlackList.contains(biomegenbase))
			{
				this.field_151546_e.add(biomegenbase);
			}
		}
		for (BiomeGenBase biome : BiomeManager.strongHoldBiomes)
		{
			if (!this.field_151546_e.contains(biome))
			{
				this.field_151546_e.add(biome);
			}
		}
	}
	
	@Override
	public String func_143025_a() {
		return "GVCGenerator";
	}
	
	@Override
	protected boolean canSpawnStructureAtCoords(int x, int z) {
		return getNextDungeon(this,worldObj,x,z,rand);
	}
	
	public boolean canSpawnStructureAtCoords_astrongHold(int p_75047_1_, int p_75047_2_) {
		if (!this.ranBiomeCheck)
		{
			Random random = new Random();
			random.setSeed(this.worldObj.getSeed());
			double d0 = random.nextDouble() * Math.PI * 2.0D;
			int l = 1;
			
			for (int i1 = 0; i1 < this.structureCoords.length; ++i1)
			{
				double d1 = (1.25D * (double)l + random.nextDouble()) * this.field_82671_h * (double)l;
				int j1 = (int)Math.round(Math.cos(d0) * d1);
				int k1 = (int)Math.round(Math.sin(d0) * d1);
				ChunkPosition chunkposition = this.worldObj.getWorldChunkManager().findBiomePosition((j1 << 4) + 8, (k1 << 4) + 8, 112, this.field_151546_e, random);
				
				if (chunkposition != null)
				{
					j1 = chunkposition.chunkPosX >> 4;
					k1 = chunkposition.chunkPosZ >> 4;
				}
				
				this.structureCoords[i1] = new ChunkCoordIntPair(j1, k1);
				d0 += (Math.PI * 2D) * (double)l / (double)this.field_82672_i;
				
				if (i1 == this.field_82672_i)
				{
					l += 2 + random.nextInt(5);
					this.field_82672_i += 1 + random.nextInt(2);
				}
			}
			
			this.ranBiomeCheck = true;
		}
		
		ChunkCoordIntPair[] achunkcoordintpair = this.structureCoords;
		int l1 = achunkcoordintpair.length;
		
		for (int k = 0; k < l1; ++k)
		{
			ChunkCoordIntPair chunkcoordintpair = achunkcoordintpair[k];
			
			if (p_75047_1_ == chunkcoordintpair.chunkXPos && p_75047_2_ == chunkcoordintpair.chunkZPos)
			{
				return true;
			}
		}
		
		return false;
	}
	@Override
	protected StructureStart getStructureStart(int x, int z) {
		System.out.println("generated pos:");
		System.out.println("x" + x);
		System.out.println("z" + z);
		return new StructuresStartDungeonBase(currentDungeon,this.worldObj, this.rand, x, z);
	}
}
