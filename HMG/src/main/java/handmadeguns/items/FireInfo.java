package handmadeguns.items;

public class FireInfo {
	public int reloadtime;
	public int bulletRound;
	public int powor;
	public double knockback = 0.1;
	public double knockbackY =0.1;
	public float ex = 2.5F;
	public boolean canex;
	public float speed;
	public float gra = 0.029F;
	
	public int shotgun_pellet = 1;
	public int cartType;
	public int magType;
	public int magentityCnt;
	public int cartentityCnt = 1;
	public boolean dropcart = true;
	public boolean cart_cocked = false;
	public boolean dropMagEntity = true;
	
	public boolean hascustombulletmodel = false;
	public boolean hascustomcartridgemodel = false;
	public boolean hascustommagemodel = false;
	public String bulletmodelMAG = "default";
	public String bulletmodelCart = "default";
	public boolean canbounce = false;
	public int fuse = 0;
	public float bouncerate = 0.3f;
	public float bouncelimit = 90;
	public float acceleration;
	public float induction_precision;
	public float resistance = 0.99f;
	public int guntype;
}
