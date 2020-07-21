package handmadeguns.client.render;

import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

public class HMGGunParts_Motion_PosAndRotation {
    public float rotationX;
    public float rotationY;
    public float rotationZ;
    public float posX;
    public float posY;
    public float posZ;
    public boolean renderOnOff = true;
    public Vector3f rotateVec;
    public HMGGunParts_Motion_PosAndRotation(){

    }
    public HMGGunParts_Motion_PosAndRotation(float centerX,float centerY,float centerZ,float VectorX,float VectorY,float VectorZ,float rotationAmount){
        this.posX      =centerX;
        this.posY      =centerY;
        this.posZ      =centerZ;
        this.rotateVec = new Vector3f(VectorX,VectorY,VectorZ);
        this.rotationX = rotationAmount;
    }
    public HMGGunParts_Motion_PosAndRotation(float offsetX,float offsetY,float offsetZ,float rotationX,float rotationY,float rotationZ){
        this.posX      =offsetX;
        this.posY      =offsetY;
        this.posZ      =offsetZ;
        this.rotationX =rotationX;
        this.rotationY =rotationY;
        this.rotationZ =rotationZ;
    }
    public void set(float offsetX,float offsetY,float offsetZ,float rotationX,float rotationY,float rotationZ){
        this.posX      =offsetX;
        this.posY      =offsetY;
        this.posZ      =offsetZ;
        this.rotationX =rotationX;
        this.rotationY =rotationY;
        this.rotationZ =rotationZ;
    }

    public String toString()
    {
        return "Position : X " + posX + " , Y " + posY + " , Z " + posZ + " , R " + rotationZ + " , P " + rotationX + " , Y " + rotationY;
    }
}
