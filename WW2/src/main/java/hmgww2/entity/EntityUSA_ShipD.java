package hmgww2.entity;


import java.util.Calendar;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hmggvcmob.GVCMobPlus;
import hmggvcmob.ai.AITankAttack;
import hmggvcmob.entity.FireRist;
import hmggvcmob.entity.ModifiedBoundingBox;
import hmggvcmob.entity.TurretObj;
import hmggvcmob.entity.VesselBaseLogic;
import hmgww2.mod_GVCWW2;
import hmgww2.blocks.BlockFlagBase;
import hmgww2.blocks.BlockUSAFlagBase;
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

public class EntityUSA_ShipD  extends EntityUSA_ShipBase
{
	// public int type;
	
	public EntityUSA_ShipD(World par1World)
	{
		super(par1World);
		this.setSize(7, 10);
		ignoreFrustumCheck = true;
		renderDistanceWeight = Double.MAX_VALUE;
		draft = 2.2f;
		nboundingbox = new ModifiedBoundingBox(-100,-100,-100,100,100,100,
				                                      0,5,0,
				                                      10,10,90);
		nboundingbox.rot.set(baseLogic.bodyRot);
		GVCMobPlus.proxy.replaceBoundingbox(this,nboundingbox);
		nboundingbox.centerRotX = 0;
		nboundingbox.centerRotY = 0;
		nboundingbox.centerRotZ = 0;
		
		baseLogic = new VesselBaseLogic(this,0.01f,4.0f,false,"hmgww2:hmgww2.VesselTurbine");
		baseLogic.canControlonWater = true;
		aiTankAttack = new AITankAttack(this,3600,900,10,10);
		aiTankAttack.setAlways_movearound(true);
		this.tasks.addTask(1,aiTankAttack);
		playerpos = new Vector3d(0,19.02,-13.32);
		zoomingplayerpos = new Vector3d(-0.8,3.2D,-0.3);
		cannonpos = new Vector3d(0,2.175,-3.566);
		Vector3d turretPitchCenterpos = new Vector3d(0,2.1706,0);
		turretpos = new Vector3d(0,8.5442,-33.5458);
		TurretObj mainTurret1 = new TurretObj(worldObj);
		{
			mainTurret1.onmotherPos = turretpos;
			mainTurret1.cannonpos = cannonpos;
			mainTurret1.turretPitchCenterpos = turretPitchCenterpos;
			mainTurret1.turretspeedY = 5;
			mainTurret1.turretspeedP = 8;
			mainTurret1.turretanglelimtPitchMax = 5;
			mainTurret1.turretanglelimtPitchmin = -80;
			mainTurret1.traverseSound = null;
			mainTurret1.currentEntity = this;
			mainTurret1.powor = 15;
			mainTurret1.ex = 1.0F;
			mainTurret1.cycle_setting = 60;
			mainTurret1.firesound = "hmgww2:hmgww2.75mmfire";
			mainTurret1.spread = 1;
			mainTurret1.speed = 8;
			mainTurret1.canex = true;
			mainTurret1.guntype = 2;
			mainTurret1.fireRists = new FireRist[]{new FireRist(160,-160,90,-90)};
		}
		turretpos = new Vector3d(-2.8444,10.0666,-7.5907);
		TurretObj mainTurret2 = new TurretObj(worldObj);
		{
			mainTurret2.onmotherPos = turretpos;
			mainTurret2.cannonpos = cannonpos;
			mainTurret2.turretPitchCenterpos = turretPitchCenterpos;
			mainTurret2.turretspeedY = 5;
			mainTurret2.turretspeedP = 8;
			mainTurret2.turretanglelimtPitchMax = 5;
			mainTurret2.turretanglelimtPitchmin = -80;
			mainTurret2.traverseSound = null;
			mainTurret2.currentEntity = this;
			mainTurret2.powor = 15;
			mainTurret2.ex = 1.0F;
			mainTurret2.cycle_setting = 60;
			mainTurret2.firesound = "hmgww2:hmgww2.75mmfire";
			mainTurret2.spread = 1;
			mainTurret2.speed = 8;
			mainTurret2.canex = true;
			mainTurret2.guntype = 2;
			mainTurret2.fireRists = new FireRist[]{new FireRist(180,10,90,-90)};
		}
		turretpos = new Vector3d(2.8444,10.0666,-7.5907);
		TurretObj mainTurret3 = new TurretObj(worldObj);
		{
			mainTurret3.onmotherPos = turretpos;
			mainTurret3.cannonpos = cannonpos;
			mainTurret3.turretPitchCenterpos = turretPitchCenterpos;
			mainTurret3.turretspeedY = 5;
			mainTurret3.turretspeedP = 8;
			mainTurret3.turretanglelimtPitchMax = 5;
			mainTurret3.turretanglelimtPitchmin = -80;
			mainTurret3.traverseSound = null;
			mainTurret3.currentEntity = this;
			mainTurret3.powor = 15;
			mainTurret3.ex = 1.0F;
			mainTurret3.cycle_setting = 60;
			mainTurret3.firesound = "hmgww2:hmgww2.75mmfire";
			mainTurret3.spread = 1;
			mainTurret3.speed = 8;
			mainTurret3.canex = true;
			mainTurret3.guntype = 2;
			mainTurret3.fireRists = new FireRist[]{new FireRist(-10,-180,90,-90)};
		}
		turretpos = new Vector3d(-2.9801,6.5867,20.3231);
		TurretObj mainTurret4 = new TurretObj(worldObj);
		{
			mainTurret4.onmotherPos = turretpos;
			mainTurret4.cannonpos = cannonpos;
			mainTurret4.turretPitchCenterpos = turretPitchCenterpos;
			mainTurret4.turretspeedY = 5;
			mainTurret4.turretspeedP = 8;
			mainTurret4.turretanglelimtPitchMax = 5;
			mainTurret4.turretanglelimtPitchmin = -80;
			mainTurret4.traverseSound = null;
			mainTurret4.currentEntity = this;
			mainTurret4.powor = 15;
			mainTurret4.ex = 1.0F;
			mainTurret4.cycle_setting = 60;
			mainTurret4.firesound = "hmgww2:hmgww2.75mmfire";
			mainTurret4.spread = 1;
			mainTurret4.speed = 8;
			mainTurret4.canex = true;
			mainTurret4.guntype = 2;
			mainTurret4.fireRists = new FireRist[]{new FireRist(180,20,90,-90)};
		}
		turretpos = new Vector3d(2.7055,6.5867,24.6199);
		TurretObj mainTurret5 = new TurretObj(worldObj);
		{
			mainTurret5.onmotherPos = turretpos;
			mainTurret5.cannonpos = cannonpos;
			mainTurret5.turretPitchCenterpos = turretPitchCenterpos;
			mainTurret5.turretspeedY = 5;
			mainTurret5.turretspeedP = 8;
			mainTurret5.turretanglelimtPitchMax = 5;
			mainTurret5.turretanglelimtPitchmin = -80;
			mainTurret5.traverseSound = null;
			mainTurret5.currentEntity = this;
			mainTurret5.powor = 15;
			mainTurret5.ex = 1.0F;
			mainTurret5.cycle_setting = 60;
			mainTurret5.firesound = "hmgww2:hmgww2.75mmfire";
			mainTurret5.spread = 1;
			mainTurret5.speed = 8;
			mainTurret5.canex = true;
			mainTurret5.guntype = 2;
			mainTurret5.fireRists = new FireRist[]{new FireRist(-20,-180,90,-90)};
		}
		turretpos = new Vector3d(0,8.5442,32.9060);
		TurretObj mainTurret6 = new TurretObj(worldObj);
		{
			mainTurret6.onmotherPos = turretpos;
			mainTurret6.cannonpos = cannonpos;
			mainTurret6.turretPitchCenterpos = turretPitchCenterpos;
			mainTurret6.turretspeedY = 5;
			mainTurret6.turretspeedP = 8;
			mainTurret6.turretanglelimtPitchMax = 5;
			mainTurret6.turretanglelimtPitchmin = -80;
			mainTurret6.traverseSound = null;
			mainTurret6.currentEntity = this;
			mainTurret6.powor = 15;
			mainTurret6.ex = 1.0F;
			mainTurret6.cycle_setting = 60;
			mainTurret6.firesound = "hmgww2:hmgww2.75mmfire";
			mainTurret6.spread = 1;
			mainTurret6.speed = 8;
			mainTurret6.canex = true;
			mainTurret6.guntype = 2;
			mainTurret6.fireRists = new FireRist[]{new FireRist(-20,-180,90,-90),new FireRist(180,20,90,-90)};
		}
		turretpos = new Vector3d(-3.5077,7.1202,4.2273);
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
			torp1.powor = 800;
			torp1.ex = 1.0F;
			torp1.cycle_setting = 20;
			torp1.firesound = null;
			torp1.flushName = null;
			torp1.spread = 4;
			torp1.speed = 1f;
			torp1.acceler = 0.001f;
			torp1.magazinerem = 3;
			torp1.magazineMax = 3;
			torp1.reloadSetting = -1;
			torp1.canex = true;
			torp1.guntype = 11;
			torp1.fuse = 400;
			torp1.torpdraft = 2;
			torp1.bulletmodel = "byfrou01_torpedo";
			torp1.fireRists = new FireRist[]{new FireRist(-10,-180,90,-90)};
		}
		turretpos = new Vector3d(3.2205,7.1202,8.5345);
		TurretObj torp2 = new TurretObj(worldObj);
		{
			torp2.onmotherPos = turretpos;
			torp2.cannonpos = cannonpos;
			torp2.turretPitchCenterpos = new Vector3d(0,0,0);
			torp2.turretspeedY = 5;
			torp2.turretspeedP = 8;
			torp2.turretanglelimtPitchMax = 0;
			torp2.turretanglelimtPitchmin = 0;
			torp2.traverseSound = null;
			torp2.currentEntity = this;
			torp2.powor = 800;
			torp2.ex = 1.0F;
			torp2.cycle_setting = 20;
			torp2.firesound = null;
			torp2.flushName = null;
			torp2.spread = 4;
			torp2.speed = 1f;
			torp2.acceler = 0.001f;
			torp2.magazinerem = 3;
			torp2.magazineMax = 3;
			torp2.reloadSetting = -1;
			torp2.canex = true;
			torp2.guntype = 11;
			torp2.fuse = 400;
			torp2.torpdraft = 2;
			torp2.bulletmodel = "byfrou01_torpedo";
			torp2.fireRists = new FireRist[]{new FireRist(180,10,90,-90)};
		}
		turretpos = new Vector3d(-0.9984,7.0179,45.6628);
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
		turretpos = new Vector3d(0.9984,7.0179,45.6628);
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
		
		turrets = new TurretObj[]{mainTurret1,mainTurret2,mainTurret3,mainTurret4,mainTurret5,mainTurret6,torp1,torp2,depth_charge1,depth_charge2};
		mainturrets = new TurretObj[]{mainTurret1,mainTurret2,mainTurret3,mainTurret4,mainTurret5,mainTurret6};
		subturrets = new TurretObj[]{torp1,torp2};
		SPturrets = new TurretObj[]{depth_charge1,depth_charge2};
		armor = 12;
	}
	protected void applyEntityAttributes()
	{
		maxHealth = 3000;
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

