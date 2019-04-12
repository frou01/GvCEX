package hmggvcmob.entity;

import net.minecraft.block.material.Material;
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
    default TurretObj[] getmotherTurrets(){
        return null;
    }

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
    
    default boolean ishittingWater()
    {
        boolean inWater = false;
        if (((Entity)this).worldObj.handleMaterialAcceleration(((Entity)this).boundingBox.expand(0.0D, -0.4000000059604645D, 0.0D).contract(0.001D, 0.001D, 0.001D).offset(0,0,0), Material.water, ((Entity)this)))
        {
            inWater = true;
        }
        else
        {
            inWater = false;
        }
        
        return inWater;
    }
}
