package handmadeguns.client.render;

import java.util.ArrayList;

public class HMGGunParts_Motions {
    public ArrayList<HMGGunParts_Motion> motions = new ArrayList<HMGGunParts_Motion>();
    public void addmotion(HMGGunParts_Motion motion){
        motions.add(motion);
    }
    public HMGGunParts_Motion_PosAndRotation getpartsMotion(float flame){
        HMGGunParts_Motion motionkey = this.getmotionobject(flame);
        if(motionkey != null) return motionkey.posAndRotation(flame);
        return null;
    }
    public HMGGunParts_Motion getmotionobject(float flame){
        for(HMGGunParts_Motion motion:motions){
            if(motion.startflame <= flame && motion.endflame >= flame){
                return motion;
            }
        }
        return null;
    }
}
