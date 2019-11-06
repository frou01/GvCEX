package hmggvcmob.entity.friend;

import handmadeguns.items.guns.HMGItem_Unified_Guns;
import handmadevehicle.SlowPathFinder.WorldForPathfind;
import hmggvcmob.ai.AIAttackGun;
import hmggvcmob.ai.AINearestAttackableTarget;
import hmggvcmob.ai.AIattackOnCollide;
import handmadeguns.entity.IFF;
import hmggvcmob.ai.AIHurtByTarget;
import hmggvcmob.entity.IGVCmob;
import handmadevehicle.entity.parts.ITank;
import hmggvcmob.entity.guerrilla.EntityGBase;
import hmggvcmob.entity.guerrilla.EntityGBases;
import littleMaidMobX.LMM_EntityLittleMaid;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

import static handmadeguns.HandmadeGunsCore.islmmloaded;

public class EntityPMCBase extends EntitySoBases implements IFF,IGVCmob {

	public float viewWide = 1.14f;
	public float roo;
	public int deathTicks;
	public double movespeed = 0.3d;
	public float spread = 2;
	public double rndyaw;
	public double rndpitch;
	public int type = 0;
	public EntityAISwimming aiSwimming;
	public EntityAIOpenDoor AIOpenDoor;
	protected boolean IVehicle;
	AIAttackGun aiAttackGun;
	public int homeposX;
	public int homeposY;
	public int homeposZ;

	public int resetFollowpathCnt;

	public int moveoffsetx;
	public int moveoffsety;
	public int moveoffsetz;
	public Entity master;
	WorldForPathfind worldForPathfind;
	public int mode = 0;
	public boolean interact(EntityPlayer p_70085_1_) {
		if (super.interact(p_70085_1_)) {
			return false;
		} else if (!this.worldObj.isRemote && (this.riddenByEntity == null || this.riddenByEntity == p_70085_1_)) {
			if(p_70085_1_.isSneaking()){
				if (mode == 0) {
					mode = 1;
					homeposX = (int) posX;
					homeposY = (int) posY;
					homeposZ = (int) posZ;
					p_70085_1_.addChatComponentMessage(new ChatComponentTranslation(
							"Defense this position!"));
				} else if (mode == 1) {
					mode = 2;
					homeposX = (int) p_70085_1_.posX;
					homeposY = (int) p_70085_1_.posY;
					homeposZ = (int) p_70085_1_.posZ;
					master = p_70085_1_;
					p_70085_1_.addChatComponentMessage(new ChatComponentTranslation(
							"I'll Follow Leader!"));
				} else if (mode == 2){
					mode = 0;
				}
			}
			return true;
		}
		return false;
	}
	public EntityPMCBase(World par1World) {
		super(par1World);
		worldForPathfind = new WorldForPathfind(par1World);
		this.getNavigator().setBreakDoors(true);
		aiSwimming = new EntityAISwimming(this);
		AIOpenDoor           =new EntityAIOpenDoor(this, true);
		this.tasks.addTask(0, aiSwimming);
		this.tasks.addTask(3, new AIattackOnCollide(this, EntityLiving.class, 1.0D, true));
		this.tasks.addTask(3, new AIattackOnCollide(this, EntityGBases.class, 1.0D, true));
		this.tasks.addTask(4, new EntityAIRestrictOpenDoor(this));
		this.tasks.addTask(5, AIOpenDoor);
		this.tasks.addTask(6, new EntityAIMoveTowardsRestriction(this, this instanceof handmadevehicle.entity.parts.IVehicle ? 0: 1));
		//こっから先は待機時（？）
		this.tasks.addTask(7, new EntityAIWander(this, this instanceof handmadevehicle.entity.parts.IVehicle ? 0: 1));
		this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(8, new EntityAILookIdle(this));
		//ターゲティング
		this.targetTasks.addTask(1, new AIHurtByTarget(this, true));
		this.targetTasks.addTask(2, new AINearestAttackableTarget(this, EntityGBase.class, 0, true));
		this.targetTasks.addTask(3, new AINearestAttackableTarget(this, EntityLiving.class, 0, true, false, IMob.mobSelector));
	}



	public boolean canAttackClass(Class par1Class) {
		return EntityCreature.class != par1Class;
	}
	public void onUpdate()
    {
	    this.getEntityData().setBoolean("HMGisUsingItem",false);
        super.onUpdate();
		if(mode == 1) {
			if (this.getAttackTarget() == null && this.getDistanceSq(homeposX+1, homeposY+1, homeposZ+1)>256) {
				if(this.getNavigator().getPath() != null && this.getNavigator().getPath().getFinalPathPoint().xCoord != homeposX+1 && this.getNavigator().getPath().getFinalPathPoint().xCoord != homeposY && this.getNavigator().getPath().getFinalPathPoint().xCoord != homeposZ+1)this.getNavigator().setPath(worldForPathfind.getEntityPathToXYZ(this, homeposX+1, homeposY, homeposZ+1, 80f, true, false, false, true), 1.0d);
				if(onGround)this.getNavigator().setPath(worldForPathfind.getEntityPathToXYZ(this, homeposX+1, homeposY, homeposZ+1, 80f, true, false, false, true), 1.0d);
			}
		}else if(mode == 2) {
			if (master != null) {
				homeposX = (int) master.posX;
				homeposY = (int) master.posY;
				homeposZ = (int) master.posZ;
				if ((this.getAttackTarget() == null && this.getDistanceSq(homeposX, homeposY, homeposZ) > 64) || this.getDistanceSq(homeposX, homeposY + 1, homeposZ) > 256) {
					if (resetFollowpathCnt > 10) {
						homeposX = (int) master.posX + moveoffsetx;
						homeposY = (int) master.posY;
						homeposZ = (int) master.posZ + moveoffsetz;
						resetFollowpathCnt = 0;
						if (onGround || isInWater())
							this.getNavigator().setPath(worldForPathfind.getEntityPathToXYZ(this, homeposX, homeposY, homeposZ, 60f, true, false, false, true), 1.0d);
					}
				} else {
					moveoffsetx = rand.nextInt(12) * (this.rand.nextBoolean() ? -1 : 1);
					moveoffsetz = rand.nextInt(12) * (this.rand.nextBoolean() ? -1 : 1);
					resetFollowpathCnt = 0;
				}
				resetFollowpathCnt++;
			} else {
				mode = 0;
			}
		}
		if(rand.nextInt(10) == 0) this.addPotionEffect(new PotionEffect(Potion.regeneration.id, 1, 2));
    }
	
	public void onLivingUpdate()
    {
        this.updateArmSwingProgress();
        float f = this.getBrightness(1.0F);

        if (f > 0.5F)
        {
            this.entityAge += 2;
        }

        super.onLivingUpdate();
    }

    public boolean getCanSpawnHere()
    {
    	return false;
    }
    
    protected boolean isValidLightLevel()
    {
        int var1 = MathHelper.floor_double(this.posX);
        int var2 = MathHelper.floor_double(this.boundingBox.minY);
        int var3 = MathHelper.floor_double(this.posZ);

        if (this.worldObj.getSavedLightValue(EnumSkyBlock.Sky, var1, var2, var3) > this.rand.nextInt(32))
        {
            return false;
        }
        else
        {
            int var4 = this.worldObj.getBlockLightValue(var1, var2, var3);

            if (this.worldObj.isThundering())
            {
                int var5 = this.worldObj.skylightSubtracted;
                this.worldObj.skylightSubtracted = 10;
                var4 = this.worldObj.getBlockLightValue(var1, var2, var3);
                this.worldObj.skylightSubtracted = var5;
            }

            return var4 <= this.rand.nextInt(6);
        }
    }
	
	/**
	 * Returns true if the newer Entity AI code should be run
	 */
	public boolean isAIEnabled() {
		return true;
	}

	protected boolean canDespawn()
	{
		return false;
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
	public boolean is_this_entity_friend(Entity entity) {
		if(entity instanceof EntityPlayer){
			return true;
		}else if(entity instanceof EntitySoBases) {
			return true;
		}else if(islmmloaded && entity instanceof LMM_EntityLittleMaid){
			return true;
		}
		return false;
	}
	public void readEntityFromNBT(NBTTagCompound p_70037_1_)
	{
		super.readEntityFromNBT(p_70037_1_);

		homeposX = p_70037_1_.getInteger("homeposX");
		homeposY = p_70037_1_.getInteger("homeposY");
		homeposZ = p_70037_1_.getInteger("homeposZ");
		mode = p_70037_1_.getInteger("mode");
	}
	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	public void writeEntityToNBT(NBTTagCompound p_70014_1_)
	{
		super.writeEntityToNBT(p_70014_1_);

		p_70014_1_.setInteger("homeposX",homeposX);
		p_70014_1_.setInteger("homeposY",homeposY);
		p_70014_1_.setInteger("homeposZ",homeposZ);
		p_70014_1_.setInteger("mode",mode);
	}

	@Override
	public float getviewWide() {
		return viewWide;
	}

	@Override
	public boolean canhearsound(Entity target) {
		boolean flag;
		double dist = getDistanceToEntity(target);
		flag = dist < target.getEntityData().getFloat("GunshotLevel") * 16;
		return flag;
	}

	@Override
	public void setspawnedtile(TileEntity flag) {

	}
	public double[] getTargetpos() {
		return new double[]{homeposX,homeposY,homeposZ};
	}
}
