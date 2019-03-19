package hmggvcmob.entity.friend;


import hmggvcutil.GVCUtils;
import hmggvcmob.ai.AIAttackGun;
import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GVCEntitySoldier extends EntitySoBase
{
    public GVCEntitySoldier(World par1World)
    {
        super(par1World);
        this.setSize(0.6F, 1.8F);
        this.tasks.addTask(1,aiAttackGun = new AIAttackGun(this,80,20,10,15,15,true));
    }
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(movespeed = 0.33000000417232513D);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(60.0D);
        //this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(30.0D);
    }
    
    public void addRandomArmor()
    {
        super.addRandomArmor();
        this.setCurrentItemOrArmor(0, new ItemStack(GVCUtils.fn_m16a4));
    }

//    public void onUpdate()
//    {
//    	super.onUpdate();
//
//
//    	{// 1
//			Entity entity = null;
//			List<Entity> llist = this.worldObj.getEntitiesWithinAABBExcludingEntity(this,
//					this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(50D, 50D, 50D));
//			if (llist != null) {
//				for (int lj = 0; lj < llist.size(); lj++) {
//					Entity entity1 = (Entity) llist.get(lj);
//					if (entity1.canBeCollidedWith()) {
//						if (this.CanAttack(entity1) && entity1 != null )
//						//if (entity1 instanceof EntityPlayer && entity1 != null )
//						{
//							{
//								boolean flag = this.getEntitySenses().canSee(entity1);
//									double d5 = entity1.posX - this.posX;
//									double d7 = entity1.posZ - this.posZ;
//									double d6 = entity1.posY - this.posY;
//									double d1 = this.posY - (entity1.posY);
//						            double d3 = (double)MathHelper.sqrt_double(d5 * d5 + d7 * d7);
//						            float f11 = (float)(-(Math.atan2(d1, d3) * 180.0D / Math.PI));
//									double ddx = Math.abs(d5);
//									double ddz = Math.abs(d7);
//
//									if (flag && this.getHealth() > 0.0F) {
//										this.rotationYawHead = this.renderYawOffset = -((float) Math.atan2(d5, d7)) * 180.0F
//										/ (float) Math.PI;
//								this.rotationp = this.rotationPitch = -f11 + 10;
//								/*this.getLookHelper().setLookPosition(
//										entity1.posX, entity1.posY + (double)entity1.getEyeHeight(), entity1.posZ, 10.0F,
//										(float)this.getVerticalFaceSpeed());*/
//										this.AttackTask((EntityLivingBase) entity1, ddx, ddz);
//										break;
//									}
//							}
//							//break;
//						}
//					}
//				}
//			}
//			// EntityAI_MoveS.MoveEntity(this, true, 0.5D, 60D);
//		} // 1
//
//    }
//
//	public void AttackTask(EntityLivingBase entity1, double x, double z){
//
//		ItemStack itemstack = this.getEquipmentInSlot(0);
//    	//if (itemstack != null && itemstack.getItem() == mod_TFMDF.gun_t3ar)
//    	{//3
//			//TFItemGunBase gun = (TFItemGunBase) itemstack.getItem();
//			if(!this.isRiding() && !this.worldObj.isRemote && this.getFlagMode() == 0){
//				if ((x > 10 || z > 10))
//				{
//					MoveHelper goflag = new MoveHelper(this, (int)entity1.posX, (int)entity1.posY, (int)entity1.posZ, 1D);
//		    		goflag.go();
//				}
//			}
//			//gun.flash = false;
//			if (cooltime > 5 + this.worldObj.rand.nextInt(5)) {// 2
//				if ((x < 20 && z < 20))
//				{
//					boolean flag = this.getEntitySenses().canSee(entity1);
//					double ddy = Math.abs(entity1.posY - this.posY);
//					if (flag && ddy < 8) {
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
//						var3.setThrowableHeading(var4, var6 + (double) var10, var8, 2.0F, 10.0F);
//						if (!this.worldObj.isRemote) {
//							this.worldObj.spawnEntityInWorld(var3);
//						}
//						//this.playSound(GVCSoundEvent.Fire_Bullet, 5.0F,1.2F);
//						this.playSound("gvcguns:gvcguns.fire", 3.0F, 1.0F);
//						if (!this.worldObj.isRemote) {
//							cooltime = 0;
//						}
//						//gun.flash = true;
//						if (GVCMobPlus.cfg_canEjectCartridge) {
//							GVCEntityBulletCartridge var30 = new GVCEntityBulletCartridge(this.worldObj, this, 1);
//							var30.setThrowableHeading(var4, var6 + (double) var10, var8, -0.1F, 12.0F);
//							// this.playSound("gvcguns:gvcguns.grenade", 1.0F,
//							// 1.5F);
//							this.worldObj.spawnEntityInWorld(var30);
//						}
//
//						if (this.worldObj.rand.nextInt(10) == 0) {
//							if (GVCMobPlus.cfg_throwngrenade == true) {
//								GVCEntityGrenade var30 = new GVCEntityGrenade(this.worldObj, this);
//								var30.setThrowableHeading(var4, var6 + (double) var10, var8, 1.0F, 12.0F);
//								this.playSound("gvcguns:gvcguns.grenade", 1.0F, 1.5F);
//								this.worldObj.spawnEntityInWorld(var30);
//							}
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
//
//    }
    
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
