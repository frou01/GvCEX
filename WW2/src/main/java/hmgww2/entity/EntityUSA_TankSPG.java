package hmgww2.entity;


import java.util.List;

import gvclib.entity.EntityB_Bullet;
import hmgww2.mod_GVCWW2;
import hmgww2.network.WW2MessageKeyPressed;
import hmgww2.network.WW2PacketHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityUSA_TankSPG extends EntityUSABase
{
	// public int type;
	
    public EntityUSA_TankSPG(World par1World)
    {
        super(par1World);
        this.setSize(4F, 3F);
        //this.tasks.addTask(1, new AIEntityInvasionFlag(this, 1.0D));
      //  this.tasks.addTask(2, new AIEntityAIWander(this, 1.0D));
        
    }
    
    public void updateRiderPosition() {
		if (this.riddenByEntity != null) {
			double ix = 0;
			double iz = 0;
			float f1 = this.rotationYawHead * (2 * (float) Math.PI / 360);
			float f11 = this.rotationYawHead * (2 * (float) Math.PI / 360);
			ix -= MathHelper.sin(f1+1.3F) * 0.7;
			iz += MathHelper.cos(f1+1.3F) * 0.7;
			//ix -= MathHelper.sin(f1) * 0.5;
			//iz += MathHelper.cos(f1) * 0.5;
			this.riddenByEntity.setPosition(this.posX - ix,
					this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset(), this.posZ - iz);
		}
	}
    public double getMountedYOffset() {
		return 1.5D;
	}
    

	protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0D);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(160.0D);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(80.0D);
        this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(30.0D);
    }

	public boolean interact(EntityPlayer p_70085_1_) {
		if (super.interact(p_70085_1_)) {
			return true;
		} else if (!this.worldObj.isRemote && (this.riddenByEntity == null || this.riddenByEntity == p_70085_1_)) {
			if(p_70085_1_.isSneaking()){
				if(this.getMobMode() == 0){
					this.setMobMode(1);
				}else{
					this.setMobMode(0);
				}
			}else if(!p_70085_1_.isRiding()){
				this.setMobMode(1);
				p_70085_1_.mountEntity(this);
			}
			return true;
		} else {
			return false;
		}
	}
	public boolean attackEntityFrom(DamageSource source, float par2)
    {
		Entity entity;
    	entity = source.getSourceOfDamage();
    	
		if(entity != null){
			if(entity instanceof EntityPlayer && this.riddenByEntity != null && this.riddenByEntity == entity)
	        {
	    		return false;
	        }else
	        {
				par2 = par2 * this.TankArmor(entity,par2, 0.75F, 1.3F, 1.1F, this.rotationYawHead) * this.AntiBullet(entity, par2,2);
				return super.attackEntityFrom(source, par2);
			}
		}else{
			return super.attackEntityFrom(source, par2);
		}
    }
	
    protected void addRandomArmor()
    {
        super.addRandomArmor();
    }
    
    
    
    public void onUpdate()
    {
    	super.onUpdate();
    	this.vehicle = true;
    	this.opentop = false;
    	float f1 = this.rotationYawHead * (2 * (float) Math.PI / 360);
		float sp = 0.03F;
		this.overlayhight = 0.0F;
		this.overlayhight_3 = 0.5F;
		float turnspeed = 3F;
		this.thmax = 5F;
		this.thmin = -2F;
		this.thmaxa = 0.2F;
		this.thmina = -0.15F;
		{
			++cooltime;
			++cooltime2;
	    }
		this.ammo1 = 80;
		this.magazine = 1;
		reload_time1 = 200;
		this.w1name = "105mmCannon";
		if(this.getRemain_L() <= 0){
			++reload1;
			if(reload1 == reload_time1 - 20){
				this.playSound("hmgww2:hmgww2.reload_cannon", 1.0F, 1.0F);
			}
			if(reload1 >= reload_time1){
				this.setRemain_L(this.magazine);
				reload1 = 0;
			}
		}
		this.ammo4 = 1;
		this.magazine4 = 60;
		reload_time4 = 100;
		this.w4name = "7.7mmMachineGun";
		if(this.getRemain_S() <= 0){
			++reload4;
			if(reload4 == reload_time4 - 40){
				this.playSound("hmgww2:hmgww2.reload_mg", 1.0F, 1.0F);
			}
			if(reload4 >= reload_time4){
				this.setRemain_S(this.magazine4);
				reload4 = 0;
			}
		}
		
		
		
    	if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityLivingBase) {
    		this.setMobMode(1);
			EntityLivingBase entitylivingbase = (EntityLivingBase) this.riddenByEntity;
			
			this.roteEntity(entitylivingbase);
			
			

			if (this.riddenByEntity instanceof EntityPlayer) {//player
				EntityPlayer player = (EntityPlayer) this.riddenByEntity;
				PL_TankMove.move2(entitylivingbase, this, sp, turnspeed);
				boolean leftc = mod_GVCWW2.proxy.leftclick();
				boolean jumpc = mod_GVCWW2.proxy.jumped();
				if(leftc){
					WW2PacketHandler.INSTANCE.sendToServer(new WW2MessageKeyPressed(11,this.getEntityId()));
					this.server1 = true;
				}
				if(jumpc){
					WW2PacketHandler.INSTANCE.sendToServer(new WW2MessageKeyPressed(12,this.getEntityId()));
					this.server2 = true;
				}
				Vec3 looked = player.getLookVec();
				Vec3 look = this.getLookVec();
				if (this.server1) {
					if (cooltime > this.ammo1 && this.getRemain_L() > 0) {
							this.Weapon(10, player, 0, 0, 2.1, 3,this.posX,this.posY,this.posZ,
									"gvclib:textures/entity/BulletAAA.obj","gvclib:textures/entity/BulletAAA.png","hmgww2:hmgww2.fire_amr", look,
									70, 1.5F, 1F, 6, 1, 0.025D, true);//1.57/1.5/3
						cooltime = 0;
						this.setRemain_L(this.getRemain_L() -1);
						this.motionX += MathHelper.sin(this.rotationYaw * 0.01745329252F)*2;
			    	    this.motionZ -= MathHelper.cos(this.rotationYaw * 0.01745329252F)*2;
					}
					this.server1 = false;
				} 
			}//player
			
			
		}else if(!this.dead && this.getMobMode() == 0)
    	{// 1
			
			this.AITankMove(f1, sp, turnspeed, 80, 120);
			// attacktask
			{
				if(cooltime > ammo1 && this.getRemain_L() > 0)
				{
					this.Attacktask(10, 1, 120D, "gvclib:textures/entity/BulletAAA.obj",
							"gvclib:textures/entity/BulletAAA.png", "hmgww2:hmgww2.fire_amr", 0, 0, 6, 3,
							this.posX, this.posY, this.posZ, 15, 5,
							70, 1.6F, 1F, 6F, 1, 0.025D, 400, 0, true);
				}
			}
		} // 1
    	if(this.getHealth() > 0.0F){
    		this.AITurret();
    	}
    	AI_TankSet.set2(this, "hmgww2:hmgww2.sound_tank", f1, sp, 0.1F);
    }
    
    public void AITurret(){
    	++cooltime4;
    	double k = this.posX;
		double l = this.posY;
		double i = this.posZ;
		double x2 = 0;
		double z2 = 0;
		x2 -= MathHelper.sin(this.rotationYaw * 0.01745329252F) * 1;
		z2 += MathHelper.cos(this.rotationYaw * 0.01745329252F) * 1;
		AxisAlignedBB axisalignedbb2 = AxisAlignedBB.getBoundingBox(
        		(double)(k+x2), (double)(l), (double)(i+z2), 
        		(double)(k+x2), (double)(l+1), (double)(i+z2))
        		.expand(30, 30, 30);
		
    	{//2
        	Entity entity = null;
    		List<Entity> llist = this.worldObj.getEntitiesWithinAABBExcludingEntity(this,axisalignedbb2);
    		if (llist != null) {
    			for (int lj = 0; lj < llist.size(); lj++) {
    				Entity entity1 = (Entity) llist.get(lj);
    				if (entity1.canBeCollidedWith()) {
    					if (this.CanAttack(entity1) && entity1 != null ) 
    					{
    						boolean flag = this.getEntitySenses().canSee(entity1);
    						if(flag && this.posY <= entity1.posY)
    						{
    							this.combattask_2 = true;
    								double d5 = entity1.posX - this.posX;
    								double d7 = entity1.posZ - this.posZ;
    								double d6 = entity1.posY - this.posY;
    								double d1 = this.posY - (entity1.posY);
    					            double d3 = (double)MathHelper.sqrt_double(d5 * d5 + d7 * d7);
    					            float f11 = (float)(-(Math.atan2(d1, d3) * 180.0D / Math.PI));
    								this.rotation_2 = -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
    								this.rotationp_2 =  -f11 + 10;
    								double ddx = Math.abs(d5);
    								double ddz = Math.abs(d7);
    								if(cooltime4 > this.ammo4  && this.getRemain_S() > 0)
    								{
    									float ix1 = 0;
    									float iz1 = 0;
    									float f111 = this.rotationYaw * (2 * (float) Math.PI / 360);
    									ix1 -= MathHelper.sin(f111) * 1;
    									iz1 += MathHelper.cos(f111) * 1;
    									ix1 -= MathHelper.sin(f111+1.0F) * 1;
    									iz1 += MathHelper.cos(f111+1.0F) * 1;
    									{
    										EntityB_Bullet var3 = new EntityB_Bullet(this.worldObj, this, this);
											var3.Bdamege = 6;
											var3.gra = 0.029;
											var3.friend = this;
											var3.exlevel = 2.0F;
											var3.ex = true;
											double var4 = entity1.posX - this.posX-ix1;
											double var6 = entity1.posY + (double) entity1.getEyeHeight()
													+ 0.200000023841858D - var3.posY-1.5D;
											double var8 = entity1.posZ - this.posZ-iz1;
											float var10 = MathHelper.sqrt_double(var4 * var4 + var8 * var8) * 0.01F;
											var3.setLocationAndAngles(this.posX + ix1, this.posY + 1.1D, this.posZ + iz1,
													this.rotationYaw, this.rotationPitch);
											var3.setThrowableHeading(var4, var6 + (double) var10, var8, 3F, 1.0F);
        									if (!this.worldObj.isRemote) {
        										this.worldObj.spawnEntityInWorld(var3);
        									}
    									}
    									this.playSound("hmgww2:hmgww2.fire_rifle", 5.0F, 1F);
    									{
    										this.setRemain_S(this.getRemain_S() -1);
    										cooltime4 = 0;
    									}
    								}
    							break;
    						}
    					}
    					
    				}
    			}
    		}
        }//2
    }
    
    
    protected void onDeathUpdate() {
		++this.deathTicks;

		//this.moveEntity(0.0D, -0.05D, 0.0D);
		if(this.deathTicks == 1 && !this.worldObj.isRemote){
        	this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 3.5F, false);
        }
		if (this.deathTicks == 200 && !this.worldObj.isRemote) {
			Item i = new ItemStack(Blocks.iron_block, 0).getItem();
	        this.dropItem(i, 2);
			this.setDead();
		}
	}
    
	public boolean isConverting() {
		return false;
	}
}
