package handmadevehicle.entity.parts.logics;

import cpw.mods.fml.client.FMLClientHandler;
import handmadevehicle.HMVPacketHandler;
import handmadevehicle.Utils;
import handmadevehicle.entity.EntityCameraDummy;
import handmadevehicle.entity.parts.*;
import handmadevehicle.entity.parts.turrets.TurretObj;
import handmadevehicle.entity.prefab.Prefab_Vehicle_Base;
import handmadevehicle.packets.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
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
import static handmadevehicle.HMVehicle.HMV_Proxy;
import static handmadevehicle.HMVehicle.cfgVehicleWheel_DownRange;
import static handmadevehicle.HMVehicle.cfgVehicleWheel_UpRange;
import static handmadevehicle.Utils.*;
import static handmadevehicle.Utils.unitY;
import static java.lang.Math.*;
import static java.lang.Math.toRadians;
import static net.minecraft.util.MathHelper.wrapAngleTo180_float;

public class BaseLogic implements IbaseLogic,IneedMouseTrack,MultiRiderLogics {
	
	public float bodyrotationYaw;
	public float bodyrotationPitch;
	public float bodyrotationRoll;
	public float prevbodyrotationYaw;
	public float prevbodyrotationPitch;
	public float prevbodyrotationRoll;
	public float throttle;
	
	public Prefab_Vehicle_Base info;
	
	public TurretObj mainTurret;
	public TurretObj subTurret;
	
	public TurretObj[] turrets = new TurretObj[0];
	public TurretObj[] allturrets = new TurretObj[0];
	public TurretObj[] mainTurrets;
	public TurretObj[] subTurrets;
	
	public Entity[] riddenByEntities = new Entity[1];
	public SeatInfo[] seatInfos = {new SeatInfo()};
	public SeatInfo[] seatInfos_zoom = {new SeatInfo()};
	
	public Quat4d bodyRot = new Quat4d(0,0,0,1);
	public Quat4d rotationmotion = new Quat4d(0,0,0,1);
	public Quat4d prevbodyRot = new Quat4d(0,0,0,1);
	
	
	public boolean needStartSound = false;
	
	public float pera_trackPos;
	public float prev_pera_trackPos;
	
	Entity mc_Entity;
	IVehicle iVehicle;
	World worldObj;
	public void moveEntity(double p_70091_1_, double p_70091_3_, double p_70091_5_)
	{
		if (mc_Entity.noClip)
		{
			mc_Entity.boundingBox.offset(p_70091_1_, p_70091_3_, p_70091_5_);
			mc_Entity.posX = (mc_Entity.boundingBox.minX + mc_Entity.boundingBox.maxX) / 2.0D;
			mc_Entity.posY = mc_Entity.boundingBox.minY + (double)mc_Entity.yOffset - (double)mc_Entity.ySize;
			mc_Entity.posZ = (mc_Entity.boundingBox.minZ + mc_Entity.boundingBox.maxZ) / 2.0D;
		}
		else
		{
			if(!worldObj.isRemote){
				destroyNearBlocks(mc_Entity.boundingBox);
			}
			mc_Entity.worldObj.theProfiler.startSection("move");
			mc_Entity.ySize *= 0.4F;
			double d3 = mc_Entity.posX;
			double d4 = mc_Entity.posY;
			double d5 = mc_Entity.posZ;
//			
//			if (mc_Entity.isInWeb)
//			{
//				mc_Entity.isInWeb = false;
//				p_70091_1_ *= 0.25D;
//				p_70091_3_ *= 0.05000000074505806D;
//				p_70091_5_ *= 0.25D;
//				mc_Entity.motionX = 0.0D;
//				mc_Entity.motionY = 0.0D;
//				mc_Entity.motionZ = 0.0D;
//			}
			
			double d6 = p_70091_1_;
			double d7 = p_70091_3_;
			double d8 = p_70091_5_;
			AxisAlignedBB axisalignedbb = ((ModifiedBoundingBox)mc_Entity.boundingBox).noMod_copy();
			boolean flag = mc_Entity.onGround && mc_Entity.isSneaking();
			
			if (flag)
			{
				double d9;
				
				for (d9 = 0.05D; p_70091_1_ != 0.0D && mc_Entity.worldObj.getCollidingBoundingBoxes(mc_Entity, axisalignedbb.getOffsetBoundingBox(p_70091_1_, -1.0D, 0.0D)).isEmpty(); d6 = p_70091_1_)
				{
					if (p_70091_1_ < d9 && p_70091_1_ >= -d9)
					{
						p_70091_1_ = 0.0D;
					}
					else if (p_70091_1_ > 0.0D)
					{
						p_70091_1_ -= d9;
					}
					else
					{
						p_70091_1_ += d9;
					}
				}
				
				for (; p_70091_5_ != 0.0D && mc_Entity.worldObj.getCollidingBoundingBoxes(mc_Entity, axisalignedbb.getOffsetBoundingBox(0.0D, -1.0D, p_70091_5_)).isEmpty(); d8 = p_70091_5_)
				{
					if (p_70091_5_ < d9 && p_70091_5_ >= -d9)
					{
						p_70091_5_ = 0.0D;
					}
					else if (p_70091_5_ > 0.0D)
					{
						p_70091_5_ -= d9;
					}
					else
					{
						p_70091_5_ += d9;
					}
				}
				
				while (p_70091_1_ != 0.0D && p_70091_5_ != 0.0D && mc_Entity.worldObj.getCollidingBoundingBoxes(mc_Entity, axisalignedbb.getOffsetBoundingBox(p_70091_1_, -1.0D, p_70091_5_)).isEmpty())
				{
					if (p_70091_1_ < d9 && p_70091_1_ >= -d9)
					{
						p_70091_1_ = 0.0D;
					}
					else if (p_70091_1_ > 0.0D)
					{
						p_70091_1_ -= d9;
					}
					else
					{
						p_70091_1_ += d9;
					}
					
					if (p_70091_5_ < d9 && p_70091_5_ >= -d9)
					{
						p_70091_5_ = 0.0D;
					}
					else if (p_70091_5_ > 0.0D)
					{
						p_70091_5_ -= d9;
					}
					else
					{
						p_70091_5_ += d9;
					}
					
					d6 = p_70091_1_;
					d8 = p_70091_5_;
				}
			}
			
			List list = mc_Entity.worldObj.getCollidingBoundingBoxes(mc_Entity, axisalignedbb.addCoord(p_70091_1_, p_70091_3_, p_70091_5_));
			
			for (int i = 0; i < list.size(); ++i)
			{
				p_70091_3_ = ((AxisAlignedBB)list.get(i)).calculateYOffset(mc_Entity.boundingBox, p_70091_3_);
			}
			
			mc_Entity.boundingBox.offset(0.0D, p_70091_3_, 0.0D);
			
			if (!mc_Entity.field_70135_K && d7 != p_70091_3_)
			{
				p_70091_5_ = 0.0D;
				p_70091_3_ = 0.0D;
				p_70091_1_ = 0.0D;
			}
			
			boolean flag1 = mc_Entity.onGround || d7 != p_70091_3_ && d7 < 0.0D;
			int j;
			
			for (j = 0; j < list.size(); ++j)
			{
				p_70091_1_ = ((AxisAlignedBB)list.get(j)).calculateXOffset(mc_Entity.boundingBox, p_70091_1_);
			}
			
			mc_Entity.boundingBox.offset(p_70091_1_, 0.0D, 0.0D);
			
			if (!mc_Entity.field_70135_K && d6 != p_70091_1_)
			{
				p_70091_5_ = 0.0D;
				p_70091_3_ = 0.0D;
				p_70091_1_ = 0.0D;
			}
			
			for (j = 0; j < list.size(); ++j)
			{
				p_70091_5_ = ((AxisAlignedBB)list.get(j)).calculateZOffset(mc_Entity.boundingBox, p_70091_5_);
			}
			
			mc_Entity.boundingBox.offset(0.0D, 0.0D, p_70091_5_);
			
			if (!mc_Entity.field_70135_K && d8 != p_70091_5_)
			{
				p_70091_5_ = 0.0D;
				p_70091_3_ = 0.0D;
				p_70091_1_ = 0.0D;
			}
			
			double d10;
			double d11;
			int k;
			double d12;
			
			if (mc_Entity.stepHeight > 0.0F && flag1 && (flag || mc_Entity.ySize < 0.05F) && (d6 != p_70091_1_ || d8 != p_70091_5_))
			{
				d12 = p_70091_1_;
				d10 = p_70091_3_;
				d11 = p_70091_5_;
				p_70091_1_ = d6;
				p_70091_3_ = (double)mc_Entity.stepHeight;
				p_70091_5_ = d8;
				AxisAlignedBB axisalignedbb1 = ((ModifiedBoundingBox) mc_Entity.boundingBox).noMod_copy();
				mc_Entity.boundingBox.setBB(axisalignedbb);
				list = mc_Entity.worldObj.getCollidingBoundingBoxes(mc_Entity, axisalignedbb1.addCoord(d6, p_70091_3_, d8));
				
				for (k = 0; k < list.size(); ++k)
				{
					p_70091_3_ = ((AxisAlignedBB)list.get(k)).calculateYOffset(mc_Entity.boundingBox, p_70091_3_);
				}
				
				mc_Entity.boundingBox.offset(0.0D, p_70091_3_, 0.0D);
				
				if (!mc_Entity.field_70135_K && d7 != p_70091_3_)
				{
					p_70091_5_ = 0.0D;
					p_70091_3_ = 0.0D;
					p_70091_1_ = 0.0D;
				}
				
				for (k = 0; k < list.size(); ++k)
				{
					p_70091_1_ = ((AxisAlignedBB)list.get(k)).calculateXOffset(mc_Entity.boundingBox, p_70091_1_);
				}
				
				mc_Entity.boundingBox.offset(p_70091_1_, 0.0D, 0.0D);
				
				if (!mc_Entity.field_70135_K && d6 != p_70091_1_)
				{
					p_70091_5_ = 0.0D;
					p_70091_3_ = 0.0D;
					p_70091_1_ = 0.0D;
				}
				
				for (k = 0; k < list.size(); ++k)
				{
					p_70091_5_ = ((AxisAlignedBB)list.get(k)).calculateZOffset(mc_Entity.boundingBox, p_70091_5_);
				}
				
				mc_Entity.boundingBox.offset(0.0D, 0.0D, p_70091_5_);
				
				if (!mc_Entity.field_70135_K && d8 != p_70091_5_)
				{
					p_70091_5_ = 0.0D;
					p_70091_3_ = 0.0D;
					p_70091_1_ = 0.0D;
				}
				
				if (!mc_Entity.field_70135_K && d7 != p_70091_3_)
				{
					p_70091_5_ = 0.0D;
					p_70091_3_ = 0.0D;
					p_70091_1_ = 0.0D;
				}
				else
				{
					p_70091_3_ = (double)(-mc_Entity.stepHeight);
					
					for (k = 0; k < list.size(); ++k)
					{
						p_70091_3_ = ((AxisAlignedBB)list.get(k)).calculateYOffset(mc_Entity.boundingBox, p_70091_3_);
					}
					
					mc_Entity.boundingBox.offset(0.0D, p_70091_3_, 0.0D);
				}
				
				if (d12 * d12 + d11 * d11 >= p_70091_1_ * p_70091_1_ + p_70091_5_ * p_70091_5_)
				{
					p_70091_1_ = d12;
					p_70091_3_ = d10;
					p_70091_5_ = d11;
					mc_Entity.boundingBox.setBB(axisalignedbb1);
				}
			}
			
			mc_Entity.worldObj.theProfiler.endSection();
			mc_Entity.worldObj.theProfiler.startSection("rest");
			mc_Entity.posX = (mc_Entity.boundingBox.minX + mc_Entity.boundingBox.maxX) / 2.0D;
			mc_Entity.posY = mc_Entity.boundingBox.minY + (double)mc_Entity.yOffset - (double)mc_Entity.ySize;
			mc_Entity.posZ = (mc_Entity.boundingBox.minZ + mc_Entity.boundingBox.maxZ) / 2.0D;
			mc_Entity.isCollidedHorizontally = d6 != p_70091_1_ || d8 != p_70091_5_;
			mc_Entity.isCollidedVertically = d7 != p_70091_3_;
			mc_Entity.onGround = d7 != p_70091_3_ && d7 < 0.0D;
			mc_Entity.isCollided = mc_Entity.isCollidedHorizontally || mc_Entity.isCollidedVertically;
			((HasBaseLogic)mc_Entity).updateFallState_public(p_70091_3_, mc_Entity.onGround);
			
			if (d6 != p_70091_1_)
			{
				mc_Entity.motionX = 0.0D;
			}
			
			if (d7 != p_70091_3_)
			{
				mc_Entity.motionY = 0.0D;
			}
			
			if (d8 != p_70091_5_)
			{
				mc_Entity.motionZ = 0.0D;
			}
			
			d12 = mc_Entity.posX - d3;
			d10 = mc_Entity.posY - d4;
			d11 = mc_Entity.posZ - d5;
			
			if (!flag && mc_Entity.ridingEntity == null)
			{
				int j1 = MathHelper.floor_double(mc_Entity.posX);
				k = MathHelper.floor_double(mc_Entity.posY - 0.20000000298023224D - (double)mc_Entity.yOffset);
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
				
				mc_Entity.distanceWalkedModified = (float)((double)mc_Entity.distanceWalkedModified + (double)MathHelper.sqrt_double(d12 * d12 + d11 * d11) * 0.6D);
				mc_Entity.distanceWalkedOnStepModified = (float)((double)mc_Entity.distanceWalkedOnStepModified + (double)MathHelper.sqrt_double(d12 * d12 + d10 * d10 + d11 * d11) * 0.6D);
				
				if (mc_Entity.distanceWalkedOnStepModified > (float) HMV_Proxy.getNextstepdistance(mc_Entity) && block.getMaterial() != Material.air)
				{
					HMV_Proxy.setNextstepdistance(mc_Entity,(int)mc_Entity.distanceWalkedOnStepModified + 1);
					
					if (mc_Entity.isInWater())
					{
						float f = MathHelper.sqrt_double(mc_Entity.motionX * mc_Entity.motionX * 0.20000000298023224D + mc_Entity.motionY * mc_Entity.motionY + mc_Entity.motionZ * mc_Entity.motionZ * 0.20000000298023224D) * 0.35F;
						
						if (f > 1.0F)
						{
							f = 1.0F;
						}
						
//						mc_Entity.playSound(mc_Entity.getSwimSound(), f, 1.0F + (rand.nextFloat() - rand.nextFloat()) * 0.4F);
					}
					
//					mc_Entity.func_145780_a(j1, k, l, block);
					block.onEntityWalking(mc_Entity.worldObj, j1, k, l, mc_Entity);
				}
			}
			
			try
			{
				((HasBaseLogic)mc_Entity).func_145775_I_public();
			}
			catch (Throwable throwable)
			{
				CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Checking entity block collision");
				CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being checked for collision");
				mc_Entity.addEntityCrashInfo(crashreportcategory);
				throw new ReportedException(crashreport);
			}
			
			mc_Entity.extinguish();
//
//			if (mc_Entity.worldObj.func_147470_e(mc_Entity.boundingBox.contract(0.001D, 0.001D, 0.001D)))
//			{
//				mc_Entity.dealFireDamage(1);
//			}
//			else if (mc_Entity.fire <= 0)
//			{
//				mc_Entity.fire = -mc_Entity.fireResistance;
//			}
//
//			if (flag2 && mc_Entity.fire > 0)
//			{
//				mc_Entity.playSound("random.fizz", 0.7F, 1.6F + (rand.nextFloat() - rand.nextFloat()) * 0.4F);
//				mc_Entity.fire = -mc_Entity.fireResistance;
//			}
			
			mc_Entity.worldObj.theProfiler.endSection();
		}
	}
	
	
	private void destroyNearBlocks(AxisAlignedBB boundingBox){
		
		int destroy_counterx = 0;
		int destroy_countery = 0;
		int destroy_counterz = 0;
		
		for (int x = (int) boundingBox.minX; x <= boundingBox.maxX; x++) {
			for (int y = (int) boundingBox.minY; y <= boundingBox.maxY + 1; y++) {
				for (int z = (int) boundingBox.minZ; z <= boundingBox.maxZ; z++) {
					Block collidingblock = worldObj.getBlock(x, y, z);
					if (sqrt(mc_Entity.motionX * mc_Entity.motionX +mc_Entity.motionY *mc_Entity.motionY +mc_Entity.motionZ *mc_Entity.motionZ) > collidingblock.getBlockHardness(null, 0, 0, 0)/2 || is_forceBrakeBrock(collidingblock)) {
						if(!worldObj.isAirBlock(x, y, z)){
							worldObj.setBlockToAir(x,y,z);
							mc_Entity.worldObj.playAuxSFX(2001, x,y,z, Block.getIdFromBlock(collidingblock));
							destroy_counterx++;
							destroy_countery++;
							destroy_counterz++;
						}
					}
				}
			}
		}
		for (int x = (int) boundingBox.maxX; x <= boundingBox.maxX + 2; x++) {
			for (int y = (int) boundingBox.minY+1; y <= boundingBox.maxY; y++) {
				for (int z = (int) boundingBox.minZ; z <= boundingBox.maxZ; z++) {
					Block collidingblock = worldObj.getBlock(x, y, z);
					if (abs(mc_Entity.motionX) > collidingblock.getBlockHardness(null, 0, 0, 0)/2 || is_forceBrakeBrock(collidingblock)) {
						if(!worldObj.isAirBlock(x, y, z)) {
							worldObj.setBlockToAir(x,y,z);
							mc_Entity.worldObj.playAuxSFX(2001, x,y,z, Block.getIdFromBlock(collidingblock));
							destroy_counterx++;
						}
					}
				}
			}
		}
		for (int x = (int) boundingBox.minX - 2; x <= boundingBox.minX; x++) {
			for (int y = (int) boundingBox.minY+1; y <= boundingBox.maxY; y++) {
				for (int z = (int) boundingBox.minZ; z <= boundingBox.maxZ; z++) {
					Block collidingblock = worldObj.getBlock(x, y, z);
					if (abs(mc_Entity.motionX) > collidingblock.getBlockHardness(null, 0, 0, 0)/2 || is_forceBrakeBrock(collidingblock)) {
						if(!worldObj.isAirBlock(x, y, z)){
							worldObj.setBlockToAir(x,y,z);
							mc_Entity.worldObj.playAuxSFX(2001, x,y,z, Block.getIdFromBlock(collidingblock));
							destroy_counterx++;
						}
					}
				}
			}
		}
		for (int x = (int) boundingBox.minX; x <= boundingBox.maxX; x++) {
			for (int y = (int) boundingBox.minY+1; y <= boundingBox.maxY; y++) {
				for (int z = (int) boundingBox.maxZ; z <= boundingBox.maxZ + 2; z++) {
					Block collidingblock = worldObj.getBlock(x, y, z);
					if (abs(mc_Entity.motionZ) > collidingblock.getBlockHardness(null, 0, 0, 0)/2 || is_forceBrakeBrock(collidingblock)) {
						if(!worldObj.isAirBlock(x, y, z)){
							worldObj.setBlockToAir(x,y,z);
							mc_Entity.worldObj.playAuxSFX(2001, x,y,z, Block.getIdFromBlock(collidingblock));
							destroy_counterz++;
						}
					}
				}
			}
		}
		for (int x = (int) boundingBox.minX; x <= boundingBox.maxX; x++) {
			for (int y = (int) boundingBox.minY+1; y <= boundingBox.maxY; y++) {
				for (int z = (int) boundingBox.minZ - 2; z <= boundingBox.minZ; z++) {
					Block collidingblock = worldObj.getBlock(x, y, z);
					if (abs(mc_Entity.motionZ) > collidingblock.getBlockHardness(null, 0, 0, 0)/2 || is_forceBrakeBrock(collidingblock)) {
						if(!worldObj.isAirBlock(x, y, z)){
							worldObj.setBlockToAir(x,y,z);
							mc_Entity.worldObj.playAuxSFX(2001, x,y,z, Block.getIdFromBlock(collidingblock));
							destroy_counterz++;
						}
					}
				}
			}
		}
		
		
		for (int x = (int) boundingBox.maxX; x <= boundingBox.maxX + 2; x++) {
			for (int y = (int) boundingBox.minY+1; y <= boundingBox.maxY; y++) {
				for (int z = (int) boundingBox.maxZ; z <= boundingBox.maxZ + 2; z++) {
					Block collidingblock = worldObj.getBlock(x, y, z);
					if (sqrt(mc_Entity.motionX * mc_Entity.motionX +mc_Entity.motionZ *mc_Entity.motionZ) > collidingblock.getBlockHardness(null, 0, 0, 0)/2 || is_forceBrakeBrock(collidingblock)) {
						if(!worldObj.isAirBlock(x, y, z)){
							worldObj.setBlockToAir(x,y,z);
							mc_Entity.worldObj.playAuxSFX(2001, x,y,z, Block.getIdFromBlock(collidingblock));
							destroy_counterx++;
							destroy_counterz++;
						}
					}
				}
			}
		}
		for (int x = (int) boundingBox.minX - 2; x <= boundingBox.minX; x++) {
			for (int y = (int) boundingBox.minY+1; y <= boundingBox.maxY; y++) {
				for (int z = (int) boundingBox.minZ - 2; z <= boundingBox.minZ; z++) {
					Block collidingblock = worldObj.getBlock(x, y, z);
					if (sqrt(mc_Entity.motionX * mc_Entity.motionX +mc_Entity.motionZ *mc_Entity.motionZ) > collidingblock.getBlockHardness(null, 0, 0, 0)/2 || is_forceBrakeBrock(collidingblock)) {
						if(!worldObj.isAirBlock(x, y, z)){
							worldObj.setBlockToAir(x,y,z);
							mc_Entity.worldObj.playAuxSFX(2001, x,y,z, Block.getIdFromBlock(collidingblock));
							destroy_counterx++;
							destroy_counterz++;
						}
					}
				}
			}
		}
		
		for (int x = (int) boundingBox.maxX; x <= boundingBox.maxX + 2; x++) {
			for (int y = (int) boundingBox.minY+1; y <= boundingBox.maxY; y++) {
				for (int z = (int) boundingBox.maxZ; z <= boundingBox.maxZ + 2; z++) {
					Block collidingblock = worldObj.getBlock(x, y, z);
					if (sqrt(mc_Entity.motionX * mc_Entity.motionX +mc_Entity.motionZ *mc_Entity.motionZ) > collidingblock.getBlockHardness(null, 0, 0, 0)/2 || is_forceBrakeBrock(collidingblock)) {
						if(!worldObj.isAirBlock(x, y, z)){
							worldObj.setBlockToAir(x,y,z);
							mc_Entity.worldObj.playAuxSFX(2001, x,y,z, Block.getIdFromBlock(collidingblock));
							destroy_counterx++;
							destroy_counterz++;
						}
					}
				}
			}
		}
		for (int x = (int) boundingBox.minX - 2; x <= boundingBox.minX; x++) {
			for (int y = (int) boundingBox.minY+1; y <= boundingBox.maxY; y++) {
				for (int z = (int) boundingBox.minZ - 2; z <= boundingBox.minZ; z++) {
					Block collidingblock = worldObj.getBlock(x, y, z);
					if (sqrt(mc_Entity.motionX * mc_Entity.motionX +mc_Entity.motionZ *mc_Entity.motionZ) > collidingblock.getBlockHardness(null, 0, 0, 0)/2 || is_forceBrakeBrock(collidingblock)) {
						if(!worldObj.isAirBlock(x, y, z)){
							worldObj.setBlockToAir(x,y,z);
							mc_Entity.worldObj.playAuxSFX(2001, x,y,z, Block.getIdFromBlock(collidingblock));
							destroy_counterx++;
							destroy_counterz++;
						}
					}
				}
			}
		}
		
		
		for(int temp= 0;temp < destroy_counterx;temp ++ ){
			mc_Entity.motionX *= 0.95;
		}
		for(int temp= 0;temp < destroy_countery;temp ++ ){
			mc_Entity.motionY *= 0.95;
		}
		for(int temp= 0;temp < destroy_counterz;temp ++ ){
			mc_Entity.motionZ *= 0.95;
		}
		
	}
	boolean is_forceBrakeBrock(Block collidingblock){
		return collidingblock.getMaterial() == Material.leaves ||
				       collidingblock.getMaterial() == Material.wood ||
				       collidingblock.getMaterial() == Material.cactus ||
				       collidingblock.getMaterial() == Material.glass;
	}
	
	public void setinfo(Prefab_Vehicle_Base info) {
		this.info = info;
		this.allturrets = new TurretObj[info.prefab_attachedWeapons_all.length];
		this.turrets = info.getTurretOBJs(worldObj,this.iVehicle,this.allturrets);
		this.seatInfos = info.getSeatInfoOBJs(allturrets);
		this.seatInfos_zoom = info.getSeatInfoOBJs_zoom(allturrets);
		this.riddenByEntities = new Entity[seatInfos.length];
		this.prefab_vehicle = info;
	}
	
	
	
	public Entity[] getRiddenEntityList(){
		return riddenByEntities;
	}
	
	public SeatInfo[] getRiddenSeatList() {
		return seatInfos;
	}
	
	public boolean pickupEntity(Entity p_70085_1_, int StartSeachSeatNum){
		if(isRidingEntity(p_70085_1_))return false;
		boolean flag = false;
		if(!mc_Entity.worldObj.isRemote) {
			for (int cnt = 0; cnt < riddenByEntities.length; cnt++) {
				int tempid = cnt + StartSeachSeatNum;
				while(tempid < 0)tempid = tempid + riddenByEntities.length;
				while(tempid >= seatInfos.length)tempid = tempid - riddenByEntities.length;
				if (riddenByEntities[tempid] == null) {
					riddenByEntities[tempid] = p_70085_1_;
					flag = true;
					break;
				}
			}
			if (flag)
				HMVPacketHandler.INSTANCE.sendToAll(new HMVPacketPickNewEntity(mc_Entity.getEntityId(), riddenByEntities));
		}
		return flag;
//		p_70085_1_.mountEntity(this);
	}
	public boolean isRidingEntity(Entity entity){
		for(Entity aRiddenby: riddenByEntities){
			if(entity == aRiddenby)return true;
		}
		return false;
	}
	public void updateRider(){
		int cnt = 0;
		for (Entity entity : riddenByEntities) {
			if (entity != null) {
				if((mc_Entity.worldObj.isRemote && entity == HMV_Proxy.getEntityPlayerInstance())) {
					HMV_Proxy.setPlayerSeatID(cnt);
					if( entity.isSneaking())
					HMVPacketHandler.INSTANCE.sendToServer(new HMVPacketDisMountEntity(mc_Entity.getEntityId(),entity.getEntityId()));
				}else
				if(!mc_Entity.worldObj.isRemote && entity.isDead){
//						System.out.println("debug");
					riddenByEntities[cnt] = null;
					entity.ridingEntity = null;
					HMVPacketHandler.INSTANCE.sendToAll(new HMVPacketPickNewEntity(mc_Entity.getEntityId(),riddenByEntities));
				}else {
					entity.ridingEntity = mc_Entity;
				}
			}
			cnt ++;
		}
		mc_Entity.riddenByEntity = riddenByEntities[0];
	}
	
	public void riderPosUpdate(){
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
				TurretObj seatmaingun = seatInfos[cnt].maingun;
				TurretObj seatsubgun = seatInfos[cnt].subgun;
				if(seatmaingun != null) {
					seatmaingun.currentEntity = entity;
					seatmaingun.motherEntity = mc_Entity;
					if(seatInfos[cnt].hasParentGun) {
						seatmaingun.update(bodyRot, new Vector3d(mc_Entity.posX, mc_Entity.posY, -mc_Entity.posZ));
					}
					if(seatInfos[cnt].prefab_seat.aimGun && !worldObj.isRemote) {
						if (entity instanceof EntityLiving && ((EntityLiving) entity).getAttackTarget() != null) {
							seatmaingun.aimToEntity(((EntityLiving) entity).getAttackTarget());
						} else {
							seatmaingun.aimtoAngle(entity.getRotationYawHead(), entity.rotationPitch);
						}
					}
					if(seatsubgun!=null){
						seatsubgun.currentEntity = entity;
						seatsubgun.motherEntity = mc_Entity;
						if(seatInfos[cnt].hasParentGun) {
							seatsubgun.update(bodyRot, new Vector3d(mc_Entity.posX, mc_Entity.posY, -mc_Entity.posZ));
						}
						if(seatInfos[cnt].prefab_seat.aimGun && !worldObj.isRemote) {
							if (entity instanceof EntityLiving && ((EntityLiving) entity).getAttackTarget() != null) {
								seatsubgun.aimToEntity(((EntityLiving) entity).getAttackTarget());
							} else {
								seatsubgun.aimtoAngle(entity.getRotationYawHead(), entity.rotationPitch);
							}
						}
					}
					if(seatInfos[cnt].prefab_seat.seatOnTurret) {
						Vector3d temp = new Vector3d(seatmaingun.pos);
						Vector3d tempplayerPos = new Vector3d(HMV_Proxy.iszooming() && seatInfos_zoom.length > cnt && seatInfos_zoom[cnt] != null ? seatInfos_zoom[cnt].pos : seatInfos[cnt].pos);
						Vector3d temp2 = seatmaingun.getGlobalVector_fromLocalVector_onTurretPoint(tempplayerPos);
						temp.add(temp2);
						transformVecforMinecraft(temp);
//			System.out.println(temp);
						entity.setPosition(temp.x,
								temp.y,
								temp.z);
						entity.posX = temp.x;
						entity.posY = temp.y;
						entity.posZ = temp.z;
					}
					entity.motionX = mc_Entity.motionX;
					entity.motionY = mc_Entity.motionY;
					entity.motionZ = mc_Entity.motionZ;
				}else {
					Vector3d tempplayerPos = new Vector3d(HMV_Proxy.iszooming() && seatInfos_zoom.length > cnt && seatInfos_zoom[cnt] != null? seatInfos_zoom[cnt].pos: seatInfos[cnt].pos);
					tempplayerPos.sub(new Vector3d(0,0,0));
					Vector3d temp = transformVecByQuat(tempplayerPos, bodyRot);
					transformVecforMinecraft(temp);
					temp.add(new Vector3d(0,0,0));
//				System.out.println("" + temp);
					temp.add(thispos);
					entity.setPosition(temp.x,
							temp.y,
							temp.z);
					entity.posX = temp.x;
					entity.posY = temp.y;
					entity.posZ = temp.z;
					entity.motionX = mc_Entity.motionX;
					entity.motionY = mc_Entity.motionY;
					entity.motionZ = mc_Entity.motionZ;
				}
				
				seatInfos[cnt].currentSeatOffset_fromV.sub(new Vector3d(entity.posX, entity.posY, entity.posZ), thispos);
				if(worldObj.isRemote){
					if(entity == HMV_Proxy.getEntityPlayerInstance()){
						
						if(seatmaingun != null)HMVPacketHandler.INSTANCE.sendToServer(new HMVPacketTriggerSeatGun(HMV_Proxy.leftclick(), HMV_Proxy.rightclick(), mc_Entity.getEntityId(), cnt));
						if(HMV_Proxy.next_Seatclick())HMVPacketHandler.INSTANCE.sendToServer(new HMVPacketChangeSeat(mc_Entity.getEntityId(),cnt,true));
						else
						if(HMV_Proxy.previous_Seatclick())HMVPacketHandler.INSTANCE.sendToServer(new HMVPacketChangeSeat(mc_Entity.getEntityId(),cnt,false));
					}
				}else {
					if(seatmaingun != null) {
						if (entity instanceof EntityLiving) {
							if (mc_Entity instanceof EntityLiving && ((EntityLiving) mc_Entity).getAttackTarget() != null) {
								((EntityLiving) entity).getLookHelper().setLookPositionWithEntity(((EntityLiving) mc_Entity).getAttackTarget(),180,180);
								((EntityLiving) entity).setAttackTarget(((EntityLiving) mc_Entity).getAttackTarget());
								seatInfos[cnt].gunTrigger1 = entity.getEntityData().getBoolean("HMGisUsingItem") || ((EntityLiving) entity).canEntityBeSeen(((EntityLiving) mc_Entity).getAttackTarget());
							}
						}
						if (!seatInfos[cnt].prefab_seat.aimGun || seatmaingun.aimIn) {
							if (seatInfos[cnt].gunTrigger1) {
								seatmaingun.fireall();
							}
						}
//					System.out.println("" + riddenByEntitiesInfo[cnt].gunTrigger1);
						seatInfos[cnt].gunTrigger1 = false;
					}
					if(seatsubgun != null) {
						if (entity instanceof EntityLiving) {
							if (mc_Entity instanceof EntityLiving && ((EntityLiving) mc_Entity).getAttackTarget() != null) {
								((EntityLiving) entity).getLookHelper().setLookPositionWithEntity(((EntityLiving) mc_Entity).getAttackTarget(),180,180);
								((EntityLiving) entity).setAttackTarget(((EntityLiving) mc_Entity).getAttackTarget());
								seatInfos[cnt].gunTrigger2 = entity.getEntityData().getBoolean("HMGisUsingItem") || ((EntityLiving) entity).canEntityBeSeen(((EntityLiving) mc_Entity).getAttackTarget());
							}
						}
						if (!seatInfos[cnt].prefab_seat.aimGun || seatsubgun.aimIn) {
							if (seatInfos[cnt].gunTrigger2) {
								seatsubgun.fireall();
							}
						}
//					System.out.println("" + riddenByEntitiesInfo[cnt].gunTrigger1);
						seatInfos[cnt].gunTrigger2 = false;
					}
				}
				entity.ridingEntity = mc_Entity;
				if(entity instanceof IhasprevRidingEntity)((IhasprevRidingEntity) entity).setprevRidingEntity(mc_Entity);
				
			}
			cnt++;
		}
		mc_Entity.riddenByEntity = null;
	}
	
	public void updateCommon(){
		for(TurretObj aturret :turrets){
			aturret.update(bodyRot,new Vector3d(mc_Entity.posX,mc_Entity.posY,-mc_Entity.posZ));
		}
		if(!worldObj.isRemote){
			Quat4d Headrot = new Quat4d(0, 0, 0, 1);
			Headrot = quatRotateAxis(Headrot, new AxisAngle4d(unitX, toRadians(this.cameraPitch) / 2));
			Headrot = quatRotateAxis(Headrot, new AxisAngle4d(unitY, toRadians(this.cameraYaw) / 2));
			this.camerarot.set(Headrot);
			this.camerarot_current.set(this.camerarot);
		}
		if (riddenByEntities[0] != null) {
			Quat4d currentcamRot = new Quat4d(bodyRot);
			currentcamRot.mul(HMV_Proxy.iszooming() && prefab_vehicle.camerarot_zoom != null? prefab_vehicle.camerarot_zoom : camerarot_current);
			double[] cameraxyz = Utils.eulerfrommatrix(Utils.matrixfromQuat(currentcamRot));
			cameraxyz[0] = toDegrees(cameraxyz[0]);
			cameraxyz[1] = toDegrees(cameraxyz[1]);
			cameraxyz[2] = toDegrees(cameraxyz[2]);
			riddenByEntities[0].rotationYaw = (float) cameraxyz[1];
			riddenByEntities[0].prevRotationYaw = (float) cameraxyz[1];
			riddenByEntities[0].setRotationYawHead((float) cameraxyz[1]);
			riddenByEntities[0].rotationPitch = (float) cameraxyz[0];
			riddenByEntities[0].prevRotationPitch = (float) cameraxyz[0];
		}
	}
	
	
	public void collideWithNearbyEntities()
	{
		List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this.mc_Entity, this.mc_Entity.boundingBox.expand(0.20000000298023224D, 0.0D, 0.20000000298023224D));
		
		if (list != null && !list.isEmpty())
		{
			for (int i = 0; i < list.size(); ++i)
			{
				Entity entity = (Entity)list.get(i);
				
				if (entity.canBePushed() && entity.width > 1.5)
				{
					iVehicle.public_collideWithEntity(entity);
				}
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
				d2 = (double)MathHelper.sqrt_double(d2);
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
				d0 *= (double)(1.0F - this.mc_Entity.entityCollisionReduction);
				d1 *= (double)(1.0F - this.mc_Entity.entityCollisionReduction);
				this.mc_Entity.addVelocity(-d0, 0.0D, -d1);
				p_70108_1_.addVelocity(d0, 0.0D, d1);
			}
		}
	}
	
	
	public void moveEntityWithHeading(float p_70612_1_, float p_70612_2_)
	{
		
		if(!worldObj.isRemote) {
			if (this.mc_Entity.isInWater() || ishittingWater()) {
				if(this.mc_Entity.isInWater()){
					this.mc_Entity.motionY += 0.02D;
				}else {
					this.mc_Entity.motionY -= 0.01D;
				}
				
				float f2;
				f2 = this.mc_Entity.worldObj.getBlock(MathHelper.floor_double(this.mc_Entity.posX), MathHelper.floor_double(this.mc_Entity.boundingBox.minY) - 1, MathHelper.floor_double(this.mc_Entity.posZ)).slipperiness * 0.91F;
				float f3 = 0.16277136F / (f2 * f2 * f2);
				float f4;
				f4 = ((EntityLivingBase)this.mc_Entity).getAIMoveSpeed() * f3;
				
				this.mc_Entity.moveFlying(p_70612_1_, p_70612_2_, f4);
				this.mc_Entity.moveEntity(this.mc_Entity.motionX, this.mc_Entity.motionY, this.mc_Entity.motionZ);
				handleWaterMovement();
				this.mc_Entity.motionX *= 0.800000011920929D;
				this.mc_Entity.motionY *= 0.800000011920929D;
				this.mc_Entity.motionZ *= 0.800000011920929D;
				this.mc_Entity.setAir(0);
			} else if (this.mc_Entity.handleLavaMovement()) {
				this.mc_Entity.moveFlying(p_70612_1_, p_70612_2_, 0.02F);
				this.mc_Entity.motionY -= 0.02D;
				this.mc_Entity.moveEntity(this.mc_Entity.motionX, this.mc_Entity.motionY, this.mc_Entity.motionZ);
				this.mc_Entity.motionX *= 0.5D;
				this.mc_Entity.motionY *= 0.5D;
				this.mc_Entity.motionZ *= 0.5D;
			} else {
				float f2 = 0.91F;
				
				if (this.mc_Entity.onGround) {
					f2 = 0;
				}
				
				float f3 = 0.16277136F / (f2 * f2 * f2);
				float f4;
				
				if (this.mc_Entity.onGround) {
					f4 = ((EntityLivingBase)this.mc_Entity).getAIMoveSpeed() * f3;
				} else {
					f4 = ((EntityLivingBase)this.mc_Entity).jumpMovementFactor;
				}
				f2 = 0.91F;
				
				if (this.mc_Entity.onGround) {
					f2 = this.mc_Entity.worldObj.getBlock(MathHelper.floor_double(this.mc_Entity.posX), MathHelper.floor_double(this.mc_Entity.boundingBox.minY) - 1, MathHelper.floor_double(this.mc_Entity.posZ)).slipperiness * 0.91F;
				}
				
				this.mc_Entity.motionY -= 0.08D;
				
				this.mc_Entity.moveEntity(this.mc_Entity.motionX, this.mc_Entity.motionY, this.mc_Entity.motionZ);
				
				this.mc_Entity.motionY *= 0.9800000190734863D;
				this.mc_Entity.motionX *= (double) f2;
				this.mc_Entity.motionZ *= (double) f2;
			}
		}
	}
	
	public boolean handleWaterMovement()
	{
		if (this.mc_Entity.worldObj.handleMaterialAcceleration(this.mc_Entity.boundingBox.expand(0.0D, -0.4000000059604645D, 0.0D).contract(0.001D, 0.001D, 0.001D).offset(0,info.draft,0), Material.water, this.mc_Entity))
		{
			if (!iVehicle.getinWater())
			{
				float f = MathHelper.sqrt_double(this.mc_Entity.motionX * this.mc_Entity.motionX * 0.20000000298023224D + this.mc_Entity.motionY * this.mc_Entity.motionY + this.mc_Entity.motionZ * this.mc_Entity.motionZ * 0.20000000298023224D) * 0.2F;
				
				if (f > 1.0F)
				{
					f = 1.0F;
				}
				
				this.mc_Entity.playSound(info.splashsound, f, 1.0F + (rand.nextFloat() - rand.nextFloat()) * 0.4F);
			}
			
			this.mc_Entity.fallDistance = 0.0F;
			iVehicle.setinWater(true);
		}
		else
		{
			iVehicle.setinWater(false);
		}
		
		return iVehicle.getinWater();
	}
	public boolean ishittingWater()
	{
		boolean inWater = false;
		if (this.mc_Entity.worldObj.handleMaterialAcceleration(this.mc_Entity.boundingBox.expand(0.0D, -0.4000000059604645D, 0.0D).contract(0.001D, 0.001D, 0.001D).offset(0,0,0), Material.water, this.mc_Entity))
		{
			inWater = true;
		}
		else
		{
			inWater = false;
		}
		
		return inWater;
	}
	
	public void setPosition(double p_70107_1_, double p_70107_3_, double p_70107_5_)
	{
		mc_Entity.posX = p_70107_1_;
		mc_Entity.posY = p_70107_3_;
		mc_Entity.posZ = p_70107_5_;
		float f = mc_Entity.width / 2.0F;
		float f1 = mc_Entity.height;
		mc_Entity.boundingBox.setBounds(p_70107_1_ - (double)f, p_70107_3_ - (double) mc_Entity.yOffset + (double) mc_Entity.ySize, p_70107_5_ - (double)f, p_70107_1_ + (double)f, p_70107_3_ - (double) mc_Entity.yOffset + (double) mc_Entity.ySize + (double)f1, p_70107_5_ + (double)f);
		if(mc_Entity.boundingBox instanceof ModifiedBoundingBox){
			((ModifiedBoundingBox) mc_Entity.boundingBox).update(mc_Entity.posX,
					mc_Entity.posY,
					mc_Entity.posZ);
		}
	}
	
	public void riderPosUpdate_forRender(Vector3d thispos){
		
		int cnt = 0;
//		System.out.println("thispos  " + thispos);
		for (Entity entity : riddenByEntities) {
			if (entity != null) {
				entity.setLocationAndAngles(thispos.x + seatInfos[cnt].currentSeatOffset_fromV.x,
						thispos.y + seatInfos[cnt].currentSeatOffset_fromV.y - entity.yOffset,
						thispos.z + seatInfos[cnt].currentSeatOffset_fromV.z,entity.rotationYaw,entity.rotationPitch);
			}
			cnt++;
		}
	}
	
	public Prefab_Vehicle_Base prefab_vehicle;
	public float health = 150;
	public float maxhealth = 150;
	public float mousex;
	public float mousey;
	public float yawrudder;
	public float rollrudder;
	public float pitchrudder;
	
	public boolean climbYawDir=new Random().nextBoolean();
	public boolean mouseStickMode = true;
	
	public int mode = 0;//0:attack 1:leave 2:follow player 3:go to home
	public boolean trigger1 = false;
	public boolean trigger2 = false;
	//	public double[][] gunpos = new double[6][3];
	public Vector3d prevmotionVec = new Vector3d(0,0,0);
	Vector3d unitX = new Vector3d(1,0,0);
	Vector3d unitY = new Vector3d(0,1,0);
	Vector3d unitZ = new Vector3d(0,0,1);
	public EntityCameraDummy camera;
	public Quat4d camerarot = new Quat4d(0,0,0,1);
	public Quat4d camerarot_current = new Quat4d(0,0,0,1);
	//	public double[] camerapos_zoom = new double[]{0,2.5-0.21,-3.6};
	
	public float cameraYaw;
	public float cameraPitch;
	
	public int gearprogress;
	public int flaplevel;
	
	
	
	
	
	Hasmode vehicle_hasmode;
	
//	public boolean rising_after_Attack;
//
//	public boolean T_useMain_F_useSub = true;
//	public boolean T_StartDive_F_FlyToStartDivePos = true;
//	public int changeWeaponCycle;
//
//	public int outSightCnt = 0;
	
	
	
	
	public boolean serverspace = false;
	public boolean serverw = false;
	public boolean servers = false;
	public boolean servera = false;
	public boolean serverd = false;
	public boolean serverf = false;
	public boolean serverx = false;
	
	Random rand = new Random();
	
	public BaseLogic(World world, Entity entity) {
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
		if(entity instanceof Hasmode) vehicle_hasmode = (Hasmode) entity;
		
		camera = new EntityCameraDummy(this.worldObj);
	}
	public void onUpdate(){
		prevbodyRot.set(bodyRot);
		((ModifiedBoundingBox) mc_Entity.boundingBox).rot.set(this.bodyRot);
		((ModifiedBoundingBox) mc_Entity.boundingBox).update(mc_Entity.posX, mc_Entity.posY, mc_Entity.posZ);
		
		prevmotionVec.set(mc_Entity.motionX, mc_Entity.motionY, mc_Entity.motionZ);
		Vector3d tailwingvector = Utils.transformVecByQuat(new Vector3d(unitY), bodyRot);
		Vector3d bodyvector = Utils.transformVecByQuat(new Vector3d(unitZ), bodyRot);
		Vector3d mainwingvector = Utils.transformVecByQuat(new Vector3d(unitX), bodyRot);
		tailwingvector.normalize();
		bodyvector.normalize();
		mainwingvector.normalize();
		Utils.transformVecforMinecraft(tailwingvector);
		Utils.transformVecforMinecraft(bodyvector);
		Utils.transformVecforMinecraft(mainwingvector);
		if(needStartSound){
			needStartSound = false;
			if(mc_Entity.worldObj.isRemote) HMV_Proxy.playsoundasVehicle(1024, mc_Entity);
		}
		needStartSound = getsound() != null;
		if(mc_Entity instanceof EntityLivingBase)this.health = ((EntityLivingBase) mc_Entity).getHealth();
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
			if(riddenByEntities[iVehicle.getpilotseatid()] == HMV_Proxy.getEntityPlayerInstance()) {
				camera.setLocationAndAngles(
						mc_Entity.posX + bodyvector.x + tailwingvector.x + mainwingvector.x,
						mc_Entity.posY + bodyvector.y + tailwingvector.y + mainwingvector.y,
						mc_Entity.posZ + bodyvector.z + tailwingvector.z + mainwingvector.z,
						bodyrotationYaw,bodyrotationPitch);
			}else{
				tailwingvector = Utils.transformVecByQuat(new Vector3d(unitY), bodyRot);
				bodyvector = Utils.transformVecByQuat(new Vector3d(unitZ), bodyRot);
				mainwingvector = Utils.transformVecByQuat(new Vector3d(unitX), bodyRot);
				tailwingvector.normalize();
				bodyvector.normalize();
				mainwingvector.normalize();
				Utils.transformVecforMinecraft(tailwingvector);
				Utils.transformVecforMinecraft(bodyvector);
				Utils.transformVecforMinecraft(mainwingvector);
				
				double[] xyz = Utils.eulerfrommatrix(Utils.matrixfromQuat(bodyRot));
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
			
			FCS(mainwingvector,tailwingvector,bodyvector);
//			if(mc_Entity instanceof Hasmode && ((Hasmode) mc_Entity).standalone()){
//				autocontrol(bodyvector);
//			}
			HMVPacketHandler.INSTANCE.sendToAll(new HMVPakcetVehicleState(mc_Entity.getEntityId(),bodyRot, throttle,trigger1,trigger2));
//			turret(mainwingvector,tailwingvector,bodyvector);
			
			double[] xyz = Utils.eulerfrommatrix(Utils.matrixfromQuat(bodyRot));
			bodyrotationPitch = (float) toDegrees(xyz[0]);
			if(!Double.isNaN(xyz[1])){
				bodyrotationYaw = (float) toDegrees(xyz[1]);
			}
			bodyrotationRoll = (float) toDegrees(xyz[2]);
			
			
			mc_Entity.rotationYaw = bodyrotationYaw;
			mc_Entity.rotationPitch = bodyrotationPitch;
		}
		control(bodyvector);
		prev_pera_trackPos = pera_trackPos;
		pera_trackPos += throttle*10;
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
		
		if(mc_Entity instanceof IMultiTurretVehicle){
			HMVPacketHandler.INSTANCE.sendToAll(new HMVPakcetVehicleTurretSync(mc_Entity.getEntityId(), (IMultiTurretVehicle) mc_Entity));
		}
		
		updateCommon();
		updateRider();
		riderPosUpdate();
	}
	
	
	
	public void setVelocity(double p_70016_1_, double p_70016_3_, double p_70016_5_)
	{
	}
	boolean standalone(){
		return vehicle_hasmode != null && vehicle_hasmode.standalone();
	}
	void control(Vector3d bodyvector){
		if(worldObj.isRemote && !standalone() && ispilot(HMV_Proxy.getEntityPlayerInstance())){
			ArrayList<Integer> keys = new ArrayList<>();
			if(HMG_proxy.getMCInstance().inGameHasFocus) {
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
				if (serverw = HMV_Proxy.throttle_up_click()) {
					keys.add(16);
				}
				if (servera = HMV_Proxy.yaw_Left_click()) {
					keys.add(17);
				}
				if (servers = HMV_Proxy.throttle_down_click()) {
					keys.add(18);
				}
				if (serverd = HMV_Proxy.yaw_Right_click()) {
					keys.add(19);
				}
				if (serverf = HMV_Proxy.flap_click()) {
					keys.add(20);
				}
				int[] keys_array = new int[keys.size()];
				for (int id = 0; id < keys_array.length; id++) {
					keys_array[id] = keys.get(id);
				}
				HMVPacketHandler.INSTANCE.sendToServer(new HMVMMessageKeyPressed(keys_array, mc_Entity.getEntityId()));
				if (!FMLClientHandler.instance().getClient().isGamePaused() && Display.isActive()) {
					if (mouseStickMode) {
						mousex += ((float) Mouse.getDX()) / 4;
						mousey += ((float) Mouse.getDY()) / 4;
//					cameraYaw = 0;
//					cameraPitch = 0;
					}else {
						if (HMV_Proxy.hasStick()) {
							mousex = HMV_Proxy.getXaxis() * 16;
							yawrudder = HMV_Proxy.getZaxis() * 5;
							mousey = -HMV_Proxy.getYaxis() * 16;
						}
					}
					if (HMV_Proxy.changeControlclick()) mouseStickMode = !mouseStickMode;
					if (HMV_Proxy.resetCamrotclick()) {
						cameraYaw = 0;
						cameraPitch = 0;
					}
					if (HMV_Proxy.pitchUp()) mousey = 16;
					if (HMV_Proxy.pitchDown()) mousey = -16;
					if (HMV_Proxy.rollRight()) mousex = 16;
					if (HMV_Proxy.rollLeft()) mousex = -16;
					HMVPacketHandler.INSTANCE.sendToServer(new HMVPacketMouseD(mousex,
							                                                          mousey, yawrudder,cameraYaw,cameraPitch, mc_Entity.getEntityId()));
				}
			}
		}
//		if(!worldObj.isRemote)System.out.println("" + yawladder);
		rollrudder = (abs(mousex) > 16 ? (mousex > 0 ? 16f : -16f) : mousex)/8;
		pitchrudder = (abs(mousey) > 16 ? (mousey > 0 ? 16f : -16f) : mousey)/8;
		
		if(abs(mousex) > 16){
			mousex *= 0.7;
		}else {
			mousex = 0;
		}
		if(abs(mousey) > 16){
			mousey *= 0.7;
		}else {
			mousey = 0;
		}
		if(!worldObj.isRemote) {
//			System.out.println("yawladder " + yawladder);
//		System.out.println("" + pitchladder);
			Vector3d motionvec = new Vector3d(mc_Entity.motionX, mc_Entity.motionY, mc_Entity.motionZ);
			double currentForcedEffect = (mc_Entity.onGround ? prefab_vehicle.forced_rudder_effect_OnGround : prefab_vehicle.forced_rudder_effect);
			double cof = (currentForcedEffect * throttle);
			if(motionvec.length() > 0.001)cof += ((1- currentForcedEffect) * angle_cos(bodyvector, motionvec) * (motionvec.length()));
//			System.out.println("" + cof);
			if(abs(cof) > 0.001) {
				if (abs(pitchrudder) > 0.0001) {
//				Vector3d axisx = Utils.transformVecByQuat(new Vector3d(unitX), bodyRot);
					AxisAngle4d axisxangled = new AxisAngle4d(unitX, toRadians(-pitchrudder / 4 * cof * prefab_vehicle.pitchspeed));
					rotationmotion = Utils.quatRotateAxis(rotationmotion, axisxangled);
				}
				if (abs(yawrudder) > 0.001) {
					AxisAngle4d axisyangled;
					if (mc_Entity.onGround) {
//					Vector3d axisy = Utils.transformVecByQuat(new Vector3d(unitY), bodyRot);
						axisyangled = new AxisAngle4d(unitY, toRadians(yawrudder / 5 * cof * prefab_vehicle.yawspeed_taxing));
					} else {
//					Vector3d axisy = Utils.transformVecByQuat(new Vector3d(unitY), bodyRot);
						axisyangled = new AxisAngle4d(unitY, toRadians(yawrudder / 5 * cof * prefab_vehicle.yawspeed));
					}
					rotationmotion = Utils.quatRotateAxis(rotationmotion, axisyangled);
				}
				if (abs(rollrudder) > 0.0001) {
//				Vector3d axisz = Utils.transformVecByQuat(new Vector3d(unitZ), bodyRot);
					AxisAngle4d axiszangled = new AxisAngle4d(unitZ, toRadians(rollrudder / 4 * cof * prefab_vehicle.rollspeed));
					rotationmotion = Utils.quatRotateAxis(rotationmotion, axiszangled);
				}
			}
			
			double[] xyz = Utils.eulerfrommatrix(Utils.matrixfromQuat(bodyRot));
			bodyrotationPitch = (float) toDegrees(xyz[0]);
			if (!Double.isNaN(xyz[1])) {
				bodyrotationYaw = (float) toDegrees(xyz[1]);
			}
			bodyrotationRoll = (float) toDegrees(xyz[2]);
//		HMVPacketHandler.INSTANCE.sendToServer(new HMVPakcetVehicleState(planebody.getEntityId(),bodyRot, throttle,trigger1,trigger2));
//				if(th<2.5){
//					th +=0.1;
//				}
//				if (proxy.throttle_up_click()) {
//					th += 0.1;
//					GVCMPacketHandler.INSTANCE.sendToServer(new HMVMMessageKeyPressed(16, this.getEntityId()));
//				}
//				if (proxy.yaw_Left_click()) {
////					GVCMPacketHandler.INSTANCE.sendToServer(new HMVMMessageKeyPressed(17, this.getEntityId()));
//					servera = true;
//				}
//				if (proxy.throttle_down_click()) {
//					th -= 0.1;
//					GVCMPacketHandler.INSTANCE.sendToServer(new HMVMMessageKeyPressed(18, this.getEntityId()));
//				}
//				if (proxy.yaw_Right_click()) {
////					GVCMPacketHandler.INSTANCE.sendToServer(new HMVMMessageKeyPressed(19, this.getEntityId()));
//					serverd = true;
//				}
//				if (proxy.leftclick()) {
//					GVCMPacketHandler.INSTANCE.sendToServer(new HMVMMessageKeyPressed(11, this.getEntityId()));
//				}
//				if (proxy.throttle_BrakeKeyDown()) {
//					GVCMPacketHandler.INSTANCE.sendToServer(new HMVMMessageKeyPressed(12, this.getEntityId()));
//				}
//				if (proxy.flap_click()) {
//					GVCMPacketHandler.INSTANCE.sendToServer(new HMVMMessageKeyPressed(20, this.getEntityId()));
//				}
//
//				th -=0.05;
//				if(th>5){
//					th = 5;
//				}
//				if(th<0){
//					th = 0;
//				}
////				GVCMPacketHandler.INSTANCE.sendToServer(new HMVPacketMouseD(Mouse.getDX(),Mouse.getDY(),this.getEntityId()));
//				mousex += Mouse.getDX()*0.01;
//				mousey += Mouse.getDY()*0.01;
//				if (servera) {
//					yawladder +=-1;
//					servera = false;
//				}
//				if (serverd) {
//					yawladder +=1;
//					serverd = false;
//				}
//
//				prevmousex = mousex;
//				if (abs(mousex)>0.00001) {
//					mainwingvector = Utils.rotationVector_byAxisVector(bodyVector,mainwingvector, abs(mousex)>4 ? (mousex>0?4f:-4f):mousex);
//					tailwingvector = Utils.rotationVector_byAxisVector(bodyVector,tailwingvector, abs(mousex)>4 ? (mousex>0?4f:-4f):mousex);
//				}
//				mousex*=0.9;
//
//				prevmousey = mousey;
//				if (abs(mousey)>0.00001) {
//					bodyVector     = Utils.rotationVector_byAxisVector(mainwingvector,bodyVector    ,abs(mousey)>4?(mousey>0?-4f:4f):-mousey);
//					tailwingvector = Utils.rotationVector_byAxisVector(mainwingvector,tailwingvector,abs(mousey)>4?(mousey>0?-4f:4f):-mousey);
//				}
//				mousey*=0.9;
//				prevyawladder = yawladder;
//				yawladder *=0.8;
//				if(abs(yawladder) < 0.001) yawladder = prevyawladder =0;else {
//					bodyVector = Utils.rotationVector_byAxisVector(tailwingvector, bodyVector, yawladder);
//					mainwingvector = Utils.rotationVector_byAxisVector(tailwingvector, mainwingvector, yawladder);
//				}
//				bodyrotationPitch = wrapAngleTo180_float((float) toDegrees(asin(bodyVector.yCoord)));
//
//				Vec3 temp1 = Vec3.createVectorHelper(bodyVector.xCoord,bodyVector.yCoord,bodyVector.zCoord);
//				bodyrotationYaw = wrapAngleTo180_float((float) toDegrees(atan2(temp1.xCoord, temp1.zCoord)));
//
//				if(abs(bodyrotationPitch)<45) {
//					Vec3 temp = Vec3.createVectorHelper(mainwingvector.xCoord,mainwingvector.yCoord,mainwingvector.zCoord);
//					temp.rotateAroundY(-(float) toRadians(bodyrotationYaw));
//					temp.rotateAroundX(-(float) toRadians(bodyrotationPitch));
//					bodyrotationRoll = (float) toDegrees(atan2(temp.yCoord, temp.xCoord));
//				}else {
//					Vec3 temp = Vec3.createVectorHelper(tailwingvector.xCoord,tailwingvector.yCoord,tailwingvector.zCoord);
//					temp.rotateAroundY(-(float) toRadians(bodyrotationYaw));
//					temp.rotateAroundX(-(float) toRadians(bodyrotationPitch));
//					bodyrotationRoll = (float) toDegrees(atan2(temp.yCoord, temp.xCoord))-90;
//				}
//				GVCXMPacketSyncPMCHeliData packet = new GVCXMPacketSyncPMCHeliData(this.getEntityId(),bodyrotationYaw,bodyrotationPitch,bodyrotationRoll);
//				GVCMPacketHandler.INSTANCE.sendToServer(packet);
//				FMLClientHandler.instance().getClient().mouseHelper.deltaX = 0;
//				FMLClientHandler.instance().getClient().mouseHelper.deltaY = 0;
//
//				setRotationYaw(bodyrotationYaw);
//				setRotationPitch(bodyrotationPitch);
//				setRotationRoll(bodyrotationRoll);
		}
		
		if(!standalone()) {
			if (health > 0 && serverw) {
				throttle += 0.05;
			}
			if (health <= 0) {
				throttle -= 0.05;
			}
			if (servera) {
				yawrudder--;
			}
			if (serverd) {
				yawrudder++;
			}
			if (servers) {
				throttle -= 0.05;
			}
			if (prefab_vehicle.T_Land_F_Plane && serverspace) {
				throttle *= 0.75;
			}
		}
		if(abs(yawrudder) < 0.001) yawrudder = 0;
		yawrudder *= 0.8;
		
	}
//	void autocontrol(Vector3d bodyvector){
//		if(health>0){
//			bodyvector = new Vector3d(bodyvector);//copy
//			bodyvector.scale(-1);
//			int genY = this.worldObj.getHeightValue((int) mc_Entity.posX, (int) mc_Entity.posZ);//target alt
//			float alt;
//			alt = (float) (mc_Entity.posY - genY);
//			if(mc_Entity instanceof EntityLiving && ((EntityLiving) mc_Entity).getAttackTarget() != null){
//				if(!T_useMain_F_useSub && subTurret == null)T_useMain_F_useSub = true;
//				EntityLivingBase target = ((EntityLiving) mc_Entity).getAttackTarget();
//				if(target.onGround && genY < target.posY)
//					alt = (float) (mc_Entity.posY - target.posY);
//				Vector3d courseVec = new Vector3d(target.posX,target.posY,target.posZ);
//				courseVec.sub(new Vector3d(mc_Entity.posX, mc_Entity.posY, mc_Entity.posZ));
//				courseVec.normalize();
//
//				float targetyaw = wrapAngleTo180_float(-(float) toDegrees(atan2(courseVec.x, courseVec.z)));
//				double AngulardifferenceYaw = targetyaw - this.bodyrotationYaw;
//				AngulardifferenceYaw = wrapAngleTo180_double(AngulardifferenceYaw);
//				double planespeed = mc_Entity.motionX * mc_Entity.motionX + mc_Entity.motionY * mc_Entity.motionY + mc_Entity.motionZ * mc_Entity.motionZ;
//				TurretObj currentTurret = T_useMain_F_useSub ? mainTurret : subTurret;
//				if(currentTurret != null) {
//					Vector3d currentCannonpos = currentTurret.getCannonPos();
//					double[] elevations = CalculateGunElevationAngle(currentCannonpos.x, currentCannonpos.y, currentCannonpos.z, target, (float) currentTurret.prefab_turret.gunInfo.gravity, currentTurret.prefab_turret.gunInfo.speed + (float) sqrt(planespeed));
//					float targetpitch = (float) -elevations[0];
//					if (!prefab_vehicle.useMain_withSub && (T_useMain_F_useSub ? mainTurret.isreloading() : subTurret.isreloading())) {
//						T_StartDive_F_FlyToStartDivePos = false;
//					}
//					if (!prefab_vehicle.useMain_withSub) {
//						if (T_useMain_F_useSub) {
//							if (mainTurret.isreloading()) {
//								rising_after_Attack = true;
//								T_useMain_F_useSub = !T_useMain_F_useSub;
//								changeWeaponCycle = 0;
//							}
//						} else {
//							if (subTurret.isreloading()) {
//								rising_after_Attack = true;
//								T_useMain_F_useSub = !T_useMain_F_useSub;
//								changeWeaponCycle = 0;
//							}
//						}
//					}
//					double toTargetPitch = -toDegrees(asin(courseVec.y));
//					Vector3d motionvec = new Vector3d(mc_Entity.motionX, mc_Entity.motionY, mc_Entity.motionZ);
//					if (!rising_after_Attack && T_StartDive_F_FlyToStartDivePos) {
////					System.out.println("" + targetpitch);
//						boolean insight = elevations[2] != -1;
//						boolean istarget_onGround = ((target instanceof ITank && ((ITank) target).getBaseLogic().ishittingWater()) || target.isInWater() || target.onGround || (target.ridingEntity != null && target.ridingEntity.onGround));
//
//						if (prefab_vehicle.type_F_Plane_T_Heli) {
//							if ((motionvec.y < -0.1 || alt < prefab_vehicle.cruiseALT))
//								throttle += 0.05;
//							else
//								throttle -= 0.05;
//							if (alt < prefab_vehicle.cruiseALT) T_StartDive_F_FlyToStartDivePos = false;
//						} else if (T_useMain_F_useSub && prefab_vehicle.Dive_bombing && istarget_onGround) {
//							if (toTargetPitch < prefab_vehicle.startDive) {
//								targetpitch = prefab_vehicle.maxClimb;
//								throttle += 0.05;
//								insight = false;
//								outSightCnt--;
//							} else {
//								if (targetpitch < prefab_vehicle.startDive) {
//									float AngulardifferencePitch = targetpitch - bodyrotationPitch;
//									targetpitch = prefab_vehicle.startDive-20;
//									throttle += 0.05;
//									insight = AngulardifferenceYaw > prefab_vehicle.yawsightwidthmin && AngulardifferenceYaw < prefab_vehicle.yawsightwidthmax && AngulardifferencePitch > prefab_vehicle.pitchsighwidthmin && AngulardifferencePitch < prefab_vehicle.pitchsighwidthmax;;
//								} else {
//									throttle += 0.05;
//								}
//							}
//						} else if (T_useMain_F_useSub && prefab_vehicle.Torpedo_bomber) {
//							throttle += 0.05;
//							if (alt < prefab_vehicle.minALT + 5) {
//								targetpitch = -20;
//							} else if (alt > prefab_vehicle.minALT) {
//								targetpitch = 10;
//							} else {
//								targetpitch = 0;
//							}
//						} else if (prefab_vehicle.throttledown_onDive && istarget_onGround) {
//							if (throttle > (prefab_vehicle.throttle_Max / 3) * 2) throttle -= 0.05;
//							else throttle += 0.05;
//						} else {
//							throttle += 0.05;
//						}
//						float AngulardifferencePitch = targetpitch - bodyrotationPitch;
//						if (targetpitch < prefab_vehicle.maxClimb) targetpitch = prefab_vehicle.maxClimb;
//						if (prefab_vehicle.type_F_Plane_T_Heli && targetpitch > prefab_vehicle.maxDive) {
//							targetpitch = prefab_vehicle.maxDive;
//							T_StartDive_F_FlyToStartDivePos = false;
//						}
////					if(targetpitch < 0 && istarget_onGround)targetpitch = 0;
//						if (!istarget_onGround) {
//							handle_Yaw(AngulardifferenceYaw, targetpitch, alt);
//							pitchHandle_considerYaw(targetpitch, AngulardifferenceYaw > 0);
//						} else {
//							if (handle_Yaw(AngulardifferenceYaw, 0, alt)) {
//								if (abs(AngulardifferenceYaw) > 20 && abs(bodyrotationRoll) < 10) {
//									pitchHandle_considerYaw(targetpitch, AngulardifferenceYaw > 0);
//								} else {
//									pitchHandle(targetpitch);
//								}
//							} else {
//								pitchHandle_considerYaw(0, AngulardifferenceYaw > 0);
//							}
//						}
//						insight &= AngulardifferenceYaw > prefab_vehicle.yawsightwidthmin && AngulardifferenceYaw < prefab_vehicle.yawsightwidthmax && AngulardifferencePitch > prefab_vehicle.pitchsighwidthmin && AngulardifferencePitch < prefab_vehicle.pitchsighwidthmax;
//						if (T_useMain_F_useSub && prefab_vehicle.Torpedo_bomber) {
//							insight = mc_Entity.getDistanceSqToEntity(target) < 1600;
//							if (insight) {
//								rising_after_Attack = true;
//								T_StartDive_F_FlyToStartDivePos = false;
//							}
//						}
//						if (!prefab_vehicle.useMain_withSub && mainTurret != null && subTurret != null) {
//							if (prefab_vehicle.sholdUseMain_ToG) {
//								if (istarget_onGround) {
//									T_useMain_F_useSub = !mainTurret.isreloading();
//								} else {
//									T_useMain_F_useSub = false;
//								}
//							} else if (prefab_vehicle.sholdUseMain_ToA) {
//								if (!istarget_onGround) {
//									T_useMain_F_useSub = !mainTurret.isreloading();
//								} else {
//									T_useMain_F_useSub = false;
//								}
//							}
//						}
//						if (insight) {
//							if (prefab_vehicle.useMain_withSub) {
//								trigger1 = true;
//								trigger2 = true;
//							} else if (T_useMain_F_useSub) {
//								if (!mainTurret.isreloading())
//									trigger1 = true;
//								else {
//									rising_after_Attack = true;
//									T_useMain_F_useSub = !T_useMain_F_useSub;
//									changeWeaponCycle = 0;
//								}
//							} else {
//								if (!subTurret.isreloading())
//									trigger2 = true;
//								else {
//									rising_after_Attack = true;
//									T_useMain_F_useSub = !T_useMain_F_useSub;
//									changeWeaponCycle = 0;
//								}
//							}
//							outSightCnt--;
//						} else {
//							outSightCnt++;
//							if (outSightCnt > prefab_vehicle.outSightCntMax) {
//								outSightCnt = 0;
//								rising_after_Attack = true;
//								//離脱
//							}
//						}
//						changeWeaponCycle++;
//						if (changeWeaponCycle > prefab_vehicle.changeWeaponCycleSetting) {
//							changeWeaponCycle = 0;
//							T_useMain_F_useSub = !T_useMain_F_useSub;
//						}
//						if (target.onGround && toTargetPitch > prefab_vehicle.maxDive) {
//							T_StartDive_F_FlyToStartDivePos = false;
//							rising_after_Attack = true;
//						}
//						if (!prefab_vehicle.Torpedo_bomber && alt < prefab_vehicle.minALT) {
//							rising_after_Attack = true;
//						}
//					} else {
//						if (prefab_vehicle.type_F_Plane_T_Heli) {
//							if ((motionvec.y < -0.1 || alt < prefab_vehicle.cruiseALT))
//								throttle += 0.05;
//							else
//								throttle -= 0.05;
//							if (alt < prefab_vehicle.cruiseALT) T_StartDive_F_FlyToStartDivePos = false;
//						} else
//							throttle += 0.05;
//						if (!target.onGround) T_StartDive_F_FlyToStartDivePos = true;
//						if (toTargetPitch < (prefab_vehicle.Dive_bombing ? prefab_vehicle.startDive / 1.1 : prefab_vehicle.startDive))
//							T_StartDive_F_FlyToStartDivePos = true;
//						if (toTargetPitch < 0 && alt > prefab_vehicle.minALT) rising_after_Attack = false;
//						if (alt > prefab_vehicle.cruiseALT) rising_after_Attack = false;
//						if (!target.onGround && outSightCnt > 0) {
//							rising_after_Attack = true;
//							outSightCnt -= 10;
//						}
//						if(prefab_vehicle.Dive_bombing && bodyrotationPitch>30){
//							setControl_brake(true);
//						}else {
//							setControl_brake(false);
//						}
//						if (rand.nextInt(500) == 0) climbYawDir = !climbYawDir;
//						if (prefab_vehicle.type_F_Plane_T_Heli) {
//							handle_withPitch(0, 15, prefab_vehicle.cruiseALT);
//						} else if (alt > prefab_vehicle.cruiseALT + 40)
//							handle_withPitch(!T_StartDive_F_FlyToStartDivePos && !(toTargetPitch < (prefab_vehicle.Dive_bombing ? prefab_vehicle.startDive / 2 : prefab_vehicle.startDive)) && alt > prefab_vehicle.cruiseALT * 0.75 ? -AngulardifferenceYaw : climbYawDir ? 12 : -12, 2, prefab_vehicle.cruiseALT);
//						else {
//							handle_withPitch(!T_StartDive_F_FlyToStartDivePos && !(toTargetPitch < (prefab_vehicle.Dive_bombing ? prefab_vehicle.startDive / 2 : prefab_vehicle.startDive)) && alt > prefab_vehicle.cruiseALT * 0.75 ? -AngulardifferenceYaw : climbYawDir ? 12 : -12, alt < prefab_vehicle.cruiseALT ? prefab_vehicle.maxClimb : 0, prefab_vehicle.cruiseALT);
//							rollHandle(0);
//						}
//					}
//				}else {
//					Vector3d motionvec = new Vector3d(mc_Entity.motionX, mc_Entity.motionY, mc_Entity.motionZ);
//					if (prefab_vehicle.type_F_Plane_T_Heli) {
//						if ((motionvec.y < -0.1 || alt < prefab_vehicle.cruiseALT))
//							throttle += 0.05;
//						else if(alt < prefab_vehicle.cruiseALT + 20)throttle -= 0.05;
//
//						handle_withPitch(AngulardifferenceYaw+(climbYawDir ? 15 : -15), prefab_vehicle.maxDive, alt);
//					} else {
//						throttle += 0.05;
//						handle_withPitch(AngulardifferenceYaw + (climbYawDir ? 15 : -15), 0, alt);
//					}
//				}
////				if (rising_after_Attack && alt < 50) {
////					pitchHandle(maxClimb);
////					throttle += 0.05;
////				} else {
////					if(rising_after_Attack)rising_after_Attack = false;
////					if(alt < 20)T_StartDive_F_FlyToStartDivePos = rising_after_Attack = true;
////					{
////						AngulardifferenceYaw = wrapAngleTo180_double(AngulardifferenceYaw);
////						if (subTurret == null) T_useMain_F_useSub = true;
////						if(targetpitch<maxDive-20)T_StartDive_F_FlyToStartDivePos = true;
////						if(targetpitch>maxDive)T_StartDive_F_FlyToStartDivePos = false;
////						if(alt>60)T_StartDive_F_FlyToStartDivePos = true;
////						if(target.onGround)T_StartDive_F_FlyToStartDivePos = true;
////						System.out.println("" + AngulardifferenceYaw + T_StartDive_F_FlyToStartDivePos);
////						if(T_StartDive_F_FlyToStartDivePos) {
////							boolean insight = true;
////							if(AngulardifferenceYaw < -3){
////								//turn left
////								if (alt > 30) {
////									if (bodyrotationRoll > -maxbank * (target.onGround?0.5:1) + 3) {
////										mousex += 8;
////									} else if (bodyrotationRoll < -maxbank * (target.onGround?0.5:1) - 3) {
////										mousex -= 8;
////									}
////								}
////								if (alt > 10 && abs(bodyrotationRoll) < 90) {
////									yawladder++;
////								}
////							}else if(AngulardifferenceYaw > 3){
////								//turn right
////								if (alt > 30) {
////									if (bodyrotationRoll > maxbank * (target.onGround?0.5:1) + 3) {
////										mousex += 8;
////									} else if (bodyrotationRoll < maxbank * (target.onGround?0.5:1) - 3) {
////										mousex -= 8;
////									}
////								}
////								if (alt > 10 && abs(bodyrotationRoll) < 90) {
////									yawladder--;
////								}
////							}else {
////								if (bodyrotationRoll > 2) {
////									mousex += 4;
////								} else if (bodyrotationRoll < -2) {
////									mousex -= 4;
////								}
////							}
////							if (bodyrotationPitch < targetpitch - 2) {
////								mousey -= 16;
////							} else if (bodyrotationPitch > targetpitch + 2) {
////								mousey += 16;
////							}
////							if(abs(bodyrotationPitch - targetpitch)>5) insight = false;
////							if(abs(AngulardifferenceYaw)>5) insight = false;
////							if(insight){
////								if(throttle > throttle_Max/2)throttle -= 0.05;
////								if(useMain_withSub){
////									trigger1 = true;
////									trigger2 = true;
////								}else if(T_useMain_F_useSub){
////									trigger1 = true;
////								}else if(!T_useMain_F_useSub){
////									trigger2 = true;
////								}
////								changeWeaponCycle ++;
////								if(changeWeaponCycle > changeWeaponCycleSetting){
////									changeWeaponCycle = 0;
////									T_useMain_F_useSub = !T_useMain_F_useSub;
////								}
////							}else {
////								throttle += 0.05;
////							}
////						} else {
////							if (bodyrotationRoll > 5) {
////								mousex += 4;
////							} else if (bodyrotationRoll < -5) {
////								mousex -= 4;
////							}
////							if (target.onGround && alt > 30){
////								if(abs(bodyrotationRoll) < 90){
////									if (bodyrotationPitch < 10) {
////										mousey -= 4;
////									} else if (bodyrotationPitch > 10) {
////										mousey += 4;
////									}
////								}
////							}
////						}
////					}
////				}
//			}else
//			{
//				switch (((Hasmode) mc_Entity).getMobMode()) {
//					case 0://wait
//					{
//						Vector3d motionvec = new Vector3d(mc_Entity.motionX, mc_Entity.motionY, mc_Entity.motionZ);
//						if(rand.nextInt(500) == 0)climbYawDir = !climbYawDir;
//						if(prefab_vehicle.type_F_Plane_T_Heli){
//							handle_withPitch(climbYawDir ? 12 : -12,15, prefab_vehicle.cruiseALT);
//						}else if(alt< prefab_vehicle.cruiseALT){
//							handle_withPitch(climbYawDir ? 12 : -12, prefab_vehicle.maxClimb, prefab_vehicle.cruiseALT);
//						}
//						else if(alt> prefab_vehicle.cruiseALT + 40) handle_withPitch(climbYawDir ? 12 : -12,2, prefab_vehicle.cruiseALT);
//						else handle_withPitch(climbYawDir ? 12 : -12,0, prefab_vehicle.cruiseALT);
//
//						if (prefab_vehicle.type_F_Plane_T_Heli && (motionvec.y < -0.1 || alt < prefab_vehicle.cruiseALT)) {
//							throttle += 0.05;
//						} else if (!prefab_vehicle.type_F_Plane_T_Heli && (motionvec.y < bodyvector.y || alt < prefab_vehicle.cruiseALT)) {
//							throttle += 0.05;
//						} else {
//							if (throttle > prefab_vehicle.throttle_Max * 0.8) throttle -= 0.05;
//						}
//						break;
//					}
//					case 1:
//					case 2://follow
//					{
//						Vector3d motionvec = new Vector3d(mc_Entity.motionX, mc_Entity.motionY, mc_Entity.motionZ);
//						if (motionvec.length() > 0.1) motionvec.normalize();
//						if (prefab_vehicle.type_F_Plane_T_Heli && (motionvec.y < -0.1 || alt < prefab_vehicle.cruiseALT)) {
//							throttle += 0.05;
//						} else if (!prefab_vehicle.type_F_Plane_T_Heli && (motionvec.y < bodyvector.y || alt < prefab_vehicle.cruiseALT)) {
//							throttle += 0.05;
//						} else {
//							if (throttle > prefab_vehicle.throttle_Max * 0.8) throttle -= 0.05;
//						}
//						double[] targetpos = ((Hasmode) mc_Entity).getTargetpos();
//						if(targetpos == null)targetpos = new double[]{mc_Entity.posX, mc_Entity.posY, mc_Entity.posZ};
//						Vector3d courseVec = new Vector3d(targetpos);
//						courseVec.sub(new Vector3d(mc_Entity.posX, courseVec.y, mc_Entity.posZ));
////						double angletocourse = toDegrees(bodyvector.angle(courseVec));
////						System.out.println("" + angletocourse);
//						float targetyaw = wrapAngleTo180_float(-(float) toDegrees(atan2(courseVec.x, courseVec.z)));
//						double AngulardifferenceYaw = targetyaw - this.bodyrotationYaw;
//						AngulardifferenceYaw = wrapAngleTo180_double(AngulardifferenceYaw);
//						if(alt < prefab_vehicle.minALT){
//							rollHandle(0);
//							pitchHandle(!prefab_vehicle.type_F_Plane_T_Heli ? prefab_vehicle.maxClimb : 0);
//						}
//						else {
//							float pitch = alt < prefab_vehicle.cruiseALT ? prefab_vehicle.maxClimb: ((alt > prefab_vehicle.cruiseALT + 60) ? 5 : 0);
//							if (prefab_vehicle.type_F_Plane_T_Heli) {
//								double dist = courseVec.length();
//								if (dist > 16) {
//									handle_withPitch(AngulardifferenceYaw, 10, alt);
//								} else {
//									handle_withPitch(AngulardifferenceYaw, 0, alt);
//								}
//							} else {
//								handle_withPitch(AngulardifferenceYaw, pitch, alt);
//							}
//						}
////						pitchHandle(alt<60?alt<30?maxClimb:0:maxDive);
//
//						break;
//					}
//
//				}
//			}
//		}else {
//			throttle-=0.05;
//		}
//	}
//	public boolean handle_withPitch(double AngulardifferenceYaw, double targetPitch, double alt){
//		handle_Yaw(AngulardifferenceYaw,targetPitch,alt);
//		return pitchHandle_considerYaw(targetPitch,AngulardifferenceYaw>0);
//	}
//	public boolean handle_Yaw(double AngulardifferenceYaw, double targetPitch, double alt){
//		double maxRollCof = 1 - abs(targetPitch)/90;
//		boolean canUseElevator = (abs(bodyrotationRoll) < 90 && targetPitch+10>bodyrotationPitch) || (abs(bodyrotationRoll) > 90 && targetPitch-10<bodyrotationPitch);
////		System.out.println("canUseElevator " + canUseElevator);
//		boolean canUseLadder = abs(bodyrotationRoll) < 20 || (abs(bodyrotationRoll) < 90 && targetPitch-10<bodyrotationPitch) || (abs(bodyrotationRoll) > 90 && targetPitch+10>bodyrotationPitch);
////		System.out.println("canUseLadder " + canUseLadder);
////		System.out.println("turnDir " + (AngulardifferenceYaw>0));
//		if(maxRollCof < 0)maxRollCof = 0;
//		maxRollCof *= min(AngulardifferenceYaw * AngulardifferenceYaw, 225) / 225;
//		if(AngulardifferenceYaw < 0){
//			//turn Left
//			if(!prefab_vehicle.type_F_Plane_T_Heli) {
//
//				if (alt > prefab_vehicle.cruiseALT) rollHandle(-prefab_vehicle.maxbank * maxRollCof);
//				else if (alt > prefab_vehicle.minALT) rollHandle(-prefab_vehicle.maxbank * maxRollCof * (alt) / (prefab_vehicle.cruiseALT));
//				else {
//					rollHandle(0);
//				}
//			}else {
//				rollHandle(0);
//			}
//
//			if(!prefab_vehicle.type_F_Plane_T_Heli) {
//				if (abs(bodyrotationRoll) < 45) {
//					yawrudder -= min(abs(AngulardifferenceYaw), 10) / 10;//a
//				} else if (abs(bodyrotationRoll) > 135) {
//					yawrudder += min(abs(AngulardifferenceYaw), 10) / 10;//d
//				}
//			}else if(canUseLadder){
//				if (abs(bodyrotationRoll) < 60) {
//					yawrudder -= min(abs(AngulardifferenceYaw)/5, 16)/16;//a
//				} else if (abs(bodyrotationRoll) > 120) {
//					yawrudder += min(abs(AngulardifferenceYaw)/5, 16)/16;//d
//				}
//			}
//			if(!prefab_vehicle.type_F_Plane_T_Heli) {
//				if (abs(bodyrotationRoll) > 45 && abs(bodyrotationRoll) < 135 && canUseElevator) {
//					if (bodyrotationRoll < 0) {
//						//左に傾いている
//						mousey -= abs(AngulardifferenceYaw) / prefab_vehicle.pitchspeed / 3;//↓
//					} else if (bodyrotationRoll > 0) {
//						//右に傾いている
//						mousey += abs(AngulardifferenceYaw) / prefab_vehicle.pitchspeed / 3;//↑
//					}
//				}
//			}
//		}else if(AngulardifferenceYaw > 0){
//			//turn Right
//
//			if(!prefab_vehicle.type_F_Plane_T_Heli) {
//				if (alt > prefab_vehicle.cruiseALT) rollHandle(prefab_vehicle.maxbank * maxRollCof);
//				else if (alt > prefab_vehicle.minALT) rollHandle(prefab_vehicle.maxbank * maxRollCof * (alt) / (prefab_vehicle.cruiseALT));
//				else {
//					rollHandle(0);
//				}
//			} else {
//				rollHandle(0);
//			}
//
//			if(!prefab_vehicle.type_F_Plane_T_Heli) {
//				if (abs(bodyrotationRoll) < 45) {
//					yawrudder += min(abs(AngulardifferenceYaw), 10) / 10;//d
//				} else if (abs(bodyrotationRoll) > 135) {
//					yawrudder -= min(abs(AngulardifferenceYaw), 10) / 10;//a
//				}
//			}else if(canUseLadder){
//				if (abs(bodyrotationRoll) < 60) {
//					yawrudder += min(abs(AngulardifferenceYaw)/5, 16)/16;//d
//				} else if (abs(bodyrotationRoll) > 120) {
//					yawrudder -= min(abs(AngulardifferenceYaw)/5, 16)/16;//a
//				}
//			}
//			if(!prefab_vehicle.type_F_Plane_T_Heli) {
//				if (abs(bodyrotationRoll) > 45 && abs(bodyrotationRoll) < 135 && canUseElevator) {
//					if (bodyrotationRoll < 0) {
//						//左に傾いている
//						mousey += abs(AngulardifferenceYaw) / prefab_vehicle.pitchspeed / 3;//↓
//					} else if (bodyrotationRoll > 0) {
//						//右に傾いている
//						mousey -= abs(AngulardifferenceYaw) / prefab_vehicle.pitchspeed / 3;//↑
//					}
//				}
//			}
//
////			if(abs(bodyrotationRoll)<30){
////				pitchHandle(targetPitch);
////			}else
////			if(abs(bodyrotationRoll)>30 && bodyrotationPitch > targetPitch-10) {
////				if (bodyrotationRoll < 0 && bodyrotationPitch < targetPitch+5) {//機首が高い
////					//右に傾いている
////					//操縦桿押し込み
////					mousey -= abs(AngulardifferenceYaw)/2;
//////					System.out.println("debug3");
////				} else if (bodyrotationRoll > 0 && bodyrotationPitch > targetPitch-5) {//機首が低い
////					//左に傾いている
////					//操縦桿引き込み
////					mousey += abs(AngulardifferenceYaw)/2;
//////					System.out.println("debug4");
////				}
////			}
//		}else {
//			rollHandle(0);
//		}
////		System.out.println("yawladder auto" + yawladder);
//		return abs(AngulardifferenceYaw) < 20;
//	}
//	public void rollHandle(double targetRoll){
//		double[] xyz = Utils.eulerfrommatrix(Utils.matrixfromQuat(rotationmotion));
//		if (bodyrotationRoll > targetRoll) {
//			mousex -= abs(bodyrotationRoll-targetRoll)/4;
//			xyz[2] = toDegrees(xyz[2]);
//			if(xyz[2]<0)mousex -= abs(xyz[2])/10;
//		} else if (bodyrotationRoll < targetRoll) {
//			mousex += abs(bodyrotationRoll-targetRoll)/4;
//			xyz[2] = toDegrees(xyz[2]);
//			if(xyz[2]>0)mousex += abs(xyz[2])/10;
//		}
//	}
//	public boolean pitchHandle(double targetPitch){
//
//		double sensiv = prefab_vehicle.type_F_Plane_T_Heli ? 0.05:0.25;
//		if(prefab_vehicle.type_F_Plane_T_Heli) {
//			if(targetPitch< prefab_vehicle.maxClimb)targetPitch = prefab_vehicle.maxClimb;
//			if(targetPitch> prefab_vehicle.maxDive)targetPitch = prefab_vehicle.maxDive;
//		}
//		double AngulardifferencePitch = bodyrotationPitch - targetPitch;
//		double[] xyz = Utils.eulerfrommatrix(Utils.matrixfromQuat(rotationmotion));
//		xyz[0] = toDegrees(xyz[2]);
////		if (bodyrotationRoll > 0) {
////			mousex -= abs(AngulardifferencePitch)/15 * sensiv;
////		} else if (bodyrotationRoll < 0) {
////			mousex += abs(AngulardifferencePitch)/15 * sensiv;
////		}
//		if (AngulardifferencePitch < 0){
//			//機首下げ
//			if(abs(bodyrotationRoll)<45) {
//				mousey += abs(AngulardifferencePitch)/ prefab_vehicle.pitchspeed * sensiv;
//			}else if(abs(bodyrotationRoll)>135){
//				mousey -= abs(AngulardifferencePitch)/ prefab_vehicle.pitchspeed * sensiv;
//			}
//			if(abs(bodyrotationRoll) > 45 && abs(bodyrotationRoll) < 135) {
//				if (bodyrotationRoll < 0) {
//					//左に傾いている
//					yawrudder +=min(abs(AngulardifferencePitch),10)/40 * sensiv;
//				} else if (bodyrotationRoll > 0) {
//					//右に傾いている
//					yawrudder -=min(abs(AngulardifferencePitch),10)/40 * sensiv;
//				}
//			}
//		}else if (AngulardifferencePitch > 0){
//			//機首上げ
//			if(abs(bodyrotationRoll)<45) {
//				mousey -= abs(AngulardifferencePitch)/ prefab_vehicle.pitchspeed/2 * sensiv;;
//			}else if(abs(bodyrotationRoll)>135){
//				mousey += abs(AngulardifferencePitch)/ prefab_vehicle.pitchspeed/2 * sensiv;;
//			}
//			if(abs(bodyrotationRoll) > 45 && abs(bodyrotationRoll) < 135) {
//				if (bodyrotationRoll < 0) {
//					//左に傾いている
//					yawrudder -=min(abs(AngulardifferencePitch),10)/40 * sensiv;;
//				} else if (bodyrotationRoll > 0) {
//					//右に傾いている
//					yawrudder +=min(abs(AngulardifferencePitch),10)/40 * sensiv;;
//				}
//			}
//		}else {
//			return true;
//		}
//		return false;
//	}
//
//	public boolean pitchHandle_considerYaw(double targetPitch,boolean yawDir){
//		//yawDir=false:turnLeft
//		//yawDir=true :turnRight
//		double sensiv = prefab_vehicle.type_F_Plane_T_Heli ? 0.05:1;
//		if(prefab_vehicle.type_F_Plane_T_Heli) {
//			if(targetPitch< prefab_vehicle.maxClimb)targetPitch = prefab_vehicle.maxClimb;
//			if(targetPitch> prefab_vehicle.maxDive)targetPitch = prefab_vehicle.maxDive;
//		}
//		double AngulardifferencePitch = bodyrotationPitch - targetPitch;
//		double[] xyz = Utils.eulerfrommatrix(Utils.matrixfromQuat(rotationmotion));
//		xyz[0] = toDegrees(xyz[2]);
////		if (bodyrotationRoll > 0) {
////			mousex -= abs(AngulardifferencePitch)/15 * sensiv;
////		} else if (bodyrotationRoll < 0) {
////			mousex += abs(AngulardifferencePitch)/15 * sensiv;
////		}
//		boolean allow_PitchUp = abs(bodyrotationRoll)<5 || (yawDir && bodyrotationRoll > 0) || (!yawDir && bodyrotationRoll < 0);
//		boolean allow_PitchDown = abs(bodyrotationRoll)<5 || (yawDir && bodyrotationRoll < 0) || (!yawDir && bodyrotationRoll > 0);
//		if (AngulardifferencePitch < 0){
//			//機首下げ
//			if(allow_PitchDown) {
//				if (abs(bodyrotationRoll) < 45) {
//					mousey += abs(AngulardifferencePitch) / prefab_vehicle.pitchspeed * sensiv;
//				} else if (abs(bodyrotationRoll) > 135) {
//					mousey -= abs(AngulardifferencePitch) / prefab_vehicle.pitchspeed * sensiv;
//				}
//			}
//			if(abs(bodyrotationRoll) > 15 && abs(bodyrotationRoll) < 165) {
//				if (bodyrotationRoll < 0 && !yawDir) {
//					//左に傾いている
////					System.out.println("debug");
//					yawrudder -=min(abs(AngulardifferencePitch),10)/10 * sensiv;
//				} else if (bodyrotationRoll > 0 && yawDir) {
//					//右に傾いている
////					System.out.println("debug");
//					yawrudder +=min(abs(AngulardifferencePitch),10)/10 * sensiv;
//				}
//			}
//		}else if (AngulardifferencePitch > 0){
//			//機首上げ
//			if(allow_PitchUp) {
//				if (abs(bodyrotationRoll) < 45) {
//					mousey -= abs(AngulardifferencePitch) / prefab_vehicle.pitchspeed / 2 * sensiv;
//					;
//				} else if (abs(bodyrotationRoll) > 135) {
//					mousey += abs(AngulardifferencePitch) / prefab_vehicle.pitchspeed / 2 * sensiv;
//					;
//				}
//			}
//			if(abs(bodyrotationRoll) > 15 && abs(bodyrotationRoll) < 165) {
//				if (bodyrotationRoll < 0 && yawDir) {
//					//左に傾いている
////					System.out.println("debug");
//					yawrudder +=min(abs(AngulardifferencePitch),10)/10 * sensiv;
//				} else if (bodyrotationRoll > 0 && !yawDir) {
//					//右に傾いている
////					System.out.println("debug");
//					yawrudder -=min(abs(AngulardifferencePitch),10)/10 * sensiv;
//				}
//			}
//		}else {
//			return true;
//		}
//		return false;
//	}
	
	void FCS(Vector3d mainwingvector,Vector3d tailwingvector,Vector3d bodyvector){
		if (trigger1) {
			if(mainTurret != null){
				if(riddenByEntities[iVehicle.getpilotseatid()] != null)mainTurret.currentEntity = riddenByEntities[iVehicle.getpilotseatid()];
				mainTurret.currentEntity.motionX = this.mc_Entity.motionX;
				mainTurret.currentEntity.motionY = this.mc_Entity.motionY;
				mainTurret.currentEntity.motionZ = this.mc_Entity.motionZ;
				mainTurret.fireall();
			}
//			for(int i = 0;i<2;i++){
//				HMGEntityBullet var3 = new HMGEntityBullet(this.worldObj, riddenByEntities[0].riddenByEntity, 40, 8, 3);
//				var3.setLocationAndAngles(
//						planebody.posX + mainwingvector.x * gunpos[i][0] +     tailwingvector.x * (gunpos[i][1] - 2.5) - bodyvector.x * gunpos[i][2]
//						, planebody.posY + mainwingvector.y * gunpos[i][0] + 2 + tailwingvector.y * (gunpos[i][1] - 2.5) - bodyvector.y * gunpos[i][2]
//						, planebody.posZ + mainwingvector.z * gunpos[i][0] +     tailwingvector.z * (gunpos[i][1] - 2.5) - bodyvector.z * gunpos[i][2]
//						,bodyrotationYaw,bodyrotationPitch);
////						var3.setHeadingFromThrower(bodyrotationPitch, this.bodyrotationYaw, 0, 8, 10F);
//				var3.motionX = planebody.motionX + bodyvector.x * -6 + this.rand.nextGaussian() * (double)(this.rand.nextBoolean() ? -1 : 1) * 0.01 * 3;
//				var3.motionY = planebody.motionY + bodyvector.y * -6 + this.rand.nextGaussian() * (double)(this.rand.nextBoolean() ? -1 : 1) * 0.01 * 3;
//				var3.motionZ = planebody.motionZ + bodyvector.z * -6 + this.rand.nextGaussian() * (double)(this.rand.nextBoolean() ? -1 : 1) * 0.01 * 3;
//				var3.bulletTypeName = "byfrou01_GreenTracer";
//				this.worldObj.spawnEntityInWorld(var3);
//			}
//			if(planebody.getEntityData().getFloat("GunshotLevel")<4)
//				soundedentity.add(planebody);
//			planebody.getEntityData().setFloat("GunshotLevel",4);
//			planebody.playSound("gvcguns:gvcguns.fire", 4.0F, 0.5F);
			trigger1 = false;
		}
		if(trigger2){
			if(subTurret != null){
				if(riddenByEntities[iVehicle.getpilotseatid()] != null)subTurret.currentEntity = riddenByEntities[iVehicle.getpilotseatid()];
				subTurret.currentEntity.motionX = this.mc_Entity.motionX;
				subTurret.currentEntity.motionY = this.mc_Entity.motionY;
				subTurret.currentEntity.motionZ = this.mc_Entity.motionZ;
				subTurret.fireall();
			}
			trigger2 = false;
		}
	}
	
	void motionUpdate(Vector3d mainwingvector,Vector3d tailwingvector,Vector3d bodyvector){
		
		if(!worldObj.isRemote)
		{
			Vector3d motionvec = new Vector3d(mc_Entity.motionX, mc_Entity.motionY, mc_Entity.motionZ);
			
			Vector3d windvec = new Vector3d(motionvec);
			if (mc_Entity.onGround && windvec.y < 0) windvec.y = 0;
			if (windvec.length() > 0.000001 && prefab_vehicle.stability_roll != 0) {
				Vector3d axisstall = new Vector3d();
				windvec.normalize();
				windvec.add(new Vector3d(bodyvector.x * 0.3,
						                        bodyvector.y * 0.3,
						                        bodyvector.z * 0.3));
				tailwingvector.normalize();
				axisstall.cross(bodyvector, windvec);
				axisstall.normalize();
				axisstall.z = -axisstall.z;
				Quat4d quat4d = new Quat4d();
				quat4d.inverse(bodyRot);
				quat4d.normalize();
				axisstall = Utils.transformVecByQuat(axisstall, quat4d);
				if (!Double.isNaN(axisstall.x) && !Double.isNaN(axisstall.y) && !Double.isNaN(axisstall.z)) {
					windvec.set(motionvec);
					if (mc_Entity.onGround && windvec.y < 0) windvec.y = 0;
					Vector3d bodyVec_front = new Vector3d(bodyvector);
					bodyVec_front.scale(-1);
					double sin = angle_cos(bodyVec_front, motionvec);
					sin = sqrt(1 - sin * sin);
//					System.out.println(sin);
					AxisAngle4d axisxangledstall = new AxisAngle4d(axisstall, motionvec.length() * sin / prefab_vehicle.stability_roll);
					rotationmotion = Utils.quatRotateAxis(rotationmotion, axisxangledstall);
				}
			}
			
			windvec = new Vector3d(motionvec);
			if (mc_Entity.onGround && windvec.y < 0) windvec.y = 0;
			
			rotationmotion.normalize();
			if (!mc_Entity.onGround) {
				double cos = prefab_vehicle.forced_rotmotion_reduceSpeed - ((1- prefab_vehicle.forced_rotmotion_reduceSpeed) * angle_cos(bodyvector, motionvec)) * prefab_vehicle.rotmotion_reduceSpeed;
				if (Double.isNaN(cos)) cos = prefab_vehicle.rotmotion_reduceSpeed;
				rotationmotion.interpolate(new Quat4d(0, 0, 0, 1), cos);
				
				double[] xyz = Utils.eulerfrommatrix(Utils.matrixfromQuat(rotationmotion));
				AxisAngle4d axiszangled = new AxisAngle4d(unitZ, -xyz[2] * prefab_vehicle.rotmotion_reduceSpeedRoll);
				rotationmotion = Utils.quatRotateAxis(rotationmotion, axiszangled);
			} else {
				rotationmotion.interpolate(new Quat4d(0, 0, 0, 1), prefab_vehicle.forced_rotmotion_reduceSpeed + (1- prefab_vehicle.forced_rotmotion_reduceSpeed) * prefab_vehicle.rotmotion_reduceSpeed_onGround * 1);
			}
			
			
			tailwingvector = Utils.transformVecByQuat(new Vector3d(unitY), bodyRot);
			bodyvector = Utils.transformVecByQuat(new Vector3d(unitZ), bodyRot);
			mainwingvector = Utils.transformVecByQuat(new Vector3d(unitX), bodyRot);
			
			Utils.transformVecforMinecraft(tailwingvector);
			Utils.transformVecforMinecraft(bodyvector);
			Utils.transformVecforMinecraft(mainwingvector);
			
			motionvec = new Vector3d(mc_Entity.motionX, mc_Entity.motionY, mc_Entity.motionZ);
			Vector3d powerVec = new Vector3d(prefab_vehicle.unitThrottle);
			powerVec = transformVecByQuat(powerVec,bodyRot);
			transformVecforMinecraft(powerVec);
			Vector3d powerVec_ground = new Vector3d(powerVec);
			if (!(Double.isNaN(bodyvector.x) || Double.isNaN(bodyvector.y) || Double.isNaN(bodyvector.z))) {
				powerVec.scale(-throttle * prefab_vehicle.speedfactor);
				if(mc_Entity.onGround){
					powerVec_ground.scale(-throttle * prefab_vehicle.speedfactor_onGround);
					motionvec.add(powerVec_ground);
				}
				motionvec.add(powerVec);
				if (prefab_vehicle.throttle_AF < throttle) {
					powerVec.normalize();
					powerVec.scale(-throttle * prefab_vehicle.speedfactor_af);
					motionvec.add(powerVec);
				}
			}
			float  vertical_drag = -prefab_vehicle.gravity;
			if (motionvec.length() > 0.000001) {
				motionvec.y -= prefab_vehicle.gravity;
				double cos;
				{
					cos = angle_cos(bodyvector, motionvec);
					Vector3d tailwingvectorForFloating = new Vector3d(tailwingvector);
					tailwingvectorForFloating.scale(motionvec.length() * motionvec.length() * -cos * (prefab_vehicle.liftfactor + flaplevel * prefab_vehicle.flapliftfactor));
//			System.out.println("debug" + cos);
//					motionvec.x += tailwingvectorForFloating.x / slipresist;
					motionvec.y += abs(tailwingvectorForFloating.y);
					vertical_drag += abs(tailwingvectorForFloating.y);
//					motionvec.z += tailwingvectorForFloating.z / slipresist;
				}
//				if (planebody.onGround && motionvec.y < 0) motionvec.y = 0;
				if (motionvec.length() > 0.000001 && prefab_vehicle.stability_motion != 0) {
					Vector3d liftVec1 = new Vector3d();
					liftVec1.cross(mainwingvector, motionvec);
					liftVec1.normalize();
					if (!(Double.isNaN(liftVec1.x) || Double.isNaN(liftVec1.y) || Double.isNaN(liftVec1.z))) {
						double sin = angle_cos(liftVec1, bodyvector);
						liftVec1.set(tailwingvector);
						liftVec1.normalize();
						liftVec1.scale(motionvec.length() * sin * prefab_vehicle.stability_motion);
						if (motionvec.length() < 0.8) liftVec1.scale(motionvec.length() / 0.8);
						if (!Double.isNaN(sin)) {
//							System.out.println(sin);
							motionvec.add(liftVec1);
							vertical_drag += abs(liftVec1.y);
						}
					}
				}
				if (motionvec.length() > 0.000001) {
					Vector3d resistskidding = new Vector3d();
					resistskidding.cross(tailwingvector, motionvec);
					resistskidding.normalize();
					if (!(Double.isNaN(resistskidding.x) || Double.isNaN(resistskidding.y) || Double.isNaN(resistskidding.z))) {
						double sin = angle_cos(resistskidding, bodyvector);
						resistskidding.set(mainwingvector);
						resistskidding.normalize();
						resistskidding.scale(-motionvec.length() * sin * (mc_Entity.onGround ? prefab_vehicle.slipresist_onground : prefab_vehicle.slipresist));
						if (motionvec.length() < 0.8) resistskidding.scale(motionvec.length() / 0.8);
						if (!Double.isNaN(sin)) {
//							System.out.println(sin);
							motionvec.add(resistskidding);
							vertical_drag += abs(resistskidding.y);
						}
					}
				}
				Vector3d airDrug = new Vector3d(motionvec);
				airDrug.scale(motionvec.length() * (prefab_vehicle.dragfactor + gearprogress * prefab_vehicle.geardragfactor + flaplevel * prefab_vehicle.flapdragfactor)
						               + (mc_Entity.onGround ? motionvec.length() * prefab_vehicle.dragfactor_ground : 0)
						               + (mc_Entity.onGround && serverx ? motionvec.length() * prefab_vehicle.brakedragfactor_ground : 0) + (serverx ? motionvec.length() * prefab_vehicle.brakedragfactor : 0));
				motionvec.sub(airDrug);
				vertical_drag -= abs(airDrug.y);
				if (motionvec.length() < 0.0000000001){
					motionvec.scale(0);
				}
			} else {
				motionvec.scale(0);
				motionvec.y -= prefab_vehicle.gravity;
			}
			if (!Double.isNaN(motionvec.x) && !Double.isNaN(motionvec.y) && !Double.isNaN(motionvec.z)) {
				mc_Entity.motionX = abs(motionvec.x) > 0.0000001 ? motionvec.x : 0;
				mc_Entity.motionY = abs(motionvec.y) > 0.0000001 ? motionvec.y : 0;
				mc_Entity.motionZ = abs(motionvec.z) > 0.0000001 ? motionvec.z : 0;
			}
			
			motionvec = new Vector3d(mc_Entity.motionX, mc_Entity.motionY, mc_Entity.motionZ);
			HMVPacketHandler.INSTANCE.sendToAll(new HMVPakcetPlaneState(mc_Entity.getEntityId(),bodyRot,rotationmotion,motionvec,throttle));
			moveEntity(mc_Entity.motionX, mc_Entity.motionY, mc_Entity.motionZ);
			if (mc_Entity.onGround)
				followGround(mainwingvector,tailwingvector,bodyvector,vertical_drag);
			
			double backmotionX = mc_Entity.motionX;
			double backmotionY = mc_Entity.motionY;
			double backmotionZ = mc_Entity.motionZ;
			motionvec.normalize();
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
			if (mc_Entity.isCollidedHorizontally) {
				if (backmotionX * backmotionX + backmotionY * backmotionY + backmotionZ * backmotionZ > 1) {
					mc_Entity.attackEntityFrom(DamageSource.fall, (float) (backmotionX * backmotionX + backmotionY * backmotionY + backmotionZ * backmotionZ) * 30);
				}
			}
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
		}else {
			moveEntity(mc_Entity.motionX, mc_Entity.motionY, mc_Entity.motionZ);
		}
		bodyRot.mul(rotationmotion);
		{
			
			Vector3d motionvec = new Vector3d(mc_Entity.motionX, mc_Entity.motionY, mc_Entity.motionZ);
			if ((motionvec.y < -bodyvector.y && prefab_vehicle.autoflap) || mc_Entity.onGround || serverf || HMV_Proxy.flap_click()) {
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
		
	}
	public void followGround(Vector3d mainwingvector,Vector3d tailwingvector,Vector3d bodyvector,float vertical_drag){
		mc_Entity.stepHeight = prefab_vehicle.off_road_capability + abs(MathHelper.sin(-bodyrotationPitch * 0.017453292F - (float) Math.PI)) * prefab_vehicle.wheelZ;
		System.out.println("debug" + mc_Entity.stepHeight);
		Vec3 tankFrontVec_level;
		Vec3 tankRight;
		{
			float f1;
			float f2;
			f1 = -MathHelper.cos(-bodyrotationYaw * 0.017453292F - (float) Math.PI);
			f2 = -MathHelper.sin(-bodyrotationYaw * 0.017453292F - (float) Math.PI);
			tankFrontVec_level = Vec3.createVectorHelper((double) (f2) * prefab_vehicle.wheelZ, (double) 0, (double) (f1) * prefab_vehicle.wheelZ);
			tankRight = Vec3.createVectorHelper((f1)* prefab_vehicle.wheelX, (double) 0, -(f2)* prefab_vehicle.wheelX);
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
			else FR = amovingObjectPosition.hitVec;
		}
		{
			Vec3 vec3 = Vec3.createVectorHelper(mc_Entity.posX, mc_Entity.posY + tankFrontVec.y + cfgVehicleWheel_UpRange, mc_Entity.posZ);
			vec3 = vec3.addVector(tankFrontVec_level.xCoord, tankFrontVec_level.yCoord, tankFrontVec_level.zCoord);
			vec3 = vec3.addVector(-tankRight.xCoord, -tankRight.yCoord, -tankRight.zCoord);
//            playerlook = Vec3.createVectorHelper(playerlook.xCoord * 256, playerlook.yCoord * 256, playerlook.zCoord * 256);
			Vec3 vec31 = vec3.addVector(0, -cfgVehicleWheel_DownRange, 0);
			MovingObjectPosition amovingObjectPosition = mc_Entity.worldObj.func_147447_a(vec3, vec31, false, true, false);
			if(amovingObjectPosition == null)FL = vec31;
			else FL = amovingObjectPosition.hitVec;
		}
		{
			Vec3 vec3 = Vec3.createVectorHelper(mc_Entity.posX, mc_Entity.posY - tankFrontVec.y + cfgVehicleWheel_UpRange, mc_Entity.posZ);
			vec3 = vec3.addVector(-tankFrontVec_level.xCoord, -tankFrontVec_level.yCoord, -tankFrontVec_level.zCoord);
			vec3 = vec3.addVector(tankRight.xCoord, tankRight.yCoord, tankRight.zCoord);
//            playerlook = Vec3.createVectorHelper(playerlook.xCoord * 256, playerlook.yCoord * 256, playerlook.zCoord * 256);
			Vec3 vec31 = vec3.addVector(0, -cfgVehicleWheel_DownRange, 0);
			MovingObjectPosition amovingObjectPosition = mc_Entity.worldObj.func_147447_a(vec3, vec31, false, true, false);
			if(amovingObjectPosition == null)BR = vec31;
			else BR = amovingObjectPosition.hitVec;
		}
		{
			Vec3 vec3 = Vec3.createVectorHelper(mc_Entity.posX, mc_Entity.posY - tankFrontVec.y + cfgVehicleWheel_UpRange, mc_Entity.posZ);
			vec3 = vec3.addVector(-tankFrontVec_level.xCoord, -tankFrontVec_level.yCoord, -tankFrontVec_level.zCoord);
			vec3 = vec3.addVector(-tankRight.xCoord, -tankRight.yCoord, -tankRight.zCoord);
//            playerlook = Vec3.createVectorHelper(playerlook.xCoord * 256, playerlook.yCoord * 256, playerlook.zCoord * 256);
			Vec3 vec31 = vec3.addVector(0, -cfgVehicleWheel_DownRange, 0);
			MovingObjectPosition amovingObjectPosition = mc_Entity.worldObj.func_147447_a(vec3, vec31, false, true, false);
			if(amovingObjectPosition == null)BL = vec31;
			else BL = amovingObjectPosition.hitVec;
		}
		
		double targetbodyrotationPitch = 0;
		double targetbodyrotationRoll = 0;
		{
			Vec3 vec1 = BL.addVector(-FR.xCoord,-FR.yCoord,-FR.zCoord);
			Vec3 vec2 = BR.addVector(-FL.xCoord,-FL.yCoord,-FL.zCoord);
			Vec3 normal = vec1.crossProduct(vec2).normalize();
			tankRight = tankRight.normalize();
			Vec3 pitchVec = normal.crossProduct(tankRight).normalize();
			Vec3 rollVec = normal.crossProduct(pitchVec).normalize();
			double groundPitch = toDegrees(sin(pitchVec.yCoord));
			double groundRoll = toDegrees(sin(rollVec.yCoord));
			targetbodyrotationPitch = ((float) -groundPitch - (bodyrotationPitch + prefab_vehicle.onground_pitch ));
//                System.out.println("debug " + bodyrotationPitch);
			targetbodyrotationRoll = ((float) -groundRoll - bodyrotationRoll);
			if(abs(targetbodyrotationRoll)>45)targetbodyrotationRoll *= -1;
//                if(tank.worldObj.isRemote){
//                    System.out.println(vec2);
//                    System.out.println(normal);
//                }
		}
		AxisAngle4d axisxangled;
		
		
		Vector3d axisFollowGroundPitch = getjavaxVecObj(tankRight);
		axisFollowGroundPitch.normalize();
		axisFollowGroundPitch.z = -axisFollowGroundPitch.z;
		Quat4d quat4d = new Quat4d();
		quat4d.inverse(bodyRot);
		quat4d.normalize();
		axisFollowGroundPitch = Utils.transformVecByQuat(axisFollowGroundPitch, quat4d);
		
		axisxangled = new AxisAngle4d(axisFollowGroundPitch, toRadians((targetbodyrotationPitch) * vertical_drag));
		rotationmotion = Utils.quatRotateAxis(rotationmotion, axisxangled);
		axisxangled = new AxisAngle4d(unitZ, -toRadians((targetbodyrotationRoll) * vertical_drag));
		rotationmotion = Utils.quatRotateAxis(rotationmotion, axisxangled);
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
		return prefab_vehicle.soundname;
	}
	
	public float getsoundPitch(){
		return abs(throttle / prefab_vehicle.throttle_Max* prefab_vehicle.soundpitch);
	}
	
	public void yourSoundIsremain(){
		needStartSound = false;
	}
	
	
	public void setControl_RightClick(boolean value) {
		trigger1 = value;
	}
	
	
	public void setControl_LeftClick(boolean value) {
		trigger2 = value;
	}
	
	
	public void setControl_Space(boolean value) {
		serverspace = value;
	}
	
	
	public void setControl_brake(boolean value) {
		serverx = value;
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
		mousex = tempMouseX;
		mousey = tempMouseY;
		yawrudder = tempMouseZ;
	}
	
	public double[] getCamerapos(){
		return HMV_Proxy.iszooming() && seatInfos_zoom[getpilotseatid()] != null ? seatInfos_zoom[getpilotseatid()].pos: seatInfos[getpilotseatid()].pos;
	}
	
	public int getpilotseatid(){
		return iVehicle.getpilotseatid();
	}
	
}
