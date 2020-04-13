package hmggvcmob.ai;

import handmadeguns.entity.IFF;
import hmggvcmob.entity.IflagBattler;
import hmggvcmob.camp.CampObjAndPos;
import hmggvcmob.world.WorldSavedData_Flag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.World;

import java.util.*;

import static java.lang.Math.abs;

public class AITargetFlag extends EntityAIBase {
    IflagBattler flagBattler;
    EntityLiving flagBattlerBody;
    IFF flagbattlerIFF;
    int cool;
    World world;
    WorldSavedData_Flag worldSavedData_flag;
    Random random = new Random();

    public AITargetFlag(IflagBattler flagBattler,EntityLiving entityLiving,IFF iff){
        this.flagBattler = flagBattler;
        this.flagBattlerBody = entityLiving;
        this.flagbattlerIFF = iff;
        this.world = flagBattlerBody.worldObj;
        cool=new Random().nextInt(600);
    }
    @Override
    public boolean shouldExecute() {
        cool--;
        if(cool < 0 && (flagBattler.isPlatoonLeader() || flagBattler.isFree())){//•ª‘à‚É–¢Š‘®‚©A•ª‘à’·
            cool = new Random().nextInt(20);
            try {
                if(worldSavedData_flag == null) worldSavedData_flag = WorldSavedData_Flag.get(world);
                Iterator<CampObjAndPos> iterator = worldSavedData_flag.campObjHashMap.values().iterator();
                CampObjAndPos nearest = null;
                double dist = -1;
                while (iterator.hasNext()){
                    CampObjAndPos campObjAndPos = iterator.next();
                    double currentDist = flagBattlerBody.getDistanceSq(campObjAndPos.flagPos[0],campObjAndPos.flagPos[1],campObjAndPos.flagPos[2]);
                    if((flagBattler.isThisAttackAbleCamp(campObjAndPos.campObj) || (flagBattler.isThisFriendCamp(campObjAndPos.campObj) && campObjAndPos.attacked))
                            && (dist == -1 || currentDist < dist )){
                        dist = currentDist;
                        nearest = campObjAndPos;
                    }
                }
                if(nearest != null){
                    if(flagBattler.isFree())flagBattler.makePlatoon();
                    flagBattler.setTargetCampPosition(nearest.flagPos);
//                    System.out.println("debug Target:" + nearest.flagPos[0] + "," + nearest.flagPos[1] + "," + nearest.flagPos[2]);
                    return true;
                }
            }catch (Exception e){
            }
        }
        return false;
    }
}