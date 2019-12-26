package hmggvcmob.ai;

import handmadeguns.entity.IFF;
import hmggvcmob.IflagBattler;
import hmggvcmob.camp.CampObjAndPos;
import hmggvcmob.tile.TileEntityFlag;
import hmggvcmob.world.WorldSavedData_Flag;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.*;

import static java.lang.Math.abs;
import static java.lang.Math.random;

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
        if(cool < 0 && (flagBattler.getPlatoon() == null || flagBattler.getPlatoonLeader() == flagBattler)){//•ª‘à‚É–¢Š‘®‚©A•ª‘à’·
            cool = new Random().nextInt(20);
            try {
                if(worldSavedData_flag == null) worldSavedData_flag = WorldSavedData_Flag.get(world);
                Iterator<CampObjAndPos> iterator = worldSavedData_flag.campObjHashMap.values().iterator();
                CampObjAndPos nearest = null;
                double dist = -1;
                while (iterator.hasNext()){
                    CampObjAndPos campObjAndPos = iterator.next();
                    double currentDist = flagBattlerBody.getDistanceSq(campObjAndPos.flagPos[0],campObjAndPos.flagPos[1],campObjAndPos.flagPos[2]);
                    if((flagBattler.isThisAttackAbleCamp(campObjAndPos.campObj) || (flagBattler.isThisAttackAbleCamp(campObjAndPos.campObj) && campObjAndPos.attacked))
                            && (dist == -1 || currentDist < dist )){
                        dist = currentDist;
                        nearest = campObjAndPos;
                    }
                }
                if(nearest != null){
                    flagBattler.setTargetCampPosition(nearest.flagPos);
                    flagBattler.makePlatoon();
//                    System.out.println("debug Target:" + nearest.flagPos[0] + "," + nearest.flagPos[1] + "," + nearest.flagPos[2]);
                    return true;
                }
            }catch (Exception e){
            }
        }
        IflagBattler platoonLeader;
        if((platoonLeader = flagBattler.getPlatoonLeader()) != null) {
            if (((Entity) platoonLeader).isDead) {
                flagBattler.joinPlatoon(null);
                return false;
            }
        }
        return false;
    }
}