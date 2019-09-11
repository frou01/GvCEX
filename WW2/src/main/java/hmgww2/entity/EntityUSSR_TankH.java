package hmgww2.entity;


import hmggvcmob.ai.AITankAttack;
import handmadevehicle.entity.parts.logics.TankBaseLogicLogic;
import handmadevehicle.entity.parts.turrets.TurretObj;
import net.minecraft.world.World;

import javax.vecmath.Vector3d;

public class EntityUSSR_TankH extends EntityUSSR_TankBase
{
	// public int type;
	
    public EntityUSSR_TankH(World par1World)
    {
        super(par1World);
        this.setSize(4F, 2.5F);
        baseLogic = new TankBaseLogicLogic(this,0.06f,0.12f,false,"hmgww2:hmgww2.Sov_HTTrack");
        aiTankAttack = new AITankAttack(this,6400,400,10,10);
        this.tasks.addTask(1,aiTankAttack);
        playerpos = new Vector3d(0,3.4,0);
        zoomingplayerpos = new Vector3d(0.26f,2.65D,-1.7);
        subturretpos = new Vector3d(0.4747,1.260,-2.235);
        cannonpos = new Vector3d(0,2.70,-2.00F);
        turretpos = new Vector3d(0,0,0);
        mainTurret = new TurretObj(worldObj);
        {
            mainTurret.onMotherPos = turretpos;
            mainTurret.cannonPos = cannonpos;
            mainTurret.turretPitchCenterpos = new Vector3d(0,2.65,1.2);
            mainTurret.turretspeedY = 1;
            mainTurret.turretspeedP = 1;
            mainTurret.currentEntity = this;
            mainTurret.powor = 500;
            mainTurret.cycle_setting = 400;
            mainTurret.reloadsound = "hmgww2:hmgww2.ML-20DeloadAndReload";
            mainTurret.ex = 3.0F;
            mainTurret.firesound = "hmgww2:hmgww2.152mmhowit";
            mainTurret.spread = 2;
            mainTurret.speed = 4;
            mainTurret.canex = true;
            mainTurret.guntype = 2;
        }
        subTurret = new TurretObj(worldObj);
        {
            subTurret.currentEntity = this;
            subTurret.turretanglelimtPitchmin = -20;
            subTurret.turretanglelimtPitchMax = 20;
            subTurret.turretanglelimtYawmin = -20;
            subTurret.turretanglelimtYawMax = 20;
            subTurret.turretspeedY = 8;
            subTurret.turretspeedP = 10;
            subTurret.traverseSound = null;
        
            subTurret.onMotherPos = subturretpos;
            subTurret.cycle_setting = 1;
            subTurret.spread = 5;
            subTurret.speed = 8;
            subTurret.firesound = "handmadeguns:handmadeguns.fire";
            subTurret.flashName = "arrow";
            subTurret.flashfuse = 1;
            subTurret.flashscale = 1.5f;
        
            subTurret.powor = 8;
            subTurret.ex = 0;
            subTurret.canex = false;
            subTurret.guntype = 0;
        
            subTurret.magazineMax = 47;
            subTurret.reloadSetting = 100;
            subTurret.flashoffset = 0.5f;
        }
    
        turrets = new TurretObj[]{mainTurret,subTurret};
        armor = 50;
        armor_Top_cof = 0.4f;
        armor_Side_cof = 0.8f;
        armor_Back_cof = 0.7f;
    }
    protected void applyEntityAttributes()
    {
        maxHealth = 1000;
        super.applyEntityAttributes();
    }
}
