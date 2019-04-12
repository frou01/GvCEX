package hmgww2.entity;


import java.util.Calendar;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hmggvcmob.GVCMobPlus;
import hmggvcmob.ai.AITankAttack;
import hmggvcmob.entity.*;
import hmgww2.mod_GVCWW2;
import hmgww2.blocks.BlockFlagBase;
import hmgww2.blocks.BlockJPNFlagBase;
import hmgww2.network.WW2MessageKeyPressed;
import hmgww2.network.WW2PacketHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIRestrictSun;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.biome.BiomeGenBase;

import javax.vecmath.Vector3d;

public class EntityJPN_ShipD extends EntityJPN_ShipBase
{
	// public int type;
	
	public EntityJPN_ShipD(World par1World) {
		super(par1World);
		this.setSize(7, 10);
		ignoreFrustumCheck = true;
		renderDistanceWeight = Double.MAX_VALUE;
		
		nboundingbox = new ModifiedBoundingBox(-100,-100,-100,100,100,100,
				                                      0,5,-10.53,
				                                      10,13,82);
		nboundingbox.rot.set(baseLogic.bodyRot);
		GVCMobPlus.proxy.replaceBoundingbox(this,nboundingbox);
		nboundingbox.centerRotX = 0;
		nboundingbox.centerRotY = 0;
		nboundingbox.centerRotZ = 0;
		
		baseLogic = new VesselBaseLogic(this,0.03f,4.0f,false,"hmgww2:hmgww2.VesselTurbine");
		baseLogic.canControlonWater = true;
		aiTankAttack = new AITankAttack(this,3600,900,10,10);
		aiTankAttack.setAlways_movearound(true);
		this.tasks.addTask(1,aiTankAttack);
		playerpos = new Vector3d(0,19.02,-13.32);
		zoomingplayerpos = new Vector3d(-0.8,3.2D,-0.3);
		cannonpos = new Vector3d(0,0.7324,-1.872);
		turretpos = new Vector3d(0,7.9753,-27.07);
		TurretObj mainTurret1 = new TurretObj(worldObj);
		{
			mainTurret1.onmotherPos = turretpos;
			mainTurret1.cannonpos = cannonpos;
			mainTurret1.turretPitchCenterpos = new Vector3d(0,0.7329,0.01402);
			mainTurret1.turretspeedY = 5;
			mainTurret1.turretspeedP = 8;
			mainTurret1.turretanglelimtPitchMax = 5;
			mainTurret1.turretanglelimtPitchmin = -80;
			mainTurret1.traverseSound = null;
			mainTurret1.currentEntity = this;
			mainTurret1.powor = 15;
			mainTurret1.ex = 1.0F;
			mainTurret1.cycle_setting = 120;
			mainTurret1.firesound = "hmgww2:hmgww2.12.7cmFire";
			mainTurret1.spread = 1;
			mainTurret1.speed = 8;
			mainTurret1.canex = true;
			mainTurret1.guntype = 2;
			mainTurret1.fireRists = new FireRist[]{new FireRist(160,-160,90,-90)};
		}
		turretpos = new Vector3d(0,5.8224,9.864);
		TurretObj mainTurret2 = new TurretObj(worldObj);
		{
			mainTurret2.onmotherPos = turretpos;
			mainTurret2.cannonpos = cannonpos;
			mainTurret2.turretPitchCenterpos = new Vector3d(0,0.7329,0.01402);
			mainTurret2.turretspeedY = 5;
			mainTurret2.turretspeedP = 8;
			mainTurret2.turretanglelimtPitchMax = 5;
			mainTurret2.turretanglelimtPitchmin = -80;
			mainTurret2.traverseSound = null;
			mainTurret2.currentEntity = this;
			mainTurret2.powor = 15;
			mainTurret2.ex = 1.0F;
			mainTurret2.cycle_setting = 120;
			mainTurret2.firesound = "hmgww2:hmgww2.12.7cmFire";
			mainTurret2.spread = 1;
			mainTurret2.speed = 8;
			mainTurret2.canex = true;
			mainTurret2.guntype = 2;
			mainTurret2.fireRists = new FireRist[]{new FireRist(150,15,90,-90),new FireRist(-15,-150,90,-90)};
		}
		turretpos = new Vector3d(0,5.311,3.527);
		TurretObj torp1 = new TurretObj(worldObj);
		{
			torp1.onmotherPos = turretpos;
			torp1.cannonpos = cannonpos;
			torp1.turretPitchCenterpos = new Vector3d(0,0,0);
			torp1.turretspeedY = 5;
			torp1.turretspeedP = 8;
			torp1.turretanglelimtPitchMax = 0;
			torp1.turretanglelimtPitchmin = 0;
			torp1.traverseSound = null;
			torp1.currentEntity = this;
			torp1.powor = 2400;
			torp1.ex = 1.0F;
			torp1.cycle_setting = 20;
			torp1.firesound = null;
			torp1.flushName = null;
			torp1.spread = 4;
			torp1.speed = 1f;
			torp1.acceler = 0.001f;
			torp1.magazinerem = 1;
			torp1.magazineMax = 1;
			torp1.reloadSetting = 1200;
			torp1.canex = true;
			torp1.guntype = 11;
			torp1.fuse = 400;
			torp1.torpdraft = 2;
			torp1.bulletmodel = "byfrou01_torpedo";
			torp1.fireRists = new FireRist[]{new FireRist(150,30,90,-90),new FireRist(-30,-150,90,-90)};
		}
		
		turretpos = new Vector3d(-2.7768,5.311,37.6780);
		TurretObj depth_charge1 = new TurretObj(worldObj);
		{
			depth_charge1.onmotherPos = turretpos;
			depth_charge1.cannonpos = cannonpos;
			depth_charge1.turretPitchCenterpos = new Vector3d(0,0,0);
			depth_charge1.turretspeedY = 5;
			depth_charge1.turretspeedP = 8;
			depth_charge1.turretanglelimtPitchMax = 20;
			depth_charge1.turretanglelimtPitchmin = 20;
			depth_charge1.turretanglelimtYawMax = 180;
			depth_charge1.turretanglelimtYawmin = 180;
			depth_charge1.traverseSound = null;
			depth_charge1.currentEntity = this;
			depth_charge1.powor = 100;
			depth_charge1.ex = 10.0F;
			depth_charge1.cycle_setting = 5;
			depth_charge1.firesound = null;
			depth_charge1.flushName = null;
			depth_charge1.spread = 4;
			depth_charge1.speed = 0.4f;
			depth_charge1.magazinerem = 20;
			depth_charge1.magazineMax = 20;
			depth_charge1.reloadSetting = 1200;
			depth_charge1.canex = true;
			depth_charge1.guntype = 2;
			depth_charge1.fuse = 20;
			depth_charge1.bulletmodel = "byfrou01_depth_charge";
		}
		turretpos = new Vector3d(2.7768,5.311,37.6780);
		TurretObj depth_charge2 = new TurretObj(worldObj);
		{
			depth_charge2.onmotherPos = turretpos;
			depth_charge2.cannonpos = cannonpos;
			depth_charge2.turretPitchCenterpos = new Vector3d(0,0,0);
			depth_charge2.turretspeedY = 5;
			depth_charge2.turretspeedP = 8;
			depth_charge2.turretanglelimtPitchMax = 20;
			depth_charge2.turretanglelimtPitchmin = 20;
			depth_charge2.turretanglelimtYawMax = 180;
			depth_charge2.turretanglelimtYawmin = 180;
			depth_charge2.traverseSound = null;
			depth_charge2.currentEntity = this;
			depth_charge2.powor = 100;
			depth_charge2.ex = 10.0F;
			depth_charge2.cycle_setting = 5;
			depth_charge2.firesound = null;
			depth_charge2.flushName = null;
			depth_charge2.spread = 4;
			depth_charge2.speed = 0.4f;
			depth_charge2.magazinerem = 20;
			depth_charge2.magazineMax = 20;
			depth_charge2.reloadSetting = 1200;
			depth_charge2.canex = true;
			depth_charge2.guntype = 2;
			depth_charge2.fuse = 20;
			depth_charge2.bulletmodel = "byfrou01_depth_charge";
		}
		
		turrets = new TurretObj[]{mainTurret1,mainTurret2,torp1,depth_charge1,depth_charge2};
		mainturrets = new TurretObj[]{mainTurret1,mainTurret2};
		subturrets = new TurretObj[]{torp1};
		SPturrets = new TurretObj[]{depth_charge1,depth_charge2};
		armor = 12;
	}
	protected void applyEntityAttributes()
	{
		maxHealth = 2000;
		super.applyEntityAttributes();
	}
	
	@Override
	public TurretObj[] getmotherTurrets() {
		return turrets;
	}
	
	@Override
	public float getthirdDist() {
		return 50;
	}
}
