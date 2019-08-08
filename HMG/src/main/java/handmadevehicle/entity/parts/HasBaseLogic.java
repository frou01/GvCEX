package handmadevehicle.entity.parts;

import handmadevehicle.entity.parts.logics.LogicsBase;

public interface HasBaseLogic {
	LogicsBase getBaseLogic();
	
	void updateFallState_public(double stepHeight, boolean onground);
	void func_145775_I_public();
}
