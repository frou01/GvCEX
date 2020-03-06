package handmadevehicle;

import javax.vecmath.Quat4d;

public class Test {

	double x,y,z,w;
	public final void mul(Quat4d PY,Quat4d R)
	{
		double     x, y, z, w;

		x = PY.w*R.x + PY.x*R.w + PY.y*R.z - PY.z*R.y;
		y = PY.w*R.y + PY.y*R.w - PY.x*R.z + PY.z*R.x;
		z = PY.w*R.z + PY.z*R.w + PY.x*R.y - PY.y*R.x;
		w = PY.w*R.w - PY.x*R.x - PY.y*R.y - PY.z*R.z;

		this.w = w;
		this.x = x;
		this.y = y;
	}
}
