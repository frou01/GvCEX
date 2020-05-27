package handmadevehicle.entity.parts.logics;

import cpw.mods.fml.client.FMLClientHandler;
import handmadeguns.HMGPacketHandler;
import handmadeguns.entity.bullets.HMGEntityBullet_TE;
import handmadeguns.network.PacketPlaySound_Gui;
import handmadeguns.network.PacketSpawnParticle;
import handmadevehicle.HMVehicle;
import handmadevehicle.entity.EntityDummy_rider;
import handmadevehicle.entity.EntityVehicle;
import handmadevehicle.entity.prefab.Prefab_AdditionalBoundingBox;
import handmadevehicle.entity.prefab.Prefab_AttachedWeapon;
import handmadevehicle.network.HMVPacketHandler;
import handmadevehicle.entity.EntityCameraDummy;
import handmadevehicle.entity.parts.*;
import handmadevehicle.entity.parts.turrets.TurretObj;
import handmadevehicle.entity.prefab.Prefab_Vehicle_Base;
import handmadevehicle.inventory.InventoryVehicle;
import handmadevehicle.network.packets.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.*;
import net.minecraft.world.World;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import javax.vecmath.AxisAngle4d;
import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static handmadeguns.HandmadeGunsCore.HMG_proxy;
import static handmadeguns.HandmadeGunsCore.cfg_blockdestroy;
import static handmadeguns.Util.GunsUtils.isCollidableBlock;
import static handmadevehicle.HMVehicle.*;
import static handmadevehicle.Utils.*;
import static handmadevehicle.entity.parts.turrets.TurretObj.getActiveTurret;
import static java.lang.Math.*;
import static java.lang.Math.toRadians;
import static net.minecraft.util.MathHelper.wrapAngleTo180_double;
import static net.minecraft.util.MathHelper.wrapAngleTo180_float;
import static net.minecraft.util.MathHelper.floor_double;

public class BaseLogic implements IbaseLogic,IneedMouseTrack,MultiRiderLogics {
	
	public float bodyrotationYaw;
	public float bodyrotationPitch;
	public float bodyrotationRoll;
	public float prevbodyrotationYaw;
	public float prevbodyrotationPitch;
	public float prevbodyrotationRoll;
	public float throttle;
	public InventoryVehicle inventoryVehicle;
	
	
	public TurretObj[] turrets = new TurretObj[0];
	public TurretObj[] allturrets = new TurretObj[0];
	
	public Entity[] riddenByEntities = new Entity[1];
	public SeatInfo[] seatInfos = {new SeatInfo()};
	public SeatInfo[] seatInfos_zoom = {new SeatInfo()};
	
	public Quat4d bodyRot = new Quat4d(0,0,0,1);
	public Quat4d serverSideBodyRot;
	public Quat4d rotationmotion = new Quat4d(0,0,0,1);
	public Quat4d serverSideRotationmotion;
	public Quat4d prevbodyRot = new Quat4d(0,0,0,1);
	public Vector3d receivedPosition;
	public Vector3d receivedMotion;
	public Vector3d prevPos = null;

	public Vector3d[] prevAdditionalBoxPoses;

	
	public boolean needStartSound = false;
	
	public float pera_trackPos;
	public float prev_pera_trackPos;
	private float current_Draft = 5;

	public float current_onground_pitch;
	public float current_onground_roll;


	public EntityVehicle mc_Entity;
	private IVehicle iVehicle;
	public World worldObj;

	public Vector3d localMotionVec = new Vector3d();
	public Vector3d prevlocalMotionVec = new Vector3d();
	private int nextStepDistance = 1;



	public void moveEntity(double inMotionX, double inMotionY, double inMotionZ)
	{
		Vector3d inMotionVec = new Vector3d(inMotionX,inMotionY,inMotionZ);
		if (mc_Entity.noClip || worldObj.isRemote)
		{
			List list = this.worldObj.getCollidingBoundingBoxes(mc_Entity, ((ModifiedBoundingBox)mc_Entity.boundingBox.addCoord(0, inMotionY, 0)).noMod_copy());

			for (Object o : list) {
				inMotionY = ((AxisAlignedBB) o).calculateYOffset(mc_Entity.boundingBox, inMotionY);
			}
			mc_Entity.boundingBox.offset(inMotionX, inMotionY, inMotionZ);
			mc_Entity.posX = (mc_Entity.boundingBox.minX + mc_Entity.boundingBox.maxX) / 2.0D;
			mc_Entity.posY = mc_Entity.boundingBox.minY +  mc_Entity.yOffset -  mc_Entity.ySize;
			mc_Entity.posZ = (mc_Entity.boundingBox.minZ + mc_Entity.boundingBox.maxZ) / 2.0D;
		}
		else
		{

			double[] motions = new double[]{inMotionX,inMotionY,inMotionZ};
			moveAdditionalBox(motions);
			inMotionX = motions[0];
			inMotionY = motions[1];
			inMotionZ = motions[2];
//			if (mc_Entity.isInWeb)
//			{
//				mc_Entity.isInWeb = false;
//				inMotionX *= 0.25D;
//				inMotionY *= 0.05000000074505806D;
//				inMotionZ *= 0.25D;
//				this.motionX = 0.0D;
//				this.motionY = 0.0D;
//				this.motionZ = 0.0D;
//			}

//			boolean flag = false;
//
//			if (flag)
//			{
//				double d9;
//
//				for (d9 = 0.05D; inMotionX != 0.0D && this.worldObj.getCollidingBoundingBoxes(mc_Entity, mc_Entity.boundingBox.getOffsetBoundingBox(inMotionX, -1.0D, 0.0D)).isEmpty(); d6 = inMotionX)
//				{
//					if (inMotionX < d9 && inMotionX >= -d9)
//					{
//						inMotionX = 0.0D;
//					}
//					else if (inMotionX > 0.0D)
//					{
//						inMotionX -= d9;
//					}
//					else
//					{
//						inMotionX += d9;
//					}
//				}
//
//				for (; inMotionZ != 0.0D && this.worldObj.getCollidingBoundingBoxes(mc_Entity, mc_Entity.boundingBox.getOffsetBoundingBox(0.0D, -1.0D, inMotionZ)).isEmpty(); d8 = inMotionZ)
//				{
//					if (inMotionZ < d9 && inMotionZ >= -d9)
//					{
//						inMotionZ = 0.0D;
//					}
//					else if (inMotionZ > 0.0D)
//					{
//						inMotionZ -= d9;
//					}
//					else
//					{
//						inMotionZ += d9;
//					}
//				}
//
//				while (inMotionX != 0.0D && inMotionZ != 0.0D && this.worldObj.getCollidingBoundingBoxes(mc_Entity, mc_Entity.boundingBox.getOffsetBoundingBox(inMotionX, -1.0D, inMotionZ)).isEmpty())
//				{
//					if (inMotionX < d9 && inMotionX >= -d9)
//					{
//						inMotionX = 0.0D;
//					}
//					else if (inMotionX > 0.0D)
//					{
//						inMotionX -= d9;
//					}
//					else
//					{
//						inMotionX += d9;
//					}
//
//					if (inMotionZ < d9 && inMotionZ >= -d9)
//					{
//						inMotionZ = 0.0D;
//					}
//					else if (inMotionZ > 0.0D)
//					{
//						inMotionZ -= d9;
//					}
//					else
//					{
//						inMotionZ += d9;
//					}
//
//					d6 = inMotionX;
//					d8 = inMotionZ;
//				}
//			}
			double firstPosX = mc_Entity.posX;
			double firstPosY = mc_Entity.posY;
			double firstPosZ = mc_Entity.posZ;


			double d6 = inMotionX;
			double d7 = inMotionY;
			double d8 = inMotionZ;

			AxisAlignedBB entityBoundingBox = mc_Entity.boundingBox;
			List list = this.worldObj.getCollidingBoundingBoxes(mc_Entity, ((ModifiedBoundingBox)entityBoundingBox).noMod_copy().addCoord(inMotionX, inMotionY, inMotionZ));

			for (Object o : list) {
				inMotionY = ((AxisAlignedBB) o).calculateYOffset(entityBoundingBox, inMotionY);
			}

			entityBoundingBox.offset(0.0D, inMotionY, 0.0D);

			if (!mc_Entity.field_70135_K && d7 != inMotionY)
			{
				inMotionZ = 0.0D;
				inMotionY = 0.0D;
				inMotionX = 0.0D;
			}

			boolean flag1 = mc_Entity.onGround || d7 != inMotionY && d7 < 0.0D;

			int j;

			for (j = 0; j < list.size(); ++j)
			{
				inMotionX = ((AxisAlignedBB)list.get(j)).calculateXOffset(entityBoundingBox, inMotionX);
			}

			entityBoundingBox.offset(inMotionX, 0.0D, 0.0D);

			if (!mc_Entity.field_70135_K && d6 != inMotionX)
			{
				inMotionZ = 0.0D;
				inMotionY = 0.0D;
				inMotionX = 0.0D;
			}

			for (j = 0; j < list.size(); ++j)
			{
				inMotionZ = ((AxisAlignedBB)list.get(j)).calculateZOffset(entityBoundingBox, inMotionZ);
			}

			entityBoundingBox.offset(0.0D, 0.0D, inMotionZ);

			if (!mc_Entity.field_70135_K && d8 != inMotionZ)
			{
				inMotionZ = 0.0D;
				inMotionY = 0.0D;
				inMotionX = 0.0D;
			}

			double d10;
			double d11;
			int k;
			double d12;

			if ((!worldObj.isRemote && mc_Entity.stepHeight > 0.0F) && flag1 && mc_Entity.ySize < 0.05F && (d6 != inMotionX || d8 != inMotionZ))
			{
				d12 = inMotionX;
				d10 = inMotionY;
				d11 = inMotionZ;
				inMotionX = d6;
				inMotionY = mc_Entity.stepHeight;
				inMotionZ = d8;
				AxisAlignedBB axisalignedbb1 = entityBoundingBox.copy();
				entityBoundingBox.setBB(entityBoundingBox.copy());
				list = getCollidingBoundingBoxes(mc_Entity, entityBoundingBox.addCoord(d6, inMotionY, d8));

				for (k = 0; k < list.size(); ++k)
				{
					inMotionY = ((AxisAlignedBB)list.get(k)).calculateYOffset(entityBoundingBox, inMotionY);
				}

				entityBoundingBox.offset(0.0D, inMotionY, 0.0D);

				for (k = 0; k < list.size(); ++k)
				{
					inMotionX = ((AxisAlignedBB)list.get(k)).calculateXOffset(entityBoundingBox, inMotionX);
				}

				entityBoundingBox.offset(inMotionX, 0.0D, 0.0D);


				for (k = 0; k < list.size(); ++k)
				{
					inMotionZ = ((AxisAlignedBB)list.get(k)).calculateZOffset(entityBoundingBox, inMotionZ);
				}

				entityBoundingBox.offset(0.0D, 0.0D, inMotionZ);


				{
					inMotionY = -mc_Entity.stepHeight;

					for (k = 0; k < list.size(); ++k)
					{
						inMotionY = ((AxisAlignedBB)list.get(k)).calculateYOffset(entityBoundingBox, inMotionY);
					}

					entityBoundingBox.offset(0.0D, inMotionY, 0.0D);
				}

				if (d12 * d12 + d11 * d11 >= inMotionX * inMotionX + inMotionZ * inMotionZ)
				{
					inMotionX = d12;
					inMotionY = d10;
					inMotionZ = d11;
					entityBoundingBox.setBB(axisalignedbb1);
				}
			}
			if(!worldObj.isRemote) {
				mc_Entity.posX = (entityBoundingBox.minX + entityBoundingBox.maxX) / 2.0D;
				mc_Entity.posY = entityBoundingBox.minY +  mc_Entity.yOffset -  mc_Entity.ySize;
				mc_Entity.posZ = (entityBoundingBox.minZ + entityBoundingBox.maxZ) / 2.0D;
			}
			mc_Entity.isCollidedHorizontally = d6 != inMotionX || d8 != inMotionZ;
			mc_Entity.isCollidedVertically = d7 != inMotionY;
			mc_Entity.onGround = d7 != inMotionY && d7 < 0.0D;
			mc_Entity.isCollided = mc_Entity.isCollidedHorizontally || mc_Entity.isCollidedVertically;
			iVehicle.updateFallState_public(inMotionY, mc_Entity.onGround);

			if (d6 != inMotionX)
			{
				mc_Entity.motionX = 0.0D;
			}

			if (d7 != inMotionY)
			{
				mc_Entity.motionY = 0.0D;
			}

			if (d8 != inMotionZ)
			{
				mc_Entity.motionZ = 0.0D;
			}

			d12 = mc_Entity.posX - firstPosX;
			d10 = mc_Entity.posY - firstPosY;
			d11 = mc_Entity.posZ - firstPosZ;

			int j1 = MathHelper.floor_double(mc_Entity.posX);
			k = MathHelper.floor_double(mc_Entity.posY - 0.20000000298023224D - mc_Entity.yOffset);
			int l = MathHelper.floor_double(mc_Entity.posZ);
			Block block = mc_Entity.worldObj.getBlock(j1, k, l);
			int i1 = mc_Entity.worldObj.getBlock(j1, k - 1, l).getRenderType();

			if (i1 == 11 || i1 == 32 || i1 == 21)
			{
				block = mc_Entity.worldObj.getBlock(j1, k - 1, l);
			}

			if (block != Blocks.ladder)
			{
				d10 = 0.0D;
			}

			mc_Entity.distanceWalkedModified = (float)(mc_Entity.distanceWalkedModified + MathHelper.sqrt_double(d12 * d12 + d11 * d11) * 0.6D);
			mc_Entity.distanceWalkedOnStepModified = (float)(mc_Entity.distanceWalkedOnStepModified + MathHelper.sqrt_double(d12 * d12 + d10 * d10 + d11 * d11) * 0.6D);

			if (mc_Entity.distanceWalkedOnStepModified > (float)this.nextStepDistance && block.getMaterial() != Material.air)
			{
				this.nextStepDistance = (int)mc_Entity.distanceWalkedOnStepModified + 1;
				block.onEntityWalking(this.worldObj, j1, k, l, mc_Entity);
			}

			try
			{
				iVehicle.func_145775_I_public();
			}
			catch (Throwable throwable)
			{
				CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Checking entity block collision");
				CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being checked for collision");
				mc_Entity.addEntityCrashInfo(crashreportcategory);
				throw new ReportedException(crashreport);
			}

			destroyNearBlocks(((ModifiedBoundingBox)entityBoundingBox).noMod_copy(),
					inMotionVec.x-mc_Entity.motionX,inMotionVec.y-mc_Entity.motionY,inMotionVec.z-mc_Entity.motionZ);
		}
	}

	//容易に貫通するので対策が必要
	public void moveAdditionalBox(double[] inMotions){
		Vector3d thisposVec = new Vector3d(this.mc_Entity.posX,
		this.mc_Entity.posY,
		this.mc_Entity.posZ);
		for(Prefab_AdditionalBoundingBox box : prefab_vehicle.additionalBoundingBoxes) {
			Vector3d posGlobal = new Vector3d();
			posGlobal.sub(box.info.pos,prefab_vehicle.rotcenterVec);
			posGlobal = transformVecByQuat(box.info.pos,prevbodyRot);
			transformVecforMinecraft(posGlobal);
			posGlobal.add(prefab_vehicle.rotcenterVec);

			AxisAlignedBB entityBoundingBox = AxisAlignedBB.getBoundingBox(
					 thisposVec.x + posGlobal.x - box.info.size.x,
					 thisposVec.y + posGlobal.y - box.info.size.y,
					 thisposVec.z + posGlobal.z - box.info.size.z,

					thisposVec.x + posGlobal.x + box.info.size.x,
					thisposVec.y + posGlobal.y + box.info.size.y,
					thisposVec.z + posGlobal.z + box.info.size.z);

			double firstPosX = mc_Entity.posX;
			double firstPosY = mc_Entity.posY;
			double firstPosZ = mc_Entity.posZ;

			Vector3d motionVec = new Vector3d(inMotions);
			Vector3d inMotionVec = new Vector3d(inMotions);
			{
				Vector3d relativePos = new Vector3d(posGlobal);

				if(motionVec.dot(relativePos)<0)continue;
			}


			double d6 = motionVec.x;
			double d7 = motionVec.y;
			double d8 = motionVec.z;

			List list = this.worldObj.getCollidingBoundingBoxes(mc_Entity, entityBoundingBox.addCoord(motionVec.x, motionVec.y, motionVec.z));

			for (Object o : list) {
				motionVec.y = ((AxisAlignedBB) o).calculateYOffset(entityBoundingBox, motionVec.y);
			}

			entityBoundingBox.offset(0.0D, motionVec.y, 0.0D);

			if (!mc_Entity.field_70135_K && d7 != motionVec.y)
			{
				motionVec.z = 0.0D;
				motionVec.y = 0.0D;
				motionVec.x = 0.0D;
			}

			boolean flag1 = mc_Entity.onGround || d7 != motionVec.y && d7 < 0.0D;

			int j;

			for (j = 0; j < list.size(); ++j)
			{
				motionVec.x = ((AxisAlignedBB)list.get(j)).calculateXOffset(entityBoundingBox, motionVec.x);
			}

			entityBoundingBox.offset(motionVec.x, 0.0D, 0.0D);

			if (!mc_Entity.field_70135_K && d6 != motionVec.x)
			{
				motionVec.z = 0.0D;
				motionVec.y = 0.0D;
				motionVec.x = 0.0D;
			}

			for (j = 0; j < list.size(); ++j)
			{
				motionVec.z = ((AxisAlignedBB)list.get(j)).calculateZOffset(entityBoundingBox, motionVec.z);
			}

			entityBoundingBox.offset(0.0D, 0.0D, motionVec.z);

			if (!mc_Entity.field_70135_K && d8 != motionVec.z)
			{
				motionVec.z = 0.0D;
				motionVec.y = 0.0D;
				motionVec.x = 0.0D;
			}

			double d10;
			double d11;
			int k;
			double d12;

			if ((!worldObj.isRemote && mc_Entity.stepHeight > 0.0F) && flag1 && mc_Entity.ySize < 0.05F && (d6 != motionVec.x || d8 != motionVec.z))
			{
				d12 = motionVec.x;
				d10 = motionVec.y;
				d11 = motionVec.z;
				motionVec.x = d6;
				motionVec.y = mc_Entity.stepHeight;
				motionVec.z = d8;
				AxisAlignedBB axisalignedbb1 = entityBoundingBox.copy();
				entityBoundingBox.setBB(entityBoundingBox.copy());
				list = getCollidingBoundingBoxes(mc_Entity, entityBoundingBox.addCoord(d6, motionVec.y, d8));

				for (k = 0; k < list.size(); ++k)
				{
					motionVec.y = ((AxisAlignedBB)list.get(k)).calculateYOffset(entityBoundingBox, motionVec.y);
				}

				entityBoundingBox.offset(0.0D, motionVec.y, 0.0D);

				for (k = 0; k < list.size(); ++k)
				{
					motionVec.x = ((AxisAlignedBB)list.get(k)).calculateXOffset(entityBoundingBox, motionVec.x);
				}

				entityBoundingBox.offset(motionVec.x, 0.0D, 0.0D);


				for (k = 0; k < list.size(); ++k)
				{
					motionVec.z = ((AxisAlignedBB)list.get(k)).calculateZOffset(entityBoundingBox, motionVec.z);
				}

				entityBoundingBox.offset(0.0D, 0.0D, motionVec.z);


				{
					motionVec.y = -mc_Entity.stepHeight;

					for (k = 0; k < list.size(); ++k)
					{
						motionVec.y = ((AxisAlignedBB)list.get(k)).calculateYOffset(entityBoundingBox, motionVec.y);
					}

					entityBoundingBox.offset(0.0D, motionVec.y, 0.0D);
				}

				if (d12 * d12 + d11 * d11 >= motionVec.x * motionVec.x + motionVec.z * motionVec.z)
				{
					motionVec.x = d12;
					motionVec.y = d10;
					motionVec.z = d11;
					entityBoundingBox.setBB(axisalignedbb1);
				}
			}
			if(!worldObj.isRemote) {
				mc_Entity.posX = posGlobal.x + (entityBoundingBox.minX + entityBoundingBox.maxX) / 2.0D;
				mc_Entity.posY = posGlobal.y + entityBoundingBox.minY +  mc_Entity.yOffset -  mc_Entity.ySize;
				mc_Entity.posZ = posGlobal.z + (entityBoundingBox.minZ + entityBoundingBox.maxZ) / 2.0D;
			}
			mc_Entity.isCollidedHorizontally = d6 != motionVec.x || d8 != motionVec.z;
			mc_Entity.isCollidedVertically = d7 != motionVec.y;
			mc_Entity.onGround |= d7 != motionVec.y && d7 < 0.0D;
			mc_Entity.isCollided = mc_Entity.isCollidedHorizontally || mc_Entity.isCollidedVertically;
			iVehicle.updateFallState_public(motionVec.y, mc_Entity.onGround);

			if (d6 != motionVec.x)
			{
				motionVec.x = 0.0D;
			}

			if (d7 != motionVec.y)
			{
				motionVec.y = 0.0D;
			}

			if (d8 != motionVec.z)
			{
				motionVec.z = 0.0D;
			}

			d12 = posGlobal.x + mc_Entity.posX - firstPosX;
			d10 = posGlobal.y + mc_Entity.posY - firstPosY;
			d11 = posGlobal.z + mc_Entity.posZ - firstPosZ;

			int j1 = floor_double(mc_Entity.posX);
			k = floor_double(mc_Entity.posY - 0.20000000298023224D - mc_Entity.yOffset);
			int l = floor_double(mc_Entity.posZ);
			Block block = mc_Entity.worldObj.getBlock(j1, k, l);
			int i1 = mc_Entity.worldObj.getBlock(j1, k - 1, l).getRenderType();

			if (i1 == 11 || i1 == 32 || i1 == 21)
			{
				block = mc_Entity.worldObj.getBlock(j1, k - 1, l);
			}

			if (block != Blocks.ladder)
			{
				d10 = 0.0D;
			}

			mc_Entity.distanceWalkedModified = (float)(mc_Entity.distanceWalkedModified + MathHelper.sqrt_double(d12 * d12 + d11 * d11) * 0.6D);
			mc_Entity.distanceWalkedOnStepModified = (float)(mc_Entity.distanceWalkedOnStepModified + MathHelper.sqrt_double(d12 * d12 + d10 * d10 + d11 * d11) * 0.6D);

			if (mc_Entity.distanceWalkedOnStepModified > (float)this.nextStepDistance && block.getMaterial() != Material.air)
			{
				this.nextStepDistance = (int)mc_Entity.distanceWalkedOnStepModified + 1;
				block.onEntityWalking(this.worldObj, j1, k, l, mc_Entity);
			}

		}
	}
	public List getCollidingBoundingBoxes(Entity p_72945_1_, AxisAlignedBB p_72945_2_)
	{
		ArrayList collidingBoundingBoxes = new ArrayList();
		int i = MathHelper.floor_double(p_72945_2_.minX);
		int j = MathHelper.floor_double(p_72945_2_.maxX + 1.0D);
		int k = MathHelper.floor_double(p_72945_2_.minY);
		int l = MathHelper.floor_double(p_72945_2_.maxY + 1.0D);
		int i1 = MathHelper.floor_double(p_72945_2_.minZ);
		int j1 = MathHelper.floor_double(p_72945_2_.maxZ + 1.0D);

		for (int k1 = i; k1 < j; ++k1)
		{
			for (int l1 = i1; l1 < j1; ++l1)
			{
				if (worldObj.blockExists(k1, 64, l1))
				{
					for (int i2 = k - 1; i2 < l; ++i2)
					{
						Block block;

						if (k1 >= -30000000 && k1 < 30000000 && l1 >= -30000000 && l1 < 30000000)
						{
							block = worldObj.getBlock(k1, i2, l1);
						}
						else
						{
							block = Blocks.stone;
						}

						if(!is_forceBrakeBrock(block))block.addCollisionBoxesToList(worldObj, k1, i2, l1, p_72945_2_, collidingBoundingBoxes, p_72945_1_);
					}
				}
			}
		}

		double d0 = 0.25D;
		List list = worldObj.getEntitiesWithinAABBExcludingEntity(p_72945_1_, p_72945_2_.expand(d0, d0, d0));

		for (int j2 = 0; j2 < list.size(); ++j2)
		{
			AxisAlignedBB axisalignedbb1 = ((Entity)list.get(j2)).getBoundingBox();

			if (axisalignedbb1 != null && axisalignedbb1.intersectsWith(p_72945_2_))
			{
				collidingBoundingBoxes.add(axisalignedbb1);
			}

			axisalignedbb1 = p_72945_1_.getCollisionBox((Entity)list.get(j2));

			if (axisalignedbb1 != null && axisalignedbb1.intersectsWith(p_72945_2_))
			{
				collidingBoundingBoxes.add(axisalignedbb1);
			}
		}

		return collidingBoundingBoxes;
	}
	
	
	private void destroyNearBlocks(AxisAlignedBB boundingBox,double inMotionX, double inMotionY, double inMotionZ){
		if(prefab_vehicle.destroyGroundBlockCof < 0 || !cfg_blockdestroy)return;
		float destroy_counter = 0;
		inMotionY *= prefab_vehicle.antiGroundHitCof_Y;
		double speed = inMotionX * inMotionX + inMotionY * inMotionY + inMotionZ * inMotionZ;
		speed *= prefab_vehicle.destroyGroundBlockCof;

		for (int x = floor_double(boundingBox.minX); x <= floor_double(boundingBox.maxX); x++) for (int y = floor_double(boundingBox.minY); y <= floor_double(boundingBox.maxY); y++) for (int z = floor_double(boundingBox.minZ); z <= floor_double(boundingBox.maxZ); z++) {
			Block collidingblock = worldObj.getBlock(x, y, z);
			if ((speed > collidingblock.getBlockHardness(null, 0, 0, 0) && collidingblock.getBlockHardness(null, 0, 0, 0) > 0
					&& isCollidableBlock(worldObj.getBlock(x, y, z)))||
					is_forceBrakeBrock(collidingblock)) {
				worldObj.setBlockToAir(x,y,z);
				mc_Entity.worldObj.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(collidingblock) + (worldObj.getBlockMetadata(x, y, z) << 12));
				if(!is_forceBrakeBrock(collidingblock))destroy_counter+=collidingblock.getBlockHardness(null, 0, 0, 0);
			}
		}
		boundingBox = boundingBox.addCoord(inMotionX,inMotionY,inMotionZ);
		for (int x = floor_double(boundingBox.minX); x <= floor_double(boundingBox.maxX); x++) for (int y = floor_double(boundingBox.minY); y <= floor_double(boundingBox.maxY); y++) for (int z = floor_double(boundingBox.minZ); z <= floor_double(boundingBox.maxZ); z++) {
			Block collidingblock = worldObj.getBlock(x, y, z);
			if ((speed > collidingblock.getBlockHardness(worldObj, 0, 0, 0)  && collidingblock.getBlockHardness(null, 0, 0, 0) > 0
					&& isCollidableBlock(worldObj.getBlock(x, y, z)))||
					is_forceBrakeBrock(collidingblock)) {
				worldObj.setBlockToAir(x, y, z);
				mc_Entity.worldObj.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(collidingblock) + (worldObj.getBlockMetadata(x, y, z) << 12));
				if(!is_forceBrakeBrock(collidingblock))destroy_counter+=collidingblock.getBlockHardness(null, 0, 0, 0);
			}
		}

		if(destroy_counter > 1){
			mc_Entity.motionX /=destroy_counter;
			mc_Entity.motionY /=destroy_counter;
			mc_Entity.motionZ /=destroy_counter;
		}
		if(destroy_counter > 5){
			mc_Entity.attackEntityFrom(DamageSource.inWall, (float) (destroy_counter-5)*prefab_vehicle.antiGroundHitCof);
		}
		
	}
	private boolean is_forceBrakeBrock(Block collidingblock){
		//TODO https://twitter.com/BUMPKEY4423/status/1245889077150240769
		return collidingblock.getMaterial() == Material.leaves ||
				collidingblock.getMaterial() == Material.wood ||
				collidingblock.getMaterial() == Material.cloth ||
				collidingblock.getMaterial() == Material.cactus ||
				collidingblock.getMaterial() == Material.glass;
	}
	
	public void setinfo(Prefab_Vehicle_Base info) {
		this.prefab_vehicle = info;
		this.allturrets = new TurretObj[info.prefab_attachedWeapons_all.length];
		this.turrets = new TurretObj[info.prefab_attachedWeapons.length];//数カウンタだけで良さそうな気はする
		int cnt = 0;
		for(Prefab_AttachedWeapon prefab_attachedWeapon:info.prefab_attachedWeapons_all){
			this.allturrets[cnt] = prefab_attachedWeapon.getTurretOBJ(worldObj,this.iVehicle,this.allturrets);
			cnt++;
		}
		cnt = 0;
		int cntID = 0;
		for(TurretObj turretObj:this.allturrets){
			turretObj.turretID_OnVehicle = cntID;
			cntID++;
			if(turretObj.isMother){
				this.turrets[cnt] = turretObj;
				cnt++;
			}
		}
		this.seatInfos = info.getSeatInfoOBJs(allturrets);
		this.seatInfos_zoom = info.getSeatInfoOBJs_zoom(allturrets);
		this.riddenByEntities = new Entity[seatInfos.length];
		this.flare_remain = prefab_vehicle.flare_Max;

		for(TurretObj aturret:allturrets){
			aturret.motherEntity = this.mc_Entity;
		}
		inventoryVehicle = new InventoryVehicle(this);
		this.current_Draft = info.draft;
		this.health = prefab_vehicle.maxhealth;

		prevAdditionalBoxPoses = new Vector3d[prefab_vehicle.additionalBoundingBoxes.length];

		this.current_onground_pitch = prefab_vehicle.onground_pitch;
		this.current_onground_roll = prefab_vehicle.onground_roll;
	}
	
	
	
	public Entity[] getRiddenEntityList(){
		return riddenByEntities;
	}
	
	public SeatInfo[] getRiddenSeatList() {
		return seatInfos;
	}
	
	public boolean pickupEntity(Entity entity, int StartSeachSeatNum){
		if(isRidingEntity(entity))return false;
		boolean flag = false;
		if(!mc_Entity.worldObj.isRemote) {
			for (int cnt = 0; cnt < riddenByEntities.length; cnt++) {
				int tempid = cnt + StartSeachSeatNum;
				while(tempid < 0)tempid = tempid + riddenByEntities.length;
				while(tempid >= seatInfos.length)tempid = tempid - riddenByEntities.length;
				if (riddenByEntities[tempid] == null) {
					riddenByEntities[tempid] = entity;
					if(!(entity.ridingEntity instanceof EntityDummy_rider)){
						entity.ridingEntity = new EntityDummy_rider(worldObj, this,cnt);
						entity.ridingEntity.riddenByEntity = entity;
						entity.ridingEntity.setPosition(entity.posX,entity.posY,entity.posZ);
					}
					flag = true;
					break;
				}
			}
			if (flag)
				HMVPacketHandler.INSTANCE.sendToAll(new HMVPacketPickNewEntity(mc_Entity.getEntityId(), riddenByEntities));
		}
		return flag;
//		entity.mountEntity(this);
	}
	public boolean isRidingEntity(Entity entity){
		for(Entity aRiddenby: riddenByEntities){
			if(entity == aRiddenby)return true;
		}
		return false;
	}
	private void updateRider(){
		int cnt = 0;
		for (Entity entity : riddenByEntities) {
			if (entity != null) {
				if((mc_Entity.worldObj.isRemote && entity == HMV_Proxy.getEntityPlayerInstance())) {
					HMV_Proxy.setPlayerSeatID(cnt);
					entity.ridingEntity = mc_Entity;
					if(HMV_Proxy.isSneaking())
						HMVPacketHandler.INSTANCE.sendToServer(new HMVPacketDisMountEntity(mc_Entity.getEntityId(),entity.getEntityId()));
				}else
				if(!mc_Entity.worldObj.isRemote) {
					if (entity.isDead) {
//						System.out.println("debug");
						if(entity.ridingEntity != null)entity.ridingEntity.setDead();
						riddenByEntities[cnt] = null;
						entity.ridingEntity = null;
					} else {
						if(!(entity.ridingEntity instanceof EntityDummy_rider)){
							entity.ridingEntity = new EntityDummy_rider(worldObj, this,cnt);
							entity.ridingEntity.riddenByEntity = entity;
							entity.ridingEntity.setPosition(entity.posX,entity.posY,entity.posZ);
						}
					}
				}else{
					entity.rotationYaw = entity.getRotationYawHead();
					if (entity.isDead) {
//						System.out.println("debug");
						riddenByEntities[cnt] = null;
						entity.ridingEntity = null;
					}else entity.ridingEntity = mc_Entity;
				}

			}

			TurretObj seatmaingun = null;
			if(seatInfos[cnt].maingun != null)seatmaingun = seatInfos[cnt].maingun[seatInfos[cnt].currentWeaponMode];
			TurretObj seatsubgun = seatInfos[cnt].subgun;
			if (worldObj.isRemote) {
				if (entity != null && entity == HMV_Proxy.getEntityPlayerInstance()) {
					if (HMV_Proxy.weapon_Mode_click() || (HMV_Proxy.leftclick() && seatInfos[cnt].maingun != null && getActiveTurret(seatInfos[cnt].maingun[seatInfos[cnt].currentWeaponMode]) == null)){
						seatInfos[cnt].currentWeaponMode++;
//						System.out.println("" + prefab_vehicle.weaponModeSound);
						HMG_proxy.playGUISound(prefab_vehicle.weaponModeSound, 1.0F);
					}
					if (seatInfos[cnt].maingun == null || seatInfos[cnt].currentWeaponMode >= seatInfos[cnt].maingun.length) {
						seatInfos[cnt].currentWeaponMode = 0;
					}
					if(HMV_Proxy.changeControlclick()){
						seatInfos[cnt].syncToPlayerAngle = !seatInfos[cnt].syncToPlayerAngle;

						String message;
						if(seatInfos[cnt].syncToPlayerAngle){
							message = "Aim : On";
						}else {
							message = "Aim : Off";
						}
						((EntityPlayer)entity).addChatComponentMessage(new ChatComponentTranslation(message));
					}

					if(HMG_proxy.getMCInstance().inGameHasFocus && HMG_proxy.getMCInstance().currentScreen == null) {
						if (seatmaingun != null || seatsubgun != null)
							HMVPacketHandler.INSTANCE.sendToServer(new HMVPacketTriggerSeatGun(HMV_Proxy.leftclick(), HMV_Proxy.rightclick(),seatInfos[cnt].syncToPlayerAngle, seatInfos[cnt].currentWeaponMode, mc_Entity.getEntityId(), cnt));
						if (HMV_Proxy.next_Seatclick())
							HMVPacketHandler.INSTANCE.sendToServer(new HMVPacketChangeSeat(mc_Entity.getEntityId(), cnt, true));
						else if (HMV_Proxy.previous_Seatclick())
							HMVPacketHandler.INSTANCE.sendToServer(new HMVPacketChangeSeat(mc_Entity.getEntityId(), cnt, false));
					}
				}
			} else {
				if (seatmaingun != null) {
					if (seatInfos[cnt].gunTrigger1) {
						seatmaingun.fireall();
					}
//					System.out.println("" + riddenByEntitiesInfo[cnt].gunTrigger1);
				}
				if (seatsubgun != null) {
					if (seatInfos[cnt].gunTrigger2) {
						seatsubgun.fireall();
					}
//					System.out.println("" + riddenByEntitiesInfo[cnt].gunTrigger1);
				}
				if(entity == null){
					seatInfos[cnt].gunTrigger1 = false;
					seatInfos[cnt].gunTrigger2 = false;
				}
			}

			cnt ++;
		}
		if(!worldObj.isRemote)HMVPacketHandler.INSTANCE.sendToAll(new HMVPacketPickNewEntity(mc_Entity.getEntityId(),riddenByEntities));
		for(TurretObj aturret :turrets){
			aturret.motherEntity = mc_Entity;
			aturret.update(bodyRot,new Vector3d(mc_Entity.posX,mc_Entity.posY,-mc_Entity.posZ));
		}
		mc_Entity.riddenByEntity = riddenByEntities[0];
	}

	private void riderPosUpdate(){
		int cnt = 0;
		Vector3d thispos = new Vector3d(mc_Entity.posX,
				                               mc_Entity.posY,
				                               mc_Entity.posZ);
//		System.out.println("thispos  " + thispos);
		for (Entity entity : riddenByEntities) {
			if (entity != null) {
//				if(worldObj.isRemote)System.out.println("debug CL Pre" + cnt + " , " + entity);
//				else System.out.println("debug SV Pre" + cnt + " , " + entity);
//			temp.add(playeroffsetter);
//			System.out.println(temp);
				TurretObj seatmaingun = null;
				if(seatInfos[cnt].maingun != null)seatmaingun = seatInfos[cnt].maingun[seatInfos[cnt].currentWeaponMode];
				TurretObj seatsubgun = seatInfos[cnt].subgun;
				if(entity instanceof ITurretUser)((ITurretUser) entity).setSeatID(cnt);
				if(seatmaingun != null) {
					seatmaingun.currentEntity = entity;
					if(seatInfos[cnt].prefab_seat.aimMainGun && !worldObj.isRemote) {
						if((entity instanceof ITurretUser))
							((ITurretUser) entity).setTurretMain(seatmaingun);
						else if(entity instanceof EntityPlayer)
							if(seatInfos[cnt].syncToPlayerAngle)seatmaingun.aimtoAngle(((EntityPlayer) entity).rotationYaw, entity.rotationPitch);

						if(seatInfos[cnt].prefab_seat.aimingMainTurret != -1){
							seatmaingun = seatInfos[cnt].maingun[seatInfos[cnt].prefab_seat.aimingMainTurret];
							if((entity instanceof ITurretUser))
								((ITurretUser) entity).setTurretMain(seatmaingun);
							else if(entity instanceof EntityPlayer)
								if(seatInfos[cnt].syncToPlayerAngle)seatmaingun.aimtoAngle(((EntityPlayer) entity).rotationYaw, entity.rotationPitch);
						}
						if(seatInfos[cnt].maingun != null)seatmaingun = seatInfos[cnt].maingun[seatInfos[cnt].currentWeaponMode];
					}
					if(seatsubgun!=null){
						seatsubgun.currentEntity = entity;
                        if(!worldObj.isRemote) {
	                        if (seatInfos[cnt].prefab_seat.aimSubGun) {
		                        if ((entity instanceof ITurretUser))
			                        ((ITurretUser) entity).setTurretSub(seatsubgun);
		                        else if (entity instanceof EntityPlayer)
			                        if(seatInfos[cnt].syncToPlayerAngle)seatsubgun.aimtoAngle(((EntityPlayer) entity).rotationYaw, entity.rotationPitch);
	                        } else {
		                        if ((entity instanceof ITurretUser))
			                        ((ITurretUser) entity).setTurretSub(seatsubgun);
		                        seatsubgun.noTraverse = true;
	                        }
                        }
					}
					if(seatInfos[cnt].prefab_seat.seatOnTurret) {
						if(seatInfos[cnt].prefab_seat.sittingMainTurret != -1){
							seatmaingun = seatInfos[cnt].maingun[seatInfos[cnt].prefab_seat.sittingMainTurret];
						}
						Vector3d temp = new Vector3d(seatmaingun.pos);
						Vector3d tempplayerPos = new Vector3d(entity == HMV_Proxy.getEntityPlayerInstance() && HMV_Proxy.iszooming() && seatInfos_zoom.length > cnt && seatInfos_zoom[cnt] != null ? seatInfos_zoom[cnt].pos : seatInfos[cnt].pos);
						Vector3d temp2 = seatmaingun.getGlobalVector_fromLocalVector_onTurretPoint(tempplayerPos);
						temp.add(temp2);
						transformVecforMinecraft(temp);
//			System.out.println(temp);
						entity.setPosition(temp.x,
								temp.y - (worldObj.isRemote && entity == HMV_Proxy.getEntityPlayerInstance() ? 0:(entity.getEyeHeight()+0.2)),
								temp.z);
						entity.posX = temp.x;
						entity.posY = temp.y - (worldObj.isRemote && entity == HMV_Proxy.getEntityPlayerInstance() ? 0:(entity.getEyeHeight()+0.2));
						entity.posZ = temp.z;
					}else {
						Vector3d tempplayerPos = new Vector3d(entity == HMV_Proxy.getEntityPlayerInstance() && HMV_Proxy.iszooming() && seatInfos_zoom.length > cnt && seatInfos_zoom[cnt] != null? seatInfos_zoom[cnt].pos: seatInfos[cnt].pos);
						tempplayerPos.sub(new Vector3d(prefab_vehicle.rotcenter));
						tempplayerPos = transformVecByQuat(tempplayerPos, bodyRot);
						transformVecforMinecraft(tempplayerPos);
						tempplayerPos.add(new Vector3d(prefab_vehicle.rotcenter));
//				System.out.println("" + tempplayerPos);
						tempplayerPos.add(thispos);
						entity.setPosition(tempplayerPos.x,
								tempplayerPos.y - (worldObj.isRemote && entity == HMV_Proxy.getEntityPlayerInstance() ? 0:(entity.getEyeHeight()+0.2)),
								tempplayerPos.z);
						entity.posX = tempplayerPos.x;
						entity.posY = tempplayerPos.y - (worldObj.isRemote && entity == HMV_Proxy.getEntityPlayerInstance() ? 0:(entity.getEyeHeight()+0.2));
						entity.posZ = tempplayerPos.z;
					}
					entity.motionX = mc_Entity.motionX;
					entity.motionY = mc_Entity.motionY;
					entity.motionZ = mc_Entity.motionZ;
				} else {
					Vector3d tempplayerPos = new Vector3d(entity == HMV_Proxy.getEntityPlayerInstance() && HMV_Proxy.iszooming() && seatInfos_zoom.length > cnt && seatInfos_zoom[cnt] != null? seatInfos_zoom[cnt].pos: seatInfos[cnt].pos);
					tempplayerPos.sub(new Vector3d(prefab_vehicle.rotcenter));
					tempplayerPos = transformVecByQuat(tempplayerPos, bodyRot);
					transformVecforMinecraft(tempplayerPos);
					tempplayerPos.add(new Vector3d(prefab_vehicle.rotcenter));
//				System.out.println("" + tempplayerPos);
					tempplayerPos.add(thispos);
					entity.setPosition(tempplayerPos.x,
							tempplayerPos.y - (worldObj.isRemote && entity == HMV_Proxy.getEntityPlayerInstance() ? 0:(entity.getEyeHeight()+0.2)),
							tempplayerPos.z);
					entity.posX = tempplayerPos.x;
					entity.posY = tempplayerPos.y - (worldObj.isRemote && entity == HMV_Proxy.getEntityPlayerInstance() ? 0:(entity.getEyeHeight()+0.2));
					entity.posZ = tempplayerPos.z;
					entity.motionX = mc_Entity.motionX;
					entity.motionY = mc_Entity.motionY;
					entity.motionZ = mc_Entity.motionZ;
				}
				seatInfos[cnt].prevSeatOffset_fromV = new Vector3d(seatInfos[cnt].currentSeatOffset_fromV);
				seatInfos[cnt].currentSeatOffset_fromV.sub(new Vector3d(entity.posX, entity.posY, entity.posZ), thispos);
				getVector_local_inRotatedObj(seatInfos[cnt].currentSeatOffset_fromV,seatInfos[cnt].currentSeatOffset_fromV,bodyRot);
				seatInfos[cnt].currentSeatOffset_fromV.z *= -1;
				if(entity.ridingEntity instanceof EntityDummy_rider){
					entity.ridingEntity.setPosition(entity.posX,entity.posY,entity.posZ);
				}
//				entity.ridingEntity = mc_Entity;
//				if(entity instanceof IhasprevRidingEntity)((IhasprevRidingEntity) entity).setprevRidingEntity(mc_Entity);
				
			}
			cnt++;
		}
		mc_Entity.riddenByEntity = null;
	}
	
	private void updateCommon(){
		if(!worldObj.isRemote){
			Quat4d Headrot = new Quat4d(0,0,0,1);
			Headrot = quatRotateAxis(Headrot, new AxisAngle4d(unitX, toRadians(this.cameraPitch) / 2));
			Headrot = quatRotateAxis(Headrot, new AxisAngle4d(unitY, toRadians(this.cameraYaw) / 2));
			this.camerarot.set(Headrot);
			this.camerarot_current.set(this.camerarot);
		}
		if (riddenByEntities[0] instanceof EntityPlayer) {
			Quat4d currentcamRot = new Quat4d(bodyRot);
			currentcamRot.mul(riddenByEntities[0] == HMV_Proxy.getEntityPlayerInstance() && HMV_Proxy.iszooming() && prefab_vehicle.camerarot_zoom != null? prefab_vehicle.camerarot_zoom : camerarot_current);
			double[] cameraxyz = eulerfromQuat((currentcamRot));
			cameraxyz[0] = toDegrees(cameraxyz[0]);
			cameraxyz[1] = toDegrees(cameraxyz[1]);
			cameraxyz[2] = toDegrees(cameraxyz[2]);
			riddenByEntities[0].rotationYaw = (float) cameraxyz[1];
			riddenByEntities[0].prevRotationYaw = (float) cameraxyz[1];
			if(worldObj.isRemote)riddenByEntities[0].setRotationYawHead((float) cameraxyz[1]);
			if(Double.isNaN(cameraxyz[0])){
				cameraxyz[0] = 0;
			}
			riddenByEntities[0].rotationPitch = (float) cameraxyz[0];
			riddenByEntities[0].prevRotationPitch = (float) cameraxyz[0];
		}
	}
	
	
	public void collideWithNearbyEntities()
	{
		List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this.mc_Entity, this.mc_Entity.boundingBox.expand(0.20000000298023224D, 0.0D, 0.20000000298023224D));

		if (list != null && !list.isEmpty()) for (Object o : list) {
			Entity entity = (Entity) o;

			if (entity.canBePushed() && !isRidingEntity(entity)) {
				if (entity.width > 1.5)
					iVehicle.public_collideWithEntity(entity);
				else applyEntityCollision_pushonly(entity);
			}else if(entity instanceof HasBaseLogic){
				iVehicle.public_collideWithEntity(entity);
			}
		}
	}
	
	public void applyEntityCollision(Entity p_70108_1_)
	{
		if (p_70108_1_.riddenByEntity != this.mc_Entity && p_70108_1_.ridingEntity != this.mc_Entity && p_70108_1_.width > 1.5)
		{
			double d0 = p_70108_1_.posX - this.mc_Entity.posX;
			double d1 = p_70108_1_.posZ - this.mc_Entity.posZ;
			double d2 = MathHelper.abs_max(d0, d1);
			
			if (d2 >= 0.009999999776482582D)
			{
				d2 = MathHelper.sqrt_double(d2);
				d0 /= d2;
				d1 /= d2;
				double d3 = 1.0D / d2;
				
				if (d3 > 1.0D)
				{
					d3 = 1.0D;
				}
				
				d0 *= d3;
				d1 *= d3;
				d0 *= 0.05000000074505806D;
				d1 *= 0.05000000074505806D;
				d0 *= 1.0F - this.mc_Entity.entityCollisionReduction;
				d1 *= 1.0F - this.mc_Entity.entityCollisionReduction;
				this.mc_Entity.addVelocity(-d0, 0.0D, -d1);
				p_70108_1_.addVelocity(d0, 0.0D, d1);
			}
		}
	}
	private void applyEntityCollision_pushonly(Entity p_70108_1_)
	{
		if (!isRidingEntity(p_70108_1_))
		{
			double d0 = p_70108_1_.posX - this.mc_Entity.posX;
			double d1 = p_70108_1_.posZ - this.mc_Entity.posZ;
			double d2 = MathHelper.abs_max(d0, d1);

			if (d2 >= 0.009999999776482582D)
			{
				d2 = MathHelper.sqrt_double(d2);
				d0 /= d2;
				d1 /= d2;
				double d3 = 1.0D / d2;

				if (d3 > 1.0D)
				{
					d3 = 1.0D;
				}

				d0 *= d3;
				d1 *= d3;
				d0 *= 0.05000000074505806D;
				d1 *= 0.05000000074505806D;
				d0 *= 1.0F - this.mc_Entity.entityCollisionReduction;
				d1 *= 1.0F - this.mc_Entity.entityCollisionReduction;
				p_70108_1_.addVelocity(d0, 0.0D, d1);
			}
		}
	}
	

	
	private void handleWaterMovement()
	{
		if (ishittingWater())
		{
			if (!iVehicle.getinWater())
			{
				float f = MathHelper.sqrt_double(this.mc_Entity.motionX * this.mc_Entity.motionX * 0.20000000298023224D + this.mc_Entity.motionY * this.mc_Entity.motionY + this.mc_Entity.motionZ * this.mc_Entity.motionZ * 0.20000000298023224D) * 0.2F;
				
				if (f > 1.0F)
				{
					f = 1.0F;
				}
				this.mc_Entity.playSound(prefab_vehicle.splashsound, f, 1.0F + (rand.nextFloat() - rand.nextFloat()) * 0.4F);
			}
			double floatLevel = sinking/current_Draft;

			mc_Entity.motionY+=prefab_vehicle.gravity*floatLevel;
			this.mc_Entity.fallDistance = 0.0F;
			iVehicle.setinWater(true);
		}
		else
		{
			iVehicle.setinWater(false);
		}
		iVehicle.getinWater();
	}
	private boolean inWater;
	public boolean ishittingWater()
	{
		inWater = false;
		sinking = 0;
		if (handleMaterialAcceleration(worldObj,((ModifiedBoundingBox)this.mc_Entity.boundingBox).noMod_copy().expand(0.0D, -0.4000000059604645D, 0.0D).contract(0.001D, 0.001D, 0.001D).offset(0,0,0), Material.water, this.mc_Entity))
		{
			inWater = true;
			sinking = maxWaterHeight - mc_Entity.posY;
			if(sinking > prefab_vehicle.molded_depth)sinking = prefab_vehicle.molded_depth;
		}
		else
		{
			inWater = false;
		}

		return inWater;
	}
	private static double maxWaterHeight = 0;
	private static double sinking = 0;
	private boolean handleMaterialAcceleration(World worldObj, AxisAlignedBB p_72918_1_, Material p_72918_2_, Entity p_72918_3_)
	{
		int i = MathHelper.floor_double(p_72918_1_.minX);
		int j = MathHelper.floor_double(p_72918_1_.maxX + 1.0D);
		int k = MathHelper.floor_double(p_72918_1_.minY);
		int l = MathHelper.floor_double(p_72918_1_.maxY + 1.0D);
		int i1 = MathHelper.floor_double(p_72918_1_.minZ);
		int j1 = MathHelper.floor_double(p_72918_1_.maxZ + 1.0D);
		maxWaterHeight = 0;
		if (!worldObj.checkChunksExist(i, k, i1, j, l, j1))
		{
			return false;
		}
		else
		{
			boolean flag = false;
			Vec3 vec3 = Vec3.createVectorHelper(0.0D, 0.0D, 0.0D);

			for (int k1 = i; k1 < j; ++k1)
			{
				for (int l1 = k; l1 < l; ++l1)
				{
					for (int i2 = i1; i2 < j1; ++i2)
					{
						Block block = worldObj.getBlock(k1, l1, i2);

						if (block.getMaterial() == p_72918_2_)
						{
							double d0 = ((float)(l1 + 1) - BlockLiquid.getLiquidHeightPercent(worldObj.getBlockMetadata(k1, l1, i2)));
							if(d0>maxWaterHeight)maxWaterHeight = d0;
							if (l >= d0)
							{
								flag = true;
								block.velocityToAddToEntity(worldObj, k1, l1, i2, p_72918_3_, vec3);
							}
						}
					}
				}
			}

			if (vec3.lengthVector() > 0.0D && p_72918_3_.isPushedByWater())
			{
				vec3 = vec3.normalize();
				double d1 = 0.014D;
				p_72918_3_.motionX += vec3.xCoord * d1;
				p_72918_3_.motionY += vec3.yCoord * d1;
				p_72918_3_.motionZ += vec3.zCoord * d1;
			}

			return flag;
		}
	}

	public void setPosition(double p_70107_1_, double p_70107_3_, double p_70107_5_)
	{
		mc_Entity.posX = p_70107_1_;
		mc_Entity.posY = p_70107_3_;
		mc_Entity.posZ = p_70107_5_;
		float f = mc_Entity.width / 2.0F;
		float f1 = mc_Entity.height;
		mc_Entity.boundingBox.setBounds(p_70107_1_ - f, p_70107_3_ -  mc_Entity.yOffset +  mc_Entity.ySize, p_70107_5_ - f, p_70107_1_ + f, p_70107_3_ -  mc_Entity.yOffset +  mc_Entity.ySize + f1, p_70107_5_ + f);
		if(mc_Entity.boundingBox instanceof ModifiedBoundingBox){
			((ModifiedBoundingBox) mc_Entity.boundingBox).update(mc_Entity.posX,
					mc_Entity.posY,
					mc_Entity.posZ);
		}
	}

	public void riderPosUpdate_forRender_withoutPlayer(Vector3d thispos,Quat4d currentQuat,float partialTicks){

		int cnt = 0;
		for (Entity entity : riddenByEntities) {
			if (entity != null && !(entity == HMV_Proxy.getEntityPlayerInstance())) {
				Vector3d currentOffsetVec_Local = vector_interior_division(seatInfos[cnt].prevSeatOffset_fromV,seatInfos[cnt].currentSeatOffset_fromV,partialTicks);

				transformVecforMinecraft(currentOffsetVec_Local);
				Vector3d currentOffsetVec = transformVecByQuat(currentOffsetVec_Local, currentQuat);
				transformVecforMinecraft(currentOffsetVec);

				entity.setLocationAndAngles(
						thispos.x + currentOffsetVec.x,
						thispos.y + currentOffsetVec.y - entity.yOffset,
						thispos.z + currentOffsetVec.z,entity.rotationYaw,entity.rotationPitch);
			}
			cnt++;
		}
	}

	public void riderPosUpdate_camera(Vector3d thispos,Quat4d currentQuat,double partialTicks){

		int cnt = 0;
//		System.out.println("thispos  " + thispos);
		Entity entity = riddenByEntities[0];
		if (entity != null) {
			Vector3d currentOffsetVec_Local = vector_interior_division(seatInfos[0].prevSeatOffset_fromV,seatInfos[0].currentSeatOffset_fromV,partialTicks);
//			currentOffsetVec_Local.z *= -1;
			transformVecforMinecraft(currentOffsetVec_Local);
			Vector3d currentOffsetVec = transformVecByQuat(currentOffsetVec_Local, currentQuat);
			transformVecforMinecraft(currentOffsetVec);

			camera.setLocationAndAngles(
					thispos.x + currentOffsetVec.x,
					thispos.y + currentOffsetVec.y - entity.yOffset,
					thispos.z + currentOffsetVec.z,0,0);
		}
	}
	
	public Prefab_Vehicle_Base prefab_vehicle;
	public float health = 150;
//	public float maxhealth = 150;
	public float yawrudder;
	public float rollrudder;
	public float pitchrudder;

	public boolean mouseStickMode = true;
	
	public int mode = 0;//0:attack 1:leave 2:follow player 3:go to home
	//	public double[][] gunpos = new double[6][3];
	public Vector3d forVapour_PrevMotionVec = null;
	private Vector3d unitX = new Vector3d(1,0,0);
	private Vector3d unitY = new Vector3d(0,1,0);
	private Vector3d unitZ = new Vector3d(0,0,1);
	public EntityCameraDummy camera;
	public Quat4d camerarot = new Quat4d(0,0,0,1);
	public Quat4d camerarot_current = new Quat4d(0,0,0,1);
	//	public double[] camerapos_zoom = new double[]{0,2.5-0.21,-3.6};
	
	public float cameraYaw;
	public float cameraPitch;
	
	public int gearprogress;
	public int flaplevel;
	public int brakeLevel;

	
	
	

	
//	public boolean rising_after_Attack;
//
//	public boolean T_useMain_F_useSub = true;
//	public boolean T_StartDive_F_FlyToStartDivePos = true;
//	public int changeWeaponCycle;
//
//	public int outSightCnt = 0;
	
	
	
	
	private boolean serverspace = false;
	private boolean serverw = false;
	private boolean servers = false;
	private boolean servera = false;
	private boolean serverd = false;
	private boolean serverf = false;
	public boolean serverx = false;
	public boolean server_Flare = false;
	public boolean server_easyMode = false;
	public double server_easyMode_pitchTarget = 0;
	public double server_easyMode_yawTarget = 0;
	public boolean server_easyMode_PitchUp = false;
	public boolean server_easyMode_PitchDown = false;
	public boolean server_easyMode_TurnRight = false;
	public boolean server_easyMode_TurnLeft = false;
	public int flare_remain = 0;
	public int flare_cool = 0;
	public int missileAlertTime = 0;
	private boolean server_allow_Entity_Ride = false;
	
	Random rand = new Random();
	
	public BaseLogic(World world, EntityVehicle entity) {
//		gunpos[0][0] = 0.1;
//		gunpos[0][1] = 1.27;
//		gunpos[0][2] = 0.19;
//		gunpos[1][0] = -0.1;
//		gunpos[1][1] = 1.27;
//		gunpos[1][2] = 0.19;
//		gunpos[2][0] = 2.9;
//		gunpos[2][1] = 1.03;
//		gunpos[2][2] = -0.41;
//		gunpos[3][0] = -2.68;
//		gunpos[3][1] = 1.03;
//		gunpos[3][2] = -0.41;
		seatInfos[0] = new SeatInfo();
		seatInfos[0].pos[0] = 0;
		seatInfos[0].pos[1] = 2.3;
		seatInfos[0].pos[2] = 0;
		worldObj = world;
		mc_Entity = entity;
		iVehicle = (IVehicle) entity;
		
		camera = new EntityCameraDummy(this.worldObj);


	}
	public void onUpdate(){
		NaNCheck(bodyRot);
		NaNCheck(rotationmotion);
		Vector3d tailwingvector = transformVecByQuat(new Vector3d(unitY), bodyRot);
		Vector3d bodyvector = transformVecByQuat(new Vector3d(unitZ), bodyRot);
		Vector3d mainwingvector = transformVecByQuat(new Vector3d(unitX), bodyRot);
		tailwingvector.normalize();
		bodyvector.normalize();
		mainwingvector.normalize();
		transformVecforMinecraft(tailwingvector);
		transformVecforMinecraft(bodyvector);
		transformVecforMinecraft(mainwingvector);

		if(receivedPosition != null){
			Vector3d nowPos = new Vector3d(receivedPosition);
			if(prevPos == null){
				prevPos = new Vector3d(nowPos);
			}else {
				Vector3d nowMotion = new Vector3d(mc_Entity.motionX, mc_Entity.motionY, mc_Entity.motionZ);
				if(receivedMotion != null){
					nowMotion = new Vector3d(receivedMotion);
				}
				getVector_local_inRotatedObj(nowMotion, nowMotion, bodyRot);
				if(worldObj.isRemote && prefab_vehicle.wingVapourTrailPoint != null) {
//					System.out.println("debug" + dist.length()*(1-angle * angle));
					if (abs(nowMotion.y) > prefab_vehicle.wingVapourTrailStart) {
						for(int pointID = 0;pointID < prefab_vehicle.wingVapourTrailPoint.length;pointID++) {
							Vector3d point = new Vector3d(prefab_vehicle.wingVapourTrailPoint[pointID]);
							point.sub(prefab_vehicle.rotcenterVec);
							Vector3d globalPoint_now = transformVecByQuat(point, bodyRot);
							Vector3d globalPoint_prev = transformVecByQuat(point, prevbodyRot);
							transformVecforMinecraft(globalPoint_now);
							transformVecforMinecraft(globalPoint_prev);
							globalPoint_now.add(prefab_vehicle.rotcenterVec);
							globalPoint_prev.add(prefab_vehicle.rotcenterVec);
							PacketSpawnParticle packetSpawnParticle = new PacketSpawnParticle(
									prevPos.x + globalPoint_prev.x,
									prevPos.y + globalPoint_prev.y,
									prevPos.z + globalPoint_prev.z,
									nowPos.x + globalPoint_now.x,
									nowPos.y + globalPoint_now.y,
									nowPos.z + globalPoint_now.z, 3);
							packetSpawnParticle.trailwidth = (float) (abs(nowMotion.y)*4);
							packetSpawnParticle.name = "Vapour";
							packetSpawnParticle.fuse = 40;
							packetSpawnParticle.animationspeed = 40;
							HMG_proxy.spawnParticles(packetSpawnParticle);
						}

					}
				}
				prevPos.set(nowPos);
			}
		}
		prevbodyRot.set(bodyRot);
		((ModifiedBoundingBox) mc_Entity.boundingBox).rot.set(this.bodyRot);
		((ModifiedBoundingBox) mc_Entity.boundingBox).update(mc_Entity.posX, mc_Entity.posY, mc_Entity.posZ,this);
		if(!getsound().equals(playingSound))needStartSound = true;
		if(needStartSound){
			needStartSound = false;
			if(mc_Entity.worldObj.isRemote)HMV_Proxy.playsoundasVehicle(1024, mc_Entity);
		}
		needStartSound = getsound() != null;
		prevbodyrotationYaw = bodyrotationYaw;
		prevbodyrotationPitch = bodyrotationPitch;
		prevbodyrotationRoll = bodyrotationRoll;
		if(worldObj.isRemote){
			
			while (this.bodyrotationYaw - this.prevbodyrotationYaw < -180.0F)
			{
				this.prevbodyrotationYaw -= 360.0F;
			}
			
			while (this.bodyrotationYaw - this.prevbodyrotationYaw >= 180.0F)
			{
				this.prevbodyrotationYaw += 360.0F;
			}
			
			
			
			while (this.bodyrotationRoll - this.prevbodyrotationRoll < -180.0F)
			{
				this.prevbodyrotationRoll -= 360.0F;
			}
			
			while (this.bodyrotationRoll - this.prevbodyrotationRoll >= 180.0F)
			{
				this.prevbodyrotationRoll += 360.0F;
			}
			prevbodyrotationYaw=wrapAngleTo180_float(prevbodyrotationYaw);
			bodyrotationYaw = wrapAngleTo180_float(bodyrotationYaw);
			if(this.health <= prefab_vehicle.maxhealth/2) {
				if (this.health <= prefab_vehicle.maxhealth / 4) {
					this.worldObj.spawnParticle("smoke", mc_Entity.posX + 2*mainwingvector.x, mc_Entity.posY + 2*mainwingvector.y, mc_Entity.posZ + 2*mainwingvector.z, 0.0D, 0.0D, 0.0D);
					this.worldObj.spawnParticle("smoke", mc_Entity.posX - 2*mainwingvector.x, mc_Entity.posY - 2*mainwingvector.y, mc_Entity.posZ - 2*mainwingvector.z, 0.0D, 0.0D, 0.0D);
					int rx = this.worldObj.rand.nextInt(5);
					int rz = this.worldObj.rand.nextInt(5);
					this.worldObj.spawnParticle("flame", mc_Entity.posX - 2 + rx, mc_Entity.posY + 2D, mc_Entity.posZ - 2 + rz, 0.0D, 0.0D, 0.0D);
					this.worldObj.spawnParticle("flame", mc_Entity.posX - 2 + rx, mc_Entity.posY + 2D, mc_Entity.posZ - 2 + rz, 0.0D, 0.0D, 0.0D);
				} else {
					this.worldObj.spawnParticle("smoke", mc_Entity.posX + 2, mc_Entity.posY + 2D, mc_Entity.posZ - 1, 0.0D, 0.0D, 0.0D);
				}
			}
			{
				tailwingvector = transformVecByQuat(new Vector3d(unitY), bodyRot);
				bodyvector = transformVecByQuat(new Vector3d(unitZ), bodyRot);
				mainwingvector = transformVecByQuat(new Vector3d(unitX), bodyRot);
				tailwingvector.normalize();
				bodyvector.normalize();
				mainwingvector.normalize();
				transformVecforMinecraft(tailwingvector);
				transformVecforMinecraft(bodyvector);
				transformVecforMinecraft(mainwingvector);
				
				double[] xyz = eulerfromQuat((bodyRot));
				bodyrotationPitch = (float) toDegrees(xyz[0]);
				if(!Double.isNaN(xyz[1])){
					bodyrotationYaw = (float) toDegrees(xyz[1]);
				}
				bodyrotationRoll = (float) toDegrees(xyz[2]);
			}
//			turret(mainwingvector,tailwingvector,bodyvector);
		}else{
			for(int x = (int) mc_Entity.boundingBox.minX+3; x<= mc_Entity.boundingBox.maxX-3; x++){
				for(int y = (int) mc_Entity.boundingBox.minY+3; y<= mc_Entity.boundingBox.maxY-3; y++){
					for(int z = (int) mc_Entity.boundingBox.minZ+3; z<= mc_Entity.boundingBox.maxZ-3; z++){
						Block collidingblock = worldObj.getBlock(x,y,z);
						if(collidingblock.getMaterial() == Material.leaves || collidingblock.getMaterial() == Material.wood || collidingblock.getMaterial() == Material.glass || collidingblock.getMaterial() == Material.cloth){
							worldObj.setBlockToAir(x,y,z);
						}
					}
				}
			}
			
//			FCS(mainwingvector,tailwingvector,bodyvector);
//			if(mc_Entity instanceof Hasmode && ((Hasmode) mc_Entity).standalone()){
//				autocontrol(bodyvector);
//			}
//			turret(mainwingvector,tailwingvector,bodyvector);
			
			double[] xyz = eulerfromQuat((bodyRot));
			bodyrotationPitch = (float) toDegrees(xyz[0]);
			if(!Double.isNaN(xyz[1])){
				bodyrotationYaw = (float) toDegrees(xyz[1]);
			}
			bodyrotationRoll = (float) toDegrees(xyz[2]);
			

			mc_Entity.rotationPitch = bodyrotationPitch;

			if(mc_Entity instanceof IMultiTurretVehicle){
				HMVPacketHandler.INSTANCE.sendToAll(new HMVPakcetVehicleTurretSync(mc_Entity.getEntityId(), (IMultiTurretVehicle) mc_Entity));
			}
		}
		control(bodyvector,mainwingvector);
		prev_pera_trackPos = pera_trackPos;
		pera_trackPos += throttle*1.8f;
		if(pera_trackPos > prefab_vehicle.max_pera_trackPos){
			float temp_pera_trackPos = pera_trackPos%prefab_vehicle.max_pera_trackPos;
			prev_pera_trackPos += temp_pera_trackPos-pera_trackPos;
			pera_trackPos = temp_pera_trackPos;
		}
		if(pera_trackPos < 0){
			float temp_pera_trackPos = (prefab_vehicle.max_pera_trackPos + pera_trackPos)%prefab_vehicle.max_pera_trackPos;
			prev_pera_trackPos += temp_pera_trackPos-pera_trackPos;
			pera_trackPos = temp_pera_trackPos;
		}


		motionUpdate(mainwingvector,tailwingvector,bodyvector);
		updateCommon();
		updateRider();
		riderPosUpdate();
	}
	

	void control(Vector3d bodyvector , Vector3d wingVector){


		if(health < 0){
			throttle = 0;
		}else {
			if (serverspace) {
				if (abs(throttle) < prefab_vehicle.throttle_speed) throttle = 0;
				if (throttle > 0) {
					throttle -= prefab_vehicle.throttle_speed;
				} else if (throttle < 0) {
					throttle += prefab_vehicle.throttle_speed;
				}
			}
			if (this.mc_Entity.getEntityData().getBoolean("flare")) {
				this.mc_Entity.getEntityData().setBoolean("flare", false);
			}
			if (!worldObj.isRemote && prefab_vehicle.hasFlare) {
				flare_cool--;
				if (riddenByEntities[getpilotseatid()] instanceof EntityPlayerMP && this.mc_Entity.getEntityData().getBoolean("behome")) {
					if (missileAlertTime < 0) {
						HMGPacketHandler.INSTANCE.sendTo(new PacketPlaySound_Gui(prefab_vehicle.missileAlertSound, 1), (EntityPlayerMP) riddenByEntities[getpilotseatid()]);
						missileAlertTime = prefab_vehicle.missileAlertCool;
					}
					missileAlertTime--;
				} else {
					missileAlertTime = 0;
				}
				if (server_Flare && flare_cool < 0 && flare_remain > 0) {
					HMGEntityBullet_TE flare1 = new HMGEntityBullet_TE(worldObj, this.mc_Entity, 0, 0, 0, 0, true, "byfrou01_Flare");
					flare1.resistance = 0.99f;
					flare1.motionX += wingVector.x / 5 + mc_Entity.motionX;
					flare1.motionY += wingVector.y / 5 + mc_Entity.motionY;
					flare1.motionZ += wingVector.z / 5 + mc_Entity.motionZ;
					flare1.gra = 0.049f;
					flare1.fuse = 80;
					worldObj.spawnEntityInWorld(flare1);
					HMGEntityBullet_TE flare2 = new HMGEntityBullet_TE(worldObj, this.mc_Entity, 0, 0, 0, 0, true, "byfrou01_Flare");
					flare2.resistance = 0.99f;
					flare2.motionX -= wingVector.x / 5 - mc_Entity.motionX;
					flare2.motionY -= wingVector.y / 5 - mc_Entity.motionY;
					flare2.motionZ -= wingVector.z / 5 - mc_Entity.motionZ;
					flare2.posX -= wingVector.x;
					flare2.posY -= wingVector.y;
					flare2.posZ -= wingVector.z;
					flare2.gra = 0.049f;
					flare2.fuse = 80;
					worldObj.spawnEntityInWorld(flare2);
					this.mc_Entity.getEntityData().setBoolean("flare", true);
					if (riddenByEntities[getpilotseatid()] instanceof EntityPlayerMP)
						HMGPacketHandler.INSTANCE.sendTo(new PacketPlaySound_Gui(prefab_vehicle.flareSound, 1), (EntityPlayerMP) riddenByEntities[getpilotseatid()]);

					flare_remain--;
					flare_cool = 10;
				}
				this.mc_Entity.getEntityData().setBoolean("behome", false);
			}
		}
		if(pitchrudder > 16)pitchrudder = 16;
		if(pitchrudder <-16)pitchrudder =-16;
		if(rollrudder > 16)rollrudder = 16;
		if(rollrudder <-16)rollrudder =-16;
		if(!worldObj.isRemote) {
			if (health > 0 && !serverspace) {
				if (serverw) {
					throttle += prefab_vehicle.throttle_speed;
					serverw = false;
				}
				if (servers) {
					throttle -= prefab_vehicle.throttle_speed;
					servers = false;
				}
			}

			if(server_allow_Entity_Ride && mc_Entity instanceof EntityVehicle){
				server_allow_Entity_Ride = false;
				((EntityVehicle) mc_Entity).canUseByMob = !((EntityVehicle) mc_Entity).canUseByMob;
				if(riddenByEntities[getpilotseatid()] instanceof EntityPlayerMP){
					if(((EntityVehicle) mc_Entity).canUseByMob){
						((EntityPlayerMP)riddenByEntities[getpilotseatid()]).addChatComponentMessage(new ChatComponentTranslation("Mobs can use this vehicle"));
					}else {
						((EntityPlayerMP)riddenByEntities[getpilotseatid()]).addChatComponentMessage(new ChatComponentTranslation("Mobs can not use this vehicle"));
					}
				}
			}

			if(health > 0) {
				if (!prefab_vehicle.T_Land_F_Plane && (server_easyMode || (riddenByEntities[getpilotseatid()]) instanceof EntityLiving)) {
					turn(bodyvector);
				} else {
					{
						double yaw_Target = 0;
						if (servera || serverd) {
							yaw_Target = yawrudder;
						}
						if (servera) {
							yaw_Target = -16;
						} else if (serverd) {
							yaw_Target = 16;
						}
						if (abs(yaw_Target - yawrudder) < 1) yawrudder = (float) (yaw_Target);
						else if (yaw_Target > yawrudder) {
							yawrudder += 1;
						} else if (yaw_Target < yawrudder) {
							yawrudder -= 1;
						}
					}
				}
			}
		} else {
			ArrayList<Integer> keys = new ArrayList<>();
			if(ispilot(HMV_Proxy.getEntityPlayerInstance())){
				if(HMG_proxy.getMCInstance().inGameHasFocus && HMG_proxy.getMCInstance().currentScreen == null) {
					if (HMV_Proxy.leftclick()) {
						keys.add(11);
					}
					if (HMV_Proxy.rightclick()) {
						keys.add(12);
					}
					if (HMV_Proxy.throttle_BrakeKeyDown()) {
						keys.add(13);
					}
					if (serverx = HMV_Proxy.air_Brake_click()) {
						keys.add(14);
					}
					if (server_Flare = HMV_Proxy.flare_Smoke_click()) {
						keys.add(15);
					}
					if (HMV_Proxy.changeEasyControlMode()) {
						server_easyMode = !server_easyMode;
						keys.add(0);
					}
					if (server_easyMode_PitchDown = HMV_Proxy.pitchDown()) {
						keys.add(1);
					}
					if (server_easyMode_PitchUp = HMV_Proxy.pitchUp()) {
						keys.add(2);
					}
					if (server_easyMode_TurnLeft = HMV_Proxy.rollLeft()) {
						keys.add(3);
					}
					if (server_easyMode_TurnRight = HMV_Proxy.rollRight()) {
						keys.add(4);
					}
					if (HMV_Proxy.openGUIKeyDown()) {
						keys.add(5);
					}
					if (HMV_Proxy.changeControlclick()) {
						System.out.println("debug");
						mouseStickMode = !mouseStickMode;
						if(prefab_vehicle.T_Land_F_Plane) {
							seatInfos[getpilotseatid()].syncToPlayerAngle = mouseStickMode;
							String message;
							if (seatInfos[getpilotseatid()].syncToPlayerAngle) {
								message = "Aim : On";
							} else {
								message = "Aim : Off";
							}
							HMV_Proxy.getEntityPlayerInstance().addChatComponentMessage(new ChatComponentTranslation(message));
						}
					}
					if (serverf = HMV_Proxy.flap_click()) {
						keys.add(20);
					}
					if (server_allow_Entity_Ride = HMV_Proxy.allow_Entity_Ride_click()) {
						keys.add(21);
					}
					if (!FMLClientHandler.instance().getClient().isGamePaused() && Display.isActive()) {
						double throttle_Target = throttle;
						double yaw_Target = 0;
						double pitch_Target = 0;
						double roll_Target = 0;
						int tempMouseDX = mouseStickMode && !prefab_vehicle.T_Land_F_Plane ? Mouse.getDX() : 0;
						int tempMouseDY = mouseStickMode && !prefab_vehicle.T_Land_F_Plane ? Mouse.getDY() : 0;
						if (cfgControl_useStick && HMV_Proxy.hasStick()) {
							if (!mouseStickMode || tempMouseDX != 0 || HMV_Proxy.getXaxis() != 0)
								roll_Target = HMV_Proxy.getXaxis() * 16;
							yaw_Target = HMV_Proxy.getZaxis() * 16;
							if (!mouseStickMode || tempMouseDY != 0 || HMV_Proxy.getYaxis() != 0)
								pitch_Target = -HMV_Proxy.getYaxis() * 16;
							double throttle_percent = HMV_Proxy.getZaxis2();
//							System.out.println(throttle_percent);
							if (throttle_percent > 0) {
								throttle_Target = throttle_percent * prefab_vehicle.throttle_Max;
							} else {
								throttle_Target = -throttle_percent * prefab_vehicle.throttle_min;
							}
						}
						if (mouseStickMode) {
							roll_Target += tempMouseDX * 4;
							pitch_Target += tempMouseDY * 4;
//					cameraYaw = 0;
//					cameraPitch = 0;
						}
						if (HMV_Proxy.resetCamrotclick()) {
							cameraYaw = 0;
							cameraPitch = 0;
						}
						if (!server_easyMode) {
							if (HMV_Proxy.pitchUp()) pitch_Target = 16;
							if (HMV_Proxy.pitchDown()) pitch_Target = -16;
							if (HMV_Proxy.rollRight()) roll_Target = 16;
							if (HMV_Proxy.rollLeft()) roll_Target = -16;
						}
						if (HMV_Proxy.throttle_BrakeKeyDown()) throttle_Target = 0;
						if (HMV_Proxy.throttle_up_click()) throttle_Target = prefab_vehicle.throttle_Max;
						if (HMV_Proxy.throttle_down_click()) throttle_Target = prefab_vehicle.throttle_min;

						if (servera = HMV_Proxy.yaw_Left_click()) {
							yaw_Target = -16;
						}
						if (serverd = HMV_Proxy.yaw_Right_click()) {
							yaw_Target = 16;
						}
						if (abs(throttle_Target - throttle) < prefab_vehicle.throttle_speed)
							throttle = (float) (throttle_Target);
						else if (throttle_Target > throttle) {
							throttle += prefab_vehicle.throttle_speed;
						} else if (throttle_Target < throttle) {
							throttle -= prefab_vehicle.throttle_speed;
						}
						if (pitch_Target > 16) pitch_Target = 16;
						if (pitch_Target < -16) pitch_Target = -16;

						if (roll_Target > 16) roll_Target = 16;
						if (roll_Target < -16) roll_Target = -16;

						if (yaw_Target > 16) yaw_Target = 16;
						if (yaw_Target < -16) yaw_Target = -16;

						if (abs(pitch_Target - pitchrudder) < prefab_vehicle.rudderSpeed)
							pitchrudder = (float) (pitch_Target);
						else if (pitch_Target > pitchrudder) {
							pitchrudder += prefab_vehicle.rudderSpeed;
						} else if (pitch_Target < pitchrudder) {
							pitchrudder -= prefab_vehicle.rudderSpeed;
						}

						if (abs(roll_Target - rollrudder) < prefab_vehicle.rudderSpeed)
							rollrudder = (float) (roll_Target);
						else if (roll_Target > rollrudder) {
							rollrudder += prefab_vehicle.rudderSpeed;
						} else if (roll_Target < rollrudder) {
							rollrudder -= prefab_vehicle.rudderSpeed;
						}

						if (abs(yaw_Target - yawrudder) < prefab_vehicle.rudderSpeed) yawrudder = (float) (yaw_Target);
						else if (yaw_Target > yawrudder) {
							yawrudder += prefab_vehicle.rudderSpeed;
						} else if (yaw_Target < yawrudder) {
							yawrudder -= prefab_vehicle.rudderSpeed;
						}
						HMVPacketHandler.INSTANCE.sendToServer(new HMVPacketMouseD(rollrudder,
								pitchrudder, yawrudder, throttle, cameraYaw, cameraPitch, mc_Entity.getEntityId(), true));
					}
				}
			}
			int[] keys_array = new int[keys.size()];
			for (int id = 0; id < keys_array.length; id++) {
				keys_array[id] = keys.get(id);
			}
			HMVPacketHandler.INSTANCE.sendToServer(new HMVMMessageKeyPressed(keys_array, mc_Entity.getEntityId()));
		}
//		if(!worldObj.isRemote)System.out.println("" + yawladder);

		
	}
	float yawRudder_Target;
	float pitchRudder_Target;
	float rollRudder_Target;

	public Vector3d getTargetLook()
	{
		double f1;
		double f2;
		double f3;
		double f4;

		double yawDiff = wrapAngleTo180_double(this.server_easyMode_yawTarget-bodyrotationYaw);
		double pitchDiff = wrapAngleTo180_double(this.server_easyMode_pitchTarget - this.bodyrotationPitch);

		if(abs(yawDiff)>45){
			yawDiff /= abs(yawDiff)/45;
		}
		if(abs(pitchDiff)>45){
			pitchDiff /= abs(pitchDiff)/45;
		}
		f1 = cos(-yawDiff * 0.017453292F - (float)Math.PI);
		f2 = sin(-yawDiff * 0.017453292F - (float)Math.PI);
		f3 = -cos(-pitchDiff * 0.017453292F);
		f4 = sin(-pitchDiff * 0.017453292F);
		return new Vector3d((f2 * f3), f4, (f1 * f3));
	}
	public void turn(Vector3d bodyvector){

		yawRudder_Target = 0;
		pitchRudder_Target = 0;
		rollRudder_Target = 0;

		Vector3d targetVector_Global = getTargetLook();
		Vector3d targetVector_Local = new Vector3d(targetVector_Global);
		RotateVectorAroundZ(targetVector_Local,-bodyrotationRoll);
		targetVector_Local.scale(-8);

		double rollTarget = 0;
		double dist = wrapAngleTo180_double(server_easyMode_yawTarget-bodyrotationYaw) * abs(cos(toRadians(server_easyMode_pitchTarget)));
		boolean dir = dist>0;
		dist = abs(dist);
		if(dist>15){
			double cof = (dist-15)/40;
			if(cof>1)cof = 1;
			rollTarget = prefab_vehicle.maxbank * cof;
			if(!dir)rollTarget *= -1;
		}
		double rollDiff = wrapAngleTo180_double(rollTarget-bodyrotationRoll)/prefab_vehicle.rollspeed / 100;

		rollRudder_Target+=rollDiff;
		double[] xyz = eulerfromQuat((rotationmotion));

		Vector3d motionvec = new Vector3d(mc_Entity.motionX, mc_Entity.motionY, mc_Entity.motionZ);
		double currentForcedEffect = (mc_Entity.onGround ? prefab_vehicle.forced_rudder_effect_OnGround : prefab_vehicle.forced_rudder_effect) + (inWater ? prefab_vehicle.forced_rudder_effect_InWater * (sinking/prefab_vehicle.molded_depth):0);
		double cof = 0;
		if(motionvec.length() > 0.001)cof = -angle_cos(bodyvector, motionvec) * (motionvec.length());
		cof += (currentForcedEffect * throttle);

		targetVector_Local.scale(targetVector_Local.length());
		yawRudder_Target = (float) (targetVector_Local.x / prefab_vehicle.yawspeed/cof * 4 / 32);
		pitchRudder_Target = (float) (targetVector_Local.y / prefab_vehicle.pitchspeed/cof / 32);

		if(abs(yawRudder_Target) > 16){
			yawRudder_Target*=16/abs(yawRudder_Target);
			pitchRudder_Target*=16/abs(yawRudder_Target);
		}
		if(abs(pitchRudder_Target) > 16){
			yawRudder_Target*=16/abs(pitchRudder_Target);
			pitchRudder_Target*=16/abs(pitchRudder_Target);
		}

		{
			double different = 1 + (targetVector_Local.x * targetVector_Local.x + targetVector_Local.y * targetVector_Local.y);
//
//
//			boolean yawFlag = yawRudder_Target < 0;
//			boolean pitchFlag = pitchRudder_Target < 0;
			if(!prefab_vehicle.type_F_Plane_T_Heli) {
				yawRudder_Target -= abs(localMotionVec.z) * xyz[1] / prefab_vehicle.yawspeed/cof / prefab_vehicle.rotmotion_reduceSpeed * 4 /different * 4;
				pitchRudder_Target -= abs(localMotionVec.z) * xyz[0] / prefab_vehicle.pitchspeed/cof / prefab_vehicle.rotmotion_reduceSpeed /different * 4;
				yawRudder_Target += localMotionVec.x * prefab_vehicle.stability_roll / prefab_vehicle.yawspeed/cof * 4 / different * 16;
				pitchRudder_Target -= localMotionVec.y * prefab_vehicle.stability_roll / prefab_vehicle.pitchspeed/cof / different * 16;
			}else{
				yawRudder_Target -= abs(localMotionVec.z) * xyz[1] / prefab_vehicle.yawspeed/cof / prefab_vehicle.rotmotion_reduceSpeed * 4 /different * 64;
				pitchRudder_Target -= abs(localMotionVec.z) * xyz[0] / prefab_vehicle.pitchspeed/cof / prefab_vehicle.rotmotion_reduceSpeed /different * 64;
//				yawRudder_Target += localMotionVec.x * prefab_vehicle.stability_roll / prefab_vehicle.yawspeed/cof * 4 / different;
//				pitchRudder_Target -= localMotionVec.y * prefab_vehicle.stability_roll / prefab_vehicle.pitchspeed/cof / different;
			}
//			if(yawFlag != yawRudder_Target < 0)yawRudder_Target = 0;
//			if(pitchFlag != pitchRudder_Target < 0)pitchRudder_Target = 0;
		}









		if(rollRudder_Target > 16) rollRudder_Target = 16;
		if(rollRudder_Target <-16) rollRudder_Target =-16;
		if(abs(yawRudder_Target) > 16){
			yawRudder_Target*=16/abs(yawRudder_Target);
			pitchRudder_Target*=16/abs(yawRudder_Target);
		}
		if(abs(pitchRudder_Target) > 16){
			yawRudder_Target*=16/abs(pitchRudder_Target);
			pitchRudder_Target*=16/abs(pitchRudder_Target);
		}

		if(abs(pitchRudder_Target - pitchrudder) < prefab_vehicle.rudderSpeed)pitchrudder = (float) (pitchRudder_Target);
		else
		if(pitchRudder_Target > pitchrudder){
			pitchrudder +=prefab_vehicle.rudderSpeed;
		}else if(pitchRudder_Target < pitchrudder){
			pitchrudder -=prefab_vehicle.rudderSpeed;
		}
		if(abs(rollRudder_Target - rollrudder) < prefab_vehicle.rudderSpeed)rollrudder = (float) (rollRudder_Target);
		else
		if(rollRudder_Target > rollrudder){
			rollrudder +=prefab_vehicle.rudderSpeed;
		}else if(rollRudder_Target < rollrudder){
			rollrudder -=prefab_vehicle.rudderSpeed;
		}
		if(abs(yawRudder_Target - yawrudder) < prefab_vehicle.rudderSpeed)yawrudder = (float) (yawRudder_Target);
		else
		if(yawRudder_Target > yawrudder){
			yawrudder += prefab_vehicle.rudderSpeed;
		}else if(yawRudder_Target < yawrudder){
			yawrudder -= prefab_vehicle.rudderSpeed;
		}

//		getVector_local_inRotatedObj(targetVector_Local,targetVector_Global,bodyRot);
//
//		boolean yaw_useYawRudder = (abs(bodyrotationRoll)<60 || abs(bodyrotationRoll)>120) && pitchTarget<10;
//		boolean yaw_revertYawRudder = abs(bodyrotationRoll)>90;
//		boolean yaw_usePitchRudder = abs(bodyrotationRoll)>45&& abs(bodyrotationRoll)<135 && ((abs(bodyrotationRoll) < 90 && pitchTarget < 10) || (abs(bodyrotationRoll) > 90 && pitchTarget > -10)) && dir == bodyrotationRoll>0;
//
//		boolean pitch_useYawRudder = abs(bodyrotationRoll)>45 && abs(bodyrotationRoll)<135;
////		System.out.println("debug" + pitch_useYawRudder);
//		boolean pitch_revertPitchRudder = abs(bodyrotationRoll)>90;
//		boolean pitch_usePitchRudder = true;
//
//
//		dist = abs(dist);
//		double rollTarget = 0;
//		if(dist>15){
//			rollTarget = prefab_vehicle.maxbank * (dist-15)/30;
//			if(rollTarget>prefab_vehicle.maxbank)rollTarget = prefab_vehicle.maxbank;
//			if(!dir)rollTarget *= -1;
//		}
//		double rollDiff = wrapAngleTo180_double(rollTarget-bodyrotationRoll);
//		if(rollDiff<0){
//			rollRudder_Target-=rollDiff * rollDiff/16;
//		}else if(rollDiff>0){
//			rollRudder_Target+=rollDiff * rollDiff/16;
//		}
//
//		if(dist>16)dist = 16;
//		if(dir){
//			if(yaw_useYawRudder){
//				if(!yaw_revertYawRudder) yawRudder_Target +=dist;
//				else yawRudder_Target -=dist;
//			}
//		}else {
//			if(yaw_useYawRudder){
//				if(!yaw_revertYawRudder) yawRudder_Target -=dist;
//				else yawRudder_Target +=dist;
//			}
//		}
//		if(yaw_usePitchRudder){
//			pitchRudder_Target -=dist;
//		}
//		if(pitchDir){
//			//Down
//			if(pitch_revertPitchRudder){
//				if (abs(bodyrotationRoll) < 10 || abs(bodyrotationRoll)>170)
//					pitchRudder_Target -= pitchTarget;
//				else if (dir != bodyrotationRoll < 0) {
//					pitchRudder_Target -= pitchTarget;
//				}
//			}else {
//				if (abs(bodyrotationRoll) < 10 || abs(bodyrotationRoll)>170)
//					pitchRudder_Target += pitchTarget;
//				else if (dir == bodyrotationRoll < 0) {
//					pitchRudder_Target += pitchTarget;
//				}
//			}
//			if(pitch_useYawRudder) {
//				if (bodyrotationRoll < 0){yawRudder_Target -= pitchTarget;
//				}
//				else {yawRudder_Target += pitchTarget;
//				}
//			}
//		}else {
//			//Up
//			if(pitch_revertPitchRudder){
//				if (abs(bodyrotationRoll) < 10 || abs(bodyrotationRoll)>170)
//					pitchRudder_Target += pitchTarget;
//				else if (dir == bodyrotationRoll < 0) {
//					pitchRudder_Target += pitchTarget;
//				}
//			}else {
//				if (abs(bodyrotationRoll) < 10 || abs(bodyrotationRoll)>170)
//					pitchRudder_Target -= pitchTarget;
//				else if (dir != bodyrotationRoll < 0) {
//					pitchRudder_Target -= pitchTarget;
//				}
//			}
//			if(pitch_useYawRudder) {
//				if (bodyrotationRoll < 0){yawRudder_Target += pitchTarget;
//				}
//				else {yawRudder_Target -= pitchTarget;
//				}
//			}
//		}
//		if(!prefab_vehicle.type_F_Plane_T_Heli)yawRudder_Target += localMotionVec.x;
//
//
//		if(pitchRudder_Target > 16) pitchRudder_Target = 16;
//		if(pitchRudder_Target <-16) pitchRudder_Target =-16;
//
//		if(rollRudder_Target > 16) rollRudder_Target = 16;
//		if(rollRudder_Target <-16) rollRudder_Target =-16;
//
//		if(yawRudder_Target > 16) yawRudder_Target = 16;
//		if(yawRudder_Target <-16) yawRudder_Target =-16;
//
//		if(abs(pitchRudder_Target - pitchrudder) < prefab_vehicle.rudderSpeed)pitchrudder = (float) (pitchRudder_Target);
//		else
//		if(pitchRudder_Target > pitchrudder){
//			pitchrudder +=prefab_vehicle.rudderSpeed;
//		}else if(pitchRudder_Target < pitchrudder){
//			pitchrudder -=prefab_vehicle.rudderSpeed;
//		}
//
//		if(abs(rollRudder_Target - rollrudder) < prefab_vehicle.rudderSpeed)rollrudder = (float) (rollRudder_Target);
//		else
//		if(rollRudder_Target > rollrudder){
//			rollrudder +=prefab_vehicle.rudderSpeed;
//		}else if(rollRudder_Target < rollrudder){
//			rollrudder -=prefab_vehicle.rudderSpeed;
//		}
//
//		if(abs(yawRudder_Target - yawrudder) < prefab_vehicle.rudderSpeed)yawrudder = (float) (yawRudder_Target);
//		else
//		if(yawRudder_Target > yawrudder){
//			yawrudder += prefab_vehicle.rudderSpeed;
//		}else if(yawRudder_Target < yawrudder){
//			yawrudder -= prefab_vehicle.rudderSpeed;
//		}

	}

	public void setKeyStateFromArray(int[] keys){

		if(health > 0) {
			setControl_Space(false);
			setControl_brake(false);
			setControl_throttle_up(false);
			setControl_yaw_Left(false);
			setControl_throttle_down(false);
			setControl_yaw_Right(false);
			setControl_flap(false);
			setControl_Flare(false);

			server_easyMode_PitchDown = false;
			server_easyMode_PitchUp = false;
			server_easyMode_TurnLeft = false;
			server_easyMode_TurnRight = false;
			server_easyMode_pitchTarget = 0;

			for (int i : keys) {
				switch (i) {
//				case 11:
//					setControl_LeftClick(true);
//					break;
//				case 12:
//					setControl_RightClick(true);
//					break;
					case 0:
						server_easyMode = !server_easyMode;
						if (riddenByEntities[getpilotseatid()] instanceof EntityPlayerMP) {
							if (server_easyMode) {
								((EntityPlayerMP) riddenByEntities[getpilotseatid()]).addChatComponentMessage(new ChatComponentTranslation("EasyControlMode"));
							} else {
								((EntityPlayerMP) riddenByEntities[getpilotseatid()]).addChatComponentMessage(new ChatComponentTranslation("NormalControlMode"));
							}
						}
						break;
					case 1:
						server_easyMode_PitchDown = true;
						server_easyMode_pitchTarget -= 20;

						if (current_onground_pitch != prefab_vehicle.onground_pitch
								&& current_onground_pitch != prefab_vehicle.susPitch_down) {
							current_onground_pitch = prefab_vehicle.onground_pitch;
						} else {
							current_onground_pitch = prefab_vehicle.susPitch_down;
						}

						break;
					case 2:
						server_easyMode_PitchUp = true;
						server_easyMode_pitchTarget += 20;

						if (current_onground_pitch != prefab_vehicle.onground_pitch
								&& current_onground_pitch != prefab_vehicle.susPitch_up) {
							current_onground_pitch = prefab_vehicle.onground_pitch;
						} else {
							current_onground_pitch = prefab_vehicle.susPitch_up;
						}
						break;
					case 3:
						server_easyMode_TurnLeft = true;
						server_easyMode_yawTarget = bodyrotationYaw - 45;

						if (current_onground_roll != prefab_vehicle.onground_roll
								&& current_onground_roll != prefab_vehicle.susRoll_left) {
							current_onground_roll = prefab_vehicle.onground_roll;
						} else {
							current_onground_roll = prefab_vehicle.susRoll_left;
						}
						break;
					case 4:
						server_easyMode_TurnRight = true;
						server_easyMode_yawTarget = bodyrotationYaw + 45;

						if (current_onground_roll != prefab_vehicle.onground_roll
								&& current_onground_roll != prefab_vehicle.susRoll_right) {
							current_onground_roll = prefab_vehicle.onground_roll;
						} else {
							current_onground_roll = prefab_vehicle.susRoll_right;
						}
						break;
					case 5:
						Entity opener = riddenByEntities[getpilotseatid()];
						if (opener instanceof EntityPlayer) {
							((EntityPlayer) opener).openGui(HMVehicle.INSTANCE, 0, opener.worldObj, (int) opener.posX, (int) opener.posY, (int) opener.posZ);
						}
						break;
					case 13:
						setControl_Space(true);
						break;
					case 14:
						setControl_brake(true);
						break;
					case 15:
						setControl_Flare(true);
						break;
					case 16:
						setControl_throttle_up(true);
						break;
					case 17:
						setControl_yaw_Left(true);
						break;
					case 18:
						setControl_throttle_down(true);
						break;
					case 19:
						setControl_yaw_Right(true);
						break;
					case 20:
						setControl_flap(true);
						break;
					case 21:
						server_allow_Entity_Ride = true;
						break;
				}
			}
		}
	}
	
//	void FCS(Vector3d mainwingvector,Vector3d tailwingvector,Vector3d bodyvector){
//		if (trigger1) {
//			if(mainTurret != null){
//				if(riddenByEntities[iVehicle.getpilotseatid()] != null)mainTurret.currentEntity = riddenByEntities[iVehicle.getpilotseatid()];
//				mainTurret.currentEntity.motionX = this.mc_Entity.motionX;
//				mainTurret.currentEntity.motionY = this.mc_Entity.motionY;
//				mainTurret.currentEntity.motionZ = this.mc_Entity.motionZ;
//				mainTurret.fireall();
//			}
////			for(int i = 0;i<2;i++){
////				HMGEntityBullet var3 = new HMGEntityBullet(this.worldObj, riddenByEntities[0].riddenByEntity, 40, 8, 3);
////				var3.setLocationAndAngles(
////						planebody.posX + mainwingvector.x * gunpos[i][0] +     tailwingvector.x * (gunpos[i][1] - 2.5) - bodyvector.x * gunpos[i][2]
////						, planebody.posY + mainwingvector.y * gunpos[i][0] + 2 + tailwingvector.y * (gunpos[i][1] - 2.5) - bodyvector.y * gunpos[i][2]
////						, planebody.posZ + mainwingvector.z * gunpos[i][0] +     tailwingvector.z * (gunpos[i][1] - 2.5) - bodyvector.z * gunpos[i][2]
////						,bodyrotationYaw,bodyrotationPitch);
//////						var3.setHeadingFromThrower(bodyrotationPitch, this.bodyrotationYaw, 0, 8, 10F);
////				var3.motionX = planebody.motionX + bodyvector.x * -6 + this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : 1) * 0.01 * 3;
////				var3.motionY = planebody.motionY + bodyvector.y * -6 + this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : 1) * 0.01 * 3;
////				var3.motionZ = planebody.motionZ + bodyvector.z * -6 + this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : 1) * 0.01 * 3;
////				var3.bulletTypeName = "byfrou01_GreenTracer";
////				this.worldObj.spawnEntityInWorld(var3);
////			}
////			if(planebody.getEntityData().getFloat("GunshotLevel")<4)
////				soundedentity.add(planebody);
////			planebody.getEntityData().setFloat("GunshotLevel",4);
////			planebody.playSound("gvcguns:gvcguns.fire", 4.0F, 0.5F);
//			trigger1 = false;
//		}
//		if(trigger2){
//			if(subTurret != null){
//				if(riddenByEntities[iVehicle.getpilotseatid()] != null)subTurret.currentEntity = riddenByEntities[iVehicle.getpilotseatid()];
//				subTurret.currentEntity.motionX = this.mc_Entity.motionX;
//				subTurret.currentEntity.motionY = this.mc_Entity.motionY;
//				subTurret.currentEntity.motionZ = this.mc_Entity.motionZ;
//				subTurret.fireall();
//			}
//			trigger2 = false;
//		}
//	}
	
	void motionUpdate(Vector3d mainwingvector,Vector3d tailwingvector,Vector3d bodyvector){
		NaNCheck(tailwingvector);
		NaNCheck(bodyvector);
		prevlocalMotionVec.set(localMotionVec);

		if(serverx)brakeLevel++;
		else brakeLevel--;

		if(brakeLevel > 20){
			brakeLevel = 20;
		}else if(brakeLevel < 0){
			brakeLevel = 0;
		}

		float weaponWeight = 0;
		for(TurretObj aturret:allturrets){
			if(aturret.gunItem != null){
				weaponWeight += aturret.gunItem.gunInfo.weight;
			}
		}

		if(worldObj.isRemote) {
			if (receivedPosition != null) {
				mc_Entity.posX = receivedPosition.x;
				mc_Entity.posY = receivedPosition.y;
				mc_Entity.posZ = receivedPosition.z;
				setPosition(mc_Entity.posX, mc_Entity.posY, mc_Entity.posZ);
				receivedPosition = null;
			}
			if (receivedMotion != null) {
				mc_Entity.motionX = receivedMotion.x;
				mc_Entity.motionY = receivedMotion.y;
				mc_Entity.motionZ = receivedMotion.z;
				receivedMotion = null;
			}
			if (serverSideBodyRot != null) {
				this.bodyRot.set(serverSideBodyRot);
				serverSideBodyRot = null;
			}
			if (serverSideRotationmotion != null) {
				this.rotationmotion.set(serverSideRotationmotion);
				serverSideRotationmotion = null;
			}
		}
		Vector3d motionvec = new Vector3d(mc_Entity.motionX, mc_Entity.motionY, mc_Entity.motionZ);

		Vector3d windvec = new Vector3d(motionvec);
		if (mc_Entity.onGround && windvec.y < 0) windvec.y = 0;
		if (windvec.lengthSquared() > 0) {
			Vector3d axisstall = new Vector3d();
			windvec.normalize();
			windvec.add(new Vector3d(bodyvector.x * 0.3,
					bodyvector.y * 0.3,
					bodyvector.z * 0.3));
			axisstall.cross(bodyvector, windvec);
			NaNCheck(axisstall);
			if(axisstall.lengthSquared()>0) {
				axisstall.normalize();
				axisstall.z = -axisstall.z;
				Quat4d quat4d = new Quat4d(0, 0, 0, 1);
				inverse_safe(bodyRot, quat4d);
				NaNCheck(quat4d);
				NaNCheck(axisstall);
				axisstall = transformVecByQuat(axisstall, quat4d);
				if (!Double.isNaN(axisstall.x) && !Double.isNaN(axisstall.y) && !Double.isNaN(axisstall.z)) {
					windvec.set(motionvec);
					if (mc_Entity.onGround && windvec.y < 0) windvec.y = 0;
					Vector3d bodyVec_front = new Vector3d(bodyvector);
					bodyVec_front.scale(-1);
					double sin = angle_cos(bodyVec_front, windvec);
					if (sin < 0) {
						axisstall.scale(-1);
					}
					sin = sqrt(1 - sin * sin);
					AxisAngle4d axisxangledstall = new AxisAngle4d(axisstall, windvec.length() * sin * (prefab_vehicle.stability_roll + (mc_Entity.onGround ? prefab_vehicle.stability_roll_onGround : 0) + (inWater ? prefab_vehicle.stability_roll_inWater * (sinking / prefab_vehicle.molded_depth) : 0)));
					rotationmotion = quatRotateAxis(rotationmotion, axisxangledstall);
				}
			}
		}

		{


			tailwingvector = transformVecByQuat(new Vector3d(unitY), bodyRot);
			bodyvector = transformVecByQuat(new Vector3d(unitZ), bodyRot);
			mainwingvector = transformVecByQuat(new Vector3d(unitX), bodyRot);

			transformVecforMinecraft(tailwingvector);
			transformVecforMinecraft(bodyvector);
			transformVecforMinecraft(mainwingvector);

			motionvec.y -= prefab_vehicle.gravity;
			windvec.y -= prefab_vehicle.gravity;
			if (mc_Entity.onGround && windvec.y < 0) windvec.y = 0;

			{
				Vector3d localMotionVec = new Vector3d();
				Vector3d localWindVec = new Vector3d();
				getVector_local_inRotatedObj(motionvec, localMotionVec, bodyRot);
				getVector_local_inRotatedObj(windvec, localWindVec, bodyRot);
				{

					{
						double dot = localWindVec.dot(prefab_vehicle.wingUnit);
						double hittingWind = -dot;
						if(!Double.isNaN(hittingWind)) {
							Vector3d wingUnit = new Vector3d(prefab_vehicle.wingUnit);
							wingUnit.scale(hittingWind * abs(hittingWind) * prefab_vehicle.stability_motion);
							localMotionVec.add(wingUnit);
						}
					}
					if(inWater){
						double dot = localWindVec.dot(prefab_vehicle.wingUnit);
						double hittingWind = -dot;
						if(!Double.isNaN(hittingWind)) {
							Vector3d wingUnit = new Vector3d(prefab_vehicle.wingUnit);
							wingUnit.scale(hittingWind * abs(hittingWind) * prefab_vehicle.stability_motion_inwater);
							localMotionVec.add(wingUnit);
						}
					}
					{
						double dot = localWindVec.dot(prefab_vehicle.wingUnit);
						double hittingWind = -dot;

						if(!Double.isNaN(hittingWind) && localWindVec.lengthSquared()>0) {
							Vector3d wingUnit1 = new Vector3d(prefab_vehicle.wingUnit);
							Vector3d wingUnit2 = new Vector3d(prefab_vehicle.wingUnit);
							double temp = 1 + localWindVec.lengthSquared() * pow(1 - hittingWind / localWindVec.length(), 2) * prefab_vehicle.stability_motion2;
							wingUnit1.scale(-hittingWind);
							localMotionVec.sub(wingUnit1);
							if(temp != 0) {
								wingUnit2.scale(-hittingWind / temp);
								localMotionVec.add(wingUnit2);
							}
						}
					}
				}

//				localMotionVec.y -= (localWindVec.y > 0 ? 1 : -1) * localWindVec.y * localWindVec.y * prefab_vehicle.stability_motion;
//
//				double temp = 1 + (localWindVec.x * localWindVec.x + localWindVec.z * localWindVec.z) * prefab_vehicle.stability_motion2;
//				localMotionVec.y = localMotionVec.y/temp;
//
//				if(inWater){
//					localMotionVec.y -= (localWindVec.y > 0 ? 1 : -1) * localWindVec.y * localWindVec.y * prefab_vehicle.stability_motion_inwater;
//				}

				double prevDist = localMotionVec.length();
				localMotionVec.y += cos(toRadians(bodyrotationRoll)) * cos(toRadians(bodyrotationPitch))
						* abs(localWindVec.z)
						* (prefab_vehicle.liftfactor + flaplevel*prefab_vehicle.flapliftfactor) * prefab_vehicle.bodyWeight/(prefab_vehicle.bodyWeight + weaponWeight);
				if(prevDist*prevDist<localMotionVec.lengthSquared()){
					if(localMotionVec.lengthSquared()>0) {
						localMotionVec.normalize();
						localMotionVec.scale(prevDist);
					}
				}
				{
					Vector3d temp = transformVecByQuat(localMotionVec,bodyRot);
					if (!Double.isNaN(motionvec.x) && !Double.isNaN(motionvec.y) && !Double.isNaN(motionvec.z)) {
						motionvec = temp;
						transformVecforMinecraft(motionvec);
					}
				}
			}


			windvec = new Vector3d(motionvec);
			if (mc_Entity.onGround && windvec.y < 0) windvec.y = 0;
			bodyRot.mul(rotationmotion);
			if(getQuat4DLength(rotationmotion)>0) {
				if (!mc_Entity.onGround && !inWater){
					double cos = prefab_vehicle.forced_rotmotion_reduceSpeed - ((1 - prefab_vehicle.forced_rotmotion_reduceSpeed) * angle_cos(bodyvector, motionvec)) * prefab_vehicle.rotmotion_reduceSpeed;
					if (Double.isNaN(cos)) cos = prefab_vehicle.rotmotion_reduceSpeed;
					if (mc_Entity.onGround || windvec.lengthSquared() < 1)
						rotationmotion.interpolate(new Quat4d(0,0,0,1), cos);
					else
						rotationmotion.interpolate(new Quat4d(0,0,0,1), cos);

					{
						double[] xyz = eulerfromQuat((rotationmotion));
						AxisAngle4d axiszangled = new AxisAngle4d(unitZ, -xyz[2] * prefab_vehicle.rotmotion_reduceSpeedRoll);
						rotationmotion = quatRotateAxis(rotationmotion, axiszangled);
					}
				} else {
					rotationmotion.interpolate(new Quat4d(0,0,0,1), prefab_vehicle.forced_rotmotion_reduceSpeed + (1 - prefab_vehicle.forced_rotmotion_reduceSpeed) *
							(
									(mc_Entity.onGround ? prefab_vehicle.rotmotion_reduceSpeed_onGround : 0) +
											(inWater ? prefab_vehicle.rotmotion_reduceSpeed_inWater : 0)
							));
					if (mc_Entity.onGround) {
						double[] xyz = eulerfromQuat((rotationmotion));
						AxisAngle4d axiszangledP = new AxisAngle4d(unitX, xyz[0] * prefab_vehicle.rotmotion_reduceSpeedPitch_onGround);
						AxisAngle4d axiszangledY = new AxisAngle4d(unitY, -xyz[1] * prefab_vehicle.rotmotion_reduceSpeedYaw_onGround);
						AxisAngle4d axiszangledR = new AxisAngle4d(unitZ, -xyz[2] * prefab_vehicle.rotmotion_reduceSpeedRoll_onGround);
						rotationmotion = quatRotateAxis(rotationmotion, axiszangledY);
						rotationmotion = quatRotateAxis(rotationmotion, axiszangledP);
						rotationmotion = quatRotateAxis(rotationmotion, axiszangledR);
					}
					if (inWater) {
						double[] xyz = eulerfromQuat((rotationmotion));
						AxisAngle4d axiszangledP = new AxisAngle4d(unitX, xyz[0] * prefab_vehicle.rotmotion_reduceSpeedPitch_inWater);
						AxisAngle4d axiszangledY = new AxisAngle4d(unitY, -xyz[1] * prefab_vehicle.rotmotion_reduceSpeedYaw_inWater);
						AxisAngle4d axiszangledR = new AxisAngle4d(unitZ, -xyz[2] * prefab_vehicle.rotmotion_reduceSpeedRoll_inWater);
						rotationmotion = quatRotateAxis(rotationmotion, axiszangledY);
						rotationmotion = quatRotateAxis(rotationmotion, axiszangledP);
						rotationmotion = quatRotateAxis(rotationmotion, axiszangledR);
					}
				}
			}
			NaNCheck(rotationmotion);

			if (!Double.isNaN(motionvec.x) && !Double.isNaN(motionvec.y) && !Double.isNaN(motionvec.z)) {
				mc_Entity.motionX = motionvec.x;
				mc_Entity.motionY = motionvec.y;
				mc_Entity.motionZ = motionvec.z;
			}

			Vector3d motionvec_backUp = new Vector3d(motionvec);
			handleWaterMovement();


			setPosition(mc_Entity.posX, mc_Entity.posY, mc_Entity.posZ);
			boolean prevOnGround = mc_Entity.onGround;
			moveEntity(mc_Entity.motionX, mc_Entity.motionY, mc_Entity.motionZ);
			if(worldObj.isRemote && mc_Entity.onGround && mc_Entity.motionY<0)mc_Entity.motionY = 0;
			motionvec = new Vector3d(mc_Entity.motionX, mc_Entity.motionY, mc_Entity.motionZ);
			double motionYBackUp = mc_Entity.motionY;

			//空気抵抗・動摩擦は動きへの反作用。よって加減速はこっちで計算する。
//			Vector3d powerVec = new Vector3d(prefab_vehicle.unitThrottle);
//			powerVec = transformVecByQuat(powerVec,bodyRot);
//			transformVecforMinecraft(powerVec);
//			if (!(Double.isNaN(powerVec.x) || Double.isNaN(powerVec.y) || Double.isNaN(powerVec.z))) {
//				if (prefab_vehicle.throttle_AF < throttle) {
//					powerVec.normalize();
//					powerVec.scale(-throttle * (prefab_vehicle.speedfactor_af + prefab_vehicle.speedfactor) * (prefab_vehicle.bodyWeight/(prefab_vehicle.bodyWeight + weaponWeight)));
//					motionvec.add(powerVec);
//				}else {
//					powerVec.scale(-throttle * prefab_vehicle.speedfactor * (prefab_vehicle.bodyWeight/(prefab_vehicle.bodyWeight + weaponWeight)));
//					motionvec.add(powerVec);
//				}
//			}


			if(mc_Entity.onGround || inWater){
				followGround(mainwingvector,tailwingvector,bodyvector, (float) abs(motionvec_backUp.y + (motionvec.y - motionYBackUp)));
			}else if(prevOnGround && prefab_vehicle.T_Land_F_Plane){
				followGround(mainwingvector,tailwingvector,bodyvector, prefab_vehicle.gravity);
				if(mc_Entity.onGround){
					motionvec.y = 0;
					mc_Entity.motionY = 0;
				}
			}

			if (motionvec.lengthSquared() > 0) {//
				Vector3d drug = new Vector3d(motionvec);
				drug.normalize();
				drug.scale(motionvec.length() *
								(prefab_vehicle.dragfactor + gearprogress * prefab_vehicle.geardragfactor)
									  + (inWater ? motionvec.length() * (prefab_vehicle.dragfactor_inwater * (sinking/prefab_vehicle.molded_depth)) : 0)
						             /* + (mc_Entity.onGround ? prefab_vehicle.dragfactor_ground * (motionvec_backUp.y < 0 ? -motionvec_backUp.y :0) : 0) *//*ここは別処理に移行*/
						             /* + (mc_Entity.onGround && serverx ? prefab_vehicle.brakedragfactor_ground * (motionvec_backUp.y < 0 ? -motionvec_backUp.y :0): 0)*/);
				if(drug.lengthSquared() > motionvec.lengthSquared()){
					motionvec.set(0,0,0);
				}
				else motionvec.sub(drug);
			}
			getVector_local_inRotatedObj(motionvec, localMotionVec, bodyRot);


			if (motionvec.lengthSquared() > 0) {
				if(localMotionVec.z != 0) {
					double dragFactor = ((
							localMotionVec.z * localMotionVec.z *
									(
											prefab_vehicle.dragfactor_front
													+
													(serverx ? prefab_vehicle.brakedragfactor : 0)
													+
													flaplevel * prefab_vehicle.flapdragfactor
													+
													(mc_Entity.onGround ? -(motionvec_backUp.y < 0 ? motionvec_backUp.y : 0) * prefab_vehicle.dragfactor_ground : 0)
									)
									+
									(serverx && mc_Entity.onGround ? -(motionvec_backUp.y < 0 ? motionvec_backUp.y : 0) * prefab_vehicle.brakefactor_ground : 0)));
					if (abs(dragFactor) > abs(localMotionVec.z)) {
						localMotionVec.z = 0;
					} else {
						localMotionVec.z *= (abs(localMotionVec.z) - dragFactor) / abs(localMotionVec.z);
					}
				}
				{
					double slipResist = 0;
					slipResist = (localMotionVec.x > 0 ? 1 : -1) * localMotionVec.x * localMotionVec.x * prefab_vehicle.slipresist;
					if (inWater)
						slipResist += (localMotionVec.x > 0 ? 1 : -1) * localMotionVec.x * prefab_vehicle.slipresist_inwater;
					if (mc_Entity.onGround) {
						slipResist += (localMotionVec.x > 0 ? 1 : -1) * abs(motionvec_backUp.y) * prefab_vehicle.slipresist_onground;
					}
					if (slipResist > 0 && slipResist > localMotionVec.x) {
						localMotionVec.x = 0;
					} else if (slipResist < 0 && slipResist < localMotionVec.x) {
						localMotionVec.x = 0;
					} else if (slipResist != 0) {
						localMotionVec.x -= slipResist;
					}
				}
				motionvec = transformVecByQuat(localMotionVec,bodyRot);
				transformVecforMinecraft(motionvec);
			}

			{
				//車輪・履帯を介すると速度はその回転速度に収束する。
				//よって前後方向(z軸)についての移動量を出し、
				double dot = localMotionVec.dot(prefab_vehicle.unitThrottle);
				double motion_zaxis = -dot;
				//その移動量と車輪・履帯の回転速を比べ加減速させる。
				//この際摩擦係数となっているdragfactor_groundをかけて緩急を付ける。
//					System.out.println("motionvec" + motionvec);
				double accelerator =
						(
								(
										throttle * (prefab_vehicle.speedfactor)
										+
										(mc_Entity.onGround ? (!serverx ?throttle:0) * prefab_vehicle.speedfactor_onGround : (inWater ? (!serverx ?throttle:0) * prefab_vehicle.speedfactor_inWater:0))
								)
										-
										motion_zaxis )
						*
						(
								prefab_vehicle.torque_AirBone
										+
								(prefab_vehicle.turbine_torque*abs(throttle))
										+
								(mc_Entity.onGround ? prefab_vehicle.torque_ground * (motionvec_backUp.y < 0 ? -motionvec_backUp.y :0) : 0)
										+
								(inWater ? prefab_vehicle.torque_inWater * (sinking/prefab_vehicle.molded_depth) : 0)
						)
						 + (throttle > prefab_vehicle.throttle_AF?prefab_vehicle.speedfactor_af:0)
						 + (servera || serverd ? prefab_vehicle.forced_OnYawingSpeed_Move / (1 + localMotionVec.length() * 10) * abs(motionvec_backUp.y) * throttle: 0);
				accelerator *= prefab_vehicle.bodyWeight/(prefab_vehicle.bodyWeight + weaponWeight);
//				System.out.println("weaponWeight " + prefab_vehicle.bodyWeight/(prefab_vehicle.bodyWeight + weaponWeight));
				Vector3d unitThrottle = new Vector3d(prefab_vehicle.unitThrottle);
				unitThrottle.scale(-accelerator);
				localMotionVec.add(unitThrottle);
				motionvec = transformVecByQuat(localMotionVec,bodyRot);
				transformVecforMinecraft(motionvec);
			}
			if (!Double.isNaN(motionvec.x) && !Double.isNaN(motionvec.y) && !Double.isNaN(motionvec.z)) {
				mc_Entity.motionX = motionvec.x;
				mc_Entity.motionY = motionvec.y;
				mc_Entity.motionZ = motionvec.z;
			}

			if (mc_Entity.motionY > 0) {
				mc_Entity.isAirBorne = true;
			}


			if (throttle > prefab_vehicle.throttle_Max) {
				throttle = prefab_vehicle.throttle_Max;
			}
			if (throttle < prefab_vehicle.throttle_min) {
				throttle = prefab_vehicle.throttle_min;
			}
			mc_Entity.fallDistance = 0;

			{
//			System.out.println("yawladder " + yawladder);
//		System.out.println("" + pitchladder);
				motionvec = new Vector3d(mc_Entity.motionX, mc_Entity.motionY, mc_Entity.motionZ);
				double currentForcedEffect = prefab_vehicle.forced_rudder_effect + (mc_Entity.onGround ? prefab_vehicle.forced_rudder_effect_OnGround : 0) + (inWater ? prefab_vehicle.forced_rudder_effect_InWater * (sinking/prefab_vehicle.molded_depth):0);
				double cof = 0;
				if(motionvec.length() > 0.001)cof = -angle_cos(bodyvector, motionvec) * (motionvec.length());
				cof += (currentForcedEffect * throttle);
//				System.out.println("cof " + cof);
//				System.out.println("abs(motionvec_backUp.y) " + abs(motionvec_backUp.y));
				if(abs(cof) > 0) {
					if (abs(pitchrudder) > 0.0001) {
						AxisAngle4d axisxangled = new AxisAngle4d(unitX, toRadians(-pitchrudder / 4 * cof *
								(prefab_vehicle.pitchspeed + (inWater ? prefab_vehicle.pitchspeed_Inwater * (sinking/prefab_vehicle.molded_depth):0))));
						rotationmotion = quatRotateAxis(rotationmotion, axisxangled);
					}
					if (abs(yawrudder) > 0.0001) {
						AxisAngle4d axisyangled;
						axisyangled = new AxisAngle4d(unitY, toRadians
								(
										yawrudder / 4 * (cof *
												(prefab_vehicle.yawspeed +
														(mc_Entity.onGround ? prefab_vehicle.yawspeed_taxing * abs(motionvec_backUp.y):
																(inWater ? prefab_vehicle.yawspeed_Inwater * (sinking/prefab_vehicle.molded_depth):0)
														)
												)
												+ prefab_vehicle.forced_OnYawingSpeed_Roll / (1 + localMotionVec.length() * 10) * abs(motionvec_backUp.y) * throttle)
								)
						);

						rotationmotion = quatRotateAxis(rotationmotion, axisyangled);
					}
					if (abs(rollrudder) > 0.0001) {
						AxisAngle4d axiszangled = new AxisAngle4d(unitZ, toRadians(rollrudder / 4 * cof *
								(prefab_vehicle.rollspeed + (inWater ? prefab_vehicle.rollspeed_Inwater:0))));
						rotationmotion = quatRotateAxis(rotationmotion, axiszangled);
					}
				}

				double[] xyz = eulerfromQuat((bodyRot));
				bodyrotationPitch = (float) toDegrees(xyz[0]);
				if (!Double.isNaN(xyz[1])) {
					bodyrotationYaw = (float) toDegrees(xyz[1]);
				}
				bodyrotationRoll = (float) toDegrees(xyz[2]);
			}
		}

		{
			if (mc_Entity.onGround) {
				gearprogress = 100;
			} else {
				if (throttle > prefab_vehicle.throttle_gearDown) {
					gearprogress--;
				} else {
					gearprogress++;
				}

				if (gearprogress < 0) {
					gearprogress = 0;
				}
				if (gearprogress > 100) {
					gearprogress = 100;
				}

			}
			motionvec = new Vector3d(mc_Entity.motionX, mc_Entity.motionY, mc_Entity.motionZ);
			if ((motionvec.y < -bodyvector.y && prefab_vehicle.autoflap) || serverf) {
				Flapextension();
			} else {
				Flapstorage();
			}
			if (flaplevel < 0) {
				flaplevel = 0;
			}
			if (flaplevel > 75) {
				flaplevel = 75;
			}
		}
		if(mc_Entity.onGround)rotationBySomeFactor(new Vector3d(
				-(prevlocalMotionVec.x-localMotionVec.x),
				prevlocalMotionVec.y-localMotionVec.y,
				(prevlocalMotionVec.z-localMotionVec.z)),new Vector3d(prefab_vehicle.powerPos_onGround));
		if(inWater)rotationBySomeFactor(new Vector3d(
				-(prevlocalMotionVec.x-localMotionVec.x),
				prevlocalMotionVec.y-localMotionVec.y,
				(prevlocalMotionVec.z-localMotionVec.z)),new Vector3d(prefab_vehicle.powerPos_Inwater));

		if(!worldObj.isRemote){
			HMVPacketHandler.INSTANCE.sendToAll(new HMVPakcetVehicleState(mc_Entity.getEntityId(),bodyRot,rotationmotion,motionvec,
					new Vector3d(mc_Entity.posX,mc_Entity.posY,mc_Entity.posZ),throttle,health,mc_Entity.onGround,mouseStickMode));
		}
		rotationmotion.normalize();
		bodyRot.normalize();

		if(mc_Entity.onGround && abs(bodyrotationRoll) > 45){
			mc_Entity.attackEntityFrom(DamageSource.inWall, (float) (abs(bodyrotationRoll) - 45)*0.1f*prefab_vehicle.antiGroundHitCof);
		}
	}
	public void rotationBySomeFactor(Vector3d factor,Vector3d factorPos){
		if(factor.lengthSquared() > 0) {
			double amount = factor.length();
			factorPos.sub(new Vector3d(prefab_vehicle.center_of_gravity));
			factorPos.normalize();
			factor.normalize();
			Vector3d recoilRotVector = new Vector3d();
			recoilRotVector.cross(factorPos, factor);
			if(!Double.isNaN(recoilRotVector.x) &&
			!Double.isNaN(recoilRotVector.y) &&
			!Double.isNaN(recoilRotVector.z) ) {
				AxisAngle4d recoilRotor = new AxisAngle4d(recoilRotVector, -amount * recoilRotVector.length()/prefab_vehicle.motionRollResist);
				this.rotationmotion = quatRotateAxis(this.rotationmotion, recoilRotor);
			}
		}
	}
	public void rotationByRecoil(Vector3d factor,Vector3d factorPos){
		if(factor.lengthSquared() > 0) {
			double amount = factor.length();
			factorPos.sub(new Vector3d(prefab_vehicle.center_of_gravity));
			factorPos.normalize();
			factor.normalize();
			Vector3d recoilRotVector = new Vector3d();
			recoilRotVector.cross(factorPos, factor);
//			System.out.println("recoilRotVector " + recoilRotVector);
			if(!Double.isNaN(recoilRotVector.x) &&
			!Double.isNaN(recoilRotVector.y) &&
			!Double.isNaN(recoilRotVector.z) ) {
				AxisAngle4d recoilRotor = new AxisAngle4d(recoilRotVector, -amount * recoilRotVector.length()/prefab_vehicle.recoilResist);
				this.rotationmotion = quatRotateAxis(this.rotationmotion, recoilRotor);
			}
		}
	}
	public void followGround(Vector3d mainwingvector,Vector3d tailwingvector,Vector3d bodyvector,float vertical_drag){
		if(abs(vertical_drag)>prefab_vehicle.gravity){
			vertical_drag *= prefab_vehicle.gravity/abs(vertical_drag);
		}
		mc_Entity.stepHeight = prefab_vehicle.off_road_capability + abs(MathHelper.sin(-bodyrotationPitch * 0.017453292F - (float) Math.PI)) * prefab_vehicle.wheelZ;
//		System.out.println("debug" + mc_Entity.stepHeight);
		Vec3 tankFrontVec_level;
		Vec3 tankRight;
		{
			float f1;
			float f2;
			f1 = -MathHelper.cos(-bodyrotationYaw * 0.017453292F - (float) Math.PI);
			f2 = -MathHelper.sin(-bodyrotationYaw * 0.017453292F - (float) Math.PI);
			tankFrontVec_level = Vec3.createVectorHelper( (f2) * prefab_vehicle.wheelZ,  0,  (f1) * prefab_vehicle.wheelZ);
			tankRight = Vec3.createVectorHelper((f1)* prefab_vehicle.wheelX,  0, -(f2)* prefab_vehicle.wheelX);
		}
		Vector3d tankFrontVec = transformVecByQuat(new Vector3d(0,0,-1),bodyRot);
		{
			Vector3d temp = new Vector3d(tankFrontVec);
			temp.y = 0;
			tankFrontVec.scale(prefab_vehicle.wheelZ/temp.length());
		}
		Vec3 FR;
		Vec3 FL;
		Vec3 BR;
		Vec3 BL;
		{
			Vec3 vec3 = Vec3.createVectorHelper(mc_Entity.posX, mc_Entity.posY + tankFrontVec.y + cfgVehicleWheel_UpRange, mc_Entity.posZ);
			vec3 = vec3.addVector(tankFrontVec_level.xCoord, tankFrontVec_level.yCoord, tankFrontVec_level.zCoord);
			vec3 = vec3.addVector(tankRight.xCoord, tankRight.yCoord, tankRight.zCoord);
//            playerlook = Vec3.createVectorHelper(playerlook.xCoord * 256, playerlook.yCoord * 256, playerlook.zCoord * 256);
			Vec3 vec31 = vec3.addVector(0, -cfgVehicleWheel_DownRange, 0);
			MovingObjectPosition amovingObjectPosition = mc_Entity.worldObj.func_147447_a(vec3, vec31, false, true, false);
			if(amovingObjectPosition == null)FR = vec31;
			else {
				mc_Entity.onGround = true;
				FR = amovingObjectPosition.hitVec;
			}
		}
		{
			Vec3 vec3 = Vec3.createVectorHelper(mc_Entity.posX, mc_Entity.posY + tankFrontVec.y + cfgVehicleWheel_UpRange, mc_Entity.posZ);
			vec3 = vec3.addVector(tankFrontVec_level.xCoord, tankFrontVec_level.yCoord, tankFrontVec_level.zCoord);
			vec3 = vec3.addVector(-tankRight.xCoord, -tankRight.yCoord, -tankRight.zCoord);
//            playerlook = Vec3.createVectorHelper(playerlook.xCoord * 256, playerlook.yCoord * 256, playerlook.zCoord * 256);
			Vec3 vec31 = vec3.addVector(0, -cfgVehicleWheel_DownRange, 0);
			MovingObjectPosition amovingObjectPosition = mc_Entity.worldObj.func_147447_a(vec3, vec31, false, true, false);
			if(amovingObjectPosition == null)FL = vec31;
			else {
				mc_Entity.onGround = true;
				FL = amovingObjectPosition.hitVec;
			}
		}
		{
			Vec3 vec3 = Vec3.createVectorHelper(mc_Entity.posX, mc_Entity.posY - tankFrontVec.y + cfgVehicleWheel_UpRange, mc_Entity.posZ);
			vec3 = vec3.addVector(-tankFrontVec_level.xCoord, -tankFrontVec_level.yCoord, -tankFrontVec_level.zCoord);
			vec3 = vec3.addVector(tankRight.xCoord, tankRight.yCoord, tankRight.zCoord);
//            playerlook = Vec3.createVectorHelper(playerlook.xCoord * 256, playerlook.yCoord * 256, playerlook.zCoord * 256);
			Vec3 vec31 = vec3.addVector(0, -cfgVehicleWheel_DownRange, 0);
			MovingObjectPosition amovingObjectPosition = mc_Entity.worldObj.func_147447_a(vec3, vec31, false, true, false);
			if(amovingObjectPosition == null)BR = vec31;
			else {
				mc_Entity.onGround = true;
				BR = amovingObjectPosition.hitVec;
			}
		}
		{
			Vec3 vec3 = Vec3.createVectorHelper(mc_Entity.posX, mc_Entity.posY - tankFrontVec.y + cfgVehicleWheel_UpRange, mc_Entity.posZ);
			vec3 = vec3.addVector(-tankFrontVec_level.xCoord, -tankFrontVec_level.yCoord, -tankFrontVec_level.zCoord);
			vec3 = vec3.addVector(-tankRight.xCoord, -tankRight.yCoord, -tankRight.zCoord);
//            playerlook = Vec3.createVectorHelper(playerlook.xCoord * 256, playerlook.yCoord * 256, playerlook.zCoord * 256);
			Vec3 vec31 = vec3.addVector(0, -cfgVehicleWheel_DownRange, 0);
			MovingObjectPosition amovingObjectPosition = mc_Entity.worldObj.func_147447_a(vec3, vec31, false, true, false);
			if(amovingObjectPosition == null)BL = vec31;
			else {
				mc_Entity.onGround = true;
				BL = amovingObjectPosition.hitVec;
			}
		}
		
		double targetbodyrotationPitch = 0;
		double targetbodyrotationRoll = 0;
		{
			Vec3 vec1 = BL.addVector(-FR.xCoord,-FR.yCoord,-FR.zCoord);
			Vec3 vec2 = BR.addVector(-FL.xCoord,-FL.yCoord,-FL.zCoord);
			Vec3 normal = vec1.crossProduct(vec2).normalize();
			tankRight = tankRight.normalize();
			Vec3 pitchVec = normal.crossProduct(tankRight).normalize();
			Vec3 rollVec = normal.crossProduct(getMinecraftVecObj(bodyvector)).normalize();
			double groundPitch = toDegrees(sin(pitchVec.yCoord));
			double groundRoll = toDegrees(sin(rollVec.yCoord));
			targetbodyrotationPitch = ((float) -groundPitch - (bodyrotationPitch + current_onground_pitch));
			targetbodyrotationPitch = wrapAngleTo180_double(targetbodyrotationPitch);
//                System.out.println("debug " + bodyrotationPitch);
			targetbodyrotationRoll = ((float) groundRoll - (bodyrotationRoll + current_onground_roll));
			if(abs(targetbodyrotationRoll) > 90){
				targetbodyrotationRoll = -(180-targetbodyrotationRoll);
				targetbodyrotationPitch = -(targetbodyrotationPitch);
			}
			targetbodyrotationRoll = wrapAngleTo180_double(targetbodyrotationRoll);
			targetbodyrotationPitch = wrapAngleTo180_double(targetbodyrotationPitch);
//                if(tank.worldObj.isRemote){
//                    System.out.println(vec2);
//                    System.out.println(normal);
//                }
		}
		AxisAngle4d axisxangled;

		Quat4d quat4d = new Quat4d(0,0,0,1);
		inverse_safe(bodyRot,quat4d);
		NaNCheck(quat4d);

		axisxangled = new AxisAngle4d(unitX,
				-toRadians(
						(targetbodyrotationPitch) *
								(
										(mc_Entity.onGround ? prefab_vehicle.off_road_followability : 0) * vertical_drag +
												(inWater ? prefab_vehicle.off_road_followability_inwater * (1-sinking/prefab_vehicle.molded_depth): 0)
								)
				) * abs(cos(toRadians(bodyrotationRoll)))
		);
		rotationmotion = quatRotateAxis(rotationmotion, axisxangled);

		axisxangled = new AxisAngle4d(unitZ,
				toRadians(
						targetbodyrotationRoll *
								(
										((mc_Entity.onGround ? prefab_vehicle.off_road_followability_roll : 0)) * vertical_drag +
												(inWater ? prefab_vehicle.off_road_followability_inwater_roll * (1-sinking/prefab_vehicle.molded_depth) : 0)
								)
				) * abs(cos(toRadians(bodyrotationPitch)))
		);
		rotationmotion = quatRotateAxis(rotationmotion, axisxangled);

		if(inWater) {
			axisxangled = new AxisAngle4d(unitX,
					toRadians(prefab_vehicle.HeadPopUpInwater * localMotionVec.z * sinking/prefab_vehicle.molded_depth)
			);
			rotationmotion = quatRotateAxis(rotationmotion, axisxangled);
		}
	}
	
	public void Flapextension(){
		flaplevel++;
	}
	public void Flapstorage(){
		flaplevel--;
	}
	public boolean isConverting() {
		return false;
	}
	
	
	@Override
	public String getsound() {
		if(health < 0 && prefab_vehicle.OnDying_soundname != null){
			return prefab_vehicle.OnDying_soundname;
		}
		if(prefab_vehicle.throttle_AF > 0 && throttle > prefab_vehicle.throttle_AF){
			return prefab_vehicle.AFsoundname;
		}else
		return prefab_vehicle.soundname;
	}
	
	public float getsoundPitch(){
		return health < 0 ? 1 : abs(throttle / prefab_vehicle.throttle_Max* prefab_vehicle.soundpitch);
	}
	String playingSound;
	public void yourSoundIsremain(String playingSound){
		this.playingSound = playingSound;
		needStartSound = false;
	}
	
	
	public void setControl_Space(boolean value) {
		serverspace = value;
	}
	
	
	public void setControl_brake(boolean value) {
		serverx = value;
	}
	public void setControl_Flare(boolean value) {
		server_Flare = value;
	}
	
	
	public void setControl_throttle_up(boolean value) {
		serverw = value;
	}
	
	
	public void setControl_yaw_Left(boolean value) {
		servera = value;
	}
	
	
	public void setControl_throttle_down(boolean value) {
		servers = value;
	}
	
	
	public void setControl_yaw_Right(boolean value) {
		serverd = value;
	}
	
	
	public void setControl_flap(boolean value) {
		serverf = value;
	}
	
	
	@Override
	public void setMouse(float tempMouseX, float tempMouseY, float tempMouseZ) {
		rollrudder = tempMouseX;
		pitchrudder = tempMouseY;
		yawrudder = tempMouseZ;
	}
	
	public double[] getCamerapos(){
		return HMV_Proxy.iszooming() && seatInfos_zoom[getpilotseatid()] != null ? seatInfos_zoom[getpilotseatid()].pos:
				prefab_vehicle.camerapos != null ? prefab_vehicle.camerapos : seatInfos[getpilotseatid()].pos;
	}
	
	public int getpilotseatid(){
		return iVehicle.getpilotseatid();
	}
	
	public void saveToTag(NBTTagCompound tagCompound){
		tagCompound.setFloat("health",health);
		tagCompound.setDouble("bodyRot.w",bodyRot.w);
		tagCompound.setDouble("bodyRot.x",bodyRot.x);
		tagCompound.setDouble("bodyRot.y",bodyRot.y);
		tagCompound.setDouble("bodyRot.z",bodyRot.z);
		tagCompound.setInteger("flare_remain",flare_remain);

		int id = 0;
		for(TurretObj aturret:allturrets){
			aturret.motherEntity = this.mc_Entity;
			aturret.saveToTag(tagCompound,id++);
		}

		NBTTagList nbttaglist = new NBTTagList();
		
		for (int i = 0; i < this.inventoryVehicle.getSizeInventory(); ++i)
		{
			if (this.inventoryVehicle.getStackInSlot(i) != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				this.inventoryVehicle.getStackInSlot(i).writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
		tagCompound.setTag("Items", nbttaglist);
	}
	public void readFromTag(NBTTagCompound tagCompound){
		health = tagCompound.getFloat("health");
		bodyRot.w = tagCompound.getDouble("bodyRot.w");
		bodyRot.x = tagCompound.getDouble("bodyRot.x");
		bodyRot.y = tagCompound.getDouble("bodyRot.y");
		bodyRot.z = tagCompound.getDouble("bodyRot.z");
		flare_remain = tagCompound.getInteger("flare_remain");

		NBTTagList nbttaglist = tagCompound.getTagList("Items", 10);
		
		for (int i = 0; i < nbttaglist.tagCount(); ++i)
		{
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			int j = nbttagcompound1.getByte("Slot") & 255;
			
			if (j < inventoryVehicle.getSizeInventory())
			{
				ItemStack itemStack = ItemStack.loadItemStackFromNBT(nbttagcompound1);
				System.out.println("debug" + itemStack);
				inventoryVehicle.setInventorySlotContents(j,itemStack);
			}
		}

		int id = 0;
		for(TurretObj aturret:allturrets){
			aturret.motherEntity = this.mc_Entity;
			aturret.readFromTag(tagCompound,id++);
		}
	}
}
