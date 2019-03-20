package hmgww2.entity;

import gvclib.entity.EntityBases;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class PL_AirCraftMove {
	public static void movefighter(EntityLivingBase player, EntityBases entity, float sp, float turnspeed){
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
		float f = Math.abs(entity.prevRotationYawHead - player.rotationYawHead);
		float f2 = entity.rotationYawHead - player.rotationYawHead;
		if(entity.getHealth() > 0.0F) {
			if(entity.onGround)
			{
				if(entity.thpower > 1){
				if(f <= turnspeed && f >= -turnspeed){
					entity.rotation = player.prevRotationYawHead;
					entity.rotationp = player.prevRotationPitch;
					entity.rotationYawHead = player.rotationYaw;
					entity.rotationYaw = player.rotationYawHead;
					entity.prevRotationYaw = player.prevRotationYawHead;
					entity.prevRotationYawHead = player.prevRotationYawHead;
					entity.renderYawOffset = player.prevRotationYawHead;
				}
				else if(f2 > 0.1F){
					if(f2 > 180F){
						PL_RoteModel.rotemodel(entity,+ turnspeed/5);
					}else{
						PL_RoteModel.rotemodel(entity,- turnspeed/5);
					}
				}
				else if(f2 < -0.1F){
					if(f2 < -180F){
						PL_RoteModel.rotemodel(entity,- turnspeed/5);
					}else{
						PL_RoteModel.rotemodel(entity,+ turnspeed/5);
					}
				}
				entity.rotationPitch = player.rotationPitch;
				entity.prevRotationPitch = player.prevRotationPitch;
				}
			}else{
				/*if(f <= turnspeed*0.8 && f >= -turnspeed*0.8){
					entity.rotation = player.prevRotationYawHead;
					entity.rotationp = player.prevRotationPitch;
					entity.rotationYawHead = player.rotationYaw;
					entity.rotationYaw = player.rotationYawHead;
					entity.prevRotationYaw = player.prevRotationYawHead;
					entity.prevRotationYawHead = player.prevRotationYawHead;
					entity.renderYawOffset = player.prevRotationYawHead;
				}else*/
				if(f2 > turnspeed){
					if(f2 > 180F){
						PL_RoteModel.rotemodel(entity,+ turnspeed);
						if(entity.throte < 50){
							entity.throte = entity.throte + 2;
						}
					}else{
						PL_RoteModel.rotemodel(entity,- turnspeed);
						if(entity.throte > -50){
							entity.throte = entity.throte - 2;
						}
					}
				}
				else if(f2 < -turnspeed){
					if(f2 < -180F){
						PL_RoteModel.rotemodel(entity,- turnspeed);
						if(entity.throte > -50){
							entity.throte = entity.throte - 2;
						}
					}else{
						PL_RoteModel.rotemodel(entity,+ turnspeed);
						if(entity.throte < 50){
							entity.throte = entity.throte + 2;
						}
					}
				}
				entity.rotationPitch = player.rotationPitch;
				entity.prevRotationPitch = player.prevRotationPitch;
			}
		}
		
		
		
		
		
		
		
		
		float ix1 = 0;
		float iz1 = 0;
		float f111 = entity.rotationYaw * (2 * (float) Math.PI / 360);
		ix1 -= MathHelper.sin(f111) * 2;
		iz1 += MathHelper.cos(f111) * 2;
		
		//Vec3d looked = player.getLookVec();
		Vec3 look = entity.getLookVec();
		
		if (player.moveForward > 0.0F) {
			if(entity.throttle < entity.thmax){
				++entity.throttle;
			}
		}
		if (player.moveForward < 0.0F) {
			if(entity.throttle >= 1){
				--entity.throttle;
			}
		}
		if( entity.throttle >= 0){
			if(entity.thpower < entity.throttle){
				if( entity.throttle > entity.thmax-5){
					entity.thpower = entity.thpower + 0.5D;
				}else{
					entity.thpower = entity.thpower + 0.25D;
				}
			}else{
				entity.thpower = entity.thpower - 0.2D;
			}
			if(entity.throttle <= 0 && entity.throttle > 0){
				entity.thpower = entity.thpower - 0.5D;
			}
		}
	}
}
