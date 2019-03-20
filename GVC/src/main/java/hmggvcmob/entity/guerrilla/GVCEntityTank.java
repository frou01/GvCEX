package hmggvcmob.entity.guerrilla;


import hmggvcmob.tile.TileEntityFlag;
import hmggvcmob.ai.AITankAttack;
import hmggvcmob.entity.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;

import static hmggvcmob.GVCMobPlus.proxy;
import static hmggvcmob.event.GVCMXEntityEvent.soundedentity;
import static hmggvcmob.util.Calculater.CalculateGunElevationAngle;
import static hmggvcmob.util.Calculater.transformVecforMinecraft;
import static java.lang.Math.abs;

public class GVCEntityTank extends EntityGBase implements ITank
{
	int count_for_reset;
	public double angletime;
	public int fireCycle1;
	public int fireCycle2;
	public int cooltime;
	public int magazine;

	public float subturretrotationYaw;
	public float subturretrotationPitch;

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

	public int mgMagazine;
	public int mgReloadProgress;
	public TankBaseLogic baseLogic = new TankBaseLogic(this,0.2f,2.0f,false,"gvcmob:gvcmob.TankTrack");
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
	public int soundtick = 0;

	public GVCEntityTank(World par1World)
	{
		super(par1World);
		this.tasks.removeTask(aiSwimming);
		this.setSize(1.5F, 1.6F);
		nboundingbox = new ModifiedBoundingBox(-20,-20,-20,20,20,20,
				0,1.1,0,3,2.2,9);
		nboundingbox.rot.set(baseLogic.bodyRot);
		proxy.replaceBoundingbox(this,nboundingbox);
		nboundingbox.centerRotX = 0;
		nboundingbox.centerRotY = 0;
		nboundingbox.centerRotZ = 0;
		aiTankAttack = new AITankAttack(this,1600,400,10,50);
		this.tasks.addTask(1,aiTankAttack);
		viewWide = 3.15f;
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

		canusePlacedGun = false;
		canuseAlreadyPlacedGun = false;
		turrets = new TurretObj[]{mainTurret,subTurret};
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
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(300.0D);
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(80.0D);
		this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(30.0D);
	}

	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(2, new Integer((int)0));
		this.dataWatcher.addObject(3, new Integer((int)0));
		this.dataWatcher.addObject(24, new Float((int)0));
		this.dataWatcher.addObject(25, new Float((int)0));
		this.dataWatcher.addObject(26, new Float((int)0));
		this.dataWatcher.addObject(27, new Float((int)0));
		this.dataWatcher.addObject(28, new Float((int)0));
		this.dataWatcher.addObject(29, new Float((int)0));
		this.dataWatcher.addObject(30, new Float((int)0));
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

	public boolean attackEntityFrom(DamageSource source, float par2)
	{

		if (this.riddenByEntity == source.getEntity()) {
			return false;
		} else {
			if (par2 <= 25) {
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
	
	protected void dropFewItems(boolean par1, int par2)
	{
		int var3;
		int var4;
		
		var3 = 1 + this.rand.nextInt(3);
		
		this.entityDropItem(new ItemStack(Blocks.iron_block, var3), 0.0F);
		
		var3 = this.rand.nextInt(2);
		this.entityDropItem(new ItemStack(Blocks.emerald_block, var3), 0.0F);
		
		var3 = this.rand.nextInt(3);
		this.entityDropItem(new ItemStack(Blocks.redstone_block, var3), 0.0F);
		
		var3 = this.rand.nextInt(5);
		if(var3 == 0) this.entityDropItem(new ItemStack(Blocks.beacon, 1), 0.0F);
		var3 = this.rand.nextInt(200);
		if(var3 == 0) this.entityDropItem(new ItemStack(Blocks.diamond_block, 1), 0.0F);
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
				fireCycle1--;
				fireCycle2--;
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
		subTurret.update(baseLogic.bodyRot,new Vector3d(this.posX,this.posY,-this.posZ));
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

	public void cannonFire(){
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
		subTurret.currentEntity = this;
		if(subTurret.aimToEntity(target)){
			subTurret.fire();
		}
		subturretrotationYaw = (float) subTurret.turretrotationYaw;
		subturretrotationPitch = (float) subTurret.turretrotationPitch;
	}
	public void mgFire(){

		subTurret.currentEntity = riddenByEntity;
		subTurret.fire();
//        Quat4d turretyawrot = new Quat4d(0,0,0,1);
//
//        Vector3d axisy = Calculater.transformVecByQuat(new Vector3d(0,1,0), turretyawrot);
//        AxisAngle4d axisyangled = new AxisAngle4d(axisy, toRadians(baseLogic.turretrotationYaw)/2);
//        turretyawrot = Calculater.quatRotateAxis(turretyawrot,axisyangled);
//
//
//
//
//        {
//            if (fireCycle2 < 0) {
//
//
//                Vector3d lookVec = new Vector3d(0,0,1);
//
//
//                Quat4d gun = new Quat4d(0, 0, 0, 1);
//                Vector3d axisY = transformVecByQuat(baseLogic.unitY, gun);
//                AxisAngle4d axisxangledY = new AxisAngle4d(axisY, toRadians(subturretrotationYaw) / 2);
//                gun = quatRotateAxis(gun, axisxangledY);
//
//                Vector3d axisX = transformVecByQuat(baseLogic.unitX, gun);
//                AxisAngle4d axisxangledX = new AxisAngle4d(axisX, toRadians(-subturretrotationPitch) / 2);
//                gun = quatRotateAxis(gun, axisxangledX);
//
//                lookVec = transformVecByQuat(lookVec,gun);
//                lookVec = transformVecByQuat(lookVec,turretyawrot);
//                lookVec = transformVecByQuat(lookVec,baseLogic.bodyRot);
//
//                Calculater.transformVecforMinecraft(lookVec);
//
//
//
//
//                Vector3d Vec_transformed = baseLogic.getTransformedVector_onturret(subturretpos,turretYawCenterpos);
//
//                Calculater.transformVecforMinecraft(Vec_transformed);
//
//
//                fireCycle2 = 1;
//                HMGEntityBullet var3 = new HMGEntityBullet(worldObj, this, 20, 8F, 3.0F);
//                var3.gra = 0.05f;
//                var3.setLocationAndAngles(this.posX + Vec_transformed.x, this.posY + Vec_transformed.y, this.posZ + Vec_transformed.z,
//                        riddenByEntity.getRotationYawHead(), riddenByEntity.rotationPitch);
//                var3.setHeadingFromThrower(riddenByEntity.rotationPitch, riddenByEntity.getRotationYawHead(), 0, 5.8f, 3.0F);
//                var3.setThrowableHeading(lookVec.x,lookVec.y,lookVec.z,5.8f,3.0F);
//                if (!this.worldObj.isRemote) {
//                    this.worldObj.spawnEntityInWorld(var3);
//                    HMGPacketHandler.INSTANCE.sendToAll(new PacketSpawnParticle(this.posX + Vec_transformed.x, this.posY + Vec_transformed.y, this.posZ + Vec_transformed.z, 100));
//                    this.playSound("handmadeguns:handmadeguns.HeavyMachineGun", 5.0F, 1.0F);
//                    if(this.getEntityData().getFloat("GunshotLevel")<0.1)
//                        soundedentity.add(this);
//                    this.getEntityData().setFloat("GunshotLevel",5);
//                }
//            }
//        }
	}

	public void mgAim(float targetyaw,float targetpitch){
		subTurret.currentEntity = this;
		subTurret.aimtoAngle(targetyaw,targetpitch);
		subturretrotationYaw = (float) subTurret.turretrotationYaw;
		subturretrotationPitch = (float) subTurret.turretrotationPitch;
//        Quat4d turretyawrot = new Quat4d(0,0,0,1);
//
//        Vector3d axisy = Calculater.transformVecByQuat(new Vector3d(0,1,0), turretyawrot);
//        AxisAngle4d axisyangled = new AxisAngle4d(axisy, toRadians(baseLogic.turretrotationYaw)/2);
//        turretyawrot = Calculater.quatRotateAxis(turretyawrot,axisyangled);
//        Quat4d gunnerRot = new Quat4d(0, 0, 0, 1);
//
//
//        Vector3d axisY = transformVecByQuat(baseLogic.unitY, gunnerRot);
//        AxisAngle4d axisxangledY = new AxisAngle4d(axisY, toRadians(targetyaw) / 2);
//        gunnerRot = quatRotateAxis(gunnerRot, axisxangledY);
//
//        Vector3d axisX = transformVecByQuat(baseLogic.unitX, gunnerRot);
//        AxisAngle4d axisxangledX = new AxisAngle4d(axisX, toRadians(-targetpitch) / 2);
//        gunnerRot = quatRotateAxis(gunnerRot, axisxangledX);
//
//
//        Vector3d lookVec = new Vector3d(0, 0, -1);
//
//        lookVec = transformVecByQuat(lookVec, gunnerRot);
//
//        Quat4d temp = new Quat4d();
//
//        temp.inverse(baseLogic.bodyRot);
//        lookVec = transformVecByQuat(lookVec, temp);
//
//        temp.inverse(turretyawrot);
//        lookVec = transformVecByQuat(lookVec, temp);
//
//        subturretrotationYaw = (float) toDegrees(atan2(lookVec.x,lookVec.z));
//        subturretrotationPitch = (float) toDegrees(asin(lookVec.y));
//        if(subturretrotationPitch<-20){
//            subturretrotationPitch = -20;
//        }
	}

	public boolean aimToTarget(Entity target){
		return baseLogic.aimMainTurret_toTarget(target);
	}
//    public void aimToTarget(){
////        baseLogic.turretrotationYaw = wrapAngleTo180_float(baseLogic.turretrotationYaw);
////        float targetrote = wrapAngleTo180_float(this.riddenByEntity.getRotationYawHead());
////        boolean result1;
////        if((baseLogic.turretrotationYaw - targetrote > 5&&baseLogic.turretrotationYaw - targetrote < 355 )||(baseLogic.turretrotationYaw - targetrote < -5&&baseLogic.turretrotationYaw - targetrote > -355)){
////            if ((targetrote - baseLogic.turretrotationYaw > 0 && targetrote - baseLogic.turretrotationYaw < 180)|| (targetrote - baseLogic.turretrotationYaw <-180 )) {
////                baseLogic.turretrotationYaw += 5;
////                this.playSound("gvcguns:gvcguns.zye", 3.0F, 1F);
////                if(this.getEntityData().getFloat("GunshotLevel")<0.1)
////                    soundedentity.add(this);
////                this.getEntityData().setFloat("GunshotLevel",3);
////            } else {
////                baseLogic.turretrotationYaw -= 5;
////                this.playSound("gvcguns:gvcguns.zye", 3.0F, 1F);
////                if(this.getEntityData().getFloat("GunshotLevel")<0.1)
////                    soundedentity.add(this);
////                this.getEntityData().setFloat("GunshotLevel",3);
////            }
////        }else{
////            baseLogic.turretrotationYaw = targetrote;
////        }
////        baseLogic.turretrotationPitch = wrapAngleTo180_float(baseLogic.turretrotationPitch);
////        float targetpitch = this.riddenByEntity.rotationPitch;
////        if(targetpitch <-15){
////            targetpitch =-15;
////        }else if(targetpitch >15){
////            targetpitch =15;
////        }
////        if((baseLogic.turretrotationPitch - targetpitch > 3&&baseLogic.turretrotationPitch - targetpitch < 357 )||(baseLogic.turretrotationPitch - targetpitch < -3&&baseLogic.turretrotationPitch - targetpitch > -357)){
////            if ((targetpitch - baseLogic.turretrotationPitch > 0 && targetpitch - baseLogic.turretrotationPitch < 180)|| (targetpitch - baseLogic.turretrotationPitch <-180 )) {
////                baseLogic.turretrotationPitch += 3;
////                this.playSound("gvcguns:gvcguns.zye", 3.0F, 1F);
////                if(this.getEntityData().getFloat("GunshotLevel")<0.1)
////                    soundedentity.add(this);
////                this.getEntityData().setFloat("GunshotLevel",3);
////            } else {
////                baseLogic.turretrotationPitch -= 3;
////                this.playSound("gvcguns:gvcguns.zye", 3.0F, 1F);
////                if(this.getEntityData().getFloat("GunshotLevel")<0.1)
////                    soundedentity.add(this);
////                this.getEntityData().setFloat("GunshotLevel",3);
////            }
////        }else{
////            baseLogic.turretrotationPitch = targetpitch;
////        }
////        if(baseLogic.turretrotationPitch <-15){
////            baseLogic.turretrotationPitch =-15;
////        }else if(baseLogic.turretrotationPitch >15){
////            baseLogic.turretrotationPitch =15;
////        }
//    }

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
		if(this.deathTicks == 150)
			this.setDead();
		else
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

		this.subturretrotationYaw 		= p_70037_1_.getFloat("subturretrotationYaw");
		this.subturretrotationPitch 	= p_70037_1_.getFloat("subturretrotationPitch");
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
		p_70014_1_.setFloat("subturretrotationYaw",this.subturretrotationYaw);
		p_70014_1_.setFloat("subturretrotationPitch",this.subturretrotationPitch);
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
		mainTurret.update(baseLogic.bodyRot,new Vector3d(this.posX,this.posY,-this.posZ));
	}
}
