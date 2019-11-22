package handmadevehicle;

import cpw.mods.fml.common.registry.GameRegistry;
import handmadeguns.HMGGunMaker;
import handmadeguns.HandmadeGunsCore;
import handmadeguns.client.render.HMGGunParts;
import handmadeguns.items.guns.HMGItem_Unified_Guns;
import handmadevehicle.Items.ItemVehicle;
import handmadevehicle.entity.prefab.*;
import handmadevehicle.render.HMVVehicleParts;
import net.minecraft.item.Item;

import javax.vecmath.Vector3d;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import static handmadeguns.HandmadeGunsCore.tabshmg;
import static handmadevehicle.AddWeapon.prefab_turretHashMap;
import static handmadevehicle.HMVehicle.tabHMV;
import static java.lang.Boolean.parseBoolean;
import static java.lang.Float.parseFloat;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class AddNewVehicle extends HMGGunMaker {
	private static final HashMap<String,Prefab_Vehicle_Base> prefabBaseHashMap = new HashMap<>();
	public void load( boolean isClient, File file){
		try {
			String dataName = null;
			Prefab_Vehicle_Base data = new Prefab_Vehicle_Base();
			VehicleType vehicleType = null;
			Prefab_AttachedWeapon current = null;
			int turretId_current = 0;
			String tabname = null;
			ArrayList<HMGGunParts> partslist = new ArrayList<HMGGunParts>();
			//File file = new File(configfile,"hmg_handmadeguns.txt");
			if (checkBeforeReadfile(file))
			{
				
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"Shift-JIS"));
				String str = "";
				String str_line = "";
				while((str_line = br.readLine()) != null) {
					str = str.concat(str_line);
					//System.out.println(str);
					if(str.contains(";")) {
						str = str.concat(" ");
						String[] str_temp = str.split(";");
						for(String a_calm:str_temp) {
							str = a_calm;
							String[] type = a_calm.split(",");
							for (int i = 0; i < type.length; i++) {
								type[i] = type[i].trim();
							}
							switch (type[0]) {
								case "Name":
									dataName = type[1];
									if (prefabBaseHashMap.containsKey(dataName)) data = prefabBaseHashMap.get(dataName);
									break;
								case "modelName":
									System.out.println(type[1]);
									data.modelName = type[1];
									break;
								case "modelName_texture":
									data.modelName_texture = type[1];
									break;
								case "scale":
									data.scale = parseFloat(type[1]);
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
								case "throttle_speed":
									data.throttle_speed = parseFloat(type[1]);
									break;
								case "draft":
									data.draft = parseFloat(type[1]);
									break;
								case "molded_depth":
									data.molded_depth = parseFloat(type[1]);
									break;
								case "floatOnWater":
									data.floatOnWater = parseBoolean(type[1]);
									break;
								case "thirdDist":
									data.thirdDist = parseFloat(type[1]);
									break;
								case "splashsound":
									data.splashsound = type[1];
									break;
								case "sightTex":
									data.sightTex = type[1];
									break;
								//LandOnly
								//AirOnly
								case "ParentWeapons_NUM":
									data.prefab_attachedWeapons = new Prefab_AttachedWeapon[parseInt(type[1])];
									break;
								case "AllWeapons_NUM":
									data.prefab_attachedWeapons_all = new Prefab_AttachedWeapon[parseInt(type[1])];
									break;
								case "addParentWeapon":
									data.prefab_attachedWeapons_all[turretId_current] = data.prefab_attachedWeapons[parseInt(type[1])] = new Prefab_AttachedWeapon();
									turretId_current++;
									if(prefab_turretHashMap.containsKey(type[2]))data.prefab_attachedWeapons[parseInt(type[1])].prefab_turret = prefab_turretHashMap.get(type[2]);
									else {
										Item check = GameRegistry.findItem("HandmadeGuns",type[2]);
										if(check instanceof HMGItem_Unified_Guns){
											data.prefab_attachedWeapons[parseInt(type[1])].prefab_turret = new Prefab_Turret(((HMGItem_Unified_Guns) check));
										}
									}
									if(data.prefab_attachedWeapons[parseInt(type[1])].prefab_turret.needGunStack){
										data.prefab_attachedWeapons[parseInt(type[1])].linkedGunStackID = data.weaponSlotNum++;
										data.weaponSlot_linkedTurret_StackWhiteList.add(data.prefab_attachedWeapons[parseInt(type[1])].prefab_turret.gunStackwhitelist);
									}


									data.prefab_attachedWeapons[parseInt(type[1])].turretsPos = new Vector3d(parseDouble(type[3]), parseDouble(type[4]), parseDouble(type[5]));
									data.prefab_attachedWeapons[parseInt(type[1])].prefab_Childturrets = new Prefab_AttachedWeapon[parseInt(type[6])];
									current = data.prefab_attachedWeapons[parseInt(type[1])];
									break;
								case "addChildWeapon":
									data.prefab_attachedWeapons_all[turretId_current] = current.prefab_Childturrets[parseInt(type[1])] = new Prefab_AttachedWeapon();
									turretId_current++;
									if(prefab_turretHashMap.containsKey(type[2]))current.prefab_Childturrets[parseInt(type[1])].prefab_turret = prefab_turretHashMap.get(type[2]);
									else {
										Item check = GameRegistry.findItem("HandmadeGuns",type[2]);
										if(check instanceof HMGItem_Unified_Guns){
											current.prefab_Childturrets[parseInt(type[1])].prefab_turret = new Prefab_Turret(((HMGItem_Unified_Guns) check));
										}
									}
									if(current.prefab_Childturrets[parseInt(type[1])].prefab_turret.needGunStack){
										current.prefab_Childturrets[parseInt(type[1])].linkedGunStackID = data.weaponSlotNum++;
										data.weaponSlot_linkedTurret_StackWhiteList.add(current.prefab_Childturrets[parseInt(type[1])].prefab_turret.gunStackwhitelist);
									}

									current.prefab_Childturrets[parseInt(type[1])].turretsPos = new Vector3d(parseDouble(type[3]), parseDouble(type[4]), parseDouble(type[5]));
									current.prefab_Childturrets[parseInt(type[1])].prefab_Childturrets = new Prefab_AttachedWeapon[parseInt(type[6])];
									current.prefab_Childturrets[parseInt(type[1])].motherTurret = current;
									current = current.prefab_Childturrets[parseInt(type[1])];
									break;
								case "Set_CurrentTurret_to_Mother":
									current = current.motherTurret;
									break;
								
								case "SetUpSeat1_NUM":
									data.prefab_seats = new Prefab_Seat[parseInt(type[1])];
									data.prefab_seats_zoom = new Prefab_Seat[parseInt(type[1])];
									break;
								case "SetUpSeat2_AddSeat_Normal":
									data.prefab_seats_zoom[parseInt(type[1])] = data.prefab_seats[parseInt(type[1])] = new Prefab_Seat(new double[]{parseDouble(type[2]), parseDouble(type[3]), parseDouble(type[4])}, parseBoolean(type[5]), parseBoolean(type[6]), parseBoolean(type[7]), parseInt(type[8]), parseInt(type[9]));
									break;
								case "SetUpSeat3_AddSeat_Zoom":
									data.prefab_seats_zoom[parseInt(type[1])] = new Prefab_Seat(new double[]{parseDouble(type[2]), parseDouble(type[3]), parseDouble(type[4])}, parseBoolean(type[5]), parseBoolean(type[6]), parseBoolean(type[7]), parseInt(type[8]), parseInt(type[9]));
									break;
								case "SetUpSeat4_AddSeat_AdditionalTurret":
									data.prefab_seats[parseInt(type[1])].mainid = new int[type.length - 2];
									int cnt = -2;
									for(String column:type){
										if(cnt < 0){
											cnt++;
											continue;
										}
										data.prefab_seats[parseInt(type[1])].mainid[cnt] = parseInt(column);
										cnt++;
									}
									break;
								case "back":
									current = current.motherTurret;
									break;
								case "Tabname":
									tabname = type[1];
									break;
								case "End":
									if (isClient) {
										data.setModel();
										if (!partslist.isEmpty()) data.partslist = partslist;
									}
									prefabBaseHashMap.put(dataName, data);
									Item check = GameRegistry.findItem("HMVehicle",dataName);
									if(check == null){
										Item itemVehicle;
										GameRegistry.registerItem(itemVehicle = new ItemVehicle(dataName).setUnlocalizedName(dataName), dataName);
										if(tabname == null) itemVehicle.setCreativeTab(tabHMV);
										else if(tabshmg.containsKey(tabname)){
											itemVehicle.setCreativeTab(tabshmg.get(tabname));
										}
									}
									break;
							}
							data.readSettings(type);
							if (isClient) readParts_vehicle(type, partslist);
						}
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
	
	
	public void readParts_vehicle(String[] type,ArrayList<HMGGunParts> partslist){
		readerCnt = 0;
		readParts(type,partslist);
		switch (type[readerCnt++]) {
			case "AddPartsRenderAsTrackInf":
				((HMVVehicleParts)currentParts).AddRenderinfTrack(Float.parseFloat(type[readerCnt++]), Float.parseFloat(type[readerCnt++]), Float.parseFloat(type[readerCnt++]), Float.parseFloat(type[readerCnt++]), Float.parseFloat(type[readerCnt++]), Float.parseFloat(type[readerCnt++]));
				break;
			case "AddTrackPos":
				((HMVVehicleParts)currentParts).AddTrackPositions(type);
				break;
			case "IsTrack":
				((HMVVehicleParts)currentParts).setIsTrack(Boolean.parseBoolean(type[readerCnt++]), parseInt(type[readerCnt++]));
				break;
			case "IsCloningTrack":
				((HMVVehicleParts)currentParts).setIsTrack_Cloning(Boolean.parseBoolean(type[readerCnt++]), parseInt(type[readerCnt++]));
				break;
			case "TurretParts":
				((HMVVehicleParts)currentParts).isTurretParts = true;
				((HMVVehicleParts)currentParts).linkedTurretID = parseInt(type[readerCnt++]);
				break;
			case "isTurret_linkedGunMount":
				((HMVVehicleParts)currentParts).isTurret_linkedGunMount = true;
				break;
			case "AddSomeMotion":
				((HMVVehicleParts)currentParts).AddSomethingMotionKey(type);
				break;
			case "AddSomeInfo":
				((HMVVehicleParts)currentParts).AddRenderinfSomething(Float.parseFloat(type[readerCnt++]), Float.parseFloat(type[readerCnt++]), Float.parseFloat(type[readerCnt++]), Float.parseFloat(type[readerCnt++]), Float.parseFloat(type[readerCnt++]), Float.parseFloat(type[readerCnt++]),parseInt(type[readerCnt]));
				break;
		}
	}
	public HMGGunParts createGunPart(String[] strings){
		return new HMVVehicleParts(strings[1]);
	}
	public HMGGunParts createGunPart(String[] strings,int motherID,HMGGunParts mother){
		return new HMVVehicleParts(strings[1],motherID,mother);
	}
}
