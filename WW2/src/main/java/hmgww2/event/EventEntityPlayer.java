package hmgww2.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import gvclib.entity.EntityBBase;
import gvclib.entity.EntityBases;
import hmgww2.mod_GVCWW2;
import hmgww2.entity.EntityGERBase;
import hmgww2.entity.EntityJPNBase;
import hmgww2.entity.EntityRUSBase;
import hmgww2.entity.EntityUSABase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class EventEntityPlayer {
	
	@SubscribeEvent
	public void onHurtEventMob(LivingHurtEvent event) {
		EntityLivingBase target = event.entityLiving;
		DamageSource source = event.source;
		float damage = event.ammount;
		ItemStack hold = null;
		
		if (target instanceof EntityJPNBase && target != null) 
		{
			EntityJPNBase en = (EntityJPNBase) target.ridingEntity;
			if (target != null && source.getEntity() instanceof EntityJPNBase) {
				EntityJPNBase attacker = (EntityJPNBase) source.getEntity();
				if (attacker != null) {
					event.ammount = 0;
				}
			}
			if (target != null && source.isExplosion()) {
				event.ammount = 0;
			}
		}
		if (target instanceof EntityUSABase && target != null) 
		{
			EntityUSABase en = (EntityUSABase) target.ridingEntity;
			if (target != null && source.getEntity() instanceof EntityUSABase) {
				EntityUSABase attacker = (EntityUSABase) source.getEntity();
				if (attacker != null) {
					event.ammount = 0;
				}
			}
			if (target != null && source.isExplosion()) {
				event.ammount = 0;
			}
		}
		if (target instanceof EntityGERBase && target != null) 
		{
			EntityGERBase en = (EntityGERBase) target.ridingEntity;
			if (target != null && source.getEntity() instanceof EntityGERBase) {
				EntityGERBase attacker = (EntityGERBase) source.getEntity();
				if (attacker != null) {
					event.ammount = 0;
				}
			}
			if (target != null && source.isExplosion()) {
				event.ammount = 0;
			}
		}
		if (target instanceof EntityRUSBase && target != null) 
		{
			EntityRUSBase en = (EntityRUSBase) target.ridingEntity;
			if (target != null && source.getEntity() instanceof EntityRUSBase) {
				EntityRUSBase attacker = (EntityRUSBase) source.getEntity();
				if (attacker != null) {
					event.ammount = 0;
				}
			}
			if (target != null && source.isExplosion()) {
				event.ammount = 0;
			}
		}
		
		/*if (target != null && source.getEntity() instanceof EntityPlayer) {
			target.motionX = 0;
			target.motionY = 0;
			target.motionZ = 0;
			//target.worldObj.createExplosion(target, target.posX, target.posY+1,target.posZ, 0.0F, false);
		}*/
	}
	
	/*@SubscribeEvent
	public void onLivingMob(LivingEvent event) {
		EntityLivingBase target = event.entityLiving;
		if (target != null) {
			if(target.hurtResistantTime > 0){
			target.motionX = target.motionX * 0.5;
			target.motionY = target.motionY * 0.5;
			target.motionZ = target.motionZ * 0.5;
			}
		}
	}*/
	
	@SubscribeEvent
	public void onHurtEvent(LivingHurtEvent event) {
		EntityLivingBase target = event.entityLiving;
		DamageSource source = event.source;
		float damage = event.ammount;
		ItemStack hold = null;

		if (target instanceof EntityPlayer && target != null) 
		{
			EntityPlayer entityplayer = (EntityPlayer) target;
			if (entityplayer.getEquipmentInSlot(4) != null
					&& (entityplayer.getEquipmentInSlot(4).getItem() == mod_GVCWW2.armor_jpn)) {
				if (target != null && source.getEntity() instanceof EntityJPNBase) {
					EntityJPNBase attacker = (EntityJPNBase) source.getEntity();
					if (attacker != null) {
						event.ammount = 0;
					}
				}
				if (target != null && source.isExplosion()) {
					event.ammount = 0;
				}
			}
			if (entityplayer.getEquipmentInSlot(4) != null
					&& (entityplayer.getEquipmentInSlot(4).getItem() == mod_GVCWW2.armor_usa)) {
				if (target != null && source.getEntity() instanceof EntityUSABase) {
					EntityUSABase attacker = (EntityUSABase) source.getEntity();
					if (attacker != null) {
						event.ammount = 0;
					}
				}
				if (target != null && source.isExplosion()) {
					event.ammount = 0;
				}
			}
			if (entityplayer.getEquipmentInSlot(4) != null
					&& (entityplayer.getEquipmentInSlot(4).getItem() == mod_GVCWW2.armor_ger)) {
				if (target != null && source.getEntity() instanceof EntityGERBase) {
					EntityGERBase attacker = (EntityGERBase) source.getEntity();
					if (attacker != null) {
						event.ammount = 0;
					}
				}
				if (target != null && source.isExplosion()) {
					event.ammount = 0;
				}
			}
			if (entityplayer.getEquipmentInSlot(4) != null
					&& (entityplayer.getEquipmentInSlot(4).getItem() == mod_GVCWW2.armor_rus)) {
				if (target != null && source.getEntity() instanceof EntityRUSBase) {
					EntityRUSBase attacker = (EntityRUSBase) source.getEntity();
					if (attacker != null) {
						event.ammount = 0;
					}
				}
				if (target != null && source.isExplosion()) {
					event.ammount = 0;
				}
			}
		}
		
		if (target.ridingEntity instanceof EntityBases && target.ridingEntity != null) 
		{
			EntityBases en = (EntityBases) target.ridingEntity;
			if (target != null && source.getEntity() instanceof EntityLivingBase) {
				EntityLivingBase attacker = (EntityLivingBase) source.getEntity();
				if (attacker != null && !en.opentop) {
					event.ammount = 0;
				}
			}
			if (target != null && source.isExplosion() && !en.opentop) {
				event.ammount = 0;
			}
		}
		
	}
}
