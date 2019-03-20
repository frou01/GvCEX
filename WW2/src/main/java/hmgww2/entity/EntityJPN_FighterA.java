package hmgww2.entity;


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
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityJPN_FighterA extends EntityJPNBase
{
	// public int type;
	
    public EntityJPN_FighterA(World par1World)
    {
        super(par1World);
        this.setSize(4F, 2F);
    }
    
    public double getMountedYOffset() {
		return 0.6D;
	}
    

	protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0D);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100.0D);
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
				par2 = par2 * this.AntiBulletAir(entity, par2,0);
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
    	this.combattask_2 = false;
    	float f1 = this.rotationYawHead * (2 * (float) Math.PI / 360);
		float sp = 0.02F;
		this.overlayhight = 0.0F;
		this.overlayhight_3 = 1F;
		this.thmax = 40;
		this.magazine = 1;
		float turnspeed = 0.8F;
		{
    		if(ontick % 10 == 0 && this.thpower > 1){
    			this.playSound("hmgww2:hmgww2.sound_pera", 2.0F, 1.0F);
    		}
    	}
		{
			++cooltime;
			++cooltime2;
	    }
		this.ammo1 = 2;
		this.magazine = 40;
		reload_time1 = 200;
		this.w1name = "20mmMachineCannon";
		
		if(this.getWeaponMode() == 1)
		{
			this.ammo2 = 1;
			this.magazine2 = 1;
			reload_time2 = 300;
			this.w2name = "Torpedo";
		}else{
			this.ammo2 = 1;
			this.magazine2 = 1;
			reload_time2 = 300;
			this.w2name = "TNT";
		}
		
		this.ammo4 = 1;
		this.magazine4 = 60;
		reload_time4 = 100;
		this.w4name = "7.7mmMachineGun";
		if(this.getRemain_L() <= 0){
			++reload1;
			if(reload1 == reload_time1 - 40){
				this.playSound("hmgww2:hmgww2.reload_mg", 1.0F, 1.0F);
			}
			if(reload1 >= reload_time1){
				this.setRemain_L(this.magazine);
				reload1 = 0;
			}
		}
		if(this.getRemain_R() <= 0){
			++reload2;
			if(reload2 == reload_time2 - 10){
				this.playSound("hmgww2:hmgww2.reload_shell", 1.0F, 1.0F);
			}
			if(reload2 >= reload_time2){
				this.setRemain_R(this.magazine2);
				reload2 = 0;
			}
		}
		if(this.getRemain_S() <= 0){
			++reload4;
			if(reload4 == reload_time4 - 10){
				this.playSound("hmgww2:hmgww2.reload_mg", 1.0F, 1.0F);
			}
			if(reload4 >= reload_time4){
				this.setRemain_S(this.magazine4);
				reload4 = 0;
			}
		}
		
		
		this.AirCraftSet();
		
    	if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityLivingBase) {
    		this.setMobMode(1);
			EntityLivingBase entitylivingbase = (EntityLivingBase) this.riddenByEntity;

			this.roteEntity(entitylivingbase);
			
			this.AirCraftMove(entitylivingbase, sp, turnspeed);
			Vec3 look = this.getLookVec();

			if (this.riddenByEntity instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) this.riddenByEntity;
				boolean leftc = mod_GVCWW2.proxy.leftclick();
				boolean jumpc = mod_GVCWW2.proxy.jumped();
				boolean xc = mod_GVCWW2.proxy.xclick();
				if(leftc){
					WW2PacketHandler.INSTANCE.sendToServer(new WW2MessageKeyPressed(11,this.getEntityId()));
					this.server1 = true;
				}
				if(jumpc){
					WW2PacketHandler.INSTANCE.sendToServer(new WW2MessageKeyPressed(12,this.getEntityId()));
					this.server2 = true;
				}
				if(xc){
					WW2PacketHandler.INSTANCE.sendToServer(new WW2MessageKeyPressed(14,this.getEntityId()));
					this.serverx = true;
				}
				
				if (this.serverx) {
					if(this.getWeaponMode() == 0){
						this.setWeaponMode(1);
					}else{
						this.setWeaponMode(0);
					}
					this.serverx = false;
					++this.gun_count1;
				}
				{
					if(this.gun_count1 > 1){
						++this.reload1;
						if(this.reload1 >= this.reload_time1){
							this.gun_count1 = 0;
							this.reload1 = 0;
						}
					}
				}
				
				Vec3 looked = player.getLookVec();
				if (this.server1) {
					if (cooltime > this.ammo1 && this.getRemain_L() > 0) {
							this.Weapon(1, player, 1.57F, 2, 1.1, 1.0,this.posX,this.posY,this.posZ,
									"gvclib:textures/entity/BulletAAA.obj","gvclib:textures/entity/BulletAAA.png","hmgww2:hmgww2.fire_30mm", look,
									12, 3F, 1F, 1, 1, 0.025D, false);//1.57/1.5/3
							this.Weapon(1, player, -1.57F, 2, 1.1, 1.0,this.posX,this.posY,this.posZ,
									"gvclib:textures/entity/BulletAAA.obj","gvclib:textures/entity/BulletAAA.png","hmgww2:hmgww2.fire_30mm", look,
									12, 3F, 1F, 1, 1, 0.025D, false);//1.57/1.5/3
						cooltime = 0;
						this.setRemain_L(this.getRemain_L() -1);
					}
					this.server1 = false;
				} 
				if (this.server2) {
					if (cooltime2 > this.ammo2 && this.getRemain_R() > 0) {
						if(this.getWeaponMode() == 1)
						{
							this.Weapon(11, player, 0F, 0, -1, 1.0,this.posX,this.posY,this.posZ,
									"gvclib:textures/entity/BulletAAA.obj","gvclib:textures/entity/BulletAAA.png","hmgww2:hmgww2.fire_grenade", looked,
									400, 2F, 1F, 4, 1, 0.029D, false);//1.57/1.5/3
						}else{
							this.Weapon(10, player, 1.3F, 2, -1, 1.0,this.posX,this.posY,this.posZ,
									"gvclib:textures/entity/BulletAAA.obj","gvclib:textures/entity/BulletAAA.png","hmgww2:hmgww2.fire_grenade", looked,
									80, 0.6F, 1F, 4, 1, 0.029D, false);//1.57/1.5/3
							this.Weapon(10, player, -1.3F, 2, -1, 1.0,this.posX,this.posY,this.posZ,
									"gvclib:textures/entity/BulletAAA.obj","gvclib:textures/entity/BulletAAA.png","hmgww2:hmgww2.fire_grenade", looked,
									80, 0.6F, 1F, 4, 1, 0.029D, false);//1.57/1.5/3
							this.Weapon(10, player, 0F, 0, -1, 1.0,this.posX,this.posY,this.posZ,
									"gvclib:textures/entity/BulletAAA.obj","gvclib:textures/entity/BulletAAA.png","hmgww2:hmgww2.fire_grenade", looked,
									80, 0.6F, 1F, 4, 1, 0.029D, false);//1.57/1.5/3
						}
					cooltime2 = 0;
					this.setRemain_R(this.getRemain_R() -1);
				    }
					this.server2 = false;
				} 
				
				
				
			}
			
			if(this.getHealth() > 0.0F){
	    		this.AITurret();
	    	}
		}else if(!this.dead && this.getMobMode() == 0)
    	{// 1
			this.AIAirCraftMove(f1, sp, turnspeed, 30, 120D);
			if(this.getHealth() > 0.0F){
	    		this.AITurret();
	    	}
			if(cooltime > ammo1 && this.getRemain_L() > 0)
			{
				this.Attacktask(1, 15, 40D, "gvclib:textures/entity/BulletAAA.obj",
						"gvclib:textures/entity/BulletAAA.png", "hmgww2:hmgww2.fire_30mm", 1.57F, 2, 1.1, 1.0,
						this.posX, this.posY, this.posZ, 15, 15,
					     12, 3F, 1F, 1F, 1, 0.020D, 400, 0, false);
				this.Attacktask(1, 10, 40D, "gvclib:textures/entity/BulletAAA.obj",
						"gvclib:textures/entity/BulletAAA.png", "hmgww2:hmgww2.fire_30mm", -1.57F, 2, 1.1, 1.0,
						this.posX, this.posY, this.posZ, 15, 15,
					     12, 3F, 1F, 1F, 1, 0.020D, 400, 0, false);
			}
			if(cooltime2 > this.ammo2 && this.getRemain_R() > 0)
			{
				if(this.getWeaponMode() == 1)
				{
					this.Attacktask(11, 2, 30D, "gvclib:textures/entity/BulletAAA.obj",
							"gvclib:textures/entity/BulletAAA.png", "hmgww2:hmgww2.fire_grenade", 0, 0, -1, 1.0,
							this.posX, this.posY, this.posZ, 30, 40,
							80, 2F, 1F, 4F, 1, 0.029D, 1200, 0, false);
				}else{
					this.Attacktask(10, 2, 30D, "gvclib:textures/entity/BulletAAA.obj",
							"gvclib:textures/entity/BulletAAA.png", "hmgww2:hmgww2.fire_grenade", 1.3F, 2, -1, 1.0,
							this.posX, this.posY, this.posZ, 30, 40,
							80, 0.3F, 1F, 4F, 1, 0.029D, 1200, 0, false);
					this.Attacktask(10, 0, 30D, "gvclib:textures/entity/BulletAAA.obj",
							"gvclib:textures/entity/BulletAAA.png", "hmgww2:hmgww2.fire_grenade",-1.3F, 2, -1, 1.0,
							this.posX, this.posY, this.posZ, 30, 40,
							80, 0.3F, 1F, 4F, 1, 0.029D, 1200, 0, false);
					this.Attacktask(10, 0, 30D, "gvclib:textures/entity/BulletAAA.obj",
							"gvclib:textures/entity/BulletAAA.png", "hmgww2:hmgww2.fire_grenade", 0, 0, -1, 1.0,
							this.posX, this.posY, this.posZ, 30, 40,
							80, 0.3F, 1F, 4F, 1, 0.029D, 1200, 0, false);
				}
			}
		} // 1
		else{
			cooltime = 0;
			cooltime2 = 0;
			this.rotationp = 0;
			this.motionX -= MathHelper.sin(f1) * sp * thpower/20;
			this.motionZ += MathHelper.cos(f1) * sp * thpower/20;
			if (!this.onGround && this.motionY < 0.0D)
	        {
	            this.motionY *= 0.9D;
	        }
			if( this.th >= 0){
				this.th = this.th -0.5D;
			}
			if( this.thpower >= 0){
				this.thpower = this.thpower -0.5D;
			}
		}
    	
    	
    }
	
	protected void onDeathUpdate() {
		++this.deathTicks;
		if(this.deathTicks == 1){
        	this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 0F, false);
        }
		if(this.onGround && !this.worldObj.isRemote){
			this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 3.5F, false);
			this.setDead();
			Item i = new ItemStack(Blocks.gold_block, 0).getItem();
	        this.dropItem(i, 2);
		}
		if (this.deathTicks == 200 && !this.worldObj.isRemote) {
			this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 3.5F, false);
			this.setDead();
			Item i = new ItemStack(Blocks.gold_block, 0).getItem();
	        this.dropItem(i, 2);
		}
	}
    
	public boolean isConverting() {
		return false;
	}
}
