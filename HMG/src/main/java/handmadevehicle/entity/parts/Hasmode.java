package handmadevehicle.entity.parts;

import net.minecraft.entity.Entity;

import javax.vecmath.Vector3d;

public interface Hasmode {
	Modes getMobMode();
	double[] getTargetpos();
	Vector3d getMoveToPos();
}
