package hmgww2.entity;

import gvclib.entity.EntityBases;
import net.minecraft.entity.EntityLivingBase;

public class PL_TankMove {
	public static void move2(EntityLivingBase player, EntityBases entity, float sp, float turnspeed){
		if(player.rotationYawHead > 360F || player.rotationYawHead < -360F){
			player.rotationYawHead = 0;
			player.rotationYaw = 0;
			player.prevRotationYaw = 0;
			player.prevRotationYawHead = 0;
			player.renderYawOffset = 0;
		}
		if(player.rotationYawHead > 180F){
			player.rotationYawHead = -179F;
			player.rotationYaw = -179F;
			player.prevRotationYaw = -179F;
			player.prevRotationYawHead = -179F;
			player.renderYawOffset = -179F;
		}
		if(player.rotationYawHead < -180F){
			player.rotationYawHead = 179F;
			player.rotationYaw = 179F;
			player.prevRotationYaw = 179F;
			player.prevRotationYawHead = 179F;
			player.renderYawOffset = 179F;
		}
		if (player.moveForward > 0.0F) {
			if(entity.throttle < entity.thmax){
				entity.throttle = entity.throttle + entity.thmaxa;
			}
		}
		if (player.moveForward < 0.0F) {
			if(entity.throttle > entity.thmin){
				entity.throttle = entity.throttle + entity.thmina;
			}
		}
		//if(tro != 0)
		{
			if (player.moveStrafing < 0.0F) {
				if (player.moveForward >= 0.0F) {
					entity.rotationYawHead = entity.rotationYawHead + turnspeed;
					entity.rotationYaw = entity.rotationYaw + turnspeed;
				}else {
					entity.rotationYawHead = entity.rotationYawHead - turnspeed;
					entity.rotationYaw = entity.rotationYaw - turnspeed;
				}
			}
			if (player.moveStrafing > 0.0F) {
				if (player.moveForward >= 0.0F) {
					entity.rotationYawHead = entity.rotationYawHead - turnspeed;
					entity.rotationYaw = entity.rotationYaw - turnspeed;
				}else {
					entity.rotationYawHead = entity.rotationYawHead + turnspeed;
					entity.rotationYaw = entity.rotationYaw + turnspeed;
				}
			}
		}
	}
}
