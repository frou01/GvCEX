package hmgww2.entity;


import handmadevehicle.entity.parts.logics.TankBaseLogicLogic;
import handmadevehicle.entity.parts.turrets.TurretObj;
import net.minecraft.world.World;

import javax.vecmath.Vector3d;

public class EntityGER_TankH extends EntityGER_TankBase
{
	
    public EntityGER_TankH(World par1World)
    {
        super(par1World);
        this.setSize(4F, 2.5F);
        baseLogic = new TankBaseLogicLogic(this,0.04f,0.1f,true,"hmgww2:hmgww2.TigerTrack");
        aiTankAttack = new AITankAttack(this,6400,1600,10,10);
        this.tasks.addTask(1,aiTankAttack);
        playerpos = new Vector3d(1,3.4D,0.7);
        zoomingplayerpos = new Vector3d(0.4837,2.186,-1.447);
        subturretpos = new Vector3d(-0.4747,1.680,-2.235);
        cannonpos = new Vector3d(0,2.191,4.674);
        turretpos = new Vector3d(0,0,0);
        mainTurret = new TurretObj(worldObj);
        {
            mainTurret.onMotherPos = turretpos;
            mainTurret.cannonPos = cannonpos;
            mainTurret.turretPitchCenterpos = new Vector3d(0,2.0,1.00F);
            mainTurret.turretspeedY = 0.5;
            mainTurret.turretspeedP = 3;
            mainTurret.currentEntity = this;
            mainTurret.powor = 240;
            mainTurret.ex = 2.0F;
            mainTurret.firesound = "hmgww2:hmgww2.88mmfire";
            mainTurret.firesoundLV = 8;
            mainTurret.firesoundPitch = 1.2f;
            mainTurret.spread = 0.5f;
            mainTurret.speed = 8;
            mainTurret.canex = true;
            mainTurret.guntype = 2;
            mainTurret.cycle_setting = 150;
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
        turrets = new TurretObj[]{mainTurret,subTurret};
        armor = 80;
        armor_Top_cof = 0.5f;
        armor_Side_cof = 0.8f;
        armor_Back_cof = 0.6f;
    }
    protected void applyEntityAttributes()
    {
        maxHealth = 800;
        super.applyEntityAttributes();
    }
}
