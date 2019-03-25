package hmgww2.entity;


import hmggvcmob.entity.*;
import hmgww2.mod_GVCWW2;
import hmgww2.network.WW2MessageKeyPressed;
import hmgww2.network.WW2PacketHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;

public class EntityGER_FighterA extends EntityGER_FighterBase
{
	// public int type;
	
	public EntityGER_FighterA(World par1World)
	{
		super(par1World);
		this.setSize(5f, 3f);
		this.maxhealth = 500;
//		nboundingbox = new ModifiedBoundingBox(-20,-20,-20,20,20,20,0,0,-6.27,2.5,5,19);
//		nboundingbox.rot.set(this.bodyRot);
//		proxy.replaceBoundingbox(this,nboundingbox);
//		((ModifiedBoundingBox)this.boundingBox).updateOBB(this.posX,this.posY,this.posZ);
		ignoreFrustumCheck = true;
		this.fireCycle1 = 1;
		baseLogic.speedfactor = 0.003f;
		baseLogic.throttle_gearDown = 1.7f;
		baseLogic.throttle_Max = 5.0f;
		baseLogic.liftfactor = 0.1f;
		baseLogic.dragfactor = 0.11f;
		baseLogic.rollspeed = 0.3f;
		baseLogic.pitchspeed = 0.3f;
		baseLogic.yawspeed = 0.2f;
		baseLogic.stability = 1000;
		baseLogic.maxDive = 60;
		baseLogic.maxClimb = -60;
		baseLogic.maxbank = 20;
		baseLogic.soundname = "hmgww2:hmgww2.sound_pera";
		
		baseLogic.camerapos = new double[]{0,2.3,0};
		baseLogic.rotcenter = new double[]{0,1.5,0};
		
		baseLogic.onground_pitch = -10;
		TurretObj cannon3 = new TurretObj(worldObj);
		{
			{
				cannon3.onmotherPos = new Vector3d(-1.25,0.95,0);
				cannon3.motherRotCenter = new Vector3d(0,0,0);
				cannon3.cannonpos = new Vector3d(0,2.15,-2);
				cannon3.turretspeedY = 5;
				cannon3.turretspeedP = 8;
				cannon3.turretanglelimtPitchMax = 5;
				cannon3.turretanglelimtPitchmin = -80;
				cannon3.traverseSound = null;
				cannon3.currentEntity = this;
				cannon3.powor = 8;
				cannon3.ex = 0.5f;
				cannon3.cycle_setting = 0;
				cannon3.firesound = "handmadeguns:handmadeguns.fire";
				cannon3.spread = 5;
				cannon3.speed = 16;
				cannon3.magazineMax = 100;
				cannon3.magazinerem = 100;
				cannon3.reloadTimer = 70;
				cannon3.canex = true;
				cannon3.guntype = 0;
			}
			{
				TurretObj cannon4 = new TurretObj(worldObj);
				cannon4.onmotherPos = new Vector3d(-1.45,0.95,0);
				cannon4.motherRotCenter = new Vector3d(0,0,0);
				cannon4.cannonpos = new Vector3d(0,2.15,-2);
				cannon4.turretspeedY = 5;
				cannon4.turretspeedP = 8;
				cannon4.turretanglelimtPitchMax = 5;
				cannon4.turretanglelimtPitchmin = -80;
				cannon4.traverseSound = null;
				cannon4.currentEntity = this;
				cannon4.powor = 8;
				cannon4.ex = 0.5f;
				cannon4.cycle_setting = 0;
				cannon4.firesound = "handmadeguns:handmadeguns.fire";
				cannon4.spread = 5;
				cannon4.speed = 16;
				cannon4.magazineMax = 100;
				cannon4.magazinerem = 100;
				cannon4.reloadTimer = 70;
				cannon4.canex = true;
				cannon4.guntype = 0;
				cannon3.addchild(cannon4);
			}
		}
		baseLogic.mainTurret = cannon3;
		baseLogic.subTurret = new TurretObj(worldObj);
		{
			baseLogic.subTurret.onmotherPos = new Vector3d(0,-0.1,-2);
			baseLogic.subTurret.motherRotCenter = new Vector3d(0,1.2,0);
			baseLogic.subTurret.cannonpos = new Vector3d(0,-0.1,-2);
			baseLogic.subTurret.turretspeedY = 5;
			baseLogic.subTurret.turretspeedP = 8;
			baseLogic.subTurret.turretanglelimtPitchMax = 5;
			baseLogic.subTurret.turretanglelimtPitchmin = -80;
			baseLogic.subTurret.traverseSound = null;
			baseLogic.subTurret.currentEntity = this;
			baseLogic.subTurret.powor = 100;
			baseLogic.subTurret.ex = 8.0F;
			baseLogic.subTurret.cycle_setting = 2;
			baseLogic.subTurret.firesound = null;
			baseLogic.subTurret.bulletmodel = "byfrou01_Bomb";
			baseLogic.subTurret.flushName = null;
			baseLogic.subTurret.spread = 0;
			baseLogic.subTurret.speed = 0;
			baseLogic.subTurret.canex = true;
			baseLogic.subTurret.guntype = 2;
			baseLogic.subTurret.magazineMax = 2;
			baseLogic.subTurret.magazinerem = 2;
			baseLogic.subTurret.reloadSetting = 500;
		}
	}
}
