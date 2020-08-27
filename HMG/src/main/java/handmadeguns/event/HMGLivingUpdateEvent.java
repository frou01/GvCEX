package handmadeguns.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import handmadeguns.entity.PlacedGunEntity;
import handmadeguns.entity.bullets.HMGEntityBulletBase;
import handmadeguns.items.guns.HMGItem_Unified_Guns;
import handmadevehicle.entity.EntityVehicle;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import static net.minecraft.util.DamageSource.inWall;

public class HMGLivingUpdateEvent {
    @SubscribeEvent
    public void canupdate(EntityEvent.CanUpdate event){
        if(event.entity instanceof HMGEntityBulletBase){
            event.entity.setDead();
        }
        if(event.entity instanceof EntityVehicle && ((EntityVehicle) event.entity).canDespawn){
            event.entity.setDead();
        }
    }
    @SubscribeEvent
    public void canupdate(LivingEvent.LivingUpdateEvent event){
        event.entity.worldObj.MAX_ENTITY_RADIUS = 40;
    }

    @SubscribeEvent
    public void entitydamaged(LivingHurtEvent event)
    {
        EntityLivingBase entity = event.entityLiving;

        if ((entity != null && entity.ridingEntity instanceof PlacedGunEntity)) {
            HMGItem_Unified_Guns item_unified_guns = ((PlacedGunEntity) entity.ridingEntity).
                    gunItem;
            if(item_unified_guns != null) {
                if(item_unified_guns.gunInfo.turretMaxHP != -1){
                    event.ammount = 0;
                    event.setCanceled(true);
                }else if(event.source == inWall){
                    event.ammount = 0;
                    event.setCanceled(true);
                    entity.hurtTime = 0;
                }
            }
        }
    }
    @SubscribeEvent
    public void livingAttackEvent(LivingAttackEvent event){
        EntityLivingBase entity = event.entityLiving;

        if ((entity != null && entity.ridingEntity instanceof PlacedGunEntity)) {
            HMGItem_Unified_Guns item_unified_guns = ((PlacedGunEntity) entity.ridingEntity).
                    gunItem;
            if(item_unified_guns != null) {
                if(item_unified_guns.gunInfo.turretMaxHP != -1){
                    event.setCanceled(true);
                }else if(event.source == inWall){
                    event.setCanceled(true);
                    entity.hurtTime = 0;
                }
            }
        }
    }
}
