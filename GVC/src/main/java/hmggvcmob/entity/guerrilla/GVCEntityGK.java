package hmggvcmob.entity.guerrilla;

import handmadeguns.HandmadeGunsCore;
import hmggvcmob.SlowPathFinder.WorldForPathfind;
import hmggvcmob.ai.AIGKFire;
import hmggvcmob.ai.AIGKkick;
import hmggvcmob.entity.GVCEx;
import hmggvcmob.entity.IIRVING;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import static hmggvcmob.GVCMobPlus.cfg_guerrillasrach;
import static hmvehicle.Utils.CalculateGunElevationAngle;

public class GVCEntityGK extends EntityGBase implements IIRVING
{
	private int totalTicks;
	private int ticksAtLastPos;
	private boolean passedLastInductionpoint;
	private Vec3 lastPosCheck  = Vec3.createVectorHelper(0.0D, 0.0D, 0.0D);
	private int forget;
	private int tgthealth;
	public int kickprogeress;
	public int Accumulationdamage_leg;
	public boolean staning;
	public boolean vehicle = false;
	public boolean opentop = true;
	public WorldForPathfind worldForPathfind;
	public float bodyrotationYaw;
	public float bodyrotationPitch;
	public float turretrotationYaw;
	public float turretrotationPitch;
	public int angletime;
	public float rote;
	int fireCycle1;
	int fireCycle2;
	int jumpCycle3;
	int jumpprogress3;
	public int soundtick = 0;
	int dash_jump;
	public boolean onstarting = false;
	public boolean onstopping = false;
	public boolean isstanding = false;
	public boolean combattask_2 = false;
	public boolean combattask_4 = false;
	// public int type;
	public GVCEntityGK(World par1World)
	{
		super(par1World);
		this.setSize(2.5F, 4F);
		this.tasks.removeTask(AIattackOncollidetoPlayer);
		this.tasks.removeTask(AIattackOncollidetoVillager);
		this.tasks.removeTask(AIattackOncollidetoSoldier);
		this.tasks.removeTask(AIRestrictOpenDoor);
		this.tasks.removeTask(EntityAIOpenDoor);
		this.tasks.removeTask(AIMoveTowardsRestriction);
		this.tasks.removeTask(AIMoveThroughVillage);
		this.tasks.addTask(1,new AIGKkick(this,this,3.5f));
		this.tasks.addTask(2,new AIGKFire(this));
		this.tasks.addTask(3,new EntityAIWander(this,1));
		this.targetTasks.removeTask(AIattackOncollidetoPlayer);
		this.targetTasks.removeTask(AIattackOncollidetoVillager);
		this.targetTasks.removeTask(AIattackOncollidetoSoldier);
		this.targetTasks.removeTask(EntityAIOpenDoor);
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		this.jumpCycle3 = 120;
		canuseAlreadyPlacedGun = false;
	}


	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(26, new Byte((byte)0));
		this.dataWatcher.addObject(27, new Byte((byte)0));
		this.dataWatcher.addObject(28, new Byte((byte)0));
		this.dataWatcher.addObject(29, new Byte((byte)0));
	}

	public double getMountedYOffset() {
		return 2.0D;
	}


	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0D);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(90.0D);
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(cfg_guerrillasrach);
		this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(30.0D);
	}



	public boolean attackEntityFrom(DamageSource source, float par2)
	{

		if(source.getDamageType().equals("fall")){
			return false;
		}
		if(!staning && source.getEntity() != null){
			Accumulationdamage_leg+=par2;
		}
		if(!source.isFireDamage()) {
			if (par2 <= 10) {
				this.playSound("random.anvil_land", 0.5F, 1F);
				return false;
			} else if (par2 > 10 && par2 <= 49) {
				this.playSound("random.anvil_land", 0.5F, 1.5F);
				par2 = par2 / 2;
			}
			return super.attackEntityFrom(source, par2);
		}else {
			return false;
		}
	}

	public void addRandomArmor()
	{
		super.addRandomArmor();
	}

	public void onUpdate()
	{
		super.onUpdate();
		if(!this.worldObj.isRemote){

			for(int x = (int)this.boundingBox.minX-1;x<=this.boundingBox.maxX;x++){
				for(int y = (int)this.boundingBox.minY-1;y<=this.boundingBox.maxY;y++){
					for(int z = (int)this.boundingBox.minZ-1;z<=this.boundingBox.maxZ;z++){
						Block collidingblock = worldObj.getBlock(x,y,z);
						if(collidingblock.getMaterial() == Material.leaves || collidingblock.getMaterial() == Material.wood || collidingblock.getMaterial() == Material.glass || collidingblock.getMaterial() == Material.cloth){
							worldObj.setBlockToAir(x,y,z);
						}
					}
				}
			}
			Accumulationdamage_leg--;
			if(this.getHealth() <= this.getMaxHealth()/2){
				if(this.getHealth() <= this.getMaxHealth()/4){
					this.worldObj.spawnParticle("smoke", this.posX-2, this.posY + 2D, this.posZ+2, 0.0D, 0.0D, 0.0D);
					this.worldObj.spawnParticle("smoke", this.posX+2, this.posY + 2D, this.posZ-1, 0.0D, 0.0D, 0.0D);
					int rx = this.worldObj.rand.nextInt(5);
					int rz = this.worldObj.rand.nextInt(5);
					this.worldObj.spawnParticle("flame", this.posX-2+rx, this.posY + 2D, this.posZ-2+rz, 0.0D, 0.0D, 0.0D);
					this.worldObj.spawnParticle("flame", this.posX-2+rx, this.posY + 2D, this.posZ-2+rz, 0.0D, 0.0D, 0.0D);
				}else{
					this.worldObj.spawnParticle("smoke", this.posX-2, this.posY + 2D, this.posZ+2, 0.0D, 0.0D, 0.0D);
					this.worldObj.spawnParticle("smoke", this.posX+2, this.posY + 2D, this.posZ-1, 0.0D, 0.0D, 0.0D);
					int rx = this.worldObj.rand.nextInt(5);
					int rz = this.worldObj.rand.nextInt(5);
					this.worldObj.spawnParticle("smoke", this.posX-2+rx, this.posY + 2D, this.posZ-2+rz, 0.0D, 0.0D, 0.0D);
				}
			}
			if(Accumulationdamage_leg>80){
				this.setSneaking(true);
				staning = true;
			}
			if(staning){
				Accumulationdamage_leg--;
				combattask_2 = false;
				combattask_4 = false;
				getNavigator().clearPathEntity();
				kickprogeress = 0;
			}
			if(Accumulationdamage_leg<0){
				this.setSneaking(false);
				staning = false;
				Accumulationdamage_leg = 0;
			}
			this.dataWatcher.updateObject(26,new Byte(combattask_2 ? (byte)0:(byte)1));
			this.dataWatcher.updateObject(27,new Byte(combattask_4 ? (byte)0:(byte)1));
		}else {
			combattask_2 = 0 == ((Byte)this.dataWatcher.getWatchableObjectByte(26)).byteValue();
			combattask_4 = 0 == ((Byte)this.dataWatcher.getWatchableObjectByte(27)).byteValue();
			if(this.isSneaking()){
				Accumulationdamage_leg-=2;
				combattask_2 = false;
				combattask_4 = false;
				angletime = 0;
				onstopping = false;
				onstarting = false;
			}else {
				if (combattask_4) {
					++kickprogeress;
					if (kickprogeress > 10) {
						combattask_4 = false;
						kickprogeress = 0;
					}
				} else if (combattask_2) {
					++kickprogeress;
					if (kickprogeress > 2) {
						combattask_2 = false;
						kickprogeress = 0;
					}
				}
				if (this.getDistance(prevPosX,posY,prevPosZ)!=0){
					onstarting = true;
					isstanding = false;
					onstopping = false;
					int an = 8;
					if(angletime == 20){
						HandmadeGunsCore.proxy.playsoundat("gvcguns:gvcguns.wark",1,3,1,(float) posX,(float)posY,(float)posZ);
					}
					if(angletime == 100){
						HandmadeGunsCore.proxy.playsoundat("gvcguns:gvcguns.wark",1,3,1,(float) posX,(float)posY,(float)posZ);
					}
					if(angletime == 80){
						HandmadeGunsCore.proxy.playsoundat("gvcguns:gvcguns.zye",1,3,1,(float) posX,(float)posY,(float)posZ);
					}
					if(angletime == 160){
						HandmadeGunsCore.proxy.playsoundat("gvcguns:gvcguns.zye",1,3,1,(float) posX,(float)posY,(float)posZ);
					}
					if (angletime < 160) {
						angletime += an;
					} else {
						angletime = 0;
						onstarting = false;
					}
				} else {
					isstanding = true;
					angletime =0;
					onstarting = false;
					onstopping = true;
				}
			}
		}
	}



	protected String getLivingSound()
	{
		return "mob.cow.say";
	}

	protected void onDeathUpdate() {
		++this.deathTicks;
		if(this.deathTicks == 1){
			//this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 0F, false);
			GVCEx ex = new GVCEx(this, 3F);
			ex.Ex();
		}
		if (this.deathTicks == 150 && !this.worldObj.isRemote) {
			this.setDead();
			this.dropItem(new ItemStack(Blocks.gold_block, 0).getItem(), 2);
			this.dropItem(new ItemStack(Blocks.iron_block, 0).getItem(), 4);
			this.dropItem(new ItemStack(Blocks.redstone_block, 0).getItem(),2);
			this.dropItem(new ItemStack(Blocks.emerald_block, 0).getItem(),3);
			if(rnd.nextInt(5) == 0){
				this.dropItem(new ItemStack(Blocks.beacon,0).getItem(),1);
			}
		}
	}

	public boolean isConverting() {
		return false;
	}

	@Override
	public boolean isstaning() {
		return staning;
	}

	@Override
	public boolean iscombattask_4() {
		return combattask_4;
	}

	@Override
	public int getkickprogeress() {
		return kickprogeress;
	}

	@Override
	public void setcombattask_4(boolean value) {
		combattask_4 = value;
	}

	@Override
	public void setkickprogeress(int value) {
		kickprogeress = value;
	}
}
