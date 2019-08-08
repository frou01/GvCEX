package handmadevehicle.entity.parts;

import handmadeguns.entity.SpHitCheckEntity;
import handmadevehicle.entity.parts.logics.MultiRiderLogics;
import net.minecraft.entity.Entity;

public interface ImultiRidable extends SpHitCheckEntity {
	default Entity[] getRiddenEntityList(){
		return ((MultiRiderLogics)((HasBaseLogic)this).getBaseLogic()).getRiddenEntityList();
	}
	
	default boolean pickupEntity(Entity p_70085_1_, int StartSeachSeatNum){
		return ((MultiRiderLogics)((HasBaseLogic)this).getBaseLogic()).pickupEntity(p_70085_1_,StartSeachSeatNum);
	}
	
	default int getpilotseatid(){
		return 0;
	}
	
	default boolean isRidingEntity(Entity entity){
		for(Entity aRiddenby: getRiddenEntityList()){
			if(entity == aRiddenby)return true;
		}
		return false;
	}
	
}
