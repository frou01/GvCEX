package hmggvcmob.entity.guerrilla;


import hmggvcmob.entity.IdriveableVehicle;
import hmggvcutil.GVCUtils;
import hmggvcmob.entity.GVCEx;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.*;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import static hmggvcutil.GVCUtils.fn_rpg7;
import static hmggvcmob.GVCMobPlus.cfg_guerrillasrach;

public class GVCEntityHeli extends EntityGBase implements IdriveableVehicle
{
	// public int type;
	public int soundtick = 0;
	
    public GVCEntityHeli(World par1World)
    {
        super(par1World);
        this.setSize(4F, 4F);
		canuseAlreadyPlacedGun = false;
    }
	public void addRandomArmor()
	{
		super.addRandomArmor();
		this.setCurrentItemOrArmor(0, new ItemStack(fn_rpg7));
		if(rnd.nextInt(2) == 0) {
			GVCEntityGuerrillaMG entityskeleton = new GVCEntityGuerrillaMG(this.worldObj);
			entityskeleton.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
			entityskeleton.addRandomArmor();
			this.worldObj.spawnEntityInWorld(entityskeleton);
			entityskeleton.ridingEntity = this;
			this.riddenByEntity = entityskeleton;
		}else{
			GVCEntityGuerrillaRPG entityskeleton = new GVCEntityGuerrillaRPG(this.worldObj);
			entityskeleton.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
			entityskeleton.addRandomArmor();
			this.worldObj.spawnEntityInWorld(entityskeleton);
			entityskeleton.ridingEntity = this;
			this.riddenByEntity = entityskeleton;
		}
	}
    public void updateRiderPosition() {
		if (this.riddenByEntity != null) {
			this.riddenByEntity.setPosition(this.posX,
					this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset(), this.posZ);
		}
	}
    public double getMountedYOffset() {
		return -0.0D;
	}
    

	protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0D);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(90.0D);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(cfg_guerrillasrach);
        this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(30.0D);
    }



	public boolean attackEntityFrom(DamageSource source, float par2)
	{
		if(this.riddenByEntity != null && this.riddenByEntity == source.getEntity()) {
			return false;
		}
		return super.attackEntityFrom(source,par2);
	}
    
//    protected void rotemodel(float f){
//		this.rotationYawHead = this.rotationYaw + f;
//		this.rotationYaw = this.rotationYawHead + f;
//		this.prevRotationYaw = this.prevRotationYawHead + f;
//		this.prevRotationYawHead = this.prevRotationYawHead + f;
//		this.renderYawOffset = this.prevRotationYawHead + f;
//    	//this.rotation =this.prevRotationYawHead + f;
//	}
    public boolean getCanSpawnHere(){
    	return super.getCanSpawnHere() && worldObj.canBlockSeeTheSky((int)posX,(int)posY,(int)posZ);
	}
    public void onUpdate()
    {

    	super.onUpdate();
//    	if(this.getAttackTarget() instanceof EntityGBases){
//    		this.setAttackTarget(null);
//		}
//    	this.vehicle = true;
//    	this.opentop = false;
//    	float f1 = this.rotationYawHead * (2 * (float) Math.PI / 360);
//		float sp = 0.04F;
//		this.overlayhight = 0.0F;
//		this.overlayhight_3 = 0.2F;
//		this.ammo1 = 2;
//		this.ammo2 = 2;
//		this.ammo_2 = 4;
//		float turnspeed = 2F;
//		{
//			++this.soundtick;
//			if(this.soundtick > 2){
//				this.playSound("gvcmob:gvcmob.heli", 4F, 1.0F);
//				this.soundtick = 0;
//			}
//		}
//		{
////			double x1 = 0;
////			double z1 = 0;
////			x1 -= MathHelper.sin(this.rotationYawHead * 0.01745329252F) * 2.4;
////			z1 += MathHelper.cos(this.rotationYawHead * 0.01745329252F) * 2.4;
////			x1 -= MathHelper.sin(this.rotationYawHead * 0.01745329252F -1.3F) * 1.4;
////			z1 += MathHelper.cos(this.rotationYawHead * 0.01745329252F -1.3F) * 1.4;
////			double x2 = 0;
////			double z2 = 0;
////			x2 -= MathHelper.sin(this.rotationYawHead * 0.01745329252F) * 2.4;
////			z2 += MathHelper.cos(this.rotationYawHead * 0.01745329252F) * 2.4;
////			x2 -= MathHelper.sin(this.rotationYawHead * 0.01745329252F +1.3F) * 1.4;
////			z2 += MathHelper.cos(this.rotationYawHead * 0.01745329252F +1.3F) * 1.4;
////			this.worldObj.spawnParticle("cloud", this.posX-x1, this.posY + 1.6D, this.posZ-z1, 0.0D, 0.0D, 0.0D);
////			this.worldObj.spawnParticle("cloud", this.posX-x2, this.posY + 1.6D, this.posZ-z2, 0.0D, 0.0D, 0.0D);
////			if(this.getHealth() <= this.getMaxHealth()/2){
////				if(this.getHealth() <= this.getMaxHealth()/4){
////					this.worldObj.spawnParticle("smoke", this.posX-2, this.posY + 2D, this.posZ+2, 0.0D, 0.0D, 0.0D);
////					this.worldObj.spawnParticle("smoke", this.posX+2, this.posY + 2D, this.posZ-1, 0.0D, 0.0D, 0.0D);
////					int rx = this.worldObj.rand.nextInt(5);
////					int rz = this.worldObj.rand.nextInt(5);
////					this.worldObj.spawnParticle("flame", this.posX-2+rx, this.posY + 2D, this.posZ-2+rz, 0.0D, 0.0D, 0.0D);
////					this.worldObj.spawnParticle("flame", this.posX-2+rx, this.posY + 2D, this.posZ-2+rz, 0.0D, 0.0D, 0.0D);
////				}else{
////					this.worldObj.spawnParticle("smoke", this.posX-2, this.posY + 2D, this.posZ+2, 0.0D, 0.0D, 0.0D);
////					this.worldObj.spawnParticle("smoke", this.posX+2, this.posY + 2D, this.posZ-1, 0.0D, 0.0D, 0.0D);
////					int rx = this.worldObj.rand.nextInt(5);
////					int rz = this.worldObj.rand.nextInt(5);
////					this.worldObj.spawnParticle("smoke", this.posX-2+rx, this.posY + 2D, this.posZ-2+rz, 0.0D, 0.0D, 0.0D);
////				}
////			}
////		}
////		if(this.rotationYawHead > 360F || this.rotationYawHead < -360F){
////			this.rotation = 0;
////			this.rotationp = 0;
////			this.rotationYawHead = 0;
////			this.rotationYaw = 0;
////			this.prevRotationYaw = 0;
////			this.prevRotationYawHead = 0;
////			this.renderYawOffset = 0;
//		}
//		if(this.rotationYawHead > 180F){
//			this.rotation = -179F;
//			this.rotationp = -179F;
//			this.rotationYawHead = -179F;
//			this.rotationYaw = -179F;
//			this.prevRotationYaw = -179F;
//			this.prevRotationYawHead = -179F;
//			this.renderYawOffset = -179F;
//		}
//		if(this.rotationYawHead < -180F){
//			this.rotation = 179F;
//			this.rotationp = 179F;
//			this.rotationYawHead = 179F;
//			this.rotationYaw = 179F;
//			this.prevRotationYaw = 179F;
//			this.prevRotationYawHead = 179F;
//			this.renderYawOffset = 179F;
//		}
//		this.motionY *= 0.7;
//

		if(this.getHealth() > 0.0F)
    	{// 1
			int genY = this.worldObj.getHeightValue((int) this.posX, (int) this.posZ) + 15;
			if (this.posY < genY) {
				this.motionY += 0.4D;
			}
			this.rotationYaw = this.rotationYawHead - 45;
			EntityLivingBase target = this.getAttackTarget();
			double dist = 0;
			if(target != null){
				this.getLookHelper().setLookPositionWithEntity(target,90,90);
				dist = this.getDistanceSqToEntity(target);
				Vec3 look = Vec3.createVectorHelper(target.posX - this.posX , 0, target.posZ - this.posZ);
				look.normalize();
				if(dist < 1600){
					this.motionX += -look.xCoord * 0.025;
					this.motionZ += -look.yCoord * 0.025;
				}else{
					this.motionX += +look.xCoord * 0.025;
					this.motionZ += +look.yCoord * 0.025;
				}
				this.motionX +=  look.zCoord * 0.018;
				this.motionZ += -look.xCoord * 0.018;
			}
			this.motionX *= 0.4;
			this.motionY *= 0.4;
			this.motionZ *= 0.4;
			++this.soundtick;
			if(this.soundtick > 6){
				this.playSound("gvcmob:gvcmob.heli", 4F, 1F);
				this.soundtick = 0;
			}
			if(!worldObj.isRemote){
				for(int x = (int)this.boundingBox.minX-1;x<=this.boundingBox.maxX;x++){
					for(int y = (int)this.boundingBox.minY-1;y<=this.boundingBox.maxY;y++){
						for(int z = (int)this.boundingBox.minZ-1;z<=this.boundingBox.maxZ;z++){
							Block collidingblock = worldObj.getBlock(x,y,z);
							if(collidingblock.getMaterial() == Material.leaves || collidingblock.getMaterial() == Material.wood || collidingblock.getMaterial() == Material.glass || collidingblock.getMaterial() == Material.cloth){
								worldObj.setBlockToAir(x,y,z);
							}
						}
					}
				}
			}
//			Entity entity = null;
//			List<Entity> llist = this.worldObj.getEntitiesWithinAABBExcludingEntity(this,
//					this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(60D, 60D, 60D));
//			if (llist != null) {
//				for (int lj = 0; lj < llist.size(); lj++) {
//					Entity entity1 = (Entity) llist.get(lj);
//					if (entity1.canBeCollidedWith()) {
//						boolean flag = this.getEntitySenses().canSee(entity1);
//						if (this.CanAttack(entity1) && entity1 != null && flag)
//						//if (entity1 instanceof EntityPlayer && entity1 != null )
//						{
//							{
//								double d5 = entity1.posX - this.posX;
//								double d7 = entity1.posZ - this.posZ;
//								double d6 = entity1.posY - this.posY;
//								double d1 = this.posY - (entity1.posY);
//					            double d3 = (double)MathHelper.sqrt_double(d5 * d5 + d7 * d7);
//					            float f11 = (float)(-(Math.atan2(d1, d3) * 180.0D / Math.PI));
//					            this.rotation = this.rote =  -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
//								this.rotationp = this.rotationPitch = -f11 + 10;
//								if(this.rote > 180F){
//									this.rote = -179F;
//								}
//								if(this.rote < -180F){
//									this.rote = 179F;
//								}
//								{
//					            	this.rotationYaw = this.rotation;
//					            	this.rotationYawHead = this.rotation;
//					            }
//								int genY = this.worldObj.getHeightValue((int)entity1.posX, (int)entity1.posZ)+10;
//						    	if (this.posY < genY)
//						        {
//						    		this.motionY += 0.2D;
//						        }
//
//								double ddx = Math.abs(d5);
//								double ddz = Math.abs(d7);
//								if ((ddx > 40 || ddz > 40)) {
//						            MoveHelper goflag = new MoveHelper(this, (int)entity1.posX, (int)entity1.posY, (int)entity1.posZ, 1D);
//						    		goflag.gotank();
//								}else{
//									this.motionX += MathHelper.sin(this.rotationYawHead * 0.01745329252F - 1.57F) * 0.030;
//									this.motionZ -= MathHelper.cos(this.rotationYawHead * 0.01745329252F - 1.57F) * 0.030;
//								}
//								this.AttackTask((EntityLivingBase) entity1, ddx, ddz);
//								this.target = true;
//						}
//							break;
//						}
//					}
//				}
//			}
//
			
		} // 1
    }
    
    
//    public void AttackTask(EntityLivingBase entity1, double x, double z){
//
//    	//if (itemstack != null && itemstack.getItem() == mod_GVCR.gun_ak74)
//    	{//3
//			//ItemGunBase gun = (ItemGunBase) itemstack.getItem();
//			//gun.flash = false;
//			if (cooltime > this.ammo1) {// 2
//				//if ((x < 30 && z < 30))
//				{
//					boolean flag = this.getEntitySenses().canSee(entity1);
//					double ddy = Math.abs(entity1.posY - this.posY);
//					//if (flag)
//					if(ddy < 12)
//					{
//						{
//							double xx11 = 0;
//							double zz11 = 0;
//							xx11 -= MathHelper.sin(this.rotation * 0.01745329252F) * 3.5;
//							zz11 += MathHelper.cos(this.rotation * 0.01745329252F) * 3.5;
//							Vec3 look = this.getLookVec();
//							double kaku = look.yCoord * 4;
//							{
//								GVCEntityBulletGe  var3 = new GVCEntityBulletGe(this.worldObj, this);
//								var3.Bdamege = 5;
//								var3.gra = 0.025;
//								var3.friend = this;
//								var3.exlevel = 2.0F;
//								var3.ex = false;
//								var3.setLocationAndAngles(this.posX + xx11, this.posY + 2.2D + kaku, this.posZ + zz11,
//										this.rotationYaw, this.rotationPitch);
//								double var4 = entity1.posX - this.posX;
//								double var6 = entity1.posY + (double) entity1.getEyeHeight()
//										+ 0.200000023841858D - var3.posY;
//								double var8 = entity1.posZ - this.posZ;
//								float var10 = MathHelper.sqrt_double(var4 * var4 + var8 * var8) * 0.01F;
//								var3.setThrowableHeading(var4, var6 + (double) var10, var8, 2.0F, 20.0F);
//								if (!this.worldObj.isRemote) {
//									this.worldObj.spawnEntityInWorld(var3);
//								}
//								if (GVCMobPlus.cfg_canEjectCartridge) {
//									GVCEntityBulletCartridge var30 = new GVCEntityBulletCartridge(this.worldObj, this, 1);
//									var30.setThrowableHeading(var4, var6 + (double) var10, var8, -0.1F, 12.0F);
//									// this.playSound("gvcguns:gvcguns.grenade", 1.0F,
//									// 1.5F);
//									this.worldObj.spawnEntityInWorld(var30);
//								}
//							}
//						}
//						this.playSound("gvcguns:gvcguns.fire", 3.0F, 1.0F);
//						if (!this.worldObj.isRemote) {
//							cooltime = 0;
//						}
//					}
//				}
//			} // 2
//			else {
//				if (!this.worldObj.isRemote) {
//					++cooltime;
//				}
//			}
//		}//3
//    }

    protected void onDeathUpdate() {
		++this.deathTicks;
		if(this.deathTicks == 1){
        	//this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 0F, false);
        	GVCEx ex = new GVCEx(this, 3F);
        	ex.Ex();
        }
		if(this.onGround && !this.worldObj.isRemote){
			GVCEx ex = new GVCEx(this, 3F);
        	ex.Ex();
			this.setDead();
			Item i = new ItemStack(Blocks.gold_block, 0).getItem();
	        this.dropItem(i, 1);
		}
		if (this.deathTicks == 200 && !this.worldObj.isRemote) {
			GVCEx ex = new GVCEx(this, 3F);
        	ex.Ex();
			this.setDead();
			Item i = new ItemStack(Blocks.gold_block, 0).getItem();
	        this.dropItem(i, 1);
		}
	}
    
    protected void dropFewItems(boolean par1, int par2)
    {
        int var3;
        int var4;

        var3 = this.rand.nextInt(3 + par2);

        for (var4 = 0; var4 < var3; ++var4)
        {
        	this.entityDropItem(new ItemStack(Blocks.iron_block, 10), 0.0F);
        }

        for (var4 = 0; var4 < var3; ++var4)
        {
        	this.entityDropItem(new ItemStack(Blocks.emerald_block, 5), 0.0F);
        }
        
        for (var4 = 0; var4 < var3; ++var4)
        {
        	this.entityDropItem(new ItemStack(Blocks.redstone_block, 5), 0.0F);
        }
    }

    protected void dropRareDrop(int par1)
    {
        
            this.entityDropItem(new ItemStack(Blocks.beacon, 1), 0.0F);
        
    }
    
    public void onDeath(DamageSource par1DamageSource)
    {
        super.onDeath(par1DamageSource);
        this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 3.5F, false);
        if (!this.worldObj.isRemote)
        {
			GVCEntityGuerrilla entityskeleton = new GVCEntityGuerrilla(this.worldObj);
			entityskeleton.setLocationAndAngles(this.posX+2, this.posY+3, this.posZ+2, this.rotationYaw, 0.0F);
			entityskeleton.setCurrentItemOrArmor(0, new ItemStack(GVCUtils.fn_aks74u));
			this.worldObj.spawnEntityInWorld(entityskeleton);

			GVCEntityGuerrilla entityskeleton2 = new GVCEntityGuerrilla(this.worldObj);
			entityskeleton2.setLocationAndAngles(this.posX-2, this.posY+3, this.posZ+2, this.rotationYaw, 0.0F);
			entityskeleton2.setCurrentItemOrArmor(0, new ItemStack(GVCUtils.fn_aks74u));
			this.worldObj.spawnEntityInWorld(entityskeleton2);
        }
    }
    
	public boolean isConverting() {
		return false;
	}

	@Override
	public int getfirecyclesettings1() {
		return 0;
	}

	@Override
	public int getfirecycleprogress1() {
		return 0;
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
		return 0;
	}

	@Override
	public float getbodyrotationYaw() {
		return 0;
	}

	@Override
	public float getthrottle() {
		return 0;
	}
}
