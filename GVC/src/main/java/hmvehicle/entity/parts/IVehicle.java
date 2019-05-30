package hmvehicle.entity.parts;

import javax.vecmath.Quat4d;

public interface IVehicle extends HasLoopSound , HasBaseLogic , Hasmode{
    int getfirecyclesettings1();
    int getfirecycleprogress1();
    int getfirecyclesettings2();
    int getfirecycleprogress2();
    float getturretrotationYaw();
    float getbodyrotationYaw();
    float getthrottle();
    
    void setBodyRot(Quat4d quat4d);
    void setthrottle(float th);
    
    default String getsightTex(){
        return null;
    }
    default float getthirdDist(){
        return 4;
    }
}
