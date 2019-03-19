package minegear;

import DungeonGeneratorBase.ComponentDungeonBase;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.terraingen.InitNoiseGensEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;

public class GenerateEventHandler {
	MapGenBase mapGenBaseHouses = new MapGenBase();

	// コンストラクタ相当(getModdedMapGen or InitMapGenEvent)相当
	@SubscribeEvent
	public void onInitNoiseGensEvent(InitNoiseGensEvent event) {


	}

	// generateStructuresInChunk相当
	// 要塞や村より前のタイミングならこちら
	// ここで生成すると、要塞や村・溶岩溜まり等の後で生成される地形要素に潰される可能性がある
	//　その代わり村や要塞を避けるような判定は不要
	@SubscribeEvent
	public void onPopulateChunkEvent(PopulateChunkEvent.Pre event) {
// 通常世界(Overworld)にサンプルダンジョンを生成したいのでディメンションIDで通常世界かどうか判断する
		if(event.world.provider.dimensionId == 0) {
			// ８チャンク以内に追加構造物生成に適したチャンクがあるかを調べ、ある場合は生成する追加構造物の構成パーツを決定する
			mapGenBaseHouses.func_151539_a(event.chunkProvider, event.world, event.chunkX, event.chunkZ, null);

			//追加構造物の一部が このチャンク範囲に重複するかどうかを調べ、重複する場合は追加構造物のブロックをチャンク内に設置する
			try {
				mapGenBaseHouses.generateStructuresInChunk(event.world, event.rand, event.chunkX, event.chunkZ);
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}

	// generateStructuresInChunk相当
	// 要塞や村より後のタイミングならこちら
	// event.typeにより順番が決まる＆呼ばれなかったりするので注意
	// ここで生成するなら要塞等を潰さないように注意
	@SubscribeEvent
	public void onPopulateChunkEvent(PopulateChunkEvent.Populate event) {
//		{
//			// ８チャンク以内に追加構造物生成に適したチャンクがあるかを調べ、ある場合は生成する追加構造物の構成パーツを決定する
//			mapGenRoads.func_151539_a(event.chunkProvider, event.world, event.chunkX, event.chunkZ, null);
//
//			//追加構造物の一部が このチャンク範囲に重複するかどうかを調べ、重複する場合は追加構造物のブロックをチャンク内に設置する
//			mapGenRoads.generateStructuresInChunk(event.world, event.rand, event.chunkX, event.chunkZ);
//		}
	}

	// generateStructuresInChunk相当
	// 完全にチャンクの要素が決定された後のタイミングならこちら
	// ここで生成するなら要塞等を潰さないように注意
	@SubscribeEvent
	public void onPopulateChunkEvent(PopulateChunkEvent.Post event) {

	}

}
