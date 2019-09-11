package handmadevehicle.entity.prefab;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Float.parseFloat;

public class Prefab_Vehicle_LandVehicle extends Prefab_Vehicle_Base {
	public float yawspeed = 0.2f;
	public float speed = 1;
	public boolean always_point_to_target = false;
	public Prefab_Vehicle_LandVehicle(){
		rotcenter = new double[]{0,0,0};
	}
}
