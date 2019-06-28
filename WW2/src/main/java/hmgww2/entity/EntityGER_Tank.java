package hmgww2.entity;


import hmggvcmob.ai.AITankAttack;
import hmvehicle.entity.parts.logics.TankBaseLogic;
import hmvehicle.entity.parts.turrets.TurretObj;
import net.minecraft.world.World;

import javax.vecmath.Vector3d;

public class EntityGER_Tank extends EntityGER_TankBase
{
	// public int type;
	
    public EntityGER_Tank(World par1World)
    {
        super(par1World);
        this.setSize(4F, 2.5F);
        baseLogic = new TankBaseLogic(this,0.27f,0.65f,false,"hmgww2:hmgww2.PzIVTrack");
        aiTankAttack = new AITankAttack(this,6400,1600,200,200);
        this.tasks.addTask(1,aiTankAttack);
        aiTankAttack.setAlways_poit_to_target(true);
        playerpos = new Vector3d(0,3.4D,0.7);
        zoomingplayerpos = new Vector3d(0.55,2.35D,-0.80);
        cannonpos = new Vector3d(0,2.0,-1.00F);
        turretpos = new Vector3d(0,0,0);
        
        mainTurret = new TurretObj(worldObj);
        {
            mainTurret.onMotherPos = turretpos;
            mainTurret.cannonPos = cannonpos;
            mainTurret.turretPitchCenterpos = new Vector3d(0F, 2.00F, 1.00F);
            mainTurret.turretspeedY = 3;
            mainTurret.turretspeedP = 3;
            mainTurret.currentEntity = this;
            mainTurret.powor = 90;
            mainTurret.ex = 2.0F;
            mainTurret.reloadsound = "hmgww2:hmgww2.deload_pick_load";
            mainTurret.firesound = "hmgww2:hmgww2.75mmfire";
            mainTurret.spread = 0.5f;
            mainTurret.speed = 8;
            mainTurret.canex = true;
            mainTurret.guntype = 2;
        }
        subTurret = new TurretObj(worldObj);
        {
            subTurret.currentEntity = this;
            subTurret.turretanglelimtPitchmin = -90;
            subTurret.turretanglelimtPitchMax = 45;
            subTurret.turretanglelimtYawmin = -360;
            subTurret.turretanglelimtYawMax = 360;
            subTurret.turretspeedY = 8;
            subTurret.turretspeedP = 10;
            subTurret.traverseSound = null;
    
            subturretpos = new Vector3d(0,2.9,0.7);
            subTurret.onMotherPos = subturretpos;
            subTurret.turretPitchCenterpos = new Vector3d(0,0.25, 0.5 + 0.7);
            subTurret.cannonPos = new Vector3d(0,0.285,1.2);
            subTurret.cycle_setting = 0;
            subTurret.spread = 5;
            subTurret.speed = 8;
            subTurret.firesound = "handmadeguns:handmadeguns.MG34";
            subTurret.flashName = "arrow";
            subTurret.flashfuse = 1;
            subTurret.flashscale = 1.5f;
        
            subTurret.powor = 8;
            subTurret.ex = 0;
            subTurret.canex = false;
            subTurret.guntype = 0;
        
            subTurret.magazineMax = 500;
            subTurret.reloadSetting = 100;
            subTurret.flashoffset = 0.5f;
        }
        subturret_is_mainTurret_child = true;
        mainTurret.addchild_NOTtriggerLinked(subTurret);
        turrets = new TurretObj[]{mainTurret,subTurret};
        armor = 40;
    }
    protected void applyEntityAttributes()
    {
        maxHealth = 350;
        super.applyEntityAttributes();
    }
    
    public void onUpdate(){
        super.onUpdate();
        if(!worldObj.isRemote){
            subturretrotationYaw = (float) subTurret.turretrotationYaw;
            subturretrotationPitch = (float) subTurret.turretrotationPitch;
        }else {
            subTurret.turretrotationYaw = subturretrotationYaw;
            subTurret.turretrotationPitch = subturretrotationPitch;
        }
    }
}
