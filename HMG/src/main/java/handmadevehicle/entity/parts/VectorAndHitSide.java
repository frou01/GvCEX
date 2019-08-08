package handmadevehicle.entity.parts;

import javax.vecmath.Vector3d;

public class VectorAndHitSide {
	Vector3d vector3d;
	int hitside;
	public VectorAndHitSide(Vector3d avec,int side){
		vector3d = avec;
		hitside = side;
	}
}
