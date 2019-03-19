package hmggvcmob.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import hmggvcmob.entity.friend.EntitySoBase;
import net.minecraft.entity.Entity;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

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
}
