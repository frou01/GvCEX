package handmadevehicle.entity.parts;

import handmadevehicle.entity.parts.turrets.TurretObj;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;

public interface ITank extends IVehicle{

    void subFireToTarget(Entity target);
    void mainFireToTarget(Entity target);
    void mainFire();
    void subFire();
    
    
}
