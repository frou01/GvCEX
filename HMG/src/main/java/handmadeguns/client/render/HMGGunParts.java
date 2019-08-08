package handmadeguns.client.render;

import java.util.ArrayList;

public class HMGGunParts {
    public String partsname;
    public boolean isLarm;
    public boolean isRarm;
    public ArrayList<HMGGunParts> childs = new ArrayList<HMGGunParts>();
    public ArrayList<Boolean> current_magazineType = null;
    public ArrayList<Boolean> select_magazineType = null;
    public boolean isfounder;
    public HMGGunParts mother;
    public int motherIndex;

    private HMGGunParts_Motion_PosAndRotation defaultPosAndRotation;
    private HMGGunParts_Motion_PosAndRotation defaultPosAndRotation_ForOffset = new HMGGunParts_Motion_PosAndRotation();

    public boolean rendering_Def;

    private HMGGunParts_Motion_PosAndRotation adsPosAndRotation;

    boolean rendering_Ads;

    private HMGGunParts_Motion_PosAndRotation recoilPosAndRotation;

    boolean rendering_Recoil;

    private HMGGunParts_Motion_PosAndRotation cockPosAndRotation;

    boolean rendering_Cock;

    private HMGGunParts_Motion_PosAndRotation reloadPosAndRotation;

    boolean rendering_Reload;

    private HMGGunParts_Motion_PosAndRotation reloadPosAndRotation_Arm;

    private HMGGunParts_Motion_PosAndRotation bulletPosAndRotation;

    int Maximum_number_of_bullets;//�ő僋�[�v��
    //�e���Ń��[�v�����邪1200���Ƃ���LMG�ł������������΂����ƂɂȂ�̂ő΍�
    boolean isavatar;
    boolean isbullet;

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
    boolean                     hasMotionCock;
    private HMGGunParts_Motions onrecoilmotions;
    boolean                     hasMotionRecoil;
    private HMGGunParts_Motions onreloadmotions;
    boolean                     hasMotionReload;
    private HMGGunParts_Motions baseYawInfo;
    boolean                     hasbaseYawInfo;
    private HMGGunParts_Motions basePitchInfo;
    boolean                     hasbasePitchInfo;
    private HMGGunParts_Motions bulletpositions;
    boolean                     isbelt;

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
    HMGGunParts_Motion_PosAndRotation getRenderinfDefault_offset(){
        return defaultPosAndRotation_ForOffset;
    }
    HMGGunParts_Motion_PosAndRotation getRenderinfOfDef(){
        return defaultPosAndRotation;
    }
    public HMGGunParts_Motion_PosAndRotation getRenderinfOfADS(){
        return adsPosAndRotation;
    }
    HMGGunParts_Motion_PosAndRotation getRenderinfOfRecoil(){
        return recoilPosAndRotation;
    }
    HMGGunParts_Motion_PosAndRotation getRenderinfOfCock(){
        return cockPosAndRotation;
    }
    HMGGunParts_Motion_PosAndRotation getRenderinfOfReload(){
        return reloadPosAndRotation;
    }
    HMGGunParts_Motion_PosAndRotation getRenderinfOfBullet(){
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
        defaultPosAndRotation = new HMGGunParts_Motion_PosAndRotation();
        defaultPosAndRotation.posX      =centerX;
        defaultPosAndRotation.posY      =centerY;
        defaultPosAndRotation.posZ      =centerZ;
        defaultPosAndRotation.rotationX =rotationX;
        defaultPosAndRotation.rotationY =rotationY;
        defaultPosAndRotation.rotationZ =rotationZ;
    }
    public void AddRenderinfDefoffset(float offsetx,float offsety,float offsetz,float rotationX,float rotationY,float rotationZ){
        defaultPosAndRotation_ForOffset = new HMGGunParts_Motion_PosAndRotation();
        defaultPosAndRotation_ForOffset.posX      =offsetx;
        defaultPosAndRotation_ForOffset.posY      =offsety;
        defaultPosAndRotation_ForOffset.posZ      =offsetz;
        defaultPosAndRotation_ForOffset.rotationX =rotationX;
        defaultPosAndRotation_ForOffset.rotationY =rotationY;
        defaultPosAndRotation_ForOffset.rotationZ =rotationZ;
    }
    public void AddRenderinfADS(float offsetX,float offsetY,float offsetZ,float rotationX,float rotationY,float rotationZ){
        adsPosAndRotation = new HMGGunParts_Motion_PosAndRotation();
        adsPosAndRotation.posX      =offsetX;
        adsPosAndRotation.posY      =offsetY;
        adsPosAndRotation.posZ      =offsetZ;
        adsPosAndRotation.rotationX =rotationX;
        adsPosAndRotation.rotationY =rotationY;
        adsPosAndRotation.rotationZ =rotationZ;
        rendering_Ads = true;
    }
    public void AddRenderinfCock(float offsetX,float offsetY,float offsetZ,float rotationX,float rotationY,float rotationZ){
        cockPosAndRotation = new HMGGunParts_Motion_PosAndRotation();
        cockPosAndRotation.posX      =offsetX;
        cockPosAndRotation.posY      =offsetY;
        cockPosAndRotation.posZ      =offsetZ;
        cockPosAndRotation.rotationX =rotationX;
        cockPosAndRotation.rotationY =rotationY;
        cockPosAndRotation.rotationZ =rotationZ;
        rendering_Cock = true;
    }
    public void AddRenderinfReload(float offsetX,float offsetY,float offsetZ,float rotationX,float rotationY,float rotationZ){
        reloadPosAndRotation = new HMGGunParts_Motion_PosAndRotation();
        reloadPosAndRotation.posX      =offsetX;
        reloadPosAndRotation.posY      =offsetY;
        reloadPosAndRotation.posZ      =offsetZ;
        reloadPosAndRotation.rotationX =rotationX;
        reloadPosAndRotation.rotationY =rotationY;
        reloadPosAndRotation.rotationZ =rotationZ;
        rendering_Reload = true;
    }
    public void AddRenderinfRecoil(float offsetX,float offsetY,float offsetZ,float rotationX,float rotationY,float rotationZ){
        recoilPosAndRotation = new HMGGunParts_Motion_PosAndRotation();
        recoilPosAndRotation.posX      =offsetX;
        recoilPosAndRotation.posY      =offsetY;
        recoilPosAndRotation.posZ      =offsetZ;
        recoilPosAndRotation.rotationX =rotationX;
        recoilPosAndRotation.rotationY =rotationY;
        recoilPosAndRotation.rotationZ =rotationZ;
        rendering_Recoil = true;
    }
    public void AddRenderinfBullet(float offsetX,float offsetY,float offsetZ,float rotationX,float rotationY,float rotationZ){
        //��Ɏc�e�����ݒ�l�ŉ�]�y�шړ����s���`�悷��B
        bulletPosAndRotation = new HMGGunParts_Motion_PosAndRotation();
        bulletPosAndRotation.posX      =offsetX;
        bulletPosAndRotation.posY      =offsetY;
        bulletPosAndRotation.posZ      =offsetZ;
        bulletPosAndRotation.rotationX =rotationX;
        bulletPosAndRotation.rotationY =rotationY;
        bulletPosAndRotation.rotationZ =rotationZ;
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