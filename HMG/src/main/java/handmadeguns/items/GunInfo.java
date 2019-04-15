package handmadeguns.items;

import handmadeguns.Util.PlaceGunShooterPosGetter;
import net.minecraft.item.Item;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import java.util.ArrayList;

public class GunInfo {
	
	
	public float spread_setting;
	public float ads_spread_cof = 0.5f;
	
	
	public double recoil;
	public double recoil_sneak;
	public float scopezoom;
	public int cycle;
	public boolean needcock = false;
	public boolean chargeType;
	
	public ArrayList<Integer> burstcount = new ArrayList<Integer>();
	public ArrayList<Integer> rates = new ArrayList<Integer>();
	
	
	public String soundre= "handmadeguns:handmadeguns.reload";
	public float soundrelevel = 1.0f;
	public float soundrespeed = 1.0F;
	public int cocktime = 20;
	public boolean canobj;
	
	public String soundco = "handmadeguns:handmadeguns.cooking";
	
	
	public float soundspeed = 1.0F;
	public String soundbase= "handmadeguns:handmadeguns.fire";
	public float soundbaselevel = 4.0f;
	public String soundsu= "handmadeguns:handmadeguns.supu";
	public float soundsuplevel = 1.0f;
	
	public String lockSound_entity = "handmadeguns:handmadeguns.lockon";
	public String lockSound_block = "handmadeguns:handmadeguns.lockon";
	public float lockpitch_entity = 1;
	public float lockpitch_block = 0.5f;
	
	
	public Item[] magazines;
	public int[] magazineItemCounts;
	
	public double WalkSpeed = 1D;
	
	public boolean muzzleflash = true;
	
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
	
	
	public boolean userenderscript = false;
	public ScriptEngine renderscript;
	public ScriptEngine script;
	Invocable invocable;
	
	public boolean isOneuse = false;
	public boolean guerrila_can_use = true;
	public boolean isinRoot = true;
	public boolean soldiercanstorage = true;
	public boolean canlock = false;
	public boolean canlockBlock = false;
	public boolean canlockEntity = false;
	public String flashname = null;
	public int flashfuse = 1;
	public float flashScale = 1;
	public boolean canfix;
	public boolean needfix;
	public boolean fixAsEntity;
	
	public PlaceGunShooterPosGetter posGetter;
	public float yoffset;
	
	
	public float onTurretScale = 1.0f;
	public boolean restrictTurretMoveSpeed;
	public float turretMoveSpeedP;
	public float turretMoveSpeedY;
	public float turreboxW;
	public float turreboxH;
	public int turretMaxHP;
	public boolean restrictTurretAngle = false;
	public float turretanglelimtMxP = 0;
	public float turretanglelimtMxY = 0;
	public float turretanglelimtmnP = 0;
	public float turretanglelimtmnY = 0;
}
