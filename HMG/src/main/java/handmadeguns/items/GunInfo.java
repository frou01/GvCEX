package handmadeguns.items;

import handmadeguns.Util.PlaceGunShooterPosGetter;
import handmadeguns.entity.PlacedGunEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.vecmath.Vector3d;
import java.util.ArrayList;
import java.util.UUID;

import static handmadeguns.HandmadeGunsCore.cfg_defaultknockback;
import static handmadeguns.HandmadeGunsCore.cfg_defaultknockbacky;

public class GunInfo {
	
	public final UUID field_110179_h = UUID.fromString("254F543F-8B6F-407F-931B-4B76FEB8BA0D");
	public int power;
	public int bulletRound = 30;
	public float speed;
	public boolean hasspreadDiffusionSettings = false;
	public float spreadDiffusionMax = 0;
	public float spreadDiffusionmin = 0;
	public float spreadDiffusionRate = 0;
	public float spreadDiffusionHeadRate = 0;
	public float spreadDiffusionWalkRate = 0;
	public float spreadDiffusionReduceRate = 0;
	public float spread_setting = 1;
	public float ads_spread_cof = 0.5f;
	public double recoil;
	public double recoil_sneak;
	public float onTurretScale = 1.0f;
	public boolean restrictTurretMoveSpeed;
	public float turretspeedY = -1;
	public float turretspeedP = -1;
	public float turreboxW = 0.8f;
	public float turreboxH = 0.8f;
	public int turretMaxHP = -1;
	public boolean restrictTurretAngle = false;
	public int canuseclass = -1;
	public int guntype = -1;
	public float cycle = 1;
	public float ex = 2.5F;
	public boolean destroyBlock = true;
	public String[] soundre= {"handmadeguns:handmadeguns.reload"};
	public float soundrelevel = 1.0f;
	public boolean canobj = true;
	public boolean renderMCcross = true;
	public boolean renderHMGcross = true;
	public String soundco = "handmadeguns:handmadeguns.cooking";
	public float scopezoombase = 1;
	public float scopezoomred = 1;
	public float scopezoomscope = 4;
	public float soundspeed = 1.0F;
	public String soundbase= "handmadeguns:handmadeguns.fire";
	public float soundbaselevel = 4.0f;
	public String soundsu= "handmadeguns:handmadeguns.supu";
	public float soundsuplevel = 1.0f;
	public String lockSound_entity = "handmadeguns:handmadeguns.lockon";
	public boolean lockSound_NoStop = false;
	public String lockSound_block = "handmadeguns:handmadeguns.lockon";
	public float lockpitch_entity = 1;
	public float lockpitch_block = 0.5f;
	public Item[] magazine = new Item[1];
	public int[] reloadTimes = new int[1];
	public int magazineItemCount = 1;
	public String adstexture = "handmadeguns:textures/misc/ironsight";
	public String adstexturer = "handmadeguns:textures.misc.reddot";
	public String adstextures = "handmadeguns:textures.misc.scope";
	public ResourceLocation lockOnMarker = new ResourceLocation("handmadeguns:textures/items/lockonmarker0.png");
	public ResourceLocation predictMarker = new ResourceLocation("handmadeguns:textures/items/predictMarker.png");
	public boolean zoomren = true;
	public boolean zoomrer = true;
	public boolean zoomres = true;
	public boolean zoomrent = false;
	public boolean zoomrert = false;
	public boolean zoomrest = false;
	public String texture;
	public double motion = 1D;
	public double weight = 1D;
	public boolean muzzleflash = true;
	public float soundrespeed = 1.0F;
	public int cocktime = 20;
	public boolean needcock = false;
	public boolean needFirstCock = false;
	public int pellet = 1;
	//01/27
	//02/14
	public int cartType = 1;
	public int magType = 5;
	public int magentityCnt = 1;
	public int cartentityCnt = 1;
	public boolean dropcart = true;
	public boolean cart_cocked = false;
	public boolean dropMagEntity = true;
	//0307
	public String soundunder_gl= "handmadeguns:handmadeguns.cooking";
	public String soundunder_sg= "handmadeguns:handmadeguns.cooking";
	public int under_gl_power = 20;
	public boolean under_gl_canbounce = true;
	public int under_gl_fuse = -1;
	public float under_gl_speed = 2;
	public float under_gl_bure = 5;
	public double under_gl_recoil = 5;
	public float under_gl_gra = 0.01F;
	public int under_sg_power = 4;
	public float under_sg_speed = 3;
	public float under_sg_bure = 20;
	public double under_sg_recoil = 5;
	public float under_sg_gra = 0.029F;
	public float attackDamage = 1;
	public float foruseattackDamage = 1;
	public boolean hasAttachRestriction = false;
	public ArrayList<String> attachwhitelist = new ArrayList<String>();
	public boolean useundergunsmodel = false;
	public float underoffsetpx;
	public float underoffsetpy;
	public float underoffsetpz;
	public float underrotationx;
	public float underrotationy;
	public float underrotationz;
	public float onunderoffsetpx;
	public float onunderoffsetpy;
	public float onunderoffsetpz;
	public float onunderrotationx;
	public float onunderrotationy;
	public float onunderrotationz;
	public float modelscale = 1;
	public float inworldScale = 1;
	public boolean hascustombulletmodel = false;
	public boolean hascustomcartridgemodel = false;
	public boolean hascustommagemodel = false;
	public String bulletmodelN = "default";
	public String bulletmodelAR = "default";
	public String bulletmodelAP = "default";
	public String bulletmodelAT = "default";
	public String bulletmodelFrag = "default";
	public String bulletmodelHE = "default";
	public String bulletmodelTE = "default";
	public String bulletmodelGL = "default";
	public String bulletmodelRPG = "byfrou01_Rocket";
	public String bulletmodelMAG = null;
	public String bulletmodelCart = "default";
	public int fuse = 0;
	public double knockback = cfg_defaultknockback;
	public double knockbackY =cfg_defaultknockbacky;
	public float  bouncerate = 0.3f;
	public float  bouncelimit = 90;
	public float  resistance = 0.99f;
	public float  acceleration;
	public float gravity = 0.029F;
	public boolean canbounce = false;
	public ArrayList<Integer> burstcount = new ArrayList<Integer>(){
//		{
//			add(-1);
//		}
	};
	public ArrayList<Float> rates = new ArrayList<Float>(){
//		{
//			add(1);
//		}
	};

	public ArrayList<Float> elevationOffsets = new ArrayList<>();
	public ArrayList<String> elevationOffsets_info = null;
	public boolean userenderscript = false;
	public ScriptEngine renderscript;
	public ScriptEngine script;
	public ScriptEngine script_withGUI;
	public float mat31rotex;
	public float mat31rotey;
	public float mat31rotez;
	public boolean isOneuse = false;
	public boolean guerrila_can_use = true;
	public boolean canInRoot = true;
	public boolean soldiercanstorage = true;
	public boolean use_internal_secondary;
	public boolean canlock = false;
	public boolean canlockBlock = false;
	public boolean canlockEntity = false;
	public boolean displayPredict = false;
	public boolean displayPredict_MoveSight = true;
	public double seekerSize = 60;
	public float seekerSize_bullet = 50;
	public boolean semiActive = false;
	public boolean lock_to_Vehicle = false;
	public double lookDown = 1;
	public double radarRange = 1200 * 1200;
	public float induction_precision;
	public float lockOn_MaxSpeed = -1;
	public float lockOn_minSpeed = -1;
	public float lockOn_MaxThrottle = -1;
	public float lockOn_minThrottle = -1;
	public String flashname = null;
	public int flashfuse = 1;
	public float flashScale = 1;
	public boolean canfix;
	public boolean needfix;
	public boolean userOnBarrel = true;
	public boolean fixAsEntity;
	public float[] sightattachoffset = new float[3];
	public PlaceGunShooterPosGetter posGetter = new PlaceGunShooterPosGetter();;
	public float yoffset;
	public double[] sightPosN = new double[3];
	public double[] sightPosR = new double[3];
	public double[] sightPosS = new double[3];
	public Vector3d[] sightOffset_zeroIn = new Vector3d[]{new Vector3d()};
	public boolean canceler;
	public boolean chargeType;
	public boolean[] hasNightVision = new boolean[]{false,false,false};
	public float turretanglelimtPitchMax = 360;
	public float turretanglelimtPitchmin = -360;
	public float turretanglelimtYawMax = 360;
	public float turretanglelimtYawmin = -360;
	public double torpdraft;
	public float damagerange;

	public boolean hasVT   = false;
	public double  VTRange = 10;
	public double  VTWidth = 30;

	public float resistanceinWater;
	
	public static Vec3 getLook(float p_70676_1_, Entity entity)
	{
	    float f1;
	    float f2;
	    float f3;
	    float f4;
	
	
	
	    f1 = MathHelper.cos(-(entity instanceof EntityLivingBase ? ((EntityLivingBase)entity).rotationYawHead : (entity instanceof PlacedGunEntity ?((PlacedGunEntity) entity).rotationYawGun:entity.rotationYaw)) * 0.017453292F - (float)Math.PI);
	    f2 = MathHelper.sin(-(entity instanceof EntityLivingBase ? ((EntityLivingBase)entity).rotationYawHead : (entity instanceof PlacedGunEntity ?((PlacedGunEntity) entity).rotationYawGun:entity.rotationYaw)) * 0.017453292F - (float)Math.PI);
	    f3 = -MathHelper.cos(-entity.rotationPitch * 0.017453292F);
	    f4 = MathHelper.sin(-entity.rotationPitch * 0.017453292F);
	    return Vec3.createVectorHelper((double)(f2 * f3), (double)f4, (double)(f1 * f3));
	}
}
