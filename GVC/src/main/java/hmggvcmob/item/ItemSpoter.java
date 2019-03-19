package hmggvcmob.item;

import handmadeguns.entity.PlacedGunEntity;
import hmggvcmob.entity.EntitySupportTGT;
import hmggvcmob.event.GVCMXEntityEvent;
import hmggvcmob.util.SpotObj;
import hmggvcmob.util.SpotType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static hmggvcmob.GVCMobPlus.tabgvcm;

public class ItemSpoter extends Item {
	int LockedPosX;
	int LockedPosY;
	int LockedPosZ;
	boolean islockingentity;
	boolean islockingblock;
	Entity TGT;
	public ItemSpoter(){
		super();
		this.maxStackSize = 64;
		this.setCreativeTab(tabgvcm);
	}
	public boolean onEntitySwing(EntityLivingBase entityplayer,ItemStack itemstack){
		if(!itemstack.hasTagCompound()){
			itemstack.setTagCompound(new NBTTagCompound());
			itemstack.getTagCompound().setBoolean("Mode",true);
		}
		itemstack.getTagCompound().setBoolean("Mode",!itemstack.getTagCompound().getBoolean("Mode"));
		if(entityplayer  instanceof EntityPlayerMP) {
			((EntityPlayerMP)entityplayer).addChatComponentMessage(new ChatComponentTranslation(
					                                                                 "Change Mode to " + (itemstack.getTagCompound().getBoolean("Mode") ? "Entity" : "Block")));
		}
		return true;
	}
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		if(!itemstack.hasTagCompound()){
			itemstack.setTagCompound(new NBTTagCompound());
			itemstack.getTagCompound().setBoolean("Mode",true);
		}
		if(!world.isRemote && entityplayer != null) {
			lockon(itemstack, world, entityplayer);
			
			ArrayList<SpotObj> spotObjArrayList = GVCMXEntityEvent.spots_needSpawn.get(world.provider.dimensionId);
			if(spotObjArrayList != null) {
				if (islockingentity && itemstack.getTagCompound().getBoolean("Mode"))
					spotObjArrayList.add(new SpotObj(entityplayer.getTeam(), SpotType.Entity, TGT, 60000));
				else if (islockingblock && !itemstack.getTagCompound().getBoolean("Mode"))
					spotObjArrayList.add(new SpotObj(entityplayer.getTeam(), SpotType.Pos, new float[]{LockedPosX, LockedPosY, LockedPosZ}, 60000));
			}
		}
		
		return itemstack;
	}
	public void lockon(ItemStack itemstack, World world, Entity entity){
		Vec3 vec3 = Vec3.createVectorHelper(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ);
		Vec3 playerlook = entity instanceof EntityLivingBase?((EntityLivingBase)entity).getLook(1.0f):entity instanceof PlacedGunEntity ?((PlacedGunEntity)entity).getLook(1.0f):null;
		playerlook = Vec3.createVectorHelper(playerlook.xCoord * 256, playerlook.yCoord * 256, playerlook.zCoord * 256);
		Vec3 vec31 = Vec3.createVectorHelper(entity.posX + playerlook.xCoord, entity.posY + entity.getEyeHeight() + playerlook.yCoord, entity.posZ + playerlook.zCoord);
		MovingObjectPosition movingobjectposition = entity.worldObj.func_147447_a(vec3, vec31, false, true, false);
		Block hitblock;
		Random rand = new Random();
		while (movingobjectposition != null) {
			hitblock = entity.worldObj.getBlock(movingobjectposition.blockX, movingobjectposition.blockY, movingobjectposition.blockZ);
			if ((hitblock.getMaterial() == Material.plants) || ((
					                                                    hitblock.getMaterial() == Material.glass ||
							                                                    hitblock instanceof BlockFence ||
							                                                    hitblock instanceof BlockFenceGate ||
							                                                    hitblock == Blocks.iron_bars) && rand.nextInt(5) <= 1)) {
				Vec3 penerater = movingobjectposition.hitVec.normalize();
				vec3 = Vec3.createVectorHelper(movingobjectposition.hitVec.xCoord + penerater.xCoord, movingobjectposition.hitVec.yCoord + penerater.yCoord, movingobjectposition.hitVec.zCoord + penerater.zCoord);
				vec31 = Vec3.createVectorHelper(entity.posX + playerlook.xCoord, entity.posY + entity.getEyeHeight() + playerlook.yCoord, entity.posZ + playerlook.zCoord);
				movingobjectposition = entity.worldObj.func_147447_a(vec3, vec31, false, true, false);
			} else {
				break;
			}
		}
		vec3 = Vec3.createVectorHelper(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ);
		vec31 = Vec3.createVectorHelper(entity.posX + playerlook.xCoord, entity.posY + entity.getEyeHeight() + playerlook.yCoord, entity.posZ + playerlook.zCoord);
		if (movingobjectposition != null) {
			vec31 = Vec3.createVectorHelper(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
		}
		Entity rentity = null;
		List list = entity.worldObj.getEntitiesWithinAABBExcludingEntity(entity, entity.boundingBox.addCoord(playerlook.xCoord, playerlook.yCoord, playerlook.zCoord).expand(1.0D, 1.0D, 1.0D));
		double d0 = 0.0D;
		double d1 = 0;
		for (int i1 = 0; i1 < list.size(); ++i1) {
			Entity entity1 = (Entity) list.get(i1);
			if (entity1.canBeCollidedWith() && (entity1 != entity)) {
				float f = 0.5F;
				AxisAlignedBB axisalignedbb = entity1.boundingBox.expand((double) f, (double) f, (double) f);
				MovingObjectPosition movingobjectposition1 = axisalignedbb.calculateIntercept(vec3, vec31);
				
				if (movingobjectposition1 != null) {
					d1 = vec3.distanceTo(movingobjectposition1.hitVec);
					
					if (d1 < d0 || d0 == 0.0D) {
						rentity = entity1;
						d0 = d1;
					}
				}
			}
		}
		
		if (rentity != null) {
			d1 = vec3.distanceTo(vec31);
			vec3.xCoord = vec3.xCoord + (vec31.xCoord - vec3.xCoord) * d0 / d1;
			vec3.yCoord = vec3.yCoord + (vec31.yCoord - vec3.yCoord) * d0 / d1;
			vec3.zCoord = vec3.zCoord + (vec31.zCoord - vec3.zCoord) * d0 / d1;
			
			movingobjectposition = new MovingObjectPosition(rentity);
			movingobjectposition.hitVec = vec3;
		}
		if (movingobjectposition != null) {
			if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY && movingobjectposition.entityHit != null) {
				if (!islockingentity || (entity.ridingEntity == null || entity.ridingEntity != movingobjectposition.entityHit) && TGT != movingobjectposition.entityHit) {
					TGT = movingobjectposition.entityHit;
					islockingentity = true;
					islockingblock = false;
				}
			} else {
				if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
					if (LockedPosX != movingobjectposition.blockX ||
							    LockedPosY != movingobjectposition.blockY ||
							    LockedPosZ != movingobjectposition.blockZ) {
						LockedPosX = movingobjectposition.blockX;
						LockedPosY = movingobjectposition.blockY;
						LockedPosZ = movingobjectposition.blockZ;
						islockingblock = true;
						islockingentity = false;
						TGT = null;
					}
				}
			}
		}
	}
}
