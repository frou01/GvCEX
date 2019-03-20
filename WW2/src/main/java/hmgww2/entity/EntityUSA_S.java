package hmgww2.entity;


import gvclib.entity.EntityB_Bullet;
import gvclib.entity.EntityB_Shell;
import gvclib.item.ItemGunBase;
import hmgww2.mod_GVCWW2;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityUSA_S extends EntityUSABase
{
    public EntityUSA_S(World par1World)
    {
        super(par1World);
        this.setSize(0.4F, 1.8F);
        this.tasks.addTask(1, new EntityAISwimming(this));
        //this.tasks.addTask(1, new AIEntityInvasionFlag(this, 1.0D));
        //this.tasks.addTask(2, new EntityAIWander(this, 1.0D));
        //this.tasks.addTask(9, new EntityAILookIdle(this));
    }
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0D);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(50.0D);
        //this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(30.0D);
    }

    protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
    {
        this.dropItem(mod_GVCWW2.b_magazine, 1);
        this.dropItem(Items.gunpowder, this.worldObj.rand.nextInt(3)+1);
        if(this.worldObj.rand.nextInt(10) == 0){
        	this.dropItem(mod_GVCWW2.gun_m1911, 1);
        }
    }
    
    protected void addRandomArmor()
    {
        super.addRandomArmor();
        int iii = this.worldObj.rand.nextInt(10);
        if(iii == 0){
        	this.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_m1904));
        }else if(iii == 1){
        	this.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_m1904));
        }else if(iii == 2){
        	this.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_bar));
        }else if(iii == 3){
        	this.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_m1b));
        }else if(iii == 4){
        	this.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_m1t));
        }else if(iii == 5){
        	this.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_m1t));
        }else{
        	this.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_m1g));
        }
        this.setCurrentItemOrArmor(4, new ItemStack(mod_GVCWW2.armor_usa));
    }

    public void onUpdate()
    {
    	super.onUpdate();
    	++cooltime;
    	ItemStack itemstack = this.getEquipmentInSlot(0);
    	if(this.getRemain_L() <= 0){
			++reload1;
			if(reload1 == 1){
				if (itemstack != null && itemstack.getItem() == mod_GVCWW2.gun_m1g) 
	        	{
					this.playSound("hmgww2:hmgww2.reload_clip", 2.0F, 1.0F);
	        	}
			}
			if(reload1 == reload_time1 - 10){
				this.playSound("hmgww2:hmgww2.reload_mag", 1.0F, 1.0F);
			}
			if(reload1 >= reload_time1){
				this.setRemain_L(this.magazine);
				reload1 = 0;
			}
		}
    	
    	if (itemstack != null && itemstack.getItem() instanceof ItemGunBase) 
    	{
    		ItemGunBase gun = (ItemGunBase) itemstack.getItem();
    		if (itemstack != null && itemstack.getItem() == mod_GVCWW2.gun_m1g) 
        	{
    			this.AISMove(0, 3, 0, 20, 60);
    			this.ammo1 = 30;
        		this.magazine = gun.getMaxDamage();
        		reload_time1 = gun.reloadtime;
        		if(cooltime > ammo1 && this.getRemain_L() > 0)
    			{
    				this.Attacktask(0, 1, 40D, "gvclib:textures/entity/BulletAAA.obj",
    						"gvclib:textures/entity/BulletAAA.png", "hmgww2:hmgww2.fire_rifle", 0, 0, 1.5, 1,
    						this.posX, this.posY, this.posZ, 15, 5,
    						gun.powor/2, gun.speed, gun.bure * 3, 2F, 1, 0.025D, 400, 0, false);
    			}
        	}
    		if (itemstack != null && itemstack.getItem() == mod_GVCWW2.gun_bar) 
        	{
    			this.AISMove(0, 3, 0, 30, 60);
    			this.ammo1 = 2;
        		this.magazine = gun.getMaxDamage();
        		reload_time1 = gun.reloadtime;
        		if(cooltime > ammo1 && this.getRemain_L() > 0)
    			{
    				this.Attacktask(0, 1, 40D, "gvclib:textures/entity/BulletAAA.obj",
    						"gvclib:textures/entity/BulletAAA.png", "hmgww2:hmgww2.fire_rifle", 0, 0, 1.5, 1,
    						this.posX, this.posY, this.posZ, 15, 5,
    						gun.powor/2, gun.speed, gun.bure * 3, 2F, 1, 0.025D, 400, 0, false);
    			}
        	}
    		if (itemstack != null && itemstack.getItem() == mod_GVCWW2.gun_m1t) 
        	{
    			this.AISMove(0, 3, 0, 10, 60);
    			this.ammo1 = 3;
        		this.magazine = gun.getMaxDamage();
        		reload_time1 = gun.reloadtime;
        		if(cooltime > ammo1 && this.getRemain_L() > 0)
    			{
    				this.Attacktask(0, 1, 20D, "gvclib:textures/entity/BulletAAA.obj",
    						"gvclib:textures/entity/BulletAAA.png", "hmgww2:hmgww2.fire_hg", 0, 0, 1.5, 1,
    						this.posX, this.posY, this.posZ, 15, 5,
    						gun.powor/2, gun.speed, gun.bure * 4, 2F, 1, 0.025D, 400, 0, false);
    			}
        	}
    		if (itemstack != null && itemstack.getItem() == mod_GVCWW2.gun_m1904) 
        	{
    			this.AISMove(0, 3, 0, 40, 60);
    			this.ammo1 = 40;
        		this.magazine = gun.getMaxDamage();
        		reload_time1 = gun.reloadtime;
        		if(cooltime > ammo1 && this.getRemain_L() > 0)
    			{
    				this.Attacktask(0, 1, 50D, "gvclib:textures/entity/BulletAAA.obj",
    						"gvclib:textures/entity/BulletAAA.png", "hmgww2:hmgww2.fire_rifle", 0, 0, 1.5, 1,
    						this.posX, this.posY, this.posZ, 15, 5,
    						gun.powor/2, gun.speed, gun.bure, 2F, 1, 0.025D, 400, 0, false);
    			}
        	}
    		if (itemstack != null && itemstack.getItem() == mod_GVCWW2.gun_m1b) 
        	{
    			this.AISMove(0, 3, 0, 30, 60);
    			this.ammo1 = 80;
        		this.magazine = gun.getMaxDamage();
        		reload_time1 = gun.reloadtime;
        		if(cooltime > ammo1 && this.getRemain_L() > 0)
    			{
    				this.Attacktask(3, 1, 40D, "gvclib:textures/entity/BulletAAA.obj",
    						"gvclib:textures/entity/BulletAAA.png", "hmgww2:hmgww2.fire_roket", 0, 0, 1.5, 1,
    						this.posX, this.posY, this.posZ, 15, 5,
    						gun.powor/2, gun.speed, gun.bure * 2, 2F, 1, 0.025D, 400, 0, true);
    			}
        	}
    	}
    	
    }
    
	public void AttackTask(EntityLivingBase entity1, double x, double z){
    	
		ItemStack itemstack = this.getEquipmentInSlot(0);
    	if (itemstack != null && itemstack.getItem() == mod_GVCWW2.gun_m1g) 
    	{//3
			ItemGunBase gun = (ItemGunBase) itemstack.getItem();
			if(!this.isRiding() && !this.worldObj.isRemote){
				if ((x > 10 || z > 10))
				{
					AIEntityGoFlag goflag = new AIEntityGoFlag(this, (int)entity1.posX, (int)entity1.posY, (int)entity1.posZ, 1D);
		    		goflag.go();
				}
			}
			//gun.flash = false;
			if (cooltime > 30 + this.worldObj.rand.nextInt(5)) {// 2
				if ((x < 40 && z < 40))
				{
					boolean flag = this.getEntitySenses().canSee(entity1);
					double ddy = Math.abs(entity1.posY - this.posY);
					if (flag && ddy < 20) {
						EntityB_Bullet var3 = new EntityB_Bullet(this.worldObj, this);
						var3.Bdamege = 4;
						var3.gra = 0.025;
						var3.friend = this.fri;
						//EntitySnowball var3 = new EntitySnowball(this.worldObj, this);
						double var4 = entity1.posX - this.posX;
						double var6 = entity1.posY + (double) entity1.getEyeHeight()
								+ 0.200000023841858D - var3.posY;
						double var8 = entity1.posZ - this.posZ;
						float var10 = MathHelper.sqrt_double(var4 * var4 + var8 * var8) * 0.01F;
						var3.setThrowableHeading(var4, var6 + (double) var10, var8, 2.0F, 15.0F);
						if (!this.worldObj.isRemote) {
							this.worldObj.spawnEntityInWorld(var3);
						}
						//this.playSound(GVCSoundEvent.Fire_Bullet, 5.0F,1.2F);
						this.playSound(gun.sound, 5.0F, gun.soundspeed);
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
		}//3
    	
    	if (itemstack != null && itemstack.getItem() == mod_GVCWW2.gun_bar) 
    	{//3
			ItemGunBase gun = (ItemGunBase) itemstack.getItem();
			if(!this.isRiding() && !this.worldObj.isRemote){
				if ((x > 20 || z > 20))
				{
					AIEntityGoFlag goflag = new AIEntityGoFlag(this, (int)entity1.posX, (int)entity1.posY, (int)entity1.posZ, 1D);
		    		goflag.go();
				}
			}
			//gun.flash = false;
			if (cooltime > 2 + this.worldObj.rand.nextInt(5)) {// 2
				if ((x < 30 && z < 30))
				{
					boolean flag = this.getEntitySenses().canSee(entity1);
					double ddy = Math.abs(entity1.posY - this.posY);
					if (flag&& ddy < 20) {
						EntityB_Bullet var3 = new EntityB_Bullet(this.worldObj, this);
						var3.Bdamege = 4;
						var3.gra = 0.025;
						var3.friend = this.fri;
						//EntitySnowball var3 = new EntitySnowball(this.worldObj, this);
						double var4 = entity1.posX - this.posX;
						double var6 = entity1.posY + (double) entity1.getEyeHeight()
								+ 0.200000023841858D - var3.posY;
						double var8 = entity1.posZ - this.posZ;
						float var10 = MathHelper.sqrt_double(var4 * var4 + var8 * var8) * 0.01F;
						var3.setThrowableHeading(var4, var6 + (double) var10, var8, 2.0F, 15.0F);
						if (!this.worldObj.isRemote) {
							this.worldObj.spawnEntityInWorld(var3);
						}
						//this.playSound(GVCSoundEvent.Fire_Bullet, 5.0F,1.2F);
						this.playSound(gun.sound, 5.0F, gun.soundspeed);
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
		}//3
    	
    	if (itemstack != null && itemstack.getItem() == mod_GVCWW2.gun_m1t) 
    	{//3
			ItemGunBase gun = (ItemGunBase) itemstack.getItem();
			if(!this.isRiding() && !this.worldObj.isRemote){
				if ((x > 5 || z > 5))
				{
					AIEntityGoFlag goflag = new AIEntityGoFlag(this, (int)entity1.posX, (int)entity1.posY, (int)entity1.posZ, 2D);
		    		goflag.go();
				}
			}
			//gun.flash = false;
			if (cooltime > 3 + this.worldObj.rand.nextInt(5)) {// 2
				if ((x < 15 && z < 15))
				{
					boolean flag = this.getEntitySenses().canSee(entity1);
					double ddy = Math.abs(entity1.posY - this.posY);
					if (flag&& ddy < 8) {
						EntityB_Bullet var3 = new EntityB_Bullet(this.worldObj, this);
						var3.Bdamege = 3;
						var3.gra = 0.025;
						var3.friend = this.fri;
						//EntitySnowball var3 = new EntitySnowball(this.worldObj, this);
						double var4 = entity1.posX - this.posX;
						double var6 = entity1.posY + (double) entity1.getEyeHeight()
								+ 0.200000023841858D - var3.posY;
						double var8 = entity1.posZ - this.posZ;
						float var10 = MathHelper.sqrt_double(var4 * var4 + var8 * var8) * 0.01F;
						var3.setThrowableHeading(var4, var6 + (double) var10, var8, 2.0F, 25.0F);
						if (!this.worldObj.isRemote) {
							this.worldObj.spawnEntityInWorld(var3);
						}
						//this.playSound(GVCSoundEvent.Fire_Bullet, 5.0F,1.2F);
						this.playSound(gun.sound, 5.0F, gun.soundspeed);
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
		}//3
    	
    	
    	if (itemstack != null && itemstack.getItem() == mod_GVCWW2.gun_m1904) 
    	{//3
			ItemGunBase gun = (ItemGunBase) itemstack.getItem();
			if(!this.isRiding() && !this.worldObj.isRemote){
				if ((x > 30 || z > 30))
				{
					AIEntityGoFlag goflag = new AIEntityGoFlag(this, (int)entity1.posX, (int)entity1.posY, (int)entity1.posZ, 1D);
		    		goflag.go();
				}
			}
			//gun.flash = false;
			if (cooltime > 50 + this.worldObj.rand.nextInt(5)) {// 2
				if ((x < 40 && z < 40))
				{
					boolean flag = this.getEntitySenses().canSee(entity1);
					double ddy = Math.abs(entity1.posY - this.posY);
					if (flag) {
						EntityB_Bullet var3 = new EntityB_Bullet(this.worldObj, this);
						var3.Bdamege = 10;
						var3.gra = 0.025;
						var3.friend = this.fri;
						//EntitySnowball var3 = new EntitySnowball(this.worldObj, this);
						double var4 = entity1.posX - this.posX;
						double var6 = entity1.posY + (double) entity1.getEyeHeight()
								+ 0.200000023841858D - var3.posY;
						double var8 = entity1.posZ - this.posZ;
						float var10 = MathHelper.sqrt_double(var4 * var4 + var8 * var8) * 0.01F;
						var3.setThrowableHeading(var4, var6 + (double) var10, var8, 4.0F, 1.0F);
						if (!this.worldObj.isRemote) {
							this.worldObj.spawnEntityInWorld(var3);
						}
						//this.playSound(GVCSoundEvent.Fire_Bullet, 5.0F,1.2F);
						this.playSound(gun.sound, 5.0F, gun.soundspeed);
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
		}//3
    	
    	if (itemstack != null && itemstack.getItem() == mod_GVCWW2.gun_m1b) 
    	{//3
			ItemGunBase gun = (ItemGunBase) itemstack.getItem();
			if(!this.isRiding() && !this.worldObj.isRemote){
				if ((x > 30 || z > 30))
				{
					AIEntityGoFlag goflag = new AIEntityGoFlag(this, (int)entity1.posX, (int)entity1.posY, (int)entity1.posZ, 1D);
		    		goflag.go();
				}
			}
			//gun.flash = false;
			if (cooltime > 80 + this.worldObj.rand.nextInt(5)) {// 2
				if ((x < 40 && z < 40))
				{
					boolean flag = this.getEntitySenses().canSee(entity1);
					double ddy = Math.abs(entity1.posY - this.posY);
					if (flag&& ddy < 8) {
						EntityB_Shell var3 = new EntityB_Shell(this.worldObj, this);
						var3.friend = this.fri;
						var3.Bdamege = 50;
						var3.gra = 0.025;
						var3.ex = mod_GVCWW2.cfg_blockdestory;
						var3.exlevel = 2.0F;
						//EntitySnowball var3 = new EntitySnowball(this.worldObj, this);
						double var4 = entity1.posX - this.posX;
						double var6 = entity1.posY + (double) entity1.getEyeHeight()
								+ 0.200000023841858D - var3.posY;
						double var8 = entity1.posZ - this.posZ;
						float var10 = MathHelper.sqrt_double(var4 * var4 + var8 * var8) * 0.01F;
						var3.setThrowableHeading(var4, var6 + (double) var10, var8, 1.2F, 15.0F);
						if (!this.worldObj.isRemote) {
							this.worldObj.spawnEntityInWorld(var3);
						}
						//this.playSound(GVCSoundEvent.Fire_Bullet, 5.0F,1.2F);
						this.playSound(gun.sound, 5.0F, gun.soundspeed);
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
		}//3
    }
    
	public boolean isConverting() {
		return false;
	}
	protected String getLivingSound()
    {
        return "mob.skeleton.say";
    }

    protected String getHurtSound()
    {
        return "mob.skeleton.hurt";
    }

    protected String getDeathSound()
    {
        return "mob.skeleton.death";
    }

    protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_)
    {
        this.playSound("mob.skeleton.step", 0.15F, 1.0F);
    }
}
