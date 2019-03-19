package handmadeguns.client.render;

import java.util.ArrayList;

public class HMGParts {
    public String partsname;
    public boolean isLarm;
    public boolean isRarm;
    public ArrayList<HMGParts> childs = new ArrayList<HMGParts>();
    public boolean isfounder;
    public HMGParts mother;
    public int motherIndex;

    private HMGGunParts_Motion_PosAndRotation defaultPosAndRotation;
    private HMGGunParts_Motion_PosAndRotation dummy = new HMGGunParts_Motion_PosAndRotation();

    public boolean rendering_Def;

    private HMGGunParts_Motion_PosAndRotation spPosAndRotation;

    private HMGGunParts_Motion_PosAndRotation forwardPosAndRotation;

    private HMGGunParts_Motion_PosAndRotation rightPosAndRotation;

    private HMGGunParts_Motion_PosAndRotation leftPosAndRotation;

    private HMGGunParts_Motion_PosAndRotation backPosAndRotation;
    private HMGGunParts_Motion_PosAndRotation sneakPosAndRotation;
    private HMGGunParts_Motion_PosAndRotation jumpingPosAndRotation;

    private HMGGunParts_Motions onforwardmotions;
    private HMGGunParts_Motions onrightmotions;
    private HMGGunParts_Motions onleftmotions;
    private HMGGunParts_Motions onbackmotions;
    private HMGGunParts_Motions onjumpedmotions;
    private HMGGunParts_Motions onsneakingmotions;
    public HMGParts(){
        onforwardmotions = new HMGGunParts_Motions();
        onrightmotions = new HMGGunParts_Motions();
        onleftmotions = new HMGGunParts_Motions();
        onbackmotions = new HMGGunParts_Motions();
        onjumpedmotions = new HMGGunParts_Motions();
        onsneakingmotions = new HMGGunParts_Motions();
    }
    public HMGParts(String partsname) {
        this();
        this.partsname = partsname;
    }
    HMGGunParts_Motion_PosAndRotation getRenderinfOfDummy(){
        return dummy;
    }
    public HMGGunParts_Motion_PosAndRotation getRenderinfOfDef(){
        return defaultPosAndRotation;
    }
    public HMGGunParts_Motion_PosAndRotation getRenderinfOfADS(){
        return spPosAndRotation;
    }
    HMGGunParts_Motion_PosAndRotation getRenderinfOfRecoil(){
        return forwardPosAndRotation;
    }
    HMGGunParts_Motion_PosAndRotation getRenderinfOfCock(){
        return rightPosAndRotation;
    }
    HMGGunParts_Motion_PosAndRotation getRenderinfOfReload(){
        return leftPosAndRotation;
    }
    HMGGunParts_Motion_PosAndRotation getRenderinfOfBullet(){
        return backPosAndRotation;
    }
    public HMGGunParts_Motion_PosAndRotation getRenderinfOfSneak(){
        return sneakPosAndRotation;
    }
    public HMGGunParts_Motion_PosAndRotation getRenderinfOfJumping(){
        return jumpingPosAndRotation;
    }
    public HMGGunParts_Motion_PosAndRotation getforwardmotion(float flame){
        return onforwardmotions.getpartsMotion(flame);
    }
    public HMGGunParts_Motion_PosAndRotation getrightmotion(float flame){
        return onrightmotions.getpartsMotion(flame);
    }
    public HMGGunParts_Motion_PosAndRotation getleftmotion(float flame){
        return onleftmotions.getpartsMotion(flame);
    }
    public HMGGunParts_Motion_PosAndRotation getbackmotion(float flame){
        return onbackmotions.getpartsMotion(flame);
    }
    public HMGGunParts_Motion_PosAndRotation getjumpedmotion(float flame){
        return onjumpedmotions.getpartsMotion(flame);
    }
    public HMGGunParts_Motion_PosAndRotation getsneakingmotion(float flame){
        return onsneakingmotions.getpartsMotion(flame);
    }
    public void AddRenderinfDef(float centerX,float centerY,float centerZ,float rotationX,float rotationY,float rotationZ){
        defaultPosAndRotation = new HMGGunParts_Motion_PosAndRotation();
        defaultPosAndRotation.posX      =centerX;
        defaultPosAndRotation.posY      =centerY;
        defaultPosAndRotation.posZ      =centerZ;
        defaultPosAndRotation.rotationX =rotationX;
        defaultPosAndRotation.rotationY =rotationY;
        defaultPosAndRotation.rotationZ =rotationZ;
    }
    public void AddRenderinfSP(float centerX, float centerY, float centerZ, float rotationX, float rotationY, float rotationZ){
        spPosAndRotation = new HMGGunParts_Motion_PosAndRotation();
        spPosAndRotation.posX      =centerX;
        spPosAndRotation.posY      =centerY;
        spPosAndRotation.posZ      =centerZ;
        spPosAndRotation.rotationX =rotationX;
        spPosAndRotation.rotationY =rotationY;
        spPosAndRotation.rotationZ =rotationZ;
    }
    public void AddRenderinfright(float centerX, float centerY, float centerZ, float rotationX, float rotationY, float rotationZ){
        rightPosAndRotation = new HMGGunParts_Motion_PosAndRotation();
        rightPosAndRotation.posX      =centerX;
        rightPosAndRotation.posY      =centerY;
        rightPosAndRotation.posZ      =centerZ;
        rightPosAndRotation.rotationX =rotationX;
        rightPosAndRotation.rotationY =rotationY;
        rightPosAndRotation.rotationZ =rotationZ;
    }
    public void AddRenderinfleft(float centerX, float centerY, float centerZ, float rotationX, float rotationY, float rotationZ){
        leftPosAndRotation = new HMGGunParts_Motion_PosAndRotation();
        leftPosAndRotation.posX      =centerX;
        leftPosAndRotation.posY      =centerY;
        leftPosAndRotation.posZ      =centerZ;
        leftPosAndRotation.rotationX =rotationX;
        leftPosAndRotation.rotationY =rotationY;
        leftPosAndRotation.rotationZ =rotationZ;
    }
    public void AddRenderinfforward(float centerX, float centerY, float centerZ, float rotationX, float rotationY, float rotationZ){
        forwardPosAndRotation = new HMGGunParts_Motion_PosAndRotation();
        forwardPosAndRotation.posX      =centerX;
        forwardPosAndRotation.posY      =centerY;
        forwardPosAndRotation.posZ      =centerZ;
        forwardPosAndRotation.rotationX =rotationX;
        forwardPosAndRotation.rotationY =rotationY;
        forwardPosAndRotation.rotationZ =rotationZ;
    }
    public void AddRenderinfback(float centerX, float centerY, float centerZ, float rotationX, float rotationY, float rotationZ){
        backPosAndRotation = new HMGGunParts_Motion_PosAndRotation();
        backPosAndRotation.posX      =centerX;
        backPosAndRotation.posY      =centerY;
        backPosAndRotation.posZ      =centerZ;
        backPosAndRotation.rotationX =rotationX;
        backPosAndRotation.rotationY =rotationY;
        backPosAndRotation.rotationZ =rotationZ;
    }
    public void AddRenderinfsneak(float centerX, float centerY, float centerZ, float rotationX, float rotationY, float rotationZ){
        sneakPosAndRotation = new HMGGunParts_Motion_PosAndRotation();
        sneakPosAndRotation.posX      =centerX;
        sneakPosAndRotation.posY      =centerY;
        sneakPosAndRotation.posZ      =centerZ;
        sneakPosAndRotation.rotationX =rotationX;
        sneakPosAndRotation.rotationY =rotationY;
        sneakPosAndRotation.rotationZ =rotationZ;
    }
    public void AddRenderinfjump(float centerX, float centerY, float centerZ, float rotationX, float rotationY, float rotationZ){
        jumpingPosAndRotation = new HMGGunParts_Motion_PosAndRotation();
        jumpingPosAndRotation.posX      =centerX;
        jumpingPosAndRotation.posY      =centerY;
        jumpingPosAndRotation.posZ      =centerZ;
        jumpingPosAndRotation.rotationX =rotationX;
        jumpingPosAndRotation.rotationY =rotationY;
        jumpingPosAndRotation.rotationZ =rotationZ;
    }
    public void AddMotionKeyonRight(int   startflame,
                                    float startcenterX,
                                    float startcenterY,
                                    float startcenterZ,
                                    float startrotationX,
                                    float startrotationY,
                                    float startrotationZ,
                                    int   endflame,
                                    float endcenterX,
                                    float endcenterY,
                                    float endcenterZ,
                                    float endrotationX,
                                    float endrotationY,
                                    float endrotationZ){
        HMGGunParts_Motion motion = new HMGGunParts_Motion();
        motion.startflame =         startflame;
        motion.startposX =          startcenterX;
        motion.startposY =          startcenterY;
        motion.startposZ =          startcenterZ;
        motion.startrotationX =     startrotationX;
        motion.startrotationY =     startrotationY;
        motion.startrotationZ =     startrotationZ;
        motion.endflame =         endflame;
        motion.endposX =          endcenterX;
        motion.endposY =          endcenterY;
        motion.endposZ =          endcenterZ;
        motion.endrotationX =     endrotationX;
        motion.endrotationY =     endrotationY;
        motion.endrotationZ =     endrotationZ;
        motion.setup();
        onrightmotions.addmotion(motion);
    }
    public void AddMotionKeyonRight(int   startflame,
                                    boolean isrendering,
                                    int   endflame){
        HMGGunParts_Motion motion = new HMGGunParts_Motion();
        motion.startflame =         startflame;
        motion.isrendering =         isrendering;
        motion.endflame =         endflame;
        motion.setup();
        onrightmotions.addmotion(motion);
    }
    public void AddMotionKeyonForward(int   startflame,
                                      float startcenterX,
                                      float startcenterY,
                                      float startcenterZ,
                                      float startrotationX,
                                      float startrotationY,
                                      float startrotationZ,
                                      int   endflame,
                                      float endcenterX,
                                      float endcenterY,
                                      float endcenterZ,
                                      float endrotationX,
                                      float endrotationY,
                                      float endrotationZ){
        HMGGunParts_Motion motion = new HMGGunParts_Motion();
        motion.startflame =         startflame;
        motion.startposX =          startcenterX;
        motion.startposY =          startcenterY;
        motion.startposZ =          startcenterZ;
        motion.startrotationX =     startrotationX;
        motion.startrotationY =     startrotationY;
        motion.startrotationZ =     startrotationZ;
        motion.endflame =         endflame;
        motion.endposX =          endcenterX;
        motion.endposY =          endcenterY;
        motion.endposZ =          endcenterZ;
        motion.endrotationX =     endrotationX;
        motion.endrotationY =     endrotationY;
        motion.endrotationZ =     endrotationZ;
        motion.setup();
        onforwardmotions.addmotion(motion);
    }
    public void AddMotionKeyonForward(int   startflame,
                                      boolean isrendering,
                                      int   endflame){
        HMGGunParts_Motion motion = new HMGGunParts_Motion();
        motion.startflame =         startflame;
        motion.isrendering =         isrendering;
        motion.endflame =         endflame;
        motion.setup();
        onforwardmotions.addmotion(motion);
    }
    public void AddMotionKeyonLeft(int   startflame,
                                   float startcenterX,
                                   float startcenterY,
                                   float startcenterZ,
                                   float startrotationX,
                                   float startrotationY,
                                   float startrotationZ,
                                   int   endflame,
                                   float endcenterX,
                                   float endcenterY,
                                   float endcenterZ,
                                   float endrotationX,
                                   float endrotationY,
                                   float endrotationZ){
        HMGGunParts_Motion motion = new HMGGunParts_Motion();
        motion.startflame =         startflame;
        motion.startposX =          startcenterX;
        motion.startposY =          startcenterY;
        motion.startposZ =          startcenterZ;
        motion.startrotationX =     startrotationX;
        motion.startrotationY =     startrotationY;
        motion.startrotationZ =     startrotationZ;
        motion.endflame =         endflame;
        motion.endposX =          endcenterX;
        motion.endposY =          endcenterY;
        motion.endposZ =          endcenterZ;
        motion.endrotationX =     endrotationX;
        motion.endrotationY =     endrotationY;
        motion.endrotationZ =     endrotationZ;
        motion.setup();
        onleftmotions.addmotion(motion);
    }

    public void AddMotionKeyonLeft(int   startflame,
                                   boolean isrendering,
                                   int   endflame){
        HMGGunParts_Motion motion = new HMGGunParts_Motion();
        motion.startflame =         startflame;
        motion.isrendering =         isrendering;
        motion.endflame =         endflame;
        motion.setup();
        onleftmotions.addmotion(motion);
    }
    public void AddMotionKeyonBack(int   startflame,
                                   float startcenterX,
                                   float startcenterY,
                                   float startcenterZ,
                                   float startrotationX,
                                   float startrotationY,
                                   float startrotationZ,
                                   int   endflame,
                                   float endcenterX,
                                   float endcenterY,
                                   float endcenterZ,
                                   float endrotationX,
                                   float endrotationY,
                                   float endrotationZ){
        HMGGunParts_Motion motion = new HMGGunParts_Motion();
        motion.startflame =         startflame;
        motion.startposX =          startcenterX;
        motion.startposY =          startcenterY;
        motion.startposZ =          startcenterZ;
        motion.startrotationX =     startrotationX;
        motion.startrotationY =     startrotationY;
        motion.startrotationZ =     startrotationZ;
        motion.endflame =         endflame;
        motion.endposX =          endcenterX;
        motion.endposY =          endcenterY;
        motion.endposZ =          endcenterZ;
        motion.endrotationX =     endrotationX;
        motion.endrotationY =     endrotationY;
        motion.endrotationZ =     endrotationZ;
        motion.setup();
        onbackmotions.addmotion(motion);
    }
    public void AddMotionKeyonJumped(int   startflame,
                                   float startcenterX,
                                   float startcenterY,
                                   float startcenterZ,
                                   float startrotationX,
                                   float startrotationY,
                                   float startrotationZ,
                                   int   endflame,
                                   float endcenterX,
                                   float endcenterY,
                                   float endcenterZ,
                                   float endrotationX,
                                   float endrotationY,
                                   float endrotationZ){
        HMGGunParts_Motion motion = new HMGGunParts_Motion();
        motion.startflame =         startflame;
        motion.startposX =          startcenterX;
        motion.startposY =          startcenterY;
        motion.startposZ =          startcenterZ;
        motion.startrotationX =     startrotationX;
        motion.startrotationY =     startrotationY;
        motion.startrotationZ =     startrotationZ;
        motion.endflame =         endflame;
        motion.endposX =          endcenterX;
        motion.endposY =          endcenterY;
        motion.endposZ =          endcenterZ;
        motion.endrotationX =     endrotationX;
        motion.endrotationY =     endrotationY;
        motion.endrotationZ =     endrotationZ;
        motion.setup();
        onjumpedmotions.addmotion(motion);
    }
    public void AddMotionKeyonSneaking(int   startflame,
                                   float startcenterX,
                                   float startcenterY,
                                   float startcenterZ,
                                   float startrotationX,
                                   float startrotationY,
                                   float startrotationZ,
                                   int   endflame,
                                   float endcenterX,
                                   float endcenterY,
                                   float endcenterZ,
                                   float endrotationX,
                                   float endrotationY,
                                   float endrotationZ){
        HMGGunParts_Motion motion = new HMGGunParts_Motion();
        motion.startflame =         startflame;
        motion.startposX =          startcenterX;
        motion.startposY =          startcenterY;
        motion.startposZ =          startcenterZ;
        motion.startrotationX =     startrotationX;
        motion.startrotationY =     startrotationY;
        motion.startrotationZ =     startrotationZ;
        motion.endflame =         endflame;
        motion.endposX =          endcenterX;
        motion.endposY =          endcenterY;
        motion.endposZ =          endcenterZ;
        motion.endrotationX =     endrotationX;
        motion.endrotationY =     endrotationY;
        motion.endrotationZ =     endrotationZ;
        motion.setup();
        onsneakingmotions.addmotion(motion);
    }
}