package hmgww2.entity;


import hmgww2.mod_GVCWW2;
import hmgww2.network.WW2MessageKeyPressed;
import hmgww2.network.WW2PacketHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityRUS_Fighter extends EntityRUSBase
{
	// public int type;
	
    public EntityRUS_Fighter(World par1World)
    {
        super(par1World);
        this.setSize(4F, 2F);
        this.tasks.addTask(0, new EntityAISwimming(this));
        //this.tasks.addTask(1, new AIEntityInvasionFlag(this, 1.0D));
      //  this.tasks.addTask(2, new AIEntityAIWander(this, 1.0D));
        
    }
    
    public double getMountedYOffset() {
		return 0.6D;
	}
    

	protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0D);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(80.0D);
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
    	float f1 = this.rotationYawHead * (2 * (float) Math.PI / 360);
		float sp = 0.03F;
		this.overlayhight = 0.0F;
		this.overlayhight_3 = 1F;
		this.thmax = 40;
		float turnspeed = 1.4F;
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
		this.ammo2 = 1;
		this.magazine2 = 100;
		reload_time2 = 150;
		this.w2name = "7.7mmMachineGun";
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
			if(reload2 == reload_time2 - 40){
				this.playSound("hmgww2:hmgww2.reload_mg", 1.0F, 1.0F);
			}
			if(reload2 >= reload_time2){
				this.setRemain_R(this.magazine2);
				reload2 = 0;
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
				if(leftc){
					WW2PacketHandler.INSTANCE.sendToServer(new WW2MessageKeyPressed(11,this.getEntityId()));
					this.server1 = true;
				}
				
				if (this.server1) {
					if (cooltime > this.ammo1 && this.getRemain_L() > 0) {
						this.Weapon(1, player, 0F, 0, 1.1, 2.0,this.posX,this.posY,this.posZ,
								"gvclib:textures/entity/BulletAAA.obj","gvclib:textures/entity/BulletAAA.png","hmgww2:hmgww2.fire_30mm", look,
								12, 4F, 1F, 1, 1, 0.029D, false);//1.57/1.5/3
						cooltime = 0;
						this.setRemain_L(this.getRemain_L() -1);
					}
					if (cooltime2 > this.ammo2 && this.getRemain_R() > 0) {
						this.Weapon(0, player, 1.57F, 0.5, 1.5, 2.0,this.posX,this.posY,this.posZ,
								"gvclib:textures/entity/BulletAAA.obj","gvclib:textures/entity/BulletAAA.png","hmgww2:hmgww2.fire_rifle", look,
								6, 4F, 1F, 1, 1, 0.029D, false);//1.57/1.5/3
					cooltime2 = 0;
					this.setRemain_R(this.getRemain_R() -1);
				    }
					this.server1 = false;
				} 
				
				
			}
			
			
			
			
		}else if(!this.dead && this.getMobMode() == 0)
    	{// 1
			//this.AITankMove(f1, sp, turnspeed, 30, 60);
			this.AIAirCraftMove(f1, sp, turnspeed, 30, 120D);
			if(cooltime > ammo1 && this.getRemain_L() > 0)
			{
				this.Attacktask(1, 10, 40D, "gvclib:textures/entity/BulletAAA.obj",
						"gvclib:textures/entity/BulletAAA.png", "hmgww2:hmgww2.fire_30mm", 0F, 0, 1.1, 2.0,
						this.posX, this.posY, this.posZ, 15, 15,
						12, 4F, 1F, 1, 1, 0.029D, 400, 0, false);
			}
			if(cooltime2 > this.ammo2 && this.getRemain_R() > 0)
			{
				this.Attacktask(0, 12, 40D, "gvclib:textures/entity/BulletAAA.obj",
						"gvclib:textures/entity/BulletAAA.png", "hmgww2:hmgww2.fire_rifle", 1.57F, 0.5, 1.5, 2.0,
						this.posX, this.posY, this.posZ, 15, 15,
						6, 4F, 1F, 1, 1, 0.029D, 400, 0, false);
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
	        this.dropItem(i, 1);
		}
		if (this.deathTicks == 200 && !this.worldObj.isRemote) {
			this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 3.5F, false);
			this.setDead();
			Item i = new ItemStack(Blocks.gold_block, 0).getItem();
	        this.dropItem(i, 1);
		}
	}
    
	public boolean isConverting() {
		return false;
	}
}
