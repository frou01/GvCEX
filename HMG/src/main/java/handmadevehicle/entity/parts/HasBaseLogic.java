package handmadevehicle.entity.parts;

import handmadevehicle.entity.parts.logics.BaseLogic;

public interface HasBaseLogic {
	BaseLogic getBaseLogic();
	
	void updateFallState_public(double stepHeight, boolean onground);
	void func_145775_I_public();
}
