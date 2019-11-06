package handmadeguns.client.render;

import static handmadeguns.HMGGunMaker.*;
import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;

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

    public HMGGunParts_Motion(){

    }
    public HMGGunParts_Motion(String[] type){
        if(type.length>6) {
            set(parseInt(type[readerCnt++]), Float.parseFloat(type[readerCnt++]), Float.parseFloat(type[readerCnt++]), Float.parseFloat(type[readerCnt++]), Float.parseFloat(type[readerCnt++]), Float.parseFloat(type[readerCnt++]), Float.parseFloat(type[readerCnt++]), parseInt(type[readerCnt++]), Float.parseFloat(type[readerCnt++]), Float.parseFloat(type[readerCnt++]), Float.parseFloat(type[readerCnt++]), Float.parseFloat(type[readerCnt++]), Float.parseFloat(type[readerCnt++]), Float.parseFloat(type[readerCnt++]));
        }else {
            startflame = parseInt(type[readerCnt++]);
            isrendering = parseBoolean(type[readerCnt++]);
            endflame = parseInt(type[readerCnt++]);
            setup();
        }
    }

    public void set(int   startflame,
                              float startoffsetX,
                              float startoffsetY,
                              float startoffsetZ,
                              float startrotationX,
                              float startrotationY,
                              float startrotationZ,
                              int   endflame,
                              float endoffsetX,
                              float endoffsetY,
                              float endoffsetZ,
                              float endrotationX,
                              float endrotationY,
                              float endrotationZ){
        this.startflame =         startflame;
        this.startposX =          startoffsetX;
        this.startposY =          startoffsetY;
        this.startposZ =          startoffsetZ;
        this.startrotationX =     startrotationX;
        this.startrotationY =     startrotationY;
        this.startrotationZ =     startrotationZ;
        this.endflame =         endflame;
        this.endposX =          endoffsetX;
        this.endposY =          endoffsetY;
        this.endposZ =          endoffsetZ;
        this.endrotationX =     endrotationX;
        this.endrotationY =     endrotationY;
        this.endrotationZ =     endrotationZ;
        this.setup();
    }
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
        HMGGunParts_Motion_PosAndRotation posAndRotation = new HMGGunParts_Motion_PosAndRotation(startposX + size_posX * (flameforCompletion/size_flame),
                                                                                                        startposY + size_posY * (flameforCompletion/size_flame),
                                                                                                        startposZ + size_posZ * (flameforCompletion/size_flame),
                                                                                                        startrotationX + size_rotationX * (flameforCompletion/size_flame),
                                                                                                        startrotationY + size_rotationY * (flameforCompletion/size_flame),
                                                                                                        startrotationZ + size_rotationZ * (flameforCompletion/size_flame));
        posAndRotation.renderOnOff = isrendering;
        return posAndRotation;
    }
}
