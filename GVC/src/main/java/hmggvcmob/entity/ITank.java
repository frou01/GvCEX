package hmggvcmob.entity;

import net.minecraft.entity.Entity;

import javax.vecmath.Quat4d;

public interface ITank extends IdriveableVehicle{
    void setbodyrotationYaw(float value);
    void setturretrotationYaw(float value);
    float getrotationYawmotion();
    void setrotationYawmotion(float value);
    void setBodyrot(Quat4d rot);
    void setthrottle(float value);

    void subFire(Entity target);
    void mainFire(Entity target);

    TurretObj getMainTurret();
    TurretObj[] getTurrets();

    TankBaseLogic getBaseLogic();


    public void setTurretrotationYaw(float floats);
    public float getTurretrotationYaw();
    public void setTurretrotationPitch(float floats);
    public float getTurretrotationPitch();
    public void setRotationYaw(float floats);
    public float getRotationYaw();
    public void setRotationPitch(float floats);
    public float getRotationPitch();
    public void setRotationRoll(float floats);
    public float getRotationRoll();
}
