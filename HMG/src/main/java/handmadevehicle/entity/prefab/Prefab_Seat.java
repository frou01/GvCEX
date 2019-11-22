package handmadevehicle.entity.prefab;


import handmadevehicle.entity.parts.SeatInfo;
import handmadevehicle.entity.parts.turrets.TurretObj;

public class Prefab_Seat {
	public double[] pos = new double[3];
	public boolean hasGun;
	public boolean aimGun;
	public boolean seatOnTurret = true;
	public int[] mainid;
	public int subid;
	public float userProtect_maxDamageLevel = 0;
	public float zoomLevel = 1.0f;
	public Prefab_Seat(){
	}
	public Prefab_Seat(double[] pos,boolean hasGun,boolean aimGun,boolean seatOnTurret,int mainid,int subid){
		this.pos = pos;
		this.hasGun = hasGun;
		this.aimGun = aimGun;
		this.seatOnTurret = seatOnTurret;
		if(mainid != -1)this.mainid = new int[]{mainid};
		this.subid = subid;
	}
	
	public SeatInfo getSeatOBJ(TurretObj[] turrets){
		TurretObj[] maingun = null;
		if(mainid != null && mainid.length > 0) {
			maingun = new TurretObj[mainid.length];
			int cnt = 0;
			for (int id : mainid) {
				if (id >= 0) maingun[cnt] = turrets[id];
				cnt++;
			}
		}
		return new SeatInfo(pos,this,maingun, subid != -1 ? turrets[subid]:null);
	}
}
