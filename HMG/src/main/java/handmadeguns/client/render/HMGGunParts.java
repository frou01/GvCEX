package handmadeguns.client.render;

import java.util.ArrayList;

public class HMGGunParts {
    public boolean rotateTypeIsVector = false;
    public String partsname;
    public boolean isLarm;
    public boolean isRarm;
    public ArrayList<HMGGunParts> childs = new ArrayList<HMGGunParts>();
    public ArrayList<Boolean> current_magazineType = null;
    public ArrayList<Boolean> select_magazineType = null;
    public HMGGunParts mother;
    public int motherIndex;

    private HMGGunParts_Motion_PosAndRotation rotCenterAndRotation;
    private HMGGunParts_Motion_PosAndRotation defaultPosAndRotation_ForOffset = new HMGGunParts_Motion_PosAndRotation(0,0,0,0,0,0);

    public boolean rendering_Def;
    public boolean rendering_Ads;
    public boolean rendering_Recoil;
    public boolean rendering_Cock;
    public boolean rendering_Reload;

    private HMGGunParts_Motion_PosAndRotation adsPosAndRotation;


    private HMGGunParts_Motion_PosAndRotation recoilPosAndRotation;
    

    private HMGGunParts_Motion_PosAndRotation cockPosAndRotation;
    

    private HMGGunParts_Motion_PosAndRotation reloadPosAndRotation;
    

    private HMGGunParts_Motion_PosAndRotation reloadPosAndRotation_Arm;

    private HMGGunParts_Motion_PosAndRotation bulletPosAndRotation;
    
    public int Maximum_number_of_bullets;//�ő僋�[�v��
    //�e���Ń��[�v�����邪1200���Ƃ���LMG�ł������������΂����ƂɂȂ�̂ő΍�
    public boolean isavatar;
    public boolean isbullet;

    public boolean isattachpart;
    public boolean isscope;
    public boolean isdot;
    public boolean issight;
    public boolean isgrip;
    public boolean isgripcover;
    public boolean isswordbase;
    public boolean issword;
    public boolean isunderSG;
    public boolean isunderGL;
    public boolean ismuzzlepart;
    public boolean islight;
    public boolean islasersight;
    public boolean isgripBase;
    public boolean isunderGunbase;
    public boolean isoverbarrelbase;
    public boolean ismuzzulebase;
    public boolean issightbase;

    public boolean base;
    public boolean carryingHandle;
    public boolean underOnly;
    public boolean underOnly_not;
    public boolean reticleAndPlate;


    private HMGGunParts_Motions oncockmotions;
    public boolean                     hasMotionCock;
    private HMGGunParts_Motions onrecoilmotions;
    public boolean                     hasMotionRecoil;
    private HMGGunParts_Motions onreloadmotions;
    public boolean                     hasMotionReload;
    private HMGGunParts_Motions baseYawInfo;
    public boolean                     hasbaseYawInfo;
    private HMGGunParts_Motions basePitchInfo;
    public boolean                     hasbasePitchInfo;
    private HMGGunParts_Motions bulletpositions;
    public boolean                     isbelt;

    public HMGGunParts(){
        oncockmotions = new HMGGunParts_Motions();
        onrecoilmotions = new HMGGunParts_Motions();
        onreloadmotions = new HMGGunParts_Motions();
        bulletpositions = new HMGGunParts_Motions();
        baseYawInfo = new HMGGunParts_Motions();
        basePitchInfo = new HMGGunParts_Motions();
    }
    public HMGGunParts(String partsname) {
        this();
        if(partsname.equals("Larm"))isLarm=true;
        if(partsname.equals("Rarm"))isRarm=true;
        this.partsname = partsname;
    }
    
    public HMGGunParts(String string, int motherID, HMGGunParts mother) {
        this(string);
        this.mother = mother;
        this.motherIndex = motherID;
    }
    
    public HMGGunParts_Motion_PosAndRotation getRenderinfDefault_offset(){
        return defaultPosAndRotation_ForOffset;
    }
    public HMGGunParts_Motion_PosAndRotation getRenderinfCenter(){
        return rotCenterAndRotation;
    }
    public HMGGunParts_Motion_PosAndRotation getRenderinfOfADS(){
        return adsPosAndRotation;
    }
    public HMGGunParts_Motion_PosAndRotation getRenderinfOfRecoil(){
        return recoilPosAndRotation;
    }
    public HMGGunParts_Motion_PosAndRotation getRenderinfOfCock(){
        return cockPosAndRotation;
    }
    public HMGGunParts_Motion_PosAndRotation getRenderinfOfReload(){
        return reloadPosAndRotation;
    }
    public HMGGunParts_Motion_PosAndRotation getRenderinfOfBullet(){
        return bulletPosAndRotation;
    }
    public HMGGunParts_Motion_PosAndRotation getcockmotion(float flame){
        return oncockmotions.getpartsMotion(flame);
    }
    public HMGGunParts_Motion_PosAndRotation getRecoilmotion(float flame){
        return onrecoilmotions.getpartsMotion(flame);
    }
    public HMGGunParts_Motion_PosAndRotation getReloadmotion(float flame){
        return onreloadmotions.getpartsMotion(flame);
    }
    public HMGGunParts_Motion_PosAndRotation getYawInfo(float flame){
        return baseYawInfo.getpartsMotion(flame);
    }
    public HMGGunParts_Motion_PosAndRotation getPitchInfo(float flame){
        return basePitchInfo.getpartsMotion(flame);
    }
    public HMGGunParts_Motion_PosAndRotation getBulletposition(float flame){
        return bulletpositions.getpartsMotion(flame);
    }
    public void AddRenderinfDef(float centerX,float centerY,float centerZ,float rotationX,float rotationY,float rotationZ){
        rotCenterAndRotation = new HMGGunParts_Motion_PosAndRotation(centerX,centerY,centerZ,rotationX,rotationY,rotationZ);
    }
    public void AddRenderinfDef(float centerX,float centerY,float centerZ,float VectorX,float VectorY,float VectorZ,float rotationAmount){
        rotCenterAndRotation = new HMGGunParts_Motion_PosAndRotation(centerX,centerY,centerZ,VectorX,VectorY,VectorZ,rotationAmount);
    }
    public void AddRenderinfDefoffset(float offsetx,float offsety,float offsetz,float rotationX,float rotationY,float rotationZ){
        defaultPosAndRotation_ForOffset = new HMGGunParts_Motion_PosAndRotation(offsetx,offsety,offsetz,rotationX,rotationY,rotationZ);
    }
    public void AddRenderinfADS(float offsetX,float offsetY,float offsetZ,float rotationX,float rotationY,float rotationZ){
        adsPosAndRotation = new HMGGunParts_Motion_PosAndRotation(offsetX,offsetY,offsetZ,rotationX,rotationY,rotationZ);
        rendering_Ads = true;
    }
    public void AddRenderinfCock(float offsetX,float offsetY,float offsetZ,float rotationX,float rotationY,float rotationZ){
        cockPosAndRotation = new HMGGunParts_Motion_PosAndRotation(offsetX,offsetY,offsetZ,rotationX,rotationY,rotationZ);
        rendering_Cock = true;
    }
    public void AddRenderinfReload(float offsetX,float offsetY,float offsetZ,float rotationX,float rotationY,float rotationZ){
        reloadPosAndRotation = new HMGGunParts_Motion_PosAndRotation(offsetX,offsetY,offsetZ,rotationX,rotationY,rotationZ);
        rendering_Reload = true;
    }
    public void AddRenderinfRecoil(float offsetX,float offsetY,float offsetZ,float rotationX,float rotationY,float rotationZ){
        recoilPosAndRotation = new HMGGunParts_Motion_PosAndRotation(offsetX,offsetY,offsetZ,rotationX,rotationY,rotationZ);
        rendering_Recoil = true;
    }
    public void AddRenderinfBullet(float offsetX,float offsetY,float offsetZ,float rotationX,float rotationY,float rotationZ){
        bulletPosAndRotation = new HMGGunParts_Motion_PosAndRotation(offsetX,offsetY,offsetZ,rotationX,rotationY,rotationZ);
        isbullet = true;
    }
    public void AddMotionKeyRecoil(int   startflame,
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
        HMGGunParts_Motion motion = new HMGGunParts_Motion();
        motion.startflame =         startflame;
        motion.startposX =          startoffsetX;
        motion.startposY =          startoffsetY;
        motion.startposZ =          startoffsetZ;
        motion.startrotationX =     startrotationX;
        motion.startrotationY =     startrotationY;
        motion.startrotationZ =     startrotationZ;
        motion.endflame =         endflame;
        motion.endposX =          endoffsetX;
        motion.endposY =          endoffsetY;
        motion.endposZ =          endoffsetZ;
        motion.endrotationX =     endrotationX;
        motion.endrotationY =     endrotationY;
        motion.endrotationZ =     endrotationZ;
        motion.setup();
        onrecoilmotions.addmotion(motion);
        hasMotionRecoil = true;
    }
    public void AddMotionKeyRecoil(int   startflame,
                                   boolean isrendering,
                                   int   endflame){
        HMGGunParts_Motion motion = new HMGGunParts_Motion();
        motion.startflame =         startflame;
        motion.isrendering =         isrendering;
        motion.endflame =         endflame;
        motion.setup();
        onrecoilmotions.addmotion(motion);
        hasMotionRecoil = true;
    }
    public void AddMotionKeyCock(int   startflame,
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
        HMGGunParts_Motion motion = new HMGGunParts_Motion();
        motion.startflame =         startflame;
        motion.startposX =          startoffsetX;
        motion.startposY =          startoffsetY;
        motion.startposZ =          startoffsetZ;
        motion.startrotationX =     startrotationX;
        motion.startrotationY =     startrotationY;
        motion.startrotationZ =     startrotationZ;
        motion.endflame =         endflame;
        motion.endposX =          endoffsetX;
        motion.endposY =          endoffsetY;
        motion.endposZ =          endoffsetZ;
        motion.endrotationX =     endrotationX;
        motion.endrotationY =     endrotationY;
        motion.endrotationZ =     endrotationZ;
        motion.setup();
        oncockmotions.addmotion(motion);
        hasMotionCock = true;
    }
    public void AddMotionKeyCock(int   startflame,
                                   boolean isrendering,
                                   int   endflame){
        HMGGunParts_Motion motion = new HMGGunParts_Motion();
        motion.startflame =         startflame;
        motion.isrendering =         isrendering;
        motion.endflame =         endflame;
        motion.setup();
        oncockmotions.addmotion(motion);
        hasMotionCock = true;
    }
    public void AddMotionKeyReload(int   startflame,
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
        HMGGunParts_Motion motion = new HMGGunParts_Motion();
        motion.startflame =         startflame;
        motion.startposX =          startoffsetX;
        motion.startposY =          startoffsetY;
        motion.startposZ =          startoffsetZ;
        motion.startrotationX =     startrotationX;
        motion.startrotationY =     startrotationY;
        motion.startrotationZ =     startrotationZ;
        motion.endflame =         endflame;
        motion.endposX =          endoffsetX;
        motion.endposY =          endoffsetY;
        motion.endposZ =          endoffsetZ;
        motion.endrotationX =     endrotationX;
        motion.endrotationY =     endrotationY;
        motion.endrotationZ =     endrotationZ;
        motion.setup();
        onreloadmotions.addmotion(motion);
        hasMotionReload = true;
    }
    public void AddInfoKeyTurretYaw(int   startflame,
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
        HMGGunParts_Motion motion = new HMGGunParts_Motion();
        motion.startflame =         startflame;
        motion.startposX =          startoffsetX;
        motion.startposY =          startoffsetY;
        motion.startposZ =          startoffsetZ;
        motion.startrotationX =     startrotationX;
        motion.startrotationY =     startrotationY;
        motion.startrotationZ =     startrotationZ;
        motion.endflame =         endflame;
        motion.endposX =          endoffsetX;
        motion.endposY =          endoffsetY;
        motion.endposZ =          endoffsetZ;
        motion.endrotationX =     endrotationX;
        motion.endrotationY =     endrotationY;
        motion.endrotationZ =     endrotationZ;
        motion.setup();
        baseYawInfo.addmotion(motion);
        hasbaseYawInfo = true;
    }
    public void AddInfoKeyTurretYaw(int   startflame,
                                 boolean isrendering,
                                 int   endflame){
        HMGGunParts_Motion motion = new HMGGunParts_Motion();
        motion.startflame =         startflame;
        motion.isrendering =         isrendering;
        motion.endflame =         endflame;
        motion.setup();
        baseYawInfo.addmotion(motion);
        hasbaseYawInfo = true;
    }
    public void AddInfoKeyTurretPitch(int   startflame,
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
        HMGGunParts_Motion motion = new HMGGunParts_Motion();
        motion.startflame =         startflame;
        motion.startposX =          startoffsetX;
        motion.startposY =          startoffsetY;
        motion.startposZ =          startoffsetZ;
        motion.startrotationX =     startrotationX;
        motion.startrotationY =     startrotationY;
        motion.startrotationZ =     startrotationZ;
        motion.endflame =         endflame;
        motion.endposX =          endoffsetX;
        motion.endposY =          endoffsetY;
        motion.endposZ =          endoffsetZ;
        motion.endrotationX =     endrotationX;
        motion.endrotationY =     endrotationY;
        motion.endrotationZ =     endrotationZ;
        motion.setup();
        basePitchInfo.addmotion(motion);
        hasbasePitchInfo = true;
    }
    public void AddInfoKeyTurretPitch(int   startflame,
                                    boolean isrendering,
                                    int   endflame){
        HMGGunParts_Motion motion = new HMGGunParts_Motion();
        motion.startflame =         startflame;
        motion.isrendering =         isrendering;
        motion.endflame =         endflame;
        motion.setup();
        basePitchInfo.addmotion(motion);
        hasbasePitchInfo = true;
    }

    public void AddMotionKeyReload(int   startflame,
                                 boolean isrendering,
                                 int   endflame){
        HMGGunParts_Motion motion = new HMGGunParts_Motion();
        motion.startflame =         startflame;
        motion.isrendering =         isrendering;
        motion.endflame =         endflame;
        motion.setup();
        onreloadmotions.addmotion(motion);
        hasMotionReload = true;
    }
    public void AddBulletPositions(int   startflame,
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
        //�w�肳�ꂽ�o�H�����ǂ��Ēe��`�悷��p
        HMGGunParts_Motion motion = new HMGGunParts_Motion();
        motion.startflame =         startflame;
        motion.startposX =          startoffsetX;
        motion.startposY =          startoffsetY;
        motion.startposZ =          startoffsetZ;
        motion.startrotationX =     startrotationX;
        motion.startrotationY =     startrotationY;
        motion.startrotationZ =     startrotationZ;
        motion.endflame =         endflame;
        motion.endposX =          endoffsetX;
        motion.endposY =          endoffsetY;
        motion.endposZ =          endoffsetZ;
        motion.endrotationX =     endrotationX;
        motion.endrotationY =     endrotationY;
        motion.endrotationZ =     endrotationZ;
        motion.setup();
        bulletpositions.addmotion(motion);
        isbelt = true;
    }
    
    public void setIsbullet(boolean isbullet,int number_of_bullets){
        this.isavatar = isbullet;
        this.Maximum_number_of_bullets = number_of_bullets;
    }
}