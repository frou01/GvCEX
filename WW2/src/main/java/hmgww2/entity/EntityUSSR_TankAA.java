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
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import javax.vecmath.Vector3d;

public class EntityUSSR_TankAA extends EntityUSSR_TankBase
{
	// public int type;
	
    public EntityUSSR_TankAA(World par1World)
    {
        super(par1World);
        maxHealth = 150;
        this.setSize(4F, 2.5F);
        baseLogic = new TankBaseLogic(this,0.5f,2.0f,false,"gvcmob:gvcmob.T34Track");
        playerpos = new Vector3d(-0.8,3.2D,-0.3);
        zoomingplayerpos = new Vector3d(-0.8,3.2D,-0.3);
        cannonpos = new Vector3d(0,2.65F,-0.8f);
        turretpos = new Vector3d(0,0,1.7f);
        mainTurret = new TurretObj(worldObj);
        {
            mainTurret.onmotherPos = turretpos;
            mainTurret.cannonpos = cannonpos;
            mainTurret.turretspeedY = 5;
            mainTurret.turretspeedP = 8;
            mainTurret.turretanglelimtPitchMax = 5;
            mainTurret.turretanglelimtPitchmin = -80;
            mainTurret.traverseSound = null;
            mainTurret.currentEntity = this;
            mainTurret.powor = 15;
            mainTurret.ex = 1.0F;
            mainTurret.cycle_setting = 3;
            mainTurret.firesound = "hmgww2:hmgww2.fire_30mm";
            mainTurret.spread = 1;
            mainTurret.speed = 16;
            mainTurret.magazinerem = 20;
            mainTurret.magazineMax = 20;
            mainTurret.reloadSetting = 20;
            mainTurret.canex = true;
            mainTurret.guntype = 2;
        }
        subTurret = null;
    
        turrets = new TurretObj[]{mainTurret};
        armor = 6;
    }
    protected void applyEntityAttributes()
    {
        maxHealth = 50;
        super.applyEntityAttributes();
    }
}
