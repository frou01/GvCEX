package hmggvcmob.entity.friend;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import handmadeguns.entity.IFF;
import handmadeguns.items.guns.HMGItem_Unified_Guns;
import handmadevehicle.SlowPathFinder.WorldForPathfind;
import handmadevehicle.entity.EntityVehicle;
import handmadevehicle.entity.parts.ITurretUser;
import handmadevehicle.entity.parts.IVehicle;
import handmadevehicle.entity.parts.turrets.TurretObj;
import hmggvcmob.IflagBattler;
import handmadevehicle.SlowPathFinder.ModifiedPathNavigater;
import hmggvcmob.ai.*;
import hmggvcmob.entity.EntityBodyHelper_modified;
import hmggvcmob.entity.IGVCmob;
import hmggvcmob.entity.IHasVehicleGacha;
import hmggvcmob.entity.VehicleSpawnGachaOBJ;
import hmggvcmob.entity.guerrilla.EntityGBase;
import hmggvcmob.entity.guerrilla.EntityGBases;
import hmggvcmob.tile.TileEntityFlag;
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
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.List;

import static handmadeguns.HandmadeGunsCore.islmmloaded;
import static handmadeguns.Util.Utils.getmovingobjectPosition_forBlock;
import static handmadevehicle.entity.EntityVehicle.EntityVehicle_spawnByMob;
import static hmggvcmob.GVCMobPlus.fn_PMCflag;
import static hmggvcmob.GVCMobPlus.fn_Supplyflag;
import static java.lang.Math.abs;

public class EntitySoBases extends EntityCreature implements INpc , IflagBattler ,  IFF, IGVCmob, ITurretUser {
	private EntityBodyHelper_modified bodyHelper;
	public Entity prevRidingEntity;
	public String summoningVehicle = null;//nullは無し。自然湧きはLivingSpawnEvent.SpecialSpawnで設定する
	//特殊事情で（ダンジョンとかで）自由な役職の兵士を載せたいときのためにフィールドは残しておこう
	
	public int flagx;
	public int flagy;
	public int flagz;
	private ModifiedPathNavigater modifiedPathNavigater;
	WorldForPathfind worldForPathfind;
	
	public float viewWide = 1.14f;
	public int deathTicks;
	public double movespeed = 0.3d;
	public float spread = 2;
	public double rndyaw;
	public double rndpitch;
	public int type = 0;
	public EntityAISwimming aiSwimming;
	AIAttackGun aiAttackGun;
	public static int spawnedcount;
	public TileEntity spawnedtile = null;
	public boolean isridingVehicle;
	public EntitySoBases(World par1World) {
		super(par1World);
		this.bodyHelper = new EntityBodyHelper_modified(this);
		renderDistanceWeight = 16384;
		this.worldForPathfind = new WorldForPathfind(worldObj);
		this.modifiedPathNavigater = new ModifiedPathNavigater(this, worldObj,worldForPathfind);
		
		this.getNavigator().setBreakDoors(true);
		aiSwimming = new EntityAISwimming(this);
		this.tasks.addTask(0, aiSwimming);
		this.tasks.addTask(0, new AIAttackByTank(this, null, worldForPathfind, this));
		this.tasks.addTask(3, new AIattackOnCollide(this, EntityLiving.class, 1.0D, true));
		this.tasks.addTask(3, new AIattackOnCollide(this, EntityGBases.class, 1.0D, true));
		this.tasks.addTask(3, new AIAttackFlag(this,(IflagBattler) this,worldForPathfind));
		this.tasks.addTask(5, new EntityAIRestrictOpenDoor(this));
		this.tasks.addTask(6, new EntityAIOpenDoor(this, true));
		this.tasks.addTask(7, new EntityAIMoveTowardsRestriction(this, 1.0D));
		//こっから先は待機時（？）
		this.tasks.addTask(8, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(8, new EntityAILookIdle(this));
		//ターゲティング
		this.targetTasks.addTask(1, new AIHurtByTarget(this, true));
		this.targetTasks.addTask(2, new AINearestAttackableTarget(this, EntityGBase.class, 0, true));
		this.targetTasks.addTask(3, new AINearestAttackableTarget(this, EntityLiving.class, 0, true, false, IMob.mobSelector));
		this.targetTasks.addTask(4, new AITargetFlag(this,this,this));
		
	}

	protected float func_110146_f(float p_110146_1_, float p_110146_2_)
	{
		if (this.isAIEnabled())
		{
			this.bodyHelper.func_75664_a();
			return p_110146_2_;
		}
		else
		{
			return super.func_110146_f(p_110146_1_, p_110146_2_);
		}
	}

	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(22, new Integer((int)0));
	}
	
	public void setMobMode(int integer) {
		this.dataWatcher.updateObject(22, Integer.valueOf(integer));
	}
	public int getMobMode() {
		return this.dataWatcher.getWatchableObjectInt(22);
	}
	
    public void readEntityFromNBT(NBTTagCompound p_70037_1_)
    {
        super.readEntityFromNBT(p_70037_1_);
        
        setMobMode(p_70037_1_.getInteger("MobMode"));
    }
    public void writeEntityToNBT(NBTTagCompound p_70014_1_)
    {
        super.writeEntityToNBT(p_70014_1_);
	    spawnedcount--;
//	    if(ridingEntity instanceof ImultiRidable)ridingEntity = null;
        
        p_70014_1_.setInteger("MobMode", getMobMode());
    }
	
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
    
	public void setDead(){
		super.setDead();
		spawnedcount--;
		if(spawnedtile != null && spawnedtile instanceof TileEntityFlag)((TileEntityFlag) spawnedtile).spawnedEntities.remove(this);
	}
    public boolean canAttackClass(Class par1Class)
    {
        return EntityCreature.class != par1Class;
    }

    protected String getHurtSound()
    {
        return "gvcmob:gvcmob.pmcHurt";
    }

    protected String getDeathSound()
    {
	    return "gvcmob:gvcmob.pmcDeath";
    }

    protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_) {
        this.playSound("gvcmob:gvcmob.pmcWalk", 1F, 1.0F);
    }
    
    protected boolean canDespawn()
    {
        return false;
    }
    
    public boolean isAIEnabled()
    {
        return true;
    }

	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
	}

	public void addRandomArmor() {
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
		MovingObjectPosition movingobjectposition = getmovingobjectPosition_forBlock(worldObj,startpos, targetpos, false, true, false);
		if(movingobjectposition!=null) {
			return false;
		}
		return !((this.isInWater() || p_70685_1_.isInWater()) && getDistanceSqToEntity(p_70685_1_) > 256);
	}
	
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
	
	public void onUpdate() {
		if(summoningVehicle != null) {

			int var12 = MathHelper.floor_double((double) (this.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
			System.out.println(summoningVehicle);
			EntityVehicle bespawningEntity = EntityVehicle_spawnByMob(worldObj,summoningVehicle);
			bespawningEntity.setLocationAndAngles(this.posX, this.posY, this.posZ, var12 , 0.0F);
			if(bespawningEntity.checkObstacle()) {
				worldObj.spawnEntityInWorld(bespawningEntity);
				if(bespawningEntity.pickupEntity(this,0)) {
					isridingVehicle = true;
				}
			}
			summoningVehicle = null;
		}
		if(this.getAttackTarget() != null && this.getAttackTarget().isDead)this.setAttackTarget(null);
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
			if (this.getEntityData().getBoolean("HMGisUsingItem")) {
				this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(movespeed / 4);
			} else {
				this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(movespeed);
			}
		}
		this.getEntityData().setBoolean("HMGisUsingItem",false);
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
	
	public PathNavigate getNavigator()
	{
		return this.modifiedPathNavigater;
	}
	protected void updateAITasks()
	{
		super.updateAITasks();
		modifiedPathNavigater.onUpdateNavigation();
	}
	
	public void dismountEntity(Entity p_110145_1_)
	{
	}
	
	@Override
	public void mountEntity(Entity p_70078_1_)
	{
		
		if (p_70078_1_ == null)
		{
			if (this.ridingEntity != null)
			{
				this.ridingEntity.riddenByEntity = null;
			}
			
			this.ridingEntity = null;
		}
		else
		{
			if (this.ridingEntity != null)
			{
				this.ridingEntity.riddenByEntity = null;
			}

			if (p_70078_1_ != null)
			{
				for (Entity entity1 = p_70078_1_.ridingEntity; entity1 != null; entity1 = entity1.ridingEntity)
				{
					if (entity1 == this)
					{
						return;
					}
				}
			}

			this.ridingEntity = p_70078_1_;
			p_70078_1_.riddenByEntity = this;
		}
	}

    protected void collideWithNearbyEntities()
    {
        List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(0.20000000298023224D, 0.0D, 0.20000000298023224D));

        if (list != null && !list.isEmpty())
        {
            for (int i = 0; i < list.size(); ++i)
            {
                Entity entity = (Entity)list.get(i);

                if (entity.canBePushed())
                {
                    this.collideWithEntity(entity);
                }
                if(!isridingVehicle && !entity.isDead && !worldObj.isRemote && entity instanceof EntityVehicle && ((EntityVehicle) entity).canUseByMob) {
                    Entity pilot = ((EntityVehicle) entity).getBaseLogic().getRiddenEntityList()[((EntityVehicle) entity).getpilotseatid()];
                    if((pilot == null || is_this_entity_friend(pilot))&&!((EntityVehicle) entity).getBaseLogic().isRidingEntity(this)){
						if(((EntityVehicle) entity).pickupEntity(this,0)) {
							isridingVehicle = true;
						}
					}
                }
            }
        }
    }
	
	public void applyEntityCollision(Entity p_70108_1_)
	{
		boolean flag = p_70108_1_.riddenByEntity != this && p_70108_1_.ridingEntity != this;
		if (flag)
		{
			double d0 = p_70108_1_.posX - this.posX;
			double d1 = p_70108_1_.posZ - this.posZ;
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
				d0 *= (double)(1.0F - this.entityCollisionReduction);
				d1 *= (double)(1.0F - this.entityCollisionReduction);
				this.addVelocity(-d0, 0.0D, -d1);
				p_70108_1_.addVelocity(d0, 0.0D, d1);
			}
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

	@Override
	public boolean isthisFlagIsEnemys(Block block) {
		return block != fn_PMCflag && block != fn_Supplyflag;
	}
	
	public boolean shouldDismountInWater(Entity entity){
		return false;
	}


    public void setVehicleName(String string) {
        summoningVehicle = string;
    }

    @Override
	public boolean is_this_entity_friend(Entity entity) {
		if(entity instanceof EntityPlayer){
			return true;
		}else if(entity instanceof EntitySoBases) {
			return true;
		}else if(islmmloaded && entity instanceof LMM_EntityLittleMaid){
			return true;
		}
		return false;
	}
	
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
		flag = dist < target.getEntityData().getFloat("GunshotLevel") * 14;
		return flag;
	}
	@Override
	public void setspawnedtile(TileEntity flag) {
		spawnedtile = flag;
	}

	public int seatID;
	public TurretObj currentMainTurret;
	public TurretObj currentSubTurret;
    @Override
    public void setSeatID(int id) {
        seatID = id;
    }

    @Override
    public void setTurretMain(TurretObj turret) {
        currentMainTurret = turret;
    }

    @Override
    public void setTurretSub(TurretObj turret) {
        currentSubTurret = turret;
    }

    @Override
    public int getSeatID() {
        return seatID;
    }

    @Override
    public TurretObj getTurretMain() {
        return currentMainTurret;
    }

    @Override
    public TurretObj getTurretSub() {
        return currentSubTurret;
    }
}
