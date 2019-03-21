package hmgww2.entity;

import hmggvcmob.GVCMobPlus;
import hmggvcmob.ai.AITankAttack;
import hmggvcmob.entity.*;
import hmggvcmob.tile.TileEntityFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;

import static hmggvcmob.GVCMobPlus.proxy;
import static hmggvcmob.event.GVCMXEntityEvent.soundedentity;
import static hmggvcmob.util.Calculater.transformVecforMinecraft;

public class EntityUSSR_TankBase extends EntityUSSRBase implements IRideableTank,IControlable
{
	// public int type;
	
	int count_for_reset;
	public double angletime;
	public int fireCycle1;
	public int fireCycle2;
	
	public float subturretrotationYaw;
	public float subturretrotationPitch;
	
	public int mgMagazine;
	public int mgReloadProgress;
	public TankBaseLogic baseLogic = new TankBaseLogic(this,0.5f,2.0f,false,"gvcmob:gvcmob.T34Track");
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
	
	
	public EntityUSSR_TankBase(World par1World)
	{
		super(par1World);
		this.setSize(4F, 2.5F);
		
		nboundingbox = new ModifiedBoundingBox(-20,-20,-20,20,20,20,
				                                      0,1.5,0,3.4,3,6.5);
		nboundingbox.rot.set(baseLogic.bodyRot);
		GVCMobPlus.proxy.replaceBoundingbox(this,nboundingbox);
		aiTankAttack = new AITankAttack(this,1600,100,10,10);
		this.tasks.addTask(1,aiTankAttack);
		nboundingbox.centerRotX = 0;
		nboundingbox.centerRotY = 0;
		nboundingbox.centerRotZ = 0;
		this.tasks.removeTask(aiSwimming);
		viewWide = 2.09f;
		yOffset = 0;
		mainTurret = new TurretObj(worldObj);
		{
			mainTurret.onmotherPos = turretpos;
			mainTurret.cannonpos = cannonpos;
			mainTurret.turretspeedY = 1;
			mainTurret.turretspeedP = 1;
			mainTurret.currentEntity = this;
			mainTurret.powor = 80;
			mainTurret.ex = 5.0F;
			mainTurret.firesound = "gvcmob:gvcmob.TankFire";
			mainTurret.spread = 1;
			mainTurret.speed = 16;
			mainTurret.canex = true;
			mainTurret.guntype = 2;
		}
		subTurret = new TurretObj(worldObj);
		{
			subTurret.currentEntity = this;
			subTurret.turretanglelimtPitchmin = -20;
			subTurret.turretanglelimtPitchMax = 20;
			subTurret.turretanglelimtYawmin = -20;
			subTurret.turretanglelimtYawMax = 20;
			subTurret.turretspeedY = 8;
			subTurret.turretspeedP = 10;
			subTurret.traverseSound = null;
			
			subTurret.onmotherPos = subturretpos;
			subTurret.cycle_setting = 1;
			subTurret.spread = 5;
			subTurret.speed = 8;
			subTurret.firesound = "handmadeguns:handmadeguns.fire";
			subTurret.flushName  = "arrow";
			subTurret.flushfuse  = 1;
			subTurret.flushscale  = 1.5f;
			
			subTurret.powor = 8;
			subTurret.ex = 0;
			subTurret.canex = false;
			subTurret.guntype = 0;
			
			subTurret.magazineMax = 47;
			subTurret.reloadSetting = 100;
			subTurret.flushoffset = 0.5f;
		}
		
		turrets = new TurretObj[]{mainTurret,subTurret};
	}
	
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(maxHealth);
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(80.0D);
		this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(30.0D);
	}
	
	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(2, Integer.valueOf(0));
		this.dataWatcher.addObject(3, Integer.valueOf(0));
		this.dataWatcher.addObject(24, Float.valueOf(0));
		this.dataWatcher.addObject(25, Float.valueOf(0));
		this.dataWatcher.addObject(26, Float.valueOf(0));
		this.dataWatcher.addObject(27, Float.valueOf(0));
		this.dataWatcher.addObject(28, Float.valueOf(0));
		this.dataWatcher.addObject(29, Float.valueOf(0));
		this.dataWatcher.addObject(30, Float.valueOf(0));
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
	
	public void setCanonnreloadcycle(int ints) {
		this.dataWatcher.updateObject(2, Integer.valueOf(ints));
	}
	public int getCanonnreloadcycle() {
		return this.dataWatcher.getWatchableObjectInt(2);
	}
	public void setremainMg(int ints) {
		this.dataWatcher.updateObject(3, Integer.valueOf(ints));
	}
	public int getremainMg() {
		return this.dataWatcher.getWatchableObjectInt(3);
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
				p_70085_1_.mountEntity(this);
			}
			return true;
		} else {
			return false;
		}
	}
	
	public void updateRiderPosition() {
		if (this.riddenByEntity != null) {
			mainTurret.setmotherpos(new Vector3d(this.posX,this.posY,-this.posZ),baseLogic.bodyRot);
			Vector3d temp = new Vector3d(mainTurret.pos);
			Vector3d tempplayerPos = new Vector3d(proxy.iszooming() ? zoomingplayerpos:playerpos);
			Vector3d playeroffsetter = new Vector3d(0,((worldObj.isRemote && this.riddenByEntity == proxy.getEntityPlayerInstance()) ? 0:(this.riddenByEntity.getEyeHeight() + this.riddenByEntity.yOffset)),0);
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
		}
	}
	
	public void jump(){
	
	}
	
	public void moveEntityWithHeading(float p_70612_1_, float p_70612_2_)
	{
		
		if(!worldObj.isRemote) {
			if (this.isInWater()) {
				this.moveFlying(p_70612_1_, p_70612_2_, this.isAIEnabled() ? 0.04F : 0.02F);
				this.motionY -= 0.02D;
				this.moveEntity(this.motionX, this.motionY, this.motionZ);
				this.motionX *= 0.800000011920929D;
				this.motionY *= 0.800000011920929D;
				this.motionZ *= 0.800000011920929D;
			} else if (this.handleLavaMovement()) {
				this.moveFlying(p_70612_1_, p_70612_2_, 0.02F);
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
		this.stepHeight = 1.5f;
		if(!this.worldObj.isRemote){
			baseLogic.updateServer();
			
			if(riddenByEntity != null){
				mgAim(riddenByEntity.getRotationYawHead(),riddenByEntity.rotationPitch);
			}
			
			if(!(this.getHealth()<=0)){
				if(!(mgMagazine>0)){
					mgReloadProgress++;
				}
				if(mgReloadProgress > 100){
					mgMagazine = 100;
				}
				fireCycle1 = mainTurret.cycle_timer;
				setremainMg(mgMagazine);
				setCanonnreloadcycle(fireCycle1);
			}
			++this.soundtick;
			if (this.soundtick > 10) {
				this.playSound("gvcmob:gvcmob.tank", 1.20F, 1.0F);
				if(this.getEntityData().getFloat("GunshotLevel")<0.1)
					soundedentity.add(this);
				this.getEntityData().setFloat("GunshotLevel",1);
				this.soundtick = 0;
			}
			setSubTurretrotationYaw(subturretrotationYaw);
			setSubTurretrotationPitch(subturretrotationPitch);
		}else{
			baseLogic.updateClient();
			
			
			
			
			
			
			subturretrotationYaw = getSubTurretrotationYaw();
			subturretrotationPitch = getSubTurretrotationPitch();
			this.mgMagazine = getremainMg();
			this.fireCycle1 = getCanonnreloadcycle();
			
			
			this.renderYawOffset = rotationYaw;
			this.prevRenderYawOffset = prevRotationYaw;
			if(count_for_reset > 10000){
				this.setAttackTarget(null);
				count_for_reset = 0;
			}
			mainTurret.turretrotationYaw = baseLogic.turretrotationYaw;
			mainTurret.turretrotationPitch = baseLogic.turretrotationPitch;
		}
		baseLogic.updateCommon();
		mainTurret.update(baseLogic.bodyRot,new Vector3d(this.posX,this.posY,-this.posZ));
		if(subTurret != null)subTurret.update(baseLogic.bodyRot,new Vector3d(this.posX,this.posY,-this.posZ));
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
	}
	public void mainFire(Entity target){
		mainTurret.currentEntity = this;
		mainTurret.fire();
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
	public TankBaseLogic getBaseLogic() {
		return baseLogic;
	}
	
	public void mainFire(){
		mainTurret.currentEntity = this.riddenByEntity;
		mainTurret.fire();
	}
	public void subFire(Entity target){
		if(subTurret != null) {
			subTurret.currentEntity = this;
			if (subTurret.aimToEntity(target)) {
				subTurret.fire();
			}
			subturretrotationYaw = (float) subTurret.turretrotationYaw;
			subturretrotationPitch = (float) subTurret.turretrotationPitch;
		}
	}
	public void subFire(){
		if(subTurret != null) {
			subTurret.currentEntity = riddenByEntity;
			subTurret.fire();
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
		return 100;
	}
	
	@Override
	public int getfirecycleprogress1() {
		return fireCycle1;
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
	public void setbodyrotationYaw(float value) {
		baseLogic.bodyrotationYaw = value;
	}
	
	@Override
	public void setturretrotationYaw(float value) {
		baseLogic.turretrotationYaw = value;
	}
	
	@Override
	public float getrotationYawmotion() {
		return baseLogic.rotationmotion;
	}
	
	@Override
	public void setrotationYawmotion(float value) {
		baseLogic.rotationmotion = value;
	}
	
	@Override
	public void setBodyrot(Quat4d rot) {
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
	
	public boolean canBePushed()
	{
		return false;
	}
	
	protected void onDeathUpdate() {
		++this.deathTicks;
		if(this.deathTicks == 3){
			//this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 0F, false);
			GVCEx ex = new GVCEx(this, 3F);
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
			GVCEx ex = new GVCEx(this, 1F);
			ex.offset[0] = (float) (rand.nextInt(30) - 15) / 10;
			ex.offset[1] = (float) (rand.nextInt(30) - 15) / 10;
			ex.offset[2] = (float) (rand.nextInt(30) - 15) / 10;
			ex.Ex();
		}
		if (this.deathTicks >= 140) {
			GVCEx ex = new GVCEx(this, 8F);
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
}
