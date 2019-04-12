package hmgww2.entity;


import hmggvcmob.ai.AITankAttack;
import hmggvcmob.entity.GVCEx;
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

public class EntityGER_Tank extends EntityGER_TankBase
{
	// public int type;
	
    public EntityGER_Tank(World par1World)
    {
        super(par1World);
        this.setSize(4F, 2.5F);
        baseLogic = new TankBaseLogic(this,0.8f,2.5f,false,"gvcmob:gvcmob.T34Track");
        aiTankAttack = new AITankAttack(this,6400,1600,10,10);
        this.tasks.addTask(1,aiTankAttack);
        playerpos = new Vector3d(0,3.4D,0.7);
        zoomingplayerpos = new Vector3d(0.55,2.35D,-0.80);
        subturretpos = new Vector3d(0,2.9,0.7);
        cannonpos = new Vector3d(0,2.0,-1.00F);
        turretpos = new Vector3d(0,0,0);
        mainTurret = new TurretObj(worldObj);
        {
            mainTurret.onmotherPos = turretpos;
            mainTurret.cannonpos = cannonpos;
            mainTurret.turretspeedY = 3;
            mainTurret.turretspeedP = 3;
            mainTurret.currentEntity = this;
            mainTurret.powor = 90;
            mainTurret.ex = 2.0F;
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
        
            subTurret.onmotherPos = subturretpos;
            subTurret.turretPitchCenterpos = new Vector3d(0,0.25,-1.2);
            subTurret.cannonpos = new Vector3d(0,0.285,-1.2);
            subTurret.cycle_setting = 0;
            subTurret.spread = 5;
            subTurret.speed = 8;
            subTurret.firesound = "handmadeguns:handmadeguns.MG34";
            subTurret.flushName  = "arrow";
            subTurret.flushfuse  = 1;
            subTurret.flushscale  = 1.5f;
        
            subTurret.powor = 8;
            subTurret.ex = 0;
            subTurret.canex = false;
            subTurret.guntype = 0;
        
            subTurret.magazineMax = 500;
            subTurret.reloadSetting = 100;
            subTurret.flushoffset = 0.5f;
        }
        subturret_is_mainTurret_child = true;
        mainTurret.addchild(subTurret);
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
