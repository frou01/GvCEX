package hmggvcmob.entity.guerrilla;

import handmadeguns.entity.IMGGunner;
import handmadeguns.entity.PlacedGunEntity;
import handmadeguns.items.guns.HMGItem_Unified_Guns;
import hmggvcmob.GVCMobPlus;
import hmggvcmob.IflagBattler;
import hmggvcmob.ai.*;
import handmadeguns.entity.IFF;
import hmggvcmob.ai.AIHurtByTarget;
import hmggvcmob.entity.IGVCmob;
import hmggvcmob.entity.friend.EntitySoBases;
import hmggvcmob.entity.friend.GVCEntityFlag;
import hmggvcmob.tile.TileEntityFlag;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

import static hmggvcmob.GVCMobPlus.cfg_guerrillacanusePlacedGun;
import static java.lang.Math.pow;
import static net.minecraft.util.MathHelper.wrapAngleTo180_float;

public class EntityGBase extends EntityGBases implements IFF,IGVCmob,IMGGunner {
	public boolean flaginvensionmode = false;
	public float viewWide = 0.267f;
	//AI等行動関係を共通化しておくクラス
    Random rnd = new Random();
	public int staningtime;
	public float roo;
	public int deathTicks;
	public int type = 0;
	public float spread = 10;
	public double movespeed = 0.3d;
	TileEntity spawnedtile = null;
	int placing;
	boolean canuseAlreadyPlacedGun = true;
	boolean canusePlacedGun = true;


	EntityAISwimming aiSwimming;
	AIattackOnCollide              AIattackOncollidetoPlayer;
	AIattackOnCollide              AIattackOncollidetoVillager;
	AIattackOnCollide              AIattackOncollidetoSoldier;
	EntityAIRestrictOpenDoor       AIRestrictOpenDoor;
	EntityAIOpenDoor               EntityAIOpenDoor;
	EntityAIMoveTowardsRestriction AIMoveTowardsRestriction;
	AIMoveThroughVillage           AIMoveThroughVillage;
	AIAttackGun aiAttackGun;

	public EntityGBase(World par1World) {
		super(par1World);
		aiSwimming =new EntityAISwimming(this);
		AIattackOncollidetoPlayer  =new AIattackOnCollide(this, EntityPlayer.class, 1.0D, true);
		AIattackOncollidetoVillager= new AIattackOnCollide(this, EntityVillager.class, 1.0D, true);
		AIattackOncollidetoSoldier =new AIattackOnCollide(this, EntitySoBases.class, 1.0D, true);
		AIRestrictOpenDoor         =new EntityAIRestrictOpenDoor(this);
		EntityAIOpenDoor           =new EntityAIOpenDoor(this, true);
		AIMoveTowardsRestriction   =new EntityAIMoveTowardsRestriction(this, 1.0D);
		AIMoveThroughVillage       =new AIMoveThroughVillage(this, 1.0D, false);
		this.getNavigator().setBreakDoors(true);
		this.tasks.addTask(0, aiSwimming);
        this.tasks.addTask(3, AIattackOncollidetoPlayer);
        this.tasks.addTask(3, AIattackOncollidetoVillager);
		this.tasks.addTask(3, AIattackOncollidetoSoldier);
		this.tasks.addTask(3, new AIAttackFlag(this,(IflagBattler) this));
		this.tasks.addTask(4, AIRestrictOpenDoor);
		this.tasks.addTask(5, EntityAIOpenDoor);
		this.tasks.addTask(6, AIMoveTowardsRestriction);
		this.tasks.addTask(7, AIMoveThroughVillage);
		//こっから先は待機時（？）
		this.tasks.addTask(8, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(8, new EntityAILookIdle(this));
		//ターゲティング
		this.targetTasks.addTask(1, new AIHurtByTarget(this, true));
		this.targetTasks.addTask(2, new AINearestAttackableTarget(this, EntityPlayer.class, 0, true));
		this.targetTasks.addTask(2, new AINearestAttackableTarget(this, EntitySoBases.class, 0, true));
		this.targetTasks.addTask(2, new AINearestAttackableTarget(this, EntityVillager.class, 0, true));
		this.targetTasks.addTask(2, new AINearestAttackableTarget(this, EntityGolem.class, 0, true));
		this.targetTasks.addTask(2, new AINearestAttackableTarget(this, GVCEntityFlag.class, 0, false));
		this.targetTasks.addTask(4, new AITargetFlag(this,this,this));
		this.setCanPickUpLoot(true);
//		this.targetTasks.addTask(2, new AINearestAttackableTarget(this, EntityLiving.class, 0, false));
	}

	public boolean attackEntityFrom(DamageSource source, float par2)
	{
		if(!worldObj.isRemote) {
			if (this.getHeldItem() != null && (source.getEntity() != getAttackTarget()||par2>7) && (source.getDamageType().equals("mob") || source.getDamageType().equals("player"))) {
				this.entityDropItem(this.getHeldItem(), 1);
				if (this.getHeldItem().getItem() instanceof HMGItem_Unified_Guns && ((HMGItem_Unified_Guns) this.getHeldItem().getItem()).getcurrentMagazine(this.getHeldItem()) != null) {
					this.dropItem(((HMGItem_Unified_Guns) this.getHeldItem().getItem()).getcurrentMagazine(this.getHeldItem()), 1);
				}
				staningtime = 10;
				this.setCurrentItemOrArmor(0, null);
			}
		}
		return super.attackEntityFrom(source, par2);

	}
	public void dropMagazine(){
		if (this.getHeldItem().getItem() instanceof HMGItem_Unified_Guns && ((HMGItem_Unified_Guns) this.getHeldItem().getItem()).getcurrentMagazine(this.getHeldItem()) != null) {
			this.dropItem(((HMGItem_Unified_Guns) this.getHeldItem().getItem()).getcurrentMagazine(this.getHeldItem()), 1);
		}
	}
//	@SideOnly(Side.CLIENT)
//	public int getBrightnessForRender(float par1) {
//		System.out.println("par1" + par1);
//		return (int)(par1*16);
//	}

	/**
	 * Gets how bright this entity is.
	 */
//	public float getBrightness(float par1) {
//		return par1;
//	}

	public boolean canAttackClass(Class par1Class) {
		return EntityCreature.class != par1Class;
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
	public int getVerticalFaceSpeed()
	{
		return 90;
	}
    public void onUpdate(){
		super.onUpdate();
		staningtime--;
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(movespeed);

	
	    if(cfg_guerrillacanusePlacedGun && canuseAlreadyPlacedGun && !worldObj.isRemote && ridingEntity == null && this.getAttackTarget() != null) {
		    List PlaceGunDetector = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(2, 3.0D, 2));
		
		    if (PlaceGunDetector != null && !PlaceGunDetector.isEmpty()) {
			    for (int i = 0; i < PlaceGunDetector.size(); ++i) {
				    Entity colliedentity = (Entity) PlaceGunDetector.get(i);
				    if (colliedentity.riddenByEntity == null && colliedentity instanceof PlacedGunEntity) {
					    this.mountEntity((PlacedGunEntity) colliedentity);
					    this.setCurrentItemOrArmor(0, null);
				    }
			    }
		    }
	    }
		if(this.getHeldItem() != null && this.aiAttackGun != null){
			this.getHeldItem().getItem().onUpdate(this.getHeldItem(),worldObj,this,0,true);
			if(!worldObj.isRemote && cfg_guerrillacanusePlacedGun && canusePlacedGun && ridingEntity == null && onGround &&this.getAttackTarget() != null && this.getHeldItem().getItem()instanceof HMGItem_Unified_Guns && ((HMGItem_Unified_Guns) this.getHeldItem().getItem()).gunInfo.fixAsEntity){
				placing ++;
				if(placing>30) {
					placing = 0;
					//視線方向のブロックを調べる
					int headdirction_four = getdirection_in4(
							wrapAngleTo180_float(rotationYawHead));
//					System.out.println("debug" + headdirction_four);
					Block willGunSetBlock = null;
					int[] offset = new int[2];
					switch (headdirction_four) {
						case 0:
							//south
							willGunSetBlock = worldObj.getBlock((int) posX-1, (int) posY-1, (int) posZ);
							offset[0] = -1;
							offset[1] = 0;
							break;
						case -1:
							//east
							willGunSetBlock = worldObj.getBlock((int) posX, (int) posY-1, (int) posZ-1);
							offset[0] = 0;
							offset[1] = -1;
							break;
						case 1:
							//west
							willGunSetBlock = worldObj.getBlock((int) posX-2, (int) posY-1, (int) posZ-1);
							offset[0] = -2;
							offset[1] = -1;
							break;
						case 2:
							//north
							willGunSetBlock = worldObj.getBlock((int) posX-1, (int) posY-1, (int) posZ - 2);
							offset[0] = -1;
							offset[1] = -2;
							break;
					}
					if (willGunSetBlock != null && willGunSetBlock != Blocks.air) {
						PlacedGunEntity gunEntity = new PlacedGunEntity(worldObj, getHeldItem());
						gunEntity.setLocationAndAngles((int) this.posX + 0.5f + offset[0], this.posY + 1.8, (int) this.posZ + 0.5f + offset[1], this.rotationYaw, this.rotationPitch);
						gunEntity.issummonbyMob = true;
						worldObj.spawnEntityInWorld(gunEntity);
						this.mountEntity(gunEntity);
						this.setCurrentItemOrArmor(0, null);
					}
				}
			}

//			if(bullets != this.getHeldItem().getItemDamage()){
//				this.aiAttackGun.burstingtime++;
//			}


			if(this.getEntityData().getBoolean("HMGisUsingItem")){
				this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(movespeed/4);
			}else{
				this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(movespeed);
			}
		}

		if(!worldObj.isRemote &&
				this.ridingEntity instanceof PlacedGunEntity &&
				((PlacedGunEntity) ridingEntity).issummonbyMob &&
				ridingEntity.ridingEntity == null &&
				(this.getAttackTarget() == null || this.getAttackTarget().isDead)){
			this.setCurrentItemOrArmor(0,((PlacedGunEntity) ridingEntity).gunStack);
			((PlacedGunEntity) ridingEntity).gunStack = null;
			ridingEntity.setDead();
			ridingEntity = null;
		}
		if(ridingEntity instanceof PlacedGunEntity){
			if(this.getEntityData().getBoolean("HMGisUsingItem")){
				((PlacedGunEntity) ridingEntity).firing = true;
			}else {
				((PlacedGunEntity) ridingEntity).firing = false;
			}
		}
		this.getEntityData().setBoolean("HMGisUsingItem",false);
	}
    
//    public boolean spawnhight(){
//    	boolean spawn = true;
//
//    	int var1 = MathHelper.floor_double(this.posX);
//        int var2 = MathHelper.floor_double(this.boundingBox.minY);
//        int var3 = MathHelper.floor_double(this.posZ);
//        if(var2 < this.worldObj.getHeightValue(var1, var3) - 5 && this.rand.nextInt(16) != 0){
//        	spawn = false;
//        }
//
//        return spawn;
//    }

	
	/**
	 * Returns true if the newer Entity AI code should be run
	 */

	public boolean canPickUpLoot(){
		return staningtime < 0;
	}
	public boolean isAIEnabled() {
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

	public void setDead(){
		super.setDead();
		if(spawnedtile != null && spawnedtile instanceof TileEntityFlag)((TileEntityFlag) spawnedtile).spawnedEntities.remove(this);
	}

	public static float getMobScale() {
		return 8;
	}
	@Override
	public boolean is_this_entity_friend(Entity entity) {
		return false;
	}

	@Override
	public float getviewWide() {
		return viewWide;
	}


	@Override
	public boolean getCanSpawnHere()
	{
		flaginvensionmode = false;
		for(int i = 0;i<worldObj.loadedTileEntityList.size();i++) {
			TileEntity tileentity;
			Object aLoadedTileEntityList = worldObj.loadedTileEntityList.get(i);
			if(aLoadedTileEntityList != null) {
				tileentity = (TileEntity) aLoadedTileEntityList;
				flaginvensionmode = (tileentity.getBlockType() == GVCMobPlus.fn_Supplyflag);
				if (flaginvensionmode) break;
			}
		}
		return super.getCanSpawnHere();
	}
	@Override
	public float getBlockPathWeight(int p_70783_1_, int p_70783_2_, int p_70783_3_)
	{
		return flaginvensionmode ? 0.0F : (0.5F - this.worldObj.getLightBrightness(p_70783_1_, p_70783_2_, p_70783_3_));
	}
	@Override
	protected boolean isValidLightLevel()
	{
		int i = MathHelper.floor_double(this.posX);
		int j = MathHelper.floor_double(this.boundingBox.minY);
		int k = MathHelper.floor_double(this.posZ);
		if(!flaginvensionmode || !worldObj.canBlockSeeTheSky(i, j, k)) return super.isValidLightLevel();


		if (this.worldObj.getSavedLightValue(EnumSkyBlock.Sky, i, j, k) <= this.rand.nextInt(32))
		{
			return false;
		}
		else
		{
			int l = this.worldObj.getSavedLightValue(EnumSkyBlock.Block,i, j, k);
			int l2= this.worldObj.getBlockLightValue(i, j, k);

			if (this.worldObj.isThundering())
			{
				int i1 = this.worldObj.skylightSubtracted;
				this.worldObj.skylightSubtracted = 10;
				l2 = this.worldObj.getBlockLightValue(i, j, k);
				this.worldObj.skylightSubtracted = i1;
			}

			return l < this.rand.nextInt(6) && l2>9 + this.rand.nextInt(8);
		}
	}

	@Override
	public boolean canSeeTarget(Entity target) {
		boolean flag;
		flag = canhearsound(target);
		if (!flag) {
			Vec3 lookVec = getLookVec();
			Vec3 toTGTvec = Vec3.createVectorHelper(target.posX - posX, target.posY + target.getEyeHeight() - (posY + getEyeHeight()), target.posZ - posZ);
			toTGTvec = toTGTvec.normalize();
			float enemylight = target.getBrightness(1.0f);
			float thislight = this.getBrightness(1.0f);
			return getDistanceSqToEntity(target) < pow(60 * (1 + enemylight - thislight) * (0.1 + thislight*0.9),2)&& lookVec.squareDistanceTo(toTGTvec) < getviewWide()  * (1 + enemylight - thislight) * (0.4 + thislight*0.6)/2;
		}else{
			return true;
		}
	}

	@Override
	public boolean canhearsound(Entity target) {
		boolean flag;
		double dist = getDistanceToEntity(target);
		flag = dist < target.getEntityData().getFloat("GunshotLevel") * 10;
		return flag;
	}

	@Override
	public void setspawnedtile(TileEntity flag) {
		spawnedtile = flag;
	}
	public int getdirection_in4(float globalDir){
		if(globalDir <= 45 && globalDir > -45){
			return 0;
		}
		if(globalDir <= 135 && globalDir > 45){
			return 1;
		}
		if(globalDir <= -45 && globalDir > -135){
			return -1;
		}
		if(globalDir <= -135 || globalDir > 135){
			return 2;
		}
		return 0;
	}

	@Override
	public void extraprocessInMGFire() {
	}
}
