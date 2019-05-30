package hmvehicle.entity.parts;

import hmvehicle.entity.parts.turrets.TurretObj;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;

import javax.vecmath.Quat4d;

public interface ITank extends IVehicle,HasBaseLogic{
    void setrotationYawmotion(float value);
    void setthrottle(float value);

    void subFireToTarget(Entity target);
    void mainFireToTarget(Entity target);
    void mainFire();
    void subFire();

    TurretObj getMainTurret();
    TurretObj[] getTurrets();
    default TurretObj[] getmotherTurrets(){
        return null;
    }



    void setTurretrotationYaw(float floats);
    float getTurretrotationYaw();
    void setTurretrotationPitch(float floats);
    float getTurretrotationPitch();
    void setRotationYaw(float floats);
    float getRotationYaw();
    void setRotationPitch(float floats);
    float getRotationPitch();
    void setRotationRoll(float floats);
    float getRotationRoll();
    
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
