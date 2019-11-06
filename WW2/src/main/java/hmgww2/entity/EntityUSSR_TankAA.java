package hmgww2.entity;


import handmadevehicle.entity.parts.logics.TankBaseLogicLogic;
import handmadevehicle.entity.parts.turrets.TurretObj;
import net.minecraft.world.World;

import javax.vecmath.Vector3d;

public class EntityUSSR_TankAA extends EntityUSSR_TankBase
{
	// public int type;
	
    public EntityUSSR_TankAA(World par1World)
    {
        super(par1World);
        this.setSize(4F, 2.5F);
        baseLogic = new TankBaseLogicLogic(this,0.14f,0.7f,false,"gvcmob:gvcmob.JeepWheel");
        aiTankAttack = new AITankAttack(this,6400,1600,10,10);
        this.tasks.addTask(1,aiTankAttack);
        playerpos = new Vector3d(-0.8,3.2D,0.3);
        zoomingplayerpos = new Vector3d(-0.8,3.2D,0.3);
        cannonpos = new Vector3d(0,2.85F,0);
        turretpos = new Vector3d(0,0,0.7);
        mainTurret = new TurretObj(worldObj);
        {
            mainTurret.onMotherPos = turretpos;
            mainTurret.cannonPos = cannonpos;
            mainTurret.turretPitchCenterpos = new Vector3d(0F, 2.85F, 0f);
            mainTurret.turretspeedY = 5;
            mainTurret.turretspeedP = 8;
            mainTurret.turretanglelimtPitchMax = 5;
            mainTurret.turretanglelimtPitchmin = -80;
            mainTurret.traverseSound = null;
            mainTurret.currentEntity = this;
            mainTurret.powor = 50;
            mainTurret.ex = 1.0F;
            mainTurret.canex = false;
            mainTurret.cycle_setting = 4;
            mainTurret.firesound = "hmgww2:hmgww2.fire_30mm";
            mainTurret.spread = 7;
            mainTurret.speed = 4;
            mainTurret.magazinerem = -1;
            mainTurret.magazineMax = -1;
            mainTurret.reloadSetting = -1;
            mainTurret.guntype = 2;
        }
        subTurret = null;
    
        turrets = new TurretObj[]{mainTurret};
        armor = 6;
    }
    protected void applyEntityAttributes()
    {
        maxHealth = 250;
        super.applyEntityAttributes();
    }
}
