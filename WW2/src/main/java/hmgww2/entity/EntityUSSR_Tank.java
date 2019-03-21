package hmgww2.entity;


import hmggvcmob.entity.TankBaseLogic;
import hmggvcmob.entity.TurretObj;
import hmgww2.mod_GVCWW2;
import hmgww2.network.WW2MessageKeyPressed;
import hmgww2.network.WW2PacketHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import javax.vecmath.Vector3d;

public class EntityUSSR_Tank extends EntityUSSR_TankBase
{
	// public int type;
	
    public EntityUSSR_Tank(World par1World)
    {
        super(par1World);
        this.setSize(4F, 2.5F);
        armor = 34;
        baseLogic = new TankBaseLogic(this,0.5f,2.0f,false,"gvcmob:gvcmob.T34Track");
        playerpos = new Vector3d(-0.4,2.65D,0.0);
        zoomingplayerpos = new Vector3d(-0,2.75D,0.85);
        subturretpos = new Vector3d(-0.4747,1.260,-2.235);
        cannonpos = new Vector3d(0,2.0,-1.00F);
        turretpos = new Vector3d(0,0,0);
        mainTurret = new TurretObj(worldObj);
        {
            mainTurret.onmotherPos = turretpos;
            mainTurret.cannonpos = cannonpos;
            mainTurret.turretspeedY = 2;
            mainTurret.turretspeedP = 3;
            mainTurret.currentEntity = this;
            mainTurret.powor = 75;
            mainTurret.ex = 3.0F;
            mainTurret.firesound = "hmgww2:hmgww2.fire_cannon";
            mainTurret.spread = 1;
            mainTurret.speed = 16;
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
        
            subTurret.onmotherPos = subturretpos;
            subTurret.cycle_setting = 1;
            subTurret.spread = 5;
            subTurret.speed = 8;
            subTurret.firesound = "handmadeguns:handmadeguns.fire";
            subTurret.flushName  = "arrow";
            subTurret.flushfuse  = 1;
            subTurret.flushscale  = 1.5f;
        
            subTurret.powor = 8;
            subTurret.ex = 0;
            subTurret.canex = false;
            subTurret.guntype = 0;
        
            subTurret.magazineMax = 47;
            subTurret.reloadSetting = 100;
            subTurret.flushoffset = 0.5f;
        }
    
        turrets = new TurretObj[]{mainTurret,subTurret};
    }
    protected void applyEntityAttributes()
    {
        maxHealth = 150;
        super.applyEntityAttributes();
    }
    
}
