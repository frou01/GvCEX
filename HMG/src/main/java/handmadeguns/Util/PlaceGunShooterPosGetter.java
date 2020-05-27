package handmadeguns.Util;

import javax.vecmath.Vector3d;

public class PlaceGunShooterPosGetter {
    public double curretnSightPos[] = new double[]{0,0,0};
    public double barrelpos[] = new double[]{0,0.25,0.5};
    public double multi_barrelpos[][];
    public Vector3d cannonPos = new Vector3d();
    public Vector3d[] multicannonPos = null;

    public double turretRotationYawPoint[] = new double[]{0,0,0};
    public Vector3d turretYawCenterpos = new Vector3d();
    public double turretRotationPitchPoint[] = new double[]{0,0,0};
    public Vector3d turretPitchCenterpos = new Vector3d();
}
