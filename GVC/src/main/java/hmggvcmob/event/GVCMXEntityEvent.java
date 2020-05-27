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

    @SubscribeEvent
    public void canupdate(EntityEvent.CanUpdate event){
        if(event.entity instanceof TU95){
            event.entity.setDead();
        }
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
