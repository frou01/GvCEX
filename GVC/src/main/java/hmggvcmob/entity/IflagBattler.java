package hmggvcmob.entity;

import handmadevehicle.entity.parts.Modes;
import hmggvcmob.camp.CampObj;

import static handmadevehicle.entity.parts.Modes.Go;
import static handmadevehicle.entity.parts.Modes.Wait;


public interface IflagBattler extends IPlatoonable {
    CampObj getCampObj();
    default int[] getTargetCampPosition(){
        if(getPlatoon() != null)return new int[]{
                (int) getPlatoon().PlatoonTargetPos.x,
                (int) getPlatoon().PlatoonTargetPos.y,
                (int) getPlatoon().PlatoonTargetPos.z};
        else return null;
    }
    default void setTargetCampPosition(int[] ints){
        if(getPlatoon()!= null)getPlatoon().setPlatoonTargetPos(ints);
    }

    @Override
    default Modes getMobMode() {
        if(getTargetCampPosition() != null)return Go;
        else return Wait;
    }

    @Override
    default double[] getTargetpos() {
        return new double[]{getTargetCampPosition()[0],getTargetCampPosition()[1],getTargetCampPosition()[2]};
    }

    boolean isThisAttackAbleCamp(CampObj campObj);
    boolean isThisFriendCamp(CampObj campObj);
    boolean isThisIgnoreSpawnCamp(CampObj campObj);
}
