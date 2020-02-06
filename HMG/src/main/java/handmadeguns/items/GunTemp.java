package handmadeguns.items;

import handmadevehicle.entity.parts.turrets.TurretObj;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

import javax.script.Invocable;

public class GunTemp {
	//情報一時保存用
	public float tempspread;
	public float tempspreadDiffusion;
	public String sound= "none";
	
	public float soundlevel = 4;
	
	public ItemStack[] items = new ItemStack[6];
	public ItemStack magazineStack;
	public boolean muzzle = true;
	public Invocable invocable;
	public Entity TGT;
	public int LockedPosX;
	public int LockedPosY;
	public int LockedPosZ;
	public boolean islockingentity;
	public boolean islockingblock;
	
	public int selectingMagazine;
	public int currentMgazine;
	public int currentElevation;

	public TurretObj currentConnectedTurret;
	public boolean connectedTurret;
}
