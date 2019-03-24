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

public class EntityJPN_Fighter extends EntityJPN_FighterBase
{
	public EntityJPN_Fighter(World par1World)
	{
		super(par1World);
		this.setSize(5f, 3f);
//		nboundingbox = new ModifiedBoundingBox(-20,-20,-20,20,20,20,0,0,-6.27,2.5,5,19);
//		nboundingbox.rot.set(this.bodyRot);
//		proxy.replaceBoundingbox(this,nboundingbox);
//		((ModifiedBoundingBox)this.boundingBox).updateOBB(this.posX,this.posY,this.posZ);
		ignoreFrustumCheck = true;
		this.fireCycle1 = 1;
		baseLogic.speedfactor = 0.02f;
		baseLogic.throttle_gearDown = 1.7f;
		baseLogic.throttle_Max = 4.0f;
		baseLogic.liftfactor = 0.1f;
		baseLogic.dragfactor = 0.08f;
		baseLogic.rollspeed = 0.3f;
		baseLogic.pitchspeed = 0.3f;
		baseLogic.yawspeed = 0.3f;
		baseLogic.stability = 1000;
		baseLogic.maxDive = 60;
		baseLogic.maxClimb = -70;
		baseLogic.soundname = "hmgww2:hmgww2.sound_pera";
		
		baseLogic.camerapos = new double[]{0,2.5,0};
		baseLogic.rotcenter = new double[]{0,1.1,0};
		
		baseLogic.onground_pitch = -10;
		baseLogic.useMain_withSub = true;
		baseLogic.mainTurret = new TurretObj(worldObj);
		{
			baseLogic.mainTurret.onmotherPos = new Vector3d(0,1.2,-2);
			baseLogic.mainTurret.motherRotCenter = new Vector3d(0,1.2,0);
			baseLogic.mainTurret.cannonpos = new Vector3d(0,1.5,-2);
			baseLogic.mainTurret.turretspeedY = 5;
			baseLogic.mainTurret.turretspeedP = 8;
			baseLogic.mainTurret.turretanglelimtPitchMax = 5;
			baseLogic.mainTurret.turretanglelimtPitchmin = -80;
			baseLogic.mainTurret.traverseSound = null;
			baseLogic.mainTurret.currentEntity = this;
			baseLogic.mainTurret.powor = 15;
			baseLogic.mainTurret.ex = 0.5f;
			baseLogic.mainTurret.cycle_setting = 1;
			baseLogic.mainTurret.firesound = "hmgww2:hmgww2.fire_30mm";
			baseLogic.mainTurret.spread = 5;
			baseLogic.mainTurret.speed = 16;
			baseLogic.mainTurret.magazineMax = 50;
			baseLogic.mainTurret.magazinerem = 50;
			baseLogic.mainTurret.reloadTimer = 150;
			baseLogic.mainTurret.canex = true;
			baseLogic.mainTurret.guntype = 2;
		}
		baseLogic.subTurret = new TurretObj(worldObj);
		{
			baseLogic.subTurret.onmotherPos = new Vector3d(0,0,0);
			baseLogic.subTurret.motherRotCenter = new Vector3d(0.3,1.2,-2);
			baseLogic.subTurret.cannonpos = new Vector3d(0,1.5,-2);
			baseLogic.subTurret.turretspeedY = 5;
			baseLogic.subTurret.turretspeedP = 8;
			baseLogic.subTurret.turretanglelimtPitchMax = 5;
			baseLogic.subTurret.turretanglelimtPitchmin = -80;
			baseLogic.subTurret.traverseSound = null;
			baseLogic.subTurret.currentEntity = this;
			baseLogic.subTurret.powor = 8;
			baseLogic.subTurret.cycle_setting = 0;
			baseLogic.subTurret.firesound = "handmadeguns:handmadeguns.fire";
			baseLogic.subTurret.spread = 10;
			baseLogic.subTurret.speed = 16;
			baseLogic.subTurret.canex = true;
			baseLogic.subTurret.magazineMax = 100;
			baseLogic.subTurret.magazinerem = 100;
			baseLogic.subTurret.reloadTimer = 70;
			baseLogic.subTurret.guntype = 0;
			{
				TurretObj turretObj = new TurretObj(worldObj);
				turretObj.onmotherPos = new Vector3d(0,0,0);
				turretObj.turretspeedY = 5;
				turretObj.turretspeedP = 8;
				turretObj.turretanglelimtPitchMax = 5;
				turretObj.turretanglelimtPitchmin = -80;
				turretObj.traverseSound = null;
				turretObj.currentEntity = this;
				turretObj.powor = 8;
				turretObj.cycle_setting = 0;
				turretObj.firesound = "handmadeguns:handmadeguns.fire";
				turretObj.spread = 10;
				turretObj.speed = 16;
				turretObj.canex = true;
				turretObj.magazineMax = 100;
				turretObj.magazinerem = 100;
				turretObj.reloadTimer = 70;
				turretObj.guntype = 0;
			}
		}
	}
}
