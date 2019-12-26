package handmadevehicle;

import handmadevehicle.entity.parts.turrets.FireRist;
import handmadevehicle.entity.prefab.Prefab_Vehicle_Base;
import handmadevehicle.entity.prefab.Prefab_Turret;

import javax.vecmath.Vector3d;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import static handmadeguns.HMGGunMaker.readFireInfo;
import static java.lang.Boolean.parseBoolean;
import static java.lang.Float.parseFloat;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class AddWeapon {
	public static final HashMap<String,Prefab_Turret> prefab_turretHashMap = new HashMap<>();
	public static void load( boolean isClient, File file){
		try {
			//File file = new File(configfile,"hmg_handmadeguns.txt");
			if (checkBeforeReadfile(file))
			{
				
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"Shift-JIS"));
				String str;
				Prefab_Turret prefab_turret = new Prefab_Turret();
				String name = null;
				while((str = br.readLine()) != null) {
					
					String[] type = str.split(",");
					switch (type[0]){
						case "WeaponName":
							name = type[1];
							if(prefab_turretHashMap.containsKey(name))prefab_turret = prefab_turretHashMap.get(name);
							prefab_turret.turretName = name;
							break;
						case "WeaponDisplayName":
							prefab_turret.turretName = type[1];
							break;
						case "turretYawCenterpos":
							prefab_turret.gunInfo.posGetter.turretYawCenterpos = new Vector3d(parseDouble(type[1]),parseDouble(type[2]),parseDouble(type[3]));
							break;
						case "turretPitchCenterpos":
							prefab_turret.gunInfo.posGetter.turretPitchCenterpos = new Vector3d(parseDouble(type[1]),parseDouble(type[2]),parseDouble(type[3]));
							break;
						case "cannonPos":
							prefab_turret.gunInfo.posGetter.cannonPos = new Vector3d(parseDouble(type[1]),parseDouble(type[2]),parseDouble(type[3]));
							break;
						case "multicannonPos":
							prefab_turret.gunInfo.posGetter.multicannonPos = new Vector3d[(type.length-1)/3];
							for(int id = 0;id < prefab_turret.gunInfo.posGetter.multicannonPos.length; id++){
								prefab_turret.gunInfo.posGetter.multicannonPos[id] = new Vector3d(parseDouble(type[id * 3 + 1]),parseDouble(type[id * 3 + 2]),parseDouble(type[id * 3 + 3]));
							}
							break;
						case "linked_MotherTrigger":
							prefab_turret.linked_MotherTrigger = parseBoolean(type[1]);
							break;
						case "fireAll_child":
							prefab_turret.fireAll_child = parseBoolean(type[1]);
							break;
						case "fireAll_cannon":
							prefab_turret.fireAll_cannon = parseBoolean(type[1]);
							break;
						case "positionLinked":
							prefab_turret.positionLinked = parseBoolean(type[1]);
							break;
						case "syncTurretAngle":
							prefab_turret.syncTurretAngle = parseBoolean(type[1]);
							break;
						case "elevationType":
							prefab_turret.elevationType = parseInt(type[1]);
							break;
						case "turretanglelimtYawMax":
							prefab_turret.gunInfo.turretanglelimtYawMax = parseFloat(type[1]);
							break;
						case "turretanglelimtYawmin":
							prefab_turret.gunInfo.turretanglelimtYawmin = parseFloat(type[1]);
							break;
						case "turretanglelimtPitchMax":
							prefab_turret.gunInfo.turretanglelimtPitchMax = parseFloat(type[1]);
							break;
						case "turretanglelimtPitchmin":
							prefab_turret.gunInfo.turretanglelimtPitchmin = parseFloat(type[1]);
							break;
						case "turretspeedY":
							prefab_turret.gunInfo.turretspeedY = (float) parseDouble(type[1]);
							break;
						case "turretspeedP":
							prefab_turret.gunInfo.turretspeedP = (float) parseDouble(type[1]);
							break;
						case "traverseSound":
							prefab_turret.traverseSound = type[1];
							break;
						case "traversesoundLV":
							prefab_turret.traversesoundLV = parseFloat(type[1]);
							break;
						case "traversesoundPitch":
							prefab_turret.traversesoundPitch = parseFloat(type[1]);
							break;
						case "hasReflexSight":
							prefab_turret.hasReflexSight = parseBoolean(type[1]);
							break;
						case "fireRists_First":
							prefab_turret.fireRists = new FireRist[parseInt(type[1])];
							break;
						case "fireRists":
							prefab_turret.fireRists[parseInt(type[1])] = new FireRist(parseFloat(type[2]),parseFloat(type[3]),parseFloat(type[4]),parseFloat(type[5]));
							break;
						case "userOnBarrell":
							prefab_turret.userOnBarrell = parseBoolean(type[1]);
							break;
						case "flashoffset":
							prefab_turret.flashoffset = parseDouble(type[1]);
							break;
						case "childFireBlank":
							prefab_turret.childFireBlank = parseInt(type[1]);
							break;
						case "useVehicleInventory":
							prefab_turret.useVehicleInventory = parseBoolean(type[1]);
							break;
						case "needGunStack":
							prefab_turret.needGunStack = parseBoolean(type[1]);
							break;
						case "useGunSight":
							prefab_turret.useGunSight = parseBoolean(type[1]);
							break;
						case "gunStackwhitelist":
							prefab_turret.gunStackwhitelist = new String[type.length-1];
						    {
						    	for(int i = 1;i < type.length;i++){
									prefab_turret.gunStackwhitelist[i-1] = type[i];
								}
						    }
							break;
						case "noStackRestriction":
							prefab_turret.gunStackwhitelist = new String[0];
							break;
						case "End":
							prefab_turretHashMap.put(name,prefab_turret);
							break;
					}
					readFireInfo(prefab_turret.gunInfo,type);
					
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
}
