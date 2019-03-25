package hmgww2.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import handmadeguns.entity.IFF;
import handmadeguns.entity.PlacedGunEntity;
import handmadeguns.items.guns.HMGItem_Unified_Guns;
import hmggvcmob.SlowPathFinder.WorldForPathfind;
import hmggvcmob.ai.AIAttackGun;
import hmggvcmob.ai.AIHurtByTarget;
import hmggvcmob.ai.AINearestAttackableTarget;
import hmggvcmob.ai.AIattackOnCollide;
import hmggvcmob.entity.*;
import hmggvcmob.entity.friend.EntitySoBases;
import hmggvcmob.entity.guerrilla.EntityGBases;
import hmgww2.Nation;
import hmgww2.items.ItemIFFArmor;
import littleMaidMobX.LMM_EntityLittleMaid;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.World;

import javax.vecmath.Quat4d;
import java.util.List;

import static handmadeguns.HandmadeGunsCore.islmmloaded;
import static hmgww2.mod_GVCWW2.cfg_candespawn;
import static hmgww2.mod_GVCWW2.cfg_canusePlacedGun;
import static java.lang.Math.abs;
import static net.minecraft.util.MathHelper.wrapAngleTo180_float;

public abstract class EntityBases_Plane extends EntityBases implements ImultiRideableVehicle,Iplane{
	PlaneBaseLogic baseLogic;
	
	
	public float maxhealth = 150;
	public float angletime;
	public int fireCycle1;
	
	public EntityBases_Plane(World par1World) {
		super(par1World);
		this.setSize(5f, 5f);
//		nboundingbox = new ModifiedBoundingBox(-20,-20,-20,20,20,20,0,0,-6.27,2.5,5,19);
//		nboundingbox.rot.set(this.bodyRot);
//		proxy.replaceBoundingbox(this,nboundingbox);
//		((ModifiedBoundingBox)this.boundingBox).updateOBB(this.posX,this.posY,this.posZ);
		ignoreFrustumCheck = true;
		this.fireCycle1 = 1;
		baseLogic = new PlaneBaseLogic(worldObj, this);
		baseLogic.speedfactor = 0.004f;
		baseLogic.liftfactor = 0.05f;
		baseLogic.flapliftfactor = 0;
		baseLogic.flapdragfactor = 0;
		baseLogic.geardragfactor = 0.000001f;
		baseLogic.dragfactor = 0.01f;
		baseLogic.gravity = 0.02f;
		baseLogic.stability = 3000;
		baseLogic.rotmotion_reduceSpeed = 0.2;
//		baseLogic.slipresist = 4;
		
	}
	
	public double getMountedYOffset() {
		return 0.6D;
	}
	
	
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0D);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(120.0D);
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(80.0D);
		this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(30.0D);
	}
	
	public void onUpdate() {
		double[] pos = new double[]{this.posX, this.posY, this.posZ};
		double[] motion = new double[]{this.motionX, this.motionY, this.motionZ};
		boolean onground = this.onGround;
		super.onUpdate();
		this.onGround = onground;
		this.posX = pos[0];
		this.posY = pos[1];
		this.posZ = pos[2];
		this.motionX = motion[0];
		this.motionY = motion[1];
		this.motionZ = motion[2];
		if (!this.standalone() && baseLogic.childEntities[0] != null && baseLogic.childEntities[0].riddenByEntity == null) {
			this.onGround = true;
		}
		baseLogic.onUpdate();
	}
	
	public void setVelocity(double p_70016_1_, double p_70016_3_, double p_70016_5_) {
//		baseLogic.setVelocity(p_70016_1_,p_70016_3_,p_70016_5_);
	}
	
	@Override
	public int getfirecyclesettings1() {
		return 0;
	}
	
	@Override
	public int getfirecycleprogress1() {
		return 0;
	}
	
	@Override
	public int getfirecyclesettings2() {
		return 0;
	}
	
	@Override
	public int getfirecycleprogress2() {
		return 0;
	}
	
	@Override
	public float getturretrotationYaw() {
		return 0;
	}
	
	@Override
	public float getbodyrotationYaw() {
		return baseLogic.bodyrotationYaw;
	}
	
	@Override
	public float getthrottle() {
		return (float) baseLogic.throttle;
	}
	
	@Override
	public void setBodyRot(Quat4d quat4d) {
		baseLogic.bodyRot = quat4d;
	}
	
	@Override
	public void setthrottle(float th) {
		baseLogic.throttle = th;
	}
	
	@Override
	public void setTrigger(boolean trig1, boolean trig2) {
		baseLogic.trigger1 = trig1;
		baseLogic.trigger2 = trig2;
	}
	
	@Override
	public void initseat() {
	
	}
	
	@Override
	public GVCEntityChild[] getChilds() {
		return baseLogic.childEntities;
	}
	
	@Override
	public void addChild(GVCEntityChild seat) {
		if (seat.idinmasterEntityt != -1 && seat.idinmasterEntityt < baseLogic.childEntities.length) {
			baseLogic.childEntities[seat.idinmasterEntityt] = seat;
			baseLogic.childEntities[seat.idinmasterEntityt].master = this;
		}
	}
	
	@Override
	public boolean isRidingEntity(Entity entity) {
		for (int i = 0; i < baseLogic.childEntities.length; i++) {
			if (baseLogic.childEntities[i] != null) {
				if (baseLogic.childEntities[i].riddenByEntity == entity) return true;
			}
		}
		return false;
	}
	
	public boolean interact(EntityPlayer p_70085_1_) {
		if (!this.worldObj.isRemote && (this.riddenByEntity == null || this.riddenByEntity == p_70085_1_)) {
			if (p_70085_1_.isSneaking()) {
				if (p_70085_1_.getEquipmentInSlot(0) != null) {
					ItemStack itemstack = p_70085_1_.getEquipmentInSlot(0);
					if (itemstack.getItem() == Items.iron_ingot) {
						if (!p_70085_1_.capabilities.isCreativeMode) itemstack.stackSize--;
						if (itemstack.stackSize <= 0 && !p_70085_1_.capabilities.isCreativeMode) {
							p_70085_1_.destroyCurrentEquippedItem();
						}
						this.setHealth(this.getHealth() + 30);
					}
					if (itemstack.getItem() == Item.getItemFromBlock(Blocks.iron_block)) {
						if (!p_70085_1_.capabilities.isCreativeMode) itemstack.stackSize--;
						if (itemstack.stackSize <= 0 && !p_70085_1_.capabilities.isCreativeMode) {
							p_70085_1_.destroyCurrentEquippedItem();
						}
						this.setHealth(this.getHealth() + 300);
					}
				} else if (this.getMobMode() == 0) {
					mode = 1;
					this.setMobMode(1);
					homeposX = (int) p_70085_1_.posX;
					homeposY = (int) p_70085_1_.posY;
					homeposZ = (int) p_70085_1_.posZ;
					master = p_70085_1_;
					p_70085_1_.addChatComponentMessage(new ChatComponentTranslation(
							                                                               "Cover mode"));
				} else if (this.getMobMode() == 1) {
					mode = 2;
					this.setMobMode(2);
					
					p_70085_1_.addChatComponentMessage(new ChatComponentTranslation(
							                                                               "Defense  " + (int) posX + "," + (int) posZ));
					homeposX = (int) posX;
					homeposY = (int) posY;
					homeposZ = (int) posZ;
				} else if (this.getMobMode() == 2) {
					p_70085_1_.addChatComponentMessage(new ChatComponentTranslation("wait "));
					mode = 3;
					this.setMobMode(3);
				} else if (this.getMobMode() == 3) {
					mode = 0;
					this.setMobMode(0);
				}
			} else if (!p_70085_1_.isRiding()) {
				mode = 0;
				this.setMobMode(0);
				if (baseLogic.childEntities[getpilotseatid()] != null && baseLogic.childEntities[getpilotseatid()].riddenByEntity == null)
					p_70085_1_.mountEntity(baseLogic.childEntities[getpilotseatid()]);
			}
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean isChild(Entity entity) {
		for (Entity achild : baseLogic.childEntities) {
			if (entity == achild) return true;
		}
		return entity == this;
	}
	
	@Override
	public int getpilotseatid() {
		return 0;
	}
	
	public void applyEntityCollision(Entity p_70108_1_) {
	}
	
	@Override
	public boolean shouldRenderInPass(int pass) {
		return pass == 1 || pass == 0;
	}
	
	@Override
	public PlaneBaseLogic getBaseLogic() {
		return baseLogic;
	}
	
	protected void onDeathUpdate() {
		++this.deathTicks;
		if (this.deathTicks == 1) {
			this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 0F, false);
		}
		if (this.onGround && !this.worldObj.isRemote) {
			this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 3.5F, false);
			this.setDead();
			Item i = new ItemStack(Blocks.gold_block, 0).getItem();
			this.dropItem(i, 2);
		}
		if (this.deathTicks == 200 && !this.worldObj.isRemote) {
			this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 3.5F, false);
			this.setDead();
			Item i = new ItemStack(Blocks.gold_block, 0).getItem();
			this.dropItem(i, 2);
		}
	}
	
	public boolean isConverting() {
		return false;
	}
	
	public boolean canBePushed() {
		return false;
	}
	
	public void moveFlying(float p_70060_1_, float p_70060_2_, float p_70060_3_) {
	}
	
	public void jump() {
	
	}
	
	public void moveEntityWithHeading(float p_70612_1_, float p_70612_2_) {
	
	}
	
	@Override
	public boolean standalone() {
		return mode != 0;
	}
}
