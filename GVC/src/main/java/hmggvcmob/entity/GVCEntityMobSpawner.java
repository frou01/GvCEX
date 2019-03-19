package hmggvcmob.entity;

import java.util.List;

import hmggvcutil.GVCUtils;
import hmggvcmob.GVCMobPlus;
import hmggvcmob.entity.guerrilla.*;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class GVCEntityMobSpawner extends EntityThrowable
{
	public int fuse;
	
    public EntityLivingBase thrower;
    public double power = 2.5D;	    
    public double punch = 1;		
    public boolean flame = false;
    public double explosionX;
    public double explosionY;
    public double explosionZ;
    public float explosionSize;

	public GVCEntityMobSpawner(World par1World)
    {
        super(par1World);
        this.fuse = 60;
    }

    public GVCEntityMobSpawner(World par1World, EntityLivingBase par2EntityLivingBase)
    {
        super(par1World, par2EntityLivingBase);
        this.fuse = 60;
    }

    public GVCEntityMobSpawner(World par1World, double par2, double par4, double par6)
    {
        super(par1World, par2, par4, par6);
        this.fuse = 60;
    }
    public void onUpdate()
    {
    	int x = MathHelper.floor_double(this.posX);
    	int y = MathHelper.floor_double(this.posY);
    	int z = MathHelper.floor_double(this.posZ);
    	
    	this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY -= 0.03999999910593033D;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9800000190734863D;
        this.motionY *= 0.9800000190734863D;
        this.motionZ *= 0.9800000190734863D;


        if (this.onGround)
        {
            this.motionX *= 0.699999988079071D;
            this.motionZ *= 0.699999988079071D;
            this.motionY *= 0.5D;
        }
    	
    	
    	if(this.worldObj.getBlock(x, y-1, z) == GVCMobPlus.fn_mobspawner || this.worldObj.getBlock(x, y, z) == GVCMobPlus.fn_mobspawner){
    		
    	}
    	else{
    		this.setDead();
    		for (int i0 = 0; i0 < 3; ++i0){
        		for (int i1 = 0; i1 < 3; ++i1){
        			for (int i2 = 0; i2 < 3; ++i2){
        				if(this.worldObj.getBlock(x+i0-1, y+i1-1, z+i2-1) == GVCMobPlus.fn_mobspawner){
        				this.worldObj.setBlock(x+i0-1, y+i1-1, z+i2-1, Blocks.air);
        				}
        			}
        		}
    		}
    	}
    	
    	
    	super.onUpdate();
    	
    	int i1 = this.worldObj.rand.nextInt(20);
    	int i2 = this.worldObj.rand.nextInt(21);
    	
    	EntityMob entity = null;
        List llist = this.worldObj.getEntitiesWithinAABBExcludingEntity(entity, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(30.0D, 30.0D, 30.0D));
        double d0 = 8.0D;
        EntityLivingBase entitylivingbase = this.getThrower();
        if(llist!=null){
            for (int lj = 0; lj < llist.size(); lj++) {
            	
            	Entity entity1 = (Entity)llist.get(lj);
            	if (entity1.canBeCollidedWith() && (entity1 != entitylivingbase))
                {
            		if ((entity1 instanceof EntityPlayer) && entity1 != null)
                    {
            			if (!this.worldObj.isRemote){
            				this.SpwanGuerrilla(-10+i1, 0, -10+i2, 300);
            				}
                    }
                }
            }
        }
    	
    	
    	
    	
    	
    	
    	
    	
    	
    }

    public void SpwanGuerrilla(int x, int y, int z, int pa){
    	int i = this.worldObj.rand.nextInt(10);
    	//int i = 0;
    	   if(this.worldObj.rand.nextInt(pa) == 0
    			   ||this.worldObj.rand.nextInt(pa) == 1
    			   ||this.worldObj.rand.nextInt(pa) == 2){
    	   GVCEntityGuerrilla entityskeleton = new GVCEntityGuerrilla(worldObj);
           entityskeleton.setLocationAndAngles(this.posX+i+x, this.posY+y, this.posZ+i+z, this.rotationYaw, 0.0F);
           entityskeleton.setCurrentItemOrArmor(0, new ItemStack(GVCUtils.fn_ak74));
           worldObj.spawnEntityInWorld(entityskeleton);
               }
    	   /*if(this.worldObj.rand.nextInt(pa) == 3){
    	   GVCEntityGuerrillaP entityskeleton = new GVCEntityGuerrillaP(worldObj);
           entityskeleton.setLocationAndAngles(this.posX+i+x, this.posY+y+20, this.posZ+i+z, this.rotationYaw, 0.0F);
           entityskeleton.setCurrentItemOrArmor(0, new ItemStack(GVCUtils.fn_aks74u));
           worldObj.spawnEntityInWorld(entityskeleton);
               }*/
    	   if(this.worldObj.rand.nextInt(pa) == 4){
        	   GVCEntityGuerrillaRPG entityskeleton = new GVCEntityGuerrillaRPG(worldObj);
               entityskeleton.setLocationAndAngles(this.posX+i+x, this.posY+y, this.posZ+i+z, this.rotationYaw, 0.0F);
               entityskeleton.setCurrentItemOrArmor(0, new ItemStack(GVCUtils.fn_rpg7));
               worldObj.spawnEntityInWorld(entityskeleton);
               }
    	   if(this.worldObj.rand.nextInt(pa) == 5){
        	   GVCEntityGuerrillaMG entityskeleton = new GVCEntityGuerrillaMG(worldObj);
               entityskeleton.setLocationAndAngles(this.posX+i+x, this.posY+y, this.posZ+i+z, this.rotationYaw, 0.0F);
               entityskeleton.setCurrentItemOrArmor(0, new ItemStack(GVCUtils.fn_pkm));
               worldObj.spawnEntityInWorld(entityskeleton);
               }
    	   if(this.worldObj.rand.nextInt(pa) == 6){
        	   GVCEntityGuerrillaSP entityskeleton = new GVCEntityGuerrillaSP(worldObj);
               entityskeleton.setLocationAndAngles(this.posX+i+x, this.posY+y, this.posZ+i+z, this.rotationYaw, 0.0F);
               entityskeleton.setCurrentItemOrArmor(0, new ItemStack(GVCUtils.fn_svd));
               worldObj.spawnEntityInWorld(entityskeleton);
               }
    	   if(this.worldObj.rand.nextInt(pa) == 7){
        	   GVCEntityGuerrillaBM entityskeleton = new GVCEntityGuerrillaBM(worldObj);
               entityskeleton.setLocationAndAngles(this.posX+i+x, this.posY+y, this.posZ+i+z, this.rotationYaw, 0.0F);
               //entityskeleton.setCurrentItemOrArmor(0, new ItemStack(GVCUtils.fn_pkm));
               worldObj.spawnEntityInWorld(entityskeleton);
               }
    }
    
    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "mob.irongolem.hit";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "mob.irongolem.death";
    }

    protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_)
    {
        this.playSound("mob.irongolem.walk", 1.0F, 1.0F);
    }

	@Override
	protected void onImpact(MovingObjectPosition p_70184_1_) {
		
	}
	protected boolean canDespawn()
    {
        return false;
    }
}