package handmadeguns.client.render;

public class HMGGunParts_Motion {
    public int startflame;
    public int endflame;
    public float startrotationX;
    public float startrotationY;
    public float startrotationZ;
    public float startposX;
    public float startposY;
    public float startposZ;
    public int size_flame;
    public float size_rotationX;
    public float size_rotationY;
    public float size_rotationZ;
    public float size_posX;
    public float size_posY;
    public float size_posZ;
    public float endrotationX;
    public float endrotationY;
    public float endrotationZ;
    public float endposX;
    public float endposY;
    public float endposZ;
    public boolean isrendering = true;
    public void setup(){
        size_rotationX = endrotationX -startrotationX;
        size_rotationY = endrotationY -startrotationY;
        size_rotationZ = endrotationZ -startrotationZ;
        size_posX = endposX -startposX;
        size_posY = endposY -startposY;
        size_posZ = endposZ -startposZ;
        size_flame = endflame - startflame;
    }
    public HMGGunParts_Motion_PosAndRotation posAndRotation(float flame){
        float flameforCompletion = flame-startflame;
        HMGGunParts_Motion_PosAndRotation posAndRotation = new HMGGunParts_Motion_PosAndRotation();
        posAndRotation.rotationX = startrotationX + size_rotationX * (flameforCompletion/size_flame);
        posAndRotation.rotationY = startrotationY + size_rotationY * (flameforCompletion/size_flame);
        posAndRotation.rotationZ = startrotationZ + size_rotationZ * (flameforCompletion/size_flame);
        posAndRotation.posX = startposX + size_posX * (flameforCompletion/size_flame);
        posAndRotation.posY = startposY + size_posY * (flameforCompletion/size_flame);
        posAndRotation.posZ = startposZ + size_posZ * (flameforCompletion/size_flame);
        return posAndRotation;
    }
}
