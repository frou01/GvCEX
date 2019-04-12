package hmgww2.entity;

import hmggvcmob.GVCMobPlus;
import hmggvcmob.ai.AITankAttack;
import hmggvcmob.entity.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;

import static hmggvcmob.GVCMobPlus.proxy;
import static hmggvcmob.event.GVCMXEntityEvent.soundedentity;
import static hmggvcmob.util.Calculater.transformVecByQuat;
import static hmggvcmob.util.Calculater.transformVecforMinecraft;

public abstract class EntityBases_Ship extends EntityBases_Tank implements IMultiTurretVehicle
{
	
	float draft = 1.8f;
	TurretObj[] mainturrets;
	TurretObj[] subturrets;
	TurretObj[] SPturrets;
	boolean usingSP = false;
	
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
				double prevmotionX = motionX;
				double prevmotionZ = motionZ;
				this.moveEntity(this.motionX, this.motionY, this.motionZ);
				handleWaterMovement();
				if(!this.isInWater() && isCollidedHorizontally){
					this.moveEntity(0, 1, 0);
					this.moveEntity(prevmotionX/10, 0, prevmotionZ/10);
				}
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
	}
	
	public void updateRiderPosition() {
		if (this.riddenByEntity != null) {
			Vector3d tempplayerPos = new Vector3d(proxy.iszooming() ? zoomingplayerpos:playerpos);
			Vector3d temp = transformVecByQuat(tempplayerPos,baseLogic.bodyRot);
			transformVecforMinecraft(temp);
			temp.add(new Vector3d(this.posX,
					                     this.posY,
					                     this.posZ));
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
	public void tankUpdate(){
		this.stepHeight = 1.5f;
		if(!this.worldObj.isRemote){
			baseLogic.updateServer();
		}else{
			baseLogic.updateClient();
			mode= getMobMode();
		}
		baseLogic.updateCommon();
		if(getmainTurrets() != null)for(TurretObj aturret :getmainTurrets()){
			aturret.update(baseLogic.bodyRot,new Vector3d(this.posX,this.posY,-this.posZ));
		}
		if(getsubTurrets() != null)for(TurretObj aturret :getsubTurrets()){
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
	public void mainFire(Entity target){
		if(getmainTurrets() != null)for(TurretObj aturret :getmainTurrets()){
			if(aturret.ready) {
				aturret.currentEntity = this;
				aturret.fire();
			}
		}
	}
	public void mainFire(){
		if(getmainTurrets() != null)for(TurretObj aturret :getmainTurrets()){
			if(aturret.ready) {
				aturret.currentEntity = this.riddenByEntity;
				aturret.fire();
			}
		}
	}
	public void subFire(Entity target){
		if(getsubTurrets() != null)for(TurretObj aturret :getsubTurrets()){
			if(aturret.ready) {
				aturret.currentEntity = this;
				aturret.fire();
			}
		}
	}
	public void subFire(){
		if(getsubTurrets() != null)for(TurretObj aturret :getsubTurrets()){
			if(aturret.ready) {
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
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(4096.0D);
		this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(30.0D);
	}
}
