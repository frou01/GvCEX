package Sokoniyatugairuzo.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;

public class Targeting {
    public long lastbattletime;
    @SubscribeEvent
    public void targetevent(LivingSetAttackTargetEvent event){
        EntityLivingBase entity = event.target;
        if(entity==null){
            return;
        }
        if(entity instanceof EntityPlayer && ((EntityPlayer) entity).worldObj.getWorldTime()-lastbattletime>10) {
            ((EntityPlayer) entity).worldObj.playSoundAtEntity(entity, "sokoniyatugairuzo:sokoniyatugairuzo.attention", 1.0F, 1.0F);
        }
        lastbattletime = event.target.worldObj.getWorldTime();
    }
}
