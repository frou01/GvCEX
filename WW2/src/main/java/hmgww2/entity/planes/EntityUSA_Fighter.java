package hmgww2.entity.planes;


import handmadevehicle.entity.parts.SeatInfo;
import handmadevehicle.entity.parts.turrets.TurretObj;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import javax.vecmath.Vector3d;

public class EntityUSA_Fighter extends EntityUSA_FighterBase
{
	public EntityUSA_Fighter(World par1World)
	{
		super(par1World);
		this.setSize(5f, 3f);
		armor = 6;
//		nboundingbox = new ModifiedBoundingBox(-20,-20,-20,20,20,20,0,0,-6.27,2.5,5,19);
//		nboundingbox.rot.set(this.bodyRot);
//		proxy.replaceBoundingbox(this,nboundingbox);
//		((ModifiedBoundingBox)this.boundingBox).updateOBB(this.posX,this.posY,this.posZ);
		ignoreFrustumCheck = true;
		baseLogic.prefab_vehicle.throttle_Max = 4.5f;
		baseLogic.prefab_vehicle.rollspeed = 0.6f;
		baseLogic.prefab_vehicle.pitchspeed = 0.3f;
		baseLogic.prefab_vehicle.yawspeed = 0.1f;
		baseLogic.prefab_vehicle.maxDive = 60;
		baseLogic.prefab_vehicle.maxClimb = -25;
		baseLogic.prefab_vehicle.maxbank = 60;
		baseLogic.prefab_vehicle.soundname = "hmgww2:hmgww2.sound_pera";
		
		baseLogic.prefab_vehicle.camerapos = new double[]{0,2.867,-0.2963};
		baseLogic.prefab_vehicle.rotcenter = new double[]{0,1.1,0};
		baseLogic.riddenByEntities = new Entity[1];
		baseLogic.seatInfos = new SeatInfo[1];
		baseLogic.seatInfos[0] = new SeatInfo();
		baseLogic.seatInfos_zoom[0] = new SeatInfo();
		baseLogic.seatInfos[0].pos = baseLogic.prefab_vehicle.camerapos;
		baseLogic.seatInfos_zoom[0].pos = baseLogic.prefab_vehicle.camerapos;
		
		baseLogic.onground_pitch = -16;
		{
			TurretObj cannon1 = new TurretObj(worldObj);
			{
				cannon1.onMotherPos = new Vector3d(0,0,0);
				cannon1.motherRotCenter = new Vector3d(0,1.1,0);
				cannon1.cannonPos = new Vector3d(1.7691,1.1519,2.2870);
				cannon1.multicannonPos = new Vector3d[3];
				cannon1.multicannonPos[0] = new Vector3d(1.7691,1.1519,2.2870);
				cannon1.multicannonPos[1] = new Vector3d(2.076,1.181,2.271);
				cannon1.multicannonPos[2] = new Vector3d(2.3820,1.2093,2.25552);
				cannon1.turretspeedY = 5;
				cannon1.turretspeedP = 8;
				cannon1.turretanglelimtPitchMax = 5;
				cannon1.turretanglelimtPitchmin = -80;
				cannon1.traverseSound = null;
				cannon1.currentEntity = this;
				cannon1.powor = 10;
				cannon1.ex = 0.5f;
				cannon1.cycle_setting = 1;
				cannon1.flashName = "arrow";
				cannon1.flashoffset = 0;
				cannon1.flashscale = 1;
				cannon1.flashfuse = 1;
				cannon1.firesound = "handmadeguns:handmadeguns.HeavyMachineGun";
				cannon1.spread = 5;
				cannon1.speed = 5;
				cannon1.magazineMax = 100;
				cannon1.magazinerem = 100;
				cannon1.reloadTimer = 70;
				cannon1.canex = false;
				cannon1.fireAll_cannon = false;
				cannon1.guntype = 0;
				
				{
					TurretObj cannon2 = new TurretObj(worldObj);
					cannon2.onMotherPos = new Vector3d(0,0,0);
					cannon2.motherRotCenter = new Vector3d(0,0,0);
					cannon2.cannonPos = new Vector3d(2.076,1.181,2.271);
					cannon2.multicannonPos = new Vector3d[3];
					cannon2.multicannonPos[0] = new Vector3d(-1.7691,1.1519,2.2870);
					cannon2.multicannonPos[1] = new Vector3d(-2.076,1.181,2.271);
					cannon2.multicannonPos[2] = new Vector3d(-2.3820,1.2093,2.25552);
					cannon2.turretspeedY = 5;
					cannon2.turretspeedP = 8;
					cannon2.turretanglelimtPitchMax = 5;
					cannon2.turretanglelimtPitchmin = -80;
					cannon2.traverseSound = null;
					cannon2.currentEntity = this;
					cannon2.powor = 10;
					cannon2.ex = 0.5f;
					cannon2.cycle_setting = 1;
					cannon2.flashName = "arrow";
					cannon2.flashoffset = 0;
					cannon2.flashscale = 1;
					cannon2.flashfuse = 1;
					cannon2.firesound = "handmadeguns:handmadeguns.HeavyMachineGun";
					cannon2.spread = 5;
					cannon2.speed = 5;
					cannon2.magazineMax = 100;
					cannon2.magazinerem = 100;
					cannon2.reloadTimer = 70;
					cannon2.canex = false;
					cannon2.fireAll_cannon = false;
					cannon2.guntype = 0;
					cannon1.addchild_triggerLinked(cannon2);
				}
			}
			baseLogic.mainTurret = cannon1;
		}
	}
	protected void applyEntityAttributes()
	{
		maxHealth = 150;
		super.applyEntityAttributes();
	}
}
