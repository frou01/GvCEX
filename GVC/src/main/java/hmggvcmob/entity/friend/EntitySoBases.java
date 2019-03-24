package hmggvcmob.entity.friend;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hmggvcmob.IflagBattler;
import hmggvcmob.tile.TileEntityFlag;
import net.minecraft.block.Block;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import static hmggvcmob.GVCMobPlus.fn_PMCflag;
import static hmggvcmob.GVCMobPlus.fn_Supplyflag;
import static java.lang.Math.abs;

public class EntitySoBases extends EntityCreature implements INpc , IflagBattler {
	private static final IEntitySelector horseBreedingSelector = new IEntitySelector()
	{
		private static final String __OBFID = "CL_00001642";
		/**
		 * Return whether the specified entity is applicable to this filter.
		 */
		public boolean isEntityApplicable(Entity p_82704_1_)
		{
			return p_82704_1_ instanceof EntityHorse && ((EntityHorse)p_82704_1_).func_110205_ce();
		}
	};
	public boolean zoom = true;
	public float roo;
	public int deathTicks;
	public EntityLivingBase fri;
	
	public boolean vehicle = false;
	public boolean opentop = true;


	public boolean server1 = false;
	public boolean server2 = false;
	public boolean serverspace = false;
	public boolean serverx = false;
	public boolean serverw = false;
	public boolean servers = false;
	public boolean servera = false;
	public boolean serverd = false;
	public boolean serverf = false;
	
	
	
	public double thpower;
	public float th;
	public double thpera = 0;
	public float throte = 0;
	
	public int soundtick = 0;
	
	public float overlayhight = 1.0F;
	public float overlayhight_3 = 1.0F;

	public int ammo_2;
	public int cooltime_2;
	public int cooltime_3;
	public int cooltime_4;
	public boolean combattask_2 = false;
	public boolean combattask_4 = false;
	public boolean onstarting = false;
	public boolean onstopping = false;
	public boolean isstanding = false;
	public int flagx;
	public int flagy;
	public int flagz;
	
	public EntitySoBases(World par1World) {
		super(par1World);
		renderDistanceWeight = 16384;
	}

	
	/**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound p_70037_1_)
    {
        super.readEntityFromNBT(p_70037_1_);
        
        setMobMode(p_70037_1_.getInteger("MobMode"));
    }
	/**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound p_70014_1_)
    {
        super.writeEntityToNBT(p_70014_1_);
        
        p_70014_1_.setInteger("MobMode", getMobMode());
    }
    protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(22, new Integer((int)0));
    }

    public void setMobMode(int integer) {
    	this.dataWatcher.updateObject(22, Integer.valueOf(integer));
	}
    public int getMobMode() {
    	return this.dataWatcher.getWatchableObjectInt(22);
	}


    
    public boolean CanAttack(Entity entity){
    	return false;
    }

    
    protected int deadtime;
	
    
    
    
    
    
    
    
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityLivingData)
    {
        par1EntityLivingData = super.onSpawnWithEgg(par1EntityLivingData);
        {
            this.addRandomArmor();
            this.enchantEquipment();
        }

        this.setCanPickUpLoot(this.rand.nextFloat() < 0.55F * this.worldObj.func_147462_b(this.posX, this.posY, this.posZ));
        return par1EntityLivingData;
    }
	
	
	
	@SideOnly(Side.CLIENT)
    public int getBrightnessForRender(float par1)
    {
        return super.getBrightnessForRender(par1);
    }

    /**
     * Gets how bright this entity is.
     */
    /*public float getBrightness(float par1)
    {
        return 1.0F;
    }*/
    
    
    
    public boolean canAttackClass(Class par1Class)
    {
        return EntityCreature.class != par1Class;
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
        this.playSound("mob.irongolem.walk", 1F, 1.0F);
    }
    
    protected boolean canDespawn()
    {
        return false;
    }
    
    /**
     * Returns true if the newer Entity AI code should be run
     */
    public boolean isAIEnabled()
    {
        return true;
    }

	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
	}

	public void addRandomArmor() {
	}

	

	public boolean isConverting() {
		return false;
	}

	public static float getMobScale() {
		return 8;
	}


	@Override
	public Block getFlag() {
		return fn_PMCflag;
	}
	public boolean isCreatureType(EnumCreatureType type, boolean forSpawnCount)
	{
		return type.getCreatureClass() == EntitySoBases.class;
	}
	@Override
	public boolean istargetingflag() {
		return worldObj.getTileEntity(flagx,flagy,flagz) instanceof TileEntityFlag && worldObj.getBlock(flagx,flagy,flagz) != getFlag();
	}

	@Override
	public Vec3 getflagposition() {
		return Vec3.createVectorHelper(flagx,flagy,flagz);
	}

	@Override
	public void setflagposition(int x,int y,int z) {
		flagx = x;
		flagy = y;
		flagz = z;
	}
	@Override
	public boolean canEntityBeSeen(Entity p_70685_1_)
	{
		Vec3 startpos = Vec3.createVectorHelper(this.posX, this.posY + (double) this.getEyeHeight(), this.posZ);
		Vec3 targetpos = Vec3.createVectorHelper(p_70685_1_.posX, p_70685_1_.posY + (double) p_70685_1_.getEyeHeight(), p_70685_1_.posZ);
		Vec3 penerater = Vec3.createVectorHelper(p_70685_1_.posX - this.posX,  p_70685_1_.posY + (double) p_70685_1_.getEyeHeight()-(this.posY + (double) this.getEyeHeight()),p_70685_1_.posZ - this.posZ);
		penerater = penerater.normalize();
		MovingObjectPosition movingobjectposition = this.worldObj.rayTraceBlocks(startpos, targetpos);
		int abortcnt=0;
		while(movingobjectposition!=null) {
			if(abortcnt > 7)return false;
			Block hitedblock = this.worldObj.getBlock(movingobjectposition.blockX, movingobjectposition.blockY, movingobjectposition.blockZ);
			if(hitedblock.getMaterial().isOpaque()){
				return false;
			}
			Vec3 newstartpos = Vec3.createVectorHelper(movingobjectposition.hitVec.xCoord + penerater.xCoord, movingobjectposition.hitVec.yCoord + penerater.yCoord, movingobjectposition.hitVec.zCoord + penerater.zCoord);
			if(startpos.squareDistanceTo(newstartpos)>startpos.squareDistanceTo(targetpos))break;
			movingobjectposition = this.worldObj.rayTraceBlocks(newstartpos,targetpos);
			abortcnt++;
		}
		return true;
	}
	/**
	 * returns a (normalized) vector of where this entity is looking
	 */
	public Vec3 getLookVec()
	{
		return this.getLook(1.0F);
	}

	/**
	 * interpolated look vector
	 */
	public Vec3 getLook(float p_70676_1_)
	{
		float f1;
		float f2;
		float f3;
		float f4;

		if (p_70676_1_ == 1.0F)
		{
			f1 = MathHelper.cos(-this.rotationYawHead * 0.017453292F - (float)Math.PI);
			f2 = MathHelper.sin(-this.rotationYawHead * 0.017453292F - (float)Math.PI);
			f3 = -MathHelper.cos(-this.rotationPitch * 0.017453292F);
			f4 = MathHelper.sin(-this.rotationPitch * 0.017453292F);
			return Vec3.createVectorHelper((double)(f2 * f3), (double)f4, (double)(f1 * f3));
		}
		else
		{
			f1 = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * p_70676_1_;
			f2 = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * p_70676_1_;
			f3 = MathHelper.cos(-f2 * 0.017453292F - (float)Math.PI);
			f4 = MathHelper.sin(-f2 * 0.017453292F - (float)Math.PI);
			float f5 = -MathHelper.cos(-f1 * 0.017453292F);
			float f6 = MathHelper.sin(-f1 * 0.017453292F);
			return Vec3.createVectorHelper((double)(f4 * f5), (double)f6, (double)(f3 * f5));
		}
	}
	public void onUpdate() {
		super.onUpdate();
	}
	public void moveFlying(float p_70060_1_, float p_70060_2_, float p_70060_3_)
	{
		float f3 = p_70060_1_ * p_70060_1_ + p_70060_2_ * p_70060_2_;

		if (f3 >= 1.0E-4F)
		{
			f3 = MathHelper.sqrt_float(f3);

			if (f3 < 1.0F)
			{
				f3 = 1.0F;
			}
			f3 = p_70060_3_ / f3;
			f3 = abs(f3);
			p_70060_1_ *= f3;
			p_70060_2_ *= f3;
			float f4 = MathHelper.sin(this.rotationYaw * (float)Math.PI / 180.0F);
			float f5 = MathHelper.cos(this.rotationYaw * (float)Math.PI / 180.0F);
			this.motionX += (double)(-p_70060_2_ * f4 + p_70060_1_ * f5);
			this.motionZ += (double)( p_70060_2_ * f5 + p_70060_1_ * f4);
		}
	}

	@Override
	public boolean isthisFlagIsEnemys(Block block) {
		return block != fn_PMCflag && block != fn_Supplyflag;
	}
	
	public boolean shouldDismountInWater(Entity entity){
		return false;
	}
	
	
}
