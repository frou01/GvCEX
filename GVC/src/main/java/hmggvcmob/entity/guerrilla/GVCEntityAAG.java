package hmggvcmob.entity.guerrilla;


import handmadevehicle.SlowPathFinder.WorldForPathfind;
import hmggvcutil.GVCUtils;
import hmggvcmob.ai.AIAttackGun;
import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import static hmggvcmob.GVCMobPlus.cfg_guerrillasrach;

public class GVCEntityAAG extends EntityGBase
{
    public GVCEntityAAG(World par1World)
    {
        super(par1World);
        canRideVehicle = false;
        this.setSize(0.4F, 1.8F);
        this.tasks.addTask(1,aiAttackGun = new AIAttackGun(this,1200,1200,10,100,true,true,new WorldForPathfind(worldObj)));
        viewWide = 0.75f;
        spread = 0;
        canuseAlreadyPlacedGun = false;
        canusePlacedGun = false;
    }
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0d);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(80.0D);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(cfg_guerrillasrach);
        //this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(30.0D);
    }
    
    public static float getMobScale() {
		return 4;
	}
    
    public void addRandomArmor()
    {
        super.addRandomArmor();
        this.setCurrentItemOrArmor(0, new ItemStack(GVCUtils.fn_pkm));
    }

    /**
     * Drop 0-2 items of this living's type
     */
    protected void dropFewItems(boolean par1, int par2)
    {
        int var3;
        int var4;
        var3 = this.rand.nextInt(3 + par2);
        for (var4 = 0; var4 < var3; ++var4)
        {
            this.dropItem(Items.gunpowder, 1);
        }

        for (var4 = 0; var4 < var3; ++var4)
        {
            this.dropItem(Items.emerald, 1);
        }
        
        for (var4 = 0; var4 < var3; ++var4)
        {
            this.dropItem(GVCUtils.fn_magazine, 1);
        }
        if(getHeldItem()!=null)
        this.dropItem(getHeldItem().getItem(), 1);
    }

    protected void dropRareDrop(int par1)
    {
            this.entityDropItem(new ItemStack(Items.skull, 1, 1), 0.0F);
    }

    public void onUpdate()
    {
        movespeed = 0;
        if(!worldObj.isRemote)
            if (this.getAttackTarget() != null) {
                this.setSneaking(true);
            } else {
                this.setSneaking(false);
            }
        super.onUpdate();
    }
//
//	public void AttackTask(EntityLivingBase entity1, double x, double z){
//
//    	//if (itemstack != null && itemstack.getItem() == mod_TFMDF.gun_t3ar)
//    	{//3
//			//TFItemGunBase gun = (TFItemGunBase) itemstack.getItem();
//			//gun.flash = false;
//			if (cooltime > 2 + this.worldObj.rand.nextInt(5)) {// 2
//				if ((x < 60 && z < 60))
//				{
//					boolean flag = this.getEntitySenses().canSee(entity1);
//					double ddy = Math.abs(entity1.posY - this.posY);
//					if (flag) {
//						GVCEntityBulletGe var3 = new GVCEntityBulletGe(this.worldObj, this);
//						var3.Bdamege = 7;
//						var3.gra = 0.025;
//						var3.friend = this.fri;
//						//EntitySnowball var3 = new EntitySnowball(this.worldObj, this);
//						double var4 = entity1.posX - this.posX;
//						double var6 = entity1.posY + (double) entity1.getEyeHeight()
//								+ 0.200000023841858D - var3.posY;
//						double var8 = entity1.posZ - this.posZ;
//						float var10 = MathHelper.sqrt_double(var4 * var4 + var8 * var8) * 0.01F;
//						var3.setThrowableHeading(var4, var6 + (double) var10, var8, 3.0F, 10.0F);
//						if (!this.worldObj.isRemote) {
//							this.worldObj.spawnEntityInWorld(var3);
//						}
//						//this.playSound(GVCSoundEvent.Fire_Bullet, 5.0F,1.2F);
//						this.playSound("gvcguns:gvcguns.fire", 3.0F, 1.0F);
//						if (!this.worldObj.isRemote) {
//							cooltime = 0;
//						}
//						//gun.flash = true;
//					}
//				}
//			} // 2
//			else {
//				if (!this.worldObj.isRemote) {
//					++cooltime;
//				}
//			}
//		}//3
//
//    }

    public void readEntityFromNBT(NBTTagCompound p_70037_1_)
    {
        canDespawn = p_70037_1_.getBoolean("candespawn");
        super.readEntityFromNBT(p_70037_1_);
    }
    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound p_70014_1_)
    {
        p_70014_1_.setBoolean("candespawn" , canDespawn);
        super.writeEntityToNBT(p_70014_1_);
    }
    protected boolean canDespawn()
    {
        return canDespawn;
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
