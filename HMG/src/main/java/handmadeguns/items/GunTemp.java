package handmadeguns.items;

import handmadeguns.HandmadeGunsCore;
import handmadevehicle.entity.parts.turrets.TurretObj;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

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
	public int selector;

	public TurretObj currentConnectedTurret;
	public boolean connectedTurret;

	public void readPropertyFromNBT(GunInfo gunInfo, NBTTagCompound nbt, boolean isADS, World world){
		if (this.connectedTurret = nbt.getBoolean("IsTurretStack") && this.currentConnectedTurret == null) {
			return;
		}
		this.tempspread = gunInfo.spread_setting;
		if (isADS) {
			this.tempspread = this.tempspread * gunInfo.ads_spread_cof;
		}
		this.tempspreadDiffusion = nbt.getFloat("Diffusion");
		if (this.tempspreadDiffusion > gunInfo.spreadDiffusionMax)
			this.tempspreadDiffusion = gunInfo.spreadDiffusionMax;
		this.tempspreadDiffusion -= gunInfo.spreadDiffusionReduceRate;
		if (this.tempspreadDiffusion < gunInfo.spreadDiffusionmin)
			this.tempspreadDiffusion = gunInfo.spreadDiffusionmin;
		this.tempspread += gunInfo.spread_setting * this.tempspreadDiffusion;


		this.sound = gunInfo.soundbase;
		this.soundlevel = gunInfo.soundbaselevel;
		this.muzzle = gunInfo.muzzleflash;
		this.selectingMagazine = nbt.getInteger("get_selectingMagazine");
		this.currentMgazine = nbt.getInteger("getcurrentMagazine");


		this.islockingentity = nbt.getBoolean("islockedentity");
		this.TGT = world.getEntityByID(nbt.getInteger("TGT"));
		this.islockingblock = nbt.getBoolean("islockedblock");
		this.LockedPosX = nbt.getInteger("LockedPosX");
		this.LockedPosY = nbt.getInteger("LockedPosY");
		this.LockedPosZ = nbt.getInteger("LockedPosZ");
		this.currentElevation = nbt.getInteger("currentElevation");
		this.selector = nbt.getInteger("HMGMode");
	}
}
