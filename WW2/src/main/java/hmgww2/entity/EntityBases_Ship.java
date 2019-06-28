package hmgww2.entity;

import hmvehicle.entity.parts.*;
import hmvehicle.entity.parts.logics.MultiRiderLogics;
import hmvehicle.entity.parts.turrets.TurretObj;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import javax.vecmath.Vector3d;

import static handmadeguns.Util.Utils.getmovingobjectPosition_forBlock;
import static hmvehicle.HMVehicle.proxy_HMVehicle;

public abstract class EntityBases_Ship extends EntityBases_Tank implements IShip , ITank , ImultiRidable
{
	
	float draft = 1.8f;
	TurretObj[] mainturrets;
	TurretObj[] subturrets;
	TurretObj[] SPturrets;
	public boolean usingSP = false;
	public boolean prevusingSP = true;
	boolean issubmarine;
	
	public String modeBell_normal = "hmgww2:hmgww2.WeaponBell";
	public String modeBell_SP = "hmgww2:hmgww2.WeaponBell";
	
	boolean fstopper = false;
	public void moveEntityWithHeading(float p_70612_1_, float p_70612_2_)
	{
		
		if(!worldObj.isRemote) {
			if (this.isInWater() || ishittingWater()) {
				if(this.isInWater()){
					this.motionY += 0.02D;
				}else {
					this.motionY -= 0.01D;
				}
				
				float f2;
				f2 = this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ)).slipperiness * 0.91F;
				float f3 = 0.16277136F / (f2 * f2 * f2);
				float f4;
				f4 = this.getAIMoveSpeed() * f3;
				
				this.moveFlying(p_70612_1_, p_70612_2_, f4);
				this.moveEntity(this.motionX, this.motionY, this.motionZ);
				handleWaterMovement();
				this.motionX *= 0.800000011920929D;
				this.motionY *= 0.800000011920929D;
				this.motionZ *= 0.800000011920929D;
				setAir(0);
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
					f2 = 0;
				}
				
				float f3 = 0.16277136F / (f2 * f2 * f2);
				float f4;
				
				if (this.onGround) {
					f4 = this.getAIMoveSpeed() * f3;
				} else {
					f4 = this.jumpMovementFactor;
				}
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
	public boolean handleWaterMovement()
	{
		if (this.worldObj.handleMaterialAcceleration(this.boundingBox.expand(0.0D, -0.4000000059604645D, 0.0D).contract(0.001D, 0.001D, 0.001D).offset(0,draft,0), Material.water, this))
		{
			if (!this.inWater)
			{
				float f = MathHelper.sqrt_double(this.motionX * this.motionX * 0.20000000298023224D + this.motionY * this.motionY + this.motionZ * this.motionZ * 0.20000000298023224D) * 0.2F;
				
				if (f > 1.0F)
				{
					f = 1.0F;
				}
				
				this.playSound(this.getSplashSound(), f, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F);
				float f1 = (float)MathHelper.floor_double(this.boundingBox.minY + draft);
				int i;
				float f2;
				float f3;
				
				for (i = 0; (float)i < 1.0F + this.width * 20.0F; ++i)
				{
					f2 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width;
					f3 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width;
					this.worldObj.spawnParticle("bubble", this.posX + (double)f2, (double)(f1 + draft), this.posZ + (double)f3, this.motionX, this.motionY - (double)(this.rand.nextFloat() * 0.2F), this.motionZ);
				}
				
				for (i = 0; (float)i < 1.0F + this.width * 20.0F; ++i)
				{
					f2 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width;
					f3 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width;
					this.worldObj.spawnParticle("splash", this.posX + (double)f2, (double)(f1 + draft), this.posZ + (double)f3, this.motionX, this.motionY, this.motionZ);
				}
			}
			
			this.fallDistance = 0.0F;
			this.inWater = true;
		}
		else
		{
			this.inWater = false;
		}
		
		return this.inWater;
	}
	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(3, Integer.valueOf(0));//mode
		this.dataWatcher.addObject(23, Float.valueOf(0));//draft
	}
	public void setWeaponMode(int ints) {
		this.dataWatcher.updateObject(3, Integer.valueOf(ints));
	}
	public int getWeaponMode() {
		return this.dataWatcher.getWatchableObjectInt(3);
	}
	public void setDraft_dataWatcher(float floats) {
		this.dataWatcher.updateObject(23, Float.valueOf(floats));
	}
	public float getDraft_dataWatcher() {
		return this.dataWatcher.getWatchableObjectFloat(23);
	}
	public boolean ishittingWater()
	{
		boolean inWater = false;
		if (this.worldObj.handleMaterialAcceleration(this.boundingBox.expand(0.0D, -0.4000000059604645D, 0.0D).contract(0.001D, 0.001D, 0.001D).offset(0,0,0), Material.water, this))
		{
			inWater = true;
		}
		else
		{
			inWater = false;
		}
		
		return inWater;
	}
	
	public EntityBases_Ship(World par1World)
	{
		super(par1World);
//		if(par1World.isRemote)
//			HMVPacketHandler.INSTANCE.sendToAll(new HMVPacketPickNewEntity(this.getEntityId()));
		
		
		interval = 32;
	}
	
	public void updateRiderPosition() {
	
	}
	public boolean writeMountToNBT(NBTTagCompound p_98035_1_)
	{
		return false;
	}
	public void writeEntityToNBT(NBTTagCompound tagCompound){
		super.writeEntityToNBT(tagCompound);
//		try {
//			int cnt = 0;
//			for (Entity entity : riddenByEntities) {
//				if(entity != null) tagCompound.setByteArray("RiddenbyUUID" + cnt, fromObject(entity.getUniqueID()));
//				cnt++;
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
	public void readFromNBT(NBTTagCompound tagCompound){
		super.readFromNBT(tagCompound);
	}
	public void onUpdate() {
		if(this.standalone()){
			if(this.getAttackTarget() != null && this.getAttackTarget() instanceof EntityBases_Ship && ((EntityBases_Ship) this.getAttackTarget()).issubmarine){
				usingSP = true;
				setassault();
			}else {
				usingSP = false;
				resetassault();
			}
		}
		if(worldObj.isRemote){
			usingSP = getWeaponMode() == 0;
			if(prevusingSP != usingSP) {
				if (!usingSP) {
					if (modeBell_normal != null) proxy_HMVehicle.playsoundasVehicle_noRepeat(modeBell_normal, 2 * 2 * 16, this, this, 40);
				}else if(modeBell_SP != null)proxy_HMVehicle.playsoundasVehicle_noRepeat(modeBell_SP, 2 * 2 * 16, this, this, 40);
			}
			prevusingSP = usingSP;
			draft = getDraft_dataWatcher();
		}else {
			setWeaponMode(usingSP?0:1);
			setDraft_dataWatcher(draft);
		}
		nboundingbox.update(this.posX,this.posY,this.posZ);
		super.onUpdate();
		baseLogic.updateCommon();
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
		return true;
	}
	public void tankUpdate(){
		this.stepHeight = 1.5f;
		if(!this.worldObj.isRemote){
			baseLogic.updateServer();
		}else{
			baseLogic.updateClient();
			mode= getMobMode();
		}
		if(getTurrets() != null)for(TurretObj aturret :getTurrets()){
			aturret.update(baseLogic.bodyRot,new Vector3d(this.posX,this.posY,-this.posZ));
		}
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
	}
	
	
	@Override
	public TurretObj[] getmainTurrets() {
		return mainturrets;
	}
	
	@Override
	public TurretObj[] getsubTurrets() {
		return usingSP? SPturrets:subturrets;
	}
	public void mainFireToTarget(Entity target){
		if(getmainTurrets() != null)for(TurretObj aturret :getmainTurrets()){
			if(aturret.readyaim) {
				aturret.currentEntity = this;
				aturret.fire();
			}
		}
	}
	public void mainFire(){
		if(getmainTurrets() != null)for(TurretObj aturret :getmainTurrets()){
			if(aturret.readyaim) {
				aturret.currentEntity = this.riddenByEntity;
				aturret.fire();
			}
		}
	}
	public void subFireToTarget(Entity target){
		if(!usingSP && getsubTurrets() != null)for(TurretObj aturret :getsubTurrets()){
			if(aturret.readyaim) {
				aturret.currentEntity = this;
				aturret.fire();
			}
		}
	}
	public void subFire(){
		if(getsubTurrets() != null)for(TurretObj aturret :getsubTurrets()){
			if(aturret.readyaim) {
				aturret.currentEntity = this.riddenByEntity;
				aturret.fire();
			}
		}
	}
	
	public void mgAim(float targetyaw,float targetpitch){
	}
	
	public boolean canBePushed() {
		return false;
	}
	
	public void Excontrol1(boolean keystate){
		if(keystate) {
			if (!fstopper) {
				usingSP = !usingSP;
			}
			fstopper = true;
		}else {
			fstopper = false;
		}
	}
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(maxHealth);
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(80);
		this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(30.0D);
	}
	public void setassault(){
	
	}
	public void resetassault(){
	
	}
	public void setPosition(double x, double y, double z)
	{
		if(Double.isInfinite(x))x = 0;
		if(Double.isInfinite(y))y = 0;
		if(Double.isInfinite(z))z = 0;
		if(baseLogic != null)baseLogic.setPosition(x,y,z);
	}
	
	public boolean pickupEntity(Entity p_70085_1_, int StartSeachSeatNum){
		return ((MultiRiderLogics)((HasBaseLogic)this).getBaseLogic()).pickupEntity(p_70085_1_,StartSeachSeatNum);
	}
	
	public float getDraft(){
		return draft;
	}
}
