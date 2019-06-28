package hmgww2.entity;


import hmggvcmob.ai.AITankAttack;
import hmgww2.entity.ai.AITankBombEnemyFlag;
import hmvehicle.entity.parts.logics.TankBaseLogic;
import hmvehicle.entity.parts.turrets.TurretObj;
import net.minecraft.world.World;

import javax.vecmath.Vector3d;

public class EntityGER_TankSPG extends EntityGER_TankBase
{
	// public int type;
    
    public EntityGER_TankSPG(World par1World)
    {
        super(par1World);
        this.setSize(4F, 2.5F);
        baseLogic = new TankBaseLogic(this,0.14f,0.5f,false,"hmgww2:hmgww2.PzIVTrack");
        AITankBombEnemyFlag tankBombEnemyFlag = new AITankBombEnemyFlag(this);
        this.tasks.addTask(1,tankBombEnemyFlag);
        aiTankAttack = new AITankAttack(this,6400,400,10,10);
        aiTankAttack.setAlways_poit_to_target(true);
        baseLogic.always_poit_to_target = true;
        this.tasks.addTask(2,aiTankAttack);
        playerpos = new Vector3d(0.7,2.85,1.6);
        seat_onTurret = false;
        zoomingplayerpos = new Vector3d(0.3,2.70,0.6);
        cannonpos = new Vector3d(0,0.15,-2.00F);
        turretpos = new Vector3d(0F,2.1500f, 0.200f);
        mainTurret = new TurretObj(worldObj);
        {
            mainTurret.onMotherPos = new Vector3d(0F,2.1500f, -0.200f);
            mainTurret.cannonPos = cannonpos;
            mainTurret.turretPitchCenterpos = new Vector3d(0,0,0);
            mainTurret.turretspeedY = 1;
            mainTurret.turretspeedP = 0.5;
            mainTurret.currentEntity = this;
            mainTurret.powor = 500;
            mainTurret.cycle_setting = 400;
            mainTurret.ex = 3.0F;
            mainTurret.firesound = "hmgww2:hmgww2.10.5cmLefh";
            mainTurret.spread = 2;
            mainTurret.speed = 4;
            mainTurret.canex = true;
            mainTurret.guntype = 2;
            mainTurret.turretanglelimtYawMax = 15;
            mainTurret.turretanglelimtYawmin = -15;
            mainTurret.turretanglelimtPitchMax = 15;
            mainTurret.turretanglelimtPitchmin = -65;
        }
        
        turrets = new TurretObj[]{mainTurret};
        armor = 20;
        armor_Top_cof = 0.4f;
        armor_Side_cof = 0.8f;
        armor_Back_cof = 0.7f;
    }
    protected void applyEntityAttributes()
    {
        maxHealth = 200;
        super.applyEntityAttributes();
    }
}
