package handmadevehicle.entity;

import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import handmadeguns.entity.IFF;
import handmadeguns.entity.I_SPdamageHandle;
import handmadevehicle.AddNewVehicle;
import handmadevehicle.Items.ItemWrench;
import handmadevehicle.SlowPathFinder.ModifiedPathNavigater;
import handmadevehicle.SlowPathFinder.WorldForPathfind;
import handmadevehicle.entity.parts.*;
import handmadevehicle.entity.parts.logics.BaseLogic;
import handmadevehicle.entity.parts.turrets.TurretObj;
import handmadevehicle.entity.prefab.DropItemData;
import handmadevehicle.entity.prefab.Prefab_Vehicle_Base;
import handmadevehicle.network.HMVPacketHandler;
import handmadevehicle.network.packets.HMVPacketPickNewEntity;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import javax.vecmath.Vector3d;

import static cpw.mods.fml.common.network.ByteBufUtils.readTag;
import static cpw.mods.fml.common.network.ByteBufUtils.writeTag;
import static handmadeguns.HandmadeGunsCore.HMG_proxy;
import static handmadeguns.Util.GunsUtils.getmovingobjectPosition_forBlock;
import static handmadevehicle.HMVehicle.HMV_Proxy;
import static handmadevehicle.HMVehicle.itemWrench;
import static handmadevehicle.Utils.*;
import static java.lang.Integer.parseInt;
import static java.lang.Math.abs;

public class EntityVehicle extends Entity implements IFF,IVehicle,IMultiTurretVehicle,IEntityAdditionalSpawnData, I_SPdamageHandle ,IhasMoveHelper {
	public String typename;
	
	public int deathTicks;
	

	public double movespeed = 0.3d;
	private WorldForPathfind worldForPathfind;
	private ModifiedPathNavigater modifiedPathNavigater;
	public boolean canUseByMob = false;
	public boolean canDespawn = false;

	BaseLogic baseLogic;
	MoveHelperForVehicle moveHelperForVehicle;
	ModifiedBoundingBox nboundingbox;
	
	public EntityVehicle(World par1World) {
		super(par1World);
		this.modifiedPathNavigater = new ModifiedPathNavigater(this, worldObj,worldForPathfind = new WorldForPathfind(worldObj),64);
		//AI入りはこのmodでは実装しない！良いな！

		renderDistanceWeight = Double.MAX_VALUE;
		if(this.worldObj instanceof WorldServer) {
			EntityTracker entitytracker = ((WorldServer) this.worldObj).getEntityTracker();
			ObfuscationReflectionHelper.setPrivateValue(EntityTracker.class, entitytracker, 1048576, "entityViewDistance", "E", "field_72792_d");
		}
	}
	
	@Override
	protected void entityInit() {
	
	}
	
	public EntityVehicle(World par1World,String typename) {
		this(par1World);
		this.init_2(typename);
	}
	static public EntityVehicle EntityVehicle_spawnByMob(World par1World,String typename) {
		EntityVehicle bespawningEntity = new EntityVehicle(par1World,typename);
		bespawningEntity.canUseByMob = true;
		bespawningEntity.canDespawn = true;
		return bespawningEntity;
	}
	public void init_2(String typename) {
		this.typename = typename;
		
		ignoreFrustumCheck = true;
		baseLogic = new BaseLogic(worldObj, this);
		this.moveHelperForVehicle = new MoveHelperForVehicle(this,baseLogic);
		Prefab_Vehicle_Base infos = AddNewVehicle.seachInfo(typename);
		baseLogic.setinfo(infos);
		nboundingbox = new ModifiedBoundingBox(boundingBox.minX, boundingBox.minY, boundingBox.minZ, boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ,
				0, 1.5, 0,
				3.4, 3, 6.5);
		nboundingbox.rot.set(baseLogic.bodyRot);
		nboundingbox.centerRotX = baseLogic.prefab_vehicle.rotcenter[0];
		nboundingbox.centerRotY = baseLogic.prefab_vehicle.rotcenter[1];
		nboundingbox.centerRotZ = baseLogic.prefab_vehicle.rotcenter[2];
		if(infos.boxes != null){
			nboundingbox.boxes = new OBB[infos.boxes.length];
			int cnt = 0;
			for(OBBInfo aobb:infos.boxes){
				nboundingbox.boxes[cnt] = new OBB();
				nboundingbox.boxes[cnt].info = aobb;
//				System.out.println("" + nboundingbox.boxes[cnt].toString());
				cnt++;
			}
		}
		nboundingbox.calculateMax_And_Min();
		HMV_Proxy.replaceBoundingbox(this,nboundingbox);
//		this.applyEntityAttributes2();
		this.setSize(baseLogic.prefab_vehicle.boundingBoxSizeX, baseLogic.prefab_vehicle.boundingBoxSizeY);
		this.width = baseLogic.prefab_vehicle.boundingBoxSizeX;
	}
	public ModifiedPathNavigater getNavigator()
	{
		return this.modifiedPathNavigater;
	}
	
	public double getMountedYOffset() {
		return 0.0D;
	}


	public float getEyeHeight()
	{
		return this.height/2;
	}

	public int chunkCheckCNT = 20;
	public void onUpdate() {
		if(worldObj.isRemote && HMG_proxy.getEntityPlayerInstance() != null && HMG_proxy.getEntityPlayerInstance().isDead){
			this.setDead();
		}
		modifiedPathNavigater.onUpdateNavigation();
		moveHelperForVehicle.onUpdateMoveHelper();
		boolean onground = this.onGround;
		double backupMotionX = this.motionX;
		double backupMotionZ = this.motionY;
		double backupMotionY = this.motionZ;
		Vector3d backupPos = new Vector3d(
				this.posX,
				this.posY,
				this.posZ
		);
		super.onUpdate();
		this.motionX = backupMotionX;
		this.motionY = backupMotionZ;
		this.motionZ = backupMotionY;
		this.posX = backupPos.x;
		this.posY = backupPos.y;
		this.posZ = backupPos.z;
		this.prevPosX = backupPos.x;
		this.prevPosY = backupPos.y;
		this.prevPosZ = backupPos.z;

		this.onGround = onground;
		baseLogic.onUpdate();

		if(baseLogic.health<0)onDeathUpdate();
		if (!this.worldObj.isRemote)
		{
			this.collideWithNearbyEntities();
		}
		despawnEntity();
	}
	public void setLocationAndAngles(double p_70012_1_, double p_70012_3_, double p_70012_5_, float p_70012_7_, float p_70012_8_)
	{
		this.prevPosX = this.posX = p_70012_1_;
		this.prevPosY = this.posY = p_70012_3_ + (double)this.yOffset;
		this.prevPosZ = this.posZ = p_70012_5_;
		this.rotationYaw = p_70012_7_;
		this.rotationPitch = p_70012_8_;
		this.setPosition(this.posX, this.posY, this.posZ);
	}

	@SideOnly(Side.CLIENT)
	public void setPositionAndRotation2(double p_70056_1_, double p_70056_3_, double p_70056_5_, float p_70056_7_, float p_70056_8_, int p_70056_9_)
	{
		this.setPosition(posX, posY, posZ);
	}

	public void setVelocity(double p_70016_1_, double p_70016_3_, double p_70016_5_) {
//		baseLogic.setVelocity(p_70016_1_,p_70016_3_,p_70016_5_);
	}
	
	@Override
	public BaseLogic getBaseLogic() {
		return baseLogic;
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
	
	
	
	
	
	
	
	
	public boolean canAttackClass(Class par1Class) {
		return EntityCreature.class != par1Class;
	}

//	public void setAttackTarget(EntityLivingBase p_70624_1_)
//	{
//		if(mode == 3 || mode == 4){
//			super.setAttackTarget(null);
//		}else super.setAttackTarget(p_70624_1_);
//	}
	
	public int getVerticalFaceSpeed()
	{
		return 90;
	}
	
	public boolean isAIEnabled() {
		return true;
	}
	
	protected boolean canDespawn()
	{
		return canDespawn && rider_canDespawn();
	}
	protected boolean rider_canDespawn(){
		for(Entity entity:baseLogic.riddenByEntities){
			if(entity != null)return false;
		}
		return true;
	}
	
	
	public boolean attackEntityFrom_with_Info(MovingObjectPosition movingObjectPosition, DamageSource source, float level){
		BaseLogic temp;
		float temparomor = 0;
		if (source.getEntity() != null) {
			temp = ((HasBaseLogic) this).getBaseLogic();
//			double angle_position = abs(toDegrees(TankFrontVec.angle(shooterPositionVec)));
			Vector3d TankRighttVec = new Vector3d(-1, 0, 0);
			TankRighttVec = transformVecByQuat(TankRighttVec, temp.bodyRot);
			TankRighttVec.z *= -1;
			int boxId = movingObjectPosition.sideHit / 6;
			OBB box = nboundingbox.boxes[boxId];
			int hitside = movingObjectPosition.sideHit % 6;
			double angle_sin = 0;
			Vector3d Incident_vector = new Vector3d();
			Incident_vector.normalize((Vector3d) movingObjectPosition.hitInfo);
			switch (hitside) {
				case 2: //正面
//					System.out.println("front");
					angle_sin = abs(Incident_vector.z);
					temparomor = box.info.armor_Front;
					break;
				case 4://ヒダリ
//					System.out.println("left");
					angle_sin = abs(Incident_vector.x);
					temparomor = box.info.armor_SideLeft;
					break;
				case 5://ミギ
//					System.out.println("right");
					angle_sin = abs(Incident_vector.x);
					temparomor = box.info.armor_SideRight;
					break;
				case 0://上
//					System.out.println("top");
					angle_sin = abs(Incident_vector.y);
					temparomor = box.info.armor_Top;
					break;
				case 1://下
//					System.out.println("bottom");
					angle_sin = abs(Incident_vector.y);
					temparomor = box.info.armor_Bottom;
					break;
				case 3: //背面
//					System.out.println("back");
					angle_sin = abs(Incident_vector.z);
					temparomor = box.info.armor_Back;
					break;
			}
//			System.out.println("angle_sin" + angle_sin);
			temparomor /=angle_sin;
//			System.out.println("temparomor" + temparomor);
			if (level <= temparomor) {
				if (!source.getDamageType().equals("mob"))
					this.playSound("gvcmob:gvcmob.ArmorBounce", 0.5F, 1/(level / temparomor));
			}else this.playSound("gvcmob:gvcmob.armorhit",5, 1F);
			level -= temparomor;
			if(level<0)level = 0;
		}
		return this.attackEntityFrom(source,level);
	}
	public int invalidateCnt = 0;
	public void despawnEntity()
	{
		if (!worldObj.isRemote && worldObj instanceof WorldServer)
		{
			EntityPlayer entityplayer = this.worldObj.getClosestPlayerToEntity(this, -1.0D);
			if(entityplayer != null) {
				double d0 = entityplayer.posX - this.posX;
				double d1 = entityplayer.posY - this.posY;
				double d2 = entityplayer.posZ - this.posZ;
				double d3 = d0 * d0 + d1 * d1 + d2 * d2;
				if (this.canDespawn() && (!this.baseLogic.prefab_vehicle.T_Land_F_Plane || invalidateCnt++ > 1200 || (d3 > ((WorldServer)worldObj).func_73046_m().getConfigurationManager().getViewDistance()*16 *
						((WorldServer)worldObj).func_73046_m().getConfigurationManager().getViewDistance()*16))) {
//					System.out.println("debug");

					noDrop = true;
					this.setDead();
				}
			}
		}
	}

	public boolean attackEntityFrom(DamageSource source, float par2) {
		if(baseLogic!= null){
			if(baseLogic.riddenByEntities[baseLogic.getpilotseatid()] != null){
				baseLogic.riddenByEntities[baseLogic.getpilotseatid()].attackEntityFrom(source,0);
			}
		}
		if(source.getDamageType().equals(DamageSource.fall.damageType) ||
				   source.getDamageType().equals(DamageSource.outOfWorld.damageType) ||
				   source.getDamageType().equals(DamageSource.inWall.damageType))return attackEntityFrom_exceptArmor(source, par2);

		if(source.isExplosion()){
			par2 *= baseLogic.prefab_vehicle.antiExplosionCof;
		}
		if(source.getEntity() != null && source.getDamageType().equals("player")){
			if(baseLogic.isRidingEntity(source.getEntity())) par2 = 0;
			else if (!worldObj.isRemote && ((EntityPlayer)source.getSourceOfDamage()).getHeldItem() != null &&
					((EntityPlayer)source.getSourceOfDamage()).getHeldItem().getItem() == itemWrench){

				Entity pilot = baseLogic.riddenByEntities[baseLogic.getpilotseatid()];
				if(baseLogic.health == baseLogic.prefab_vehicle.maxhealth) {
					Item check = GameRegistry.findItem("HMVehicle", typename);
					if (check != null) {
						if (pilot instanceof EntityPlayer) {
							if (((EntityPlayer) pilot).isOnSameTeam((EntityLivingBase) source.getEntity())) {
								noDrop = true;
								this.setDead();
								this.dropItem(check, 1);

								for (int i = 0; i < baseLogic.inventoryVehicle.items.length; ++i) {
									if (baseLogic.inventoryVehicle.items[i] != null) {
										this.func_146097_a(baseLogic.inventoryVehicle.items[i], true, false);
										baseLogic.inventoryVehicle.items[i] = null;
									}
								}
							}
						} else if (pilot == null) {
							noDrop = true;
							this.setDead();
							this.dropItem(check, 1);

							for (int i = 0; i < baseLogic.inventoryVehicle.items.length; ++i) {
								if (baseLogic.inventoryVehicle.items[i] != null) {
									this.func_146097_a(baseLogic.inventoryVehicle.items[i], true, false);
									baseLogic.inventoryVehicle.items[i] = null;
								}
							}
						}
					}
				}
				if(!(pilot instanceof EntityPlayer) && (!(pilot instanceof IFF) || ((IFF) pilot).is_this_entity_friend(source.getEntity()))){
					Entity[] entitylist = baseLogic.riddenByEntities;
					for (Entity picked : entitylist) {
						if(picked != null){
							baseLogic.disMountEntity(picked);
							System.out.println("-----------------------------------");

							System.out.println("debug           " + picked);
							System.out.println("debug   riding  " + picked.ridingEntity);
						}
					}
					HMVPacketHandler.INSTANCE.sendToAll(new HMVPacketPickNewEntity(baseLogic.mc_Entity.getEntityId(), baseLogic.riddenByEntities));
				}
			}
		}

		if(par2 < 0)par2 = 0;

		baseLogic.health -= par2;
		return true;
	}

	public void mountEntity(Entity p_70078_1_){
		pickupEntity(p_70078_1_,0);
	}
	public void heal(float par2){
		if(deathTicks != 0)return;
		baseLogic.health += par2;
		if(baseLogic.health > baseLogic.prefab_vehicle.maxhealth)baseLogic.health = baseLogic.prefab_vehicle.maxhealth;
	}
	public boolean attackEntityFrom_exceptArmor(DamageSource source, float par2){
		baseLogic.health -= par2;
		return super.attackEntityFrom(source, par2);
	}
	public void addVelocity(double p_70024_1_, double p_70024_3_, double p_70024_5_)
	{
		this.motionX += p_70024_1_*baseLogic.prefab_vehicle.antiExplosionCof;
		this.motionY += p_70024_3_*baseLogic.prefab_vehicle.antiExplosionCof;
		this.motionZ += p_70024_5_*baseLogic.prefab_vehicle.antiExplosionCof;
		this.isAirBorne = true;
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
	
	public boolean canEntityBeSeen(Entity p_70685_1_)
	{
		Vec3 startpos = Vec3.createVectorHelper(this.posX, this.posY + (double) this.getEyeHeight(), this.posZ);
		Vec3 targetpos = Vec3.createVectorHelper(p_70685_1_.posX, p_70685_1_.posY + (double) p_70685_1_.getEyeHeight(), p_70685_1_.posZ);
		MovingObjectPosition movingobjectposition = getmovingobjectPosition_forBlock(worldObj,startpos, targetpos);
		if(movingobjectposition!=null) {
			return false;
		}
		return !((this.isInWater() || p_70685_1_.isInWater()) && getDistanceSqToEntity(p_70685_1_) > 256);
	}
	/**
	 * returns a (normalized) vector of where this entity is looking
	 */
//	public Vec3 getLookVec()
//	{
//		return this.getLook(1.0F);
//	}
	
	public boolean interactFirst(EntityPlayer p_70085_1_) {
		if(p_70085_1_.getHeldItem() != null &&
				p_70085_1_.getHeldItem().getItem() instanceof ItemWrench){
			if (((ItemWrench) p_70085_1_.getHeldItem().getItem()).itemInteractionForEntity2(p_70085_1_.getHeldItem(),p_70085_1_,this))
			{
			}
		}
		if (!this.worldObj.isRemote && (this.riddenByEntity == null || this.riddenByEntity != p_70085_1_)) {
			if(!p_70085_1_.isSneaking()&&!p_70085_1_.isRiding() && !((baseLogic.riddenByEntities[baseLogic.getpilotseatid()] instanceof EntityPlayer) && ((EntityPlayer)baseLogic.riddenByEntities[baseLogic.getpilotseatid()]).isOnSameTeam(p_70085_1_))){
				pickupEntity(p_70085_1_,0);
			}
			return true;
		} else {
			return false;
		}
	}
	public boolean pickupEntity(Entity p_70085_1_, int StartSeachSeatNum){
		return this.getBaseLogic().pickupEntity(p_70085_1_,StartSeachSeatNum);
	}

//	public Vec3 getLook(float p_70676_1_)
//	{
//		float f1;
//		float f2;
//		float f3;
//		float f4;
//
//		if (p_70676_1_ == 1.0F)
//		{
//			f1 = MathHelper.cos(-this.rotationYawHead * 0.017453292F - (float)Math.PI);
//			f2 = MathHelper.sin(-this.rotationYawHead * 0.017453292F - (float)Math.PI);
//			f3 = -MathHelper.cos(-this.rotationPitch * 0.017453292F);
//			f4 = MathHelper.sin(-this.rotationPitch * 0.017453292F);
//			return Vec3.createVectorHelper((double)(f2 * f3), (double)f4, (double)(f1 * f3));
//		}
//		else
//		{
//			f1 = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * p_70676_1_;
//			f2 = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * p_70676_1_;
//			f3 = MathHelper.cos(-f2 * 0.017453292F - (float)Math.PI);
//			f4 = MathHelper.sin(-f2 * 0.017453292F - (float)Math.PI);
//			float f5 = -MathHelper.cos(-f1 * 0.017453292F);
//			float f6 = MathHelper.sin(-f1 * 0.017453292F);
//			return Vec3.createVectorHelper((double)(f4 * f5), (double)f6, (double)(f3 * f5));
//		}
//	}
//	public void moveFlying(float p_70060_1_, float p_70060_2_, float p_70060_3_)
//	{
//		float f3 = p_70060_1_ * p_70060_1_ + p_70060_2_ * p_70060_2_;
//
//		if (f3 >= 1.0E-4F)
//		{
//			f3 = MathHelper.sqrt_float(f3);
//
//			if (f3 < 1.0F)
//			{
//				f3 = 1.0F;
//			}
//			f3 = p_70060_3_ / f3;
//			f3 = abs(f3);
//			p_70060_1_ *= f3;
//			p_70060_2_ *= f3;
//			float f4 = MathHelper.sin(this.rotationYaw * (float)Math.PI / 180.0F);
//			float f5 = MathHelper.cos(this.rotationYaw * (float)Math.PI / 180.0F);
//			this.motionX += (double)(-p_70060_2_ * f4 + p_70060_1_ * f5);
//			this.motionZ += (double)( p_70060_2_ * f5 + p_70060_1_ * f4);
//		}
//	}
	
	
//	public void onLivingUpdate()
//	{
//		this.updateArmSwingProgress();
//		float f = this.getBrightness(1.0F);
//
//		if (f > 0.5F)
//		{
//			this.entityAge += 2;
//		}
//
//		super.onLivingUpdate();
//	}
	
	public void readEntityFromNBT(NBTTagCompound p_70037_1_)
	{
		typename = p_70037_1_.getString("typename");
		canUseByMob = p_70037_1_.getBoolean("canUseByMob");
		canDespawn = p_70037_1_.getBoolean("despawn");
		init_2(typename);
//		super.readEntityFromNBT(p_70037_1_);
		baseLogic.readFromTag(p_70037_1_);
	}
	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	public void writeEntityToNBT(NBTTagCompound p_70014_1_)
	{
//		super.writeEntityToNBT(p_70014_1_);
		p_70014_1_.setString("typename",typename);
		p_70014_1_.setBoolean("canUseByMob",canUseByMob);
		p_70014_1_.setBoolean("despawn", canDespawn);
		baseLogic.saveToTag(p_70014_1_);
	}
	public boolean writeMountToNBT(NBTTagCompound p_98035_1_)
	{
		return false;
	}
	
	@Override
	public void writeSpawnData(ByteBuf buffer){
		writeEntityToNBT(this.getEntityData());
		writeTag(buffer, this.getEntityData());
	}
	
	@Override
	public void readSpawnData(ByteBuf additionalData){
		readEntityFromNBT(readTag(additionalData));
	}
	public boolean shouldDismountInWater(Entity entity){
		return false;
	}
	@Override
	public boolean is_this_entity_friend(Entity entity){
		return false;
	}

	public boolean standalone(){
		return false;
	}
	@SideOnly(Side.CLIENT)
	@Override
	public boolean isInRangeToRenderDist(double p_70112_1_)
	{
		return true;
	}
	@SideOnly(Side.CLIENT)
	@Override
	public boolean isInRangeToRender3d(double p_145770_1_, double p_145770_3_, double p_145770_5_)
	{
		return true;
	}
	
//	@Override
//	protected String getLivingSound()
//	{
//		return null;
//	}
	protected String getHurtSound()
	{
		return null;
	}
	
	@Override
	public void yourSoundIsremain(String playingSound) {
		getBaseLogic().yourSoundIsremain(playingSound);
	}
	
	/**
	 * Returns the sound this mob makes on death.
	 */
	protected String getDeathSound()
	{
		return null;
	}
	
	protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_)
	{
//		this.playSound("mob.irongolem.walk", 1.0F, 1.0F);
	}
	
	public void setPosition(double x, double y, double z)
	{
		super.setPosition(x,y,z);
		if(getBaseLogic() != null)getBaseLogic().setPosition(x,y,z);
	}
	
	protected void onDeathUpdate() {
		++this.deathTicks;
		if(baseLogic.prefab_vehicle.deathType == 0)setDead();
		if(baseLogic.prefab_vehicle.deathType == 1) {
			if (this.deathTicks == 3) {
				//this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 0F, false);
				ExplodeEffect ex = new ExplodeEffect(this, 3F);
				ex.offset[0] = (float) (rand.nextInt(30) - 15) / 10;
				ex.offset[1] = (float) (rand.nextInt(30) - 15) / 10 + 1.5f;
				ex.offset[2] = (float) (rand.nextInt(30) - 15) / 10;
				ex.Ex();

			}
			if (this.deathTicks > 40) {
				if (worldObj.isRemote) {
					for (int i = 0; i < 5; i++) {
						worldObj.spawnParticle("flame",
								this.posX + (float) (rand.nextInt(20) - 10) / 10,
								this.posY + (float) (rand.nextInt(20) - 10) / 10 + 1.5f,
								this.posZ + (float) (rand.nextInt(20) - 10) / 10,
								0.0D, 0.5D, 0.0D);
						worldObj.spawnParticle("smoke",
								this.posX + (float) (rand.nextInt(30) - 15) / 10,
								this.posY + (float) (rand.nextInt(30) - 15) / 10 + 1.5f,
								this.posZ + (float) (rand.nextInt(30) - 15) / 10,
								0.0D, 0.2D, 0.0D);
						worldObj.spawnParticle("cloud",
								this.posX + (float) (rand.nextInt(30) - 15) / 10,
								this.posY + (float) (rand.nextInt(30) - 15) / 10 + 1.5f,
								this.posZ + (float) (rand.nextInt(30) - 15) / 10,
								0.0D, 0.3D, 0.0D);
					}
				}
//			this.playSound("handmadeguns:handmadeguns.fireee", 1.20F, 0.8F);
			} else if (rand.nextInt(3) == 0) {
				ExplodeEffect ex = new ExplodeEffect(this, 1F);
				ex.offset[0] = (float) (rand.nextInt(30) - 15) / 10;
				ex.offset[1] = (float) (rand.nextInt(30) - 15) / 10;
				ex.offset[2] = (float) (rand.nextInt(30) - 15) / 10;
				ex.Ex();

			}
			if (this.deathTicks >= 140) {
				ExplodeEffect ex = new ExplodeEffect(this, 8F);
				ex.Ex();
				for (int i = 0; i < 15; i++) {
					worldObj.spawnParticle("flame",
							this.posX + (float) (rand.nextInt(20) - 10) / 10,
							this.posY + (float) (rand.nextInt(20) - 10) / 10,
							this.posZ + (float) (rand.nextInt(20) - 10) / 10,
							(rand.nextInt(20) - 10) / 100f,
							(rand.nextInt(20) - 10) / 100f,
							(rand.nextInt(20) - 10) / 100f);
					worldObj.spawnParticle("smoke",
							this.posX + (float) (rand.nextInt(30) - 15) / 10,
							this.posY + (float) (rand.nextInt(30) - 15) / 10,
							this.posZ + (float) (rand.nextInt(30) - 15) / 10,
							(rand.nextInt(20) - 10) / 100f,
							(rand.nextInt(20) - 10) / 100f,
							(rand.nextInt(20) - 10) / 100f);
					worldObj.spawnParticle("cloud",
							this.posX + (float) (rand.nextInt(30) - 15) / 10,
							this.posY + (float) (rand.nextInt(30) - 15) / 10,
							this.posZ + (float) (rand.nextInt(30) - 15) / 10,
							(rand.nextInt(20) - 10) / 100f,
							(rand.nextInt(20) - 10) / 100f,
							(rand.nextInt(20) - 10) / 100f);
				}
				if (this.deathTicks == 150)
					this.setDead();
			}
		}else if(baseLogic.prefab_vehicle.deathType == 2) {
			if (this.deathTicks == 3) {
				//this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 0F, false);
				ExplodeEffect ex = new ExplodeEffect(this, 3F);
				ex.offset[0] = (float) (rand.nextInt(30) - 15) / 10;
				ex.offset[1] = (float) (rand.nextInt(30) - 15) / 10 + 1.5f;
				ex.offset[2] = (float) (rand.nextInt(30) - 15) / 10;
				ex.Ex();

			}
			if (this.deathTicks > 40) {
				if (worldObj.isRemote) {
					for (int i = 0; i < 5; i++) {
						worldObj.spawnParticle("flame",
								this.posX + (float) (rand.nextInt(20) - 10) / 10,
								this.posY + (float) (rand.nextInt(20) - 10) / 10 + 1.5f,
								this.posZ + (float) (rand.nextInt(20) - 10) / 10,
								0.0D, 0.5D, 0.0D);
						worldObj.spawnParticle("smoke",
								this.posX + (float) (rand.nextInt(30) - 15) / 10,
								this.posY + (float) (rand.nextInt(30) - 15) / 10 + 1.5f,
								this.posZ + (float) (rand.nextInt(30) - 15) / 10,
								0.0D, 0.2D, 0.0D);
						worldObj.spawnParticle("cloud",
								this.posX + (float) (rand.nextInt(30) - 15) / 10,
								this.posY + (float) (rand.nextInt(30) - 15) / 10 + 1.5f,
								this.posZ + (float) (rand.nextInt(30) - 15) / 10,
								0.0D, 0.3D, 0.0D);
					}
				}
//			this.playSound("handmadeguns:handmadeguns.fireee", 1.20F, 0.8F);
			}
			if (this.deathTicks >= 140) {
				ExplodeEffect ex = new ExplodeEffect(this, 8F);
				ex.Ex();
				for (int i = 0; i < 15; i++) {
					worldObj.spawnParticle("flame",
							this.posX + (float) (rand.nextInt(20) - 10) / 10,
							this.posY + (float) (rand.nextInt(20) - 10) / 10,
							this.posZ + (float) (rand.nextInt(20) - 10) / 10,
							(rand.nextInt(20) - 10) / 100f,
							(rand.nextInt(20) - 10) / 100f,
							(rand.nextInt(20) - 10) / 100f);
					worldObj.spawnParticle("smoke",
							this.posX + (float) (rand.nextInt(30) - 15) / 10,
							this.posY + (float) (rand.nextInt(30) - 15) / 10,
							this.posZ + (float) (rand.nextInt(30) - 15) / 10,
							(rand.nextInt(20) - 10) / 100f,
							(rand.nextInt(20) - 10) / 100f,
							(rand.nextInt(20) - 10) / 100f);
					worldObj.spawnParticle("cloud",
							this.posX + (float) (rand.nextInt(30) - 15) / 10,
							this.posY + (float) (rand.nextInt(30) - 15) / 10,
							this.posZ + (float) (rand.nextInt(30) - 15) / 10,
							(rand.nextInt(20) - 10) / 100f,
							(rand.nextInt(20) - 10) / 100f,
							(rand.nextInt(20) - 10) / 100f);
				}
				if (this.deathTicks == 150)
					this.setDead();
			}
		}
	}

	/**
	 * Will get destroyed next tick.
	 */
	public boolean noDrop = false;
	public void setDead()
	{

		int i;

		if(!worldObj.isRemote && !noDrop) {
			for (i = 0; i < baseLogic.inventoryVehicle.items.length; ++i) {
				if (baseLogic.inventoryVehicle.items[i] != null) {
					this.func_146097_a(baseLogic.inventoryVehicle.items[i], true, false);
					baseLogic.inventoryVehicle.items[i] = null;
				}
			}

			if (!baseLogic.prefab_vehicle.dropItems.isEmpty())
				for (DropItemData dropItemData : baseLogic.prefab_vehicle.dropItems) {
					if (dropItemData.rate > rand.nextFloat()) {
						String[] itemids = dropItemData.name.split(":");
						ItemStack willDrop = null;
						if (itemids.length == 2) {
							Item willDropItem = GameRegistry.findItem(itemids[0], itemids[1]);
							if (willDropItem == null) {
								willDrop = new ItemStack(GameRegistry.findBlock(itemids[0], itemids[1]), dropItemData.num);
							} else {
								willDrop = new ItemStack(willDropItem, dropItemData.num);
							}
						} else if (itemids.length == 3) {
							Item willDropItem = GameRegistry.findItem(itemids[0], itemids[1]);
							if (willDropItem != null)
								willDrop = new ItemStack(willDropItem, dropItemData.num, parseInt(itemids[2]));
							else
								willDrop = new ItemStack(GameRegistry.findBlock(itemids[0], itemids[1]), dropItemData.num, parseInt(itemids[2]));
						}
						if (willDrop != null) {
							EntityItem entityItem = new EntityItem(worldObj, this.posX, this.posY, this.posZ, willDrop);
							worldObj.spawnEntityInWorld(entityItem);
						}
					}
				}
		}


		this.isDead = true;
	}

	public void func_146097_a(ItemStack p_146097_1_, boolean p_146097_2_, boolean p_146097_3_)
	{
		if (p_146097_1_ == null)
		{
		}
		else if (p_146097_1_.stackSize == 0)
		{
		}
		else
		{
			EntityItem entityitem = new EntityItem(this.worldObj, this.posX, this.posY - 0.30000001192092896D + (double)this.getEyeHeight(), this.posZ, p_146097_1_);
			entityitem.delayBeforeCanPickup = 40;

			if (p_146097_3_)
			{
				entityitem.func_145799_b(this.getCommandSenderName());
			}

			float f = 0.1F;
			float f1;

			if (p_146097_2_)
			{
				f1 = this.rand.nextFloat() * 0.5F;
				float f2 = this.rand.nextFloat() * (float)Math.PI * 2.0F;
				entityitem.motionX = (double)(-MathHelper.sin(f2) * f1);
				entityitem.motionZ = (double)(MathHelper.cos(f2) * f1);
				entityitem.motionY = 0.20000000298023224D;
			}
			else
			{
				f = 0.3F;
				entityitem.motionX = (double)(-MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * f);
				entityitem.motionZ = (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * f);
				entityitem.motionY = (double)(-MathHelper.sin(this.rotationPitch / 180.0F * (float)Math.PI) * f + 0.1F);
				f = 0.02F;
				f1 = this.rand.nextFloat() * (float)Math.PI * 2.0F;
				f *= this.rand.nextFloat();
				entityitem.motionX += Math.cos((double)f1) * (double)f;
				entityitem.motionY += (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F);
				entityitem.motionZ += Math.sin((double)f1) * (double)f;
			}

			this.joinEntityItemWithWorld(entityitem);
		}
	}
	public void joinEntityItemWithWorld(EntityItem p_71012_1_)
	{
		if (captureDrops)
		{
			capturedDrops.add(p_71012_1_);
			return;
		}
		this.worldObj.spawnEntityInWorld(p_71012_1_);
	}


	public void updateRiderPosition()
	{
	}
	@Override
	public TurretObj[] getmainTurrets() {
		return null;
	}
	
	@Override
	public TurretObj[] getsubTurrets() {
		return null;
	}
	
	@Override
	public TurretObj[] getTurrets() {
		return getBaseLogic().turrets;
	}
	
	@Override
	public void applyEntityCollision(Entity p_70108_1_)
	{
		getBaseLogic().applyEntityCollision(p_70108_1_);
	}
	
//	@Override
//	public void moveEntityWithHeading(float p_70612_1_, float p_70612_2_)
//	{
//		getBaseLogic().moveEntityWithHeading(p_70612_1_,p_70612_2_);
//	}
	
	@Override
	public boolean handleWaterMovement(){
		return false;
	}
	
	@Override
	public void moveEntity(double x, double y, double z){
		getBaseLogic().moveEntity(x,y,z);
	}
	
	@Override
	public void updateFallState_public(double stepHeight, boolean onground){
		this.updateFallState(stepHeight,onground);
	}
	
	@Override
	public void func_145775_I_public() {
		this.func_145775_I();
	}
	
	@Override
	public boolean getinWater() {
		return inWater;
	}
	
	@Override
	public void setinWater(boolean value) {
		inWater = value;
	}

	protected void collideWithNearbyEntities(){
		getBaseLogic().collideWithNearbyEntities();
	}
	@Override
	public void public_collideWithEntity(Entity entity) {
		entity.applyEntityCollision(this);
	}
	
	public boolean shouldRenderInPass(int pass)
	{
		return pass == 0 || pass == 1;
	}
	@Override
	public boolean canBeCollidedWith()
	{
		return true;
	}
	public boolean checkObstacle()
	{
		return this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty() && !this.worldObj.isAnyLiquid(this.boundingBox);
	}

	@Override
	public MoveHelperForVehicle getmoveHelper() {
		return moveHelperForVehicle;
	}
}
