package GuerrillaBigTrench;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.terraingen.InitNoiseGensEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;

public class GenerateEventHandler {
	MapgeneratorBigTrench mapGenBaseHouses = new MapgeneratorBigTrench();

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
				mapGenBaseHouses.func_151539_a(event.chunkProvider, event.world, event.chunkX, event.chunkZ, null);
				mapGenBaseHouses.generateStructuresInChunk(event.world, event.rand, event.chunkX, event.chunkZ);
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}
	@SubscribeEvent
	public void onPopulateChunkEvent(PopulateChunkEvent.Post event) {
	}
}
