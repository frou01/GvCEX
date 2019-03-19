package hmggvcmob.ai;

import handmadeguns.entity.IFF;
import hmggvcmob.IflagBattler;
import hmggvcmob.tile.TileEntityFlag;
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

import java.util.*;

import static java.lang.Math.abs;
import static java.lang.Math.random;

public class AITargetFlag extends EntityAIBase {
    IflagBattler flagBattler;
    EntityLiving flagBattlerBody;
    IFF flagbattlerIFF;
    int cool;
    Random random = new Random();

    public AITargetFlag(IflagBattler flagBattler,EntityLiving entityLiving,IFF iff){
        this.flagBattler = flagBattler;
        this.flagBattlerBody = entityLiving;
        this.flagbattlerIFF = iff;
        cool=new Random().nextInt(600);
    }
    @Override
    public boolean shouldExecute() {
        cool--;
        if (flagBattler.istargetingflag()||cool>0) {
            return false;
        }
        if(!flagBattler.istargetingflag()){
            cool=new Random().nextInt(600);
            TileEntity tileentityL = null;
            TileEntity tileentity;
            double tempdist1;
            double tempdist2=16384;
            try {
                for (int i = 0;i < flagBattlerBody.worldObj.loadedTileEntityList.size();i++) {
                    Object aLoadedTileEntityList = flagBattlerBody.worldObj.loadedTileEntityList.get(i);
                    tileentity = (TileEntity) aLoadedTileEntityList;
                    if (tileentity instanceof TileEntityFlag && flagBattler.isthisFlagIsEnemys(flagBattlerBody.worldObj.getBlock(tileentity.xCoord, tileentity.yCoord, tileentity.zCoord))) {
                        tempdist1 = flagBattlerBody.getDistanceSq(tileentity.xCoord, tileentity.yCoord, tileentity.zCoord);
                        if (tempdist1 < tempdist2) {
                            tempdist2 = tempdist1;
                            tileentityL = tileentity;
                        }
                    }
                }
                if (tileentityL != null) {
                    flagBattler.setflagposition(tileentityL.xCoord, tileentityL.yCoord, tileentityL.zCoord);
//                    System.out.println("debug");
                    List nearEntitys = flagBattlerBody.worldObj.getEntitiesWithinAABBExcludingEntity(flagBattlerBody, flagBattlerBody.boundingBox.expand(32, 32, 32));
                    for (Object o : nearEntitys) {
                        if (o instanceof EntityLiving && flagbattlerIFF.is_this_entity_friend((Entity) o) && o instanceof IflagBattler) {
                            ((IflagBattler) o).setflagposition(tileentityL.xCoord, tileentityL.yCoord, tileentityL.zCoord);
                        }
                    }
                    return true;
                }
            }catch (Exception e){
            }
        }
        return false;
    }
}