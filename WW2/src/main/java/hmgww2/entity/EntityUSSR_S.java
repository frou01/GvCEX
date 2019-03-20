package hmgww2.entity;


import gvclib.item.ItemGunBase;
import hmgww2.mod_GVCWW2;
import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityUSSR_S extends EntityUSSRBase
{
    public EntityUSSR_S(World par1World)
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
        	this.dropItem(mod_GVCWW2.gun_tt33, 1);
        }
    }

    protected void addRandomArmor()
    {
        super.addRandomArmor();
        int iii = this.worldObj.rand.nextInt(10);
        if(iii == 0){
        	this.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_ppsh41));
        }else if(iii == 1){
        	this.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_ppsh41));
        }else if(iii == 2){
        	this.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_dp28));
        }else if(iii == 3){
        	this.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_m1891sr));
        }else if(iii == 4){
        	this.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_grenadet));
        }else if(iii == 5){
        	this.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_grenadet));
        }else
        {
        	this.setCurrentItemOrArmor(0, new ItemStack(mod_GVCWW2.gun_m1891));
        }
        this.setCurrentItemOrArmor(4, new ItemStack(mod_GVCWW2.armor_rus));
    }

    public void onUpdate()
    {
    	super.onUpdate();
    	++cooltime;
    	if(this.getRemain_L() <= 0){
			++reload1;
			if(reload1 == reload_time1 - 10){
				this.playSound("hmgww2:hmgww2.reload_mag", 1.0F, 1.0F);
			}
			if(reload1 >= reload_time1){
				this.setRemain_L(this.magazine);
				reload1 = 0;
			}
		}
    	
    	ItemStack itemstack = this.getEquipmentInSlot(0);
    	if (itemstack != null && itemstack.getItem() instanceof ItemGunBase) 
    	{
    		ItemGunBase gun = (ItemGunBase) itemstack.getItem();
    		if (itemstack != null && itemstack.getItem() == mod_GVCWW2.gun_m1891) 
        	{
    			this.AISMove(0, 3, 0, 20, 60);
    			this.ammo1 = 40;
        		this.magazine = gun.getMaxDamage();
        		reload_time1 = gun.reloadtime;
        		if(cooltime > ammo1 && this.getRemain_L() > 0)
    			{
    				this.Attacktask(0, 1, 40D, "gvclib:textures/entity/BulletAAA.obj",
    						"gvclib:textures/entity/BulletAAA.png", "hmgww2:hmgww2.fire_rifle", 0, 0, 1.5, 1,
    						this.posX, this.posY, this.posZ, 15, 5,
    						gun.powor/2, gun.speed, gun.bure * 2, 2F, 1, 0.025D, 400, 0, false);
    			}
        	}
    		if (itemstack != null && itemstack.getItem() == mod_GVCWW2.gun_dp28) 
        	{
    			this.AISMove(0, 3, 0, 30, 60);
    			this.ammo1 = 3;
        		this.magazine = gun.getMaxDamage();
        		reload_time1 = gun.reloadtime;
        		if(cooltime > ammo1 && this.getRemain_L() > 0)
    			{
    				this.Attacktask(0, 1, 40D, "gvclib:textures/entity/BulletAAA.obj",
    						"gvclib:textures/entity/BulletAAA.png", "hmgww2:hmgww2.fire_rifle", 0, 0, 1.5, 1,
    						this.posX, this.posY, this.posZ, 15, 5,
    						gun.powor/2, gun.speed, gun.bure * 4, 2F, 1, 0.025D, 400, 0, false);
    			}
        	}
    		if (itemstack != null && itemstack.getItem() == mod_GVCWW2.gun_ppsh41) 
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
    						gun.powor/2, gun.speed, gun.bure * 5, 2F, 1, 0.025D, 400, 0, false);
    			}
        	}
    		if (itemstack != null && itemstack.getItem() == mod_GVCWW2.gun_m1891sr) 
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
    		
    	}
    	if (itemstack != null && itemstack.getItem() == mod_GVCWW2.gun_grenadet) 
    	{
			this.AISMove(0, 3, 0, 10, 60);
			this.ammo1 = 80;
    		this.magazine = 1;
    		reload_time1 = 80;
    		if(cooltime > ammo1 && this.getRemain_L() > 0)
			{
				this.Attacktask(5, 1, 20D, "gvclib:textures/entity/BulletAAA.obj",
						"gvclib:textures/entity/BulletAAA.png", "hmgww2:hmgww2.throw_grenade", 0, 0, 1.5, 1,
						this.posX, this.posY, this.posZ, 15, 5,
						50, 1, 5, 2F, 1, 0.025D, 400, 0, false);
			}
    	}
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
