package hmggvcmob.entity.guerrilla;

import hmggvcmob.IflagBattler;
import handmadevehicle.SlowPathFinder.ModifiedPathNavigater;
import hmggvcmob.tile.TileEntityFlag;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import static handmadeguns.Util.Utils.getmovingobjectPosition_forBlock;
import static hmggvcmob.GVCMobPlus.fn_Guerrillaflag;
import static java.lang.Math.abs;

public class EntityGBases extends EntityMob implements IflagBattler{
    public boolean candespawn = true;
	//ゲームエンジン寄りな何やかんやを共通化しておくクラス？
	public int deathTicks;
	public int type = 0;
	public Block flag;


    public int flagx;
    public int flagy;
    public int flagz;
    
    private ModifiedPathNavigater modifiedPathNavigater;
    
    public EntityGBases(World par1World) {
        super(par1World);
        renderDistanceWeight = 16384;
        this.modifiedPathNavigater = new ModifiedPathNavigater(this, worldObj);
    }
    public PathNavigate getNavigator()
    {
        return this.modifiedPathNavigater;
    }
    protected void updateAITasks()
    {
        super.updateAITasks();
        modifiedPathNavigater.onUpdateNavigation();
    }
	/**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound p_70037_1_)
    {
        super.readEntityFromNBT(p_70037_1_);
    }
	/**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound p_70014_1_)
    {
        super.writeEntityToNBT(p_70014_1_);
    }
    protected void entityInit() {
		super.entityInit();
    }


    
    
    public boolean CanAttack(Entity entity){
    	return true;
    }
    
    private int deadtime;
	public void onUpdate()
    {
        super.onUpdate();
        if(this.getAttackTarget() != null && this.getAttackTarget().isDead)this.setAttackTarget(null);
        if(getMoveHelper().getSpeed()<0){
            getNavigator().clearPathEntity();
        }
        try {
        }catch (Exception e){

        }
        {
        	if (!this.worldObj.isRemote && (this.worldObj.difficultySetting == EnumDifficulty.PEACEFUL && this.candespawn))
            {
                this.setDead();
            }
        }
        {
        	++deadtime;
        	if (!this.worldObj.isRemote && deadtime > 6000)
            {
                this.setDead();
            }
        }
    }
    
    
    
    
    
    
    
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityLivingData)
    {
        par1EntityLivingData = super.onSpawnWithEgg(par1EntityLivingData);
        {
            this.addRandomArmor();
            this.enchantEquipment();
        }
        return par1EntityLivingData;
    }
	
	
	
	/*@SideOnly(Side.CLIENT)
    public int getBrightnessForRender(float par1)
    {
        return 15728880;
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
        return candespawn && getAttackTarget() == null;
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
		super.addRandomArmor();
	}

	public boolean isConverting() {
		return false;
	}

	public static float getMobScale() {
		return 8;
	}

    @Override
    public Block getFlag() {
        return fn_Guerrillaflag;
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
        MovingObjectPosition movingobjectposition = getmovingobjectPosition_forBlock(worldObj,startpos, targetpos, false, true, false);
        if(movingobjectposition!=null) {
            return false;
        }
        return !((this.isInWater() || p_70685_1_.isInWater()) && getDistanceSqToEntity(p_70685_1_) > 256);
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
        return block != fn_Guerrillaflag;
    }

}
