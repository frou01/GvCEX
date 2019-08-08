package handmadeguns.items;

public class FireTemp {
	public int power;
	public int fuse;
	public float speed;
	public String model = null;
	public float exlevel;
	public boolean destroyBlock;
	
	public double knockback;
	public double knockbackY;
	public float  bouncerate;
	public float  bouncelimit;
	public float  resistance;
	public float  acceleration;
	public float  gra;
	public FireTemp(){}
	public FireTemp(GunInfo gunInfo){
		this.power = gunInfo.power;
		this.fuse = gunInfo.fuse;
		this.speed = gunInfo.speed;
		this.exlevel = gunInfo.ex;
		this.destroyBlock = gunInfo.destroyBlock;
		
		this.knockback = gunInfo.knockback;
		this.knockbackY = gunInfo.knockbackY;
		this.bouncerate = gunInfo.bouncerate;
		this.bouncelimit = gunInfo.bouncelimit;
		this.resistance = gunInfo.resistance;
		this.acceleration = gunInfo.acceleration;
		this.gra = gunInfo.gravity;
	}
	public void applyMagOption(HMGItemCustomMagazine magazine){
		this.power *= magazine.damagemodify;
		this.speed *= magazine.speedmodify;
		if(magazine.fuse != -1)this.fuse = magazine.fuse;
		if(magazine.explosionlevel != -1)this.exlevel = magazine.explosionlevel;
		this.destroyBlock &= magazine.blockdestroyex;
		if(magazine.bulletmodel != null)this.model = magazine.bulletmodel;
		
		if(!Double.isNaN(magazine.knockback))this.knockback = magazine.knockback;
		if(!Double.isNaN(magazine.knockbackY))this.knockbackY = magazine.knockbackY;
		if(!Float.isNaN(magazine.bouncerate))this.bouncerate = magazine.bouncerate;
		if(!Float.isNaN(magazine.bouncelimit))this.bouncelimit = magazine.bouncelimit;
		if(!Float.isNaN(magazine.resistance))this.resistance = magazine.resistance;
		if(!Float.isNaN(magazine.acceleration))this.acceleration = magazine.acceleration;
		if(!Float.isNaN(magazine.gra))this.gra = magazine.gra;
	}
}
