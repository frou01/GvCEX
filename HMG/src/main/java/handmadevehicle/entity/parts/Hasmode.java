package handmadevehicle.entity.parts;

import net.minecraft.entity.Entity;

public interface Hasmode {
	int getMobMode();
	double[] getTargetpos();
	
	default double distToTargetPos(){
		double[] targetpos = getTargetpos();
		if(targetpos != null){
			return ((Entity)this).getDistanceSq(targetpos[0],targetpos[1],targetpos[2]);
		}
		return -1;
	}
	boolean standalone();
}
