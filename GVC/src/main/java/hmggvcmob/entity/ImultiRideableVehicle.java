package hmggvcmob.entity;

import handmadeguns.entity.SpHitCheckEntity;
import net.minecraft.entity.Entity;

import javax.vecmath.Quat4d;

public interface ImultiRideableVehicle extends IdriveableVehicle,SpHitCheckEntity {
    int getfirecyclesettings1();
    int getfirecycleprogress1();
    int getfirecyclesettings2();
    int getfirecycleprogress2();
    float getturretrotationYaw();
    float getbodyrotationYaw();
    float getthrottle();
    void setBodyRot(Quat4d quat4d);
    void setthrottle(float th);
    void setTrigger(boolean trig1, boolean trig2);
    void initseat();

    GVCEntityChild[] getChilds();

    void addChild(GVCEntityChild seat);
    boolean isRidingEntity(Entity entity);
    boolean isChild(Entity entity);

    int getpilotseatid();
}
