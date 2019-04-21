package hmggvcmob.entity.guerrilla;


import hmggvcmob.ai.AITankAttack;
import hmggvcmob.entity.*;
import hmggvcmob.tile.TileEntityFlag;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;

import static hmggvcmob.GVCMobPlus.proxy;
import static hmggvcmob.event.GVCMXEntityEvent.soundedentity;
import static hmggvcmob.util.Calculater.transformVecforMinecraft;

public class GVCEntityAPC extends EntityGBase implements ITank {
	int count_for_reset;
	public double angletime;
	public int fireCycle1;
	public int cooltime;
	public int magazine;
	public int soundtick = 0;

//	public float bodyrotationYaw;
//	public float bodyrotationPitch;
//	public float prevbodyrotationYaw;
//	public float turretrotationYaw;
//	public float turretrotationPitch;
//
//	public float prevturretrotationYaw;
//	public float prevturretrotationPitch;
//	public float subturretrotationYaw;
//	public float subturretrotationPitch;

	public byte hatchprogress;
	public TankBaseLogic baseLogic = new TankBaseLogic(this,0.3f,1.4f,false,"gvcmob:gvcmob.APCTrack");
	ModifiedBoundingBox nboundingbox;

	Vector3d playerpos = new Vector3d(-0.764f,2.4f,0.02124 - 0.59742);
	Vector3d zoomingplayerpos = new Vector3d(-0.1638,2.68614,0.44874 - 0.59742);
	Vector3d subturretpos = new Vector3d(0,1,3);
	Vector3d cannonpos = new Vector3d(0,0,0);
	Vector3d turretpos = new Vector3d(0,2.5625,-0.4375);

	AITankAttack aiTankAttack;

	public TurretObj mainTurret;
	public TurretObj subTurret;
	public TurretObj[] turrets;

	public GVCEntityAPC(World par1World)
	{
		super(par1World);
		this.tasks.removeTask(aiSwimming);
		this.setSize(3F, 1.6F);
		nboundingbox = new ModifiedBoundingBox(-20,-20,-20,20,20,20,
				0,1.5,0,3.4,3,6.5);
		nboundingbox.rot.set(baseLogic.bodyRot);
		proxy.replaceBoundingbox(this,nboundingbox);
		nboundingbox.centerRotX = 0;
		nboundingbox.centerRotY = 0;
		nboundingbox.centerRotZ = 0;

		this.tasks.removeTask(aiSwimming);
		aiTankAttack = new AITankAttack(this,1600,400,100,400);
		aiTankAttack.noLineCheck_subfire = true;
		this.tasks.addTask(1,aiTankAttack);
		viewWide = 2.09f;
		yOffset = 0;
		mainTurret = new TurretObj(worldObj);
		{
			mainTurret.currentEntity = this;
			mainTurret.turretanglelimtPitchmin = -70;
			mainTurret.turretanglelimtPitchMax = 40;

			mainTurret.turretanglelimtYawmin = -60;
			mainTurret.turretanglelimtYawMax = 60;
			mainTurret.turretspeedY = 8;
			mainTurret.turretspeedP = 10;
			mainTurret.traverseSound = null;

			mainTurret.onmotherPos = turretpos;
			mainTurret.cannonpos = cannonpos;
			mainTurret.cycle_setting = 2;
			mainTurret.spread = 5;
			mainTurret.speed = 8;
			mainTurret.firesound = "handmadeguns:handmadeguns.HeavyMachineGun";
			mainTurret.flushscale  = 2;


			mainTurret.powor = 20;
			mainTurret.ex = 0;
			mainTurret.canex = false;
			mainTurret.guntype = 0;

			mainTurret.magazineMax = 50;
			mainTurret.reloadSetting = 300;
			mainTurret.flushoffset = 0.5f;
		}
		canusePlacedGun = false;
		canuseAlreadyPlacedGun = false;

		turrets = new TurretObj[]{mainTurret};
	}
	public boolean interact(EntityPlayer p_70085_1_) {
		return false;
	}
	public void updateRiderPosition() {
		if (this.riddenByEntity != null) {
			Vector3d temp = new Vector3d(mainTurret.pos);
			Vector3d tempplayerPos = new Vector3d(proxy.iszooming() ? zoomingplayerpos:playerpos);
			Vector3d playeroffsetter = new Vector3d(0,((worldObj.isRemote && this.riddenByEntity == proxy.getEntityPlayerInstance()) ? 0:(this.riddenByEntity.getEyeHeight() + this.riddenByEntity.yOffset)),0);
			tempplayerPos.sub(playeroffsetter);
			Vector3d temp2 = mainTurret.getGlobalVector_fromLocalVector_onTurretPoint(tempplayerPos);
			temp.add(temp2);
			transformVecforMinecraft(temp);
			temp.add(playeroffsetter);
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

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100D);
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(80.0D);
		this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(30.0D);
	}

	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(22, new Integer((int)0));
		this.dataWatcher.addObject(23, new Byte((byte)0));
		this.dataWatcher.addObject(24, new Float((int)0));
		this.dataWatcher.addObject(25, new Float((int)0));
		this.dataWatcher.addObject(26, new Float((int)0));
		this.dataWatcher.addObject(27, new Float((int)0));
		this.dataWatcher.addObject(28, new Float((int)0));
		this.dataWatcher.addObject(29, new Float((int)0));
		this.dataWatcher.addObject(30, new Float((int)0));
	}

	public void sethatchProgress(byte bytes) {
		this.dataWatcher.updateObject(23, Byte.valueOf(bytes));
	}
	public byte gethatchProgress() {
		return this.dataWatcher.getWatchableObjectByte(23);
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
		this.dataWatcher.updateObject(22, Integer.valueOf(ints));
	}
	public int getCanonnreloadcycle() {
		return this.dataWatcher.getWatchableObjectInt(22);
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

	public boolean attackEntityFrom(DamageSource source, float par2)
	{

		if (this.riddenByEntity == source.getEntity()) {
			return false;
		} else {
			if (par2 <= 20) {
				if (!source.getDamageType().equals("mob")) this.playSound("gvcmob:gvcmob.ArmorBounce", 0.5F, 2F);
				return false;
			}
			this.playSound("gvcmob:gvcmob.armorhit", 0.5F, 1F);
			return super.attackEntityFrom(source,par2);
		}

	}

	public void addRandomArmor()
	{
		super.addRandomArmor();
	}

//    protected void rotemodel(float f){
//		this.rotationYawHead = this.rotationYaw + f;
//		this.rotationYaw = this.rotationYawHead + f;
//		this.prevRotationYaw = this.prevRotationYawHead + f;
//		this.prevRotationYawHead = this.prevRotationYawHead + f;
//		this.renderYawOffset = this.prevRotationYawHead + f;
//    	//this.rotation =this.prevRotationYawHead + f;
//	}

	public void onUpdate()
	{
		super.onUpdate();
		this.stepHeight = 1.5f;
		if(!this.worldObj.isRemote){
			baseLogic.updateServer();
			if(!(this.getHealth()<=0)){
				fireCycle1 = mainTurret.cycle_timer;
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
			hatchprogress--;
			if(hatchprogress<0)hatchprogress = 0;
			sethatchProgress((byte) hatchprogress);
		}else{
			baseLogic.updateClient();






			this.fireCycle1 = getCanonnreloadcycle();


			this.renderYawOffset = rotationYaw;
			this.prevRenderYawOffset = prevRotationYaw;
			mainTurret.turretrotationYaw = baseLogic.turretrotationYaw;
			mainTurret.turretrotationPitch = baseLogic.turretrotationPitch;
			hatchprogress = gethatchProgress();
		}
		baseLogic.updateCommon();
		mainTurret.update(baseLogic.bodyRot,new Vector3d(this.posX,this.posY,-this.posZ));
		((ModifiedBoundingBox)this.boundingBox).rot.set(baseLogic.bodyRot);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
	}
	public void mainFire(Entity target){
		mainTurret.currentEntity = this;
		mainTurret.fire();
//        Vector3d Vec_transformedbybody = baseLogic.getTransformedVector_onturret(cannonpos,turretYawCenterpos);
//
//        Calculater.transformVecforMinecraft(Vec_transformedbybody);
//        if(fireCycle1 <0){
//            fireCycle1 = 100;
//            if (!this.worldObj.isRemote) {
//                Vector3d lookVec = baseLogic.getCannonDir();
//                Calculater.transformVecforMinecraft(lookVec);
//                HMGPacketHandler.INSTANCE.sendToAll(new PacketPlaysound(this, "gvcmob:gvcmob.120mmFire", 1, 5));
//                if (this.getEntityData().getFloat("GunshotLevel") < 0.1)
//                    soundedentity.add(this);
//                this.getEntityData().setFloat("GunshotLevel", 5);
//                HMGEntityBulletExprode var3 = new HMGEntityBulletExprode(this.worldObj, this, 600, 6.5f, 3, 5.0F, false);
//                var3.gra = (float) (0.029f / cfg_defgravitycof);
//                var3.setLocationAndAngles(
//                        this.posX + Vec_transformedbybody.x,
//                        this.posX + Vec_transformedbybody.y,
//                        this.posX + Vec_transformedbybody.z,
//                        baseLogic.turretrotationYaw, baseLogic.turretrotationPitch);
//                var3.setThrowableHeading(lookVec.x,lookVec.y,lookVec.z,8,2);
//                var3.canbounce = false;
//                var3.fuse = 0;
//                this.worldObj.spawnEntityInWorld(var3);
//                {
//                    PacketSpawnParticle flash = new PacketSpawnParticle(
//                            this.posX + Vec_transformedbybody.x + lookVec.x * 6,
//                            this.posY + Vec_transformedbybody.y + lookVec.y * 6,
//                            this.posZ + Vec_transformedbybody.z + lookVec.z * 6,
//                            toDegrees(-atan2(lookVec.x, lookVec.z)),
//                            toDegrees(-asin(lookVec.y)), 100, "CannonMuzzleFlash", true);
//                    flash.fuse = 3;
//                    flash.scale = 5;
//                    flash.id = 100;
//                    HMGPacketHandler.INSTANCE.sendToAll(flash);
//                }
//            }
////			if (!this.worldObj.isRemote) {
////				this.worldObj.createExplosion(this, this.posX,
////						this.posY + 2.2D,this.posZ, 0.0F, false);
////			}
//        }
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
//        if(fireCycle1 <0){
//            fireCycle1 = 100;
//
//
//            if (!this.worldObj.isRemote) {
//
//                Vector3d lookVec = baseLogic.getCannonDir();
//                Calculater.transformVecforMinecraft(lookVec);
//
//
//
//                Vector3d Vec_transformedbybody = baseLogic.getTransformedVector_onturret(cannonpos,turretYawCenterpos);
//
//                Calculater.transformVecforMinecraft(Vec_transformedbybody);
//
//                HMGPacketHandler.INSTANCE.sendToAll(new PacketPlaysound(this,"gvcmob:gvcmob.120mmFire",1,10));
//                this.getEntityData().setFloat("GunshotLevel",10);
//                soundedentity.add(this);
//                if(this.riddenByEntity != null){
//
//                    soundedentity.add(this.riddenByEntity);
//                    HMGEntityBulletExprode var3 = new HMGEntityBulletExprode(this.worldObj, riddenByEntity, 600, 6.5f, 3, 5.0F, false);
//                    var3.gra = (float) (0.029f / cfg_defgravitycof);
//                    var3.setLocationAndAngles(this.posX + Vec_transformedbybody.x, this.posY + Vec_transformedbybody.y, this.posZ + Vec_transformedbybody.z,
//                            baseLogic.turretrotationYaw, baseLogic.turretrotationPitch);
//                    var3.setThrowableHeading(lookVec.x, lookVec.y, lookVec.z, 8, 0.5F);
//                    var3.canbounce = false;
//                    var3.fuse = 0;
//                    this.worldObj.spawnEntityInWorld(var3);
//                }
//
//
//
//                {
//                    PacketSpawnParticle flash = new PacketSpawnParticle(
//                            this.posX + Vec_transformedbybody.x + lookVec.x*6,
//                            this.posY + Vec_transformedbybody.y + lookVec.y*6,
//                            this.posZ + Vec_transformedbybody.z + lookVec.z*6,
//                            toDegrees(-atan2(lookVec.x,lookVec.z)),
//                            toDegrees(-asin(lookVec.y)),100,"CannonMuzzleFlash",true);
//                    flash.fuse = 3;
//                    flash.scale = 5;
//                    flash.id = 100;
//                    HMGPacketHandler.INSTANCE.sendToAll(flash);
//                }
//            }
////			if (!this.worldObj.isRemote) {
////				this.worldObj.createExplosion(this, this.posX,
////						this.posY + 2.2D,this.posZ, 0.0F, false);
////			}
//        }
	}
	public void subFire(Entity target){
		if(hatchprogress > 8 && rand.nextInt(9) == 0) {
			Vector3d temp = new Vector3d(mainTurret.pos);
			Vector3d temphatchpos = new Vector3d(subturretpos);
			Vector3d temp2 = mainTurret.getGlobalVector_fromLocalVector_onTurretPoint(temphatchpos);
			temp.add(temp2);
			transformVecforMinecraft(temp);
//			System.out.println(temp);
			GVCEntityAPCSpawn var30 = new GVCEntityAPCSpawn(this.worldObj);
			var30.setThrowableHeading((rnd.nextInt(100) - 50) / 50, 1, (rnd.nextInt(100) - 50) / 50, 1.0F, 12.0F);
			this.worldObj.spawnEntityInWorld(var30);
			var30.setPosition(temp.x,
					temp.y,
					temp.z);
		}
		if(hatchprogress<16)hatchprogress+=2;
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
	public boolean isConverting() {
		return false;
	}
	public void readEntityFromNBT(NBTTagCompound p_70037_1_)
	{
		super.readEntityFromNBT(p_70037_1_);
		baseLogic.turretrotationYaw 			= p_70037_1_.getFloat("turretrotationYaw");
		baseLogic.turretrotationPitch 		= p_70037_1_.getFloat("turretrotationPitch");
		baseLogic.bodyrotationYaw 			= p_70037_1_.getFloat("bodyrotationYaw");
		baseLogic.bodyrotationPitch 			= p_70037_1_.getFloat("bodyrotationPitch");

	}
	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	public void writeEntityToNBT(NBTTagCompound p_70014_1_)
	{
		super.writeEntityToNBT(p_70014_1_);
		p_70014_1_.setFloat("turretrotationYaw",baseLogic.turretrotationYaw);
		p_70014_1_.setFloat("turretrotationPitch",baseLogic.turretrotationPitch);
		p_70014_1_.setFloat("bodyrotationYaw",	baseLogic.bodyrotationYaw);
		p_70014_1_.setFloat("bodyrotationPitch",baseLogic.bodyrotationPitch);

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
	public void setLocationAndAngles(double x, double y, double z, float yaw, float pitch){
		super.setLocationAndAngles(x,y,z,yaw,pitch);
		baseLogic.setLocationAndAngles(yaw,pitch);
	}

	public boolean canBePushed()
	{
		return false;
	}
}
