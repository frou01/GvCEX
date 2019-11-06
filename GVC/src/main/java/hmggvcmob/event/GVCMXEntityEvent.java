package hmggvcmob.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import handmadeguns.event.GunSoundEvent;
import handmadeguns.items.guns.HMGItem_Unified_Guns;
import hmggvcutil.GVCUtils;
import hmggvcmob.entity.TU95;
import hmggvcmob.entity.guerrilla.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;

import java.util.ArrayList;
import java.util.List;

import static hmggvcmob.GVCMobPlus.*;

public class GVCMXEntityEvent {
    public static List<Entity> soundedentity = new ArrayList<>();
    
    World prevWorld;

    public GVCMXEntityEvent(){
    }
    @SubscribeEvent
    public void canupdate(EntityEvent.CanUpdate event){
        if(event.entity instanceof TU95){
            event.entity.setDead();
        }
    }
    @SubscribeEvent
    public void gunsoundevent(GunSoundEvent event){
        soundedentity.add(event.targetentity);
    }

    
    @SubscribeEvent
    public void worldTickEvent(TickEvent.WorldTickEvent event){
        int dimid = event.world.provider.dimensionId;
//        if(event.world.isRemote){
//            System.out.println("debug");
//            ArrayList<SpotObj> spotObjArrayList_Spawn = spots_needSpawn_Client.get(Integer.valueOf(dimid));
//            ArrayList<SpotObj> tempremove = new ArrayList<>();
//            for(SpotObj aspotobj : spotObjArrayList_Spawn) {
//                float[] pos = new float[3];
//                if(aspotobj.type == SpotType.Entity){
//                    aspotobj.pos[0] = (float) aspotobj.target.posX;
//                    aspotobj.pos[1] = (float) aspotobj.target.posY;
//                    aspotobj.pos[2] = (float) aspotobj.target.posZ;
//                }
//                if (FMLClientHandler.instance().getClientPlayerEntity().getDistanceSq(aspotobj.pos[0],
//                        aspotobj.pos[1],
//                        aspotobj.pos[2]) > 4096) {
//                    Vector3d toentityVec = new Vector3d(
//                                                               aspotobj.pos[0] - FMLClientHandler.instance().getClientPlayerEntity().posX,
//                                                               aspotobj.pos[1] - FMLClientHandler.instance().getClientPlayerEntity().posY + FMLClientHandler.instance().getClientPlayerEntity().getEyeHeight(),
//                                                               aspotobj.pos[2] - FMLClientHandler.instance().getClientPlayerEntity().posZ
//                    );
//                    toentityVec.normalize();
//                    toentityVec.scale(64);
//                    pos[0] = (float) (toentityVec.x + FMLClientHandler.instance().getClientPlayerEntity().posX);
//                    pos[1] = (float) (toentityVec.y + FMLClientHandler.instance().getClientPlayerEntity().posY + FMLClientHandler.instance().getClientPlayerEntity().getEyeHeight());
//                    pos[2] = (float) (toentityVec.z + FMLClientHandler.instance().getClientPlayerEntity().posZ);
//                }
//                System.out.println("debug" + pos[0] + " " + pos[1] + " " + pos[2]);
//                HandmadeGunsCore.proxy.spawnParticles(new PacketSpawnParticle(pos[0], pos[1], pos[2], 2));
//                aspotobj.remaintime--;
//                if(aspotobj.remaintime < 0){
//                    tempremove.add(aspotobj);
//                }
//            }
//            spotObjArrayList_Spawn.removeAll(tempremove);
//        } else
        //アップデート処理必要かこれ？
//        ArrayList<SpotObj> spotObjArrayList_Update = spots_toupdate.get(Integer.valueOf(dimid));
//        ArrayList<SpotObj> tempremove = new ArrayList<>();
//        {
//            //update
//            for(SpotObj aspot : spotObjArrayList_Update){
//                aspot.remaintime--;
//
//            }
//        }
    }
    
    @SubscribeEvent
    public void clientTickEvent(TickEvent.ClientTickEvent event){
    }
	
	@SubscribeEvent
	public void targetevent(LivingSetAttackTargetEvent event){
		EntityLivingBase targeted = event.target;
		EntityLivingBase targeting = event.entityLiving;
		if(targeted==null){
			return;
		}
		if(targeted instanceof EntityPlayer) {
			if (targeting instanceof GVCEntityDrawn) {
				((EntityPlayer) targeted).addStat(unmanned_Craft,1);
			}else
			if (targeting instanceof GVCEntityGK) {
				((EntityPlayer) targeted).addStat(war_has_changed,1);
			}else
			if (targeting instanceof GVCEntityGuerrillaSkeleton) {
				((EntityPlayer) targeted).addStat(unending_war,1);
			}
			
			
			if (targeting instanceof EntityGBase) {
				((EntityPlayer) targeted).addStat(No_place_to_HIDE,1);
			}
		}
	}
	
	@SubscribeEvent
	public void deathEvent(LivingDeathEvent event){
		Entity killing = event.source.getEntity();
		EntityLivingBase dieing = event.entityLiving;
		if(killing==null){
			return;
		}
		if(dieing instanceof EntityPlayer) {
			if (killing instanceof EntityGBase) {
				((EntityPlayer) dieing).addStat(killedGuerrilla,1);
			}
		}
	}
	
	@SubscribeEvent
	public void pickItemEvent(EntityItemPickupEvent event){
		EntityLivingBase picking = event.entityPlayer;
		EntityItem picked = event.item;
		if(picking==null){
			return;
		}
		if(picked.getEntityItem().getItem() instanceof HMGItem_Unified_Guns) {
			if(picked.getEntityItem().getItem() == GVCUtils.type38) ((EntityPlayer) picking).addStat(Gun_of_the_Lost_Country,1);
		}
	}
}
