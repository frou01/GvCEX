package hmggvcmob.entity;

import static java.lang.Integer.parseInt;

public class VehicleSpawnGachaOBJ {
	public int rate;
	public String vehicleName;
	public VehicleSpawnGachaOBJ(String string){
		String[] type = string.split(":");
		if(type.length > 1) {
			vehicleName = type[0];
			rate = parseInt(type[1]);
		}else {//blank
			rate = parseInt(type[0]);
		}
	}
}
