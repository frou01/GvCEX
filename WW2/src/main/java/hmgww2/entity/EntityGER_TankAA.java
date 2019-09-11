package hmgww2.entity;


import handmadevehicle.entity.parts.logics.TankBaseLogicLogic;
import hmggvcmob.ai.AITankAttack;
import handmadevehicle.entity.parts.turrets.TurretObj;
import net.minecraft.world.World;

import javax.vecmath.Vector3d;

public class EntityGER_TankAA extends EntityGER_TankBase
{
	// public int type;
	
    public EntityGER_TankAA(World par1World) {
        super(par1World);
        maxHealth = 150;
        this.setSize(4F, 2.5F);
        baseLogic = new TankBaseLogicLogic(this,0.16f,0.7f, false, "gvcmob:gvcmob.JeepWheel");
        aiTankAttack = new AITankAttack(this, 6400, 1600, 10, 10);
        this.tasks.addTask(1, aiTankAttack);
        playerpos = new Vector3d(0, 2.6521, 3.394-2.3059f);
        zoomingplayerpos = new Vector3d(0, 2.6521, 3.394-2.3059f);
        cannonpos = new Vector3d(0,2.100,3);
        turretpos = new Vector3d(0, 0, 2.3059f);
        {
            TurretObj cannon1 = new TurretObj(worldObj);
            cannon1.onMotherPos = new Vector3d(0, 0, 2.30f);
            cannon1.cannonPos = new Vector3d(0,2,0.7);
            cannon1.multicannonPos = new Vector3d[4];
            cannon1.multicannonPos[0] = new Vector3d(0.35,2.150,0.7);
            cannon1.multicannonPos[1] = new Vector3d(-0.35,2.150,0.7);
            cannon1.multicannonPos[2] = new Vector3d(0.35,1.750,0.7);
            cannon1.multicannonPos[3] = new Vector3d(-0.35,1.750,0.7);
            cannon1.turretPitchCenterpos = new Vector3d(0F, 2f,0);
            cannon1.turretspeedY = 5;
            cannon1.turretspeedP = 8;
            cannon1.turretanglelimtPitchMax = 5;
            cannon1.turretanglelimtPitchmin = -80;
            cannon1.traverseSound = null;
            cannon1.currentEntity = this;
            cannon1.powor = 25;
            cannon1.ex = 1.0F;
            cannon1.canex = false;
            cannon1.cycle_setting = 1;
            cannon1.firesound = "hmgww2:hmgww2.20mmfire";
            cannon1.spread = 15;
            cannon1.speed = 6;
            cannon1.magazinerem = 80;
            cannon1.magazineMax = 80;
            cannon1.reloadSetting = 100;
            cannon1.guntype = 2;
            cannon1.flashoffset = 2;
            cannon1.syncTurretAngle = true;
            cannon1.fireAll_child = false;
            mainTurret = cannon1;
        }

//        {
//            TurretObj cannon2 = new TurretObj(worldObj);
//            cannon2.onMotherPos = new Vector3d(0, 0, 2.30f);
//            cannon2.cannonPos = new Vector3d(-0.35,2.150,0.7);
//            cannon2.turretPitchCenterpos = new Vector3d(0F, 2f,0);
//            cannon2.turretspeedY = 5;
//            cannon2.turretspeedP = 8;
//            cannon2.turretanglelimtPitchMax = 5;
//            cannon2.turretanglelimtPitchmin = -80;
//            cannon2.traverseSound = null;
//            cannon2.currentEntity = this;
//            cannon2.powor = 27;
//            cannon2.ex = 1.0F;
//            cannon2.canex = false;
//            cannon2.cycle_setting = 3;
//            cannon2.firesound = "hmgww2:hmgww2.20mmfire";
//            cannon2.spread = 5;
//            cannon2.speed = 6;
//            cannon2.magazinerem = 20;
//            cannon2.magazineMax = 20;
//            cannon2.reloadSetting = 30;
//            cannon2.guntype = 2;
//            cannon2.flashoffset = 2;
//            cannon2.syncTurretAngle = true;
//            cannon2.fireAll = false;
//            mainTurret.addbrother(cannon2);
//        }
//        {
//            TurretObj cannon3 = new TurretObj(worldObj);
//            cannon3.onMotherPos = new Vector3d(0, 0, 2.30f);
//            cannon3.cannonPos = new Vector3d(0.35,1.750,0.7);
//            cannon3.turretPitchCenterpos = new Vector3d(0F, 2f,0);
//            cannon3.turretspeedY = 5;
//            cannon3.turretspeedP = 8;
//            cannon3.turretanglelimtPitchMax = 5;
//            cannon3.turretanglelimtPitchmin = -80;
//            cannon3.traverseSound = null;
//            cannon3.currentEntity = this;
//            cannon3.powor = 27;
//            cannon3.ex = 1.0F;
//            cannon3.canex = false;
//            cannon3.cycle_setting = 3;
//            cannon3.firesound = "hmgww2:hmgww2.20mmfire";
//            cannon3.spread = 5;
//            cannon3.speed = 6;
//            cannon3.magazinerem = 20;
//            cannon3.magazineMax = 20;
//            cannon3.reloadSetting = 30;
//            cannon3.guntype = 2;
//            cannon3.flashoffset = 2;
//            cannon3.syncTurretAngle = true;
//            cannon3.fireAll = false;
//            mainTurret.addbrother(cannon3);
//        }
//        {
//            TurretObj cannon4 = new TurretObj(worldObj);
//            cannon4.onMotherPos = new Vector3d(0, 0, 2.30f);
//            cannon4.cannonPos = new Vector3d(-0.35,1.750,0.7);
//            cannon4.turretPitchCenterpos = new Vector3d(0F, 2f,0);
//            cannon4.turretspeedY = 5;
//            cannon4.turretspeedP = 8;
//            cannon4.turretanglelimtPitchMax = 5;
//            cannon4.turretanglelimtPitchmin = -80;
//            cannon4.traverseSound = null;
//            cannon4.currentEntity = this;
//            cannon4.powor = 27;
//            cannon4.ex = 1.0F;
//            cannon4.canex = false;
//            cannon4.cycle_setting = 3;
//            cannon4.firesound = "hmgww2:hmgww2.20mmfire";
//            cannon4.spread = 5;
//            cannon4.speed = 6;
//            cannon4.magazinerem = 20;
//            cannon4.magazineMax = 20;
//            cannon4.reloadSetting = 30;
//            cannon4.guntype = 2;
//            cannon4.flashoffset = 2;
//            cannon4.syncTurretAngle = true;
//            cannon4.fireAll = false;
//            mainTurret.addbrother(cannon4);
//        }
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
