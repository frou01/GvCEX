package handmadevehicle.entity.parts;

import handmadevehicle.entity.parts.turrets.TurretObj;
import handmadevehicle.entity.prefab.Prefab_Seat;

import javax.vecmath.Vector3d;

public class SeatInfo {
    public SeatInfo(){
    
    }
    public SeatInfo(double[] pos,Prefab_Seat prefab_seat,TurretObj[] maingun,TurretObj subgun){
        this(pos);
        this.prefab_seat = prefab_seat;
        this.maingun = maingun;
        this.subgun = subgun;
    }
    public SeatInfo(double[] pos){
        this.pos = pos;
    }
    public SeatInfo(Vector3d pos){
        this.pos = new double[]{pos.x,pos.y,-pos.z};
    }
    public double[] pos = new double[]{0,1.5,0};
    public Prefab_Seat prefab_seat;
    public boolean gunTrigger1;
    public boolean gunTrigger2;
    public int gunTrigger1Freeze = -1;
    public int gunTrigger2Freeze = -1;
    public boolean seekerKey;

    public boolean syncToPlayerAngle = true;

    public TurretObj[] maingun;
    public int currentWeaponMode = 0;
    public TurretObj subgun;
    public Vector3d currentSeatOffset_fromV = new Vector3d();
    public Vector3d prevSeatOffset_fromV = new Vector3d();

}
