package handmadeguns.Util;

public class SoundInfo {
    public String sound;
    public float LV;
    public float SP;
    public float MinBltSP;
    public float MaxDist;
    public SoundInfo(String name, float lv, float sp,float minsp,float maxDist){
        sound = name;
        LV = lv;
        SP = sp;
        MinBltSP = minsp;
        MaxDist = maxDist;
    }
}
