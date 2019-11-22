package hmggvcmob.world;

import hmggvcmob.GVCMobPlus;
import hmggvcmob.camp.CampObj;
import hmggvcmob.camp.CampObjAndPos;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapStorage;

import javax.vecmath.Vector3d;
import java.util.*;

import static hmggvcmob.GVCMobPlus.campsHash;
import static hmggvcmob.GVCMobPlus.supply;

public class WorldSavedData_Flag extends WorldSavedData {
	private static final String DATA_NAME = GVCMobPlus.modid + "_FlagData";
	public HashMap<ChunkCoordIntPair, CampObjAndPos> campObjHashMap = new HashMap<>();

	public WorldSavedData_Flag(String p_i1677_1_)
	{
		super(p_i1677_1_);
		setDirty(true);
	}

	public WorldSavedData_Flag() {
		super(DATA_NAME);
		setDirty(true);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		int num,cnt=0;
		num = tag.getInteger("CampObjNum");
		for(;cnt < num;cnt++) {
			campObjHashMap.put(new ChunkCoordIntPair(tag.getInteger("CampObjX"+cnt),tag.getInteger("CampObjZ"+cnt))
					, new CampObjAndPos(new int[]{tag.getInteger("CampObjBlockX"+cnt),tag.getInteger("CampObjBlockY"+cnt),tag.getInteger("CampObjBlockZ"+cnt)},
							campsHash.get(tag.getString("CampObj" + cnt))));
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		System.out.println("debug");
		int num,cnt=0;
		tag.setInteger("CampObjNum",campObjHashMap.size());
		for(Map.Entry<ChunkCoordIntPair, CampObjAndPos> entry : campObjHashMap.entrySet()){
			tag.setInteger("CampObjX"+cnt,entry.getKey().chunkXPos);
			tag.setInteger("CampObjZ"+cnt,entry.getKey().chunkZPos);
			tag.setInteger("CampObjBlockX"+cnt,entry.getValue().flagPos[0]);
			tag.setInteger("CampObjBlockY"+cnt,entry.getValue().flagPos[1]);
			tag.setInteger("CampObjBlockZ"+cnt,entry.getValue().flagPos[2]);
			tag.setString("CampObj"+cnt,entry.getValue().campObj.campName);
			cnt++;
		}
	}
	public static WorldSavedData_Flag get(World world) {
		WorldSavedData instance = world.perWorldStorage.loadData(WorldSavedData_Flag.class,DATA_NAME);
		if(instance instanceof WorldSavedData_Flag){
			return (WorldSavedData_Flag) instance;
		}else {
			if(!world.isRemote) {
				System.out.println("first");
				System.out.println("debug " + instance);
			}
			instance = new WorldSavedData_Flag();
			world.perWorldStorage.setData(DATA_NAME,instance);
			return (WorldSavedData_Flag) world.perWorldStorage.loadData(WorldSavedData_Flag.class,DATA_NAME);
		}
	}
}
