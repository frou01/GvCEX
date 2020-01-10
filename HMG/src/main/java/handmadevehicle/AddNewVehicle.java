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
	private static Prefab_Vehicle_Base currentVehicleData;
	public void load( boolean isClient, File file){
		String str_Debug = null;
		int l = 0;
		try {
			String dataName = null;
			currentVehicleData = new Prefab_Vehicle_Base();
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
					l++;
					str = str.concat(str_line);
					//System.out.println(str);
					if(str.contains(";")) {
						str = str.concat(" ");
						str_Debug = str;
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
									if (prefabBaseHashMap.containsKey(dataName)) currentVehicleData = prefabBaseHashMap.get(dataName);
									currentVehicleData.weaponSlotNum = 0;
									currentVehicleData.cargoSlotNum = 0;
									break;
								case "modelName":
									System.out.println(type[1]);
									currentVehicleData.modelName = type[1];
									break;
								case "modelName_texture":
									currentVehicleData.modelName_texture = type[1];
									break;
								case "scale":
									currentVehicleData.scale = parseFloat(type[1]);
									break;
								case "health":
									currentVehicleData.maxhealth = parseFloat(type[1]);
									break;
								case "soundname":
									currentVehicleData.soundname = type[1];
									currentVehicleData.AFsoundname = type[1] + "AF";
									break;
								case "soundpitch":
									currentVehicleData.soundpitch = parseFloat(type[1]);
									break;
								case "throttle_Max":
									currentVehicleData.throttle_Max = parseFloat(type[1]);
									break;
								case "throttle_min":
									currentVehicleData.throttle_min = parseFloat(type[1]);
									break;
								case "throttle_speed":
									currentVehicleData.throttle_speed = parseFloat(type[1]);
									break;
								case "draft":
									currentVehicleData.draft = parseFloat(type[1]);
									break;
								case "molded_depth":
									currentVehicleData.molded_depth = parseFloat(type[1]);
									break;
								case "floatOnWater":
									currentVehicleData.floatOnWater = parseBoolean(type[1]);
									break;
								case "splashsound":
									currentVehicleData.splashsound = type[1];
									break;
								case "sightTex":
									currentVehicleData.sightTex = type[1];
									break;
								//LandOnly
								//AirOnly
								case "ParentWeapons_NUM":
									currentVehicleData.prefab_attachedWeapons = new Prefab_AttachedWeapon[parseInt(type[1])];
									break;
								case "AllWeapons_NUM":
									currentVehicleData.prefab_attachedWeapons_all = new Prefab_AttachedWeapon[parseInt(type[1])];
									break;
								case "addParentWeapon":
									currentVehicleData.prefab_attachedWeapons_all[turretId_current] = currentVehicleData.prefab_attachedWeapons[parseInt(type[1])] = new Prefab_AttachedWeapon();
									current = currentVehicleData.prefab_attachedWeapons[parseInt(type[1])];
									turretId_current++;
									if(prefab_turretHashMap.containsKey(type[2]))current.prefab_turret = prefab_turretHashMap.get(type[2]);
									else {
										Item check = GameRegistry.findItem("HandmadeGuns",type[2]);
										if(check instanceof HMGItem_Unified_Guns){
											current.prefab_turret = new Prefab_Turret(((HMGItem_Unified_Guns) check));
										}
									}
									if(current.prefab_turret.needGunStack){
										current.linkedGunStackID = currentVehicleData.weaponSlotNum++;
										currentVehicleData.weaponSlot_linkedTurret_StackWhiteList.add(current.prefab_turret.gunStackwhitelist);
									}


									current.turretsPos = new Vector3d(parseDouble(type[3]), parseDouble(type[4]), parseDouble(type[5]));
									current.prefab_Childturrets = new Prefab_AttachedWeapon[parseInt(type[6])];
									if(type.length >= 8)current.prefab_ChildOnBarrel = new Prefab_AttachedWeapon[parseInt(type[7])];
									break;
								case "addChildWeapon":
									currentVehicleData.prefab_attachedWeapons_all[turretId_current] = current.prefab_Childturrets[parseInt(type[1])] = new Prefab_AttachedWeapon();
									current.prefab_Childturrets[parseInt(type[1])].motherTurret = current;
									current = current.prefab_Childturrets[parseInt(type[1])];
									turretId_current++;
									if(prefab_turretHashMap.containsKey(type[2]))current.prefab_turret = prefab_turretHashMap.get(type[2]);
									else {
										Item check = GameRegistry.findItem("HandmadeGuns",type[2]);
										if(check instanceof HMGItem_Unified_Guns){
											current.prefab_turret = new Prefab_Turret(((HMGItem_Unified_Guns) check));
										}
									}
									if(current.prefab_turret.needGunStack){
										current.linkedGunStackID = currentVehicleData.weaponSlotNum++;
										currentVehicleData.weaponSlot_linkedTurret_StackWhiteList.add(current.prefab_turret.gunStackwhitelist);
									}

									current.turretsPos = new Vector3d(parseDouble(type[3]), parseDouble(type[4]), parseDouble(type[5]));
									current.prefab_Childturrets = new Prefab_AttachedWeapon[parseInt(type[6])];
									break;
								case "addBarrelChildWeapon":
									currentVehicleData.prefab_attachedWeapons_all[turretId_current] = current.prefab_ChildOnBarrel[parseInt(type[1])] = new Prefab_AttachedWeapon();
									current.prefab_ChildOnBarrel[parseInt(type[1])].motherTurret = current;
									current = current.prefab_ChildOnBarrel[parseInt(type[1])];
									turretId_current++;
									if(prefab_turretHashMap.containsKey(type[2]))current.prefab_turret = prefab_turretHashMap.get(type[2]);
									else {
										Item check = GameRegistry.findItem("HandmadeGuns",type[2]);
										if(check instanceof HMGItem_Unified_Guns){
											current.prefab_turret = new Prefab_Turret(((HMGItem_Unified_Guns) check));
										}
									}
									if(current.prefab_turret.needGunStack){
										current.linkedGunStackID = currentVehicleData.weaponSlotNum++;
										currentVehicleData.weaponSlot_linkedTurret_StackWhiteList.add(current.prefab_turret.gunStackwhitelist);
									}

									current.turretsPos = new Vector3d(parseDouble(type[3]), parseDouble(type[4]), parseDouble(type[5]));
									current.prefab_Childturrets = new Prefab_AttachedWeapon[parseInt(type[6])];
									break;
								case "Set_CurrentTurret_to_Mother":
									current = current.motherTurret;
									break;
								
								case "SetUpSeat1_NUM":
									currentVehicleData.prefab_seats = new Prefab_Seat[parseInt(type[1])];
									currentVehicleData.prefab_seats_zoom = new Prefab_Seat[parseInt(type[1])];
									break;
								case "SetUpSeat2_AddSeat_Normal":
									if(type.length < 11)currentVehicleData.prefab_seats_zoom[parseInt(type[1])] = currentVehicleData.prefab_seats[parseInt(type[1])] =
											new Prefab_Seat(new double[]{parseDouble(type[2]), parseDouble(type[3]), parseDouble(type[4])}, parseBoolean(type[5]), parseBoolean(type[6]), parseBoolean(type[7]), parseInt(type[8]), parseInt(type[9]));
									else currentVehicleData.prefab_seats_zoom[parseInt(type[1])] = currentVehicleData.prefab_seats[parseInt(type[1])] =
											new Prefab_Seat(new double[]{parseDouble(type[2]), parseDouble(type[3]), parseDouble(type[4])}, parseBoolean(type[5]), parseBoolean(type[6]), parseBoolean(type[7]),parseBoolean(type[8]), parseInt(type[9]), parseInt(type[10]));
									break;
								case "SetUpSeat3_AddSeat_Zoom":
									if(type.length < 11)
										currentVehicleData.prefab_seats_zoom[parseInt(type[1])] =
												new Prefab_Seat(new double[]{parseDouble(type[2]), parseDouble(type[3]), parseDouble(type[4])}, parseBoolean(type[5]), parseBoolean(type[6]), parseBoolean(type[7]), parseInt(type[8]), parseInt(type[9]));
									else currentVehicleData.prefab_seats_zoom[parseInt(type[1])] = 
											new Prefab_Seat(new double[]{parseDouble(type[2]), parseDouble(type[3]), parseDouble(type[4])}, parseBoolean(type[5]), parseBoolean(type[6]), parseBoolean(type[7]),parseBoolean(type[8]), parseInt(type[9]), parseInt(type[10]));

									break;
								case "SetUpSeat4_AddSeat_AdditionalTurret":
									currentVehicleData.prefab_seats[parseInt(type[1])].mainid = new int[type.length - 2];
									int cnt = -2;
									for(String column:type){
										if(cnt < 0){
											cnt++;
											continue;
										}
										currentVehicleData.prefab_seats[parseInt(type[1])].mainid[cnt] = parseInt(column);
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
										currentVehicleData.setModel();
										if (!partslist.isEmpty()) currentVehicleData.partslist = partslist;
									}
									prefabBaseHashMap.put(dataName, currentVehicleData);
									Item check = GameRegistry.findItem("HMVehicle",dataName);
									if(check == null){
										Item itemVehicle;
										GameRegistry.registerItem(itemVehicle = new ItemVehicle(dataName).setUnlocalizedName(dataName), dataName);
										if(tabname == null) itemVehicle.setCreativeTab(tabHMV);
										else if(tabshmg.containsKey(tabname)){
											itemVehicle.setCreativeTab(tabshmg.get(tabname));
										}
										itemVehicle.setTextureName("handmadevehicle:"+dataName);
									}
									break;
							}
							currentVehicleData.readSettings(type);
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
			System.out.println("Failed in Line " + l);
			System.out.println("" + str_Debug);
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
			case "IsPera":
				((HMVVehicleParts)currentParts).setIsPera(Boolean.parseBoolean(type[readerCnt++]));
				break;
			case "IsCloningTrack":
				((HMVVehicleParts)currentParts).setIsTrack_Cloning(Boolean.parseBoolean(type[readerCnt++]), parseInt(type[readerCnt++]));
				break;
			case "TurretParts":
				((HMVVehicleParts)currentParts).isTurretParts = true;
				((HMVVehicleParts)currentParts).linkedTurretID = parseInt(type[readerCnt++]);
				Vector3d weaponPos = currentVehicleData.prefab_attachedWeapons_all[((HMVVehicleParts)currentParts).linkedTurretID].turretsPos;
				currentParts.AddRenderinfDefoffset((float) weaponPos.x / currentVehicleData.scale * 0.5f,
						(float) weaponPos.y / currentVehicleData.scale * 0.5f,
						(float) -weaponPos.z / currentVehicleData.scale * 0.5f,
						0, 0, 0);
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
