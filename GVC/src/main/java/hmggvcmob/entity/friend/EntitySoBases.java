package hmggvcmob.entity.friend;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import handmadeguns.Util.GunsUtils;
import handmadeguns.entity.IFF;
import handmadeguns.entity.PlacedGunEntity;
import handmadeguns.items.guns.HMGItem_Unified_Guns;
import handmadevehicle.SlowPathFinder.WorldForPathfind;
import handmadevehicle.entity.EntityDummy_rider;
import handmadevehicle.entity.EntityVehicle;
import handmadevehicle.entity.parts.ITurretUser;
import handmadevehicle.entity.parts.Modes;
import handmadevehicle.entity.parts.logics.BaseLogic;
import handmadevehicle.entity.parts.turrets.TurretObj;
import handmadevehicle.entity.prefab.Prefab_Seat;
import handmadevehicle.entity.prefab.Prefab_Vehicle_Base;
import hmggvcmob.GVCMobPlus;
import hmggvcmob.entity.*;
import handmadevehicle.SlowPathFinder.ModifiedPathNavigater;
import hmggvcmob.ai.*;
import hmggvcmob.camp.CampObj;
import hmggvcmob.entity.guerrilla.EntityGBase;
import hmggvcmob.entity.guerrilla.EntityGBases;
import littleMaidMobX.LMM_EntityLittleMaid;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

import javax.vecmath.Vector3d;
import java.util.Iterator;
import java.util.List;

import static handmadeguns.HandmadeGunsCore.islmmloaded;
import static handmadeguns.Util.GunsUtils.getmovingobjectPosition_forBlock;
import static handmadevehicle.Utils.canMoveEntity;
import static handmadevehicle.entity.EntityVehicle.EntityVehicle_spawnByMob;
import static hmggvcmob.GVCMobPlus.*;
import static java.lang.Math.abs;

public abstract class EntitySoBases extends EntityCreature implements INpc ,  IFF, IGVCmob, ITurretUser , IPlatoonable {
	private EntityBodyHelper_modified bodyHelper;
	public String summoningVehicle = null;

	private ModifiedPathNavigater modifiedPathNavigater;
	WorldForPathfind worldForPathfind;
	
	public float viewWide = 1.14f;
	public double movespeed = 0.3d;
	public float spread = 1;
	public EntityAISwimming aiSwimming;
	public AIAttackGun aiAttackGun;
	public AITargetFlag aiTargetFlag;
	public static int spawnedcount;

	public int rideCool = 0;

	public EntitySoBases(World par1World) {
		super(par1World);
		this.moveHelper = new EntityMoveHelperModified(this);
		this.bodyHelper = new EntityBodyHelper_modified(this);
		renderDistanceWeight = 16384;
		this.worldForPathfind = new WorldForPathfind(worldObj);
		this.modifiedPathNavigater = new ModifiedPathNavigater(this, worldObj,worldForPathfind);

		ObfuscationReflectionHelper.setPrivateValue(EntityLiving.class, this, moveHelper, "moveHelper", "field_70765_h");
		ObfuscationReflectionHelper.setPrivateValue(EntityLiving.class, this, modifiedPathNavigater, "navigator", "field_70699_by");

		this.getNavigator().setBreakDoors(true);
		this.tasks.addTask(0, aiSwimming = new EntityAISwimming(this));
		this.tasks.addTask(0, new AIAttackByTank(this, null, worldForPathfind, this));
		this.tasks.addTask(3, new AIattackOnCollide(this, EntityLiving.class, 1.0D, true));
		this.tasks.addTask(3, new AIattackOnCollide(this, EntityGBases.class, 1.0D, true));
		this.tasks.addTask(3, new AIDriveTank(this, null, worldForPathfind));
		this.tasks.addTask(5, new EntityAIRestrictOpenDoor(this));
		this.tasks.addTask(6, new EntityAIOpenDoor(this, true));
		this.tasks.addTask(7, new EntityAIMoveTowardsRestriction(this, 1.0D));
		this.tasks.addTask(8, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(8, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new AIHurtByTarget(this, true));
		this.targetTasks.addTask(2, new AINearestAttackableTarget(this, EntityGBase.class, 0, true));
		this.targetTasks.addTask(3, new AINearestAttackableTarget(this, EntityLiving.class, 0, true, false, IMob.mobSelector));
		
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
	}
	
    public void readEntityFromNBT(NBTTagCompound p_70037_1_)
    {
        super.readEntityFromNBT(p_70037_1_);
		seatID = p_70037_1_.getInteger("seatID");
		if(p_70037_1_.hasKey("platoonTargetPos"))getEntityData().setIntArray("platoonTargetPos",p_70037_1_.getIntArray("platoonTargetPos"));
    }
    public void writeEntityToNBT(NBTTagCompound p_70014_1_)
    {
        super.writeEntityToNBT(p_70014_1_);
        if(platoonOBJ != null)p_70014_1_.setIntArray("platoonTargetPos",new int[]{(int) platoonOBJ.PlatoonTargetPos.x,(int) platoonOBJ.PlatoonTargetPos.y,(int) platoonOBJ.PlatoonTargetPos.z});
	    spawnedcount--;
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

	public boolean isCreatureType(EnumCreatureType type, boolean forSpawnCount)
	{
		return type.getCreatureClass() == EntitySoBases.class;
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
	boolean platoon_check = false;
	public void onUpdate() {
		if(getPlatoon() == null && !platoon_check){
			platoon_check = true;
			if(getEntityData().hasKey("platoonTargetPos")){
				System.out.println("debug");
				makePlatoon_OnLoading();
				platoonOBJ.setPlatoonTargetPos(getEntityData().getIntArray("platoonTargetPos"));
			}
		}

		if(!worldObj.isRemote && getPlatoon() != null){

			if(abs(platoonOBJ.lastUpdatedTick - FMLCommonHandler.instance().getMinecraftServerInstance().getTickCounter()) > 2 || platoonOBJ.leader.entity.isDead || !worldObj.checkChunksExist((int)platoonOBJ.leader.entity.posX,(int)platoonOBJ.leader.entity.posY,(int)platoonOBJ.leader.entity.posZ,(int)platoonOBJ.leader.entity.posX,(int)platoonOBJ.leader.entity.posY,(int)platoonOBJ.leader.entity.posZ)) {
				platoonOBJ.leader.entity = this;//落ち着け、ジーン！指揮を引き継げ！
			}

			if(isPlatoonLeader()){
				if(platoonOBJ.platoonMode == Modes.Follow){
					if(platoonOBJ.platoonTargetEntity == null){//やっぱり飛んでる…
						platoonOBJ.platoonMode = Modes.Wait;
					}else {
						platoonOBJ.setPlatoonTargetPos(platoonOBJ.platoonTargetEntity);
					}
				}
				platoonOBJ.update();
			}
		}
		super.onUpdate();
		if(summoningVehicle != null) {

			int var12 = MathHelper.floor_double((double) (this.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
			System.out.println(summoningVehicle);
			EntityVehicle bespawningEntity = EntityVehicle_spawnByMob(worldObj,summoningVehicle);
			bespawningEntity.noDrop = true;
			bespawningEntity.setLocationAndAngles(this.posX, this.posY, this.posZ, var12 , 0.0F);
			if((!bespawningEntity.getBaseLogic().prefab_vehicle.T_Land_F_Plane && this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ))) || bespawningEntity.checkObstacle()) {
				if(bespawningEntity.pickupEntity(this,0)) {
					this.setCurrentItemOrArmor(0,null);
				}
				bespawningEntity.canUseByMob = true;
				bespawningEntity.despawn = true;
				Prefab_Vehicle_Base prefab_vehicle = bespawningEntity.getBaseLogic().prefab_vehicle;
				for(int slotID = 0 ;slotID < prefab_vehicle.weaponSlotNum;slotID++) {
					if(prefab_vehicle.weaponSlot_linkedTurret_StackWhiteList.get(slotID) != null) {
						int randUsingSlot = rand.nextInt(prefab_vehicle.weaponSlot_linkedTurret_StackWhiteList.get(slotID).length);
						String whiteList = prefab_vehicle.weaponSlot_linkedTurret_StackWhiteList.get(slotID)[randUsingSlot];
						System.out.println("" + whiteList);
						Item check = GameRegistry.findItem("HandmadeGuns", whiteList);
						if (check instanceof HMGItem_Unified_Guns && ((HMGItem_Unified_Guns) check).gunInfo.guerrila_can_use) {
							bespawningEntity.getBaseLogic().inventoryVehicle.setInventorySlotContents(slotID, new ItemStack(check));
						}
					}else{
						int randUsingSlot = rand.nextInt(GVCMobPlus.Guns_CanUse.size());
						Item choosenGun = GVCMobPlus.Guns_CanUse.get(randUsingSlot);
						bespawningEntity.getBaseLogic().inventoryVehicle.setInventorySlotContents(slotID, new ItemStack(choosenGun));
					}
				}
				if(!prefab_vehicle.T_Land_F_Plane) {
					bespawningEntity.setLocationAndAngles(this.posX, 128, this.posZ, var12, 0.0F);
					bespawningEntity.getBaseLogic().throttle = prefab_vehicle.throttle_Max;
				}
				for (int cnt = 0;cnt < bespawningEntity.getBaseLogic().riddenByEntities.length;cnt++) {
					EntityLiving entity = new GVCEntitySoldier(worldObj);
					entity.copyLocationAndAnglesFrom(this);
					entity.onSpawnWithEgg(null);
					if(bespawningEntity.pickupEntity(entity,cnt)) {
						Prefab_Seat sittingSeat = bespawningEntity.getBaseLogic().seatInfos[((EntityDummy_rider)entity.ridingEntity).linkedSeatID].prefab_seat;
						if(sittingSeat.isBlindedSeat || sittingSeat.hasGun){
							this.setCurrentItemOrArmor(0,null);
						}
						worldObj.spawnEntityInWorld(entity);
					}
				}
				for(TurretObj turretObj : bespawningEntity.getBaseLogic().allturrets){
					if(turretObj.gunItem != null && turretObj.gunStack != null){
						turretObj.gunItem.checkTags(turretObj.gunStack);
						turretObj.gunItem.resetReload(turretObj.gunStack,worldObj,this,0);
					}
				}
				worldObj.spawnEntityInWorld(bespawningEntity);
			}
			summoningVehicle = null;
		}
		if(this.getAttackTarget() != null && this.getAttackTarget().isDead)this.setAttackTarget(null);
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
			this.getHeldItem().getItem().onUpdate(this.getHeldItem(), worldObj, this, 0, true);
			rotationPitch = backRP;
			rotationYaw = backRY;
			if (this.getEntityData().getBoolean("HMGisUsingItem")) {
				this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(movespeed / 4);
			} else {
				this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(movespeed);
			}
		}
		if(ridingEntity instanceof PlacedGunEntity){
			if(this.getEntityData().getBoolean("HMGisUsingItem")){
				((PlacedGunEntity) ridingEntity).firing = true;
			}else {
				((PlacedGunEntity) ridingEntity).firing = false;
			}
		}
		if(ridingEntity == null){
			rideCool --;
		}
		this.getEntityData().setBoolean("HMGisUsingItem",false);
		if(modifiedPathNavigater.getSpeed() < 0 && rand.nextInt(10)==0 && this.getAttackTarget() == null)modifiedPathNavigater.setSpeed(1);

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
	private EntityMoveHelperModified moveHelper;
	protected void updateAITasks()
	{
		super.updateAITasks();
	}
	public EntityMoveHelper getMoveHelper()
	{
		return this.moveHelper;
	}
	public float moveToDir = 0;
	public void setAIMoveSpeed(float p_70659_1_)
	{
		super.setAIMoveSpeed(p_70659_1_);
		this.setMoveForward(p_70659_1_);
		moveToDir = this.rotationYaw;
	}
	public void moveEntityWithHeading(float p_70612_1_, float p_70612_2_){
		this.rotationYaw = moveToDir;
		super.moveEntityWithHeading(p_70612_1_,p_70612_2_);
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
		if(this.ridingEntity instanceof EntityDummy_rider){
			if(!((EntityDummy_rider) this.ridingEntity).linkedBaseLogic.prefab_vehicle.T_Land_F_Plane)return true;
		}
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
	public boolean canEntityBeSeen(Entity p_70685_1_)
	{

		if(ridingEntity instanceof EntityDummy_rider){
			BaseLogic connected = ((EntityDummy_rider) ridingEntity).linkedBaseLogic;
			if(connected.seatInfos[((EntityDummy_rider) ridingEntity).linkedSeatID].prefab_seat.isBlindedSeat)return false;
		}

		Vec3 vec3 = Vec3.createVectorHelper(this.posX, this.posY + this.getEyeHeight(), this.posZ);
		Vec3 vec31 = Vec3.createVectorHelper(p_70685_1_.posX, p_70685_1_.posY + p_70685_1_.getEyeHeight(), p_70685_1_.posZ);

		MovingObjectPosition movingobjectposition = GunsUtils.getmovingobjectPosition_forBlock(worldObj,vec3, vec31, false, true, false);
		return movingobjectposition == null && canSeeTarget(p_70685_1_);
	}

	@Override
	public boolean canhearsound(Entity target) {
		boolean flag;
		double dist = getDistanceToEntity(target);
		flag = dist < target.getEntityData().getFloat("GunshotLevel") * 14;
		return flag;
	}

	@Override
	public AIAttackGun getAttackGun() {
		return aiAttackGun;
	}


	public boolean canuseAlreadyPlacedGun = true;
	public int placing;
	protected void collideWithNearbyEntities()
	{
		if(!worldObj.isRemote) {
			List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(0.20000000298023224D, 0.0D, 0.20000000298023224D));

			if (list != null && !list.isEmpty()) {
				for (int i = 0; i < list.size(); ++i) {
					Entity entity = (Entity) list.get(i);

					if (entity.canBePushed()) {
						this.collideWithEntity(entity);
					}

					if (this.ridingEntity == null && rideCool < 0 && !entity.isDead) {
						if (entity instanceof EntityVehicle && ((EntityVehicle) entity).canUseByMob) {
							Entity pilot = ((EntityVehicle) entity).getBaseLogic().getRiddenEntityList()[((EntityVehicle) entity).getpilotseatid()];
							if ((pilot == null || is_this_entity_friend(pilot)) && !((EntityVehicle) entity).getBaseLogic().isRidingEntity(this)) {
//                            System.out.println("" + );
								rideCool = 200;
								((EntityVehicle) entity).pickupEntity(this, seatID);
							}
							((EntityVehicle) entity).getBaseLogic().health += 0.01;
						}else {
							if(cfg_guerrillacanusePlacedGun && canuseAlreadyPlacedGun && !worldObj.isRemote && this.getAttackTarget() != null) {
								if (entity.riddenByEntity == null && entity instanceof PlacedGunEntity) {//TODO プレイヤー指示で搭乗するようにしたい
									placing++;
									if (placing > 60) {
										placing = 0;
										rideCool = 200;
										this.mountEntity((PlacedGunEntity) entity);
									}
									this.setCurrentItemOrArmor(0, null);
									break;
								}
							}
						}
					}
					if(entity instanceof EntityLivingBase && is_this_entity_friend(entity)){
						((EntityLivingBase) entity).heal(1f);
					}
				}
			}
		}
	}

	public int seatID = 0;
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


	protected void despawnEntity()
	{
		Event.Result result = null;
		if (this.isNoDespawnRequired())
		{
			this.entityAge = 0;
		}
		else if ((this.entityAge & 0x1F) == 0x1F && (result = ForgeEventFactory.canEntityDespawn(this)) != Event.Result.DEFAULT)
		{
			if (result == Event.Result.DENY || this.ridingEntity instanceof EntityDummy_rider)
			{
				this.entityAge = 0;
			}
			else
			{
				this.setDead();
			}
		}
		else
		{
			EntityPlayer entityplayer = this.worldObj.getClosestPlayerToEntity(this, -1.0D);

			if (entityplayer != null)
			{
				double d0 = entityplayer.posX - this.posX;
				double d1 = entityplayer.posY - this.posY;
				double d2 = entityplayer.posZ - this.posZ;
				double d3 = d0 * d0 + d1 * d1 + d2 * d2;

				if (this.canDespawn() && d3 > 16384.0D)
				{
					if(this.ridingEntity instanceof EntityDummy_rider){
						((EntityDummy_rider) this.ridingEntity).linkedBaseLogic.mc_Entity.despawnEntity();
					}
					this.setDead();
				}
			}
		}
	}



	PlatoonOBJ platoonOBJ;
    EntityAndPos myPos;

	@Override
	public void setPlatoon(PlatoonOBJ entities) {
		if(!this.isDead) {
			if(platoonOBJ != entities) {
				this.platoonOBJ = entities;
				this.platoonOBJ.addMember(this);
			}
		}
	}

	@Override
	public PlatoonOBJ getPlatoon() {
		return this.platoonOBJ;
	}

	@Override
	public void setPosObj(EntityAndPos entityAndPos) {
		myPos = entityAndPos;
	}

	@Override
	public double[] getTargetpos() {
		return new double[]{platoonOBJ.PlatoonTargetPos.x,platoonOBJ.PlatoonTargetPos.y,platoonOBJ.PlatoonTargetPos.z};
	}

	@Override
	public Vector3d getMoveToPos() {
		return myPos.getPos();
	}
}
