package hmvehicle.entity.parts.logics;

import hmvehicle.entity.parts.IControlable;
import hmvehicle.entity.parts.ModifiedBoundingBox;

public interface IbaseLogic extends IControlable {
	String getsound();
	float getsoundPitch();
	default void yourSoundIsremain(){
	
	}
	void setPosition(double p_70107_1_, double p_70107_3_, double p_70107_5_);
}
