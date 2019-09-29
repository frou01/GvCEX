package hmggvcmob.ai;

import hmggvcmob.IflagBattler;
import handmadevehicle.SlowPathFinder.WorldForPathfind;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.Vec3;

import java.util.Random;

public class AIAttackFlag extends EntityAIBase{
    IflagBattler flagBattler;
    EntityLiving flagBattlerBody;
    private WorldForPathfind worldForPathfind;
    int repathcnt = 0;
    int repathcntcool = 0;
    Random rnd = new Random();

    public AIAttackFlag(IflagBattler flagBattler,EntityLiving entityLiving){
        this.flagBattler = flagBattler;
        this.flagBattlerBody = entityLiving;
    }
    @Override
    public boolean shouldExecute() {
        return flagBattler.istargetingflag() && ((EntityLiving) flagBattler).getAttackTarget() == null;
    }
    public AIAttackFlag(EntityLiving guerrilla,IflagBattler flagBattler){
        this.flagBattlerBody = guerrilla;
        this.flagBattler = flagBattler;
        worldForPathfind = new WorldForPathfind(guerrilla.worldObj);
    }
    @Override
    public void updateTask(){
        Vec3 flagpos = flagBattler.getflagposition();
        repathcnt++;
        flagBattlerBody.getLookHelper().setLookPosition((int)flagpos.xCoord, (int) flagpos.yCoord, (int) flagpos.zCoord,90,90);
        if(repathcnt > repathcntcool){
            if(flagBattlerBody.getDistanceSq((int)flagpos.xCoord, (int) flagpos.yCoord, (int) flagpos.zCoord)>10 && (flagBattlerBody.onGround || flagBattlerBody.isWet())) flagBattlerBody.getNavigator().setPath(worldForPathfind.getEntityPathToXYZ(flagBattlerBody, (int)flagpos.xCoord, (int) flagpos.yCoord, (int) flagpos.zCoord, 120, true, false, false, true), 1.0d);
        }else {
            repathcntcool = 10 + rnd.nextInt(30);
        }
    }

}
