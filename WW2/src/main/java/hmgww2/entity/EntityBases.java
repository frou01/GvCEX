package hmgww2.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import handmadeguns.entity.IFF;
import handmadeguns.items.guns.HMGItem_Unified_Guns;
import hmggvcmob.IflagBattler;
import hmggvcmob.SlowPathFinder.WorldForPathfind;
import hmggvcmob.ai.AIAttackGun;
import hmggvcmob.ai.AIHurtByTarget;
import hmggvcmob.ai.AINearestAttackableTarget;
import hmggvcmob.ai.AIattackOnCollide;
import hmggvcmob.entity.IGVCmob;
import hmggvcmob.entity.IRideableTank;
import hmggvcmob.entity.IdriveableVehicle;
import hmggvcmob.entity.friend.EntitySoBases;
import hmggvcmob.entity.guerrilla.EntityGBase;
import hmggvcmob.entity.guerrilla.EntityGBases;
import hmggvcmob.tile.TileEntityFlag;
import hmgww2.Nation;
import hmgww2.items.ItemIFFArmor;
import littleMaidMobX.LMM_EntityLittleMaid;
import net.minecraft.block.Block;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import static handmadeguns.HandmadeGunsCore.islmmloaded;
import static hmggvcmob.GVCMobPlus.fn_PMCflag;
import static hmggvcmob.GVCMobPlus.fn_Supplyflag;
import static java.lang.Math.abs;

public abstract class EntityBases extends EntityCreature implements IFF,INpc, IflagBattler,IGVCmob {
	public int deathTicks;
	public EntityLivingBase fri;
	
	public boolean vehicle = false;
	public boolean opentop = true;
	
	
	public boolean server1 = false;
	public boolean server2 = false;
	public boolean serverx = false;
	
	
	
	public double thpower;
	public float th;
	public double thpera = 0;
	public float throte = 0;
	
	public int soundtick = 0;
	
	public float overlayhight = 1.0F;
	public float overlayhight_3 = 1.0F;
	public float overlaywidth_3 = 1.0F;
	public int flagx;
	public int flagy;
	public int flagz;
	
	public float viewWide = 1.14f;
	public double movespeed = 0.3d;
	public float spread = 2;
	public double rndyaw;
	public double rndpitch;
	public int type = 0;
	public EntityAISwimming aiSwimming;
	public EntityAIOpenDoor AIOpenDoor;
	AIAttackGun aiAttackGun;
	public int homeposX;
	public int homeposY;
	public int homeposZ;
	
	public int resetFollowpathCnt;
	
	public int moveoffsetx;
	public int moveoffsetz;
	public Entity master;
	WorldForPathfind worldForPathfind;
	public boolean interact(EntityPlayer p_70085_1_) {
		if (super.interact(p_70085_1_)) {
			return false;
		} else if (!this.worldObj.isRemote && (this.riddenByEntity == null || this.riddenByEntity == p_70085_1_)) {
			if(p_70085_1_.isSneaking()){
				if (mode == 0) {
					mode = 1;
					homeposX = (int) posX;
					homeposY = (int) posY;
					homeposZ = (int) posZ;
					p_70085_1_.addChatComponentMessage(new ChatComponentTranslation(
							                                                               "Defense this position!"));
				} else if (mode == 1) {
					mode = 2;
					homeposX = (int) p_70085_1_.posX;
					homeposY = (int) p_70085_1_.posY;
					homeposZ = (int) p_70085_1_.posZ;
					master = p_70085_1_;
					p_70085_1_.addChatComponentMessage(new ChatComponentTranslation(
							                                                               "OK,I'll Follow Leader!"));
				} else if (mode == 2){
					mode = 0;
				}
			}
			return true;
		}
		return false;
	}
	public EntityBases(World par1World) {
		super(par1World);
		worldForPathfind = new WorldForPathfind(par1World);
		this.fri = this;
		this.getNavigator().setBreakDoors(true);
		aiSwimming = new EntityAISwimming(this);
		AIOpenDoor           =new EntityAIOpenDoor(this, true);
		this.tasks.addTask(0, aiSwimming);
		this.tasks.addTask(3, new AIattackOnCollide(this, EntityLiving.class, 1.0D, true));
		this.tasks.addTask(3, new AIattackOnCollide(this, EntityGBases.class, 1.0D, true));
		this.tasks.addTask(4, new EntityAIRestrictOpenDoor(this));
		this.tasks.addTask(5, AIOpenDoor);
		this.tasks.addTask(6, new EntityAIMoveTowardsRestriction(this, this instanceof IdriveableVehicle ? 0: 1));
		//こっから先は待機時（？）
		this.tasks.addTask(7, new EntityAIWander(this, this instanceof IdriveableVehicle ? 0: 1));
		this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(8, new EntityAILookIdle(this));
		//ターゲティング
		this.targetTasks.addTask(1, new AIHurtByTarget(this, true));
		this.targetTasks.addTask(2, new AINearestAttackableTarget(this, EntityGBase.class, 0, true));
		this.targetTasks.addTask(3, new AINearestAttackableTarget(this, EntityLiving.class, 0, true, false, IMob.mobSelector));
	}
	
	public boolean CanAttack(Entity entity) {
		boolean cri = true;
		if ((entity instanceof EntityMob) && !entity.isDead  && ((EntityLivingBase) entity).getHealth() > 0.0F && getEntitySenses().canSee(entity)) {
			return true;
		}else {
			return false;
		}
	}
	
	
	
	
	public boolean canAttackClass(Class par1Class) {
		return EntityCreature.class != par1Class;
	}
	public void onUpdate()
	{
		super.onUpdate();
		if(this.getAttackTarget() != null && (this.getAttackTarget() instanceof EntitySoBases || this.getAttackTarget() instanceof EntityPlayer || (islmmloaded && this.getAttackTarget() instanceof LMM_EntityLittleMaid)))this.setAttackTarget(null);
		if(this.getHeldItem() != null) {
			if(this.getHeldItem().hasTagCompound()){
				if(this.getHeldItem().getItem() instanceof HMGItem_Unified_Guns)((HMGItem_Unified_Guns)this.getHeldItem().getItem()).checkTags(this.getHeldItem());
			}
			if(!worldObj.isRemote)
				if (this.getEntityData().getBoolean("HMGisUsingItem")) {
					this.setSneaking(true);
				} else {
					this.setSneaking(false);
				}
			int bullets = this.getHeldItem().getItemDamage();
			float backRP = rotationPitch;
			float backRY = rotationYaw;
			if (this.rand.nextInt(5) == 0) {
				rndyaw = this.rand.nextGaussian() * (double) (this.rand.nextBoolean() ? -1 : 1) * spread;
				rndpitch = this.rand.nextGaussian() * (double) (this.rand.nextBoolean() ? -1 : 1) * spread;
			}
			this.rotationPitch += rndyaw;
			this.rotationYaw += rndpitch;
			this.getHeldItem().getItem().onUpdate(this.getHeldItem(), worldObj, this, 0, true);
			rotationPitch = backRP;
			rotationYaw = backRY;
			if (bullets != this.getHeldItem().getItemDamage()) {
				this.aiAttackGun.burstingtime++;
			}
		}
		if(this instanceof IRideableTank){
			if(mode == 2) {
				if (this.getAttackTarget() == null && this.getDistanceSq(homeposX+1, homeposY+1, homeposZ+1)>256) {
					if(this.getNavigator().getPath() != null && this.getNavigator().getPath().getFinalPathPoint().xCoord != homeposX+1 && this.getNavigator().getPath().getFinalPathPoint().xCoord != homeposY && this.getNavigator().getPath().getFinalPathPoint().xCoord != homeposZ+1)this.getNavigator().setPath(worldForPathfind.getEntityPathToXYZ(this, homeposX+1, homeposY, homeposZ+1, 80f, true, false, false, true), 1.0d);
					this.getNavigator().setPath(worldForPathfind.getEntityPathToXYZ(this, homeposX+1, homeposY, homeposZ+1, 80f, true, false, false, true), 1.2);
				}
			}else if(mode == 1) {
				if (master != null) {
					homeposX = (int) master.posX;
					homeposY = (int) master.posY;
					homeposZ = (int) master.posZ;
					if ((this.getAttackTarget() == null && this.getDistanceSq(homeposX, homeposY, homeposZ) > 64) || this.getDistanceSq(homeposX, homeposY + 1, homeposZ) > 256) {
						if (resetFollowpathCnt > 10) {
							homeposX = (int) master.posX + moveoffsetx;
							homeposY = (int) master.posY;
							homeposZ = (int) master.posZ + moveoffsetz;
							resetFollowpathCnt = 0;
							this.getNavigator().setPath(worldForPathfind.getEntityPathToXYZ(this, homeposX, homeposY, homeposZ, 60f, true, false, false, true), 1.2);
						}
					} else {
						moveoffsetx = rand.nextInt(12) * (this.rand.nextBoolean() ? -1 : 1);
						moveoffsetz = rand.nextInt(12) * (this.rand.nextBoolean() ? -1 : 1);
						resetFollowpathCnt = 0;
					}
					resetFollowpathCnt++;
				}else {
					mode = 2;
				}
			}
		}else {
			if(mode == 1) {
				if (this.getAttackTarget() == null && this.getDistanceSq(homeposX+1, homeposY+1, homeposZ+1)>256) {
					if(this.getNavigator().getPath() != null && this.getNavigator().getPath().getFinalPathPoint().xCoord != homeposX+1 && this.getNavigator().getPath().getFinalPathPoint().xCoord != homeposY && this.getNavigator().getPath().getFinalPathPoint().xCoord != homeposZ+1)this.getNavigator().setPath(worldForPathfind.getEntityPathToXYZ(this, homeposX+1, homeposY, homeposZ+1, 80f, true, false, false, true), 1.0d);
					if(onGround)this.getNavigator().setPath(worldForPathfind.getEntityPathToXYZ(this, homeposX+1, homeposY, homeposZ+1, 80f, true, false, false, true), 1.0d);
				}
			}else if(mode == 2) {
				if (master != null) {
					homeposX = (int) master.posX;
					homeposY = (int) master.posY;
					homeposZ = (int) master.posZ;
					if ((this.getAttackTarget() == null && this.getDistanceSq(homeposX, homeposY, homeposZ) > 64) || this.getDistanceSq(homeposX, homeposY + 1, homeposZ) > 256) {
						if (resetFollowpathCnt > 10) {
							homeposX = (int) master.posX + moveoffsetx;
							homeposY = (int) master.posY;
							homeposZ = (int) master.posZ + moveoffsetz;
							resetFollowpathCnt = 0;
							if (onGround || isInWater())
								this.getNavigator().setPath(worldForPathfind.getEntityPathToXYZ(this, homeposX, homeposY, homeposZ, 60f, true, false, false, true), 1.0d);
						}
					} else {
						moveoffsetx = rand.nextInt(12) * (this.rand.nextBoolean() ? -1 : 1);
						moveoffsetz = rand.nextInt(12) * (this.rand.nextBoolean() ? -1 : 1);
						resetFollowpathCnt = 0;
					}
					resetFollowpathCnt++;
				} else {
					mode = 0;
				}
			}
		}
		this.getEntityData().setBoolean("HMGisUsingItem",false);
		if(rand.nextInt(10) == 0) this.addPotionEffect(new PotionEffect(Potion.regeneration.id, 1, 2));
	}
	public boolean isAIEnabled() {
		return true;
	}
	
	protected boolean canDespawn()
	{
		return false;
	}
	
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
	}
	
	public void addRandomArmor() {
		super.addRandomArmor();
	}
	
	public boolean isConverting() {
		return false;
	}
	
	
	
	public int mode = 0;
	
	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(22, new Integer((int)0));
	}
	
	public void setMobMode(int integer) {
		mode = integer;
		this.dataWatcher.updateObject(22, Integer.valueOf(integer));
	}
	public int getMobMode() {
		if(worldObj.isRemote){
			mode = this.dataWatcher.getWatchableObjectInt(22);
		}
		return mode;
	}
	
	
	protected int deadtime;
	
	
	
	
	
	
	
	
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityLivingData)
	{
		par1EntityLivingData = super.onSpawnWithEgg(par1EntityLivingData);
		{
			this.addRandomArmor();
			this.enchantEquipment();
		}
		
		this.setCanPickUpLoot(this.rand.nextFloat() < 0.55F * this.worldObj.func_147462_b(this.posX, this.posY, this.posZ));
		return par1EntityLivingData;
	}
	
	
	
	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender(float par1)
	{
		return super.getBrightnessForRender(par1);
	}
	
	/**
	 * Gets how bright this entity is.
	 */
    /*public float getBrightness(float par1)
    {
        return 1.0F;
    }*/
	
	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	protected String getHurtSound()
	{
		return "mob.irongolem.hit";
	}
	
	/**
	 * Returns the sound this mob makes on death.
	 */
	protected String getDeathSound()
	{
		return "mob.irongolem.death";
	}
	
	protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_)
	{
		this.playSound("mob.irongolem.walk", 1F, 1.0F);
	}
	
	public static float getMobScale() {
		return 8;
	}
	
	
	@Override
	public Block getFlag() {
		return fn_PMCflag;
	}
	public boolean isCreatureType(EnumCreatureType type, boolean forSpawnCount)
	{
		return type.getCreatureClass() == EntitySoBases.class;
	}
	@Override
	public boolean istargetingflag() {
		return worldObj.getTileEntity(flagx,flagy,flagz) instanceof TileEntityFlag && worldObj.getBlock(flagx,flagy,flagz) != getFlag();
	}
	
	@Override
	public Vec3 getflagposition() {
		return Vec3.createVectorHelper(flagx,flagy,flagz);
	}
	
	@Override
	public void setflagposition(int x,int y,int z) {
		flagx = x;
		flagy = y;
		flagz = z;
	}
	@Override
	public boolean canEntityBeSeen(Entity p_70685_1_)
	{
		Vec3 startpos = Vec3.createVectorHelper(this.posX, this.posY + (double) this.getEyeHeight(), this.posZ);
		Vec3 targetpos = Vec3.createVectorHelper(p_70685_1_.posX, p_70685_1_.posY + (double) p_70685_1_.getEyeHeight(), p_70685_1_.posZ);
		Vec3 penerater = Vec3.createVectorHelper(p_70685_1_.posX - this.posX,  p_70685_1_.posY + (double) p_70685_1_.getEyeHeight()-(this.posY + (double) this.getEyeHeight()),p_70685_1_.posZ - this.posZ);
		penerater = penerater.normalize();
		MovingObjectPosition movingobjectposition = this.worldObj.rayTraceBlocks(startpos, targetpos);
		int abortcnt=0;
		while(movingobjectposition!=null) {
			if(abortcnt > 7)return false;
			Block hitedblock = this.worldObj.getBlock(movingobjectposition.blockX, movingobjectposition.blockY, movingobjectposition.blockZ);
			if(hitedblock.getMaterial().isOpaque()){
				return false;
			}
			Vec3 newstartpos = Vec3.createVectorHelper(movingobjectposition.hitVec.xCoord + penerater.xCoord, movingobjectposition.hitVec.yCoord + penerater.yCoord, movingobjectposition.hitVec.zCoord + penerater.zCoord);
			if(startpos.squareDistanceTo(newstartpos)>startpos.squareDistanceTo(targetpos))break;
			movingobjectposition = this.worldObj.rayTraceBlocks(newstartpos,targetpos);
			abortcnt++;
		}
		return true;
	}
	/**
	 * returns a (normalized) vector of where this entity is looking
	 */
	public Vec3 getLookVec()
	{
		return this.getLook(1.0F);
	}
	
	/**
	 * interpolated look vector
	 */
	public Vec3 getLook(float p_70676_1_)
	{
		float f1;
		float f2;
		float f3;
		float f4;
		
		if (p_70676_1_ == 1.0F)
		{
			f1 = MathHelper.cos(-this.rotationYawHead * 0.017453292F - (float)Math.PI);
			f2 = MathHelper.sin(-this.rotationYawHead * 0.017453292F - (float)Math.PI);
			f3 = -MathHelper.cos(-this.rotationPitch * 0.017453292F);
			f4 = MathHelper.sin(-this.rotationPitch * 0.017453292F);
			return Vec3.createVectorHelper((double)(f2 * f3), (double)f4, (double)(f1 * f3));
		}
		else
		{
			f1 = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * p_70676_1_;
			f2 = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * p_70676_1_;
			f3 = MathHelper.cos(-f2 * 0.017453292F - (float)Math.PI);
			f4 = MathHelper.sin(-f2 * 0.017453292F - (float)Math.PI);
			float f5 = -MathHelper.cos(-f1 * 0.017453292F);
			float f6 = MathHelper.sin(-f1 * 0.017453292F);
			return Vec3.createVectorHelper((double)(f4 * f5), (double)f6, (double)(f3 * f5));
		}
	}
	public void moveFlying(float p_70060_1_, float p_70060_2_, float p_70060_3_)
	{
		float f3 = p_70060_1_ * p_70060_1_ + p_70060_2_ * p_70060_2_;
		
		if (f3 >= 1.0E-4F)
		{
			f3 = MathHelper.sqrt_float(f3);
			
			if (f3 < 1.0F)
			{
				f3 = 1.0F;
			}
			f3 = p_70060_3_ / f3;
			f3 = abs(f3);
			p_70060_1_ *= f3;
			p_70060_2_ *= f3;
			float f4 = MathHelper.sin(this.rotationYaw * (float)Math.PI / 180.0F);
			float f5 = MathHelper.cos(this.rotationYaw * (float)Math.PI / 180.0F);
			this.motionX += (double)(-p_70060_2_ * f4 + p_70060_1_ * f5);
			this.motionZ += (double)( p_70060_2_ * f5 + p_70060_1_ * f4);
		}
	}
	
	
	public void onLivingUpdate()
	{
		this.updateArmSwingProgress();
		float f = this.getBrightness(1.0F);
		
		if (f > 0.5F)
		{
			this.entityAge += 2;
		}
		
		super.onLivingUpdate();
	}
	
	public void readEntityFromNBT(NBTTagCompound p_70037_1_)
	{
		super.readEntityFromNBT(p_70037_1_);
		
		homeposX = p_70037_1_.getInteger("homeposX");
		homeposY = p_70037_1_.getInteger("homeposY");
		homeposZ = p_70037_1_.getInteger("homeposZ");
		mode = p_70037_1_.getInteger("mode");
	}
	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	public void writeEntityToNBT(NBTTagCompound p_70014_1_)
	{
		super.writeEntityToNBT(p_70014_1_);
		
		p_70014_1_.setInteger("homeposX",homeposX);
		p_70014_1_.setInteger("homeposY",homeposY);
		p_70014_1_.setInteger("homeposZ",homeposZ);
		p_70014_1_.setInteger("mode",mode);
	}
	@Override
	public boolean isthisFlagIsEnemys(Block block) {
		return block != fn_PMCflag && block != fn_Supplyflag;
	}
	
	public boolean shouldDismountInWater(Entity entity){
		return false;
	}
	@Override
	public boolean is_this_entity_friend(Entity entity){
		if(entity instanceof EntityBases){
			return ((EntityBases) entity).getnation() == this.getnation();
		}else if(entity instanceof EntityPlayer){
			return this.getnation() == ((ItemIFFArmor) ((EntityPlayer) entity).getEquipmentInSlot(4).getItem()).nation;
		}
		return false;
	}
	
	abstract public Nation getnation();
	
	@Override
	public float getviewWide() {
		return viewWide;
	}
	@Override
	public boolean canSeeTarget(Entity target) {
		boolean flag;
		flag = canhearsound(target);
		if (!flag) {
			Vec3 lookVec = getLookVec();
			Vec3 toTGTvec = Vec3.createVectorHelper(target.posX - posX, target.posY + target.getEyeHeight() - (posY + getEyeHeight()), target.posZ - posZ);
			toTGTvec = toTGTvec.normalize();
			return lookVec.squareDistanceTo(toTGTvec) < getviewWide() * 1.2f;
		}else
			return true;
	}
	
	@Override
	public boolean canhearsound(Entity target) {
		boolean flag;
		double dist = getDistanceToEntity(target);
		flag = dist < target.getEntityData().getFloat("GunshotLevel") * 16;
		return flag;
	}
	
	@Override
	public void setspawnedtile(TileEntity flag) {
		flag = flag;
	}
}
