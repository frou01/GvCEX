package hmggvcmob.entity;

import net.minecraft.entity.Entity;

import javax.vecmath.Quat4d;

public interface IMultiTurretVehicle extends ITank{
    TurretObj[] getmainTurrets();
    TurretObj[] getsubTurrets();
    default void Excontrol1(boolean keystate){
    
    }
    default void Excontrol2(boolean keystate){
    
    }
}
