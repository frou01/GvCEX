package hmggvcmob.event;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import hmggvcmob.entity.IflagBattler;
import hmggvcmob.camp.CampObjAndPos;
import hmggvcmob.entity.IHasVehicleGacha;
import hmggvcmob.entity.VehicleSpawnGachaOBJ;
import hmggvcmob.entity.friend.EntitySoBase;
import hmggvcmob.world.WorldSavedData_Flag;
import net.minecraft.util.MathHelper;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;

import java.util.Random;

public class GVCMSpawnEvent {

//
//
//	@SubscribeEvent
//	public void SpawnEvent(LivingSpawnEvent event){
//		System.out.println("debug");
//		EntityLivingBase entity = event.entityLiving;
//		if(entity != null){
//			if(entity instanceof EntitySoBase || entity instanceof EntityGBase){
//				Block blockEntitySpawning = event.world.getBlock((int)event.x,(int)event.y,(int)event.z);
//				if(blockEntitySpawning.isBlockNormalCube());
//				int var1 = MathHelper.floor_double(entity.posX);
//		        int var2 = MathHelper.floor_double(entity.boundingBox.minY);
//		        int var3 = MathHelper.floor_double(entity.posZ);
//		        if(var2 < entity.worldObj.getHeightValue(var1, var3) - 5 && entity.worldObj.rand.nextInt(8) != 0){
//		        	if(!entity.worldObj.isRemote){
//		        		int posy = entity.worldObj.getHeightValue(var1, var3) + 2;
//		        		entity.setPositionAndUpdate(var1, posy, var3);
//		        	}
//		        }
//			}
//		}
//
//	}

    //	@Event.HasResult
//	public static class CheckSpawn extends LivingSpawnEvent
//	{
//		public CheckSpawn(EntityLiving entity, World world, float x, float y, float z)
//		{
//			super(entity, world, x, y, z);
//		}
//	}

    @SubscribeEvent
    public void SpawnEvent(EntityJoinWorldEvent event) {
        if(event.entity instanceof EntitySoBase && !event.entity.worldObj.isRemote){
            EntitySoBase.spawnedcount = 0;
            for(Object te : event.entity.worldObj.loadedEntityList){
                if(te instanceof EntitySoBase)
                    EntitySoBase.spawnedcount++;
            }
        }
    }
    @SubscribeEvent
    public void checkspawnSpawnevent(LivingSpawnEvent.CheckSpawn event) {
        event.setResult(Event.Result.DEFAULT);
        Chunk spawningChunk = event.world.getChunkFromBlockCoords(
                MathHelper.floor_double(event.x),
                MathHelper.floor_double(event.z));
        WorldSavedData_Flag worldSavedData_flag = WorldSavedData_Flag.get(event.world);
        CampObjAndPos campObj = worldSavedData_flag.campObjHashMap.get(spawningChunk.getChunkCoordIntPair());
        if (campObj != null) {
            if (event.entityLiving instanceof IflagBattler) {
                if (((IflagBattler) event.entityLiving).isThisIgnoreSpawnCamp(campObj.campObj)) {
                    event.setResult(Event.Result.DENY);
                }
            } else {
                event.setResult(Event.Result.DENY);
            }
        }
    }
    @SubscribeEvent
    public void specialSpawnevent(LivingSpawnEvent.SpecialSpawn event) {
        if(event.entityLiving instanceof IHasVehicleGacha){//�ԗ��K�`���̂�����
            Random rand = new Random();
            if(((IHasVehicleGacha) event.entityLiving).getVehicleGacha_rate_sum() >0) {
                int currentRate = rand.nextInt(((IHasVehicleGacha) event.entityLiving).getVehicleGacha_rate_sum());
                for (VehicleSpawnGachaOBJ gachaOBJ : ((IHasVehicleGacha) event.entityLiving).getVehicleGacha()) {
                    System.out.println(currentRate);
                    if (currentRate < gachaOBJ.rate) {
                        if(gachaOBJ.vehicleName != null)((IHasVehicleGacha) event.entityLiving).setVehicleName(gachaOBJ.vehicleName);
                        break;
                    }
                    currentRate -= gachaOBJ.rate;
                }
            }
        }
    }
}
