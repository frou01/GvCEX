package handmadevehicle;

import cpw.mods.fml.common.registry.GameRegistry;
import handmadevehicle.Items.ItemVehicle;
import handmadevehicle.entity.prefab.*;

import javax.vecmath.Vector3d;
import java.io.*;
import java.util.HashMap;

import static handmadevehicle.AddWeapon.prefab_turretHashMap;
import static java.lang.Boolean.parseBoolean;
import static java.lang.Float.parseFloat;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class AddNewVehicle {
	private static final HashMap<String,Prefab_Vehicle_Base> prefabBaseHashMap = new HashMap<>();
	public static void load( boolean isClient, File file){
		try {
			String dataName = null;
			Prefab_Vehicle_Base data = null;
			VehicleType vehicleType = null;
			Prefab_AttachedWeapon current = null;
			int turretId_current = 0;
			//File file = new File(configfile,"hmg_handmadeguns.txt");
			if (checkBeforeReadfile(file))
			{
				
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"Shift-JIS"));
				String str;
				while((str = br.readLine()) != null) {
					//System.out.println(str);
					String[] type = str.split(",");
					switch (type[0]) {
						case "Name":
							dataName = type[1];
							break;
						case "Type":
							if(type[1].equals("Land")) {
								data = new Prefab_Vehicle_LandVehicle();
								vehicleType = VehicleType.Land;
							}
							else
							if(type[1].equals("Air")) {
								data = new Prefab_Vehicle_Plane();
								vehicleType = VehicleType.Air;
							}
							break;
						case "modelName":
							System.out.println(type[1]);
							data.modelName = type[1];
							break;
						case "modelName_texture":
							data.modelName_texture = type[1];
							break;
						case "health":
							data.maxhealth = parseFloat(type[1]);
							break;
						case "soundname":
							data.soundname = type[1];
							break;
						case "soundpitch":
							data.soundpitch = parseFloat(type[1]);
							break;
						case "throttle_Max":
							data.throttle_Max = parseFloat(type[1]);
							break;
						case "throttle_min":
							data.throttle_min = parseFloat(type[1]);
							break;
						case "ParentWeapons_NUM":
							data.prefab_attachedWeapons = new Prefab_AttachedWeapon[parseInt(type[1])];
							break;
						case "AllWeapons_NUM":
							data.prefab_attachedWeapons_all = new Prefab_AttachedWeapon[parseInt(type[1])];
							break;
						case "addParentWeapon":
							data.prefab_attachedWeapons_all[turretId_current] = data.prefab_attachedWeapons[parseInt(type[1])] = new Prefab_AttachedWeapon();
							turretId_current++;
							data.prefab_attachedWeapons[parseInt(type[1])].prefab_turret = prefab_turretHashMap.get(type[2]);
							data.prefab_attachedWeapons[parseInt(type[1])].turretsPos = new Vector3d(parseDouble(type[3]),parseDouble(type[4]),parseDouble(type[5]));
							data.prefab_attachedWeapons[parseInt(type[1])].prefab_Childturrets = new Prefab_AttachedWeapon[parseInt(type[6])];
							current = data.prefab_attachedWeapons[parseInt(type[1])];
							break;
						case "addChildWeapon":
							data.prefab_attachedWeapons_all[turretId_current] = current.prefab_Childturrets[parseInt(type[1])] = new Prefab_AttachedWeapon();
							turretId_current++;
							current.prefab_Childturrets[parseInt(type[1])].prefab_turret = prefab_turretHashMap.get(type[2]);
							current.prefab_Childturrets[parseInt(type[1])].turretsPos = new Vector3d(parseDouble(type[3]),parseDouble(type[4]),parseDouble(type[5]));
							current.prefab_Childturrets[parseInt(type[1])].prefab_Childturrets = new Prefab_AttachedWeapon[parseInt(type[6])];
							current.prefab_Childturrets[parseInt(type[1])].motherTurret = current;
							current = data.prefab_attachedWeapons[parseInt(type[1])];
							break;
						
						case "SetUpSeat1_NUM":
							data.prefab_seats = new Prefab_Seat[parseInt(type[1])];
							data.prefab_seats_zoom = new Prefab_Seat[parseInt(type[1])];
							break;
						case "SetUpSeat2_AddSeat_Normal":
							data.prefab_seats_zoom[parseInt(type[1])] = data.prefab_seats[parseInt(type[1])] = new Prefab_Seat(new double[]{parseDouble(type[2]),parseDouble(type[3]),parseDouble(type[4])},parseBoolean(type[5]),parseBoolean(type[6]),parseInt(type[7]),parseInt(type[8]));
							break;
						case "SetUpSeat3_AddSeat_Zoom":
							data.prefab_seats_zoom[parseInt(type[1])] = new Prefab_Seat(new double[]{parseDouble(type[2]),parseDouble(type[3]),parseDouble(type[4])},parseBoolean(type[5]),parseBoolean(type[6]),parseInt(type[7]),parseInt(type[8]));
							break;
						case "throttle_speed":
							((Prefab_Vehicle_LandVehicle)data).throttle_speed = parseFloat(type[1]);
							break;
						case "yawspeed":
							((Prefab_Vehicle_LandVehicle)data).yawspeed = parseFloat(type[1]);
							break;
						case "speed":
							((Prefab_Vehicle_LandVehicle)data).yawspeed = parseFloat(type[1]);
							break;
						case "spinturn":
							((Prefab_Vehicle_LandVehicle)data).spinturn = parseBoolean(type[1]);
							break;
						case "canControlonWater":
							((Prefab_Vehicle_LandVehicle)data).canControlonWater = parseBoolean(type[1]);
							break;
						case "back":
							current = current.motherTurret;
							break;
						case "End":
							if(isClient)data.setModel();
							prefabBaseHashMap.put(dataName,data);
							GameRegistry.registerItem(new ItemVehicle(dataName,vehicleType).setUnlocalizedName("dataName"),dataName);
							break;
					}
					
				}
				br.close();  // ファイルを閉じる
			}
			else
			{
			
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	private static boolean checkBeforeReadfile(File file){
		if (file.exists()){
			if (file.isFile() && file.canRead()){
				return true;
			}
		}
		
		return false;
	}
	public static Prefab_Vehicle_Base seachInfo(String typeName){
		if(prefabBaseHashMap.containsKey(typeName))
			return prefabBaseHashMap.get(typeName);
		return null;
	}
}
