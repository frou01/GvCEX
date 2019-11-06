package handmadevehicle.entity.parts;

import javax.vecmath.Vector3d;

public class VectorAndHitSide {
	Vector3d vector3d;
	Vector3d vector3d_relative;
	int hitside;
	public VectorAndHitSide(Vector3d avec,Vector3d relative,int side){
		this.vector3d = avec;
		this.vector3d_relative = relative;
		this.hitside = side;
	}
}
