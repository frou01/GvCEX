package hmggvcmob;

import handmadevehicle.entity.parts.Hasmode;
import hmggvcmob.camp.CampObj;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;

import java.util.ArrayList;


public interface IflagBattler extends Hasmode {
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

    @Override
    default int getMobMode() {
        if(getTargetCampPosition() != null)return 1;
        else return 0;
    }

    @Override
    default double[] getTargetpos() {
        return new double[]{getTargetCampPosition()[0],getTargetCampPosition()[1],getTargetCampPosition()[2]};
    }

    @Override
    default boolean standalone() {
        return true;
    }

    boolean isThisAttackAbleCamp(CampObj campObj);
    boolean isThisFriendCamp(CampObj campObj);
    boolean isThisIgnoreSpawnCamp(CampObj campObj);
}
