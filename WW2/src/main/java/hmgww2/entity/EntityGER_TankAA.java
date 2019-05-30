package hmgww2.entity;


import hmggvcmob.ai.AITankAttack;
import hmvehicle.entity.parts.logics.TankBaseLogic;
import hmvehicle.entity.parts.turrets.TurretObj;
import net.minecraft.world.World;

import javax.vecmath.Vector3d;

public class EntityGER_TankAA extends EntityGER_TankBase
{
	// public int type;
	
    public EntityGER_TankAA(World par1World) {
        super(par1World);
        maxHealth = 150;
        this.setSize(4F, 2.5F);
        baseLogic = new TankBaseLogic(this,0.16f,0.95f, false, "hmgww2:hmgww2.T34Track");
        aiTankAttack = new AITankAttack(this, 6400, 1600, 10, 10);
        this.tasks.addTask(1, aiTankAttack);
        playerpos = new Vector3d(0, 2.6521, 3.394-2.3059f);
        zoomingplayerpos = new Vector3d(0, 2.6521, 3.394-2.3059f);
        cannonpos = new Vector3d(0,2.100,3);
        turretpos = new Vector3d(0, 0, 2.3059f);
        mainTurret = new TurretObj(worldObj);
        {
            mainTurret.onmotherPos = new Vector3d(0, 0, 2.3059f);
            mainTurret.cannonpos = new Vector3d(0,2.100,3);
            mainTurret.turretPitchCenterpos = new Vector3d(0F, 2.1303f, + 2.6810f - 2.3059f);
            mainTurret.turretspeedY = 5;
            mainTurret.turretspeedP = 8;
            mainTurret.turretanglelimtPitchMax = 5;
            mainTurret.turretanglelimtPitchmin = -80;
            mainTurret.traverseSound = null;
            mainTurret.currentEntity = this;
            mainTurret.powor = 27;
            mainTurret.ex = 1.0F;
            mainTurret.canex = false;
            mainTurret.cycle_setting = 4;
            mainTurret.firesound = "hmgww2:hmgww2.fire_amr";
            mainTurret.spread = 1;
            mainTurret.speed = 6;
            mainTurret.magazinerem = 20;
            mainTurret.magazineMax = 20;
            mainTurret.reloadSetting = 20;
            mainTurret.guntype = 2;
        }
        subTurret = null;
    
        turrets = new TurretObj[]{mainTurret};
        armor = 6;
    
    }
    protected void applyEntityAttributes()
    {
        maxHealth = 150;
        super.applyEntityAttributes();
    }
}
