package hmgww2.entity;


import java.util.Calendar;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gvclib.entity.EntityB_Bullet;
import gvclib.entity.EntityB_BulletAA;
import gvclib.entity.EntityB_GrenadeB;
import gvclib.entity.EntityB_Shell;
import gvclib.entity.EntityT_TNT;
import gvclib.entity.EntityT_Torpedo;
import hmgww2.mod_GVCWW2;
import hmgww2.blocks.BlockFlagBase;
import hmgww2.blocks.BlockUSAFlagBase;
import hmgww2.network.WW2MessageKeyPressed;
import hmgww2.network.WW2PacketHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIRestrictSun;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.biome.BiomeGenBase;

public class EntityUSA_ShipD extends EntityUSABase
{
	// public int type;
	
    public EntityUSA_ShipD(World par1World)
    {
        super(par1World);
        this.setSize(15F, 10F);
        this.tasks.addTask(0, new EntityAISwimming(this));
        //this.tasks.addTask(1, new AIEntityInvasionFlag(this, 1.0D));
      //  this.tasks.addTask(2, new AIEntityAIWander(this, 1.0D));
        
    }
    
    public double getMountedYOffset() {
		return 3.0D;
	}
    

	protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0D);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(500.0D);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(100.0D);
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
    	if(entity instanceof EntityB_Bullet)
        {
    		return false;
        }
    	if(entity instanceof EntityB_BulletAA)
        {
    		par2 = par2 /2;
        }
    	if(entity instanceof EntityB_GrenadeB)
        {
    		par2 = par2 /2;
        }
    	if (this.isEntityInvulnerable())
        {
            return false;
        }else
    	if(source.getEntity() instanceof EntityPlayer)
        {
    		if(this.riddenByEntity != null && this.riddenByEntity == source.getEntity()){
    			return false;
    		}else{
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
    
    protected void rotemodel(float f){
		this.rotationYawHead = this.rotationYaw + f;
		this.rotationYaw = this.rotationYawHead + f;
		this.prevRotationYaw = this.prevRotationYawHead + f;
		this.prevRotationYawHead = this.prevRotationYawHead + f;
		this.renderYawOffset = this.prevRotationYawHead + f;
	}
    
    public void onUpdate()
    {
    	super.onUpdate();
    	this.vehicle = true;
    	this.opentop = false;
    	float f1 = this.rotationYawHead * (2 * (float) Math.PI / 360);
    	this.combattask = false;
    	this.combattask_2 = false;
    	this.combattask_3 = false;
    	this.combattask_4 = false;
    	this.combattask_5 = false;
    	this.combattask_6 = false;
		float sp = 0.1F;
		this.overlayhight = 1.5F;
		this.overlayhight_3 = 2.5F;
		this.overlaywidth_3 = 4F;
		this.ammo1 = 6;
		this.ammo2 = 400;
		
		this.ammo_5 = 3;
		this.ammo_6 = 3;
		
		float turnspeed = 1F;
		if(this.rotationYawHead > 360F || this.rotationYawHead < -360F){
			this.rotation = 0;
			this.rotationp = 0;
			this.rotationYawHead = 0;
			this.rotationYaw = 0;
			this.prevRotationYaw = 0;
			this.prevRotationYawHead = 0;
			this.renderYawOffset = 0;
		}
		if(this.rotationYawHead > 180F){
			this.rotation = -179F;
			this.rotationp = -179F;
			this.rotationYawHead = -179F;
			this.rotationYaw = -179F;
			this.prevRotationYaw = -179F;
			this.prevRotationYawHead = -179F;
			this.renderYawOffset = -179F;
		}
		if(this.rotationYawHead < -180F){
			this.rotation = 179F;
			this.rotationp = 179F;
			this.rotationYawHead = 179F;
			this.rotationYaw = 179F;
			this.prevRotationYaw = 179F;
			this.prevRotationYawHead = 179F;
			this.renderYawOffset = 179F;
		}
    	if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityLivingBase) {
    		this.setMobMode(1);
			EntityLivingBase entitylivingbase = (EntityLivingBase) this.riddenByEntity;

			this.rotation = entitylivingbase.prevRotationYawHead;
			this.rotationp = entitylivingbase.prevRotationPitch;
			
			this.rotation_2 = entitylivingbase.prevRotationYawHead;
			this.rotationp_2 = entitylivingbase.prevRotationPitch;
			
			Vec3 look = this.getLookVec();

			if (entitylivingbase.moveForward > 0.0F) {
				this.motionX = look.xCoord * sp * 5;
				this.motionZ = look.zCoord * sp * 5;
			}
			if (entitylivingbase.moveForward < 0.0F) {
				this.motionX = -look.xCoord * 0.1 * 2;
				this.motionZ = -look.zCoord * 0.1 * 2;
			}
			if (entitylivingbase.moveStrafing < 0.0F) {
				this.rotationYawHead = this.rotationYawHead + turnspeed;
				this.rotationYaw = this.rotationYaw + turnspeed;
				this.prevRotationYaw = this.prevRotationYaw + turnspeed;
				this.prevRotationYawHead = this.prevRotationYawHead + turnspeed;
			}
			if (entitylivingbase.moveStrafing > 0.0F) {
				this.rotationYawHead = this.rotationYawHead - turnspeed;
				this.rotationYaw = this.rotationYaw - turnspeed;
				this.prevRotationYaw = this.prevRotationYaw - turnspeed;
				this.prevRotationYawHead = this.prevRotationYawHead - turnspeed;
			}
			
			

			if (this.riddenByEntity instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) this.riddenByEntity;
				boolean leftc = mod_GVCWW2.proxy.leftclick();
				boolean jumpc = mod_GVCWW2.proxy.jumped();
				if(leftc){
					WW2PacketHandler.INSTANCE.sendToServer(new WW2MessageKeyPressed(11,this.getEntityId()));
					if(this.worldObj.isRemote){
						this.server1 = true;
					}
				}else{
			        //{++cooltime; }
				}
				if(jumpc){
					WW2PacketHandler.INSTANCE.sendToServer(new WW2MessageKeyPressed(12,this.getEntityId()));
					if(this.worldObj.isRemote){
						this.server2 = true;
					}
				}else{
			        //{++cooltime2;}
				}
				{
					++cooltime;
					++cooltime2;
			    }
				
				if (this.server1) {
					if (cooltime > this.ammo1) {
						Vec3 looked = player.getLookVec();
						{
							double xx11 = 0;
							double zz11 = 0;
							xx11 -= MathHelper.sin(this.rotationYaw * 0.01745329252F) * 5.5;
							zz11 += MathHelper.cos(this.rotationYaw * 0.01745329252F) * 5.5;
							xx11 -= MathHelper.sin(this.rotation * 0.01745329252F-0.5F) * 1.5;
							zz11 += MathHelper.cos(this.rotation * 0.01745329252F-0.5F) * 1.5;
							
							double xx12 = 0;
							double zz12 = 0;
							xx12 -= MathHelper.sin(this.rotationYaw * 0.01745329252F) * 5.5;
							zz12 += MathHelper.cos(this.rotationYaw * 0.01745329252F) * 5.5;
							xx12 -= MathHelper.sin(this.rotation * 0.01745329252F+0.5F) * 1.5;
							zz12 += MathHelper.cos(this.rotation * 0.01745329252F+0.5F) * 1.5;
							
							{
								EntityB_BulletAA var3 = new EntityB_BulletAA(player.worldObj, player, this);
								var3.Bdamege = 40;
								var3.gra = 0.029;
								var3.friend = this;
								var3.exnear = true;
								var3.setLocationAndAngles(player.posX + xx11, this.posY + 6D, player.posZ + zz11,
										player.rotationYaw, player.rotationPitch);
								var3.setThrowableHeading(looked.xCoord, looked.yCoord+0.1, looked.zCoord, 2.5F, 5F);
								if (!player.worldObj.isRemote) {
									player.worldObj.spawnEntityInWorld(var3);
								}
							}
							{
								EntityB_BulletAA var3 = new EntityB_BulletAA(player.worldObj, player, this);
								var3.Bdamege = 40;
								var3.gra = 0.029;
								var3.friend = this;
								var3.exnear = true;
								var3.setLocationAndAngles(player.posX + xx12, this.posY + 6D, player.posZ + zz12,
										player.rotationYaw, player.rotationPitch);
								var3.setThrowableHeading(looked.xCoord, looked.yCoord+0.1, looked.zCoord, 2.5F, 5F);
								if (!player.worldObj.isRemote) {
									player.worldObj.spawnEntityInWorld(var3);
								}
							}
							player.playSound("hmgww2:hmgww2.fire", 1.0F, 1.0F);
						}
							cooltime = 0;
					}
					this.server1 = false;
				} 
				if (this.server2) {
					if (cooltime2 > this.ammo2) {
						Vec3 looked = player.getLookVec();
						{
							double xx11 = 0;
							double zz11 = 0;
							xx11 -= MathHelper.sin(this.rotationYaw * 0.01745329252F) * -6.5;
							zz11 += MathHelper.cos(this.rotationYaw * 0.01745329252F) * -6.5;
							xx11 -= MathHelper.sin(this.rotation * 0.01745329252F-1.0F) * 1.5;
							zz11 += MathHelper.cos(this.rotation * 0.01745329252F-1.0F) * 1.5;
							
							double xx12 = 0;
							double zz12 = 0;
							xx12 -= MathHelper.sin(this.rotationYaw * 0.01745329252F) * -6.5;
							zz12 += MathHelper.cos(this.rotationYaw * 0.01745329252F) * -6.5;
							xx12 -= MathHelper.sin(this.rotation * 0.01745329252F+1.0F) * 1.5;
							zz12 += MathHelper.cos(this.rotation * 0.01745329252F+1.0F) * 1.5;
							
							double xx13 = 0;
							double zz13 = 0;
							xx13 -= MathHelper.sin(this.rotationYaw * 0.01745329252F) * -6.5;
							zz13 += MathHelper.cos(this.rotationYaw * 0.01745329252F) * -6.5;
							xx13 -= MathHelper.sin(this.rotation * 0.01745329252F) * 1.5;
							zz13 += MathHelper.cos(this.rotation * 0.01745329252F) * 1.5;
							
							{
								EntityT_Torpedo var3 = new EntityT_Torpedo(player.worldObj, player);
								var3.Bdamege = 400;
								var3.friend = this;
								var3.extime = 1200;
								var3.setLocationAndAngles(player.posX + xx11, this.posY + 5D, player.posZ + zz11,
										player.rotationYaw, player.rotationPitch);
								var3.setThrowableHeading(looked.xCoord, 0, looked.zCoord, 1.6F, 5F);
								if (!player.worldObj.isRemote) {
									player.worldObj.spawnEntityInWorld(var3);
								}
							}
							{
								EntityT_Torpedo var3 = new EntityT_Torpedo(player.worldObj, player);
								var3.Bdamege = 400;
								var3.friend = this;
								var3.extime = 1200;
								var3.setLocationAndAngles(player.posX + xx12, this.posY + 5D, player.posZ + zz12,
										player.rotationYaw, player.rotationPitch);
								var3.setThrowableHeading(looked.xCoord, 0, looked.zCoord, 1.6F, 5F);
								if (!player.worldObj.isRemote) {
									player.worldObj.spawnEntityInWorld(var3);
								}
							}
							{
								EntityT_Torpedo var3 = new EntityT_Torpedo(player.worldObj, player);
								var3.Bdamege = 400;
								var3.friend = this;
								var3.extime = 1200;
								var3.setLocationAndAngles(player.posX + xx13, this.posY + 5D, player.posZ + zz13,
										player.rotationYaw, player.rotationPitch);
								var3.setThrowableHeading(looked.xCoord, 0, looked.zCoord, 1.6F, 5F);
								if (!player.worldObj.isRemote) {
									player.worldObj.spawnEntityInWorld(var3);
								}
							}
							player.playSound("hmgww2:hmgww2.firegrenade", 1.0F, 0.5F);
						}
							cooltime2 = 0;
					}
					this.server2 = false;
				} 
			}
			
			
			
			
			
			
			
			
			
			
			
			
			
			
		}else if(!this.dead && this.getMobMode() == 0)
    	{// 1
			
			Entity entity = null;
			List<Entity> llist = this.worldObj.getEntitiesWithinAABBExcludingEntity(this,
					this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(80D, 50D, 80D));
			if (llist != null) {
				for (int lj = 0; lj < llist.size(); lj++) {
					Entity entity1 = (Entity) llist.get(lj);
					if (entity1.canBeCollidedWith()) {
						if (this.CanAttack(entity1) && entity1 != null ) 
						//if (entity1 instanceof EntityPlayer && entity1 != null ) 
						{
							{
								boolean flag = this.getEntitySenses().canSee(entity1);
									double d5 = entity1.posX - this.posX;
									double d7 = entity1.posZ - this.posZ;
									double d6 = entity1.posY - this.posY;
									double d1 = this.posY - (entity1.posY);
						            double d3 = (double)MathHelper.sqrt_double(d5 * d5 + d7 * d7);
						            float f11 = (float)(-(Math.atan2(d1, d3) * 180.0D / Math.PI));
						            this.rotation = this.rote =  -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
									this.rotationp = this.rotationPitch = -f11 + 10;
									this.rotation_2 =  -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
									this.rotationp_2 = this.rotationPitch = -f11 + 10;
									if(this.rote > 180F){
										this.rote = -179F;
									}
									if(this.rote < -180F){
										this.rote = 179F;
									}
									double ddx = Math.abs(d5);
									double ddz = Math.abs(d7);
if ((ddx > 50 || ddz > 50)  && this.getFlagMode() == 0) {
										
										float f3 = (float) (this.rotationYawHead - this.rote);
							            //if(!this.worldObj.isRemote)
							            {
							            	if(this.rotationYawHead != this.rote  && !(f3 <= turnspeed && f3 >= -turnspeed)){
							            		if(f3 > 0.1F){
							    					if(f3 > 180F){
							    						this.rotemodel(+ turnspeed);
							    					}else{
							    						this.rotemodel(- turnspeed);
							    					}
							    				}
							    				else if(f3 < -0.1F){
							    					if(f3 < -180F){
							    						this.rotemodel(- turnspeed);
							    					}else{
							    						this.rotemodel(+ turnspeed);
							    					}
							    				}
								            }
							            }
							            AIEntityGoFlag goflag = new AIEntityGoFlag(this, (int)entity1.posX, (int)entity1.posY, (int)entity1.posZ,
							            		10D * sp);
							    		goflag.goship();
									}else if ((ddx <= 50 && ddz <= 50)  && this.getFlagMode() == 0) {
										float f3 = (float) (this.rotationYawHead - this.rote);
							            {
							            	if(this.rotationYawHead != this.rote  && !(f3 <= turnspeed && f3 >= -turnspeed)){
							            		if(f3 > 0.1F){
							    					if(f3 > 180F){
							    						this.rotemodel(- turnspeed);
							    					}else{
							    						this.rotemodel(+ turnspeed);
							    					}
							    				}
							    				else if(f3 < -0.1F){
							    					if(f3 < -180F){
							    						this.rotemodel(+ turnspeed);
							    					}else{
							    						this.rotemodel(- turnspeed);
							    					}
							    				}
								            }
							            }
							            AIEntityGoFlag goflag = new AIEntityGoFlag(this, (int)entity1.posX, (int)entity1.posY, (int)entity1.posZ,
							            		10D * sp);
							    		goflag.goship();
									}
									this.AttackTask((EntityLivingBase) entity1, ddx, ddz);
									this.combattask = true;
							}
							break;
						}
						if(entity1 instanceof EntityPlayer && entity1 != null && !this.CanAttack(entity1) && this.getFlagMode() == 3){
							double d5 = entity1.posX - this.posX;
							double d7 = entity1.posZ - this.posZ;
							double d6 = entity1.posY - this.posY;
							double d1 = this.posY - (entity1.posY);
				            double d3 = (double)MathHelper.sqrt_double(d5 * d5 + d7 * d7);
				            float f11 = (float)(-(Math.atan2(d1, d3) * 180.0D / Math.PI));
							double ddx = Math.abs(d5);
							double ddz = Math.abs(d7);
							this.rotation = this.rote =  -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
							this.rotationp = this.rotationPitch = -f11 + 10;
							if(this.rote > 180F){
								this.rote = -179F;
							}
							if(this.rote < -180F){
								this.rote = 179F;
							}
							if ((ddx > 5 || ddz > 5)) {
								if (!this.isRiding()) {
									AIEntityGoFlag goflag = new AIEntityGoFlag(this, (int)entity1.posX, (int)entity1.posY, (int)entity1.posZ,
						            		10D * sp);
						    		goflag.goship();
								}
								float f3 = (float) (this.rotationYawHead - this.rote);
					            {
					            	if(this.rotationYawHead != this.rote  && !(f3 <= turnspeed && f3 >= -turnspeed)){
					            		if(f3 > 0.1F){
					    					if(f3 > 180F){
					    						this.rotemodel(+ turnspeed);
					    					}else{
					    						this.rotemodel(- turnspeed);
					    					}
					    				}
					    				else if(f3 < -0.1F){
					    					if(f3 < -180F){
					    						this.rotemodel(- turnspeed);
					    					}else{
					    						this.rotemodel(+ turnspeed);
					    					}
					    				}
						            }
					            }
							}
							break;
						}
					}
				}
			}
			// EntityAI_MoveS.MoveEntity(this, true, 0.5D, 60D);
			{
	    		if(this.getFlagMode() == 2){
	    			AIEntityGoFlag goflag = new AIEntityGoFlag(this, this.getFlagX(), this.getFlagY(), this.getFlagZ(), 10D * sp);
	    			goflag.goship();
	    			double d5 = this.getFlagX() - this.posX;
					double d7 = this.getFlagY() - this.posZ;
					double d6 = this.getFlagZ() - this.posY;
					double d1 = this.posY - (this.getFlagY());
		            double d3 = (double)MathHelper.sqrt_double(d5 * d5 + d7 * d7);
		            float f11 = (float)(-(Math.atan2(d1, d3) * 180.0D / Math.PI));
					double ddx = Math.abs(d5);
					double ddz = Math.abs(d7);
					this.rotation = this.rote =  -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
					this.rotationp = this.rotationPitch = -f11 + 10;
					if(this.rote > 180F){
						this.rote = -179F;
					}
					if(this.rote < -180F){
						this.rote = 179F;
					}
					if ((ddx > 5 || ddz > 5)) {
						float f3 = (float) (this.rotationYawHead - this.rote);
			            {
			            	if(this.rotationYawHead != this.rote  && !(f3 <= turnspeed && f3 >= -turnspeed)){
			            		if(f3 > 0.1F){
			    					if(f3 > 180F){
			    						this.rotemodel(+ turnspeed);
			    					}else{
			    						this.rotemodel(- turnspeed);
			    					}
			    				}
			    				else if(f3 < -0.1F){
			    					if(f3 < -180F){
			    						this.rotemodel(- turnspeed);
			    					}else{
			    						this.rotemodel(+ turnspeed);
			    					}
			    				}
				            }
			            }
					}
	    		}
	    		if(this.getFlagMode() == 1){
	    			this.setFlagMode(0);
	    			{
	    	        	for (int x = 0; x < 128; ++x){
	    	        		for (int y = 0; y < 64; ++y){
	    	        			for (int z = 0; z < 128; ++z){
	    	        				if(this.worldObj.getBlock((int)this.posX + x - 64, (int)this.posY + y - 32, (int)this.posZ + z - 64) 
	    	        						instanceof BlockFlagBase
	    	        						&& !(this.worldObj.getBlock((int)this.posX + x - 64, (int)this.posY + y - 32, (int)this.posZ + z - 64) 
	    	    	        						instanceof BlockUSAFlagBase)){
	    	        					this.setFlagX((int)this.posX + x - 64);
	    	        					this.setFlagY((int)this.posY + y - 32);
	    	        					this.setFlagZ((int)this.posZ + z - 64);
	    	        					this.setFlagMode(2);
	    	        					break;
	    	        				}
	    	        			}
	    	        			if(this.getFlagMode() == 2){
	    	        				break;
	    	        			}
	    	        		}
	    	        		if(this.getFlagMode() == 2){
		        				break;
		        			}
	    	        	}
	        		}
	    		}
	    		
	    	}
		} // 1
    	
    	if(this.getHealth() > 0.0F){
    		this.AITurret();
    	}
    	
    	
    }
    
    
    public void AttackTask(EntityLivingBase entity1, double x, double z){
    	
    	//if (itemstack != null && itemstack.getItem() == mod_GVCR.gun_ak74) 
    	{//3
			//ItemGunBase gun = (ItemGunBase) itemstack.getItem();
			//gun.flash = false;
			if (cooltime > this.ammo1) {// 2
				//if ((x < 30 && z < 30))
				{
					boolean flag = this.getEntitySenses().canSee(entity1);
					double ddy = Math.abs(entity1.posY - this.posY);
					//if (flag) 
					{
						Vec3 looked = this.getLookVec();
						{
							double xx11 = 0;
							double zz11 = 0;
							xx11 -= MathHelper.sin(this.rotationYaw * 0.01745329252F) * 5.5;
							zz11 += MathHelper.cos(this.rotationYaw * 0.01745329252F) * 5.5;
							xx11 -= MathHelper.sin(this.rotation * 0.01745329252F-0.5F) * 1.5;
							zz11 += MathHelper.cos(this.rotation * 0.01745329252F-0.5F) * 1.5;
							
							double xx12 = 0;
							double zz12 = 0;
							xx12 -= MathHelper.sin(this.rotationYaw * 0.01745329252F) * 5.5;
							zz12 += MathHelper.cos(this.rotationYaw * 0.01745329252F) * 5.5;
							xx12 -= MathHelper.sin(this.rotation * 0.01745329252F+0.5F) * 1.5;
							zz12 += MathHelper.cos(this.rotation * 0.01745329252F+0.5F) * 1.5;
							
							{
								EntityB_BulletAA var3 = new EntityB_BulletAA(this.worldObj, this, this);
								var3.Bdamege = 40;
								var3.gra = 0.029;
								var3.friend = this;
								var3.exnear = true;
								var3.setLocationAndAngles(this.posX + xx11, this.posY + 6D, this.posZ + zz11,
										this.rotationYaw, this.rotationPitch);
								double var4 = entity1.posX - this.posX;
								double var6 = entity1.posY + (double) entity1.getEyeHeight()
										+ 0.200000023841858D - var3.posY;
								double var8 = entity1.posZ - this.posZ;
								float var10 = MathHelper.sqrt_double(var4 * var4 + var8 * var8) * 0.1F;
								var3.setThrowableHeading(var4, var6 + (double) var10, var8, 2.5F, 25.0F);
								if (!this.worldObj.isRemote) {
									this.worldObj.spawnEntityInWorld(var3);
								}
							}
							{
								EntityB_BulletAA var3 = new EntityB_BulletAA(this.worldObj, this, this);
								var3.Bdamege = 40;
								var3.gra = 0.029;
								var3.friend = this;
								var3.exnear = true;
								var3.setLocationAndAngles(this.posX + xx12, this.posY + 6D, this.posZ + zz12,
										this.rotationYaw, this.rotationPitch);
								double var4 = entity1.posX - this.posX;
								double var6 = entity1.posY + (double) entity1.getEyeHeight()
										+ 0.200000023841858D - var3.posY;
								double var8 = entity1.posZ - this.posZ;
								float var10 = MathHelper.sqrt_double(var4 * var4 + var8 * var8) * 0.1F;
								var3.setThrowableHeading(var4, var6 + (double) var10, var8, 2.5F, 25.0F);
								if (!this.worldObj.isRemote) {
									this.worldObj.spawnEntityInWorld(var3);
								}
							}
							this.playSound("hmgww2:hmgww2.fire", 1.0F, 1F);
						}
						if (!this.worldObj.isRemote) {
							cooltime = 0;
						}
						//gun.flash = true;
					}
				}					
			} // 2
			else {
				if (!this.worldObj.isRemote) {
					++cooltime;
				}
			}
			
			if (cooltime2 > this.ammo2) {// 2
				//if ((x < 30 && z < 30))
				{
					boolean flag = this.getEntitySenses().canSee(entity1);
					double ddy = Math.abs(entity1.posY - this.posY);
					//if (flag) 
					{
						Vec3 looked = this.getLookVec();
						{
							double xx11 = 0;
							double zz11 = 0;
							xx11 -= MathHelper.sin(this.rotationYaw * 0.01745329252F) * -6.5;
							zz11 += MathHelper.cos(this.rotationYaw * 0.01745329252F) * -6.5;
							xx11 -= MathHelper.sin(this.rotation * 0.01745329252F-1.0F) * 1.5;
							zz11 += MathHelper.cos(this.rotation * 0.01745329252F-1.0F) * 1.5;
							
							double xx12 = 0;
							double zz12 = 0;
							xx12 -= MathHelper.sin(this.rotationYaw * 0.01745329252F) * -6.5;
							zz12 += MathHelper.cos(this.rotationYaw * 0.01745329252F) * -6.5;
							xx12 -= MathHelper.sin(this.rotation * 0.01745329252F+1.0F) * 1.5;
							zz12 += MathHelper.cos(this.rotation * 0.01745329252F+1.0F) * 1.5;
							
							double xx13 = 0;
							double zz13 = 0;
							xx13 -= MathHelper.sin(this.rotationYaw * 0.01745329252F) * -6.5;
							zz13 += MathHelper.cos(this.rotationYaw * 0.01745329252F) * -6.5;
							xx13 -= MathHelper.sin(this.rotation * 0.01745329252F) * 1.5;
							zz13 += MathHelper.cos(this.rotation * 0.01745329252F) * 1.5;
							
							{
								EntityT_Torpedo var3 = new EntityT_Torpedo(this.worldObj, this);
								var3.Bdamege = 400;
								var3.friend = this;
								var3.extime = 1200;
								var3.setLocationAndAngles(this.posX + xx11, this.posY + 5D, this.posZ + zz11,
										this.rotationYaw, this.rotationPitch);
								double var4 = entity1.posX - this.posX;
								double var6 = entity1.posY + (double) entity1.getEyeHeight()
										+ 0.200000023841858D - var3.posY;
								double var8 = entity1.posZ - this.posZ;
								float var10 = MathHelper.sqrt_double(var4 * var4 + var8 * var8) * 0.1F;
								var3.setThrowableHeading(var4, 0, var8, 1.6F, 5.0F);
								if (!this.worldObj.isRemote) {
									this.worldObj.spawnEntityInWorld(var3);
								}
							}
							{
								EntityT_Torpedo var3 = new EntityT_Torpedo(this.worldObj, this);
								var3.Bdamege = 400;
								var3.friend = this;
								var3.extime = 1200;
								var3.setLocationAndAngles(this.posX + xx12, this.posY + 5D, this.posZ + zz12,
										this.rotationYaw, this.rotationPitch);
								double var4 = entity1.posX - this.posX;
								double var6 = entity1.posY + (double) entity1.getEyeHeight()
										+ 0.200000023841858D - var3.posY;
								double var8 = entity1.posZ - this.posZ;
								float var10 = MathHelper.sqrt_double(var4 * var4 + var8 * var8) * 0.1F;
								var3.setThrowableHeading(var4, 0, var8, 1.6F, 5.0F);
								if (!this.worldObj.isRemote) {
									this.worldObj.spawnEntityInWorld(var3);
								}
							}
							{
								EntityT_Torpedo var3 = new EntityT_Torpedo(this.worldObj, this);
								var3.Bdamege = 400;
								var3.friend = this;
								var3.extime = 1200;
								var3.setLocationAndAngles(this.posX + xx13, this.posY + 5D, this.posZ + zz13,
										this.rotationYaw, this.rotationPitch);
								double var4 = entity1.posX - this.posX;
								double var6 = entity1.posY + (double) entity1.getEyeHeight()
										+ 0.200000023841858D - var3.posY;
								double var8 = entity1.posZ - this.posZ;
								float var10 = MathHelper.sqrt_double(var4 * var4 + var8 * var8) * 0.1F;
								var3.setThrowableHeading(var4, 0, var8, 1.6F, 5.0F);
								if (!this.worldObj.isRemote) {
									this.worldObj.spawnEntityInWorld(var3);
								}
							}
							this.playSound("hmgww2:hmgww2.firegrenade", 1.0F, 0.5F);
						}
						if (!this.worldObj.isRemote) {
							cooltime2 = 0;
						}
						//gun.flash = true;
					}
				}					
			} // 2
			else {
				if (!this.worldObj.isRemote) {
					++cooltime2;
				}
			}
		}//3
    }
    
    public void AITurret(){
    	double k = this.posX;
		double l = this.posY;
		double i = this.posZ;
		double x5 = 0;
		double z5 = 0;
		x5 -= MathHelper.sin(this.rotationYaw * 0.01745329252F -1.3F) * 50.0;
		z5 += MathHelper.cos(this.rotationYaw * 0.01745329252F -1.3F) * 50.0;
		double x6 = 0;
		double z6 = 0;
		x6 -= MathHelper.sin(this.rotationYaw * 0.01745329252F +1.3F) * 50.0;
		z6 += MathHelper.cos(this.rotationYaw * 0.01745329252F +1.3F) * 50.0;
		AxisAlignedBB axisalignedbb5 = AxisAlignedBB.getBoundingBox(
        		(double)(k+x5), (double)(l+10), (double)(i+z5), 
        		(double)(k+x5), (double)(l+10), (double)(i+z5))
        		.expand(80, 80, 80);
		AxisAlignedBB axisalignedbb6 = AxisAlignedBB.getBoundingBox(
        		(double)(k+x6), (double)(l+10), (double)(i+z6), 
        		(double)(k+x6), (double)(l+10), (double)(i+z6))
        		.expand(80, 80, 80);
		
		{//5
        	Entity entity = null;
    		List<Entity> llist = this.worldObj.getEntitiesWithinAABBExcludingEntity(this,axisalignedbb5);
    		if (llist != null) {
    			for (int lj = 0; lj < llist.size(); lj++) {
    				Entity entity1 = (Entity) llist.get(lj);
    				if (entity1.canBeCollidedWith()) {
    					if (this.CanAttack(entity1) && entity1 != null ) 
    					{
    						boolean flag = this.getEntitySenses().canSee(entity1);
    						if(flag)
    						{
    							this.combattask_5 = true;
    								double d5 = entity1.posX - this.posX;
    								double d7 = entity1.posZ - this.posZ;
    								double d6 = entity1.posY - this.posY;
    								double d1 = this.posY - (entity1.posY);
    					            double d3 = (double)MathHelper.sqrt_double(d5 * d5 + d7 * d7);
    					            float f11 = (float)(-(Math.atan2(d1, d3) * 180.0D / Math.PI));
    								this.rotation_5 = -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
    								this.rotationp_5 =  -f11 + 10;
    								double ddx = Math.abs(d5);
    								double ddz = Math.abs(d7);
    								if(cooltime_5 > this.ammo_5)
    								{
    									float ix1 = 0;
    									float iz1 = 0;
    									float f111 = this.rotationYaw * (2 * (float) Math.PI / 360);
    									ix1 -= MathHelper.sin(f111-1.3F) * 3.5;
    									iz1 += MathHelper.cos(f111-1.3F) * 3.5;
    									ix1 -= MathHelper.sin(this.rotation_6 * 0.01745329252F) * 1;
    									iz1 += MathHelper.cos(this.rotation_6 * 0.01745329252F) * 1;
    									EntityB_Bullet var3 = new EntityB_Bullet(this.worldObj, this, this);
    									var3.Bdamege = 12;
    									var3.gra = 0.029;
    									var3.friend = this;
    									var3.exnear = true;
    									double var4 = entity1.posX - this.posX - ix1;
    									double var6 = entity1.posY + (double) entity1.getEyeHeight()
    											+ 0.200000023841858D - var3.posY + 1;
    									double var8 = entity1.posZ - this.posZ - iz1;
    									float var10 = MathHelper.sqrt_double(var4 * var4 + var8 * var8) * 0.01F;
    									var3.setLocationAndAngles(this.posX + ix1, this.posY + 7.2,this.posZ + iz1, this.rotationYaw, 0F);
    									var3.setThrowableHeading(var4, var6 + (double) var10, var8, 3F, 25.0F);
    									if (!this.worldObj.isRemote) {
    										this.worldObj.spawnEntityInWorld(var3);
    									}
    									this.playSound("hmgww2:hmgww2.fire", 5.0F, 1.0F);
    									Vec3 look = this.getLookVec();
    									{
    										cooltime_5 = 0;
    									}
    								}
    							break;
    						}
    					}
    					
    				}
    			}
    		}
    		{
    			{
    				++cooltime_5;
    			}
    		}
        }//5
		{//6
        	Entity entity = null;
    		List<Entity> llist = this.worldObj.getEntitiesWithinAABBExcludingEntity(this,axisalignedbb6);
    		if (llist != null) {
    			for (int lj = 0; lj < llist.size(); lj++) {
    				Entity entity1 = (Entity) llist.get(lj);
    				if (entity1.canBeCollidedWith()) {
    					if (this.CanAttack(entity1) && entity1 != null ) 
    					{
    						boolean flag = this.getEntitySenses().canSee(entity1);
    						if(flag)
    						{
    							this.combattask_6 = true;
    								double d5 = entity1.posX - this.posX;
    								double d7 = entity1.posZ - this.posZ;
    								double d6 = entity1.posY - this.posY;
    								double d1 = this.posY - (entity1.posY);
    					            double d3 = (double)MathHelper.sqrt_double(d5 * d5 + d7 * d7);
    					            float f11 = (float)(-(Math.atan2(d1, d3) * 180.0D / Math.PI));
    								this.rotation_6 = -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
    								this.rotationp_6 =  -f11 + 10;
    								double ddx = Math.abs(d5);
    								double ddz = Math.abs(d7);
    								if(cooltime_6 > this.ammo_6)
    								{
    									float ix1 = 0;
    									float iz1 = 0;
    									float f111 = this.rotationYaw * (2 * (float) Math.PI / 360);
    									ix1 -= MathHelper.sin(f111+1.3F) * 3.5;
    									iz1 += MathHelper.cos(f111+1.3F) * 3.5;
    									ix1 -= MathHelper.sin(this.rotation_6 * 0.01745329252F) * 1;
    									iz1 += MathHelper.cos(this.rotation_6 * 0.01745329252F) * 1;
    									EntityB_Bullet var3 = new EntityB_Bullet(this.worldObj, this, this);
    									var3.Bdamege = 12;
    									var3.gra = 0.029;
    									var3.friend = this;
    									var3.exnear = true;
    									double var4 = entity1.posX - this.posX - ix1;
    									double var6 = entity1.posY + (double) entity1.getEyeHeight()
    											+ 0.200000023841858D - var3.posY + 1;
    									double var8 = entity1.posZ - this.posZ - iz1;
    									float var10 = MathHelper.sqrt_double(var4 * var4 + var8 * var8) * 0.01F;
    									var3.setLocationAndAngles(this.posX + ix1, this.posY + 7.2,this.posZ + iz1, this.rotationYaw, 0F);
    									var3.setThrowableHeading(var4, var6 + (double) var10, var8, 3F, 25.0F);
    									if (!this.worldObj.isRemote) {
    										this.worldObj.spawnEntityInWorld(var3);
    									}
    									this.playSound("hmgww2:hmgww2.fire", 5.0F, 1.0F);
    									Vec3 look = this.getLookVec();
    									{
    										cooltime_6 = 0;
    									}
    								}
    							break;
    						}
    					}
    					
    				}
    			}
    		}
    		{
    			{
    				++cooltime_6;
    			}
    		}
        }//5
		
		
    }
    
    
    
    protected void onDeathUpdate() {
		++this.deathTicks;

		//this.moveEntity(0.0D, -0.05D, 0.0D);
		if(this.deathTicks == 1 && !this.worldObj.isRemote){
        	this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 3.5F, false);
        }
		if (this.deathTicks == 200 && !this.worldObj.isRemote) {
			this.setDead();
		}
	}
    
	public boolean isConverting() {
		return false;
	}
}
