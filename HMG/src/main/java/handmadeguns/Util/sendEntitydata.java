package handmadeguns.Util;

import handmadeguns.entity.bullets.HMGEntityBulletBase;

import java.io.Serializable;

public class sendEntitydata implements Serializable{
    public double motionX;
    public double motionY;
    public double motionZ;
    public boolean inGround;
    public double posX;
    public double posY;
    public double posZ;
    public float yaw;
    public float pitch;
    public sendEntitydata(HMGEntityBulletBase entityin){
        motionX = entityin.motionX;
        motionY = entityin.motionY;
        motionZ = entityin.motionZ;
        inGround = entityin.inGround;
        posX = entityin.posX;
        posY = entityin.posY;
        posZ = entityin.posZ;
        yaw = entityin.rotationYaw;
        pitch = entityin.rotationPitch;
    }
}
