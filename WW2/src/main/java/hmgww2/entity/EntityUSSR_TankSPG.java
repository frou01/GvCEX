package hmgww2.entity;


import hmggvcmob.ai.AITankAttack;
import hmgww2.entity.ai.AITankBombEnemyFlag;
import handmadevehicle.entity.parts.logics.TankBaseLogic;
import handmadevehicle.entity.parts.turrets.TurretObj;
import net.minecraft.world.World;

import javax.vecmath.Vector3d;

public class EntityUSSR_TankSPG extends EntityUSSR_TankBase
{
	// public int type;
	
    public EntityUSSR_TankSPG(World par1World)
    {
        super(par1World);
        this.setSize(4F, 2.5F);
        baseLogic = new TankBaseLogic(this,0.14f,0.7f,false,"gvcmob:gvcmob.T34Track");
        AITankBombEnemyFlag tankBombEnemyFlag = new AITankBombEnemyFlag(this);
        this.tasks.addTask(1,tankBombEnemyFlag);
        aiTankAttack = new AITankAttack(this,6400,1600,10,10);
        aiTankAttack.setAlways_poit_to_target(true);
        baseLogic.always_point_to_target = true;
        this.tasks.addTask(2,aiTankAttack);
        playerpos = new Vector3d(0,3.2,2.5);
        zoomingplayerpos = new Vector3d(0,3.2,2.5);
        cannonpos = new Vector3d(0,2.65F,0.0f);
        turretpos = new Vector3d(0,0,1.7f);
        mainTurret = new TurretObj(worldObj);
        {
            mainTurret.onMotherPos = turretpos;
            mainTurret.cannonPos = cannonpos;
            mainTurret.turretPitchCenterpos = new Vector3d(0,1.6,0.4);
            mainTurret.turretspeedY = 0.5;
            mainTurret.turretspeedP = 0.5;
            mainTurret.turretanglelimtPitchMax = -10;
            mainTurret.turretanglelimtPitchmin = -40;
            mainTurret.traverseSound = null;
            mainTurret.currentEntity = this;
            mainTurret.powor = 15;
            mainTurret.ex = 1.0F;
            mainTurret.cycle_setting = 3;
            mainTurret.flashName = null;
            mainTurret.firesound = "hmgww2:hmgww2.katyusha";
            mainTurret.bulletmodel = "byfrou01_Rocket";
            mainTurret.spread = 10;
            mainTurret.speed = 3;
            mainTurret.canex = true;
            mainTurret.magazinerem = 20;
            mainTurret.magazineMax = 20;
            mainTurret.reloadSetting = 1200;
            mainTurret.guntype = 3;
            mainTurret.elevationType = 0;
        }
        subTurret = null;
    
        turrets = new TurretObj[]{mainTurret};
        armor = 0;
    }
    protected void applyEntityAttributes()
    {
        maxHealth = 50;
        super.applyEntityAttributes();
    }
}
