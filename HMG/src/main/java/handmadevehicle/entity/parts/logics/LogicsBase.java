package handmadevehicle.entity.parts.logics;

import handmadevehicle.HMVPacketHandler;
import handmadevehicle.entity.parts.*;
import handmadevehicle.entity.parts.turrets.TurretObj;
import handmadevehicle.entity.prefab.Prefab_Vehicle_Base;
import handmadevehicle.packets.HMVPacketChangeSeat;
import handmadevehicle.packets.HMVPacketDisMountEntity;
import handmadevehicle.packets.HMVPacketPickNewEntity;
import handmadevehicle.packets.HMVPacketTriggerSeatGun;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ReportedException;
import net.minecraft.world.World;

import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;
import java.util.List;
import java.util.Random;

import static handmadevehicle.HMVehicle.proxy_HMVehicle;
import static handmadevehicle.Utils.transformVecByQuat;
import static handmadevehicle.Utils.transformVecforMinecraft;
import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public abstract class LogicsBase implements IbaseLogic {
	
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
	
	Entity mc_Entity;
	IVehicle iVehicle;
	World worldObj;
	Random rand = new Random();
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
				
				if (mc_Entity.distanceWalkedOnStepModified > (float) proxy_HMVehicle.getNextstepdistance(mc_Entity) && block.getMaterial() != Material.air)
				{
					proxy_HMVehicle.setNextstepdistance(mc_Entity,(int)mc_Entity.distanceWalkedOnStepModified + 1);
					
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
				if((mc_Entity.worldObj.isRemote && entity instanceof EntityPlayer && entity.isSneaking())) {
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
					if(seatInfos[cnt].aimGun) {
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
						if(seatInfos[cnt].aimGun) {
							if (entity instanceof EntityLiving && ((EntityLiving) entity).getAttackTarget() != null) {
								seatsubgun.aimToEntity(((EntityLiving) entity).getAttackTarget());
							} else {
								seatsubgun.aimtoAngle(entity.getRotationYawHead(), entity.rotationPitch);
							}
						}
					}
					Vector3d temp = new Vector3d(seatmaingun.pos);
					Vector3d tempplayerPos = new Vector3d(proxy_HMVehicle.iszooming() && seatInfos_zoom.length > cnt && seatInfos_zoom[cnt] != null? seatInfos_zoom[cnt].pos: seatInfos[cnt].pos);
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
					
					seatInfos[cnt].currentSeatOffset_fromV.sub(new Vector3d(entity.posX, entity.posY, entity.posZ),thispos);
					entity.motionX = mc_Entity.motionX;
					entity.motionY = mc_Entity.motionY;
					entity.motionZ = mc_Entity.motionZ;
				}else {
					Vector3d tempplayerPos = new Vector3d(proxy_HMVehicle.iszooming() && seatInfos_zoom.length > cnt && seatInfos_zoom[cnt] != null? seatInfos_zoom[cnt].pos: seatInfos[cnt].pos);
					tempplayerPos.sub(new Vector3d(0,0,0));
					Vector3d temp = transformVecByQuat(tempplayerPos, bodyRot);
					transformVecforMinecraft(temp);
					temp.add(new Vector3d(0,0,0));
//				System.out.println("" + temp);
					temp.add(thispos);
					entity.setPosition(temp.x,
							temp.y - ((mc_Entity.worldObj.isRemote && entity instanceof EntityPlayer) ? 0 : entity.getEyeHeight()),
							temp.z);
					entity.posX = temp.x;
					entity.posY = temp.y - ((mc_Entity.worldObj.isRemote && entity instanceof EntityPlayer) ? 0 : entity.getEyeHeight());
					entity.posZ = temp.z;
					seatInfos[cnt].currentSeatOffset_fromV.sub(new Vector3d(entity.posX, entity.posY, entity.posZ),thispos);
					entity.motionX = mc_Entity.motionX;
					entity.motionY = mc_Entity.motionY;
					entity.motionZ = mc_Entity.motionZ;
				}
				if(worldObj.isRemote){
					if(entity == proxy_HMVehicle.getEntityPlayerInstance()){
						
						if(seatmaingun != null)HMVPacketHandler.INSTANCE.sendToServer(new HMVPacketTriggerSeatGun(proxy_HMVehicle.leftclick(), proxy_HMVehicle.rightclick(), mc_Entity.getEntityId(), cnt));
						if(proxy_HMVehicle.next_Seatclick())HMVPacketHandler.INSTANCE.sendToServer(new HMVPacketChangeSeat(mc_Entity.getEntityId(),cnt,true));
						else
						if(proxy_HMVehicle.previous_Seatclick())HMVPacketHandler.INSTANCE.sendToServer(new HMVPacketChangeSeat(mc_Entity.getEntityId(),cnt,false));
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
						if (seatmaingun.aimtoOut) {
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
						if (seatsubgun.aimtoOut) {
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
	}
	
	
	protected void collideWithNearbyEntities()
	{
		List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this.mc_Entity, this.mc_Entity.boundingBox.expand(0.20000000298023224D, 0.0D, 0.20000000298023224D));
		
		if (list != null && !list.isEmpty())
		{
			for (int i = 0; i < list.size(); ++i)
			{
				Entity entity = (Entity)list.get(i);
				
				if (entity.canBePushed() && entity.width > 1.5)
				{
					((EntityLivingBase)this.mc_Entity).collideWithEntity(entity);
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
		if (this.mc_Entity.worldObj.handleMaterialAcceleration(this.mc_Entity.boundingBox.expand(0.0D, -0.4000000059604645D, 0.0D).contract(0.001D, 0.001D, 0.001D).offset(0,draft,0), Material.water, this))
		{
			if (!this.mc_Entity.inWater)
			{
				float f = MathHelper.sqrt_double(this.mc_Entity.motionX * this.mc_Entity.motionX * 0.20000000298023224D + this.mc_Entity.motionY * this.mc_Entity.motionY + this.mc_Entity.motionZ * this.mc_Entity.motionZ * 0.20000000298023224D) * 0.2F;
				
				if (f > 1.0F)
				{
					f = 1.0F;
				}
				
				this.mc_Entity.playSound(this.mc_Entity.getSplashSound(), f, 1.0F + (this.mc_Entity.rand.nextFloat() - this.mc_Entity.rand.nextFloat()) * 0.4F);
				float f1 = (float)MathHelper.floor_double(this.mc_Entity.boundingBox.minY + draft);
				int i;
				float f2;
				float f3;
				
				for (i = 0; (float)i < 1.0F + this.mc_Entity.width * 20.0F; ++i)
				{
					f2 = (this.mc_Entity.rand.nextFloat() * 2.0F - 1.0F) * this.mc_Entity.width;
					f3 = (this.mc_Entity.rand.nextFloat() * 2.0F - 1.0F) * this.mc_Entity.width;
					this.mc_Entity.worldObj.spawnParticle("bubble", this.mc_Entity.posX + (double)f2, (double)(f1 + draft), this.mc_Entity.posZ + (double)f3, this.mc_Entity.motionX, this.mc_Entity.motionY - (double)(this.mc_Entity.rand.nextFloat() * 0.2F), this.mc_Entity.motionZ);
				}
				
				for (i = 0; (float)i < 1.0F + this.mc_Entity.width * 20.0F; ++i)
				{
					f2 = (this.mc_Entity.rand.nextFloat() * 2.0F - 1.0F) * this.mc_Entity.width;
					f3 = (this.mc_Entity.rand.nextFloat() * 2.0F - 1.0F) * this.mc_Entity.width;
					this.mc_Entity.worldObj.spawnParticle("splash", this.mc_Entity.posX + (double)f2, (double)(f1 + draft), this.mc_Entity.posZ + (double)f3, this.mc_Entity.motionX, this.mc_Entity.motionY, this.mc_Entity.motionZ);
				}
			}
			
			this.mc_Entity.fallDistance = 0.0F;
			iVehicle.setinWater(true);
		}
		else
		{
			this.mc_Entity.inWater = false;
		}
		
		return this.mc_Entity.inWater;
	}
	public boolean ishittingWater()
	{
		boolean inWater = false;
		if (this.mc_Entity.worldObj.handleMaterialAcceleration(this.mc_Entity.boundingBox.expand(0.0D, -0.4000000059604645D, 0.0D).contract(0.001D, 0.001D, 0.001D).offset(0,0,0), Material.water, this))
		{
			inWater = true;
		}
		else
		{
			inWater = false;
		}
		
		return inWater;
	}
	
	
}
