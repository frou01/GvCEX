package handmadeguns.client.render;

import javax.vecmath.Vector3d;

public class HMGGunParts_Motion_PosAndRotation {
    public float rotationX;
    public float rotationY;
    public float rotationZ;
    public float posX;
    public float posY;
    public float posZ;
    public Vector3d rotateVec;
    public HMGGunParts_Motion_PosAndRotation(float centerX,float centerY,float centerZ,float VectorX,float VectorY,float VectorZ,float rotationAmount){
        this.posX      =centerX;
        this.posY      =centerY;
        this.posZ      =centerZ;
        this.rotateVec = new Vector3d(VectorX,VectorY,VectorZ);
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
}
