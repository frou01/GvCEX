package handmadevehicle.entity;

import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import handmadeguns.entity.IFF;
import handmadevehicle.AddNewVehicle;
import handmadevehicle.entity.parts.*;
import handmadevehicle.entity.parts.logics.BaseLogic;
import handmadevehicle.entity.parts.turrets.TurretObj;
import handmadevehicle.entity.prefab.Prefab_Vehicle_Base;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import javax.vecmath.Vector3d;

import static handmadeguns.Util.Utils.getmovingobjectPosition_forBlock;
import static handmadevehicle.HMVehicle.HMV_Proxy;
import static handmadevehicle.Utils.*;
import static handmadevehicle.Utils.transformVecByQuat;
import static java.lang.Math.abs;
import static java.lang.Math.sin;

public class EntityVehicle extends EntityCreature implements IFF,INpc,IVehicle,IMultiTurretVehicle,IEntityAdditionalSpawnData {
	public String typename;
	
	public int deathTicks;
	
	
	public float armor = 0;
	public float armor_tilt = 0;
	public float armor_Top_cof = 0.2f;
	public float armor_Front_cof = 1f;
	public float armor_Back_cof = 0.5f;
	public float armor_Side_cof = 0.7f;
	
	
	public boolean server1 = false;
	public boolean server2 = false;
	public boolean serverx = false;
	
	public double movespeed = 0.3d;
	public EntityAISwimming aiSwimming;
	public EntityAIOpenDoor AIOpenDoor;
	public Entity master;
	
	BaseLogic baseLogic;
	ModifiedBoundingBox nboundingbox;
	public EntityVehicle(World par1World) {
		super(par1World);
		this.getNavigator().setBreakDoors(true);
		//AI入りはこのmodでは実装しない！良いな！
		
		renderDistanceWeight = 16384;
		if(this.worldObj instanceof WorldServer) {
			EntityTracker entitytracker = ((WorldServer) this.worldObj).getEntityTracker();
			ObfuscationReflectionHelper.setPrivateValue(EntityTracker.class, entitytracker, 1024, "entityViewDistance", "E", "field_72792_d");
		}
		this.setSize(3f, 3f);
	}
	public EntityVehicle(World par1World,String typename) {
		super(par1World);
		this.init_2(typename);
		this.setSize(3f, 3f);
	}
	public void init_2(String typename) {
		this.typename = typename;
		
		ignoreFrustumCheck = true;
		baseLogic = new BaseLogic(worldObj, this);
		Prefab_Vehicle_Base infos = AddNewVehicle.seachInfo(typename);
		baseLogic.setinfo(infos);
		nboundingbox = new ModifiedBoundingBox(boundingBox.minX, boundingBox.minY, boundingBox.minZ, boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ,
				0, 1.5, 0,
				3.4, 3, 6.5);
		nboundingbox.calculateMax_And_Min();
		nboundingbox.rot.set(baseLogic.bodyRot);
		nboundingbox.centerRotX = baseLogic.info.rotcenter[0];
		nboundingbox.centerRotY = baseLogic.info.rotcenter[1];
		nboundingbox.centerRotZ = baseLogic.info.rotcenter[2];
		nboundingbox.boxes = infos.boxes;
		HMV_Proxy.replaceBoundingbox(this,nboundingbox);
		this.applyEntityAttributes2();
		this.setHealth(this.getMaxHealth());
	}
	
	public double getMountedYOffset() {
		return 0.6D;
	}
	
	
	public void onUpdate() {
		boolean onground = this.onGround;
		this.lastTickPosX = this.posX;
		this.lastTickPosY = this.posY;
		this.lastTickPosZ = this.posZ;
		
		
		super.onUpdate();
		this.onGround = onground;
		baseLogic.onUpdate();
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
		return true;
	}
	
	public void moveFlying(float p_70060_1_, float p_70060_2_, float p_70060_3_) {
	}
	
	public void jump() {
	
	}
	
	
	
	
	
	
	
	protected void updateAITasks()
	{
		super.updateAITasks();
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
		return false;
	}
	
	
	protected void applyEntityAttributes2()
	{
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(getBaseLogic().info.maxhealth);
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(80.0D);
		this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(30.0D);
	}
	
	public void addRandomArmor() {
		super.addRandomArmor();
	}
	
	
	
	
	protected void entityInit() {
		super.entityInit();
	}
	
	public boolean attackEntityFrom_with_Info(MovingObjectPosition movingObjectPosition, DamageSource source, float level){
		float temparomor = armor;
		BaseLogic temp;
		if (source instanceof EntityDamageSourceIndirect && source.getEntity() != null) {
			temp = ((HasBaseLogic) this).getBaseLogic();
			Vector3d frontArmorVec = new Vector3d(0, 0, -1);
			Vector3d leftsideArmorVec = new Vector3d(1, 0, 0);
			Vector3d rightsideArmorVec = new Vector3d(-1, 0, 0);
			Vector3d backArmorVec = new Vector3d(0, 0, 1);
			Vector3d topArmorVec = new Vector3d(0, 1, 0);
			RotateVectorAroundX(frontArmorVec, -armor_tilt);
			RotateVectorAroundZ(leftsideArmorVec, armor_tilt);
			RotateVectorAroundZ(rightsideArmorVec, -armor_tilt);
			RotateVectorAroundX(backArmorVec, armor_tilt);
			frontArmorVec = transformVecByQuat(frontArmorVec, temp.bodyRot);
			leftsideArmorVec = transformVecByQuat(leftsideArmorVec, temp.bodyRot);
			rightsideArmorVec = transformVecByQuat(rightsideArmorVec, temp.bodyRot);
			backArmorVec = transformVecByQuat(backArmorVec, temp.bodyRot);
			topArmorVec = transformVecByQuat(topArmorVec, temp.bodyRot);
			frontArmorVec.z *= -1;
			backArmorVec.z *= -1;
			leftsideArmorVec.z *= -1;
			rightsideArmorVec.z *= -1;
			topArmorVec.z *= -1;
			Vector3d shooterMotionVec = new Vector3d(source.getSourceOfDamage().motionX, source.getSourceOfDamage().motionY, source.getSourceOfDamage().motionZ);
//			Vector3d shooterPositionVec = new Vector3d(source.getSourceOfDamage().posX - this.posX
//					                                          , source.getSourceOfDamage().posY - (this.posY + 1.5f)
//					                                          , source.getSourceOfDamage().posZ - this.posZ
//			);
			Vector3d TankFrontVec = new Vector3d(0, 0, -1);
			TankFrontVec = transformVecByQuat(TankFrontVec, temp.bodyRot);
			TankFrontVec.z *= -1;
//			double angle_position = abs(toDegrees(TankFrontVec.angle(shooterPositionVec)));
			Vector3d TankRighttVec = new Vector3d(-1, 0, 0);
			TankRighttVec = transformVecByQuat(TankRighttVec, temp.bodyRot);
			TankRighttVec.z *= -1;
			
			int hitside = movingObjectPosition.sideHit % 6;
			shooterMotionVec.scale(-1);
			if (hitside == 2) {//正面装甲にヒット
				double angle = abs(frontArmorVec.angle(shooterMotionVec));
				temparomor *= (sin(angle)) + armor_Front_cof;
			} else if (hitside == 4){
				double angle = abs(leftsideArmorVec.angle(shooterMotionVec));
				temparomor *= (sin(angle)) + armor_Side_cof;
			} else if(hitside == 5) {
				double angle = abs(rightsideArmorVec.angle(shooterMotionVec));
				temparomor *= (sin(angle)) + armor_Side_cof;
			}else if(hitside == 0 || hitside == 1){
				double angle = abs(topArmorVec.angle(shooterMotionVec));
				temparomor *= (sin(angle)) + armor_Top_cof;
			} else if (hitside == 3) {//背面にヒット
				double angle = abs(backArmorVec.angle(shooterMotionVec));
				temparomor *= (sin(angle)) + armor_Back_cof;
			}
			if (armor != 0 && level > armor / 2f) {
				armor -= 1;
			}
			if (level <= temparomor) {
				if (armor != 0) if (!source.getDamageType().equals("mob"))
					this.playSound("gvcmob:gvcmob.ArmorBounce", 0.5F, 2 - (level / temparomor));
			}else if (armor != 0) this.playSound("gvcmob:gvcmob.armorhit",5, 1F);
			level -= temparomor;
		}
		return this.attackEntityFrom_exceptArmor(source,level);
	}
	
	public boolean attackEntityFrom(DamageSource source, float par2) {
		if(source.getDamageType().equals(DamageSource.fall.damageType) ||
				   source.getDamageType().equals(DamageSource.outOfWorld.damageType) ||
				   source.getDamageType().equals(DamageSource.inWall.damageType))return attackEntityFrom_exceptArmor(source, par2);
		par2 -= armor;
		if(par2 < 0)par2 = 0;
		if (this.riddenByEntity == source.getEntity()) {
			return false;
		} else if (this == source.getEntity()) {
			return false;
		} else if(this instanceof IVehicle && ((IVehicle)this).isRidingEntity(source.getEntity())) {
			return false;
		}else {
			return super.attackEntityFrom(source, par2);
		}
	}
	public boolean attackEntityFrom_exceptArmor (DamageSource source, float par2){
		return super.attackEntityFrom(source, par2);
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
	
	/**
	 * Gets how bright this entity is.
	 */
    /*public float getBrightness(float par1)
    {
        return 1.0F;
    }*/
	
	public static float getMobScale() {
		return 8;
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
	/**
	 * returns a (normalized) vector of where this entity is looking
	 */
	public Vec3 getLookVec()
	{
		return this.getLook(1.0F);
	}
	
	public boolean interact(EntityPlayer p_70085_1_) {
		if (!this.worldObj.isRemote && (this.riddenByEntity == null || this.riddenByEntity != p_70085_1_)) {
			if(!p_70085_1_.isSneaking()&&!p_70085_1_.isRiding()){
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
		typename = p_70037_1_.getString("typename");
		
		init_2(typename);
	}
	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	public void writeEntityToNBT(NBTTagCompound p_70014_1_)
	{
		super.writeEntityToNBT(p_70014_1_);
		p_70014_1_.setString("typename",typename);
	}
	
	@Override
	public void writeSpawnData(ByteBuf buffer){
		ByteBufUtils.writeUTF8String(buffer,typename);
	}
	
	@Override
	public void readSpawnData(ByteBuf additionalData){
		typename = ByteBufUtils.readUTF8String(additionalData);
		
		System.out.println("" + typename);
		init_2(typename);
	}
	public boolean shouldDismountInWater(Entity entity){
		return false;
	}
	@Override
	public boolean is_this_entity_friend(Entity entity){
		return false;
	}
	
	public int getdirection_in4(float globalDir){
		if(globalDir <= 45 && globalDir > -45){
			return 0;
		}
		if(globalDir <= 135 && globalDir > 45){
			return 1;
		}
		if(globalDir <= -45 && globalDir > -135){
			return -1;
		}
		if(globalDir <= -135 || globalDir > 135){
			return 2;
		}
		return 0;
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
	
	@Override
	protected String getLivingSound()
	{
		return null;
	}
	protected String getHurtSound()
	{
		return null;
	}
	
	@Override
	public void yourSoundIsremain() {
		getBaseLogic().needStartSound = false;
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
		if(this.deathTicks == 3){
			//this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 0F, false);
			ExplodeEffect ex = new ExplodeEffect(this, 3F);
			ex.offset[0] = (float) (rand.nextInt(30) - 15)/10;
			ex.offset[1] = (float) (rand.nextInt(30) - 15)/10 + 1.5f;
			ex.offset[2] = (float) (rand.nextInt(30) - 15)/10;
			ex.Ex();
		}
		if(this.deathTicks > 40) {
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
			this.playSound("gvcguns:gvcguns.fireee", 1.20F, 0.8F);
		}else
		if (rand.nextInt(3) == 0) {
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
						(rand.nextInt(20) - 10) / 100,
						(rand.nextInt(20) - 10) / 100,
						(rand.nextInt(20) - 10) / 100 );
				worldObj.spawnParticle("smoke",
						this.posX + (float) (rand.nextInt(30) - 15) / 10,
						this.posY + (float) (rand.nextInt(30) - 15) / 10,
						this.posZ + (float) (rand.nextInt(30) - 15) / 10,
						(rand.nextInt(20) - 10) / 100,
						(rand.nextInt(20) - 10) / 100,
						(rand.nextInt(20) - 10) / 100 );
				worldObj.spawnParticle("cloud",
						this.posX + (float) (rand.nextInt(30) - 15) / 10,
						this.posY + (float) (rand.nextInt(30) - 15) / 10,
						this.posZ + (float) (rand.nextInt(30) - 15) / 10,
						(rand.nextInt(20) - 10) / 100,
						(rand.nextInt(20) - 10) / 100,
						(rand.nextInt(20) - 10) / 100 );
			}
			if(this.deathTicks == 150)
				this.setDead();
		}
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
	
	@Override
	public void moveEntityWithHeading(float p_70612_1_, float p_70612_2_)
	{
		getBaseLogic().moveEntityWithHeading(p_70612_1_,p_70612_2_);
	}
	
	@Override
	public boolean handleWaterMovement(){
		return getBaseLogic().handleWaterMovement();
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
	@Override
	protected void collideWithNearbyEntities(){
		getBaseLogic().collideWithNearbyEntities();
	}
	@Override
	public void public_collideWithEntity(Entity entity) {
		collideWithEntity(entity);
	}
}
