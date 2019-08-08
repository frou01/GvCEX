package handmadevehicle.entity.parts;

import handmadevehicle.entity.parts.turrets.TurretObj;

import javax.vecmath.Vector3d;

public class SeatInfo {
    public SeatInfo(){
    
    }
    public SeatInfo(double[] pos,boolean hasGun,boolean aimGun,TurretObj maingun,TurretObj subgun){
        this(pos);
        this.hasGun = hasGun;
        this.aimGun = aimGun;
        this.maingun = maingun;
        this.subgun = subgun;
    }
    public SeatInfo(double[] pos){
        this.pos = pos;
    }
    public SeatInfo(Vector3d pos){
        this.pos = new double[]{pos.x,pos.y,pos.z};
    }
    public double[] pos = new double[]{0,1.5,0};
    public boolean hasGun;
    public boolean aimGun;
    public boolean hasParentGun = false;
    public boolean gunTrigger1;
    public boolean gunTrigger2;
    public TurretObj maingun;
    public TurretObj subgun;
    public Vector3d currentSeatOffset_fromV = new Vector3d();

}
