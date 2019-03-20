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
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityJPN_Tank extends EntityJPNBase
{
	// public int type;
	
    public EntityJPN_Tank(World par1World)
    {
        super(par1World);
        this.setSize(4F, 2.5F);
    }
    
    public double getMountedYOffset() {
		return 1.0D;
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
			if(entity.posY >= this.posY + 2)
			{
				par2 = par2 * this.TankArmor(entity,par2, 0.8F, 1.3F, 1.3F, this.rotation) * this.AntiBullet(entity, par2, 1);
				return super.attackEntityFrom(source, par2);
			}
			else{
				par2 = par2 * this.TankArmor(entity,par2, 0.8F, 1.4F, 1.2F, this.rotationYawHead) * this.AntiBullet(entity, par2, 2);
				return super.attackEntityFrom(source, par2);
			}
		}else{
			return super.attackEntityFrom(source, par2);
		}
    }
	
	
	
	/*protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
    {
		Item i = new ItemStack(Blocks.iron_block, 0).getItem();
        this.dropItem(i, 3);
    }*/
	
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
		this.overlayhight = 0.3F;
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
		reload_time1 = 80;
		this.w1name = "47mmCannon";
		this.ammo2 = 2;
		this.magazine2 = 40;
		reload_time2 = 100;
		this.w2name = "MachineGun";
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
				if (this.server1) {
					if (cooltime > this.ammo1 && this.getRemain_L() > 0) {
							this.Weapon(3, player, 0, 0, 2.1, 1.8,this.posX,this.posY,this.posZ,
									"gvclib:textures/entity/BulletAAA.obj","gvclib:textures/entity/BulletAAA.png","hmgww2:hmgww2.fire_amr", looked,
									30, 3F, 1F, 2, 1, 0.025D, true);//1.57/1.5/3
						cooltime = 0;
						this.setRemain_L(this.getRemain_L() -1);
					}
					this.server1 = false;
				} 
				if (this.server2) {
					if (cooltime2 > this.ammo2 && this.getRemain_R() > 0) {
							this.Weapon(0, player, -1.3F, 0.6, 2.1, 1.8,this.posX,this.posY,this.posZ,
									"gvclib:textures/entity/BulletAAA.obj","gvclib:textures/entity/BulletAAA.png","hmgww2:hmgww2.fire_rifle", looked,
									7, 3, 5F, 2, 1, 0.029D, false);//1.57/1.5/3
						cooltime2 = 0;
						this.setRemain_R(this.getRemain_R() -1);
					}
					this.server2 = false;
				} 
				
				
				
				
			}//player
			
			
		}else if(!this.dead && this.getMobMode() == 0)
    	{// 1
			
			this.AITankMove(f1, sp, turnspeed, 30, 60);
			// attacktask
			{
				if(cooltime > ammo1 && this.getRemain_L() > 0)
				{
					this.Attacktask(3, 1, 40D, "gvclib:textures/entity/BulletAAA.obj",
							"gvclib:textures/entity/BulletAAA.png", "hmgww2:hmgww2.fire_amr", 0, 0, 2.1, 1.8,
							this.posX, this.posY, this.posZ, 15, 5,
							30, 3F, 1F, 2F, 1, 0.025D, 400, 0, true);
				}
			}
		} // 1
    	AI_TankSet.set2(this, "hmgww2:hmgww2.sound_tank", f1, sp, 0.1F);
    }
    
    protected void onDeathUpdate() {
		++this.deathTicks;

		//this.moveEntity(0.0D, -0.05D, 0.0D);
		if(this.deathTicks == 1 && !this.worldObj.isRemote){
        	this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 3.5F, false);
        }
		if (this.deathTicks == 200 && !this.worldObj.isRemote) {
			Item i = new ItemStack(Blocks.iron_block, 0).getItem();
	        this.dropItem(i, 3);
	        
			this.setDead();
		}
	}
    
	public boolean isConverting() {
		return false;
	}
}
