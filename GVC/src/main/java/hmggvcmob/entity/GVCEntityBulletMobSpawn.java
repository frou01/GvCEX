package hmggvcmob.entity;

import hmggvcmob.entity.guerrilla.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import java.util.Random;

import static hmggvcmob.GVCMobPlus.*;

public class GVCEntityBulletMobSpawn extends EntityThrowable
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

	public GVCEntityBulletMobSpawn(World par1World)
    {
        super(par1World);
        this.fuse = 1;
    }

    public GVCEntityBulletMobSpawn(World par1World, EntityLivingBase par2EntityLivingBase)
    {
        super(par1World, par2EntityLivingBase);
        this.fuse = 1;
    }

    public GVCEntityBulletMobSpawn(World par1World, double par2, double par4, double par6)
    {
        super(par1World, par2, par4, par6);
        this.fuse = 1;
    }

    
    public void onUpdate()
    {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY -= 0.03999999910593033D;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9800000190734863D;
        this.motionY *= 0.9800000190734863D;
        this.motionZ *= 0.9800000190734863D;
        
        int i = this.worldObj.rand.nextInt(3);

        if (this.onGround)
        {
            this.motionX *= 0.699999988079071D;
            this.motionZ *= 0.699999988079071D;
            this.motionY *= -0.5D;
        }

        
        
        if (this.fuse-- <= 0)
        {
        	
        	
            this.setDead();

        	int x = MathHelper.floor_double(this.posX);
        	int y = MathHelper.floor_double(this.posY);
        	int z = MathHelper.floor_double(this.posZ);
        	int i1 = this.worldObj.rand.nextInt(10);
        	int j = this.worldObj.rand.nextInt(2);
        	int k = this.worldObj.rand.nextInt(11);
        	int i2 = this.worldObj.rand.nextInt(3)+1;

        			if (!this.worldObj.isRemote){
        				for (int i0 = 0; i0 < i2; ++i0){
        				this.SpwanGuerrilla(i1, j+2, k, 7);
        				}
            	}
        }
        else
        {
            this.worldObj.spawnParticle("smoke", this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D);
        }
    }

    public void SpwanGuerrilla(int x, int y, int z, int pa){
    	int i = this.worldObj.rand.nextInt(10);
    	int j = this.worldObj.rand.nextInt(11);
    	//int i = 0;
    	   if(this.worldObj.rand.nextInt(pa) == 0
    			   ||this.worldObj.rand.nextInt(pa) == 1
    			   ||this.worldObj.rand.nextInt(pa) == 2){
    	   GVCEntityGuerrilla entityskeleton = new GVCEntityGuerrilla(worldObj);
           entityskeleton.setLocationAndAngles(this.posX+i+x-5, this.posY+y, this.posZ+j+z-5, this.rotationYaw, 0.0F);
           entityskeleton.setCurrentItemOrArmor(0, new ItemStack((Item)Guns_AR.get(new Random().nextInt(Guns_AR.size()))));
           worldObj.spawnEntityInWorld(entityskeleton);
               }
    	   if(this.worldObj.rand.nextInt(pa) == 4){
        	   GVCEntityGuerrillaRPG entityskeleton = new GVCEntityGuerrillaRPG(worldObj);
               entityskeleton.setLocationAndAngles(this.posX+i+x-5, this.posY+y, this.posZ+j+z-5, this.rotationYaw, 0.0F);
               entityskeleton.setCurrentItemOrArmor(0,  new ItemStack((Item)Guns_RR.get(new Random().nextInt(Guns_RR.size()))));
               worldObj.spawnEntityInWorld(entityskeleton);
               }
    	   if(this.worldObj.rand.nextInt(pa) == 5){
        	   GVCEntityGuerrillaMG entityskeleton = new GVCEntityGuerrillaMG(worldObj);
               entityskeleton.setLocationAndAngles(this.posX+i+x-5, this.posY+y-5, this.posZ+j+z-5, this.rotationYaw, 0.0F);
               entityskeleton.setCurrentItemOrArmor(0,  new ItemStack((Item)Guns_LMG.get(new Random().nextInt(Guns_LMG.size()))));
               worldObj.spawnEntityInWorld(entityskeleton);
               }
    	   if(this.worldObj.rand.nextInt(pa) == 6){
        	   GVCEntityGuerrillaSP entityskeleton = new GVCEntityGuerrillaSP(worldObj);
               entityskeleton.setLocationAndAngles(this.posX+i+x-5, this.posY+y-5, this.posZ+j+z-5, this.rotationYaw, 0.0F);
               entityskeleton.setCurrentItemOrArmor(0,  new ItemStack((Item)Guns_SR.get(new Random().nextInt(Guns_SR.size()))));
               worldObj.spawnEntityInWorld(entityskeleton);
               }
    	   if(this.worldObj.rand.nextInt(pa) == 7){
        	   GVCEntityGuerrillaBM entityskeleton = new GVCEntityGuerrillaBM(worldObj);
               entityskeleton.setLocationAndAngles(this.posX+i+x-5, this.posY+y-5, this.posZ+j+z-5, this.rotationYaw, 0.0F);
               //entityskeleton.setCurrentItemOrArmor(0, new ItemStack(GVCUtils.fn_pkm));
               worldObj.spawnEntityInWorld(entityskeleton);
               }
    }
    
    /**
     * Called when this EntityThrowable hits a block or entity.
     */
    protected void onImpact(MovingObjectPosition par1MovingObjectPosition){}
}
