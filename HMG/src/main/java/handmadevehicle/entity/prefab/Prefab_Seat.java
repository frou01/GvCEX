package handmadevehicle.entity.prefab;


import handmadevehicle.entity.parts.SeatInfo;
import handmadevehicle.entity.parts.turrets.TurretObj;

public class Prefab_Seat {
	public double[] pos = new double[3];
	public boolean hasGun;
	public boolean aimGun;
	public boolean seatOnTurret = true;
	public int mainid;
	public int subid;
	public Prefab_Seat(){
	}
	public Prefab_Seat(double[] pos,boolean hasGun,boolean aimGun,int mainid,int subid){
		this.pos = pos;
		this.hasGun = hasGun;
		this.aimGun = aimGun;
		this.mainid = mainid;
		this.subid = subid;
	}
	
	public SeatInfo getSeatOBJ(TurretObj[] turrets){
		return new SeatInfo(pos,this,hasGun,aimGun, mainid != -1 ? turrets[mainid]:null, subid != -1 ? turrets[subid]:null);
	}
}
