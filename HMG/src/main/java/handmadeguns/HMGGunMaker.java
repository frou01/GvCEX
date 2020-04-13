package handmadeguns;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import handmadeguns.items.*;
import handmadeguns.items.guns.*;
import handmadeguns.client.render.*;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.vecmath.Vector3d;

import static handmadeguns.HandmadeGunsCore.*;
import static java.lang.Boolean.parseBoolean;
import static java.lang.Double.parseDouble;
import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;
import static java.lang.Math.*;

public class HMGGunMaker {
	public static ArrayList Guns = new ArrayList();

	public static int currentIndex = 0;
	public static HMGGunParts currentParts = null;




	public void load( boolean isClient, File file1) {
		GunInfo gunInfo = new GunInfo();
		String  GunName = null;
		String  displayNamegun = null;
		String  objtexture;
		String  objmodel;
		float   modelhigh = 0;
		float   modelhighr = 0;
		float   modelhighs = 0;
		float   modelwidthx = 0;
		float   modelwidthxr = 0;
		float   modelwidthxs = 0;
		float   modelwidthz = 0;
		float   modelwidthzr = 0;
		float   modelwidthzs = 0;
		float   rotationx = 0;
		float   rotationxr = 0;
		float   rotationxs = 0;
		float   rotationy = 0;
		float   rotationyr = 0;
		float   rotationys = 0;
		float   rotationz = 0;
		float   rotationzr = 0;
		float   rotationzs = 0;
		boolean arm;
		float   armrotationxr;
		float   armrotationyr;
		float   armrotationzr;
		float   armoffsetxr;
		float   armoffsetyr;
		float   armoffsetzr;
		float   armrotationxl;
		float   armrotationyl;
		float   armrotationzl;
		float   armoffsetxl;
		float   armoffsetyl;
		float   armoffsetzl;
		float   nox = -0.7F;
		float   noy = 0.7F;
		float   noz = 0F;
		float[] thirdGunOffset = {0,0,0};
		float   eqrotax = 0;
		float   eqrotay = 0;
		float   eqrotaz = 0;
		float   mat31posx;
		float   mat31posy;
		float   mat31posz;
		float   mat31rotex;
		float   mat31rotey;
		float   mat31rotez;
		float   mat32posx;
		float   mat32posy;
		float   mat32posz;
		float   mat32rotex;
		float   mat32rotey;
		float   mat32rotez;
		boolean mat22;
		float   mat22posx;
		float   mat22posy;
		float   mat22posz;
		float   mat22rotex;
		float   mat22rotey;
		float   mat22rotez;
		float   mat25posx;
		float   mat25posy;
		float   mat25posz;
		float   mat25rotex;
		float   mat25rotey;
		float   mat25rotez;
		float   spposx;
		float   spposy;
		float   spposz;
		float   sprotex;
		float   sprotey;
		float   sprotez;
		int     firetype = 0;
		int     righttype = 0;
		boolean semi = false;
		int     BPitemstack;
		Item[]  BPaddi;
		String[]  bptemnames = null;
		boolean mat25;
		boolean mat2;
		boolean alljump;
		float   jump;
		boolean cockleft;
		Item    itema = null;
		Item    itemb = null;
		Item    itemc = null;
		Item    itemd = null;
		Item    iteme = null;
		Item    itemf = null;
		Item    itemg = null;
		Item    itemh = null;
		Item    itemi = null;

		ItemStack    itemblocka = null;
		ItemStack    itemblockb = null;
		ItemStack    itemblockc = null;
		ItemStack    itemblockd = null;
		ItemStack    itemblocke = null;
		ItemStack    itemblockf = null;
		ItemStack    itemblockg = null;
		ItemStack    itemblockh = null;
		ItemStack    itemblocki = null;
		String  re1 = "abc";
		String  re2 = "def";
		String  re3 = "ghi";
		boolean remat31;
		boolean reloadanim = true;
		ArrayList<Float[]> reloadanimation = new ArrayList<Float[]>();
		int maxstacksize = 1;
		double[] seatoffset = new double[]{0,0,0};
		objmodel = "ar.obj";
		objtexture = "ar.png";
		BPitemstack = 1;
		BPaddi = null;
		modelwidthx = 0.694F;
		modelwidthxr = 0.694F;
		modelwidthxs = 0.694F;
		modelwidthz = 0F;
		modelwidthzr = 0F;
		modelwidthzs = 0F;

		arm = false;
		armrotationxr = -1.57F;
		armrotationyr = 0F;
		armrotationzr = 0F;
		armoffsetxr = 0.5F;
		armoffsetyr = 0.5F;
		armoffsetzr = 0.5F;
		armrotationxl = -0.8F;
		armrotationyl = 0F;
		armrotationzl = 90F;
		armoffsetxl = 0.1F;
		armoffsetyl = 0.3F;
		armoffsetzl = -1F;

		mat31posx = 0F;
		mat31posy = 0F;
		mat31posz = 0F;
		mat31rotex = 0F;
		mat31rotez = 0F;
		mat31rotey = 0F;

		mat32posx = 0F;
		mat32posy = 0.5F;
		mat32posz = 0F;
		mat32rotex = 0F;
		mat32rotez = 0F;
		mat32rotey = 0F;

		mat22 = false;
		mat22posx = 0F;
		mat22posy = 1.5F;
		mat22posz = 2F;
		mat22rotex = 90F;
		mat22rotey = 0F;
		mat22rotez = 0F;

		mat25posx = 0F;
		mat25posy = 0.75F;
		mat25posz = 1.1F;
		mat25rotex = 0F;
		mat25rotey = 0F;
		mat25rotez = -90F;

		spposx = 0.5F;
		spposy = 0.0F;
		spposz = 0.5F;
		sprotex = 20F;
		sprotey = 60F;
		sprotez = 0F;

		mat2 = false;
		mat25 = false;
		alljump = false;
		cockleft = false;
		jump = 0;










		re1 = "abc";
		re2 = "def";
		re3 = "ghi";
		displayNamegun = null;
		
		remat31 = true;

		reloadanim = false;
		reloadanimation = new ArrayList<Float[]>();
		maxstacksize = 1;
		float armscale = 1;
		float gunPartsScale = 1;
//		boolean g_can_use = true;
//		boolean can_be_Root = true;
//		boolean soldiercanstorage = true;
//		boolean useinternalsettings = false;
//		float induction_precision = 0;
//		int canuseclass = -1;
//		float acceleration = 0;
//		boolean canlock = false;
//		boolean canlockBlock = false;
//		boolean canlockEntity = false;
		float barrelattachoffset[] = new float[3];
		float barrelattachrotation[] = new float[3];
		float sightattachoffset[] = new float[3];
		float sightattachrotation[] = new float[3];
		float lightattachoffset[] = new float[3];
		float lightattachrotation[] = new float[3];
		float gripattachoffset[] = new float[3];
		float gripattachrotation[] = new float[3];
		String tabname = null;
//
//		boolean restrictTurretMoveSpeed = false;
//		float turretMoveSpeedP = 0;
//		float turretMoveSpeedY = 0;
//		boolean restrictTurretAngle = false;
//		float turretanglelimtMxP = 0;
//		float turretanglelimtMxY = 0;
//		float turretanglelimtmnP = 0;
//		float turretanglelimtmnY = 0;
//		float boxW = 1;
//		float boxH = 1;
//		float onTurretScale = 1;
//		int turretHP = 0;
//		boolean[] hasNightVision = new boolean[]{false,false,false};

		ArrayList<HMGGunParts> partslist = new ArrayList<HMGGunParts>();
		currentIndex = 0;

		try {
			File file = file1;
			// File file = new File(configfile,"hmg_handmadeguns.txt");
			if (checkBeforeReadfile(file)) {
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"Shift-JIS"));

				String str;
				while ((str = br.readLine()) != null) { // 1行ずつ読み込む
					// System.out.println(str);
					String[] type = str.split(",");
					if(!type[0].equals("Recipe1")&& !type[0].equals("Recipe2") && !type[0].equals("Recipe3") && !type[0].equals("addRecipe") && !type[0].equals("addNewRecipe") && !type[0].equals("Magazine")){
						for(int i=0;i<type.length;i++){
							type[i] = type[i].trim();
						}
					}
					readFireInfo(gunInfo,type);


					if (type.length != 0){// 1

						switch (type[0]) {
							case "Name":
								displayNamegun = type[1];
								break;
							case "ReloadMotion":
								while ((str = br.readLine()) != null) {
									reloadanim = true;
									if (str.equals("{")) continue;
									if (str.equals("}")) break;
									if (str.isEmpty()) continue;
									String[] key = str.split(",");
									reloadanimation.add(parseInt(key[0]), new Float[]{
													parseFloat(key[1]),
													
													parseFloat(key[2]),
													parseFloat(key[3]), parseFloat(key[4]), parseFloat(key[5]),
													parseFloat(key[6]), parseFloat(key[7]), parseFloat(key[8]),
													
													parseFloat(key[9]),
													
													parseFloat(key[10]), parseFloat(key[11]), parseFloat(key[12]),
													parseFloat(key[13]), parseFloat(key[14]), parseFloat(key[15]),
													
													parseFloat(key[16]), parseFloat(key[17]), parseFloat(key[18]),
													parseFloat(key[19]), parseFloat(key[20]), parseFloat(key[21]),
													
													parseFloat(key[22]),
													parseFloat(key[23]), parseFloat(key[24]), parseFloat(key[25]),
													parseFloat(key[26]), parseFloat(key[27]), parseFloat(key[28]),
													parseFloat(key[29]), parseFloat(key[30]), parseFloat(key[31])
											}
									);
								}
								break;
							case "RendeScript":
							case "Script": {
								gunInfo.userenderscript = true;
								FileReader sc = new FileReader(new File(HMG_proxy.ProxyFile(), "mods" + File.separatorChar + "handmadeguns"
										                                                           + File.separatorChar + "assets" + File.separatorChar + "handmadeguns" + File.separatorChar +
										                                                           "scripts" + File.separatorChar + type[1])); // ファイルを開く
								gunInfo.renderscript = doScript(sc);
								break;
							}
							case "GunScript": {
								FileReader sc = new FileReader(new File(HMG_proxy.ProxyFile(), "mods" + File.separatorChar + "handmadeguns"
										                                                           + File.separatorChar + "assets" + File.separatorChar + "handmadeguns" + File.separatorChar +
										                                                           "scripts" + File.separatorChar + type[1])); // ファイルを開く
								gunInfo.script = doScript(sc);
								break;
							}
							case "CanObj":
								gunInfo.canobj = parseBoolean(type[1]);
								break;
							case "ObjModel":
								objmodel = type[1];
								break;
							case "ObjTexture":
								objtexture = type[1];
								break;
							case "ModelEquipped":
								nox = parseFloat(type[1]);
								noy = parseFloat(type[2]);
								noz = parseFloat(type[3]);
								break;
							case "ThirdModelEquipped":
								thirdGunOffset = new float[]{parseFloat(type[1]), parseFloat(type[2]), parseFloat(type[3])};
								break;
							case "ModelEquippedRotation":
								eqrotax = parseFloat(type[1]);
								eqrotay = parseFloat(type[2]);
								eqrotaz = parseFloat(type[3]);
								break;
							case "ModelHigh":
							case "ADSOffsetY":
								modelhigh = parseFloat(type[1]);
								modelhighr = parseFloat(type[2]);
								modelhighs = parseFloat(type[3]);
								break;
							case "ModelWidthX":
							case "ADSOffsetX":
								modelwidthx = parseFloat(type[1]);
								modelwidthxr = parseFloat(type[2]);
								modelwidthxs = parseFloat(type[3]);
								break;
							case "ModelWidthZ":
							case "ADSOffsetZ":
								modelwidthz = parseFloat(type[1]);
								modelwidthzr = parseFloat(type[2]);
								modelwidthzs = parseFloat(type[3]);
								break;

							case "SimpleADSOffsetX":
								modelwidthx = 0.694f-parseFloat(type[1]);
								modelwidthxr = 0.694f-parseFloat(type[2]);
								modelwidthxs = 0.694f-parseFloat(type[3]);
								break;
							case "SimpleADSOffsetY":
								modelhigh = 1.8f-parseFloat(type[1]);
								modelhighr = 1.8f-parseFloat(type[2]);
								modelhighs = 1.8f-parseFloat(type[3]);
								break;
							case "SimpleADSOffsetZ":
								modelwidthz = -1.4f + parseFloat(type[1]);
								modelwidthzr = -1.4f + parseFloat(type[2]);
								modelwidthzs = -1.4f + parseFloat(type[3]);
								break;
							case "ModelRotationX":
								rotationx = -180 + parseFloat(type[1]);
								rotationxr = -180 + parseFloat(type[2]);
								rotationxs = -180 + parseFloat(type[3]);
								break;
							case "ModelRotationY":
								rotationy = -45 + parseFloat(type[1]);
								rotationyr = -45 + parseFloat(type[2]);
								rotationys = -45 + parseFloat(type[3]);
								break;
							case "ModelRotationZ":
								rotationz = -180 + parseFloat(type[1]);
								rotationzr = -180 + parseFloat(type[2]);
								rotationzs = -180 + parseFloat(type[3]);
								break;
							case "ModelRotationX2":
								rotationx = parseFloat(type[1]);
								rotationxr = parseFloat(type[2]);
								rotationxs = parseFloat(type[3]);
								break;
							case "ModelRotationY2":
								rotationy = parseFloat(type[1]);
								rotationyr = parseFloat(type[2]);
								rotationys = parseFloat(type[3]);
								break;
							case "ModelRotationZ2":
								rotationz = parseFloat(type[1]);
								rotationzr = parseFloat(type[2]);
								rotationzs = parseFloat(type[3]);
								break;
							case "ModelArm":
								arm = parseBoolean(type[1]);
								break;
							case "ModelArmRotationR":
								armrotationxr = parseFloat(type[1]);
								armrotationyr = parseFloat(type[2]);
								armrotationzr = parseFloat(type[3]);
								break;
							case "ModelArmOffsetR":
								armoffsetxr = parseFloat(type[1]);
								armoffsetyr = parseFloat(type[2]);
								armoffsetzr = parseFloat(type[3]);
								break;
							case "ModelArmRotationL":
								armrotationxl = parseFloat(type[1]);
								armrotationyl = parseFloat(type[2]);
								armrotationzl = parseFloat(type[3]);
								break;
							case "ModelArmOffsetL":
								armoffsetxl = parseFloat(type[1]);
								armoffsetyl = parseFloat(type[2]);
								armoffsetzl = parseFloat(type[3]);
								break;
							case "Mat31Point":
								mat31posx = parseFloat(type[1]);
								mat31posy = parseFloat(type[2]);
								mat31posz = parseFloat(type[3]);
								break;
							case "Mat31Rotation":
								mat31rotex = parseFloat(type[1]);
								mat31rotey = parseFloat(type[2]);
								mat31rotez = parseFloat(type[3]);
								break;
							case "Mat32Point":
								mat32posx = parseFloat(type[1]);
								mat32posy = parseFloat(type[2]);
								mat32posz = parseFloat(type[3]);
								break;
							case "Mat32Rotation":
								mat32rotex = parseFloat(type[1]);
								mat32rotey = parseFloat(type[2]);
								mat32rotez = parseFloat(type[3]);
								break;
							case "Mat22":
								mat22 = parseBoolean(type[1]);
								break;
							case "Mat22Point":
								mat22posx = parseFloat(type[1]);
								mat22posy = parseFloat(type[2]);
								mat22posz = parseFloat(type[3]);
								break;
							case "Mat22Rotation":
								mat22rotex = parseFloat(type[1]);
								mat22rotey = parseFloat(type[2]);
								mat22rotez = parseFloat(type[3]);
								break;
							case "Mat25Point":
								mat25posx = parseFloat(type[1]);
								mat25posy = parseFloat(type[2]);
								mat25posz = parseFloat(type[3]);
								break;
							case "Mat25Rotation":
								mat25rotex = parseFloat(type[1]);
								mat25rotey = parseFloat(type[2]);
								mat25rotez = parseFloat(type[3]);
								break;
							case "SprintingPoint":
								spposx = parseFloat(type[1]);
								spposy = parseFloat(type[2]);
								spposz = parseFloat(type[3]);
								break;
							case "SprintingRotation":
								sprotex = parseFloat(type[1]);
								sprotey = parseFloat(type[2]);
								sprotez = parseFloat(type[3]);
								break;
							case "MuzzleJump":
								jump = parseFloat(type[1]);
								break;
							case "CockedLeftHand":
								cockleft = parseBoolean(type[1]);
								break;
							case "ALLCocked":
								alljump = parseBoolean(type[1]);
								break;
							case "ReloadMat31":
								remat31 = parseBoolean(type[1]);
								break;
							case "GripSetPoint":
								for (int i = 0; i < 3; i++)
									gripattachoffset[i] = parseFloat(type[i + 1]);
								break;
							case "GripSetAngle":
								for (int i = 0; i < 3; i++)
									gripattachrotation[i] = parseFloat(type[i + 1]);
								break;
							case "MaxstackSize":
								maxstacksize = parseInt(type[1]);
								break;
							case "armoffsetScale":
								armscale = parseFloat(type[1]);
								break;
							case "SightSetPoint":
								for (int i = 0; i < 3; i++)
									sightattachoffset[i] = parseFloat(type[i + 1]);
								break;
							case "sightattachrotation":
								for (int i = 0; i < 3; i++)
									sightattachrotation[i] = parseFloat(type[i + 1]);
								break;
							case "LightSetPoint":
								for (int i = 0; i < 3; i++)
									lightattachoffset[i] = parseFloat(type[i + 1]);
								break;
							case "LightSetAngle":
								for (int i = 0; i < 3; i++)
									lightattachrotation[i] = parseFloat(type[i + 1]);
								break;
							case "MuzzleSetPoint":
								for (int i = 0; i < 3; i++)
									barrelattachrotation[i] = parseFloat(type[i + 1]);
								break;
							case "Tabname":
								tabname = type[1];
								break;
							case "BPItemStack":
								BPitemstack = parseInt(type[1]);
								BPaddi = new Item[BPitemstack];
								bptemnames = new String[BPitemstack];
								break;
							case "BPItems":
							/*
							 * for(int ii = 0; ii < itemstack; ++ii){ //addi =
							 * new Item[GameRegistry.findItem("HandmadeGuns",
							 * type[1+ ii])]; Item add =
							 * GameRegistry.findItem("HandmadeGuns", type[1+
							 * ii]); addit.add(add);
							 *
							 * }
							 */
								for (int ii = 0; ii < BPitemstack; ++ii) {
									BPaddi[ii] = GameRegistry.findItem("HandmadeGuns", type[1 + ii]);
									bptemnames[ii] = type[1 + ii];
								}
								break;
							
							
							case "OnEntity_PlayerPosOffset":
								seatoffset = new double[]{parseDouble(type[1]), parseDouble(type[2]), parseDouble(type[3])};
								break;

							case "Gunparts_offsetScale":
								if(isClient)gunPartsScale = parseFloat(type[1]);
								break;
						}
						if(isClient) {
							readParts(type,partslist);
						}
						
						if(cfg_forceunifiedguns) {
							HMGItem_Unified_Guns newgun = null;
							switch (type[0]) {
								case "HG":
									newgun = new HMGItem_Unified_Guns();

									GunName = type[1];
									newgun.setUnlocalizedName(GunName)
											.setTextureName("handmadeguns:" + gunInfo.texture).setMaxDamage(gunInfo.bulletRound)
									;
									if (gunInfo.burstcount.isEmpty()) {
										gunInfo.burstcount.add(1);
									}
									if (gunInfo.guntype == -1)
										gunInfo.guntype = 0;//通常弾
									
									if(gunInfo.canuseclass != -1) {
									} else gunInfo.canuseclass = 0;//通常ゲリラ

									
									if(!gunInfo.hasspreadDiffusionSettings){
										gunInfo.spreadDiffusionMax = 8;
										gunInfo.spreadDiffusionmin = 0;
										gunInfo.spreadDiffusionRate = 4;
										gunInfo.spreadDiffusionHeadRate = 0.5f;
										gunInfo.spreadDiffusionWalkRate = 4;
										gunInfo.spreadDiffusionReduceRate = 2;
									}
									if (displayNamegun != null) {
										LanguageRegistry.instance().addNameForObject(newgun, "en_US", displayNamegun);
									} else {
										LanguageRegistry.instance().addNameForObject(newgun, "en_US", GunName);
									}
									Guns.add(newgun);
									break;
								case "AR":
									newgun = new HMGItem_Unified_Guns();

									GunName = type[1];
									newgun.setUnlocalizedName(GunName).setTextureName("handmadeguns:" + gunInfo.texture)
											.setMaxDamage(gunInfo.bulletRound);

									if (displayNamegun != null) {
										LanguageRegistry.instance().addNameForObject(newgun, "en_US", displayNamegun);
									} else {
										LanguageRegistry.instance().addNameForObject(newgun, "en_US", GunName);
									}
									if (gunInfo.guntype == -1)
										gunInfo.guntype = 0;
									if (gunInfo.canuseclass != -1) {
									} else gunInfo.canuseclass = 0;//通常ゲリラ

									if (gunInfo.burstcount.isEmpty()) {
										gunInfo.burstcount.add(-1);
									}
									if(!gunInfo.hasspreadDiffusionSettings){
										gunInfo.spreadDiffusionMax = 4;
										gunInfo.spreadDiffusionmin = 0;
										gunInfo.spreadDiffusionRate = 0.9f;
										gunInfo.spreadDiffusionHeadRate = 0.2f;
										gunInfo.spreadDiffusionWalkRate = 3;
										gunInfo.spreadDiffusionReduceRate = 0.5f;
									}
									Guns.add(newgun);
									break;
								case "SG":
									newgun = new HMGItem_Unified_Guns();

									GunName = type[1];
									newgun.setUnlocalizedName(GunName).setTextureName("handmadeguns:" + gunInfo.texture)
											.setMaxDamage(gunInfo.bulletRound);

									if (displayNamegun != null) {
										LanguageRegistry.instance().addNameForObject(newgun, "en_US", displayNamegun);
									} else {
										LanguageRegistry.instance().addNameForObject(newgun, "en_US", GunName);
									}
									if (gunInfo.burstcount.isEmpty()) {
										gunInfo.burstcount.add(1);
									}
									if (gunInfo.guntype == -1)
										gunInfo.guntype = 1;
									if (gunInfo.canuseclass != -1) {
									} else gunInfo.canuseclass = 1;//ショットガンゲリラ
									
									if(!gunInfo.hasspreadDiffusionSettings){
										gunInfo.spreadDiffusionMax = 0;
										gunInfo.spreadDiffusionmin = 0;
									}
									Guns.add(newgun);
									break;
								case "SGF":
									newgun = new HMGItem_Unified_Guns();

									GunName = type[1];
									newgun.setUnlocalizedName(GunName).setTextureName("handmadeguns:" + gunInfo.texture)
											.setMaxDamage(gunInfo.bulletRound);

									if (displayNamegun != null) {
										LanguageRegistry.instance().addNameForObject(newgun, "en_US", GunName);
									} else {
										LanguageRegistry.instance().addNameForObject(newgun, "en_US", GunName);
									}
									if (gunInfo.guntype == -1)
										gunInfo.guntype = 1;
									if (gunInfo.canuseclass != -1) {
									} else gunInfo.canuseclass = 1;//ショットガンゲリラ
									if (gunInfo.burstcount.isEmpty()) {
										gunInfo.burstcount.add(-1);
									}
									Guns.add(newgun);
									if(!gunInfo.hasspreadDiffusionSettings){
										gunInfo.spreadDiffusionMax = 0;
										gunInfo.spreadDiffusionmin = 0;
									}
									break;
								case "SR":
									newgun = new HMGItem_Unified_Guns();

									GunName = type[1];
									newgun.setUnlocalizedName(GunName).setTextureName("handmadeguns:" + gunInfo.texture)
											.setMaxDamage(gunInfo.bulletRound);

									if (displayNamegun != null) {
										LanguageRegistry.instance().addNameForObject(newgun, "en_US", displayNamegun);
									} else {
										LanguageRegistry.instance().addNameForObject(newgun, "en_US", GunName);
									}
									if (gunInfo.burstcount.isEmpty()) {
										gunInfo.burstcount.add(1);
									}
									if (gunInfo.guntype == -1)
										gunInfo.guntype = 0;
									if (gunInfo.canuseclass != -1) {
									} else gunInfo.canuseclass = 2;//スナイパーゲリラ
									Guns.add(newgun);
									if(!gunInfo.hasspreadDiffusionSettings){
										gunInfo.spreadDiffusionMax = 8;
										gunInfo.spreadDiffusionmin = 0;
										gunInfo.spreadDiffusionRate = 4;
										gunInfo.spreadDiffusionHeadRate = 0.5f;
										gunInfo.spreadDiffusionWalkRate = 4;
										gunInfo.spreadDiffusionReduceRate = 2;
									}
									break;
								case "AMR":
									newgun = new HMGItem_Unified_Guns();

									GunName = type[1];
									newgun.setUnlocalizedName(GunName).setTextureName("handmadeguns:" + gunInfo.texture)
											.setMaxDamage(gunInfo.bulletRound);

									if (displayNamegun != null) {
										LanguageRegistry.instance().addNameForObject(newgun, "en_US", displayNamegun);
									} else {
										LanguageRegistry.instance().addNameForObject(newgun, "en_US", GunName);
									}
									if (gunInfo.burstcount.isEmpty()) {
										gunInfo.burstcount.add(1);
									}
									if (gunInfo.guntype == -1)
										gunInfo.guntype = 4;
									if (gunInfo.canuseclass != -1) {
									} else gunInfo.canuseclass = 2;//スナイパーゲリラ
									Guns.add(newgun);
									if(!gunInfo.hasspreadDiffusionSettings){
										gunInfo.spreadDiffusionMax = 8;
										gunInfo.spreadDiffusionmin = 0;
										gunInfo.spreadDiffusionRate = 4;
										gunInfo.spreadDiffusionHeadRate = 0.5f;
										gunInfo.spreadDiffusionWalkRate = 4;
										gunInfo.spreadDiffusionReduceRate = 1;
									}
									break;
								case "LMG":
									newgun = new HMGItem_Unified_Guns();

									GunName = type[1];
									newgun.setUnlocalizedName(GunName).setTextureName("handmadeguns:" + gunInfo.texture)
											.setMaxDamage(gunInfo.bulletRound);

									if (displayNamegun != null) {
										LanguageRegistry.instance().addNameForObject(newgun, "en_US", displayNamegun);
									} else {
										LanguageRegistry.instance().addNameForObject(newgun, "en_US", GunName);
									}
									if (gunInfo.guntype == -1)
										gunInfo.guntype = 0;
									if (gunInfo.canuseclass != -1) {
									} else gunInfo.canuseclass = 3;//機関銃ゲリラ
									if (gunInfo.burstcount.isEmpty()) {
										gunInfo.burstcount.add(-1);
									}
									Guns.add(newgun);
									if(!gunInfo.hasspreadDiffusionSettings){
										gunInfo.spreadDiffusionMax = 3;
										gunInfo.spreadDiffusionmin = 0;
										gunInfo.spreadDiffusionRate = 0.3f;
										gunInfo.spreadDiffusionHeadRate = 0;
										gunInfo.spreadDiffusionWalkRate = 2;
										gunInfo.spreadDiffusionReduceRate = 0.1f;
									}
									break;
								case "RR":
									newgun = new HMGItem_Unified_Guns();

									GunName = type[1];
									newgun.setUnlocalizedName(GunName).setTextureName("handmadeguns:" + gunInfo.texture)
											.setMaxDamage(gunInfo.bulletRound);

									if (displayNamegun != null) {
										LanguageRegistry.instance().addNameForObject(newgun, "en_US", displayNamegun);
									} else {
										LanguageRegistry.instance().addNameForObject(newgun, "en_US", GunName);
									}
									if (gunInfo.burstcount.isEmpty()) {
										gunInfo.burstcount.add(1);
									}
									if (gunInfo.guntype == -1)
										gunInfo.guntype = 3;
									if (gunInfo.canuseclass != -1) {
									} else gunInfo.canuseclass = 4;//対物ゲリラ
									Guns.add(newgun);
									if(!gunInfo.hasspreadDiffusionSettings){
										gunInfo.spreadDiffusionMax = 3;
										gunInfo.spreadDiffusionmin = 0;
										gunInfo.spreadDiffusionRate = 1.8f;
										gunInfo.spreadDiffusionHeadRate = 0.2f;
										gunInfo.spreadDiffusionWalkRate = 3;
										gunInfo.spreadDiffusionReduceRate = 0.6f;
									}
									break;
								case "RL":
									newgun = new HMGItem_Unified_Guns();
									
									GunName = type[1];
									newgun.setUnlocalizedName(GunName).setTextureName("handmadeguns:" + gunInfo.texture)
											.setMaxDamage(gunInfo.bulletRound);
									
									if (displayNamegun != null) {
										LanguageRegistry.instance().addNameForObject(newgun, "en_US", displayNamegun);
									} else {
										LanguageRegistry.instance().addNameForObject(newgun, "en_US", GunName);
									}
									if (gunInfo.burstcount.isEmpty()) {
										gunInfo.burstcount.add(1);
									}
									if (gunInfo.guntype == -1)
										gunInfo.guntype = 3;
									if (gunInfo.canuseclass != -1) {
									} else gunInfo.canuseclass = 4;//対物ゲリラ
									Guns.add(newgun);
									if(!gunInfo.hasspreadDiffusionSettings){
										gunInfo.spreadDiffusionMax = 3;
										gunInfo.spreadDiffusionmin = 0;
										gunInfo.spreadDiffusionRate = 1.8f;
										gunInfo.spreadDiffusionHeadRate = 0.2f;
										gunInfo.spreadDiffusionWalkRate = 3;
										gunInfo.spreadDiffusionReduceRate = 0.6f;
									}
									break;
								case "GL":
									newgun = new HMGItem_Unified_Guns();

									GunName = type[1];
									newgun.setUnlocalizedName(GunName).setTextureName("handmadeguns:" + gunInfo.texture)
											.setMaxDamage(gunInfo.bulletRound);

									if (displayNamegun != null) {
										LanguageRegistry.instance().addNameForObject(newgun, "jp_JP", displayNamegun);
										LanguageRegistry.instance().addNameForObject(newgun, "en_US", GunName);
									} else {
										LanguageRegistry.instance().addNameForObject(newgun, "en_US", GunName);
									}
									if (gunInfo.burstcount.isEmpty()) {
										gunInfo.burstcount.add(1);
									}
									if (gunInfo.guntype == -1)
										gunInfo.guntype = 2;
									if (gunInfo.canuseclass != -1) {
									} else gunInfo.canuseclass = 4;//対物ゲリラ
									if(!gunInfo.hasspreadDiffusionSettings){
										gunInfo.spreadDiffusionMax = 3;
										gunInfo.spreadDiffusionmin = 0;
										gunInfo.spreadDiffusionRate = 1.8f;
										gunInfo.spreadDiffusionHeadRate = 0.2f;
										gunInfo.spreadDiffusionWalkRate = 3;
										gunInfo.spreadDiffusionReduceRate = 0.6f;
									}
									Guns.add(newgun);
									break;
								case "BOW":
									GunName = type[1];
									newgun = new HMGItem_Unified_Guns();

									GunName = type[1];
									newgun.setUnlocalizedName(GunName).setTextureName("handmadeguns:" + gunInfo.texture)
											.setMaxDamage(gunInfo.bulletRound);


									if (displayNamegun != null) {
										LanguageRegistry.instance().addNameForObject(newgun, "jp_JP", displayNamegun);
										LanguageRegistry.instance().addNameForObject(newgun, "en_US", GunName);
									} else {
										LanguageRegistry.instance().addNameForObject(newgun, "en_US", GunName);
									}
									gunInfo.chargeType = true;
									if (gunInfo.burstcount.isEmpty()) {
										gunInfo.burstcount.add(1);
									}
									if (gunInfo.guntype == -1)
										gunInfo.guntype = 0;
									if (gunInfo.canuseclass != -1) {
									} else gunInfo.canuseclass = 0;//使えるクラスは通常ゲリラ
									if(!gunInfo.hasspreadDiffusionSettings){
										gunInfo.spreadDiffusionMax = 0;
										gunInfo.spreadDiffusionmin = 0;
									}
									Guns.add(newgun);
									break;
								case "Unified_guns":
									GunName = type[1];
									newgun = new HMGItem_Unified_Guns();

									GunName = type[1];
									newgun.setUnlocalizedName(GunName).setTextureName("handmadeguns:" + gunInfo.texture)
											.setMaxDamage(gunInfo.bulletRound);


									if (displayNamegun != null) {
										LanguageRegistry.instance().addNameForObject(newgun, "jp_JP", displayNamegun);
										LanguageRegistry.instance().addNameForObject(newgun, "en_US", GunName);
									} else {
										LanguageRegistry.instance().addNameForObject(newgun, "en_US", GunName);
									}
									if (gunInfo.canuseclass != -1) {
									} else gunInfo.canuseclass = 0;//使えるクラスは通常ゲリラ
									Guns.add(newgun);
									break;
							}
							if(newgun != null) {
								try {
									Item check = GameRegistry.findItem("HandmadeGuns",GunName);
									if(check == null)
										GameRegistry.registerItem(newgun, GunName);
									else if(check instanceof HMGItem_Unified_Guns){
										newgun = (HMGItem_Unified_Guns) check;
									}
									System.out.println("debug loading " + newgun.getUnlocalizedName());
								}catch (Exception e){
									System.out.println("Warning! Error!" + newgun.getUnlocalizedName());
									e.printStackTrace();
								}
								if (gunInfo.canobj && isClient) {
									IModelCustom gunobj = AdvancedModelLoader.loadModel(new ResourceLocation("handmadeguns:textures/model/" + objmodel));
									ResourceLocation guntexture = new ResourceLocation("handmadeguns:textures/model/" + objtexture);
									if(partslist.isEmpty()) {
										IItemRenderer gunrender = MinecraftForgeClient.getItemRenderer(new ItemStack(newgun), IItemRenderer.ItemRenderType.EQUIPPED);
										if(gunrender instanceof HMGRenderItemGun_U){
											((HMGRenderItemGun_U)gunrender).setSomeParam(gunobj, guntexture,
													gunInfo.modelscale, modelhigh, modelhighr, modelhighs, modelwidthx, modelwidthxr, modelwidthxs, modelwidthz
													, modelwidthzr, modelwidthzs, rotationx, rotationxr, rotationxs, rotationy, rotationyr, rotationys
													, rotationz, rotationzr, rotationzs,
													arm, armrotationxr, armrotationyr, armrotationzr, armoffsetxr, armoffsetyr, armoffsetzr
													, armrotationxl, armrotationyl, armrotationzl, armoffsetxl, armoffsetyl, armoffsetzl,
													nox, noy, noz, mat31posx, mat31posy, mat31posz, mat31rotex, mat31rotey, mat31rotez
													, mat32posx, mat32posy, mat32posz, mat32rotex, mat32rotey, mat32rotez, armscale);
											((HMGRenderItemGun_U)gunrender).barrelattachoffset = barrelattachoffset;
											((HMGRenderItemGun_U)gunrender).barrelattachrotation = barrelattachrotation;
											((HMGRenderItemGun_U)gunrender).sightattachoffset = sightattachoffset;
											((HMGRenderItemGun_U)gunrender).sightattachrotation = sightattachrotation;
											((HMGRenderItemGun_U)gunrender).lightattachoffset = lightattachoffset;
											((HMGRenderItemGun_U)gunrender).lightattachrotation = lightattachrotation;
											((HMGRenderItemGun_U)gunrender).gripattachoffset = gripattachoffset;
											((HMGRenderItemGun_U)gunrender).gripattachrotation = gripattachrotation;
											((HMGRenderItemGun_U)gunrender).mat22offsetx = mat22posx;
											((HMGRenderItemGun_U)gunrender).mat22offsety = mat22posy;
											((HMGRenderItemGun_U)gunrender).mat22offsetz = mat22posz;
											((HMGRenderItemGun_U)gunrender).mat22rotationx = mat22rotex;
											((HMGRenderItemGun_U)gunrender).mat22rotationy = mat22rotey;
											((HMGRenderItemGun_U)gunrender).mat22rotationz = mat22rotez;
											((HMGRenderItemGun_U)gunrender).mat25offsetx = mat25posx;
											((HMGRenderItemGun_U)gunrender).mat25offsety = mat25posy;
											((HMGRenderItemGun_U)gunrender).mat25offsetz = mat25posz;
											((HMGRenderItemGun_U)gunrender).mat25rotationx = mat25rotex;
											((HMGRenderItemGun_U)gunrender).mat25rotationy = mat25rotey;
											((HMGRenderItemGun_U)gunrender).mat25rotationz = mat25rotez;
											((HMGRenderItemGun_U)gunrender).Sprintoffsetx = spposx;
											((HMGRenderItemGun_U)gunrender).Sprintoffsety = spposy;
											((HMGRenderItemGun_U)gunrender).Sprintoffsetz = spposz;
											((HMGRenderItemGun_U)gunrender).Sprintrotationx = sprotex;
											((HMGRenderItemGun_U)gunrender).Sprintrotationy = sprotey;
											((HMGRenderItemGun_U)gunrender).Sprintrotationz = sprotez;
											((HMGRenderItemGun_U)gunrender).jump = jump;
											((HMGRenderItemGun_U)gunrender).cock_left = cockleft;
											((HMGRenderItemGun_U)gunrender).all_jump = alljump;
											((HMGRenderItemGun_U)gunrender).remat31 = remat31;
											((HMGRenderItemGun_U)gunrender).reloadanim = reloadanim;
											((HMGRenderItemGun_U)gunrender).reloadanimation = reloadanimation;
										}else {
											HMGRenderItemGun_U hmgRenderItemGun_u = new HMGRenderItemGun_U(gunobj, guntexture,
													gunInfo.modelscale, modelhigh, modelhighr, modelhighs, modelwidthx, modelwidthxr, modelwidthxs, modelwidthz
													, modelwidthzr, modelwidthzs, rotationx, rotationxr, rotationxs, rotationy, rotationyr, rotationys
													, rotationz, rotationzr, rotationzs,
													arm, armrotationxr, armrotationyr, armrotationzr, armoffsetxr, armoffsetyr, armoffsetzr
													, armrotationxl, armrotationyl, armrotationzl, armoffsetxl, armoffsetyl, armoffsetzl,
													nox, noy, noz, mat31posx, mat31posy, mat31posz, mat31rotex, mat31rotey, mat31rotez
													, mat32posx, mat32posy, mat32posz, mat32rotex, mat32rotey, mat32rotez, armscale);
											hmgRenderItemGun_u.barrelattachoffset = barrelattachoffset;
											hmgRenderItemGun_u.barrelattachrotation = barrelattachrotation;
											hmgRenderItemGun_u.sightattachoffset = sightattachoffset;
											hmgRenderItemGun_u.sightattachrotation = sightattachrotation;
											hmgRenderItemGun_u.lightattachoffset = lightattachoffset;
											hmgRenderItemGun_u.lightattachrotation = lightattachrotation;
											hmgRenderItemGun_u.gripattachoffset = gripattachoffset;
											hmgRenderItemGun_u.gripattachrotation = gripattachrotation;
											mat22 = mat22;
											hmgRenderItemGun_u.mat22offsetx = mat22posx;
											hmgRenderItemGun_u.mat22offsety = mat22posy;
											hmgRenderItemGun_u.mat22offsetz = mat22posz;
											hmgRenderItemGun_u.mat22rotationx = mat22rotex;
											hmgRenderItemGun_u.mat22rotationy = mat22rotey;
											hmgRenderItemGun_u.mat22rotationz = mat22rotez;
											hmgRenderItemGun_u.mat25offsetx = mat25posx;
											hmgRenderItemGun_u.mat25offsety = mat25posy;
											hmgRenderItemGun_u.mat25offsetz = mat25posz;
											hmgRenderItemGun_u.mat25rotationx = mat25rotex;
											hmgRenderItemGun_u.mat25rotationy = mat25rotey;
											hmgRenderItemGun_u.mat25rotationz = mat25rotez;
											hmgRenderItemGun_u.Sprintoffsetx = spposx;
											hmgRenderItemGun_u.Sprintoffsety = spposy;
											hmgRenderItemGun_u.Sprintoffsetz = spposz;
											hmgRenderItemGun_u.Sprintrotationx = sprotex;
											hmgRenderItemGun_u.Sprintrotationy = sprotey;
											hmgRenderItemGun_u.Sprintrotationz = sprotez;
											hmgRenderItemGun_u.jump = jump;
											hmgRenderItemGun_u.cock_left = cockleft;
											hmgRenderItemGun_u.all_jump = alljump;
											hmgRenderItemGun_u.remat31 = remat31;
											hmgRenderItemGun_u.reloadanim = reloadanim;
											hmgRenderItemGun_u.reloadanimation = reloadanimation;
											MinecraftForgeClient.registerItemRenderer(newgun, hmgRenderItemGun_u);
										}
									}else {
										IItemRenderer gunrender = MinecraftForgeClient.getItemRenderer(new ItemStack(newgun), IItemRenderer.ItemRenderType.EQUIPPED);
										if(gunrender instanceof HMGRenderItemGun_U_NEW){
											((HMGRenderItemGun_U_NEW)gunrender).reloadConstructor(gunobj, guntexture, gunInfo.modelscale);
											((HMGRenderItemGun_U_NEW)gunrender).setEqippedOffset_Normal(nox, noy, noz);
											((HMGRenderItemGun_U_NEW)gunrender).setEqippedOffset_Third(thirdGunOffset[0], thirdGunOffset[1], thirdGunOffset[2]);
											((HMGRenderItemGun_U_NEW)gunrender).setEqippedRotation_Normal(eqrotax, eqrotay, eqrotaz);
											
											((HMGRenderItemGun_U_NEW)gunrender).setmodelADSPosAndRotation(modelwidthx, modelhigh, modelwidthz, rotationx, rotationy, rotationz);
											((HMGRenderItemGun_U_NEW)gunrender).setADSoffsetRed(modelwidthxr, modelhighr, modelwidthzr);
											((HMGRenderItemGun_U_NEW)gunrender).setADSoffsetScope(modelwidthxs, modelhighs, modelwidthzs);
											
											((HMGRenderItemGun_U_NEW)gunrender).setADSrotationRed(rotationxr, rotationyr, rotationzr);
											((HMGRenderItemGun_U_NEW)gunrender).setADSrotationScope(rotationxs, rotationys, rotationzs);
											
											((HMGRenderItemGun_U_NEW)gunrender).setarmOffsetAndRotationL(armoffsetxl, armoffsetyl, armoffsetzl, armrotationxl, armrotationyl, armrotationzl);
											((HMGRenderItemGun_U_NEW)gunrender).setarmOffsetAndRotationR(armoffsetxr, armoffsetyr, armoffsetzr, armrotationxr, armrotationyr, armrotationzr);
											((HMGRenderItemGun_U_NEW)gunrender).setArmoffsetScale(armscale);
											((HMGRenderItemGun_U_NEW)gunrender).setSprintOffsetAndRotation(spposx, spposy, spposz, sprotex, sprotey, sprotez);
											
											((HMGRenderItemGun_U_NEW)gunrender).partsRender_gun.partslist = partslist;
											((HMGRenderItemGun_U_NEW)gunrender).partsRender_gun.gunPartsScale = gunPartsScale;
											((HMGRenderItemGun_U_NEW)gunrender).partsRender_gun.muzzleattachoffset = barrelattachoffset;
											((HMGRenderItemGun_U_NEW)gunrender).partsRender_gun.muzzleattachrotation = barrelattachrotation;
											((HMGRenderItemGun_U_NEW)gunrender).partsRender_gun.sightattachoffset = sightattachoffset;
											((HMGRenderItemGun_U_NEW)gunrender).partsRender_gun.sightattachrotation = sightattachrotation;
											((HMGRenderItemGun_U_NEW)gunrender).partsRender_gun.overbarrelattachoffset = lightattachoffset;
											((HMGRenderItemGun_U_NEW)gunrender).partsRender_gun.overbarrelattachrotation = lightattachrotation;
											((HMGRenderItemGun_U_NEW)gunrender).partsRender_gun.gripattachoffset = gripattachoffset;
											((HMGRenderItemGun_U_NEW)gunrender).partsRender_gun.gripattachrotation = gripattachrotation;
											((HMGRenderItemGun_U_NEW)gunrender).jump = jump;
										}else {
											HMGRenderItemGun_U_NEW renderItemGun_u_new = new HMGRenderItemGun_U_NEW(gunobj, guntexture, gunInfo.modelscale);
											renderItemGun_u_new.setEqippedOffset_Normal(nox, noy, noz);
											renderItemGun_u_new.setEqippedOffset_Third(thirdGunOffset[0], thirdGunOffset[1], thirdGunOffset[2]);
											renderItemGun_u_new.setEqippedRotation_Normal(eqrotax, eqrotay, eqrotaz);
											
											renderItemGun_u_new.setmodelADSPosAndRotation(modelwidthx, modelhigh, modelwidthz, rotationx, rotationy, rotationz);
											renderItemGun_u_new.setADSoffsetRed(modelwidthxr, modelhighr, modelwidthzr);
											renderItemGun_u_new.setADSoffsetScope(modelwidthxs, modelhighs, modelwidthzs);
											
											renderItemGun_u_new.setADSrotationRed(rotationxr, rotationyr, rotationzr);
											renderItemGun_u_new.setADSrotationScope(rotationxs, rotationys, rotationzs);
											
											renderItemGun_u_new.setarmOffsetAndRotationL(armoffsetxl, armoffsetyl, armoffsetzl, armrotationxl, armrotationyl, armrotationzl);
											renderItemGun_u_new.setarmOffsetAndRotationR(armoffsetxr, armoffsetyr, armoffsetzr, armrotationxr, armrotationyr, armrotationzr);
											renderItemGun_u_new.setArmoffsetScale(armscale);
											renderItemGun_u_new.setSprintOffsetAndRotation(spposx, spposy, spposz, sprotex, sprotey, sprotez);
											
											renderItemGun_u_new.partsRender_gun.partslist = partslist;
											renderItemGun_u_new.partsRender_gun.gunPartsScale = gunPartsScale;
											renderItemGun_u_new.partsRender_gun.muzzleattachoffset = barrelattachoffset;
											renderItemGun_u_new.partsRender_gun.muzzleattachrotation = barrelattachrotation;
											renderItemGun_u_new.partsRender_gun.sightattachoffset = sightattachoffset;
											renderItemGun_u_new.partsRender_gun.sightattachrotation = sightattachrotation;
											renderItemGun_u_new.partsRender_gun.overbarrelattachoffset = lightattachoffset;
											renderItemGun_u_new.partsRender_gun.overbarrelattachrotation = lightattachrotation;
											renderItemGun_u_new.partsRender_gun.gripattachoffset = gripattachoffset;
											renderItemGun_u_new.partsRender_gun.gripattachrotation = gripattachrotation;
											renderItemGun_u_new.jump = jump;
											MinecraftForgeClient.registerItemRenderer(newgun, renderItemGun_u_new);
										}
									}
								}
								newgun.gunInfo = gunInfo;
								newgun.setMaxStackSize(maxstacksize);
								newgun.setmodelADSPosAndRotation(modelwidthx + seatoffset[0],modelhigh + seatoffset[1],modelwidthz + seatoffset[2]);
								newgun.setADSoffsetRed(modelwidthxr + seatoffset[0],modelhighr + seatoffset[1],modelwidthzr + seatoffset[2]);
								newgun.setADSoffsetScope(modelwidthxs + seatoffset[0],modelhighs + seatoffset[1],modelwidthzs + seatoffset[2]);
								if(tabname == null) newgun.setCreativeTab(HandmadeGunsCore.tabhmg);
								else if(tabshmg.containsKey(tabname)){
									newgun.setCreativeTab(tabshmg.get(tabname));
								}
							}
						}
						if (type[0].equals("SWORD")) {
							GunName = type[1];
							Item newgun = new HMGXItemGun_Sword(gunInfo.power, gunInfo.speed, 0, 0, 0, gunInfo.attackDamage, 1,
									"handmadeguns:" + gunInfo.soundbase, "handmadeguns:" + gunInfo.soundre, gunInfo.renderMCcross, righttype).setUnlocalizedName(GunName).setTextureName("handmadeguns:" + gunInfo.texture)
									.setMaxDamage(gunInfo.bulletRound).setCreativeTab(HandmadeGunsCore.tabhmg);

							Item check = GameRegistry.findItem("HandmadeGuns",GunName);
							if(check == null)
								GameRegistry.registerItem(newgun, GunName);
							else if(check instanceof HMGXItemGun_Sword){
								newgun = (HMGXItemGun_Sword) check;
							}
							System.out.println("debug" + newgun.getUnlocalizedName());

							if(displayNamegun != null){
								LanguageRegistry.instance().addNameForObject(newgun, "en_US", displayNamegun);
							}else{
								LanguageRegistry.instance().addNameForObject(newgun, "en_US", GunName);
							}

							if (gunInfo.canobj && isClient) {
								ResourceLocation guntexture = new ResourceLocation("handmadeguns:textures/model/" + objtexture);
								IModelCustom gunobj = AdvancedModelLoader
										.loadModel(new ResourceLocation("handmadeguns:textures/model/" + objmodel));
								MinecraftForgeClient.registerItemRenderer(newgun, new HMGRenderItemGun_S(gunobj,guntexture,
										                                                                        gunInfo.modelscale, modelhigh, modelhighr, modelhighs, modelwidthx, modelwidthxr, modelwidthxs, modelwidthz
										, modelwidthzr, modelwidthzs, rotationx, rotationxr, rotationxs, rotationy, rotationyr, rotationys
										, rotationz, rotationzr, rotationzs,
										arm, armrotationxr, armrotationyr, armrotationzr, armoffsetxr, armoffsetyr, armoffsetzr
										, armrotationxl, armrotationyl, armrotationzl, armoffsetxl, armoffsetyl, armoffsetzl,
										nox, noy, noz, mat31posx, mat31posy, mat31posz, 	mat31rotex, mat31rotey, mat31rotez
										,mat32posx, mat32posy, mat32posz, mat32rotex, mat32rotey, mat32rotez));
							}
							Guns.add(newgun);
						} else if (type[0].equals("BP")) {
							GunName = type[1];
							/*
							 * for(int ii = 0; ii < itemstack; ++ii){ //addi =
							 * new Item[GameRegistry.findItem("HandmadeGuns",
							 * type[1+ ii])]; Item add =
							 * GameRegistry.findItem("HandmadeGuns", type[2+
							 * ii]); addit.add(add);
							 *
							 * }
							 */

							Item newgun = new HMGItemBattlePack(BPaddi,bptemnames, BPitemstack).setUnlocalizedName(GunName)
									.setTextureName("handmadeguns:" + gunInfo.texture).setCreativeTab(HandmadeGunsCore.tabhmg);
							Item check = GameRegistry.findItem("HandmadeGuns",GunName);
							if(check == null)
								GameRegistry.registerItem(newgun, GunName);

							if(displayNamegun != null){
								LanguageRegistry.instance().addNameForObject(newgun, "en_US", displayNamegun);
							}else{
								LanguageRegistry.instance().addNameForObject(newgun, "en_US", GunName);
							}

							Guns.add(newgun);
							/*
							 * for(int ii = 0; ii < itemstack; ++ii){ //addi =
							 * new Item[GameRegistry.findItem("HandmadeGuns",
							 * type[1+ ii])]; Item add =
							 * GameRegistry.findItem("HandmadeGuns", type[2+
							 * ii]); addit.remove(add);
							 *
							 * }
							 */
						}
						if (type[0].equals("addRecipe")) {
							Item additem = GameRegistry.findItem("HandmadeGuns", type[1]);
							if(additem != null) {
								int num = parseInt(type[2]);
								re1 = type[3];
								re2 = type[4];
								re3 = type[5];
								int ia = parseInt(type[6]);
								int ib = parseInt(type[7]);
								int ic = parseInt(type[8]);
								int id = parseInt(type[9]);
								int ie = parseInt(type[10]);
								int ief = parseInt(type[11]);
								int ig = parseInt(type[12]);
								int ih = parseInt(type[13]);
								int ii = parseInt(type[14]);

								itema = Item.getItemById(ia);
								itemb = Item.getItemById(ib);
								itemc = Item.getItemById(ic);
								itemd = Item.getItemById(id);
								iteme = Item.getItemById(ie);
								itemf = Item.getItemById(ief);
								itemg = Item.getItemById(ig);
								itemh = Item.getItemById(ih);
								itemi = Item.getItemById(ii);

								GameRegistry.addRecipe(new ItemStack(additem, num), re1, re2, re3,
										'a', itema, 'b', itemb,
										'c', itemc, 'd', itemd,
										'e', iteme, 'f', itemf,
										'g', itemg, 'h', itemh,
										'i', itemi);
							}
						} else if (type[0].equals("addSmelting")) {
							Item additem = GameRegistry.findItem("HandmadeGuns", type[1]);
							if(additem != null) {
								float xp = parseFloat(type[2]);

								int ia = parseInt(type[3]);
								itema = Item.getItemById(ia);

								if(itema != null && additem != null)
								GameRegistry.addSmelting(itema, new ItemStack(additem), xp);
							}
						} else if (type[0].equals("addSmelting2")) {
							Item additem = GameRegistry.findItem(type[1], type[2]);
							if(additem != null) {
								float xp = parseFloat(type[5]);
								itema = GameRegistry.findItem(type[3], type[4]);

								if(itema != null && additem != null)
								GameRegistry.addSmelting(itema, new ItemStack(additem), xp);
							}
						}

						if(type[0].equals("Recipe1")){
							re1 = type[1];
						}
						if(type[0].equals("Recipe2")){
							re2 = type[1];
						}
						if(type[0].equals("Recipe3")){
							re3 = type[1];
						}
						if(type[0].equals("ItemA") && !type[1].equals("null")){
							itema = GameRegistry.findItem(type[1], type[2]);
							if(itema == null){
								itemblocka = new ItemStack(GameRegistry.findBlock(type[1], type[2]));
							}
						}
						if(type[0].equals("ItemB") && !type[1].equals("null")){
							itemb = GameRegistry.findItem(type[1], type[2]);
							if(itemb == null){
								itemblockb = new ItemStack(GameRegistry.findBlock(type[1], type[2]));
							}
						}
						if(type[0].equals("ItemC") && !type[1].equals("null")){
							itemc = GameRegistry.findItem(type[1], type[2]);
							if(itemc == null){
								itemblockc = new ItemStack(GameRegistry.findBlock(type[1], type[2]));
							}
						}
						if(type[0].equals("ItemD") && !type[1].equals("null")){
							itemd = GameRegistry.findItem(type[1], type[2]);
							if(itemd == null){
								itemblockd = new ItemStack(GameRegistry.findBlock(type[1], type[2]));
							}
						}
						if(type[0].equals("ItemE") && !type[1].equals("null")){
							iteme = GameRegistry.findItem(type[1], type[2]);
							if(iteme == null){
								itemblocke = new ItemStack(GameRegistry.findBlock(type[1], type[2]));
							}
						}
						if(type[0].equals("ItemF") && !type[1].equals("null")){
							itemf = GameRegistry.findItem(type[1], type[2]);
							if(itemf == null){
								itemblockf = new ItemStack(GameRegistry.findBlock(type[1], type[2]));
							}
						}
						if(type[0].equals("ItemG") && !type[1].equals("null")){
							itemg = GameRegistry.findItem(type[1], type[2]);
							if(itemg == null){
								itemblockg = new ItemStack(GameRegistry.findBlock(type[1], type[2]));
							}
						}
						if(type[0].equals("ItemH") && !type[1].equals("null")){
							itemh = GameRegistry.findItem(type[1], type[2]);
							if(itemh == null){
								itemblockh = new ItemStack(GameRegistry.findBlock(type[1], type[2]));
							}
						}
						if(type[0].equals("ItemI") && !type[1].equals("null")){
							itemi = GameRegistry.findItem(type[1], type[2]);
							if(itemi == null){
								itemblocki = new ItemStack(GameRegistry.findBlock(type[1], type[2]));
							}
						}
						if(type[0].equals("addNewRecipe")){
							Item additem = GameRegistry.findItem(type[1], type[2]);
							if(additem != null) {
								int num = parseInt(type[3]);
								try {
									GameRegistry.addRecipe(new ItemStack(additem, num),
											re1,
											re2,
											re3,
											'a', itema,
											'b', itemb,
											'c', itemc,
											'd', itemd,
											'e', iteme,
											'f', itemf,
											'g', itemg,
											'h', itemh,
											'i', itemi
									);
									itema = itemb = itemc = itemd = iteme = itemf = itemg = itemh = itemi = null;
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
							re1 = "   ";
							re2 = "   ";
							re3 = "   ";
						}


					} // 1

				}
				br.close(); // ファイルを閉じる
			} else {

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//TODO:INJECTING
	public static void addRecipe(File file1){
		String str_Debug = null;
		int l = 0;
		boolean[] onslot = {false,false,false,false,false,false,false,false,false};
		Item[] items = new Item[9];
		ItemStack[] itemstacks = new ItemStack[9];
		String[] itemids;
		byte isfixationrecipe = 0;
		List<Object> itemlist = new ArrayList<Object>(Arrays.asList(new Object[9]));

		try{
			File file = file1;
			if(checkBeforeReadfile(file)){
				BufferedReader br = new BufferedReader(new FileReader(file));

				String str;
				while ((str = br.readLine()) != null){
					str_Debug = str;
					l++;
					String[] type = str.split(",");
					int index;
					if(type[0] == null){
					}else switch(type[0].toUpperCase()){
						case "ADDRECIPE":
							isfixationrecipe = 0;
							break;
						case "ADDSMALLRECIPE":
							isfixationrecipe = 1;
							break;
						case "ADDSHAPELESSRECIPE":
							isfixationrecipe = 2;
							break;
						case "SLOT1":
							setSlot(0, type, onslot, items, itemstacks);
							break;
						case "SLOT2":
							setSlot(1, type, onslot, items, itemstacks);
							break;
						case "SLOT3":
							setSlot(2, type, onslot, items, itemstacks);
							break;
						case "SLOT4":
							setSlot(3, type, onslot, items, itemstacks);
							break;
						case "SLOT5":
							setSlot(4, type, onslot, items, itemstacks);
							break;
						case "SLOT6":
							setSlot(5, type, onslot, items, itemstacks);
							break;
						case "SLOT7":
							setSlot(6, type, onslot, items, itemstacks);
							break;
						case "SLOT8":
							setSlot(7, type, onslot, items, itemstacks);
							break;
						case "SLOT9":
							setSlot(8, type, onslot, items, itemstacks);
							break;
						case "CRAFTITEM":
							itemids = type[1].split(":");
							itemlist = new ArrayList<Object>(Arrays.asList(new Object[9]));
							char[] rf = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i'};
							for(int var0 = 0; var0 < 9; var0++){
								if(items[var0] != null){
									itemlist.set(var0, items[var0]);
								}else{
									itemlist.set(var0, itemstacks[var0]);
								}
								if(!onslot[var0]){
									rf[var0] = ' ';
								}
							}

							System.out.println("AddRecipe----------------------------------------------------------------------------------------------------------------------------");
							for(int count = 0; count < itemlist.size() ; count++){
								System.out.println("Item [" + count + "] : " + (Object)itemlist.get(count));
							}
							switch(isfixationrecipe){
								case 0:
									System.out.println("Recipe1 : " + String.valueOf(new char[]{rf[0], rf[1], rf[2]}));
									System.out.println("Recipe2 : " + String.valueOf(new char[]{rf[3], rf[4], rf[5]}));
									System.out.println("Recipe3 : " + String.valueOf(new char[]{rf[6], rf[7], rf[8]}));
									break;
								case 1:
									System.out.println("Recipe1 : " + String.valueOf(new char[]{rf[0], rf[1]}));
									System.out.println("Recipe2 : " + String.valueOf(new char[]{rf[2], rf[3]}));
									break;
							}

							System.out.println(new ItemStack(GameRegistry.findItem(itemids[0], itemids[1]), parseInt(itemids[3]), parseInt(itemids[2])));

							if(isfixationrecipe == 0){
								try{
									GameRegistry.addRecipe(
											new ItemStack(GameRegistry.findItem(itemids[0], itemids[1]), parseInt(itemids[3]), parseInt(itemids[2])),
											String.valueOf(new char[]{rf[0], rf[1], rf[2]}),
											String.valueOf(new char[]{rf[3], rf[4], rf[5]}),
											String.valueOf(new char[]{rf[6], rf[7], rf[8]}),
											'a',itemlist.get(0),
											'b',itemlist.get(1),
											'c',itemlist.get(2),
											'd',itemlist.get(3),
											'e',itemlist.get(4),
											'f',itemlist.get(5),
											'g',itemlist.get(6),
											'h',itemlist.get(7),
											'i',itemlist.get(8)
									);
								}catch(Exception e){
									e.printStackTrace();
								}
							}else if(isfixationrecipe == 1){
								try{
									GameRegistry.addRecipe(
											new ItemStack(GameRegistry.findItem(itemids[0], itemids[1]), parseInt(itemids[3]), parseInt(itemids[2])),
											String.valueOf(new char[]{rf[0], rf[1]}),
											String.valueOf(new char[]{rf[2], rf[3]}),
											'a',itemlist.get(0),
											'b',itemlist.get(1),
											'c',itemlist.get(2),
											'd',itemlist.get(3)
									);
								}catch(Exception e){
									e.printStackTrace();
								}
							}else if(isfixationrecipe == 2){
								List<Object> recipeitems = new ArrayList<Object>(9);
								for(int var0 = 0 ; var0 < itemlist.size() ; var0++){
									Object object = itemlist.get(var0);
									if(object != null){
										recipeitems.add(object);
									}
								}
								try{
									GameRegistry.addShapelessRecipe(
											new ItemStack(GameRegistry.findItem(itemids[0], itemids[1]), parseInt(itemids[3]), parseInt(itemids[2])),
											recipeitems.toArray(new Object[recipeitems.size()])
									);
								}catch(Exception e){
									e.printStackTrace();
								}
							}
							onslot[0]=onslot[1]=onslot[2]=onslot[3]=onslot[4]=onslot[5]=onslot[6]=onslot[7]=onslot[8]=false;
							items = new Item[9];
							itemstacks = new ItemStack[9];
							itemlist = new ArrayList<Object>(Arrays.asList(new Object[9]));
							break;
					}
				}
				br.close();
			}
		}catch(FileNotFoundException e){
			System.out.println("Failed in Line " + l);
			System.out.println("" + str_Debug);
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public static void setSlot(int index,String[] type, boolean[] onslot ,Item[] items, ItemStack[] itemstacks){
		if(type[1] != null){
			System.out.println("debug Recipe Item " + type[1]);
			String[] itemids = type[1].split(":");
			if(itemids.length == 2){
				System.out.println("debug Recipe modId " + itemids[0]);
				items[index] = GameRegistry.findItem(itemids[0], itemids[1]);
				itemstacks[index] = null;
				if(items[index] == null){
					System.out.println("debug : BlockMode");
					itemstacks[index] = new ItemStack(GameRegistry.findBlock(itemids[0], itemids[1]));
				}
			}
			if(itemids.length == 3){
				itemstacks[index] = new ItemStack(GameRegistry.findItem(itemids[0], itemids[1]), 1, parseInt(itemids[2]));
				if(itemstacks[index] == null){
					itemstacks[index] = new ItemStack(GameRegistry.findBlock(itemids[0], itemids[1]), 1, parseInt(itemids[2]));
				}
				items[index] = null;
			}
			onslot[index] = true;
		}
	}
	//INJECTEND


	public HMGGunParts createGunPart(String[] strings){
		return new HMGGunParts(strings[1]);
	}
	public HMGGunParts createGunPart(String[] strings,int motherID,HMGGunParts mother){
		return new HMGGunParts(strings[1],motherID,mother);
	}
	private static boolean checkBeforeReadfile(File file) {
		if (file.exists()) {
			if (file.isFile() && file.canRead()) {
				return true;
			}
		}

		return false;
	}
	public static ScriptEngine doScript(ResourceLocation resource)
	{
		try
		{
			String script = getText(resource, true);
			return doScript(script);
		}
		catch(IOException e)
		{
			throw new RuntimeException("Script load error : " + resource.getResourcePath(), e);
		}
	}
	private static String getText(ResourceLocation resource, boolean indention) throws IOException
	{
		List<String> list = readText(resource);
		StringBuilder sb = new StringBuilder();
		for(String s : list)
		{
			sb.append(s);
			if(indention)
			{
				sb.append("\n");
			}
		}
		return sb.toString();
	}
	private static List<String> readText(ResourceLocation resource) throws IOException
	{
		List<String> list = new ArrayList<String>();
		InputStream is = getInputStream(resource);
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		String currentLine = null;

		while((currentLine = reader.readLine()) != null)
		{
			list.add(currentLine);
		}

		is.close();
		reader.close();

		return list;
	}
	private static InputStream getInputStream(ResourceLocation par1) throws IOException
	{
		return Minecraft.getMinecraft().getResourceManager().getResource(par1).getInputStream();
	}
	private static ScriptEngine doScript(String s)
	{
		ScriptEngine se = (new ScriptEngineManager(null)).getEngineByName("js");//引数にnull入れないと20でぬるぽ

		try
		{
			if(se.toString().contains("Nashorn"))
			{
				//Java8ではimportPackage()が使えないので、その対策
				se.eval("load(\"nashorn:mozilla_compat.js\");");
			}

			//se.put("packreg", REGISTER);

			se.eval(s);
			return se;
		}
		catch(ScriptException e)
		{
			throw new RuntimeException("Script exec error", e);
		}
	}
	public static int readerCnt = 0;
	public void readParts(String[] type,ArrayList<HMGGunParts> partslist){
		switch (type[0]) {

			case "AddParts":
				currentIndex = partslist.size();
				partslist.add(currentParts = createGunPart(type));
				break;
			case "SetAsNormalParts":
				currentParts.rendering_Def = true;
				currentParts.AddRenderinfDef(0, 0, 0, 0, 0, 0);
				currentParts.AddRenderinfDefoffset(0, 0, 0, 0, 0, 0);
				currentParts.AddRenderinfADS(0, 0, 0, 0, 0, 0);
				currentParts.AddRenderinfRecoil(0, 0, 0, 0, 0, 0);
				currentParts.AddRenderinfCock(0, 0, 0, 0, 0, 0);
				currentParts.AddRenderinfReload(0, 0, 0, 0, 0, 0);
				
				
				break;
			case "AddPartsRotationCenterAndRotationAmount":
				currentParts.AddRenderinfDef(parseFloat(type[1]), parseFloat(type[2]), parseFloat(type[3]), parseFloat(type[4]), parseFloat(type[5]), parseFloat(type[6]));
				break;
			case "AddPartsRotationCenterAndRotationAmount_TypeVector":
				currentParts.AddRenderinfDef(parseFloat(type[1]), parseFloat(type[2]), parseFloat(type[3]), parseFloat(type[4]), parseFloat(type[5]), parseFloat(type[6]), parseFloat(type[7]));
				break;
			case "AddPartsRotationDefOffset":
				currentParts.AddRenderinfDefoffset(parseFloat(type[1]), parseFloat(type[2]), parseFloat(type[3]), parseFloat(type[4]), parseFloat(type[5]), parseFloat(type[6]));
				break;
			case "RenderOnNormal":
				currentParts.rendering_Def = true;
				break;
			case "AddPartsOnADSOffsetAndRotation":
				currentParts.AddRenderinfADS(parseFloat(type[1]), parseFloat(type[2]), parseFloat(type[3]), parseFloat(type[4]), parseFloat(type[5]), parseFloat(type[6]));
				break;
			case "AddPartsOnRecoilOffsetAndRotation":
				currentParts.AddRenderinfRecoil(parseFloat(type[1]), parseFloat(type[2]), parseFloat(type[3]), parseFloat(type[4]), parseFloat(type[5]), parseFloat(type[6]));
				break;
			case "AddPartsOnCockOffsetAndRotation":
				currentParts.AddRenderinfCock(parseFloat(type[1]), parseFloat(type[2]), parseFloat(type[3]), parseFloat(type[4]), parseFloat(type[5]), parseFloat(type[6]));
				break;
			case "AddPartsOnReloadOffsetAndRotation":
				currentParts.AddRenderinfReload(parseFloat(type[1]), parseFloat(type[2]), parseFloat(type[3]), parseFloat(type[4]), parseFloat(type[5]), parseFloat(type[6]));
				break;
			case "AddPartsRenderAsBulletInf":
				currentParts.AddRenderinfBullet(parseFloat(type[1]), parseFloat(type[2]), parseFloat(type[3]), parseFloat(type[4]), parseFloat(type[5]), parseFloat(type[6]));
				break;
			case "AddRecoilMotionKey":
				if (type.length > 4) {
					currentParts.AddMotionKeyRecoil(parseInt(type[1]), parseFloat(type[2]), parseFloat(type[3]), parseFloat(type[4]), parseFloat(type[5]), parseFloat(type[6]), parseFloat(type[7]), parseInt(type[8]), parseFloat(type[9]), parseFloat(type[10]), parseFloat(type[11]), parseFloat(type[12]), parseFloat(type[13]), parseFloat(type[14]));
				} else {
					currentParts.AddMotionKeyRecoil(parseInt(type[1]), parseBoolean(type[2]), parseInt(type[3]));
				}
				break;
			case "AddYawInfoKey":
				if (type.length > 4) {
					currentParts.AddInfoKeyTurretYaw(parseInt(type[1]), parseFloat(type[2]), parseFloat(type[3]), parseFloat(type[4]), parseFloat(type[5]), parseFloat(type[6]), parseFloat(type[7]), parseInt(type[8]), parseFloat(type[9]), parseFloat(type[10]), parseFloat(type[11]), parseFloat(type[12]), parseFloat(type[13]), parseFloat(type[14]));
				} else {
					currentParts.AddInfoKeyTurretYaw(parseInt(type[1]), parseBoolean(type[2]), parseInt(type[3]));
				}
				break;
			case "AddPitchInfoKey":
				if (type.length > 4) {
					currentParts.AddInfoKeyTurretPitch(parseInt(type[1]), parseFloat(type[2]), parseFloat(type[3]), parseFloat(type[4]), parseFloat(type[5]), parseFloat(type[6]), parseFloat(type[7]), parseInt(type[8]), parseFloat(type[9]), parseFloat(type[10]), parseFloat(type[11]), parseFloat(type[12]), parseFloat(type[13]), parseFloat(type[14]));
				} else {
					currentParts.AddInfoKeyTurretPitch(parseInt(type[1]), parseBoolean(type[2]), parseInt(type[3]));
				}
				break;
			case "AddCockMotionKey":
				if (type.length > 4) {
					currentParts.AddMotionKeyCock(parseInt(type[1]), parseFloat(type[2]), parseFloat(type[3]), parseFloat(type[4]), parseFloat(type[5]), parseFloat(type[6]), parseFloat(type[7]), parseInt(type[8]), parseFloat(type[9]), parseFloat(type[10]), parseFloat(type[11]), parseFloat(type[12]), parseFloat(type[13]), parseFloat(type[14]));
				} else {
					currentParts.AddMotionKeyCock(parseInt(type[1]), parseBoolean(type[2]), parseInt(type[3]));
				}
				break;
			case "AddReloadMotionKey":
				if (type.length > 4) {
					currentParts.AddMotionKeyReload(parseInt(type[1]), parseFloat(type[2]), parseFloat(type[3]), parseFloat(type[4]), parseFloat(type[5]), parseFloat(type[6]), parseFloat(type[7]), parseInt(type[8]), parseFloat(type[9]), parseFloat(type[10]), parseFloat(type[11]), parseFloat(type[12]), parseFloat(type[13]), parseFloat(type[14]));
				} else {
					currentParts.AddMotionKeyReload(parseInt(type[1]), parseBoolean(type[2]), parseInt(type[3]));
				}
				break;
			case "AddBulletPositions":

				if (type.length > 4) {
					currentParts.AddBulletPositions(parseInt(type[1]), parseFloat(type[2]), parseFloat(type[3]), parseFloat(type[4]), parseFloat(type[5]), parseFloat(type[6]), parseFloat(type[7]), parseInt(type[8]), parseFloat(type[9]), parseFloat(type[10]), parseFloat(type[11]), parseFloat(type[12]), parseFloat(type[13]), parseFloat(type[14]));
				} else {
					currentParts.AddBulletPositions(parseInt(type[1]), parseBoolean(type[2]), parseInt(type[3]));
				}
				break;
			case "NeedDraw_Current_Magazine_ID_List":
				currentParts.current_magazineType = new ArrayList<Boolean>();
				for(int i = 1;i < type.length;i++){
					currentParts.current_magazineType.add(parseBoolean(type[i]));
				}
				break;
			case "NeedDraw_Select_Magazine_ID_List":
				currentParts.select_magazineType = new ArrayList<Boolean>();
				for(int i = 1;i < type.length;i++){
					currentParts.select_magazineType.add(parseBoolean(type[i]));
				}
				break;
			case "Isbullet":
				currentParts.setIsbullet(parseBoolean(type[1]), parseInt(type[2]));
				break;
			case "attachpart":
				currentParts.isattachpart = true;
				break;
			case "scope":
				currentParts.isscope = true;
				break;
			case "dot":
				currentParts.isdot = true;
				break;
			case "sight":
				currentParts.issight = true;
				break;
			case "grip":
				currentParts.isgrip = true;
				break;
			case "gripcover":
				currentParts.isgripcover = true;
				break;
			case "sword":
				currentParts.issword = true;
				break;
			case "swordbase":
				currentParts.isswordbase = true;
				break;
			case "underSG":
				currentParts.isunderSG = true;
				break;
			case "underGL":
				currentParts.isunderGL = true;
				break;
			case "muzzlepart":
				currentParts.ismuzzlepart = true;
				break;
			case "light":
				currentParts.islight = true;
				break;
			case "lasersight":
				currentParts.islasersight = true;
				break;
			case "gripBase":
				currentParts.isgripBase = true;
				break;
			case "underGunbase":
				currentParts.isunderGunbase = true;
				break;
			case "overbarrelbase":
				currentParts.isoverbarrelbase = true;
				break;
			case "muzzulebase":
				currentParts.ismuzzulebase = true;
				break;
			case "sightbase":
				currentParts.issightbase = true;
				break;
			case "turretbase":
				currentParts.base = true;
				break;
			case "carryingHandle":
				currentParts.carryingHandle = true;
				break;
			case "underOnly":
				currentParts.underOnly = true;
				break;
			case "underOnly_not":
				currentParts.underOnly_not = true;
				break;
			case "reticleAndPlate":
				currentParts.reticleAndPlate = true;
				break;
			case "AddChildParts":
				currentIndex = currentParts.childs.size();
				currentParts.childs.add(currentParts = createGunPart(type, currentIndex, currentParts));
				break;
			case "AddReticleChildParts":
				if(currentParts.reticleChild == null)currentParts.reticleChild = new ArrayList<>();
				currentIndex = currentParts.reticleChild.size();
				currentParts.reticleChild.add(currentParts = createGunPart(type, currentIndex, currentParts));
				break;
			case "BackParts":
				currentIndex = currentParts.motherIndex;
				currentParts = currentParts.mother;
				break;
		}
	}
	
	public static void readFireInfo(GunInfo gunInfo,String[] type){
		switch (type[0]){
			case "BulletPower":
				gunInfo.power = parseInt(type[1]);
				break;
			case "BulletSpeed":
				gunInfo.speed = parseFloat(type[1]);
				break;
			case "BulletGravity":
				gunInfo.gravity = parseFloat(type[1]);
				break;
			case "Explosion":
				gunInfo.ex = parseFloat(type[1]);
				break;
			case "BlockDestory":
				gunInfo.destroyBlock = parseBoolean(type[1]);
				break;
			case "Acceleration":
				gunInfo.acceleration = parseFloat(type[1]);
				break;
			case "Induction_precision":
				gunInfo.induction_precision = parseFloat(type[1]);
				break;
			case "lockOn_MaxSpeed":
				gunInfo.lockOn_MaxSpeed = parseFloat(type[1]);
				break;
			case "lockOn_minSpeed":
				gunInfo.lockOn_minSpeed = parseFloat(type[1]);
				break;
			case "lockOn_MaxThrottle":
				gunInfo.lockOn_MaxThrottle = parseFloat(type[1]);
				break;
			case "lockOn_minThrottle":
				gunInfo.lockOn_minThrottle = parseFloat(type[1]);
				break;
			case "canBounce":
				gunInfo.canbounce = parseBoolean(type[1]);
				break;
			case "BounceRate":
				gunInfo.bouncerate = parseFloat(type[1]);
				break;
			case "BounceLimit":
				gunInfo.bouncelimit = parseFloat(type[1]);
				break;
			case "resistance":
				gunInfo.resistance = parseFloat(type[1]);
				break;
			case "bulletFuse":
				gunInfo.fuse = parseInt(type[1]);
				break;
			case "BlletSpread":
				gunInfo.spread_setting = parseFloat(type[1]);
				break;
			case "BulletSpread":
				gunInfo.spread_setting = parseFloat(type[1]);
				break;
			case "BulletSpreadDiffusion":
			case "BlletSpreadDiffusion":
				gunInfo.hasspreadDiffusionSettings = true;
				gunInfo.spreadDiffusionMax = parseFloat(type[1]);
				gunInfo.spreadDiffusionmin = parseFloat(type[2]);
				gunInfo.spreadDiffusionRate = parseFloat(type[3]);
				gunInfo.spreadDiffusionHeadRate = parseFloat(type[4]);
				gunInfo.spreadDiffusionWalkRate = parseFloat(type[5]);
				gunInfo.spreadDiffusionReduceRate = parseFloat(type[6]);
				break;
			case "ADS_Spread_coefficient":
				gunInfo.ads_spread_cof = parseFloat(type[1]);
				break;
			case "Recoil":
				gunInfo.recoil = parseDouble(type[1]);
				gunInfo.recoil_sneak = parseDouble(type[1]) / 2;
				break;
			case "Recoil_sneaking":
				gunInfo.recoil_sneak = parseDouble(type[1]);
				break;
			case "ReloadTime":
				gunInfo.reloadTimes = new int[type.length-1];
				for(int i = 1;i < type.length;i++){
					gunInfo.reloadTimes[i-1] = parseInt(type[i]);
				}
				break;
			case "RemainingBullet":
				gunInfo.bulletRound = parseInt(type[1]);
				break;
			case "Attacking":
				gunInfo.attackDamage = parseFloat(type[1]);
				break;
			case "Motion":
				gunInfo.motion = parseDouble(type[1]);
				break;
			case "Weight":
				gunInfo.weight = parseDouble(type[1]);
				break;
			case "Zoom":
				gunInfo.scopezoombase = parseFloat(type[1]);
				gunInfo.scopezoomred = parseFloat(type[2]);
				gunInfo.scopezoomscope = parseFloat(type[3]);
				break;
			case "ZoomRender":
				gunInfo.zoomren = parseBoolean(type[1]);
				break;
			case "ZoomRenderType":
				gunInfo.zoomren = parseBoolean(type[1]);
				gunInfo.zoomrer = parseBoolean(type[2]);
				gunInfo.zoomres = parseBoolean(type[3]);
				break;
			case "ZoomRenderTypeTxture":
				gunInfo.zoomrent = parseBoolean(type[1]);
				gunInfo.zoomrert = parseBoolean(type[2]);
				gunInfo.zoomrest = parseBoolean(type[3]);
				break;
			case "ScopeTexture":
				gunInfo.adstexture =  "handmadeguns:textures/misc/" + type[1];
				gunInfo.adstexturer = "handmadeguns:textures/misc/" + type[2];
				gunInfo.adstextures = "handmadeguns:textures/misc/" + type[3];
				break;
			case "RenderCross":
				gunInfo.renderMCcross = parseBoolean(type[1]);
				break;
			case "RenderHMGCross":
				gunInfo.renderHMGcross = parseBoolean(type[1]);
				break;
			case "NightVision":
				gunInfo.hasNightVision[0] = parseBoolean(type[1]);
				gunInfo.hasNightVision[1] = parseBoolean(type[2]);
				gunInfo.hasNightVision[2] = parseBoolean(type[3]);
				break;
			case "Cycle":
				gunInfo.cycle = parseFloat(type[1]);
				break;
			case "Bursts":
				gunInfo.burstcount.clear();
				for (int i = 1; i < type.length; i++)
					gunInfo.burstcount.add(parseInt(type[i]));
				break;
			case "Rates":
				gunInfo.rates.clear();
				for (int i = 1; i < type.length; i++)
					gunInfo.rates.add(parseFloat(type[i]));
				break;
			case "ElevationOffsets":{
				gunInfo.elevationOffsets.clear();
				for (int i = 1; i < type.length; i++)
					gunInfo.elevationOffsets.add(parseFloat(type[i]));
			}
			case "Texture":
				gunInfo.texture = type[1];
				break;
			case "GunSound":
				gunInfo.soundbase = "handmadeguns:" + type[1];
				gunInfo.soundsu = "handmadeguns:" + type[2];
				break;
			case "GunSound_FullName":
				gunInfo.soundbase = type[1];
				gunInfo.soundsu = type[2];
				break;
			case "SoundSpeed":
				gunInfo.soundspeed = parseFloat(type[1]);
				break;
			case "GunSoundLV":
				gunInfo.soundbaselevel = parseFloat(type[1]);
				gunInfo.soundsuplevel = parseFloat(type[2]);
				break;
			case "lockSound_entity":
				gunInfo.lockSound_entity = type[1];
				break;
			case "lockSound_NoStop":
				gunInfo.lockSound_NoStop = parseBoolean(type[1]);
				break;
			case "GunSoundReload":
				gunInfo.soundre = new String[type.length-1];
				for (int i = 1; i < type.length; i++)
					gunInfo.soundre[i-1] = "handmadeguns:" + type[1];
				break;
			case "GunSoundReload_FullPath":
				gunInfo.soundre = new String[type.length-1];
				for (int i = 1; i < type.length; i++)
					gunInfo.soundre[i-1] = type[1];
				break;
			case "GunSoundReloadLV":
				gunInfo.soundrelevel = parseFloat(type[1]);
				break;
			case "GunSoundReloadSP":
				gunInfo.soundrespeed = parseFloat(type[1]);
				break;
			case "GunSoundCooking":
				gunInfo.soundco = "handmadeguns:" + type[1];
				break;
			case "Magazine": {
				int ii = parseInt(type[1]);
				
				if (ii == 0) {
					gunInfo.magazine[0] = GameRegistry.findItem(type[2], type[3]);
				} else {
					gunInfo.magazine[0] = Item.getItemById(ii);
				}
				if(gunInfo.magazine[0] == null){
					System.out.println("debug : No Magazine Item");
				}
				break;
			}
			case "MultiMagazine": {
				gunInfo.magazine = new Item[type.length-1];
				for(int i = 1;i < type.length;i++){
					String[] parts = type[i].split(":");
					String modId = parts[0];
					String name = parts[1];
					gunInfo.magazine[i-1] = GameRegistry.findItem(modId, name);
				}
				break;
			}
			case "ModelScala":
				gunInfo.modelscale = parseFloat(type[1]);
				break;
			case "InworldScale":
				gunInfo.inworldScale = parseFloat(type[1]);
				break;
			case "CockingTime":
				gunInfo.cocktime = parseInt(type[1]);
				gunInfo.needcock = true;
				break;
			case "ShotGun_Pellet":
				gunInfo.pellet = parseInt(type[1]);
				break;
			case "PerFireRound":
				gunInfo.pellet = parseInt(type[1]);
				break;
			case "Cartridge":
				gunInfo.dropcart = parseBoolean(type[1]);
				break;
			case "DropCartridgeEndCocked":
				gunInfo.cart_cocked = parseBoolean(type[1]);
				break;
			case "UnderGL_Sound":
				gunInfo.use_internal_secondary = true;
				gunInfo.soundunder_gl = type[1];
				break;
			case "UnderGL_Power":
				gunInfo.use_internal_secondary = true;
				gunInfo.under_gl_power = parseInt(type[1]);
				break;
			case "UnderGL_Canbounce":
				gunInfo.use_internal_secondary = true;
				gunInfo.under_gl_canbounce = parseBoolean(type[1]);
				break;
			case "UnderGL_Fuse":
				gunInfo.use_internal_secondary = true;
				gunInfo.under_gl_fuse = parseInt(type[1]);
				break;
			case "UnderGL_Speed":
				gunInfo.use_internal_secondary = true;
				gunInfo.under_gl_speed = parseFloat(type[1]);
				break;
			case "UnderGL_Spread":
				gunInfo.use_internal_secondary = true;
				gunInfo.under_gl_bure = parseFloat(type[1]);
				break;
			case "UnderGL_Recoil":
				gunInfo.use_internal_secondary = true;
				gunInfo.under_gl_recoil = parseDouble(type[1]);
				break;
			case "UnderGL_Gravity":
				gunInfo.use_internal_secondary = true;
				gunInfo.under_gl_gra = parseFloat(type[1]);
				break;
			case "UnderSG_Sound":
				gunInfo.use_internal_secondary = true;
				gunInfo.soundunder_sg = type[1];
				break;
			case "UnderSG_Power":
				gunInfo.use_internal_secondary = true;
				gunInfo.under_sg_power = parseInt(type[1]);
				break;
			case "UnderSG_Speed":
				gunInfo.use_internal_secondary = true;
				gunInfo.under_sg_speed = parseFloat(type[1]);
				break;
			case "UnderSG_Spread":
				gunInfo.use_internal_secondary = true;
				gunInfo.under_sg_bure = parseFloat(type[1]);
				break;
			case "UnderSG_Recoil":
				gunInfo.use_internal_secondary = true;
				gunInfo.under_sg_recoil = parseDouble(type[1]);
				break;
			case "UnderSG_Gravity":
				gunInfo.use_internal_secondary = true;
				gunInfo.under_sg_gra = parseFloat(type[1]);
				break;
			case "CartridgeType":
				gunInfo.cartType = parseInt(type[1]);
				break;
			case "MagType":
				gunInfo.magType = parseInt(type[1]);
				break;
			case "MagCount":
				gunInfo.magentityCnt = parseInt(type[1]);
				break;
			case "CartCount":
				gunInfo.cartentityCnt = parseInt(type[1]);
				break;
			case "DropMagazine":
				gunInfo.dropMagEntity = parseBoolean(type[1]);
				break;
			case "setunderRestriction":
				gunInfo.hasAttachRestriction = parseBoolean(type[1]);
				break;
			case "attachRestriction":
				gunInfo.hasAttachRestriction = parseBoolean(type[1]);
				break;
			case "allowundername":
				for (int i = 1; i < type.length; i++)
					gunInfo.attachwhitelist.add(type[i]);
				break;
			case "allowattach":
				for (int i = 1; i < type.length; i++)
					gunInfo.attachwhitelist.add(type[i]);
				break;
			case "UseUnderGun'smodel":
				gunInfo.useundergunsmodel = parseBoolean(type[1]);
//								System.out.println("useundergunsmodel" + useundergunsmodel);
				break;
			case "UnderGunOffset":
				gunInfo.underoffsetpx = parseFloat(type[1]);
				gunInfo.underoffsetpy = parseFloat(type[2]);
				gunInfo.underoffsetpz = parseFloat(type[3]);
				break;
			case "UnderGunRotation":
				gunInfo.underrotationx = parseFloat(type[1]);
				gunInfo.underrotationy = parseFloat(type[2]);
				gunInfo.underrotationz = parseFloat(type[3]);
				break;
			case "OnUnderGunOffset":
				gunInfo.onunderoffsetpx = parseFloat(type[1]);
				gunInfo.onunderoffsetpy = parseFloat(type[2]);
				gunInfo.onunderoffsetpz = parseFloat(type[3]);
				break;
			case "OnUnderGunRotation":
				gunInfo.onunderrotationx = parseFloat(type[1]);
				gunInfo.onunderrotationy = parseFloat(type[2]);
				gunInfo.onunderrotationz = parseFloat(type[3]);
				break;
			
			case "BulletNameALL":
				gunInfo.bulletmodelN =
						gunInfo.bulletmodelAR =
								gunInfo.bulletmodelAP =
										gunInfo.bulletmodelAT =
												gunInfo.bulletmodelFrag =
														gunInfo.bulletmodelHE =
																gunInfo.bulletmodelTE =
																		gunInfo.bulletmodelCart =
																				gunInfo.bulletmodelGL =
																						gunInfo.bulletmodelRPG = type[1];
				gunInfo.hascustombulletmodel = true;
				break;
			case "BulletNameNormal":
				gunInfo.bulletmodelN = type[1];
				gunInfo.hascustombulletmodel = true;
				break;
			case "BulletNameAR":
				gunInfo.bulletmodelAR = type[1];
				gunInfo.hascustombulletmodel = true;
				break;
			case "BulletNameAP":
				gunInfo.bulletmodelAP = type[1];
				gunInfo.hascustombulletmodel = true;
				break;
			case "BulletNameAT":
				gunInfo.bulletmodelAT = type[1];
				gunInfo.hascustombulletmodel = true;
				break;
			case "BulletNameFrag":
				gunInfo.bulletmodelFrag = type[1];
				gunInfo.hascustombulletmodel = true;
				break;
			case "BulletNameHE":
				gunInfo.bulletmodelHE = type[1];gunInfo.hascustombulletmodel = true;
				break;
			case "BulletNameTE":
				gunInfo.bulletmodelTE = type[1];
				gunInfo.hascustombulletmodel = true;
				break;
			case "BulletNameGL":
				gunInfo.bulletmodelGL = type[1];
				gunInfo.hascustombulletmodel = true;
				break;
			case "BulletNameRPG":
				gunInfo.bulletmodelRPG = type[1];
				gunInfo.hascustombulletmodel = true;
				break;
			case "BulletNameCart":
				gunInfo.bulletmodelCart = type[1];
				gunInfo.hascustomcartridgemodel = true;
				break;
			case "BulletNameMAG":
				gunInfo.bulletmodelMAG = type[1];
				gunInfo.hascustommagemodel = true;
				break;
			case "KnockBack":
				gunInfo.knockback = parseDouble(type[1]);
				gunInfo.knockbackY = parseDouble(type[2]);
				break;
			case "isOneuse":
				gunInfo.isOneuse = parseBoolean(type[1]);
				break;
			
			case "MuzzleFlash":
				gunInfo.muzzleflash = parseBoolean(type[1]);
				break;
			case "MagazineItemCount":
				gunInfo.magazineItemCount = parseInt(type[1]);
				break;
			case "guerrila_cant_use_this":
				gunInfo.guerrila_can_use = false;
				break;
			case "Dont_Be_Inside_Root_Chest":
				gunInfo.canInRoot = false;
				break;
			case "soldier_cant_storage_this":
				gunInfo.soldiercanstorage = false;
				break;
			case "Canlock":
				gunInfo.canlock = gunInfo.canlockEntity = parseBoolean(type[1]);
				break;
			case "canlockEntity":
				gunInfo.canlockEntity = parseBoolean(type[1]);
				break;
			case "seekerSize":
				gunInfo.seekerSize = Double.parseDouble(type[1]);
				break;
			case "seekerSize_bullet":
				gunInfo.seekerSize_bullet = parseFloat(type[1]);
				break;
			case "CanlockBlock":
				gunInfo.canlockBlock = parseBoolean(type[1]);
				break;
			case "lock_to_Vehicle":
				gunInfo.lock_to_Vehicle = parseBoolean(type[1]);
				break;
			case "lookDown":
				gunInfo.lookDown = sin(toRadians(parseFloat(type[1])));
				break;
			case "radarRange":
				gunInfo.radarRange = parseFloat(type[1]) * parseFloat(type[1]);
				break;
			case "guntype":
				gunInfo.guntype = parseInt(type[1]);
				break;
			case "Class":
				gunInfo.canuseclass = parseInt(type[1]);
				break;
			case "CustomFlash":
				gunInfo.flashname = type[1];
				gunInfo.flashfuse = parseInt(type[2]);
				if (type.length == 4) {
					gunInfo.flashScale = parseFloat(type[3]);
				}
				break;
			case "Canfix":
				gunInfo.canfix = parseBoolean(type[1]);
				break;
			case "NeedFix":
				gunInfo.needfix = parseBoolean(type[1]);
				break;
			case "UserOnBarrel":
				gunInfo.userOnBarrel = parseBoolean(type[1]);
				break;
			case "FixAsEntity":
				gunInfo.fixAsEntity = parseBoolean(type[1]);
				break;
			case "damagerange":
				gunInfo.damagerange = parseFloat(type[1]);
				break;
			case "hasVT":
				gunInfo.hasVT = parseBoolean(type[1]);
				break;
			case "VTRange":
				gunInfo.VTRange = Double.parseDouble(type[1]);
				break;
			case "VTWidth":
				gunInfo.VTWidth = Double.parseDouble(type[1]);
				break;
			case "OnEntity_RotationYawPoint":
				gunInfo.posGetter.turretRotationYawPoint = new double[]{parseDouble(type[1]), parseDouble(type[2]), parseDouble(type[3])};
				gunInfo.posGetter.turretYawCenterpos = new Vector3d(gunInfo.posGetter.turretRotationYawPoint);
				break;
			case "OnEntity_RotationPitchPoint":
				gunInfo.posGetter.turretRotationPitchPoint = new double[]{parseDouble(type[1]), parseDouble(type[2]), parseDouble(type[3])};
				gunInfo.posGetter.turretPitchCenterpos = new Vector3d(gunInfo.posGetter.turretRotationPitchPoint);
				break;
			case "OnEntity_BarrelPoint":
				gunInfo.posGetter.barrelpos = new double[]{parseDouble(type[1]), parseDouble(type[2]), parseDouble(type[3])};
				gunInfo.posGetter.cannonPos = new Vector3d(gunInfo.posGetter.barrelpos);
				break;
			case "OnEntity_multi_BarrelPoint":
				gunInfo.posGetter.multi_barrelpos = new double[parseInt(type[1])][3];
				gunInfo.posGetter.multicannonPos = new Vector3d[parseInt(type[1])];
				int id = 0;
				double[][] multi_barrelpos = gunInfo.posGetter.multi_barrelpos;
				for (int i = 0; i < multi_barrelpos.length; i++) {
					double[] aBarrelPos = multi_barrelpos[i];
					aBarrelPos[0] = parseDouble(type[id * 3 + 2]);
					aBarrelPos[1] = parseDouble(type[id * 3 + 3]);
					aBarrelPos[2] = parseDouble(type[id * 3 + 4]);
					gunInfo.posGetter.multicannonPos[i] = new Vector3d(aBarrelPos);
					id++;
				}
				break;
			case "OnEntity_Yoffset":
				gunInfo.yoffset = parseFloat(type[1]);
				break;
			case "restrictTurretMoveSpeed":
				gunInfo.restrictTurretMoveSpeed = true;
				gunInfo.turretspeedP = parseFloat(type[1]);
				gunInfo.turretspeedY = parseFloat(type[2]);
				break;
			case "Turretanglelimit":
				gunInfo.restrictTurretAngle = true;
				gunInfo.turretanglelimtPitchMax = parseFloat(type[1]);
				gunInfo.turretanglelimtPitchmin = parseFloat(type[2]);
				gunInfo.turretanglelimtYawMax = parseFloat(type[3]);
				gunInfo.turretanglelimtYawmin = parseFloat(type[4]);
				break;
			case "TurretBoxSize":
				gunInfo.turreboxW = parseFloat(type[1]);
				gunInfo.turreboxH = parseFloat(type[2]);
				break;
			case "OnTurretScale":
				gunInfo.onTurretScale = parseFloat(type[1]);
				break;
			case "TurretHP":
				gunInfo.turretMaxHP = parseInt(type[1]);
				break;
		}
	}

	private static ScriptEngine doScript(FileReader s)
	{
		ScriptEngine se = (new ScriptEngineManager(null)).getEngineByName("js");//引数にnull入れないと20でぬるぽ

		try
		{
			if(se.toString().contains("Nashorn"))
			{
				//Java8ではimportPackage()が使えないので、その対策
				se.eval("load(\"nashorn:mozilla_compat.js\");");
			}

			//se.put("packreg", REGISTER);

			se.eval(s);
			return se;
		}
		catch(ScriptException e)
		{
			throw new RuntimeException("Script exec error", e);
		}
	}
}
