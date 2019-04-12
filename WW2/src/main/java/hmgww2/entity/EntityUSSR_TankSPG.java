package hmgww2.entity;


import hmggvcmob.ai.AITankAttack;
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
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import javax.vecmath.Vector3d;

public class EntityUSSR_TankSPG extends EntityUSSR_TankBase
{
	// public int type;
	
    public EntityUSSR_TankSPG(World par1World)
    {
        super(par1World);
        this.setSize(4F, 2.5F);
        baseLogic = new TankBaseLogic(this,0.5f,2.0f,false,"gvcmob:gvcmob.T34Track");
        aiTankAttack = new AITankAttack(this,6400,1600,10,10);
        this.tasks.addTask(1,aiTankAttack);
        playerpos = new Vector3d(0,3.2,2.5);
        zoomingplayerpos = new Vector3d(0,3.2,2.5);
        cannonpos = new Vector3d(0,2.65F,-1.0f);
        turretpos = new Vector3d(0,0,1.7f);
        mainTurret = new TurretObj(worldObj);
        {
            mainTurret.onmotherPos = turretpos;
            mainTurret.cannonpos = cannonpos;
            mainTurret.turretPitchCenterpos = new Vector3d(0,1.6,0.4);
            mainTurret.turretspeedY = 5;
            mainTurret.turretspeedP = 8;
            mainTurret.turretanglelimtPitchMax = -10;
            mainTurret.turretanglelimtPitchmin = -80;
            mainTurret.traverseSound = null;
            mainTurret.currentEntity = this;
            mainTurret.powor = 15;
            mainTurret.ex = 1.0F;
            mainTurret.cycle_setting = 3;
            mainTurret.firesound = "hmgww2:hmgww2.katyusha";
            mainTurret.bulletmodel = "byfrou01_Rocket";
            mainTurret.spread = 10;
            mainTurret.speed = 16;
            mainTurret.canex = true;
            mainTurret.magazinerem = 20;
            mainTurret.magazineMax = 20;
            mainTurret.reloadSetting = 20;
            mainTurret.guntype = 2;
            mainTurret.gravity = 0.49f;
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
