package hmgww2.entity.planes;


import handmadevehicle.entity.parts.IMultiTurretVehicle;
import handmadevehicle.entity.parts.SeatInfo;
import handmadevehicle.entity.parts.turrets.TurretObj;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import javax.vecmath.Vector3d;
import java.util.ArrayList;

import static handmadevehicle.Utils.addAllTurret;

public class EntityJPN_FighterA extends EntityJPN_FighterBase implements IMultiTurretVehicle
{
	public EntityJPN_FighterA(World par1World)
	{
		super(par1World);
		this.setSize(5f, 3f);
		this.maxHealth = 500;
//		nboundingbox = new ModifiedBoundingBox(-20,-20,-20,20,20,20,0,0,-6.27,2.5,5,19);
//		nboundingbox.rot.set(this.bodyRot);
//		proxy.replaceBoundingbox(this,nboundingbox);
//		((ModifiedBoundingBox)this.boundingBox).update(this.posX,this.posY,this.posZ);
//		ignoreFrustumCheck = true;
//		baseLogic.throttle_gearDown = 1.7f;
//		baseLogic.throttle_Max = 2.7f;
//		baseLogic.Torpedo_bomber = true;
//		baseLogic.rollspeed = 0.6f;
//		baseLogic.pitchspeed = 0.2f;
//		baseLogic.yawspeed = 0.3f;
//		baseLogic.sholdUseMain_ToG = true;
//
//		baseLogic.startDive = 45;
//		baseLogic.maxDive = 30;
//		baseLogic.cruiseALT = 40;
//		baseLogic.maxClimb = -12;
//		baseLogic.maxbank = 60;
//		baseLogic.minALT = 5;
//		baseLogic.soundname = "hmgww2:hmgww2.sound_pera";
		
		ignoreFrustumCheck = true;
		baseLogic.planeInfo.throttle_gearDown = 1.7f;
		baseLogic.planeInfo.throttle_Max = 3.0f;
		baseLogic.planeInfo.rollspeed = 0.25f;
		baseLogic.planeInfo.pitchspeed = 0.45f;
		baseLogic.planeInfo.yawspeed = 0.2f;
		baseLogic.planeInfo.Dive_bombing = true;
		baseLogic.planeInfo.sholdUseMain_ToG = true;
		baseLogic.planeInfo.startDive = 50;
		baseLogic.planeInfo.maxDive = 80;
		baseLogic.planeInfo.cruiseALT = 80;
		baseLogic.planeInfo.maxClimb = -25;
		baseLogic.planeInfo.maxbank = 40;
		baseLogic.planeInfo.minALT = 30;
		baseLogic.planeInfo.soundname = "hmgww2:hmgww2.sound_pera";
		baseLogic.planeInfo.pitchsighwidthmax = 5;
		baseLogic.planeInfo.pitchsighwidthmin = -10;
		baseLogic.planeInfo.yawsightwidthmax = 15;
		baseLogic.planeInfo.yawsightwidthmin = -15;
		
		baseLogic.planeInfo.camerapos = new double[]{-0.09919,2.528,-0.5840};
		baseLogic.planeInfo.rotcenter = new double[]{0,1.5,0};
		baseLogic.riddenByEntities = new Entity[1];
		baseLogic.seatInfos = new SeatInfo[1];
		baseLogic.seatInfos[0] = new SeatInfo();
		baseLogic.seatInfos_zoom[0] = new SeatInfo();
		baseLogic.seatInfos[0].pos = baseLogic.planeInfo.camerapos;
		baseLogic.seatInfos_zoom[0].pos = new double[]{0,2.528,-1.674};
		
		baseLogic.onground_pitch = -12;
		TurretObj cannon1 = new TurretObj(worldObj);
		
		{
			cannon1.onMotherPos = new Vector3d(0,0,0);
			cannon1.motherRotCenter = new Vector3d(0,1.5,0);
			cannon1.multicannonPos = new Vector3d[2];
			cannon1.multicannonPos[0] = new Vector3d(0.1642 , 2.313 , 2.985);
			cannon1.multicannonPos[1] = new Vector3d(-0.1642 , 2.313 , 2.985);
			cannon1.turretanglelimtPitchMax = 5;
			cannon1.turretanglelimtPitchmin = -80;
			cannon1.traverseSound = null;
			cannon1.currentEntity = this;
			cannon1.powor = 8;
			cannon1.ex = 0.5f;
			cannon1.cycle_setting = 1;
			cannon1.flashName = "arrow";
			cannon1.flashscale = 2;
			cannon1.flashfuse = 1;
			cannon1.flashoffset = 0;
			cannon1.firesound = "handmadeguns:handmadeguns.fire";
			cannon1.magazineMax = 50;
			cannon1.magazinerem = 50;
			cannon1.spread = 2.5f;
			cannon1.speed = 8;
			cannon1.canex = false;
			cannon1.fireAll_cannon = true;
			cannon1.guntype = 2;
		}
		baseLogic.subTurret = cannon1;
		
		TurretObj bomb1 = new TurretObj(worldObj);
		{
			bomb1.onMotherPos = new Vector3d(0,0,0);
			bomb1.motherRotCenter = new Vector3d(baseLogic.planeInfo.rotcenter);
			bomb1.multicannonPos = new Vector3d[2];
			bomb1.multicannonPos[0] = new Vector3d(5.420, 1.334, -0.7071);
			bomb1.multicannonPos[1] = new Vector3d(-5.420, 1.334, -0.7071);
			bomb1.turretspeedY = 5;
			bomb1.turretspeedP = 8;
			bomb1.turretanglelimtPitchMax = 5;
			bomb1.turretanglelimtPitchmin = -80;
			bomb1.traverseSound = null;
			bomb1.currentEntity = this;
			bomb1.powor = 125;
			bomb1.ex = 3;
			bomb1.cycle_setting = 0;
			bomb1.firesound = null;
			bomb1.bulletmodel = "60kgBomb";
			bomb1.flashName = null;
			bomb1.spread = 25;
			bomb1.speed = 0.1f;
			bomb1.canex = true;
			bomb1.guntype = 2;
			bomb1.magazineMax = 1;
			bomb1.fireAll_cannon = true;
			bomb1.reloadSetting = 500;
		}
		
		TurretObj bomb2 = new TurretObj(worldObj);
		{
			bomb2.onMotherPos = new Vector3d(0,0,0);
			bomb1.cannonPos = new Vector3d(0, 0.7028, -0.6236);
			bomb2.motherRotCenter = new Vector3d(baseLogic.planeInfo.rotcenter);
			bomb2.turretspeedY = 5;
			bomb2.turretspeedP = 8;
			bomb2.turretanglelimtPitchMax = 5;
			bomb2.turretanglelimtPitchmin = -80;
			bomb2.traverseSound = null;
			bomb2.currentEntity = this;
			bomb2.powor = 500;
			bomb2.ex = 3;
			bomb2.cycle_setting = 0;
			bomb2.firesound = null;
			bomb2.bulletmodel = "500kgBomb";
			bomb2.flashName = null;
			bomb2.spread = 25;
			bomb2.speed = 0.1f;
			bomb2.canex = true;
			bomb2.guntype = 2;
			bomb2.magazineMax = 1;
			bomb2.reloadSetting = 500;
			bomb1.addchild_triggerLinked(bomb2);
		}
		baseLogic.mainTurret = bomb1;
		sightTex = "hmgww2:textures/hud/Type99BombSight.png";
	}
	protected void applyEntityAttributes()
	{
		maxHealth = 300;
		super.applyEntityAttributes();
	}
	
	
	@Override
	public TurretObj[] getmainTurrets() {
		if(baseLogic.mainTurrets == null) {
			ArrayList<TurretObj> turrets = new ArrayList<TurretObj>();
			addAllTurret(turrets, baseLogic.mainTurret);
			baseLogic.mainTurrets = turrets.toArray(new TurretObj[turrets.size()]);
		}
		return baseLogic.mainTurrets;
	}
	
	@Override
	public TurretObj[] getsubTurrets() {
		if(baseLogic.subTurrets == null) {
			ArrayList<TurretObj> turrets = new ArrayList<TurretObj>();
			addAllTurret(turrets, baseLogic.subTurret);
			baseLogic.subTurrets = turrets.toArray(new TurretObj[turrets.size()]);
		}
		return baseLogic.subTurrets;
	}
	
	@Override
	public TurretObj[] getTurrets() {
		if(baseLogic.turrets == null) {
			ArrayList<TurretObj> turrets = new ArrayList<TurretObj>();
			addAllTurret(turrets, baseLogic.mainTurret);
			addAllTurret(turrets, baseLogic.subTurret);
			baseLogic.turrets = turrets.toArray(new TurretObj[turrets.size()]);
		}
		return baseLogic.turrets;
	}
	
	
	public String getsightTex(){
		return sightTex;
	}
}
