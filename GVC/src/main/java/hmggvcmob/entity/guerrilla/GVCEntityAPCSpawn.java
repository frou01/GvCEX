package hmggvcmob.entity.guerrilla;

import hmggvcmob.entity.guerrilla.GVCEntityGuerrilla;
import hmggvcmob.entity.guerrilla.GVCEntityGuerrillaMG;
import hmggvcmob.entity.guerrilla.GVCEntityGuerrillaRPG;
import hmggvcmob.entity.guerrilla.GVCEntityGuerrillaSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import java.util.Random;

public class GVCEntityAPCSpawn extends EntityGBase
{
	public int fuse;
	
    public EntityLivingBase thrower;
    public double power = 2.5D;
    public boolean flame = false;

	public GVCEntityAPCSpawn(World par1World)
    {
        super(par1World);
        this.fuse = 1;
        this.renderDistanceWeight = 0;
    }
    public void setThrowableHeading(double p_70186_1_, double p_70186_3_, double p_70186_5_, float p_70186_7_, float p_70186_8_)
    {
        float f2 = MathHelper.sqrt_double(p_70186_1_ * p_70186_1_ + p_70186_3_ * p_70186_3_ + p_70186_5_ * p_70186_5_);
        p_70186_1_ /= (double)f2;
        p_70186_3_ /= (double)f2;
        p_70186_5_ /= (double)f2;
        p_70186_1_ += this.rand.nextGaussian() * (double)(this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * (double)p_70186_8_;
        p_70186_3_ += this.rand.nextGaussian() * (double)(this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * (double)p_70186_8_;
        p_70186_5_ += this.rand.nextGaussian() * (double)(this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * (double)p_70186_8_;
        p_70186_1_ *= (double)p_70186_7_;
        p_70186_3_ *= (double)p_70186_7_;
        p_70186_5_ *= (double)p_70186_7_;
        this.motionX = p_70186_1_;
        this.motionY = p_70186_3_;
        this.motionZ = p_70186_5_;
        float f3 = MathHelper.sqrt_double(p_70186_1_ * p_70186_1_ + p_70186_5_ * p_70186_5_);
        this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(p_70186_1_, p_70186_5_) * 180.0D / Math.PI);
        this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(p_70186_3_, (double)f3) * 180.0D / Math.PI);
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

            
            
            if (!this.worldObj.isRemote)
            {
                //this.explode();
                Random rnd = new Random();
                int mobgatcha = rnd.nextInt(20);
                if(mobgatcha <2){
                    GVCEntityGuerrillaSP entityskeleton = new GVCEntityGuerrillaSP(worldObj);
                    entityskeleton.setLocationAndAngles(this.posX + i, this.posY + 2, this.posZ + i, this.rotationYaw, 0.0F);
                    entityskeleton.addRandomArmor();
                    worldObj.spawnEntityInWorld(entityskeleton);
                }else if(mobgatcha<12){
                    GVCEntityGuerrilla entityskeleton = new GVCEntityGuerrilla(worldObj);
                    entityskeleton.setLocationAndAngles(this.posX + i, this.posY + 2, this.posZ + i, this.rotationYaw, 0.0F);
                    entityskeleton.addRandomArmor();
                    worldObj.spawnEntityInWorld(entityskeleton);
                }else if(mobgatcha<17){
                    GVCEntityGuerrillaMG entityskeleton = new GVCEntityGuerrillaMG(worldObj);
                    entityskeleton.setLocationAndAngles(this.posX + i, this.posY + 2, this.posZ + i, this.rotationYaw, 0.0F);
                    entityskeleton.addRandomArmor();
                    worldObj.spawnEntityInWorld(entityskeleton);
                }else{
                    GVCEntityGuerrillaRPG entityskeleton = new GVCEntityGuerrillaRPG(worldObj);
                    entityskeleton.setLocationAndAngles(this.posX + i, this.posY + 2, this.posZ + i, this.rotationYaw, 0.0F);
                    entityskeleton.addRandomArmor();
                    worldObj.spawnEntityInWorld(entityskeleton);
                }
                //entityskeleton.mountEntity(entityskeleton1);
                
            }
        }
        else
        {
            this.worldObj.spawnParticle("smoke", this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D);
        }
    }

    public void explode()
    {
        float var1 = 2.7F;
        this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, var1, false);
        
        /*IFN_Explosion ex = new IFN_Explosion(worldObj, this, posX, posY, posZ, 3.5F);
        ex.isFlaming = false;
		ex.isSmoking = true;
        ex.doExplosionA();
        ex.doExplosionB(true);*/
        
    }

    /**
     * Called when this EntityThrowable hits a block or entity.
     */
    protected void onImpact(MovingObjectPosition par1MovingObjectPosition){}
}
