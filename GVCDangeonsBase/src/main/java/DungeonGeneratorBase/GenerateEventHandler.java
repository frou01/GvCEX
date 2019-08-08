package DungeonGeneratorBase;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraftforge.event.terraingen.InitNoiseGensEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;

public class GenerateEventHandler {
	MapGenStructure_forGVC mapGenStructure;
	public GenerateEventHandler(MapGenStructure_forGVC mapGenStructure) {
		this.mapGenStructure = mapGenStructure;
	}

	@SubscribeEvent
	public void onInitNoiseGensEvent(InitNoiseGensEvent event) {

	}
	@SubscribeEvent
	public void onPopulateChunkEvent(PopulateChunkEvent.Pre event) {
	}
	@SubscribeEvent
	public void onPopulateChunkEvent(PopulateChunkEvent.Populate event) {
		if(event.type == PopulateChunkEvent.Populate.EventType.DUNGEON) {
			try {
				mapGenStructure.func_151539_a(event.chunkProvider, event.world, event.chunkX, event.chunkZ, null);
				mapGenStructure.generateStructuresInChunk(event.world, event.rand, event.chunkX, event.chunkZ);
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}
	@SubscribeEvent
	public void onPopulateChunkEvent(PopulateChunkEvent.Post event) {
	}
}
