package hmgww2.entity;

import hmggvcmob.ai.*;
import handmadevehicle.entity.ExplodeEffect;
import handmadevehicle.entity.parts.logics.IbaseLogic;
import handmadevehicle.entity.parts.logics.MultiRiderLogics;
import handmadevehicle.entity.parts.logics.TankBaseLogic;
import handmadevehicle.entity.parts.turrets.TurretObj;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;

import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;

import java.util.List;

import static hmggvcmob.GVCMobPlus.proxy;
import static handmadevehicle.Utils.transformVecByQuat;
import static handmadevehicle.Utils.transformVecforMinecraft;
import static handmadevehicle.HMVehicle.proxy_HMVehicle;
import static java.lang.Math.abs;

public abstract class EntityBases_Tank extends EntityBases implements ITank, IControlable
{
	// public int type;
	
	int count_for_reset;
	
	public boolean seat_onTurret = true;
	
	public boolean subturret_is_mainTurret_child = false;
	public float subturretrotationYaw;
	public float subturretrotationPitch;
	public TankBaseLogic baseLogic = new TankBaseLogic(this,0.5f,2.0f,false,"hmgww2:hmgww2.T34Track");
	ModifiedBoundingBox nboundingbox;
	
	Vector3d playerpos = new Vector3d(-0.525,2.1D,0.0);
	Vector3d zoomingplayerpos = new Vector3d(-0,2.2D,0.3);
	Vector3d subturretpos = new Vector3d(0.4747,1.260,-2.235);
	Vector3d cannonpos = new Vector3d(0,2.06F,-1.58F);
	Vector3d turretpos = new Vector3d(0,0,-0.4488F);
	
	AITankAttack aiTankAttack;
	
	public TurretObj mainTurret;
	public TurretObj subTurret;
	public TurretObj[] turrets;
	
	public float maxHealth;
	public String sightTex = null;
	
	
	public EntityBases_Tank(World par1World)
	{
		super(par1World);
		this.setSize(4F, 2.5F);
		
		nboundingbox = new ModifiedBoundingBox(boundingBox.minX,boundingBox.minY,boundingBox.minZ,boundingBox.maxX,boundingBox.maxY,boundingBox.maxZ,
				                                      0,1.5,0,
				                                      3.4 , 3 , 6.5);
		nboundingbox.rot.set(baseLogic.bodyRot);
		proxy.replaceBoundingbox(this,nboundingbox);
		nboundingbox.centerRotX = 0;
		nboundingbox.centerRotY = 0;
		nboundingbox.centerRotZ = 0;
		this.tasks.removeTask(aiSwimming);
		viewWide = 2.09f;
		yOffset = 0;
		interval = 10;
	}
	
	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(2, Integer.valueOf(0));//reloadProgress
//		this.dataWatcher.addObject(3, Integer.valueOf(0));//none
//		this.dataWatcher.addObject(23, Integer.valueOf(0));//none
		this.dataWatcher.addObject(24, Float.valueOf(0));//subY
		this.dataWatcher.addObject(25, Float.valueOf(0));//subP
		this.dataWatcher.addObject(26, Float.valueOf(0));//turY
		this.dataWatcher.addObject(27, Float.valueOf(0));//turP
		this.dataWatcher.addObject(28, Float.valueOf(0));//Y
		this.dataWatcher.addObject(29, Float.valueOf(0));//P
		this.dataWatcher.addObject(30, Float.valueOf(0));//R
	}
	public void setSubTurretrotationYaw(float floats) {
		this.dataWatcher.updateObject(24, Float.valueOf(floats));
	}
	public float getSubTurretrotationYaw() {
		return this.dataWatcher.getWatchableObjectFloat(24);
	}
	public void setSubTurretrotationPitch(float floats) {
		this.dataWatcher.updateObject(25, Float.valueOf(floats));
	}
	public float getSubTurretrotationPitch() {
		return this.dataWatcher.getWatchableObjectFloat(25);
	}
	
	
	public void setRotationYaw(float floats) {
		this.dataWatcher.updateObject(28, Float.valueOf(floats));
	}
	public float getRotationYaw() {
		return this.dataWatcher.getWatchableObjectFloat(28);
	}
	public void setRotationPitch(float floats) {
		this.dataWatcher.updateObject(29, Float.valueOf(floats));
	}
	public float getRotationPitch() {
		return this.dataWatcher.getWatchableObjectFloat(29);
	}
	public void setRotationRoll(float floats) {
		this.dataWatcher.updateObject(30, Float.valueOf(floats));
	}
	public float getRotationRoll() {
		return this.dataWatcher.getWatchableObjectFloat(30);
	}
	
	public void setTurretrotationYaw(float floats) {
		this.dataWatcher.updateObject(26, Float.valueOf(floats));
	}
	public float getTurretrotationYaw() {
		return this.dataWatcher.getWatchableObjectFloat(26);
	}
	public void setTurretrotationPitch(float floats) {
		this.dataWatcher.updateObject(27, Float.valueOf(floats));
	}
	public float getTurretrotationPitch() {
		return this.dataWatcher.getWatchableObjectFloat(27);
	}
	
	public boolean interact(EntityPlayer p_70085_1_) {
		if (!this.worldObj.isRemote && (this.riddenByEntity == null || this.riddenByEntity == p_70085_1_)) {
			if(p_70085_1_.isSneaking()){
				if(p_70085_1_.getEquipmentInSlot(0) != null) {
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
				}else
				if(this.getMobMode() == 0){
					mode = 1;
					this.setMobMode(1);
					homeposX = (int) p_70085_1_.posX;
					homeposY = (int) p_70085_1_.posY;
					homeposZ = (int) p_70085_1_.posZ;
					master = p_70085_1_;
					p_70085_1_.addChatComponentMessage(new ChatComponentTranslation(
							                                                               "Cover mode"));
				}else if(this.getMobMode() == 1) {
					mode = 2;
					this.setMobMode(2);
					
					p_70085_1_.addChatComponentMessage(new ChatComponentTranslation(
							                                                               "Defense  " + (int)posX + "," + (int)posZ));
					homeposX = (int) posX;
					homeposY = (int) posY;
					homeposZ = (int) posZ;
				}else if(this.getMobMode() == 2){
					p_70085_1_.addChatComponentMessage(new ChatComponentTranslation("wait "));
					mode = 3;
					this.setMobMode(3);
				}else if(this.getMobMode() == 3){
					mode = 0;
					this.setMobMode(0);
				}
			}else if(!p_70085_1_.isRiding()){
				mode = 0;
				this.setMobMode(0);
				pickupEntity(p_70085_1_,0);
			}
			return true;
		} else {
			return false;
		}
	}
	public boolean pickupEntity(Entity p_70085_1_, int StartSeachSeatNum){
		if(!(this instanceof ImultiRidable)) {
			p_70085_1_.mountEntity(this);
			return true;
		}else {
			return ((MultiRiderLogics)((HasBaseLogic)this).getBaseLogic()).pickupEntity(p_70085_1_,StartSeachSeatNum);
		}
	}
	public void updateRiderPosition() {
		if (!(this instanceof ImultiRidable) && this.riddenByEntity != null) {
			if(seat_onTurret) {
				mainTurret.calculatePos(new Vector3d(this.posX, this.posY, -this.posZ), baseLogic.bodyRot);
				Vector3d temp = new Vector3d(mainTurret.pos);
				Vector3d tempplayerPos = new Vector3d(proxy_HMVehicle.iszooming() ? zoomingplayerpos : playerpos);
				tempplayerPos.z *= -1;
				Vector3d playeroffsetter = new Vector3d(0, ((worldObj.isRemote && this.riddenByEntity == proxy_HMVehicle.getEntityPlayerInstance()) ? 0 : (this.riddenByEntity.getEyeHeight() + this.riddenByEntity.yOffset)), 0);
				tempplayerPos.sub(playeroffsetter);
				Vector3d temp2 = mainTurret.getGlobalVector_fromLocalVector_onTurretPoint(tempplayerPos);
				temp.add(temp2);
				transformVecforMinecraft(temp);
//			temp.add(playeroffsetter);
//			System.out.println(temp);
				this.riddenByEntity.setPosition(temp.x,
						temp.y,
						temp.z);
				this.riddenByEntity.posX = temp.x;
				this.riddenByEntity.posY = temp.y;
				this.riddenByEntity.posZ = temp.z;
			}else {
				Vector3d temp = new Vector3d(this.posX,this.posY,-this.posZ);
				Vector3d tempplayerPos = new Vector3d(proxy_HMVehicle.iszooming() ? zoomingplayerpos : playerpos);
				tempplayerPos.z *= -1;
				Vector3d playeroffsetter = new Vector3d(0, ((worldObj.isRemote && this.riddenByEntity == proxy_HMVehicle.getEntityPlayerInstance()) ? 0 : (this.riddenByEntity.getEyeHeight() + this.riddenByEntity.yOffset)), 0);
				tempplayerPos.sub(playeroffsetter);
				tempplayerPos = transformVecByQuat(tempplayerPos,this.baseLogic.bodyRot);
				temp.add(tempplayerPos);
				transformVecforMinecraft(temp);
				this.riddenByEntity.setPosition(temp.x,
						temp.y,
						temp.z);
				this.riddenByEntity.posX = temp.x;
				this.riddenByEntity.posY = temp.y;
				this.riddenByEntity.posZ = temp.z;
			}
		}
	}
	
	public void jump(){
	
	}
	
	public void moveEntityWithHeading(float p_70612_1_, float p_70612_2_)
	{
		
		if(!worldObj.isRemote) {
			if (this.isInWater()) {
				this.moveFlying(p_70612_1_, p_70612_2_, (this.getAIMoveSpeed() < 0?-1:0) * (this.isAIEnabled() ? 0.04F : 0.02F));
				this.motionY -= 0.02D;
				this.moveEntity(this.motionX, this.motionY, this.motionZ);
				this.motionX *= 0.800000011920929D;
				this.motionY *= 0.800000011920929D;
				this.motionZ *= 0.800000011920929D;
			} else if (this.handleLavaMovement()) {
				this.moveFlying(p_70612_1_, p_70612_2_, (this.getAIMoveSpeed() < 0?-1:0) * (this.isAIEnabled() ? 0.04F : 0.02F));
				this.motionY -= 0.02D;
				this.moveEntity(this.motionX, this.motionY, this.motionZ);
				this.motionX *= 0.5D;
				this.motionY *= 0.5D;
				this.motionZ *= 0.5D;
			} else {
				float f2 = 0.91F;
				
				if (this.onGround) {
					f2 = this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ)).slipperiness * 0.91F;
				}
				
				float f3 = 0.16277136F / (f2 * f2 * f2);
				float f4;
				
				if (this.onGround) {
					f4 = this.getAIMoveSpeed() * f3;
				} else {
					f4 = this.jumpMovementFactor;
				}
				this.moveFlying(p_70612_1_, p_70612_2_, f4);
				f2 = 0.91F;
				
				if (this.onGround) {
					f2 = this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ)).slipperiness * 0.91F;
				}
				
				this.motionY -= 0.08D;
				
				this.moveEntity(this.motionX, this.motionY, this.motionZ);
				
				this.motionY *= 0.9800000190734863D;
				this.motionX *= (double) f2;
				this.motionZ *= (double) f2;
			}
		}
	}
	
	
	
	
	public void onUpdate()
	{
		super.onUpdate();
		tankUpdate();
		worldObj.MAX_ENTITY_RADIUS = 40;
	}
	public void tankUpdate(){
		this.stepHeight = 1.5f;
		if(!this.worldObj.isRemote){
			baseLogic.updateServer();
			
			if(riddenByEntity != null){
				mgAim(riddenByEntity.getRotationYawHead(),riddenByEntity.rotationPitch);
			}
			
			setSubTurretrotationYaw(subturretrotationYaw);
			setSubTurretrotationPitch(subturretrotationPitch);
		}else{
			baseLogic.updateClient();
			
			subturretrotationYaw = getSubTurretrotationYaw();
			subturretrotationPitch = getSubTurretrotationPitch();
			
			
			this.renderYawOffset = rotationYaw;
			this.prevRenderYawOffset = prevRotationYaw;
			if(count_for_reset > 10000){
				this.setAttackTarget(null);
				count_for_reset = 0;
			}
			mode= getMobMode();
		}
		baseLogic.updateCommon();
		if(mainTurret != null)mainTurret.update(baseLogic.bodyRot,new Vector3d(this.posX,this.posY,-this.posZ));
		if(!subturret_is_mainTurret_child && subTurret != null)subTurret.update(baseLogic.bodyRot,new Vector3d(this.posX,this.posY,-this.posZ));
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
	}
	
	@Override
	public TurretObj getMainTurret() {
		return mainTurret;
	}
	
	@Override
	public TurretObj[] getTurrets() {
		return turrets;
	}
	
	@Override
	public IbaseLogic getBaseLogic() {
		return baseLogic;
	}
	
	public void mainFireToTarget(Entity target){
		mainTurret.currentEntity = this;
		mainTurret.fireall();
	}
	public void mainFire(){
		mainTurret.currentEntity = this.riddenByEntity;
		mainTurret.fireall();
	}
	public void subFireToTarget(Entity target){
		if(subTurret != null) {
			subTurret.currentEntity = this;
			if (subTurret.aimToEntity(target)) {
				subTurret.fireall();
			}
			subturretrotationYaw = (float) subTurret.turretrotationYaw;
			subturretrotationPitch = (float) subTurret.turretrotationPitch;
		}
	}
	public void subFire(){
		if(subTurret != null) {
			subTurret.currentEntity = riddenByEntity;
			subTurret.fireall();
		}
		
	}
	
	public void mgAim(float targetyaw,float targetpitch){
		if(subTurret != null) {
			subTurret.currentEntity = this;
			subTurret.aimtoAngle(targetyaw, targetpitch);
			subturretrotationYaw = (float) subTurret.turretrotationYaw;
			subturretrotationPitch = (float) subTurret.turretrotationPitch;
		}
	}
	
	@Override
	public boolean standalone() {
		return mode != 0;
	}
	
	
	public boolean isConverting() {
		return false;
	}
	
	
	
	
	
	@Override
	public float getviewWide() {
		return viewWide;
	}
	
	
	
	
	@Override
	public int getfirecyclesettings1() {
		return getMainTurret() != null ? getMainTurret().cycle_setting:0;
	}
	
	@Override
	public int getfirecycleprogress1() {
		return getMainTurret() != null ? getMainTurret().cycle_timer:0;
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
		return baseLogic.turretrotationYaw;
	}
	
	@Override
	public float getbodyrotationYaw() {
		return baseLogic.bodyrotationYaw;
	}
	
	@Override
	public void setrotationYawmotion(float value) {
		baseLogic.rotationmotion = value;
	}
	
	@Override
	public void setBodyRot(Quat4d rot) {
		baseLogic.bodyRot.set(rot);
	}
	
	@Override
	public float getthrottle() {
		return baseLogic.throttle;
	}
	
	@Override
	public void setthrottle(float value) {
		baseLogic.throttle = value;
	}
	
	public void moveFlying(float p_70060_1_, float p_70060_2_, float p_70060_3_){
		baseLogic.moveFlying(p_70060_1_,p_70060_2_,p_70060_3_);
	}
	
	public void setPosition(double x, double y, double z)
	{
		super.setPosition(x,y,z);
		if(baseLogic != null)baseLogic.setPosition(x,y,z);
	}
	@Override
	public void setControl_RightClick(boolean value) {
		server1 = value;
	}
	
	@Override
	public void setControl_LeftClick(boolean value) {
		server2 = value;
	}
	
	@Override
	public void setControl_Space(boolean value) {
		baseLogic.serverspace = value;
	}
	
	@Override
	public void setControl_x(boolean value) {
		serverx = value;
	}
	
	@Override
	public void setControl_w(boolean value) {
		baseLogic.serverw = value;
	}
	
	@Override
	public void setControl_a(boolean value) {
		baseLogic.servera = value;
	}
	
	@Override
	public void setControl_s(boolean value) {
		baseLogic.servers = value;
	}
	
	@Override
	public void setControl_d(boolean value) {
		baseLogic.serverd = value;
	}
	
	@Override
	public void setControl_f(boolean value) {
		baseLogic.serverf = value;
	}
	
	@Override
	public boolean getControl_RightClick() {
		return server1;
	}
	
	@Override
	public boolean getControl_LeftClick() {
		return server2;
	}
	
	@Override
	public boolean getControl_Space() {
		return baseLogic.serverspace;
	}
	
	@Override
	public boolean getControl_x() {
		return serverx;
	}
	
	@Override
	public boolean getControl_w() {
		return baseLogic.serverw;
	}
	
	@Override
	public boolean getControl_a() {
		return baseLogic.servera;
	}
	
	@Override
	public boolean getControl_s() {
		return baseLogic.servers;
	}
	
	@Override
	public boolean getControl_d() {
		return baseLogic.serverd;
	}
	
	@Override
	public boolean getControl_f() {
		return baseLogic.serverf;
	}
	
	
	@Override
	public void setLocationAndAngles(double x, double y, double z, float yaw, float pitch){
		super.setLocationAndAngles(x,y,z,yaw,pitch);
		baseLogic.setLocationAndAngles(yaw,pitch);
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
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(maxHealth);
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(80.0D);
		this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(30.0D);
	}
	
	
	public String getsightTex(){
		return sightTex;
	}
	
	protected void collideWithNearbyEntities()
	{
		List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(0.20000000298023224D, 0.0D, 0.20000000298023224D));
		
		if (list != null && !list.isEmpty())
		{
			for (int i = 0; i < list.size(); ++i)
			{
				Entity entity = (Entity)list.get(i);
				
				if (entity.canBePushed() && entity.width > 1.5)
				{
					this.collideWithEntity(entity);
				}
			}
		}
	}
	
	public void applyEntityCollision(Entity p_70108_1_)
	{
		if (p_70108_1_.riddenByEntity != this && p_70108_1_.ridingEntity != this && p_70108_1_.width > 1.5)
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
	
	
	@Override
	public void moveEntity(double x, double y, double z){
		baseLogic.moveEntity(x,y,z);
	}
	
	@Override
	public void updateFallState_public(double stepHeight, boolean onground){
		this.updateFallState(stepHeight,onground);
	}
	
	@Override
	public void func_145775_I_public() {
		this.func_145775_I();
	}
	
}
