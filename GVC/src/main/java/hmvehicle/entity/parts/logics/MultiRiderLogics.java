package hmvehicle.entity.parts.logics;

import hmvehicle.HMVPacketHandler;
import hmvehicle.packets.HMVPacketPickNewEntity;
import net.minecraft.entity.Entity;

public interface MultiRiderLogics {
	Entity[] getRiddenEntityList();
	boolean pickupEntity(Entity p_70085_1_, int StartSeachSeatNum);
	boolean isRidingEntity(Entity entity);
	default boolean ispilot(Entity entity){
		return getRiddenEntityList()[0] == entity;
	}
}
