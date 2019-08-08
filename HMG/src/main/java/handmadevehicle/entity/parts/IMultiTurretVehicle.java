package handmadevehicle.entity.parts;

import handmadevehicle.entity.parts.turrets.TurretObj;

public interface IMultiTurretVehicle{
    TurretObj[] getmainTurrets();
    TurretObj[] getsubTurrets();
    TurretObj[] getTurrets();
    default void Excontrol1(boolean keystate){
    
    }
    default void Excontrol2(boolean keystate){
    
    }
}
