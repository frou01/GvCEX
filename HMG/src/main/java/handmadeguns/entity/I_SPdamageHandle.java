package handmadeguns.entity;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;

public interface I_SPdamageHandle {
	
	
	default boolean attackEntityFrom_with_Info(MovingObjectPosition movingObjectPosition, DamageSource source, float level){
		return ((Entity)this).attackEntityFrom(source,level);
	}
}
