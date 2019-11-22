package hmggvcmob;

import hmggvcmob.camp.CampObj;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;

import java.util.ArrayList;


public interface IflagBattler {
    CampObj getCampObj();
    byte getState();
    void setState(byte state);
    int[] getTargetCampPosition();
    void setTargetCampPosition(int[] ints);
    void makePlatoon();
    void setPlatoon(ArrayList<Entity> entities);
    void joinPlatoon(IflagBattler iflagBattler);
    ArrayList<Entity> getPlatoon();
    IflagBattler getPlatoonLeader();

    boolean isThisAttackAbleCamp(CampObj campObj);
    boolean isThisFriendCamp(CampObj campObj);
    boolean isThisIgnoreSpawnCamp(CampObj campObj);
}
